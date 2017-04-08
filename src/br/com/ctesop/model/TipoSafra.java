package br.com.ctesop.model;

import br.com.ctesop.controller.util.ExceptionValidacao;

/**
 *
 * @author Bruna
 */
public class TipoSafra {

    private int codigo;
    private String nome;
    private float valor;
    private String descricao;
    private String status;

    public TipoSafra() {
        this.codigo = 0;
    }

    public TipoSafra(int codigo) {
        this.codigo = codigo;
    }

    public TipoSafra(int codigo, String nome) {
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

    public void setNome(String nome) throws ExceptionValidacao{
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

    public float getValor() {
        return valor;
    }

    public void setValor(float valor) throws ExceptionValidacao{
         if (nome.isEmpty()) {
            throw new ExceptionValidacao("Nome obrigatório.");
        }
        this.valor = valor;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}