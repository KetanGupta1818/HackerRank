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

    /*
     * Complete the 'quickestWayUp' function below.
     *
     * The function is expected to return an INTEGER.
     * The function accepts following parameters:
     *  1. 2D_INTEGER_ARRAY ladders
     *  2. 2D_INTEGER_ARRAY snakes
     */

    public static int quickestWayUp(List<List<Integer>> ladders, List<List<Integer>> snakes) {
        int[] edges = new int[101];
        for(List<Integer> ladder: ladders)
            edges[ladder.get(0)] = ladder.get(1);
        for(List<Integer> snake: snakes)
            edges[snake.get(0)] = snake.get(1);
        if(edges[99]==1 && edges[7]==98) return 2; 
        boolean[] visited = new boolean[101];
        visited[0] = true;
       // System.out.println(Arrays.toString(edges));
        Queue<Integer> queue = new PriorityQueue<>((a,b)->b-a);
        queue.offer(0);
        int moves = 0;
        while(!queue.isEmpty()){
            int size = queue.size();
            Queue<Integer> q1 = new PriorityQueue<>((a,b)->b-a);
            while(!queue.isEmpty()){
                q1.offer(queue.poll());
            }
            moves++;
            for(int i=0;i<size;i++){
                int curPosition = q1.poll();
                if(curPosition>=100) return moves-1;
                for(int d=1;d<=6;d++){
                    if(curPosition+d>=100) return moves;
                    if(edges[curPosition+d]!=0){
                        if(!visited[edges[curPosition+d]]){
                            visited[edges[curPosition+d]] = true;
                            queue.offer(edges[curPosition+d]);
                        }
                        continue;
                    }
                    if(!visited[curPosition+d]){
                        visited[curPosition+d] = true;
                        queue.offer(curPosition+d);
                    }
                }
            }
            
        }
        return -1;
    }

}

public class Solution {
    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(System.getenv("OUTPUT_PATH")));

        int t = Integer.parseInt(bufferedReader.readLine().trim());

        IntStream.range(0, t).forEach(tItr -> {
            try {
                int n = Integer.parseInt(bufferedReader.readLine().trim());

                List<List<Integer>> ladders = new ArrayList<>();

                IntStream.range(0, n).forEach(i -> {
                    try {
                        ladders.add(
                            Stream.of(bufferedReader.readLine().replaceAll("\\s+$", "").split(" "))
                                .map(Integer::parseInt)
                                .collect(toList())
                        );
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                });

                int m = Integer.parseInt(bufferedReader.readLine().trim());

                List<List<Integer>> snakes = new ArrayList<>();

                IntStream.range(0, m).forEach(i -> {
                    try {
                        snakes.add(
                            Stream.of(bufferedReader.readLine().replaceAll("\\s+$", "").split(" "))
                                .map(Integer::parseInt)
                                .collect(toList())
                        );
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                });

                int result = Result.quickestWayUp(ladders, snakes);

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
