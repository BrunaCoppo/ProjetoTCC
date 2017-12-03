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
public class EntregaProducao {

    private int codigo;
    private Cooperativa cooperativa;
    private Safra safra;
    private Date data;
    private float quantidadeEntregue;
    private float desconto;
    private String descricao;
    private String status;
    private float valor;

    public EntregaProducao(int aInt, String string) {
        this.codigo = codigo;
    }

    public EntregaProducao() {

    }

    public String getProduto() {
        return getSafra().getProduto().getNome();
    }

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

    public Safra getSafra() {
        return safra;
    }

    public void setSafra(Safra safra) {
        this.safra = safra;
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

    public float getQuantidadeEntregue() {
        return quantidadeEntregue;
    }

    public void setQuantidadeEntregue(float quantidadeEntregue) {
        this.quantidadeEntregue = quantidadeEntregue;
    }

    public String getQuantidadeFormatada() {
        NumberFormat nf = NumberFormat.getNumberInstance();
        nf.setMaximumFractionDigits(2);
        return nf.format(quantidadeEntregue);
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

    public double getValorTotal() {
        double total = 0;
        double totalDesconto = 0;
        totalDesconto = (getDesconto() * getQuantidadeEntregue());
        total += (getQuantidadeEntregue() * getValor());
        total = (total - totalDesconto);
        return total;
    }

    public String getValorTotalFormatado() {
        NumberFormat nf = NumberFormat.getNumberInstance();
        nf.setMinimumFractionDigits(2);
        nf.setMaximumFractionDigits(2);
        return nf.format(this.getValorTotal());
    }

    public float getDesconto() {
        return desconto;
    }

    public void setDesconto(float desconto) {
        this.desconto = desconto;
    }

    public void setDesconto(String desconto) throws ExceptionValidacao {
        NumberFormat nf = NumberFormat.getNumberInstance();
        try {
            this.desconto = nf.parse(desconto).floatValue();
        } catch (ParseException ex) {
            throw new ExceptionValidacao("Desconto inválido.");
        }
    }

    public String getDescontoFormatado() {
        NumberFormat nf = NumberFormat.getNumberInstance();
        nf.setMinimumFractionDigits(2);
        nf.setMaximumFractionDigits(2);
        return nf.format(this.desconto);
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
