package top.godtm.core.match;

import top.godtm.core.ocr.Record;
import top.godtm.core.ocr.Ticket;

/**
 * TODO
 * Created by jingangsheng on 28/01/2018.
 */
public class MatcherHelper {
    private IMatcher matcher;

    private MatcherHelper() {
        matcher = new Matcher();
    }

    public static MatcherHelper getInstance(){
        return MatcherHelperHolder.INSTANCE;
    }

    private static class MatcherHelperHolder{
        private static MatcherHelper INSTANCE = new MatcherHelper();
    }

    public MatchResult match(Record awardNo, Ticket ticket) {
        return matcher.match(awardNo, ticket);
    }

    public String matchSingleRow(Record awardNo, Record ticketRow) {
        return matcher.matchSingleRow(awardNo, ticketRow);
    }
}
