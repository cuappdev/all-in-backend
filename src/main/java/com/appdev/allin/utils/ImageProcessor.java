package com.appdev.allin.utils;

import com.drew.imaging.ImageMetadataReader;
import com.drew.metadata.Metadata;
import com.drew.metadata.exif.ExifIFD0Directory;

import java.awt.image.BufferedImage;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.io.File;
import java.io.IOException;

import org.imgscalr.Scalr;

public class ImageProcessor {

    /**
     * Crops the given image to the specified width and height.
     * If the specified dimensions are larger than the original image,
     * the method ensures that the cropped area does not exceed the image bounds.
     *
     * @param originalImage The original BufferedImage to be cropped.
     * @param targetWidth   The desired width of the cropped image.
     * @param targetHeight  The desired height of the cropped image.
     * @return A new BufferedImage representing the cropped portion of the original
     *         image.
     */
    public static BufferedImage cropImage(BufferedImage originalImage, int targetWidth, int targetHeight) {
        int originalWidth = originalImage.getWidth();
        int originalHeight = originalImage.getHeight();

        int x = (originalWidth - targetWidth) / 2;
        int y = (originalHeight - targetHeight) / 2;

        x = Math.max(0, x);
        y = Math.max(0, y);

        targetWidth = Math.min(targetWidth, originalWidth - x);
        targetHeight = Math.min(targetHeight, originalHeight - y);

        return originalImage.getSubimage(x, y, targetWidth, targetHeight);
    }

    /**
     * Corrects the orientation of an image based on its EXIF metadata.
     * For images with metadata specifying an orientation, the image is rotated
     * to match the correct orientation. If no metadata is available or if an error
     * occurs, the original image is returned.
     *
     * @param image The BufferedImage to correct.
     * @param file  The image file used to retrieve EXIF metadata.
     * @return A new BufferedImage with corrected orientation, or the original image
     *         if no correction is necessary.
     * @throws IOException If an error occurs while reading the file.
     */
    public static BufferedImage correctOrientation(BufferedImage image, File file) throws IOException {
        try {
            Metadata metadata = ImageMetadataReader.readMetadata(file);
            if (metadata == null) {
                System.err.println("No metadata found for orientation correction.");
                return image;
            }
            ExifIFD0Directory directory = metadata.getFirstDirectoryOfType(ExifIFD0Directory.class);
            if (directory != null && directory.containsTag(ExifIFD0Directory.TAG_ORIENTATION)) {
                int orientation = directory.getInt(ExifIFD0Directory.TAG_ORIENTATION);
                switch (orientation) {
                    case 6:
                        return rotateImage(image, 90);
                    case 3:
                        return rotateImage(image, 180);
                    case 8:
                        return rotateImage(image, 270);
                    default:
                        return image;
                }
            }
        } catch (Exception e) {
            System.err.println("Could not determine image orientation: " + e.getMessage());
        }
        return image;
    }

    /**
     * Rotates image by [angle] degrees.
     * 
     * @param image The original BufferedImage to rotate.
     * @param angle The angle in degrees by which to rotate the image. Positive
     *              values rotate the image clockwise, and negative values rotate it
     *              counterclockwise.
     * @return A new BufferedImage representing the rotated image.
     */
    public static BufferedImage rotateImage(BufferedImage image, double angle) {
        // Calculate the new image dimensions after rotation
        double radians = Math.toRadians(angle);
        int newWidth = (int) Math.abs(image.getWidth() * Math.cos(radians))
                + (int) Math.abs(image.getHeight() * Math.sin(radians));
        int newHeight = (int) Math.abs(image.getWidth() * Math.sin(radians))
                + (int) Math.abs(image.getHeight() * Math.cos(radians));

        // Create a new image with the calculated dimensions
        BufferedImage rotatedImage = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = rotatedImage.createGraphics();

        // Set the rendering hints for better quality
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Calculate the rotation point (center of the original image)
        int x = (newWidth - image.getWidth()) / 2;
        int y = (newHeight - image.getHeight()) / 2;

        // Rotate around the center of the new image
        g2d.rotate(radians, newWidth / 2.0, newHeight / 2.0);
        g2d.drawImage(image, x, y, null);
        g2d.dispose();

        System.out.println("Image rotated by " + angle + " degrees.");
        return rotatedImage;
    }

    /**
     * Scales image to target width and height.
     * 
     * @param originalImage The original BufferedImage to be scaled.
     * @param targetWidth   The desired width of the scaled image.
     * @param targetHeight  The desired height of the scaled image.
     * @return A new BufferedImage representing the scaled image.
     */
    public static BufferedImage scaleImage(BufferedImage originalImage, int targetWidth, int targetHeight) {
        return Scalr.resize(originalImage, Scalr.Method.ULTRA_QUALITY, targetWidth, targetHeight);
    }
}