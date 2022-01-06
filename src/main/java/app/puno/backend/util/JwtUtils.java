package app.puno.backend.util;

import app.puno.backend.model.Profile;
import app.puno.backend.model.Profile.Role;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.time.temporal.ChronoUnit;
import java.util.Base64;
import java.util.UUID;
import java.util.function.Function;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class JwtUtils {

	private final JwtParser jwtParser;
	private final Key secretKey;

	public JwtUtils(@Value("${jwt.secret:secret_jwt_key_here_puno_app}") String secret) {
		this.secretKey = Keys.hmacShaKeyFor(Base64.getEncoder().encode(secret.getBytes(StandardCharsets.UTF_8)));
		this.jwtParser = Jwts.parserBuilder().setSigningKey(secretKey).build();
	}

	public String createToken(Profile profile) {
		return Jwts.builder()
				.setIssuer("puno.app")
				.setSubject(profile.getId().toString())
				.claim("id", profile.getId())
				.claim("role", profile.getRole().name())
				.setIssuedAt(TimeUtils.getDateNow())
				.setExpiration(TimeUtils.getDateNowAfter(1, ChronoUnit.MINUTES))
				.signWith(secretKey, SignatureAlgorithm.HS256)
				.compact();
	}


	@Nullable
	private Claims parseClaims(String token) {
		try {
			return jwtParser.parseClaimsJws(token).getBody();
		} catch (JwtException ex) {
			return null;
		}
	}

	@Nullable
	public <T> T withExtractor(String token, Function<Claims, T> extractor) {
		Claims claims = parseClaims(token);
		Boolean expired = hasExpired(token);
		if (claims == null || expired == null || expired) {
			return null;
		}
		return extractor.apply(claims);
	}

	@Nullable
	private <T> T withExtractor(String token, String claim, Class<T> clazz) {
		return withExtractor(token, claims -> claims.get(claim, clazz));
	}

	@Nullable
	public Boolean hasExpired(String token) {
		Claims claims = parseClaims(token);
		if (claims == null) {
			return null;
		}
		return claims.getExpiration().before(TimeUtils.getDateNow());
	}

	@Nullable
	public UUID getProfileId(String token) {
		return withExtractor(token, claims -> UUID.fromString(claims.get("id", String.class)));
	}

	@Nullable
	public Role getRole(String token) {
		return withExtractor(token, claims -> Role.valueOf(claims.get("role", String.class)));
	}

}
