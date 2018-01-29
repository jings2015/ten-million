package top.godtm.match;

import top.godtm.ocr.Record;
import top.godtm.ocr.Ticket;

/**
 * 匹配中奖号码
 * Created by jingangsheng on 28/01/2018.
 */
public interface IMatcher {

    MatchResult match(Record awardNo, Ticket ticket);

    String matchSingleRow(Record awardNo, Record ticketRow);
}
