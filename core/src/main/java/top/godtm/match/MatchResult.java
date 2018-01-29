package top.godtm.match;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import top.godtm.ocr.Record;
import top.godtm.ocr.Ticket;

import java.util.List;

/**
 * TODO
 * Created by jingangsheng on 28/01/2018.
 */
@Getter
@Setter
public class MatchResult {

    private Ticket ticket;

    private Record awardNo;

    private List<String> matchResult;

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("期数：").append(ticket.getNo()).append("\n");
        sb.append("开奖号码为：\n");
        for (String s : awardNo.getRedBalls()) {
            sb.append(s).append(" ");
        }

        sb.append("| ");
        for (String s : awardNo.getBlueBalls()) {
            sb.append(s).append(" ");
        }

        sb.append("\n");
        sb.append("中奖情况为：\n");
        for (String s : matchResult) {
            sb.append(s).append("\n");
        }
        return sb.toString();
    }
}
