package kerntaak2;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;

public class PasswordDAO implements Serializable {

	private Connection conn = null;
	private PreparedStatement ptmt = null;
	private ResultSet resultset = null;

	public PasswordDAO() {

	}

	private Connection getConnection() throws SQLException {
		Connection conn = null;
		conn = ConnectionFactory.getInstance().getConnection();
		return conn;
	}

	public Password findPassword(String sellerEmail) {
		Password password = null;
		int rowcount = 0;
		try {
			String query = "select * from passwords where emailAddress=?";
			conn = getConnection();
			ptmt = conn.prepareStatement(query);
			ptmt.setString(1, sellerEmail);
			resultset = ptmt.executeQuery();
			if (resultset.last()) {
				rowcount = resultset.getRow();
			}
			if (rowcount == 0) {
				return password;
			} else {
				resultset.first();
				password = new Password();
				password.setEmailAddress(resultset.getString(2));
				password.setPassword(resultset.getString(1));
			}

		}

		catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (ptmt != null) {
					ptmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return password;

	}
}
