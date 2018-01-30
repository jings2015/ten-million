package top.godtm.core.ocr;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

/**
 * 彩票单条记录
 * Created by jingangsheng on 21/01/2018.
 */
@ToString
public class Record {
    @Getter
    @Setter
    private String rowNumber;
    @Getter
    private List<String> redBalls = new ArrayList<>();
    @Getter
    private List<String> blueBalls = new ArrayList<>();

    /**
     * 开奖时间
     */
    @Getter
    @Setter
    private String resultTime;
    /**
     * 兑奖截止时间
     */
    @Getter
    @Setter
    private String encashTime;

    public Record addRedBall(String number) {
        if (redBalls.size() == 5) {
            return this;
        }
        redBalls.add(number);
        return this;
    }

    public Record addBlueBall(String number) {
        if (blueBalls.size() == 2) {
            return this;
        }
        blueBalls.add(number);
        return this;
    }

}
