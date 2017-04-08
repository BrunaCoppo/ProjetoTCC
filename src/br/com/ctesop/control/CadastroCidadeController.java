package br.com.ctesop.control;

import br.com.ctesop.controller.util.Alerta;
import br.com.ctesop.controller.util.ExceptionValidacao;
import br.com.ctesop.dao.CidadeDAO;
import br.com.ctesop.dao.EstadoDAO;
import br.com.ctesop.model.Cidade;
import br.com.ctesop.model.Estado;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
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

public class CadastroCidadeController implements Initializable {

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
    private ComboBox<Estado> cbEstado;

    @FXML
    private Button btnCadastroEstado;

    @FXML
    private ToggleGroup tgStatus;

    @FXML
    private TableColumn<Cidade, String> tcCidade;

    @FXML
    private TableColumn<Cidade, String> tcUf;

    @FXML
    private TableColumn<Cidade, String> tcStatus;

    @FXML
    private TableView<Cidade> tbCidade;

    @FXML
    private RadioButton rbAtivo;

    @FXML
    private RadioButton rbInativo;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //Exibir na tabela
        tcCidade.setCellValueFactory(new PropertyValueFactory<>("nome"));
        tcUf.setCellValueFactory(new PropertyValueFactory<>("estado"));
        tcStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        carregarComboEstado();
        atualizarTabela();
        habilitar(false);
    }

    @FXML
    public void novo(ActionEvent event) {
        codigo = 0;
        limpar();
        habilitar(true);
    }

    @FXML
    public void editar(ActionEvent event) {
        if (tbCidade.getSelectionModel().isEmpty()) {
            return;
        }

        Cidade selecionado = tbCidade.getSelectionModel().getSelectedItem();
        codigo = selecionado.getCodigo();
        tfNome.setText(selecionado.getNome());
        cbEstado.getSelectionModel().select(selecionado.getEstado());
        if (selecionado.getStatus().equalsIgnoreCase("A")) {
            rbAtivo.setSelected(true);
        } else {
            rbInativo.setSelected(true);
        }
        habilitar(true);
    }

    @FXML
    public void salvar(ActionEvent event) {
        try {
            Cidade cidade = new Cidade();
            cidade.setCodigo(codigo);
            cidade.setNome(tfNome.getText());
            cidade.setEstado(cbEstado.getSelectionModel().getSelectedItem());

            if (rbAtivo.isSelected()) {
                cidade.setStatus("A");
            } else {
                cidade.setStatus("I");
            }

            CidadeDAO.salvar(cidade);

            Alerta.sucesso("Cidade salva com sucesso.");

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
    public void cancelar(ActionEvent event) {
        habilitar(false);
        limpar();
    }

    @FXML
    public void cadastroEstado(ActionEvent event) {
        try {
            String url = "/br/com/ctesop/view/CadastroEstado.fxml";
            Scene scene = new Scene(new FXMLLoader(getClass().getResource(url)).load());
            Stage stage = new Stage();
            stage.setTitle("Cadastro de Estado");
            stage.setScene(scene);
            stage.show();
            //Carregar o estado na cidade alterado
            stage.setOnHidden(new EventHandler<WindowEvent>() {
                @Override
                public void handle(WindowEvent event) {
                    carregarComboEstado();
                }
            });
        } catch (Exception e) {
            Alerta.erro("Erro ao abrir cadastro de estado.", e);
        }
    }

    private void atualizarTabela() {
        try {
            tbCidade.setItems(CidadeDAO.listar(false));
            tbCidade.refresh();
        } catch (Exception e) {
            Alerta.erro("Erro ao consultar dados.", e);
        }
    }

    private void limpar() {
        tfNome.setText("");
        cbEstado.getSelectionModel().select(0);
        rbAtivo.setSelected(true);
    }

    private void habilitar(boolean habilitar) {
        btnNovo.setDisable(habilitar);
        btnEditar.setDisable(habilitar);
        btnSalvar.setDisable(!habilitar);
        btnCancelar.setDisable(!habilitar);
        tfNome.setDisable(!habilitar);
        cbEstado.setDisable(!habilitar);
        btnCadastroEstado.setDisable(!habilitar);
        rbAtivo.setDisable(!habilitar);
        rbInativo.setDisable(!habilitar);
    }

    private void carregarComboEstado() {
        try {
            cbEstado.setItems(EstadoDAO.listar(true));
        } catch (Exception e) {
            Alerta.erro("Erro ao consultar dados.", e);
        }
    }
}
