package net.meep.magicprogramming.interpreter.Classes;

import java.util.List;

public class ParserTreeNode {
    public Token token;
    public List<ParserTreeNode> children;
    public String argumentName;

    public ParserTreeNode(Token token) {
        argumentName = token.argumentName;
        this.token = token;
    }

    @Override
    public String toString() {
        return ((argumentName != null) ? ": " : "") + token + " - " + children;
    }
}
