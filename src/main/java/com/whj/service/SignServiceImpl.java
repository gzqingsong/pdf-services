package com.whj.service;

import com.whj.entity.sign.KeyWordBean;
import com.whj.entity.sign.SignPDFBean;
import com.whj.entity.sign.SignPDFRequestBean;
import com.whj.util.EncryptPDFUtil;
import com.whj.util.KeywordPDFUtils;
import com.whj.util.SignPDFUtils;
import org.springframework.stereotype.Service;
import sun.misc.BASE64Decoder;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * @author
 * @date 2022/1/11 14:00
 * @Description:
 */
@Service
public class SignServiceImpl implements SignService {
    @Override
    public String uploadSign(String img) {

        //String idCard="3000";
        // 生成jpeg图片 idCard.asString()
        String url = "F:\\GZOU\\code\\pdf-main\\src\\main\\resources\\img\\sign.png";
        try {
            if (img == null) // 图像数据为空
            {
                return "no";
            }
            int i = img.indexOf("base64,") + 7;//获取前缀data:image/gif;base64,的坐标
            String newImage = img.substring(i, img.length());//去除前缀
            BASE64Decoder decoder = new BASE64Decoder();
            // Base64解码
            byte[] bytes = decoder.decodeBuffer(newImage);
            for (int j = 0; j < bytes.length; ++j) {
                if (bytes[j] < 0) {// 调整异常数据
                    bytes[j] += 256;
                }
            }
            OutputStream out = new FileOutputStream(url);
            out.write(bytes);
            out.flush();
            out.close();
            return "yes";
        } catch (Exception e) {
            return "no";
        }
    }

    @Override
    public String sign() {
        try {
            //初始化文件
            String srcPath = "F:\\GZOU\\code\\pdf-main\\src\\main\\resources\\pdf\\out.pdf";
            //输出文件
            String outPath = "F:\\GZOU\\code\\pdf-main\\src\\main\\resources\\pdf\\testout.pdf";

            signimg("F:\\GZOU\\code\\pdf-main\\src\\main\\resources\\img\\sign.png", srcPath, "批准人", outPath);
            //加密
            EncryptPDFUtil.encryptPDF(outPath, "EncryptPDF");
            return "yes";
        } catch (Exception e) {
            return "no";
        }
    }

    public static void signimg(String imgurl, String pdfurl, String keywords, String outPDFPath) throws IOException {
        List list = new ArrayList();
        SignPDFBean bean1 = new SignPDFBean();
        bean1.setKeyStorePass("111111");
        bean1.setKeyStorePath("F:\\GZOU\\code\\pdf-main\\src\\main\\resources\\bhl.p12");
        bean1.setKeyWord(keywords);
        bean1.setSealPath(imgurl);
        bean1.setSignLocation(keywords);
        bean1.setSignReason("程序员签字");
        list.add(bean1);

        SignPDFRequestBean requestBean = new SignPDFRequestBean();
        requestBean.setSrcPDFPath(pdfurl);
        requestBean.setOutPDFPath(outPDFPath);
        requestBean.setSignPDFBeans(list);

        long startTime = System.currentTimeMillis();
        // 1.解析pdf文件
        Map<Integer, List<KeyWordBean>> map = KeywordPDFUtils.getPDFText(requestBean.getSrcPDFPath());
        // 2.获取关键字坐标
        List<SignPDFBean> beans = requestBean.getSignPDFBeans();
        byte[] fileData = null;
        InputStream in = null;
        for (int i = 0; i < beans.size(); i++) {
            SignPDFBean pdfBean = beans.get(i);
            KeyWordBean bean = KeywordPDFUtils.getKeyWordXY1(map, pdfBean.getKeyWord());
            if (null == bean) {
                System.out.println("未查询到关键字。。。");
            }
            System.out.println("111" + bean.toString());
            long keyTime = System.currentTimeMillis();
            if (i == 0) {
                in = new FileInputStream(requestBean.getSrcPDFPath());
            } else {
                in = new ByteArrayInputStream(fileData);
            }
            // 3.进行盖章
            fileData = SignPDFUtils.sign(pdfBean.getKeyStorePass(), pdfBean.getKeyStorePath(), in, pdfBean.getSealPath(), bean.getX(), bean.getY(), bean.getPage(), pdfBean.getSignReason(), pdfBean.getSignLocation());
            long signTime = System.currentTimeMillis();
        }
        // 4.输出盖章后pdf文件
        FileOutputStream f = new FileOutputStream(new File(requestBean.getOutPDFPath()));
        f.write(fileData);
        f.close();
        in.close();
        long endTime = System.currentTimeMillis();
        System.out.println("总时间：" + (endTime - startTime));
    }
}
