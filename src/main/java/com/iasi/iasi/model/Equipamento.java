package com.iasi.iasi.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "TB_IASI_EQUIPAMENTO")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Equipamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_EQUIPAMENTO")
    private long idEquipamento;

    @Column(name = "NOME_EQUIPAMENTO")
    private String nomeEquipamento;

    @Column(name = "TIPO_EQUIPAMENTO")
    private String tipoEquipamento;

    @Column(name = "LOCALIZACAO_EQUIPAMENTO")
    private String localizacaoEquipamento;

    @Column(name = "DATA_INSTALACAO_EQUIPAMENTO")
    private Date dataInstalacaoEquipamento;

    @Column(name = "ESTADO_EQUIPAMENTO")
    private String estadoEquipamento;

    @ManyToOne
    @JoinColumn(name = "ID_EMPRESA")
    private Empresa empresa;

    public Equipamento(String nomeEquipamento, String tipoEquipamento, String localizacaoEquipamento, Date dataInstalacaoEquipamento, String estadoEquipamento, Empresa empresa) {
        this.nomeEquipamento = nomeEquipamento;
        this.tipoEquipamento = tipoEquipamento;
        this.localizacaoEquipamento = localizacaoEquipamento;
        this.dataInstalacaoEquipamento = dataInstalacaoEquipamento;
        this.estadoEquipamento = estadoEquipamento;
        this.empresa = empresa;
    }

}
