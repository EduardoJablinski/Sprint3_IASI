package com.iasi.iasi.repository;

import com.iasi.iasi.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Usuario findByNomeUsuario(String nomeUsuario);
}
