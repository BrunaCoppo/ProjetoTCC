package br.com.ctesop.dao;

import br.com.ctesop.model.Caixa;
import br.com.ctesop.model.ParcelaReceber;
import br.com.ctesop.model.Recebimento;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author Bruna
 */
public class RecebimentoDAO {

    public static void receber(Recebimento recebimento) throws Exception {

        String sql = "insert into tbrecebimento (codparcelareceber, valorrecebimento, datarecebimento, descricao, status) values (?,?,?,?,?)";

        Conexao con = new Conexao();

        PreparedStatement ps = con.getConexao().prepareStatement(sql);
        ps.setInt(1, recebimento.getParcelaReceber().getCodigo());
        ps.setFloat(2, recebimento.getValorRecebimento());
        ps.setDate(3, new java.sql.Date(recebimento.getDataRecebimento().getTime()));
        ps.setString(4, recebimento.getDescricao());
        ps.setString(5, recebimento.getStatus());
        ps.execute();

        ResultSet rs = ps.getGeneratedKeys();
        rs.next();
        int codRecebimento = rs.getInt(1);

        Caixa caixaAberto = CaixaDAO.getCaixaAberto(con);


        sql = "insert into tbmovimentacaocaixa (codrecebimento, codtbcaixa, data, valor, status) VALUES (?, ?, ?, ?, ?)";
        ps = con.getConexao().prepareStatement(sql);
        ps.setInt(1, codRecebimento);
        ps.setInt(2, caixaAberto.getCodigo());
        ps.setDate(3, new Date(recebimento.getDataRecebimento().getTime()));
        ps.setDouble(4, recebimento.getValorRecebimento());
        ps.setString(5, "P");
        ps.execute();

        sql = "update tbcaixa set valorfechamento=valorfechamento+? where codtbcaixa=?";
        ps = con.getConexao().prepareStatement(sql);
        ps.setDouble(1, recebimento.getValorRecebimento());
        ps.setInt(2, caixaAberto.getCodigo());
        ps.execute();

        //atualiza status parcela
        if (recebimento.getParcelaReceber().getValorRestante() <= recebimento.getValorRecebimento()) {
            sql = "update tbparcelareceber set status='P' where codparcelareceber=?";
            ps = con.getConexao().prepareStatement(sql);
            ps.setDouble(1, recebimento.getParcelaReceber().getCodigo());
            ps.execute();
        }

        con.confirmar();

    }

    public static ObservableList<Recebimento> listar(boolean somenteAtivos) throws Exception {
        String sql = ""
                + " select *"
                + " from tbrecebimento as r "
                + " inner join tbparcelareceber as pr on pr.codparcelareceber = r.codparcelareceber";
               
        if (somenteAtivos) {
            sql += " where r.status='A' ";
        }
        sql += " order by r.status, r.descricao ";

        Conexao con = new Conexao();
        PreparedStatement ps = con.getConexao().prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        ObservableList<Recebimento> lista = FXCollections.observableArrayList();
        while (rs.next()) {
            Recebimento recebimento = new Recebimento();
            recebimento.setCodigo(rs.getInt("r.codrecebimento"));
            recebimento.setParcelaReceber(new ParcelaReceber(rs.getInt("pr.codparcelareceber"), rs.getFloat("pr.valor")));
            recebimento.setDataRecebimento(rs.getDate("r.datarecebimento"));
            recebimento.setValorRecebimento(rs.getFloat("r.valorrecebimento"));
            recebimento.setDescricao(rs.getString("r.descricao"));
            recebimento.setStatus(rs.getString("r.status"));
            lista.add(recebimento);

        }
        return lista;
    }

    public static float consultarRecebimentosEntrega(int codEntrega) throws Exception {
        String sql = "select sum(r.valorrecebimento) "
                + " from tbrecebimento as r"
                + " inner join tbparcelareceber as pr "
                + " on pr.codparcelareceber = r.codparcelareceber"
                + " where pr.codcontasreceber=?";

        Conexao con = new Conexao();
        PreparedStatement ps = con.getConexao().prepareStatement(sql);
        ps.setInt(1, codEntrega);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            return rs.getFloat(1);
        }
        return 0;
    }

    public static float consultarRecebimentoParcela(int codRecebimento) throws Exception {
        String sql = "select sum(r.valorrecebimento) "
                + " from tbrecebimento as r"
                + " where r.codparcelareceber=?";

        Conexao con = new Conexao();
        PreparedStatement ps = con.getConexao().prepareStatement(sql);
        ps.setInt(1, codRecebimento);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            return rs.getFloat(1);
        }
        return 0;
    }
}
