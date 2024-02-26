package Main;

import com.sun.image.codec.jpeg.JPEGCodec;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class Main {
    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            generateAndSaveCaptcha();
        }
    }

    private static void generateAndSaveCaptcha() {
        String readLine;
        try {
            int validatetype = 0; // 验证码类型
            int validatenum = 4; // 验证码位数
            Random random = new Random();
            int i = validatenum * 28;
            StringBuilder str = new StringBuilder();
            String str2 = "";
            if (validatetype == 0) {
                str2 = "ValidateNum.key"; // 纯数字
            } else if (validatetype == 1) {
                str2 = "ValidateENChar.key";
            } else if (validatetype == 2) {
                str2 = "ValidateChinese.key";
            }
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(new File(str2))));
            while (true) {
                readLine = bufferedReader.readLine();
                if (readLine == null) {
                    break;
                }
                str = new StringBuilder(str + readLine.trim());
            }
            bufferedReader.close();
            int length = str.length();
            BufferedImage bufferedImage = new BufferedImage(i, 38, 1);
            Graphics graphics = bufferedImage.getGraphics();
            graphics.setColor(Color.white);
            graphics.fillRect(0, 0, i, 38);
            graphics.drawRect(0, 0, i - 1, 38 - 1);
            StringBuilder str3 = new StringBuilder(); // 验证码内容
            for (int i2 = 0; i2 < validatenum; i2++) {
                int nextInt = random.nextInt(length);
                String substring = str.substring(nextInt, nextInt + 1);
                str3.append(substring);
                graphics.setColor(new Color(random.nextInt(255), random.nextInt(255), random.nextInt(255)));
                graphics.setFont(new Font("simsun", 1, 18 + random.nextInt(validatenum)));
                graphics.drawString(substring, (28 * i2) + 2, 25);
            }
            for (int i3 = 0; i3 < 80; i3++) {
                int nextInt2 = random.nextInt(i);
                int nextInt3 = random.nextInt(38);
                graphics.drawLine(nextInt2, nextInt3, nextInt2, nextInt3);
            }
            graphics.dispose();

            // 获取当前时间戳
            long timestamp = System.currentTimeMillis();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
            String formattedTimestamp = sdf.format(new Date(timestamp));

            // 将验证码内容与时间戳组合成图片名称
            String imageName = str3 + "_" + formattedTimestamp + ".jpg";

            // 保存图片到服务器的某个目录下
            File imageFile = new File("output/" + imageName);
            FileOutputStream fileOutputStream = new FileOutputStream(imageFile);
            JPEGCodec.createJPEGEncoder(fileOutputStream).encode(bufferedImage);
            fileOutputStream.close();

        } catch (Exception e) {
            System.out.println("Error:"+ e.getMessage());
            e.printStackTrace();
        }
    }
}