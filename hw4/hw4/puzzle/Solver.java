package hw4.puzzle;

import edu.princeton.cs.algs4.MinPQ;

import java.util.Deque;
import java.util.LinkedList;

public class Solver {
    private MinPQ<SearchNode> minHeap;
    private SearchNode finalState;

    public Solver(WorldState initial) {
        minHeap = new MinPQ<>();

        SearchNode first = new SearchNode(initial, 0, null);
        minHeap.insert(first);

        while (!minHeap.isEmpty()) {
            SearchNode cur = minHeap.delMin();
            if (cur.isGoal()) {
                finalState = cur;
                break;
            } else {
                for (WorldState n: cur.neighbors()) {
                    SearchNode sNode = new SearchNode(n, cur.getMoves() + 1, cur);
                    if (cur.equals(first) || !cur.getPre().getWord().equals(sNode.getWord())) {
                        minHeap.insert(sNode);
                    }
                }
            }
        }
    }

    public int moves() {
        return finalState.getMoves();
    }

    public Iterable<WorldState> solution() {
        Deque<WorldState> result = new LinkedList<>();
        result.add(finalState.getWord());
        SearchNode temp = finalState;
        while (temp.getMoves() > 0) {
            result.addFirst(temp.getPre().getWord());
            temp = temp.getPre();
        }
        return result;
    }
}
