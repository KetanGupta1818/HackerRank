

import java.io.*;
import java.util.*;

public class Solution {
    public static void main(String[] args) throws IOException {
        BufferedWriter output = new BufferedWriter(new OutputStreamWriter(System.out));
        FastReader fr = new FastReader();
        int n = fr.nextInt();
        int m = fr.nextInt();
        List<List<int[]>> graph = new ArrayList<>(); //Edge: [destination,weight]
        boolean[][] used = new boolean[n][1024];
        for(int i=0;i<n;i++) {
            graph.add(new ArrayList<>());
        }
        for(int i=0;i<m;i++){
            int u = fr.nextInt()-1;
            int v = fr.nextInt()-1;
            int w = fr.nextInt();
            graph.get(u).add(new int[]{v,w});
            graph.get(v).add(new int[]{u,w});
        }
        int source = fr.nextInt()-1;
        int destination = fr.nextInt()-1;
        Queue<int[]> queue = new ArrayDeque<>();
        queue.offer(new int[]{source,0});
        while(!queue.isEmpty()){
            int[] parentEdge = queue.remove();
            int parent = parentEdge[0];
            int parent_weight = parentEdge[1];
            used[parent][parent_weight] = true;

            for(int[] edge: graph.get(parent)){
                int child = edge[0];
                int weight = edge[1];
                if(!used[child][parent_weight | weight]){
                    //System.out.println("Child: " + child);
                    used[parent][parent_weight | weight] = true;
                    used[child][parent_weight | weight] = true;
                    queue.offer(new int[]{child,parent_weight | weight});
                }
            }
        }
        for(int i=0;i<=1023;i++){
            if(used[destination][i]){
                output.write(i+"\n");
                output.flush();
                return;
            }
        }
        output.write("-1");
        output.flush();
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
