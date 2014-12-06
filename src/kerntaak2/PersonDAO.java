package kerntaak2;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;

public class PersonDAO implements Serializable {

	private Connection conn = null;
	private PreparedStatement ptmt = null;
	private ResultSet resultset = null;

	public PersonDAO() {

	}

	private Connection getConnection() throws SQLException {
		Connection conn = null;
		conn = ConnectionFactory.getInstance().getConnection();
		return conn;
	}

	public Person findPerson(String email) {
		Person person = null;
		int rowcount = 0;
		try {
			String query = "select * from person where emailAddress=?";
			conn = getConnection();
			ptmt = conn.prepareStatement(query);
			ptmt.setString(1, email);
			resultset = ptmt.executeQuery();
			if (resultset.last()) {
				rowcount = resultset.getRow();
			}
			if (rowcount == 0) {
				return person;
			} else {
				resultset.first();
				person = new Person();
				person.setEmailAddress(resultset.getString(5));
				person.setFirstName(resultset.getString(1));
				person.setLastName(resultset.getString(2));
				person.setBlocked(resultset.getInt(3));
				person.setAdmin(resultset.getInt(4));
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

		return person;

	}

	public void updateBlocked(Person person) {
		String query = "update person set blocked=? where emailAddress=?";
		try {
			conn = getConnection();
			ptmt = conn.prepareStatement(query);
			ptmt.setInt(3, person.getBlocked());
			ptmt.executeUpdate();
		} catch (SQLException e) {
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

	}
}
