package cn.com.sun.json;

public class Message {
    private String type;
    private String uid;
    private Object data;
    public Message() {
    }

    public Message(String type, String uid, Object data) {
        this.type = type;
        this.uid = uid;
        this.data = data;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
