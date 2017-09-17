package br.com.ctesop.dao;

import br.com.ctesop.controller.util.ExceptionValidacao;
import br.com.ctesop.model.Produto;
import br.com.ctesop.model.Safra;
import br.com.ctesop.model.TipoSafra;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.util.Date;


/**
 *
 * @author Bruna
 */
public class SafraDAO {

    public static void inserir(Safra safra) throws Exception {

        String sql = "insert into tbsafra (codproduto, codtiposafra, datainicio, datatermino, status) values (?,?,?,?,?)";

        Conexao con = new Conexao();

        PreparedStatement ps = con.getConexao().prepareStatement(sql);
        ps.setInt(1, safra.getProduto().getCodigo());
        ps.setInt(2, safra.getTipoSafra().getCodigo());
        ps.setDate(3, new java.sql.Date(safra.getDataInicio().getTime()));
        ps.setDate(4, new java.sql.Date(safra.getDataTermino().getTime()));
        ps.setString(5, safra.getStatus());

        ps.execute();
        con.confirmar();

    }

    public static ObservableList<Safra> listar(boolean somenteAtivos) throws Exception {
        String sql = ""
                + " select *"
                + " from tbsafra as s"
                + " inner join tbproduto as p on p.codproduto = s.codproduto"
                + " inner join tbtiposafra as t on t.codtiposafra = s.codtiposafra";
        if (somenteAtivos) {
            sql += " where s.status='A' ";
        }
        sql += " order by s.status";

        Conexao con = new Conexao();
        PreparedStatement ps = con.getConexao().prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        ObservableList<Safra> lista = FXCollections.observableArrayList();
        while (rs.next()) {
            Safra safra = new Safra();
            safra.setCodigo(rs.getInt("s.codsafra"));
            safra.setProduto(new Produto(rs.getInt("p.codproduto"), rs.getString("p.nomeproduto")));
            safra.setTipoSafra(new TipoSafra(rs.getInt("t.codtiposafra"), rs.getString("t.tiposafra")));
            safra.setDataInicio(rs.getDate("s.datainicio"));
            safra.setDataTermino(rs.getDate("s.datatermino"));
            safra.setStatus(rs.getString("s.status"));
            lista.add(safra);
        }
        return lista;
    }

    public static void alterar(Safra safra) throws Exception {

        String sql = "update tbsafra set codproduto=?, codtiposafra=?, status=? where codsafra=?";

        Conexao con = new Conexao();

        PreparedStatement ps = con.getConexao().prepareStatement(sql);
        ps.setInt(1, safra.getProduto().getCodigo());
        ps.setInt(2, safra.getTipoSafra().getCodigo());
        ps.setString(3, safra.getStatus());

        ps.setInt(4, safra.getCodigo());

        ps.execute();
        con.confirmar();
    }

    public static void salvar(Safra safra) throws Exception {
        if (safra.getCodigo() == 0) {
            if (existe(safra)) {
                throw new ExceptionValidacao("A safra já está cadastrada.");
            }
            inserir(safra);
        } else {
            alterar(safra);
        }
    }

    private static boolean existe(Safra safra) throws Exception {
        String sql = "select count(codsafra) from tbsafra where datainicio=? and status=?";
        Conexao c = new Conexao();
        PreparedStatement ps = c.getConexao().prepareStatement(sql);
//        ps.setDate(1, safra.getDataInicio());
       // ps.setString(2, safra.getStatus());
        ResultSet rs = ps.executeQuery();
        rs.next();
        return (rs.getInt(1) > 0);
    }
    
    

}
