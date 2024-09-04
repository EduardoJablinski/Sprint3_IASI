package com.iasi.iasi.controller;

import com.iasi.iasi.model.Consumo;
import com.iasi.iasi.model.Equipamento;
import com.iasi.iasi.repository.ConsumoRepository;
import com.iasi.iasi.repository.EquipamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/api/consumos")
public class ConsumoController {

    @Autowired
    ConsumoRepository consumoRepository;

    @Autowired
    EquipamentoRepository equipamentoRepository;

    @GetMapping
    public ResponseEntity<List<Consumo>> getAllConsumos() {
        try {
            List<Consumo> consumos = new ArrayList<>();
            consumoRepository.findAll().forEach(consumos::add);
            if (consumos.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(consumos, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Consumo> getConsumoById(@PathVariable("id") long id) {
        Consumo consumo = consumoRepository.findById(id);
        if (consumo != null) {
            return new ResponseEntity<>(consumo, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<String> createConsumo(@RequestBody Consumo consumo) {
        try {
            Equipamento equipamento = equipamentoRepository.findById(consumo.getEquipamento().getIdEquipamento());
            if (equipamento == null) {
                return new ResponseEntity<>("Equipamento com o ID especificado não encontrado.", HttpStatus.NOT_FOUND);
            }
            consumo.setEquipamento(equipamento);
            consumoRepository.save(consumo);
            return new ResponseEntity<>("Consumo foi adicionado com sucesso.", HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateConsumo(@PathVariable("id") long id, @RequestBody Consumo consumo) {
        Consumo _consumo = consumoRepository.findById(id);
        if (_consumo != null) {
            _consumo.setDataConsumo(consumo.getDataConsumo());
            _consumo.setQuantidadeConsumo(consumo.getQuantidadeConsumo());
            _consumo.setTipoEnergiaConsumo(consumo.getTipoEnergiaConsumo());
            _consumo.setEmissaoGasConsumo(consumo.getEmissaoGasConsumo());
            _consumo.setEquipamento(consumo.getEquipamento());
            consumoRepository.save(_consumo);
            return new ResponseEntity<>("Consumo foi atualizado com sucesso.", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Consumo com id=" + id + " não encontrado.", HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteConsumo(@PathVariable("id") long id) {
        try {
            int result = consumoRepository.deleteById(id);
            if (result == 0) {
                return new ResponseEntity<>("Consumo com id=" + id + " não encontrado.", HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>("Consumo foi deletado com sucesso.", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Não é possível deletar o consumo.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/equipamento/{id}")
    public ResponseEntity<List<Consumo>> getConsumosByEquipamentoId(@PathVariable("id") long id) {
        List<Consumo> consumos = consumoRepository.findByEquipamentoId(id);
        if (consumos.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(consumos, HttpStatus.OK);
    }
}
