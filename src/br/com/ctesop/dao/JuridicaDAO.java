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

    public static int inserir(Juridica juridica) throws Exception {

        String sql = "insert into tbjuridica (cnpj, ei) values (?,?)";

        Conexao con = new Conexao();

        PreparedStatement ps = con.getConexao().prepareStatement(sql);
        ps.setString(1, juridica.getCnpj());
        ps.setString(2, juridica.getEi());

        ps.execute();
        con.confirmar();
        
        ResultSet rs = ps.executeQuery();
        return rs.getInt(1);

    }

    public static void alterar(Juridica juridica) throws Exception {

        String sql = "update tbjuridica set cnpj=?, ei=? where codjuridica=?";

        Conexao con = new Conexao();

        PreparedStatement ps = con.getConexao().prepareStatement(sql);
        ps.setString(1, juridica.getCnpj());
        ps.setString(2, juridica.getEi());

        ps.setInt(3, juridica.getCodigo());

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

    public static int salvar(Juridica juridica) throws Exception {
        if (juridica.getCodigo() == 0) {
            if (existe(juridica)) {
                throw new ExceptionValidacao("Situação juridica já está cadastrada.");
            }
            return inserir(juridica);
        } else {
            alterar(juridica);
            return juridica.getCodigo();

        }

    }
}
