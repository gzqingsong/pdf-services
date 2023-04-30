package com.whj.pdf;

import com.itextpdf.text.pdf.PdfSignatureAppearance;
import com.itextpdf.text.pdf.security.DigestAlgorithms;
import com.whj.entity.SignatureInfo;

import java.io.FileInputStream;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.cert.Certificate;

import static com.whj.pdf.ItextUtil.PASSWORD;

/**
 * @author
 * @date 2022/1/11 10:42
 * @Description:  盖章功能实现
 */
public class PdfStamp {
    public static void main(String[] args) {
        try {
            ItextUtil app = new ItextUtil();
            // 将证书文件放入指定路径，并读取keystore ，获得私钥和证书链
            String pkPath = "F:\\GZOU\\code\\pdf-main\\src/main/resources/bhl.p12";
            KeyStore ks = KeyStore.getInstance("PKCS12");
            ks.load(new FileInputStream(pkPath), PASSWORD);
            String alias = ks.aliases().nextElement();
            PrivateKey pk = (PrivateKey) ks.getKey(alias, PASSWORD);
            // 得到证书链
            Certificate[] chain = ks.getCertificateChain(alias);
            //需要进行签章的pdf
            String path = "F:\\HSBC\\boc\\ImageHandle-2022\\target\\classes\\temp\\PDFtest.pdf";
            // 封装签章信息
            SignatureInfo signInfo = new SignatureInfo();
            signInfo.setReason("理由");
            signInfo.setLocation("位置");
            signInfo.setPk(pk);
            signInfo.setChain(chain);
            signInfo.setCertificationLevel(PdfSignatureAppearance.NOT_CERTIFIED);
            signInfo.setDigestAlgorithm(DigestAlgorithms.SHA1);
            signInfo.setFieldName("demo");

            // 签章图片
            signInfo.setImagePath("F:\\GZOU\\code\\pdf-main\\src\\main\\resources\\pdf\\chapter.png");
            signInfo.setRenderingMode(PdfSignatureAppearance.RenderingMode.GRAPHIC);
            // 值越大，代表向x轴坐标平移 缩小 （反之，值越小，印章会放大）
            signInfo.setRectllx(100);
            //// 值越大，代表向y轴坐标向上平移（大小不变）
            signInfo.setRectlly(200);
            // 值越大   代表向x轴坐标向右平移  （大小不变）
            signInfo.setRecturx(150);
            // 值越大，代表向y轴坐标向上平移（大小不变）
            signInfo.setRectury(150);

            //签章后的pdf路径
            app.sign(path, "F:\\HSBC\\boc\\pdf-services\\target\\classes\\pdf\\ooo.pdf", signInfo);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

