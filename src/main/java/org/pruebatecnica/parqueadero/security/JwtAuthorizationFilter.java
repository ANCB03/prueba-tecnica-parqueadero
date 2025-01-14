package org.pruebatecnica.parqueadero.security;




import io.jsonwebtoken.ExpiredJwtException;
import org.pruebatecnica.parqueadero.implement.UsuarioImplement;
import org.pruebatecnica.parqueadero.services.BlackListTokenService;
import org.pruebatecnica.parqueadero.util.Constants;
import org.pruebatecnica.parqueadero.util.TokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtAuthorizationFilter extends OncePerRequestFilter {

	@Autowired
	private  UsuarioImplement userService;
	@Autowired
	private  BlackListTokenService blackListTokenService;


	@Override
	protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
									FilterChain filterChain) throws ServletException, IOException {

		String authorizationHeader = httpServletRequest.getHeader(Constants.HEADER_AUTHORIZATION_KEY);

		if (StringUtils.isEmpty(authorizationHeader) || !authorizationHeader
				.startsWith(Constants.TOKEN_BEARER_PREFIX)) {
			filterChain.doFilter(httpServletRequest, httpServletResponse);
			return;
		}
		final String token = authorizationHeader.replace(Constants.TOKEN_BEARER_PREFIX + " ", "");
		try {
			// Verificar si el token está en la lista negra
			if (blackListTokenService.isTokenBlacklisted(token)) {
				SecurityContextHolder.clearContext();
				httpServletResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
				httpServletResponse.setContentType("application/json");
				httpServletResponse.getWriter().write("{\"error\": \"Token invalidado por logout\"}");
				return;
			}

			String userName = TokenProvider.getUserName(token);
			UserDetails user = userService.loadUserByUsername(userName);
			UsernamePasswordAuthenticationToken authenticationToken = TokenProvider.getAuthentication(token, user);
			SecurityContextHolder.getContext().setAuthentication(authenticationToken);
			filterChain.doFilter(httpServletRequest, httpServletResponse);
		} catch (ExpiredJwtException ex) {
			SecurityContextHolder.clearContext();
			httpServletResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			httpServletResponse.setContentType("application/json");
			httpServletResponse.getWriter().write("{\"error\": \"Token JWT vencido o inválido\"}");
		}
	}
}
