package top.godtm.core.ocr;

/**
 * TODO
 * Created by jingangsheng on 28/01/2018.
 */
public interface IOcr {

    Ticket recognitionPic(String path);

    Ticket recognitionPic(byte[] bytes);
}
