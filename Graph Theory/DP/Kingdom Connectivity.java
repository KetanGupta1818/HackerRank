import java.util.*;

public class Solution{
    private static Set<Integer> path_set = new HashSet<>();
    private static Set<Integer> circle_set = new HashSet<>();
    private static long[] memo;
    private static boolean[] visited;
    private static final int mod = 1000_000_000;
    public static void main(String[] args){
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int m = scanner.nextInt();
        List<List<Integer>> graph = new ArrayList<>();
        for(int i=0;i<n;i++){
            graph.add(new ArrayList<>());
        }
        for(int i=0;i<m;i++){
            graph.get(scanner.nextInt()-1).add(scanner.nextInt()-1);
        }
        memo = new long[n];
        visited = new boolean[n];
        visited[0] = true;
        dfs(0, new ArrayList<Integer>(), graph, n);
        if(isInfintePath()) System.out.println("INFINITE PATHS");
        else System.out.println(memo[0]);
    }
    private static void addToPathSet(List<Integer> cur_path){
        for(int vertex: cur_path) path_set.add(vertex);
    }
    private static void addToCircleSet(List<Integer> cur_path){
        int index = cur_path.size()-1;
        int repeatedVertex = cur_path.get(index--);
        circle_set.add(repeatedVertex);
        while(index>=0 && cur_path.get(index)!=repeatedVertex){
            circle_set.add(cur_path.get(index--));
        }
    }
    private static boolean isInfintePath(){
        for(int circle_vertex: circle_set){
            if(path_set.contains(circle_vertex)) return true;
        }
        return false;
    }
    private static void updatePath(long ways, List<Integer> cur_path){
        for(int vertes: cur_path){
            memo[vertes] = (memo[vertes] + ways)%mod;
        } 
    }
    private static void dfs(int cur_vertex, List<Integer> cur_path,List<List<Integer>> graph, int n){
        cur_path.add(cur_vertex);
        for(int next_vertex: graph.get(cur_vertex)){
            if(next_vertex==n-1){
                updatePath(1L, cur_path);
                addToPathSet(cur_path);
                continue;
            }
            if(memo[next_vertex]!=0){
                if(memo[next_vertex]!=-1){
                    updatePath(memo[next_vertex], cur_path);
                }
            }
            else{
                if(visited[next_vertex]){
                    cur_path.add(next_vertex);
                    addToCircleSet(cur_path);
                    cur_path.remove(cur_path.size()-1);
                }
                else{
                    visited[next_vertex] = true;
                    dfs(next_vertex, cur_path, graph, n);
                }
            }
        }
        if(memo[cur_vertex]==0) memo[cur_vertex]=-1;
        cur_path.remove(cur_path.size()-1);
    }
}
