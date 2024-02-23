package Entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
        @Table(name = "companies")
public class Company {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "company_id")
    private int id;
    @Column(name = "company_name")
    private String name;
    @Column
    private String address;
    @OneToMany(mappedBy = "company")
    private List <Product> products = new ArrayList<Product>();
    public Company(String name, String address) {
        this.name = name;
        this.address = address;
    }
    public Company(){
    }

    @Override
    public String toString() {
        return "Company{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}
