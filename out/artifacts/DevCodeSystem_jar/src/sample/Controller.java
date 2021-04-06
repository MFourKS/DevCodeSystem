package sample;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.ResourceBundle;
import java.io.*;

public class Controller {

    public int count = 0;
    FXMLLoader loader = new FXMLLoader();
    Stage stage = new Stage();
    public String strCode = "";
    int value = 0;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ChoiceBox<String> metCode;

    @FXML
    private SplitMenuButton codeDecode;

    @FXML
    private MenuItem coder;

    @FXML
    private TextArea textInput;

    @FXML
    private TextArea textOutput;

    @FXML
    private Button openCode;

    @FXML
    private Button openLog;

    @FXML
    private Button clearLogs;

    @FXML
    private Button help;

    @FXML
    void initialize() {
       System.gc();

        metCode.valueProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {

                if ( metCode.getValue().equals("Метод 'Стопки книг'")) value = 1;
                else if ( metCode.getValue().equals("Преобразование Барроуза — Уилера")) value = 2;

            }
//            public void comboAction() {
//               if (metCode.getValue() == metCode.getValue());
//            }
        });
//-------------------------------------------------------------------------------------------------
        coder.setOnAction(actionEvent -> {
        if ( value == 1 ) {


            String[] lib;
            String[] str;
            String text = "";
            String t = textInput.getText();
            String[] code = preCode(t);

            int k = 0;

            str = new String[t.length()];
            lib = new String[t.length()];

            for (int i = 0; i < t.length(); i++) {
                str[i] = Character.toString(t.charAt(i));
                int tmp = 0;
                for (int j = 0; j < t.length(); j++) {
                    if (str[i].equals(lib[j])) {
                        tmp++;
                    }
                }
                if (tmp == 0) {
                    lib[k] = str[i];
                    k++;
                }
            }

//---------------------------------------------------------------------------------------
            for (int i = 0; i < t.length(); i++) {
                for (int j = 0; j < t.length(); j++) {
                    if (str[i].equals(lib[j])) {
                        text += code[j] + "|";
                        String tmp = lib[j];
                        for (int c = j; c > 0; c--) {
                            lib[c] = lib[c - 1];
                        }
                        lib[0] = tmp;
                    }
                }
            }

//---------------------------------------------------------------------------------------
            textOutput.clear();
            textOutput.setText("Вами выбран Метод 'Стопки книг'"+ "\n\n\n" + "Введен текст: " + "\n\n" + t + "\n\n" +
                    "Длина текста составляет " + t.length() + " символа/ов" + "\n\n" + "Генерация бинарного кода на " +
                    "основе текста: " + "\n\n" + strCode + "\n\n" + "Код полученый в результате использования метода: " + "\n\n" + text);
            try(FileWriter writer = new FileWriter("logFiles.txt", true))
            {
                writer.write("Вами выбран Метод 'Стопки книг'"+ "\n\n\n" + "Введен текст: " + "\n\n" + t + "\n\n" +
                        "Длина текста составляет " + t.length() + " символа/ов" + "\n\n" + "Генерация бинарного кода на " +
                        "основе текста: " + "\n\n" + strCode + "\n\n" + "Код полученый в результате использования метода: " + "\n\n" + text);
                writer.flush();
            }
            catch(IOException ex){

                textOutput.setText(ex.getMessage());
            }

            strCode = "";
            value = 0;
        }
//-------------------------------------------------------------------------------------------------------------
        else if ( value == 2 ) {

            String t = textInput.getText();
            String message = "";
            String[] str;
            String[] transposition;
            String[] word;
            String column1="", column2="", column3="", column4="";
            str = new String[t.length()];
            transposition = new String[t.length()];
            word = new String[t.length()];
            Arrays.fill(word, "");
            int[] wordWeight;
            wordWeight = new int[t.length()];
//            char[] alphabet = {};
//            alphabet[34] = ;
//---------------------------------------------------------

            for (int i = 0; i <t.length(); i++)
                str[i] = Character.toString(t.charAt(i));

//---------------------------------------------------------
            char[] alphabet;
//            String strAlphabet[];
//            strAlphabet = new String[35];
            alphabet = new char[35];
            alphabet[34] = ' ';

            char letter;
            int index = 0;

            for (letter='А'; letter<='Я'; letter++)
            {
                if ('Ж' == letter){
                    alphabet[index++] = 'Ё';
                }
                alphabet[index]=letter;
                index++;
            }

            alphabet[index]=' ';
//---------------------------------------------------------
            String donTSortWord = new String("");
            for (int i = 0; i <t.length(); i++){
                transposition[i] = str[i];
                word[0] += str[i];
                donTSortWord += str[i];

            }

            String saveLetter;
            for (int j = 1; j < t.length(); j++) {

                    saveLetter = transposition[0];
                    for (int i = 0; i < t.length()-1; i++) {
                        transposition[i] = transposition[i+1];
                    }
                    transposition[t.length()-1] = saveLetter;

                    for (int i = 0; i < t.length(); i++) {
                    word[j] += transposition[i];
                    }
           }
            saveLetter ="";
//---------------------------------------------------------------------------
            for (int i = 0; i < t.length(); i++) {
                column1 += str[i];
                column2 += word[i] + "\n";
            }
//---------------------------------------------------------------------------
            Arrays.sort(word);
            int numerStr = 0;

            for (int i = 0; i < t.length(); i++) {
               if (word[i].equals(donTSortWord))
                   numerStr = i + 1;
            }

            for (int i = 0; i < t.length(); i++) {
                column3 += word[i] + "\n";
            }

            for (int i = 0; i < t.length(); i++) {
                column4 += Character.toString(word[i].charAt(word[i].length()-1));
            }



            message = "Вами выбрано преобразование Барроуза — Уилера"+ "\n\n\n" + "Введен текст: " + "\n\n" + t + "\n\n" +
                    "Длина текста составляет " + t.length() + " символа/ов" + "\n\n\n" + "Вход" + "\n\n" + column1 +
                    "\n\n"  + "Все перестановки" + "\n\n" + column2 + "\n\n" + "Сортировка строк" + "\n\n"
                    + column3 +"\n\n" + "Выход" + "\n\n" + column4 + "\n\nРезультат: BWT(" + t + ") = (" + column4 + ", "
                    + numerStr + "),\nгде " + numerStr +" - номер исходной строки в отсортированной матрице.";
            textOutput.clear();
            textOutput.setText(message);
            try(FileWriter writer = new FileWriter("logFiles.txt", true))
            {
                writer.write(message);
                writer.flush();
            }
            catch(IOException ex){

                textOutput.setText(ex.getMessage());
            }

            value = 0;
                    }
//-------------------------------------------------------------------------------------------------------------
        else {
            textOutput.clear();
            textOutput.setText("Выбранные значения ещё не проработаны");
            try(FileWriter writer = new FileWriter("logFiles.txt", true))
            {
                writer.write("\n\nВыбранные значения ещё не проработаны\n\n");
                writer.flush();
            }
            catch(IOException ex){

                textOutput.setText(ex.getMessage());
            }

        }
            try(FileWriter writer = new FileWriter("logFiles.txt", true))
            {
                writer.write("\n\n/////////////////////////////////////////////////////\n\n");
                writer.flush();
            }
            catch(IOException ex){

                textOutput.setText(ex.getMessage());
            }
    });
//-------------------------------------------------------------------------------------------------
        openLog.setOnAction(actionEvent -> {
            try (FileReader reader = new FileReader("logFiles.txt")) {
                String logFiles = "";
                int c;
                while ((c = reader.read()) != -1) {

                    logFiles += (char) c;
                }
                textOutput.clear();
                textOutput.setText(logFiles);
            } catch (IOException ex) {

                textOutput.setText(ex.getMessage());
            }
        });
        help.setOnAction(actionEvent -> {
            try (FileReader reader = new FileReader("HelpMetods.txt")) {
                String help = "";
                int c;
                while ((c = reader.read()) != -1) {

                    help += (char) c;
                }
                textOutput.clear();
                textOutput.setText(help);
            } catch (IOException ex) {

                textOutput.setText(ex.getMessage());
            }
        });
//------------------------------------------------------------------------------------------
        clearLogs.setOnAction(actionEvent -> {
            try(FileWriter writer = new FileWriter("logFiles.txt", false))
            {
                writer.write("");
                writer.flush();
            }
            catch(IOException ex){

                textOutput.setText(ex.getMessage());
            }
        });
//------------------------------------------------------------------------------------------------------
        openCode.setOnAction(actionEvent -> {
            String lineController = "";
            lineController = textInput.getText();
            try (FileReader reader = new FileReader(lineController)) {
                String logFiles = "";
                int c;
                while ((c = reader.read()) != -1) {

                    logFiles += (char) c;
                }
                textOutput.clear();
                textOutput.setText(logFiles);
            } catch (IOException ex) {

                textOutput.setText(ex.getMessage() + "\n" + "Введите путь к файлу в окне ввода\nинформации и повторите операцию");
            }
        });
    }
//=====================================================================================================
    protected  String[] preCode(String a) {
        String[] masCode = new String[a.length()];
        String[] repeat = new String[a.length()];
//        for (int e = 0; e < a.length() ; e++) {
//            for (int g = 0; g < a.length(); g++) {
//                repeat[g] = Character.toString(a.charAt(g));
//                if (repeat[g].equals(repeat[e])) {
//                }
//                else {
//                }
                for (int i = 0; i < a.length(); i++) {
                    masCode[i] = "1";
                    strCode += "1";
                    for (int j = 0; j < i; j++) {
                        masCode[i] += "1";
                        strCode += "1";
                    }
                    masCode[i] += "0";
                    strCode += "0|";
                }
//            }
//        }
       return masCode;
    }
//==============================================================================================

    protected void openNewFrame (String window, Node button){

        button.getScene().getWindow().hide();

        loader.setLocation(getClass().getResource(window));
        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Parent root = loader.getRoot();
        stage.setScene(new Scene(root));
        count++;
        stage.show();
    }
}

