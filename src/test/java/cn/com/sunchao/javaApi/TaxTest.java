package cn.com.sunchao.javaApi;

import java.util.Arrays;

/**
 * @Description : 个人所得税计算器
 * @Author :sunchao
 * @Date: 2020-06-25 16:32
 */
public class TaxTest {
    private static float INSURE = 1115;

    public static float calTax(int[] incomes, float payedTax) {
        float sumIncome = Arrays.stream(incomes).sum();
        float sumAllowance = 5000 * incomes.length;
        float sumExtra = 1500 * incomes.length;
        float taxAmount = sumIncome - sumAllowance - sumExtra;
        float taxRate = 0.03f;
        float subNum = 0;
        if (taxAmount > 36000 && taxAmount <= 144000) {
            taxRate = 0.1f;
            subNum = 2520;
        } else if (taxAmount > 144000 && taxAmount <= 300000) {
            taxRate = 0.2f;
            subNum = 16920;
        } else if (taxAmount > 300000 && taxAmount <= 420000) {
            taxRate = 0.25f;
            subNum = 31920;
        } else if (taxAmount > 420000 && taxAmount <= 660000) {
            taxRate = 0.3f;
            subNum = 52920;
        } else if (taxAmount > 660000 && taxAmount <= 960000) {
            taxRate = 0.35f;
            subNum = 85920;
        } else if (taxAmount > 960000) {
            taxRate = 0.45f;
            subNum = 181920;
        }
        float tax = (sumIncome - sumAllowance - sumExtra) * taxRate - subNum - payedTax;
        if (tax < 0) {
            tax = 0f;
        }
        return tax;
    }

    public static void main(String[] args) {
        int[] allIncomes = new int[]{12457};
        float payedTax = 0;
        for (int i = 1; i <= allIncomes.length; i++) {
            int[] incomes = Arrays.copyOfRange(allIncomes, 0, i);
            float currentMonthTax = calTax(incomes, payedTax);
            System.out.println(i + "月缴税额：" + currentMonthTax);
            payedTax = payedTax + currentMonthTax;
        }
    }
}
