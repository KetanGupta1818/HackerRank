import java.io.*;
import java.math.*;
import java.security.*;
import java.text.*;
import java.util.*;
import java.util.concurrent.*;
import java.util.function.*;
import java.util.regex.*;
import java.util.stream.*;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;

public class Solution {

    public static boolean[] visited;
    public static int removals = 0;
    static int evenForest(int t_nodes, int t_edges, List<Integer> t_from, List<Integer> t_to) {
        List<List<Integer>> graph = new ArrayList<>();
        int n = t_nodes;
        for(int i=0;i<=n;i++){
            graph.add(new ArrayList<>());
        }
        for(int i=0;i<t_edges;i++){
            graph.get(t_from.get(i)).add(t_to.get(i));
            graph.get(t_to.get(i)).add(t_from.get(i));
        }
        visited = new boolean[n+1];
        visited[0] = true;
        visited[1] = true;
        int v = dfs(1, graph);
        if(v==n) System.out.println("Correct");
        return removals-1;
    }
    public static int dfs(int cur, List<List<Integer>> graph){
        int numberOfNodesBelow = 0;
        for(int next: graph.get(cur)){
            if(!visited[next]){
                visited[next] = true;
                numberOfNodesBelow+=dfs(next, graph)+1;
            }
        }
        if(numberOfNodesBelow%2==1) removals++;
        return numberOfNodesBelow;
    }

    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(System.getenv("OUTPUT_PATH")));

        String[] tNodesEdges = bufferedReader.readLine().replaceAll("\\s+$", "").split(" ");

        int tNodes = Integer.parseInt(tNodesEdges[0]);
        int tEdges = Integer.parseInt(tNodesEdges[1]);

        List<Integer> tFrom = new ArrayList<>();
        List<Integer> tTo = new ArrayList<>();

        IntStream.range(0, tEdges).forEach(i -> {
            try {
                String[] tFromTo = bufferedReader.readLine().replaceAll("\\s+$", "").split(" ");

                tFrom.add(Integer.parseInt(tFromTo[0]));
                tTo.add(Integer.parseInt(tFromTo[1]));
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

        int res = evenForest(tNodes, tEdges, tFrom, tTo);

        bufferedWriter.write(String.valueOf(res));
        bufferedWriter.newLine();

        bufferedReader.close();
        bufferedWriter.close();
    }
}
