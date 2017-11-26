package br.com.ctesop.model;

import br.com.ctesop.controller.util.Converter;
import br.com.ctesop.controller.util.ExceptionValidacao;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Date;

/**
 *
 * @author Bruna
 */
public class Pagamento {

    private int codigo;
    private ParcelaPagar pacelaPagar;
    private String descricao;
    private Date data;
    private float valor;
    private String status;

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public ParcelaPagar getPacelaPagar() {
        return pacelaPagar;
    }

    public void setPacelaPagar(ParcelaPagar pacelaPagar) {
        this.pacelaPagar = pacelaPagar;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
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

    public float getValor() {
        return valor;
    }

    public void setValor(float valor) {
        this.valor = valor;
    }

    public void setValor(String valor) throws ExceptionValidacao {
        NumberFormat nf = NumberFormat.getNumberInstance();
        try {
            this.valor = nf.parse(valor).floatValue();
        } catch (ParseException ex) {
            throw new ExceptionValidacao("Valor inválido.");
        }
    }

    public String getValorFormatado() {
        NumberFormat nfReal = NumberFormat.getNumberInstance();
        nfReal.setMinimumFractionDigits(2);
        nfReal.setMaximumFractionDigits(2);
        return nfReal.format(this.valor);
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
