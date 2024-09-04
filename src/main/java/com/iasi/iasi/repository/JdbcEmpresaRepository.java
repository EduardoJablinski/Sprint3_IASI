package com.iasi.iasi.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import com.iasi.iasi.model.Empresa;

@Repository
public class JdbcEmpresaRepository implements EmpresaRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public int save(Empresa empresa) {
        return jdbcTemplate.update("INSERT INTO TB_IASI_EMPRESA (NOME_EMPRESA, SETOR_INDUSTRIAL_EMPRESA, LOCALIZACAO_EMPRESA, TIPO_EMPRESA, CONFORMIDADE_REGULAR) VALUES(?,?,?,?,?)",
                empresa.getNome(), empresa.getSetorIndustrial(), empresa.getLocalizacao(), empresa.getTipo(), empresa.getConformidadeRegular());
    }

    @Override
    public int update(Empresa empresa) {
        return jdbcTemplate.update("UPDATE TB_IASI_EMPRESA SET NOME_EMPRESA=?, SETOR_INDUSTRIAL_EMPRESA=?, LOCALIZACAO_EMPRESA=?, TIPO_EMPRESA=?, CONFORMIDADE_REGULAR=? WHERE ID=?",
                empresa.getNome(), empresa.getSetorIndustrial(), empresa.getLocalizacao(), empresa.getTipo(), empresa.getConformidadeRegular(), empresa.getId());
    }

    @Override
    public Optional<Empresa> findById(Long id) {
        try {
            Empresa empresa = jdbcTemplate.queryForObject(
                    "SELECT * FROM TB_IASI_EMPRESA WHERE ID=?",
                    new Object[]{id},
                    (rs, rowNum) -> {
                        Empresa emp = new Empresa();
                        emp.setId(rs.getLong("ID"));
                        emp.setNome(rs.getString("NOME_EMPRESA"));
                        emp.setSetorIndustrial(rs.getString("SETOR_INDUSTRIAL_EMPRESA"));
                        emp.setLocalizacao(rs.getString("LOCALIZACAO_EMPRESA"));
                        emp.setTipo(rs.getString("TIPO_EMPRESA"));
                        emp.setConformidadeRegular(rs.getString("CONFORMIDADE_REGULAR"));
                        return emp;
                    }
            );
            return Optional.ofNullable(empresa);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public int deleteById(Long id) {
        return jdbcTemplate.update("DELETE FROM TB_IASI_EMPRESA WHERE ID=?", id);
    }

    @Override
    public List<Empresa> findAll() {
        return jdbcTemplate.query("SELECT * FROM TB_IASI_EMPRESA", (rs, rowNum) -> {
            Empresa empresa = new Empresa();
            empresa.setId(rs.getLong("ID"));
            empresa.setNome(rs.getString("NOME_EMPRESA"));
            empresa.setSetorIndustrial(rs.getString("SETOR_INDUSTRIAL_EMPRESA"));
            empresa.setLocalizacao(rs.getString("LOCALIZACAO_EMPRESA"));
            empresa.setTipo(rs.getString("TIPO_EMPRESA"));
            empresa.setConformidadeRegular(rs.getString("CONFORMIDADE_REGULAR"));
            return empresa;
        });
    }

    @Override
    public List<Empresa> findByNameContaining(String nome) {
        String q = "SELECT * from TB_IASI_EMPRESA WHERE NOME_EMPRESA LIKE '%" + nome + "%' collate binary_ci";
        return jdbcTemplate.query(q, BeanPropertyRowMapper.newInstance(Empresa.class));
    }

    @Override
    public int deleteAll() {
        return jdbcTemplate.update("DELETE from TB_IASI_EMPRESA");
    }
}
