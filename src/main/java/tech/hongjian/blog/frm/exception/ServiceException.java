package tech.hongjian.blog.frm.exception;

public class ServiceException extends RuntimeException {
    public ServiceException() {}

    public ServiceException(String msg) {
        super(msg);
    }

    public ServiceException(Exception e) {
        super(e);
    }
}
