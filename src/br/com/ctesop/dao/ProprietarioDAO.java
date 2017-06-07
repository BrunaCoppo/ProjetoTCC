
package br.com.ctesop.dao;

import br.com.ctesop.controller.util.ExceptionValidacao;
import br.com.ctesop.model.Juridica;
import br.com.ctesop.model.Proprietario;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 *
 * @author Bruna
 */
public class ProprietarioDAO {
    
    public static void inserir(Proprietario proprietario) throws Exception {

        String sql = "insert into tbproprirtsrio (codpessoa) values (?)";

        Conexao con = new Conexao();

        PreparedStatement ps = con.getConexao().prepareStatement(sql);
        ps.setInt(1, proprietario.getPessoa().getCodigo());

        ps.execute();
        con.confirmar();

    }

    public static void alterar(Proprietario proprietario) throws Exception {

        String sql = "update tbproprietario where codpessoa=?";

        Conexao con = new Conexao();

        PreparedStatement ps = con.getConexao().prepareStatement(sql);
        ps.setInt(1, proprietario.getPessoa().getCodigo());
        

        ps.execute();
        con.confirmar();
    }

    private static boolean existe(Proprietario proprietario) throws Exception {
        String sql = "select count(codpessoa) from tbproprietario";
        Conexao c = new Conexao();
        PreparedStatement ps = c.getConexao().prepareStatement(sql);
        ps.setInt(1, proprietario.getPessoa().getCodigo());
        ResultSet rs = ps.executeQuery();
        rs.next();
        return (rs.getInt(1) > 0);
    }

    public static void salvar(Proprietario proprietario) throws Exception {
        if (proprietario.getPessoa().getCodigo()== 0) {
            if (existe(proprietario)) {
                throw new ExceptionValidacao("Situação Proprietario já está cadastrada.");
            }
            inserir(proprietario);
        } else {
            alterar(proprietario);
        }
    }
    
}
