package kek.shrek.cheburek.langmodal.kripke;

import kek.shrek.cheburek.langmodal.Formula;

public class KripkeSemantics<W> {

  private KripkeModel<W> model;

  private boolean givenWorldInTheModelEntails(final W world, final Formula formula) {
    return switch (formula.getFormulaType()) {
      case ATOM -> model.givenFormulaIsTrueAtWorld(formula, world);
      case TRUE -> true;
      case NOT -> !givenWorldInTheModelEntails(world, formula.getOperand());
      case AND -> givenWorldInTheModelEntails(world, formula.getLeft())
          && givenWorldInTheModelEntails(world, formula.getRight());
      case BOX, G -> model.getRelation().getAllOutputsOf(world).stream()
        .allMatch(outputWorld -> model.givenFormulaIsTrueAtWorld(formula, outputWorld));
      case H -> model.getRelation().getAllInputsOf(world).stream()
        .allMatch(outputWorld -> model.givenFormulaIsTrueAtWorld(formula, outputWorld));
    };
  }

  private boolean isTrueInAModel(final Formula formula) {
    // circular dependencies?
    return model.getUniverse().stream().allMatch(world -> model.givenFormulaIsTrueAtWorld(formula, world));
  }
}
