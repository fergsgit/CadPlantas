package com.example.model;

public class CadPlanta {
    private String especie;
    private String origem;
    private String cuidados;
    private String tipos;
  
    //Construtor vazio (necessário para Spring/Thymeleaf)
    public CadPlanta() {
    }

    //construtor com parâmetros
    public CadPlanta(String especie, String origem, String cuidados, String tipos) {
        this.especie = especie;
        this.origem = origem;
        this.cuidados = cuidados;
        this.tipos = tipos;
    
    }

//getters e setters 
// Getter → método que retorna (lê) o valor de um atributo privado.
//Setter → método que altera (escreve) o valor de um atributo privado.

    public String getEspecie() {
        return especie; //devolve o valor atual
    }

    public void setEspecie(String especie) {
        this.especie = especie; //altera o valor
    }


    public String getOrigem() {
        return origem;
    }


    public void setOrigem(String origem) {
        this.origem = origem;
    }


    public String getCuidados() {
        return cuidados;
    }


    public void setCuidados(String cuidados) {
        this.cuidados = cuidados;
    }


    public String getTipos() {
        return tipos;
    }


    public void setTipos(String tipos) {
        this.tipos = tipos;
    }
}