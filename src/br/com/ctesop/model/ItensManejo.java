
package br.com.ctesop.model;

import java.text.NumberFormat;

/**
 *
 * @author Bruna
 */
public class ItensManejo {
   private int codigo;
    private Produto produto;
    private Manejo manejo;
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

    public Manejo getManejo() {
        return manejo;
    }

    public void setManejo(Manejo manejo) {
        this.manejo = manejo;
    }


    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public String getQuantidadeFormatada() {
        NumberFormat nf = NumberFormat.getNumberInstance();
        nf.setMaximumFractionDigits(2);
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
            return ((ItensManejo) o).getManejo().getCodigo() == getManejo().getCodigo() && ((ItensManejo) o).getProduto().getCodigo() == getProduto().getCodigo();
        }
        return false;
    } 
}
