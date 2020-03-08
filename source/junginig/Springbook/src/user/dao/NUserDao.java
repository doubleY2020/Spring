package user.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class NUserDao extends UserDao {
	public NUserDao(ConnectionMaker connectionMaker) {
		super(connectionMaker);
	}

	protected Connection getConnection() throws ClassNotFoundException,
			SQLException {
		Class.forName("com.mysql.jdbc.Driver");
		Connection c = DriverManager.getConnection("jdbc:mysql://localhost/springbook?useSSL=false", "root",
				"root123!");
		return c;
	}
}
