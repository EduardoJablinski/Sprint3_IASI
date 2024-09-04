package com.iasi.iasi.service;

import com.iasi.iasi.model.Usuario;
import com.iasi.iasi.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public void registrarUsuario(Usuario usuario) {
        String senhaCriptografada = passwordEncoder.encode(usuario.getSenhaUsuario());
        usuario.setSenhaUsuario(senhaCriptografada);
        usuarioRepository.save(usuario);
    }

    public boolean verificarCredenciais(String nomeUsuario, String senha) {
        Usuario usuario = usuarioRepository.findByNomeUsuario(nomeUsuario);
        if (usuario != null) {
            return passwordEncoder.matches(senha, usuario.getSenhaUsuario());
        }
        return false;
    }
}
