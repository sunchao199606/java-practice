package cn.com.sun.concurrent.threadlocal;


public class Computer {
    private ThreadLocal<Object> obj = new ThreadLocal<>();
    private Object object;

    public void setObj(Object obj) {
        this.obj.set(obj);
    }

    public Object getObj() {
        return obj.get();
    }

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }
}
