package br.com.ctesop.model;

import br.com.ctesop.controller.util.ExceptionValidacao;
import java.security.InvalidParameterException;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author Bruna
 */
public class ParcelaReceber {

    private int codigo;
    private ContaReceber contaReceber;
    private float valor;
    private float valorRecebido;
    private Date dataRecebimento;
    private Date dataVencimento;
    private String Status;

    public ParcelaReceber(int aInt, float aFloat) {
        this.codigo = codigo;
    }

    public ParcelaReceber() {
        
    }

    public ParcelaReceber(String pccodparcelapagar) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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

    public ContaReceber getContaReceber() {
        return contaReceber;
    }

    public void setContaReceber(ContaReceber contaReceber) {
        this.contaReceber = contaReceber;
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

    public float getValorRecebido() {
        return valorRecebido;
    }

    public void setValorRecebido(float valorRecebido) {
        this.valorRecebido = valorRecebido;
    }

    public float getValorRestante() {
        return this.valor - this.valorRecebido;
    }

    public Date getDataRecebimento() {
        return dataRecebimento;
    }

    public void setDataRecebimento(Date dataRecebimento) {
        this.dataRecebimento = dataRecebimento;
    }

    public String getDataRecebimentoFormatada() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        return sdf.format(dataRecebimento);
    }

    public Date getDataVencimento() {
        return dataVencimento;
    }

    public void setDataVencimento(Date dataVencimento) {
        this.dataVencimento = dataVencimento;
    }

    public String getDataVencimentoFormatada() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        return sdf.format(dataVencimento);
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String Status) {
        this.Status = Status;
    }

}
