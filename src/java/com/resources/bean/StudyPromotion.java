package com.resources.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Id;

public class StudyPromotion implements Serializable {

    @Id
    private Integer id;
    private String name;
    private String customerStart;
    private String customerEnd;
    private Integer totalMember;
    private BigDecimal moneyperone;
    private BigDecimal moneypercircle;
    private Boolean isActive;

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCustomerStart() {
        return customerStart;
    }

    public String getCustomerEnd() {
        return customerEnd;
    }

    public Integer getTotalMember() {
        return totalMember;
    }

    public BigDecimal getMoneyperone() {
        return moneyperone;
    }

    public BigDecimal getMoneypercircle() {
        return moneypercircle;
    }

    public Boolean getIsActive() {
        return isActive;
    }
}
