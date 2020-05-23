package user.service;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertThat;
import static user.service.UserService.MIN_LOGCOUNT_FOR_SILVER;
import static user.service.UserService.MIN_RECCOMEND_FOR_GOLD;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import user.dao.UserDao;
import user.domain.Level;
import user.domain.User;

import java.util.Arrays;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="/test-applicationContext.xml")
public class UserServiceTest {

    @Autowired
    UserService userService;
    @Autowired
    UserDao userDao;

    List<User> users;

    @Before
    public void setUp() {
        users = Arrays.asList(
                new User("tobykim", "김토비", "pw1", Level.BASIC, MIN_LOGCOUNT_FOR_SILVER-1, 0),
                new User("tobyU", "유토비", "pw2", Level.SILVER, MIN_LOGCOUNT_FOR_SILVER, 0),
                new User("tobyGo", "고토비", "pw3", Level.SILVER, 60, MIN_RECCOMEND_FOR_GOLD-1),
                new User("tobySeo", "서토비", "pw4", Level.GOLD, 60, MIN_RECCOMEND_FOR_GOLD),
                new User("tobyChoi", "최토비", "pw5", Level.GOLD, 100, Integer.MAX_VALUE);
        );
    }

    @Test
    public void upgradeLevels() {
        userDao.deleteAll();
        for(User user : users) userDao.add(user);

        userService.upgradeLevels();

        checkLevelUpgraded(users.get(0), false);
        checkLevelUpgraded(users.get(1), true);
        checkLevelUpgraded(users.get(2), false);
        checkLevelUpgraded(users.get(3), true);
        checkLevelUpgraded(users.get(4), false);
    }

    private void checkLevelUpgraded(User user, boolean upgraded) {
        User userUpdate = userDao.get(user.getId());
        if (upgraded) {
            assertThat(userUpdate.getLevel(), is(user.getLevel().nextLevel()));
        }
        else {
            assertThat(userUpdate.getLevel(), is(user.getLevel()));
        }
    }
    @Test
    public void bean(){
        assertThat(this.userService, is(notNullValue()));
    }

    @Test
    public void add() {
        userDao.deleteAll();

        User userWithLevel = users.get(4);	  // GOLD 레벨
        User userWithoutLevel = users.get(0);
        userWithoutLevel.setLevel(null);

        userService.add(userWithLevel);
        userService.add(userWithoutLevel);

        User userWithLevelRead = userDao.get(userWithLevel.getId());
        User userWithoutLevelRead = userDao.get(userWithoutLevel.getId());

        assertThat(userWithLevelRead.getLevel(), is(userWithLevel.getLevel()));
        assertThat(userWithoutLevelRead.getLevel(), is(Level.BASIC));
    }
}
