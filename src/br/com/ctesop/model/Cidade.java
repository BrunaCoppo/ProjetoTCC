package br.com.ctesop.model;

import br.com.ctesop.controller.util.ExceptionValidacao;

/**
 *
 * @author Bruna
 */
public class Cidade {

    private String nome;
    private Estado estado;
    private String status;
    private int codigo;

    public Cidade() {
        this.codigo = 0;
    }

    public Cidade(int codigo) {
        this.codigo = codigo;
    }
    
    public Cidade (int codigo, String nome){
       this.codigo = codigo;
        this.nome = nome;
        
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) throws ExceptionValidacao {

        if (nome.isEmpty()) {
            throw new ExceptionValidacao("Nome obrigatório.");
        }
        if (nome.trim().length() < 2) {
            throw new ExceptionValidacao("Nome muito curto.");
        }

        if (nome.trim().length() > 200) {
            throw new ExceptionValidacao("Nome muito longo.");

        }
        this.nome = nome;
    }

    @Override
    public String toString() {
        return getNome();
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Cidade) {
            return ((Cidade) o).getCodigo() == getCodigo();

        }
        return false;
    }
}
