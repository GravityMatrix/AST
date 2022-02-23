package com.interpreter.demo.part7.interpreter;

import com.interpreter.demo.part7.BinOp;
import com.interpreter.demo.part7.Num;

/**
 * @author WangChen
 * @since 2022-02-22 10:00
 **/
public interface NodeVisitor {


    Integer visit(BinOp binOp);


    Integer visit(Num num);


}
