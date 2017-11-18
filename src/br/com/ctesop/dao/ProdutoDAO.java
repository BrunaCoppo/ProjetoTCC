package br.com.ctesop.dao;

import br.com.ctesop.controller.util.ExceptionValidacao;
import br.com.ctesop.model.Produto;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author Bruna
 */
public class ProdutoDAO {
    public static void inserir(Produto produto) throws Exception {

        String sql = "insert into tbproduto (nomeproduto, descricao, status, produzido, quantidade) values (?,?,?,?,?)";

        Conexao con = new Conexao();

        PreparedStatement ps = con.getConexao().prepareStatement(sql);
        ps.setString(1, produto.getNome());
        ps.setString(2, produto.getDescricao());
        ps.setString(3, produto.getStatus());
        ps.setString(4, produto.getProduzido());
        ps.setFloat(5, produto.getQuantidade());

        ps.execute();
        con.confirmar();
    }

    public static ObservableList<Produto> listar(boolean somenteAtivos) throws Exception {

        String sql = "select * from tbproduto ";
        if (somenteAtivos) {
            sql += " where tbproduto.status='A' ";
        }
        sql += " order by nomeproduto";
        Conexao con = new Conexao();
        PreparedStatement ps = con.getConexao().prepareStatement(sql);

        ResultSet rs = ps.executeQuery();
        ObservableList<Produto> lista = FXCollections.observableArrayList();

        while (rs.next()) {
            Produto produto = new Produto();
            produto.setCodigo(rs.getInt("codproduto"));
            produto.setNome(rs.getString("nomeproduto"));
            produto.setDescricao(rs.getString("descricao"));
            produto.setStatus(rs.getString("status"));
            produto.setProduzido(rs.getString("produzido"));
            produto.setQuantidade(rs.getFloat("quantidade"));
            lista.add(produto);

        }
        return lista;
    }

    public static void alterar(Produto produto) throws Exception {

        String sql = "update tbproduto set nomeproduto=?, descricao=?, status=?, produzido=?, quantidade=? where codproduto=?";

        Conexao con = new Conexao();

        PreparedStatement ps = con.getConexao().prepareStatement(sql);
        ps.setString(1, produto.getNome());
        ps.setString(2, produto.getDescricao());
        ps.setString(3, produto.getStatus());
        ps.setString(4, produto.getProduzido());
        ps.setFloat(5, produto.getQuantidade());

        ps.setInt(6, produto.getCodigo());

        ps.execute();
        con.confirmar();
    }

     public static void alterarQuantidade(Produto produto, Conexao con) throws Exception {

        String sql = "update tbproduto set quantidade=quantidade+? where codproduto=?";


        PreparedStatement ps = con.getConexao().prepareStatement(sql);
       
        ps.setFloat(1, produto.getQuantidade());

        ps.setInt(2, produto.getCodigo());

        ps.execute();
        
    }

    
    public static void salvar(Produto produto) throws Exception {
        if (produto.getCodigo() == 0) {
            if (existe(produto)) {
                throw new ExceptionValidacao("O produto já está cadastrado.");
            }
            inserir(produto);
        } else {
            alterar(produto);
        }
    }

    private static boolean existe(Produto produto) throws Exception {
        String sql = "select count(codproduto) from tbproduto where nomeproduto=?";
        Conexao c = new Conexao();
        PreparedStatement ps = c.getConexao().prepareStatement(sql);
        ps.setString(1, produto.getNome());
        ResultSet rs = ps.executeQuery();
        rs.next();
        return (rs.getInt(1) > 0);
    }
    
}
