package com.sardeiro.minhasfinancas.service;

import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.sardeiro.minhasfinancas.exception.ErroAutenticacao;
import com.sardeiro.minhasfinancas.exception.RegraNegocioException;
import com.sardeiro.minhasfinancas.model.entity.Usuario;
import com.sardeiro.minhasfinancas.model.repository.UsuarioRepository;
import com.sardeiro.minhasfinancas.service.impl.UsuarioServiceImpl;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
public class UsuarioServiceTest {

	@SpyBean
	UsuarioServiceImpl service;

	@MockBean
	UsuarioRepository repository;

	@Test
	public void deveSalvarUmUsuario() {
		Assertions.assertDoesNotThrow(() -> {
		// cenario
		Mockito.doNothing().when(service).validarEmail(Mockito.anyString());
		Usuario usuario = Usuario.builder().id(1L).nome("nome").email("email@email.com").senha("senha").build();
		Mockito.when(repository.save(Mockito.any(Usuario.class))).thenReturn(usuario);
		// acao
		Usuario usuarioSalvo = service.salvarUsuario(new Usuario());
		// verificacao
		org.assertj.core.api.Assertions.assertThat(usuarioSalvo).isNotNull();
		org.assertj.core.api.Assertions.assertThat(usuarioSalvo.getId()).isEqualTo(1L);
		org.assertj.core.api.Assertions.assertThat(usuarioSalvo.getNome()).isEqualTo("nome");
		org.assertj.core.api.Assertions.assertThat(usuarioSalvo.getEmail()).isEqualTo("email@email.com");
		org.assertj.core.api.Assertions.assertThat(usuarioSalvo.getSenha()).isEqualTo("senha");
		});
	}
	
	@Test
	public void naoDeveSalvarUmUsuarioComEmailJaCadastrado() {
		
		Assertions.assertThrows(RegraNegocioException.class, () -> {
		//cenario
		String email = "email@email.com";
		Usuario usuario = Usuario.builder().email(email).build();
		
		Mockito.doThrow(RegraNegocioException.class).when(service).validarEmail(email);
		//acao
		
		service.salvarUsuario(usuario);
		
		//verificacao
		Mockito.verify(repository,Mockito.never()).save(usuario);
		});
	}

	@Test
	public void deveAutenticarUmUsuarioComSucesso() {
		Assertions.assertDoesNotThrow(() -> {

			// cenario
			String email = "email@email.com";
			String senha = "senha";

			Usuario usuario = Usuario.builder().email(email).senha(senha).id(1L).build();
			Mockito.when(repository.findByEmail(email)).thenReturn(Optional.of(usuario));

			// açao
			Usuario result = service.autenticar(email, senha);

			// verificacao

			org.assertj.core.api.Assertions.assertThat(usuario).isNotNull();
		});

	}

	@Test
	public void deveLancarErroQuandoNaoEncontradoUsuarioCadastradoComEmail() {

		// cenario
		Mockito.when(repository.findByEmail(Mockito.anyString())).thenReturn(Optional.empty());

		// acao
		Throwable exception = org.assertj.core.api.Assertions
				.catchThrowable(() -> service.autenticar("email@email.com", "senha"));

		// verificacao

		org.assertj.core.api.Assertions.assertThat(exception).isInstanceOf(ErroAutenticacao.class)
				.hasMessage("Usuário não encontrado!");

	}

	@Test
	public void deveLancarErroQuandoSenhaNaoBater() {

		String senha = "senha";
		Usuario usuario = Usuario.builder().email("email@email.com").senha(senha).build();
		Mockito.when(repository.findByEmail(Mockito.anyString())).thenReturn(Optional.of(usuario));

		// acao
		Throwable exception = org.assertj.core.api.Assertions
				.catchThrowable(() -> service.autenticar("email@email.com", "123"));
		org.assertj.core.api.Assertions.assertThat(exception).isInstanceOf(ErroAutenticacao.class)
				.hasMessage("Senha invalida!");

	}

	@Test
	public void deveValidarEmail() {
		Assertions.assertDoesNotThrow(() -> {

			// cenario
			Mockito.when(repository.existsByEmail(Mockito.anyString())).thenReturn(false);

			// açao

			service.validarEmail("email@email.com");

		});
	}

	@Test
	public void deveLancarErroAoValidarEmailQuandoExistirEmailCadastrado() {
		Assertions.assertThrows(RegraNegocioException.class, () -> {
			// cenario

			Mockito.when(repository.existsByEmail(Mockito.anyString())).thenReturn(true);

			// acao
			service.validarEmail("email@email.com");
		});
	}

}
