package com.sjd;


/*-------前端调用
        String url = "/Basic_config/basic_config/TextToImg?text1="+text1+"&fontsize1="+fontsize1+"&font1="+font1+"&italic1="+italic1+"&blod1="+blod1;//+"&url="+imgsrc;
        Image image = new Image();
        image.setUrl(url)
        ;*/
//        -----后台配置到服务中

import java.io.*;
import java.awt.image.*;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.servlet.*;
import javax.servlet.http.*;

import org.apache.log4j.Logger;

import com.aoto.framework.utils.LogUtil;
import com.google.gwt.user.client.Window;
import com.sun.image.codec.jpeg.*;

public class Util_TextToWord extends HttpServlet {

    private final static Logger logger = Logger.getLogger(TrxInfoSyncServlet.class);
    private static final long serialVersionUID = 3382766604072512263L;

    /**
     * 这个程序
     *
     * @param text1     String文本字符串
     * @param fontsize1 int 字体大小
     * @param font1     String字体类型
     * @param italic1   String是否斜体
     * @param blod1     String 是否加粗
     * @param t1        String图片生成地址
     * @param request   请求
     * @param response  回应
     */
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String text1 = request.getParameter("text1");
        int fontsize1 = Integer.parseInt(request.getParameter("fontsize1"));
        String font1 = request.getParameter("font1");
        String italic1 = request.getParameter("italic1");
        String blod1 = request.getParameter("blod1");
        String is_double_width = request.getParameter("is_double_width");
        String is_double_height = request.getParameter("is_double_height");
        //	String url= request.getParameter("url");
        response.setContentType("image/jpeg");
        response.setCharacterEncoding("utf-8");
//	 //设置响应头控制浏览器不缓存图片数据
//	   response.setDateHeader("expries", -1);
//	   response.setHeader("Cache-Control", "no-cache");
//	   response.setHeader("Pragma", "no-cache");

        System.out.println("文本内容：" + text1 + "#severlet调用次开始");
        doimg(text1, fontsize1, font1, italic1, blod1, is_double_height, is_double_width, response.getOutputStream());//, response.getOutputStream()

    }

    /**
     * 程序是以输入字体大小做标准图片，然后倍高倍宽是将图片拉伸
     *
     * @param text1             文本内容
     * @param fontsize1         字体大小（doimg 和doimg2 输入参数一样，但程序中处理方式不一样）
     * @param font1             字体
     * @param italic1           斜体
     * @param blod1             粗体
     * @param is_double_height1 倍高标志
     * @param is_double_width1  倍宽标志
     * @param out               输出
     */
    private void doimg(String text1, int fontsize1, String font1, String italic1, String blod1, String is_double_height1, String is_double_width1, OutputStream out) { //,OutputStream t1
        try {
            String text = text1;
            int fontsize = fontsize1 * 2;//输入字体的两倍，为双倍宽和高 有更好地视觉效果
            String font = font1;
            String italic = italic1;
            String blod = blod1;
            String is_double_height = is_double_height1;
            String is_double_width = is_double_width1;
            //OutputStream t=t1;
            //int length=text.trim().length();;
            int fonttype;
            if (blod.equalsIgnoreCase("bold")) {
                if (italic.equalsIgnoreCase("italic")) {
                    fonttype = Font.BOLD + Font.ITALIC;
                } else {
                    fonttype = Font.BOLD;
                }
            } else {
                if (italic.equalsIgnoreCase("italic")) {
                    fonttype = Font.ITALIC;
                } else {
                    fonttype = Font.PLAIN;
                }
            }
            Font mFont = new Font(font, fonttype, fontsize);//默认字体
            BufferedImage bi = new BufferedImage(300, 300, BufferedImage.TYPE_INT_RGB);//构建 BufferedImage试用的图片来提前获取文本的大小
            Graphics2D g = bi.createGraphics();
            g.setFont(mFont);
            g.drawString(text, 0, 60);
            FontMetrics fm = g.getFontMetrics();
            int width = fm.stringWidth(text);//自动获取输入文本的实际宽度
            int height = fm.getHeight();//自动获取输入文本的实际高度
            if (width == 0) {
                width = 1;
                logger.error("预览图片错误：输入字符串为空");
                LogUtil.log(logger, "预览图片错误：输入字符串为空");
            }

            BufferedImage bi1 = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);//构建 BufferedImage
            Graphics2D g2 = bi1.createGraphics();
            g2.setBackground(Color.white);//背景色
            g2.setPaint(Color.BLACK);//字体颜色
            g2.setFont(mFont);//设置字体
            g2.clearRect(0, 0, width, height);
            g2.drawString(text, 0, fontsize);//固定位置 写上文字

            int widthsed = (is_double_width.equals("Y")) ? width * 2 : width;
            int heightsed = (is_double_height.equals("Y")) ? height * 2 : height;
//	System.out.println("widthsed"+widthsed+"heightsed"+heightsed);

            BufferedImage biNEW = new BufferedImage(widthsed, heightsed, BufferedImage.TYPE_INT_RGB);
            Graphics g3 = biNEW.createGraphics();
//	g3.drawImage(bi1, 0, 0, widthsed, heightsed, );
            g3.drawImage(bi1, 0, 0, widthsed, heightsed, null);
            g3.setColor(Color.white);//背景色
            g3.dispose();

//	System.out.println("biNEW"+text+biNEW);
            bi1 = biNEW;
            System.out.println(is_double_width + " width" + width + "widthsed" + widthsed);
            System.out.println(is_double_height + " height" + height + "heightsed" + heightsed);
            System.out.println("bi" + text + biNEW);
//	Graphics2D g3 = bi2.createGraphics();
//	g3.setBackground(Color.white);//背景色
//	g3.clearRect(0, 0, widthsed, heightsed);
//	g3.drawImage(bi,  null, 0, heightsed);


            //FileOutputStream out =new FileOutputStream(url);   //如果想生成新的文件需要定义t

            JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);   //如果想生成新的文件需要定义t  //response.getOutputStream()

            JPEGEncodeParam param = encoder.getDefaultJPEGEncodeParam(bi1);

            param.setQuality(1.0f, false);
            encoder.setJPEGEncodeParam(param);
            encoder.encode(bi1);


            //	System.out.println("调用结束");
        } catch (Exception e) { // (IOException ioe) {
            // TODO: handle exception
            //ioe.printStackTrace();
            logger.error("预览图片生成错误");
            LogUtil.log(logger, "预览图片生成错误");
            System.out.println(e);
        } finally {
            try {
                if (null != out) {
                    out.close();
                }
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }


    }

    /**
     * 程序是以输入字体大小 的两倍做标准图片，然后倍高倍宽是将图片压缩
     *
     * @param text1             文本内容
     * @param fontsize1         字体大小（doimg 和doimg2 输入参数一样，但程序中处理方式不一样）
     * @param font1             字体
     * @param italic1           斜体
     * @param blod1             粗体
     * @param is_double_height1 倍高标志
     * @param is_double_width1  倍宽标志
     * @param out               输出
     */
    private void doimg2(String text1, int fontsize1, String font1, String italic1, String blod1, String is_double_height1, String is_double_width1, OutputStream out) { //,OutputStream t1
        try {
            String text = text1;
            int fontsize = fontsize1 * 2;//输入字体的两倍，为双倍宽和高 有更好地视觉效果
            String font = font1;
            String italic = italic1;
            String blod = blod1;
            String is_double_height = is_double_height1;
            String is_double_width = is_double_width1;
            //OutputStream t=t1;
            //int length=text.trim().length();;
            int fonttype;
            if (blod.equalsIgnoreCase("bold")) {
                if (italic.equalsIgnoreCase("italic")) {
                    fonttype = Font.BOLD + Font.ITALIC;
                } else {
                    fonttype = Font.BOLD;
                }
            } else {
                if (italic.equalsIgnoreCase("italic")) {
                    fonttype = Font.ITALIC;
                } else {
                    fonttype = Font.PLAIN;
                }
            }
            Font mFont = new Font(font, fonttype, fontsize);//默认字体
            BufferedImage bi = new BufferedImage(300, 300, BufferedImage.TYPE_INT_RGB);//构建 BufferedImage试用的图片来提前获取文本的大小
            Graphics2D g = bi.createGraphics();
            g.setFont(mFont);
            g.drawString(text, 0, 60);
            FontMetrics fm = g.getFontMetrics();
            int width = fm.stringWidth(text);//自动获取输入文本的实际宽度
            int height = fm.getHeight();//自动获取输入文本的实际高度
            if (width == 0) {
                width = 1;
                logger.error("预览图片错误：输入字符串为空");
                LogUtil.log(logger, "预览图片错误：输入字符串为空");
            }

            BufferedImage bi1 = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);//构建 BufferedImage
            Graphics2D g2 = bi1.createGraphics();
            g2.setBackground(Color.white);//背景色
            g2.setPaint(Color.BLACK);//字体颜色
            g2.setFont(mFont);//设置字体
            g2.clearRect(0, 0, width, height);
            g2.drawString(text, 0, fontsize);//固定位置 写上文字
            int widthsed;
            int heightsed;

            if (is_double_width.equals("Y")) {
                widthsed = width;
            } else {
                widthsed = width / 2;
            }
            if (is_double_height.equals("Y")) {
                heightsed = height;
            } else {
                heightsed = height / 2;
            }

            BufferedImage biNEW = new BufferedImage(widthsed, heightsed, BufferedImage.TYPE_INT_RGB);
            Graphics g3 = biNEW.createGraphics();
            g3.drawImage(bi1, 0, 0, widthsed, heightsed, null);
            g3.setColor(Color.white);//背景色
            g3.dispose();
            bi1 = biNEW;//压缩后的图片重新返回给对象bi1

            //FileOutputStream out =new FileOutputStream(url);   //如果想生成新的文件需要定义t

            JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);   //如果想生成新的文件需要定义t  //response.getOutputStream()

            JPEGEncodeParam param = encoder.getDefaultJPEGEncodeParam(bi1);

            param.setQuality(1.0f, false);
            encoder.setJPEGEncodeParam(param);
            encoder.encode(bi1);


            //	System.out.println("调用结束");
        } catch (Exception e) { // (IOException ioe) {
            // TODO: handle exception
            //ioe.printStackTrace();
            logger.error("预览图片生成错误");
            LogUtil.log(logger, "预览图片生成错误");
            System.out.println(e);
        } finally {
            try {
                if (null != out) {
                    out.close();
                }
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }


    }
//	public static void main(String[] args) {
//	// TODO Auto-generated method stub
//
//	TextToImgServlet a= new TextToImgServlet();
//	String i="d:/1.jpg";
//	a.doimg("测试ceshi", 13, "宋体","italic","bold",i) ;
//	String b="/basic_config/TextToImg?text1=asflasdf阿斯蒂芬&fontsize1=13&font1=宋体&italic1=italic&blod1=blod";//+"&url="+imgsrc
//	}

}