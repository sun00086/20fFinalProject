/*****************************************************************c******************o*******v******id********
 * File: ProductSerializer.java
 * Course materials (20F) CST 8277
 * @author Mike Norman
 *
 * Note: students do NOT need to change anything in this class
 */
package com.algonquincollege.cst8277.rest;

import java.io.IOException;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import com.algonquincollege.cst8277.models.ProductPojo;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;


/**
 * The Class ProductSerializer.
 */
public class ProductSerializer extends StdSerializer<Set<ProductPojo>> implements Serializable {
    
    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /**
     * Instantiates a new product serializer.
     */
    public ProductSerializer() {
        this(null);
    }

    /**
     * Instantiates a new product serializer.
     *
     * @param t the t
     */
    public ProductSerializer(Class<Set<ProductPojo>> t) {
        super(t);
    }

    /* (non-Javadoc)
     * @see com.fasterxml.jackson.databind.ser.std.StdSerializer#serialize(java.lang.Object, com.fasterxml.jackson.core.JsonGenerator, com.fasterxml.jackson.databind.SerializerProvider)
     */
    @Override
    public void serialize(Set<ProductPojo> originalProducts, JsonGenerator generator, SerializerProvider provider)
        throws IOException {
        
        Set<ProductPojo> hollowProducts = new HashSet<>();
        for (ProductPojo originalProduct : originalProducts) {
            // create a 'hollow' copy of the original Product entity
            ProductPojo hollowP = new ProductPojo();
            hollowP.setId(originalProduct.getId());
            hollowP.setDescription(originalProduct.getDescription());
            hollowP.setCreatedDate(originalProduct.getCreatedDate());
            hollowP.setUpdatedDate(originalProduct.getUpdatedDate());
            hollowP.setVersion(originalProduct.getVersion());
            hollowP.setSerialNo(originalProduct.getSerialNo());
            hollowP.setStores(null);
            hollowProducts.add(hollowP);
        }
        generator.writeObject(hollowProducts);
    }
}