package br.com.ctesop.model;

import br.com.ctesop.controller.util.ExceptionValidacao;
import java.security.InvalidParameterException;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import javafx.collections.ObservableList;

/**
 *
 * @author Bruna
 */
public class Compra {

    private int codigo;
    private Date data;
    private String status;
    private ArrayList<ItensCompra> itens = new ArrayList<>();
    private ObservableList<ItensCompra> itensRemover;

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        if (codigo < 0) {
            throw new InvalidParameterException("Código inválido.");
        }
        this.codigo = codigo;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public double getValorTotal() {
        double total = 0;
        for (ItensCompra iv : itens) {
            total += (iv.getValorUnitario() * iv.getQuantidade());
        }
        return total;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public ArrayList<ItensCompra> getItens() {
        return itens;
    }

    public void setItens(ArrayList<ItensCompra> itens) {
        this.itens = itens;
    }

    public void adicionarItem(ItensCompra item) {
        itens.add(item);
    }

    public ObservableList<ItensCompra> getItensRemover() {
        return itensRemover;
    }

}
