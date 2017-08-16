
package br.com.ctesop.dao;

import br.com.ctesop.controller.util.ExceptionValidacao;
import br.com.ctesop.model.TipoSafra;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author Bruna
 */
public class TipoSafraDAO {
       public static void inserir(TipoSafra tipoSafra) throws Exception {

        String sql = "insert into tbtiposafra (tiposafra, status) values (?,?)";

        Conexao con = new Conexao();

        PreparedStatement ps = con.getConexao().prepareStatement(sql);
        ps.setString(1, tipoSafra.getNome());
        ps.setString(2, tipoSafra.getStatus());

        ps.execute();
        con.confirmar();
    }

    public static ObservableList<TipoSafra> listar(boolean somenteAtivos) throws Exception {

        String sql = "select * from tbtiposafra ";
        if (somenteAtivos) {
            sql += " where status='A' ";
        }
        sql += " order by tiposafra";
        Conexao con = new Conexao();
        PreparedStatement ps = con.getConexao().prepareStatement(sql);

        ResultSet rs = ps.executeQuery();
        ObservableList<TipoSafra> lista = FXCollections.observableArrayList();

        while (rs.next()) {
            TipoSafra tipoSafra = new TipoSafra();
            tipoSafra.setCodigo(rs.getInt("codtiposafra"));
            tipoSafra.setNome(rs.getString("tiposafra"));
            tipoSafra.setStatus(rs.getString("status"));
            lista.add(tipoSafra);

        }
        return lista;
    }

    public static void alterar(TipoSafra tipoSafra) throws Exception {

        String sql = "update tbtiposafra set tiposafra=?, status=? where codtiposafra=?";

        Conexao con = new Conexao();

        PreparedStatement ps = con.getConexao().prepareStatement(sql);
        ps.setString(1, tipoSafra.getNome());
        ps.setString(2, tipoSafra.getStatus());

        ps.setInt(3, tipoSafra.getCodigo());

        ps.execute();
        con.confirmar();
    }

    public static void salvar(TipoSafra tipoSafra) throws Exception {
        if (tipoSafra.getCodigo() == 0) {
            if (existe(tipoSafra)) {
                throw new ExceptionValidacao("O tipo de safra já está cadastrado.");
            }
            inserir(tipoSafra);
        } else {
            alterar(tipoSafra);
        }
    }

    private static boolean existe(TipoSafra tipoSafra) throws Exception {
        String sql = "select count(codtiposafra) from tbtiposafra where tiposafra=?";
        Conexao c = new Conexao();
        PreparedStatement ps = c.getConexao().prepareStatement(sql);
        ps.setString(1, tipoSafra.getNome());
        ResultSet rs = ps.executeQuery();
        rs.next();
        return (rs.getInt(1) > 0);
    }
}
