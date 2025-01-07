/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

/**
 *
 * @author roberto
 */
@Embeddable
public class OrderedMovieKey implements Serializable {

    @Column(name = "order_id", nullable = false)
    private Integer orderID;

    @Column(name = "movie_id", nullable = false)
    private Integer movieID;

    public OrderedMovieKey() {}

    public OrderedMovieKey(Integer orderId, Integer movieId) {
        this.orderID = orderId;
        this.movieID = movieId;
    }

    public Integer getOrderID() {
        return orderID;
    }

    public void setOrderID(Integer orderID) {
        this.orderID = orderID;
    }

    public Integer getMovieID() {
        return movieID;
    }

    public void setMovieID(Integer movieID) {
        this.movieID = movieID;
    }
}
