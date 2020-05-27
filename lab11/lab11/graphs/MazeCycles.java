package lab11.graphs;

/**
 *  @author Josh Hug
 */
public class MazeCycles extends MazeExplorer {
    /* Inherits public fields:
    public int[] distTo;
    public int[] edgeTo;
    public boolean[] marked;
    */
    private int[] parent;
    private boolean isCycle;

    public MazeCycles(Maze m) {
        super(m);
        parent = new int[m.V()];
    }

    @Override
    public void solve() {
        dfs(0);
    }

    // Helper methods go here
    private void dfs(int v) {
        if (isCycle) {
            return;
        }

        marked[v] = true;
        announce();

        for (int w : maze.adj(v)) {
            if (marked[w] && parent[v] != w) {
                parent[w] = v;
                isCycle = true;
                setEdgeTo(w);
                announce();
                break;
            }
            if (!marked[w]) {
                parent[w] = v;
                announce();
                dfs(w);
            }
        }
    }

    private void setEdgeTo(int v) {
        edgeTo[v] = parent[v];
        int temp = parent[v];
        while (temp != v) {
            edgeTo[temp] = parent[temp];
            temp = parent[temp];
        }
    }
}
