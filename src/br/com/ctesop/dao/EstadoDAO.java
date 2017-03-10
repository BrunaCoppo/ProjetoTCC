package br.com.ctesop.dao;

import br.com.ctesop.model.Estado;
import java.sql.PreparedStatement;


public class EstadoDAO {
    
     public static void inserir(Estado estado) throws Exception {
     
        String sql = "insert into tbestado (nome, sigla) values (?,?)";
       
        Conexao con = new Conexao();

        PreparedStatement ps = con.getConexao().prepareStatement(sql);
        ps.setString(1, estado.get);
        ps.setFloat(2, lancamento.getValor());
        ps.setDate(3, new java.sql.Date(lancamento.getData().getTime()));
        ps.setString(4, lancamento.getTipo().getSigla());

        ps.execute();
        con.confirmar();
     
     }
    


}
