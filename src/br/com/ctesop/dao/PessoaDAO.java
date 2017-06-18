
package br.com.ctesop.dao;

import br.com.ctesop.controller.util.ExceptionValidacao;
import br.com.ctesop.model.Pessoa;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 *
 * @author Bruna
 */
public class PessoaDAO {
    
    public static int inserir(Pessoa pessoa) throws Exception {

        String sql = "insert into tbpessoa (codjuridica, codfisica, codcidade, nomepessoa, endereco, telefone, datacadastro, email, status) values (?,?,?,?,?,?,?,?,9)";

        Conexao con = new Conexao();

        PreparedStatement ps = con.getConexao().prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
        ps.setInt(1, pessoa.getJuridica().getCodigo());
        ps.setInt(2, pessoa.getFisica().getCodigo());
        ps.setInt(3, pessoa.getCidade().getCodigo());
        ps.setString(4, pessoa.getNome());
        ps.setString(5, pessoa.getEndereco());
        ps.setString(6, pessoa.getTelefone());
        ps.setDate(7, new java.sql.Date(pessoa.getDatacadastro().getTime()));
        ps.setString(8, pessoa.getEmail());
        ps.setString(9, pessoa.getStatus());
       

        ps.execute();
        con.confirmar();
        
        ResultSet rs = ps.executeQuery();
        return rs.getInt(1);        
    }

     public static void alterar(Pessoa pessoa) throws Exception {

        String sql = "update tbpessoa set codjuridica=?, codfisica=?, codcidade=?, nomepessoa=?, ";
                sql += "endereco=?, telefone=?, datacadastro=?, email=?, status=? where codpessoa=?";

        Conexao con = new Conexao();

        PreparedStatement ps = con.getConexao().prepareStatement(sql);
        ps.setInt(1, pessoa.getJuridica().getCodigo());
        ps.setInt(2, pessoa.getFisica().getCodigo());
        ps.setInt(3, pessoa.getCidade().getCodigo());
        ps.setString(4, pessoa.getNome());
        ps.setString(5, pessoa.getEndereco());
        ps.setString(6, pessoa.getTelefone());
        ps.setDate(7, pessoa.getDatacadastro());
        ps.setString(8, pessoa.getEmail());
        ps.setString(9, pessoa.getStatus());

        ps.setInt(10, pessoa.getCodigo());

        ps.execute();
        con.confirmar();
    }

     
      public static int salvar(Pessoa pessoa) throws Exception {
        if (pessoa.getCodigo() == 0) {
            if (existe(pessoa)) {
                throw new ExceptionValidacao("A pessoa já está cadastrada.");
            }
            return inserir(pessoa);
        } else {
            alterar(pessoa);
            return pessoa.getCodigo();
        }
    }
      
        private static boolean existe(Pessoa pessoa) throws Exception {
        String sql = "select count(codpessoa) from tbpessoa where nome=? or endereco=?";
        Conexao c = new Conexao();
        PreparedStatement ps = c.getConexao().prepareStatement(sql);
        ps.setString(1, pessoa.getNome());
        ps.setString(2, pessoa.getEndereco());
        ResultSet rs = ps.executeQuery();
        rs.next();
        return (rs.getInt(1) > 0);
    }
}
