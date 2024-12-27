package model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.sql.DataSource;

import control.exceptions.DAOException;
import model.User;

public class UserDAO implements DAOInterface<User> {

	private DataSource ds = null;
	private static String table = "user";

	public UserDAO(DataSource ds) {
		this.ds = ds;
	}

	@Override
	public void save(User bean) throws DAOException {
		String query = "INSERT INTO " + table + " " +
					   "(email, password, first_name, last_name, billing_address) VALUES (?, ?, ?, ?, ?)";
		
		try(Connection conn = ds.getConnection(); 
			PreparedStatement pstmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
			pstmt.setString(1, bean.getEmail());
			pstmt.setString(2, bean.getPassword());
			pstmt.setString(3, bean.getFirstName());
			pstmt.setString(4, bean.getLastName());
			pstmt.setString(5, bean.getBillingAddress());
			pstmt.executeUpdate();
			
			try(ResultSet rs = pstmt.getGeneratedKeys()) {
				if(rs.next()) {
					bean.setId(rs.getInt(1));
				}
			}
			
		} catch (SQLException e) {
			throw new DAOException(e);
		}
		
		return;
	}

	@Override
	public void delete(int id) throws DAOException {
		return;
	}

	@Override
	public User retrieveByID(int id) throws DAOException {
		User user = null;
		String query = "SELECT * " +
				  	   "FROM " + table + " " +
					   "WHERE id = ?";
		
		try(Connection conn = ds.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(query)) {
			pstmt.setInt(1, id);
			
			try(ResultSet rs = pstmt.executeQuery()) {
			
				if(rs.next()) {
					String email = rs.getString("email");
					String password = rs.getString("password");
					String firstName = rs.getString("first_name");
					String lastName = rs.getString("last_name");
					String billingAddress = rs.getString("billing_address");
					Float credit = rs.getFloat("credit");
					Boolean isAdmin = rs.getBoolean("is_admin"); 
					
					user = new User(id, email, password, firstName, lastName, billingAddress, credit, isAdmin);
				}
			}
			
		} catch (SQLException e) {
			throw new DAOException(e);
		}
		
		return user;
	}
	
	public User retrieveByEmailAndPassword(String email, String password) throws DAOException {
		User user = null;
		String query = "SELECT * " +
					   "FROM " + table + " " +
					   "WHERE email = ? AND password = ?";
		
		try(Connection conn = ds.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(query)) {
			pstmt.setString(1, email);
			pstmt.setString(2, password);
			
			try(ResultSet rs = pstmt.executeQuery()) {
				if (rs.next()) {
					Integer id = rs.getInt("id");
					String firstName = rs.getString("first_name");
					String lastName = rs.getString("last_name");
					String billingAddress = rs.getString("billing_address");
					Float credit = rs.getFloat("credit");
					Boolean isAdmin = rs.getBoolean("is_admin"); 
					
					user = new User(id, email, password, firstName, lastName, billingAddress, credit, isAdmin);
				}
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		}
		
		return user;
	}
	
	public Boolean checkEmailAvailability(String email) throws DAOException {
		Boolean available = Boolean.TRUE;
		String query = "SELECT * " +
					   "FROM " + table + " " +
					   "WHERE email = ?";
		
		try(Connection conn = ds.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(query)) {
			pstmt.setString(1, email);
			
			try(ResultSet rs = pstmt.executeQuery()) {
				if (rs.next()) {
					available = Boolean.FALSE;
				}
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		}
		
		return available;
	}

	@Override
	public Collection<User> retrieveAll() throws DAOException {
		ArrayList<User> usersList = new ArrayList<User>();
		String query = "SELECT * " +
					   "FROM " + table;
		
		try(Connection conn = ds.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(query);
			ResultSet rs = pstmt.executeQuery()) {
			while(rs.next()) {
				int id = rs.getInt("id");
				String email = rs.getString("email");
				String password = rs.getString("password");
				String firstName = rs.getString("first_name");
				String lastName = rs.getString("last_name");
				String billingAddress = rs.getString("billing_address");
				Float credit = rs.getFloat("credit");
				Boolean isAdmin = rs.getBoolean("is_admin"); 
				
				usersList.add(new User(id, email, password, firstName, lastName, billingAddress, credit, isAdmin));
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		}
		
		return usersList;
	}
	
	public void updateCredit(User bean, Connection conn) throws DAOException {
		String query = "UPDATE " + table + " SET credit = ? WHERE id = ?";
		
		try(PreparedStatement pstmt = conn.prepareStatement(query)) {
			pstmt.setFloat(1, bean.getCredit());
			pstmt.setInt(2, bean.getId());
			pstmt.executeUpdate();
		} catch (SQLException e) {
			throw new DAOException(e);
		}
	}
	
	public void updateUser(User bean) throws DAOException {
		String query = "UPDATE " + table + " SET first_name = ?, last_name = ?, billing_address = ?, credit = ? WHERE id = ?";
		
		try(Connection conn = ds.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(query)) {
			
			pstmt.setString(1, bean.getFirstName());
			pstmt.setString(2, bean.getLastName());
			pstmt.setString(3, bean.getBillingAddress());
			pstmt.setFloat(4, bean.getCredit());
			pstmt.setInt(5, bean.getId());
			
			pstmt.executeUpdate();
		} catch (SQLException e) {
			throw new DAOException(e);
		}
	}
	
	public void setAdmin(Integer id, Boolean isAdmin) throws DAOException {
		String query = "UPDATE " + table + " SET is_admin = ? WHERE id = ?";
		
		try(Connection conn = ds.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(query)) {
			
			pstmt.setBoolean(1, isAdmin);
			pstmt.setInt(2, id);
			
			pstmt.executeUpdate();
		} catch (SQLException e) {
			throw new DAOException(e);
		}
	}

}
