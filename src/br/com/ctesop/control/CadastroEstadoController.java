
package br.com.ctesop.control;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;

public class CadastroEstadoController implements Initializable {

    
    @FXML
    private TextField nomeEstado;

    @FXML
    private TextField siglaEstado;

    @FXML
    private ToggleGroup Status;
    

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    } 
    
        @FXML
    void editar(ActionEvent event) {

    }

    @FXML
    void novo(ActionEvent event) {

    }

    @FXML
    void salvar(ActionEvent event) {

    }
    
}
