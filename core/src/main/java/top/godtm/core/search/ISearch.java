package top.godtm.core.search;

import top.godtm.core.ocr.Record;

/**
 * TODO
 * Created by jingangsheng on 28/01/2018.
 */
public interface ISearch {
    /**
     * @param no 彩票期数
     * @return
     */
    Record getAwardNumber(String no) throws Exception;
}
