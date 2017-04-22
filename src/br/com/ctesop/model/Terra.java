package br.com.ctesop.model;

import br.com.ctesop.controller.util.ExceptionValidacao;

/**
 *
 * @author Bruna
 */
public class Terra {
    
    private int  codigo;
    private  Cidade cidade;
    private float tamanho;
    private String descricao;
    private String status;
    

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }
    
    public Cidade getCidade() {
        return cidade;
    }

    public void setCidade(Cidade cidade) {
        this.cidade = cidade;
    }

    public float getTamanho() {
        return tamanho;
    }

    public void setTamanho(float tamanho) {
        this.tamanho = tamanho;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) throws ExceptionValidacao{
        if (descricao.isEmpty()) {
            throw new ExceptionValidacao("Descrição obrigatória.");
        }
        if (descricao.trim().length() < 2) {
            throw new ExceptionValidacao("Descriçao muito curta.");
        }

        if (descricao.trim().length() > 200) {
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
    
    @Override
    public String toString() {
        return getDescricao();
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Terra) {
            return ((Terra) o).getCodigo() == getCodigo();

        }
        return false;
    }
    
}
