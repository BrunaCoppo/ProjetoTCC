package br.com.ctesop.control;

/**
 *
 * @author Bruna
 */
import br.com.ctesop.controller.util.Alerta;
import br.com.ctesop.controller.util.Converter;
import br.com.ctesop.controller.util.ExceptionValidacao;
import br.com.ctesop.dao.CidadeDAO;
import br.com.ctesop.dao.FisicaDAO;
import br.com.ctesop.dao.FornecedorDAO;
import br.com.ctesop.dao.FuncionarioDAO;
import br.com.ctesop.dao.JuridicaDAO;
import br.com.ctesop.dao.PessoaDAO;
import br.com.ctesop.dao.ProprietarioDAO;
import br.com.ctesop.model.Cidade;
import br.com.ctesop.model.Fisica;
import br.com.ctesop.model.Fornecedor;
import br.com.ctesop.model.Funcionario;
import br.com.ctesop.model.Juridica;
import br.com.ctesop.model.Pessoa;
import br.com.ctesop.model.Proprietario;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class CadastroPessoaController implements Initializable {

    private int codigo;
    private int codigoFisica;
    private int codigoJuridica;

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
    private TextField tfCPF;

    @FXML
    private TextField tfRG;

    @FXML
    private TextField tfIE;

    @FXML
    private DatePicker dpDataNascimento;

    @FXML
    private TextField tfCNPJ;

    @FXML
    private TextField tfRazaoSocial;

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
    private ComboBox<Cidade> cbCidade;

    @FXML
    private Button btnCadastrarCidade;

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
    private TableView<Pessoa> tbPessoa;

    @FXML
    private TableColumn<Pessoa, String> tcNome;

    @FXML
    private TableColumn<Pessoa, String> tcTelefone;

    @FXML
    private TableColumn<Pessoa, String> tcStatus;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        tcNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        tcTelefone.setCellValueFactory(new PropertyValueFactory<>("telefone"));
        tcStatus.setCellValueFactory(new PropertyValueFactory<>("status"));

        carregarComboCidade();
        atualizarTabela();
        habilitar(false);
    }

    private void atualizarTabela() {
        try {
            tbPessoa.setItems(PessoaDAO.listar(false));
            tbPessoa.refresh();
        } catch (Exception e) {
            Alerta.erro("Erro ao consultar dados.", e);

        }
    }

    @FXML
    void novo(ActionEvent event) {
        codigo = 0;
        limpar();
        habilitar(true);
    }

    @FXML
    void editar(ActionEvent event) {
        if (tbPessoa.getSelectionModel().isEmpty()) {
            return;
        }
        try {
            Pessoa selecionado = tbPessoa.getSelectionModel().getSelectedItem();
            codigo = selecionado.getCodigo();
            tfNome.setText(selecionado.getNome());
            tfEmail.setText(selecionado.getEmail());
            tfEndereco.setText(selecionado.getEndereco());
            tfNome.setText(selecionado.getNome());
            tfTelefone.setText(selecionado.getTelefone());
            cbCidade.getSelectionModel().select(selecionado.getCidade());
            dpDataNascimento.setValue(Converter.toLocalDate(selecionado.getDatacadastro()));
            if (selecionado.getStatus().equalsIgnoreCase("A")) {
                rbAtivo.setSelected(true);
            } else {
                rbInativo.setSelected(true);
            }

            if (selecionado.getFisica() != null) {
                codigoJuridica = 0;
                codigoFisica = selecionado.getFisica().getCodigo();
                tfCPF.setText(selecionado.getFisica().getCpf());
                tfRG.setText(selecionado.getFisica().getRg());
                tfIE.setText(selecionado.getFisica().getIe());
                dpDataNascimento.setValue(Converter.toLocalDate(selecionado.getFisica().getDataNascimento()));
                rbFisica.setSelected(true);
                mudarTipo(null);
            }
            if (selecionado.getJuridica() != null) {
                codigoFisica = 0;
                codigoJuridica = selecionado.getJuridica().getCodigo();
                tfCNPJ.setText(selecionado.getJuridica().getCnpj());
                tfIE.setText(selecionado.getJuridica().getIe());
                tfRazaoSocial.setText(selecionado.getJuridica().getRazaoSocial());
                rbJuridica.setSelected(true);
                mudarTipo(null);
            }

           if (ProprietarioDAO.existe(new Proprietario(selecionado.getCodigo()))) {
                cbProprietario.setSelected(true);
            } else {
                cbProprietario.setSelected(false);
            }
            
            if (FuncionarioDAO.existe(new Funcionario(selecionado.getCodigo()))) {
                cbFuncionario.setSelected(true);
            } else {
                cbFuncionario.setSelected(false);
            }
            
            if (FornecedorDAO.existe(new Fornecedor(selecionado.getCodigo()))) {
                cbFornecedor.setSelected(true);
            } else {
                cbFornecedor.setSelected(false);
            }
            habilitar(true);
            
        } catch (Exception e) {
            Alerta.erro("Erro ao carregar dados para edição.", e);
            e.printStackTrace();
        }
    }

    @FXML
    void salvar(ActionEvent event) {

        try {
            Pessoa pessoa = new Pessoa();
            pessoa.setCodigo(codigo);
            pessoa.setNome(tfNome.getText());
            pessoa.setEmail(tfEmail.getText());
            pessoa.setTelefone(tfTelefone.getText());
            pessoa.setEndereco(tfEndereco.getText());
            pessoa.setCidade(cbCidade.getSelectionModel().getSelectedItem());

            if (rbAtivo.isSelected()) {
                pessoa.setStatus("A");
            } else {
                pessoa.setStatus("I");
            }

            if (rbFisica.isSelected()) {
                Fisica fisica = new Fisica();
                fisica.setCodigo(codigoFisica);
                fisica.setCpf(tfCPF.getText());
                fisica.setIe(tfIE.getText());
                fisica.setRg(tfRG.getText());
                fisica.setDataNascimento(Converter.converterData(dpDataNascimento.getValue()));

                codigoFisica = FisicaDAO.salvar(fisica);
                fisica.setCodigo(codigoFisica);

                pessoa.setFisica(fisica);
                pessoa.setJuridica(null);
            } else {
                Juridica juridica = new Juridica();
                juridica.setCodigo(codigoJuridica);
                juridica.setCnpj(tfCNPJ.getText());
                juridica.setIe(tfIE.getText());
                juridica.setRazaoSocial(tfRazaoSocial.getText());

                codigoJuridica = JuridicaDAO.salvar(juridica);
                juridica.setCodigo(codigoJuridica);

                pessoa.setJuridica(juridica);
                pessoa.setFisica(null);
            }

            codigo = PessoaDAO.salvar(pessoa);

            Fornecedor fornecedor = new Fornecedor();
            fornecedor.setCodigo(codigo);
            if (cbFornecedor.isSelected()) {
                FornecedorDAO.salvar(fornecedor);
            } else {
                FornecedorDAO.excluir(fornecedor);
            }

            Proprietario proprietario = new Proprietario();
            proprietario.setCodigo(codigo);
            if (cbProprietario.isSelected()) {
                ProprietarioDAO.salvar(proprietario);
            } else {
                ProprietarioDAO.excluir(proprietario);
            }

            Funcionario funcionario = new Funcionario();
            funcionario.setCodigo(codigo);
            if (cbFuncionario.isSelected()) {
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
            e.printStackTrace();
        }
    }

    @FXML
    void cancelar(ActionEvent event) {
        habilitar(false);
        limpar();
    }

    private void limpar() {
        tfCNPJ.setText("");
        tfCPF.setText("");
        tfIE.setText("");
        tfEmail.setText("");
        tfEndereco.setText("");
        tfNome.setText("");
        tfRG.setText("");
        tfRazaoSocial.setText("");
        tfTelefone.setText("");
        cbCidade.getSelectionModel().select(0);
        dpDataNascimento.setValue(null);
        rbAtivo.setSelected(true);
        rbFisica.setSelected(true);
        mudarTipo(null);
    }

    private void habilitar(boolean habilitar) {
        btnNovo.setDisable(habilitar);
        btnEditar.setDisable(habilitar);
        btnSalvar.setDisable(!habilitar);
        btnCancelar.setDisable(!habilitar);
        tfCNPJ.setDisable(!habilitar);
        tfCPF.setDisable(!habilitar);
        tfIE.setDisable(!habilitar);
        dpDataNascimento.setDisable(!habilitar);
        tfEndereco.setDisable(!habilitar);
        tfNome.setDisable(!habilitar);
        tfRG.setDisable(!habilitar);
        tfRazaoSocial.setDisable(!habilitar);
        tfTelefone.setDisable(!habilitar);
        tfEmail.setDisable(!habilitar);
        cbCidade.setDisable(!habilitar);
        btnCadastrarCidade.setDisable(!habilitar);
        rbAtivo.setDisable(!habilitar);
        rbInativo.setDisable(!habilitar);

        cbFornecedor.setDisable(!habilitar);
        cbFuncionario.setDisable(!habilitar);
        cbProprietario.setDisable(!habilitar);

        rbFisica.setDisable(!habilitar);
        rbJuridica.setDisable(!habilitar);
    }

    @FXML
    void mudarTipo(ActionEvent event) {
        if (rbFisica.isSelected()) {
            tbFisica.setDisable(false);
            tbJuridica.setDisable(true);
        } else {
            tbFisica.setDisable(true);
            tbJuridica.setDisable(false);
        }
    }

    private void carregarComboCidade() {
        try {
            cbCidade.setItems(CidadeDAO.listar(true));
        } catch (Exception e) {
            Alerta.erro("Erro ao consultar dados.", e);
        }
    }

    @FXML
    void cadastroCidade(ActionEvent event) {
        try {
            String url = "/br/com/ctesop/view/CadastroCidade.fxml";
            Scene scene = new Scene(new FXMLLoader(getClass().getResource(url)).load());
            Stage stage = new Stage();
            stage.setTitle("Cadastro de Cidade");
            stage.setScene(scene);
            stage.show();
            stage.setOnHidden(new EventHandler<WindowEvent>() {
                @Override
                public void handle(WindowEvent event) {
                    carregarComboCidade();
                }
            });
        } catch (Exception e) {
            Alerta.erro("Erro ao abrir cadastro de cidade.", e);
        }
    }

    public void setTipo(String tipo) {
        if (tipo.equalsIgnoreCase("Funcionario")) {
            cbFuncionario.setSelected(true);
        }
        if (tipo.equalsIgnoreCase("Fornecedor")) {
            cbFornecedor.setSelected(true);
        }
        if (tipo.equalsIgnoreCase("Proprietario")) {
            cbProprietario.setSelected(true);
        }
    }

}
