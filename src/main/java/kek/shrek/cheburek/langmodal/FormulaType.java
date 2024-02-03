package kek.shrek.cheburek.langmodal;

public enum FormulaType {
  TRUE(Arity.NULLARY),
  ATOM(Arity.NULLARY),
  NOT(Arity.UNARY),
  AND(Arity.BINARY),
  BOX(Arity.UNARY),
  G(Arity.UNARY),
  H(Arity.UNARY),
  ;

  private final Arity arity;

  FormulaType(Arity arity) {
    this.arity = arity;
  }

  public boolean isUnaryOperation() {
    return arity == Arity.UNARY;
  }

  public boolean isBinaryOperation() {
    return arity == Arity.BINARY;
  }
}
