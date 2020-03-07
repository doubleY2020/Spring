package springbook.user.domain;

import java.sql.SQLException;

public class UserDaoTest {
    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        ConnectionMaker connectionMaker = new DConnectionMaker();
        //ConnectionMaker connectionMaker = new NConnectionMaker();

        UserDao dao = new UserDao(connectionMaker);

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

    }
}
