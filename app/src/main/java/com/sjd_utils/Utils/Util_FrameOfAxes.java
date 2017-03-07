package com.sjd_utils.Utils;

/**
 * Created by sjd on 2017/2/17.
 *
 * 关于坐标轴 角度处理的工具类
 */

public class Util_FrameOfAxes {
    /**
     * 角度处理工具 开始
     */
    /*
    *方法用于 获取给定点 和 水平线的 夹角
    * x,y 为坐标点
    * centerX，centerY 为圆点
    * radius 为半径
    * return 与 过圆心平行于X正半轴平行线 的夹角
    * */
    public static float getAngel(float x, float y, float centerX, float centerY) {
        double x1 = x - centerX;
        double y1 = y - centerY;
        if ( x1 > 0) {
            return (float) (Math.asin(y1 / Math.hypot(x1, y1)) * 180 / Math.PI);
        } else {
            return (float) (Math.asin(y1 / Math.hypot(x1, y1)) * 180 / Math.PI + 180);
        }
    }

    /*x,y 为 坐标点
    * centerX，centerY为圆心
    * return 当前点所在象限   Tip: 这里使用象限仅作角度正负值判断，不细究坐标轴*/
    public static int getQuadrant(float x, float y, float centerX, float centerY) {
        int tmpX = (int) (x - centerX);
        int tmpY = (int) (y - centerY);
        if (tmpX >= 0) {
            return tmpY >= 0 ? 4 : 1;
        } else {
            return tmpY >= 0 ? 3 : 2;
        }
    }

    //精确判断点 与圆心所在坐标的角度， 按照直视的直角坐标系， 屏幕坐标还需转换 0-360
    public static double getEAngel(float x, float y, float centerX, float centerY) {
        float x1 = x - centerX;
        float y1 = y - centerY;
        if (y1 == 0) {
            if (x1 >= 0) {
                return 0;
            } else {
                return 180;
            }
        }
        if (x1 == 0) {
            if (y1 > 0) {
                return 90;
            } else {
                return 270;
            }
        }
        if (y1 > 0) {
            return  (Math.acos(x1 / Math.hypot(x1, y1)) * 180 / Math.PI);
        } else {
            return  360-(Math.acos(x1 / Math.hypot(x1, y1)) * 180 / Math.PI );
        }
    }
    /**
     * 角度处理工具 结束
     */
}
