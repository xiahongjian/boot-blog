package tech.hongjian.blog.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.util.*;

import static java.util.Optional.ofNullable;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Environment {
    private Properties props = new Properties();

    public Properties props() {
        return this.props;
    }

    public static Environment of(@NonNull Map<String, String> map) {
        Environment env = new Environment();
        map.forEach((key, value) -> env.props.setProperty(key, value));
        return env;
    }

    /**
     * Set a value to props
     *
     * @param key   key
     * @param value value
     * @return return Environment instance
     */
    public Environment set(@NonNull String key, @NonNull Object value) {
        String val = value.toString();
        props.put(key, val);
        return this;
    }

    /**
     * And Set the same
     *
     * @param key   key
     * @param value value
     * @return return Environment instance
     */
    public Environment add(@NonNull String key, @NonNull Object value) {
        return set(key, value);
    }

    /**
     * Add a map to props
     *
     * @param map map config instance
     * @return return Environment instance
     */
    public Environment addAll(@NonNull Map<String, String> map) {
        map.forEach((key, value) -> this.props.setProperty(key, value));
        return this;
    }

    public Environment addAll(@NonNull Properties props) {
        props.forEach((key, value) -> this.props.setProperty(key.toString(), value.toString()));
        return this;
    }

    public Optional<String> get(String key) {
        if (null == key) return Optional.empty();
        return ofNullable(props.getProperty(key));
    }


    public String getOrNull(String key) {
        return get(key).orElse(null);
    }

    public String get(String key, String defaultValue) {
        return get(key).orElse(defaultValue);
    }

    public Optional<Object> getObject(String key) {
        return ofNullable(props.get(key));
    }

    public Optional<Integer> getInt(String key) {
        if (null != key && getObject(key).isPresent()) {
            return Optional.of(Integer.parseInt(getObject(key).get().toString()));
        }
        return Optional.empty();
    }

    public Integer getIntOrNull(String key) {
        Optional<Integer> intVal = getInt(key);
        return intVal.orElse(null);
    }

    public Integer getInt(String key, int defaultValue) {
        if (getInt(key).isPresent()) {
            return getInt(key).get();
        }
        return defaultValue;
    }

    public Optional<Long> getLong(String key) {
        if (null != key && getObject(key).isPresent()) {
            return Optional.of(Long.parseLong(getObject(key).get().toString()));
        }
        return Optional.empty();
    }

    public Long getLongOrNull(String key) {
        Optional<Long> longVal = getLong(key);
        return longVal.orElse(null);
    }

    public Long getLong(String key, long defaultValue) {
        return getLong(key).orElse(defaultValue);
    }

    public Optional<Boolean> getBoolean(String key) {
        if (null != key && getObject(key).isPresent()) {
            return Optional.of(Boolean.parseBoolean(getObject(key).get().toString()));
        }
        return Optional.empty();
    }

    public Boolean getBooleanOrNull(String key) {
        Optional<Boolean> boolVal = getBoolean(key);
        return boolVal.orElse(null);
    }

    public Boolean getBoolean(String key, boolean defaultValue) {
        return getBoolean(key).orElse(defaultValue);
    }

    public Optional<Double> getDouble(String key) {
        if (null != key && getObject(key).isPresent()) {
            return Optional.of(Double.parseDouble(getObject(key).get().toString()));
        }
        return Optional.empty();
    }

    public Double getDoubleOrNull(String key) {
        Optional<Double> doubleVal = getDouble(key);
        return doubleVal.orElse(null);
    }

    public Double getDouble(String key, double defaultValue) {
        return getDouble(key).orElse(defaultValue);
    }

//    public Optional<Date> getDate(String key) {
//        if (null != key && getObject(key).isPresent()) {
//            String value = getObject(key).get().toString();
//            Date   date  = (Date) ReflectKit.convert(Date.class, value);
//            return Optional.ofNullable(date);
//        }
//        return Optional.empty();
//    }
//
//    public Date getDateOrNull(String key) {
//        Optional<Date> dateVal = getDate(key);
//        return dateVal.orElse(null);
//    }

    public Map<String, Object> getPrefix(String key) {
        Map<String, Object> map = new HashMap<>();
        if (null != key) {
            props.forEach((key_, value) -> {
                if (key_.toString().startsWith(key)) {
                    map.put(key_.toString().substring(key.length() + 1), value);
                }
            });
        }
        return map;
    }

    public Map<String, String> toMap() {
        Map<String, String> map = new HashMap<>(props.size());
        props.forEach((k, v) -> map.put(k.toString(), v.toString()));
        return map;
    }

    public boolean hasKey(String key) {
        if (null == key) {
            return false;
        }
        return props.containsKey(key);
    }

    public boolean hasValue(String value) {
        return props.containsValue(value);
    }
}
