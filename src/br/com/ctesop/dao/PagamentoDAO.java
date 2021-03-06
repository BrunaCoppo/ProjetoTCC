package br.com.ctesop.dao;

import br.com.ctesop.model.Caixa;
import br.com.ctesop.model.Pagamento;
import br.com.ctesop.model.ParcelaPagar;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author Bruna
 */
public class PagamentoDAO {

    public static void pagar(Pagamento pagamento) throws Exception {

        String sql = "insert into tbpagamento (codparcelapagar, descricao, data, valor, status) values (?,?,?,?,?)";

        Conexao con = new Conexao();

        PreparedStatement ps = con.getConexao().prepareStatement(sql);
        ps.setInt(1, pagamento.getPacelaPagar().getCodigo());
        ps.setString(2, pagamento.getDescricao());
        ps.setDate(3, new java.sql.Date(pagamento.getData().getTime()));
        ps.setFloat(4, pagamento.getValor());
        ps.setString(5, pagamento.getStatus());
        ps.execute();

        ResultSet rs = ps.getGeneratedKeys();
        rs.next();
        int codPagamento = rs.getInt(1);

        Caixa caixaAberto = CaixaDAO.getCaixaAberto(con);

        if (caixaAberto.getValorFechamento() < pagamento.getValor()) {
            throw new Exception("Saldo insuficiente em caixa");
        }

        sql = "insert into tbmovimentacaocaixa (codpagamento, codtbcaixa, data, valor, status) VALUES (?, ?, ?, ?, ?)";
        ps = con.getConexao().prepareStatement(sql);
        ps.setInt(1, codPagamento);
        ps.setInt(2, caixaAberto.getCodigo());
        ps.setDate(3, new Date(pagamento.getData().getTime()));
        ps.setDouble(4, pagamento.getValor());
        ps.setString(5, "P");
        ps.execute();

        sql = "update tbcaixa set valorfechamento=valorfechamento-? where codtbcaixa=?";
        ps = con.getConexao().prepareStatement(sql);
        ps.setDouble(1, pagamento.getValor());
        ps.setInt(2, caixaAberto.getCodigo());
        ps.execute();

        //atualiza status parcela
        if (pagamento.getPacelaPagar().getValorRestante() <= pagamento.getValor()) {
            sql = "update tbparcelapagar set status='P' where codparcelapagar=?";
            ps = con.getConexao().prepareStatement(sql);
            ps.setDouble(1, pagamento.getPacelaPagar().getCodigo());
            ps.execute();
        }
        
        con.confirmar();

    }

    public static ObservableList<Pagamento> listar(boolean somenteAtivos) throws Exception {
        String sql = ""
                + " select *"
                + " from tbpagamento as p"
                + " inner join tbparcelapagar as pc on pc.codparcelapagar = p.codparcelapagar";
        if (somenteAtivos) {
            sql += " where p.status='A' ";
        }
        sql += " order by p.status, p.descricao ";

        Conexao con = new Conexao();
        PreparedStatement ps = con.getConexao().prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        ObservableList<Pagamento> lista = FXCollections.observableArrayList();
        while (rs.next()) {
            Pagamento pagamento = new Pagamento();
            pagamento.setCodigo(rs.getInt("p.codpagamento"));
            pagamento.setPacelaPagar(new ParcelaPagar(rs.getInt("pc.codparcelapagar"), rs.getFloat("pc.valor")));
            pagamento.setData(rs.getDate("p.data"));
            pagamento.setValor(rs.getFloat("p.valor"));
            pagamento.setDescricao(rs.getString("p.descricao"));
            pagamento.setStatus(rs.getString("p.status"));
            lista.add(pagamento);
        }
        return lista;
    }

    public static float consultarPagamentosConta(int codContaPagar) throws Exception {
        String sql = "select sum(p.valor) "
                + " from tbpagamento as p"
                + " inner join tbparcelapagar as pc "
                + " on pc.codparcelapagar = p.codparcelapagar"
                + " where pc.codcontapagar=?";

        Conexao con = new Conexao();
        PreparedStatement ps = con.getConexao().prepareStatement(sql);
        ps.setInt(1, codContaPagar);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            return rs.getFloat(1);
        }
        return 0;
    }

    public static float consultarPagamentosParcela(int codParcela) throws Exception {
        String sql = "select sum(p.valor) "
                + " from tbpagamento as p"
                + " where p.codparcelapagar=?";

        Conexao con = new Conexao();
        PreparedStatement ps = con.getConexao().prepareStatement(sql);
        ps.setInt(1, codParcela);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            return rs.getFloat(1);
        }
        return 0;
    }

}
