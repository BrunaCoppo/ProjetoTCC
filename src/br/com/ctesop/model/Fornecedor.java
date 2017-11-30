package br.com.ctesop.model;

import br.com.ctesop.controller.util.ExceptionValidacao;

/**
 *
 * @author Bruna
 */
public class Fornecedor extends Pessoa {

    public Fornecedor() {
    }

    public Fornecedor(int codigo) {
        setCodigo(codigo);
    }

    public Fornecedor(int codigo, String nome) throws ExceptionValidacao {
        setCodigo(codigo);
        setNome(nome);
    }
    
    public Fornecedor(String nome) throws ExceptionValidacao {
        setNome(nome);
    }
}
