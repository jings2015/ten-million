package top.godtm.starter.controller;

import net.coobird.thumbnailator.Thumbnails;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import top.godtm.match.MatchResult;
import top.godtm.match.MatcherHelper;
import top.godtm.ocr.OcrHelper;
import top.godtm.ocr.Record;
import top.godtm.ocr.Ticket;
import top.godtm.search.SearchHelper;

import javax.servlet.ServletRequest;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Random;

/**
 * TODO
 * Created by jingangsheng on 28/01/2018.
 */
@Controller
public class TicketController {
    public static final int MAX_PIC_SIZE = 1024 * 1024;

    @RequestMapping("/upload")
    public String upload(@RequestParam MultipartFile multipartFile, ModelMap modelMap, ServletRequest request) {

        OcrHelper ocrHelper = OcrHelper.getInstance();
        SearchHelper searchHelper = SearchHelper.getInstance();
        MatcherHelper matcherHelper = MatcherHelper.getInstance();
        byte[] fileBytes = null;

        ByteArrayOutputStream baos = null;
        try {
            int originPicSize = multipartFile.getBytes().length;
            if (originPicSize > MAX_PIC_SIZE) {
                // 压缩图片
                baos = new ByteArrayOutputStream();
                Thumbnails.of(multipartFile.getInputStream()).scale(1f).outputQuality(MAX_PIC_SIZE / originPicSize).toOutputStream(baos);
                fileBytes = baos.toByteArray();
            } else {
                fileBytes = multipartFile.getBytes();
            }
        } catch (IOException e) {
            try {
                baos.close();
            } catch (IOException e1) {

            }
        }

        Ticket ticket = ocrHelper.recognitionPic(fileBytes);
        Record awardRecord = searchHelper.getAwardNumber(ticket.getNo());
        MatchResult matchResult = matcherHelper.match(awardRecord, ticket);

        modelMap.addAttribute("result", matchResult.toString().split("\n"));

        return "index";
    }

    @RequestMapping("/")
    public String index() {

        return "index";
    }
}
