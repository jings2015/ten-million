package top.godtm.core.match;

import org.springframework.stereotype.Component;
import top.godtm.core.ocr.Record;
import top.godtm.core.ocr.Ticket;

import java.util.ArrayList;
import java.util.List;

/**
 * TODO
 * Created by jingangsheng on 28/01/2018.
 */
@Component
public class Matcher implements IMatcher {
    @Override
    public MatchResult match(Record awardNo, Ticket ticket) {
        MatchResult matchResult = new MatchResult();
        matchResult.setAwardNo(awardNo);
        matchResult.setTicket(ticket);
        List<String> resultList = new ArrayList<>();

        List<Record> ticketRowList = ticket.getRecordList();
        for (Record record : ticketRowList) {
            String singleResult = this.matchSingleRow(awardNo, record);
            resultList.add(singleResult);
        }

        matchResult.setMatchResult(resultList);
        return matchResult;
    }

    @Override
    public String matchSingleRow(Record awardNo, Record ticketRow) {
        StringBuilder sb = new StringBuilder(ticketRow.getRowNumber()).append(":");
        int matchedRed = 0;
        int matchedBlue = 0;

        List<String> awardRedBallList = awardNo.getRedBalls();
        List<String> ticketRedBallList = ticketRow.getRedBalls();
        for (String ticketRedBall : ticketRedBallList) {
            if (awardRedBallList.contains(ticketRedBall)) {
                matchedRed++;
                sb.append("[").append(ticketRedBall).append("] ");
            }else{
                sb.append(ticketRedBall).append(" ");
            }
        }

        List<String> awardBlueBallList = awardNo.getBlueBalls();
        List<String> ticketBlueBallList = ticketRow.getBlueBalls();
        for (String ticketBlueBall : ticketBlueBallList) {
            if (awardBlueBallList.contains(ticketBlueBall)) {
                matchedBlue++;
                sb.append("[").append(ticketBlueBall).append("] ");
            }else {
                sb.append(ticketBlueBall).append(" ");
            }
        }

        sb.append(this.matchAmount(matchedRed, matchedBlue));

        return sb.toString();
    }

    private String matchAmount(int redCount, int blueCount) {
        int number = redCount * 10 + blueCount;

        switch (number) {
            case 52: return "【一等奖 浮动金额】";
            case 51: return "【二等奖 浮动金额】";
            case 50:
            case 42: return "【三等奖 浮动金额】";
            case 41:
            case 32: return "【四等奖 200元】";
            case 40:
            case 31:
            case 22: return "【五等奖 10元】";
            case 30:
            case 12:
            case 21:
            case 2: return "【六等奖 5元】";
            default:
                return "";

        }
    }
}
