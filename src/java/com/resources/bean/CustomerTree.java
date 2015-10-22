package com.resources.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class CustomerTree implements Serializable {

    @Id
    private Integer key;
    private String name;
    private String userName;
    private Integer boss;
    private String parentName;
    private String levelName;
    private Integer levelId;
    private Integer circle;
    private BigDecimal pVLeft;
    private BigDecimal pVRight;
    private Integer level;
    private Integer totalChildren;

    public CustomerTree() {
    }

    public Integer getKey() {
        return key;
    }

    public void setKey(Integer key) {
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Integer getBoss() {
        return boss;
    }

    public void setBoss(Integer boss) {
        this.boss = boss;
    }

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    public String getLevelName() {
        return levelName;
    }

    public void setLevelName(String levelName) {
        this.levelName = levelName;
    }

    public Integer getCircle() {
        return circle;
    }

    public void setCircle(Integer circle) {
        this.circle = circle;
    }

    public BigDecimal getpVLeft() {
        return pVLeft;
    }

    public void setpVLeft(BigDecimal pVLeft) {
        this.pVLeft = pVLeft;
    }

    public BigDecimal getpVRight() {
        return pVRight;
    }

    public void setpVRight(BigDecimal pVRight) {
        this.pVRight = pVRight;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Integer getLevelId() {
        return levelId;
    }

    public void setLevelId(Integer levelId) {
        this.levelId = levelId;
    }

    public Integer getTotalChildren() {
        return totalChildren;
    }

    public void setTotalChildren(Integer totalChildren) {
        this.totalChildren = totalChildren;
    }    
}
