package com.interpreter.demo.part7;

import com.interpreter.demo.part7.interpreter.NodeVisitor;

/**
 * @author WangChen
 * @since 2022-02-21 17:56
 **/
public class BinOp implements AST{

    public AST left;
    public Token op;
    public AST right;

    public BinOp(AST left, Token op, AST right) {
        this.left = left;
        this.op = op;
        this.right = right;
    }

    @Override
    public String toString() {
        return "BinOp{" +
                "left=" + left.toString() +
                ", op=" + op.toString() +
                ", right=" + right.toString() +
                '}';
    }

    @Override
    public Integer accept(NodeVisitor nodeVisitor) {
        return nodeVisitor.visit(this);
    }
}
