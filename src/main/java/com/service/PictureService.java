package com.service;

import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * 图片叠加处理
 * @author 长不大的韭菜
 * @date 2020/5/30 10:40 下午
 */
@Service
public class PictureService {

    public static void main(String[] args) {
        PictureService pictureService = new PictureService();
        try {
            pictureService.generate();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 合成图片
     */
    public File generate() throws IOException{
        // 底图
        File sourceFile = new File(this.getClass().getResource("/background.png").getFile());
        // 水印图片
        File waterFile = new File(this.getClass().getResource("/studentHead.png").getFile());
        // 最终合成
        String finalFilePath = waterFile.getParentFile().getAbsolutePath() + File.separator + "idPhoto.png";

        // 本次合成之前，删除上一次请求合成后留下的图片
        deleteFile(new File(finalFilePath));

        BufferedImage buffImg = watermark(sourceFile, waterFile, 1.0f);
        generateWaterFile(buffImg, finalFilePath);

        return new File(finalFilePath);
    }

    /**
     * 输出水印图片
     *
     * @param buffImg
     *            图像加水印之后的BufferedImage对象
     * @param savePath
     *            图像加水印之后的保存路径
     */
    private void generateWaterFile(BufferedImage buffImg, String savePath) {
        int temp = savePath.lastIndexOf(".") + 1;
        try {
            ImageIO.write(buffImg, savePath.substring(temp), new File(savePath));
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }

    /**
     *
     * @Title: 构造图片
     * @Description: 生成水印并返回java.awt.image.BufferedImage
     * @param file
     *            源文件(图片)
     * @param waterFile
     *            水印文件(图片)
     * @param alpha
     *            透明度, 选择值从0.0~1.0: 完全透明~完全不透明
     * @return BufferedImage
     * @throws IOException
     */
    private BufferedImage watermark(File file, File waterFile, float alpha) throws IOException {
        // 获取底图
        BufferedImage buffImg = ImageIO.read(file);
        int buffImgWidth = buffImg.getWidth(); //获取底图的宽度
        int buffImgHight = buffImg.getHeight();//获取底图的高度

        // 获取层图1
        BufferedImage waterImg1 = ImageIO.read(waterFile);
        // 创建Graphics2D对象，用在底图对象上绘图
        Graphics2D g2d = buffImg.createGraphics();
        int waterImgWidth1 = waterImg1.getWidth();// 获取层图的宽度
        int waterImgHeight1 = waterImg1.getHeight();// 获取层图的高度
        // 在图形和图像中实现混合和透明效果
        //g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, alpha));
        // 绘制
        //g2d.drawImage(waterImg1, 775,  450, waterImgWidth1, waterImgHeight1, null);
        g2d.drawImage(waterImg1, 755,  340, 350, 430, null);


        g2d.dispose();// 释放图形上下文使用的系统资源
        return buffImg;
    }

    private void deleteFile(File file) {
        if(!file.exists()) {
            return;
        }

        if(file.isFile() || file.list()==null) {
            file.delete();
        }
    }
}
