package user.dao;

import java.sql.SQLException;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import user.domain.User;

public class UserDaoTest {
	public static void main(String[] args) throws ClassNotFoundException, SQLException {

        ApplicationContext context = new AnnotationConfigApplicationContext(DaoFactory.class);
		UserDao dao3 = context.getBean("userDao", UserDao.class);
		UserDao dao4 = context.getBean("userDao", UserDao.class);

        System.out.println(dao3); //user.dao.UserDao@48f278eb
        System.out.println(dao4); //user.dao.UserDao@48f278eb
	}
}
