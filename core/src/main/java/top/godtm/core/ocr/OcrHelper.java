package top.godtm.core.ocr;

/**
 * ocr辅助类
 * Created by jingangsheng on 28/01/2018.
 */
public class OcrHelper {

    private static IOcr ocr;

    private OcrHelper(){
        ocr = new BaiduOcr();
    }

    public static OcrHelper getInstance() {
        return OcrHelperHolder.INSTANCE;
    }


    private static class OcrHelperHolder{
        private static final OcrHelper INSTANCE = new OcrHelper();
    }


    public Ticket recognitionPic(String path) {
        return ocr.recognitionPic(path);
    }

    public Ticket recognitionPic(byte[] bytes) {
        return ocr.recognitionPic(bytes);
    }


}
