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
}
