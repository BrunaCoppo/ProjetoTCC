package br.com.ctesop.dao;

import br.com.ctesop.controller.util.ExceptionValidacao;
import br.com.ctesop.model.Fisica;
import br.com.ctesop.model.Juridica;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 *
 * @author Bruna
 */
public class JuridicaDAO {

    public static int inserir(Juridica juridica) throws Exception {

        String sql = "insert into tbjuridica (cnpj, ie, razaosocial) values (?,?,?)";

        Conexao con = new Conexao();

        PreparedStatement ps = con.getConexao().prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);

        ps.setString(1, juridica.getCnpj());
        ps.setString(2, juridica.getIe());
        ps.setString(3, juridica.getRazaoSocial());

        ps.execute();

        ResultSet rs = ps.getGeneratedKeys();
        rs.next();
        int codigoGerado = rs.getInt(1);
        con.confirmar();

        return codigoGerado;

    }

    public static void alterar(Juridica juridica) throws Exception {

        String sql = "update tbjuridica set cnpj=?, ie=?, razaosocial=? where codjuridica=?";

        Conexao con = new Conexao();

        PreparedStatement ps = con.getConexao().prepareStatement(sql);
        ps.setString(1, juridica.getCnpj());
        ps.setString(2, juridica.getIe());
        ps.setString(3, juridica.getRazaoSocial());
        ps.setInt(4, juridica.getCodigo());

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

    public static Juridica get(int codigo) throws Exception {       
        String sql = "select * from tbjuridica where codjuridica=? ";
        Conexao con = new Conexao();
        PreparedStatement ps = con.getConexao().prepareStatement(sql);
        ps.setInt(1, codigo);
        ResultSet rs = ps.executeQuery();
        
        if (rs.next()) {
            Juridica juridica = new Juridica();
            juridica.setCodigo(rs.getInt("codjuridica"));
            juridica.setRazaoSocial(rs.getString("razaosocial"));
            juridica.setCnpj(rs.getString("cnpj"));
            juridica.setIe(rs.getString("ie"));
            return juridica;
        }   
        return null;    
    }
}
