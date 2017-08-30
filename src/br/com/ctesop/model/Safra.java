/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.ctesop.model;

import java.sql.Date;

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

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
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
    public boolean equals(Object o) {
        if (o instanceof Safra) {
            return ((Safra) o).getCodigo() == getCodigo();
        }
        return false;
    }
}
