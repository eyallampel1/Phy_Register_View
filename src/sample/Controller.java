package sample;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.*;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import jssc.*;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.*;
import java.util.function.Consumer;

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
    TextField userWantsToSendThis,phyPort,phyRegister,phyPage;
    @FXML
    Button SendToTerminalBTN,phySetSpeed10,phySetSpeed100,phySetSpeed1000,phyRead,phyWrite,writeToSensorBtn,phyInit1,phyInit2;


    @FXML
    TextArea uartTerminal,GPPTerminal,cryptoTerminal;


    @FXML
    TableColumn<RegisterModel, String> registerNameColumn;

    @FXML
    TableColumn<RegisterModel, String> offsetColumn;
    @FXML
    TextArea registerDescription;

    SerialPort serialPort;
    Robot robot;
    hexToBin hextobin = new hexToBin();
    String fourBinString, oneHexChar,rawBuffer,inputString,readUart;
    FileWriter myFile;
    BufferedReader bR=null;
    File myTextFile;
int index=0,phyindexInteger=0;
int userSentSomtingToUART=1;

    public void initialize() {
        try {
            robot=new Robot();
        } catch (AWTException e) {
            e.printStackTrace();
        }

        try {
            myTextFile=new File("d:\\text.txt");
            myFile = new FileWriter("d:\\text.txt");
            bR=new BufferedReader(new FileReader("d:\\text.txt"));

        } catch (IOException e) {
            e.printStackTrace();
        }

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

        initUart();

    }

    public void sendToTerminalBtnPressed() throws SerialPortException  {
        writeToUartTerminal(userWantsToSendThis.getText()+"\n",1);
            serialPort.writeString(userWantsToSendThis.getText()+"\n");


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

    public void initUart(){
        serialPort = new SerialPort("COM5");
        try {
            serialPort.openPort();

            serialPort.setParams(SerialPort.BAUDRATE_115200,
                    SerialPort.DATABITS_8,
                    SerialPort.STOPBITS_1,
                    SerialPort.PARITY_NONE);



            serialPort.addEventListener(
                    new PortReader(   buffer -> Platform.runLater(() ->
                            writeToUartTerminal(buffer,1)
                    )),
                    SerialPort.MASK_RXCHAR);

          //  writeToUartTerminal("Hurrah!");
      //      serialPort.writeString("Hurrah!");
        }
        catch (SerialPortException ex) {
            System.out.println("There are an error on writing string to port т: " + ex);
        }
    }

public void writeToTextFile(String text)
{
    try {

   myFile.append(text+"\n");
myFile.flush();
    } catch (Exception e) {
        e.printStackTrace();
    }

}

    public void writeToUartTerminal(String buffer,int mode){
if(phyindexInteger==1) {
    index=1;
}


                            rawBuffer=buffer;
        writeToTextFile(buffer);
        if (buffer!=null) {
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    uartTerminal.appendText(buffer);
                }
            });

        }

        if (index==1){
            phyindexInteger+=1;
           if (phyindexInteger==23){
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                               readUart=buffer.substring(buffer.indexOf("0x")+2);
               hex0.setText(readUart.charAt(3)+"");
               hex1.setText(readUart.charAt(2)+"");
               hex2.setText(readUart.charAt(1)+"");
               hex3.setText(readUart.charAt(0)+"")             ;

                hex3.requestFocus();

                robot.keyPress(KeyEvent.VK_TAB);
                robot.keyRelease(KeyEvent.VK_TAB);
                robot.keyPress(KeyEvent.VK_TAB);
                robot.keyRelease(KeyEvent.VK_TAB);
                robot.keyPress(KeyEvent.VK_TAB);
                robot.keyRelease(KeyEvent.VK_TAB);
                robot.keyPress(KeyEvent.VK_TAB);
                robot.keyRelease(KeyEvent.VK_TAB);

                index=0;
                phyindexInteger=0;
            }

        }


        if(userSentSomtingToUART==3){
            readUart=buffer.substring(buffer.indexOf("0x")+2);

            hex0.setText(readUart.charAt(3)+"");
            hex1.setText(readUart.charAt(2)+"");
            hex2.setText(readUart.charAt(1)+"");
            hex3.setText(readUart.charAt(0)+"");

            hex3.requestFocus();

                robot.keyPress(KeyEvent.VK_TAB);
            robot.keyRelease(KeyEvent.VK_TAB);
                robot.keyPress(KeyEvent.VK_TAB);
            robot.keyRelease(KeyEvent.VK_TAB);
                robot.keyPress(KeyEvent.VK_TAB);
            robot.keyRelease(KeyEvent.VK_TAB);
                robot.keyPress(KeyEvent.VK_TAB);
            robot.keyRelease(KeyEvent.VK_TAB);

            userSentSomtingToUART=1;
        }

        if(userSentSomtingToUART==2){
            userSentSomtingToUART+=1;

        }




if(mode==2){
    uartTerminal.appendText(buffer);
    try {
        serialPort.writeString(buffer);
        inputString=serialPort.readString(100,100);

    } catch (SerialPortException e) {
        e.printStackTrace();
    } catch (SerialPortTimeoutException e) {
        e.printStackTrace();
    }


}
//     if(index==0) {
//         index=1;
//         writeToUartTerminal(buffer); //to delete
//
//     }

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
        String c = "Black ETC";
        String d = "Black Current/Power Monitor (INA219)";
        String e = "Black CPLD";
        String f = "Black Temperature sensor";
        String g = "Black FAN access and control";
        String h = "Black Interrupt Controller Interface";
        String i = "Black 1PPS Control & Status";
        String k = "Black EDSP Power CPLD";
        String j = "Black Discretes Control";
        String l = "Black Test-Point MUX Select";
        String aa = "Black Sofware Reset";
        String bb = "Black Blanks";
        String cc = "Black Discretes Status";


        list.removeAll(list);
        list.addAll(a, b,c,d,e,f,g,h,i,k,j,l,aa,bb,cc);

        deviceSelect.getItems().addAll(list);
        deviceSelect.getSelectionModel().selectFirst();

        deviceSelect.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number number2) {
                System.out.println(deviceSelect.getItems().get((Integer) number2));
                ///choice box has changed!!!
                if(deviceSelect.getItems().get((Integer) number2).equals("Black PHY")) {
                    initRegisterNames_Phy_TableView();
                }
                   else if(deviceSelect.getItems().get((Integer) number2).equals("Black FPGA"))   {
                    initRegisterNames_TableView();
                    }

                else if(deviceSelect.getItems().get((Integer) number2).equals("Black ETC"))   {
                    initRegisterNames_Black_ETC();
                }
                else if(deviceSelect.getItems().get((Integer) number2).equals("Black Current/Power Monitor (INA219)"))   {
                    initRegisterNames_Black_ina219();
                }
                else if(deviceSelect.getItems().get((Integer) number2).equals("Black Temperature sensor"))   {
                    initRegisterNames_Black_Temperature();
                }
                else if(deviceSelect.getItems().get((Integer) number2).equals("Black FAN access and control"))   {
                    initRegisterNames_Black_FAN();
                }
                else if(deviceSelect.getItems().get((Integer) number2).equals("Black Interrupt Controller Interface"))   {
                    initRegisterNames_Interrupt();
                }
                else if(deviceSelect.getItems().get((Integer) number2).equals("Black Discretes Control"))   {
                    initRegisterNames_Discretes_Control();
                }
                else if(deviceSelect.getItems().get((Integer) number2).equals("Black Test-Point MUX Select"))   {
                    initRegisterNames_Black_tp_mux();
                }
                else if(deviceSelect.getItems().get((Integer) number2).equals("Black Sofware Reset"))   {
                    initRegisterNames_Black_Software_Reset();
                }
                else if(deviceSelect.getItems().get((Integer) number2).equals("Black Blanks"))   {
                    initRegisterNames_Black_blanks();
                }
                else if(deviceSelect.getItems().get((Integer) number2).equals("Black Discretes Status"))   {
                    initRegisterNames_Black_discreat_status();
                }


                }

        });


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

    public void initRegisterNames_Black_discreat_status() {
        registerNameColumn.setCellValueFactory(new PropertyValueFactory<>("registerName"));
        offsetColumn.setCellValueFactory(new PropertyValueFactory<>("offsetValue"));
        RegisterModel register=new RegisterModel("FPGA_IO_B_GPI","0xc4");
        RegisterModel register2=new RegisterModel("FPGA_EDSP_GPI","0xca");
        RegisterModel register3=new RegisterModel("FPGA_EXT_FEA_RST_EDSP","0xcc");
        registerTable.getItems().clear();
        registerTable.getItems().addAll(register,register2,register3);
    }

    public void initRegisterNames_Black_blanks() {
        registerNameColumn.setCellValueFactory(new PropertyValueFactory<>("registerName"));
        offsetColumn.setCellValueFactory(new PropertyValueFactory<>("offsetValue"));
        RegisterModel register=new RegisterModel("Blank_Emul_En","0xba");
        RegisterModel register2=new RegisterModel("Blank_Rd_Val","0xbc");
        RegisterModel register3=new RegisterModel("Blank_Emul_Drv_Val","0xbe");
        registerTable.getItems().clear();
        registerTable.getItems().addAll(register,register2,register3);
    }


    public void initRegisterNames_Black_Software_Reset() {
        registerNameColumn.setCellValueFactory(new PropertyValueFactory<>("registerName"));
        offsetColumn.setCellValueFactory(new PropertyValueFactory<>("offsetValue"));
        RegisterModel register=new RegisterModel("SOFT_RESET","0xb8");
        registerTable.getItems().clear();
        registerTable.getItems().addAll(register);
    }

    public void initRegisterNames_Black_tp_mux() {
        registerNameColumn.setCellValueFactory(new PropertyValueFactory<>("registerName"));
        offsetColumn.setCellValueFactory(new PropertyValueFactory<>("offsetValue"));
        RegisterModel register=new RegisterModel("tp_sel","0xb6");
        registerTable.getItems().clear();
        registerTable.getItems().addAll(register);
    }


    public void initRegisterNames_Discretes_Control() {
        registerNameColumn.setCellValueFactory(new PropertyValueFactory<>("registerName"));
        offsetColumn.setCellValueFactory(new PropertyValueFactory<>("offsetValue"));
        RegisterModel register=new RegisterModel("GPOUT_Ctrl","0xb4");
        registerTable.getItems().clear();
        registerTable.getItems().addAll(register);
    }


    public void initRegisterNames_Interrupt() {
        registerNameColumn.setCellValueFactory(new PropertyValueFactory<>("registerName"));
        offsetColumn.setCellValueFactory(new PropertyValueFactory<>("offsetValue"));
        RegisterModel register=new RegisterModel("Interrupt Enable","0x80");
        RegisterModel register2=new RegisterModel("Interrupt Mask","0x82");
        RegisterModel register3=new RegisterModel("Interrupt Cause","0x84");
        RegisterModel register4=new RegisterModel("Interrupt Stimulus Generation","0x88");


        registerTable.getItems().clear();
        registerTable.getItems().addAll(register,register2,register3,register4);
    }


    public void initRegisterNames_Black_FAN() {
        registerNameColumn.setCellValueFactory(new PropertyValueFactory<>("registerName"));
        offsetColumn.setCellValueFactory(new PropertyValueFactory<>("offsetValue"));
        RegisterModel register=new RegisterModel("Fan_Spd_Ctrl","0x60");
        RegisterModel register2=new RegisterModel("Fan_min_bndry","0x62");
        RegisterModel register3=new RegisterModel("Fan_Speed","0x64");
        RegisterModel register4=new RegisterModel("Fan_spd_chken","0x70");
        RegisterModel register5=new RegisterModel("Fan_Status","0x74");


        registerTable.getItems().clear();
        registerTable.getItems().addAll(register,register2,register3,register4,register5);
    }

    public void initRegisterNames_Black_ETC(){
        registerNameColumn.setCellValueFactory(new PropertyValueFactory<>("registerName"));
        offsetColumn.setCellValueFactory(new PropertyValueFactory<>("offsetValue"));
        RegisterModel register=new RegisterModel("Configuration Register","0x00");
        RegisterModel register2=new RegisterModel("Alarm Register - low byte","0x01");
        RegisterModel register3=new RegisterModel("Alarm Register2 - low-middle byte","0x02");
        RegisterModel register4=new RegisterModel("Alarm Register3 - high-middle byte","0x03");
        RegisterModel register5=new RegisterModel("Alarm Register4 - high byte","0x04");
        RegisterModel register6=new RegisterModel("ETC Counter - low byte","0x05");
        RegisterModel register7=new RegisterModel("ETC Counter2 - low-middle byte","0x06");
        RegisterModel register8=new RegisterModel("ETC Counter3 - high-middle byte","0x07");
        RegisterModel register9=new RegisterModel("ETC Counter4 - high byte","0x08");
        RegisterModel register10=new RegisterModel("Event Counter - low byte","0x09");
        RegisterModel register11=new RegisterModel("Event Counter - high byte","0x0a");
        RegisterModel register12=new RegisterModel("User Memory - byte1","0x0b");
        RegisterModel register13=new RegisterModel("User Memory - byte2","0x0c");
        RegisterModel register14=new RegisterModel("User Memory - byte3","0x0d");
        RegisterModel register15=new RegisterModel("User Memory - byte4","0x0e");
        RegisterModel register16=new RegisterModel("User Memory - byte5","0x0f");
        RegisterModel register17=new RegisterModel("User Memory - byte6","0x10");
        RegisterModel register18=new RegisterModel("User Memory - byte7","0x11");
        RegisterModel register19=new RegisterModel("User Memory - byte8","0x12");
        RegisterModel register20=new RegisterModel("User Memory - byte9","0x13");
        RegisterModel register21=new RegisterModel("User Memory - byte10","0x14");
        RegisterModel register22=new RegisterModel("read 0x00 - not used","0x15");
        RegisterModel register23=new RegisterModel("Reset Command","0x1d");
        RegisterModel register24=new RegisterModel("Write Disable","0x1e");
        RegisterModel register25=new RegisterModel("Memory Disable","0x1f");

        registerTable.getItems().clear();
        registerTable.getItems().addAll(register,register2,register3,register4,register5,register6,register7,register8,register9,register10,register11,register12,register13,register14,register15,register16,register17,register18,register19,register20,register21,register22,register23,register24,register25);
    }

    public void initRegisterNames_Black_Temperature() {
        registerNameColumn.setCellValueFactory(new PropertyValueFactory<>("registerName"));
        offsetColumn.setCellValueFactory(new PropertyValueFactory<>("offsetValue"));
        RegisterModel register=new RegisterModel("Temperature register","0x00");
        RegisterModel register2=new RegisterModel("Configuration register","0x01");
        RegisterModel register3=new RegisterModel("Hysteresis register","0x02");
        RegisterModel register4=new RegisterModel("Overtemperature shutdown","0x03");

        registerTable.getItems().clear();
        registerTable.getItems().addAll(register,register2,register3,register4);
    }

    public void initRegisterNames_Black_ina219() {
        registerNameColumn.setCellValueFactory(new PropertyValueFactory<>("registerName"));
        offsetColumn.setCellValueFactory(new PropertyValueFactory<>("offsetValue"));
        RegisterModel register=new RegisterModel("Configuration","0x00");
        RegisterModel register2=new RegisterModel("Shunt voltage","0x01");
        RegisterModel register3=new RegisterModel("Bus voltage","0x02");
        RegisterModel register4=new RegisterModel("Power","0x03");
        RegisterModel register5=new RegisterModel("Current","0x04");
        RegisterModel register6=new RegisterModel("Calibration","0x05");
        registerTable.getItems().clear();
        registerTable.getItems().addAll(register,register2,register3,register4,register5,register6);
    }

    public void initRegisterNames_Phy_TableView() {
        registerNameColumn.setCellValueFactory(new PropertyValueFactory<>("registerName"));
        offsetColumn.setCellValueFactory(new PropertyValueFactory<>("offsetValue"));
        RegisterModel register=new RegisterModel("Copper Control","0x00");
        RegisterModel register2=new RegisterModel("Copper Status","0x01");
        RegisterModel register3=new RegisterModel("Phy Identifier 1","0x02");
        RegisterModel register4=new RegisterModel("Phy Identifier 2","0x03");
        registerTable.getItems().clear();
        registerTable.getItems().addAll(register,register2,register3,register4);
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
//        RegisterModel register9=new RegisterModel("ETC_sub_addr","0x10");
//        RegisterModel register10=new RegisterModel("ETC_Cmd","0x12");
//        RegisterModel register11=new RegisterModel("ETC_data_rd","0x14");
//        RegisterModel register12=new RegisterModel("ETC_data_wr","0x16");
//        RegisterModel register13=new RegisterModel("ETC_exe","0x18");
//        RegisterModel register14=new RegisterModel("ETC_status","0x1a");
        registerTable.getItems().clear();
        registerTable.getItems().addAll(register,register2,register3,register4,register5,register6,register7,register8);

    }

    public void rowSelected() throws SerialPortException, InterruptedException, IOException {
        ///show the selected register


        RegisterModel selectedRegister = registerTable.getSelectionModel().getSelectedItem();
        System.out.println(selectedRegister.getRegisterName());

        //black Discreted status
        if(selectedRegister.getRegisterName().equals("FPGA_IO_B_GPI")) {
            registerDescription.setText("This register reflects the value of FPGA_IO_B_GPI.\n" +
                    "Bits(15:2): Reserved\n" +
                    "Bits(1:0): FPGA_IO_B_GPI\n");
        }
        if(selectedRegister.getRegisterName().equals("FPGA_EDSP_GPI")) {
            registerDescription.setText("This register reflects the value of FPGA_EDSP_GPI.\n" +
                    "Bits(15:2): Reserved\n" +
                    "Bits(1:0): FPGA_EDSP_GPI\n");
        }
        if(selectedRegister.getRegisterName().equals("FPGA_EXT_FEA_RST_EDSP")) {
            registerDescription.setText("This register reflects the value of FPGA_EXT_FEA_RST_EDSP.\n" +
                    "Bits(15:2): Reserved\n" +
                    "Bits(1:0): FPGA_EXT_FEA_RST_EDSP\n");
        }
        //black Discreted status

        //black blanks
        if(selectedRegister.getRegisterName().equals("Blank_Emul_En")) {
            registerDescription.setText("Emulation enable for Blank signals\n" +
                    "Bits(15:2): Reserved\n" +
                    "Bits(1:0):\n" +
                    "   \"00\"= Normal operation\n" +
                    "   \"01\"= Emulation enable\n" +
                    "   \"10\"= Drive GNDs\n" +
                    "   \"11\"= Drive VCCs\n");
        }

        if(selectedRegister.getRegisterName().equals("Blank_Rd_Val")) {
            registerDescription.setText("Blank Output MUX Value Reflection\n" +
                    "Bits(15:8): Reserved\n" +
                    "Bits(7:0): Reflects the Blank Output MUX Value\n" +
                    "Note : the value of lower 2-bits of the MUX are outputted to\n \"FPGA_DSP_IOMG_FGPO\"\n");
        }

        if(selectedRegister.getRegisterName().equals("Blank_Emul_Drv_Val")) {
            registerDescription.setText("User defined data for emulation\n" +
                    "Bits(15:8): Reserved\n" +
                    "Bits(7:0): Data for blanks emulation. \n\tR/W\tBlank_Emul_Drv_Val\t 0xbe\n");
        }
        //black blanks

        //black soft reset
        if(selectedRegister.getRegisterName().equals("SOFT_RESET")) {
            registerDescription.setText("TBD");
        }
        //black soft reset


        //black test point MUX
        if(selectedRegister.getRegisterName().equals("tp_sel")) {
            registerDescription.setText("TBD");
        }
        //black test point MUX

        //black gpout cntrl
        if(selectedRegister.getRegisterName().equals("GPOUT_Ctrl")) {
            registerDescription.setText("GPOUT Control Register\n" +
                    "Bits(15:2): Reserved\n" +
                    "Bits(1:0):  FPGA_IO_B_GPO (1:0)\n");
        }
        //black gpout cntrl


        //BLACK INTERRUPT
        if(selectedRegister.getRegisterName().equals("Interrupt Enable")) {
            registerDescription.setText("Interrupt Enable \n" +
                    "Bits (15:1): Reserved\n" +
                    "Bit(0): Interrupt Enable: '1' – The interrupt is enabled.\n" +
                    "                                      '0' – The interrupt is disabled.  \n");
        }

        if(selectedRegister.getRegisterName().equals("Interrupt Mask")) {
            registerDescription.setText("Interrupt Mask Register.\n Per bit masking the interrupt generation.\n The bit map is like as in the \"Interrupt Cause Register\n\" (see below).\n  Writing '1' to a particular bit will disable (masking)\n the interrupt generation\n caused from this bit. At reset (default) \nall interrupt causes are masked [0xFFFF].  \n" +
                    "Note: Masking a bit will disable the interrupt generation\n but not disable the bit report of \nerror/problem/fail-status or clear the bit in the\n \"Interrupt Cause Register\" , \nthis  enables the work in poling mode. \n");
        }

        if(selectedRegister.getRegisterName().equals("Interrupt Cause")) {
            registerDescription.setText("Interrupt Cause Register.\n" +
                    "Bits (15:3): Reserved\n" +
                    "Bit(2): Intterupt Stimulus ('1' – On , '0' – Off)\n" +
                    "Bit(1): 1PPS Failure ('1' – Fail , '0' – O.K.)\n" +
                    "Bit(0): Fan Rotating Failure ('1' – Failure, '0' – O.K.)\n" +
                    "Note: Write  '0' to particular bit to clear the bit.\n");
        }

        if(selectedRegister.getRegisterName().equals("Interrupt Stimulus Generation")) {
            registerDescription.setText("Interrupt Stimulus Generation Register. \n" +
                    "Bits (15:1): Reserved\n" +
                    "Bit(0): Interrupt Generation: '1' – Generate stimulus interrupt.\n" +
                    "                                            '0' – Null.  \n");
        }

        //BLACK INTERRUPT


        //Black Fan
        if(selectedRegister.getRegisterName().equals("Fan_Spd_Ctrl")) {

            registerDescription.setText("Fan Speed Control Register\n" +
                    "Bits (15:3): Reserved\n" +
                    "Bit(2:0) : Fan Speed Setting\n" +
                    " \"000\" – Very Low Speed (TBD RPM)\n" +
                    " \"001\" – Low Speed (TBD RPM)\n" +
                    " \"010\" – Medium-Low Speed (TBD RPM)\n" +
                    " \"011\" – Medium Speed (TBD RPM)\n" +
                    " \"100\" – Medium-High Speed (TBD RPM)\n" +
                    " \"101\" – High Speed (TBD RPM)\n" +
                    " \"110\" – Very High Speed (TBD RPM)\n" +
                    " \"111\" – Fan is Off (0 RPM)\n");
        }
        if(selectedRegister.getRegisterName().equals("Fan_min_bndry")) {
            registerDescription.setText("Fan  Minimum Boundary Alowable in RPM\nR/W");
        }
        if(selectedRegister.getRegisterName().equals("Fan_Speed")) {
            registerDescription.setText("Fan Rotating Speed in RPM \n Read Only");
        }
        if(selectedRegister.getRegisterName().equals("Fan_spd_chken")) {
            registerDescription.setText("Fan Speed Check Enable\n" +
                    "Bits (15:1): Reserved\n" +
                    "Bit(0) : Fan Speed  Check Enable (Active High) \n R/W");
        }

        if(selectedRegister.getRegisterName().equals("Fan_Status")) {
            registerDescription.setText("Fan Speed Status\n" +
                    "Bits (15:1): Reserved\n" +
                    "Bit(0): The Fan speed is less then the Minimum Boundry\n" +
                    "Writing '0' to bit # 0 in this register or writing '0' to bit # 0 in the \"Interrupt Cause Register\" (0x84) clear the bit.\n");
        }

        if(selectedRegister.getRegisterName().equals("Fan_Status")) {
            registerDescription.setText("Fan Speed Status\n" +
                    "Bits (15:1): Reserved\n" +
                    "Bit(0): The Fan speed is less then the Minimum Boundry\n" +
                    "Writing '0' to bit # 0 in this register or writing '0' to bit # 0\n in the \"Interrupt Cause Register\" (0x84) clear the bit.\n\n R/W");
        }
        //Black Fan

        //BLACK temperature sensor
        if(selectedRegister.getRegisterName().equals("Temperature register")) {

            registerDescription.setText("To store measured temperature data\n"+
                    "Read Only (16 bit)");
        }

        if(selectedRegister.getRegisterName().equals("Configuration register")) {

            registerDescription.setText("R/W \n"+
                    "Default Value is 0x00 (8bit)");
        }

        if(selectedRegister.getRegisterName().equals("Hysteresis register")) {

            registerDescription.setText("R/W \n"+
                    "Default Value is 0x4800 (16bit)- 75°C");
        }

        if(selectedRegister.getRegisterName().equals("Overtemperature shutdown")) {

            registerDescription.setText("R/W \n"+
                    "Default Value is 0x5000 (16bit)\n"+
                    "Set point for overtemperature\n" +
                    "shutdown (TOS) limit default = 80 °C");
        }
        //BLACK temperature senso


        //BLACK INA 219
        if(selectedRegister.getRegisterName().equals("Configuration")) {

            registerDescription.setText("All-register reset, settings for bus\n"+
                   "voltage range, PGA Gain, ADC\n"+
                    "resolution/averaging.\n"+
                    "Default Value is :0x399F\n"+
                    "R/W");

        }

        if(selectedRegister.getRegisterName().equals("Shunt voltage")) {

            registerDescription.setText("Shunt voltage measurement data.\n"+
                    "Read Only");

        }
        if(selectedRegister.getRegisterName().equals("Bus voltage")) {

            registerDescription.setText("Bus voltage measurement data\n"+
                    "Read Only");

        }
        if(selectedRegister.getRegisterName().equals("Power")) {

            registerDescription.setText("Power measurement data.\n"+
                    "Read Only");
        }

        if(selectedRegister.getRegisterName().equals("Current")) {

            registerDescription.setText("Contains the value of the current flowing \n"+
                    "through the shunt resistor\n"+
                    "Read Only\n");
        }

        if(selectedRegister.getRegisterName().equals("Calibration")) {

            registerDescription.setText("Sets full-scale range and LSB of current\n"+
                    "and power measurements. Overall\n"+
                    "system calibration.\n\n"+
                    "R/W\n"+
                    "Default Value is 0x0000");
        }
//BLACK INA 219





//BLACK PHY
        if(selectedRegister.getRegisterName().equals("Copper Control")) {
            moveToPhyPage(1,0);
            registerDescription.setText("Bit(15):Copper Reset\n"+
                                        "Bit(14):Loopback\n"+
                                        "Bit(13):Speed Select (LSB)\n"+
                                        "Bit(12):Auto negotiation Enable\n"+
                                        "Bit(11):Power Down\n"+
                                        "Bit(10):Isolate\n"+
                                        "Bit(9) :Restart Copper Auto negotiation\n"+
                                        "Bit(8) :Copper Duplex Mode\n"+
                                        "Bit(7) :Collision Test\n"+
                                        "Bit(6):Speed Select (MSB)\n"+
                                        "Bits(5:0):Reserved\n");

        }
//BLACK PHY

        if(selectedRegister.getRegisterName().equals("id")){
            writeToUartTerminal("spi_util 0x00000000\n",1);
            serialPort.writeString("spi_util 0x00000000\n");
            userSentSomtingToUART=2;
            registerDescription.setText("ID Register  [0x = ASCII \"IOGB\"]");
        }
        if(selectedRegister.getRegisterName().equals("id2")){
            writeToUartTerminal("spi_util 0x00020000\n",1);
            serialPort.writeString("spi_util 0x00020000\n");
            userSentSomtingToUART=2;
            registerDescription.setText("ID Register  [0x = ASCII \"IOGB\"]");
        }
        if(selectedRegister.getRegisterName().equals("ver")){
            writeToUartTerminal("spi_util 0x00040000\n",1);
            serialPort.writeString("spi_util 0x00040000\n");
            userSentSomtingToUART=2;
            registerDescription.setText("Version Register (Version Number)");
        }

        else if(selectedRegister.getRegisterName().equals("date")){
            writeToUartTerminal("spi_util 0x00060000\n",1);
            serialPort.writeString("spi_util 0x00060000\n");
            userSentSomtingToUART=2;
            registerDescription.setText("Date modification register (dd/mm/yyyy - in BCD Format)");
        }
        else if(selectedRegister.getRegisterName().equals("date2")){
            writeToUartTerminal("spi_util 0x00080000\n",1);
            serialPort.writeString("spi_util 0x00080000\n");
            userSentSomtingToUART=2;
            registerDescription.setText("Date modification register (dd/mm/yyyy - in BCD Format)");
        }
        else if(selectedRegister.getRegisterName().equals("D_VER")){
            writeToUartTerminal("spi_util 0x000a0000\n",1);
            serialPort.writeString("spi_util 0x000a0000\n");
            userSentSomtingToUART=2;
            registerDescription.setText("Development Version Register ");
        }

        else if(selectedRegister.getRegisterName().equals("BoardLayoutVersion")){
            writeToUartTerminal("spi_util 0x000c0000\n",1);
            serialPort.writeString("spi_util 0x000c0000\n");
            userSentSomtingToUART=2;

            registerDescription.setText("Board Layout Version\n" +
                    "Bits (15:3): Reserved\n" +
                    "Bits (2:0): Board Layout Version {''001\", \"002\", etc.} [0x2]");
        }
        else if(selectedRegister.getRegisterName().equals("STATUS_REG")){
            writeToUartTerminal("spi_util 0x000e0000\n",1);

            serialPort.writeString("spi_util 0x000e0000\n");
            userSentSomtingToUART=2;

  //          Thread.sleep(1000);
//            bR.readLine();


            registerDescription.setText("Status Register\n" +
                    "Bits (15:11): System Clock (sysclk) Frequency (MHz) \n" +
                    "Bit (10): I2C MASTER to ETC Frequency Code1  \n" +
                    "Bit (9): I2C MASTER to Current Monitor Frequency Code1  \n" +
                    "Bit (8): I2C MASTER to Temp. Sensor Frequency Code1  \n" +
                    "Bits (7:4): MDC/MDIO Frequency (MHz) \n" +
                    "Bit (3): SPI CPOL Setting Value \n" +
                    "Bit (2): SPI CPHA Setting Value \n" +
                    "Bit (1): Ethernet Swtich Initialization is Done (Finished)2 [1]\n" +
                    "Bit (0): The PLL is Locked2  [1]\n ");
        }


    }


    private class PortReader implements SerialPortEventListener {
        private final Consumer<String> textHandler;

        PortReader(Consumer<String> textHandler) {
            this.textHandler = textHandler;
        }

        @Override
        public void serialEvent(SerialPortEvent event) {
            if (event.isRXCHAR()) {
                try {
                    String buffer = serialPort.readString();
                    textHandler.accept(buffer);
                } catch (SerialPortException ex) {
                    System.out.println(ex);
                }
            }
        }
    }


public void set10Mbps_btn_pressed() throws SerialPortException, InterruptedException {
    //write sequence
    writeToUartTerminal("spi_util 0x80400100\n",1);
    serialPort.writeString("spi_util 0x80400100\n");
    Thread.sleep(10);
    writeToUartTerminal("spi_util 0x80420000\n",1);
    serialPort.writeString("spi_util 0x80420000\n");
    Thread.sleep(10);
    writeToUartTerminal("spi_util 0x80468100\n",1);
    serialPort.writeString("spi_util 0x80468100\n");
    Thread.sleep(10);
    writeToUartTerminal("spi_util 0x80480001\n",1);
    serialPort.writeString("spi_util 0x80480001\n");
    Thread.sleep(10);
    writeToUartTerminal("spi_util 0x004a0000\n",1);
    serialPort.writeString("spi_util 0x004a0000\n");
    Thread.sleep(10);
    writeToUartTerminal("spi_util 0x00440000\n",1);
    serialPort.writeString("spi_util 0x00440000\n");

    Thread.sleep(10);
    //read sequence
    writeToUartTerminal("spi_util 0x80400100\n",1);
    serialPort.writeString("spi_util 0x80400100\n");
    Thread.sleep(10);
    writeToUartTerminal("spi_util 0x80420001\n",1);
    serialPort.writeString("spi_util 0x80420001\n");
    Thread.sleep(10);
    writeToUartTerminal("spi_util 0x80480001\n",1);
    serialPort.writeString("spi_util 0x80480001\n");
    Thread.sleep(10);
    writeToUartTerminal("spi_util 0x004a0000\n",1);
    serialPort.writeString("spi_util 0x004a0000\n");
    Thread.sleep(10);
    writeToUartTerminal("spi_util 0x00440000\n",1);
    serialPort.writeString("spi_util 0x00440000\n");


}

    public void set100Mbps_btn_pressed() throws SerialPortException, InterruptedException {
//        spi_util 0x80400100 --  MDIO_Addr , write to phy adress 0 ,select register  22 dec = 16 hex (this is the page select register) - defult value 0000
//        spi_util 0x80420000 --  perform a write operation - 0=write 1=read
//        spi_util 0x8046a100 -- this is the wr vector - fill with 0000 - move to page 4
//        spi_util 0x80480001 -- start execute
//        spi_util 0x004a0000 -- check done status is 1
//        spi_util 0x00440000 -- read register value

        //write sequence
        writeToUartTerminal("spi_util 0x80400100\n",1);
        serialPort.writeString("spi_util 0x80400100\n");
        Thread.sleep(10);
        writeToUartTerminal("spi_util 0x80420000\n",1);
        serialPort.writeString("spi_util 0x80420000\n");
        Thread.sleep(10);
        writeToUartTerminal("spi_util 0x8046a100\n",1);
        serialPort.writeString("spi_util 0x8046a100\n");
        Thread.sleep(10);
        writeToUartTerminal("spi_util 0x80480001\n",1);
        serialPort.writeString("spi_util 0x80480001\n");
        Thread.sleep(10);
        writeToUartTerminal("spi_util 0x004a0000\n",1);
        serialPort.writeString("spi_util 0x004a0000\n");
        Thread.sleep(10);
        writeToUartTerminal("spi_util 0x00440000\n",1);
        serialPort.writeString("spi_util 0x00440000\n");
        Thread.sleep(10);
//        spi_util 0x80400016 --  MDIO_Addr , write to phy adress 0 ,select register  decimal 22 hex = 16
//        spi_util 0x80420001 --  perform a read operation - 0=write 1=read
//        spi_util 0x80480001 --  start execute
//        spi_util 0x004a0000 --  check done status is 1
//        spi_util 0x00440000 --  read register value - verify page register is 0

        //read sequence
        writeToUartTerminal("spi_util 0x80400100\n",1);
        serialPort.writeString("spi_util 0x80400100\n");
        Thread.sleep(10);
        writeToUartTerminal("spi_util 0x80420001\n",1);
        serialPort.writeString("spi_util 0x80420001\n");
        Thread.sleep(10);
        writeToUartTerminal("spi_util 0x80480001\n",1);
        serialPort.writeString("spi_util 0x80480001\n");
        Thread.sleep(10);
        writeToUartTerminal("spi_util 0x004a0000\n",1);
        serialPort.writeString("spi_util 0x004a0000\n");
        Thread.sleep(10);
        writeToUartTerminal("spi_util 0x00440000\n",1);
        serialPort.writeString("spi_util 0x00440000\n");



        System.out.println("btn pressed");


    }

    public void set1000Mbps_btn_pressed() throws SerialPortException, InterruptedException {
        //write sequence
        writeToUartTerminal("spi_util 0x80400100\n",1);
        serialPort.writeString("spi_util 0x80400100\n");
        Thread.sleep(10);
        writeToUartTerminal("spi_util 0x80420000\n",1);
        serialPort.writeString("spi_util 0x80420000\n");
        Thread.sleep(10);
        writeToUartTerminal("spi_util 0x80468140\n",1);
        serialPort.writeString("spi_util 0x80468140\n");
        Thread.sleep(10);
        writeToUartTerminal("spi_util 0x80480001\n",1);
        serialPort.writeString("spi_util 0x80480001\n");
        Thread.sleep(10);
        writeToUartTerminal("spi_util 0x004a0000\n",1);
        serialPort.writeString("spi_util 0x004a0000\n");
        Thread.sleep(10);
        writeToUartTerminal("spi_util 0x00440000\n",1);
        serialPort.writeString("spi_util 0x00440000\n");

        Thread.sleep(10);
        //read sequence
        writeToUartTerminal("spi_util 0x80400100\n",1);
        serialPort.writeString("spi_util 0x80400100\n");
        Thread.sleep(10);
        writeToUartTerminal("spi_util 0x80420001\n",1);
        serialPort.writeString("spi_util 0x80420001\n");
        Thread.sleep(10);
        writeToUartTerminal("spi_util 0x80480001\n",1);
        serialPort.writeString("spi_util 0x80480001\n");
        Thread.sleep(10);
        writeToUartTerminal("spi_util 0x004a0000\n",1);
        serialPort.writeString("spi_util 0x004a0000\n");
        Thread.sleep(10);
        writeToUartTerminal("spi_util 0x00440000\n",1);
        serialPort.writeString("spi_util 0x00440000\n");
    }

    public void writeToPhy_Btn_Pressed() throws InterruptedException, SerialPortException {

        Thread.sleep(100);
        //move to user wanted page:
        writeToUartTerminal("spi_util 0x80400"+phyPort.getText()+"16\n",1);
        serialPort.writeString("spi_util 0x80400"+phyPort.getText()+"16\n");
        Thread.sleep(100);
        writeToUartTerminal("spi_util 0x80420000\n",1);
        serialPort.writeString("spi_util 0x80420000\n");
        Thread.sleep(100);
        writeToUartTerminal("spi_util 0x8046000"+phyPage.getText()+ "\n",1);
        serialPort.writeString("spi_util 0x8046000"+phyPage.getText()+ "\n");
        Thread.sleep(100);
        writeToUartTerminal("spi_util 0x80480001\n",1);
        serialPort.writeString("spi_util 0x80480001\n");
        Thread.sleep(100);
        writeToUartTerminal("spi_util 0x004a0000\n",1);
        serialPort.writeString("spi_util 0x004a0000\n");
        Thread.sleep(100);



        Thread.sleep(100);
        //write to requested register requested data:
        writeToUartTerminal("spi_util 0x80400"+phyPort.getText()+phyRegister.getText()+"\n",1);
        serialPort.writeString("spi_util 0x80400"+phyPort.getText()+phyRegister.getText()+"\n");
        Thread.sleep(100);
        writeToUartTerminal("spi_util 0x80420000\n",1);
        serialPort.writeString("spi_util 0x80420000\n");
        Thread.sleep(100);
        writeToUartTerminal("spi_util 0x8046"+hex3.getText()+hex2.getText()+hex1.getText()+hex0.getText()+"\n",1);
        serialPort.writeString("spi_util 0x8046"+hex3.getText()+hex2.getText()+hex1.getText()+hex0.getText()+"\n");
        Thread.sleep(100);


        writeToUartTerminal("spi_util 0x80480001\n",1);
        serialPort.writeString("spi_util 0x80480001\n");
        Thread.sleep(100);
        writeToUartTerminal("spi_util 0x004a0000\n",1);
        serialPort.writeString("spi_util 0x004a0000\n");
        Thread.sleep(100);


    }

    public void readFromPhy_Btn_Pressed() throws SerialPortException, InterruptedException {

        //move to user wanted page:
//        spi_util 0x80400016 --  MDIO_Addr , write to phy adress 0 ,select register  22 hex = 16 decimal (this is the page select register) - defult value 0000
//        spi_util 0x80420000 --  perform a write operation - 0=write 1=read
//        spi_util 0x80460006 -- this is the wr vector - fill with 0006 - move to page 6
//        spi_util 0x80480001 -- start execute
//        spi_util 0x004a0000 -- check done status is 1


        Thread.sleep(100);
        //move to user wanted page:
        writeToUartTerminal("spi_util 0x80400"+phyPort.getText()+"16\n",1);
        serialPort.writeString("spi_util 0x80400"+phyPort.getText()+"16\n");
        Thread.sleep(100);
        writeToUartTerminal("spi_util 0x80420000\n",1);
        serialPort.writeString("spi_util 0x80420000\n");
        Thread.sleep(100);
        writeToUartTerminal("spi_util 0x8046000"+phyPage.getText()+ "\n",1);
        serialPort.writeString("spi_util 0x8046000"+phyPage.getText()+ "\n");
        Thread.sleep(100);
        writeToUartTerminal("spi_util 0x80480001\n",1);
        serialPort.writeString("spi_util 0x80480001\n");
        Thread.sleep(100);
        writeToUartTerminal("spi_util 0x004a0000\n",1);
        serialPort.writeString("spi_util 0x004a0000\n");
        Thread.sleep(100);


        //read user wanted register
        writeToUartTerminal("spi_util 0x80400"+phyPort.getText()+""+phyRegister.getText()+"\n",1);
        serialPort.writeString("spi_util 0x80400"+phyPort.getText()+""+phyRegister.getText()+"\n");
        Thread.sleep(100);
        writeToUartTerminal("spi_util 0x80420001\n",1);
        serialPort.writeString("spi_util 0x80420001\n");
        Thread.sleep(100);
        writeToUartTerminal("spi_util 0x80480001\n",1);
        serialPort.writeString("spi_util 0x80480001\n");
        Thread.sleep(100);
        writeToUartTerminal("spi_util 0x004a0000\n",1);
        serialPort.writeString("spi_util 0x004a0000\n");
        Thread.sleep(100);
        writeToUartTerminal("spi_util 0x00440000\n",1);
        serialPort.writeString("spi_util 0x00440000\n");
        Thread.sleep(100);
        phyindexInteger=1;
    }

    public void choiceBoxPressed(){
        //RegisterModel selectedRegister = registerTable.getSelectionModel().getSelectedItem();
        //System.out.println(selectedRegister.getRegisterName());

        String selectedDevice=deviceSelect.getSelectionModel().getSelectedItem();
        System.out.println(selectedDevice);

    }

    public void moveToPhyPage(int portNumber,int pageNumber) throws InterruptedException, SerialPortException {
        Thread.sleep(100);
        //move to user wanted page:
        writeToUartTerminal("spi_util 0x80400"+portNumber+"16\n",1);
        serialPort.writeString("spi_util 0x80400"+portNumber+"16\n");
        Thread.sleep(100);
        writeToUartTerminal("spi_util 0x80420000\n",1);
        serialPort.writeString("spi_util 0x80420000\n");
        Thread.sleep(100);
        writeToUartTerminal("spi_util 0x8046000"+pageNumber+ "\n",1);
        serialPort.writeString("spi_util 0x8046000"+pageNumber+ "\n");
        Thread.sleep(100);
        writeToUartTerminal("spi_util 0x80480001\n",1);
        serialPort.writeString("spi_util 0x80480001\n");
        Thread.sleep(100);
        writeToUartTerminal("spi_util 0x004a0000\n",1);
        serialPort.writeString("spi_util 0x004a0000\n");
        Thread.sleep(100);
    }


   public void writeToSensorBtn_Pressed(){

    }


    public void phyInit1_btnPressed(){

    }

    public void phyInit2_btnPressed(){

    }
}
