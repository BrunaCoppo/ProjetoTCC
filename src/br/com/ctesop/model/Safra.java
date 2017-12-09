package br.com.ctesop.model;

import br.com.ctesop.controller.util.Converter;
import br.com.ctesop.controller.util.ExceptionValidacao;
import java.security.InvalidParameterException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author Bruna
 */
public class Safra {

    private int codigo = 0;
    private Produto produto;
    private TipoSafra tipoSafra;
    private Date dataInicio;
    private Date dataTermino;
    private String status;

    public Safra(int codigo, String nomeProduto) throws ExceptionValidacao {
        this.codigo = codigo;
        this.produto = new Produto();
        this.produto.setNome(nomeProduto);
    }

    public Safra() {

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

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) throws ExceptionValidacao {
        if (produto == null) {
            throw new ExceptionValidacao("Produto inválida!");
        }
        this.produto = produto;
    }

    public TipoSafra getTipoSafra() {
        return tipoSafra;
    }

    public void setTipoSafra(TipoSafra tipoSafra) throws ExceptionValidacao {
        if (tipoSafra == null) {
            throw new ExceptionValidacao("Tipo da Safra inválida!");
        }
        this.tipoSafra = tipoSafra;
    }

    public Date getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(Date dataInicio) {
        this.dataInicio = dataInicio;
    }

    public String getDataInicioFormatada() {
        return Converter.formatarData(dataInicio);
    }

    public Date getDataTermino() {
        return dataTermino;
    }

    public void setDataTermino(Date dataTermino) {
        this.dataTermino = dataTermino;
    }

    public String getDataTerminoFormatada() {
        if (getDataTermino() == null) {
            return "";
        }
        return Converter.formatarData(dataTermino);
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        return getTipoSafra() + " " + getProduto() + " " + sdf.format(getDataInicio());
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Safra) {
            return ((Safra) o).getCodigo() == getCodigo();
        }
        return false;
    }
}
