package br.com.ctesop.model;

import br.com.ctesop.controller.util.Converter;
import br.com.ctesop.controller.util.ExceptionValidacao;
import java.security.InvalidParameterException;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Date;

/**
 *
 * @author Bruna
 */
public class ParcelaPagar {

    private int codigo;
    private ContaPagar contaPagar;
    private Date data;
    private float valorParcela;
    private float valorPago;
    private String status;

    public ParcelaPagar(int aInt, float aFloat) {
        this.contaPagar = contaPagar;
    }

    public ParcelaPagar() {

    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
         if (codigo < 0) {
            throw new InvalidParameterException("Código inválido.");
        }
        this.codigo = codigo;
    }

    public ContaPagar getContaPagar() {
        return contaPagar;
    }

    public void setContaPagar(ContaPagar contaPagar) {
        this.contaPagar = contaPagar;
    }

    public String getDataFormatada() {
        return Converter.formatarData(data);
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public float getValorParcela() {
        return valorParcela;
    }

    public String getValorParcelaFormatado() {
        NumberFormat nf = NumberFormat.getNumberInstance();
        nf.setMinimumFractionDigits(2);
        nf.setMaximumFractionDigits(2);
        return nf.format(valorParcela);
    }

    public void setValor(String valor) throws ExceptionValidacao {
        NumberFormat nf = NumberFormat.getNumberInstance();
        try {
            this.valorParcela = nf.parse(valor).floatValue();
        } catch (ParseException ex) {
            throw new ExceptionValidacao("Valor inválido.");
        }
    }

    public void setValorParcela(float valorParcela) {
        this.valorParcela = valorParcela;
    }

    public float getValorPago() {
        return valorPago;
    }

    public void setValorPago(float valorPago) {
        this.valorPago = valorPago;
    }

    public float getValorRestante() {
        return this.valorParcela - this.valorPago;
    }

    public String getValorRestanteFormatado() {
        NumberFormat nf = NumberFormat.getNumberInstance();
        nf.setMinimumFractionDigits(2);
        nf.setMaximumFractionDigits(2);
        return nf.format(this.valorParcela - this.valorPago);
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
