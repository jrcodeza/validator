package com.jrcodeza.validator.predefined;

import java.util.ArrayList;
import java.util.Stack;

public final class Utils {

    private Utils() {
        throw new AssertionError();
    }

    public static String pathAsString(Stack<String> pathAsStack) {
        return String.join(".", new ArrayList<>(pathAsStack));
    }

}
