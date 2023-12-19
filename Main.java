public class Main {
    public static void main(String[] args) {
        VirtualMachine vm = new VirtualMachine();

        byte[] testByteArray = {00,00,00,00,01,00,00,00,00,03,0x11,00,05,00,00,00,00,04,0x08};

        System.out.println(vm.byteInterpreter(testByteArray));
    }
}