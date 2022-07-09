//Naive DFS --> check all paths from source to destination TC (v^v).. 
//TLE solution
import java.io.*;
import java.util.*;

public class Solution {
    private static int min_weight = Integer.MAX_VALUE;
    public static void main(String[] args) throws IOException {
        BufferedWriter output = new BufferedWriter(new OutputStreamWriter(System.out));
        FastReader fr = new FastReader();
        int n = fr.nextInt();
        int edges = fr.nextInt();
        List<List<int[]>> graph = new ArrayList<>();
        for(int i=0;i<n;i++) graph.add(new ArrayList<>());
        for(int i=0;i<edges;i++){
            int u = fr.nextInt()-1;
            int v = fr.nextInt()-1;
            int w = fr.nextInt();
            graph.get(u).add(new int[]{v,w});
            graph.get(v).add(new int[]{u,w});
        }
        dfs(graph,0,n-1,0,new boolean[n]);
        output.write(min_weight+"\n");
        output.flush();
    }
    private static void dfs(List<List<int[]>> graph, int cur, int des, int cur_weight, boolean[] visited){
        if(cur == des){
            min_weight = Math.min(min_weight,cur_weight);
            return;
        }
        visited[cur] = true;
        for(int[] edge: graph.get(cur)){
            int child = edge[0];
            int child_weight = edge[1];
            if(!visited[child])
                dfs(graph,child,des,cur_weight+Math.max(0,child_weight-cur_weight),visited);
        }
        visited[cur] = false;
    }
}
//Input
class FastReader {
    BufferedReader br;
    StringTokenizer st;

    public FastReader()
    {
        br = new BufferedReader(new InputStreamReader(System.in));
    }

    String next() {
        while (st == null || !st.hasMoreElements()) {
            try {
                st = new StringTokenizer(br.readLine());
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
        return st.nextToken();
    }

    int nextInt() { return Integer.parseInt(next()); }

    long nextLong() { return Long.parseLong(next()); }

    double nextDouble()
    {
        return Double.parseDouble(next());
    }

    String nextLine()
    {
        String str = "";
        try {
            if(st.hasMoreTokens()){
                str = st.nextToken("\n");
            }
            else{
                str = br.readLine();
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return str;
    }
}




//Dijsktras algorithm works correctly because the question is just a little modification of normal shortest path. In this question when parent's 
//distance is greater than parent - to- child distance the childs distance remains the same

import java.io.*;
import java.util.*;

public class Solution {
    public static void main(String[] args) throws IOException {
        BufferedWriter output = new BufferedWriter(new OutputStreamWriter(System.out));
        FastReader fr = new FastReader();
        int n = fr.nextInt();
        int edges = fr.nextInt();
        List<List<int[]>> graph = new ArrayList<>();
        for(int i=0;i<n;i++) graph.add(new ArrayList<>());
        for(int i=0;i<edges;i++){
            int u = fr.nextInt()-1;
            int v = fr.nextInt()-1;
            int w = fr.nextInt();
            graph.get(u).add(new int[]{v,w});
            graph.get(v).add(new int[]{u,w});
        }
        long dis = dijkstraAlgorithm(graph);
        if(dis == Long.MAX_VALUE) output.write("NO PATH EXISTS\n");
        else output.write(dis+"\n");
        output.flush();
    }
    private static long dijkstraAlgorithm(List<List<int[]>> graph){
        int n = graph.size();
        long[] shortestDistance = new long[n];
        Arrays.fill(shortestDistance,Long.MAX_VALUE);
        shortestDistance[0] = 0L;
        Queue<long[]> queue = new ArrayDeque<>();
        queue.offer(new long[]{0,0});
        while(!queue.isEmpty()){
            long[] current = queue.remove();
            int vertex = (int)current[0];
            long parentDistance = current[1];
            for(int[] edge: graph.get(vertex)){
                int child = edge[0];
                int child_weight = edge[1];
                long childDistance = parentDistance + Math.max(0,child_weight-parentDistance);
                if(shortestDistance[child] > childDistance){
                    shortestDistance[child] = childDistance;
                    queue.offer(new long[]{child,shortestDistance[child]});
                }
            }
        }
        return shortestDistance[n-1];
    }
}
//Input
class FastReader {
    BufferedReader br;
    StringTokenizer st;

    public FastReader()
    {
        br = new BufferedReader(new InputStreamReader(System.in));
    }

    String next() {
        while (st == null || !st.hasMoreElements()) {
            try {
                st = new StringTokenizer(br.readLine());
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
        return st.nextToken();
    }

    int nextInt() { return Integer.parseInt(next()); }

    long nextLong() { return Long.parseLong(next()); }

    double nextDouble()
    {
        return Double.parseDouble(next());
    }

    String nextLine()
    {
        String str = "";
        try {
            if(st.hasMoreTokens()){
                str = st.nextToken("\n");
            }
            else{
                str = br.readLine();
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return str;
    }
}
