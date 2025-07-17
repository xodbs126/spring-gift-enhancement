package gift.entity;

import gift.exception.itemException.ItemImageurlException;
import gift.exception.itemException.ItemNameException;
import gift.exception.itemException.ItemPriceException;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "items") // 테이블명이 items인 경우 명시
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(nullable = false, length = 255)
    private String name;

    @Column(nullable = false)
    @Min(0)
    private Integer price;

    @Column(name = "image_url", length = 255, nullable = true)
    private String imageUrl;

    protected Item() {

    }

    public Item(String name, Integer price, String imageUrl) {
        this.name = name;
        this.price = price;
        this.imageUrl = imageUrl;
    }

    public Item(Item item) {
        this.id = item.getId();
        this.name = item.getName();
        this.price = item.getPrice();
        this.imageUrl = item.getImageUrl();
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Integer getPrice() {
        return price;
    }


    public String getImageUrl() {
        return imageUrl;
    }


    public boolean isValid(String name, Integer price) {
        boolean nameMatches = (name == null || this.getName().equals(name));
        boolean priceMatches = (price == null || this.getPrice().equals(price));

        return nameMatches && priceMatches;
    }

    public void update(String name, Integer price, String imageUrl) {

        if (name == null) {
            throw new ItemNameException();
        }

        if (price == null) {
            throw new ItemPriceException();
        }

        if (imageUrl == null) {
            throw new ItemImageurlException();
        }

        this.name = name;
        this.price = price;
        this.imageUrl = imageUrl;
    }
}