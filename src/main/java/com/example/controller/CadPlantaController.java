package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*; // * importa todas essas anotações de uma vez, sem precisar escrever varias linhas como GET, POST, REQUEST, ETC
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.example.service.CadPlantaService;
import org.springframework.ui.Model;
import com.example.model.CadPlanta;


@Controller //indica que essa classe é um Controller Spring MVC, responsável por receber requisições HTTP e retornar views (HTML).

public class CadPlantaController {
    
@Autowired //injeta automaticamente o CadPlantaService
 private CadPlantaService cadplantaService;

@GetMapping("/")
    public String index() {
        return "index";
    }
    
@GetMapping("/cadastro")
    public String mostrarFormularioCadastro(Model model) {
        model.addAttribute("cadplanta", new CadPlanta());
        return "cadastro";
    }

@PostMapping("/salvar")
    public String salvarPlanta(@ModelAttribute CadPlanta cadplanta, RedirectAttributes redirectAttributes) {
        try {
            cadplantaService.salvar(cadplanta);
            redirectAttributes.addFlashAttribute("mensagem", "Planta cadastrada com sucesso!");
            return "redirect:/lista";
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("erro", e.getMessage());
            return "redirect:/cadastro";
        }
    }

@GetMapping("/lista")
    public String listarCadPlantas(Model model) {
        model.addAttribute("plantas", cadplantaService.listarPlantas());
        return "lista";
    }

@GetMapping("/consulta")
    public String mostrarFormularioConsulta() {
        return "consulta";
    } 

@PostMapping("/buscar")
    public String buscarCadPlanta(@RequestParam String especie, Model model, RedirectAttributes redirectAttributes) {
        return cadplantaService.buscarPorEspecie(especie)
                .map(cadplanta -> {
                    model.addAttribute("cadplanta", cadplanta);
                    return "consulta";
                })
                .orElseGet(() -> {
                    redirectAttributes.addFlashAttribute("erro", "Planta não encontrada com especie: " + especie);
                    return "redirect:/consulta";
                });
    }

@GetMapping("/excluir/{especie}")
    public String excluirCadPlanta(@PathVariable String especie, RedirectAttributes redirectAttributes) {
        cadplantaService.excluir(especie);
        redirectAttributes.addFlashAttribute("mensagem", "Planta excluída com sucesso!");
        return "redirect:/lista";
    }

}






