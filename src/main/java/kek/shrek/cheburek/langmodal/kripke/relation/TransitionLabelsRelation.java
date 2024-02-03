package kek.shrek.cheburek.langmodal.kripke.relation;


import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class TransitionLabelsRelation<W> implements Relation<W> {
  private final Map<String, Relation<W>> labeledRelations = new HashMap<>();

  public TransitionLabelsRelation(Map<String, Relation<W>> labeledRelations) {
    this.labeledRelations.putAll(labeledRelations);
  }

  @Override
  public Set<W> getAllOutputsOf(W argument) {
    final Set<W> allTransitions = new HashSet<>();
    for (final Relation<W> relation : labeledRelations.values()) {
      allTransitions.addAll(relation.getAllOutputsOf(argument));
    }
    return allTransitions;
  }

  @Override
  public Relation<W> inverse() {
    final Map<String, Relation<W>> inverses = new HashMap<>();
    labeledRelations.forEach((label, relation) -> inverses.put(label, relation.inverse()));
    return new TransitionLabelsRelation<W>(labeledRelations);
  }

  /*
   * Do we care?
   */
  @Override
  public Set<W> getAllInputsOf(W argument) {
    final Set<W> allTransitions = new HashSet<>();
    for (final Relation<W> relation : labeledRelations.values()) {
      allTransitions.addAll(relation.getAllInputsOf(argument));
    }
    return allTransitions;
  }
}
