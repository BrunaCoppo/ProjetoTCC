package br.com.ctesop.dao;

import br.com.ctesop.model.Compra;
import br.com.ctesop.model.ContaPagar;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author Bruna
 */
public class ContaPagarDAO {

    public static void inserir(ContaPagar contaPagar, Conexao c) throws Exception {

        String sql = "insert into tbcontapagar (codcompra, datavencimento, descricao, valor, status) values (?,?,?,?,?)";
       
        PreparedStatement ps = c.getConexao().prepareStatement(sql);
        ps.setInt(1, contaPagar.getCompra().getCodigo());
        ps.setDate(2, new java.sql.Date(contaPagar.getData().getTime()));
        ps.setString(3, contaPagar.getDescricao());
        ps.setFloat(4, contaPagar.getValor());
        ps.setString(5, contaPagar.getStatus());

        ps.execute();
        c.confirmar();

    }

    public static ObservableList<ContaPagar> listar(boolean somenteAtivos) throws Exception {
        String sql = ""
                + " select *"
                + " from tbcontapagar as cont"
                + " inner join tbcompra as com on com.codcompra = cont.codcompra";
        if (somenteAtivos) {
            sql += " where cont.status='A' ";
        }
        sql += " order by cont.status, cont.datavencimento";

        Conexao con = new Conexao();
        PreparedStatement ps = con.getConexao().prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        ObservableList<ContaPagar> lista = FXCollections.observableArrayList();
        while (rs.next()) {
            ContaPagar contaPagar = new ContaPagar();
            contaPagar.setCodigo(rs.getInt("cont.codcontapagar"));
//Ver como vai ser exibido a compra
//contaPagar.setCompra(new Compra(rs.getInt("com.codcompra"))));
            contaPagar.setData(rs.getDate("cont.datavencimento"));
            contaPagar.setDescricao(rs.getString("cont.descricao"));
            contaPagar.setStatus(rs.getString("cont.status"));
            lista.add(contaPagar);
        }
        return lista;
    }

    public static void alterar(ContaPagar contaPagar, Conexao c) throws Exception {

        String sql = "update tbcontapagar set codcompra=?, datavencimento=?, descricao=?, valor=?, status=? where codcontapagar=?";


        PreparedStatement ps = c.getConexao().prepareStatement(sql);
        ps.setInt(1, contaPagar.getCompra().getCodigo());
        ps.setDate(2,new java.sql.Date(contaPagar.getData().getTime())) ;
        ps.setString(3, contaPagar.getDescricao());
        ps.setFloat(4, contaPagar.getValor());
        ps.setString(5, contaPagar.getStatus());

        ps.setInt(6, contaPagar.getCodigo());

        ps.execute();
        c.confirmar();
    }

    public static void salvar(ContaPagar contaPagar, Conexao c) throws Exception {
        if (contaPagar.getCodigo() == 0) {
            
            inserir(contaPagar, c);
        } else {
            alterar(contaPagar, c);
        }
    }


}
