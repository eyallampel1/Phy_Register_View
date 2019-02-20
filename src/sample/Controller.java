package sample;

import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Button;
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
    Button SendToTerminalBTN,phySetSpeed10,phySetSpeed100,phySetSpeed1000,phyRead,phyWrite;


    @FXML
    TextArea uartTerminal;


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

    public void rowSelected() throws SerialPortException, InterruptedException, IOException {
        ///show the selected register


        RegisterModel selectedRegister = registerTable.getSelectionModel().getSelectedItem();
        System.out.println(selectedRegister.getRegisterName());

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

}
