package com.iasi.iasi.controller;

import com.iasi.iasi.model.Usuario;
import com.iasi.iasi.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/registrar")
    public String mostrarFormularioRegistro(Model model) {
        model.addAttribute("usuario", new Usuario());
        return "usuarios/registrar";
    }

    @PostMapping("/registrar")
    public String registrarUsuario(@ModelAttribute Usuario usuario) {
        usuarioService.registrarUsuario(usuario);
        return "redirect:/usuarios/login";
    }

    @GetMapping("/login")
    public String mostrarFormularioLogin() {
        return "usuarios/login";
    }

    @PostMapping("/login")
    public String login(@RequestParam String nomeUsuario, @RequestParam String senha, Model model) {
        boolean loginSucesso = usuarioService.verificarCredenciais(nomeUsuario, senha);
        System.out.println("Login sucesso: " + loginSucesso);
        if (loginSucesso) {
            return "redirect:/empresas";
        } else {
            model.addAttribute("erro", "Credenciais inválidas.");
            return "usuarios/login";
        }
    }
}
