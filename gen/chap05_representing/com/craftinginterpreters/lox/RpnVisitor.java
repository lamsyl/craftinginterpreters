package com.craftinginterpreters.lox;

import java.util.List;
import java.util.ArrayList;

class RpnVisitor implements Expr.Visitor<List<String>> {
    public List<String> visitBinaryExpr(Expr.Binary expr) {
        List<String> result = new ArrayList<String>();
        result.addAll(expr.left.accept(this));
        result.addAll(expr.right.accept(this));
        result.add(expr.operator.lexeme);
        return result;
    }

    public List<String> visitGroupingExpr(Expr.Grouping expr) {
        return expr.expression.accept(this);
    }

    public List<String> visitLiteralExpr(Expr.Literal expr) {
        List<String> result = new ArrayList<String>();
        result.add(expr.value.toString());
        return result;
    }

    public List<String> visitUnaryExpr(Expr.Unary expr) {
        List<String> result = new ArrayList<String>();
        result.addAll(expr.right.accept(this));
        result.add(expr.operator.lexeme);
        return result;
    }
}