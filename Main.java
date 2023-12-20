public class Main {
    public static void main(String[] args) {
        VirtualMachine vm = new VirtualMachine();

        byte[] summationOfTwentyThreeByteArray = {
            //push 23
            00,00,00,00,0x17,
            //push 1
            00,00,00,00,01,
            //store 00 01
            0x0D,00,01,
            //store 00 00
            0x0D,00,00,
            //push 0
            00,00,00,00,00,
            //push 1
            00,00,00,00,01,
            //store 00 03
            0x0D,00,03,
            //store 00 02
            0x0D,00,02,
            //load 00 02 -starting the loop
            0x0C,00,02,
            //load 00 03
            0x0C,00,03,
            //add
            02,
            //store 00 02
            0x0D,00,02,
            //load 00 03
            0x0C,00,03,
            //push 1
            00,00,00,00,01,
            //add
            02,
            //store 00 03
            0x0D,00,03,
            //load 00 03
            0x0C,00,03,
            //load 00 00,
            0x0C,00,00,
            //eq
            0x12,
            //cjump - conditional jump to loop
            0x0B,(byte) 0xFF,(byte) 0xE0,
            //load 00 02
            0x0C,00,02,
            //load 00 00
            0x0C,00,00,
            //add - last argument
            02,
            //return
            0x08 

        };

        System.out.println(vm.byteInterpreter(summationOfTwentyThreeByteArray));
    }
}