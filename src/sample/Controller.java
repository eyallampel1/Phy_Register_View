package sample;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

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

    hexToBin hextobin=new hexToBin();
    String fourBinString,oneHexChar;

    public void initialize(){
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

    public void displaybin0ToHex0(){
        displayBinToHex("bin0");
    }

    public void displaybin4ToHex1(){
        displayBinToHex("bin4");
    }


    public void displayHex0ToBin_xxxx(){
        displayHexToBin("Hex0");
    }

    public void displayHex1ToBin_xxxx(){
        displayHexToBin("Hex1");
    }

    public void displayHex2ToBin_xxxx(){
        displayHexToBin("Hex2");
    }

    public void displayHex3ToBin_xxxx(){
        displayHexToBin("Hex3");
    }

    public void displayHexToBin(String whichHexNibbel){
    switch (whichHexNibbel){
        case "Hex0":
            System.out.println("hex0");
           fourBinString= hextobin.convertHexToBin(hex0.getText());
            bin0.setText(""+fourBinString.charAt(3));
            bin1.setText(""+fourBinString.charAt(2));
            bin2.setText(""+fourBinString.charAt(1));
            bin3.setText(""+fourBinString.charAt(0));
            break;
        case "Hex1":
            System.out.println("hex1");
            fourBinString= hextobin.convertHexToBin(hex1.getText());
            bin4.setText(""+fourBinString.charAt(3));
            bin5.setText(""+fourBinString.charAt(2));
            bin6.setText(""+fourBinString.charAt(1));
            bin7.setText(""+fourBinString.charAt(0));
            break;
        case "Hex2":
            System.out.println("hex2");
            fourBinString= hextobin.convertHexToBin(hex2.getText());
            bin8.setText(""+fourBinString.charAt(3));
            bin9.setText(""+fourBinString.charAt(2));
            bin10.setText(""+fourBinString.charAt(1));
            bin11.setText(""+fourBinString.charAt(0));
            break;
        case "Hex3":
            System.out.println("hex3");
            fourBinString= hextobin.convertHexToBin(hex3.getText());
            bin12.setText(""+fourBinString.charAt(3));
            bin13.setText(""+fourBinString.charAt(2));
            bin14.setText(""+fourBinString.charAt(1));
            bin15.setText(""+fourBinString.charAt(0));

            break;
    }
    }

    public void displayBinToHex(String whichBinNibbel){
        switch (whichBinNibbel) {
            case "bin0":
                oneHexChar= hextobin.convertBinToHex(bin3.getText()+bin2.getText()+bin1.getText()+bin0.getText());
                hex0.setText(oneHexChar);
                break;
            case "bin4":
                oneHexChar= hextobin.convertBinToHex(bin7.getText()+bin6.getText()+bin5.getText()+bin4.getText());
                hex1.setText(oneHexChar);
                break;
        }
    }
}
