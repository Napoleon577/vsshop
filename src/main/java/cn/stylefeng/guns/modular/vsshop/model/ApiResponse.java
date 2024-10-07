package cn.stylefeng.guns.modular.vsshop.model;

public class ApiResponse {
    private final static String successStr="SUCCESS";
    private final static String failStr="Fail";
    //1.成功，-1，失败
    private int status;
    private String msg;
    private Object result;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
    public static ApiResponse success(Object result){
        ApiResponse apiResponse=new ApiResponse();
        apiResponse.setMsg(successStr);
        apiResponse.setStatus(1);
        apiResponse.setResult(result);
        return apiResponse;
    };
    public static ApiResponse fail(String msg){
        ApiResponse apiResponse=new ApiResponse();
        apiResponse.setMsg(msg);
        apiResponse.setStatus(-1);
        apiResponse.setResult(null);
        return apiResponse;
    };

}
