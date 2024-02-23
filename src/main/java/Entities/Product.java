package Entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "products_id")
    private int id;
    @Column(name = "product_name")
    private String name;
    @Column
    private int price;
    @ManyToOne
    @JoinColumn(name = "company")
    private Company company;

    public Product(String name, int price, Company company) {
        this.name = name;
        this.price = price;
        this.company = company;
    }

    public Product(){
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", company=" + company +
                '}';
    }
}
