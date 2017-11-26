package br.com.ctesop.model;

import br.com.ctesop.controller.util.ExceptionValidacao;
import java.security.InvalidParameterException;
import java.util.Date;

/**
 *
 * @author Bruna
 */
public class Fisica {

    private String cpf;
    private String rg;
    private String ie;
    private Date dataNascimento;
    private int codigo;

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        if (codigo < 0) {
            throw new InvalidParameterException("Código inválido.");
        }
        this.codigo = codigo;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) throws ExceptionValidacao {
        if (cpf.trim().length() != 14) {
            throw new ExceptionValidacao("CPF inválido!");
        }
        
        
        
        this.cpf = cpf;
    }

    public String getRg() {
        return rg;
    }

    public void setRg(String rg) {
        this.rg = rg;
    }

    public String getIe() {
        return ie;
    }

    public void setIe(String ei) {
        this.ie = ei;
    }

    public Date getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(Date dataNascimento) throws ExceptionValidacao {
        if (dataNascimento == null) {
            throw new ExceptionValidacao("Data de nascimento inválida!");
        }
        this.dataNascimento = dataNascimento;
    }

}
