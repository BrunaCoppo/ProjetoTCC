package br.com.ctesop.dao;

import br.com.ctesop.model.Compra;
import br.com.ctesop.model.Cooperativa;
import br.com.ctesop.model.EntregaProducao;
import br.com.ctesop.model.ItensCompra;
import br.com.ctesop.model.Safra;
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
public class EntregaProducaoDAO {

    public static int inserir(EntregaProducao entregaProducao, Conexao c) throws Exception {

        String sql = "insert into tbentregaproducao (codcooperativa, codsafra, dataentrega, quantidadeentregue , quantidadedesconto, descricao, status) values (?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement ps = c.getConexao().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        ps.setInt(1, entregaProducao.getCooperativa().getCodigo());
        ps.setInt(2, entregaProducao.getSafra().getCodigo());
        ps.setDate(3, new Date(entregaProducao.getData().getTime()));
        ps.setFloat(4, entregaProducao.getQuantidadeEntregue());
        ps.setFloat(5, entregaProducao.getDesconto());
        ps.setString(6, entregaProducao.getDescricao());
        ps.setString(7, entregaProducao.getStatus());
        ps.execute();

        ResultSet rs = ps.getGeneratedKeys();
        rs.next();
        int codigo = rs.getInt(1);
        return codigo;

    }

    private static void alterar(EntregaProducao entregaproducao, Conexao c) throws Exception {

        String sql = "update tbentregaproducao set codcooperativa=?, codsafra=?, dataentrega=?, quantidadeentregue=?, quantidadedesconto=?, descricao=?, status=? where codentregaproducao=?";
        PreparedStatement ps = c.getConexao().prepareStatement(sql);
        ps.setInt(1, entregaproducao.getCooperativa().getCodigo());
        ps.setInt(2, entregaproducao.getSafra().getCodigo());
        ps.setDate(3, new Date(entregaproducao.getData().getTime()));
        ps.setFloat(4, entregaproducao.getQuantidadeEntregue());
        ps.setFloat(5, entregaproducao.getDesconto());
        ps.setString(6, entregaproducao.getDescricao());
        ps.setString(7, entregaproducao.getStatus());
        ps.setInt(8, entregaproducao.getCodigo());
        ps.execute();

    }

    public static int salvar(EntregaProducao entregaProducao, Conexao c) throws Exception {
        if (entregaProducao.getCodigo() == 0) {
            return inserir(entregaProducao, c);
        } else {
            alterar(entregaProducao, c);
            return entregaProducao.getCodigo();
        }

    }

    public static ObservableList<EntregaProducao> listar() throws Exception {

        String sql = "select * from tbentregaproducao as e"
                + " inner join tbcooperativa as c"
                + " on c.codcooperativa = e.codcooperativa "
                + " inner join tbsafra as s"
                + " on s.codsafra= e.codsafra "
                + " inner join tbproduto as p"
                + " on p.codproduto= s.codproduto";
        Conexao con = new Conexao();
        PreparedStatement ps = con.getConexao().prepareStatement(sql);

        ResultSet rs = ps.executeQuery();
        ObservableList<EntregaProducao> lista = FXCollections.observableArrayList();

        while (rs.next()) {
            EntregaProducao entregaProducao = new EntregaProducao();
            entregaProducao.setCodigo(rs.getInt("e.codentregaproducao"));
            entregaProducao.setData(rs.getDate("e.dataentrega"));
            entregaProducao.setCooperativa(new Cooperativa(rs.getInt("e.codcooperativa"), rs.getString("c.nomecooperativa")));
            entregaProducao.setSafra(new Safra(rs.getInt("e.codsafra"), rs.getString("p.nomeproduto")));
            entregaProducao.setDesconto(rs.getFloat("e.quantidadedesconto"));
            entregaProducao.setQuantidadeEntregue(rs.getFloat("e.quantidadeentregue"));
            entregaProducao.setDescricao(rs.getString("e.descricao"));
            entregaProducao.setStatus(rs.getString("e.status"));

            lista.add(entregaProducao);

        }
        return lista;
    }

}
