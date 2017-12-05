package br.com.ctesop.dao;

import br.com.ctesop.model.Caixa;
import br.com.ctesop.model.ContaReceber;
import br.com.ctesop.model.EntregaProducao;
import br.com.ctesop.model.ParcelaReceber;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author Bruna
 */
public class ContaReceberDAO {

    public static void inserir(ContaReceber contaReceber, List<ParcelaReceber> parcelas, Conexao c) throws Exception {

        if (contaReceber.getFormaRecebimento().equalsIgnoreCase("V")) {
            CaixaDAO.getCaixaAberto(c);
        }

        String sql = "insert into tbcontasreceber (codentregaproducao, valorcontasreceber, datapagamento, descricao, status) values (?,?,?,?,?)";
        PreparedStatement ps = c.getConexao().prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
        ps.setInt(1, contaReceber.getEntregaProducao().getCodigo());
        ps.setFloat(2, contaReceber.getValorRecebido());
        ps.setDate(3, new java.sql.Date(contaReceber.getData().getTime()));
        ps.setString(4, contaReceber.getDecricao());
        ps.setString(5, contaReceber.getStatus());
        ps.execute();

        ResultSet rs = ps.getGeneratedKeys();
        rs.next();
        int codContaGerado = rs.getInt(1);

        for (ParcelaReceber parcela : parcelas) {
            sql = "insert into tbparcelareceber (codcontasreceber, valorparcela, data, status) VALUES (?, ?, ?, ?)";
            ps = c.getConexao().prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            ps.setInt(1, codContaGerado);
            ps.setFloat(2, parcela.getValorRecebido());
            ps.setDate(3, new Date(parcela.getData().getTime()));
            ps.setString(4, parcela.getStatus());
            ps.execute();

            rs = ps.getGeneratedKeys();
            rs.next();
            int codParcela = rs.getInt(1);

            if (contaReceber.getFormaRecebimento().equalsIgnoreCase("V")) {

                Caixa caixaAberto = CaixaDAO.getCaixaAberto(c);

                sql = "insert into tbrecebimento (codparcelareceber, valorrecebimento, datarecebimento, descricao, status) VALUES (?, ?, ?, ?, ?)";
                ps = c.getConexao().prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
                ps.setInt(1, codParcela);
                ps.setFloat(2, parcela.getValorRecebido());
                ps.setDate(3, new Date(parcela.getData().getTime()));
                ps.setString(4, "Recebimento a vista da conta " + codContaGerado);
                ps.setString(5, "P");
                ps.execute();

                rs = ps.getGeneratedKeys();
                rs.next();
                int codRecebimento = rs.getInt(1);

                sql = "insert into tbmovimentacaocaixa (codrecebimento, codtbcaixa, data, valor, status) VALUES (?, ?, ?, ?, ?)";
                ps = c.getConexao().prepareStatement(sql);
                ps.setInt(1, codRecebimento);
                ps.setInt(2, caixaAberto.getCodigo());
                ps.setDate(3, new Date(parcela.getData().getTime()));
                ps.setDouble(4, parcela.getValorRecebido());
                ps.setString(5, "P");
                ps.execute();

                sql = "update tbcaixa set valorfechamento=valorfechamento+? where codtbcaixa=?";
                ps = c.getConexao().prepareStatement(sql);
                ps.setDouble(1, contaReceber.getValorRecebido());
                ps.setInt(2, caixaAberto.getCodigo());
                ps.execute();
            }
        }
    }

    public static ObservableList<ContaReceber> listar() throws Exception {
        String sql = "select * from tbcontasreceber as cr "
                + "inner join tbentregaproducao as e "
                + "on e.codentregaproducao = cr.codentregaproducao "
                + "inner join tbcooperativa as co "
                + "on co.codcooperativa = e.codcooperativa";

        Conexao con = new Conexao();
        PreparedStatement ps = con.getConexao().prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        ObservableList<ContaReceber> lista = FXCollections.observableArrayList();
        while (rs.next()) {
            ContaReceber contaReceber = new ContaReceber();
            contaReceber.setCodigo(rs.getInt("cr.codcontasreceber"));
            contaReceber.setEntregaProducao(new EntregaProducao(rs.getInt("e.codentregaproducao"), rs.getString("co.nomecooperativa")));
            contaReceber.setData(rs.getDate("cr.datapagamento"));
            contaReceber.setValor(rs.getFloat("valorcontasreceber"));
            contaReceber.setValorRecebido(RecebimentoDAO.consultarRecebimentosEntrega(contaReceber.getCodigo()));
            contaReceber.setDecricao(rs.getString("cr.descricao"));
            contaReceber.setStatus(rs.getString("cr.status"));
            lista.add(contaReceber);
        }
        return lista;
    }

    public static boolean gerouConta(int codEntregaProducao, Conexao con) throws Exception {
        String sql = "select * "
                + "from tbcontasreceber "
                + "where codentregaproducao=?";

        PreparedStatement ps = con.getConexao().prepareStatement(sql);
        ps.setInt(1, codEntregaProducao);
        ResultSet rs = ps.executeQuery();
        return rs.next();
    }
}
