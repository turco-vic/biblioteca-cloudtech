package com.cloudtech.biblioteca;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record Livro(
		Long id,
		@NotBlank String titulo,
		@NotBlank String autor,
		@NotBlank String editora,
		@NotNull Integer ano,
		@NotBlank String categoria,
		@NotBlank @Pattern(regexp = "Disponível|Emprestado") String status) {
}
