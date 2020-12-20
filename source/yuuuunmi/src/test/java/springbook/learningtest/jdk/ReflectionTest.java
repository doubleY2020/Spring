package springbook.learningtest.jdk;

import org.junit.Assert;
import org.junit.Test;

import java.lang.reflect.Method;

public class ReflectionTest {
    @Test
    public void invokeMethod() throws Exception {
        String name = "String";

        // length()
        Assert.assertEquals(name.length(), 6);

        Method lengthMethod = String.class.getMethod("length");
        Assert.assertEquals(lengthMethod.invoke(name), 6);

        // charAt()
        Assert.assertEquals(name.charAt(0), 'S');

        Method charAtMethod = String.class.getMethod("charAt", int.class);
        Assert.assertEquals(charAtMethod.invoke(name, 0), 'S');

    }

    @Test
    public void simpleProxy() {
        Hello hello = new HelloTarget();
        Assert.assertEquals(hello.sayHello("Toby"), "Hello Toby");
        Assert.assertEquals(hello.sayHi("Toby"), "Hi Toby");
        Assert.assertEquals(hello.sayThankYou("Toby"), "Thank You Toby");

        Hello proxiedHello = new HelloUppercase(new HelloTarget());
        Assert.assertEquals(proxiedHello.sayHello("Toby"), "HELLO TOBY");
        Assert.assertEquals(proxiedHello.sayHi("Toby"), "HI TOBY");
        Assert.assertEquals(proxiedHello.sayThankYou("Toby"), "THANK YOU TOBY");
    }

}
