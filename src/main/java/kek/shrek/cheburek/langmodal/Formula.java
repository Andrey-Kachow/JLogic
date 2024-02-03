package kek.shrek.cheburek.langmodal;

import java.util.function.Supplier;

public class Formula {

  public static final Formula TRUE = new Formula(FormulaType.TRUE);
  public static final Formula FALSE = not(TRUE);

  private Formula left;
  private Formula right;
  private final FormulaType formulaType;
  private final String atomName;

  private Formula(final FormulaType formulaType) {
    this(formulaType, null);
  }

  private Formula(final FormulaType formulaType, final String atomName) {
    this.formulaType = formulaType;
    this.atomName = atomName;
  }

  private void setRight(final Formula formula) {
    right = formula;
  }

  private void setLeft(final Formula formula) {
    left = formula;
  }

  public static Formula atom(final String atomName) {
    return new Formula(FormulaType.ATOM, atomName);
  }

  public static Formula unaryFormula(final FormulaType formulaType, final Formula formula) {
    final Formula result = new Formula(formulaType);
    result.setRight(formula);
    return result;
  }

  private static Formula binaryFormula(final FormulaType formulaType, final Formula left, final Formula right) {
    final Formula result = unaryFormula(formulaType, right);
    result.setLeft(left);
    return result;
  }

  public static Formula not(final Formula formula) {
    return unaryFormula(FormulaType.NOT, formula);
  }

  public static Formula and(final Formula left, final Formula right) {
    return binaryFormula(FormulaType.AND, left, right);
  }

  public static Formula box(final Formula formula) {
    return unaryFormula(FormulaType.BOX, formula);
  }

  public static Formula or(final Formula left, final Formula right) {
    return not(and(not(left), not(right)));
  }

  public static Formula implies(final Formula left, final Formula right) {
    return or(not(left), right);
  }

  public static Formula ifAndOnlyIf(final Formula left, final Formula right) {
    return and(implies(left, right), implies(right, left));
  }

  public static Formula diamond(final Formula formula) {
    return not(box(not(formula)));
  }

  private static Formula accessAfterFormulaTypeAssertion(final Supplier<Boolean> assertion, final Formula result) {
    if (!assertion.get()) {
      throw new RuntimeException();
    }
    return result;
  }

  public Formula getOperand() {
    return accessAfterFormulaTypeAssertion(formulaType::isUnaryOperation, right);
  }

  public Formula getLeft() {
    return accessAfterFormulaTypeAssertion(formulaType::isBinaryOperation, left);
  }

  public Formula getRight() {
    return accessAfterFormulaTypeAssertion(formulaType::isBinaryOperation, right);
  }

  public FormulaType getFormulaType() {
    return formulaType;
  }
}
