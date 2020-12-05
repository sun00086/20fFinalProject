/*****************************************************************c******************o*******v******id********
 * File: ProductResource.java
 * Course materials (20F) CST 8277
 *
 * @author (original) Mike Norman
 * 
 * (Modified) @author Feng Sun,Wang Peng,Yang Shuting
 *
 */
package com.algonquincollege.cst8277.rest;

import static com.algonquincollege.cst8277.utils.MyConstants.ADMIN_ROLE;
import static com.algonquincollege.cst8277.utils.MyConstants.PRODUCT_RESOURCE_NAME;
import static com.algonquincollege.cst8277.utils.MyConstants.RESOURCE_PATH_ID_ELEMENT;
import static com.algonquincollege.cst8277.utils.MyConstants.RESOURCE_PATH_ID_PATH;
import static com.algonquincollege.cst8277.utils.MyConstants.USER_ROLE;
import static javax.ws.rs.core.Response.Status.OK;

import java.util.List;


import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.inject.Inject;
import javax.servlet.ServletContext;
import javax.transaction.Transactional;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.algonquincollege.cst8277.ejb.CustomerService;
import com.algonquincollege.cst8277.models.ProductPojo;


/**
 * The Class ProductResource.
 */
@Path(PRODUCT_RESOURCE_NAME)     //  "/product:"
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ProductResource {
    
    /** The customer service bean. */
    @EJB
    protected CustomerService customerServiceBean;

    /** The servlet context. */
    @Inject
    protected ServletContext servletContext;
    
    /**
     * Gets the products.
     *
     * @return the products
     */
    @GET        // get: get all products
    @RolesAllowed({ADMIN_ROLE})
    public Response getProducts() {
        servletContext.log("retrieving all products ...");
        List<ProductPojo> custs = customerServiceBean.getAllProducts();
        Response response = Response.ok(custs).build();
        return response;
    }
     
    /**
     * Gets the product by id.
     *
     * @param id the id
     * @return the product by id
     */
    @GET        // get{id} get a product by its id
    @Path(RESOURCE_PATH_ID_PATH)
    @RolesAllowed({ADMIN_ROLE,USER_ROLE})
    public Response getProductById(@PathParam(RESOURCE_PATH_ID_ELEMENT) int id) {
        servletContext.log("try to retrieve specific product " + id);
        Response response;
        ProductPojo theProduct = customerServiceBean.getProductById(id);
        if(theProduct != null) {
             response = Response.ok(theProduct).build();
             servletContext.log("retrieve product description " + theProduct.getDescription());
        }else {
            response = Response.status(Response.Status.NOT_FOUND).build();
        }
        return response;
    }
    
    /**
     * Deletet product.
     *
     * @param id the id
     * @return the response
     */
    @DELETE     // delete{id} delete a product by its id
    @RolesAllowed({ADMIN_ROLE})
    @Path(RESOURCE_PATH_ID_PATH)
    public Response deletetProduct(@PathParam(RESOURCE_PATH_ID_ELEMENT) int id) {
        boolean result = customerServiceBean.deleteProduct(id);

        if(result) {
//            return Response.ok().status(Response.Status.NO_CONTENT).build();
            return Response.ok().build();
        }else {
            return Response.notModified().build();
        }
    }
    
    /**
     * Adds the product.
     *
     * @param newProduct the new product
     * @return the response
     */
    @POST       // post --  Add a new product
    @Transactional
    @RolesAllowed({ADMIN_ROLE})
    public Response addProduct(ProductPojo newProduct) {
      Response response = null;
      ProductPojo newProductWithIdTimestamps = customerServiceBean.persistProduct(newProduct);
      response = Response.ok(newProductWithIdTimestamps).build();
      return response;
    }
    
    
    /**
     * Update product.
     *
     * @param id the id
     * @param updatedCustomer the updated customer
     * @return the response
     */
    @PUT        // put{id}  Modify product by its id
    @Transactional
    @RolesAllowed({ADMIN_ROLE})
    @Path(RESOURCE_PATH_ID_PATH)
    public Response updateProduct(@PathParam(RESOURCE_PATH_ID_ELEMENT)int id, ProductPojo updatedCustomer) {
      Response response = null;
      customerServiceBean.updateProductById(id, updatedCustomer);
      response = Response.ok(updatedCustomer).build();
      return response;
    }
}