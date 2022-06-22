package codeGenerator;
import scanner.token.Token;

public interface PIDStrategy {
    public void execute(Token token);
}
