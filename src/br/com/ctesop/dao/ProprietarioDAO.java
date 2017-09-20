package br.com.ctesop.dao;

import br.com.ctesop.controller.util.ExceptionValidacao;
import br.com.ctesop.model.Proprietario;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 *
 * @author Bruna
 */
public class ProprietarioDAO {

    public static void inserir(Proprietario proprietario) throws Exception {

        String sql = "insert into tbproprietario (codpessoa) values (?)";

        Conexao con = new Conexao();

        PreparedStatement ps = con.getConexao().prepareStatement(sql);
        ps.setInt(1, proprietario.getCodigo());

        ps.execute();
        con.confirmar();

    }

    public static boolean existe(Proprietario proprietario) throws Exception {
        String sql = "select count(codpessoa) from tbproprietario where codpessoa=?";
        Conexao c = new Conexao();
        PreparedStatement ps = c.getConexao().prepareStatement(sql);
        ps.setInt(1, proprietario.getCodigo());
        ResultSet rs = ps.executeQuery();
        rs.next();
        return (rs.getInt(1) > 0);
    }

    public static void excluir(Proprietario proprietario) throws Exception {
        String sql = "delete from tbproprietario where codpessoa=?";
        Conexao conexao = new Conexao();
        PreparedStatement ps = conexao.getConexao().prepareStatement(sql);
        ps.setInt(1, proprietario.getCodigo());
        ps.execute();
        conexao.confirmar();
    }

    public static void salvar(Proprietario proprietario) throws Exception {
        if (!existe(proprietario)) {
            inserir(proprietario);
        }
    }

}
