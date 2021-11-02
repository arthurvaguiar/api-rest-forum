package br.com.forum.service;

import javax.validation.Valid;

import org.h2.security.auth.AuthConfigException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import br.com.forum.config.security.TokenService;
import br.com.forum.controller.dto.TokenDto;
import br.com.forum.controller.form.LoginForm;

@Service
public class AutenticacaoLoginService {

	@Autowired
	private AuthenticationManager authManager;
	
	@Autowired
	private TokenService tokenService;

	public ResponseEntity<TokenDto> atutenticar(@Valid LoginForm form) {

		UsernamePasswordAuthenticationToken dadosLogin = form.converter();

		try {
			
			Authentication authentication = authManager.authenticate(dadosLogin);
			String token = tokenService.gerarToken(authentication);
			return ResponseEntity.ok(new TokenDto(token, "Bearer"));

		} catch (AuthConfigException e) {
		
			return ResponseEntity.badRequest().build();
		
		}
	}
	
	

}
