/*****************************************************************c******************o*******v******id********
 * File: CustomerResource.java
 * Course materials (20F) CST 8277
 *
 * @author (original) Mike Norman
 * 
 * update by : I. Am. A. Student 040nnnnnnn
 *
 */
package com.algonquincollege.cst8277.rest;

import static com.algonquincollege.cst8277.utils.MyConstants.ADMIN_ROLE;
import static com.algonquincollege.cst8277.utils.MyConstants.CUSTOMER_RESOURCE_NAME;
import static com.algonquincollege.cst8277.utils.MyConstants.RESOURCE_PATH_ID_ELEMENT;
import static com.algonquincollege.cst8277.utils.MyConstants.RESOURCE_PATH_ID_PATH;
import static com.algonquincollege.cst8277.utils.MyConstants.USER_ROLE;
import static com.algonquincollege.cst8277.utils.MyConstants.CUSTOMER_ADDRESS_RESOURCE_PATH;
import static javax.ws.rs.core.Response.Status.BAD_REQUEST;
import static javax.ws.rs.core.Response.Status.NOT_FOUND;
import static javax.ws.rs.core.Response.Status.OK;

import java.util.List;

import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.inject.Inject;
import javax.security.enterprise.SecurityContext;
import javax.servlet.ServletContext;
import javax.transaction.Transactional;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.ForbiddenException;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.soteria.WrappingCallerPrincipal;

import com.algonquincollege.cst8277.ejb.CustomerService;
import com.algonquincollege.cst8277.models.AddressPojo;
import com.algonquincollege.cst8277.models.CustomerPojo;
import com.algonquincollege.cst8277.models.OrderPojo;
import com.algonquincollege.cst8277.models.ProductPojo;
import com.algonquincollege.cst8277.models.OrderLinePojo;
import com.algonquincollege.cst8277.models.SecurityUser;

// TODO: Auto-generated Javadoc
/**
 * The Class CustomerResource.
 */
@Path(CUSTOMER_RESOURCE_NAME) // "customer"
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class CustomerResource {
    
    /** The customer service bean. */
    @EJB
    protected CustomerService customerServiceBean;

    /** The servlet context. */
    @Inject
    protected ServletContext servletContext;

    /** The sc. */
    @Inject
    protected SecurityContext sc;
    
    /**
     * Gets the customers.
     *
     * @return the customers
     */
    @GET        // # GET /customer
    @RolesAllowed({ADMIN_ROLE})
    public Response getCustomers() {
        servletContext.log("retrieving all customers ...");
        List<CustomerPojo> custs = customerServiceBean.getAllCustomers();
        Response response = Response.ok(custs).build();
        return response;
    }
    
    /**
     * Adds the customer.
     *
     * @param newCustomer the new customer
     * @return the response
     */
    @POST       // POST /custmer
    @Transactional
    @RolesAllowed({ADMIN_ROLE})
    public Response addCustomer(CustomerPojo newCustomer) {
      Response response = null;
      CustomerPojo newCustomerWithIdTimestamps = customerServiceBean.persistCustomer(newCustomer);
      //build a SecurityUser linked to the new customer
      customerServiceBean.buildUserForNewCustomer(newCustomerWithIdTimestamps);
      response = Response.ok(newCustomerWithIdTimestamps).build();
      return response;
    }

    /**
     * Gets the customer by id.
     *
     * @param id the id
     * @return the customer by id
     */
    @GET       // GET /customer/{id}
    @RolesAllowed({ADMIN_ROLE,USER_ROLE})
    @Path(RESOURCE_PATH_ID_PATH)                 //"/{" + RESOURCE_PATH_ID_ELEMENT + "}";
    public Response getCustomerById(@PathParam(RESOURCE_PATH_ID_ELEMENT) int id) {
        servletContext.log("try to retrieve specific customer " + id);
        Response response = null;
        CustomerPojo cust = null;

        if (sc.isCallerInRole(ADMIN_ROLE)) {
            cust = customerServiceBean.getCustomerById(id);
            response = Response.status( cust == null ? NOT_FOUND : OK).entity(cust).build();
        }
        else if (sc.isCallerInRole(USER_ROLE)) {
            WrappingCallerPrincipal wCallerPrincipal = (WrappingCallerPrincipal)sc.getCallerPrincipal();
            SecurityUser sUser = (SecurityUser)wCallerPrincipal.getWrapped();
            cust = sUser.getCustomer();
            if (cust != null && cust.getId() == id) {
                response = Response.status(OK).entity(cust).build();
            }
            else {
                throw new ForbiddenException();
            }
        }
        else {
            response = Response.status(BAD_REQUEST).build();
        }
        return response;
    }
   
    
    
    
    /**
     * Delete customer.
     *
     * @param id the id
     * @return the response
     */
    @DELETE     //DELETE /customer/{id} Removes a customer by its id
    @RolesAllowed({ADMIN_ROLE})
    @Path(RESOURCE_PATH_ID_PATH)    // customer/{id}
    public Response deleteCustomer(@PathParam(RESOURCE_PATH_ID_ELEMENT) int id) {
        Response response = null;
        customerServiceBean.deleteCustomerById(id);
        response = Response.ok().build();
        return response;
    }
    
    
    /**
     * Update customer.
     *
     * @param id the id
     * @param updatedCustomer the updated customer
     * @return the response
     */
    @PUT        //PUT /customer/{id} Modify a customer by its id
    @Transactional
    @RolesAllowed({ADMIN_ROLE})
    @Path(RESOURCE_PATH_ID_PATH)    // customer/{id}
    public Response updateCustomer(@PathParam(RESOURCE_PATH_ID_ELEMENT)int id, CustomerPojo updatedCustomer) {
      Response response = null;
      customerServiceBean.updateCustomerById(id, updatedCustomer);
      response = Response.ok().build();
      return response;
    }
    
    /**
     * Adds the address for customer.
     *
     * @param id the id
     * @param newAddress the new address
     * @return the response
     */
    @PUT       //PUT /customer/{id}/billingAddress -- add a customer address by its id
    @RolesAllowed({ADMIN_ROLE})
    @Transactional
    @Path(CUSTOMER_ADDRESS_RESOURCE_PATH)
    public Response addAddressForCustomer(@PathParam(RESOURCE_PATH_ID_ELEMENT) int id, AddressPojo newAddress) {
        Response response = null;
        CustomerPojo updatedCustomer = customerServiceBean.setAddressFor(id, newAddress);
        response = Response.ok(updatedCustomer).build();
        return response;
     }
     
  //--------------------------------------------------------------------------------
   
    //TODO - endpoints for setting up Orders/OrderLines


    /**
   * Gets the orders.
   *
   * @param id the id
   * @return the orders
   */
    @GET                  //custmer/{id}/order
    @RolesAllowed({ADMIN_ROLE,USER_ROLE})
    @Path("/{id}/order")
    public Response getOrders(@PathParam(RESOURCE_PATH_ID_ELEMENT) int id) {
        servletContext.log("retrieving all orders ...");
        List<OrderPojo> custs = customerServiceBean.getCustomerAllOrders(id);
        Response response = Response.ok(custs).build();
        return response;
    }
    
    
    
    /**
     * Adds the order.
     *
     * @param id the id
     * @param newCustomer the new customer
     * @return the response
     */
    @POST                    //custmer/{id}/order
    @Transactional
    @RolesAllowed({ADMIN_ROLE})
    @Path("/{id}/order")
    public Response addOrder(@PathParam(RESOURCE_PATH_ID_ELEMENT) int id, OrderPojo newCustomer) {
      Response response = null;
      CustomerPojo newCustomerWithIdTimestamps = customerServiceBean.persistCustomerOrder(id, newCustomer);
      response = Response.ok(newCustomerWithIdTimestamps).build();
      return response;
    }
    
    

    
    /**
     * Gets the order by id.
     *
     * @param id the id
     * @param orderid the orderid
     * @return the order by id
     */
    @GET
    @RolesAllowed({ADMIN_ROLE})
    @Path("/{id}/order/{orderid}")
    public Response getOrderById(@PathParam(RESOURCE_PATH_ID_ELEMENT) int id,@PathParam("orderid") int orderid) {
      servletContext.log("try to retrieve specific product " + id);
      OrderPojo custs  = customerServiceBean.getCustomerOrderById(id,orderid);
      Response response = Response.ok(custs).build();
      return response;
    }


    /**
     * Deletet order.
     *
     * @param id the id
     * @param orderid the orderid
     * @return the response
     */
    @DELETE
    @RolesAllowed({ADMIN_ROLE})
    @Path("/{id}/order/{orderid}")
    public Response deletetOrder(@PathParam(RESOURCE_PATH_ID_ELEMENT) int id, @PathParam("orderid")int orderid) {
        Response response = null;
        OrderPojo orderdelete = customerServiceBean.deleteCustomerOrderById(id,orderid);
        response = Response.ok(orderdelete).build();
        return response;
    }


    /**
     * Update order.
     *
     * @param id the id
     * @param orderid the orderid
     * @param order the order
     * @return the response
     */
    @Transactional
    @RolesAllowed({ADMIN_ROLE})
    @Path("/{id}/order/{orderid}")
    @PUT
    public Response updateOrder(@PathParam(RESOURCE_PATH_ID_ELEMENT) int id,@PathParam("orderid") int orderid, OrderPojo order) {
      Response response = null;
      OrderPojo orderchange=customerServiceBean.updateCustomerOrderById(id, orderid,order);
      response = Response.ok(orderchange).build();
      return response;
    }


    /**
     * Gets the order line by id.
     *
     * @param id the id
     * @param orderid the orderid
     * @return the order line by id
     */
    @GET
    @RolesAllowed({ADMIN_ROLE})
    @Path("/{id}/order/{orderid}/orderline")
    public Response getOrderLineById(@PathParam(RESOURCE_PATH_ID_ELEMENT) int id,@PathParam("orderid") int orderid) {
      servletContext.log("try to retrieve specific product " + id);
      List<OrderLinePojo> custs  = customerServiceBean.getCustomerOrderLineById(id,orderid);
      Response response = Response.ok(custs).build();
      return response;
    }
//
//
/**
 * Adds the order line.
 *
 * @param id the id
 * @param orderid the orderid
 * @param line the line
 * @return the response
 */
//
    @POST
    @Transactional
    @RolesAllowed({ADMIN_ROLE})
    @Path("/{id}/order/{orderid}/orderline")
    public Response addOrderLine(@PathParam(RESOURCE_PATH_ID_ELEMENT) int id, @PathParam("orderid") int orderid, OrderLinePojo line) {
      Response response = null;
      OrderPojo newCustomerWithIdTimestamps = customerServiceBean.persistOrderLine(id, orderid, line);
      response = Response.ok(newCustomerWithIdTimestamps).build();
      return response;
    }
    
    /**
     * Gets the order line by id.
     *
     * @param id the id
     * @param orderid the orderid
     * @param orderlineid the orderlineid
     * @return the order line by id
     */
    @GET
    @RolesAllowed({ADMIN_ROLE})
    @Path("/{id}/order/{orderid}/orderline/{orderlineid}")
    public Response getOrderLineById(@PathParam(RESOURCE_PATH_ID_ELEMENT) int id,@PathParam("orderid") int orderid , @PathParam("orderlineid") int orderlineid) {
      servletContext.log("try to retrieve specific product " + id);
      List<OrderLinePojo> custs  = customerServiceBean.getCustomerOrderLineById(id,orderid);
      Response response = Response.ok(custs).build();
      return response;
    }
}