package net.meep.magicprogramming.interpreter.Classes;

public class Token {
    public TokenType type;
    public Object value;
    public String argumentName = "";

    public Token(TokenType type, Object value) {
        this.type = type;
        this.value = value;
    }

    @Override
    public String toString() {
        return "(" + type.toString() + "," + value + ")";
    }
}