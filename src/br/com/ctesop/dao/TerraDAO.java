package br.com.ctesop.dao;

import br.com.ctesop.controller.util.ExceptionValidacao;
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
public class TerraDAO {

    public static void inserir(Terra terra) throws Exception {

        String sql = "insert into tbterra (codcidade, tamanho, descricao, status) values (?,?,?,?)";

        Conexao con = new Conexao();

        PreparedStatement ps = con.getConexao().prepareStatement(sql);
        ps.setInt(1, terra.getCidade().getCodigo());
        ps.setFloat(2, terra.getTamanho());
        ps.setString(3, terra.getDescricao());
        ps.setString(4, terra.getStatus());

        ps.execute();
        con.confirmar();
    }

    public static ObservableList<Terra> listar(boolean somenteAtivos) throws Exception {

        String sql = "select * from tbterra ";
        if (somenteAtivos) {
            sql += " where status='A' ";
        }
        sql += " order by nometerra";
        Conexao con = new Conexao();
        PreparedStatement ps = con.getConexao().prepareStatement(sql);

        ResultSet rs = ps.executeQuery();
        ObservableList<Terra> lista = FXCollections.observableArrayList();

        while (rs.next()) {
            Terra terra = new Terra();
            terra.setCodigo(rs.getInt("codterra"));
            terra.setCidade(new Cidade(rs.getInt("codcidade"), rs.getString("nomecidade")));
            terra.setTamanho(rs.getFloat("tamanho"));
            terra.setDescricao(rs.getString("descricao"));
            terra.setStatus(rs.getString("status"));
            lista.add(terra);

        }
        return lista;
    }

    public static void alterar(Terra terra) throws Exception {

        String sql = "update tbterra set codcidade=?, tamanho=?, descricao=?, status=? where codterra=?";

        Conexao con = new Conexao();

        PreparedStatement ps = con.getConexao().prepareStatement(sql);
        ps.setInt(1, terra.getCidade().getCodigo());
        ps.setFloat(2, terra.getTamanho());
        ps.setString(3, terra.getDescricao());
        ps.setString(4, terra.getStatus());

        ps.setInt(5, terra.getCodigo());

        ps.execute();
        con.confirmar();
    }

    public static void salvar(Terra terra) throws Exception {
        if (terra.getCodigo() == 0) {
            if (existe(terra)) {
                throw new ExceptionValidacao("O terra já está cadastrado.");
            }
            inserir(terra);
        } else {
            alterar(terra);
        }
    }

    private static boolean existe(Terra terra) throws Exception {
        String sql = "select count(codterra) from tbterra where codcidade=?, tamanho=? or descricao=?";
        Conexao c = new Conexao();
        PreparedStatement ps = c.getConexao().prepareStatement(sql);
        ps.setInt(1, terra.getCidade().getCodigo());
        ps.setFloat(2, terra.getTamanho());
        ps.setString(3, terra.getDescricao());
        ResultSet rs = ps.executeQuery();
        rs.next();
        return (rs.getInt(1) > 0);
    }

}
