package top.godtm.core.search;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

/**
 * TODO
 * Created by jingangsheng on 27/01/2018.
 */
public class SearchTest {
    public static void main(String[] args) throws IOException {
        StringBuilder sb = new StringBuilder();

        CloseableHttpClient httpClient = HttpClients.createDefault();
//        HttpGet httpGet = new HttpGet("http://www.baidu.com/s?ie=utf-8&f=8&rsv_bp=1&tn=baidu&wd=大乐透");
        HttpGet httpGet = new HttpGet("http://kaijiang.500.com/shtml/dlt/18012.shtml");
        CloseableHttpResponse response = httpClient.execute(httpGet);
        try {
            System.out.println(response.getStatusLine());
            HttpEntity entity = response.getEntity();

            String result = EntityUtils.toString(entity);
//            System.out.println(result);

            Document doc = Jsoup.parse(result);
            Elements redBalls = doc.select(".ball_red");
            for (Element redBall : redBalls) {
                sb.append(redBall.text());
                sb.append(",");
            }

            Elements blueBalls = doc.select(".ball_blue");
            for (Element blueBall : blueBalls) {
                sb.append(blueBall.text());
                sb.append(",");
            }

            System.out.println(sb.toString());


            // do something useful with the response body
            // and ensure it is fully consumed
            EntityUtils.consume(entity);
        } finally {
            response.close();
        }

    }
}
