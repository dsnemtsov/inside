package ru.dimas224.yandex.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.Date;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.dimas224.yandex.exception.JwtAuthenticationException;

@Component
public class JwtTokenProvider {

  @Value("${jwt-token-secret}")
  private String secret;
  @Value("${jwt-token-expired}")
  private long validityInMilliseconds;

  public String createToken(String name) {
    Claims claims = Jwts.claims().setSubject(name);

    Date now = new Date();
    Date validity = new Date(now.getTime() + validityInMilliseconds);

    return Jwts.builder()
            .setClaims(claims)
            .setIssuedAt(now)
            .setExpiration(validity)
            .signWith(SignatureAlgorithm.HS256, secret)
            .compact();
  }

  public String resolveToken(HttpServletRequest req) {
    String bearerToken = req.getHeader("Authorization");
    if (bearerToken != null && bearerToken.startsWith("Bearer_")) {
      return bearerToken.substring(7);
    }
    return null;
  }

  public boolean validateToken(String token) {
    try {
      Jws<Claims> claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(token);

      return !claims.getBody().getExpiration().before(new Date());
    } catch (JwtException | IllegalArgumentException e) {
      throw new JwtAuthenticationException("JWT token is expired or invalid");
    }
  }
}
