package cn.com.sun.algorithm;

import java.util.*;

public class Main3 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int jie = Integer.parseInt(scanner.nextLine());
        int[] nums = Arrays.stream(scanner.nextLine().split(" ")).mapToInt(Integer::parseInt).toArray();
        int d = Integer.parseInt(scanner.nextLine());
        // 氛围两组 找出所有大于 d的信道
        List<Integer> biggerList = new ArrayList<>();
        List<Integer> smallerList = new ArrayList<>();
        for (int i = 0; i <= jie; i++) {
            int pow = (int) Math.pow(2, i);
            if (pow >= d) {
                for (int num = 0; num < nums[i]; num++) {
                    biggerList.add(pow);
                }
            } else {
                for (int num = 0; num < nums[i]; num++) {
                    smallerList.add(pow);
                }
            }
        }
        //尽量凑整
        int n = 0;
        Collections.sort(smallerList);
        Collections.reverse(smallerList);
        while (smallerList.size() > 1) {
            int smallIndex = smallerList.size() - 1;
            int big = smallerList.get(0);
            while (smallIndex > 0) {
                int small = smallerList.get(smallIndex);
                if (big + small >= d) {
                    n++;
                    smallerList.remove(0);
                    smallerList.remove(smallIndex);
                    break;
                } else {
                    smallIndex--;
                }
            }
        }

        System.out.println(biggerList.size() + n);
    }
}
