package practica.procedure;

import practica.VNS.BVNS;
import practica.constructive.IConstructive;
import practica.improvement.ILocalSearch;
import practica.improvement.LocalSearch_1_1;
import practica.structure.Instance;
import practica.structure.Result;
import practica.structure.Solution;
import practica.structure.TimeManager;

import java.util.concurrent.ExecutionException;

public class AlgProcedure implements IAlgorithm{

    private final IConstructive constructive;
    private final ILocalSearch localSearch;
    private final BVNS bvns;

    public AlgProcedure(IConstructive constructive, ILocalSearch localSearch, BVNS bvns) {
        this.constructive = constructive;
        this.localSearch=localSearch;
        this.bvns=bvns;
    }

    @Override
    public Result execute(Instance instance) {

        //long totalTime=System.currentTimeMillis();
        TimeManager.initTimer();
        Result result=new Result(instance.getName());
        float secs;

        System.out.println("Constructivo");
        Solution solution=constructive.construct(instance);
        System.out.println("Búsqueda local");
        if(localSearch!=null) localSearch.execute(solution,instance);
        System.out.println("VNS");
        if(bvns!=null){
            solution=bvns.execute(solution,instance);
        }


        //totalTime = System.currentTimeMillis() - totalTime;
        //secs = totalTime / 1000f;

        secs = TimeManager.getTimeSecs();
        result.add("Time", secs);
        System.out.println("Time: "+ secs);

        result.add("OF", solution.evaluate());
        System.out.println("OF: "+ solution.evaluate());


        return result;
    }

    public String toString() {
        String localSearchName=localSearch!=null?", "+localSearch.toString():"";
        String vnsName=bvns!=null?", "+bvns.toString():"";

        return this.getClass().getSimpleName()+"("+constructive.toString()+localSearchName+vnsName+")";
    }

}
