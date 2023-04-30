package com.whj;

import com.whj.util.WaterMark;

/**
 * @author
 * @date 2022/1/24 15:18
 * @Description: 测试pdf文件加水印
 */
public class TestWaterMark {
    public static void main(String[] args) {
        /**
         * pdf生成水印
         *
         * @param srcPdfPath       插入前的文件路径
         * @param tarPdfPath       插入后的文件路径
         * @param WaterMarkContent 水印文案
         * @param numberOfPage     每页需要插入的条数
         * @throws Exception
         */
        String srcPdfPath = "F:\\GZOU\\code\\pdf-main\\src\\main\\resources\\pdf\\testout.pdf";
        String tarPdfPath = "F:\\GZOU\\code\\pdf-main\\src\\main\\resources\\pdf\\out.pdf";
        String WaterMarkContent = "测试添加水印";
        Integer numberOfPage = 3;
        try {
            WaterMark.addWaterMark(srcPdfPath, tarPdfPath, WaterMarkContent, numberOfPage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
