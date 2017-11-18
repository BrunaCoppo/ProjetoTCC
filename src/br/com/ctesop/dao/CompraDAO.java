package br.com.ctesop.dao;

import br.com.ctesop.controller.util.ExceptionValidacao;
import static br.com.ctesop.dao.EstadoDAO.alterar;
import static br.com.ctesop.dao.EstadoDAO.inserir;
import br.com.ctesop.model.Compra;
import br.com.ctesop.model.Estado;
import br.com.ctesop.model.ItensCompra;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 *
 * @author Bruna
 */
public class CompraDAO {

    public static void inserir(Compra compra, Conexao c) throws Exception {
        
        String sql = "insert into tbcompra (data, total, status) values (?, ?, ?)";
        PreparedStatement ps = c.getConexao().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        ps.setDate(1, new Date(compra.getData().getTime()));
        ps.setDouble(2, compra.getValorTotal());
        ps.setString(3, compra.getStatus());
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
    }

    private static void alterar(Compra compra, Conexao c) throws Exception {
       
        String sql = "update tbcompra set data=?, valor=?, status=? where coditemcompra=?";
        PreparedStatement ps = c.getConexao().prepareStatement(sql);
        ps.setDate(1, new Date(compra.getData().getTime()));
        ps.setDouble(2, compra.getValorTotal());
        ps.setString(3, compra.getStatus());
        ps.setInt(4, compra.getCodigo());
        ps.execute();

        for (ItensCompra item : compra.getItensRemover()) {
            sql = "delete from tbitemcompra where where coditemcompra=?";
            ps = c.getConexao().prepareStatement(sql);
            ps.setInt(1, item.getCodigo());
            ps.execute();
        }

        for (ItensCompra item : compra.getItens()) {
            if (item.getCodigo() == 0) {
                sql = "insert into tbitemcompra (codproduto,codcompra, quantidade, valor) VALUES (?, ?, ?, ?)";
                ps = c.getConexao().prepareStatement(sql);
                ps.setInt(1, item.getProduto().getCodigo());
                ps.setInt(2, item.getCompra().getCodigo());
                ps.setInt(3, item.getQuantidade());
                ps.setDouble(4, item.getValorUnitario());
                ps.execute();
            } else {
                sql = "update tbitemcompra set codproduto=?, codcompra=?, quantidade=?, valor=? where coditemcompra=?";
                ps = c.getConexao().prepareStatement(sql);
                ps.setInt(1, item.getProduto().getCodigo());
                ps.setInt(2, item.getCompra().getCodigo());
                ps.setInt(3, item.getQuantidade());
                ps.setDouble(4, item.getValorUnitario());
                ps.setInt(5, item.getCodigo());
                ps.execute();
            }

        }

    }


    public static void salvar(Compra compra, Conexao c) throws Exception {
        if (compra.getCodigo() == 0) {
            inserir(compra, c);
        } else {
            alterar(compra, c);
        }

    }
}
