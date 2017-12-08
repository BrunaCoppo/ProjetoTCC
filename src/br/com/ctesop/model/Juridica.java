
package br.com.ctesop.model;

import br.com.ctesop.controller.util.ExceptionValidacao;
import java.security.InvalidParameterException;

/**
 *
 * @author Bruna
 */
public class Juridica {

  
    private int codigo;
    private String cnpj;
    private String ie;
    private String razaoSocial;

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        if (codigo < 0) {
            throw new InvalidParameterException("Código inválido.");
        }
        this.codigo = codigo;
    }

    public String getCnpj() {
        return cnpj;
    }

    public static boolean validarCnpj (String cnpj) {
        if (!cnpj.substring(0, 1).equals("")) {
            try {
                cnpj = cnpj.replace('.', ' ');//onde há ponto coloca espaço
                cnpj = cnpj.replace('/', ' ');//onde há barra coloca espaço
                cnpj = cnpj.replace('-', ' ');//onde há traço coloca espaço
                cnpj = cnpj.replaceAll(" ", "");//retira espaço
                int soma = 0, dig;
                String cnpj_calc = cnpj.substring(0, 12);
                if (cnpj.length() != 14) {
                    return false;
                }
                char[] chr_cnpj = cnpj.toCharArray();
                /* Primeira parte */
                for (int i = 0; i < 4; i++) {
                    if (chr_cnpj[i] - 48 >= 0 && chr_cnpj[i] - 48 <= 9) {
                        soma += (chr_cnpj[i] - 48) * (6 - (i + 1));
                    }
                }
                for (int i = 0; i < 8; i++) {
                    if (chr_cnpj[i + 4] - 48 >= 0 && chr_cnpj[i + 4] - 48 <= 9) {
                        soma += (chr_cnpj[i + 4] - 48) * (10 - (i + 1));
                    }
                }
                dig = 11 - (soma % 11);
                cnpj_calc += (dig == 10 || dig == 11) ? "0" : Integer.toString(
                        dig);
                /* Segunda parte */
                soma = 0;
                for (int i = 0; i < 5; i++) {
                    if (chr_cnpj[i] - 48 >= 0 && chr_cnpj[i] - 48 <= 9) {
                        soma += (chr_cnpj[i] - 48) * (7 - (i + 1));
                    }
                }
                for (int i = 0; i < 8; i++) {
                    if (chr_cnpj[i + 5] - 48 >= 0 && chr_cnpj[i + 5] - 48 <= 9) {
                        soma += (chr_cnpj[i + 5] - 48) * (10 - (i + 1));
                    }
                }
                dig = 11 - (soma % 11);
                cnpj_calc += (dig == 10 || dig == 11) ? "0" : Integer.toString(
                        dig);
                return cnpj.equals(cnpj_calc);
            }
            catch (Exception e) {
                return false;
            }
        }
        else {
            return false;
        }
    }
    
    public void setCnpj(String cnpj) throws ExceptionValidacao {
        if(cnpj.trim().length() != 18){
            throw new ExceptionValidacao("CNPJ inválido!");
        }if (!validarCnpj(cnpj)) {
            throw new ExceptionValidacao("CNPJ inválido!");
        }
        this.cnpj = cnpj;
    }

    public String getIe() {
        return ie;
    }

    public void setIe(String ei) {
        this.ie = ei;
    }
    
    public String getRazaoSocial() {
        return razaoSocial;
    }

    public void setRazaoSocial(String razaoSocial) throws ExceptionValidacao {
        if(razaoSocial.trim().length() < 3){
            throw new ExceptionValidacao("Razão Social inválida!");
        }
        this.razaoSocial = razaoSocial;
    }
    
}
