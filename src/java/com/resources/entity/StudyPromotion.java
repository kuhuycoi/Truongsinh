package com.resources.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "[studyPromotion]")
public class StudyPromotion implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private int id;
    @Column(name = "Name", updatable = false)
    private String name;    
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "[CustomerStart]", updatable = false)
    private Customer customerStart; 
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "[CustomerEnd]", updatable = false)
    private Customer customerEnd;
    
    @Column(name = "TotalMember", updatable = false)
    private Integer totalMember;
    
    @Column(name = "Moneyperone", updatable = false)
    private BigDecimal moneyperone;
        
    @Column(name = "Moneypercircle")
    private BigDecimal moneypercircle;
    
    @Column(name = "isActive", updatable = false)
    private Boolean isActive;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Customer getCustomerStart() {
        return customerStart;
    }

    public void setCustomerStart(Customer customerStart) {
        this.customerStart = customerStart;
    }

    public Customer getCustomerEnd() {
        return customerEnd;
    }

    public void setCustomerEnd(Customer customerEnd) {
        this.customerEnd = customerEnd;
    }

    public Integer getTotalMember() {
        return totalMember;
    }

    public void setTotalMember(Integer totalMember) {
        this.totalMember = totalMember;
    }

    public BigDecimal getMoneyperone() {
        return moneyperone;
    }

    public void setMoneyperone(BigDecimal moneyperone) {
        this.moneyperone = moneyperone;
    }

    public BigDecimal getMoneypercircle() {
        return moneypercircle;
    }

    public void setMoneypercircle(BigDecimal moneypercircle) {
        this.moneypercircle = moneypercircle;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }
}
