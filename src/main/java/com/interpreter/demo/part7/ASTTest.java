package com.interpreter.demo.part7;

import com.interpreter.demo.part7.interpreter.Interpreter;

/**
 * @author WangChen
 * @since 2022-02-21 18:00
 **/
public class ASTTest {

    public static void main(String[] args) {

        Parser parser = new Parser(new Lexer("2 * (1 + 2)"));

        Interpreter interpreter = new Interpreter(parser);

        System.out.println(interpreter.interpret());
    }
}
