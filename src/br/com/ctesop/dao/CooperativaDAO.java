package br.com.ctesop.dao;

import br.com.ctesop.controller.util.ExceptionValidacao;
import br.com.ctesop.model.Cidade;
import br.com.ctesop.model.Cooperativa;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author Bruna
 */
public class CooperativaDAO {

    public static void inserir(Cooperativa cooperativa) throws Exception {

        String sql = "insert into tbcooperativa (codcidade, nomecooperativa, unidade, status) values (?,?,?,?)";

        Conexao con = new Conexao();

        PreparedStatement ps = con.getConexao().prepareStatement(sql);
        ps.setInt(1, cooperativa.getCidade().getCodigo());
        ps.setString(2, cooperativa.getNome());
        ps.setInt(3, cooperativa.getUnidade());
        ps.setString(4, cooperativa.getStatus());

        ps.execute();
        con.confirmar();

    }

    public static ObservableList<Cooperativa> listar(boolean somenteAtivos) throws Exception {
        String sql = ""
                + " select *"
                + " from tbcooperativa as co"
                + " inner join tbcidade as ci on ci.codcidade = co.codcidade";
        if (somenteAtivos) {
            sql += " where status='A' ";
        }
        sql += " order by co.status, co.nomecooperativa ";

        Conexao con = new Conexao();
        PreparedStatement ps = con.getConexao().prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        ObservableList<Cooperativa> lista = FXCollections.observableArrayList();
        while (rs.next()) {
            Cooperativa cooperativa = new Cooperativa();
            cooperativa.setCodigo(rs.getInt("co.codcooperativa"));
            cooperativa.setNome(rs.getString("co.nomecooperativa"));
            cooperativa.setCidade(new Cidade(rs.getInt("ci.codcidade"), rs.getString("ci.nomecidade")));
            cooperativa.setStatus(rs.getString("co.status"));
            lista.add(cooperativa);
        }
        return lista;
    }

    public static void alterar(Cooperativa cooperativa) throws Exception {

        String sql = "update tbcooperativa set codcidade=?, nomecooperativa=?, unidade=?, status=? where codcooperativa=?";

        Conexao con = new Conexao();

        PreparedStatement ps = con.getConexao().prepareStatement(sql);
        ps.setInt(1, cooperativa.getCidade().getCodigo());
        ps.setString(2, cooperativa.getNome());
        ps.setInt(3, cooperativa.getUnidade());
        ps.setString(4, cooperativa.getStatus());

        ps.setInt(5, cooperativa.getCodigo());

        ps.execute();
        con.confirmar();
    }

    public static void salvar(Cooperativa cooperativa) throws Exception {
        if (cooperativa.getCodigo() == 0) {
            if (existe(cooperativa)) {
                throw new ExceptionValidacao("A cooperativa já está cadastrada.");
            }
            inserir(cooperativa);
        } else {
            alterar(cooperativa);
        }
    }

    private static boolean existe(Cooperativa cooperativa) throws Exception {
        String sql = "select count(codcooperativa) from tbcooperativa where nomecooperativa=? and codcidade=?";
        Conexao c = new Conexao();
        PreparedStatement ps = c.getConexao().prepareStatement(sql);
        ps.setString(1, cooperativa.getNome());
        ps.setInt(2, cooperativa.getCidade().getCodigo());
        ResultSet rs = ps.executeQuery();
        rs.next();
        return (rs.getInt(1) > 0);
    }
}
