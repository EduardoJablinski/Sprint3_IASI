package com.iasi.iasi.repository;

import com.iasi.iasi.model.Consumo;
import com.iasi.iasi.model.Empresa;
import com.iasi.iasi.model.Equipamento;
import java.util.List;

public interface EquipamentoRepository {
    int save(Equipamento book);

    Equipamento findById(long id);

    int deleteById(long id);

    List<Equipamento> findByEmpresaId(long idEmpresa);

    List<Equipamento> findAll();

    int deleteAll();

    List<Equipamento> findByNameContaining(String nomeEquipamento);

}
