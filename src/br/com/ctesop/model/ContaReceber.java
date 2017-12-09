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
public class ContaReceber {

    private int codigo = 0;
    private EntregaProducao entregaProducao;
    private float valor;
    private Date data;
    private String decricao;
    private String status;
    private float valorRecebido;
    private String formaRecebimento;
    private int quantidadeParcelas;

    public Cooperativa getCooperativa() {
        return getEntregaProducao().getCooperativa();
    }

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

    public EntregaProducao getEntregaProducao() {
        return entregaProducao;
    }

    public void setEntregaProducao(EntregaProducao entregaProducao) throws ExceptionValidacao {
        if (entregaProducao == null) {
            throw new ExceptionValidacao("Entrega de Produção inválida!");
        }
        this.entregaProducao = entregaProducao;
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
        return nf.format(this.getValor());
    }

    public void setValor(String valor) throws ExceptionValidacao {
        NumberFormat nf = NumberFormat.getNumberInstance();
        try {
            this.valor = nf.parse(valor).floatValue();
        } catch (ParseException ex) {
            throw new ExceptionValidacao("Valor Recebido inválido.");
        }
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

    public String getDecricao() {
        return decricao;
    }

    public void setDecricao(String decricao) {
        this.decricao = decricao;
    }

    public float getValorRestante() {
        return this.valor - this.valorRecebido;
    }

    public void setValorRecebido(String recebido) throws ExceptionValidacao {
        NumberFormat nf = NumberFormat.getNumberInstance();
        try {
            this.valorRecebido = nf.parse(recebido).floatValue();
        } catch (ParseException ex) {
            throw new ExceptionValidacao("Valor recebido inválido.");
        }
    }

    public String getValorRestanteFormatado() {
        NumberFormat nf = NumberFormat.getNumberInstance();
        nf.setMinimumFractionDigits(2);
        nf.setMaximumFractionDigits(2);
        return nf.format(this.valor - this.valorRecebido);
    }

    public float getValorRecebido() {
        return valorRecebido;
    }

    public void setValorRecebido(float valorRecebido) {
        this.valorRecebido = valorRecebido;
    }

    public String getValorRecebidoFormatado() {
        NumberFormat nf = NumberFormat.getNumberInstance();
        nf.setMinimumFractionDigits(2);
        nf.setMaximumFractionDigits(2);
        return nf.format(this.valorRecebido);
    }

    public String getStatusFormatado() {
        if (getValorRestante() <= 0) {
            return "Recebido";
        } else {
            return "Aberta";
        }
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getFormaRecebimento() {
        return formaRecebimento;
    }

    public void setFormaRecebimento(String formaRecebimento) {
        this.formaRecebimento = formaRecebimento;
    }

}
