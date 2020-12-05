/*****************************************************************c******************o*******v******id********
 * File: StoreResource.java
 * Course materials (20F) CST 8277
 *
 * @author (original) Mike Norman
 * 
 * (Modified) @author Feng Sun,Wang Peng,Yang Shuting
 *
 */
package com.algonquincollege.cst8277.rest;

import static com.algonquincollege.cst8277.utils.MyConstants.RESOURCE_PATH_ID_ELEMENT;
import static com.algonquincollege.cst8277.utils.MyConstants.RESOURCE_PATH_ID_PATH;
import static com.algonquincollege.cst8277.utils.MyConstants.STORE_RESOURCE_NAME;
import static com.algonquincollege.cst8277.utils.MyConstants.USER_ROLE;
import static com.algonquincollege.cst8277.utils.MyConstants.ADMIN_ROLE;

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
//import com.algonquincollege.cst8277.models.ProductPojo;
import com.algonquincollege.cst8277.models.StorePojo;


/**
 * The Class StoreResource.
 */
@Path(STORE_RESOURCE_NAME)       // /store:
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class StoreResource {

    /** The customer service bean. */
    @EJB
    protected CustomerService customerServiceBean;

    /** The servlet context. */
    @Inject
    protected ServletContext servletContext;

    /**
     * Gets the stores.
     *
     * @return the stores
     */
    @GET            // GET /store  Retrieve all stores
    @RolesAllowed({ADMIN_ROLE,USER_ROLE})
    public Response getStores() {
        servletContext.log("retrieving all stores ...");
        Response response;
        List<StorePojo> stores = customerServiceBean.getAllStores();
        if(stores != null) {
            response = Response.ok(stores).build();
       }else {
           response = Response.status(Response.Status.NOT_FOUND).build();
       }

        return response;
    }
    
    /**
     * Gets the store by id.
     *
     * @param id the id
     * @return the store by id
     */
    @GET            //GET /store{id}  Retrieve a store by its id
    @Path(RESOURCE_PATH_ID_PATH)
    @RolesAllowed({ADMIN_ROLE,USER_ROLE})
    public Response getStoreById(@PathParam(RESOURCE_PATH_ID_ELEMENT) int id) {
        servletContext.log("try to retrieve specific store " + id);
        Response response;
        StorePojo theStore = customerServiceBean.getStoreById(id);
        if(theStore != null) {
            response = Response.ok(theStore).build();
           }else {
               response = Response.status(Response.Status.NOT_FOUND).build();
           }
            return response;
        }
    
    /**
     * Deletet store.
     *
     * @param id the id
     * @return the response
     */
    @DELETE         // DELETE /store{id}  Delete a store by its id
    @RolesAllowed({ADMIN_ROLE})
    @Path(RESOURCE_PATH_ID_PATH)
    public Response deletetStore(@PathParam(RESOURCE_PATH_ID_ELEMENT) int id) {
        boolean result = customerServiceBean.deleteStore(id);

        if(result) {
            return Response.ok().build();
        }else {
            return Response.notModified().build();
        }
    }
  
    /**
     * Adds the store.
     *
     * @param newStore the new store
     * @return the response
     */
    @POST           // POST /store  Add a new store
    @Transactional
    @RolesAllowed({ADMIN_ROLE})
    public Response addStore(StorePojo newStore) {
      Response response = null;
      StorePojo newStoreWithIdTimestamps = customerServiceBean.persistStore(newStore);
      response = Response.ok(newStoreWithIdTimestamps).build();
      return response;
    }
  
    /**
     * Update store.
     *
     * @param id the id
     * @param updatedCustomer the updated customer
     * @return the response
     */
    @PUT            // PUT /store{id}  Modified a store by its id
    @Transactional
    @RolesAllowed({ADMIN_ROLE})
    @Path(RESOURCE_PATH_ID_PATH)
    public Response updateStore(@PathParam(RESOURCE_PATH_ID_ELEMENT)int id, StorePojo updatedCustomer) {
      Response response = null;
      customerServiceBean.updateStoreById(id, updatedCustomer);
      response = Response.ok().build();
      return response;
    }
  }