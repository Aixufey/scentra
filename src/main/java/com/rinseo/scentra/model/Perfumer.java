package com.rinseo.scentra.model;

import jakarta.persistence.*;

import java.util.Set;

@Entity
public class Perfumer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @ManyToMany
    @JoinTable(
            name = "perfumer_brand",
            joinColumns = @JoinColumn(name = "perfumer_id"),
            inverseJoinColumns = @JoinColumn(name = "brand_id")
    )
    private Set<Brand > brand;
    @ManyToOne
    @JoinColumn(name = "company_id")
    private Company company;
    @ManyToOne
    @JoinColumn(name = "country_id")
    private Country country;

    public Perfumer() {
    }

    public Perfumer(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Brand> getBrand() {
        return brand;
    }

    public void setBrand(Set<Brand> brand) {
        this.brand = brand;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    @Override
    public String toString() {
        return "Perfumer{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
