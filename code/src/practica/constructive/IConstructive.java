package practica.constructive;

import practica.structure.Instance;
import practica.structure.Solution;

public interface IConstructive {
    Solution construct(Instance instance);
    String toString();
}
