package com.iasi.iasi.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "TB_IASI_EMPRESA")
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Empresa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private long id;

    @Column(name = "NOME_EMPRESA")
    private String nome;

    @Column(name = "SETOR_INDUSTRIAL_EMPRESA")
    private String setorIndustrial;

    @Column(name = "LOCALIZACAO_EMPRESA")
    private String localizacao;

    @Column(name = "TIPO_EMPRESA")
    private String tipo;

    @Column(name = "CONFORMIDADE_REGULAR")
    private String conformidadeRegular;

    public Empresa(String nome, String setorIndustrial, String localizacao, String tipo, String conformidadeRegular) {
        this.nome = nome;
        this.setorIndustrial = setorIndustrial;
        this.localizacao = localizacao;
        this.tipo = tipo;
        this.conformidadeRegular = conformidadeRegular;
    }

}
