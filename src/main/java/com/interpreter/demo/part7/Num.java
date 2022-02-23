package com.interpreter.demo.part7;

import com.interpreter.demo.part7.interpreter.NodeVisitor;

/**
 * @author WangChen
 * @since 2022-02-21 17:57
 **/
public class Num implements AST{

    public Integer value;

    public Num(Token token) {
        this.value = Integer.parseInt(String.valueOf(token.getValue()));
    }


    @Override
    public String toString() {
        return "Num{" +
                "value=" + value +
                '}';
    }

    @Override
    public Integer accept(NodeVisitor nodeVisitor) {
        return nodeVisitor.visit(this);
    }
}
