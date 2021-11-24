package practica;

import practica.VNS.BVNS;
import practica.VNS.PVNS;
import practica.VNS.RVNS;
import practica.VNS.SVNS;
import practica.constructive.GreedyConstructive;
import practica.constructive.GreedyConstructiveOF;
import practica.improvement.LocalSearch_1_1;
import practica.improvement.LocalSearch_2_2;
import practica.procedure.AlgProcedure;
import practica.structure.Instance;
import practica.structure.RandomManager;
import practica.structure.Result;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;

public class Main {

    static String pathFolder="./instances";
    static String instanceFolderPath;

    static AlgProcedure algorithm;
    static GreedyConstructive constructive= new GreedyConstructive();
    static GreedyConstructiveOF constructiveOF= new GreedyConstructiveOF();
    static LocalSearch_1_1 localSearch_1_1=new LocalSearch_1_1();

    static LocalSearch_2_2 localSearch_2_2=new LocalSearch_2_2();
    static BVNS pvns;
    static BVNS bvns;
    static BVNS rvns;
    static BVNS svns;
    static BVNS [] vnsVersions;

    public static final float MAX_TIME = 1800;

    public static void main(String[] args) {
        pvns=new PVNS(localSearch_1_1);
        rvns=new RVNS(localSearch_1_1);
        bvns=new BVNS(localSearch_1_1);
        svns=new SVNS(localSearch_1_1);

        vnsVersions=new BVNS[]{pvns};
        for(BVNS vnsVersion:vnsVersions){
            algorithm=new AlgProcedure(constructiveOF,localSearch_1_1,vnsVersion);
            execute();
        }
    }

    private static void execute()  {
        File file=new File(pathFolder);
        instanceFolderPath = file.getPath() + "/";
        List<String> instancesNames = Arrays.asList(file.list());

        printHeaders("./results/"+algorithm.toString()+".csv");
        for(String instanceName : instancesNames){
            if(!instanceName.startsWith(".") && !instanceName.startsWith("..")){
                RandomManager.setSeed(13);
                executeInstance(instanceName);
            }
        }
    }

    private static void executeInstance(String instanceName) {
        System.out.println(instanceName);
        Instance instance=new Instance(instanceFolderPath +instanceName);
        Result result= algorithm.execute(instance);
        printResults("./results/"+algorithm.toString()+".csv", result, instanceName);
    }

    private static void printHeaders(String path) {
        try (PrintWriter pw = new PrintWriter(path)) {
            pw.print("Instance;Time;OF");
            pw.println();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void printResults(String path, Result result, String name) {
        try (FileWriter fw = new FileWriter(path,true)) {
            PrintWriter pw= new PrintWriter(fw);

            pw.print(name+";");
            int nElems=result.getKeys().size();
            for (int i = 0; i < nElems; i++) {
                pw.print(result.get(i));
                if (i < nElems-1) pw.print(";");
            }
            pw.println();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
