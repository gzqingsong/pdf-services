package com.whj;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.*;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author
 * @date 2022/1/17 16:06
 * @Description:
 */
public class QR {

    // 利用模板生成pdf
    public static void pdfout(Map<String, Object> map) {

        // 模板路径

        String templatePath = "F:\\GZOU\\code\\pdf-main\\src\\main\\resources\\QR\\QR.pdf";

        // 生成的新文件路径

        String newPDFPath = "F:\\GZOU\\code\\pdf-main\\src\\main\\resources\\QR\\testout1.pdf";

        PdfReader reader;

        FileOutputStream out;

        ByteArrayOutputStream bos;

        PdfStamper stamper;

        try {

            //给表单添加中文字体 这里采用系统字体。不设置的话，中文可能无法显示

            BaseFont bf = BaseFont.createFont("F:\\GZOU\\code\\pdf-main\\src\\main\\resources\\Font\\SIMYOU.TTF", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);

            Font FontChinese = new Font(bf, 5, Font.NORMAL);

            // 输出流

            out = new FileOutputStream(newPDFPath);

            // 读取pdf模板

            reader = new PdfReader(templatePath);

            bos = new ByteArrayOutputStream();

            stamper = new PdfStamper(reader, bos);

            AcroFields form = stamper.getAcroFields();


            Map<String, Object> qrcodeFields = (Map<String, Object>) map.get("qrcodeFields");

            //遍历二维码字段

            for (Map.Entry<String, Object> entry : qrcodeFields.entrySet()) {

                String key = entry.getKey();

                Object value = entry.getValue();

                // 获取属性的类型

                if (value != null && form.getField(key) != null) {

                    //获取位置(左上右下)

                    AcroFields.FieldPosition fieldPosition = form.getFieldPositions(key).get(0);

                    //绘制二维码

                    float width = fieldPosition.position.getRight() - fieldPosition.position.getLeft();

                    BarcodeQRCode pdf417 = new BarcodeQRCode(value.toString(), (int) width, (int) width, null);

                    //生成二维码图像

                    Image image128 = pdf417.getImage();

                    //绘制在第一页

                    PdfContentByte cb = stamper.getOverContent(1);

                    //左边距(居中处理)

                    float marginLeft = (fieldPosition.position.getRight() - fieldPosition.position.getLeft() - image128.getWidth()) / 2;

                    //条码位置

                    image128.setAbsolutePosition(fieldPosition.position.getLeft() + marginLeft, fieldPosition.position.getBottom());

                    //加入条码

                    cb.addImage(image128);

                }

            }


            // 如果为false，生成的PDF文件可以编辑，如果为true，生成的PDF文件不可以编辑

            stamper.setFormFlattening(true);

            stamper.close();

            Document doc = new Document();

            Font font = new Font(bf, 20);

            PdfCopy copy = new PdfCopy(doc, out);

            doc.open();
            PdfImportedPage importPage = copy.getImportedPage(new PdfReader(bos.toByteArray()), 1);

            copy.addPage(importPage);

            doc.close();

        } catch (IOException e) {

            e.printStackTrace();

        } catch (DocumentException e) {

            e.printStackTrace();

        }

    }


    public static void main(String[] args) {

        //文本内容map

        Map<String, Object> map = new HashMap<String, Object>();

        //二维码map

        Map<String, Object> qrcodeFields = new HashMap<String, Object>();

        qrcodeFields.put("img", "https://www.baidu.com");

        //组装map传过去

        Map<String, Object> o = new HashMap<String, Object>();

        o.put("qrcodeFields", qrcodeFields);
        //执行

        pdfout(o);

    }

}
