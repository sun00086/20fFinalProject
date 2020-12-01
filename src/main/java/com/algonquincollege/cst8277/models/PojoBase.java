/*****************************************************************c******************o*******v******id********
 * File: PojoBase.java
 * Course materials (20F) CST 8277
 *
 * @author (original) Mike Norman
 * 
 * update by : I. Am. A. Student 040nnnnnnn
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

/**
 * Abstract class that is base of (class) hierarchy for all c.a.cst8277.models @Entity classes
 */
//@MappedSuperclass
@MappedSuperclass
@Access(AccessType.PROPERTY) // NOTE: by using this annotations, any annotation on a field is ignored without warning
@EntityListeners({PojoListener.class}) // a liitle bit difference from professor
public abstract class PojoBase implements Serializable {
    private static final long serialVersionUID = 1L;

    protected int id;
    protected LocalDateTime created;
    protected LocalDateTime updated;
    protected int version;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    
    @Column (name ="CREATED")
    public LocalDateTime getCreatedDate() {
        return created;
    }
    public void setCreatedDate(LocalDateTime created) {
        this.created = created;
    }
    @Column (name="UPDATED")
    public LocalDateTime getUpdatedDate() {
        return updated;
    }
    public void setUpdatedDate(LocalDateTime updated) {
        this.updated = updated;
    }
    @Version
    public int getVersion() {
        return version;
    }
    public void setVersion(int version) {
        this.version = version;
    }

    // It is a good idea for hashCode() to use the @Id property
    // since it maps to table's PK and that is how Db determine's identity
    // (same for equals()
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + id;
        return result;
    }

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