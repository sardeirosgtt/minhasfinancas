package com.sardeiro.minhasfinancas.model.repository;

import org.aspectj.apache.bcel.Repository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.sardeiro.minhasfinancas.model.entity.Usuario;


@SpringBootTest
//@RunWith(SpringRunner.class)
public class UsuarioRepositoryTest {
	
	@Autowired
	UsuarioRepository repository;
	
	@Test
	public void deveVerificarAExistenciaDeUmEmail() {
		//cenário
		Usuario usuario = Usuario.builder().nome("usuario").email("usuario.gmail.com").build();
		repository.save(usuario);
		//ação/execução
		boolean result = repository.existsByEmail("usuario.gmail.com");
		//verificação
		
		
	}

}
