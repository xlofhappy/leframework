package org.le.cms.common.interfaces.defaults;

import org.le.cms.common.interfaces.VerifyCodeGenerator;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Random;

/**
 * Cms defaults verify code generator
 *
 * @author xiaole
 * @date 2018-10-30 16:51:15
 */
public class CmsVerifyCodeGenerator implements VerifyCodeGenerator {

    @Override
    public Code generate(int w, int h, int l) throws IOException {
        Code   code = new Code();
        String s    = generateVerifyCode(l);
        code.setCode(s);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        outputImage(w, h, baos, s);
        code.setOut(baos);
        code.setType(type);
        return code;
    }

    /**
     * 使用到Algerian字体，系统里没有的话需要安装字体，字体只显示大写，去掉了1,0,i,o几个容易混淆的字符
     */
    public final String VERIFY_CODES = "23456789ABCDEFGHJKLMNPQRSTUVWXYZ";
    public final String type         = "jpg";

    private Random random = new Random();


    /**
     * 使用指定源生成验证码
     *
     * @param verifySize 验证码长度
     *
     * @return 字符串
     */
    private String generateVerifyCode(int verifySize) {
        int           codesLen   = VERIFY_CODES.length();
        Random        rand       = new Random(System.currentTimeMillis());
        StringBuilder verifyCode = new StringBuilder(verifySize);
        for ( int i = 0; i < verifySize; i++ ) {
            verifyCode.append(VERIFY_CODES.charAt(rand.nextInt(codesLen - 1)));
        }
        return verifyCode.toString();
    }

    /**
     * 生成随机验证码文件,并返回验证码值
     *
     * @param w          高度
     * @param h          宽度
     * @param outputFile 输出文件
     * @param verifySize 验证码长度
     *
     * @return 字符串
     *
     * @throws IOException 输出流异常
     */
    public String outputVerifyImage(int w, int h, File outputFile, int verifySize) throws IOException {
        String verifyCode = generateVerifyCode(verifySize);
        outputImage(w, h, outputFile, verifyCode);
        return verifyCode;
    }

    /**
     * 输出随机验证码图片流,并返回验证码值
     *
     * @param w          高度
     * @param h          宽度
     * @param os         输出流
     * @param verifySize 验证码长度
     *
     * @return 字符串
     *
     * @throws IOException 输出流异常
     */
    public String outputVerifyImage(int w, int h, OutputStream os, int verifySize) throws IOException {
        String verifyCode = generateVerifyCode(verifySize);
        outputImage(w, h, os, verifyCode);
        return verifyCode;
    }

    /**
     * 生成指定验证码图像文件
     *
     * @param w          高度
     * @param h          宽度
     * @param outputFile 输出文件
     * @param code       验证码
     *
     * @throws IOException 输出流异常
     */
    public void outputImage(int w, int h, File outputFile, String code) throws IOException {
        if ( outputFile == null ) {
            return;
        }
        File dir = outputFile.getParentFile();
        if ( !dir.exists() ) {
            boolean a = dir.mkdirs();
        }
        boolean          a   = outputFile.createNewFile();
        FileOutputStream fos = new FileOutputStream(outputFile);
        outputImage(w, h, fos, code);
        fos.close();
    }

    /**
     * 生成指定验证码到 response
     *
     * @param w        高度
     * @param h        宽度
     * @param response 响应
     * @param code     验证码
     *
     * @throws IOException 输出流异常
     */
    public void outputImage(int w, int h, HttpServletResponse response, String code) throws IOException {
        if ( response == null ) {
            return;
        }
        outputImage(w, h, response.getOutputStream(), code);
    }

    /**
     * 输出指定验证码图片流
     *
     * @param w    高度
     * @param h    宽度
     * @param os   输出流
     * @param code 验证码
     *
     * @throws IOException 输出流异常
     */
    public void outputImage(int w, int h, OutputStream os, String code) throws IOException {
        int           verifySize = code.length();
        BufferedImage image      = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
        Random        rand       = new Random();
        Graphics2D    g2         = image.createGraphics();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        Color[] colors      = new Color[5];
        Color[] colorSpaces = new Color[]{ Color.WHITE, Color.CYAN, Color.GRAY, Color.LIGHT_GRAY, Color.MAGENTA, Color.ORANGE, Color.PINK, Color.YELLOW };
        for ( int i = 0; i < colors.length; i++ ) {
            colors[i] = colorSpaces[rand.nextInt(colorSpaces.length)];
        }
        // 设置边框色
        g2.setColor(Color.GRAY);
        g2.fillRect(0, 0, w, h);

        Color c = getRandColor(200, 250);
        // 设置背景色
        g2.setColor(c);
        g2.fillRect(0, 2, w, h - 4);

        //绘制干扰线
        Random random = new Random();
        // 设置线条的颜色
        g2.setColor(getRandColor(160, 200));
        for ( int i = 0; i < 20; i++ ) {
            int x  = random.nextInt(w - 1);
            int y  = random.nextInt(h - 1);
            int xl = random.nextInt(6) + 1;
            int yl = random.nextInt(12) + 1;
            g2.drawLine(x, y, x + xl + 40, y + yl + 20);
        }

        // 添加噪点 // 噪声率
        float yawpRate = 0.05f;
        int   area     = (int) (yawpRate * w * h);
        for ( int i = 0; i < area; i++ ) {
            int x   = random.nextInt(w);
            int y   = random.nextInt(h);
            int rgb = getRandomIntColor();
            image.setRGB(x, y, rgb);
        }

        //       使图片扭曲
        shear(g2, w, h, c);

        g2.setColor(getRandColor(100, 160));
        int  fontSize = h - 4;
        Font font     = new Font("Algerian", Font.ITALIC, fontSize);
        g2.setFont(font);
        char[] chars = code.toCharArray();
        for ( int i = 0; i < verifySize; i++ ) {
            AffineTransform affine = new AffineTransform();
            affine.setToRotation(Math.PI / 4 * rand.nextDouble() * (rand.nextBoolean() ? 1 : -1), (w * 1F / verifySize) * i + fontSize / 2.0, h / 2.0);
            g2.setTransform(affine);
            g2.drawChars(chars, i, 1, ((w - 10) / verifySize) * i + 5, h * 9 / 11);
        }

        g2.dispose();
        ImageIO.write(image, type, os);
    }

    private Color getRandColor(int fc, int bc) {
        if ( fc > 255 ) { fc = 255; }
        if ( bc > 255 ) { bc = 255; }
        int r = fc + random.nextInt(bc - fc);
        int g = fc + random.nextInt(bc - fc);
        int b = fc + random.nextInt(bc - fc);
        return new Color(r, g, b);
    }

    private int getRandomIntColor() {
        int[] rgb   = getRandomRgb();
        int   color = 0;
        for ( int c : rgb ) {
            color = color << 8;
            color = color | c;
        }
        return color;
    }

    private int[] getRandomRgb() {
        int[] rgb = new int[3];
        for ( int i = 0; i < 3; i++ ) {
            rgb[i] = random.nextInt(255);
        }
        return rgb;
    }

    private void shear(Graphics g, int w1, int h1, Color color) {
        shearX(g, w1, h1, color);
        shearY(g, w1, h1, color);
    }

    private void shearX(Graphics g, int w1, int h1, Color color) {

        int period = random.nextInt(2);

        boolean borderGap = true;
        int     frames    = 1;
        int     phase     = random.nextInt(2);

        for ( int i = 0; i < h1; i++ ) {
            double d = (double) (period >> 1) * Math.sin((double) i / (double) period + (6.2831853071795862D * (double) phase) / (double) frames);
            g.copyArea(0, i, w1, 1, (int) d, 0);
            g.setColor(color);
            g.drawLine((int) d, i, 0, i);
            g.drawLine((int) d + w1, i, w1, i);
        }

    }

    private void shearY(Graphics g, int w1, int h1, Color color) {

        int period = random.nextInt(40) + 10;

        boolean borderGap = true;
        int     frames    = 20;
        int     phase     = 7;
        for ( int i = 0; i < w1; i++ ) {
            double d = (double) (period >> 1) * Math.sin((double) i / (double) period + (6.2831853071795862D * (double) phase) / (double) frames);
            g.copyArea(i, 0, 1, h1, 0, (int) d);
            g.setColor(color);
            g.drawLine(i, (int) d, i, 0);
            g.drawLine(i, (int) d + h1, i, h1);

        }

    }
}
