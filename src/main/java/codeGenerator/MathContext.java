package codeGenerator;

public class MathContext {
    private MathOperationStrategy strategy;

    public void setStrategy(MathOperationStrategy strategy) {
        this.strategy = strategy;
    }

    public void executeStrategy() {
        strategy.execute();
    }
}
