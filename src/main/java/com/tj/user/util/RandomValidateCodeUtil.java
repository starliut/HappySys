package com.tj.user.util;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.Random;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
public class RandomValidateCodeUtil {
    public static final String RANDOMCODEKEY = "RANDOMVALIDATECODEKEY";
    private String randString = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private int width = 95;
    private int height = 25;
    private int lineSize = 40;
    private int stringNum = 4;
    private static final Logger logger = LoggerFactory.getLogger(RandomValidateCodeUtil.class);
    private Random random = new Random();

    public RandomValidateCodeUtil() {
    }

    private Font getFont() {
        return new Font("Fixedsys", 1, 18);
    }

    private Color getRandColor(int fc, int bc) {
        if (fc > 255) {
            fc = 255;
        }

        if (bc > 255) {
            bc = 255;
        }

        int r = fc + this.random.nextInt(bc - fc - 16);
        int g = fc + this.random.nextInt(bc - fc - 14);
        int b = fc + this.random.nextInt(bc - fc - 18);
        return new Color(r, g, b);
    }

    public void getRandcode(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        BufferedImage image = new BufferedImage(this.width, this.height, 4);
        Graphics g = image.getGraphics();
        g.fillRect(0, 0, this.width, this.height);
        g.setFont(new Font("Times New Roman", 0, 18));
        g.setColor(this.getRandColor(110, 133));

        for(int i = 0; i <= this.lineSize; ++i) {
            this.drowLine(g);
        }

        String randomString = "";

        for(int i = 1; i <= this.stringNum; ++i) {
            randomString = this.drowString(g, randomString, i);
        }

        logger.info(randomString);
        session.removeAttribute("RANDOMVALIDATECODEKEY");
        session.setAttribute("RANDOMVALIDATECODEKEY", randomString);
        g.dispose();

        try {
            ImageIO.write(image, "JPEG", response.getOutputStream());
        } catch (Exception var8) {
            logger.error("将内存中的图片通过流动形式输出到客户端失败>>>>   ", var8);
        }

    }

    private String drowString(Graphics g, String randomString, int i) {
        g.setFont(this.getFont());
        g.setColor(new Color(this.random.nextInt(101), this.random.nextInt(111), this.random.nextInt(121)));
        String rand = String.valueOf(this.getRandomString(this.random.nextInt(this.randString.length())));
        randomString = randomString + rand;
        g.translate(this.random.nextInt(3), this.random.nextInt(3));
        g.drawString(rand, 13 * i, 16);
        return randomString;
    }

    private void drowLine(Graphics g) {
        int x = this.random.nextInt(this.width);
        int y = this.random.nextInt(this.height);
        int xl = this.random.nextInt(13);
        int yl = this.random.nextInt(15);
        g.drawLine(x, y, x + xl, y + yl);
    }

    public String getRandomString(int num) {
        return String.valueOf(this.randString.charAt(num));
    }
}
