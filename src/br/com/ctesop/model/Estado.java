package br.com.ctesop.model;

import br.com.ctesop.controller.util.ExceptionValidacao;

public class Estado {

    private int codigo;
    private String nome;
    private String sigla;
    private String status;

    public Estado() {
        this.codigo = 0;
    }

    public Estado(int codigo) {
        this.codigo = codigo;
    }

    public Estado(int codigo, String nome) {
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
        this.nome = nome.trim();
    }

    public String getSigla() {
        return sigla;
    }

    public void setSigla(String sigla) throws ExceptionValidacao {

        if (sigla.isEmpty()) {
            throw new ExceptionValidacao("Sigla obrigatória.");
        }
        if (sigla.trim().length() != 2) {
            throw new ExceptionValidacao("Sigla inválida.");

        }

        this.sigla = sigla.trim().toUpperCase();
    }

    @Override
    public String toString() {
        return getNome();
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Estado) {
            return ((Estado) o).getCodigo() == getCodigo();
        }
        return false;
    }
}
