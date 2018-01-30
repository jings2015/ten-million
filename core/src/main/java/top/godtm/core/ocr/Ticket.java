package top.godtm.core.ocr;

import java.util.List;

/**
 * 彩票
 * Created by jingangsheng on 21/01/2018.
 */
public class Ticket {
    // 期数
    private String no;

    // 倍数
    private Integer rate = 1;

    private List<Record> recordList ;

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public Integer getRate() {
        return rate;
    }

    public void setRate(Integer rate) {
        this.rate = rate;
    }

    public List<Record> getRecordList() {
        return recordList;
    }

    public void setRecordList(List<Record> recordList) {
        this.recordList = recordList;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("第").append(no).append("期\n");
        for (Record record : recordList) {
            sb.append(record.toString()).append("\n");
        }
        return sb.toString();
    }
}
