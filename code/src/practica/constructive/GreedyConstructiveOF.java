package practica.constructive;


import practica.structure.Instance;
import practica.structure.RandomManager;
import practica.structure.Solution;

import java.util.HashSet;
import java.util.Set;

public class GreedyConstructiveOF implements IConstructive{

    @Override
    public Solution construct(Instance instance) {

        Solution solution=new Solution(instance);
        int first = RandomManager.getRandom().nextInt(instance.getNumNodes());
        solution.add(first);
        while(solution.count()<instance.getP()){
            solution.add(selectNextNode(solution));
        }

        return solution;
    }

    private int selectNextNode(Solution solution){

        Set<Integer> unselectedCopy=new HashSet<Integer>(solution.getUnselected());
        int bestOF=0x3f3f3f;
        int bestNode=-1;
        for (int unselected:unselectedCopy) {
            solution.add(unselected);
            int actualOF = solution.evaluate();
            if (bestOF > actualOF) {
                bestOF = actualOF;
                bestNode=unselected;
            }
            solution.remove(unselected);
        }
        return bestNode;
    }

    public String toString() {
        return this.getClass().getSimpleName();
    }
}
