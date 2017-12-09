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
public class ContaPagar {

    private int codigo = 0;
    private Compra compra;
    private Arrendamento arrendamento;
    private Manejo manejo;
    private Date data;
    private String descricao;
    private float valor;
    private float valorPago;
    private String status;
    private String formaPagamento;
    private int quantidadeParcelas;

    public int getQuantidadeParcelas() {
        return quantidadeParcelas;
    }

    public void setQuantidadeParcelas(int quantidadeParcelas) {
        this.quantidadeParcelas = quantidadeParcelas;
    }

    public String getQtdeParcelaFormatada() {
        NumberFormat nf = NumberFormat.getNumberInstance();
        nf.setMinimumFractionDigits(2);
        nf.setMaximumFractionDigits(2);
        return nf.format(this.getQuantidadeParcelas());
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

    public Compra getCompra() {
        return compra;
    }

    public void setCompra(Compra compra) throws ExceptionValidacao {
        if (compra == null) {
            throw new ExceptionValidacao("Compra inválida!");
        }
        this.compra = compra;
    }

    public Pessoa getFornecedor() {
        return getCompra().getFornecedor();
    }

    public Arrendamento getArrendamento() {
        return arrendamento;
    }

    public void setArrendamento(Arrendamento arrendamento) throws ExceptionValidacao {
        if (arrendamento == null) {
            throw new ExceptionValidacao("Arrendamento inválida!");
        }
        this.arrendamento = arrendamento;
    }

    public Manejo getManejo() {
        return manejo;
    }

    public void setManejo(Manejo manejo) {
        this.manejo = manejo;
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
        NumberFormat nf = NumberFormat.getNumberInstance();
        nf.setMinimumFractionDigits(2);
        nf.setMaximumFractionDigits(2);
        return nf.format(this.valor);
    }

    public String getValorPagoFormatado() {
        NumberFormat nf = NumberFormat.getNumberInstance();
        nf.setMinimumFractionDigits(2);
        nf.setMaximumFractionDigits(2);
        return nf.format(this.valorPago);
    }

    public float getValorRestante() {
        return this.valor - this.valorPago;
    }

    public String getValorRestanteFormatado() {
        NumberFormat nf = NumberFormat.getNumberInstance();
        nf.setMinimumFractionDigits(2);
        nf.setMaximumFractionDigits(2);
        return nf.format(this.valor - this.valorPago);
    }

    public float getValorPago() {
        return valorPago;
    }

    public void setValorPago(float valorPago) {
        this.valorPago = valorPago;
    }

    public String getStatus() {
        return status;
    }

    public String getStatusFormatado() {
        if (getValorRestante() <= 0) {
            return "Pago";
        } else {
            return "Aberta";
        }
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getFormaPagamento() {
        return formaPagamento;
    }

    public void setFormaPagamento(String formaPagamento) {
        this.formaPagamento = formaPagamento;
    }

}
