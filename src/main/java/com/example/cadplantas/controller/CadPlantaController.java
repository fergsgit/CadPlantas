package com.example.cadplantas.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*; // importa de uma vez todas as anotações como @GetMapping, @PostMapping, etc.
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.example.cadplantas.service.CadPlantaService;
import org.springframework.ui.Model;
import com.example.cadplantas.model.CadPlanta;

@Controller 
// Indica que esta classe é um Controller Spring MVC, ou seja, ela recebe requisições HTTP e retorna as views (templates HTML).
public class CadPlantaController {
    
    @Autowired 
    // Injeta automaticamente a dependência do CadPlantaService (injeção de dependência do Spring).
    private CadPlantaService cadplantaService;

    // ========================= PÁGINA INICIAL =========================
    @GetMapping("/")
    public String index() {
        // Retorna o template "index.html" (página inicial do sistema).
        return "index";
    }
    
    // ========================= FORMULÁRIO DE CADASTRO =========================
    @GetMapping("/cadastro")
    public String mostrarFormularioCadastro(Model model) {
        // Cria um objeto vazio de CadPlanta e envia para o formulário.
        // Isso permite que o <form> em cadastro.html seja ligado automaticamente ao objeto.
        model.addAttribute("cadplanta", new CadPlanta());
        return "cadastro"; // retorna o template "cadastro.html"
    }

    // ========================= SALVAR PLANTA =========================
    @PostMapping("/salvar")
    public String salvarPlanta(@ModelAttribute CadPlanta cadplanta, RedirectAttributes redirectAttributes) {
        try {
            // Chama o service para salvar a planta na lista.
            cadplantaService.salvar(cadplanta);

            // Adiciona uma mensagem de sucesso que será exibida na página seguinte.
            redirectAttributes.addFlashAttribute("mensagem", "Planta cadastrada com sucesso!");
            
            // Redireciona para a listagem.
            return "redirect:/lista";
        } catch (RuntimeException e) {
            // Caso dê erro, adiciona uma mensagem de erro e volta para o cadastro.
            redirectAttributes.addFlashAttribute("erro", e.getMessage());
            return "redirect:/cadastro";
        }
    }

    // ========================= LISTAR PLANTAS =========================
    @GetMapping("/lista")
    public String listarCadPlantas(Model model) {
        // Busca todas as plantas cadastradas no service.
        model.addAttribute("plantas", cadplantaService.listarPlantas());
        
        // Retorna o template "lista.html".
        return "lista";
    }

    // ========================= FORMULÁRIO DE CONSULTA =========================
    @GetMapping("/consulta")
    public String mostrarFormularioConsulta() {
        // Apenas abre a página consulta.html (sem resultados ainda).
        return "consulta";
    } 

    // ========================= BUSCAR PLANTA POR ESPÉCIE =========================
    @PostMapping("/buscar")
    public String buscarCadPlanta(@RequestParam String especie, Model model, RedirectAttributes redirectAttributes) {
        // Chama o service para procurar pela espécie informada.
        return cadplantaService.buscarPorEspecie(especie)
                .map(cadplanta -> {
                    // Caso encontre, adiciona a planta encontrada ao modelo
                    // e retorna o mesmo template consulta.html já com os dados.
                    model.addAttribute("cadplanta", cadplanta);
                    return "consulta";
                })
                .orElseGet(() -> {
                    // Caso não encontre, envia mensagem de erro e redireciona de volta para consulta.
                    redirectAttributes.addFlashAttribute("erro", "Planta não encontrada com especie: " + especie);
                    return "redirect:/consulta";
                });
    }

    // ========================= EXCLUIR PLANTA =========================
    @GetMapping("/excluir/{especie}")
    public String excluirCadPlanta(@PathVariable String especie, RedirectAttributes redirectAttributes) {
        // Chama o service para excluir a planta pela espécie.
        cadplantaService.excluir(especie);

        // Adiciona mensagem de sucesso.
        redirectAttributes.addFlashAttribute("mensagem", "Planta excluída com sucesso!");
        
        // Redireciona para a lista.
        return "redirect:/lista";
    }
}
