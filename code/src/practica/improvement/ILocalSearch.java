package practica.improvement;


import practica.structure.Instance;
import practica.structure.Solution;

public interface ILocalSearch {

    void execute(Solution sol, Instance instance);
    String toString();

}
