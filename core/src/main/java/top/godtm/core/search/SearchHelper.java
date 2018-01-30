package top.godtm.core.search;

import top.godtm.core.ocr.Record;

/**
 * 搜索开奖号码辅助类
 * Created by jingangsheng on 28/01/2018.
 */
public class SearchHelper {

    private static ISearch search;
    private SearchHelper() {
        search = new SearchKaiJiang500();
    }

    public static SearchHelper getInstance(){
        return SearchHelperHolder.INSTANCE;
    }

    private static class SearchHelperHolder{
        private static SearchHelper INSTANCE = new SearchHelper();
    }

    public Record getAwardNumber(String no) {
        Record record = null;

        try {
            record = search.getAwardNumber(no);
        } catch (Exception e) {
            // TODO: 28/01/2018 logger
            e.printStackTrace();
        }
        return record;
    }

}
