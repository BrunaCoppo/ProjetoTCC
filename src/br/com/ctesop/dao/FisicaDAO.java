package br.com.ctesop.dao;

import br.com.ctesop.controller.util.ExceptionValidacao;
import br.com.ctesop.model.Fisica;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 *
 * @author Bruna
 */
public class FisicaDAO {

    public static int inserir(Fisica fisica) throws Exception {

        String sql = "insert into tbfisica (cpf, rg, ie, datanascimento) values (?,?,?,?)";

        Conexao con = new Conexao();

        PreparedStatement ps = con.getConexao().prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);

        ps.setString(1, fisica.getCpf());
        ps.setString(2, fisica.getRg());
        ps.setString(3, fisica.getIe());
        ps.setDate(4, new Date(fisica.getDataNascimento().getTime()));

      
        ps.execute();

        ResultSet rs = ps.getGeneratedKeys();
        rs.next();
        int codigoGerado = rs.getInt(1);
        con.confirmar();

        return codigoGerado;
    }

    public static void alterar(Fisica fisica) throws Exception {

        String sql = "update tbfisica set cpf=?, rg=?, ie=?, datanascimento=? where codfisica=?";

        Conexao con = new Conexao();

        PreparedStatement ps = con.getConexao().prepareStatement(sql);
        ps.setString(1, fisica.getCpf());
        ps.setString(2, fisica.getRg());
        ps.setString(3, fisica.getIe());
        ps.setDate(4, new java.sql.Date(fisica.getDataNascimento().getTime()));

        ps.setInt(5, fisica.getCodigo());

        ps.execute();
        con.confirmar();
    }

    private static boolean existe(Fisica fisica) throws Exception {
        String sql = "select count(codfisica) from tbfisica where cpf=? and rg=?";
        Conexao c = new Conexao();
        PreparedStatement ps = c.getConexao().prepareStatement(sql);
        ps.setString(1, fisica.getCpf());
        ps.setString(2, fisica.getRg());
        ResultSet rs = ps.executeQuery();
        rs.next();
        return (rs.getInt(1) > 0);
    }

    public static int salvar(Fisica fisica) throws Exception {
        if (fisica.getCodigo() == 0) {
            if (existe(fisica)) {
                throw new ExceptionValidacao("Situação fisica já está cadastrada.");
            }
            return inserir(fisica);
        } else {
            alterar(fisica);
            return fisica.getCodigo();
        }

    }

    public static Fisica get(int codigo) throws Exception {       
        String sql = "select * from tbfisica where codfisica=? ";
        Conexao con = new Conexao();
        PreparedStatement ps = con.getConexao().prepareStatement(sql);
        ps.setInt(1, codigo);
        ResultSet rs = ps.executeQuery();
        
        if (rs.next()) {
            Fisica fisica = new Fisica();
            fisica.setCodigo(rs.getInt("codfisica"));
            fisica.setDataNascimento(rs.getDate("datanascimento"));
            fisica.setCpf(rs.getString("cpf"));
            fisica.setRg(rs.getString("rg"));
            fisica.setIe(rs.getString("ie"));
            return fisica;
        }   
        return null;    
    }
}
