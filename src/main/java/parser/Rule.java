package parser;

import scanner.token.Token;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mohammad hosein on 6/25/2015.
 */
public class Rule {
    public Rule(String stringRule) {
        int index = stringRule.indexOf("#");
        if (index != -1) {
            try {
            semanticAction = Integer.parseInt(stringRule.substring(index + 1));
            }catch (NumberFormatException ex){
                semanticAction = 0;
            }
            stringRule = stringRule.substring(0, index);
        } else {
            semanticAction = 0;
        }
        String[] splited = stringRule.split("->");
//        try {
            LHS = NonTerminal.valueOf(splited[0]);
//        }catch (Exception e){
//            e.printStackTrace();
//        }
        RHS = new ArrayList<GrammarSymbol>();
        if (splited.length > 1) {
            String[] RHSs = splited[1].split(" ");
            for (String s : RHSs){
                try {
                    RHS.add(new GrammarSymbol(NonTerminal.valueOf(s)));
                } catch (Exception e) {
//                    try{
                        RHS.add(new GrammarSymbol(new Token(Token.getTypeFormString(s), s)));
//                    }catch (IllegalArgumentException d){
//                        d.printStackTrace();
//                        Log.print(s);
//                    }
                }
            }
        }
    }
    private NonTerminal LHS;
    private List<GrammarSymbol> RHS;
    private int semanticAction;

    public void setLHS(NonTerminal LHS){
        this.LHS = LHS;
    }

    public NonTerminal getLHS() {
        return LHS;
    }

    public void setRHS(List<GrammarSymbol> RHS) {
        this.RHS = RHS;
    }

    public List<GrammarSymbol> getRHS() {
        return RHS;
    }

    public void setSemanticAction(int semanticAction) {
        this.semanticAction = semanticAction;
    }

    public int getSemanticAction() {
        return semanticAction;
    }
}

class GrammarSymbol{
    private boolean isTerminal;
    private NonTerminal nonTerminal;
    private Token terminal;



    public GrammarSymbol(NonTerminal nonTerminal)
    {
        this.nonTerminal = nonTerminal;
        isTerminal = false;
    }
    public GrammarSymbol(Token terminal)
    {
        this.terminal = terminal;
        isTerminal = true;
    }
}