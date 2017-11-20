/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.ctesop.model;

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
        this.codigo = codigo;
    }

    public Terra getTerra() {
        return terra;
    }

    public void setTerra(Terra terra) {
        this.terra = terra;
    }

    public TipoManejo getTipManejo() {
        return tipManejo;
    }

    public void setTipManejo(TipoManejo tipManejo) {
        this.tipManejo = tipManejo;
    }

    public Safra getSafra() {
        return safra;
    }

    public void setSafra(Safra safra) {
        this.safra = safra;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
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

    public void setData(Date data) {
        this.data = data;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    
    
    
}
