package br.com.ctesop.model;

import br.com.ctesop.controller.util.ExceptionValidacao;

/**
 *
 * @author Bruna
 */
public class Cooperativa {

    private int codigo;
    private Cidade cidade;
    private String nome;
    private int unidade;
    private String status;

    public Cooperativa() {
        this.codigo = 0;
    }

    public Cooperativa(int codigo) {
        this.codigo = codigo;
    }

    public Cooperativa(int codigo, String nome) {
        this.codigo = codigo;
        this.nome = nome;

    }

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

    public int getUnidade() {
        return unidade;
    }

    public void setUnidade(int unidade) throws ExceptionValidacao{
         if (nome.isEmpty()) {
            throw new ExceptionValidacao("Unidade obrigatória.");
        }
        this.unidade = unidade;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return getNome();
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Cooperativa) {
            return ((Cooperativa) o).getCodigo() == getCodigo();

        }
        return false;
    }
}
