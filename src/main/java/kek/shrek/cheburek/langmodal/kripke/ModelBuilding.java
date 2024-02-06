package kek.shrek.cheburek.langmodal.kripke;

import kek.shrek.cheburek.langmodal.Formula;
import kek.shrek.cheburek.langmodal.kripke.relation.CompoundRelation;
import kek.shrek.cheburek.langmodal.kripke.relation.Relation;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Function;

public class ModelBuilding {

  public static <W> KripkeModel<W> checkedDisjointUnion(final KripkeModel<W>... models) {
    for (final KripkeModel<W> model : models) {
      for (final KripkeModel<W> otherModel : models) {
        if (model != otherModel && !ModelBuilding.areDisjoint(model, otherModel)) {
          throw new RuntimeException();
        }
      }
    }
    return disjointUnion(models);
  }

  private static <W> boolean areDisjoint(final KripkeModel<W> model, final KripkeModel<W> otherModel) {
    return model.getUniverse().stream().noneMatch(world -> otherModel.getUniverse().contains(world));
  }

  @SafeVarargs
  public static <W> KripkeModel<W> disjointUnion(final KripkeModel<W>... models) {
    final Set<Formula> atoms = new HashSet<>();
    final Set<W> universe = new HashSet<>();
    final Set<Relation<W>> relations = new HashSet<>();
    final Set<Function<Formula, Set<W>>> assignmentMappings = new HashSet<>();
    for (final KripkeModel<W> model : models) {
      atoms.addAll(model.getAssignment().getAtoms());
      universe.addAll(model.getUniverse());
      relations.add(model.getRelation());
      assignmentMappings.add(model.getAssignment().getMapping());
    }
    final Assignment<W> assignment = new Assignment<>(atoms, formula -> {
      final Set<W> responseSetOfWorlds = new HashSet<>();
      for (final Function<Formula, Set<W>> assignmentMapping : assignmentMappings) {
        responseSetOfWorlds.addAll(assignmentMapping.apply(formula));
      }
      return responseSetOfWorlds;
    });
    final Relation<W> relation = new CompoundRelation<W>(relations);
    final KripkeFrame<W> frame = new KripkeFrame<>(universe, relation);
    return new KripkeModel<>(frame, assignment);
  }

  public static <W> boolean isSubModel(final KripkeModel<W> first, final KripkeModel<W> second) {
    throw new UnsupportedOperationException();
  }
}
