package practica.VNS;

import practica.improvement.ILocalSearch;
import practica.structure.Instance;
import practica.structure.Solution;

import java.util.HashSet;
import java.util.Set;

public class SVNS extends BVNS{

    ILocalSearch localSearch;

    float alpha = 0.1f;

    public SVNS(ILocalSearch localSearch){
        super(localSearch);
        this.localSearch=localSearch;
    }

    public Solution execute(Solution solution, Instance instance) {

        int kMax= (int) Math.ceil((kMaxPCT*instance.getP())/100.0);
        int kStep=(int) Math.ceil((kStepPCT*instance.getP())/100.0);

        int bestOF=solution.evaluate();
        Solution bestSol=new Solution(instance);
        bestSol.copy(solution);

        int k=1;
        int maxItersWithoutImprove=3;
        int itersWithoutImprove=0;
        while (k<kMax)
        {
            procedure(k,solution,instance);
            int actualOF=solution.evaluate();

            if(actualOF<(1+alpha)*bestOF && itersWithoutImprove<maxItersWithoutImprove){
                itersWithoutImprove++;
                if(actualOF<bestOF){
                    k=1;
                    bestOF=actualOF;
                    itersWithoutImprove=0;
                    bestSol.copy(solution);
                }
            }
            else{
                if(maxItersWithoutImprove==itersWithoutImprove) itersWithoutImprove=0;
                k+=kStep;
            }
        }

        return bestSol;
    }

    public String toString() {
        return getClass().getSimpleName();
    }
}
