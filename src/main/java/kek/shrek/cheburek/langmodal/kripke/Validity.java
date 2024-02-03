package kek.shrek.cheburek.langmodal.kripke;

import kek.shrek.cheburek.langmodal.Formula;

import java.util.Set;

/**
 * Valid does not equal true.
 * valid = turue for all __something__
 */
public class Validity {

  public static <W> boolean isValidInAFrame(final KripkeFrame<W> frame, final Formula formula) {
    throw new UnsupportedOperationException();
  }

  public static <W> boolean isValidInAClass(final Set<KripkeFrame<W>> classOfFrames, final Formula formula) {
    return classOfFrames.stream().allMatch(frame -> isValidInAFrame(frame, formula));
  }

  public static boolean isSimplyValid(final Formula formula) {
    throw new UnsupportedOperationException();
  }

  public static <W> boolean isStatisfiable(final Formula formula) {
    throw new UnsupportedOperationException();
  }

  public static <W> boolean areEquivalent(final Formula firstFormula, final Formula secondFormula) {
    throw new UnsupportedOperationException();
  }
}
