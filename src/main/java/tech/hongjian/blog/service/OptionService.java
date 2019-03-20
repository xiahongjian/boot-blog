package tech.hongjian.blog.service;

import lombok.NonNull;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tech.hongjian.blog.db.entity.Option;
import tech.hongjian.blog.db.mapper.OptionMapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class OptionService {
    @Autowired
    private OptionMapper optionMapper;

    private static Map<String, Option> cache = new ConcurrentHashMap<>();


    public void save(Option option) {
        optionMapper.insert(option);
        cache.clear();
    }

    public void update(@NonNull Option option) {
        optionMapper.updateByPrimaryKey(option);
        cache.clear();
    }

    public void saveOption(@NonNull Option option) {
        Option opt = find(option.getName());
        if (opt != null) {
            opt.setValue(option.getValue());
            update(opt);
            return;
        }
        save(option);
    }

    public void saveOption(String name, String value) {
        saveOption(new Option(name, value));
    }

    public void saveOption(String name, String value, String description) {
        saveOption(new Option(name, value, description));
    }

    public Option find(String name) {
        if (StringUtils.isBlank(name))
            return null;
        Option option = cache.get(name);
        if (option == null) {
            option = optionMapper.selectByPrimaryKey(name);
            if (option != null) {
                cache.put(name, option);
            }
        }
        return option;
    }

    public String getOptionValue(String name) {
        Option option = find(name);
        return option == null ? null : option.getValue();
    }

    public String getOptionValue(String name, String defaultVal) {
        Option option = find(name);
        return option == null ? defaultVal : option.getValue();
    }

    public Map<String, String> getOptions() {
        List<Option> options = optionMapper.selectAll();
        Map<String, String> map = new HashMap<>(options.size());
        options.forEach(o -> map.put(o.getName(), o.getValue()));
        return map;
    }

}
