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
      throw new UnauthorizedException("Authorization token not provided");
    }
    if (token.startsWith("Bearer ")) {
      token = token.substring(7);
    }

    try {
      FirebaseToken decodedToken = FirebaseAuth.getInstance().verifyIdToken(token);

      User user = userRepo.findByUid(decodedToken.getUid())
          .orElseGet(() -> {
            // Only create a user on the /users/authorize endpoint
            if (request.getRequestURI().equals("/users/authorize")) {
              User newUser = new User(decodedToken.getUid(), decodedToken.getName(), decodedToken.getEmail(),
                  decodedToken.getPicture());
              return userRepo.save(newUser);
            }
            return null;
          });

      if (user == null) {
        throw new UnauthorizedException("User not found");
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

    } catch (FirebaseAuthException e) {
      throw new UnauthorizedException("Error verifying token: " + e.getMessage());
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
}
