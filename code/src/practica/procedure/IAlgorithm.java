package practica.procedure;

import practica.structure.Instance;
import practica.structure.Result;

import java.util.concurrent.ExecutionException;

public interface IAlgorithm {
    public Result execute(Instance instance) throws ExecutionException, InterruptedException;
}
