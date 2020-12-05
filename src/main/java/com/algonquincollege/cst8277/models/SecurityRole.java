/*****************************************************************c******************o*******v******id********
 * File: SecurityRole.java
 * Course materials (20F) CST 8277
 *
 * @author (original) Mike Norman
 * 
 * (Modified) @author Feng Sun,Wang Peng,Yang Shuting
 */
package com.algonquincollege.cst8277.models;

import java.io.Serializable;
import java.util.Set;


import static com.algonquincollege.cst8277.models.SecurityRole.ROLE_BY_NAME_QUERY;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;


/**
 * Role class used for (JSR-375) Java EE Security authorization/authentication.
 */
@Entity(name="SecurityRole")
@Table(name="SECURITY_ROLE")
@Access(AccessType.PROPERTY)
@NamedQuery(name=ROLE_BY_NAME_QUERY, query="select s from SecurityRole s where s.roleName=:param1")

public class SecurityRole implements Serializable {
    
    /**  explicit set serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /** The Constant ROLE_BY_NAME_QUERY. */
    public static final String ROLE_BY_NAME_QUERY = "roleByName";

    /** The id. */
    protected int id;
    
    /** The role name. */
    protected String roleName;
    
    /** The users. */
    protected Set<SecurityUser> users;

    /**
     * Instantiates a new security role.
     */
    public SecurityRole() {
        super();
    }
    
    /**
     * Gets the id.
     *
     * @return the id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="ROLE_ID")
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
     * Gets the role name.
     *
     * @return the role name
     */
    public String getRoleName() {
        return roleName;
    }
    
    /**
     * Sets the role name.
     *
     * @param roleName the new role name
     */
    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    /**
     * Gets the users.
     *
     * @return the users
     */
    @JsonInclude(Include.NON_NULL)
    @ManyToMany(mappedBy = "roles")
    public Set<SecurityUser> getUsers() {
        return users;
    }
    
    /**
     * Sets the users.
     *
     * @param users the new users
     */
    public void setUsers(Set<SecurityUser> users) {
        this.users = users;
    }
    
    /**
     * Adds the user to role.
     *
     * @param user the user
     */
    public void addUserToRole(SecurityUser user) {
        getUsers().add(user);
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
        SecurityRole other = (SecurityRole)obj;
        if (id != other.id) {
            return false;
        }
        return true;
    }
}