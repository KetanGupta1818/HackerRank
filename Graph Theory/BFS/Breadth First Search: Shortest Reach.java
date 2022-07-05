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


    public static List<Integer> bfs(int n, int m, List<List<Integer>> edges, int s) {
        int[] levelArray = new int[n+1];
        boolean[] visited = new boolean[n+1];
        visited[0] = true;
        visited[s] = true;
        levelArray[0] = -1;
        levelArray[s] = -1;
        List<List<Integer>> graph = new ArrayList<>();
        for(int i=0;i<n+1;i++) graph.add(new ArrayList<>());
        for(List<Integer> edge: edges) {
            graph.get(edge.get(0)).add(edge.get(1));
            graph.get(edge.get(1)).add(edge.get(0));
        }
        Queue<Integer> queue = new ArrayDeque<>();
        queue.offer(s);
        int level = 0;
        while(!queue.isEmpty()){
            level++;
            int size = queue.size();
            for(int i=0;i<size;i++){
                int cur = queue.poll();
                for(int next: graph.get(cur)){
                    if(!visited[next]){
                        visited[next] = true;
                        levelArray[next] = level;
                        queue.offer(next);
                    }
                }
            }
        }
        List<Integer> answer = new ArrayList<>();
        for(int i=1;i<n+1;i++){
            if(i==s) continue;
            if(visited[i]) answer.add(levelArray[i]*6);
            else answer.add(-1);
        }
        return answer;
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

                int s = Integer.parseInt(bufferedReader.readLine().trim());

                List<Integer> result = Result.bfs(n, m, edges, s);

                bufferedWriter.write(
                    result.stream()
                        .map(Object::toString)
                        .collect(joining(" "))
                    + "\n"
                );
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

        bufferedReader.close();
        bufferedWriter.close();
    }
}
