package cn.com.sun.algorithm;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.function.ToIntFunction;


public class SortByVolume {

    private static class Node {
        int volumeByM;
        String mv;

        public Node(int volumeByM, String mv) {
            this.volumeByM = volumeByM;
            this.mv = mv;
        }

        public int getVolumeByM() {
            return volumeByM;
        }

        @Override
        public String toString() {
            return mv;
        }
    }

    public static void main(String[] args) {
        List<Node> list = new ArrayList<>();
        Scanner scanner = new Scanner(System.in);
        int num = Integer.parseInt(scanner.nextLine());
        // 全部转化成Node,并存入List
        for (int i = 0; i < num; i++) {
            String mv = scanner.nextLine();
            int m = Integer.parseInt(mv.substring(0, mv.length() - 1));
            String v = mv.substring(mv.length() - 1, mv.length());
            Node node = null;
            if ("G".equals(v)) {
                m = m * 1000;
            } else if ("T".equals(v)) {
                m = m * 1000 * 1000;
            }
            Node n = new Node(m, mv);
            list.add(n);
        }
        // 排序 也可以用JavaApi进行排序
        ToIntFunction<Node> toIntFunction = node -> node.getVolumeByM();
        list.sort(Comparator.comparingInt(toIntFunction));
//        for (int i = 0; i < values.length; i++) {
//            for (int j = i + 1; j < values.length; j++) {
//                if (values[j] < values[i]) {
//                    int temp = values[j];
//                    values[j] = values[i];
//                    values[i] = temp;
//                }
//            }
//        }
        list.stream().forEach(System.out::println);
    }
}
