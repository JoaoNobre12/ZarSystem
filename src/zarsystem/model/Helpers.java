package zarsystem.model;

import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import zarsystem.view.blur.Blur;

import java.io.IOException;
import java.sql.Date;
import java.text.*;

/**
 * Created by joao on 09/09/16.
 * Ajuda com conversão de strings
 * tarefas ao iniciar
 */
public class Helpers{

    public static boolean verifyDate(String date){
        try {
            SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
            new Date(format.parse(date).getTime());
            return true;
        }catch (ParseException e){
            e.printStackTrace();
            return false;
        }
    }

    public static Date parseSQLDate(String dt){
        try {
            SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
            //java.sql.Date data = new java.sql.Date(format.parse(dt).getTime());

            return new Date(format.parse(dt).getTime());
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String parseStringDate(Date date) throws ParseException{

        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

        String stringDate = dateFormat.format(date);

        return String.valueOf(stringDate);
    }

    /**
     * Abstrai algumas funções da inicialização do menu principal, para limpar o initialize()
     * */
    public void initializes(Label lblTime, Pane frame, ComboBox<String> cbBoxPlano,
                                ComboBox<String> cbPlanoAluno, ComboBox<String> cbBoxDia, ObservableList<String> cbList, ObservableList<String> cbListDia){

        frame.setStyle("-fx-padding: 0px 0px 0px 0px;" +
                "-fx-background-radius: 0;"); //sem border porque inicia maximizado
        cbBoxPlano.setItems(cbList); //plano
        cbPlanoAluno.setItems(cbList);
        cbBoxDia.setItems(cbListDia);

        //relógio
        Clock.bindToTime(lblTime);
    }

    public void initPopUp(String paneName, Parent parent) throws IOException{

        //borrar fundo
        Blur.blurParent(parent);

        String url = "/zarsystem/view/popup/" + paneName;

        Stage popUp = new Stage();
        popUp.initModality(Modality.APPLICATION_MODAL);
        popUp.initStyle(StageStyle.TRANSPARENT);
        Scene scene = new Scene(FXMLLoader.load(getClass().getResource(url)));
        scene.setFill(null);
        popUp.setScene(scene);
        popUp.show();

        popUp.setOnHiding(event -> Blur.unblurParent(parent));
    }

    /*--------------------------------------Mascaras-------------------------------------------------------------*/

    /**
     * Posiciona cursor do text field no final
     * */
    public static void positionCaretAtEnd(TextField textField){
        textField.positionCaret(textField.getText().length());
    }

    /**
     * Posiciona cursor do datePicker no final
     * */
    public static void positionCaretAtEnd(DatePicker dateField){
        dateField.getEditor().positionCaret(dateField.getEditor().getText().length());
    }

    /**
     * Mascara o DateField de Data
     * */
    public static void maskDate(TextField dateField){
        dateField.setOnKeyTyped((KeyEvent event) -> {
            if(!("0123456789".contains(event.getCharacter()))){
                event.consume();
            }
            else { //escrevendo
                if(dateField.getText().length() == 10){
                    event.consume();
                }
                if(dateField.getText().length() == 2){
                    dateField.setText(dateField.getText() + "/");
                    Helpers.positionCaretAtEnd(dateField);
                }
                if(dateField.getText().length()==5){
                    dateField.setText(dateField.getText()+"/");
                    Helpers.positionCaretAtEnd(dateField);
                }
            }

        });
    }

    /**
     * Mascara o DateField de Data
     * */
    public static void maskDate(DatePicker dateField){

        dateField.getEditor().setOnKeyTyped((KeyEvent event) -> {
            if(!("0123456789".contains(event.getCharacter()))){
                event.consume();
            }
            else { //escrevendo
                if(dateField.getEditor().getText().length() == 10){
                    event.consume();
                }
                if(dateField.getEditor().getText().length() == 2){
                    dateField.getEditor().setText(dateField.getEditor().getText() + "/");
                    Helpers.positionCaretAtEnd(dateField);
                }
                if(dateField.getEditor().getText().length()==5){
                    dateField.getEditor().setText(dateField.getEditor().getText()+"/");
                    Helpers.positionCaretAtEnd(dateField);
                }
            }

        });
    }

    /**
     * Mascara o TextField de Rg
     * */
    public static void maskRg(TextField textField){ //54.265.258-8
        textField.addEventHandler(KeyEvent.KEY_TYPED, keyEvent -> {
        if(!("0123456789x".contains(keyEvent.getCharacter())))
            keyEvent.consume();
        else {
            if(textField.getText().length() == 12)
                keyEvent.consume();
            if(textField.getText().length() == 2){
                textField.setText(textField.getText() + ".");
                Helpers.positionCaretAtEnd(textField);
            }
            if(textField.getText().length() == 6){
                textField.setText(textField.getText() + ".");
                Helpers.positionCaretAtEnd(textField);
            }
            if(textField.getText().length() == 10){
                textField.setText(textField.getText() + "-");
                Helpers.positionCaretAtEnd(textField);
            }
        }
        });
    }

    /**
     * Mascara o TextField de Cpf
     * */
    public static void maskCpf(TextField textField){
        textField.addEventHandler(KeyEvent.KEY_TYPED, keyEvent -> {
            if(!("0123456789".contains(keyEvent.getCharacter())))
                keyEvent.consume();

            else {
                if(textField.getText().length() == 14)
                    keyEvent.consume();
                if(textField.getText().length() == 3){
                    textField.setText(textField.getText() + ".");
                    Helpers.positionCaretAtEnd(textField);
                }
                if(textField.getText().length() == 7){
                    textField.setText(textField.getText() + ".");
                    Helpers.positionCaretAtEnd(textField);
                }
                if(textField.getText().length() == 11){
                    textField.setText(textField.getText() + "-");
                    Helpers.positionCaretAtEnd(textField);
                }
            }

        });

    }
    /**
     * Mascara para inserção de números, não permite letras
     * @param textField campo que será mascarado
     * */
    public static void maskToNumber(TextField textField){
        textField.addEventHandler(KeyEvent.KEY_TYPED, keyEvent -> {
            if(!("0123456789".contains(keyEvent.getCharacter())))
                keyEvent.consume();
        });
    }

    /**
     * Calcula lucro estimado
     * @param valorEntrada valor de entrada do produto
     * @param valorRevenda valor que o produto será revendido;
     * @param qtdProduto quantidade do produto à entrar no estoque
     * @return lucro estimado do produto
     * */
    public static double lucroEstimado(double valorEntrada, double valorRevenda, int qtdProduto){
        return (valorRevenda - valorEntrada) * qtdProduto;
    }

    public static String calcImc(double peso, double altura){
        NumberFormat formatter = new DecimalFormat("#0.00");

        double imc = peso / (Math.pow(altura, 2));

        String result = String.valueOf(formatter.format(imc));

        if (imc < 18.5){
            result += " - Abaixo do peso.";
        }
        else if(imc >= 18.5 && imc <= 24.9){
            result += " - Peso ideal.";
        }
        else if(imc >= 25.0 && imc <= 29.9){
            result += " - Levemente acima do peso.";
        }
        else if(imc >= 30.0 && imc <= 34.9){
            result += " - Obesidade grau I.";
        }
        else if(imc >= 35.0 && imc <= 39.9){
            result += " - Obesidade grau II (Severa).";
        }
        else {
            result += " - Obesidade grau III (Mórbida).";
        }

        return result;
    }

    /**
     * Limpar múltiplos campos de texto
     * */
    public static void clearTextFields(TextField... textFields){
        for (TextField txtField : textFields){
            txtField.setText("");
        }
    }
    /**
     * Limpar datepicker
     * */
    public static void clearDatePicker(DatePicker datePicker){
        datePicker.getEditor().setText("");
    }


}
