package kek.shrek.cheburek.langmodal.kripke;

import kek.shrek.cheburek.langmodal.kripke.relation.Relation;
import kek.shrek.cheburek.langmodal.kripke.relation.TransitionLabelsRelation;

import java.util.HashSet;
import java.util.Set;

public class KripkeFrame<W> {
  final Set<W> universe = new HashSet<>();
  final Relation<W> relation;

  public KripkeFrame(Relation<W> relation) {
    this.relation = relation;
  }

  public boolean isStrictPartialOrder() {
    // TODO
    throw new RuntimeException();
  }

  public boolean isTotalOrder() {
    if (!isStrictPartialOrder()) {
      return false;
    }
    // TODO
    throw new RuntimeException();
  }

  public static <W> KripkeFrame<W> labelledTransitionSystem(final Set<W> universe, TransitionLabelsRelation<W> transitions) {
    if (universe.isEmpty()) {
      throw new RuntimeException();
    }
    final KripkeFrame<W> transitionSystemFrame = new KripkeFrame<W>(transitions);
    transitionSystemFrame.getUniverse().addAll(universe);
    return transitionSystemFrame;
  }

  public Set<W> getUniverse() {
    return universe;
  }

  public Relation<W> getRelation() {
    return relation;
  }
}
