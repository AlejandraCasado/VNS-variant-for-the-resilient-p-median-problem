package practica.VNS;

import practica.improvement.ILocalSearch;
import practica.improvement.LocalSearch_1_1;
import practica.structure.Instance;
import practica.structure.RandomManager;
import practica.structure.Solution;

import java.util.*;
import java.util.concurrent.ExecutionException;

public class BVNS {

    protected int kMaxPCT=30;
    protected int kStepPCT=10;

    ILocalSearch localSearch;


    public BVNS(ILocalSearch localSearch){
        this.localSearch=localSearch;
    }

    public Solution execute(Solution solution, Instance instance)  {

        int kMax= (int) Math.ceil((kMaxPCT*instance.getP())/100.0);
        int kStep=(int) Math.ceil((kStepPCT*instance.getP())/100.0);

        int bestOF=solution.evaluate();
        Solution bestSol=new Solution(instance);
        bestSol.copy(solution);

        int k=1;
        while (k<kMax)
        {
            procedure(k,solution,instance);
            int actualOF=solution.evaluate();
            if(actualOF<bestOF){
                k=1;
                bestOF=actualOF;
                bestSol.copy(solution);
            }
            else{
                k+=kStep;
            }
        }
        return bestSol;
    }

    protected void procedure(int k, Solution solution,Instance instance){
        shake(k,solution);
        if (localSearch != null) {
            localSearch.execute(solution, instance);
        }
    }

    protected void shake(int k, Solution sol){
        List<Integer> selectedCopy = new ArrayList<>(sol.getSelected());
        Collections.shuffle(selectedCopy, RandomManager.getRandom());
        List<Integer> unselectedCopy = new ArrayList<>(sol.getUnselected());
        Collections.shuffle(unselectedCopy, RandomManager.getRandom());

        for (int i=0; i<k; i++){
            sol.remove(selectedCopy.get(i));
            sol.add(unselectedCopy.get(i));
        }
    }

    public String toString() {
        return getClass().getSimpleName()+"("+localSearch.toString()+")";
    }
}
