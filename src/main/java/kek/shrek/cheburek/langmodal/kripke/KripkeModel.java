package kek.shrek.cheburek.langmodal.kripke;

import kek.shrek.cheburek.langmodal.Formula;
import kek.shrek.cheburek.langmodal.kripke.relation.Relation;

import java.util.Set;

public class KripkeModel<W> {
  private final KripkeFrame<W> frame;
  private final Assignment assignment;

  public KripkeModel(KripkeFrame<W> frame, Assignment assignment) {
    this.frame = frame;
    this.assignment = assignment;
  }

  /**
   * (M, w) |= A
   *
   * @param formula is A
   * @param world   is w
   */
  public boolean givenFormulaIsTrueAtWorld(final Formula formula, final W world) {
    return assignment.worldsWhereHolds(formula).contains(world);
  }

  public Set<W> getUniverse() {
    return frame.getUniverse();
  }

  public Relation<W> getRelation() {
    return frame.getRelation();
  }

  public Assignment<W> getAssignment() {
    return assignment;
  }

  public boolean givenWorldInTheModelEntails(final W world, final Formula formula) {
    return switch (formula.getFormulaType()) {
      case ATOM -> givenFormulaIsTrueAtWorld(formula, world);
      case TRUE -> true;
      case NOT -> !givenWorldInTheModelEntails(world, formula.getOperand());
      case AND -> givenWorldInTheModelEntails(world, formula.getLeft())
              && givenWorldInTheModelEntails(world, formula.getRight());
      case BOX, G -> getRelation().getAllOutputsOf(world).stream()
              .allMatch(outputWorld -> givenFormulaIsTrueAtWorld(formula, outputWorld));
      case H -> getRelation().getAllInputsOf(world).stream()
              .allMatch(outputWorld -> givenFormulaIsTrueAtWorld(formula, outputWorld));
    };
  }

  public boolean isTrueInAModel(final Formula formula) {
    // circular dependencies?
    return getUniverse().stream().allMatch(world -> givenFormulaIsTrueAtWorld(formula, world));
  }

  public boolean isTreeLike() {
    return frame.isTree();
  }

  public KripkeFrame<W> getFrame() {
    return frame;
  }
}
