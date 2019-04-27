import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class FordFulkerson {
    int[][] adjacencyMatrix;
    List<Integer> path;
    int source, sink, minWeight;
    boolean[] visited;
    boolean pathFound;


    public static void main(String[] args) {
        FordFulkerson obj = new FordFulkerson();
        obj.getUserInputs();
        int maxFlow = obj.fordFulkerson();
        System.out.println("Max Flow:" + maxFlow);
    }

    public void getUserInputs() {
        Scanner scan = new Scanner(System.in);
        int vertices = Integer.parseInt(scan.nextLine());
        int edges = Integer.parseInt(scan.nextLine());
        source = 0;
        sink = vertices-1;
        adjacencyMatrix = new int[vertices][vertices];
        visited = new boolean[vertices];
        path = new ArrayList<>();
        for(int i=0; i<edges; i++){
            String input = scan.nextLine();
            String[] inputs = input.split(" ");
            adjacencyMatrix[Integer.parseInt(inputs[0])][Integer.parseInt(inputs[1])] = Integer.parseInt(inputs[2]);
        }
    }

    public boolean dfs(int start, int end){
        visited[start] = true;

        if(start == end){
            return true;
        }

        minWeight = Integer.MAX_VALUE;
        for(int i=0; i<visited.length; i++){
            if(adjacencyMatrix[start][i]>0 && !visited[i] && !pathFound){
                minWeight = Math.min(adjacencyMatrix[start][i], minWeight);
                path.add(i);
                pathFound = dfs(i, end);
            }
        }

        if(pathFound){
            return true;
        }
         path.remove(path.size()-1);
        return false;
    }


    public int fordFulkerson(){
        int maxFlow = 0;

        path.add(source);

        while(dfs(source, sink)){
            int prev = source;
            for(int i: path){
                adjacencyMatrix[prev][i] -= minWeight;
                adjacencyMatrix[i][prev] +=minWeight;
                prev = i;
            }
            maxFlow +=minWeight;
            resetValues();
        }


        return maxFlow;
    }

    public void resetValues(){
        for(int i=0; i<visited.length; i++){
            visited[i] = false;
        }
        pathFound = false;
        path.clear();
        path.add(source);
        minWeight = Integer.MAX_VALUE;
    }

}
