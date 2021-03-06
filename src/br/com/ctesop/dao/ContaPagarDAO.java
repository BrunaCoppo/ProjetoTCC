package br.com.ctesop.dao;

import br.com.ctesop.model.Caixa;
import br.com.ctesop.model.Compra;
import br.com.ctesop.model.ContaPagar;
import br.com.ctesop.model.ParcelaPagar;
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
public class ContaPagarDAO {

    public static void inserir(ContaPagar contaPagar, List<ParcelaPagar> parcelas, Conexao c) throws Exception {

        if (contaPagar.getFormaPagamento().equalsIgnoreCase("V")) {
            Caixa caixaAberto = CaixaDAO.getCaixaAberto(c);
            
            if (caixaAberto.getValorFechamento()< contaPagar.getValor()) {
                throw  new Exception("Saldo insficiente em caixa.");
            }
        }
        
        String sql = "insert into tbcontapagar (codcompra, datavencimento, descricao, valor, status) values (?,?,?,?,?)";
        PreparedStatement ps = c.getConexao().prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
        ps.setInt(1, contaPagar.getCompra().getCodigo());
        ps.setDate(2, new java.sql.Date(contaPagar.getData().getTime()));
        ps.setString(3, contaPagar.getDescricao());
        ps.setFloat(4, contaPagar.getValor());
        ps.setString(5, contaPagar.getStatus());
        ps.execute();

        ResultSet rs = ps.getGeneratedKeys();
        rs.next();
        int codContaGerado = rs.getInt(1);

        for (ParcelaPagar parcela : parcelas) {
            sql = "insert into tbparcelapagar (codcontapagar, data, valorparcela, status) VALUES (?, ?, ?, ?)";
            ps = c.getConexao().prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            ps.setInt(1, codContaGerado);
            ps.setDate(2, new Date(parcela.getData().getTime()));
            ps.setFloat(3, parcela.getValorParcela());
            ps.setString(4, parcela.getStatus());
            ps.execute();

            rs = ps.getGeneratedKeys();
            rs.next();
            int codParcela = rs.getInt(1);

            if (contaPagar.getFormaPagamento().equalsIgnoreCase("V")) {

                Caixa caixaAberto = CaixaDAO.getCaixaAberto(c);

                if (caixaAberto.getValorFechamento() < contaPagar.getValor()) {
                    throw new Exception("Saldo insuficiente em caixa");
                }

                sql = "insert into tbpagamento (codparcelapagar, descricao, data, valor, status) VALUES (?, ?, ?, ?, ?)";
                ps = c.getConexao().prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
                ps.setInt(1, codParcela);
                ps.setString(2, "Pagamento a vista da conta " + codContaGerado);
                ps.setDate(3, new Date(parcela.getData().getTime()));
                ps.setDouble(4, parcela.getValorParcela());
                ps.setString(5, "P");
                ps.execute();

                rs = ps.getGeneratedKeys();
                rs.next();
                int codPagamento = rs.getInt(1);

                sql = "insert into tbmovimentacaocaixa (codpagamento, codtbcaixa, data, valor, status) VALUES (?, ?, ?, ?, ?)";
                ps = c.getConexao().prepareStatement(sql);
                ps.setInt(1, codPagamento);
                ps.setInt(2, caixaAberto.getCodigo());
                ps.setDate(3, new Date(parcela.getData().getTime()));
                ps.setDouble(4, parcela.getValorParcela());
                ps.setString(5, "P");
                ps.execute();

                sql = "update tbcaixa set valorfechamento=valorfechamento-? where codtbcaixa=?";
                ps = c.getConexao().prepareStatement(sql);
                ps.setDouble(1, contaPagar.getValor());
                ps.setInt(2, caixaAberto.getCodigo());
                ps.execute();
            }
        }
    }

    public static ObservableList<ContaPagar> listar() throws Exception {
        String sql = "select * "
                + "from tbcontapagar as cp "
                + "inner join tbcompra as c "
                + "on c.codcompra = cp.codcompra "
                + "inner join tbpessoa as p "
                + "on p.codpessoa = c.codfornecedor";

        Conexao con = new Conexao();
        PreparedStatement ps = con.getConexao().prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        ObservableList<ContaPagar> lista = FXCollections.observableArrayList();
        while (rs.next()) {
            ContaPagar contaPagar = new ContaPagar();
            contaPagar.setCodigo(rs.getInt("cp.codcontapagar"));
            contaPagar.setCompra(new Compra(rs.getInt("cp.codcompra"), rs.getString("p.nomepessoa")));
            contaPagar.setData(rs.getDate("cp.datavencimento"));
            contaPagar.setValor(rs.getFloat("valor"));
            contaPagar.setValorPago(PagamentoDAO.consultarPagamentosConta(contaPagar.getCodigo()));
            contaPagar.setDescricao(rs.getString("cp.descricao"));
            contaPagar.setStatus(rs.getString("cp.status"));
            lista.add(contaPagar);
        }
        return lista;
    }

    public static boolean gerouConta(int codCompra, Conexao con) throws Exception {
        String sql = "select * "
                + "from tbcontapagar "
                + "where codcompra=?";

        PreparedStatement ps = con.getConexao().prepareStatement(sql);
        ps.setInt(1, codCompra);
        ResultSet rs = ps.executeQuery();
        return rs.next();
    }
}
