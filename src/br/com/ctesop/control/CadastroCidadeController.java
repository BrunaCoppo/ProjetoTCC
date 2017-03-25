package br.com.ctesop.control;

import br.com.ctesop.controller.util.Alerta;
import br.com.ctesop.dao.CidadeDAO;
import br.com.ctesop.model.Cidade;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;

public class CadastroCidadeController implements Initializable {

    private int codigo;

    @FXML
    private TextField tfNome;

    @FXML
    private ComboBox<Cidade> cbSigla;

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
    void btEditar(ActionEvent event) {

    }

    @FXML
    void btNovo(ActionEvent event) {

    }

    @FXML
    void btSalvar(ActionEvent event) {
        try {

            Cidade cidade = new Cidade();
            cidade.setCodigo(codigo);
            cidade.setNome(tfNome.getText());
            //cidade.setEstado();

            if (rbAtivo.isSelected()) {
                cidade.setStatus("A");
            } else {
                cidade.setStatus("I");
            }

            if (codigo == 0) {
                CidadeDAO.inserir(cidade);
            } else {
                CidadeDAO.alterar(cidade);
            }

            Alerta.sucesso("Cidade salva com sucesso.");

            atualizarTabela();

        } catch (Exception e) {      
            Alerta.erro("Erro ao salvar.", e);
        }

    }

    private void atualizarTabela() {
        try {
            tbCidade.setItems(CidadeDAO.listar());
        } catch (Exception e) {
            Alerta.erro("Erro ao consultar dados.", e);
        }
    }

  

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

}
