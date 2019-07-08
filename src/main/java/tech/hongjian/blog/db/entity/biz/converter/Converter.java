package tech.hongjian.blog.db.entity.biz.converter;

import lombok.NonNull;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author xiahongjian
 * @time 2019-07-08 18:36
 */
public interface Converter<F, T> {
    T convert(F o);

    default List<T> convert(@NonNull List<F> list) {
        return list.stream().map(this::convert).collect(Collectors.toList());
    }
}
