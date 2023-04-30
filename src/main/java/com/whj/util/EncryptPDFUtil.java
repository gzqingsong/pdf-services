package com.whj.util;



import com.spire.pdf.PdfDocument;
import com.spire.pdf.security.PdfEncryptionKeySize;
import com.spire.pdf.security.PdfPermissionsFlags;

import java.util.EnumSet;

/**
 * @author
 * @date 2022/1/18 9:26
 * @Description:   将PDF加密的工具类
 */
public  class EncryptPDFUtil {
    public static void encryptPDF(String startFileName,String EncryptPDF) {
        //创建PdfDocument实例
        PdfDocument doc = new PdfDocument();
        //加载PDF文件
        doc.loadFromFile(startFileName);
        //添加一个空白页，目的为了删除jar包添加的水印，后面再移除这一页
        doc.getPages().add();
        //加密PDF文件
        PdfEncryptionKeySize keySize = PdfEncryptionKeySize.Key_128_Bit;
        String openPassword = "123456";//打开文档时，仅用于查看文档
        String permissionPassword = "test";//打开文档时，可编辑文档
        EnumSet flags = EnumSet.of(PdfPermissionsFlags.Print, PdfPermissionsFlags.Fill_Fields);
        doc.getSecurity().encrypt(openPassword, permissionPassword, flags, keySize);
        //移除第一个页
        doc.getPages().remove(doc.getPages().get(doc.getPages().getCount()-1));
        //保存文件
        doc.saveToFile("F:\\GZOU\\code\\pdf-main\\src\\main\\resources\\pdf\\"+EncryptPDF+".pdf");

        doc.close();
    }
}
