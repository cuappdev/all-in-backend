package com.appdev.allin.utils;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

import org.imgscalr.Scalr;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.drew.imaging.ImageMetadataReader;
import com.drew.metadata.Metadata;
import com.drew.metadata.exif.ExifIFD0Directory;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
public class ImageProcessor {
    @Value("${all-in.image-upload-url}")
    private static String imageUploadUrl;
    @Value("${allin.bucket}")
    private String bucket;
    public static BufferedImage cropImage(BufferedImage originalImage, int targetWidth, int targetHeight) {
        int originalWidth = originalImage.getWidth();
        int originalHeight = originalImage.getHeight();
        int x = Math.max(0, (originalWidth - targetWidth) / 2);
        int y = Math.max(0, (originalHeight - targetHeight) / 2);
        targetWidth = Math.min(targetWidth, originalWidth - x);
        targetHeight = Math.min(targetHeight, originalHeight - y);
        return originalImage.getSubimage(x, y, targetWidth, targetHeight);
    }
    public static BufferedImage correctOrientation(BufferedImage image, File file) throws IOException {
        try {
            Metadata metadata = ImageMetadataReader.readMetadata(file);
            if (metadata == null) return image;
            ExifIFD0Directory directory = metadata.getFirstDirectoryOfType(ExifIFD0Directory.class);
            if (directory != null && directory.containsTag(ExifIFD0Directory.TAG_ORIENTATION)) {
                int orientation = directory.getInt(ExifIFD0Directory.TAG_ORIENTATION);
                return switch (orientation) {
                    case 6 -> rotateImage(image, 90);
                    case 3 -> rotateImage(image, 180);
                    case 8 -> rotateImage(image, 270);
                    default -> image;
                };
            }
        } catch (Exception e) {
            System.err.println("Could not determine image orientation: " + e.getMessage());
        }
        return image;
    }
    public static BufferedImage rotateImage(BufferedImage image, double angle) {
        double radians = Math.toRadians(angle);
        int newWidth = (int) Math.abs(image.getWidth() * Math.cos(radians)) + (int) Math.abs(image.getHeight() * Math.sin(radians));
        int newHeight = (int) Math.abs(image.getWidth() * Math.sin(radians)) + (int) Math.abs(image.getHeight() * Math.cos(radians));
        BufferedImage rotatedImage = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = rotatedImage.createGraphics();
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        int x = (newWidth - image.getWidth()) / 2;
        int y = (newHeight - image.getHeight()) / 2;
        g2d.rotate(radians, newWidth / 2.0, newHeight / 2.0);
        g2d.drawImage(image, x, y, null);
        g2d.dispose();
        return rotatedImage;
    }
    public static BufferedImage scaleImage(BufferedImage originalImage, int targetWidth, int targetHeight) {
        return Scalr.resize(originalImage, Scalr.Method.ULTRA_QUALITY, targetWidth, targetHeight);
    }
    public static BufferedImage convertBase64ToImage(String base64String) throws IOException {
        String base64Image = base64String.split(",")[base64String.split(",").length - 1];
        byte[] imageBytes = Base64.getDecoder().decode(base64Image);
        try (ByteArrayInputStream bis = new ByteArrayInputStream(imageBytes)) {
            return ImageIO.read(bis);
        }
    }

    public static String urlToBase64(String imageUrl) throws IOException {
        if (imageUrl == null || imageUrl.trim().isEmpty()) {
            throw new IllegalArgumentException("Provided imageUrl is null or empty");
        }

        URL url = new URL(imageUrl);

        try (InputStream is = url.openStream();
            ByteArrayOutputStream baos = new ByteArrayOutputStream()) {

            byte[] buffer = new byte[8192];
            int bytesRead;

            while ((bytesRead = is.read(buffer)) != -1) {
                baos.write(buffer, 0, bytesRead);
            }

            byte[] imageBytes = baos.toByteArray();
            
            if (imageBytes.length == 0) {
                throw new IOException("No data read from the provided image URL: " + imageUrl);
            }
            
            return Base64.getEncoder().encodeToString(imageBytes);
        }
    }
    
    public static String uploadImage(String encodedImage, int targetWidth, int targetHeight) {
        try {
            String[] parts = encodedImage.split(",");
            String format = "png";
            if (parts[0].contains("jpeg")) format = "jpg";
            else if (parts[0].contains("webp")) format = "webp";
            BufferedImage image = convertBase64ToImage(encodedImage);
            image = scaleImage(image, targetWidth, targetHeight);
            if ("webp".equals(format)) {
                System.out.println("Converting WebP to PNG");
                format = "png";
            }
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            ImageIO.write(image, format, os);
            byte[] imageBytes = os.toByteArray();
            String mimeType = switch (format) {
                case "jpg" -> "image/jpeg";
                case "png" -> "image/png";
                default -> "image/png";
            };
            String dataUri = "data:" + mimeType + ";base64," + Base64.getEncoder().encodeToString(imageBytes);
            Map<String, Object> payload = new HashMap<>();
            payload.put("bucket", "all-in");
            payload.put("image", dataUri);
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<Map<String, Object>> request = new HttpEntity<>(payload, headers);
            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<String> response = restTemplate.postForEntity("https://upload.cornellappdev.com/upload/", request, String.class);
            if (response.getStatusCode().is2xxSuccessful()) {
                JsonNode json = new ObjectMapper().readTree(response.getBody());
                return json.has("data") ? json.get("data").asText() : null;
            } else {
                throw new RuntimeException("Upload failed: " + response.getStatusCode());
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to upload image: " + e.getMessage(), e);
        }
    }
    public static void main(String[] args) {
        try {
            File file = new File("src/main/resources/static/images/players/resize.webp");
            byte[] fileBytes = new byte[(int) file.length()];
            try (FileInputStream fis = new FileInputStream(file)) {
                fis.read(fileBytes);
            } catch (FileNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            String base64Image = Base64.getEncoder().encodeToString(fileBytes);
            // // You can replace these with actual strings instead of env vars for testing
            // String uploadUrl = System.getenv("DIGITAL_OCEAN_URL");
            // String bucket = System.getenv("BUCKET_NAME");
            // if (uploadUrl == null || bucket == null) {
            // System.err.println(":x: DIGITAL_OCEAN_URL or BUCKET_NAME is not set.");
            // return;
            // }
           String uploadedUrl = uploadImage(base64Image, 256, 256);
           System.out.println(":white_check_mark: Uploaded image URL: " + uploadedUrl);
        } catch (HttpServerErrorException e) {
            System.err.println(":x: 500 Internal Server Error");
            System.err.println("Status Code: " + e.getStatusCode());
            System.err.println("Response Body:");
            System.err.println(e.getResponseBodyAsString());
            System.err.println("Headers:");
            System.err.println(e.getResponseHeaders());
            throw e;
        } catch (HttpClientErrorException e) {
            System.err.println(":x: 4xx Client Error");
            System.err.println("Status Code: " + e.getStatusCode());
            System.err.println("Response Body:");
            System.err.println(e.getResponseBodyAsString());
            System.err.println("Headers:");
            System.err.println(e.getResponseHeaders());
            throw e;
        } catch (RestClientException e) {
            System.err.println(":x: General RestClientException:");
            e.printStackTrace();
            throw e;
        }
    }
}