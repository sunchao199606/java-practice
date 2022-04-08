package cn.com.sun.algorithm.hw;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

/**
 * @Author : mockingbird
 * @Date : 2022/3/15 20:12
 * @Description : 给定一个选手的实力值数组{1,23,7,8,90.。。。。}，并给选手从0开始编号，每轮比赛id为0的与id为1的比，
 * id为1的与id为2的比，以此类推，实力值相同的话id小的胜出，轮空的自动进入下一轮，输出比赛冠亚季军id
 */
public class Main2 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int[] input = Arrays.stream(scanner.nextLine().split(" ")).mapToInt(Integer::parseInt).toArray();
        List<Player> playerList = new ArrayList<>();
        for (int index = 0; index < input.length; index++) {
            Player player = new Player(index, input[index]);
            playerList.add(player);
        }
        //决赛圈保留3-4人
        while (playerList.size() > 4) {
            //比较的次数
            int time = playerList.size() / 2;
            // 本轮将要淘汰的选手id
            List<Integer> removeIndexList = new ArrayList<>();
            for (int i = 0; i < time; i++) {
                int firstIndex = i * 2;
                int secondIndex = i * 2 + 1;
                Player firstPlayer = playerList.get(firstIndex);
                Player secondPlayer = playerList.get(secondIndex);
                if (secondPlayer.getScore() > firstPlayer.getScore()) {
                    removeIndexList.add(firstIndex);
                } else {
                    removeIndexList.add(secondIndex);
                }
            }
            //淘汰
            for (Integer index : removeIndexList) {
                playerList.set(index, null);
            }
            playerList = playerList.stream().filter(player -> player != null).collect(Collectors.toList());
        }
        //冠军
        Player champion = null;
        //亚军
        Player yajun = null;
        Player jijun = null;
        if (playerList.size() == 4) {
            Player player1 = playerList.get(0);
            Player player2 = playerList.get(1);
            Player player3 = playerList.get(2);
            Player player4 = playerList.get(3);
            //最终决赛
            Player finalPlayer1 = player2.getScore() > player1.getScore() ? player2 : player1;
            Player finalPlayer2 = player4.getScore() > player3.getScore() ? player4 : player3;
            //争夺季军选手
            Player jijunPlayer1 = finalPlayer1 == player2 ? player1 : player2;
            Player jijunPlayer2 = finalPlayer2 == player4 ? player3 : player4;
            //冠军
            champion = finalPlayer2.getScore() > finalPlayer1.getScore() ? finalPlayer2 : finalPlayer1;
            //亚军
            yajun = champion == finalPlayer1 ? finalPlayer2 : finalPlayer1;
            //季军
            jijun = jijunPlayer2.getScore() > jijunPlayer1.getScore() ? jijunPlayer2 : jijunPlayer1;
        } else if (playerList.size() == 3) {
            Player player1 = playerList.get(0);
            Player player2 = playerList.get(1);
            Player player3 = playerList.get(2);
            //最终决赛选手
            Player finalPlayer1 = player2.getScore() > player1.getScore() ? player2 : player1;
            Player finalPlayer2 = player3;
            //季军
            jijun = finalPlayer1 == player2 ? player1 : player2;
            //冠军
            champion = finalPlayer2.getScore() > finalPlayer1.getScore() ? finalPlayer2 : finalPlayer1;
            //亚军
            yajun = champion == finalPlayer1 ? finalPlayer2 : finalPlayer1;
        }
        System.out.println(champion.getId() + " " + yajun.getId() + " " + jijun.getId());
    }

    private static class Player {
        int id;
        int score;

        public Player(int id, int score) {
            this.id = id;
            this.score = score;
        }

        public int getId() {
            return id;
        }

        public int getScore() {
            return score;
        }
    }
}
