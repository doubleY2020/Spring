package springbook.user.domain;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.sql.SQLException;

public class UserDaoConnectionCountingTest {
    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        ApplicationContext context = new AnnotationConfigApplicationContext(CountingDaoFactory.class);

        UserDao dao = context.getBean("userDao", UserDao.class);

        User user = new User();
        user.setId("dbsal");
        user.setName("윤미");
        user.setPassword("yyyyy");

        dao.add(user);

        System.out.println(user.getId() + " 등록 성공 ! ");

        User user2 = dao.get(user.getId());
        System.out.println(user2.getName());
        System.out.println(user.getPassword());
        System.out.println(user2.getId() + " 조회 성공 !");

        CountingConnectionMaker ccm = context.getBean("connectionMaker", CountingConnectionMaker.class);
        System.out.println("Connection counter : " + ccm.getCounter());
        
    }
}
