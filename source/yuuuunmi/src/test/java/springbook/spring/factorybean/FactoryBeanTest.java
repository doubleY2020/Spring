package springbook.spring.factorybean;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import springbook.learningtest.spring.factorybean.Message;
import springbook.learningtest.spring.factorybean.MessageFactoryBean;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "/factoryBeanTestContext.xml")
public class FactoryBeanTest {
    @Autowired
    ApplicationContext context;

    @Test
    public void getMessageFromFactoryBean() {
        Object message = context.getBean("message");
        Assert.assertEquals(message.getClass(), Message.class);
        Assert.assertEquals(((Message) message).getText(), "Factory Bean");
    }

    @Test
    public void getFactoryBean() throws Exception {
        Object factory = context.getBean("&message");
        Assert.assertEquals(factory.getClass(), MessageFactoryBean.class);
    }


}
