package com.sardeiro.minhasfinancas.model.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.sardeiro.minhasfinancas.model.entity.Usuario;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
public class UsuarioRepositoryTest {

	@Autowired
	UsuarioRepository repository;

	@Test
	public void deveVerificarAExistenciaDeUmEmail() {
		// cenário
		Usuario usuario = Usuario.builder().nome("usuario").email("usuario.gmail.com").build();
		repository.save(usuario);
		// ação/execução

		boolean result = repository.existsByEmail("usuario.gmail.com");

		// verificação
		Assertions.assertThat(result).isTrue();

	}

	@Test
	public void deveRetornarFalsoQuandoNaoHouverUsuarioCadastradoComEmail() {

		// cenário
		repository.deleteAll();
		// ação/execução

		boolean result = repository.existsByEmail("usuario.gmail.com");

		// verificação
		Assertions.assertThat(result).isFalse();

	}

}
