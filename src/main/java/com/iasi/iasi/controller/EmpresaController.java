package com.iasi.iasi.controller;

import com.iasi.iasi.model.Empresa;
import com.iasi.iasi.repository.EmpresaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/empresas")
public class EmpresaController {

    @Autowired
    private EmpresaRepository empresaRepository;

    @GetMapping
    public String listarEmpresas(Model model) {
        model.addAttribute("empresas", empresaRepository.findAll());
        return "empresas/listar";
    }

    @GetMapping("/novo")
    public String mostrarFormularioCriacao(Model model) {
        model.addAttribute("empresa", new Empresa());
        return "empresas/form";
    }

    @PostMapping
    public String salvarEmpresa(@ModelAttribute Empresa empresa) {
        empresaRepository.save(empresa);
        return "redirect:/empresas";
    }

    @GetMapping("/editar/{id}")
    public String mostrarFormularioEdicao(@PathVariable("id") Long id, Model model) {
        Empresa empresa = empresaRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Empresa inválida:" + id));
        model.addAttribute("empresa", empresa);
        return "empresas/form";
    }

    @PostMapping("/{id}")
    public String atualizarEmpresa(@PathVariable("id") Long id, @ModelAttribute Empresa empresa) {
        empresa.setId(id);
        empresaRepository.save(empresa);
        return "redirect:/empresas";
    }

    @GetMapping("/deletar/{id}")
    public String deletarEmpresa(@PathVariable("id") Long id) {
        empresaRepository.deleteById(id);
        return "redirect:/empresas";
    }
}
