import java.util.List;

public class Main {
    public static void main(String[] args) {
        VirtualMachine vm = new VirtualMachine();

        byte[] summationOfTwentyThreeByteArray = {
            //push 23
            00,00,00,00,0x17,
            //push 1
            00,00,00,00,01,
            //store 00 01
            00,00,00,00,01,
            0x0D,
            //store 00 00
            00,00,00,00,00,
            0x0D,
            //push 0
            00,00,00,00,00,
            //push 1
            00,00,00,00,01,
            //store 00 03
            00,00,00,00,03,
            0x0D,
            //store 00 02
            00,00,00,00,02,
            0x0D,
            //load 00 02 -starting the loop
            0x19,
            00,00,00,00,02,
            0x0C,
            //load 00 03
            00,00,00,00,03,
            0x0C,
            //add
            02,
            //store 00 02
            00,00,00,00,02,
            0x0D,
            //load 00 03
            00,00,00,00,03,
            0x0C,
            //push 1
            00,00,00,00,01,
            //add
            02,
            //store 00 03
            00,00,00,00,03,
            0x0D,
            //load 00 03
            00,00,00,00,03,
            0x0C,
            //load 00 00,
            00,00,00,00,00,
            0x0C,
            //eq
            0x12,
            0x1A,
            00,00,00,00,0x2C,
            //cjump - conditional jump to loop
            0x0B,
            //load 00 02
            00,00,00,00,02,
            0x0C,
            //load 00 00
            00,00,00,00,00,
            0x0C,
            //add - last argument
            02,
            //return
            0x08 

        };
        
       /*  String mnemonics = "PUSH 00 00 00 17 PUSH 00 00 00 01 STORE 00 01 STORE 00 00 PUSH 00 00 00 00 PUSH 00 00 00 01 STORE 00 03 STORE 00 02 LOAD 00 02 LOAD 00 03 ADD STORE 00 02 LOAD 00 03 PUSH 00 00 00 01 ADD STORE 00 03 LOAD 00 03 LOAD 00 00 EQ CJUMP FF E0 LOAD 00 02 LOAD 00 00 ADD RETURN";

        byte[] byteCode = vm.mnemonicsToByteCode(mnemonics);

        System.out.println(vm.byteInterpreter(byteCode)); */

        //System.out.println(vm.byteInterpreter(summationOfTwentyThreeByteArray));

        byte[] byteCode = {00,00,00,00,01,00,00,00,00,00,0x0D,00,00,00,00,03,00,00,00,00,01,0x0D,00,00,00,00,01,0x0C,0x08};

        System.out.println(vm.byteToMnemonicsString(summationOfTwentyThreeByteArray));
        
    }
}