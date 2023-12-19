import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.Optional;
import java.util.function.Function;

public class VirtualMachine {

    public Optional<Integer> byteInterpreter(byte[] byteChunk) {

        State stack = new State();
        byte[] memory = stack.getMemory();

        for(int i = 0; i < byteChunk.length; i++) {

            Instruction opCode = Instruction.fromInt(byteChunk[i]);
            System.out.println(opCode);

                switch (opCode) {

                    case PUSH:
                        Integer argToPush = byteChunkMerger(byteChunk, i, 4);

                        System.out.println(" " + argToPush);

                        stack.getStack().push(argToPush);
                        i += 4;
                        break;

                    case POP:
                        stack.getStack().pop();
                        break;

                    case ADD:
                        calc(stack, (args) -> args[0] + args[1]);                    
                        break;

                    case MUL:
                        calc(stack, (args) -> args[0] * args[1]); 
                        break;

                    case SUB:
                        calc(stack, (args) -> args[0] - args[1]); 
                        break;

                    case DIV:
                        calc(stack, (args) -> args[0] / args[1]); 
                        break;

                    case POW:
                        calc(stack, (args) -> (int) Math.pow(args[0].doubleValue(), args[1].doubleValue()));
                        break;

                    case MOD:
                        calc(stack, (args) -> args[0] % args[1]);
                        break;

                    case RETURN:
                        return Optional.of(stack.getStack().peek());

                    case STOP:
                        return Optional.empty();

                    case JUMP:
                        Integer argToJump = byteChunkMerger(byteChunk, i, 2);

                        System.out.println(" " + argToJump);

                        i += 2 + argToJump;
                        break;

                    case CJUMP:
                        Integer conditionInteger = stack.getStack().pop() != 0 ?  1 : 0;
                        Integer argToCJump = byteChunkMerger(byteChunk, i, 2);

                        System.out.println(" " + argToCJump);

                        i += 2 + argToCJump * conditionInteger;
                        break;

                    case LOAD:
                        Integer indexLoader = byteChunkMerger(byteChunk, i, 2);
                        Integer argToLoad = byteChunkMerger(memory, indexLoader - 1, 4);

                        stack.getStack().push(argToLoad);

                        i += 2;
                        break;

                    case STORE:
                        Integer indexStore = byteChunkMerger(byteChunk, i, 2);
                        Integer argToStore = stack.getStack().pop();

                        byte[] byteToStore = ByteBuffer.allocate(4).putInt(argToStore).array();

                        for(int j = indexStore, byteIndexIterator = 0; j < indexStore + 4; memory[j++] = byteToStore[byteIndexIterator++]);

                        i += 2;
                        break;

                    case DUP:
                        break;

                    case SWAP:
                        break;
                }
            } 

        return Optional.empty();
    }
    
    public void calc(State stack, Function<Integer[], Integer> operation) {
        Integer arg1 = stack.getStack().pop();
        Integer arg2 = stack.getStack().pop();

        Integer[] args = {arg1, arg2};

        stack.getStack().push(operation.apply(args));
    }

    public Integer byteChunkMerger(byte[] byteChunk, int index, int numberOfBytes) {
        ByteBuffer arg = ByteBuffer.wrap(byteChunk, index + 1, numberOfBytes);
        return numberOfBytes == 2 ? arg.getShort() : arg.getInt();
    }
}
