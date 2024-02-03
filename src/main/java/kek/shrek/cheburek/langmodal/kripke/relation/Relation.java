package kek.shrek.cheburek.langmodal.kripke.relation;

import java.util.Set;

public interface Relation<W> {
  Set<W> getAllOutputsOf(W argument);

  Relation<W> inverse();

  Set<W> getAllInputsOf(W argument);
}
