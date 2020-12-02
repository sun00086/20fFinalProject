/*****************************************************************c******************o*******v******id********
 * File: StoreResource.java
 * Course materials (20F) CST 8277
 *
 * @author (original) Mike Norman
 * 
 * update by : I. Am. A. Student 040nnnnnnn
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

@Path(STORE_RESOURCE_NAME)       // /store:
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class StoreResource {

    @EJB
    protected CustomerService customerServiceBean;

    @Inject
    protected ServletContext servletContext;

    @GET            // GET /store  Retrieve all stores
    @RolesAllowed({ADMIN_ROLE,USER_ROLE})
    public Response getStores() {
        servletContext.log("retrieving all stores ...");
        List<StorePojo> stores = customerServiceBean.getAllStores();
        Response response = Response.ok(stores).build();
        return response;
    }
    
    @GET            //GET /store{id}  Retrieve a store by its id
    @Path(RESOURCE_PATH_ID_PATH)
    @RolesAllowed({ADMIN_ROLE,USER_ROLE})
    public Response getStoreById(@PathParam(RESOURCE_PATH_ID_ELEMENT) int id) {
        servletContext.log("try to retrieve specific store " + id);
        StorePojo theStore = customerServiceBean.getStoreById(id);
        Response response = Response.ok(theStore).build();
        return response;
    }
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
  
    @POST           // POST /store  Add a new store
    @Transactional
    @RolesAllowed({ADMIN_ROLE})
    public Response addStore(StorePojo newStore) {
      Response response = null;
      StorePojo newStoreWithIdTimestamps = customerServiceBean.persistStore(newStore);
      response = Response.ok(newStoreWithIdTimestamps).build();
      return response;
    }
  
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