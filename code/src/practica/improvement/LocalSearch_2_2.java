package practica.improvement;

import practica.structure.Instance;
import practica.structure.Solution;

import java.util.HashSet;
import java.util.Set;

public class LocalSearch_2_2 implements ILocalSearch{

    @Override
    public void execute(Solution sol, Instance instance) {

        boolean improve=true;
        long initialTime=System.currentTimeMillis();
        long totalTime=0;
        while(improve && totalTime<30000){
            improve=search(sol);
            totalTime = System.currentTimeMillis() - initialTime;
        }

    }

    private boolean search(Solution sol){
        Set<Integer> selectedCopy=new HashSet<>(sol.getSelected());
        Set<Integer> unselectedCopy=new HashSet<>(sol.getUnselected());

        boolean bestImprovement=false;
        int ofValue=sol.evaluate();

        for(int selected:selectedCopy){
            sol.remove(selected);

            for(int selected2:selectedCopy) {
                if (selected == selected2) continue;
                sol.remove(selected2);
                for (int unselected : unselectedCopy) {
                    sol.add(unselected);
                    for (int unselected2 : unselectedCopy) {
                        if (unselected == unselected2) continue;
                        sol.add(unselected2);
                        int newOfValue = sol.evaluate();
                        if (newOfValue < ofValue && !bestImprovement) {
                            return true;
                        } else if (newOfValue >= ofValue) {
                            sol.remove(unselected2);
                        }
                    }
                    sol.remove(unselected);
                }
                sol.add(selected2);
            }
            sol.add(selected);
        }
        return false;
    }
    public String toString(){
        return this.getClass().getSimpleName();
    }
}
