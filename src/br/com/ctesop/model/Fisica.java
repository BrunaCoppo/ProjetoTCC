
package br.com.ctesop.model;

import java.sql.Date;

/**
 *
 * @author Bruna
 */
public class Fisica {
    
    private int codigoFisica;
    private String cpf;
    private String rg;
    private String ei;
    private Date dataNascimento;

    public int getCodigoFisica() {
        return codigoFisica;
    }

    public void setCodigoFisica(int codigoFisica) {
        this.codigoFisica = codigoFisica;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getRg() {
        return rg;
    }

    public void setRg(String rg) {
        this.rg = rg;
    }

    public String getEi() {
        return ei;
    }

    public void setEi(String ei) {
        this.ei = ei;
    }

    public Date getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(Date dataNascimento) {
        this.dataNascimento = dataNascimento;
    }
    
      
    
    
}
