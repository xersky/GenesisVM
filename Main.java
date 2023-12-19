public class Main {
    public static void main(String[] args) {
        VirtualMachine vm = new VirtualMachine();

        byte[] testByteArray = {00,00,00,00,23,00,00,00,00,07,0x0E,00,00,0x0F,00,02,0x08};

        System.out.println(vm.byteInterpreter(testByteArray));
    }
}