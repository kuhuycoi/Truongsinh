package com.resources.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "HistoryUpranks")
public class HistoryUpranks implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private int id;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "DatetimeCreated", length = 23, insertable = false, updatable = false)
    private Date datetimeCreated;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customerID", updatable = false)
    private Customer customerID;

    @Column(name = "levelRank")
    private Integer levelRank;

    @Column(name = "nameRank")
    private String nameRank;

    public HistoryUpranks() {
    }

    public HistoryUpranks(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDatetimeCreated() {
        return datetimeCreated;
    }

    public void setDatetimeCreated(Date datetimeCreated) {
        this.datetimeCreated = datetimeCreated;
    }

    public Customer getCustomerID() {
        return customerID;
    }

    public void setCustomerID(Customer customerID) {
        this.customerID = customerID;
    }

    public Integer getLevelRank() {
        return levelRank;
    }

    public void setLevelRank(Integer levelRank) {
        this.levelRank = levelRank;
    }

    public String getNameRank() {
        return nameRank;
    }

    public void setNameRank(String nameRank) {
        this.nameRank = nameRank;
    }
}
