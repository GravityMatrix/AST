package com.interpreter.demo.part7.interpreter;

import com.interpreter.demo.part7.*;

/**
 * @author WangChen
 * @since 2022-02-22 10:38
 **/
public class Interpreter implements NodeVisitor{

    private Parser parser;

    public Interpreter(Parser parser) {
        this.parser = parser;
    }


    @Override
    public Integer visit(BinOp binOp) {
        if (Token.Type.PLUS.equals(binOp.op.getType())) {
            return binOp.left.accept(this) + binOp.right.accept(this);
        } else if (Token.Type.SUB.equals(binOp.op.getType())) {
            return binOp.left.accept(this) - binOp.right.accept(this);
        } else if (Token.Type.MUL.equals(binOp.op.getType())) {
            return binOp.left.accept(this) * binOp.right.accept(this);
        } else if (Token.Type.DIV.equals(binOp.op.getType())) {
            return binOp.left.accept(this) / binOp.right.accept(this);
        }
        throw new RuntimeException("not support visit!");
    }

    @Override
    public Integer visit(Num num) {
        return num.value;
    }


    public Integer interpret(){
        return parser.parse().accept(this);
    }

}
