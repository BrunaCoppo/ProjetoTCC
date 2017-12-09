package br.com.ctesop.model;

import br.com.ctesop.controller.util.ExceptionValidacao;
import java.security.InvalidParameterException;
import java.util.Date;

/**
 *
 * @author Bruna
 */
public class Pessoa {

    private int codigo;
    private Juridica juridica;
    private Fisica fisica;
    private Cidade cidade;
    private String nome;
    private String endereco;
    private String telefone;
    private Date datacadastro;
    private String email;
    private String status;

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        if (codigo < 0) {
            throw new InvalidParameterException("Código inválido.");
        }
        this.codigo = codigo;
    }

    public Juridica getJuridica() {
        return juridica;
    }

    public void setJuridica(Juridica juridica) throws ExceptionValidacao {
        this.juridica = juridica;
    }

    public Fisica getFisica() {
        return fisica;
    }

    public void setFisica(Fisica fisica) {
        this.fisica = fisica;
    }

    public Cidade getCidade() {
        return cidade;
    }

    public void setCidade(Cidade cidade) throws ExceptionValidacao {
        if (cidade == null) {
            throw new ExceptionValidacao("Cidade inválida!");
        }
        this.cidade = cidade;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) throws ExceptionValidacao {
        if (nome.trim().length() < 3) {
            throw new ExceptionValidacao("Nome inválido!");
        }
        this.nome = nome;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public Date getDatacadastro() {
        return datacadastro;
    }

    public void setDatacadastro(Date datacadastro) {
        this.datacadastro = datacadastro;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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
        if (o instanceof Pessoa) {
            return ((Pessoa) o).getCodigo() == getCodigo();

        }
        return false;
    }

}
