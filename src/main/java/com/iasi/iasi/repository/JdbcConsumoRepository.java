package com.iasi.iasi.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.iasi.iasi.model.Empresa;
import com.iasi.iasi.model.Equipamento;
import com.iasi.iasi.model.Consumo;


@Repository
public class JdbcConsumoRepository implements ConsumoRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private EquipamentoRepository equipamentoRepository;


    @Override
    public int save(Consumo consumo) {
        return jdbcTemplate.update("INSERT INTO TB_IASI_CONSUMO (ID_EQUIPAMENTO, DATA_CONSUMO, QUANTIDADE_CONSUMO, TIPO_ENERGIA_CONSUMO, EMISSAO_GAS_CONSUMO) VALUES(?,?,?,?,?)",
                new Object[] { consumo.getEquipamento().getIdEquipamento(), consumo.getDataConsumo(), consumo.getQuantidadeConsumo(), consumo.getTipoEnergiaConsumo(), consumo.getEmissaoGasConsumo() });
    }


    @Override
    public Consumo findById(long id) {
        try {
            return jdbcTemplate.queryForObject("SELECT * FROM TB_IASI_CONSUMO WHERE ID_CONSUMO=?",
                    new Object[] { id }, (rs, rowNum) -> {
                        Consumo consumo = new Consumo();
                        consumo.setIdConsumo(rs.getLong("ID_CONSUMO"));
                        consumo.setDataConsumo(rs.getDate("DATA_CONSUMO"));
                        consumo.setQuantidadeConsumo(rs.getFloat("QUANTIDADE_CONSUMO"));
                        consumo.setTipoEnergiaConsumo(rs.getString("TIPO_ENERGIA_CONSUMO"));
                        consumo.setEmissaoGasConsumo(rs.getFloat("EMISSAO_GAS_CONSUMO"));
                        consumo.setEquipamento(equipamentoRepository.findById(rs.getLong("ID_EQUIPAMENTO")));
                        return consumo;
                    });
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }


    @Override
    public int deleteById(long id) {
        return jdbcTemplate.update("DELETE FROM TB_IASI_CONSUMO WHERE ID_CONSUMO=?", id);
    }

    @Override
    public List<Consumo> findByEquipamentoId(long idEquipamento) {
        // Convertendo o idEmpresa de long para String
        String equipamentoId = String.valueOf(idEquipamento);

        return jdbcTemplate.query("SELECT * FROM TB_IASI_CONSUMO WHERE ID_EQUIPAMENTO=?", new Object[]{equipamentoId}, (rs, rowNum) -> {
            Consumo consumo = new Consumo();
            consumo.setIdConsumo(rs.getLong("ID_CONSUMO"));
            consumo.setDataConsumo(rs.getDate("DATA_CONSUMO"));
            consumo.setQuantidadeConsumo(rs.getFloat("QUANTIDADE_CONSUMO"));
            consumo.setTipoEnergiaConsumo(rs.getString("TIPO_ENERGIA_CONSUMO"));
            consumo.setEmissaoGasConsumo(rs.getFloat("EMISSAO_GAS_CONSUMO"));
            // Carregar a empresa associada ao equipamento
            // Como o ID da empresa é um long, você precisa usar o método findById que aceita um long como parâmetro
            Equipamento equipamento = equipamentoRepository.findById(idEquipamento);
            consumo.setEquipamento(equipamento);
            return consumo;
        });
    }

    @Override
    public List<Consumo> findAll() {
        return jdbcTemplate.query("SELECT * FROM TB_IASI_CONSUMO", (rs, rowNum) -> {
            Consumo consumo = new Consumo();
            consumo.setIdConsumo(rs.getLong("ID_CONSUMO"));
            consumo.setDataConsumo(rs.getDate("DATA_CONSUMO"));
            consumo.setQuantidadeConsumo(rs.getFloat("QUANTIDADE_CONSUMO"));
            consumo.setTipoEnergiaConsumo(rs.getString("TIPO_ENERGIA_CONSUMO"));
            consumo.setEmissaoGasConsumo(rs.getFloat("EMISSAO_GAS_CONSUMO"));
            // Carregar a empresa associada ao equipamento
            long equipamentoId = rs.getLong("ID_EQUIPAMENTO");
            Equipamento equipamento = equipamentoRepository.findById(equipamentoId);
            consumo.setEquipamento(equipamento);
            return consumo;
        });
    }


    @Override
    public int deleteAll() {
        return jdbcTemplate.update("DELETE FROM TB_IASI_CONSUMO");
    }

}
