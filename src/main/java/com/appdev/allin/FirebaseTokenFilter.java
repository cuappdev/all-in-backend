package com.appdev.allin;

import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import com.appdev.allin.exceptions.UnauthorizedException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class FirebaseTokenFilter extends OncePerRequestFilter {
  @Override
  public void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response,
      @NonNull FilterChain chain)
      throws ServletException, IOException, UnauthorizedException {

    String token = request.getHeader("Authorization");
    if (token == null) {
      throw new UnauthorizedException("Authorization token not provided");
    }

    FirebaseToken decodedToken = null;
    try {
      decodedToken = FirebaseAuth.getInstance().verifyIdToken(token);
    } catch (FirebaseAuthException e) {
      throw new UnauthorizedException("Error verifying token: " + e.getMessage());
    }

    request.setAttribute("uid", decodedToken.getUid());
    request.setAttribute("username", decodedToken.getName());
    request.setAttribute("email", decodedToken.getEmail());
    request.setAttribute("image", decodedToken.getPicture());

    chain.doFilter(request, response);
  }

  @Override
  public boolean shouldNotFilter(@NonNull HttpServletRequest request) throws ServletException {
    String path = request.getRequestURI();
    return path.startsWith("/users/authorize") || swaggerPath(path);
  }

  private boolean swaggerPath(String path) {
    return path.startsWith("/swagger-ui") ||
        path.startsWith("/v3/api-docs") ||
        path.startsWith("/swagger-resources") ||
        path.startsWith("/webjars");
  }
}
