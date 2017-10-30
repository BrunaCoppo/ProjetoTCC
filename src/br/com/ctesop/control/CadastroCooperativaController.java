package br.com.ctesop.control;

import br.com.ctesop.controller.util.Alerta;
import br.com.ctesop.controller.util.ExceptionValidacao;
import br.com.ctesop.dao.CidadeDAO;
import br.com.ctesop.dao.CooperativaDAO;
import br.com.ctesop.model.Cidade;
import br.com.ctesop.model.Cooperativa;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 * FXML Controller class
 *
 * @author Bruna
 */
public class CadastroCooperativaController implements Initializable {

    private int codigo=0;
    @FXML
    private TextField tfNome;

    @FXML
    private ComboBox<Cidade> cbCidade;

    @FXML
    private TextField tfEndereco;

    @FXML
    private RadioButton rbAtivo;

    @FXML
    private ToggleGroup status;

    @FXML
    private RadioButton rbInativo;

    @FXML
    private Button btnNovo;

    @FXML
    private Button btnEditar;

    @FXML
    private Button btnSalvar;

    @FXML
    private Button btnCancelar;

    @FXML
    private TableView<Cooperativa> tbCooperativa;

    @FXML
    private TableColumn<Cooperativa, String> tcNome;

    @FXML
    private TableColumn<Cooperativa, String> tcCidade;


    @FXML
    private TableColumn<Cooperativa, String> tcStatus;

    @FXML
    private Button btnCadastrarCidade;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        tcNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        tcCidade.setCellValueFactory(new PropertyValueFactory<>("cidade"));
        tcStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        
        carregarComboCidade();
        atualizarTabela();
        habilitar(false);
    }

    @FXML
    void novo(ActionEvent event) {
        codigo = 0;
        limpar();
        habilitar(true);
    }

    @FXML
    void editar(ActionEvent event) {
        if (tbCooperativa.getSelectionModel().isEmpty()) {
            return;
        }

        Cooperativa selecionado = tbCooperativa.getSelectionModel().getSelectedItem();
        codigo = selecionado.getCodigo();
        tfNome.setText(selecionado.getNome());
        cbCidade.getSelectionModel().select(selecionado.getCidade());
        tfEndereco.setText(selecionado.getNome());
        if (selecionado.getStatus().equalsIgnoreCase("A")) {
            rbAtivo.setSelected(true);
        } else {
            rbInativo.setSelected(true);
        }
        habilitar(true);

    }

    @FXML
    void salvar(ActionEvent event) {
        try {
            Cooperativa cooperativa = new Cooperativa();
            cooperativa.setCodigo(codigo);
            cooperativa.setNome(tfNome.getText());
            cooperativa.setCidade(cbCidade.getSelectionModel().getSelectedItem());
            cooperativa.setEndereco(tfEndereco.getText());

            if (rbAtivo.isSelected()) {
                cooperativa.setStatus("A");
            } else {
                cooperativa.setStatus("I");
            }

            CooperativaDAO.salvar(cooperativa);

            Alerta.sucesso("Cooperativa salva com sucesso.");

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
    void cancelar(ActionEvent event) {
        habilitar(false);
        limpar();
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
            //Carregar o estado na cidade alterado
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

    private void atualizarTabela() {
        try {
            tbCooperativa.setItems(CooperativaDAO.listar(false));
            tbCooperativa.refresh();
        } catch (Exception e) {
            Alerta.erro("Erro ao consultar dados.", e);
            e.printStackTrace();
        }

    }

    private void limpar() {
        tfNome.setText("");
        cbCidade.getSelectionModel().select(0);
        tfEndereco.setText("");
        rbAtivo.setSelected(true);

    }

    private void habilitar(boolean habilitar) {
        btnNovo.setDisable(habilitar);
        btnEditar.setDisable(habilitar);
        btnSalvar.setDisable(!habilitar);
        btnCancelar.setDisable(!habilitar);
        tfNome.setDisable(!habilitar);
        cbCidade.setDisable(!habilitar);
        btnCadastrarCidade.setDisable(!habilitar);
        tfEndereco.setDisable(!habilitar);
        rbAtivo.setDisable(!habilitar);
        rbInativo.setDisable(!habilitar);
    }

    private void carregarComboCidade() {
        try {
            cbCidade.setItems(CidadeDAO.listar(true));
        } catch (Exception e) {
            Alerta.erro("Erro ao consultar dados.", e);
            
        }
    }

}
