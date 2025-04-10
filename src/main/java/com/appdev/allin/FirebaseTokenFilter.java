package com.appdev.allin;

import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;
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

  /**
   * Authenticating user via fireBase authorizer verify fireBase token and extract
   * Uid and Email from token
   */
  @Override
  protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response,
      @NonNull FilterChain chain)
      throws ServletException, IOException {

    // Extracts token from header
    String token = request.getHeader("Authorization");

    // checks if token is there
    if (token == null)
      throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Missing token!");

    FirebaseToken decodedToken = null;
    try {
      // verifies token to firebase server
      decodedToken = FirebaseAuth.getInstance().verifyIdToken(token);
    } catch (FirebaseAuthException e) {
      throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Error! " + e.toString());
    }
    // if token is invalid
    if (decodedToken == null) {
      throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid token!");
    }

    // Extract Uid and Email
    String uid = decodedToken.getUid();
    String email = decodedToken.getEmail();

    /*
     * //set Uid and Email to request
     * void setAttribute(java.lang.String name, java.lang.Object o)
     */

    request.setAttribute("uid", uid);
    request.setAttribute("email", email);

    chain.doFilter(request, response);
  }

  @Override
  protected boolean shouldNotFilter(@NonNull HttpServletRequest request) throws ServletException {
    String path = request.getRequestURI();
    return path.startsWith("/swagger-ui") ||
        path.startsWith("/v3/api-docs") ||
        path.startsWith("/swagger-resources") ||
        path.startsWith("/webjars");
  }
}