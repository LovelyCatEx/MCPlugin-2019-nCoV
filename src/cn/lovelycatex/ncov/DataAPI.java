package cn.lovelycatex.ncov;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.bukkit.command.CommandSender;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DataAPI {
    private static JsonParserEX jp = new JsonParserEX();

    public static String data = null;

    public static String confirm_desc = null;
    public static String s_desc = null;
    public static String cured_desc = null;
    public static String dead_desc = null;
    public static String serious_desc = null;

    public static String Incr_confirm = null;
    public static String Incr_s = null;
    public static String Incr_cured = null;
    public static String Incr_dead = null;
    public static String Incr_serious = null;

    public static String request(String GETurl){
        StringBuilder sb=new StringBuilder();
        try {
            URL url=new URL(GETurl);
            InputStream in=new BufferedInputStream(url.openStream());
            BufferedReader reader = new BufferedReader(new InputStreamReader(in,"UTF-8"));
            String line;
            while((line=reader.readLine())!=null){
                sb.append(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

    public static void updateData(CommandSender sender) {
        data = request(nCovMain.CONFIG_API);
        //System.out.println(data);
        if (data.equalsIgnoreCase("")) {
            MessageEX m = new MessageEX();
            m.pprivate_(sender,"&c获取数据失败！请检查API是否填写错误！ &6>> &aAPI:"+nCovMain.CONFIG_API);
            return;
        }

        JSONObject jsonObject = JSON.parseObject(data);
        String code = jsonObject.getString("code");
        String msg = jsonObject.getString("msg");

        if (!code.equalsIgnoreCase("") && !msg.equalsIgnoreCase("")) {
            if (!code.equalsIgnoreCase("200")) {
                MessageEX m = new MessageEX();
                m.pprivate_(sender,"&c获取数据失败！ &6>> &aCode:"+code+" Msg:"+msg+"&aAPI:"+nCovMain.CONFIG_API);
                return;
            }
        }

        String newslist = jsonObject.getString("newslist");
        JSONArray jsonArray = JSON.parseArray(newslist);
        newslist = jsonArray.getString(0);

        JSONObject jsonObject2 = JSON.parseObject(newslist);
        String data_news = jsonObject2.getString("news");
        String data_case = jsonObject2.getString("case");
        String data_desc = jsonObject2.getString("desc");

        JSONArray json_news = JSON.parseArray(data_news);
        JSONArray json_case = JSON.parseArray(data_case);
        JSONObject json_desc = JSON.parseObject(data_desc);

        //概况
        confirm_desc = json_desc.getString("confirmedCount");
        s_desc = json_desc.getString("suspectedCount");
        cured_desc = json_desc.getString("curedCount");
        dead_desc = json_desc.getString("deadCount");
        serious_desc = json_desc.getString("seriousCount");

        Incr_confirm = json_desc.getString("confirmedIncr");
        Incr_s = json_desc.getString("suspectedIncr");
        Incr_cured = json_desc.getString("curedIncr");
        Incr_dead = json_desc.getString("deadIncr");
        Incr_serious = json_desc.getString("seriousIncr");

        nCovMain.describe.setConfirm(confirm_desc);
        nCovMain.describe.setSuspected(s_desc);
        nCovMain.describe.setCured(cured_desc);
        nCovMain.describe.setDead(dead_desc);
        nCovMain.describe.setSerious(serious_desc);
        nCovMain.describe.setIncr_confirm(Incr_confirm);
        nCovMain.describe.setIncr_suspected(Incr_s);
        nCovMain.describe.setIncr_serious(Incr_serious);
        nCovMain.describe.setIncr_dead(Incr_dead);
        nCovMain.describe.setIncr_cured(Incr_cured);
        nCovMain.describe.setImgURL(json_desc.getString("imgUrl"));
        nCovMain.describe.setDailyPics(json_desc.getString("dailyPic"));
        //省份
        for (int i=0;i<json_case.size();i++) {
            JSONObject object = JSON.parseObject(json_case.getString(i));
            ProvinceBean provinceBean = new ProvinceBean();
            provinceBean.setConfirmed(object.getString("confirmedCount"));
            provinceBean.setSuspected(object.getString("suspectedCount"));
            provinceBean.setCured(object.getString("curedCount"));
            provinceBean.setComment(object.getString("comment"));
            provinceBean.setDead(object.getString("deadCount"));
            provinceBean.setProvinceName(object.getString("provinceShortName"));
            provinceBean.setProvinceALLName(object.getString("provinceName"));
            provinceBean.setTags(object.getString("tags"));
            nCovMain.province.getProvinceBeanMap().put(provinceBean.getProvinceALLName()+","+provinceBean.getProvinceName(),provinceBean);
        }
        //新闻
        for (int i=0;i<json_news.size();i++) {
            JSONObject object = JSON.parseObject(json_news.getString(i));
            NewsBean newsBean = new NewsBean();
            newsBean.setSummary(object.getString("summary"));
            newsBean.setTitle(object.getString("title"));
            newsBean.setBody(object.getString("body"));
            newsBean.setDate(object.getString("pubDateStr"));
            newsBean.setProvinceName(object.getString("provinceName"));
            newsBean.setInfoSource(object.getString("infoSource"));
            newsBean.setSourceURL(object.getString("sourceUrl"));
            nCovMain.news.getNewsBeanMap().put(newsBean.getProvinceName()+","+newsBean.getDate(),newsBean);
        }
    }
    public static List<String> getNews(boolean isAll) {
        List<String> list = new ArrayList<>();
        if (isAll) {
            list.add("&6>>&bnCov疫情新闻查询");
            list.add("&b共计"+nCovMain.news.getNewsBeanMap().size()+"则新闻：");
            int count = 1;
            for (Map.Entry<String,NewsBean> entry : nCovMain.news.getNewsBeanMap().entrySet()) {
                count++;
                String[] split = entry.getKey().split(","); //0省份 1时间
                list.add("&6>>>&b第"+count+"条&a("+split[0]+")&b：");
                list.add("&b"+split[1]+"&d>>&a"+entry.getValue().getTitle()+"：");
                if (!entry.getValue().getBody().equalsIgnoreCase("") && entry.getValue().getBody() != null) {
                    list.add("&6概述："+entry.getValue().getSummary());
                    list.add("&a"+entry.getValue().getBody());
                }else {
                    list.add("&a"+entry.getValue().getSummary());
                }
                list.add("&6来源："+entry.getValue().getInfoSource()+"&d/&6"+entry.getValue().getSourceURL());
            }
        }else {
            list.add("&6>>&bnCov疫情新闻查询");
            int count = 1;
            for (Map.Entry<String,NewsBean> entry : nCovMain.news.getNewsBeanMap().entrySet()) {
                count++;
                String[] split = entry.getKey().split(","); //0省份 1时间
                list.add("&6>>>&b第"+count+"条&a("+split[0]+")&b：");
                list.add("&b"+split[1]+"&d>>&a"+entry.getValue().getTitle()+"：");
                if (!entry.getValue().getBody().equalsIgnoreCase("") && entry.getValue().getBody() != null) {
                    list.add("&6概述："+entry.getValue().getSummary());
                    list.add("&a"+entry.getValue().getBody());
                }else {
                    list.add("&a"+entry.getValue().getSummary());
                }
                list.add("&6来源："+entry.getValue().getInfoSource()+"&d/&6"+entry.getValue().getSourceURL());
                break;
            }
        }
        return list;
    }

    public static List<String> getProvince(String name) {
        List<String> list = new ArrayList<>();
        boolean result = false;
        for (Map.Entry<String,ProvinceBean> entry : nCovMain.province.getProvinceBeanMap().entrySet()) {
            String[] split = entry.getKey().split(",");
            if (name.equalsIgnoreCase(split[0]) || name.equalsIgnoreCase(split[1])) {
                result = true;
                list.add("&6>>&bnCov疫情动态查询");
                list.add("&a查询省份：&b"+name);
                list.add("&c确认感染："+entry.getValue().getConfirmed());
                list.add("&e疑似感染："+entry.getValue().getSuspected());
                list.add("&a已治愈："+entry.getValue().getCured());
                list.add("&9死亡数："+entry.getValue().getDead());
                if (!entry.getValue().getTags().equalsIgnoreCase("") && entry.getValue().getTags() != null) {
                    list.add("&a附加信息："+entry.getValue().getTags());
                }
                if (!entry.getValue().getComment().equalsIgnoreCase("") && entry.getValue().getComment() != null) {
                    list.add("&a附加信息："+entry.getValue().getComment());
                }
            }
        }
        if (!result) {
            list.add(nCovMain.CONFIG_PREFIX+"&c未找到您查询的省份 &a>> &b"+name);
        }
        return list;
    }

    public static List<String> getPreview(){
        List<String> list = new ArrayList<>();
        list.add("&6>>&bnCov疫情动态查询");
        list.add("&c确认感染&d/&b新增："+confirm_desc+"/"+Incr_confirm);
        list.add("&e疑似感染&d/&b新增："+s_desc+"/"+Incr_s);
        list.add("&c严重人数&d/&b新增："+serious_desc+"/"+Incr_serious);
        list.add("&a已治愈&d/&b新增："+cured_desc+"/"+Incr_cured);
        list.add("&9死亡数&d/&b新增："+dead_desc+"/"+Incr_dead);
        return list;
    }
}
