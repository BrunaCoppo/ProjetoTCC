package br.com.ctesop.model;

import br.com.ctesop.controller.util.ExceptionValidacao;
import java.security.InvalidParameterException;

/**
 *
 * @author Bruna
 */
public class Cooperativa {

    private int codigo;
    private Cidade cidade;
    private String nome;
    private String endereco;
    private String status;


    public Cooperativa(int codigo, String nome) {
        this.codigo = codigo;
        this.nome = nome;

    }

    Cooperativa(String nome) throws ExceptionValidacao {
        setNome(nome);
    }

    public Cooperativa() {
        this.codigo = 0;
    }

    public Cooperativa(int codigo) {
        this.codigo = codigo;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        if (codigo < 0) {
            throw new InvalidParameterException("Código inválido.");
        }
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

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) throws ExceptionValidacao {
        if (nome.isEmpty()) {
            throw new ExceptionValidacao("Endereço obrigatório.");
        }
        if (nome.trim().length() < 2) {
            throw new ExceptionValidacao("Endereço muito curto.");
        }

        if (nome.trim().length() > 50) {
            throw new ExceptionValidacao("Endereço muito longo.");

        }
        this.endereco = endereco;
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
