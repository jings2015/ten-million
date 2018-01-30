package top.godtm.core.search;

import com.google.common.base.Strings;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.springframework.stereotype.Component;
import top.godtm.core.ocr.Record;

import java.net.URL;
import java.util.Arrays;

/**
 * date: 2018/1/30
 * author: wt
 */
@Component
public class SearchDLT implements ISearch {

    private static final String REQUEST_URL = "http://zx.500.com/static/info/kaijiang/xml/dlt/%s.xml";

    @Override
    public Record getAwardNumber(String no) throws Exception {
        if (Strings.isNullOrEmpty(no)) {
            no = "index";
        }
        SAXReader reader = new SAXReader();
        URL url = new URL(String.format(REQUEST_URL, no));
        Document document = reader.read(url);
        Element root = document.getRootElement();
        String periodicalNO = root.elementText("PeriodicalNO");
        String redball = root.elementText("ForeResult");
        String buleball = root.elementText("BackResult");
        String resultTime = root.elementText("ResultTime");
        String encashTime = root.elementText("EncashTime");

        Record record = new Record();
        record.setRowNumber(periodicalNO);
        Arrays.stream(redball.split(",")).forEach(record::addRedBall);
        Arrays.stream(buleball.split(",")).forEach(record::addBlueBall);
        record.setResultTime(resultTime);
        record.setEncashTime(encashTime);
        return record;
    }
}
