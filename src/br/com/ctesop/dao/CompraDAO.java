package br.com.ctesop.dao;

import br.com.ctesop.model.Compra;
import br.com.ctesop.model.Fornecedor;
import br.com.ctesop.model.ItensCompra;
import br.com.ctesop.model.Produto;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author Bruna
 */
public class CompraDAO {

    public static int inserir(Compra compra, Conexao c) throws Exception {

        String sql = "insert into tbcompra (data, total, status, codfornecedor) values (?, ?, ?,?)";
        PreparedStatement ps = c.getConexao().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        ps.setDate(1, new Date(compra.getData().getTime()));
        ps.setDouble(2, compra.getValorTotal());
        ps.setString(3, compra.getStatus());
        ps.setInt(4, compra.getFornecedor().getCodigo());
        ps.execute();

        ResultSet rs = ps.getGeneratedKeys();
        rs.next();
        int codigo = rs.getInt(1);

        for (ItensCompra item : compra.getItens()) {
            sql = "insert into tbitemcompra (codproduto, codcompra, quantidade, valor) VALUES (?, ?, ?, ?)";
            ps = c.getConexao().prepareStatement(sql);
            ps.setInt(1, item.getProduto().getCodigo());
            ps.setInt(2, codigo);
            ps.setInt(3, item.getQuantidade());
            ps.setDouble(4, item.getValorUnitario());
            ps.execute();
        }

        return codigo;
    }

    private static void alterar(Compra compra, Conexao c) throws Exception {
        
        String sql = "update tbcompra set data=?, total=?, status=?, codfornecedor=? where codcompra=?";
        PreparedStatement ps = c.getConexao().prepareStatement(sql);
        ps.setDate(1, new Date(compra.getData().getTime()));
        ps.setDouble(2, compra.getValorTotal());
        ps.setString(3, compra.getStatus());
        ps.setInt(4, compra.getFornecedor().getCodigo());
        ps.setInt(5, compra.getCodigo());
        ps.execute();

        sql = "delete from tbitemcompra where codcompra=?";
        ps = c.getConexao().prepareStatement(sql);
        ps.setInt(1, compra.getCodigo());
        ps.execute();

        for (ItensCompra item : compra.getItens()) {
            sql = "insert into tbitemcompra (codproduto, codcompra, quantidade, valor) VALUES (?, ?, ?, ?)";
            ps = c.getConexao().prepareStatement(sql);
            ps.setInt(1, item.getProduto().getCodigo());
            ps.setInt(2, compra.getCodigo());
            ps.setInt(3, item.getQuantidade());
            ps.setDouble(4, item.getValorUnitario());
            ps.execute();
        }

    }

    public static int salvar(Compra compra, Conexao c) throws Exception {
        if (compra.getCodigo() == 0) {
            return inserir(compra, c);
        } else {
            alterar(compra, c);
            return compra.getCodigo();
        }

    }

    public static ObservableList<Compra> listar() throws Exception {

        String sql = "select * from tbcompra as c"
                + " inner join tbpessoa as f"
                + " on f.codpessoa = c.codfornecedor ";
        Conexao con = new Conexao();
        PreparedStatement ps = con.getConexao().prepareStatement(sql);

        ResultSet rs = ps.executeQuery();
        ObservableList<Compra> lista = FXCollections.observableArrayList();

        while (rs.next()) {
            Compra compra = new Compra();
            compra.setCodigo(rs.getInt("c.codcompra"));
            compra.setData(rs.getDate("c.data"));
            compra.setFornecedor(new Fornecedor(rs.getInt("c.codfornecedor"), rs.getString("f.nomepessoa")));
            compra.setStatus(rs.getString("c.status"));

            sql = "select * from tbitemcompra as ic"
                    + " inner join tbproduto as p"
                    + " on p.codproduto = ic.codproduto"
                    + " where ic.codcompra=?";
            ps = con.getConexao().prepareStatement(sql);
            ps.setInt(1, compra.getCodigo());
            ResultSet rsItens = ps.executeQuery();

            while (rsItens.next()) {
                ItensCompra ic = new ItensCompra();
                ic.setCodigo(rsItens.getInt("ic.coditemcompra"));
                ic.setCompra(compra);
                ic.setProduto(new Produto(rsItens.getInt("ic.codproduto"), rsItens.getString("p.nomeproduto")));
                ic.setQuantidade(rsItens.getInt("ic.quantidade"));
                ic.setValorUnitario(rsItens.getFloat("ic.valor"));
                compra.adicionarItem(ic);
            }

            lista.add(compra);

        }
        return lista;
    }
}
