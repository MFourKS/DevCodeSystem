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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
    private MenuItem decoder;

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
                else if ( metCode.getValue().equals("Метод адаптивного кодирования Хафмена")) value = 3;

            }
//            public void comboAction() {
//               if (metCode.getValue() == metCode.getValue());
//            }
        });
//-------------------------------------------------------------------------------------------------
        coder.setOnAction(actionEvent -> {
        if ( value == 1 ) {
            long time1 = System.nanoTime();

            String[] lib;
            String[] str;
            String binaryLib = "";
            String load = "";
            String textOut = "";
            String text = "";
            String message = "";
            String t = textInput.getText();

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
                    load += str[i];
                    k++;
                }
            }
//---------------------------------------------------------------------------------------
            for (int i = 0; i < t.length(); i++) {
                for (int j = 0; j < k; j++) {
                    if (str[i].equals(lib[j])) {
                        String tmp = lib[j];
                        for (int c = j; c > 0; c--) {
                            lib[c] = lib[c - 1];
                        }
                        lib[0] = tmp;
                    }
                }
            }

            for (int i = 0; i < k; i++) {
                binaryLib += lib[i];
            }

            String resultM = "";
            String[] code = preCode(binaryLib);
            for (int j = 0; j < binaryLib.length(); j++){
                resultM += binaryLib.charAt(j) + " " + code[j]+ " \n";
            }

            for (int i = 0; i < t.length(); i++) {
                for (int j = 0; j < k; j++) {
                    if (str[i].equals(lib[j])) {
                        textOut += code[j] + "|";
                        text += code[j];
                    }
                }
            }

            long time2 = System.nanoTime();
            long timeTaken = time2 - time1;
            System.out.println("Time coder " + String.format("%.3f",(float)timeTaken/1000000000) + " s");
//---------------------------------------------------------------------------------------
            String unique = "";
            String table = "";
            String StrTables = "";

            for (int i = 0; i < k; i++) {
                unique += load.charAt(i) + " - " + code[i] + "\n";
                table += binaryLib.charAt(i) + " - " + (i+1) + " - " + getPosition(binaryLib.charAt(i)) + "\n";
                String s = Integer.toBinaryString(getPosition(binaryLib.charAt(i)));
                if (s.equals("11111111111111111111111111000000"))StrTables += code[i]+ "|" + "1000000" + "|";
                else StrTables += code[i]+ "|" + s + "|";
            }

            message = "Вами выбран Метод 'Стопки книг'"+ "\n\n\n" + "Введен текст: " + "\n\n" + t + "\n\n" +
                    "Длина текста составляет " + t.length() + " символа/ов" + "\n\n" + "Уникальные символы в тексте: " + load
                    + "\n\nГенерация бинарного кода на основе текста: \n" +  unique  + "\n\n" + strCode +
                    "\n\n" + "Код полученый в результате использования метода: " + "\n\n" + resultM +"\nЗакодированное сообщение: \n" + textOut +
                    "\n\nТаблица передаваемого сообщения\n" + table + "\n\n\nTime coder " + String.format("%.3f",(float)timeTaken/1000000000) + " s";
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

            try(FileWriter writer = new FileWriter("tables.txt", false))
            {
                writer.write(StrTables);
                writer.flush();
            }
            catch(IOException ex){

                textOutput.setText(ex.getMessage());
            }

            try(FileWriter writer = new FileWriter("code.txt", false))
            {
                writer.write(text);
                writer.flush();
            }
            catch(IOException ex){

                textOutput.setText(ex.getMessage());
            }

            strCode = "";
        }
//-------------------------------------------------------------
        else if ( value == 2 ) {

            String t = textInput.getText();
            String message = "";
            String[] str = {};
            String[] transposition;
            String[] word;
            String column1="", column2="", column3="", column4="";
            str = new String[t.length()];
            transposition = new String[t.length()];
            word = new String[t.length()];
            Arrays.fill(word, "");
            int[] wordWeight;
            wordWeight = new int[t.length()];
//          char[] alphabet = {};
//          alphabet[34] = ;
//---------------------------------------------------------

            for (int i = 0; i <t.length(); i++)
                str[i] = Character.toString(t.charAt(i));

//---------------------------------------------------------
            char[] alphabet;
//          String strAlphabet[];
//          strAlphabet = new String[35];
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

//---------------------------------------------------------------------------------------------------------

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


                    }
        else if ( value == 3 ) {

            String t = textInput.getText();
            String message = "";
            String[] str = {};
            String[] st = {};
            //int[] level = new int[t.length()];
            str = new String[t.length()];
            st = new String[t.length()];
            int[] weight =  new int[t.length()];
           //int weight = 0;
            String n = "";

            String[] code = preCode(t);

            for (int i = 0; i <t.length(); i++) {
                str[i] = Character.toString(t.charAt(i));
                st[i] = Character.toString(t.charAt(i));
            }


            for (int j = 0 ; j <t.length() ; j++)
               for (int i = 0 ; i <t.length(); i++) {
                    if (str[j].equals(str[i])){
                        weight[j]++;
                        if (j > i)
                        weight[j]=0;
                    }
               }

            for (int j = 0, a = 0 ; j <t.length() ; j++)
                for (int i = 0 ; i <t.length(); i++) {
                    if (weight[j] > weight[i]) {
                        weight[j]=-1;
                        st[j] = code[a];
                        a++;
                    }
                }
//           // for (int j = 0 ; j <t.length() ; j++)
//                for (int i = 0 ; i <t.length(); i++) {
//                    if (weight[i]<weight[i]) level[i]++;
//                }

            for (int j = 0;  j <t.length(); j++)
                if (weight[j] != 0)
                n+=str[j] + " = " + st[j] + "\n";

            message = "Вами выбран метод адаптивного кодирования Хафмена"+ "\n\n\n" + "Введен текст: " + "\n\n" + t + "\n\n" + n + "\n\n" + strCode;
            textOutput.clear();
            textOutput.setText(message);

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
        decoder.setOnAction(actionEvent -> {
                    if ( value == 1 ) {
                        long time1 = System.nanoTime();

                        String StrTables = "";
                        String StrCode = "";
                        try (FileReader reader = new FileReader("tables.txt")) {
                            int c;
                            while ((c = reader.read()) != -1) {

                                StrTables += (char) c;
                            }
                        } catch (IOException ex) {
                            textOutput.setText(ex.getMessage());
                        }
//--------------------------------------------------------------------------------------------
                        try (FileReader reader = new FileReader("code.txt")) {
                            int c;
                            while ((c = reader.read()) != -1) {

                                StrCode += (char) c;
                            }
                        } catch (IOException ex) {
                            textOutput.setText(ex.getMessage());
                        }
//-------------------------------------------------------------------------------------------
                        int words = 0, s = 0 ;
                        for (int i = 0; i < StrCode.length(); i++)
                            if (Character.toString(StrCode.charAt(i)).equals("0")) words++;

                        String[] code = new String[words+1];
                        for (int i = 0; i < words; i++){
                            code[i] = "";
                        }

                        for (int i = 0; s < words; i++){
                            code[s] += Character.toString(StrCode.charAt(i));
                            if (Character.toString(StrCode.charAt(i)).equals("0")) s++;
                        }

//------------------------------------------------------------------------------------------------
                        int elTable = 0, g = 0 ;
                        for (int i = 0; i < StrTables.length(); i++)
                            if (Character.toString(StrTables.charAt(i)).equals("|")) elTable++;

                        String[] table = new String[elTable+1];
                        for (int i = 0; i < elTable; i++){
                            table[i] = "";
                        }
                        for (int i = 0; g < elTable; i++){
                            if (Character.toString(StrTables.charAt(i)).equals("|")){
                                g++;
                                continue;
                            }
                            table[g] += Character.toString(StrTables.charAt(i));
                        }
                        //for (int i = 0; i < elTable; i++)System.out.print("\n" + table[i]);

                        String res = "";
                        for (int i = 0; i < words; i++){
                            for (int j = 0; j < elTable; j++){
                                if ((j%2 == 0)&&(code[i].equals(table[j])))
                                    try {
                                        res += Character.toString(Integer.parseInt(table[j + 1], 2) + (int) 'a' - 1);
                                    }
                                    catch (Exception ex){
                                        textOutput.setText(ex.getMessage());
                                }
                            }

                        }
                        long time2 = System.nanoTime();
                        long timeTaken = time2 - time1;
                        System.out.println("Time decoder " + String.format("%.3f",(float)timeTaken/1000000000) + " s");
                            textOutput.setText("tables.txt:\n" + StrTables +"\ncode.txt:\n"+ StrCode + "\n " + res + "\n\nTime decoder " + String.format("%.3f",(float)timeTaken/1000000000) + " s");

                    }
        });
//=====================================================================================================
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
//=====================================================================================================
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
//=====================================================================================================
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
//=====================================================================================================
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
    int getPosition(char input) {

        String regex = "[а-яёА-ЯЁ]+";
        int position = 0;
        Pattern pattern = Pattern.compile(regex);
        Matcher m = pattern.matcher(Character.toString(input));
        if (m.find()){
            position = (int) input - 976 - 96 + 1;
            if (position>=7)position++;
            if (Character.toString(input).equals("ё")||Character.toString(input).equals("Ё"))position=7;
        }
        else {
            char smalla = 'a';
            int alphabetStart = (int) smalla;
            position = (int) input - alphabetStart + 1;
        }
        return position;
    }
}

