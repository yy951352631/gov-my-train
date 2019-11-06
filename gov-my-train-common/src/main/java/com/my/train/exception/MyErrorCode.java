package com.my.train.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletResponse;
import java.text.MessageFormat;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * @author Wtq
 * @date 2019/11/4 - 14:43
 */
public enum MyErrorCode {
    //定义异常号码
    UNKONWN_ERROR("999999"),

    MODEL_CLONE_NOT_SUPPORTED("000002"),
    NOT_FOUND_MODEL_BY_ID("000003"),
    NOT_FOUND_MODEL_BY_CODE("000004"),
    FOUND_DUPLICATED_MODEL_WITH_SAME_CODE("000005"),
    NOT_ALLOWED_CHANGE_MODEL_CODE("000006"),
    NOT_ALLOWED_MODEL_CODE_NULL("000006"),
    NOT_DELETE_MODEL_HAS_CHILDREN("000008"),
    ;

    public MyRunTimeException runtimeException(Object... params) {
        return new MyRunTimeException(this, params);
    }

    /**
     * 创建运行时异常.<br>
     *
     * @param cause
     *            异常原因
     * @param params
     *            异常信息参数
     * @return 运行时异常
     */
    public MyRunTimeException runtimeException(Throwable cause, Object... params) {
        return new MyRunTimeException(this, cause, params);
    }

    /*
     * =====================
     * =====================
     */
    private static Logger logger = LoggerFactory.getLogger(MyErrorCode.class);

    private String code;

    private int scCode = HttpServletResponse.SC_INTERNAL_SERVER_ERROR;

    private MyErrorCode(String code) {
        this(code, HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
    }

    private MyErrorCode(String code, int scCode) {
        this.code = code;
        this.scCode = scCode;
    }

    public String getCode() {
        return code;
    }

    public int getScCode() {
        return scCode;
    }

    public String getMessage(Object... params) {
        String message = getMessage(Locale.getDefault(), params);
        int idx = message.indexOf("::");
        return idx > 0 ? message.substring(idx + 2).trim() : message.trim();
    }

    public String getError(Object... params) {
        String message = getMessage(Locale.getDefault(), params);
        int idx = message.indexOf("::");
        return idx > 0 ? message.substring(0, idx).trim() : message.trim();
    }

    private String getMessage(Locale locale, Object... params) {
        String message = "NO MESSAGE for code '" + name() + "'!!!";
        if (locale == null) {
            locale = Locale.getDefault();
        }
        try {
            ResourceBundle bundle = ResourceBundle.getBundle("i18n.exception.Messages", locale);
            message = bundle.getString(this.name());//通过errorCode获取相应的国际化信息
        } catch (Throwable e) {
            logger.error(e.getMessage(), e);
        }
        try {
            return new MessageFormat(message).format(params);
        } catch (Throwable e) {
            logger.error(e.getMessage(), e);
            return message;
        }
    }
}
