package com.cloudtech.biblioteca;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Service;

@Service
public class LivroService {

	private final Map<Long, Livro> livros = new LinkedHashMap<>();
	private long proximoId = 1;

	public synchronized List<Livro> listar() {
		return new ArrayList<>(livros.values());
	}

	public synchronized Optional<Livro> buscarPorId(Long id) {
		return Optional.ofNullable(livros.get(id));
	}

	public synchronized Livro cadastrar(Livro dados) {
		Livro livro = new Livro(proximoId++, dados.titulo(), dados.autor(), dados.editora(),
				dados.ano(), dados.categoria(), dados.status());
		livros.put(livro.id(), livro);
		return livro;
	}

	public synchronized Optional<Livro> alterar(Long id, Livro dados) {
		if (!livros.containsKey(id)) {
			return Optional.empty();
		}
		Livro livro = new Livro(id, dados.titulo(), dados.autor(), dados.editora(),
				dados.ano(), dados.categoria(), dados.status());
		livros.put(id, livro);
		return Optional.of(livro);
	}

}
