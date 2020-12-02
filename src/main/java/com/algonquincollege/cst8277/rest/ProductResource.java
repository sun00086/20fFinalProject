/*****************************************************************c******************o*******v******id********
 * File: ProductResource.java
 * Course materials (20F) CST 8277
 *
 * @author (original) Mike Norman
 * 
 * update by : I. Am. A. Student 040nnnnnnn
 *
 */
package com.algonquincollege.cst8277.rest;

import static com.algonquincollege.cst8277.utils.MyConstants.ADMIN_ROLE;
import static com.algonquincollege.cst8277.utils.MyConstants.PRODUCT_RESOURCE_NAME;
import static com.algonquincollege.cst8277.utils.MyConstants.RESOURCE_PATH_ID_ELEMENT;
import static com.algonquincollege.cst8277.utils.MyConstants.RESOURCE_PATH_ID_PATH;
import static com.algonquincollege.cst8277.utils.MyConstants.USER_ROLE;

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

@Path(PRODUCT_RESOURCE_NAME)     //  "/product:"
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ProductResource {
    
    @EJB
    protected CustomerService customerServiceBean;

    @Inject
    protected ServletContext servletContext;
    
    @GET        // get: get all products
    @RolesAllowed({ADMIN_ROLE,USER_ROLE})
    public Response getProducts() {
        servletContext.log("retrieving all products ...");
        List<ProductPojo> custs = customerServiceBean.getAllProducts();
        Response response = Response.ok(custs).build();
        return response;
    }
     
    @GET        // get{id} get a product by its id
    @Path(RESOURCE_PATH_ID_PATH)
    @RolesAllowed({ADMIN_ROLE,USER_ROLE})
    public Response getProductById(@PathParam(RESOURCE_PATH_ID_ELEMENT) int id) {
        servletContext.log("try to retrieve specific product " + id);
        ProductPojo theProduct = customerServiceBean.getProductById(id);
        Response response = Response.ok(theProduct).build();
        return response;
    }
    
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
    
    @POST       // post --  Add a new product
    @Transactional
    @RolesAllowed({ADMIN_ROLE})
    public Response addProduct(ProductPojo newProduct) {
      Response response = null;
      ProductPojo newProductWithIdTimestamps = customerServiceBean.persistProduct(newProduct);
      response = Response.ok(newProductWithIdTimestamps).build();
      return response;
    }
    
    
    @PUT        // put{id}  Modify product by its id
    @Transactional
    @RolesAllowed({ADMIN_ROLE})
    @Path(RESOURCE_PATH_ID_PATH)
    public Response updateProduct(@PathParam(RESOURCE_PATH_ID_ELEMENT)int id, ProductPojo updatedCustomer) {
      Response response = null;
      customerServiceBean.updateProductById(id, updatedCustomer);
      response = Response.ok().build();
      return response;
    }
}