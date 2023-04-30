package com.whj.util.QR;

import com.google.zxing.WriterException;

import java.io.IOException;

import static com.whj.util.QR.QrCodeUtils.QR_CODE_IMAGE_PATH;

/**
 * @author
 * @date 2022/1/14 15:42
 * @Description:
 */
public class TestQR {
    public static void main(String[] args) {
        try {
            /**
             * 第一个参数：内容可以是二维码 也可以是内容
             * 第二第三参数：二维码的宽高
             * 第四个参数：二维码生成后的地址
             */
            QrCodeUtils.generateQRCodeImage ("https://www.wolai.com/6MjBCdAq3mmGXBcb62V4DE", 350, 350,QR_CODE_IMAGE_PATH);
        } catch (WriterException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
