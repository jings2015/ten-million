package top.godtm.flow;

import top.godtm.match.MatchResult;
import top.godtm.match.MatcherHelper;
import top.godtm.ocr.OcrHelper;
import top.godtm.ocr.Record;
import top.godtm.ocr.Ticket;
import top.godtm.search.SearchHelper;

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
