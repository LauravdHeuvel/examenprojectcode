package kerntaak2;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;

public class SellerDAO implements Serializable {

	private Connection conn = null;
	private PreparedStatement ptmt = null;
	private ResultSet resultset = null;

	public SellerDAO() {

	}

	private Connection getConnection() throws SQLException {
		Connection conn = null;
		conn = ConnectionFactory.getInstance().getConnection();
		return conn;
	}

	public Seller findSeller(String sellerEmail) {
		Seller seller = null;
		int rowcount = 0;
		try {
			String query = "select * from seller where emailAddress=?";
			conn = getConnection();
			ptmt = conn.prepareStatement(query);
			ptmt.setString(1, sellerEmail);
			resultset = ptmt.executeQuery();
			if (resultset.last()) {
				rowcount = resultset.getRow();
			}
			if (rowcount == 0) {
				return seller;
			} else {
				resultset.first();
				seller = new Seller();
				seller.setEmailAddress(resultset.getString(4));
				seller.setFirstName(resultset.getString(1));
				seller.setLastName(resultset.getString(2));
				seller.setBlocked(resultset.getInt(3));
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

		return seller;

	}

	public List<Seller> findAllBlockedSellers() {
		List<Seller> blockedSellers = new ArrayList<Seller>();
		Seller seller = null;
		try {
			final String query = "select * from seller where blocked=1";
			conn = getConnection();
			ptmt = conn.prepareStatement(query);
			resultset = ptmt.executeQuery();
			while (resultset.next()) {
				seller = new Seller();
				seller.setFirstName(resultset.getString(1));
				seller.setLastName(resultset.getString(2));
				seller.setBlocked(resultset.getInt(3));
				seller.setEmailAddress(resultset.getString(4));

				blockedSellers.add(seller);
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
		return blockedSellers;
	}

	public void updateBlocked(Seller seller) {
		String query = "update seller set firstName=?, lastName=?,blocked=? where emailAddress=?";
		try {
			conn = getConnection();
			ptmt = conn.prepareStatement(query);
			ptmt.setString(1, seller.getFirstName());
			ptmt.setString(2, seller.getLastName());
			ptmt.setInt(3, seller.getBlocked());
			ptmt.setString(4, seller.getEmailAddress());
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
