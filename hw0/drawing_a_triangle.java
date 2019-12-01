public class drawing_a_triangle {
    public static void main(String[] args) {
        int x = 0;
        while(x < 5) {
            int star_in_row = 0;
            x = x + 1;
            while(star_in_row < x) {
                System.out.print("*");
                star_in_row = star_in_row + 1;
            }
            System.out.println("");
        }
    }
}
