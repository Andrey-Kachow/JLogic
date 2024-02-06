package kek.shrek.cheburek.langmodal.kripke;

import kek.shrek.cheburek.langmodal.Formula;

import java.util.HashSet;
import java.util.Set;

public class ModalEquivalence<W> {

  /**
   * A.k. forall
   */
  Set<Formula> relevantFormulas = new HashSet<>();
  Set<KripkeModel<W>> relevantModels = new HashSet<>();

  public boolean equivalentWorlds(
          final W firstWorld,
          final KripkeModel<W> firstModel,
          final W secondWorld,
          final KripkeModel<W> secondModel
  ) {
    final KripkeSemantics<W> firstModelSemantics = new KripkeSemantics<>(firstModel);
    final KripkeSemantics<W> secondModelSemantics = new KripkeSemantics<>(secondModel);
    return relevantFormulas.stream().allMatch(
            formula ->
                    firstModelSemantics.givenWorldInTheModelEntails(firstWorld, formula)
                            == secondModelSemantics.givenWorldInTheModelEntails(secondWorld, formula));
  }

  public boolean equivalentModels(final KripkeModel<W> firstModel, final KripkeModel<W> secondModel) {
    final KripkeSemantics<W> firstModelSemantics = new KripkeSemantics<>(firstModel);
    final KripkeSemantics<W> secondModelSemantics = new KripkeSemantics<>(secondModel);
    return relevantFormulas.stream().allMatch(formula ->
            firstModelSemantics.isTrueInAModel(formula) == secondModelSemantics.isTrueInAModel(formula));
  }
}
