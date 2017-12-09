package br.com.ctesop.model;

import br.com.ctesop.controller.util.Converter;
import br.com.ctesop.controller.util.ExceptionValidacao;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author Bruna
 */
public class Arrendamento {

    private int codigo;
    private Terra terra;
    private Date data;
    private float valor;
    private String descricao;
    private String status;

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public Terra getTerra() {
        return terra;
    }

    public void setTerra(Terra terra) throws ExceptionValidacao {
        if (terra == null) {
            throw new ExceptionValidacao("Terra inválida!");
        }
        this.terra = terra;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) throws ExceptionValidacao {
        if (data == null) {
            throw new ExceptionValidacao("Data inválida!");
        }
        this.data = data;
    }

    public float getValor() {
        return valor;
    }

    public String getDataFormatada() {
        return Converter.formatarData(data);
    }

    public void setValor(float valor) {
        this.valor = valor;
    }

    public String getValorFormatado() {
        NumberFormat nf = NumberFormat.getNumberInstance();
        nf.setMinimumFractionDigits(2);
        nf.setMaximumFractionDigits(2);
        return nf.format(valor);
    }

    public void setValor(String valor) throws ExceptionValidacao {
        NumberFormat nf = NumberFormat.getNumberInstance();
        try {
            this.valor = nf.parse(valor).floatValue();
        } catch (ParseException ex) {
            throw new ExceptionValidacao("Valor inválido.");
        }
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
    
      @Override
    public String toString() {
        return getDescricao();
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Safra) {
            return ((Safra) o).getCodigo() == getCodigo();
        }
        return false;
    }

}
