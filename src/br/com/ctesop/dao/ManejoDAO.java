package br.com.ctesop.dao;

import br.com.ctesop.controller.util.ExceptionValidacao;
import br.com.ctesop.model.Arrendamento;
import br.com.ctesop.model.Cidade;
import br.com.ctesop.model.Estado;
import br.com.ctesop.model.Manejo;
import br.com.ctesop.model.Safra;
import br.com.ctesop.model.Terra;
import br.com.ctesop.model.TipoManejo;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author Bruna
 */
public class ManejoDAO {

    public static void inserir(Manejo manejo) throws Exception {

        String sql = "insert into tbmanejo (codterra, codtipomanejo, codsafra, descricao, valor, data, status) values (?,?,?,?,?,?,?)";

        Conexao con = new Conexao();

        PreparedStatement ps = con.getConexao().prepareStatement(sql);
        ps.setInt(1, manejo.getTerra().getCodigo());
        ps.setInt(2, manejo.getTipManejo().getCodigo());
        ps.setInt(3, manejo.getSafra().getCodigo());
        ps.setString(4, manejo.getDescricao());
        ps.setFloat(5, manejo.getValor());
        ps.setDate(6, new Date(manejo.getData().getTime()));
        ps.setString(7, manejo.getStatus());

        ps.execute();
        con.confirmar();

    }

    public static ObservableList<Manejo> listar(boolean somenteAtivos) throws Exception {
        String sql = ""
                + " select *"
                + " from tbmanejo as m"
                + " inner join tbterra as t"
                + " on t.codterra = m.codterra"
                + " inner join tbtipomanejo as tm "
                + "on tm.codtipomanejo = m.codtipomanejo"
                + " inner join tbsafra as s "
                + "on s.codsafra = m.codsafra";
        if (somenteAtivos) {
            sql += " where m.status='A' ";
        }
        sql += " order by m.status, m.descricao ";

        Conexao con = new Conexao();
        PreparedStatement ps = con.getConexao().prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        ObservableList<Manejo> lista = FXCollections.observableArrayList();
        while (rs.next()) {
            Manejo manejo = new Manejo();
            manejo.setCodigo(rs.getInt("c.codmanejo"));
            manejo.setTerra(new Terra(rs.getInt("t.codterra"), rs.getString("t.descricao")));
            manejo.setTipManejo(new TipoManejo(rs.getInt("tm.codtipomanejo"), rs.getString("tm.descricao")));
            manejo.setSafra(new Safra(rs.getInt("m.codsafra"), rs.getString("m.nomeproduto")));
            manejo.setData(rs.getDate("t.data"));
            manejo.setDescricao(rs.getString("t.descricao"));
            manejo.setStatus(rs.getString("c.status"));
            lista.add(manejo);
        }
        return lista;
    }

    public static void alterar(Manejo manejo) throws Exception {

        String sql = "update tbmanejo set codterra=?, costipomanejo=?, codsafra=?, descricao=?, data=?, status=? where codmanejo=?";

        Conexao con = new Conexao();

        PreparedStatement ps = con.getConexao().prepareStatement(sql);

        ps.setInt(1, manejo.getTerra().getCodigo());
        ps.setInt(2, manejo.getTipManejo().getCodigo());
        ps.setInt(3, manejo.getSafra().getCodigo());
        ps.setString(4, manejo.getDescricao());
        ps.setFloat(5, manejo.getValor());
        ps.setDate(6, new Date(manejo.getData().getTime()));
        ps.setInt(7, manejo.getCodigo());

        ps.execute();
        con.confirmar();
    }

    public static void salvar(Manejo manejo) throws Exception {
        if (manejo.getCodigo() == 0) {
            if (existe(manejo)) {
                throw new ExceptionValidacao("O manejo já está cadastrado.");
            }
            inserir(manejo);
        } else {
            alterar(manejo);
        }
    }

    private static boolean existe(Manejo manejo) throws Exception {
        String sql = "select count(codmanejo) from tbmanejo where codterra=? and codsafra=?";
        Conexao c = new Conexao();
        PreparedStatement ps = c.getConexao().prepareStatement(sql);
        ps.setInt(1, manejo.getTerra().getCodigo());
        ps.setInt(2, manejo.getSafra().getCodigo());
        ResultSet rs = ps.executeQuery();
        rs.next();
        return (rs.getInt(1) > 0);
    }

}
