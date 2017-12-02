package br.com.ctesop.model;

import br.com.ctesop.controller.util.ExceptionValidacao;
import java.security.InvalidParameterException;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author Bruna
 */
public class ParcelaReceber {

    private int codigo;
    private ContaReceber contaReceber;
    private float valorRecebido;
    private float valorParcela;
    private Date data;
    private String Status;

    public ParcelaReceber(int aInt, float aFloat) {
        this.codigo = codigo;
    }

    public ParcelaReceber() {

    }

    public ParcelaReceber(String pccodparcelapagar) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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

    public ContaReceber getContaReceber() {
        return contaReceber;
    }

    public void setContaReceber(ContaReceber contaReceber) {
        this.contaReceber = contaReceber;
    }

    public float getValorParcela() {
        return valorParcela;
    }

    public void setValorParcela(float valorParcela) {
        this.valorParcela = valorParcela;
    }

    public String getValorParcelaFormatado() {
        NumberFormat nf = NumberFormat.getNumberInstance();
        nf.setMinimumFractionDigits(2);
        nf.setMaximumFractionDigits(2);
        return nf.format(valorParcela);
    }

    public float getValorRecebido() {
        return valorRecebido;
    }

    public void setValorRecebido(float valorRecebido) {
        this.valorRecebido = valorRecebido;
    }

    public float getValorRestante() {
        return this.valorParcela - this.valorRecebido;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public String getDataFormatada() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        return sdf.format(data);
    }


    public String getStatus() {
        return Status;
    }

    public void setStatus(String Status) {
        this.Status = Status;
    }

}
