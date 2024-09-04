package com.iasi.iasi.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional; // Certifique-se de importar isso

import com.iasi.iasi.model.Consumo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.iasi.iasi.model.Empresa;
import com.iasi.iasi.model.Equipamento;

@Repository
public class JdbcEquipamentoRepository implements EquipamentoRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private EmpresaRepository empresaRepository;

    @Override
    public int save(Equipamento equipamento) {
        System.out.println("Equipamento a ser salvo: ");
        System.out.println("Nome: " + equipamento.getNomeEquipamento());
        System.out.println("Tipo: " + equipamento.getTipoEquipamento());
        System.out.println("Localização: " + equipamento.getLocalizacaoEquipamento());
        System.out.println("Data de Instalação: " + equipamento.getDataInstalacaoEquipamento());
        System.out.println("Estado: " + equipamento.getEstadoEquipamento());
        System.out.println("ID da Empresa: " + equipamento.getEmpresa().getId());

        return jdbcTemplate.update("INSERT INTO TB_IASI_EQUIPAMENTO (NOME_EQUIPAMENTO, TIPO_EQUIPAMENTO, LOCALIZACAO_EQUIPAMENTO, DATA_INSTALACAO_EQUIPAMENTO, ESTADO_EQUIPAMENTO, ID_EMPRESA) VALUES(?,?,?,?,?,?)",
                new Object[] { equipamento.getNomeEquipamento(), equipamento.getTipoEquipamento(), equipamento.getLocalizacaoEquipamento(), equipamento.getDataInstalacaoEquipamento(), equipamento.getEstadoEquipamento(), equipamento.getEmpresa().getId() });
    }

    @Override
    public Equipamento findById(long id) {
        try {
            return jdbcTemplate.queryForObject("SELECT * FROM TB_IASI_EQUIPAMENTO WHERE ID_EQUIPAMENTO=?",
                    new Object[] { id }, (rs, rowNum) -> {
                        Equipamento equipamento = new Equipamento();
                        equipamento.setIdEquipamento(rs.getLong("ID_EQUIPAMENTO"));
                        equipamento.setNomeEquipamento(rs.getString("NOME_EQUIPAMENTO"));
                        equipamento.setTipoEquipamento(rs.getString("TIPO_EQUIPAMENTO"));
                        equipamento.setLocalizacaoEquipamento(rs.getString("LOCALIZACAO_EQUIPAMENTO"));
                        equipamento.setDataInstalacaoEquipamento(rs.getDate("DATA_INSTALACAO_EQUIPAMENTO"));
                        equipamento.setEstadoEquipamento(rs.getString("ESTADO_EQUIPAMENTO"));

                        // Carregar a empresa associada ao equipamento
                        Optional<Empresa> empresaOptional = empresaRepository.findById(rs.getLong("ID_EMPRESA"));
                        empresaOptional.ifPresent(equipamento::setEmpresa);

                        return equipamento;
                    });
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public int deleteById(long id) {
        return jdbcTemplate.update("DELETE FROM TB_IASI_EQUIPAMENTO WHERE ID_EQUIPAMENTO=?", id);
    }

    @Override
    public List<Equipamento> findByEmpresaId(long idEmpresa) {
        // Convertendo o idEmpresa de long para String
        String empresaId = String.valueOf(idEmpresa);

        return jdbcTemplate.query("SELECT * FROM TB_IASI_EQUIPAMENTO WHERE ID_EMPRESA=?", new Object[]{empresaId}, (rs, rowNum) -> {
            Equipamento equipamento = new Equipamento();
            equipamento.setIdEquipamento(rs.getLong("ID_EQUIPAMENTO"));
            equipamento.setNomeEquipamento(rs.getString("NOME_EQUIPAMENTO"));
            equipamento.setTipoEquipamento(rs.getString("TIPO_EQUIPAMENTO"));
            equipamento.setLocalizacaoEquipamento(rs.getString("LOCALIZACAO_EQUIPAMENTO"));
            equipamento.setDataInstalacaoEquipamento(rs.getDate("DATA_INSTALACAO_EQUIPAMENTO"));
            equipamento.setEstadoEquipamento(rs.getString("ESTADO_EQUIPAMENTO"));

            // Carregar a empresa associada ao equipamento
            Optional<Empresa> empresaOptional = empresaRepository.findById(idEmpresa);
            empresaOptional.ifPresent(equipamento::setEmpresa);

            return equipamento;
        });
    }

    @Override
    public List<Equipamento> findAll() {
        return jdbcTemplate.query("SELECT * FROM TB_IASI_EQUIPAMENTO", (rs, rowNum) -> {
            Equipamento equipamento = new Equipamento();
            equipamento.setIdEquipamento(rs.getLong("ID_EQUIPAMENTO"));
            equipamento.setNomeEquipamento(rs.getString("NOME_EQUIPAMENTO"));
            equipamento.setTipoEquipamento(rs.getString("TIPO_EQUIPAMENTO"));
            equipamento.setLocalizacaoEquipamento(rs.getString("LOCALIZACAO_EQUIPAMENTO"));
            equipamento.setDataInstalacaoEquipamento(rs.getDate("DATA_INSTALACAO_EQUIPAMENTO"));
            equipamento.setEstadoEquipamento(rs.getString("ESTADO_EQUIPAMENTO"));

            // Carregar a empresa associada ao equipamento
            Optional<Empresa> empresaOptional = empresaRepository.findById(rs.getLong("ID_EMPRESA"));
            empresaOptional.ifPresent(equipamento::setEmpresa);

            return equipamento;
        });
    }

    @Override
    public int deleteAll() {
        return jdbcTemplate.update("DELETE FROM TB_IASI_EQUIPAMENTO");
    }

    @Override
    public List<Equipamento> findByNameContaining(String nomeEquipamento) {
        String q = "SELECT * from TB_IASI_EQUIPAMENTO WHERE NOME_EQUIPAMENTO LIKE '%" + nomeEquipamento + "%' collate binary_ci";

        return jdbcTemplate.query(q, BeanPropertyRowMapper.newInstance(Equipamento.class));
    }
}
