package practica.VNS;

import practica.Main;
import practica.improvement.ILocalSearch;
import practica.structure.Instance;
import practica.structure.Solution;
import practica.structure.TimeManager;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.*;

public class PVNS extends BVNS{

    ILocalSearch localSearch;

    ExecutorService pool;
    List<Future<Solution>> solutions;

    public PVNS(ILocalSearch localSearch){
        super(localSearch);
        this.localSearch=localSearch;
    }

    public Solution execute(Solution solution, Instance instance) {
        pool = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        int kMax= (int) Math.ceil((kMaxPCT*instance.getP())/100.0);
        int kStep=(int) Math.ceil((kStepPCT*instance.getP())/100.0);

        solutions= new ArrayList<>(kMax);
        int iters= Runtime.getRuntime().availableProcessors();

        int bestOF=solution.evaluate();
        Solution bestSol=new Solution(instance);
        bestSol.copy(solution);

        int k=1;
        while (k<kMax )
        {
            for(int i=0; i<iters;i++){
                int finalK = k;
                solutions.add(pool.submit(() -> {
                    Solution sol=new Solution(instance);
                    sol.copy(solution);
                    procedure(finalK,sol,instance);
                    return sol;
                }));
            }
            int localBestOF=0x3f3f3f;
            Solution localBestSol=new Solution(instance);
            for(Future<Solution> sol:solutions){
                try{
                    Solution solAux=sol.get();
                    int auxOF=solAux.evaluate();
                    if(localBestOF==0x3f3f3f || auxOF<localBestOF){
                        localBestSol.copy(solAux);
                        localBestOF=auxOF;
                    }
                }catch (Exception e) {
                    e.printStackTrace();
                }
            }
            solutions.clear();

            if(localBestOF<bestOF){
                k=1;
                bestOF=localBestOF;
                bestSol.copy(localBestSol);
            }
            else{
                k+=kStep;
            }
            if (TimeManager.getTimeSecs() >= Main.MAX_TIME) break;
        }
        pool.shutdown();
        try {
            pool.awaitTermination(Integer.MAX_VALUE, TimeUnit.DAYS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return bestSol;
    }

    public String toString() {
        return getClass().getSimpleName();
    }
}
