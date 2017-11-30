package br.com.ctesop.dao;

import br.com.ctesop.controller.util.ExceptionValidacao;
import br.com.ctesop.model.Caixa;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author Bruna
 */
public class CaixaDAO {

    public static void abertura(Caixa caixa) throws Exception {
        String sql = "insert into tbcaixa ( dataabertura, valorabertura,  valorfechamento, status) values (?,?,?,?)";

        Conexao con = new Conexao();

        PreparedStatement ps = con.getConexao().prepareStatement(sql);
        ps.setDate(1, new java.sql.Date(caixa.getDataAbertura().getTime()));
        ps.setFloat(2, caixa.getValorAbertura());
        ps.setFloat(3, caixa.getValorAbertura());
        ps.setString(4, caixa.getStatus());
        ps.execute();
        con.confirmar();

    }

    public static ObservableList<Caixa> listar(boolean somenteAtivos) throws Exception {

        String sql = "select * from tbcaixa ";
        if (somenteAtivos) {
            sql += " where status='A' ";
        }
        sql += " order by descricao";
        Conexao con = new Conexao();
        PreparedStatement ps = con.getConexao().prepareStatement(sql);

        ResultSet rs = ps.executeQuery();
        ObservableList<Caixa> lista = FXCollections.observableArrayList();

        while (rs.next()) {
            Caixa caixa = new Caixa();
            caixa.setCodigo(rs.getInt("codtbcaixa"));
            caixa.setDataAbertura(rs.getDate("dataabertura"));
            caixa.setValorAbertura(rs.getFloat("valorabertura"));
            caixa.setDataFechamento(rs.getDate("datafechamento"));
            caixa.setValorFechamento(rs.getFloat("valorfechamento"));
            caixa.setStatus(rs.getString("status"));
            lista.add(caixa);

        }
        return lista;
    }

    public static void fechamento(Caixa caixa) throws Exception {

        String sql = "update tbcaixa set dataabertura=?, valorabertura=?, datafechamento=?, valorfechamento=?, status=? where codtbcaixa=?";

        Conexao con = new Conexao();

        PreparedStatement ps = con.getConexao().prepareStatement(sql);
        ps.setDate(1, new java.sql.Date(caixa.getDataAbertura().getTime()));
        ps.setFloat(2, caixa.getValorAbertura());
        ps.setDate(3, new java.sql.Date(caixa.getDataFechamento().getTime()));
        ps.setFloat(4, caixa.getValorFechamento());
        ps.setString(5, caixa.getStatus());
        ps.setInt(6, caixa.getCodigo());

        ps.execute();
        con.confirmar();
    }

    private static boolean existe(Caixa caixa) throws Exception {
        String sql = "select count(codtbcaixa) from tbcaixa where status='A'";
        Conexao c = new Conexao();
        PreparedStatement ps = c.getConexao().prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        rs.next();
        return (rs.getInt(1) > 0);
    }

    public static void confirmar(Caixa caixa) throws Exception {
        if (caixa.getCodigo() == 0) {
            if (existe(caixa)) {
                throw new ExceptionValidacao("Já existe um caixa aberto");
            }
            abertura(caixa);
        } else {
            fechamento(caixa);
        }
    }
    
     static Caixa getCaixaAberto(Conexao c) throws Exception {
        String sql = "select * from tbcaixa where status='A' order by codtbcaixa desc";
        PreparedStatement ps = c.getConexao().prepareStatement(sql);

        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            Caixa caixa = new Caixa();
            caixa.setCodigo(rs.getInt("codtbcaixa"));
            caixa.setDataAbertura(rs.getDate("dataabertura"));
            caixa.setValorAbertura(rs.getFloat("valorabertura"));
            caixa.setDataFechamento(rs.getDate("datafechamento"));
            caixa.setValorFechamento(rs.getFloat("valorfechamento"));
            caixa.setStatus(rs.getString("status"));
            return caixa;
        } else {
            throw new Exception("Nenhum caixa aberto");
        }
    }

}
