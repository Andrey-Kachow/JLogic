package kek.shrek.cheburek.langmodal.kripke;

import kek.shrek.cheburek.langmodal.Formula;

import java.util.Set;
import java.util.function.Function;

public class Definability<W> {

  private final Set<Formula> allFormulas;
  private final Set<KripkeModel<W>> allModels;
  private final Set<KripkeFrame<W>> allFrames;

  public Definability(final Set<Formula> allFormulas, final Set<KripkeModel<W>> allModels, final Set<KripkeFrame<W>> allFrames) {
    this.allFormulas = allFormulas;
    this.allModels = allModels;
    this.allFrames = allFrames;
  }

  public boolean isDefinable(final Function<Formula, Formula> operator) {
    return allFormulas.stream().allMatch(formula -> allFormulas.stream().anyMatch(
            otherFormula -> allModels.stream().allMatch(
                    model -> model.getUniverse().stream().allMatch(
                            world -> model.isTrueInAModel(operator.apply(formula)) == model.isTrueInAModel(otherFormula))
                    )
            )
    );
  }

  public boolean formulaDefines(final Formula formula, final Set<KripkeFrame<W>> classOfFrames) {
    return allFrames.stream().allMatch(frame -> classOfFrames.contains(frame) == Validity.isValidInAFrame(frame, formula));
  }
}
