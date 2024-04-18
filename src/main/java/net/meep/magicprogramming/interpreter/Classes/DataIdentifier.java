package net.meep.magicprogramming.interpreter.Classes;

public class DataIdentifier {
    public String string;
    public DataIdentifier(String string) {
        this.string = string;
    }

    @Override
    public String toString() {
        return "IDENTIFIER " + string;
    }
}
