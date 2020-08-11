package com.hand.hls.util;

import java.util.*;

public class MapUtils {
    public static List<Map> keyToCamelCase(List<Map> listMap) {
        List<Map> result = new ArrayList<>();
        if (listMap == null) {
            return result;
        }
        listMap.forEach(map -> {
            result.add(MapUtils.toResultMap(map));
        });
        return result;
    }


    public static Map toResultMap(Map map) {
        Map newWap = new HashMap();
        map.forEach((key, value) -> {
            String camelCaseMapKey = StringUtils.toCamelCase(String.valueOf(key));
            newWap.put(camelCaseMapKey, value);
        });
        return newWap;
    }

    public static List<Map> keyToCamelCaseGeneric(List<? extends Map> listMap) {
        List<Map> result = new ArrayList<>();
        if (listMap == null) {
            return result;
        }
        for (Map map : listMap) {
            Map newWap = new HashMap();
            Set set = map.entrySet();
            Iterator iterator = set.iterator();
            while (iterator.hasNext()) {
                Map.Entry next = (Map.Entry) iterator.next();
                String camelCaseMapKey = StringUtils.toCamelCase(String.valueOf(next.getKey()));
                newWap.put(camelCaseMapKey, next.getValue());
            }
            result.add(newWap);
        }
        return result;
    }
}
