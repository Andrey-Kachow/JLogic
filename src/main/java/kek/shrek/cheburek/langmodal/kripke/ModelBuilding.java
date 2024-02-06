package kek.shrek.cheburek.langmodal.kripke;

import kek.shrek.cheburek.langmodal.Formula;
import kek.shrek.cheburek.langmodal.kripke.relation.CompoundRelation;
import kek.shrek.cheburek.langmodal.kripke.relation.Relation;
import kek.shrek.cheburek.langmodal.kripke.relation.RestrictionRelation;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

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
    return first.getUniverse().containsAll(second.getUniverse())
            && second.getUniverse().stream().allMatch(world -> second.getUniverse().containsAll(second.getRelation().getAllOutputsOf(world)))
            && first.getAssignment().getAtoms().stream().allMatch(atom -> {
              final Set<W> restrictionSet =
                      second.getAssignment()
                              .getMapping()
                              .apply(atom)
                              .stream()
                              .filter(it -> second.getUniverse().contains(it))
                              .collect(Collectors.toSet());
              return second.getAssignment().getMapping().apply(atom).equals(restrictionSet);
            }
    );
  }

  public static <W> boolean isGeneratedSubModel(final KripkeModel<W> first, final KripkeModel<W> second) {
    return isSubModel(first, second) && first.getUniverse().stream().allMatch(world ->
            !second.getUniverse().contains(world) || second.getUniverse().containsAll(first.getRelation().getAllOutputsOf(world))
    );
  }

  public static <W> KripkeModel<W> generateSubModelBy(final W initialWorld, final KripkeModel<W> model) {
    final Set<W> generatedUniverse = model.getRelation().transitiveClosureWorldsFromRoot(initialWorld);
    final Relation<W> restrictionRelation = new RestrictionRelation<>(model.getRelation(), generatedUniverse);
    final Assignment<W> restrictionAssignment = model.getAssignment().restrictedTo(generatedUniverse);
    return new KripkeModel<>(new KripkeFrame<>(generatedUniverse, restrictionRelation), restrictionAssignment);
  }
}
