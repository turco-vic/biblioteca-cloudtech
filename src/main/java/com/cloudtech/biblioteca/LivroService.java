package com.cloudtech.biblioteca;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

@Service
public class LivroService {

	private final Map<Long, Livro> livros = new LinkedHashMap<>();
	private long proximoId = 1;

	public synchronized Livro cadastrar(Livro dados) {
		Livro livro = new Livro(proximoId++, dados.titulo(), dados.autor(), dados.editora(),
				dados.ano(), dados.categoria(), dados.status());
		livros.put(livro.id(), livro);
		return livro;
	}

}
