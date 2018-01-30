package top.godtm.starter.controller;

import net.coobird.thumbnailator.Thumbnails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import top.godtm.core.match.IMatcher;
import top.godtm.core.match.MatchResult;
import top.godtm.core.match.Matcher;
import top.godtm.core.ocr.BaiduOcr;
import top.godtm.core.ocr.IOcr;
import top.godtm.core.ocr.Record;
import top.godtm.core.ocr.Ticket;
import top.godtm.core.search.ISearch;
import top.godtm.core.search.SearchDLT;
import top.godtm.core.search.SearchKaiJiang500;

import javax.servlet.ServletRequest;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * TODO
 * Created by jingangsheng on 28/01/2018.
 */
@Controller
public class TicketController {

    private static final int MAX_PIC_SIZE = 1024 * 1024;

    @Autowired
    private BaiduOcr baiduOcr;
    @Autowired
    private Matcher matcher;
    @Autowired
    private SearchKaiJiang500 searchKaiJiang500;
    @Autowired
    private SearchDLT searchDLT;

    @RequestMapping("/upload")
    public String upload(@RequestParam MultipartFile multipartFile, ModelMap modelMap, ServletRequest request) throws Exception {

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
            } catch (IOException ignored) {

            }
        }

        Ticket ticket = baiduOcr.recognitionPic(fileBytes);
        Record awardRecord = searchKaiJiang500.getAwardNumber(ticket.getNo());
        MatchResult matchResult = matcher.match(awardRecord, ticket);

        modelMap.addAttribute("result", matchResult.toString().split("\n"));

        return "index";
    }

    @RequestMapping("/getAwardRecord")
    @ResponseBody
    public Object getAwardRecord(String no) throws Exception {
        return searchDLT.getAwardNumber(no);
    }

    @RequestMapping("/getMatchResult")
    @ResponseBody
    public Object getMatchResult() {
        MatchResult matchResult = new MatchResult();
        return matchResult;
    }

    @RequestMapping("/")
    public String index() {

        return "index";
    }
}
