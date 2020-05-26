package hw4.puzzle;

public class SearchNode implements Comparable<SearchNode> {
    private WorldState word;
    private int moves;
    private SearchNode pre;

    public SearchNode(WorldState word, int moves, SearchNode pre) {
        this.word = word;
        this.moves = moves;
        this.pre = pre;
    }

    public int compareTo(SearchNode sNode) {
        return this.getMoves() + this.getWord().estimatedDistanceToGoal() - sNode.getMoves() - sNode.getWord().estimatedDistanceToGoal();
    }

    public WorldState getWord() {
        return word;
    }

    public int getMoves() {
        return moves;
    }

    public SearchNode getPre() {
        return pre;
    }

    public boolean isGoal() {
        return word.estimatedDistanceToGoal() == 0;
    }

    public Iterable<WorldState> neighbors() {
        return word.neighbors();
    }
}
