
package br.com.ctesop.model;

/**
 *
 * @author Bruna
 */
public class Juridica {

  
    
    private int cnpj;
    private int ei;
    private String razaoSocial;
    
    public int getCnpj() {
        return cnpj;
    }

    public void setCnpj(int cnpj) {
        this.cnpj = cnpj;
    }

    public int getEi() {
        return ei;
    }

    public void setEi(int ei) {
        this.ei = ei;
    }

    public String getRazaoSocial() {
        return razaoSocial;
    }

    public void setRazaoSocial(String razaoSocial) {
        this.razaoSocial = razaoSocial;
    }
    
}
