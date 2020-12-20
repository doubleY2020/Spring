package springbook.user.service;

import org.junit.Before;
import org.junit.Test;
import springbook.user.domain.Level;
import springbook.user.domain.User;

import static org.junit.Assert.assertEquals;

public class UserTest {
    User user;

    @Before
    public void setUP() {
        user = new User();
    }

    @Test
    public void upgradeLevel() {
        Level[] levels = Level.values();
        for (Level level : levels) {
            if (level.nextLevel() == null) continue;
            user.setLevel(level);
            user.upgradeLevel();
            assertEquals(level.nextLevel(), user.getLevel());
        }
    }

    @Test(expected = IllegalStateException.class)
    public void cannotUpgradeLevel() {
        Level[] levels = Level.values();
        for (Level level : levels) {
            if (level.nextLevel() != null) continue;
            user.setLevel(level);
            user.upgradeLevel();
        }
    }

}
