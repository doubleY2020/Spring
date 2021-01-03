package springbook.learningtest.jdk.proxy;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.aop.framework.ProxyFactoryBean;
import springbook.learningtest.jdk.Hello;
import springbook.learningtest.jdk.HelloTarget;
import springbook.learningtest.jdk.UppercaseHandler;

import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class DynamicProxyTest {
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

        Hello proxiedHello = (Hello) Proxy.newProxyInstance(
                getClass().getClassLoader(),
                new Class[]{Hello.class},
                new UppercaseHandler(new HelloTarget())
        );
        Assert.assertEquals(proxiedHello.sayHello("Toby"), "HELLO TOBY");
        Assert.assertEquals(proxiedHello.sayHi("Toby"), "HI TOBY");
        Assert.assertEquals(proxiedHello.sayThankYou("Toby"), "THANK YOU TOBY");
    }


    @Test
    public void proxyFactoryBean() {
        ProxyFactoryBean pfBean = new ProxyFactoryBean();
        pfBean.setTarget(new HelloTarget());
        pfBean.addAdvice(new UppercaseAdvice());

        Hello proxiedHello = (Hello) pfBean.getObject();

        Assert.assertEquals(proxiedHello.sayHello("Toby"), "HELLO TOBY");
        Assert.assertEquals(proxiedHello.sayHi("Toby"), "HI TOBY");
        Assert.assertEquals(proxiedHello.sayThankYou("Toby"), "THANK YOU TOBY");


    }

    static class UppercaseAdvice implements MethodInterceptor {

        @Override
        public Object invoke(MethodInvocation invocation) throws Throwable {
            String ret = (String) invocation.proceed(); // MethodInvocation은 메소드 정보와 함께 타깃 오브젝트를 알고 있음
            return ret.toUpperCase();
        }
    }

}
