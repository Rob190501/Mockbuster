package model;

public class PurchasedMovie extends Movie {

	private static final long serialVersionUID = 1L;
	private Order order;
	private Float price;
	
	public PurchasedMovie() {
		super();
	}
	
	public PurchasedMovie(Movie movie, Order order, Float price) {
		super(movie.getId(),
			  movie.getTitle(),
		      movie.getPlot(), 
		      movie.getDuration(), 
		      movie.getYear(), 
		      movie.getAvailableLicenses(), 
		      movie.getDailyRentalPrice(), 
		      movie.getPurchasePrice(),
		      movie.isVisible(), 
		      movie.getPosterPath());
		this.order = order;
		this.price = price;
	}
	
	public PurchasedMovie(Movie movie, Float price) {
		super(movie.getId(),
			  movie.getTitle(),
		      movie.getPlot(), 
		      movie.getDuration(), 
		      movie.getYear(), 
		      movie.getAvailableLicenses(), 
		      movie.getDailyRentalPrice(), 
		      movie.getPurchasePrice(),
		      movie.isVisible(), 
		      movie.getPosterPath());
		this.order = null;
		this.price = price;
	}

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	public Float getPrice() {
		return price;
	}

	public void setPrice(Float price) {
		this.price = price;
	}
}
