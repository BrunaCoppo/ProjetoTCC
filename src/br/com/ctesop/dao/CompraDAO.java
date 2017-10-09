
package br.com.ctesop.dao;

import br.com.ctesop.model.Caixa;
import br.com.ctesop.model.Cidade;
import br.com.ctesop.model.Compra;
import java.sql.PreparedStatement;

/**
 *
 * @author Bruna
 */
public class CompraDAO {
    
    
     public static void inserir(Compra compra) throws Exception {

        String sql = "insert into tbcompra (data,total, status) values (?,?,?)";

        Conexao con = new Conexao();

        PreparedStatement ps = con.getConexao().prepareStatement(sql);
        ps.setDate(1,new java.sql.Date(compra.getData().getTime()));
        ps.setFloat(2, compra.getTotal());
        ps.setString(3, compra.getStatus());

        ps.execute();
        con.confirmar();

    }
     
     
    
}
