package kerntaak2;

import java.io.Serializable;
import java.sql.Connection;
import java.util.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;

public class LoginDAO implements Serializable {

	private Connection conn = null;
	private PreparedStatement ptmt = null;
	private ResultSet resultset = null;

	public LoginDAO() {

	}

	private Connection getConnection() throws SQLException {
		Connection conn = null;
		conn = ConnectionFactory.getInstance().getConnection();
		return conn;
	}

	public void addLogin(Login login) {

	    try {
	      String query = "insert into logindata (emailAddress, firstName, lastName,date) values (?,?,?,?)";
	      conn = getConnection();
	      ptmt = conn.prepareStatement(query);
	      ptmt.setString(1, login.getEmailAddress());
	      ptmt.setString(2, login.getFirstName());
	      ptmt.setString(3, login.getLastName());
	      ptmt.setDate(4, login.getDate());
	     
	      ptmt.executeUpdate();
	    } catch (MySQLIntegrityConstraintViolationException e) {
	      System.out.println("Duplicate key" + login.getLoginNumber());
	      e.printStackTrace();
	    } catch (SQLException e) {
	      e.printStackTrace();
	    }finally{     
	        try {
	          if (ptmt!= null){
	          ptmt.close();
	          }
	          if (conn!= null){
	            conn.close();
	          }
	        } catch (SQLException e) {
	          e.printStackTrace();
	        }
	      }
	    }
	
	public List<Login> allLogins() {
		List<Login> loginFile = new ArrayList<Login>();
		Login login = null;
		try {
			final String query = "select * from logindata";
			conn = getConnection();
			ptmt = conn.prepareStatement(query);
			resultset = ptmt.executeQuery();
			while (resultset.next()) {
				login = new Login();
				login.setLoginNumber(resultset.getInt(1));
				login.setEmailAddress(resultset.getString(2));
				login.setFirstName(resultset.getString(3));
				login.setLastName(resultset.getString(4));
				login.setDate(resultset.getDate(5));
				

				loginFile.add(login);
			}

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
		return loginFile;
	}
}
