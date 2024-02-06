package kek.shrek.cheburek.langmodal.kripke;

import kek.shrek.cheburek.langmodal.kripke.relation.Relation;
import kek.shrek.cheburek.langmodal.kripke.relation.TransitionLabelsRelation;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class KripkeFrame<W> {
  final Set<W> universe;
  final Relation<W> relation;

  public KripkeFrame(final Set<W> universe, final Relation<W> relation) {
    if (universe.isEmpty()) {
      throw new RuntimeException();
    }
    this.universe = universe;
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
    final KripkeFrame<W> transitionSystemFrame = new KripkeFrame<W>(universe, transitions);
    transitionSystemFrame.getUniverse().addAll(universe);
    return transitionSystemFrame;
  }

  public Set<W> getUniverse() {
    return universe;
  }

  public Relation<W> getRelation() {
    return relation;
  }


  public boolean isTree() {
    final Optional<W> root = getUniverse()
            .stream()
            .filter(world -> getRelation().transitiveClosureWorldsFromRoot(world).equals(getUniverse()))
            .findAny();
    if (root.isEmpty()) {
      return false;
    }
    return getUniverse().stream().allMatch(world -> world == root.get() || getRelation().getAllInputsOf(world).size() == 1)
            &&
            getUniverse().stream().allMatch(world -> getRelation().getAllOutputsOf(world).stream().noneMatch(world::equals));
  }
}
