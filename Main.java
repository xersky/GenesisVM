public class Main {
    public static void main(String[] args) {
        VirtualMachine vm = new VirtualMachine();

        byte[] testByteArray = {00,00,00,00,23,0x0D,00,00,0x0C,00,00,0x08};

        System.out.println(vm.byteInterpreter(testByteArray));
    }
}