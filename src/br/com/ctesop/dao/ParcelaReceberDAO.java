
package br.com.ctesop.dao;

import br.com.ctesop.model.ContaPagar;
import br.com.ctesop.model.ContaReceber;
import br.com.ctesop.model.ParcelaPagar;
import br.com.ctesop.model.ParcelaReceber;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author Bruna
 */
public class ParcelaReceberDAO {
     public static void inserir(ParcelaReceber parcelaReceber) throws Exception {

        String sql = "insert into tbparcelareceber (codcontareceber, valorparcela, datarecebimento, status) values (?,?,?,?)";

        Conexao con = new Conexao();

        PreparedStatement ps = con.getConexao().prepareStatement(sql);
        ps.setInt(1, parcelaReceber.getContaReceber().getCodigo());
         ps.setFloat(2, parcelaReceber.getValorParcela());
        ps.setDate(3, new Date(parcelaReceber.getData().getTime()));
        ps.setString(4, parcelaReceber.getStatus());

        ps.execute();
        con.confirmar();

    }

    public static ObservableList<ParcelaReceber> listar(ContaReceber cr) throws Exception {
        String sql = ""
                + " select *"
                + " from tbparcelareceber as p"
                + " inner join tbcontareceber as c on c.codcontareceber = p.codcontareceber "
                + " where p.codcontareceber=?";

        Conexao con = new Conexao();
        PreparedStatement ps = con.getConexao().prepareStatement(sql);
        ps.setInt(1, cr.getCodigo());
        ResultSet rs = ps.executeQuery();
        ObservableList<ParcelaReceber> lista = FXCollections.observableArrayList();
        while (rs.next()) {
            ParcelaReceber parcelaReceber = new ParcelaReceber();
            parcelaReceber.setContaReceber(cr);
            parcelaReceber.setCodigo(rs.getInt("p.codparcelareceber"));
            parcelaReceber.setData(rs.getDate("p.data"));
            parcelaReceber.setValorParcela(rs.getFloat("p.valorparcela"));
            parcelaReceber.setValorRecebido(RecebimentoDAO.consultarRecebimentoParcela(parcelaReceber.getCodigo()));
            parcelaReceber.setStatus(rs.getString("p.status"));
            lista.add(parcelaReceber);
        }
        return lista;
    }

    public static void alterar(ParcelaReceber parcelaReceber) throws Exception {

        String sql = "update tbcontareceber set codcontareceber=?, data=?, valorparcela=?, status=? where codparcelareceber=?";

        Conexao con = new Conexao();

        PreparedStatement ps = con.getConexao().prepareStatement(sql);
        ps.setInt(1, parcelaReceber.getContaReceber().getCodigo());
        ps.setDate(2, new java.sql.Date(parcelaReceber.getData().getTime()));
        ps.setFloat(3, parcelaReceber.getValorParcela());
        ps.setString(4, parcelaReceber.getStatus());

        ps.setInt(5, parcelaReceber.getCodigo());

        ps.execute();
        con.confirmar();
    }

    public static void salvar(ParcelaReceber parcelaReceber) throws Exception {
        if (parcelaReceber.getCodigo() == 0) {
            inserir(parcelaReceber);
        } else {
            alterar(parcelaReceber);
        }
    }

}
