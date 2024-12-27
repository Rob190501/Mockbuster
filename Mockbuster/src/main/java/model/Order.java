package model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;


public class Order implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private Integer id;
	private LocalDate date;
	private Float total;
	private User user;
	private Collection<RentedMovie> rentedMovies;
	private Collection<PurchasedMovie> purchasedMovies;
	
	public Order() {
	}
	
	public Order(User user) {
		this.id = -1;
		this.date = LocalDate.now();
		this.total = 0.0f;
		this.user = user;
		this.rentedMovies = new ArrayList<RentedMovie>();
		this.purchasedMovies = new ArrayList<PurchasedMovie>();
	}
	
	public Order(Integer id, LocalDate date, Float total) {
		this.id = id;
		this.date = date;
		this.total = total;
		this.user = null;
		this.rentedMovies = new ArrayList<RentedMovie>();
		this.purchasedMovies = new ArrayList<PurchasedMovie>();
	}
	
	public Order(Integer id, LocalDate date, Float total, User user) {
		this.id = id;
		this.date = date;
		this.total = total;
		this.user = user;
		this.rentedMovies = new ArrayList<RentedMovie>();
		this.purchasedMovies = new ArrayList<PurchasedMovie>();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public Float getTotal() {
		return total;
	}

	public void setTotal(Float total) {
		this.total = total;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Collection<RentedMovie> getRentedMovies() {
		return rentedMovies;
	}
	
	public void setRentedMovies(Collection<RentedMovie> rentedMovies) {
		this.rentedMovies = rentedMovies;
		
		for(RentedMovie movie : this.rentedMovies) {
			movie.setOrder(this);
		}
	}
	
	public void addRentedMovie(RentedMovie movie) {
		movie.setOrder(this);
		this.rentedMovies.add(movie);
	}
	
	public Collection<PurchasedMovie> getPurchasedMovies() {
		return purchasedMovies;
	}
	
	public void setPurchasedMovies(Collection<PurchasedMovie> purchasedMovies) {
		this.purchasedMovies = purchasedMovies;
		
		for(PurchasedMovie movie : this.purchasedMovies) {
			movie.setOrder(this);
		}
	}
	
	public void addPurchasedMovie(PurchasedMovie movie) {
		movie.setOrder(this);
		this.purchasedMovies.add(movie);
	}
}
