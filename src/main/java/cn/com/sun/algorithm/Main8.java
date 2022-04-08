package cn.com.sun.algorithm;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

/**
 * @Author : mockingbird
 * @Date : 2022/3/20 23:37
 * @Description :
 */
public class Main8 {
    // 疫情期间，需要大家保证一定的社交距离，公司组织开交流会议，座位有一排共N个座位，编号分别为[0...N-1]，要求员工一个接着一个进入会议室，
// 并且可以在任何时候离开会议室。
// 满足：每当一个员工进入时，需要坐到最大社交距离（最大化自己和最近其他人的距离）的座位；如果有多个这样的座位，则坐在索引最小的那个位置。
// 输入描述：
// 会议座位总数seatNum,(1<=seatNum<=500)
// 员工的进出顺序seatOrLeave数组，元素值为1；表示进场，元素值为负数，表示出场（特殊：位置0的员工不会离开），例如-4表示坐在位置4的员工离
// 开（保证有员工坐在该座位上）
// 输出描述：
// 最后进来员工，他会坐在第几个位置，如果位置已满，则输出-1
// 示例1 输入输出实例仅供调试，后台判题数据一般不包含实例
// 输入
// 10
// [1,1,1,1,-4,1]
// 输出
// 5
// 说明
// seat -> 0,坐在任何位置都行，但是要给他安排索引最小的位置，也就是座位0。
// seat -> 9,要和旁边的人距离最远，也就是座位9。
// seat -> 4,要和旁边的人距离最远，应该做到中间，也就是座位4。
// seat -> 2,员工最后坐在2号座位上。
// leave(4),4号座位的员工离开。
// seat -> 5,员工最后坐在5号座位上。
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int seatNum = Integer.parseInt(scanner.nextLine());
        int[] seatOrLeave = Arrays.stream(scanner.nextLine().split(",")).mapToInt(Integer::parseInt).toArray();
        // 已经有人的座位index
        List<Integer> seatedIndexList = new ArrayList<>();
        for (int i : seatOrLeave) {
            // 进来
            if (i == 1)
                nextSeat(seatNum, seatedIndexList);
            else
                seatedIndexList.remove((Integer) Math.abs(i));
        }
        System.out.println(seatedIndexList.get(seatedIndexList.size() - 1));
    }

    private static void nextSeat(int seatNum, List<Integer> seatedIndexList) {
        // 遍历全部座位，计算出最合适的位置
        int maxIndex = 0;
        // 最近最大距离
        int recentMaxDistance = Integer.MIN_VALUE;
        if (seatedIndexList.size() != 0) {
            for (int i = 0; i < seatNum; i++) {
                if (seatedIndexList.contains(i))
                    continue;
                // 离得最近人的距离
                int min = Integer.MAX_VALUE;
                for (int seatedIndex : seatedIndexList) {
                    int distance = Math.abs(i - seatedIndex);
                    min = distance < min ? distance : min;
                }
                if (min > recentMaxDistance) {
                    maxIndex = i;
                    recentMaxDistance = min;
                }
            }
        }
        seatedIndexList.add(maxIndex);
    }
}
