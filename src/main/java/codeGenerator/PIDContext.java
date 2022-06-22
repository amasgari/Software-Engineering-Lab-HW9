package codeGenerator;

import scanner.token.Token;

public class PIDContext {
    private PIDStrategy strategy;

    public void setStrategy(PIDStrategy strategy) {
        this.strategy = strategy;
    }

    public void executeStrategy(Token token) {
        strategy.execute(token);
    }
}
