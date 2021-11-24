package practica.structure;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Instance {

    private String path;
    private String name;

    private int numNodes;
    private int p;
    private int [][] adjacencyMatrix;

    public Instance(String path){
        this.path = path;
        this.name = path.substring(Math.max(path.lastIndexOf("/"), path.lastIndexOf("\\"))+1).replace(".txt", "");
        readInstance();
    }

    private void readInstance(){
        try (BufferedReader br = new BufferedReader(new FileReader(path))){
            String line;
            String[] lineContent;
            line = br.readLine();
            lineContent = line.split(" ");

            numNodes = Integer.parseInt(lineContent[1]);
            int numEdges=Integer.parseInt(lineContent[2]);
            p=Integer.parseInt(lineContent[3]);

            adjacencyMatrix= new int [numNodes][numNodes];

            for (int i = 0; i< numNodes; i++){
                for(int j=0; j<numNodes;j++){
                    adjacencyMatrix[i][j]=0x3f3f3f;
                }
            }

            for (int i = 0; i< numEdges; i++){
                line = br.readLine();
                lineContent = line.split(" ");
                int node1=Integer.parseInt(lineContent[1])-1;
                int node2=Integer.parseInt(lineContent[2])-1;
                int weight=Integer.parseInt(lineContent[3]);

                adjacencyMatrix[node1][node2]=weight;
                adjacencyMatrix[node2][node1]=weight;

            }

            } catch (IOException ioException) {
            ioException.printStackTrace();
        }
        floydWarshall();
    }

    private void floydWarshall(){
        for (int i=0; i<numNodes; i++){
            for (int j=0; j<numNodes; j++){
                for (int k=0; k<numNodes; k++){
                    if(adjacencyMatrix[i][j]>adjacencyMatrix[j][k]+adjacencyMatrix[i][k]){
                        adjacencyMatrix[i][j]=adjacencyMatrix[j][k]+adjacencyMatrix[i][k];
                    }
                }
            }
        }
    }


    public String getName(){
        return name;
    }

    public int getNumNodes() {
        return numNodes;
    }

    public int getP() {
        return p;
    }

    public int getDistance(int node1, int node2){
        return adjacencyMatrix[node1][node2];
    }


}
