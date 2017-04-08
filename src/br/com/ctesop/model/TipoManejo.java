package br.com.ctesop.model;

import br.com.ctesop.controller.util.ExceptionValidacao;

/**
 *
 * @author Bruna
 */
public class TipoManejo {

    private int codigo;
    private String nome;
    private String status;

    public TipoManejo() {
        this.codigo = 0;
    }

    public TipoManejo(int codigo) {
        this.codigo = codigo;
    }

    public TipoManejo(int codigo, String nome) {
        this.codigo = codigo;
        this.nome = nome;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
