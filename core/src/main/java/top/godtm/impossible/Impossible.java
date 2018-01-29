package top.godtm.impossible;

import top.godtm.match.MatcherHelper;
import top.godtm.ocr.Record;

import java.math.BigDecimal;
import java.text.DecimalFormat;

/**
 * TODO
 * Created by jingangsheng on 28/01/2018.
 */
public class Impossible {
    public static void main(String[] args) {

        DecimalFormat df = new DecimalFormat("###,###");

        Record award = randomRecord();
        System.out.println(award.toString());
        System.out.println("**********************************");

        MatcherHelper matcherHelper = MatcherHelper.getInstance();

        int[] counts = new int[6];
        int j = 0;
        Long amount = 0L;
        for (int i = 0; i < 1000; i++, j++) {
            Record ticket = randomRecord();

            String result = matcherHelper.matchSingleRow(award, ticket);

            if (result.contains("奖")) {
                if (result.contains("一等奖")) {
                    counts[0]++;
                    amount += 1000_0000L;
                } else if (result.contains("二等奖")) {
                    counts[1]++;
                    amount += 10_0000L;
                } else if (result.contains("三等奖")) {
                    counts[2]++;
                    amount += 1_0000L;
                } else if (result.contains("四等奖")) {
                    counts[3]++;
                    amount += 200;
                } else if (result.contains("五等奖")) {
                    counts[4]++;
                    amount += 10;
                } else if (result.contains("六等奖")) {
                    counts[5]++;
                    amount += 5;
                }
            }

//            if (counts[0] != 0 || j == 10_0000) {
            if (true) {
                j = 0;
                StringBuilder sb = new StringBuilder();
                for (int count : counts) {
                    sb.append(count).append("\t");
                }

                sb.append("|\t").append((i + 1) * 2).append("\t").append(amount).append("\t");
                sb.append(new BigDecimal(amount).divide(new BigDecimal((i + 1) * 2), 8, BigDecimal.ROUND_HALF_UP));
                System.out.println("第" + df.format(i + 1) + "次：" + sb.toString());
                if (counts[0] != 0) {
                    break;
                }
            }

        }
    }

    private static Record randomRecord(){
        Record record = new Record();
        record.setRowNumber("");

        for (int i = 0; i < 5; i++) {
            String number = randomNumber(35);
            if (null == record.getRedBalls() || !record.getRedBalls().contains(number)) {
                record.addRedBall(number);
            }else{
                i--;
            }
        }

        for (int i = 0; i < 2; i++) {
            String number = randomNumber(12);
            if (null == record.getBlueBalls() || !record.getBlueBalls().contains(number)) {
                record.addBlueBall(number);
            }else{
                i--;
            }
        }

        return record;
    }

    private static String randomNumber(int max) {
        int number = (int) (Math.random() * max + 1);
        if (number < 10) {
            return "0" + number;
        }else{
            return String.valueOf(number);
        }
    }
}
