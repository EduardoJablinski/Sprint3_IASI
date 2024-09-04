package com.iasi.iasi.repository;

import java.util.List;
import java.util.Optional; // Certifique-se de importar isso

import com.iasi.iasi.model.Empresa;

public interface EmpresaRepository {
    int save(Empresa empresa);
    int update(Empresa empresa);
    Optional<Empresa> findById(Long id); // Mude o retorno para Optional<Empresa>
    int deleteById(Long id);
    List<Empresa> findAll();
    List<Empresa> findByNameContaining(String nome);
    int deleteAll();
}
