package com.resources.entity;

import java.io.Serializable;
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
@Table(name = "ModuleInRole")
public class ModuleInRole implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private int id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "RoleID", updatable = false)
    private RoleAdmin roleId;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ModuleID", updatable = false)
    private Module moduleID;

    public ModuleInRole(int id) {
        this.id = id;
    }

    public ModuleInRole() {
    }
   
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public RoleAdmin getRoleId() {
        return roleId;
    }

    public void setRoleId(RoleAdmin roleId) {
        this.roleId = roleId;
    }

    public Module getModuleID() {
        return moduleID;
    }

    public void setModuleID(Module moduleID) {
        this.moduleID = moduleID;
    }
    
    
    
}
