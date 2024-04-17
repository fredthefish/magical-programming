package net.meep.magicprogramming.interpreter;

import net.meep.magicprogramming.interpreter.Classes.*;

import java.util.*;

public class LexerParser {
    public LexerParser() {}

    public List<Token> Lexer(String spell) {
        StringBuilder tokenString = new StringBuilder();
        List<Token> tokens = new ArrayList<>();
        boolean isString = false;
        boolean isComment = false;
        for(int i = 0; i < spell.length(); i++) {
            char c = spell.charAt(i);
            if (isComment) {
                if (c == '\n') {
                    isComment = false;
                }
            } else {
                if (isString) {
                    if (c == '"') {
                        tokens.add(new Token(TokenType.STRING, tokenString.toString()));
                        tokenString = new StringBuilder();
                        isString = false;
                    }  else tokenString.append(c);
                } else {
                    if (!(c == ' ' || c == '\n')) {
                        if (c == '(' || c == ')' || c == ',') {
                            Token newToken = addToken(spell, i, tokenString.toString());
                            if (newToken != null) tokens.add(newToken);
                            tokenString = new StringBuilder();
                            if (c == '(') tokens.add(new Token(TokenType.OPEN, null));
                            if (c == ')') tokens.add(new Token(TokenType.CLOSE, null));
                        } else if (c == '"') {
                            Token newToken = addToken(spell, i, tokenString.toString());
                            if (newToken != null) tokens.add(newToken);
                            tokenString = new StringBuilder();
                            isString = true;
                        } else if (c == '#') {
                            Token newToken = addToken(spell, i, tokenString.toString());
                            if (newToken != null) tokens.add(newToken);
                            tokenString = new StringBuilder();
                            isComment = true;
                        } else {
                            tokenString.append(c);
                        }
                    }
                }
            }
        }
        return tokens;
    }
    private Token addToken(String spell, int index, String tokenString) {
        if (tokenString.isEmpty()) return null;
        String part;
        String argumentName = "";
        StringBuilder partBuilder = new StringBuilder();
        for (int i = 0; i < tokenString.length(); i++) {
            char c = tokenString.charAt(i);
            if (c == ':') {
                argumentName = partBuilder.toString();
                partBuilder = new StringBuilder();
            } else partBuilder.append(c);
        }
        part = partBuilder.toString();
        Token token;
        if (spell.charAt(index) == '(') token = new Token(TokenType.FUNCTION, part);
        else token = new Token(TokenType.LITERAL, part);
        token.argumentName = argumentName;
        return token;
    }
    public List<ParserTreeNode> Parser(List<Token> tokens) {
        List<ParserTreeNode> parsed = new ArrayList<>();
        int layers = 0;
        for (int i = 0; i < tokens.size(); i++) {
            Token token = tokens.get(i);
            if (token.type == TokenType.FUNCTION) {
                layers++;
                ParserTreeNode node = new ParserTreeNode(token);
                parsed.add(node);
                List<Token> arguments = new ArrayList<>();
                tokens.remove(i+1); //Remove opening parenthesis.
                for (int j = i + 1; j < tokens.size(); j++) {
                    Token token1 = tokens.get(j);
                    if (token1.type == TokenType.OPEN) {
                        layers++;
                    }
                    if (token1.type == TokenType.CLOSE) {
                        layers--;
                    }
                    if (layers > 0) {
                        arguments.add(tokens.get(j));
                        tokens.remove(j);
                        j--;
                    } else {
                        tokens.remove(j);
                        break;
                    }
                }
                node.children = Parser(arguments);
            } else {
                if (token.type == TokenType.STRING) token.value = new Data(DataType.STRING, token.value);
                parsed.add(new ParserTreeNode(token));
            }
        }
        //Give an error if there are no closing parenthesis or too many closing parenthesis.
        if (layers != 0) return null;
        return parsed;
    }
}