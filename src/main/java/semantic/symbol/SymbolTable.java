package semantic.symbol;


import codeGenerator.Address;
import codeGenerator.Memory;
import codeGenerator.TypeAddress;
import codeGenerator.varType;
import errorHandler.ErrorHandler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SymbolTable {
    private Map<String, Klass> klasses;
    private Map<String, Address> keyWords;
    private Memory mem;
    private SymbolType lastType;

    public SymbolTable(Memory memory) {
        mem = memory;
        klasses = new HashMap<>();
        keyWords = new HashMap<>();
        keyWords.put("true", new Address(1, varType.Bool, TypeAddress.Imidiate));
        keyWords.put("false", new Address(0, varType.Bool, TypeAddress.Imidiate));
    }

    public void setLastType(SymbolType type) {
        lastType = type;
    }

    public void addClass(String className) {
        if (klasses.containsKey(className)) {
            ErrorHandler.printError("This class already defined");
        }
        klasses.put(className, new Klass());
    }

    public void addField(String fieldName, String className) {
        klasses.get(className).getFields().put(fieldName, new Symbol(lastType, mem.getDateAddress()));
    }

    public void addMethod(String className, String methodName, int address) {
        if (klasses.get(className).getMethodes().containsKey(methodName)) {
            ErrorHandler.printError("This method already defined");
        }
        klasses.get(className).getMethodes().put(methodName, new Method(address, lastType));
    }

    public void addMethodParameter(String className, String methodName, String parameterName) {
        klasses.get(className).getMethodes().get(methodName).addParameter(parameterName);
    }

    public void addMethodLocalVariable(String className, String methodName, String localVariableName) {
//        try {
            if (klasses.get(className).getMethodes().get(methodName).getLocalVariable().containsKey(localVariableName)) {
                ErrorHandler.printError("This variable already defined");
            }
            klasses.get(className).getMethodes().get(methodName).getLocalVariable().put(localVariableName, new Symbol(lastType, mem.getDateAddress()));
//        }catch (NullPointerException e){
//            e.printStackTrace();
//        }
    }

    public void setSuperClass(String superClass, String className) {
        klasses.get(className).setSuperClass(klasses.get(superClass));
    }

    public Address get(String keywordName) {
        return keyWords.get(keywordName);
    }

    public Symbol get(String fieldName,String className) {
//        try {
            return klasses.get(className).getField(fieldName);
//        }catch (NullPointerException n)
//        {
//            n.printStackTrace();
//            return null;
//        }
    }

    public Symbol get(String className, String methodName, String variable) {
        Symbol res = klasses.get(className).getMethodes().get(methodName).getVariable(variable);
        if (res == null)
            res = get(variable, className);
        return res;
    }

    public Symbol getNextParam(String className, String methodName) {
        return klasses.get(className).getMethodes().get(methodName).getNextParameter();
    }

    public void startCall(String className,String methodName) {
//        try {
            klasses.get(className).getMethodes().get(methodName).reset();
//        }catch (NullPointerException n)
//        {
//            n.printStackTrace();
//        }
    }

    public int getMethodCallerAddress(String className, String methodName) {
        return klasses.get(className).getMethodes().get(methodName).getCallerAddress();
    }

    public int getMethodReturnAddress(String className, String methodName) {
        return klasses.get(className).getMethodes().get(methodName).getReturnAddress();
    }

    public SymbolType getMethodReturnType(String className, String methodName) {
//        try {
            return klasses.get(className).getMethodes().get(methodName).getReturnType();
//        }catch (NullPointerException ed){
//            ed.printStackTrace();
//            return null;
//        }

    }

    public int getMethodAddress(String className, String methodName) {
        return klasses.get(className).getMethodes().get(methodName).getCodeAddress();
    }


    class Klass {
        private Map<String, Symbol> Fields;
        private Map<String, Method> Methodes;
        private Klass superClass;

        public void setFields(Map<String, Symbol> fields) {
            Fields = fields;
        }

        public Map<String, Symbol> getFields() {
            return Fields;
        }

        public void setMethodes(Map<String, Method> methodes) {
            Methodes = methodes;
        }

        public Map<String, Method> getMethodes() {
            return Methodes;
        }

        public void setSuperClass(Klass superClass) {
            this.superClass = superClass;
        }

        public Klass getSuperClass() {
            return superClass;
        }

        public Klass() {
            Fields = new HashMap<>();
            Methodes = new HashMap<>();
        }

        public Symbol getField(String fieldName) {
            if (Fields.containsKey(fieldName)) {
                return Fields.get(fieldName);
            }
            return superClass.getField(fieldName);

        }

    }

    class Method {
        private int codeAddress;
        private Map<String, Symbol> parameters;
        private Map<String, Symbol> localVariable;
        private List<String> orderdParameters;
        private int callerAddress;
        private int returnAddress;
        private SymbolType returnType;
        private int index;

        public void setCodeAddress(int codeAddress) {
            this.codeAddress = codeAddress;
        }

        public int getCodeAddress() {
            return codeAddress;
        }

        public void setParameters(Map<String, Symbol> parameters) {
            this.parameters = parameters;
        }

        public Map<String, Symbol> getParameters() {
            return parameters;
        }

        public void setLocalVariable(Map<String, Symbol> localVariable) {
            this.localVariable = localVariable;
        }

        public Map<String, Symbol> getLocalVariable() {
            return localVariable;
        }

        public void setCallerAddress(int callerAddress) {
            this.callerAddress = callerAddress;
        }

        public int getCallerAddress() {
            return callerAddress;
        }

        public void setReturnAddress(int returnAddress) {
            this.returnAddress = returnAddress;
        }

        public int getReturnAddress() {
            return returnAddress;
        }

        public void setReturnType(SymbolType returnType) {
            this.returnType = returnType;
        }

        public SymbolType getReturnType() {
            return returnType;
        }

        public Method(int codeAddress, SymbolType returnType) {
            this.codeAddress = codeAddress;
            this.returnType = returnType;
            this.orderdParameters = new ArrayList<>();
            this.returnAddress = mem.getDateAddress();
            this.callerAddress = mem.getDateAddress();
            this.parameters = new HashMap<>();
            this.localVariable = new HashMap<>();
        }

        public Symbol getVariable(String variableName) {
            if (parameters.containsKey(variableName))
                return parameters.get(variableName);
            if (localVariable.containsKey(variableName))
                return localVariable.get(variableName);
            return null;
        }

        public void addParameter(String parameterName) {
            parameters.put(parameterName, new Symbol(lastType, mem.getDateAddress()));
            orderdParameters.add(parameterName);
        }

        private void reset() {
            index = 0;
        }

        private Symbol getNextParameter() {
            return parameters.get(orderdParameters.get(index++));
        }
    }

}

//class Symbol{
//    public SymbolType type;
//    public int address;
//    public Symbol(SymbolType type , int address)
//    {
//        this.type = type;
//        this.address = address;
//    }
//}
//enum SymbolType{
//    Int,
//    Bool
//}