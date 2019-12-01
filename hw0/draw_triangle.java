public class draw_triangle {
    public static void drawTriangle(int N) {
        int x = 0;
        while(x < N) {
            int star_in_row = 0;
            x = x + 1;
            while(star_in_row < x) {
                System.out.print("*");
                star_in_row = star_in_row + 1;
            }
            System.out.println("");
        }
    }

    public static void main(String[] args) {
        drawTriangle(10);
    }
}
