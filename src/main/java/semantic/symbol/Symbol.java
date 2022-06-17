package semantic.symbol;

/**
 * Created by mohammad hosein on 6/28/2015.
 */
public class Symbol{
    private SymbolType type;
    private int address;

    public void setType(SymbolType type) {
        this.type = type;
    }

    public SymbolType getType() {
        return type;
    }

    public void setAddress(int address) {
        this.address = address;
    }

    public int getAddress() {
        return address;
    }

    public Symbol(SymbolType type , int address)
    {
        this.type = type;
        this.address = address;
    }
}
