package com.cloudtech.biblioteca;

import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

	@GetMapping("/")
	public Map<String, String> inicio() {
		return Map.of("mensagem", "Bem-vindo ao sistema de gerenciamento da Biblioteca CloudTech Solutions");
	}

}
