package br.com.ctesop.model;

import br.com.ctesop.controller.util.ExceptionValidacao;
import java.util.Date;

/**
 *
 * @author Bruna
 */
public class Arrendamento {

    private int codigo;
    private Terra terra;
    private Date data;
    private float valor;
    private String descricao;
    private String status;

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public Terra getTerra() {
        return terra;
    }

    public void setTerra(Terra terra) throws ExceptionValidacao {
        if (terra == null) {
            throw new ExceptionValidacao("Terra inválida!");
        }
        this.terra = terra;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) throws ExceptionValidacao {
         if (data == null) {
            throw new ExceptionValidacao("Data inválida!");
        }
        this.data = data;
    }

    public float getValor() {
        return valor;
    }

    public void setValor(float valor) {
        this.valor = valor;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
