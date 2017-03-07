package com.nfc;

import android.nfc.Tag;
import android.nfc.tech.MifareClassic;
import android.nfc.tech.MifareUltralight;
import android.util.Log;

import java.io.IOException;
import java.nio.charset.Charset;

/**
 * Created by sjd on 2017/1/11.
 */

public class Utils {
            //37701700151
    private static final String TAG = "SJD";

    public static void writeTagMifareUltralight(Tag tag, String tagText) {
        MifareUltralight ultralight = MifareUltralight.get(tag);
        try {
            ultralight.connect();
            ultralight.writePage(4, "abcd".getBytes(Charset.forName("US-ASCII")));
            ultralight.writePage(5, "efgh".getBytes(Charset.forName("US-ASCII")));
            ultralight.writePage(6, "ijkl".getBytes(Charset.forName("US-ASCII")));
            ultralight.writePage(7, "mnop".getBytes(Charset.forName("US-ASCII")));
        } catch (IOException e) {
            Log.e(TAG, "IOException while closing MifareUltralight...", e);
        } finally {
            try {
                ultralight.close();
            } catch (IOException e) {
                Log.e(TAG, "IOException while closing MifareUltralight...", e);
            }
        }
    }

    public static void writeTagMifareClassic(Tag tag) {
        MifareClassic mfc = MifareClassic.get(tag);
        try {
            mfc.connect();
            boolean auth = false;
            short sectorAddress = 1;
            byte[] KeyValue = null;
//            auth = mfc.authenticateSectorWithKeyA(sectorAddress, MifareClassic.KEY_DEFAULT);
            auth = isKeyMifareClassicEnable(mfc, sectorAddress, Contents.KEY_A_MINE);
            if (auth) {
                KeyValue = mfc.readBlock(4 * sectorAddress + 3);
            }
            if (auth && KeyValue != null) {
                for (int i = 0; i < 6; i++) {//Contents.KEY_A_MINE 一定是6位
                    KeyValue[i] = Contents.KEY_A_MINE[i];
                }
                for (int i = 0; i < 6; i++) {//Contents.KEY_A_MINE 一定是6位
                    KeyValue[i+10] = Contents.KEY_B_MINE[i];
                }
                Log.e(TAG, "writeTagMifareClassic:  n = " + (4 * sectorAddress + 3) + " KeyValue = " + Utils_chat.byte2HexStr(KeyValue));
                mfc.writeBlock(4 * sectorAddress + 3, KeyValue);

                //0xd5b87d75650804006263646566676869
                mfc.writeBlock(0, "  22676888      ".getBytes());

                // the last block of the sector is used for KeyA and KeyB cannot be overwritted
                mfc.writeBlock(4, "  22676888      ".getBytes());
                mfc.writeBlock(5, "1322676888000000".getBytes());

                mfc.close();
                Log.e(TAG, "writeTag: 写入成功");
            } else {
                Log.e(TAG, "writeTag: 密码错误");
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block  
            e.printStackTrace();
        } finally {
            try {
                mfc.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block  
                e.printStackTrace();
            }
        }
    }

    public static String readTagMifareUltralight(Tag tag) {
        MifareUltralight mifare = MifareUltralight.get(tag);
        try {
            mifare.connect();
            byte[] payload = mifare.readPages(4);
            return new String(payload, Charset.forName("US-ASCII"));
        } catch (IOException e) {
            Log.e(TAG, "IOException while writing MifareUltralight message...", e);
        } finally {
            if (mifare != null) {
                try {
                    mifare.close();
                } catch (IOException e) {
                    Log.e(TAG, "Error closing tag...", e);
                }
            }
        }
        return null;
    }

    public static String readTagMifareClassic(Tag tag) {
        MifareClassic mfc = MifareClassic.get(tag);//获取MifareClassic对象
        for (String tech : tag.getTechList()) {
            System.out.println(tech);
        }
        boolean auth = false;
        //读取TAG

        try {
            String metaInfo = "";
            mfc.connect();//允许对MiafreClassic标签进行IO操作
            int type = mfc.getType();//获取TAG的类型
            int sectorCount = mfc.getSectorCount();//获取TAG中包含的扇区数
            int blockCount = mfc.getBlockCount();//获取TAG中包含的块数量
            int size = mfc.getSize();//获取TAG的容量大小
            String typeS = "";
            switch (type) {
                case MifareClassic.TYPE_CLASSIC:
                    typeS = "TYPE_CLASSIC";
                    break;
                case MifareClassic.TYPE_PLUS:
                    typeS = "TYPE_PLUS";
                    break;
                case MifareClassic.TYPE_PRO:
                    typeS = "TYPE_PRO";
                    break;
                case MifareClassic.TYPE_UNKNOWN:
                    typeS = "TYPE_UNKNOWN";
                    break;
            }
            metaInfo += "卡片类型：" + typeS + "\n共" + sectorCount + "个扇区\n共"
                    + blockCount + "个块\n存储空间: " + size + "B\n";
            for (int j = 0; j < sectorCount; j++) {
//                验证当前扇区的KeyA密码，返回值为ture或false
//                常用KeyA：默认出厂密码：KEY_DEFAULT，
//                各种用途的供货商必须配合该技术的MAD：KEY_MIFARE_APPLICATION_DIRECTORY
//                被格式化成NDEF格式的密码：KEY_NFC_FORUM
//                auth = mfc.authenticateSectorWithKeyA(j, MifareClassic.KEY_DEFAULT);
                auth = isKeyMifareClassicEnable(mfc, j, Contents.KEY_A_MINE);
                int bCount;
                int bIndex;
                if (auth) {
                    metaInfo += "Sector " + j + ":验证成功\n";
                    // 读取扇区中的块
                    bCount = mfc.getBlockCountInSector(j);//获得当前扇区的所包含块的数量
                    bIndex = mfc.sectorToBlock(j);//当前扇区的第j块的块号
                    for (int i = 0; i < bCount; i++) {
                        byte[] data = mfc.readBlock(bIndex);//读取当前块的数据
                        metaInfo += "Block " + bIndex + " : " + bytesToHexString(data) + "\n";
                        bIndex++;
                    }
                } else {
                    metaInfo += "Sector " + j + ":验证失败\n";
                }
            }
            return metaInfo;
        } catch (Exception e) {
//            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
            Log.e(TAG, "readTagMifareClassic: " + e);
            e.printStackTrace();
        } finally {
            if (mfc != null) {
                try {
                    mfc.close();//释放资源
                } catch (IOException e) {
//                    Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
                    Log.e(TAG, "readTagMifareClassic: " + e);
                }
            }
        }
        return null;
    }

    /**
     * 验证第sectorIndex扇区的密码.
     *
     * @param mfc
     * @param sectorIndex
     * @return
     */
    public static Boolean isKeyMifareClassicEnable(MifareClassic mfc, int sectorIndex, byte[] myKeyA) {
        boolean auth = false;
        try {
            auth = mfc.authenticateSectorWithKeyA(sectorIndex, MifareClassic.KEY_DEFAULT);
            if (!auth) {
                auth = mfc.authenticateSectorWithKeyA(sectorIndex, myKeyA);
            }
            if (!auth) {
                auth = mfc.authenticateSectorWithKeyA(sectorIndex, MifareClassic.KEY_NFC_FORUM);
            }
        } catch (IOException e) {
            Log.e(TAG, "IOException while authenticateSectorWithKey MifareClassic...", e);
        }
        return auth;
    }

    //字符序列转换为16进制字符串
    public static String bytesToHexString(byte[] src) {
        StringBuilder stringBuilder = new StringBuilder("0x");
        if (src == null || src.length <= 0) {
            return null;
        }
        char[] buffer = new char[2];
        for (int i = 0; i < src.length; i++) {
            buffer[0] = Character.forDigit((src[i] >>> 4) & 0x0F, 16);
            buffer[1] = Character.forDigit(src[i] & 0x0F, 16);
            System.out.println(buffer);
            stringBuilder.append(buffer);
        }
        return stringBuilder.toString();
    }
}
