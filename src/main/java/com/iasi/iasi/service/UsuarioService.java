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
            System.out.println("Usuário encontrado: " + usuario.getNomeUsuario());
            boolean matches = passwordEncoder.matches(senha, usuario.getSenhaUsuario());
            System.out.println("Senha correta: " + matches);
            return matches;
        }
        System.out.println("Usuário não encontrado");
        return false;
    }
}
