//package kg.my_algorithms.HackerRank;

//import static kg.my_algorithms.Mathematics.*;

import java.io.*;
import java.util.*;

public class Solution {
    private static final int MOD = 1_000_000_007;
    public static void main(String[] args) throws IOException {
        BufferedWriter output = new BufferedWriter(new OutputStreamWriter(System.out));
        FastReader fr = new FastReader();
        int n = fr.nextInt();
        int k = fr.nextInt();
        int postBoxVertex = -1;
        boolean[] isPostBox = new boolean[n];
        boolean[] deletedNodes = new boolean[n];
        List<List<Edge>> tree = new ArrayList<>();
        for(int i=0;i<n;i++) tree.add(new ArrayList<>());
        int[] degrees = new int[n];
        for(int i=0;i<k;i++) isPostBox[postBoxVertex = fr.nextInt()-1] = true;
        for(int i=0;i<n-1;i++){
            int u = fr.nextInt()-1;
            int v = fr.nextInt()-1;
            int weight = fr.nextInt();
            tree.get(u).add(new Edge(v,weight));
            tree.get(v).add(new Edge(u,weight));
            degrees[u]++;
            degrees[v]++;
        }
        Queue<Integer> deletionQueue = new ArrayDeque<>();
        for(int vertex=0;vertex<n;vertex++){
            if(degrees[vertex]==1 && !isPostBox[vertex]) {
                deletionQueue.add(vertex);
                deletedNodes[vertex] = true;
                degrees[vertex] = 0;
            }
        }
        while(!deletionQueue.isEmpty()){
            int curNode = deletionQueue.remove();
            for(Edge edge: tree.get(curNode)){
                int child = edge.child;
                if(deletedNodes[child] || isPostBox[child]) continue;
                degrees[child]--;
                if(degrees[child]==1){
                    deletedNodes[child] = true;
                    deletionQueue.add(child);
                    degrees[child]=0;
                }
            }
        }
        int firstFarthestNode = findFarthestNodeWithWeight(tree,deletedNodes,postBoxVertex)[0];
        int diameterWeight = findFarthestNodeWithWeight(tree,deletedNodes,firstFarthestNode)[1];
        int fullTreeWeight = findTotalWeightOfTree(tree,deletedNodes,firstFarthestNode);
        int answer = diameterWeight + (fullTreeWeight-diameterWeight)*2;
        output.write(Integer.toString(answer));
        output.flush();
    }
    private static int[] findFarthestNodeWithWeight(List<List<Edge>> tree, boolean[] deletedNodes, int startVertex){
        boolean[] visited = new boolean[tree.size()];
        visited[startVertex] = true;
        Queue<int[]> queue = new ArrayDeque<>();
        queue.offer(new int[]{startVertex,0});
        int farthestNode = -1, farthestDistance = -1;
        while(!queue.isEmpty()){
            int[] cur = queue.remove();
            int curNode = cur[0];
            int weight = cur[1];
            for(Edge edge: tree.get(curNode)){
                int child = edge.child;
                if(deletedNodes[child] || visited[child]) continue;
                int childWeight = edge.weight + weight;
                if(childWeight > farthestDistance){
                    farthestDistance = childWeight;
                    farthestNode = child;
                }
                queue.offer(new int[]{child,childWeight});
                visited[child] = true;
            }
        }
        return new int[]{farthestNode,farthestDistance};
    }
    private static int findTotalWeightOfTree(List<List<Edge>> tree, boolean[] deletedNodes, int curNode){
        int sum = 0;
        deletedNodes[curNode] = true;
        for(Edge edge: tree.get(curNode)){
            int child = edge.child;
            if(deletedNodes[child]) continue;
            sum += findTotalWeightOfTree(tree, deletedNodes,child) + edge.weight;
        }
        return sum;
    }
}
class Edge{
    int child;
    int weight;

    public Edge(int child, int weight){
        this.child = child;
        this.weight = weight;
    }
}


























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
