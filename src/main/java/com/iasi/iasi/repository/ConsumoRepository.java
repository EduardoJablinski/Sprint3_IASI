package com.iasi.iasi.repository;

import com.iasi.iasi.model.Empresa;
import com.iasi.iasi.model.Equipamento;
import com.iasi.iasi.model.Consumo;
import java.util.List;

public interface ConsumoRepository {

    int save(Consumo book);

    Consumo findById(long id);

    int deleteById(long id);

    List<Consumo> findByEquipamentoId(long idEquipamento);

    List<Consumo> findAll();

    int deleteAll();

}
