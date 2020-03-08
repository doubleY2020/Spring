package springbook.user.domain;

public class DaoFactory {
    public UserDao userDao(){
        return new UserDao(connectionMaker());
    }

    private ConnectionMaker connectionMaker() {
        return  new DConnectionMaker();
    }
}