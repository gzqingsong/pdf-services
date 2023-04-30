package com.whj.util.QR;


import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.Hashtable;

/**
 * @author
 * @date 2022/1/13 16:25
 * 二维码生成工具类
 *
 * 通过Google开源的zxing库来事项生成二维码图片
 */
public class QrCodeUtils {

    public static final String QR_CODE_IMAGE_PATH = "F:\\GZOU\\code\\pdf-main\\src\\main\\resources\\QR\\MyQRCode.png";

    public static void generateQRCodeImage(String text, int width, int height, String filePath) throws WriterException, IOException {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();

        BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, width, height);

        Path path = FileSystems.getDefault().getPath(filePath);

        MatrixToImageWriter.writeToPath(bitMatrix, "PNG", path);

    }

    /**
     * 生成二维码
     * @param contents 二维码的内容
     * @param width 二维码图片宽度
     * @param height 二维码图片高度
     */
    public static BufferedImage createQrCodeBufferdImage(String contents, int width, int height){
        Hashtable hints= new Hashtable();
        hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
        BufferedImage image = null;
        try {
            BitMatrix bitMatrix = new MultiFormatWriter().encode(
                    contents,BarcodeFormat.QR_CODE, width, height, hints);
            image = toBufferedImage(bitMatrix);
        } catch (WriterException e) {
            e.printStackTrace();
        }
        return image;
    }

    public static BufferedImage toBufferedImage(BitMatrix matrix) {
        int width = matrix.getWidth();
        int height = matrix.getHeight();
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                image.setRGB(x, y, matrix.get(x, y) ? 0xFF000000 : 0xFFFFFFFF);
            }
        }
        return image;
    }

    public static void main(String[] args) {
        try {
            generateQRCodeImage ("https://www.baidu.com/", 350, 350,QR_CODE_IMAGE_PATH);
        } catch (WriterException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }


    }




}
