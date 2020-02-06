package cn.lovelycatex.ncov;

import java.util.HashMap;
import java.util.Map;

public class News {
    public Map<String,NewsBean> NewsBeanMap = new HashMap<>();

    public void setNewsBeanMap(Map<String, NewsBean> newsBeanMap) {
        NewsBeanMap = newsBeanMap;
    }

    public Map<String, NewsBean> getNewsBeanMap() {
        return NewsBeanMap;
    }
}
