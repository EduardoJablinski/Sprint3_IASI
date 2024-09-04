package com.iasi.iasi.controller;

import com.iasi.iasi.model.Empresa;
import com.iasi.iasi.model.Equipamento;
import com.iasi.iasi.repository.EmpresaRepository;
import com.iasi.iasi.repository.EquipamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/api/equipamentos")
public class EquipamentoController {

    @Autowired
    EquipamentoRepository equipamentoRepository;

    @Autowired
    EmpresaRepository empresaRepository;

    @GetMapping
    public ResponseEntity<List<Equipamento>> getAllEquipamentos(@RequestParam(required = false) String nomeEquipamento) {
        try {
            List<Equipamento> equipamentos = new ArrayList<>();
            if (nomeEquipamento == null) {
                equipamentoRepository.findAll().forEach(equipamentos::add);
            } else {
                equipamentoRepository.findByNameContaining(nomeEquipamento).forEach(equipamentos::add);
            }
            if (equipamentos.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(equipamentos, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Equipamento> getEquipamentoById(@PathVariable("id") long id) {
        Equipamento equipamento = equipamentoRepository.findById(id);
        if (equipamento != null) {
            return new ResponseEntity<>(equipamento, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<String> createEquipamento(@RequestBody Equipamento equipamento) {
        try {
            Optional<Empresa> empresaOptional = empresaRepository.findById(equipamento.getEmpresa().getId());
            if (!empresaOptional.isPresent()) {
                return new ResponseEntity<>("Empresa com o ID especificado não encontrada.", HttpStatus.NOT_FOUND);
            }
            equipamento.setEmpresa(empresaOptional.get());
            equipamentoRepository.save(equipamento);
            return new ResponseEntity<>("Equipamento foi adicionado com sucesso.", HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateEquipamento(@PathVariable("id") long id, @RequestBody Equipamento equipamento) {
        Optional<Equipamento> equipamentoOptional = Optional.ofNullable(equipamentoRepository.findById(id));
        if (equipamentoOptional.isPresent()) {
            Equipamento _equipamento = equipamentoOptional.get();
            _equipamento.setNomeEquipamento(equipamento.getNomeEquipamento());
            _equipamento.setTipoEquipamento(equipamento.getTipoEquipamento());
            _equipamento.setLocalizacaoEquipamento(equipamento.getLocalizacaoEquipamento());
            _equipamento.setDataInstalacaoEquipamento(equipamento.getDataInstalacaoEquipamento());
            _equipamento.setEstadoEquipamento(equipamento.getEstadoEquipamento());

            // Atualizando a empresa
            Optional<Empresa> empresaOptional = empresaRepository.findById(equipamento.getEmpresa().getId());
            if (empresaOptional.isPresent()) {
                _equipamento.setEmpresa(empresaOptional.get());
            } else {
                return new ResponseEntity<>("Empresa com o ID especificado não encontrada.", HttpStatus.NOT_FOUND);
            }

            equipamentoRepository.save(_equipamento);
            return new ResponseEntity<>("Equipamento foi atualizado com sucesso.", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Equipamento com id=" + id + " não encontrado.", HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteEquipamento(@PathVariable("id") long id) {
        try {
            int result = equipamentoRepository.deleteById(id);
            if (result == 0) {
                return new ResponseEntity<>("Equipamento com id=" + id + " não encontrado.", HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>("Equipamento foi deletado com sucesso.", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Não é possível deletar o equipamento.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/empresa/{id}")
    public ResponseEntity<List<Equipamento>> getEquipamentosByEmpresaId(@PathVariable("id") long id) {
        List<Equipamento> equipamentos = equipamentoRepository.findByEmpresaId(id);
        if (equipamentos.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(equipamentos, HttpStatus.OK);
    }
}
