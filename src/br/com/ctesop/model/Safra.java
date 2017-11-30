
package br.com.ctesop.model;

import java.security.InvalidParameterException;
import java.util.Date;


/**
 *
 * @author Bruna
 */
public class Safra {
    
    private int codigo = 0 ;
    private Produto produto;
    private TipoSafra tipoSafra;
    private Date dataInicio;
    private Date dataTermino;
    private String status;

    public Safra(int aInt, String string) {
       this.codigo = codigo;
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

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public TipoSafra getTipoSafra() {
        return tipoSafra;
    }

    public void setTipoSafra(TipoSafra tipoSafra) {
        this.tipoSafra = tipoSafra;
    }

    public Date getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(Date dataInicio) {
        this.dataInicio = dataInicio;
    }

    public Date getDataTermino() {
        return dataTermino;
    }

    public void setDataTermino(Date dataTermino) {
        this.dataTermino = dataTermino;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    
  
    @Override
    public String toString() {
        return getStatus();
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Safra) {
            return ((Safra) o).getCodigo() == getCodigo();
        }
        return false;
    }
}
