package br.com.ctesop.model;

import br.com.ctesop.controller.util.ExceptionValidacao;
import java.security.InvalidParameterException;
import java.util.Date;

/**
 *
 * @author Bruna
 */
public class Manejo {

    private int codigo;
    private Terra terra;
    private TipoManejo tipManejo;
    private Safra safra;
    private String descricao;
    private float valor;
    private Date data;
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

    public Terra getTerra() {
        return terra;
    }

    public void setTerra(Terra terra) throws ExceptionValidacao {
        if (terra == null) {
            throw new ExceptionValidacao("Terra inválida!");
        }
        this.terra = terra;
    }

    public TipoManejo getTipManejo() {
        return tipManejo;
    }

    public void setTipManejo(TipoManejo tipManejo) throws ExceptionValidacao {
        if (tipManejo == null) {
            throw new ExceptionValidacao("Tipo Manejo inválida!");
        }
        this.tipManejo = tipManejo;
    }

    public Safra getSafra() {
        return safra;
    }

    public void setSafra(Safra safra) throws ExceptionValidacao {
        if (safra == null) {
            throw new ExceptionValidacao("Safra inválida!");
        }
        this.safra = safra;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) throws ExceptionValidacao {
        if (descricao.trim().length() < 2) {
            throw new ExceptionValidacao("Descrição muito curto.");
        }
        if (descricao.trim().length() > 200) {
            throw new ExceptionValidacao("Desceição muito longo.");

        }
        this.descricao = descricao;
    }

    public float getValor() {
        return valor;
    }

    public void setValor(float valor) {
        this.valor = valor;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
