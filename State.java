import java.util.Stack;

public class State {
    
    Stack<Integer> stack = new Stack<Integer>();

    byte[] memory = new byte[2048];

    public Stack<Integer> getStack() {
        return this.stack;
    }

    public byte[] getMemory() {
        return this.memory;
    }

}
