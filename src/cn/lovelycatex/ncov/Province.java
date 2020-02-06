package cn.lovelycatex.ncov;

import java.util.HashMap;
import java.util.Map;

public class Province {
    private Map<String,ProvinceBean> provinceBeanMap = new HashMap<>();

    public Map<String, ProvinceBean> getProvinceBeanMap() {
        return provinceBeanMap;
    }

    public void setProvinceBeanMap(Map<String, ProvinceBean> provinceBeanMap) {
        this.provinceBeanMap = provinceBeanMap;
    }
}
