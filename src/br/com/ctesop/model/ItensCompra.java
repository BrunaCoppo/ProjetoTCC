package br.com.ctesop.model;

import java.text.NumberFormat;

/**
 *
 * @author Bruna
 */
public class ItensCompra {

    private int codigo;
    private Produto produto;
    private Compra compra;
    private int quantidade;
    private float valorUnitario;

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public Compra getCompra() {
        return compra;
    }

    public void setCompra(Compra compra) {
        this.compra = compra;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }
    
    public String getQuantidadeFormataad(){
        NumberFormat nf = NumberFormat.getNumberInstance();
        nf.setMaximumFractionDigits(2);
        nf.setMinimumFractionDigits(2);
        return nf.format(quantidade);
    }

    public float getValorUnitario() {
        return valorUnitario;
    }

    public void setValorUnitario(float valor) {
        this.valorUnitario = valor;
    }

    public String getValorUnitarioFormatado() {
        NumberFormat nf = NumberFormat.getNumberInstance();
        nf.setMinimumFractionDigits(2);
        nf.setMaximumFractionDigits(2);
        return nf.format(this.valorUnitario);
    }

    public double getSubtotal() {
        return valorUnitario * quantidade;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof ItensCompra) {
            return ((ItensCompra) o).getCodigo() == getCodigo();
        }
        return false;
    }

}
