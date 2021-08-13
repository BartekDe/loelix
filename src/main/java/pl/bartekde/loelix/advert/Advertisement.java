package pl.bartekde.loelix.advert;

import pl.bartekde.loelix.user.User;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "advertisement")
public class Advertisement {

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "name", length = 120)
    private String name;

    @Column(name = "price", precision = 19, scale = 2)
    private BigDecimal price;

    @Column(name = "is_price_negotiable", nullable = false)
    private boolean isPriceNegotiable;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public boolean isPriceNegotiable() {
        return isPriceNegotiable;
    }

    public void setPriceNegotiable(boolean priceNegotiable) {
        isPriceNegotiable = priceNegotiable;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Advertisement{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", isPriceNegotiable=" + isPriceNegotiable +
                ", user=" + user.getId() +
                '}';
    }
}
