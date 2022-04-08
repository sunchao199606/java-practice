package cn.com.sun.algorithm.leetcode;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

/**
 * @Author : mockingbird
 * @Date : 2022/3/21 13:26
 * @Description : 根据身高重建队列
 */
public class Test406 {
    //假设有打乱顺序的一群人站成一个队列，数组 people 表示队列中一些人的属性（不一定按顺序）。
//    每个 people[i] = [hi, ki] 表示第 i 个人的身高为 hi ，前面 正好 有 ki 个身高大于或等于 hi 的人。
//    请你重新构造并返回输入数组people 所表示的队列。返回的队列应该格式化为数组 queue ，
//    其中 queue[j] = [hj, kj] 是队列中第 j 个人的属性（queue[0] 是排在队列前面的人）。
//
//    示例 1：
//    输入：people = [[7,0],[4,4],[7,1],[5,0],[6,1],[5,2]]
//    输出：[[5,0],[7,0],[5,2],[6,1],[4,4],[7,1]]
//    解释：
//    编号为 0 的人身高为 5 ，没有身高更高或者相同的人排在他前面。
//    编号为 1 的人身高为 7 ，没有身高更高或者相同的人排在他前面。
//    编号为 2 的人身高为 5 ，有 2 个身高更高或者相同的人排在他前面，即编号为 0 和 1 的人。
//    编号为 3 的人身高为 6 ，有 1 个身高更高或者相同的人排在他前面，即编号为 1 的人。
//    编号为 4 的人身高为 4 ，有 4 个身高更高或者相同的人排在他前面，即编号为 0、1、2、3 的人。
//    编号为 5 的人身高为 7 ，有 1 个身高更高或者相同的人排在他前面，即编号为 1 的人。
//    因此 [[5,0],[7,0],[5,2],[6,1],[4,4],[7,1]] 是重新构造后的队列。
//
//    示例 2：
//    输入：people = [[6,0],[5,0],[4,0],[3,2],[2,2],[1,4]]
//    输出：[[4,0],[5,0],[2,2],[3,2],[1,4],[6,0]]
//
//    提示：
//    1 <= people.length <= 2000
//    0 <= hi <= 106
//    0 <= ki < people.length
//    题目数据确保队列可以被重建
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine().replaceAll("\\[", "").replaceAll("\\]", "");
        List<Person> personList = new ArrayList<>();
        for (int i = 0; i < input.length(); i += 4) {
            int h = Integer.parseInt(input.charAt(i) + "");
            int k = Integer.parseInt(input.charAt(i + 2) + "");
            personList.add(new Person(h, k));
        }
        // 按照身高排序
        List<Person> sortedPersonList = personList.stream().sorted((p1, p2) -> {
            if (p1.h < p2.h) return 1;
            else if (p1.h > p2.h) return -1;
            else {
                // h相同时先安排k小的
                if (p1.k < p2.k) return -1;
                else if (p1.k > p2.k) return 1;
                else return 0;
            }
        }).collect(Collectors.toList());
        List<Person> queue = new LinkedList<>();
        sortedPersonList.forEach(person -> {
            int pos = 0;
            // 查看k确定位置
            int k = person.k;
            if (k == 0) {
                queue.add(pos, person);
                return;
            }
            // 从queue中查看>=的元素个数
            for (int i = 0; i < queue.size(); i++) {
                int hq = queue.get(i).h;
                int h = person.h;
                // 队列中找到了>=元素
                if (hq >= h) {
                    k--;
                    if (k == 0) {
                        pos = i + 1;
                        break;
                    }
                }
            }
            queue.add(pos, person);
        });
        System.out.println(queue);
    }


//    public static int[][] reconstructQueue(int[][] people) {
//        List<Person> personList = new ArrayList<>();
//        for (int[] p : people) {
//            personList.add(new Person(p[0], p[1]));
//        }
//        // 按照身高排序
//        List<Person> sortedPersonList = personList.stream().sorted((p1, p2) -> {
//            if (p1.h < p2.h) return 1;
//            else if (p1.h > p2.h) return -1;
//            else return 0;
//        }).collect(Collectors.toList());
//        List<Person> queue = new LinkedList<>();
//        sortedPersonList.forEach(person -> {
//            int pos = 0;
//            // 查看k确定位置
//            int k = person.k;
//            if (k == 0) {
//                queue.add(pos, person);
//                return;
//            }
//            // 从queue中查看>=的元素个数
//            for (int i = 0; i < queue.size(); i++) {
//                int hq = queue.get(i).h;
//                int h = person.h;
//                // 队列中找到了>=元素
//                if (hq >= h) {
//                    k--;
//                    if (k == 0) {
//                        pos = i + 1;
//                        break;
//                    }
//                }
//            }
//            queue.add(pos, person);
//        });
//        System.out.println(queue);
//        int[][] q = new int[queue.size()][2];
//        for (int i = 0; i < q.length; i++) {
//            int h = queue.get(i).h;
//            int k = queue.get(i).k;
//            int[] p = {h, k};
//            q[i] = p;
//        }
//        return q;
//    }


    private static class Person {
        int h;
        int k;

        public Person(int h, int k) {
            this.h = h;
            this.k = k;
        }

        @Override
        public String toString() {
            return "Person{" +
                    "h=" + h +
                    ", k=" + k +
                    '}';
        }
    }
}
