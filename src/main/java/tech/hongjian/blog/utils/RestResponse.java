package tech.hongjian.blog.utils;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class RestResponse<T> {
    /**
     * 服务器响应数据
     */
    private T payload;

    /**
     * 是否成功
     */
    private boolean success;

    /**
     * 错误信息
     */
    private String msg;

    /**
     * 状态码
     */
    @Builder.Default
    private int code = -1;

    /**
     * 服务器响应时间
     */
    @Builder.Default
    private long timestamp = System.currentTimeMillis();

    public RestResponse(boolean success) {
        this.success = success;
    }

    public RestResponse(boolean success, T payload) {
        this.success = success;
        this.payload = payload;
    }

    public static RestResponse ok() {
        return RestResponse.builder().success(true).build();
    }

    public static <T> RestResponse ok(T payload) {
        return RestResponse.builder().success(true).build();
    }

    public static RestResponse fail() {
        return RestResponse.builder().success(false).build();
    }

    public static RestResponse fail(String msg) {
        return RestResponse.builder().success(false).msg(msg).build();
    }

    public static RestResponse fail(int code) {
        return RestResponse.builder().success(false).code(code).build();
    }

    public static RestResponse fail(int code, String msg) {
        return RestResponse.builder().success(false).code(code).msg(msg).build();
    }

}
