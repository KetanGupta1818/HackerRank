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
    public static long roadsAndLibraries(int n, int c_lib, int c_road, List<List<Integer>> cities) {
        // Write your code here
        if(c_lib<=c_road) return (long)n*c_lib;
        long minCost = 0;//NumberOfRoots*c_lib + NumberOfUnionOperations*c_road;
        long u=0;
        Union un = new Union(n);
        for(List<Integer> edge: cities){
            if(!un.isConnected(edge.get(0),edge.get(1))) {
                u++;
                un.union(edge.get(0),edge.get(1));
            }
        }
        return (long)un.getNumberOfRoots()*(long)c_lib + (long)c_road*u;
    }
}
class Union{
    int[] rank;
    int[] root;
    public Union(int n){
        rank = new int[n+1];
        root = new int[n+1];
        for(int i=0;i<n+1;i++){
            rank[i] = 1;
            root[i] = i;
        }
        rank[0] = -1;
        root[0] = -1;
    }
    public int find(int v){
        if(v == root[v]) return v;
        return root[v] = find(root[v]);
    }
    public boolean isConnected(int x, int y){
        return find(x) == find(y);
    }
    public void union(int x, int y){
        int rootX = find(x);
        int rootY = find(y);
        if(rootX == rootY) return;
        if(rank[rootX]>rank[rootY]) root[rootY] = rootX;
        else if(root[rootY]>rank[rootX]) root[rootX] = rootY;
        else{
            root[rootX] = rootY;
            rank[rootY]++;
        }
    }
    public int getNumberOfRoots(){
        int rs=0;
        for(int i=1;i<root.length;i++){
            if(root[i]==i) rs++;
        }
        return rs;
    }
}


public class Solution {
    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(System.getenv("OUTPUT_PATH")));

        int q = Integer.parseInt(bufferedReader.readLine().trim());

        IntStream.range(0, q).forEach(qItr -> {
            try {
                String[] firstMultipleInput = bufferedReader.readLine().replaceAll("\\s+$", "").split(" ");

                int n = Integer.parseInt(firstMultipleInput[0]);

                int m = Integer.parseInt(firstMultipleInput[1]);

                int c_lib = Integer.parseInt(firstMultipleInput[2]);

                int c_road = Integer.parseInt(firstMultipleInput[3]);

                List<List<Integer>> cities = new ArrayList<>();

                IntStream.range(0, m).forEach(i -> {
                    try {
                        cities.add(
                            Stream.of(bufferedReader.readLine().replaceAll("\\s+$", "").split(" "))
                                .map(Integer::parseInt)
                                .collect(toList())
                        );
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                });

                long result = Result.roadsAndLibraries(n, c_lib, c_road, cities);

                bufferedWriter.write(String.valueOf(result));
                bufferedWriter.newLine();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

        bufferedReader.close();
        bufferedWriter.close();
    }
}
