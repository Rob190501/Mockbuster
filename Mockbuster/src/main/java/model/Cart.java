package model;

import jakarta.persistence.Entity;
import java.util.ArrayList;
import java.util.Collection;
@Entity
public class Cart {

	private Collection<RentedMovie> rentedMovies;
	private Collection<PurchasedMovie> purchasedMovies;
	
	public Cart() {
		this.rentedMovies  = new ArrayList<RentedMovie>();
		this.purchasedMovies = new ArrayList<PurchasedMovie>();
	}
	
	public Boolean rentsContains(Integer movieID) {
		for(RentedMovie movie : rentedMovies) {
			if(movie.getId().equals(movieID)) {
				return Boolean.TRUE;
			}
		}
		return Boolean.FALSE;
	}
	
	public Boolean purchasesContains(Integer movieID) {
		for(PurchasedMovie movie : purchasedMovies) {
			if(movie.getId().equals(movieID)) {
				return Boolean.TRUE;
			}
		}
		return Boolean.FALSE;
	}
	
	public void removeFromCart(Integer movieID) {
		for(PurchasedMovie movie : purchasedMovies) {
			if(movie.getId().equals(movieID)) {
				purchasedMovies.remove(movie);
				return;
			}
		}
		for(RentedMovie movie : rentedMovies) {
			if(movie.getId().equals(movieID)) {
				rentedMovies.remove(movie);
				return;
			}
		}
	}
	
	public Float getTotal() {
		Float total = 0.0f;
		
		for(PurchasedMovie movie : purchasedMovies) {
			total += movie.getPurchasePrice();
		}
		
		for(RentedMovie movie : rentedMovies) {
			total += movie.getDailyRentalPrice() * movie.getDays();
		}
		
		return total;
	}
	
	public void empty() {
		rentedMovies.clear();
		purchasedMovies.clear();
	}
	
	public Boolean isEmpty() {
		return rentedMovies.isEmpty() && purchasedMovies.isEmpty();
	}
	
	public void decreaseRentDays(Integer movieID) {
		for(RentedMovie movie : rentedMovies) {
			if(movie.getId().equals(movieID)) {
				if(movie.getDays() > 1) {
					movie.setDays(movie.getDays()-1);
				}
				return;
			}
		}
	}
	
	public void increaseRentDays(Integer movieID) {
		for(RentedMovie movie : rentedMovies) {
			if(movie.getId().equals(movieID)) {
				if(movie.getDays() < movie.getAvailableLicenses()) {
					movie.setDays(movie.getDays()+1);
				}
				return;
			}
		}
	}

	public Collection<RentedMovie> getRentedMovies() {
		return rentedMovies;
	}

	public void setRentedMovies(Collection<RentedMovie> rentedMovies) {
		this.rentedMovies = rentedMovies;
	}
	
	public void addRentedMovie(RentedMovie rentedMovie) {
		this.rentedMovies.add(rentedMovie);
	}

	public Collection<PurchasedMovie> getPurchasedMovies() {
		return purchasedMovies;
	}

	public void setPurchasedMovies(Collection<PurchasedMovie> purchasedMovies) {
		this.purchasedMovies = purchasedMovies;
	}
	
	public void addPurchasedMovie(PurchasedMovie purchasedMovie) {
		this.purchasedMovies.add(purchasedMovie);
	}
}
