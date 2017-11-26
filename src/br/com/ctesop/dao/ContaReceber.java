
package br.com.ctesop.dao;

import br.com.ctesop.controller.util.Converter;
import br.com.ctesop.model.Cooperativa;
import java.util.Date;

/**
 *
 * @author Bruna
 */
public class ContaReceber {
    
    private int codigo;
    private Cooperativa cooperativa;
    private float valor;
    private Date data;
    private String descricao;
    private String status;

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

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
