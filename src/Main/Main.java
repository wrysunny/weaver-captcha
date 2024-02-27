package Main;

import com.sun.image.codec.jpeg.JPEGCodec;

import javax.imageio.ImageIO;
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
        for (int i = 0; i < 200000; i++) {
            generateAndSaveCaptchaOld();
            generateAndSaveCaptchaNew();
        }
    }

    private static void generateAndSaveCaptchaOld() {
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

    private static void generateAndSaveCaptchaNew() {
        String readLine;
        try {
            int validatetype = 0;
            int validatenum = 4;
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
            String str3 = "";
            for (int i2 = 0; i2 < validatenum; i2++) {
                int nextInt = random.nextInt(length);
                String substring = str.substring(nextInt, nextInt + 1);
                str3 = str3 + substring;
                graphics.setColor(new Color(random.nextInt(220), random.nextInt(220), random.nextInt(220)));
                graphics.setFont(new Font("simsun", 1, 20 + random.nextInt(validatenum)));
                graphics.drawString(substring, (28 * i2) + 2, 25);
            }
            for (int i3 = 0; i3 < 80; i3++) {
                int nextInt2 = random.nextInt(i);
                int nextInt3 = random.nextInt(38);
                graphics.drawLine(nextInt2, nextInt3, nextInt2, nextInt3);
            }
            CreateRandomLine(i, 38, 8, graphics, 100);
            setSquareBackGround(graphics, i, 38, 3);
            graphics.dispose();
            bufferedImage.flush();
            String str4 = "validateRand";
            String null2String = "";
            if (null2String.length() > 0) {
                str4 = str4 + null2String;
            }
            System.out.println("验证码生成记录:sRand:" + str3 );//+ ":验证码的key是:" + str4);
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            String FORMAT = "jpeg";
            ImageIO.write(bufferedImage, FORMAT, byteArrayOutputStream);


            // 获取当前时间戳
            long timestamp = System.currentTimeMillis();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
            String formattedTimestamp = sdf.format(new Date(timestamp));

            // 将验证码内容与时间戳组合成图片名称
            String imageName = str3 + "_" + formattedTimestamp + ".jpg";

            // 保存图片到服务器的某个目录下
            File imageFile = new File( imageName);
            FileOutputStream fileOutputStream = new FileOutputStream(imageFile);
            JPEGCodec.createJPEGEncoder(fileOutputStream).encode(bufferedImage);
            fileOutputStream.close();




        } catch (Exception e) {
            System.out.println("Error:"+ e.getMessage());
            e.printStackTrace();
        }
    }

    private static void CreateRandomLine(int i, int i2, int i3, Graphics graphics, int i4) {
        for (int i5 = 0; i5 < getIntRandom(i3, i3 + 6); i5++) {
            int intRandom = getIntRandom(0, (int) (i * 0.8d));
            int intRandom2 = getIntRandom(0, (int) (i2 * 0.8d));
            int intRandom3 = getIntRandom((int) (i * 0.8d), i);
            int intRandom4 = getIntRandom((int) (i2 * 0.6d), i2);
            graphics.setColor(getColor(i4));
            graphics.drawLine(intRandom, intRandom2, intRandom3, intRandom4);
        }
    }

    private static void setSquareBackGround(Graphics graphics, int i, int i2, int i3) {
        for (int i4 = 0; i4 < getIntRandom(i3, i3 + 2); i4++) {
            int intRandom = getIntRandom(0, (int) (i * 0.3d));
            int intRandom2 = getIntRandom(0, (int) (i2 * 0.3d));
            int intRandom3 = getIntRandom((int) (i * 0.5d), i);
            int intRandom4 = getIntRandom((int) (i2 * 0.55d), i2);
            graphics.setColor(getColor(100));
            int i5 = intRandom3 - intRandom;
            int i6 = intRandom4 - intRandom2;
            if (i5 < 0) {
                i5 = -i5;
            }
            if (i6 < 0) {
                i6 = -i6;
            }
            graphics.drawRect(intRandom, intRandom2, i5, i6);
            graphics.setColor(getColor(25));
            graphics.fillRect(intRandom, intRandom2, i5, i6);
        }
    }

    private static int getIntRandom(int i, int i2) {
        if (i2 < i) {
            i2 = i;
            i = i2;
        }
        return i + ((int) (Math.random() * (i2 - i)));
    }

    private int getIntRandom(double d, double d2) {
        if (d2 < d) {
            d2 = d;
            d = d2;
        }
        return (int) (d + ((int) (Math.random() * (d2 - d))));
    }

    private static Color getColor(int i) {
        return new Color((int) (Math.random() * 255.0d), (int) (Math.random() * 255.0d), (int) (Math.random() * 255.0d), i);
    }

    private int getCloserRandom(int i, int i2) {
        if (i + i2 > 255) {
            return getCloserRandom(i, i2 - getIntRandom(1, i2 + 20));
        }
        if (i + i2 < 0) {
            return getCloserRandom(i, i2 + getIntRandom(1, i2 + 20));
        }
        if (Math.abs(i2) < 60) {
            return getCloserRandom(i, getIntRandom(-255, 255));
        }
        return i + i2;
    }

}
