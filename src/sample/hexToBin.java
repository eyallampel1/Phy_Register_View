package sample;

public class hexToBin {
   private String hexNumber;
    private String binNumber;

//   public hexToBin(String hexNumber) {
//        this.hexNumber=hexNumber;
//    }

    public String getHexNumber() {
        return hexNumber;
    }

    public void setHexNumber(String hexNumber) {
        this.hexNumber = hexNumber;
    }

    public String convertHexToBin(String hexNibble){

       switch (hexNibble){
           case "0":
               this.binNumber="0000";
               break;
           case "1":
               this.binNumber="0001";
               break;
           case "2":
               this.binNumber="0010";
               break;
           case "3":
               this.binNumber="0011";
               break;
           case "4":
               this.binNumber="0100";
               break;
           case "5":
               this.binNumber="0101";
               break;
           case "6":
               this.binNumber="0110";
               break;
           case "7":
               this.binNumber="0111";
               break;
           case "8":
               this.binNumber="1000";
               break;
           case "9":
               this.binNumber="1001";
               break;
           case "a":
               this.binNumber="1010";
               break;
           case "b":
               this.binNumber="1011";
               break;
           case "c":
               this.binNumber="1100";
               break;
           case "d":
               this.binNumber="1101";
               break;
           case "e":
               this.binNumber="1110";
               break;
           case "f":
               this.binNumber="1111";
               break;
           default:
               binNumber="0000";
               break;
       }
       return  binNumber;
    }

    public String convertBinToHex(String binString) {
        switch (binString) {
            case "0000":
                this.hexNumber="0";
                break;
            case "0001":
                this.hexNumber="1";
                break;
            case "0010":
                this.hexNumber="2";
                break;
            case "0011":
                this.hexNumber="3";
                break;
            case "0100":
                this.hexNumber="4";
                break;
            case "0101":
                this.hexNumber="5";
                break;
            case "0110":
                this.hexNumber="6";
                break;
            case "0111":
                this.hexNumber="7";
                break;
            case "1000":
                this.hexNumber="8";
                break;
            case "1001":
                this.hexNumber="9";
                break;
            case "1010":
                this.hexNumber="a";
                break;
            case "1011":
                this.hexNumber="b";
                break;
            case "1100":
                this.hexNumber="c";
                break;
            case "1101":
                this.hexNumber="d";
                break;
            case "1110":
                this.hexNumber="e";
                break;
            case "1111":
                this.hexNumber="f";
                break;
        }
        return hexNumber;
    }

}
