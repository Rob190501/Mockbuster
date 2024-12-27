package model.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collection;

import javax.sql.DataSource;

import control.exceptions.DAOException;
import model.Movie;
import model.Order;
import model.RentedMovie;

public class RentedMovieDAO implements DAOInterface<RentedMovie> {

	private DataSource ds = null;
	private static String table = "movie_rental_order";
	
	public RentedMovieDAO(DataSource ds) {
		this.ds = ds;
	}

	@Override
	public void save(RentedMovie bean) throws DAOException {
		String query = "INSERT INTO " + table + " (order_id, movie_id, daily_price, days) VALUES (?, ?, ?, ?)";
		
		try(Connection conn = ds.getConnection(); 
			PreparedStatement pstmt = conn.prepareStatement(query)) {
				pstmt.setInt(1, bean.getOrder().getId());
				pstmt.setInt(2, bean.getId());
				pstmt.setFloat(3, bean.getDailyRentalPrice());
				pstmt.setInt(4, bean.getDays());
				pstmt.executeUpdate();
		} catch (SQLException e) {
			throw new DAOException(e);
		}
	}
	
	public void save(RentedMovie bean, Connection conn) throws DAOException {
		String query = "INSERT INTO " + table + " (order_id, movie_id, daily_price, days) VALUES (?, ?, ?, ?)";
		
		try(PreparedStatement pstmt = conn.prepareStatement(query)) {
				pstmt.setInt(1, bean.getOrder().getId());
				pstmt.setInt(2, bean.getId());
				pstmt.setFloat(3, bean.getDailyRentalPrice());
				pstmt.setInt(4, bean.getDays());
				pstmt.executeUpdate();
		} catch (SQLException e) {
			throw new DAOException(e);
		}
	}

	@Override
	public void delete(int id) throws DAOException {
		
	}

	@Override
	public RentedMovie retrieveByID(int id) throws DAOException {
		return null;
	}
	
	public void retrieveByOrder(Order order) throws DAOException {
		String query = "SELECT * FROM " + table + " " +
				   	   "WHERE order_id = ?";
		MovieDAO movieDAO = new MovieDAO(ds);
		
		try(Connection conn = ds.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(query)) {
			pstmt.setInt(1, order.getId());
			
			try(ResultSet rs = pstmt.executeQuery()) {
				while(rs.next()) {
					Integer movieID = rs.getInt("movie_id");
					Float dailyPrice = rs.getFloat("daily_price");
					Integer days = rs.getInt("days");
					
					Movie movie = movieDAO.retrieveByID(movieID);
					
					RentedMovie rentedMovie = new RentedMovie(movie, order, dailyPrice, days);
					order.addRentedMovie(rentedMovie);
				}
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		}
	}

	@Override
	public Collection<RentedMovie> retrieveAll() throws DAOException {
		return null;
	}

}
