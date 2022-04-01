package com.gll.shop.common.beans;

import com.gll.shop.common.enums.ENMsgCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
public class ResultContext<T> implements Serializable {

    private static final long serialVersionUID = 5856432252782588227L;

    /**
     * 状态码，对应枚举ENMsgCode
     * 0：请求操作成功
     * 1：业务错误
     * 2：系统异常
     * 3：未登录
     * 4：session超时
     * 5：没有权限
     */
    private String code;

    /**
     * 提示信息
     */
    private String info;

    /**
     * 用于区分错误细节的值，比如登录失败的多种情况(账号不存在，密码错误等)
     */
    private String value;

    /**
     * 成功标识
     */
    private Boolean success;

    /**
     * 需要返回的数据
     */
    private T data;
    /**
     * 对于列表操作，执行成功的记录数量
     */
    private long successNum;

    public ResultContext(T data) {
        this();
        setData(data);
    }

    private ResultContext(String code, String info, String value, Boolean success) {
        this.code = code;
        this.info = info;
        this.value = value;
        this.success = success;
        this.successNum = 0;
    }

    private ResultContext(String code, String info, Boolean success) {
        this.code = code;
        this.info = info;
        this.success = success;
        this.successNum = 0;
    }

    public ResultContext(String code, String info, Boolean success, T data) {
        this.code = code;
        this.info = info;
        this.success = success;
        this.data = data;
        this.successNum = 0;
    }

    public static <T> ResultContext<T> success(String info) {
        return new ResultContext<>(ENMsgCode.SUCCESS.getValue(), info, true);
    }

    public static <T> ResultContext<T> businessFail(String info, String value) {
        return new ResultContext<>(ENMsgCode.BUSINESS_ERROR.getValue(), info, value, false);
    }

    public static <T> ResultContext<T> businessFail(String info) {
        return businessFail(info, null);
    }

    public static <T> ResultContext<T> notLogin(String info) {
        return new ResultContext<>(ENMsgCode.NOT_LOG_IN.getValue(), info, true);
    }

    public static <T> ResultContext<T> systemException(String info, String value) {
        return new ResultContext<>(ENMsgCode.SYSTEM_EXCEPTION.getValue(), info, value, false);
    }


    public static <T> ResultContext<T> buildSuccess(String info, T data) {
        return new ResultContext<>(ENMsgCode.SUCCESS.getValue(), info, true, data);
    }

    /**
     * 对于批量操作数据的接口，需要能够提示操作成功多少条以及失败多少条记录
     * 因此需要设置成功操作数量
     *
     * @param info       提示信息
     * @param successNum 成功操作数据
     * @param <T>        数据类型
     * @return 提示操作结果
     */
    public static <T> ResultContext<T> buildSuccessNum(String info, long successNum) {
        ResultContext<T> resultContext = new ResultContext<>(ENMsgCode.SUCCESS.getValue(), info, true, null);
        resultContext.setSuccessNum(successNum);
        return resultContext;
    }


    /**
     * 对于批量操作数据的接口，需要能够提示操作成功多少条以及失败多少条记录
     * 因此需要设置成功操作数量,本方法固定返回成功1条
     *
     * @param info 提示信息
     * @param <T>  数据类型
     * @return 提示操作结果
     */
    public static <T> ResultContext<T> buildSuccessOne(String info) {
        return buildSuccessNum(info, 1);
    }


}
