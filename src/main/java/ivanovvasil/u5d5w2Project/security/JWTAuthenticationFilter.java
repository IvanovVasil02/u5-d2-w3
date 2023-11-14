package ivanovvasil.u5d5w2Project.security;

import ivanovvasil.u5d5w2Project.entities.User;
import ivanovvasil.u5d5w2Project.exceptions.AccesDeniedException;
import ivanovvasil.u5d5w2Project.services.UsersService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JWTAuthenticationFilter extends OncePerRequestFilter {

  @Autowired
  private JWTools jwTools;
  @Autowired
  private UsersService usersService;

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, AccesDeniedException, IOException {
    String authenticationHeader = request.getHeader("Authorization");
    if (authenticationHeader == null || !authenticationHeader.startsWith("Bearer ")) {
      throw new AccesDeniedException("Empty Bearer Token or missing Bearer keyword");
    } else {
      String userToken = authenticationHeader.substring(7);

      jwTools.verifyToken(userToken);

      int userId = Integer.parseInt(jwTools.extractIdFromToken(userToken));
      User currentUser = usersService.findById(userId);

      Authentication authentication = new UsernamePasswordAuthenticationToken(currentUser, null);
      SecurityContextHolder.getContext().setAuthentication(authentication);

      filterChain.doFilter(request, response);
    }
  }

  @Override
  protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
    return new AntPathMatcher().match("/authentication/**", request.getServletPath());
  }
}
