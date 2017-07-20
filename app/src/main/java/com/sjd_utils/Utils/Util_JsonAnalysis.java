package com.sjd_utils.Utils;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * 主要看解析Json的方式，
 * Created by sjd on 2017/7/20.
 */

public class Util_JsonAnalysis {
    public static JsonStructure  analysisJson(String s){
        JsonStructure jsonStructure = new JsonStructure();
        try {
            JSONObject jsonObject = new JSONObject(s);
            if(jsonObject.has("Status")){
                jsonStructure.setStatus(  jsonObject.optBoolean("Status") );
            }

            if(jsonObject.has("Data")){
                JSONObject data = jsonObject.getJSONObject("Data");
                JsonStructure.DataBean dataBean = jsonStructure.getData();
                if (data.has("Access_token")){
                    dataBean.setAccess_token(data.optString("Access_token"));
                }
                jsonStructure.setData(dataBean);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonStructure;
    }

    //凑数的，去掉报错信息
    private static class JsonStructure {
        public void setData(DataBean dataBean) {
        }

        public void setStatus(boolean status) {
        }
        DataBean dataBean;
        public DataBean getData() {
            return dataBean;
        }

        private class DataBean{
            public void setAccess_token(String access_token) {
            }
        }
    }
}
