package com.appdev.allin;

import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.appdev.allin.exceptions.UnauthorizedException;
import com.appdev.allin.user.User;
import com.appdev.allin.user.UserRepo;
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

  private final UserRepo userRepo;

  public FirebaseTokenFilter(UserRepo userRepo) {
    this.userRepo = userRepo;
  }

  @Override
  public void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response,
      @NonNull FilterChain chain)
      throws ServletException, IOException, UnauthorizedException {

    String token = request.getHeader("Authorization");
    if (token == null) {
      setErrorResponse(response, HttpServletResponse.SC_UNAUTHORIZED, "Authorization token not provided");
      return;
    }
    if (!token.startsWith("Bearer ")) {
      setErrorResponse(response, HttpServletResponse.SC_UNAUTHORIZED, "Authorization token must start with 'Bearer '");
      return;
    }
    token = token.substring(7);

    FirebaseToken decodedToken;
    try {
      decodedToken = FirebaseAuth.getInstance().verifyIdToken(token);
    } catch (FirebaseAuthException e) {
      setErrorResponse(response, HttpServletResponse.SC_UNAUTHORIZED, "Error verifying token: " + e.getMessage());
      return;
    }

    try {
      User user = userRepo.findById(decodedToken.getUid())
          .orElseGet(() -> {
            // Only create a user on the /users/me endpoint
            if (request.getRequestURI().equals("/users/me")) {
              User newUser = new User(decodedToken.getUid(), decodedToken.getName(), decodedToken.getEmail(),
                  decodedToken.getPicture());
              return userRepo.save(newUser);
            }
            return null;
          });

      if (user == null) {
        setErrorResponse(response, HttpServletResponse.SC_UNAUTHORIZED, "User not found");
        return;
      }

      // Used to inject the user into the @AuthenticationPrincipal annotated variable
      // used in the controller
      AbstractAuthenticationToken auth = new AbstractAuthenticationToken(AuthorityUtils.NO_AUTHORITIES) {
        @Override
        public Object getCredentials() {
          return null;
        }

        @Override
        public Object getPrincipal() {
          return user;
        }
      };
      auth.setAuthenticated(true);
      SecurityContextHolder.getContext().setAuthentication(auth);
    } catch (Exception e) {
      setErrorResponse(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
          "Error processing token: " + e.getMessage());
      return;
    }

    chain.doFilter(request, response);

  }

  @Override
  public boolean shouldNotFilter(@NonNull HttpServletRequest request) throws ServletException {
    // TODO: Delete when done
    // return true;
    String path = request.getRequestURI();
    return swaggerPath(path);
  }

  private boolean swaggerPath(String path) {
    return path.startsWith("/swagger-ui") ||
        path.startsWith("/v3/api-docs") ||
        path.startsWith("/swagger-resources") ||
        path.startsWith("/webjars");
  }

  private void setErrorResponse(HttpServletResponse response, int status, String message) throws IOException {
    response.setStatus(status);
    response.setContentType("application/json");
    response.getWriter().write("{\"error\": \"" + message + "\"}");
  }
}
