
package br.com.ctesop.dao;

import br.com.ctesop.controller.util.ExceptionValidacao;
import br.com.ctesop.model.Fornecedor;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 *
 * @author Bruna
 */
public class FornecedorDAO {
        
        public static void inserir(Fornecedor fornecedor) throws Exception {

        String sql = "insert into tbfornecedor (codpessoa) values (?)";

        Conexao con = new Conexao();

        PreparedStatement ps = con.getConexao().prepareStatement(sql);
        ps.setInt(1, fornecedor.getCodigo());

        ps.execute();
        con.confirmar();

    }


    private static boolean existe(Fornecedor fornecedor) throws Exception {
        String sql = "select count(codpessoa) from tbfornecedor";
        Conexao c = new Conexao();
        PreparedStatement ps = c.getConexao().prepareStatement(sql);
        ps.setInt(1, fornecedor.getCodigo());
        ResultSet rs = ps.executeQuery();
        rs.next();
        return (rs.getInt(1) > 0);
    }
    
     public static void excluir(Fornecedor fornecedor) throws Exception {
        String sql = "delete from tbfornecedor where codpessoa=?";
        Conexao conexao = new Conexao();
        PreparedStatement ps = conexao.getConexao().prepareStatement(sql);
        ps.setInt(1, fornecedor.getCodigo());
        ps.execute();
        conexao.confirmar();
    }
    
    public static void salvar(Fornecedor fornecedor) throws Exception {
        if (fornecedor.getCodigo()== 0) {
            if (existe(fornecedor)) {
                throw new ExceptionValidacao("Situação Fornecedor já está cadastrada.");
            }
            inserir(fornecedor);
        }
    }
}
