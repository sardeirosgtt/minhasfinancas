package com.sardeiro.minhasfinancas.service;

import java.util.Optional;

import com.sardeiro.minhasfinancas.model.entity.Usuario;

public interface UsuarioService {

	Usuario autenticar(String email, String senha);

	Usuario salvarUsuario(Usuario usuario);

	void validarEmail(String email);
	
	Optional <Usuario> obterPorId(Long id);

}
