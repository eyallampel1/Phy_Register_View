package sample;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

public class Controller {
    @FXML
    TextField hex0 = new TextField();
    @FXML
    TextField hex1 = new TextField();
    @FXML
    TextField hex2 = new TextField();
    @FXML
    TextField hex3 = new TextField();
    @FXML
    TextField bin0 = new TextField();
    @FXML
    TextField bin1 = new TextField();
    @FXML
    TextField bin2 = new TextField();
    @FXML
    TextField bin3 = new TextField();
    @FXML
    TextField bin4 = new TextField();
    @FXML
    TextField bin5 = new TextField();
    @FXML
    TextField bin6 = new TextField();
    @FXML
    TextField bin7 = new TextField();
    @FXML
    TextField bin8 = new TextField();
    @FXML
    TextField bin9 = new TextField();
    @FXML
    TextField bin10 = new TextField();
    @FXML
    TextField bin11 = new TextField();
    @FXML
    TextField bin12 = new TextField();
    @FXML
    TextField bin13 = new TextField();
    @FXML
    TextField bin14 = new TextField();
    @FXML
    TextField bin15 = new TextField();

    @FXML
    ChoiceBox<String> deviceSelect;
    @FXML
    ListView<String> registerNames;
    @FXML
    TableView<RegisterModel> registerTable;


    @FXML
     TableColumn<RegisterModel, String> registerNameColumn;

    @FXML
     TableColumn<RegisterModel, String> offsetColumn;
@FXML
        TextArea registerDescription;



    hexToBin hextobin = new hexToBin();
    String fourBinString, oneHexChar;

    public void initialize() {
        initComboBox();

        initRegisterNames_TableView();

        hex0.setText("0");
        hex1.setText("0");
        hex2.setText("0");
        hex3.setText("0");
        bin0.setText("0");
        bin1.setText("0");
        bin2.setText("0");
        bin3.setText("0");
        bin4.setText("0");
        bin5.setText("0");
        bin6.setText("0");
        bin7.setText("0");
        bin8.setText("0");
        bin9.setText("0");
        bin10.setText("0");
        bin11.setText("0");
        bin12.setText("0");
        bin13.setText("0");
        bin14.setText("0");
        bin15.setText("0");
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                hex3.requestFocus();
                hex3.deselect();
            }
        });
    }

    public void displaybin0ToHex0() {
        displayBinToHex("bin0");
    }

    public void displaybin4ToHex1() {
        displayBinToHex("bin4");
    }


    public void displayHex0ToBin_xxxx() {
        displayHexToBin("Hex0");
    }

    public void displayHex1ToBin_xxxx() {
        displayHexToBin("Hex1");
    }

    public void displayHex2ToBin_xxxx() {
        displayHexToBin("Hex2");
    }

    public void displayHex3ToBin_xxxx() {
        displayHexToBin("Hex3");
    }

    public void displayHexToBin(String whichHexNibbel) {
        switch (whichHexNibbel) {
            case "Hex0":
                System.out.println("hex0");
                fourBinString = hextobin.convertHexToBin(hex0.getText());
                bin0.setText("" + fourBinString.charAt(3));
                bin1.setText("" + fourBinString.charAt(2));
                bin2.setText("" + fourBinString.charAt(1));
                bin3.setText("" + fourBinString.charAt(0));
                break;
            case "Hex1":
                System.out.println("hex1");
                fourBinString = hextobin.convertHexToBin(hex1.getText());
                bin4.setText("" + fourBinString.charAt(3));
                bin5.setText("" + fourBinString.charAt(2));
                bin6.setText("" + fourBinString.charAt(1));
                bin7.setText("" + fourBinString.charAt(0));
                break;
            case "Hex2":
                System.out.println("hex2");
                fourBinString = hextobin.convertHexToBin(hex2.getText());
                bin8.setText("" + fourBinString.charAt(3));
                bin9.setText("" + fourBinString.charAt(2));
                bin10.setText("" + fourBinString.charAt(1));
                bin11.setText("" + fourBinString.charAt(0));
                break;
            case "Hex3":
                System.out.println("hex3");
                fourBinString = hextobin.convertHexToBin(hex3.getText());
                bin12.setText("" + fourBinString.charAt(3));
                bin13.setText("" + fourBinString.charAt(2));
                bin14.setText("" + fourBinString.charAt(1));
                bin15.setText("" + fourBinString.charAt(0));

                break;
        }
    }

    public void displayBinToHex(String whichBinNibbel) {
        switch (whichBinNibbel) {
            case "bin0":
                oneHexChar = hextobin.convertBinToHex(bin3.getText() + bin2.getText() + bin1.getText() + bin0.getText());
                hex0.setText(oneHexChar);
                break;
            case "bin4":
                oneHexChar = hextobin.convertBinToHex(bin7.getText() + bin6.getText() + bin5.getText() + bin4.getText());
                hex1.setText(oneHexChar);
                break;
        }
    }

    public void initComboBox() {
        ObservableList list = FXCollections.observableArrayList();
        String a = "Black FPGA";
        String b = "Black PHY";

        list.removeAll(list);
        list.addAll(a, b);

        deviceSelect.getItems().addAll(list);
        deviceSelect.getSelectionModel().selectFirst();
    }

    public void initRegisterNames_listView() {
        ObservableList list = FXCollections.observableArrayList();


        //FPGA REGISTERS
        String a = "ID";
        String b = "ID2";
        String c = "DATE1";
        String d = "DATE2";
        String e = "D_VER";

        list.removeAll(list);
        list.addAll(a, b, c, d, e);
        registerNames.getItems().addAll(list);
        // registerTable.getItems().addAll(list);

    }


    public void initRegisterNames_TableView() {
        registerNameColumn.setCellValueFactory(new PropertyValueFactory<>("registerName"));
        offsetColumn.setCellValueFactory(new PropertyValueFactory<>("offsetValue"));


        RegisterModel register=new RegisterModel("id","0x00");
        RegisterModel register2=new RegisterModel("id2","0x02");
        RegisterModel register3=new RegisterModel("ver","0x04");
        RegisterModel register4=new RegisterModel("date","0x06");
        RegisterModel register5=new RegisterModel("date2","0x08");
        RegisterModel register6=new RegisterModel("D_VER","0x0a");
        RegisterModel register7=new RegisterModel("BoardLayoutVersion","0x0c");
        RegisterModel register8=new RegisterModel("STATUS_REG","0x0e");
        RegisterModel register9=new RegisterModel("ETC_sub_addr","0x10");
        RegisterModel register10=new RegisterModel("ETC_Cmd","0x12");
        RegisterModel register11=new RegisterModel("ETC_data_rd","0x14");
        RegisterModel register12=new RegisterModel("ETC_data_wr","0x16");
        RegisterModel register13=new RegisterModel("ETC_exe","0x18");
        RegisterModel register14=new RegisterModel("ETC_status","0x1a");

        registerTable.getItems().addAll(register,register2,register3,register4,register5,register6,register7,register8,register9,register10,register11,register12,register13,register14);

    }

    public void rowSelected(){
        ///show the selected register


        RegisterModel selectedRegister = registerTable.getSelectionModel().getSelectedItem();
        System.out.println(selectedRegister.getRegisterName());

        if(selectedRegister.getRegisterName().equals("id")){
            registerDescription.setText("ID Register  [0x = ASCII \"IOGB\"]");
        }
        else if(selectedRegister.getRegisterName().equals("date")){
            registerDescription.setText("Date modification register (dd/mm/yyyy - in BCD Format)");
        }
        else if(selectedRegister.getRegisterName().equals("BoardLayoutVersion")){
            registerDescription.setText("Board Layout Version\n" +
                    "Bits (15:3): Reserved\n" +
                    "Bits (2:0): Board Layout Version {''001\", \"002\", etc.} [0x2]");
        }



    }



}
