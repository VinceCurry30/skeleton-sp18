public class Max_In_Array {
    public static int max(int[] m) {
        int max = m[0];
        for(int x : m) {
            if(x > max) {
                max = x;
            }
        }
        return max;
    }
    public static void main(String[] args) {
        int[] numbers = new int[]{9, 2, 15, 2, 22, 10, 6};
        System.out.print(max(numbers));
    }
}
