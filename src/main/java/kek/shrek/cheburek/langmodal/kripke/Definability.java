package kek.shrek.cheburek.langmodal.kripke;

import kek.shrek.cheburek.langmodal.Formula;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Function;

public class Definability<W> {

  private Set<Formula> allFormulas = new HashSet<>();
  private Set<KripkeModel<W>> allModels = new HashSet<>();

  public boolean isDefinable(final Function<Formula, Formula> operator) {
    return allFormulas.stream().allMatch(formula -> allFormulas.stream().anyMatch(
            otherFormula -> allModels.stream().allMatch(
                    model -> model.getUniverse().stream().allMatch(
                            world -> model.isTrueInAModel(operator.apply(formula)) == model.isTrueInAModel(otherFormula))
                    )
            )
    );
  }
}
