package kek.shrek.cheburek.langmodal.kripke.relation;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

public interface Relation<W> {
  Set<W> getAllOutputsOf(W argument);

  Relation<W> inverse();

  Set<W> getAllInputsOf(W argument);

  default Set<W> transitiveClosureWorldsFromRoot(W root) {
    final Set<W> accessibleWorlds = new HashSet<>();
    final Queue<W> transitiveClosureTraversalQueue = new LinkedList<>();
    transitiveClosureTraversalQueue.add(root);
    while (!transitiveClosureTraversalQueue.isEmpty()) {
      final W world = transitiveClosureTraversalQueue.poll();
      accessibleWorlds.add(world);
      transitiveClosureTraversalQueue.addAll(this.getAllOutputsOf(world));
    }
    return accessibleWorlds;
  }
}
