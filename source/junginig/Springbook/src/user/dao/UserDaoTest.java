package user.dao;

import java.sql.SQLException;

import user.domain.User;

public class UserDaoTest {
	public static void main(String[] args) throws ClassNotFoundException, SQLException {

		// UserDao가 사용할 ConnecionMaker 구현 클래스를 결정하고, 오브젝트를 만든다
		ConnectionMaker connectionMaker = new DConnectionMaker();

		// 1. UserDao 생성
        // 2. 사용할 ConnectionMaker타입의 오브젝트 제공
        // 결국 두 오브젝트 사이의 의존관계 설정 효과
		UserDao dao = new UserDao(connectionMaker);

		User user = new User();
		user.setId("hi");
		user.setName("hh");
		user.setPassword("hhhh");

		dao.add(user);

        System.out.println(user.getId() + " 등록 성공");

        User user2 = dao.get(user.getId());
        System.out.println(user2.getName());
        System.out.println(user2.getPassword());

        System.out.println(user2.getId() + " 조회 성공");
	}
}
