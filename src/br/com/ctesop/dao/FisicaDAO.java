package br.com.ctesop.dao;

import br.com.ctesop.controller.util.ExceptionValidacao;
import br.com.ctesop.model.Fisica;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 *
 * @author Bruna
 */
public class FisicaDAO {

    public static int inserir(Fisica fisica) throws Exception {

        String sql = "insert into tbfisica (cpf, rg, ei, datanascimento) values (?,?,?,?)";

        Conexao con = new Conexao();

        PreparedStatement ps = con.getConexao().prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);

        ps.setString(1, fisica.getCpf());
        ps.setString(2, fisica.getRg());
        ps.setString(3, fisica.getEi());
         ps.setDate(4,new java.sql.Date(fisica.getDataNascimento().getTime()));

        ps.execute();
        con.confirmar();
        
        ResultSet rs = ps.executeQuery();
        return rs.getInt(1);
    }

    public static void alterar(Fisica fisica) throws Exception {

        String sql = "update tbfisica set cpf=?, rg=?, ei=?, datanascimento=? where codfisica=?";

        Conexao con = new Conexao();

        PreparedStatement ps = con.getConexao().prepareStatement(sql);
        ps.setString(1, fisica.getCpf());
        ps.setString(2, fisica.getRg());
        ps.setString(3, fisica.getEi());
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
        if (fisica.getCodigo()== 0) {
            if (existe(fisica)) {
                throw new ExceptionValidacao("Situação fisica já está cadastrada.");
            }
            return inserir(fisica);
        } else {
            alterar(fisica);
            return fisica.getCodigo();
        }
        
    }
}
