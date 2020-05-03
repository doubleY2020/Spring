package user.dao;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.JUnitCore;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;
import org.springframework.test.context.ContextConfiguration;
import user.domain.User;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.List;

public class UserDaoTest {
    private UserDao dao;

    private User user1;
    private User user2;
    private User user3;

    @Before
    public void setUp(){
        dao = new UserDao();
        DataSource dataSource = new SingleConnectionDataSource("jdbc:mysql://localhost/test?useSSL=false", "root", "root123!", true);
        dao.setDataSource(dataSource);

        this.user1 = new User("jaja", "김디일", "pw");
        this.user2 = new User("mama", "김디이", "pw");
        this.user3 = new User("ejej", "김디삼", "pw");
    }

    @Test(expected = EmptyResultDataAccessException.class)
    public void getUserFailure() throws SQLException {

        dao.deleteAll();
        assertThat(dao.getCount(), is(0));
        dao.get("unknown_id");
    }

    @Test
    public void andAndGet() throws SQLException {

        dao.deleteAll();
        assertThat(dao.getCount(), is(0));

        dao.add(user1);
        dao.add(user2);
        assertThat(dao.getCount(), is(2));

        User userget1 = dao.get(user1.getId());
        assertThat(userget1.getName(), is(user1.getName()));
        assertThat(userget1.getPassword(), is(user1.getPassword()));

        User userget2 = dao.get(user2.getId());
        assertThat(userget2.getName(), is(user2.getName()));
        assertThat(userget2.getPassword(), is(user2.getPassword()));
    }

    @Test
    public void count() throws SQLException {

        dao.deleteAll();
        assertThat(dao.getCount(), is(0));

        dao.add(user1);
        assertThat(dao.getCount(), is(1));
        dao.add(user2);
        assertThat(dao.getCount(), is(2));
        dao.add(user3);
        assertThat(dao.getCount(), is(3));
    }
    @Test
    public void getAll()  {
        dao.deleteAll();

        List<User> users0 = dao.getAll();
        assertThat(users0.size(), is(0));

        dao.add(user1);
        List<User> users1 = dao.getAll();
        assertThat(users1.size(), is(1));
        checkSameUser(user1, users1.get(0));

        dao.add(user2);
        List<User> users2 = dao.getAll();
        assertThat(users2.size(), is(2));
        checkSameUser(user1, users2.get(0));
        checkSameUser(user2, users2.get(1));

        dao.add(user3);
        List<User> users3 = dao.getAll();
        assertThat(users3.size(), is(3));
        checkSameUser(user3, users3.get(0));
        checkSameUser(user1, users3.get(1));
        checkSameUser(user2, users3.get(2));
    }

    private void checkSameUser(User user1, User user2) {
        assertThat(user1.getId(), is(user2.getId()));
        assertThat(user1.getName(), is(user2.getName()));
        assertThat(user1.getPassword(), is(user2.getPassword()));
    }
    public static void main(String[] args) {
        JUnitCore.main("user.dao.UserDaoTest");
    }
}
