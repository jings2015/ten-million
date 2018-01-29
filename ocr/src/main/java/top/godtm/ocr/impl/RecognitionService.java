package top.godtm.ocr.impl;

import com.baidu.aip.ocr.AipOcr;
import org.json.JSONObject;
import top.godtm.ocr.IRecognitionService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * TODO
 * Created by jingangsheng on 20/01/2018.
 */
public class RecognitionService implements IRecognitionService {

    public static final String APP_ID = "10722983";

    public static final String API_KEY = "LoSAHxFARPU3yLoAGqSvTfbR";

    public static final String SECRET_KEY = "A47tqOmd4AXkT5MUdBTMMN2zTfSSlWUw";

    public static void main(String[] args) {
        // 初始化一个AipOcr
        AipOcr client = new AipOcr(APP_ID, API_KEY, SECRET_KEY);

        // 可选：设置网络连接参数
        client.setConnectionTimeoutInMillis(2000);
        client.setSocketTimeoutInMillis(60000);

//        // 可选：设置代理服务器地址, http和socket二选一，或者均不设置
//        client.setHttpProxy("proxy_host", proxy_port);  // 设置http代理
//        client.setSocketProxy("proxy_host", proxy_port);  // 设置socket代理

        Map<String, String> map = new HashMap<>();
//        map.put("probability", "true");


        // 调用接口
//        String path = "test.jpg";
//        String path = "/Users/Joy/Downloads/WechatIMG24.jpeg";
        String path = "/Users/Joy/Downloads/WechatIMG25.jpeg";
        JSONObject res = client.basicGeneral(path, (HashMap<String, String>) map);
//        System.out.println(res.toString(2));
//        System.out.println(res.toString());

        String total = res.toString();

        int[] indexes = new int[6];

        indexes[0] = total.indexOf("①");
        indexes[1] = total.indexOf("②");
        indexes[2] = total.indexOf("③");
        indexes[3] = total.indexOf("④");
        indexes[4] = total.indexOf("⑤");
        indexes[5] = indexes[4] + 20;

        List<Record> recordList = new ArrayList<>(5);

        for (int i = 0; i < indexes.length - 1; i++) {
            recordList.add(getRecord(total, indexes[i], indexes[i + 1]));
        }

        for (Record record : recordList) {
            System.out.println(record.toString());
        }

    }

    private static Record getRecord(String total, int startIndex, int endIndex) {
        String str = getNumbers(total, startIndex, endIndex);

        Record record = new Record();

        String r1 = str.substring(0, 2);
        String r2 = str.substring(2, 4);
        String r3 = str.substring(4, 6);
        String r4 = str.substring(6, 8);
        String r5 = str.substring(8, 10);

        String b1 = str.substring(10, 12);
        String b2 = str.substring(12, 14);

        record.addRedBall(r1);
        record.addRedBall(r2);
        record.addRedBall(r3);
        record.addRedBall(r4);
        record.addRedBall(r5);

        record.addBlueBall(b1);
        record.addBlueBall(b2);

        return record;
    }

    private static String getNumbers(String total, int startIndex, int endIndex) {
        String subStr = total.substring(startIndex, endIndex);

        String reg = "[^0-9]";
        Pattern p = Pattern.compile(reg);
        Matcher m = p.matcher(subStr);

        String ret = m.replaceAll("").trim();

        if (14 != ret.length()) {
            throw new RuntimeException("没有取到14位数字：" + ret);
        }

        return ret;
    }

    public static class Record{
        private List<String> redBalls;
        private List<String> blueBalls;

        public void addRedBall(String number) {
            if (null == redBalls) {
                redBalls = new ArrayList<>(5);
            }
            redBalls.add(number);
        }

        public void addBlueBall(String number) {
            if (null == blueBalls) {
                blueBalls = new ArrayList<>(2);
            }

            blueBalls.add(number);
        }

        public List<String> getRedBalls() {
            return redBalls;
        }

        public void setRedBalls(List<String> redBalls) {
            this.redBalls = redBalls;
        }

        public List<String> getBlueBalls() {
            return blueBalls;
        }

        public void setBlueBalls(List<String> blueBalls) {
            this.blueBalls = blueBalls;
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            for (String redBall : redBalls) {
                sb.append(redBall);
            }
            sb.append("+");
            for (String blueBall : blueBalls) {
                sb.append(blueBall);
            }

            return sb.toString();
        }
    }
}
