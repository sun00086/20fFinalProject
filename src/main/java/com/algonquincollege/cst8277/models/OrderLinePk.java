/*****************************************************************c******************o*******v******id********
 * File: OrderLinePk.java
 * Course materials (20F) CST 8277
 *
 * @author (original) Mike Norman
 * 
 * (Modified) @author Feng Sun,Wang Peng,Yang Shuting
 */
package com.algonquincollege.cst8277.models;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Embeddable;


/**
 * The Class OrderLinePk.
 */
@Embeddable
public class OrderLinePk implements Serializable {
    
    /**  explicit set serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /** The owning order id. */
    protected int owningOrderId;
    
    /** The order line no. */
    protected int orderLineNo;
    
    /**
     * Gets the owning order id.
     *
     * @return the owning order id
     */
    @Column(name="OWNING_ORDER_ID")
    public int getOwningOrderId() {
        return owningOrderId;
    }
    
    /**
     * Sets the owning order id.
     *
     * @param owningOrderId the new owning order id
     */
    public void setOwningOrderId(int owningOrderId) {
        this.owningOrderId = owningOrderId;
    }
    
    /**
     * Gets the order line no.
     *
     * @return the order line no
     */
    @Column(name="ORDERLINE_NO")
    public int getOrderLineNo() {
        return orderLineNo;
    }
    
    /**
     * Sets the order line no.
     *
     * @param orderLineNo the new order line no
     */
    public void setOrderLineNo(int orderLineNo) {
        this.orderLineNo = orderLineNo;
    }
    
    /* (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        return Objects.hash(orderLineNo, owningOrderId);
    }
    
    /* (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof OrderLinePk)) {
            return false;
        }
        OrderLinePk other = (OrderLinePk) obj;
        return orderLineNo == other.orderLineNo && owningOrderId == other.owningOrderId;
    }

}