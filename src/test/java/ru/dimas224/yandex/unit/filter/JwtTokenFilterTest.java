package ru.dimas224.yandex.unit.filter;

import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.Test;
import ru.dimas224.yandex.security.JwtTokenFilter;
import ru.dimas224.yandex.security.JwtTokenProvider;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class JwtTokenFilterTest {
  private final JwtTokenProvider provider = mock(JwtTokenProvider.class);
  private final JwtTokenFilter filter = new JwtTokenFilter(provider);

  private final HttpServletRequest request = mock(HttpServletRequest.class);
  private final HttpServletResponse response = mock(HttpServletResponse.class);
  private final FilterChain filterChain = mock(FilterChain.class);

  @Test
  void  shouldDoFilter() throws ServletException, IOException {
    String token = "token";

    when(request.getRequestURI()).thenReturn("/messages");
    when(provider.resolveToken(request)).thenReturn(token);
    when(provider.validateToken(token)).thenReturn(true);

    filter.doFilter(request, response, filterChain);

    verify(filterChain).doFilter(request, response);
  }

  @Test
  void  shouldDoNotFilter() throws ServletException, IOException {
    String token = "token";

    when(request.getRequestURI()).thenReturn("/messages");
    when(provider.resolveToken(request)).thenReturn(token);
    when(provider.validateToken(token)).thenReturn(false);

    filter.doFilter(request, response, filterChain);

    verify(response).sendError(HttpServletResponse.SC_FORBIDDEN, "Invalid token");
    verify(filterChain, never()).doFilter(request, response);
  }
}
