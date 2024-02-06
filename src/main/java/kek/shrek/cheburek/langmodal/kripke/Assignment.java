package kek.shrek.cheburek.langmodal.kripke;

import kek.shrek.cheburek.langmodal.Formula;

import java.util.Collection;
import java.util.Set;
import java.util.function.Function;

public class Assignment<W> {

  private final Set<Formula> atomsAP;

  private final Function<Formula, Set<W>> assignmentMapping;

  public Assignment(final Set<Formula> atoms, final Function<Formula, Set<W>> mapping) {
    this.atomsAP = atoms;
    this.assignmentMapping = mapping;
  }

  public Collection<W> worldsWhereHolds(final Formula formula) {
    return assignmentMapping.apply(formula);
  }

  public Collection<? extends Formula> getAtoms() {
    return atomsAP;
  }

  public Function<Formula, Set<W>> getMapping() {
    return assignmentMapping;
  }
}
