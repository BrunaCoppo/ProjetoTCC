package br.com.ctesop.model;

import br.com.ctesop.controller.util.Converter;
import java.text.NumberFormat;
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
        this.codigo = codigo;
    }

    public Compra getCompra() {
        return compra;
    }

    public void setCompra(Compra compra) {
        this.compra = compra;
    }

    public Arrendamento getArrendamento() {
        return arrendamento;
    }

    public void setArrendamento(Arrendamento arrendamento) {
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

    public String getDataaFormatada() {
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

    public String getValorFormatado() {
        NumberFormat nf = NumberFormat.getNumberInstance();
        nf.setMinimumFractionDigits(2);
        nf.setMaximumFractionDigits(2);
        return nf.format(this.valor);
    }

    public String getStatus() {
        return status;
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
