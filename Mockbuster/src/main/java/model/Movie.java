package model;

import java.io.Serializable;

public class Movie implements Serializable{
	
	private static final long serialVersionUID = 1L;
	Integer id;
	String title;
    String plot;
    Integer duration;
    Integer year;
    Integer availableLicenses;
    Float dailyRentalPrice;
    Float purchasePrice;
    Boolean isVisible;
	String posterPath;
	
	
	
	public Movie() {
	}

	public Movie(Integer id, String title, String plot, Integer duration, Integer year, Integer availableLicenses,
			Float dailyRentalPrice, Float purchasePrice, Boolean isVisible, String posterPath) {
		this.id = id;
		this.title = title;
		this.plot = plot;
		this.duration = duration;
		this.year = year;
		this.availableLicenses = availableLicenses;
		this.dailyRentalPrice = dailyRentalPrice;
		this.purchasePrice = purchasePrice;
		this.isVisible = isVisible;
		this.posterPath = posterPath;
	}

	public Movie(String title, String plot, Integer duration, Integer year, Integer availableLicenses,
			Float dailyRentalPrice, Float purchasePrice, String posterPath) {
		this.id = -1;
		this.title = title;
		this.plot = plot;
		this.duration = duration;
		this.year = year;
		this.availableLicenses = availableLicenses;
		this.dailyRentalPrice = dailyRentalPrice;
		this.purchasePrice = purchasePrice;
		this.isVisible = Boolean.TRUE;
		this.posterPath = posterPath;
	}
	
	public Movie(Integer id, String title, String plot, Integer duration, Integer year, Integer availableLicenses,
			Float dailyRentalPrice, Float purchasePrice, Boolean isVisible) {
		this.id = id;
		this.title = title;
		this.plot = plot;
		this.duration = duration;
		this.year = year;
		this.availableLicenses = availableLicenses;
		this.dailyRentalPrice = dailyRentalPrice;
		this.purchasePrice = purchasePrice;
		this.isVisible = isVisible;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getPlot() {
		return plot;
	}

	public void setPlot(String plot) {
		this.plot = plot;
	}

	public Integer getDuration() {
		return duration;
	}

	public void setDuration(Integer duration) {
		this.duration = duration;
	}

	public Integer getYear() {
		return year;
	}

	public void setYear(Integer year) {
		this.year = year;
	}

	public Integer getAvailableLicenses() {
		return availableLicenses;
	}

	public void setAvailableLicenses(Integer availableLicenses) {
		this.availableLicenses = availableLicenses;
	}

	public Float getDailyRentalPrice() {
		return dailyRentalPrice;
	}

	public void setDailyRentalPrice(Float dailyRentalPrice) {
		this.dailyRentalPrice = dailyRentalPrice;
	}

	public Float getPurchasePrice() {
		return purchasePrice;
	}

	public void setPurchasePrice(Float purchasePrice) {
		this.purchasePrice = purchasePrice;
	}
	
	public Boolean isVisible() {
		return isVisible;
	}

	public void setVisible(Boolean isVisible) {
		this.isVisible = isVisible;
	}

	public String getPosterPath() {
		return posterPath;
	}

	public void setPosterPath(String posterPath) {
		this.posterPath = posterPath;
	}
}
