package br.com.ctesop.model;

import br.com.ctesop.controller.util.ExceptionValidacao;
import java.sql.Date;
import java.text.NumberFormat;

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
        this.codigo = codigo;
    }

    public Juridica getJuridica() {
        return juridica;
    }

    public void setJuridica(Juridica juridica) {
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

    public void setCidade(Cidade cidade) {
        this.cidade = cidade;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
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

    public void setEmail(String email) throws ExceptionValidacao {

        if (email.isEmpty()) {
            throw new ExceptionValidacao("E-mail obrigatório.");
        }
        if (email.trim().length() < 2) {
            throw new ExceptionValidacao("E-mail muito curto.");
        }

        if (email.trim().length() > 50) {
            throw new ExceptionValidacao("Nome muito longo.");

        }
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
