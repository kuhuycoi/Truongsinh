package com.resources.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "RoleAdmin")
public class RoleAdmin implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private int id;
    
    @Column(name = "Name")
    private String name;
    
    @Column(name = "isActive")
    private boolean isActive;
    
    @Column(name = "isDeleted")
    private boolean isDeleted;
    
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "roleAdmID")
    private List<Admin> admins = new ArrayList();
    
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "roleId")
    private List<ModuleInRole> moduleInRoles = new ArrayList();
    
    public RoleAdmin() {
    }

    public RoleAdmin(int id) {
        this.id = id;
    }    

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

    public boolean isIsActive() {
        return isActive;
    }

    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }

    public boolean isIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    public List<Admin> getAdmins() {
        return admins;
    }

    public void setAdmins(List<Admin> admins) {
        this.admins = admins;
    }

    public List<ModuleInRole> getModuleInRoles() {
        return moduleInRoles;
    }

    public void setModuleInRoles(List<ModuleInRole> moduleInRoles) {
        this.moduleInRoles = moduleInRoles;
    }    
}
