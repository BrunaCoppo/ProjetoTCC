package br.com.ctesop.dao;

import br.com.ctesop.controller.util.ExceptionValidacao;
import br.com.ctesop.model.Cidade;
import br.com.ctesop.model.ContaPagar;
import br.com.ctesop.model.Estado;
import br.com.ctesop.model.ParcelaPagar;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author Bruna
 */
public class ParcelaPagarDAO {

    public static void inserir(ParcelaPagar parcelaPagar) throws Exception {

        String sql = "insert into tbparcelapagar (codcontapagar, data, valorparcela, status) values (?,?,?,?)";

        Conexao con = new Conexao();

        PreparedStatement ps = con.getConexao().prepareStatement(sql);
        ps.setInt(1, parcelaPagar.getContaPagar().getCodigo());
        ps.setDate(2, new Date(parcelaPagar.getData().getTime()));
        ps.setFloat(3, parcelaPagar.getValorParcela());
        ps.setString(3, parcelaPagar.getStatus());

        ps.execute();
        con.confirmar();

    }

    public static ObservableList<ParcelaPagar> listar(ContaPagar cp) throws Exception {
        String sql = ""
                + " select *"
                + " from tbparcelapagar as p"
                + " inner join tbcontapagar as c on c.codcontapagar = p.codcontapagar "
                + " where p.codcontapagar=?";

        Conexao con = new Conexao();
        PreparedStatement ps = con.getConexao().prepareStatement(sql);
        ps.setInt(1, cp.getCodigo());
        ResultSet rs = ps.executeQuery();
        ObservableList<ParcelaPagar> lista = FXCollections.observableArrayList();
        while (rs.next()) {
            ParcelaPagar parcelaPagar = new ParcelaPagar();
            parcelaPagar.setContaPagar(cp);
            parcelaPagar.setCodigo(rs.getInt("p.codparcelapagar"));
            parcelaPagar.setData(rs.getDate("p.data"));
            parcelaPagar.setValorParcela(rs.getFloat("p.valorparcela"));
            parcelaPagar.setValorPago(PagamentoDAO.consultarPagamentosParcela(parcelaPagar.getCodigo()));
            parcelaPagar.setStatus(rs.getString("p.status"));
            lista.add(parcelaPagar);
        }
        return lista;
    }

    public static void alterar(ParcelaPagar parcelaPagar) throws Exception {

        String sql = "update tbcontapagar set codcontapagar=?, data=?, valorparcela=?, status=? where codparcelapagar=?";

        Conexao con = new Conexao();

        PreparedStatement ps = con.getConexao().prepareStatement(sql);
        ps.setInt(1, parcelaPagar.getContaPagar().getCodigo());
        ps.setDate(2, new java.sql.Date(parcelaPagar.getData().getTime()));
        ps.setFloat(3, parcelaPagar.getValorParcela());
        ps.setString(4, parcelaPagar.getStatus());

        ps.setInt(5, parcelaPagar.getCodigo());

        ps.execute();
        con.confirmar();
    }

    public static void salvar(ParcelaPagar parcelaPagar) throws Exception {
        if (parcelaPagar.getCodigo() == 0) {
            inserir(parcelaPagar);
        } else {
            alterar(parcelaPagar);
        }
    }

}
