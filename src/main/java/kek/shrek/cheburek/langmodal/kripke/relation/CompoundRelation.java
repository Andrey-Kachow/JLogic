package kek.shrek.cheburek.langmodal.kripke.relation;

import java.util.HashSet;
import java.util.Set;

public class CompoundRelation<W> implements Relation<W> {

  private final Set<Relation<W>> relations;

  public CompoundRelation(Set<Relation<W>> relations) {
    this.relations = relations;
  }

  @Override
  public Set<W> getAllOutputsOf(final W argument) {
    final Set<W> outputs = new HashSet<>();
    for (final Relation<W> relation : relations) {
      outputs.addAll(relation.getAllOutputsOf(argument));
    }
    return outputs;
  }

  @Override
  public Relation<W> inverse() {
    throw new UnsupportedOperationException();
  }

  @Override
  public Set<W> getAllInputsOf(final W argument) {
    final Set<W> inputs = new HashSet<>();
    for (final Relation<W> relation : relations) {
      inputs.addAll(relation.getAllInputsOf(argument));
    }
    return inputs;
  }
}
