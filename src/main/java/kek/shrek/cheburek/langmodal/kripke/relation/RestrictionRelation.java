package kek.shrek.cheburek.langmodal.kripke.relation;

import java.util.Set;
import java.util.stream.Collectors;

public class RestrictionRelation<W> implements Relation<W> {

  final Set<W> allowedUniverse;
  final Relation<W> relation;

  public RestrictionRelation(final Relation<W> relation, final Set<W> generatedUniverse) {
    this.allowedUniverse = generatedUniverse;
    this.relation = relation;
  }

  @Override
  public Set<W> getAllOutputsOf(W argument) {
    assert allowedUniverse.contains(argument);
    return relation.getAllOutputsOf(argument).stream()
            .filter(allowedUniverse::contains)
            .collect(Collectors.toSet());
  }

  @Override
  public Relation<W> inverse() {
    return new RestrictionRelation<W>(relation.inverse(), allowedUniverse);
  }

  @Override
  public Set<W> getAllInputsOf(W argument) {
    assert allowedUniverse.contains(argument);
    return relation.getAllInputsOf(argument).stream()
            .filter(allowedUniverse::contains)
            .collect(Collectors.toSet());
  }
}
