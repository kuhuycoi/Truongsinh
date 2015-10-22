package com.resources.entity;

import java.io.Serializable;
import java.math.BigDecimal;
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
@Table(name = "HistoryPayment")
public class HistoryPayment implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer id;
    
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "[DatetimeCreated]", length = 23, insertable = false,updatable = false)
    private Date datetimeCreated;
    
    @Column(name = "TotalMoney")
    private BigDecimal totalMoney;
    
    @Column(name = "PercentPay")
    private Integer percentPay;
    
    @Column(name = "TotalPay")
    private BigDecimal totalPay;
    
    @Column(name = "isPay")
    private Boolean isPay;
    
    @Column(name = "isDelete")
    private Boolean isDelete;
    
    @Column(name = "Descrip")
    private String descrip;
    
    @Column(name = "CodeBank")
    private String codeBank;
    
    @Column(name = "chuTK")
    private String chuTK;    
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Admcheck")
    private Admin admcheck;    
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ProvincialAgenciesID")
    private ProvincialAgencies provincialAgenciesID;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CustomerID")
    private Customer customerID;

    public HistoryPayment(Integer id) {
        this.id = id;
    }

    public HistoryPayment() {
    }    
    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getDatetimeCreated() {
        return datetimeCreated;
    }

    public void setDatetimeCreated(Date datetimeCreated) {
        this.datetimeCreated = datetimeCreated;
    }

    public BigDecimal getTotalMoney() {
        return totalMoney;
    }

    public void setTotalMoney(BigDecimal totalMoney) {
        this.totalMoney = totalMoney;
    }

    public Integer getPercentPay() {
        return percentPay;
    }

    public void setPercentPay(Integer percentPay) {
        this.percentPay = percentPay;
    }

    public BigDecimal getTotalPay() {
        return totalPay;
    }

    public void setTotalPay(BigDecimal totalPay) {
        this.totalPay = totalPay;
    }

    public Boolean getIsPay() {
        return isPay;
    }

    public void setIsPay(Boolean isPay) {
        this.isPay = isPay;
    }

    public Boolean getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Boolean isDelete) {
        this.isDelete = isDelete;
    }

    public String getDescrip() {
        return descrip;
    }

    public void setDescrip(String descrip) {
        this.descrip = descrip;
    }

    public String getCodeBank() {
        return codeBank;
    }

    public void setCodeBank(String codeBank) {
        this.codeBank = codeBank;
    }

    public String getChuTK() {
        return chuTK;
    }

    public void setChuTK(String chuTK) {
        this.chuTK = chuTK;
    }

    public Admin getAdmcheck() {
        return admcheck;
    }

    public void setAdmcheck(Admin admcheck) {
        this.admcheck = admcheck;
    }

    public ProvincialAgencies getProvincialAgenciesID() {
        return provincialAgenciesID;
    }

    public void setProvincialAgenciesID(ProvincialAgencies provincialAgenciesID) {
        this.provincialAgenciesID = provincialAgenciesID;
    }

    public Customer getCustomerID() {
        return customerID;
    }

    public void setCustomerID(Customer customerID) {
        this.customerID = customerID;
    }
    
}
