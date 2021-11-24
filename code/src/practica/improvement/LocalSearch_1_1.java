package practica.improvement;

import practica.Main;
import practica.structure.Instance;
import practica.structure.RandomManager;
import practica.structure.Solution;
import practica.structure.TimeManager;

import java.util.*;

public class LocalSearch_1_1 implements ILocalSearch{

    @Override
    public void execute(Solution sol, Instance instance) {

        boolean improve=true;

        while(improve){
            improve=search(sol);
        }


    }

    private boolean search(Solution sol){
        List<Integer> selectedCopy = new ArrayList<>(sol.getSelected());
        Collections.shuffle(selectedCopy, RandomManager.getRandom());
        List<Integer> unselectedCopy = new ArrayList<>(sol.getUnselected());
        Collections.shuffle(unselectedCopy, RandomManager.getRandom());

        boolean bestImprovement=false;
        int ofValue=sol.evaluate();;
        for(int selected:selectedCopy){ //problemas con la actualización de los conjuntos
            sol.remove(selected);
            for(int unselected:unselectedCopy){
                sol.add(unselected);
                int newOfValue=sol.evaluate();
                if(newOfValue<ofValue && !bestImprovement){
                    return true;
                }
                else if(newOfValue>=ofValue){
                    sol.remove(unselected);

                }
                if (TimeManager.getTimeSecs() >= Main.MAX_TIME) break;
            }
            sol.add(selected);
            if (TimeManager.getTimeSecs() >= Main.MAX_TIME) break;
        }
        return false;
    }

    public String toString(){
        return this.getClass().getSimpleName();
    }
}
