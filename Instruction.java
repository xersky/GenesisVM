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
    SWAP(0x0F);

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
        
            default:
            throw new ExceptionInInitializerError();
        }
    }

}
