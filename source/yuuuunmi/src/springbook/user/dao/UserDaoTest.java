package springbook.user.dao;

import org.junit.Before;
import org.junit.Test;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;
import springbook.user.domain.User;

import javax.sql.DataSource;
import java.sql.SQLException;

import static org.junit.Assert.assertEquals;

public class UserDaoTest {

    private UserDao dao;
    private User user1;
    private User user2;
    private User user3;

    @Before
    public void setUp() {

        this.dao = new UserDao();
        DataSource dataSource = new SingleConnectionDataSource("jdbc:mysql://localhost/springbook?serverTimezone=UTC", "spring", "sss123", true);
        dao.setDataSource(dataSource);

        this.user1 = new User("irene", "아이린", "irenenene");
        this.user2 = new User("sana", "사나", "ssssaaa");
        this.user3 = new User("yeji", "예지", "yyyyhea");

    }


    @Test
    public void addAndGet() throws SQLException {

        dao.deleteAll();
        assertEquals(dao.getCount(), 0);

        dao.add(user1);
        dao.add(user2);
        assertEquals(dao.getCount(), 2);


        User userget1 = dao.get(user1.getId());
        assertEquals(userget1.getName(), user1.getName());
        assertEquals(userget1.getPassword(), user1.getPassword());

        User userget2 = dao.get(user2.getId());
        assertEquals(userget2.getName(), user2.getName());
        assertEquals(userget2.getPassword(), user2.getPassword());

    }

    @Test
    public void count() throws SQLException {

        dao.deleteAll();
        assertEquals(dao.getCount(), 0);


        dao.add(user1);
        assertEquals(dao.getCount(), 1);

        dao.add(user2);
        assertEquals(dao.getCount(), 2);

        dao.add(user3);
        assertEquals(dao.getCount(), 3);

    }

    @Test(expected = EmptyResultDataAccessException.class)
    public void getUserFailure() throws SQLException {
        dao.deleteAll();
        assertEquals(dao.getCount(), 0);

        dao.get("unknown_id");
    }


}
