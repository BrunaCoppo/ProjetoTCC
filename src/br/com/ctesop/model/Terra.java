package br.com.ctesop.model;

import br.com.ctesop.controller.util.ExceptionValidacao;
import java.security.InvalidParameterException;
import java.text.NumberFormat;
import java.text.ParseException;

/**
 *
 * @author Bruna
 */
public class Terra {

    private int codigo;
    private Cidade cidade;
    private float tamanho;
    private String descricao;
    private String status;

    public Terra(int aInt, String string) {
        this.codigo = codigo;
    }

    public Terra() {
        
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

    public Cidade getCidade() {
        return cidade;
    }

    public void setCidade(Cidade cidade) throws ExceptionValidacao {
        if (cidade==null) {
            throw new ExceptionValidacao("Cidade obrigatória.");
        }
        this.cidade = cidade;
    }

    public float getTamanho() {
        return tamanho;
    }

    public String getTamanhoFormatado() {
        NumberFormat nf = NumberFormat.getNumberInstance();
        nf.setMinimumFractionDigits(2);
        return nf.format(this.tamanho);
    }

    public void setTamanho(float tamanho) {

        this.tamanho = tamanho;
    }

    public void setTamanho(String tamanho) throws ExceptionValidacao {
        NumberFormat nf = NumberFormat.getNumberInstance();
        try {
            this.tamanho = nf.parse(tamanho).floatValue();
        } catch (ParseException ex) {
            throw new ExceptionValidacao("Tamanho inválido.");
        }
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) throws ExceptionValidacao {
        if (descricao.isEmpty()) {
            throw new ExceptionValidacao("Descrição obrigatória.");
        }
        if (descricao.trim().length() < 2) {
            throw new ExceptionValidacao("Descriçao muito curta.");
        }

        if (descricao.trim().length() > 200) {
            throw new ExceptionValidacao("Descrição muito longa.");

        }
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
        return getDescricao() + " "+ getTamanhoFormatado() + " " + getCidade();
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Terra) {
            return ((Terra) o).getCodigo() == getCodigo();

        }
        return false;
    }

}
