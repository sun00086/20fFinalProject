/*****************************************************************c******************o*******v******id********
 * File: SecurityUser.java
 * Course materials (20F) CST 8277
 *
 * @author (original) Mike Norman
 * 
 * update by : I. Am. A. Student 040nnnnnnn
 */
package com.algonquincollege.cst8277.models;

import static com.algonquincollege.cst8277.models.SecurityUser.SECURITY_USER_BY_NAME_QUERY;
import java.io.Serializable;
import java.security.Principal;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
//import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.algonquincollege.cst8277.rest.SecurityRoleSerializer;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

// TODO: Auto-generated Javadoc
/**
 * User class used for (JSR-375) Java EE Security authorization/authentication.
 */
@Entity(name="SecurityUser")
@Table(name="SECURITY_USER")
@Access(AccessType.PROPERTY)
@NamedQuery(name= SECURITY_USER_BY_NAME_QUERY, query = "select su from SecurityUser su where su.username =:param1")
public class SecurityUser implements Serializable, Principal {
    
    /**  explicit set serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /** The Constant USER_FOR_OWNING_CUST_QUERY. */
    public static final String USER_FOR_OWNING_CUST_QUERY =
        "userForOwningCust";
    
    /** The Constant SECURITY_USER_BY_NAME_QUERY. */
    public static final String SECURITY_USER_BY_NAME_QUERY =
        "userByName";

    /** The id. */
    protected int id;
    
    /** The username. */
    protected String username;
    
    /** The pw hash. */
    protected String pwHash;
    
    /** The roles. */
    protected Set<SecurityRole> roles = new HashSet<>();
    
    /** The cust. */
    protected CustomerPojo cust;

    /**
     * Instantiates a new security user.
     */
    public SecurityUser() {
        super();
    }

    /**
     * Gets the id.
     *
     * @return the id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="USER_ID")
    public int getId() {
        return id;
    }
    
    /**
     * Sets the id.
     *
     * @param id the new id
     */
    public void setId(int id) {
        this.id = id;
    }
    
    /**
     * Gets the username.
     *
     * @return the username
     */
    @Column(name="username")
    public String getUsername() {
        return username;
    }
    
    /**
     * Sets the username.
     *
     * @param username the new username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Gets the pw hash.
     *
     * @return the pw hash
     */
    @JsonIgnore
    public String getPwHash() {
        return pwHash;
    }
    
    /**
     * Sets the pw hash.
     *
     * @param pwHash the new pw hash
     */
    public void setPwHash(String pwHash) {
        this.pwHash = pwHash;
    }
    
    /**
     * Gets the roles.
     *
     * @return the roles
     */
    @JsonInclude(Include.NON_NULL)
    @JsonSerialize(using = SecurityRoleSerializer.class)
    @ManyToMany(targetEntity = SecurityRole.class, cascade = CascadeType.PERSIST)
    @JoinTable(name="SECURITY_USER_SECURITY_ROLE",
      joinColumns=@JoinColumn(name="USER_ID", referencedColumnName="USER_ID"),
      inverseJoinColumns=@JoinColumn(name="ROLE_ID", referencedColumnName="ROLE_ID"))
    public Set<SecurityRole> getRoles() {
        return roles;
    }
    
    /**
     * Sets the roles.
     *
     * @param roles the new roles
     */
    public void setRoles(Set<SecurityRole> roles) {
        this.roles = roles;
    }

    /**
     * Gets the customer.
     *
     * @return the customer
     */
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name="CUSTOMER_ID", referencedColumnName="ID")
    public CustomerPojo getCustomer() {
        return cust;
    }
    
    /**
     * Sets the customer.
     *
     * @param cust the new customer
     */
    public void setCustomer(CustomerPojo cust) {
        this.cust = cust;
    }

    /* (non-Javadoc)
     * @see java.security.Principal#getName()
     */
    //Principal
    @JsonIgnore
    @Override
    public String getName() {
        return getUsername();
    }

    /* (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + id;
        return result;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        SecurityUser other = (SecurityUser)obj;
        if (id != other.id) {
            return false;
        }
        return true;
    }

}