package cn.com.sun.j2v8;

import com.eclipsesource.v8.*;
import com.eclipsesource.v8.utils.V8Executor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class J2V8 {
    private V8 runtime;

    @BeforeEach
    public void initRuntime() {
        runtime = V8.createV8Runtime();
    }

    @Test
    public void helloWorld() {
        int result = runtime.executeIntegerScript(""
                + "var hello = 'hello ';\n"
                + "var world = 'world';\n"
                + "hello.concat(world).length;\n");
        System.out.println(result);
        runtime.release();
    }

    @Test
    public void accessJsObject() {
        runtime.executeVoidScript(""
                + "var person = {};\n"
                + "var hockeyTeam = {name : 'WolfPack'};\n"
                + "person.first = 'Ian';\n"
                + "person['last'] = 'Bull';\n"
                + "person.hockeyTeam = hockeyTeam;\n");
        // Access the person object
        // 引用
        V8Object person = runtime.getObject("person");
        V8Object hockeyTeam = person.getObject("hockeyTeam");
        // 赋值
        hockeyTeam.add("captain", person);
        assertTrue(runtime.executeBooleanScript("person === hockeyTeam.captain"));
        System.out.println(hockeyTeam.getString("name"));
        // 数组
        V8Object player1 = new V8Object(runtime).add("name", "John");
        V8Object player2 = new V8Object(runtime).add("name", "Chris");
        V8Array players = new V8Array(runtime).push(player1).push(player2);
        hockeyTeam.add("players", players);
        assertTrue(runtime.executeBooleanScript("person.hockeyTeam.players.length === 2"));
        // 自己创建的对象或者从runtime返回的对象需要手动释放
        player1.release();
        player2.release();
        players.release();
        hockeyTeam.release();
        person.release();
        runtime.release();
    }

    /**
     * 调用js函数
     */
    @Test
    public void callJsFunction() {
        // V8中注册addPlayer函数
        runtime.executeScript("var hockeyTeam = {\n" +
                "     name      : 'WolfPack',\n" +
                "     players   : [],\n" +
                "     addPlayer : function(player) {\n" +
                "                   this.players.push(player);\n" +
                "                   return this.players.length;\n" +
                "     }\n" +
                "}");
        V8Object hockeyTeam = runtime.getObject("hockeyTeam");
        // 利用构造好的入参调用addPlayer函数
        V8Object player1 = new V8Object(runtime).add("name", "John");
        V8Array parameters = new V8Array(runtime).push(player1);
        int size = hockeyTeam.executeIntegerFunction("addPlayer", parameters);
        System.out.println(size);

        player1.release();
        parameters.release();
    }

    /**
     * 调用java函数 调用js函数相当于调用某个java方法
     */
    @Test
    public void javaCallBack() {
        JavaVoidCallback callback = (receiver, parameters) -> {
            if (parameters.length() > 0) {
                for (int index = 0; index < parameters.length(); index++) {
                    Object parameter = parameters.get(index);
                    System.out.println(parameter);
                    if (parameter instanceof Releasable) {
                        ((Releasable) parameter).release();
                    }
                }
            }
        };
        runtime.registerJavaMethod(callback, "print");
        runtime.executeScript("print('hello','world');");
    }

    /**
     * 相当于js函数的实现在java层
     */
    @Test
    public void reflectRegisterMethod() {
        Console console = new Console();
        V8Object v8Console = new V8Object(runtime);
        runtime.add("console", v8Console);

        v8Console.registerJavaMethod(console, "log", "log", new Class<?>[]{String.class, Integer.class});
        v8Console.registerJavaMethod(console, "error", "err", new Class<?>[]{
                String.class, Integer.class
        });
        v8Console.release();
        runtime.executeScript("console.log('hello, world',1000);");
    }

    @Test
    public void v8Executor() throws InterruptedException {
        V8Executor executor = new V8Executor("script to execute...");
        executor.start();
        executor.postMessage(new String[]{});
        executor.join();
        String result = executor.getResult();
    }

    @Test
    public void performance() throws ScriptException {
        int num = 1000000000;
        long javaStart = System.currentTimeMillis();
        long sum = 0;
        for (int i = 0; i < num; i++) {
            sum += i;
        }
        long javaEnd = System.currentTimeMillis();
        long cost = javaEnd - javaStart;
        System.out.println("java cost " + cost + " ms");
        String jsFunction = "function sum(num){\n" +
                "  var start = (new Date()).getTime()\n" +
                "  var sum = 0;\n" +
                "  for (var i = 0;i<num;i++){\n" +
                "    sum+=i;\n" +
                "  }\n" +
                "  var end = (new Date()).getTime()\n" +
                "  \n" +
                "  return end - start;\n" +
                "}";
        runtime.executeScript(jsFunction);
        V8Array parameters = new V8Array(runtime);
        parameters.push(num);
        int c = runtime.executeIntegerFunction("sum", parameters);
        System.out.println("v8 cost " + c + " ms");
        parameters.release();
        runtime.release();
        ScriptEngine engine = new ScriptEngineManager().getEngineByName("nashorn");
        engine.eval("(function sum(){\n" +
                "  var start = (new Date()).getTime()\n" +
                "  var sum = 0;\n" +
                "  for (var i = 0;i<1000000000;i++){\n" +
                "    sum+=i;\n" +
                "  }\n" +
                "  var end = (new Date()).getTime()\n" +
                "  print(end - start)\n" +
                "})()");
    }

}

class Console {
    public void log(final String message, final Integer integer) {
        System.out.println("[INFO] " + message + integer);
    }

    public void error(final String message, final Integer integer) {
        System.out.println("[ERROR] " + message);
    }
}