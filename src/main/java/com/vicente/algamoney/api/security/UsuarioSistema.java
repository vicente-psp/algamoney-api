package com.vicente.algamoney.api.security;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import com.vicente.algamoney.api.model.Usuario;

import lombok.Getter;

public class UsuarioSistema extends User {
	
	@Getter
	private Usuario usuario;

	public UsuarioSistema(Usuario usuario, Collection<? extends GrantedAuthority> authorities) {
		super(usuario.getEmail(), usuario.getSenha(), authorities);
		this.usuario = usuario;
	}

	private static final long serialVersionUID = 1L;

}
