package model;

public class RentedMovie extends Movie {

	private static final long serialVersionUID = 1L;
	private Order order;
	private Float dailyPrice;
	private Integer days;
	
	public RentedMovie() {
		super();
	}
	
	public RentedMovie(Movie movie, Order order, Float dailyPrice, Integer days) {
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
		this.dailyPrice = dailyPrice;
		this.days = days;
	}
	
	public RentedMovie(Movie movie, Float dailyPrice, Integer days) {
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
		this.dailyPrice = dailyPrice;
		this.days = days;
	}
	
	public Order getOrder() {
		return order;
	}
	
	public void setOrder(Order order) {
		this.order = order;
	}
	
	public Float getDailyPrice() {
		return dailyPrice;
	}
	
	public void setDailyPrice(Float dailyPrice) {
		this.dailyPrice = dailyPrice;
	}
	
	public Integer getDays() {
		return days;
	}
	
	public void setDays(Integer days) {
		this.days = days;
	}

	
}
