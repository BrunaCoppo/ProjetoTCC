
package br.com.ctesop.dao;

import br.com.ctesop.controller.util.ExceptionValidacao;
import br.com.ctesop.model.TipoManejo;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author Bruna
 */
public class TipoManejoDAO {
    
    
    public static void inserir(TipoManejo tipoManejo) throws Exception {

        String sql = "insert into tbtipomanejo (tipomanejo, valor, descricao, status) values (?,?,?,?)";

        Conexao con = new Conexao();

        PreparedStatement ps = con.getConexao().prepareStatement(sql);
        ps.setString(1, tipoManejo.getNome());
        ps.setFloat(2, tipoManejo.getValor());
        ps.setString(3, tipoManejo.getDescricao());
        ps.setString(4, tipoManejo.getStatus());

        ps.execute();
        con.confirmar();
    }

    public static ObservableList<TipoManejo> listar(boolean somenteAtivos) throws Exception {

        String sql = "select * from tbtipomanejo ";
        if (somenteAtivos) {
            sql += " where status='A' ";
        }
        sql += " order by tipomanejo";
        Conexao con = new Conexao();
        PreparedStatement ps = con.getConexao().prepareStatement(sql);

        ResultSet rs = ps.executeQuery();
        ObservableList<TipoManejo> lista = FXCollections.observableArrayList();

        while (rs.next()) {
            TipoManejo tipoManejo = new TipoManejo();
            tipoManejo.setCodigo(rs.getInt("codtipomanejo"));
            tipoManejo.setNome(rs.getString("tipomanejo"));
            tipoManejo.setValor(rs.getFloat("valor"));
            tipoManejo.setDescricao(rs.getString("descricao"));
            tipoManejo.setStatus(rs.getString("status"));
            lista.add(tipoManejo);

        }
        return lista;
    }

    public static void alterar(TipoManejo tipoManejo) throws Exception {

        String sql = "update tbtipomanejo set tipomanejo=?, valor=?, descricao=?, status=? where codtipomanejo=?";

        Conexao con = new Conexao();

        PreparedStatement ps = con.getConexao().prepareStatement(sql);
        ps.setString(1, tipoManejo.getNome());
        ps.setFloat(2, tipoManejo.getValor());
        ps.setString(3, tipoManejo.getDescricao());
        ps.setString(4, tipoManejo.getStatus());

        ps.setInt(5, tipoManejo.getCodigo());

        ps.execute();
        con.confirmar();
    }

    public static void salvar(TipoManejo tipoManejo) throws Exception {
        if (tipoManejo.getCodigo() == 0) {
            if (existe(tipoManejo)) {
                throw new ExceptionValidacao("O tipo de manejo já está cadastrado.");
            }
            inserir(tipoManejo);
        } else {
            alterar(tipoManejo);
        }
    }

    private static boolean existe(TipoManejo tipoManejo) throws Exception {
        String sql = "select count(codtipomanejo) from tbtipomanejo where tipomanejo=?";
        Conexao c = new Conexao();
        PreparedStatement ps = c.getConexao().prepareStatement(sql);
        ps.setString(1, tipoManejo.getNome());
        ResultSet rs = ps.executeQuery();
        rs.next();
        return (rs.getInt(1) > 0);
    }
}
