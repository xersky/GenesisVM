import java.io.FileNotFoundException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

public class VirtualMachine {

    public Optional<Integer> byteInterpreter(byte[] byteChunk) {

        State stack = new State();
        byte[] memory = stack.getMemory();
        String databaseFilename = "Database.json";
        String databaseJson = new String();
        try {
            databaseJson = Utils.readFromFile(databaseFilename);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        
        for(int pc = 0; pc < byteChunk.length; pc++) {

            Instruction opCode = Instruction.fromInt(byteChunk[pc]);
            System.out.println(opCode);

                switch (opCode) {

                    case PUSH:
                        Integer argToPush = byteChunkMerger(byteChunk, pc + 1, 4);

                        stack.getStack().push(argToPush);
                        pc += 4;
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
                        Integer argToJump = stack.getStack().pop();

                        pc = argToJump;
                        if(Instruction.fromInt(byteChunk[pc]) != Instruction.JUMPDEST) throw new IllegalAccessError();
                        break;

                    case CJUMP:
                        Integer argToCJump = stack.getStack().pop();
                        boolean conditionInteger = stack.getStack().pop() != 0;

                        if(conditionInteger) {
                            pc = argToCJump;
                            System.out.println(pc);
                            if(Instruction.fromInt(byteChunk[pc]) != Instruction.JUMPDEST) throw new IllegalAccessError();
                        }
                        
                        break;

                    case JUMPDEST:
                        break;

                    case LOAD:
                        Integer indexDLoad = stack.getStack().pop();
                        Integer argToDload = byteChunkMerger(memory, indexDLoad * 4 , 4);

                        stack.getStack().push(argToDload); 
                        break;

                    case STORE:
                        Integer indexDStore = stack.getStack().pop();
                        Integer argToDStore = stack.getStack().pop();

                        byte[] byteToDStore = ByteBuffer.allocate(4).putInt(argToDStore).array();

                        for(int j = indexDStore * 4, byteIndexIterator = 0; j < indexDStore * 4 + 4; memory[j++] = byteToDStore[byteIndexIterator++]);

                        break;

                    case DUP:
                        Integer indexToDup = stack.getStack().pop();
                        Integer itemToDup = stack.getStack().elementAt(stack.getStack().size() - 1 - indexToDup);

                        stack.getStack().push(itemToDup);
                        break;

                    case SWAP:
                        Integer indexToSwap = stack.getStack().pop();
                        Integer itemToSwap = stack.getStack().elementAt(stack.getStack().size() - 1 - indexToSwap);

                        stack.getStack().insertElementAt(stack.getStack().pop(), stack.getStack().size() - indexToSwap);
                        stack.getStack().remove(indexToSwap);
                        stack.getStack().push(itemToSwap);
                        break;

                    case EXEC:
                        Integer byteCodeHash = byteChunkMerger(byteChunk, pc + 1, 4);
                        System.out.println(byteCodeHash);
                        Map<String,String> mapOfHashes = Utils.jsonParser(databaseJson).get(0);
                        String byteCode = mapOfHashes.get(String.valueOf(byteCodeHash));
                        byte[] byteArray = Utils.hexStringParser(byteCode);
                        Optional<Integer> byteExecuted = byteInterpreter(byteArray);

                        byteExecuted.ifPresent(v -> stack.getStack().push(v));

                        pc += 4;
                        break;

                    case GT:
                        calc(stack, (args) -> args[0] > args[1] ? 1 : 0);
                        break;

                    case LT:
                        calc(stack, (args) -> args[0] < args[1] ? 1 : 0);
                        break;

                    case EQ:
                        calc(stack, (args) -> args[0] == args[1] ? 1 : 0);
                        break;
                    
                    case NOT:
                        stack.getStack().push(stack.getStack().pop() != 0 ? 0 : 1);
                        break;

                    case LHS:
                        calc(stack, (args) -> args[0] << args[1]);
                        break;

                    case RHS:
                        calc(stack, (args) -> args[0] >> args[1]);
                        break;

                    case NEG:
                        Integer argToNeg = stack.getStack().pop();
                        stack.getStack().push(~argToNeg);
                        break;
                    
                    case AND:
                        calc(stack, (args) -> args[0] & args[1]);
                        break;

                    case OR:
                        calc(stack, (args) -> args[0] | args[1]);
                        break;

                    case XOR:
                        calc(stack, (args) -> args[0] ^ args[1]);
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
        ByteBuffer arg = ByteBuffer.wrap(byteChunk, index, numberOfBytes);
        return numberOfBytes == 2 ? arg.getShort() : arg.getInt();
    }

    public List<String> byteToMnemonicsArray(byte[] byteCode) {

        List<String> mnemonics = new ArrayList<String>();

        for(int i = 0; i < byteCode.length; i++) {

            Instruction opCode = Instruction.fromInt(byteCode[i]);
            
            mnemonics.add(opCode.toString());

            switch (opCode) {
                
                case PUSH:
                    mnemonics.add(byteChunkMerger(byteCode, i + 1, 4).toString());
                    i += 4;
                    break;
                default:
                    break;
            }
        } 

        return mnemonics;
    }
    
    public String byteToMnemonicsString(byte[] byteCode) {

        StringBuffer mnemonics = new StringBuffer();

        for(int i = 0; i < byteCode.length; i++) {

            Instruction opCode = Instruction.fromInt(byteCode[i]);
            
            mnemonics.append(opCode.toString() + " ");

            switch (opCode) {
                
                case PUSH:
                    mnemonics.append(byteToString(byteCode, i + 1, 4));
                    i += 4;
                    break;

                default:
                    mnemonics.append("\n");
                    break;
            }
        } 

        return mnemonics.toString().trim();
    }

    public void byteToMnemonics(byte[] byteCode) {

        for(int i = 0; i < byteCode.length; i++) {

            Instruction opCode = Instruction.fromInt(byteCode[i]);
            
            System.out.print(opCode.toString() + " ");

            switch (opCode) {
                
                case PUSH:
                    byteDisplayer(byteCode, i + 1, 4);
                    System.out.println();
                    i += 4;
                    break;

                default:
                    System.out.println();
                    break;
            }
        } 
    }
    
    public void byteDisplayer(byte[] byteCode, int indexOfByteCode, int range) {
        for(int i = indexOfByteCode; i < indexOfByteCode + range; System.out.format("%02X ", byteCode[i++]));
    } 

    public String byteToString(byte[] byteCode, int indexOfByteCode, int range) {
        StringBuffer bytes = new StringBuffer();
        for(int i = indexOfByteCode; i < indexOfByteCode + range; bytes.append(String.format("%02X ", byteCode[i++])));
        bytes.append("\n");
        return bytes.toString();
    } 

    public byte[] mnemonicsToByteCode(String mnemonics) {
        String[] mnemonicsArray = mnemonics.split(" ");
        byte[] byteCode = new byte[mnemonicsArray.length];

        for(int i = 0; i < mnemonicsArray.length; i++) {
            try { 
                byteCode[i] = (byte) Instruction.valueOf(mnemonicsArray[i].toUpperCase()).byteValue;
            } catch (Exception e) {
                byteCode[i] = (byte) Integer.parseInt(mnemonicsArray[i], 16);
            } 
        }
        return byteCode;
    }

    public String regionMnemonicsToMnemonics(String regionMnemonics) {
        String[] mnemonicsArray = regionMnemonics.split(" ");
        StringBuilder mnemonics = new StringBuilder();
        Map<String,Integer> regions = new HashMap<String,Integer>();
        int appenderCounter = 0;

        for(int i = 0; i < mnemonicsArray.length; i++) {
            try { 
                mnemonics.append("\n" + Instruction.valueOf(mnemonicsArray[i].toUpperCase()));
            } catch (Exception e) {
                if (mnemonicsArray[i].endsWith(":")) {
                    regions.put(mnemonicsArray[i], appenderCounter);
                    mnemonics.append("\nJUMPDEST");
                } else if(mnemonicsArray[i].equals("GOTO")){
                    mnemonics.append("\n" + Instruction.PUSH);
                    byte[] immediants = ByteBuffer.allocate(4).putInt(regions.get(mnemonicsArray[++i] + ":")).array();
                    for (byte immediant: immediants) {
                        mnemonics.append(" " + String.format("%02X", immediant));
                    }
                    mnemonics.append("\n" + Instruction.JUMP);
                } else mnemonics.append(" " + mnemonicsArray[i]);

            } 
            appenderCounter++;
        }    

        return mnemonics.toString();
    }
    
}
