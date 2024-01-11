public enum Instruction {
    
    PUSH(0x00),
    POP(0x01),
    ADD(0x02),
    MUL(0x03),
    DIV(0x04),
    SUB(0x05),
    POW(0x06),
    MOD(0x07),
    RETURN(0x08),
    STOP(0x09),
    JUMP(0x0A),
    CJUMP(0x0B),
    LOAD(0x0C),
    STORE(0x0D),
    DUP(0x0E),
    SWAP(0x0F),
    GT(0x10),
    LT(0x11),
    EQ(0x12),
    LHS(0x13),
    RHS(0x14),
    NEG(0x15),
    AND(0x16),
    OR(0x17),
    XOR(0x18),
    JUMPDEST(0x19),
    NOT(0x1A),
    EXEC(0x1B),
    SLOAD(0x1C),
    SSTORE(0x1D);

    public int byteValue;

    Instruction(int byteValue) {
        this.byteValue = byteValue;
    }

    public static Instruction fromInt(int n) {
        switch (n) {
            case 0x00:
                return PUSH;

            case 0x01:
                return POP;

            case 0x02:
                return ADD;

            case 0x03:
                return MUL;

            case 0x04:
                return DIV;

            case 0x05:
                return SUB;

            case 0x06:
                return POW;

            case 0x07:
                return MOD;

            case 0x08:
                return RETURN;

            case 0x09:
                return STOP;

            case 0x0A:
                return JUMP;
            
            case 0x0B:
                return CJUMP;
            
            case 0x0C:
                return LOAD;
            
            case 0x0D:
                return STORE;
            
            case 0x0E:
                return DUP;

            case 0x0F:
                return SWAP;
            
            case 0x10:
                return GT;
            
            case 0x11:
                return LT;

            case 0x12:
                return EQ;

            case 0x13:
                return LHS;

            case 0x14:
                return RHS;

            case 0x15:
                return NEG;

            case 0x16:
                return AND;

            case 0x17:
                return OR;

            case 0x18:
                return XOR;
            
            case 0x19:
                return JUMPDEST;
            
            case 0x1A:
                return NOT;

            case 0x1B:
                return EXEC;
            
            case 0x1C:
                return SLOAD;

            case 0x1D:
                return SSTORE;
        
            default:
            throw new ExceptionInInitializerError();
        }
    }

}
