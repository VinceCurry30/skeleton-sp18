public class OffByN implements CharacterComparator {
    private int difference;

    public OffByN(int N) {
        difference = N;
    }

    @Override
    public boolean equalChars(char x, char y) {
        if (x - y == difference || y - x == difference) {
            return true;
        }
        return false;
    }
}
