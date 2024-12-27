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
import model.Movie;
import model.User;

public class MovieDAO implements DAOInterface<Movie> {

	private DataSource ds = null;
	private static String table = "movie";
	
	public MovieDAO(DataSource ds) {
		this.ds = ds;
	}

	@Override
	public void save(Movie bean) throws DAOException {
		String query = "INSERT INTO " + table + " " +
				   	   "(title, plot, duration, movie_year, available_licenses, daily_rental_price, purchase_price, poster_path) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
	
		try(Connection conn = ds.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
			pstmt.setString(1, bean.getTitle());
			pstmt.setString(2, bean.getPlot());
			pstmt.setInt(3, bean.getDuration());
			pstmt.setInt(4, bean.getYear());
			pstmt.setInt(5, bean.getAvailableLicenses());
			pstmt.setFloat(6, bean.getDailyRentalPrice());
			pstmt.setFloat(7, bean.getPurchasePrice());
			pstmt.setString(8, bean.getPosterPath());
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
		// TODO Auto-generated method stub
		
	}

	@Override
	public Movie retrieveByID(int id) throws DAOException {
		Movie movie = null;
		String query = "SELECT * " +
				   	   "FROM " + table + " " +
				   	   "WHERE id = ?";
	
		try(Connection conn = ds.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(query);) {
			pstmt.setInt(1, id);
			
			try(ResultSet rs = pstmt.executeQuery()) {
				if(rs.next()) {
					String title = rs.getString("title");
					String plot = rs.getString("plot");
					Integer duration = rs.getInt("duration");
					Integer year = rs.getInt("movie_year");
					Integer availableLicenses = rs.getInt("available_licenses");
					Float dailyRentalPrice = rs.getFloat("daily_rental_price");
					Float purchasePrice = rs.getFloat("purchase_price");
					Boolean isVisible = rs.getBoolean("is_visible");
					String posterPath = rs.getString("poster_path");
					
					movie = new Movie(id, title, plot, duration, year, availableLicenses, dailyRentalPrice, purchasePrice, isVisible, posterPath);
				}
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		}
		
		return movie;
	}
	
	public Collection<Movie> retrieveByTitle(String likeTitle) throws DAOException {
		Movie movie = null;
		String query = "SELECT * " +
				   	   "FROM " + table + " " +
				   	   "WHERE title LIKE ?";
		ArrayList<Movie> movies = new ArrayList<Movie>();
	
		try(Connection conn = ds.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(query);) {
			pstmt.setString(1, "%" + likeTitle + "%");
			
			try(ResultSet rs = pstmt.executeQuery()) {
				while(rs.next()) {
					Integer id = rs.getInt("id");
					String title = rs.getString("title");
					String plot = rs.getString("plot");
					Integer duration = rs.getInt("duration");
					Integer year = rs.getInt("movie_year");
					Integer availableLicenses = rs.getInt("available_licenses");
					Float dailyRentalPrice = rs.getFloat("daily_rental_price");
					Float purchasePrice = rs.getFloat("purchase_price");
					Boolean isVisible = rs.getBoolean("is_visible");
					String posterPath = rs.getString("poster_path");
					
					movies.add(new Movie(id, title, plot, duration, year, availableLicenses, dailyRentalPrice, purchasePrice, isVisible, posterPath));
				}
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		}
		
		return movies;
	}

	@Override
	public Collection<Movie> retrieveAll() throws DAOException {
		ArrayList<Movie> movieList = new ArrayList<Movie>();
		String query = "SELECT * " +
					   "FROM " + table;
		
		try(Connection conn = ds.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(query);
			ResultSet rs = pstmt.executeQuery()) {
			while(rs.next()) {
				int id = rs.getInt("id");
				String title = rs.getString("title");
				String plot = rs.getString("plot");
				Integer duration = rs.getInt("duration");
				Integer year = rs.getInt("movie_year");
				Integer availableLicenses = rs.getInt("available_licenses");
				Float dailyRentalPrice = rs.getFloat("daily_rental_price");
				Float purchasePrice = rs.getFloat("purchase_price");
				Boolean isVisible = rs.getBoolean("is_visible");
				String posterPath = rs.getString("poster_path");
				
				movieList.add(new Movie(id, title, plot, duration, year, availableLicenses, dailyRentalPrice, purchasePrice, isVisible, posterPath));
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		}
		
		return movieList;
	}
	
	public void updateAvailableLicenses(Movie bean, Connection conn) throws DAOException {
		String query = "UPDATE " + table + " SET available_licenses = ? WHERE id = ?";
		
		try(PreparedStatement pstmt = conn.prepareStatement(query)) {
			pstmt.setInt(1, bean.getAvailableLicenses());
			pstmt.setInt(2, bean.getId());
			pstmt.executeUpdate();
		} catch (SQLException e) {
			throw new DAOException(e);
		}
	}
	
	public void updateMovie(Movie bean) throws DAOException {
		String query = "UPDATE " + table + " " +
					   "SET title = ?, plot = ?, duration = ?, movie_year = ?, available_licenses = ?, daily_rental_price = ?, purchase_price = ?, is_visible = ? " +
					   "WHERE id = ?";
		
		try(Connection conn = ds.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(query)) {
			
			pstmt.setString(1, bean.getTitle());
			pstmt.setString(2, bean.getPlot());
			pstmt.setInt(3, bean.getDuration());
			pstmt.setInt(4, bean.getYear());
			pstmt.setInt(5, bean.getAvailableLicenses());
			pstmt.setFloat(6, bean.getDailyRentalPrice());
			pstmt.setFloat(7, bean.getPurchasePrice());
			pstmt.setBoolean(8, bean.isVisible());
			pstmt.setInt(9, bean.getId());
			
			pstmt.executeUpdate();
		} catch (SQLException e) {
			throw new DAOException(e);
		}
	}
}
