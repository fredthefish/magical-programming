package net.meep.magicprogramming.interpreter.Classes;

import java.util.HashMap;

public class VirtualEnvironment {
    public HashMap<String, Object> variables;

    public VirtualEnvironment() {
        variables = new HashMap<>();
    }
}
