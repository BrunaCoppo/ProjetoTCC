
package br.com.ctesop.model;

/**
 *
 * @author Bruna
 */
public class Juridica {

  
    private int codigoJuridica;
    private String cnpj;
    private String ei;
    private String razaoSocial;

    public int getCodigoJuridica() {
        return codigoJuridica;
    }

    public void setCodigoJuridica(int codigoJuridica) {
        this.codigoJuridica = codigoJuridica;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public String getEi() {
        return ei;
    }

    public void setEi(String ei) {
        this.ei = ei;
    }
    
    public String getRazaoSocial() {
        return razaoSocial;
    }

    public void setRazaoSocial(String razaoSocial) {
        this.razaoSocial = razaoSocial;
    }
    
}
