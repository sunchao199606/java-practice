package cn.com.sun;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class TaxCalculator {

    public static void main(String[] args) {
        double[] salaryArr = new double[]{11502, 11454, 11890, 11508, 11502, 11438, 11486, 14502, 14404, 14486, 14422, 14502};
        calMonthlyTaxAndActualSalary(salaryArr, 1500, 5000.0, 0.223);
    }

    /**
     * 计算每月个税以及到手工资
     *
     * @param salaryArr     二维数组 [[工资，补贴]]
     * @param special       专项扣除
     * @param insuranceBase 五险一金缴纳基数
     * @param insuranceRate 五险一金个人缴纳比例 22.3%
     */
    public static void calMonthlyTaxAndActualSalary(double[] salaryArr, double special, double insuranceBase, double insuranceRate) {
        // 累计预扣缴额
        double totalShouldTax = 0.0;
        // 累计已缴税额
        BigDecimal totalHadTax = new BigDecimal(0.0);

        for (int index = 0; index < salaryArr.length; index++) {
            if (index == 0) System.out.println("***********************");
            double salary = salaryArr[index];
            // 当月应纳税额(每月的应纳税额 = 工资- 起征点 - 专项扣除 - 五险一金)
            System.out.println((index + 1) + "月税前总工资：" + salary);
            double monthlyShouldTax = salary - 5000 - special - insuranceBase * insuranceRate;
            if (monthlyShouldTax < 0.0) {
                System.out.println((index + 1) + "月无需缴个税");
                monthlyShouldTax = 0.0;
            }
            totalShouldTax += monthlyShouldTax;
            // 累计应缴税额(累计应缴税额 * 税率 - 速算扣除数`)
            double totalTax;
            if (totalShouldTax <= 36000.0) {
                totalTax = totalShouldTax * 0.03;
            } else if (totalShouldTax <= 144000.0) {
                totalTax = totalShouldTax * 0.1 - 2520;
            } else if (totalShouldTax <= 300000.0) {
                totalTax = totalShouldTax * 0.2 - 16920;
            } else if (totalShouldTax <= 420000.0) {
                totalTax = totalShouldTax * 0.25 - 31920;
            } else if (totalShouldTax <= 660000.0) {
                totalTax = totalShouldTax * 0.30 - 52920;
            } else if (totalShouldTax <= 960000.0) {
                totalTax = totalShouldTax * 0.35 - 85920;
            } else {
                totalTax = totalShouldTax * 0.45 - 181920;
            }
            // 当月应缴税(当月累计应缴税 - 上月累计应缴税(累计已缴税))
            BigDecimal curTax = new BigDecimal(totalTax - totalHadTax.doubleValue());
            curTax = curTax.setScale(2, RoundingMode.HALF_UP);
            System.out.println((index + 1) + "月个税：" + curTax);
            // 当月到手工资(当月工资 = 基本工资 + 浮动奖金(绩效工资) - 当月应缴税 - 五险一金)
            double curSal = salary - curTax.doubleValue() - insuranceBase * insuranceRate;
            System.out.println((index + 1) + "月到手工资：" + curSal);
            totalHadTax = new BigDecimal(totalTax);
            System.out.println("***********************");
        }
        System.out.println("个税已缴总额：" + totalHadTax.setScale(2, RoundingMode.HALF_UP));
    }


//    /**
//     * 计算个人所得税
//     * www.xcc.cn 版权所有
//     * @param minusAmount 扣除社保的金额
//     * @param minusAmount2 当月工资(扣除社保后)(算年终奖)
//     * @param salaryType 收入类型
//     * @param minimumAmount 个税基数，5000
//     * */
//    public GeshuiDto calc(double originAmount, double minusAmount, double minusAmount2, String salaryType, int minimumAmount) {
//        GeshuiDto dto = new GeshuiDto();
//        dto.originAmount = originAmount;
//        dto.minusAmount = minusAmount;
//        dto.taxAmount = 0;
//        dto.finalAmount = 0;
//
//        if(originAmount == 0){
//            return dto;
//        }
//
//        //工资、薪金所得
//        if(Objects.equal("1", salaryType)){
//            double balanceAmount = BigDecimal.valueOf(originAmount)
//                    .subtract(BigDecimal.valueOf(minusAmount))
//                    .subtract(BigDecimal.valueOf(minimumAmount)).doubleValue();
//            dto.balanceAmount = balanceAmount;
//            if(minimumAmount == 5000) {
//                if(balanceAmount >0 && balanceAmount <=3000){
//                    dto.taxAmount = BigDecimal.valueOf(balanceAmount).multiply(BigDecimal.valueOf(0.03)).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
//                }
//                if(balanceAmount >3000 && balanceAmount <=12000){
//                    dto.taxAmount = BigDecimal.valueOf(balanceAmount).multiply(BigDecimal.valueOf(0.1)).setScale(2, BigDecimal.ROUND_HALF_UP).subtract(BigDecimal.valueOf(210)).doubleValue();
//                }
//                if(balanceAmount >12000 && balanceAmount <=25000){
//                    dto.taxAmount = BigDecimal.valueOf(balanceAmount).multiply(BigDecimal.valueOf(0.2)).setScale(2, BigDecimal.ROUND_HALF_UP).subtract(BigDecimal.valueOf(1410)).doubleValue();
//                }
//                if(balanceAmount >25000 && balanceAmount <=35000){
//                    dto.taxAmount = BigDecimal.valueOf(balanceAmount).multiply(BigDecimal.valueOf(0.25)).setScale(2, BigDecimal.ROUND_HALF_UP).subtract(BigDecimal.valueOf(2660)).doubleValue();
//                }
//                if(balanceAmount >35000 && balanceAmount <=55000){
//                    dto.taxAmount = BigDecimal.valueOf(balanceAmount).multiply(BigDecimal.valueOf(0.3)).setScale(2, BigDecimal.ROUND_HALF_UP).subtract(BigDecimal.valueOf(4410)).doubleValue();
//                }
//                if(balanceAmount >55000 && balanceAmount <=80000){
//                    dto.taxAmount = BigDecimal.valueOf(balanceAmount).multiply(BigDecimal.valueOf(0.35)).setScale(2, BigDecimal.ROUND_HALF_UP).subtract(BigDecimal.valueOf(7160)).doubleValue();
//                }
//                if(balanceAmount >80000 ){
//                    dto.taxAmount = BigDecimal.valueOf(balanceAmount).multiply(BigDecimal.valueOf(0.45)).setScale(2, BigDecimal.ROUND_HALF_UP).subtract(BigDecimal.valueOf(15160)).doubleValue();
//                }
//            }else {
//                if(balanceAmount >0 && balanceAmount <=1500){
//                    dto.taxAmount = BigDecimal.valueOf(balanceAmount).multiply(BigDecimal.valueOf(0.03)).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
//                }
//                if(balanceAmount >1500 && balanceAmount <=4500){
//                    dto.taxAmount = BigDecimal.valueOf(balanceAmount).multiply(BigDecimal.valueOf(0.1)).setScale(2, BigDecimal.ROUND_HALF_UP).subtract(BigDecimal.valueOf(105)).doubleValue();
//                }
//                if(balanceAmount >4500 && balanceAmount <=9000){
//                    dto.taxAmount = BigDecimal.valueOf(balanceAmount).multiply(BigDecimal.valueOf(0.2)).setScale(2, BigDecimal.ROUND_HALF_UP).subtract(BigDecimal.valueOf(555)).doubleValue();
//                }
//                if(balanceAmount >9000 && balanceAmount <=35000){
//                    dto.taxAmount = BigDecimal.valueOf(balanceAmount).multiply(BigDecimal.valueOf(0.25)).setScale(2, BigDecimal.ROUND_HALF_UP).subtract(BigDecimal.valueOf(1005)).doubleValue();
//                }
//                if(balanceAmount >35000 && balanceAmount <=55000){
//                    dto.taxAmount = BigDecimal.valueOf(balanceAmount).multiply(BigDecimal.valueOf(0.3)).setScale(2, BigDecimal.ROUND_HALF_UP).subtract(BigDecimal.valueOf(2755)).doubleValue();
//                }
//                if(balanceAmount >55000 && balanceAmount <=80000){
//                    dto.taxAmount = BigDecimal.valueOf(balanceAmount).multiply(BigDecimal.valueOf(0.35)).setScale(2, BigDecimal.ROUND_HALF_UP).subtract(BigDecimal.valueOf(5505)).doubleValue();
//                }
//                if(balanceAmount >80000 ){
//                    dto.taxAmount = BigDecimal.valueOf(balanceAmount).multiply(BigDecimal.valueOf(0.45)).setScale(2, BigDecimal.ROUND_HALF_UP).subtract(BigDecimal.valueOf(13505)).doubleValue();
//                }
//            }
//
//        }else if(Objects.equal("2", salaryType)){ //个体工商户生产、经营所得
//            double balanceAmount = originAmount;
//            dto.balanceAmount = balanceAmount;
//            if(balanceAmount >0 && balanceAmount <= 15000){
//                dto.taxAmount = BigDecimal.valueOf(balanceAmount).multiply(BigDecimal.valueOf(0.05)).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
//            }
//            if(balanceAmount > 15000 && balanceAmount <= 30000){
//                dto.taxAmount = BigDecimal.valueOf(balanceAmount).multiply(BigDecimal.valueOf(0.1)).setScale(2, BigDecimal.ROUND_HALF_UP).subtract(BigDecimal.valueOf(750)).doubleValue();
//            }
//            if(balanceAmount > 30000 && balanceAmount <= 60000){
//                dto.taxAmount = BigDecimal.valueOf(balanceAmount).multiply(BigDecimal.valueOf(0.2)).setScale(2, BigDecimal.ROUND_HALF_UP).subtract(BigDecimal.valueOf(3750)).doubleValue();
//            }
//            if(balanceAmount > 60000 && balanceAmount <= 100000){
//                dto.taxAmount = BigDecimal.valueOf(balanceAmount).multiply(BigDecimal.valueOf(0.3)).setScale(2, BigDecimal.ROUND_HALF_UP).subtract(BigDecimal.valueOf(9750)).doubleValue();
//            }
//            if(balanceAmount > 100000){
//                dto.taxAmount = BigDecimal.valueOf(balanceAmount).multiply(BigDecimal.valueOf(0.35)).setScale(2, BigDecimal.ROUND_HALF_UP).subtract(BigDecimal.valueOf(14750)).doubleValue();
//            }
//        }else if(Objects.equal("3", salaryType)){ //年终奖所得
//            double balanceAmount = 0;
//            if(minusAmount2 - minimumAmount < 0){
//                balanceAmount = BigDecimal.valueOf(originAmount).subtract(BigDecimal.valueOf(3500-minusAmount2)).doubleValue();
//            }else{
//                balanceAmount = originAmount;
//            }
//            dto.balanceAmount = balanceAmount;
//            double avg_balanceAmount = balanceAmount/12;
//            if(minimumAmount == 5000) {
//                if(avg_balanceAmount >0 && avg_balanceAmount<=3000){
//                    dto.taxAmount = BigDecimal.valueOf(balanceAmount).multiply(BigDecimal.valueOf(0.03)).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
//                }
//                if(avg_balanceAmount >3000 && avg_balanceAmount<=12000){
//                    dto.taxAmount = BigDecimal.valueOf(balanceAmount).multiply(BigDecimal.valueOf(0.1)).setScale(2, BigDecimal.ROUND_HALF_UP).subtract(BigDecimal.valueOf(210)).doubleValue();
//                }
//                if(avg_balanceAmount >12000 && avg_balanceAmount<=25000){
//                    dto.taxAmount = BigDecimal.valueOf(balanceAmount).multiply(BigDecimal.valueOf(0.2)).setScale(2, BigDecimal.ROUND_HALF_UP).subtract(BigDecimal.valueOf(1410)).doubleValue();
//                }
//                if(avg_balanceAmount >25000 && avg_balanceAmount<=35000){
//                    dto.taxAmount = BigDecimal.valueOf(balanceAmount).multiply(BigDecimal.valueOf(0.25)).setScale(2, BigDecimal.ROUND_HALF_UP).subtract(BigDecimal.valueOf(2660)).doubleValue();
//                }
//                if(avg_balanceAmount >35000 && avg_balanceAmount<=55000){
//                    dto.taxAmount = BigDecimal.valueOf(balanceAmount).multiply(BigDecimal.valueOf(0.3)).setScale(2, BigDecimal.ROUND_HALF_UP).subtract(BigDecimal.valueOf(4410)).doubleValue();
//                }
//                if(avg_balanceAmount >55000 && avg_balanceAmount<=80000){
//                    dto.taxAmount = BigDecimal.valueOf(balanceAmount).multiply(BigDecimal.valueOf(0.35)).setScale(2, BigDecimal.ROUND_HALF_UP).subtract(BigDecimal.valueOf(7160)).doubleValue();
//                }
//                if(avg_balanceAmount >80000){
//                    dto.taxAmount = BigDecimal.valueOf(balanceAmount).multiply(BigDecimal.valueOf(0.45)).setScale(2, BigDecimal.ROUND_HALF_UP).subtract(BigDecimal.valueOf(15160)).doubleValue();
//                }
//            }else {
//                if(avg_balanceAmount >0 && avg_balanceAmount<=1500){
//                    dto.taxAmount = BigDecimal.valueOf(balanceAmount).multiply(BigDecimal.valueOf(0.03)).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
//                }
//                if(avg_balanceAmount >1500 && avg_balanceAmount<=4500){
//                    dto.taxAmount = BigDecimal.valueOf(balanceAmount).multiply(BigDecimal.valueOf(0.1)).setScale(2, BigDecimal.ROUND_HALF_UP).subtract(BigDecimal.valueOf(105)).doubleValue();
//                }
//                if(avg_balanceAmount >4500 && avg_balanceAmount<=9000){
//                    dto.taxAmount = BigDecimal.valueOf(balanceAmount).multiply(BigDecimal.valueOf(0.2)).setScale(2, BigDecimal.ROUND_HALF_UP).subtract(BigDecimal.valueOf(555)).doubleValue();
//                }
//                if(avg_balanceAmount >9000 && avg_balanceAmount<=35000){
//                    dto.taxAmount = BigDecimal.valueOf(balanceAmount).multiply(BigDecimal.valueOf(0.25)).setScale(2, BigDecimal.ROUND_HALF_UP).subtract(BigDecimal.valueOf(1005)).doubleValue();
//                }
//                if(avg_balanceAmount >35000 && avg_balanceAmount<=55000){
//                    dto.taxAmount = BigDecimal.valueOf(balanceAmount).multiply(BigDecimal.valueOf(0.3)).setScale(2, BigDecimal.ROUND_HALF_UP).subtract(BigDecimal.valueOf(2775)).doubleValue();
//                }
//                if(avg_balanceAmount >55000 && avg_balanceAmount<=80000){
//                    dto.taxAmount = BigDecimal.valueOf(balanceAmount).multiply(BigDecimal.valueOf(0.35)).setScale(2, BigDecimal.ROUND_HALF_UP).subtract(BigDecimal.valueOf(5505)).doubleValue();
//                }
//                if(avg_balanceAmount >80000){
//                    dto.taxAmount = BigDecimal.valueOf(balanceAmount).multiply(BigDecimal.valueOf(0.45)).setScale(2, BigDecimal.ROUND_HALF_UP).subtract(BigDecimal.valueOf(13505)).doubleValue();
//                }
//            }
//
//        }else if(Objects.equal("4", salaryType)){ //劳务报酬所得
//            dto.balanceAmount = originAmount;
//            if(originAmount>0 && originAmount <= 800){
//                dto.taxAmount =0;
//            }else if(originAmount > 800){
//                double balanceAmount = 0;
//                if(originAmount >800 && originAmount <= 4000){
//                    balanceAmount = originAmount-800;
//                }
//                if(originAmount > 4000){
//                    balanceAmount = originAmount*0.8;
//                }
//                if(balanceAmount >0 && balanceAmount <= 20000){
//                    dto.taxAmount = BigDecimal.valueOf(balanceAmount).multiply(BigDecimal.valueOf(0.2)).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
//                }
//                if(balanceAmount >20000 && balanceAmount <= 50000){
//                    dto.taxAmount = BigDecimal.valueOf(balanceAmount).multiply(BigDecimal.valueOf(0.3)).setScale(2, BigDecimal.ROUND_HALF_UP).subtract(BigDecimal.valueOf(2000)).doubleValue();
//                }
//                if(balanceAmount >50000){
//                    dto.taxAmount = BigDecimal.valueOf(balanceAmount).multiply(BigDecimal.valueOf(0.4)).setScale(2, BigDecimal.ROUND_HALF_UP).subtract(BigDecimal.valueOf(7000)).doubleValue();
//                }
//            }
//        }else if(Objects.equal("5", salaryType)){ //稿酬所得
//            double balanceAmount = 0;
//            if(originAmount >0 && originAmount <= 4000){
//                balanceAmount = originAmount-800;
//            }else if(originAmount > 4000){
//                balanceAmount = originAmount*0.8;
//            }
//            dto.balanceAmount = balanceAmount;
//            dto.taxAmount = BigDecimal.valueOf(balanceAmount).multiply(BigDecimal.valueOf(0.14)).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
//        }else if(Objects.equal("6", salaryType)){ //特许权使用费所得
//            double balanceAmount = 0;
//            if(originAmount >0 && originAmount <= 4000){
//                balanceAmount = originAmount-800;
//            }else if(originAmount > 4000){
//                balanceAmount = originAmount*0.8;
//            }
//            dto.balanceAmount = balanceAmount;
//            dto.taxAmount = BigDecimal.valueOf(balanceAmount).multiply(BigDecimal.valueOf(0.2)).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
//        }else if(Objects.equal("7", salaryType)){ //财产租赁所得
//            double balanceAmount = 0;
//            if(originAmount >0 && originAmount <= 4000){
//                balanceAmount = originAmount-800;
//            }else if(originAmount > 4000){
//                balanceAmount = originAmount*0.8;
//            }
//            dto.balanceAmount = balanceAmount;
//            dto.taxAmount = BigDecimal.valueOf(balanceAmount).multiply(BigDecimal.valueOf(0.2)).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
//        }else if(Objects.equal("8", salaryType)){ //财产转让所得
//            dto.balanceAmount = originAmount;
//            dto.taxAmount = BigDecimal.valueOf(originAmount).multiply(BigDecimal.valueOf(0.2)).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
//        }else if(Objects.equal("9", salaryType)){ //利息、股息、红利所得
//            dto.balanceAmount = originAmount;
//            dto.taxAmount = BigDecimal.valueOf(originAmount).multiply(BigDecimal.valueOf(0.2)).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
//        }else if(Objects.equal("10", salaryType)){ //偶然所得
//            dto.balanceAmount = originAmount;
//            dto.taxAmount = BigDecimal.valueOf(originAmount).multiply(BigDecimal.valueOf(0.2)).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
//        }else{
//            return dto;
//        }
//        if(Objects.equal("1", salaryType)){
//            dto.finalAmount = BigDecimal.valueOf(originAmount)
//                    .subtract(BigDecimal.valueOf(minusAmount))
//                    .subtract(BigDecimal.valueOf(dto.taxAmount))
//                    .setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
//        }else{
//            dto.finalAmount = BigDecimal.valueOf(originAmount)
//                    .subtract(BigDecimal.valueOf(dto.taxAmount))
//                    .setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
//        }
//        return dto;
//    }
}
