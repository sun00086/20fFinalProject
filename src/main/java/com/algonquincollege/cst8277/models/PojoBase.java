/*****************************************************************c******************o*******v******id********
 * File: PojoBase.java
 * Course materials (20F) CST 8277
 *
 * @author (original) Mike Norman
 * 
 * (Modified) @author Feng Sun,Wang Peng,Yang Shuting
 */
package com.algonquincollege.cst8277.models;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Version;

// TODO: Auto-generated Javadoc
/**
 * Abstract class that is base of (class) hierarchy for all c.a.cst8277.models @Entity classes
 */
//@MappedSuperclass
@MappedSuperclass
@Access(AccessType.PROPERTY) // NOTE: by using this annotations, any annotation on a field is ignored without warning
@EntityListeners({PojoListener.class}) // a liitle bit difference from professor
public abstract class PojoBase implements Serializable {
    
    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /** The id. */
    protected int id;
    
    /** The created. */
    protected LocalDateTime created;
    
    /** The updated. */
    protected LocalDateTime updated;
    
    /** The version. */
    protected int version;

    /**
     * Gets the id.
     *
     * @return the id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
     * Gets the created date.
     *
     * @return the created date
     */
    @Column (name ="CREATED")
    public LocalDateTime getCreatedDate() {
        return created;
    }
    
    /**
     * Sets the created date.
     *
     * @param created the new created date
     */
    public void setCreatedDate(LocalDateTime created) {
        this.created = created;
    }
    
    /**
     * Gets the updated date.
     *
     * @return the updated date
     */
    @Column (name="UPDATED")
    public LocalDateTime getUpdatedDate() {
        return updated;
    }
    
    /**
     * Sets the updated date.
     *
     * @param updated the new updated date
     */
    public void setUpdatedDate(LocalDateTime updated) {
        this.updated = updated;
    }
    
    /**
     * Gets the version.
     *
     * @return the version
     */
    @Version
    public int getVersion() {
        return version;
    }
    
    /**
     * Sets the version.
     *
     * @param version the new version
     */
    public void setVersion(int version) {
        this.version = version;
    }

    // It is a good idea for hashCode() to use the @Id property
    // since it maps to table's PK and that is how Db determine's identity
    /* (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    // (same for equals()
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
        if (!(obj instanceof PojoBase)) {
            return false;
        }
        PojoBase other = (PojoBase)obj;
        if (id != other.id) {
            return false;
        }
        return true;
    }
}