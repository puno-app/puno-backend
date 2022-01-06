package app.puno.backend.security.filter;

import app.puno.backend.model.Profile;
import app.puno.backend.repository.ProfileRepository;
import app.puno.backend.service.ProfileService;
import app.puno.backend.service.ProfileService.ProfileUserDetails;
import app.puno.backend.util.JwtUtils;
import java.io.IOException;
import java.util.UUID;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

	private final JwtUtils jwtUtils;
	private final ProfileRepository profileRepository;

	public JwtRequestFilter( JwtUtils jwtUtils,  ProfileRepository profileRepository) {
		this.jwtUtils = jwtUtils;
		this.profileRepository = profileRepository;
	}

	@Override
	protected void doFilterInternal( HttpServletRequest request,  HttpServletResponse response,
			 FilterChain filterChain)
			throws ServletException, IOException {
		String authenticationHeader = request.getHeader("Authorization");
		if (authenticationHeader == null) {
			filterChain.doFilter(request, response);
			return;
		}

		// Bearer_ -- 7 characters
		String bearerToken = authenticationHeader.substring(7);
		UUID profileId = jwtUtils.getProfileId(bearerToken);
		if (profileId == null) {
			filterChain.doFilter(request, response);
			return;
		}

		Profile profile = profileRepository.findById(profileId).orElse(null);
		if (profile == null) {
			filterChain.doFilter(request, response);
			return;
		}

		ProfileUserDetails profileUserDetails = ProfileService.wrapProfile(profile);
		UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
				profileUserDetails,
				null,
				profileUserDetails.getAuthorities()
		);

		authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
		SecurityContextHolder.getContext().setAuthentication(authenticationToken);
		filterChain.doFilter(request, response);
	}

}
