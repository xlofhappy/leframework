package com.qweather.leframework.base.common.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Arrays;

/**
 * Created by xiaole on 16/4/1.
 * 图片加水印，设置透明度
 * http://blog.csdn.net/hfmbook
 *
 * @author Gary
 * 创建日期：2014年12月16日 22:45:17
 */
public class ImageUtil {

    private static final Logger logger = LoggerFactory.getLogger(ImageUtil.class);

    /**
     * 给图片添加水印
     *
     * @param iconPath   水印图片路径
     * @param srcImgPath 源图片路径
     * @param targetPath 目标图片路径
     */
    public static void markImageByIcon(String iconPath, String srcImgPath, String targetPath) {
        markImageByIcon(iconPath, new File(srcImgPath), targetPath, null);
    }

    /**
     * 给图片添加水印、可设置水印图片旋转角度
     *
     * @param iconPath   水印图片路径
     * @param src        源图片
     * @param targetPath 目标图片路径
     * @param degree     水印图片旋转角度
     */
    public static void markImageByIcon(String iconPath, File src, String targetPath, Integer degree) {
        OutputStream os = null;
        try {
            Image         srcImg  = ImageIO.read(src);
            BufferedImage buffImg = new BufferedImage(srcImg.getWidth(null), srcImg.getHeight(null), BufferedImage.TYPE_INT_RGB);
            // 得到画笔对象
            Graphics2D g = buffImg.createGraphics();

            // 设置对线段的锯齿状边缘处理
            g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);

            g.drawImage(srcImg.getScaledInstance(srcImg.getWidth(null), srcImg.getHeight(null), Image.SCALE_SMOOTH), 0, 0, null);

            if ( null != degree ) {
                // 设置水印旋转
                g.rotate(Math.toRadians(degree), (double) buffImg.getWidth() / 2, (double) buffImg.getHeight() / 2);
            }
            // 水印图象的路径 水印一般为gif或者png的，这样可设置透明度
            ImageIcon imgIcon = new ImageIcon(iconPath);
            // 得到Image对象。
            Image img = imgIcon.getImage();
            // 透明度
            float alpha = 0.2f;
            g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, alpha));
            // 表示水印图片的位置
            g.drawImage(img, 150, 300, null);
            g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER));
            g.dispose();
            os = new FileOutputStream(targetPath);
            // 生成图片
            ImageIO.write(buffImg, "jpg", os);
        } catch ( Exception e ) {
            e.printStackTrace();
        } finally {
            try {
                if ( null != os ) {
                    os.close();
                }
            } catch ( Exception e ) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Title: thumbnailImage
     * <Description: 根据图片路径生成缩略图 </p>
     *
     * @param imgFile    原图片路径
     * @param w          缩略图宽
     * @param h          缩略图高
     * @param targetFile 生成缩略图的前缀
     * @param force      是否强制按照宽高生成缩略图(如果为false，则生成最佳比例缩略图)
     */
    public static void thumbnailImage(File imgFile, int w, int h, File targetFile, boolean force) {
        if ( imgFile.exists() ) {
            try {
                // ImageIO 支持的图片类型 : [BMP, bmp, jpg, JPG, wbmp, jpeg, png, PNG, JPEG, WBMP, GIF, gif]
                String types  = Arrays.toString(ImageIO.getReaderFormatNames());
                String suffix = null;
                // 获取图片后缀
                String dot = ".";
                if ( imgFile.getName().contains(dot) ) {
                    suffix = imgFile.getName().substring(imgFile.getName().lastIndexOf(".") + 1);
                }// 类型和图片后缀全部小写，然后判断后缀是否合法
                if ( suffix == null || !types.toLowerCase().contains(suffix.toLowerCase()) ) {
                    logger.error("Sorry, the image suffix is illegal. the standard image suffix is {}." + types);
                    return;
                }
                logger.debug("target image's size, width:{}, height:{}.", w, h);
                Image img = ImageIO.read(imgFile);
                if ( !force ) {
                    // 根据原图与要求的缩略图比例，找到最合适的缩略图比例
                    int width  = img.getWidth(null);
                    int height = img.getHeight(null);
                    if ( (width * 1.0) / w < (height * 1.0) / h ) {
                        if ( width > w ) {
                            h = Integer.parseInt(new java.text.DecimalFormat("0").format(height * w / (width * 1.0)));
                            logger.debug("change image's height, width:{}, height:{}.", w, h);
                        }
                    } else {
                        if ( height > h ) {
                            w = Integer.parseInt(new java.text.DecimalFormat("0").format(width * h / (height * 1.0)));
                            logger.debug("change image's width, width:{}, height:{}.", w, h);
                        }
                    }
                }
                BufferedImage bi = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
                Graphics      g  = bi.getGraphics();
                g.drawImage(img, 0, 0, w, h, Color.LIGHT_GRAY, null);
                g.dispose();
                // 将图片保存在原目录并加上前缀
                ImageIO.write(bi, suffix, targetFile);
            } catch ( IOException e ) {
                logger.error("generate thumbnail image failed.", e);
            }
        } else {
            logger.warn("the image is not exist.");
        }
    }
}
