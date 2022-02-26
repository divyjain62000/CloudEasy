package com.cloudeasy.enums.compilerinterpreter;

public enum CompilerInterpreterEnum {
    C_AND_CPP("C/C++"),
    PYTHON("Python"),
    JAVA8("Java 8 (OpenJDK)"),
    OPEN_JDK("Java 11 (OpenJDK)"),
    NODE_JS("Node.js");

    private String compilerInterpreter;

    CompilerInterpreterEnum(String compilerInterpreter) {
        this.compilerInterpreter = compilerInterpreter;
    }

    public String getCompilerInterpreterName() {
        return compilerInterpreter;
    }
}
