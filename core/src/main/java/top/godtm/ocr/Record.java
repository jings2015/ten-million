package top.godtm.ocr;

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
    @Getter @Setter
    private String rowNumber;
    @Getter
    private List<String> redBalls = new ArrayList<>(5);
    @Getter
    private List<String> blueBalls = new ArrayList<>(2);

    public Record addRedBall(String number) {
        redBalls.add(number);
        return this;
    }

    public Record addBlueBall(String number) {
        blueBalls.add(number);
        return this;
    }

}
