package com.interpreter.demo.part7;

import com.interpreter.demo.part7.interpreter.NodeVisitor;

import java.io.Serializable;

/**
 * @author WangChen
 * @since 2022-02-21 17:54
 **/
public interface AST extends Serializable {

    Integer accept(NodeVisitor nodeVisitor);

}
