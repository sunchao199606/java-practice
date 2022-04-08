package cn.com.sun.algorithm;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * @Author : mockingbird
 * @Date : 2022/3/18 15:09
 * @Description :
 */
public class Main4 {
    /*
      输入字符串s输出s中包含所有整数的最小和，
      说明：
      1.字符串s只包含a~z,A~Z,+,-，
      2.合法的整数包括正整数，一个或者多个0-9组成，如：0,2,3,002,102
      3.负整数，负号开头，数字部分由一个或者多个0-9组成，如-2,-012,-23,-00023
      输入描述：包含数字的字符串
      输出描述：所有整数的最小和
      示例：
        输入：
          bb1234aa
      　输出
          10
      　输入：
          bb12-34aa
      　输出：
          -31
      说明：1+2-(34)=-31
       */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        String input = scanner.nextLine();
        int index = 0;
        // 正数列表
        List<Integer> negativeList = new ArrayList<>();
        // 负数列表
        List<Integer> positiveList = new ArrayList<>();
        while (index < input.length()) {
            char c = input.charAt(index);
            //如果是-继续往下遍历
            if (c == 45) {
                // 拿到最长负数
                StringBuilder builder = new StringBuilder("-");
                int tempIndex = index + 1;
                // 当下一个字符为- 或者数字时
                while (45 == input.charAt(tempIndex) || Character.isDigit(input.charAt(tempIndex))) {
                    //如果是连续的-，则直接忽略
                    if (45 == input.charAt(tempIndex) && input.charAt(tempIndex - 1) == 45) {
                        tempIndex++;
                        continue;
                    }
                    //
                    if (45 == input.charAt(tempIndex) && Character.isDigit(input.charAt(tempIndex - 1))) {
                        break;
                    }
                    builder.append(input.charAt(tempIndex));
                    tempIndex++;
                }
                negativeList.add(Integer.parseInt(builder.toString()));
                index = tempIndex;
                continue;
            }
            // 正数
            if ((48 <= c && c <= 57)) {
                positiveList.add(Character.getNumericValue(c));
            }
            index++;
        }
        int sumPositive = positiveList.stream().mapToInt(Integer::intValue).sum();
        int sumNegative = negativeList.stream().mapToInt(Integer::intValue).sum();
        System.out.println(sumPositive + sumNegative);
    }
}
