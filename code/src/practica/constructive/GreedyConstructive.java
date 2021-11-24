package practica.constructive;


import practica.structure.Instance;
import practica.structure.Solution;

public class GreedyConstructive implements IConstructive{

    @Override
    public Solution construct(Instance instance) {

        Solution solution=new Solution(instance);
        solution.add(selectFirstNode(instance));
        while(solution.count()<instance.getP()){
            solution.add(selectNextNode(instance,solution));
        }

        return solution;
    }

    private int selectFirstNode(Instance instance){
        int selected=-1;
        int minDistance=0x3f3f3f;
        for (int i=0;i<instance.getNumNodes();i++){
            int maxDistance=0;
            for(int j=0;j<instance.getNumNodes();j++){
                if(maxDistance<instance.getDistance(i,j)){
                    maxDistance=instance.getDistance(i,j);
                }
            }
            if(maxDistance<minDistance){
                minDistance=maxDistance;
                selected=i;
            }
        }
        return selected;
    }

    private int selectNextNode(Instance instance, Solution solution){
        int sol=-1;
        int maxDistance=0;
        for (int unselected:solution.getUnselected()){
            int minDistance=0x3f3f3f;
            for(int selected:solution.getSelected()){
                if(minDistance>instance.getDistance(unselected,selected)){
                    minDistance=instance.getDistance(unselected,selected);
                }
            }
            if(maxDistance<minDistance){
                maxDistance=minDistance;
                sol=unselected;
            }
        }
        return sol;
    }



    public String toString() {
        return this.getClass().getSimpleName();
    }
}
