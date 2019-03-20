package tech.hongjian.blog.utils;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.PropertyFilter;
import com.alibaba.fastjson.serializer.SimplePropertyPreFilter;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class JSONUtil {
    public static <T> T parse(String text, Class<T> clazz) {
        return JSONObject.parseObject(text, clazz);
    }

    public static String toJson(Object object) {
        return JSONObject.toJSONString(object);
    }

    public static String toJson(Object object, String... props) {
        if (object == null || props == null || props.length == 0)
            return JSONObject.toJSONString(object);
        return JSONObject.toJSONString(object, new SimplePropertyPreFilter(props));
    }

    public static String toJsonExclude(Object object, String... excludeProps) {
        if (object == null || excludeProps == null || excludeProps.length == 0)
            return JSONObject.toJSONString(object);
        Set<String> props = new HashSet<>(Arrays.asList(excludeProps));
        return JSONObject.toJSONString(object, (PropertyFilter) (object1, name, value) -> !props.contains(name));
    }
}
