package br.com.ctesop.control;

/**
 *
 * @author Bruna
 */
import br.com.ctesop.controller.util.Alerta;
import br.com.ctesop.controller.util.ExceptionValidacao;
import br.com.ctesop.dao.FisicaDAO;
import br.com.ctesop.dao.FornecedorDAO;
import br.com.ctesop.dao.JuridicaDAO;
import br.com.ctesop.dao.PessoaDAO;
import br.com.ctesop.model.Fisica;
import br.com.ctesop.model.Fornecedor;
import br.com.ctesop.model.Funcionario;
import br.com.ctesop.model.Juridica;
import br.com.ctesop.model.Pessoa;
import br.com.ctesop.model.Proprietario;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;

public class CadastroPessoaController {
    
    private int codigo;

    @FXML
    private Button btnNovo;

    @FXML
    private Button btnEditar;

    @FXML
    private Button btnSalvar;

    @FXML
    private Button btnCancelar;

    @FXML
    private TextField tfNome;

    @FXML
    private TextField tfEndereco;

    @FXML
    private TextField tfTelefone;

    @FXML
    private TextField tfEmail;

    @FXML
    private RadioButton rbAtivo;

    @FXML
    private ToggleGroup status;

    @FXML
    private RadioButton rbInativo;

    @FXML
    private CheckBox cbFornecedor;

    @FXML
    private CheckBox cbFuncionario;

    @FXML
    private CheckBox cbProprietario;

    @FXML
    private RadioButton rbFisica;

    @FXML
    private ToggleGroup situacao;

    @FXML
    private RadioButton rbJuridica;
        
    @FXML
    private Tab tbFisica;

    @FXML
    private Tab tbJuridica;

    @FXML
    private TableColumn<?, ?> tcNome;

    @FXML
    private TableColumn<?, ?> tcTelefone;

    @FXML
    private TableColumn<?, ?> tcStatus;


    
    @FXML
    void novo(ActionEvent event) {

    }

    @FXML
    void Editar(ActionEvent event) {

    }

    @FXML
    void Salvar(ActionEvent event) {
        try {
            Pessoa pessoa = new Pessoa();
            pessoa.setCodigo(codigo);
            pessoa.setNome(tfNome.getText());
            //Todos os demais dados de pessoa

            if (rbAtivo.isSelected()) {
                pessoa.setStatus("A");
            } else {
                pessoa.setStatus("I");
            }

            if (rbFisica.isSelected()) {
                Fisica fisica = new Fisica();
                fisica.setCodigo(codigoFisica);
                //Demais dados da pessoa fisica
                //Mudar tipo de dado para String e no banco para VARCHAR(20)

                codigoFisica = FisicaDAO.salvar(fisica);
                fisica.setCodigo(codigoFisica);
                
                pessoa.setFisica(fisica);
                pessoa.setJuridica(null);
            } else {
                Juridica juridica = new Juridica();
                juridica.setCodigo(codigoJuridico);
                //Demais dados da pessoa juridica

                codigoJuridico = JuridicaDAO.salvar(juridica);
                juridica.setCodigo(codigoJuridico);
                
                pessoa.setJuridica(juridica);
                pessoa.setFisica(null);
            }

            codigo = PessoaDAO.salvar(pessoa);

            if (cbFornecedor.isSelected()) {
                Fornecedor fornecedor = new Fornecedor();
                fornecedor.setCodigo(codigo);
                FornecedorDAO.salvar(fornecedor);
            } else {
                FornecedorDAO.excluir(fornecedor);
            }

            if (cbProprietario.isSelected()) {
                Proprietario proprietario = new Proprietario();
                proprietario.setCodigo(codigo);
                ProprietarioDAO.salvar(proprietario);
            } else {
                ProprietarioDAO.excluir(proprietario);
            }

            if (cbFuncionario.isSelected()) {
                Funcionario funcionario = new Funcionario();
                funcionario.setCodigo(codigo);
                FuncionarioDAO.salvar(funcionario);
            } else {
                FuncionarioDAO.excluir(funcionario);
            }

            Alerta.sucesso("Pessoa salva com sucesso.");

            atualizarTabela();
            limpar();
            habilitar(false);
        } catch (ExceptionValidacao e) {
            Alerta.alerta("Erro ao salvar.", e);
        } catch (Exception e) {
            Alerta.erro("Erro ao salvar.", e);
        }
    }

    @FXML
    void Cancelar(ActionEvent event) {

    }
    
    @FXML
    void mudarTipo(ActionEvent event) {
        if (rbFisica.isSelected()) {
            tbFisica.setDisable(false);
            tbJuridica.setDisable(true);
        }else{
            tbFisica.setDisable(true);
            tbJuridica.setDisable(false);
        }
    }
    
    
}
