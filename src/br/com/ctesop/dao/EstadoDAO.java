package br.com.ctesop.dao;

import br.com.ctesop.controller.util.ExceptionValidacao;
import br.com.ctesop.model.Estado;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author Bruna
 */
public class EstadoDAO {

    public static void inserir(Estado estado) throws Exception {

        String sql = "insert into tbestado (nomeestado, sigla, status) values (?,?,?)";

        Conexao con = new Conexao();

        PreparedStatement ps = con.getConexao().prepareStatement(sql);
        ps.setString(1, estado.getNome());
        ps.setString(2, estado.getSigla());
        ps.setString(3, estado.getStatus());

        ps.execute();
        con.confirmar();
    }

    public static ObservableList<Estado> listar() throws Exception {

        String sql = "Select * from tbestado order by nomeestado";
        Conexao con = new Conexao();
        PreparedStatement ps = con.getConexao().prepareStatement(sql);

        ResultSet rs = ps.executeQuery();
        ObservableList<Estado> lista = FXCollections.observableArrayList();

        while (rs.next()) {
            Estado estado = new Estado();
            estado.setCodigo(rs.getInt("codestado"));
            estado.setNome(rs.getString("nomeestado"));
            estado.setSigla(rs.getString("sigla"));
            estado.setStatus(rs.getString("status"));
            lista.add(estado);

        }
        return lista;
    }

    public static void alterar(Estado estado) throws Exception {

        String sql = "update tbestado set nomeestado=?, sigla=?, status=? where codestado=?";

        Conexao con = new Conexao();

        PreparedStatement ps = con.getConexao().prepareStatement(sql);
        ps.setString(1, estado.getNome());
        ps.setString(2, estado.getSigla());
        ps.setString(3, estado.getStatus());

        ps.setInt(4, estado.getCodigo());

        ps.execute();
        con.confirmar();
    }

    public static void salvar(Estado estado) throws Exception {
        if (estado.getCodigo() == 0) {
            if (existe(estado)) {
                throw new ExceptionValidacao("O estado já está cadastrado.");
            }
            inserir(estado);
        } else {
            alterar(estado);
        }
    }

    private static boolean existe(Estado estado) throws Exception {
        String sql = "select count(codestado) from tbestado where nomeestado=? or sigla=?";
        Conexao c = new Conexao();
        PreparedStatement ps = c.getConexao().prepareStatement(sql);
        ps.setString(1, estado.getNome());
        ps.setString(2, estado.getSigla());
        ResultSet rs = ps.executeQuery();
        rs.next();
        return (rs.getInt(1) > 0);
    }
}
