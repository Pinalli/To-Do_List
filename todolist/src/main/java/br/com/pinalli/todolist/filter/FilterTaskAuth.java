package br.com.pinalli.todolist.filter;

import java.io.IOException;
import java.util.Base64;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import at.favre.lib.crypto.bcrypt.BCrypt;
import br.com.pinalli.todolist.user.IUserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class FilterTaskAuth extends OncePerRequestFilter {

  @Autowired
  private IUserRepository userRepository;

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {

    var servletPath = request.getServletPath();

    if (servletPath.startsWith("/tasks/")) {
      // get authentication of the user
      var authorization = request.getHeader("Authorization");
      // get only hash password of the user
      var authEncoded = authorization.substring("Basic".length()).trim();
      // decode the hash password
      byte[] authDecode = Base64.getDecoder().decode(authEncoded);
      // convert to string
      var authString = new String(authDecode);
      String[] credentials = authString.split(":");
      String username = credentials[0];
      String password = credentials[1];
      // check if user is valid
      var validUser = this.userRepository.findByUsername(username);
      if (validUser == null) {
        response.sendError(401);
      } else {
        var passwordVerify = BCrypt.verifyer().verify(password.toCharArray(), validUser.getPassword());
        if (passwordVerify.verified) {
          // set id of the user in the request
          request.setAttribute("idUser", validUser.getId());
          filterChain.doFilter(request, response);

        } else {
          response.sendError(401);
        }
      }
    } else {
      filterChain.doFilter(request, response);
    }
  }
}