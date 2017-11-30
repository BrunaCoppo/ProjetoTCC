package br.com.ctesop.model;

import br.com.ctesop.controller.util.Converter;
import br.com.ctesop.controller.util.ExceptionValidacao;
import java.security.InvalidParameterException;
import java.text.NumberFormat;
import java.util.Date;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author Bruna
 */
public class Compra {

    private int codigo;
    private Pessoa fornecedor;
    private Date data;
    private String status;
    private ObservableList<ItensCompra> itens = FXCollections.observableArrayList();

    public Compra(int aInt) {
        this.codigo = codigo;
    }

    public Compra() {
        
    }

    public Compra(int codigo, String fornecedor) throws ExceptionValidacao {
        this.codigo = codigo;
        this.fornecedor = new Fornecedor(fornecedor);
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

    public Pessoa getFornecedor() {
        return fornecedor;
    }

    public void setFornecedor(Pessoa fornecedor) {
        this.fornecedor = fornecedor;
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

    public double getValorTotal() {
        double total = 0;
        for (ItensCompra iv : itens) {
            total += (iv.getValorUnitario() * iv.getQuantidade());
        }
        return total;
    }

    public String getValorTotalFormatado() {
        NumberFormat nf = NumberFormat.getNumberInstance();
        nf.setMinimumFractionDigits(2);
        nf.setMaximumFractionDigits(2);
        return nf.format(this.getValorTotal());
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public ObservableList<ItensCompra> getItens() {
        return itens;
    }

    public void setItens(ObservableList<ItensCompra> itens) {
        this.itens = itens;
    }

    public void adicionarItem(ItensCompra item) {
        itens.add(item);
    }

    public void removerItem(ItensCompra item) {
        itens.remove(item);
    }
}
