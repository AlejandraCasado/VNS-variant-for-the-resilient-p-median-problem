package practica.VNS;

import practica.improvement.ILocalSearch;
import practica.structure.Instance;
import practica.structure.Solution;

import java.util.HashSet;
import java.util.Set;

public class RVNS extends BVNS{

    ILocalSearch localSearch;

    public RVNS(ILocalSearch localSearch){
        super(localSearch);
        this.localSearch=localSearch;
    }

    protected void procedure(int k, Solution solution,Instance instance){
        shake(k,solution);
    }

    public String toString() {
        return getClass().getSimpleName();
    }
}
