package user.service;


import org.springframework.mail.SimpleMailMessage;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import user.dao.UserDao;
import user.domain.Level;
import user.domain.User;

import java.util.List;


public class UserService {
    public static final int MIN_LOGCOUNT_FOR_SILVER = 50;
    public static final int MIN_RECCOMEND_FOR_GOLD = 30;

    private PlatformTransactionManager transactionManager;
    private MailSender mailSender;
    UserDao userDao;

    public void setUserDao(UserDao userDao){
        this.userDao = userDao;
    }
    public void setTransactionManager(PlatformTransactionManager transactionManager){
        this.transactionManager = transactionManager;
    }
    public void setMailSender(MailSender ms){
        this.mailSender = ms;
    }

    public void upgradeLevels() {
        TransactionStatus status = transactionManager.getTransaction(new DefaultTransactionDefinition());
        //트랜잭션 시작
    try{
        List<User> users = userDao.getAll();
        for(User user : users) {
            if (canUpgradeLevel(user)) {
                upgradeLevel(user);
            }
        }
       transactionManager.commit(status);
    } catch (Exception e){
        transactionManager.rollback(status);
        throw e;
    }

    }
    private boolean canUpgradeLevel(User user) {
        Level currentLevel = user.getLevel();
        switch(currentLevel) {
            case BASIC: return (user.getLogin() >= MIN_LOGCOUNT_FOR_SILVER);
            case SILVER: return (user.getRecommend() >= MIN_RECCOMEND_FOR_GOLD);
            case GOLD: return false;
            default: throw new IllegalArgumentException("Unknown Level: " + currentLevel);
        }
    }

    protected void upgradeLevel(User user){
       user.upgradeLevel();
       userDao.update(user);
       sendUpgradeEmail(user);
    }

    private void sendUpgradeEmail(User user) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(user.getEmail());
        mailMessage.setFrom("useradmin@ksug.org");
        mailMessage.setSubject("Upgrade 안내");
        mailMessage.setText("등급 상승 : "+user.getLevel().name());

        mailSender.send(mailMessage);
    }

    public void add(User user) {
        if (user.getLevel() == null) user.setLevel(Level.BASIC);
        userDao.add(user);
    }

}
