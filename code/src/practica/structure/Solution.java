package practica.structure;

import java.util.HashSet;
import java.util.Set;

public class Solution {

    private Instance instance;
    Set<Integer> selected;
    Set<Integer> unselected;

    public Solution(Instance instance){
        this.instance=instance;
        selected=new HashSet<>(instance.getP());
        unselected=new HashSet<>(instance.getNumNodes());
        for(int i=0; i<instance.getNumNodes();i++){
            unselected.add(i);
        }

    }

    public void add(int elem){
        selected.add(elem);
        unselected.remove(elem);
    }
    public void remove(int elem){
        selected.remove(elem);
        unselected.add(elem);
    }

    public int count(){
        return selected.size();
    }

    public int evaluate(){
        int OF=0;

        for(int unselectedNode:unselected){
            int bestNode=-1;
            int secondBestNode=-1;
            for(int selectedNode:selected){
                if(bestNode==-1 || instance.getDistance(unselectedNode,selectedNode)<instance.getDistance(unselectedNode,bestNode)){
                    secondBestNode=bestNode;
                    bestNode=selectedNode;
                }
                else if(secondBestNode==-1 || instance.getDistance(unselectedNode,selectedNode)<instance.getDistance(unselectedNode,secondBestNode)){
                    secondBestNode=selectedNode;
                }
            }
            OF+=instance.getDistance(unselectedNode,secondBestNode);
        }
        return OF;
    }

    public void copy(Solution sol){

        this.instance=sol.getInstance();
        this.selected.clear();
        this.selected.addAll(sol.getSelected());
        this.unselected.clear();
        this.unselected.addAll(sol.getUnselected());

    }

    public Instance getInstance() {
        return instance;
    }

    public Set<Integer> getUnselected() {
        return unselected;
    }

    public Set<Integer> getSelected() {
        return selected;
    }

    public int distToSelected(int v) {
        int d = 0;
        for (Integer s : selected) {
            d += instance.getDistance(v,s);
        }
        return d;
    }
}
