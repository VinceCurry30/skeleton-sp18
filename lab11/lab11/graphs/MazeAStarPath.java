package lab11.graphs;

/**
 *  @author Josh Hug
 */
public class MazeAStarPath extends MazeExplorer {
    /* Inherits public fields:
    public int[] distTo;
    public int[] edgeTo;
    public boolean[] marked;
    */
    private int s;
    private int t;
    private int targetX;
    private int targetY;
    private boolean targetFound = false;
    private Maze maze;

    public MazeAStarPath(Maze m, int sourceX, int sourceY, int targetX, int targetY) {
        super(m);
        maze = m;
        s = maze.xyTo1D(sourceX, sourceY);
        t = maze.xyTo1D(targetX, targetY);
        this.targetX = targetX;
        this.targetY = targetY;
        distTo[s] = 0;
        edgeTo[s] = s;
    }

    /** Estimate of the distance from v to the target. */
    private int h(int v) {
        return Math.abs(maze.toX(v) - targetX) + Math.abs(maze.toY(v) - targetY);
    }

    /** Performs an A star search from vertex s. */
    private void astar(int s) {
        for (int i = 0; i < maze.V(); i++) {
            if (i != s) {
                distTo[i] = Integer.MAX_VALUE;
            }
        }
        ArrayHeap<Integer> fringe = new ArrayHeap<>();
        for (int i = 0; i < maze.V(); i++) {
            fringe.insert(i, distTo[i]);
        }
        while (fringe.size() != 0) {
            int v = fringe.removeMin();
            marked[v] = true;
            announce();
            for (int w : maze.adj(v)) {
                if (fringe.contains(w)) {
                    if (distTo[w] > distTo[v]) {
                        edgeTo[w] = v;
                        announce();
                    }
                    relax(w, fringe);
                }
            }
            if (v == t) {
                targetFound = true;
                return;
            }
        }
    }

    private void relax(int w, ArrayHeap<Integer> fringe) {
        if (distTo[w] > distTo[edgeTo[w]] + 1) {
            distTo[w] = distTo[edgeTo[w]] + 1;
            announce();
            if (fringe.contains(w)) {
                fringe.changePriority(w, distTo[w] + h(w));
            }
        }
    }

    @Override
    public void solve() {
        astar(s);
    }

}

