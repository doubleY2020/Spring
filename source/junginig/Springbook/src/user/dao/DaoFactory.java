package user.dao;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DriverManager;

@Configuration // 애플리케이션 컨텍스트 또는 빈 팩토리가 사용할 설정정보라는 표시
public class DaoFactory {
    @Bean // 오브젝트 생성을 담당하는 IoC용 메소드 라는 표시
    public UserDao userDao() {

        UserDao userDao = new UserDao();
        //DataSource타입의 빈을 DI받는 userDao()빈 메소드
        userDao.setDataSource(dataSource());
        return userDao;
    }

    @Bean
    public DataSource dataSource(){ //DataSource타입의 dataSource빈 정의 메소드

        SimpleDriverDataSource dataSource = new SimpleDriverDataSource();

        return dataSource;
    }
}
