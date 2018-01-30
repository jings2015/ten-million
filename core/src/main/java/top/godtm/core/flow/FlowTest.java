package top.godtm.core.flow;

import top.godtm.core.ocr.OcrHelper;
import top.godtm.core.match.MatchResult;
import top.godtm.core.match.MatcherHelper;
import top.godtm.core.ocr.Record;
import top.godtm.core.ocr.Ticket;
import top.godtm.core.search.SearchHelper;

/**
 * TODO
 * Created by jingangsheng on 28/01/2018.
 */
public class FlowTest {

    public static void main(String[] args) {
        OcrHelper ocrHelper = OcrHelper.getInstance();
        SearchHelper searchHelper = SearchHelper.getInstance();
        MatcherHelper matcherHelper = MatcherHelper.getInstance();

        Ticket ticket = ocrHelper.recognitionPic("/Users/Joy/Downloads/WechatIMG28.jpeg");
        Record awardRecord = searchHelper.getAwardNumber(ticket.getNo());
        MatchResult matchResult = matcherHelper.match(awardRecord, ticket);

        System.out.println(matchResult.toString());

    }
}
