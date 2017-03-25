package br.com.ctesop.model;

import br.com.ctesop.controller.util.ExceptionValidacao;

/**
 *
 * @author Bruna
 */
public class Cidade {

    private String nome;
    private String sigla;
    private String status;
    private int codigo;
    private Estado estado;

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

        if (nome.trim().length() > 200) {
            throw new ExceptionValidacao("Nome muito longo.");

        }
        this.nome = nome;
    }

    public String getSigla() {
        return sigla;
    }

    public void setSigla(String sigla) throws ExceptionValidacao {
        if (sigla.isEmpty()) {
            throw new ExceptionValidacao("Sigla obrigatória");
        }
        if (sigla.trim().length() != 2) {
            throw new ExceptionValidacao("Sigla inválida");

        }
        this.sigla = sigla.toUpperCase();
    }
}
