package br.com.ctesop.model;

import br.com.ctesop.controller.util.Converter;
import java.util.Date;

/**
 *
 * @author Bruna
 */
public class ContaReceber {

    private Cooperativa cooperativa;
    private float valor;
    private Date data;
    private String decricao;
    private String status;

    public Cooperativa getCooperativa() {
        return cooperativa;
    }

    public void setCooperativa(Cooperativa cooperativa) {
        this.cooperativa = cooperativa;
    }

    public float getValor() {
        return valor;
    }

    public void setValor(float valor) {
        this.valor = valor;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public String getDataFormatada() {
        return Converter.formatarData(data);
    }

    public String getDecricao() {
        return decricao;
    }

    public void setDecricao(String decricao) {
        this.decricao = decricao;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
