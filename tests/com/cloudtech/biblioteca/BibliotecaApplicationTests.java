package com.cloudtech.biblioteca;

import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.jayway.jsonpath.JsonPath;

@SpringBootTest
@AutoConfigureMockMvc
class BibliotecaApplicationTests {

	private static final String LIVRO_JSON = """
			{
			  "titulo": "Banco de Dados para Iniciantes",
			  "autor": "Maria Souza",
			  "editora": "EduTech",
			  "ano": 2023,
			  "categoria": "Informática",
			  "status": "Disponível"
			}
			""";

	@Autowired
	private MockMvc mockMvc;

	private Integer cadastrarLivro() throws Exception {
		String resposta = mockMvc.perform(post("/livros/cadastrar")
				.contentType(MediaType.APPLICATION_JSON)
				.content(LIVRO_JSON))
				.andReturn().getResponse().getContentAsString();
		return JsonPath.read(resposta, "$.id");
	}

	@Test
	void paginaInicialRetornaMensagemDeBoasVindas() throws Exception {
		mockMvc.perform(get("/"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.mensagem")
						.value("Bem-vindo ao sistema de gerenciamento da Biblioteca CloudTech Solutions"));
	}

	@Test
	void listarLivrosRetornaLivrosCadastrados() throws Exception {
		Integer id = cadastrarLivro();

		mockMvc.perform(get("/livros"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$[*].id", hasItem(id)));
	}

	@Test
	void cadastrarLivroRetornaLivroComId() throws Exception {
		mockMvc.perform(post("/livros/cadastrar")
				.contentType(MediaType.APPLICATION_JSON)
				.content(LIVRO_JSON))
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.id").isNumber())
				.andExpect(jsonPath("$.titulo").value("Banco de Dados para Iniciantes"))
				.andExpect(jsonPath("$.status").value("Disponível"));
	}

	@Test
	void alterarLivroAtualizaOsDados() throws Exception {
		Integer id = cadastrarLivro();

		mockMvc.perform(put("/livros/alterar/" + id)
				.contentType(MediaType.APPLICATION_JSON)
				.content("""
						{
						  "titulo": "Banco de Dados Essencial",
						  "autor": "Maria Souza",
						  "editora": "EduTech",
						  "ano": 2024,
						  "categoria": "Banco de Dados",
						  "status": "Emprestado"
						}
						"""))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id").value(id))
				.andExpect(jsonPath("$.titulo").value("Banco de Dados Essencial"))
				.andExpect(jsonPath("$.status").value("Emprestado"));
	}

	@Test
	void consultarLivroPorIdRetornaOLivro() throws Exception {
		Integer id = cadastrarLivro();

		mockMvc.perform(get("/livros/" + id))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id").value(id))
				.andExpect(jsonPath("$.autor").value("Maria Souza"));
	}

	@Test
	void documentacaoListaOsEndpoints() throws Exception {
		mockMvc.perform(get("/documentacao"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.endpoints.length()").value(6))
				.andExpect(jsonPath("$.endpoints", hasItem("/livros/alterar/{id}")));
	}

}
