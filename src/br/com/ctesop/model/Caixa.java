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
public class Caixa {

    private int codigo;
    private Date dataAbertura;
    private Date dataFechamento;
    private float valorAbertura;
    private float valorFechamento;
    private String status;

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        if (codigo < 0) {
            throw new InvalidParameterException("Código inválido.");
        }
        this.codigo = codigo;
    }

    public Date getDataAbertura() {
        return dataAbertura;
    }

    public String getDataAberturaFormatada() {
        return Converter.formatarData(dataAbertura);
    }

    public void setDataAbertura(Date dataAbertura) {
        this.dataAbertura = dataAbertura;
    }

    public Date getDataFechamento() {
        return dataFechamento;
    }

    public void setDataFechamento(Date dataFechamento) {
        this.dataFechamento = dataFechamento;
    }

    public float getValorAbertura() {
        return valorAbertura;
    }

    public void setValorAbertura(float valorAbertura) {
        this.valorAbertura = valorAbertura;
    }

    public String getDataFechamentoFormatada() {
        return Converter.formatarData(dataFechamento);
    }

    public void setValorAbertura(String valorAbertura) throws ExceptionValidacao {
        NumberFormat nf = NumberFormat.getNumberInstance();
        try {
            this.valorAbertura = nf.parse(valorAbertura).floatValue();
        } catch (ParseException ex) {
            throw new ExceptionValidacao("Valor Abertura inválido.");
        }
    }

    public float getValorFechamento() {
        return valorFechamento;
    }

    public void setValorFechamento(float valorFechamento) {
        this.valorFechamento = valorFechamento;
    }

    public void setValorFechamento(String valorFechamento) throws ExceptionValidacao {
        NumberFormat nf = NumberFormat.getNumberInstance();
        try {
            this.valorFechamento = nf.parse(valorFechamento).floatValue();
        } catch (ParseException ex) {
            throw new ExceptionValidacao("Valor Fechamento inválido.");
        }
    }

    public String getValorAberturaFormatado() {
        NumberFormat nfReal = NumberFormat.getCurrencyInstance();
        nfReal.setMinimumFractionDigits(2);
        nfReal.setMaximumFractionDigits(2);
        return nfReal.format(this.valorAbertura);
    }

    public String getValorFechamentoFormatado() {
        NumberFormat nfReal = NumberFormat.getCurrencyInstance();
        nfReal.setMinimumFractionDigits(2);
        nfReal.setMaximumFractionDigits(2);
        return nfReal.format(this.valorFechamento);
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return getDataAberturaFormatada();
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Terra) {
            return ((Caixa) o).getCodigo() == getCodigo();

        }
        return false;
    }

}
