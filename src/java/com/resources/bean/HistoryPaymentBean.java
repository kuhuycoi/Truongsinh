package com.resources.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class HistoryPaymentBean implements Serializable {

    @Id
    private Integer id;
    private Date datetimeCreated;
    private Date orderforDate;
    private BigDecimal totalMoney;
    private Integer percentPay;
    private BigDecimal totalPay;
    private Boolean isPay;
    private Boolean isBank;
    private Boolean isDelete;
    private String descrip;
    private String codeBank;
    private String chuTK;
    private Integer adminId;
    private String adminUsername;
    private Integer provincialAgenciesID;
    private String provincialAgenciesName;
    private Integer customerID;
    private String customerUsername;
    private String customerFirstName;
    private String customerLastName;
    private String bank;

    public HistoryPaymentBean(Integer id) {
        this.id = id;
    }

    public HistoryPaymentBean() {
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

    public Date getOrderforDate() {
        return orderforDate;
    }

    public void setOrderforDate(Date orderforDate) {
        this.orderforDate = orderforDate;
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

    public Integer getAdminId() {
        return adminId;
    }

    public void setAdminId(Integer adminId) {
        this.adminId = adminId;
    }

    public String getAdminUsername() {
        return adminUsername;
    }

    public void setAdminUsername(String adminUsername) {
        this.adminUsername = adminUsername;
    }

    public Integer getProvincialAgenciesID() {
        return provincialAgenciesID;
    }

    public void setProvincialAgenciesID(Integer provincialAgenciesID) {
        this.provincialAgenciesID = provincialAgenciesID;
    }

    public String getProvincialAgenciesName() {
        return provincialAgenciesName;
    }

    public void setProvincialAgenciesName(String provincialAgenciesName) {
        this.provincialAgenciesName = provincialAgenciesName;
    }

    public Integer getCustomerID() {
        return customerID;
    }

    public void setCustomerID(Integer customerID) {
        this.customerID = customerID;
    }

    public String getCustomerUsername() {
        return customerUsername;
    }

    public void setCustomerUsername(String customerUsername) {
        this.customerUsername = customerUsername;
    }

    public String getBank() {
        return bank;
    }

    public void setBank(String bank) {
        this.bank = bank;
    }

    public String getCustomerFirstName() {
        return customerFirstName;
    }

    public void setCustomerFirstName(String customerFirstName) {
        this.customerFirstName = customerFirstName;
    }

    public String getCustomerLastName() {
        return customerLastName;
    }

    public void setCustomerLastName(String customerLastName) {
        this.customerLastName = customerLastName;
    }

    public Boolean getIsBank() {
        return isBank;
    }

    public void setIsBank(Boolean isBank) {
        this.isBank = isBank;
    }
    
}
