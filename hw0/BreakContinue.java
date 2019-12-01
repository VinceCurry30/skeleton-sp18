public class BreakContinue {
  /** replaces each element a[i] with the sum of a[i] through a[i + n], but only if a[i] is positive valued.*/
  public static void windowPosSum(int[] a, int n) {
    for(int i = 0; i < a.length; i++) {
      if(a[i] <= 0) {
        continue;
      }
      int sum = 0;
      for(int j = i; j <= i + n; j++) {
        if(j >= a.length) {
          break;
        }
        sum = sum + a[j];
      }
      a[i] = sum;
    }
  }

  public static void main(String[] args) {
    int[] a = {1, 2, -3, 4, 5, 4};
    int n = 3;
    windowPosSum(a, n);

    int[] b = {1, -1, -1, 10, 5, -1};
    int m = 2;
    windowPosSum(b, m);

    // Should print 4, 8, -3, 13, 9, 4
    System.out.println(java.util.Arrays.toString(a));
    // Should print -1, -1, -1, 14, 4, -1
    System.out.println(java.util.Arrays.toString(b));
  }
}
