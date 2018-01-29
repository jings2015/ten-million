package top.godtm.ocr;

import com.baidu.aip.ocr.AipOcr;
import org.json.JSONObject;
import top.godtm.properties.OcrProperties;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 百度ocr识别彩票
 * Created by jingangsheng on 21/01/2018.
 */
public class BaiduOcr implements IOcr{
    public static final String APP_ID = OcrProperties.appId;

    public static final String API_KEY = OcrProperties.apiKey;

    public static final String SECRET_KEY = OcrProperties.secretKey;

    public static final String[] ROW_NUMBERS = {"①", "②", "③", "④", "⑤"};

    /**
     * 识别彩票记录
     *
     * @param path
     * @return
     */
    @Override
    public Ticket recognitionPic(String path) {
        // 获取照片文字
        String picWords = this.getPicWords(path);

        int[] indexes = new int[ROW_NUMBERS.length];
        for (int i = 0; i < ROW_NUMBERS.length; i++) {
            indexes[i] = picWords.indexOf(ROW_NUMBERS[i]);
        }

        List<Record> recordList = new ArrayList<>(5);
        for (int i = 0; i < indexes.length; i++) {
            Record record = this.getRecord(picWords, indexes[i], indexes[i] + 35);
            if (null != record) {
                record.setRowNumber(ROW_NUMBERS[i]);
                recordList.add(record);
            }
        }

        String no = this.getTicketNo(picWords);

        Ticket ticket = new Ticket();
        ticket.setNo(no);
        ticket.setRecordList(recordList);

        return ticket;
    }

    @Override
    public Ticket recognitionPic(byte[] bytes) {
        // 获取照片文字
        String picWords = this.getPicWords(bytes);

        int[] indexes = new int[ROW_NUMBERS.length];
        for (int i = 0; i < ROW_NUMBERS.length; i++) {
            indexes[i] = picWords.indexOf(ROW_NUMBERS[i]);
        }

        List<Record> recordList = new ArrayList<>(5);
        for (int i = 0; i < indexes.length; i++) {
            Record record = this.getRecord(picWords, indexes[i], indexes[i] + 35);
            if (null != record) {
                record.setRowNumber(ROW_NUMBERS[i]);
                recordList.add(record);
            }
        }

        String no = this.getTicketNo(picWords);

        Ticket ticket = new Ticket();
        ticket.setNo(no);
        ticket.setRecordList(recordList);

        return ticket;
    }

    /**
     * 获取照片文字
     *
     * @param path
     * @return
     */
    private String getPicWords(String path) {
        // 初始化一个AipOcr
        AipOcr client = new AipOcr(APP_ID, API_KEY, SECRET_KEY);

        // 可选：设置网络连接参数
        client.setConnectionTimeoutInMillis(2000);
        client.setSocketTimeoutInMillis(60000);

        // 调用接口
        JSONObject res = client.basicGeneral(path, new HashMap<String, String>());

        return res.toString();
    }

    /**
     * 获取照片文字
     *
     * @param bytes
     * @return
     */
    private String getPicWords(byte[] bytes) {
        // 初始化一个AipOcr
        AipOcr client = new AipOcr(APP_ID, API_KEY, SECRET_KEY);

        // 可选：设置网络连接参数
        client.setConnectionTimeoutInMillis(2000);
        client.setSocketTimeoutInMillis(60000);

        // 调用接口
        JSONObject res = client.basicGeneral(bytes, new HashMap<String, String>());

        return res.toString();
    }

    /**
     * 根据照片文字获取彩票记录
     *
     * @param picWords
     * @param startIndex
     * @param endIndex
     * @return
     */
    private Record getRecord(String picWords, int startIndex, int endIndex) {
        if (-1 == startIndex) {
            return null;
        }
        String str = this.getNumbers(picWords, startIndex, endIndex);

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

    /**
     * 从照片文字中取出对应位置的数字
     *
     * @param picWords
     * @param startIndex
     * @param endIndex
     * @return
     */
    private String getNumbers(String picWords, int startIndex, int endIndex) {
        String subStr = picWords.substring(startIndex, endIndex);

        String ret = this.getNumbersFromString(subStr);

        if (14 > ret.length()) {
            throw new RuntimeException("没有取到14位数字：" + ret);
        } else if (14 < ret.length()) {
            ret = ret.substring(0, 14);
        }

        return ret;
    }

    private String getNumbersFromString(String string) {
        String reg = "[^0-9]";
        Pattern p = Pattern.compile(reg);
        Matcher m = p.matcher(string);

        String numbers = m.replaceAll("").trim();

        return numbers;
    }

    private String getTicketNo(String picWords) {
        String reg = "(第[0-9]+期)";
        Pattern p = Pattern.compile(reg);
        Matcher m = p.matcher(picWords);
        String matchString = null;
        if (m.find()) {
            matchString = m.group(1);
        }else{
            System.out.println("not found");
        }
        String no = this.getNumbersFromString(matchString);

        return no;
    }

    public static void main(String[] args) {
        BaiduOcr baiduOcr = new BaiduOcr();
        Ticket ticket = baiduOcr.recognitionPic("/Users/Joy/Downloads/WechatIMG24.jpeg");
        System.out.println(ticket.toString());

//        baiduOcr.getTicketNo("{\"words_result\":[{\"words\":\"体彩级大\"},{\"words\":\"中国体育彩\"},{\"words\":\"第18008期\"},{\"words\":\"2018年01月17日开奖票\"},{\"words\":\"110339-732100-073848-098801125426fq8txw\"},{\"words\":\"单式票\"},{\"words\":\"2倍\"},{\"words\":\"合计20元\"},{\"words\":\"①0508103233+0212\"},{\"words\":\"②1013283032\"},{\"words\":\"0104中\"},{\"words\":\"③0307162635+0912\"},{\"words\":\"④0104122223+0610育\"},{\"words\":\"⑤0612152730+0204形\"},{\"words\":\"法甲赛事推荐01月17日04:00摩纳哥VS尼斯\"},{\"words\":\"体彩11选5促销岁末感恩奖上送票\"},{\"words\":\"滨江区江汉路84号\"},{\"words\":\"01-006052-10100218/01/1618:0720k\"},{\"words\":\"中\"}],\"words_result_num\":19,\"log_id\":974605945697070937}");
    }

}
