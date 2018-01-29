package top.godtm.ocr;

import java.util.ArrayList;
import java.util.List;

/**
 * 彩票单条记录
 * Created by jingangsheng on 21/01/2018.
 */
public class Record {
    private String rowNumber;
    private List<String> redBalls;
    private List<String> blueBalls;

    public void addRedBall(String number) {
        if (null == redBalls) {
            redBalls = new ArrayList<>(5);
        }
        redBalls.add(number);
    }

    public void addBlueBall(String number) {
        if (null == blueBalls) {
            blueBalls = new ArrayList<>(2);
        }

        blueBalls.add(number);
    }

    public String getRowNumber() {
        return rowNumber;
    }

    public void setRowNumber(String rowNumber) {
        this.rowNumber = rowNumber;
    }

    public List<String> getRedBalls() {
        return redBalls;
    }

    public void setRedBalls(List<String> redBalls) {
        this.redBalls = redBalls;
    }

    public List<String> getBlueBalls() {
        return blueBalls;
    }

    public void setBlueBalls(List<String> blueBalls) {
        this.blueBalls = blueBalls;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(rowNumber).append(" ");
        for (String redBall : redBalls) {
            sb.append(redBall);
        }
        sb.append("+");
        for (String blueBall : blueBalls) {
            sb.append(blueBall);
        }

        return sb.toString();
    }
}
