package com.example.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import com.example.model.CadPlanta;

@Service
public class CadPlantaService {
 private List<CadPlanta> cadplantas= new ArrayList<>();

// Adiciona uma nova planta (método salvar para o Controller)
public void salvar(CadPlanta cadplanta) {
    cadplantas.add(cadplanta);
}

//retorna todas as plantas
public List<CadPlanta> listarPlantas() {
    return cadplantas;
}

// Adiciona uma nova planta
public void adicionarPlanta(CadPlanta planta) {
    cadplantas.add(planta);
}

// Remove uma planta pelo índice
public void removerPlanta(int index) {
    if(index >= 0 && index < cadplantas.size()) {
        cadplantas.remove(index);
    }
}

//recebe um índice (posição) da planta na lista cadplantas.Ex: 0 para a primeira planta,1 para a segunda, etc.
public Optional<CadPlanta> buscarPlanta(int index) {
    if (index >= 0 && index < cadplantas.size()) {
        return Optional.of(cadplantas.get(index)); // retorna a planta
    } else {
        return Optional.empty(); // não encontrou
    }
}

//busca por especie
public Optional<CadPlanta> buscarPorEspecie(String especie) {
        return cadplantas.stream()
                         .filter(p -> p.getEspecie().equalsIgnoreCase(especie))
                         .findFirst();
    }

// Método para excluir por espécie
public void excluir(String especie) {
       cadplantas.removeIf(p -> p.getEspecie().equalsIgnoreCase(especie));
    }

}


        
