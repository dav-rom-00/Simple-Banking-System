// You can experiment here, it won’t be checked

import java.util.Arrays;

public class Task {
  public static void main(String[] args) {
    int[] nums = new int[]{1,2,3};
    Arrays.fill(nums, 0,2,9);
    System.out.println(Arrays.toString(nums));
  }
}
