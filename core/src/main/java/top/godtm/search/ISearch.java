package top.godtm.search;

import top.godtm.ocr.Record;

import java.io.IOException;

/**
 * TODO
 * Created by jingangsheng on 28/01/2018.
 */
public interface ISearch {
    /**
     *
     * @param no 彩票期数
     * @return
     */
    Record getAwardNumber(String no) throws IOException;
}
