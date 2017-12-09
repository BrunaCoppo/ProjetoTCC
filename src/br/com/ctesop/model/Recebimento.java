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
public class Recebimento {

    private int codigo = 0;
    private ParcelaReceber parcelaReceber;
    private float valorRecebimento;
    private Date dataRecebimento;
    private String descricao;
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

    public ParcelaReceber getParcelaReceber() {
        return parcelaReceber;
    }

    public void setParcelaReceber(ParcelaReceber parcelaReceber) throws ExceptionValidacao {
        if (parcelaReceber == null) {
            throw new ExceptionValidacao("Parcela Receber inválida!");
        }
        this.parcelaReceber = parcelaReceber;
    }

    public float getValorRecebimento() {
        return valorRecebimento;
    }

    public void setValorRecebimento(float valorRecebimento) {
        this.valorRecebimento = valorRecebimento;
    }

    public void setValor(String valor) throws ExceptionValidacao {
        NumberFormat nf = NumberFormat.getNumberInstance();
        try {
            this.valorRecebimento = nf.parse(valor).floatValue();
        } catch (ParseException ex) {
            throw new ExceptionValidacao("Valor inválido.");
        }
    }

    public String getValorFormatado() {
        NumberFormat nfReal = NumberFormat.getNumberInstance();
        nfReal.setMinimumFractionDigits(2);
        nfReal.setMaximumFractionDigits(2);
        return nfReal.format(this.valorRecebimento);
    }

    public Date getDataRecebimento() {
        return dataRecebimento;
    }

    public void setDataRecebimento(Date dataRecebimento) {
        this.dataRecebimento = dataRecebimento;
    }

    public String getDataFormatada() {
        return Converter.formatarData(dataRecebimento);
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
