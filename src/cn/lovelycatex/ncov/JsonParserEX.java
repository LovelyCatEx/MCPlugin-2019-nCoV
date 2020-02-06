package cn.lovelycatex.ncov;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

public class JsonParserEX {
    public String ParseToString(String json,String get) {
        String result = null;
        JSONObject jsonObject = JSON.parseObject(json);
        result = jsonObject.getString(get);
        return result;
    }
    public Integer ParseToInt(String json,String get) {
        Integer result = null;
        JSONObject jsonObject = JSON.parseObject(json);
        result = jsonObject.getInteger(get);
        return result;
    }
    public boolean ParseToBoolean(String json,String get) {
        boolean result = false;
        JSONObject jsonObject = JSON.parseObject(json);
        result = jsonObject.getBoolean(get);
        return result;
    }
    @Deprecated
    public String ParseToStringEX(String json,String get) {
        String[] split = get.split("\\.");
        String jsonEX = null;
        for (int i=0;i<split.length;i++) {
            if (i == 0) {
                jsonEX = ParseToString(json,split[i]);
            }
            jsonEX = ParseToString(jsonEX,split[i]);
            System.out.println(jsonEX);
        }
        return jsonEX;
    }
}
