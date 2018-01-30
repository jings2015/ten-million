package top.godtm.core.search;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;
import top.godtm.core.ocr.Record;
import top.godtm.core.exception.BizException;

import java.io.IOException;

/**
 * TODO
 * Created by jingangsheng on 28/01/2018.
 */
@Component
public class SearchKaiJiang500 implements ISearch {
    public static final String URL_TEMPLATE = "http://kaijiang.500.com/shtml/dlt/?.shtml";

    @Override
    public Record getAwardNumber(String no) throws IOException {

        String url = URL_TEMPLATE.replace("?", no);

        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(url);
        Record record = new Record();
        String resultHtml = null;

        // 抓取开奖号码
        CloseableHttpResponse response = null;
        try {
            response = httpClient.execute(httpGet);
            resultHtml = EntityUtils.toString(response.getEntity());
        } finally {
            try {
                response.close();
            } catch (Exception e) {
            }
        }

        Document doc = Jsoup.parse(resultHtml);

        // 识别红球
        Elements redBalls = doc.select(".ball_red");
        if (5 != redBalls.size()) {
            throw new BizException("搜索开奖500红球数量不正确：" + redBalls.size());
        }

        for (Element redBall : redBalls) {
            String redBallText = redBall.text();
            if (!redBallText.matches("[0-9]+")) {
                throw new BizException("查询彩票结果不正确：" + no);
            }
            record.addRedBall(redBallText);
        }

        // 识别蓝球
        Elements blueBalls = doc.select(".ball_blue");
        if (2 != blueBalls.size()) {
            throw new BizException("搜索开奖500蓝球数量不正确：" + blueBalls.size());
        }

        for (Element blueBall : blueBalls) {
            record.addBlueBall(blueBall.text());
        }

        return record;
    }
}
