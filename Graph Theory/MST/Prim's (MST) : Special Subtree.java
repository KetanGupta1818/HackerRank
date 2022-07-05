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

class Result {
    public static int prims(int n, List<List<Integer>> edges, int start) {
        Union un = new Union(n);
        edges.sort(Comparator.comparingInt(e -> e.get(2)));
        int e=0,weight=0,i=0;
        while(e!=n-1){
            List<Integer> edge = edges.get(i);
            if(!un.isConnected(edge.get(0),edge.get(1))){
                un.union(edge.get(0),edge.get(1));
                weight+=edge.get(2);
                e++;
            }
            i++;
        }
        return weight;
    }

}
class Union{
    private int[] rank;
    private int[] root;
    public Union(int n){
        rank = new int[n+1];
        root = new int[n+1];
        for(int i=0;i<root.length;i++) {
            rank[i] = 1;
            root[i] = i;
        }
        rank[0] = -1;
        root[0] = -1;
    }
    public int find(int v){
        if(v ==root[v]) return v;
        return root[v] = find(root[v]);
    }
    public boolean isConnected(int x, int y){
        return find(x)==find(y);
    }
    public void union(int x, int y){
        int rootX = find(x);
        int rootY = find(y);
        if(rootX==rootY) return;
        if(rank[rootY]>rank[rootX]){
            root[rootX] = rootY;
        }
        else if(rank[rootX]>rank[rootY]){
            root[rootY] = rootX;
        }
        else{
            root[rootX] = rootY;
            rank[rootY]++;
        }
    }
}

public class Solution {
    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(System.getenv("OUTPUT_PATH")));

        String[] firstMultipleInput = bufferedReader.readLine().replaceAll("\\s+$", "").split(" ");

        int n = Integer.parseInt(firstMultipleInput[0]);

        int m = Integer.parseInt(firstMultipleInput[1]);

        List<List<Integer>> edges = new ArrayList<>();

        IntStream.range(0, m).forEach(i -> {
            try {
                edges.add(
                    Stream.of(bufferedReader.readLine().replaceAll("\\s+$", "").split(" "))
                        .map(Integer::parseInt)
                        .collect(toList())
                );
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

        int start = Integer.parseInt(bufferedReader.readLine().trim());

        int result = Result.prims(n, edges, start);

        bufferedWriter.write(String.valueOf(result));
        bufferedWriter.newLine();

        bufferedReader.close();
        bufferedWriter.close();
    }
}
