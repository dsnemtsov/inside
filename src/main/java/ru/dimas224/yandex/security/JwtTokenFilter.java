package ru.dimas224.yandex.security;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class JwtTokenFilter implements Filter {

  private JwtTokenProvider jwtTokenProvider;

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
    HttpServletRequest req = (HttpServletRequest) request;
    String uri = req.getRequestURI();

    if (uri.contains("/messages")) {
      String token = jwtTokenProvider.resolveToken(req);
      if (token == null || !jwtTokenProvider.validateToken(token)) {
        ((HttpServletResponse) response)
                .sendError(HttpServletResponse.SC_FORBIDDEN, "Invalid token");
        return;
      }
    }
    chain.doFilter(request, response);
  }
}
