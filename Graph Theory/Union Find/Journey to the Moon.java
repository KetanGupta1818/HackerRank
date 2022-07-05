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
    public static long journeyToMoon(int n, List<List<Integer>> astronaut) {
        Union un = new Union(n);
        for(List<Integer> as: astronaut){
            if(!un.isConnected(as.get(0),as.get(1)))
                un.union(as.get(0),as.get(1));
        }
        Map<Integer,Long> map = un.getMapOfRootsWithSize();
        long ways = 0L;
        for(int root: map.keySet())
            ways+= ((long) map.get(root) *(n-map.get(root))) ;
        return (ways/2);
    }
}
class Union{
    int[] rank;
    int[] root;
    public Union(int n){
        rank = new int[n];
        root = new int[n];
        for(int i=0;i<n;i++){
            rank[i] = 1;
            root[i] = i;
        }
    }
    public int find(int v){
        if(root[v] == v) return v;
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
        else if(rank[rootX]<rank[rootY]) root[rootX] = rootY;
        else{
            root[rootX] = rootY;
            rank[rootY]++;
        }
    }
    public Map<Integer, Long> getMapOfRootsWithSize(){
        Map<Integer,Long> map = new HashMap<>();
        for (int j : root) {
            int root = find(j);
            map.put(root,map.getOrDefault(root,(long)0)+1);
        }
        return map;
    }
}

public class Solution {
    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(System.getenv("OUTPUT_PATH")));

        String[] firstMultipleInput = bufferedReader.readLine().replaceAll("\\s+$", "").split(" ");

        int n = Integer.parseInt(firstMultipleInput[0]);

        int p = Integer.parseInt(firstMultipleInput[1]);

        List<List<Integer>> astronaut = new ArrayList<>();

        IntStream.range(0, p).forEach(i -> {
            try {
                astronaut.add(
                    Stream.of(bufferedReader.readLine().replaceAll("\\s+$", "").split(" "))
                        .map(Integer::parseInt)
                        .collect(toList())
                );
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

        long result = Result.journeyToMoon(n, astronaut);

        bufferedWriter.write(String.valueOf(result));
        bufferedWriter.newLine();

        bufferedReader.close();
        bufferedWriter.close();
    }
}
