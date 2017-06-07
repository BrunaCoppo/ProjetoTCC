package br.com.ctesop.dao;

import br.com.ctesop.controller.util.ExceptionValidacao;
import br.com.ctesop.model.Juridica;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 *
 * @author Bruna
 */
public class JuridicaDAO {

    public static void inserir(Juridica juridica) throws Exception {

        String sql = "insert into tbjuridica (cnpj, ei) values (?,?)";

        Conexao con = new Conexao();

        PreparedStatement ps = con.getConexao().prepareStatement(sql);
        ps.setString(1, juridica.getCnpj());
        ps.setString(2, juridica.getEi());

        ps.execute();
        con.confirmar();

    }

    public static void alterar(Juridica juridica) throws Exception {

        String sql = "update tbjuridica set cnpj=?, ei=? where codjuridica=?";

        Conexao con = new Conexao();

        PreparedStatement ps = con.getConexao().prepareStatement(sql);
        ps.setString(1, juridica.getCnpj());
        ps.setString(2, juridica.getEi());

        ps.setInt(3, juridica.getCodigoJuridica());

        ps.execute();
        con.confirmar();
    }

    private static boolean existe(Juridica juridica) throws Exception {
        String sql = "select count(codjuridica) from tbjuridica where cnpj=?";
        Conexao c = new Conexao();
        PreparedStatement ps = c.getConexao().prepareStatement(sql);
        ps.setString(1, juridica.getCnpj());
        ResultSet rs = ps.executeQuery();
        rs.next();
        return (rs.getInt(1) > 0);
    }

    public static void salvar(Juridica juridica) throws Exception {
        if (juridica.getCodigoJuridica()== 0) {
            if (existe(juridica)) {
                throw new ExceptionValidacao("Situação juridica já está cadastrada.");
            }
            inserir(juridica);
        } else {
            alterar(juridica);
        }
    }
}
