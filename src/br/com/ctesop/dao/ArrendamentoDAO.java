package br.com.ctesop.dao;

import br.com.ctesop.controller.util.ExceptionValidacao;
import br.com.ctesop.model.Arrendamento;
import br.com.ctesop.model.Cidade;
import br.com.ctesop.model.Estado;
import br.com.ctesop.model.Terra;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author Bruna
 */
public class ArrendamentoDAO {

    public static void inserir(Arrendamento arrendamento) throws Exception {

        String sql = "insert into tbarrendamento (codterra, data, valor, descricao, status) values (?,?,?,?,?)";

        Conexao con = new Conexao();

        PreparedStatement ps = con.getConexao().prepareStatement(sql);
        ps.setInt(1, arrendamento.getTerra().getCodigo());
        ps.setDate(2, new java.sql.Date(arrendamento.getData().getTime()));
        ps.setFloat(3, arrendamento.getValor());
        ps.setString(4, arrendamento.getDescricao());
        ps.setString(5, arrendamento.getStatus());

        ps.execute();
        con.confirmar();

    }

    public static ObservableList<Arrendamento> listar(boolean somenteAtivos) throws Exception {
        String sql = ""
                + " select *"
                + " from tbarrendamento as a"
                + " inner join tbterra as t on t.codterra = a.codterra";
        if (somenteAtivos) {
            sql += " where a.status='A' ";
        }
        sql += " order by a.status, a.data ";

        Conexao con = new Conexao();
        PreparedStatement ps = con.getConexao().prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        ObservableList<Arrendamento> lista = FXCollections.observableArrayList();
        while (rs.next()) {
            Arrendamento arrendamento = new Arrendamento();
            arrendamento.setCodigo(rs.getInt("a.codarrendamento"));
            arrendamento.setData(rs.getDate("a.data"));
            arrendamento.setTerra(new Terra(rs.getInt("t.codestado"), rs.getString("t.nomeestado")));
            arrendamento.setValor(rs.getFloat("a.valor"));
            arrendamento.setDescricao(rs.getString("a.descricao"));
            arrendamento.setStatus(rs.getString("a.status"));
            lista.add(arrendamento);
        }
        return lista;
    }

    public static void alterar(Arrendamento arrendamento) throws Exception {

        String sql = "update tbarrendamento set codterra=?, data=?, valor=?, descricao=?, status=? where codarrendamento=?";

        Conexao con = new Conexao();

        PreparedStatement ps = con.getConexao().prepareStatement(sql);
        ps.setInt(1, arrendamento.getTerra().getCodigo());
        ps.setDate(2, new java.sql.Date(arrendamento.getData().getTime()));
        ps.setFloat(3, arrendamento.getValor());
        ps.setString(4, arrendamento.getDescricao());
        ps.setString(5, arrendamento.getStatus());

        ps.setInt(6, arrendamento.getCodigo());

        ps.execute();
        con.confirmar();
    }

    public static void salvar(Arrendamento arrendamento) throws Exception {
        if (arrendamento.getCodigo() == 0) {
            if (existe(arrendamento)) {
                throw new ExceptionValidacao("O Arrendamento já está cadastrada.");
            }
            inserir(arrendamento);
        } else {
            alterar(arrendamento);
        }
    }

    private static boolean existe(Arrendamento arrendamento) throws Exception {
        String sql = "select count(codarrendamento) from tbarrendamento where codestado=?";
        Conexao c = new Conexao();
        PreparedStatement ps = c.getConexao().prepareStatement(sql);
        ps.setInt(1, arrendamento.getTerra().getCodigo());

        ResultSet rs = ps.executeQuery();
        rs.next();
        return (rs.getInt(1) > 0);
    }
}
