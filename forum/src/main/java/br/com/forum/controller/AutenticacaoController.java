package br.com.forum.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.forum.controller.dto.TokenDto;
import br.com.forum.controller.form.LoginForm;
import br.com.forum.service.AutenticacaoLoginService;

@RestController
@RequestMapping("/auth")
public class AutenticacaoController {

    @Autowired
    private AutenticacaoLoginService service;

    @PostMapping
    public ResponseEntity<TokenDto> autenticar(@RequestBody @Valid LoginForm form) {
        return service.atutenticar(form);

    }

}
