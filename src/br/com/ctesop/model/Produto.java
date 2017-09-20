package br.com.ctesop.model;

import br.com.ctesop.controller.util.ExceptionValidacao;
import java.security.InvalidParameterException;

/**
 *
 * @author Bruna
 */
public class Produto {

    private int codigo;
    private String nome;
    private String descricao;
    private String status;
    private String produzido;
    private float quantidade = 0;

    public Produto() {
        this.codigo = 0;
    }

    public Produto(int codigo) {
        if (codigo < 0) {
            throw new InvalidParameterException("Código inválido.");
        }
        this.codigo = codigo;
    }

    public Produto(int codigo, String nome) {
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

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) throws ExceptionValidacao {
        if (nome.isEmpty()) {
            throw new ExceptionValidacao("Descrição obrigatória.");
        }
        if (nome.trim().length() < 2) {
            throw new ExceptionValidacao("Descrição muito curta.");
        }

        if (nome.trim().length() > 200) {
            throw new ExceptionValidacao("Descrição muito longa.");

        }
        this.descricao = descricao;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getProduzido() {
        return produzido;
    }

    public void setProduzido(String produzido) {
        this.produzido = produzido;
    }

    public float getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(float quantidade) {
        this.quantidade = quantidade;
    }
        
    @Override
    public String toString() {
        return getNome();
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Produto) {
            return ((Produto) o).getCodigo() == getCodigo();
        }
        return false;
    }

}
