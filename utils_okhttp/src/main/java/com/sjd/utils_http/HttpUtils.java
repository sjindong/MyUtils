package com.sjd.utils_http;

import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.Socket;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by sjd on 2017/2/13.
 */
public class HttpUtils {
    /**
     * 保存数据，传递参数给web服务器端
     *
     * @param title      标题
     * @param timelength 时长
     * @return
     */
    public static boolean save(String title, String timelength) throws Exception {
        //119.119.228.5为本机IP地址，不能用localhost代替
        String path = "http://192.168.1.5:8080/http_service/servlet";
        Map<String, String> params = new HashMap<String, String>();
        params.put("title", title);
        params.put("timelength", timelength);
        //get请求方式
        return sendGETRequest(path, params, "UTF-8");
        //post请求方式
        //return sendPOSTRequest(path,params,"UTF-8");
        //httpClient请求方式，如果单纯传递参数的话建议使用GET或者POST请求方式
        //return sendHttpClientPOSTRequest(path,params,"UTF-8");//httpclient已经集成在android中
    }

    /**
     * 通过HttpClient发送post请求
     *
     * @param path
     * @param params
     * @param encoding
     * @return
     * @throws Exception
     */
    private static boolean sendHttpClientPOSTRequest(String path, Map<String, String> params, String encoding) throws Exception {
        /*List<NameValuePair> pairs = new ArrayList<NameValuePair>();//存放请求参数
        for (Map.Entry<String, String> entry : params.entrySet()) {
            pairs.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
        }
        //防止客户端传递过去的参数发生乱码，需要对此重新编码成UTF-8
        UrlEncodedFormEntity entity = new UrlEncodedFormEntity(pairs, encoding);
        HttpPost httpPost = new HttpPost(path);
        httpPost.setEntity(entity);
        DefaultHttpClient client = new DefaultHttpClient();
        HttpResponse response = client.execute(httpPost);
        if (response.getStatusLine().getStatusCode() == 200) {
            return true;
        }*/
        return false;
    }

    /**
     * 放松post请求
     *
     * @param path     请求路径
     * @param params   请求参数
     * @param encoding 编码
     * @return 请求是否成功
     */
    private static boolean sendPOSTRequest(String path, Map<String, String> params, String encoding) throws Exception {
        StringBuilder data = new StringBuilder(path);
        for (Map.Entry<String, String> entry : params.entrySet()) {
            data.append(entry.getKey()).append("=");
            //防止客户端传递过去的参数发生乱码，需要对此重新编码成UTF-8
            data.append(URLEncoder.encode(entry.getValue(), encoding));
            data.append("&");
        }
        data.deleteCharAt(data.length() - 1);
        byte[] entity = data.toString().getBytes();//得到实体数据
        HttpURLConnection conn = (HttpURLConnection) new URL(path).openConnection();
        conn.setConnectTimeout(5000);
        conn.setRequestMethod("POST");
        conn.setDoOutput(true);//设置为允许对外输出数据
        conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        conn.setRequestProperty("Content-Length", String.valueOf(entity.length));
        OutputStream outStream = conn.getOutputStream();
        outStream.write(entity);//写到缓存
        if (conn.getResponseCode() == 200) {//只有取得服务器返回的http协议的任何一个属性时才能把请求发送出去
            return true;
        }
        return false;
    }

    /**
     * 发送GET请求
     *
     * @param path   请求路径
     * @param params 请求参数
     * @return 请求是否成功
     * @throws Exception
     */
    private static boolean sendGETRequest(String path, Map<String, String> params, String encoding) throws Exception {
        StringBuilder url = new StringBuilder(path);
        url.append("?");
        for (Map.Entry<String, String> entry : params.entrySet()) {
            url.append(entry.getKey()).append("=");
            //get方式请求参数时对参数进行utf-8编码，URLEncoder
            //防止客户端传递过去的参数发生乱码，需要对此重新编码成UTF-8
            url.append(URLEncoder.encode(entry.getValue(), encoding));
            url.append("&");
        }
        url.deleteCharAt(url.length() - 1);
        HttpURLConnection conn = (HttpURLConnection) new URL(url.toString()).openConnection();
        conn.setConnectTimeout(5000);
        conn.setRequestMethod("GET");
        if (conn.getResponseCode() == 200) {
            return true;
        }
        return false;
    }

    private void startSocket() {
        Socket socket = null;
        String message =null;// mEditText.getText().toString() + "/r/n";
        try {
            //创建Socket 
            socket = new Socket("192.168.1.2", 54321);
            //socket = new Socket("10.14.114.127",54321); //IP：10.14.114.127，端口54321 
            //向服务器发送消息 
            PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);
            out.println(message + "wmy");
            //接收来自服务器的消息 
            BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String msg = br.readLine();
            if (msg != null) {
//                mTextView.setText(msg);
            } else {
//                mTextView.setText("数据错误!");
            }
            //关闭流 
            out.close();
            br.close();
            //关闭Socket 
            socket.close();
        } catch (Exception e) {
            // TODO: handle exception 
            Log.e("SJD", e.toString());
        }
    }
}
