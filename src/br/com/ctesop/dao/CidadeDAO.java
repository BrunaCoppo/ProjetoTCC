package br.com.ctesop.dao;

import br.com.ctesop.controller.util.ExceptionValidacao;
import br.com.ctesop.model.Cidade;
import br.com.ctesop.model.Estado;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author Bruna
 */
public class CidadeDAO {

    public static void inserir(Cidade cidade) throws Exception {

        String sql = "insert into tbcidade (nomecidade,codestado, status) values (?,?,?)";

        Conexao con = new Conexao();

        PreparedStatement ps = con.getConexao().prepareStatement(sql);
        ps.setString(1, cidade.getNome());
        ps.setInt(2, cidade.getEstado().getCodigo());
        ps.setString(3, cidade.getStatus());

        ps.execute();
        con.confirmar();

    }

    public static ObservableList<Cidade> listar() throws Exception {
        String sql = ""
                + " select *"
                + " from tbcidade as c"
                + " inner join tbestado as e on e.codestado = c.codestado"
                + " order by c.status, c.nomecidade ";
        Conexao con = new Conexao();
        PreparedStatement ps = con.getConexao().prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        ObservableList<Cidade> lista = FXCollections.observableArrayList();
        while (rs.next()) {
            Cidade cidade = new Cidade();
            cidade.setCodigo(rs.getInt("c.codcidade"));
            cidade.setNome(rs.getString("c.nomecidade"));
            cidade.setEstado(new Estado(rs.getInt("e.codestado"), rs.getString("e.nomeestado")));
            cidade.setStatus(rs.getString("c.status"));
            lista.add(cidade);
        }
        return lista;
    }

    public static void alterar(Cidade cidade) throws Exception {

        String sql = "update tbcidade set nomecidade=?, codestado=?, status=? where codcidade=?";

        Conexao con = new Conexao();

        PreparedStatement ps = con.getConexao().prepareStatement(sql);
        ps.setString(1, cidade.getNome());
        ps.setInt(2, cidade.getEstado().getCodigo());
        ps.setString(3, cidade.getStatus());

        ps.setInt(4, cidade.getCodigo());

        ps.execute();
        con.confirmar();
    }

    public static void salvar(Cidade cidade) throws Exception {
        if (cidade.getCodigo() == 0) {
            if (existe(cidade)) {
                throw new ExceptionValidacao("A cidade já está cadastrada.");
            }
            inserir(cidade);
        } else {
            alterar(cidade);
        }
    }

    private static boolean existe(Cidade cidade) throws Exception {
        String sql = "select count(codcidade) from tbcidade where nomecidade=? and codestado=?";
        Conexao c = new Conexao();
        PreparedStatement ps = c.getConexao().prepareStatement(sql);
        ps.setString(1, cidade.getNome());
        ps.setInt(2, cidade.getEstado().getCodigo());
        ResultSet rs = ps.executeQuery();
        rs.next();
        return (rs.getInt(1) > 0);
    }
}
