
package br.com.ctesop.dao;

import br.com.ctesop.controller.util.ExceptionValidacao;
import br.com.ctesop.model.Funcionario;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 *
 * @author Bruna
 */
public class FuncionarioDAO {
    
        public static void inserir(Funcionario funcionario) throws Exception {

        String sql = "insert into tbfuncionario (codpessoa) values (?)";

        Conexao con = new Conexao();

        PreparedStatement ps = con.getConexao().prepareStatement(sql);
        ps.setInt(1, funcionario.getCodigo());

        ps.execute();
        con.confirmar();

    }

    public static void alterar(Funcionario funcionario) throws Exception {

        String sql = "update tbfuncionario where codpessoa=?";

        Conexao con = new Conexao();

        PreparedStatement ps = con.getConexao().prepareStatement(sql);
        ps.setInt(1, funcionario.getCodigo());
        

        ps.execute();
        con.confirmar();
    }

    private static boolean existe(Funcionario funcionario) throws Exception {
        String sql = "select count(codpessoa) from tbfuncionario";
        Conexao c = new Conexao();
        PreparedStatement ps = c.getConexao().prepareStatement(sql);
        ps.setInt(1, funcionario.getCodigo());
        ResultSet rs = ps.executeQuery();
        rs.next();
        return (rs.getInt(1) > 0);
    }

    public static void salvar(Funcionario funcionario) throws Exception {
        if (funcionario.getCodigo()== 0) {
            if (existe(funcionario)) {
                throw new ExceptionValidacao("Situação Proprietario já está cadastrada.");
            }
            inserir(funcionario);
        } else {
            alterar(funcionario);
        }
    }
}
