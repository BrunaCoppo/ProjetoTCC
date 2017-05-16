
package br.com.ctesop.model;

import com.mysql.fabric.xmlrpc.base.Data;

/**
 *
 * @author Bruna
 */
public class Fisica {
    
    private int cpf;
    private int rg;
    private int ei;
    private Data dataNascimento;

    public int getCpf() {
        return cpf;
    }

    public void setCpf(int cpf) {
        this.cpf = cpf;
    }

    public int getRg() {
        return rg;
    }

    public void setRg(int rg) {
        this.rg = rg;
    }

    public int getEi() {
        return ei;
    }

    public void setEi(int ei) {
        this.ei = ei;
    }

    public Data getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(Data dataNascimento) {
        this.dataNascimento = dataNascimento;
    }
    
    
}
