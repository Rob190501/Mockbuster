package model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;

import javax.sql.DataSource;

import control.exceptions.DAOException;
import model.Movie;
import model.Order;
import model.PurchasedMovie;

public class PurchasedMovieDAO implements DAOInterface<PurchasedMovie> {

	private DataSource ds;
	private static String table = "movie_purchase_order";
	
	public PurchasedMovieDAO(DataSource ds) {
		this.ds = ds;
	}

	@Override
	public void save(PurchasedMovie bean) throws DAOException {
		String query = "INSERT INTO " + table + " (order_id, movie_id, price) VALUES (?, ?, ?)";
		
		try(Connection conn = ds.getConnection(); 
			PreparedStatement pstmt = conn.prepareStatement(query)) {
			pstmt.setInt(1, bean.getOrder().getId());
			pstmt.setInt(2, bean.getId());
			pstmt.setFloat(3, bean.getPurchasePrice());
				
			pstmt.executeUpdate();
		} catch (SQLException e) {
			throw new DAOException(e);
		}
	}
	
	public void save(PurchasedMovie bean, Connection conn) throws DAOException {
		String query = "INSERT INTO " + table + " (order_id, movie_id, price) VALUES (?, ?, ?)";
		
		try(PreparedStatement pstmt = conn.prepareStatement(query)) {
			pstmt.setInt(1, bean.getOrder().getId());
			pstmt.setInt(2, bean.getId());
			pstmt.setFloat(3, bean.getPurchasePrice());
				
			pstmt.executeUpdate();
		} catch (SQLException e) {
			throw new DAOException(e);
		}
	}

	@Override
	public void delete(int id) throws DAOException {
	}

	@Override
	public PurchasedMovie retrieveByID(int id) throws DAOException {
		return null;
	}
	
	public void retrieveByOrder(Order order) throws DAOException {
		String rentQuery = "SELECT * FROM " + table + " " +
				   		   "WHERE order_id = ?";
		MovieDAO movieDAO = new MovieDAO(ds);
		
		try(Connection conn = ds.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(rentQuery)) {
			pstmt.setInt(1, order.getId());
			
			try(ResultSet rs = pstmt.executeQuery()) {
				while(rs.next()) {
					Integer movieID = rs.getInt("movie_id");
					Float price = rs.getFloat("price");
					
					Movie movie = movieDAO.retrieveByID(movieID);
					
					PurchasedMovie purchasedMovie = new PurchasedMovie(movie, order, price);
					order.addPurchasedMovie(purchasedMovie);
				}
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		}
	}

	@Override
	public Collection<PurchasedMovie> retrieveAll() throws DAOException {
		return null;
	}

}
