
package br.com.ctesop.dao;

import br.com.ctesop.controller.util.ExceptionValidacao;
import br.com.ctesop.model.Cidade;
import br.com.ctesop.model.Pessoa;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Types;
import java.util.Date;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author Bruna
 */
public class PessoaDAO {

    public static int inserir(Pessoa pessoa) throws Exception {

        String sql = "insert into tbpessoa (" + (pessoa.getFisica() != null ? "codfisica" : "codjuridica") + ",codcidade, nomepessoa, endereco, telefone, datacadastro, email, status) values (?,?,?,?,?,?,?,?)";

        Conexao con = new Conexao();

        PreparedStatement ps = con.getConexao().prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);

        pessoa.setDatacadastro(new Date());

        if (pessoa.getFisica() != null) {
            ps.setInt(1, pessoa.getFisica().getCodigo());
        } else {
            ps.setInt(1, pessoa.getJuridica().getCodigo());
        }

        ps.setInt(2, pessoa.getCidade().getCodigo());
        ps.setString(3, pessoa.getNome());
        ps.setString(4, pessoa.getEndereco());
        ps.setString(5, pessoa.getTelefone());
        ps.setDate(6, new java.sql.Date(pessoa.getDatacadastro().getTime()));
        ps.setString(7, pessoa.getEmail());
        ps.setString(8, pessoa.getStatus());

        ps.execute();

        int codGerado = 0;

        ResultSet rs = ps.getGeneratedKeys();
        if (rs.next()) {
            codGerado = rs.getInt(1);
        }
        con.confirmar();
        return codGerado;
    }

    public static void alterar(Pessoa pessoa) throws Exception {

        String sql = "update tbpessoa set codfisica=?, codjuridica=?, codcidade=?, nomepessoa=?, ";
        sql += "endereco=?, telefone=?, email=?, status=? where codpessoa=?";

        Conexao con = new Conexao();

        PreparedStatement ps = con.getConexao().prepareStatement(sql);
        if (pessoa.getFisica() != null) {
            ps.setInt(1, pessoa.getFisica().getCodigo());
            ps.setNull(2, Types.INTEGER);
        } else {
            ps.setNull(1, Types.INTEGER);
            ps.setInt(2, pessoa.getJuridica().getCodigo());
        }

        ps.setInt(3, pessoa.getCidade().getCodigo());
        ps.setString(4, pessoa.getNome());
        ps.setString(5, pessoa.getEndereco());
        ps.setString(6, pessoa.getTelefone());
        ps.setString(7, pessoa.getEmail());
        ps.setString(8, pessoa.getStatus());

        ps.setInt(9, pessoa.getCodigo());

        ps.execute();
        con.confirmar();
    }

    public static int salvar(Pessoa pessoa) throws Exception {
        if (pessoa.getCodigo() == 0) {
            if (existe(pessoa)) {
                throw new ExceptionValidacao("A pessoa já está cadastrada.");
            }
            return inserir(pessoa);
        } else {
            alterar(pessoa);
            return pessoa.getCodigo();
        }
    }

    private static boolean existe(Pessoa pessoa) throws Exception {
        String sql = "select count(codpessoa) from tbpessoa where nomepessoa=? or endereco=?";
        Conexao c = new Conexao();
        PreparedStatement ps = c.getConexao().prepareStatement(sql);
        ps.setString(1, pessoa.getNome());
        ps.setString(2, pessoa.getEndereco());
        ResultSet rs = ps.executeQuery();
        rs.next();
        return (rs.getInt(1) > 0);
    }

    public static ObservableList<Pessoa> listar(boolean somenteAtivos) throws Exception {

        String sql = "select * from tbpessoa ";
        if (somenteAtivos) {
            sql += " where tbpessoa.status='A' ";
        }
        sql += " order by nomepessoa";
        Conexao con = new Conexao();
        PreparedStatement ps = con.getConexao().prepareStatement(sql);

        ResultSet rs = ps.executeQuery();
        ObservableList<Pessoa> lista = FXCollections.observableArrayList();

        while (rs.next()) {
            Pessoa pessoa = new Pessoa();
            pessoa.setCodigo(rs.getInt("codpessoa"));
            pessoa.setNome(rs.getString("nomepessoa"));
            pessoa.setCidade(new Cidade(rs.getInt("codcidade")));
            pessoa.setEndereco(rs.getString("endereco"));
            pessoa.setTelefone(rs.getString("telefone"));
            pessoa.setEmail(rs.getString("email"));            
            pessoa.setStatus(rs.getString("status"));
            pessoa.setFisica(FisicaDAO.get(rs.getInt("codfisica")));
            pessoa.setJuridica(JuridicaDAO.get(rs.getInt("codjuridica")));
                                    
            lista.add(pessoa);

        }
        return lista;
    }
}
