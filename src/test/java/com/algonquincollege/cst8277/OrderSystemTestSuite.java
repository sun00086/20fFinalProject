/*****************************************************************c******************o*******v******id********
 * File: OrderSystemTestSuite.java
 * Course materials (20F) CST 8277
 * (Original Author) Mike Norman
 *
 * @date 2020 10
 *
 * (Modified) @author Student Name
 */
package com.algonquincollege.cst8277;

import static com.algonquincollege.cst8277.utils.MyConstants.PRODUCT_RESOURCE_NAME;
import static com.algonquincollege.cst8277.utils.MyConstants.APPLICATION_API_VERSION;
import static com.algonquincollege.cst8277.utils.MyConstants.CUSTOMER_ADDRESS_RESOURCE_PATH;
import static com.algonquincollege.cst8277.utils.MyConstants.CUSTOMER_RESOURCE_NAME;
import static com.algonquincollege.cst8277.utils.MyConstants.ORDER_RESOURCE_NAME;
import static com.algonquincollege.cst8277.utils.MyConstants.CUSTOMER_ADDRESS_SUBRESOURCE_NAME;
import static com.algonquincollege.cst8277.utils.MyConstants.DEFAULT_ADMIN_USER;
import static com.algonquincollege.cst8277.utils.MyConstants.DEFAULT_ADMIN_USER_PASSWORD;
import static com.algonquincollege.cst8277.utils.MyConstants.DEFAULT_USER_PASSWORD;
import static com.algonquincollege.cst8277.utils.MyConstants.DEFAULT_USER_PREFIX;
import static com.algonquincollege.cst8277.utils.MyConstants.RESOURCE_PATH_ID_PATH;
import static com.algonquincollege.cst8277.utils.MyConstants.RESOURCE_PATH_ID_ELEMENT;
import static com.algonquincollege.cst8277.utils.MyConstants.CUSTOMER_ORDER_RESOURCE_PATH;

import static javax.ws.rs.core.Response.Status;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.collection.IsEmptyCollection.empty;
//import static org.junit.Assert.assertEquals;
//import static org.junit.Assert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.lang.invoke.MethodHandles;
import java.net.URI;
import java.util.List;

import javax.persistence.TypedQuery;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;

import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.client.authentication.HttpAuthenticationFeature;
import org.glassfish.jersey.logging.LoggingFeature;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.bridge.SLF4JBridgeHandler;

import com.algonquincollege.cst8277.models.BillingAddressPojo;
import com.algonquincollege.cst8277.models.CustomerPojo;
import com.algonquincollege.cst8277.models.OrderPojo;
import com.algonquincollege.cst8277.models.ProductPojo;
import com.algonquincollege.cst8277.models.ShippingAddressPojo;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

// TODO: Auto-generated Javadoc
/**
 * The Class OrderSystemTestSuite.
 */
@TestMethodOrder(MethodOrderer.Alphanumeric.class)
public class OrderSystemTestSuite {
    
    /** The Constant _thisClaz. */
    private static final Class<?> _thisClaz = MethodHandles.lookup().lookupClass();
    
    /** The Constant logger. */
    private static final Logger logger = LoggerFactory.getLogger(_thisClaz);

    /** The Constant APPLICATION_CONTEXT_ROOT. */
    static final String APPLICATION_CONTEXT_ROOT = "rest-orderSystem";
    
    /** The Constant HTTP_SCHEMA. */
    static final String HTTP_SCHEMA = "http";
    
    /** The Constant HOST. */
    static final String HOST = "localhost";
    
    //TODO - if you changed your Payara's default port (to say for example 9090)
    //       your may need to alter this constant
/** The Constant PORT. */
    //    static final int PORT = 9090;
    static final int PORT = 8080;

    /** The uri. */
    // test fixture(s)
    static URI uri;
    
    /** The admin auth. */
    static HttpAuthenticationFeature adminAuth;
    
    /** The user auth. */
    static HttpAuthenticationFeature userAuth;

    /**
     * One time set up.
     *
     * @throws Exception the exception
     */
    @BeforeAll
    public static void oneTimeSetUp() throws Exception {
        logger.debug("oneTimeSetUp");
        SLF4JBridgeHandler.removeHandlersForRootLogger();
        SLF4JBridgeHandler.install();
        uri = UriBuilder
            .fromUri(APPLICATION_CONTEXT_ROOT + APPLICATION_API_VERSION)
            .scheme(HTTP_SCHEMA)
            .host(HOST)
            .port(PORT)
            .build();
        adminAuth = HttpAuthenticationFeature.basic(DEFAULT_ADMIN_USER, DEFAULT_ADMIN_USER_PASSWORD);
        userAuth = HttpAuthenticationFeature.basic(DEFAULT_USER_PREFIX, DEFAULT_USER_PASSWORD);
    }
    
    

    /** The web target. */
    protected WebTarget webTarget;
    
    /**
     * Sets the up.
     */
    @BeforeEach
    public void setUp() {
        Client client = ClientBuilder.newClient(
            new ClientConfig().register(MyObjectMapperProvider.class).register(new LoggingFeature()));
        webTarget = client.target(uri);
    }

    /**
     * Test 01 all customers with adminrole.
     *
     * @throws JsonMappingException the json mapping exception
     * @throws JsonProcessingException the json processing exception
     */
    @Test
    public void test01_all_customers_with_adminrole() throws JsonMappingException, JsonProcessingException {
        Response response = webTarget
            //.register(userAuth)
            .register(adminAuth)
            .path(CUSTOMER_RESOURCE_NAME)
            .request()
            .get();
        assertThat(response.getStatus(), is(200));
        List<CustomerPojo> custs = response.readEntity(new GenericType<List<CustomerPojo>>(){});
        assertThat(custs, is(not(empty())));
        //TODO - depending on what is in your Db when you run this, you may need to change the next line
        assertThat(custs, hasSize(8));
    }
    
    // TODO - create39 more test-cases that send GET/PUT/POST/DELETE messages
    // to REST'ful endpoints for the OrderSystem entities using the JAX-RS
    /**
     * Test 02 get customer by id with adminrole.
     *
     * @throws JsonMappingException the json mapping exception
     * @throws JsonProcessingException the json processing exception
     */
    // ClientBuilder APIs
    @Test
    public void test02_get_customer_by_id_with_adminrole()throws JsonMappingException, JsonProcessingException {
        Response response = webTarget
            .register(adminAuth)
            .path(CUSTOMER_RESOURCE_NAME + RESOURCE_PATH_ID_PATH)
            .resolveTemplate(RESOURCE_PATH_ID_ELEMENT, 10)
            .request()
            .get();
        assertThat(response.getStatus(), is(200));
        CustomerPojo custs = response.readEntity(CustomerPojo.class);
        assertEquals(10,custs.getId());
        assertEquals("noon",custs.getLastName());//
        
    }
    
    /**
     * Test 03 create customer by admin.
     *
     * @throws JsonMappingException the json mapping exception
     * @throws JsonProcessingException the json processing exception
     */
    @Test
    public void test03_create_customer_by_admin()  throws JsonMappingException, JsonProcessingException{
        CustomerPojo custs = new CustomerPojo();
        custs.setFirstName("lee");
        custs.setEmail("uu@gamil.com");
        custs.setLastName("noon");
        custs.setPhoneNumber("887777787");
        Response response = webTarget
            .register(adminAuth)
            .path(CUSTOMER_RESOURCE_NAME )
            .request()
            .post(Entity.json(custs));
        assertThat(response.getStatus(), is(200));
        CustomerPojo custs2 = response.readEntity(new GenericType<CustomerPojo>(){});
        assertEquals("lee",custs2.getFirstName());
       
    }

    /**
     * Test 04 update customer with admin.
     *
     * @throws JsonMappingException the json mapping exception
     * @throws JsonProcessingException the json processing exception
     */
    @Test
    public void test04_update_customer_with_admin()throws JsonMappingException, JsonProcessingException {
        CustomerPojo custs = new CustomerPojo();
        custs.setFirstName("lee2");
        custs.setEmail("uu2@gamil.com");
        custs.setLastName("noon2");
        custs.setPhoneNumber("8877777872");
        
        Response response = webTarget
            .register(adminAuth)
            .path(CUSTOMER_RESOURCE_NAME  + RESOURCE_PATH_ID_PATH)
            .resolveTemplate(RESOURCE_PATH_ID_ELEMENT, 10)
            .request()
            .put(Entity.json(custs));
        assertEquals(200, response.getStatus());
        
        Response response2 = webTarget
            .register(adminAuth)
            .path(CUSTOMER_RESOURCE_NAME + RESOURCE_PATH_ID_PATH)
            .resolveTemplate(RESOURCE_PATH_ID_ELEMENT, 10)
            .request()
            .get();
        CustomerPojo custs3 = response2.readEntity(CustomerPojo.class);
        assertEquals("uu2@gamil.com",custs3.getEmail());
    }
    
    /**
     * Test 05 delete customer with admin.
     *
     * @throws JsonMappingException the json mapping exception
     * @throws JsonProcessingException the json processing exception
     */
    @Test
    public void test05_delete_customer_with_admin() throws JsonMappingException, JsonProcessingException{
//        CustomerPojo custs = new CustomerPojo();
//        custs.setFirstName("lee21");
//        custs.setEmail("uu21@gamil.com");
//        custs.setLastName("noon21");
//        custs.setPhoneNumber("88777778721");
//        Response response = webTarget
//            .register(adminAuth)
//            .path(CUSTOMER_RESOURCE_NAME+ RESOURCE_PATH_ID_PATH)
//            .resolveTemplate(RESOURCE_PATH_ID_ELEMENT, 10)
//            .request()
//            .post(Entity.json(custs));
//        assertEquals(200,response.getStatus());
//        CustomerPojo custs3 = response.readEntity(new GenericType<CustomerPojo>(){});
        
//        int id = custs.getId();
        Response response = webTarget
            .register(adminAuth)
            .path(CUSTOMER_RESOURCE_NAME + RESOURCE_PATH_ID_PATH)
            .resolveTemplate(RESOURCE_PATH_ID_ELEMENT, 17)
            .request()
            .delete();
        
        assertEquals(200, response.getStatus());
        
        Response response2 = webTarget
            .register(adminAuth)
            .path(CUSTOMER_RESOURCE_NAME + RESOURCE_PATH_ID_PATH)
            .resolveTemplate(RESOURCE_PATH_ID_ELEMENT, 17)
            .request()
            .get();
        CustomerPojo custs4 = response2.readEntity(new GenericType<CustomerPojo>(){});
        assertEquals(404, response2.getStatus());
    }
    
    /**
     * Test 06 create order admin.
     *
     * @throws JsonMappingException the json mapping exception
     * @throws JsonProcessingException the json processing exception
     */
    @Test
    public void test06_create_order_admin()  throws JsonMappingException, JsonProcessingException{
        OrderPojo or = new OrderPojo();
        or.setDescription("one");
        Response response = webTarget
            .register(adminAuth)
            .path( CUSTOMER_RESOURCE_NAME + CUSTOMER_ORDER_RESOURCE_PATH)
            .resolveTemplate(RESOURCE_PATH_ID_ELEMENT, 4)
            .request()
            .post(Entity.json(or));
        assertThat(response.getStatus(), is(200));
        CustomerPojo cust = response.readEntity(CustomerPojo.class);
        List<OrderPojo> orders = cust.getOrders();
        int orderIdx = orders.size() -1;
        
        assertEquals("one", cust.getOrders().get(orderIdx).getDescription());
       
    }
    
    /**
     * Test 07 get order by cust I D with adminrole.
     *
     * @throws JsonMappingException the json mapping exception
     * @throws JsonProcessingException the json processing exception
     */
    @Test
    public void test07_get_order_by_custID_with_adminrole()throws JsonMappingException, JsonProcessingException {
        Response response = webTarget
            .register(adminAuth)
            .path(CUSTOMER_RESOURCE_NAME + CUSTOMER_ORDER_RESOURCE_PATH)
            .resolveTemplate(RESOURCE_PATH_ID_ELEMENT, 3)
            .request()
            .get();
        assertThat(response.getStatus(), is(200));
        List<OrderPojo> or2 = response.readEntity(new GenericType<List<OrderPojo>>(){});
        assertThat(response.getStatus(), is(200));
        assertEquals(2,or2.size());
        
    }

    /**
     * Test 08 update order with admin.
     *
     * @throws JsonMappingException the json mapping exception
     * @throws JsonProcessingException the json processing exception
     */
    //did not test yet
    @Test
    public void test08_update_order_with_admin()throws JsonMappingException, JsonProcessingException {
        OrderPojo or = new OrderPojo();
        or.setDescription("two");
        Response response = webTarget
            .register(adminAuth)
            .path(ORDER_RESOURCE_NAME )
            .request()
            .post(Entity.json(or));
        
        OrderPojo or2 =  response.readEntity(new GenericType<OrderPojo>(){});
        assertEquals("two",or2.getDescription());
        or2.setDescription("change two");
        Response response2 = webTarget
            .register(adminAuth)
            .path(ORDER_RESOURCE_NAME )
            .request()
            .put(Entity.json(or2));
        assertEquals(200,response2.getStatus());
        OrderPojo  or3 = response.readEntity(new GenericType< OrderPojo >(){});
        assertEquals("change two",or3.getDescription());
    }
    
    /**
     * Test 09 delete order with admin.
     *
     * @throws JsonMappingException the json mapping exception
     * @throws JsonProcessingException the json processing exception
     */
    //not tested
    @Test
    public void test09_delete_order_with_admin() throws JsonMappingException, JsonProcessingException{
        OrderPojo or = new OrderPojo();
     
        or.setDescription("three");
        Response response = webTarget
            .register(adminAuth)
            .path(ORDER_RESOURCE_NAME )
            .request()
            .post(Entity.json(or));
        assertEquals(200,response.getStatus());
        OrderPojo or2 = response.readEntity(new GenericType<OrderPojo>(){});
        
        int id = or2.getId();
        Response response3 = webTarget
            .register(adminAuth)
            .path(ORDER_RESOURCE_NAME + "/"+id )
            .request()
            .delete();
        
        OrderPojo or3= response3.readEntity(new GenericType<OrderPojo>(){});
        assertEquals(200, response.getStatus());
        assertEquals("three", or3.getDescription());
    }
    
    /**
     * Test 10 create address admin.
     *
     * @throws JsonMappingException the json mapping exception
     * @throws JsonProcessingException the json processing exception
     */
    @Test
    public void test10_create_address_admin()  throws JsonMappingException, JsonProcessingException{
        BillingAddressPojo bs = new BillingAddressPojo();
        bs.setCity("ottawa");
        bs.setCountry("canada");
        bs.setState("ON");
        bs.setPostal("v8v n8u");
        bs.setStreet("boolyn");
        Response response = webTarget
            .register(adminAuth)
            .path(CUSTOMER_ADDRESS_SUBRESOURCE_NAME  )
            .request()
            .post(Entity.json(bs));
        assertThat(response.getStatus(), is(200));
        BillingAddressPojo bs2 = response.readEntity(new GenericType<BillingAddressPojo>(){});
        assertEquals("ottawa",bs2.getCity());
       
    }
    
    /**
     * Test 11 update cust billing addr by cust I D with adminrole.
     *
     * @throws JsonMappingException the json mapping exception
     * @throws JsonProcessingException the json processing exception
     */
    @Test
    public void test11_update_cust_billingAddr_by_custID_with_adminrole()throws JsonMappingException, JsonProcessingException {
        BillingAddressPojo bill = new BillingAddressPojo();
        bill.setCity("ottawa");
        bill.setCountry("canada");
        Response response = webTarget
            .register(adminAuth)
            .path(CUSTOMER_RESOURCE_NAME + CUSTOMER_ADDRESS_RESOURCE_PATH)
            .resolveTemplate(RESOURCE_PATH_ID_ELEMENT, 3)
            .request()
            .put(Entity.entity(bill, MediaType.APPLICATION_JSON_TYPE));
        assertThat(response.getStatus(), is(200));
        CustomerPojo or2 = response.readEntity(new GenericType<CustomerPojo>(){});
        assertEquals("ottawa",or2.getBillingAddress().getCity());//
        
    }
    
    /**
     * Test 12 update cust shipp addr by cust I D with adminrole.
     *
     * @throws JsonMappingException the json mapping exception
     * @throws JsonProcessingException the json processing exception
     */
    @Test
    public void test12_update_cust_shippAddr_by_custID_with_adminrole()throws JsonMappingException, JsonProcessingException {
        ShippingAddressPojo bill = new ShippingAddressPojo();
        bill.setCity("toronto");
        bill.setCountry("canada");
        Response response = webTarget
            .register(adminAuth)
            .path(CUSTOMER_RESOURCE_NAME + CUSTOMER_ADDRESS_RESOURCE_PATH)
            .resolveTemplate(RESOURCE_PATH_ID_ELEMENT, 3)
            .request()
            .put(Entity.entity(bill, MediaType.APPLICATION_JSON_TYPE));
        assertThat(response.getStatus(), is(200));
        CustomerPojo or2 = response.readEntity(new GenericType<CustomerPojo>(){});
        assertEquals("toronto",or2.getShippingAddress().getCity());//
        
    }

    /**
     * Test 13 update cust address with user.
     *
     * @throws JsonMappingException the json mapping exception
     * @throws JsonProcessingException the json processing exception
     */
    @Test
    public void test13_update_cust_address_with_user() throws JsonMappingException, JsonProcessingException{
        BillingAddressPojo bill = new BillingAddressPojo();
        bill.setCity("ottawa");
        bill.setCountry("canada");
        Response response = webTarget
            .register(userAuth)
            .path(CUSTOMER_RESOURCE_NAME + CUSTOMER_ADDRESS_RESOURCE_PATH)
            .resolveTemplate(RESOURCE_PATH_ID_ELEMENT, 3)
            .request()
            .put(Entity.entity(bill, MediaType.APPLICATION_JSON_TYPE));
        assertThat(response.getStatus(), is(403));
    }
    
    
   

    /**
     * Test 14 something.
     */
    public void test14_something() {
    }
    
    /**
     * Test 15 something.
     */
    public void test15_something() {
    }

    /**
     * Test 16 something.
     */
    public void test16_something() {
    }
    
    /**
     * Test 17 something.
     */
    public void test17_something() {
    }

    /**
     * Test 18 something.
     */
    public void test18_something() {
    }
    
    /**
     * Test 19 something.
     */
    public void test19_something() {
    }
    
    /**
     * Test 20 something.
     */
    public void test20_something() {
    }

    /**
     * Test 21 something.
     */
    public void test21_something() {
    }

    
    /**
     * Test 22 something.
     */
    public void test22_something() {
    }
    
    /**
     * Test 23 create order with admin role.
     *
     * @throws JsonMappingException the json mapping exception
     * @throws JsonProcessingException the json processing exception
     */
    @Test
    public void test23_create_order_with_adminRole () throws JsonMappingException, JsonProcessingException {
    }


    /**
     * Test 24 create customer by user.
     *
     * @throws JsonMappingException the json mapping exception
     * @throws JsonProcessingException the json processing exception
     */
    @Test
    public void test24_create_customer_by_user() throws JsonMappingException, JsonProcessingException{
        CustomerPojo custs = new CustomerPojo();
        custs.setFirstName("lee");
        custs.setEmail("uu@gamil.com");
        custs.setLastName("noon");
        custs.setPhoneNumber("887777787");
        Response response = webTarget
            .register(userAuth)
            .path(CUSTOMER_RESOURCE_NAME )
            .request()
            .post(Entity.json(custs));
        assertThat(response.getStatus(), is(403));
    }

    /**
     * Test 25 get customer by id with userrole.
     *
     * @throws JsonMappingException the json mapping exception
     * @throws JsonProcessingException the json processing exception
     */
    @Test
    public void test25_get_customer_by_id_with_userrole()throws JsonMappingException, JsonProcessingException {
        Response response = webTarget
//            .register(adminAuth)
            .register(userAuth)
            .path(CUSTOMER_RESOURCE_NAME + RESOURCE_PATH_ID_PATH)
            .resolveTemplate(RESOURCE_PATH_ID_ELEMENT, 1)
            .request()
            .get();
        assertThat(response.getStatus(), is(403));
    }
    
    /**
     * Test 26 get all customers with userrole.
     *
     * @throws JsonMappingException the json mapping exception
     * @throws JsonProcessingException the json processing exception
     */
    @Test
    public void test26_get_all_customers_with_userrole() throws JsonMappingException, JsonProcessingException {
        Response response = webTarget
            .register(userAuth)
//            .register(adminAuth)
            .path(CUSTOMER_RESOURCE_NAME)
            .request()
            .get();
        assertThat(response.getStatus(), is(403));
    }
    

    /**
     * Test 27 update order with user.
     */
    //not tested
    public void test27_update_order_with_user() {
        OrderPojo or = new OrderPojo();
        or.setDescription("two");
        Response response = webTarget
            .register(adminAuth)
            .path(ORDER_RESOURCE_NAME )
            .request()
            .post(Entity.json(or));
        
        OrderPojo or2 =  response.readEntity(new GenericType<OrderPojo>(){});
        assertEquals("two",or2.getDescription());
        or2.setDescription("change two");
        Response response2 = webTarget
            .register(adminAuth)
            .path(ORDER_RESOURCE_NAME )
            .request()
            .put(Entity.json(or2));
        assertEquals(200,response2.getStatus());
        OrderPojo  or3 = response.readEntity(new GenericType< OrderPojo >(){});
        assertEquals("change two",or3.getDescription());
    }
    

    /**
     * Test 28 create order with userrole.
     */
    @Test
    public void test28_create_order_with_userrole() {
        OrderPojo or = new OrderPojo();
        or.setDescription("one");
        Response response = webTarget
            .register(userAuth)
            .path( CUSTOMER_RESOURCE_NAME + CUSTOMER_ORDER_RESOURCE_PATH)
            .resolveTemplate(RESOURCE_PATH_ID_ELEMENT, 4)
            .request()
            .post(Entity.json(or));
        assertThat(response.getStatus(), is(403));
    }

    /**
     * Test 29 delete order with userrole.
     */
    @Test
    public void test29_delete_order_with_userrole() {
        Response response3 = webTarget
            .register(userAuth)
            .path(CUSTOMER_RESOURCE_NAME + CUSTOMER_ORDER_RESOURCE_PATH)
            .resolveTemplate(RESOURCE_PATH_ID_ELEMENT, 1)
            .request()
            .delete();
        assertEquals(401, response3.getStatus());
    }
    
    /**
     * Test 30 get order by cust I D with userole.
     *
     * @throws JsonMappingException the json mapping exception
     * @throws JsonProcessingException the json processing exception
     */
    @Test
    public void test30_get_order_by_custID_with_userole()throws JsonMappingException, JsonProcessingException {
        Response response = webTarget
            .register(userAuth)
            .path(CUSTOMER_RESOURCE_NAME + CUSTOMER_ORDER_RESOURCE_PATH)
            .resolveTemplate(RESOURCE_PATH_ID_ELEMENT, 3)
            .request()
            .get();
        assertThat(response.getStatus(), is(200));
    }

    /**
     * Test 31 create product with admin role.
     *
     * @throws JsonMappingException the json mapping exception
     * @throws JsonProcessingException the json processing exception
     */
    @Test
    public void test31_create_product_with_adminRole () throws JsonMappingException, JsonProcessingException{
        ProductPojo pp = new ProductPojo();
        pp.setDescription("goodProduct");
        
        Response response = webTarget
            .register(adminAuth)
            .path(PRODUCT_RESOURCE_NAME)
            .request()
            .post(Entity.entity(pp, MediaType.APPLICATION_JSON_TYPE));
        assertThat(response.getStatus(), is(200));
    }

    /**
     * Test 32 create product with user role.
     *
     * @throws JsonMappingException the json mapping exception
     * @throws JsonProcessingException the json processing exception
     */
    @Test
    public void test32_create_product_with_userRole() throws JsonMappingException, JsonProcessingException {
        ProductPojo pp = new ProductPojo();
        pp.setDescription("badProduct");
        
        Response response = webTarget
            .register(userAuth)
            .path(PRODUCT_RESOURCE_NAME)
            .request()
            .post(Entity.entity(pp, MediaType.APPLICATION_JSON_TYPE));
        assertThat(response.getStatus(), is(403));
    }
    
    /**
     * Test 33 admin get product by ID.
     *
     * @throws JsonMappingException the json mapping exception
     * @throws JsonProcessingException the json processing exception
     */
    @Test
    public void test33_admin_get_product_by_ID() throws JsonMappingException, JsonProcessingException{
        Response response = webTarget
            .register(adminAuth)
            .path(PRODUCT_RESOURCE_NAME+ RESOURCE_PATH_ID_PATH )
            .resolveTemplate(RESOURCE_PATH_ID_ELEMENT, 3)
            .request()
            .get();
        assertThat(response.getStatus(), is(200));
        ProductPojo pp = response.readEntity(ProductPojo.class);
        assertThat(pp.getDescription(), is("goodProduct"));


    }

    /**
     * Test 34 user get product by ID.
     *
     * @throws JsonMappingException the json mapping exception
     * @throws JsonProcessingException the json processing exception
     */
    @Test
    public void test34_user_get_product_by_ID() throws JsonMappingException, JsonProcessingException{
        Response response = webTarget
            .register(userAuth)
            .path(PRODUCT_RESOURCE_NAME + RESOURCE_PATH_ID_PATH)
            .resolveTemplate(RESOURCE_PATH_ID_ELEMENT, 3)
            .request()
            .get();
        assertThat(response.getStatus(), is(403));
    }
    
    /**
     * Test 35 admin get all product.
     *
     * @throws JsonMappingException the json mapping exception
     * @throws JsonProcessingException the json processing exception
     */
    @Test
    public void test35_admin_get_all_product() throws JsonMappingException, JsonProcessingException{
        Response response = webTarget
            .register(adminAuth)
            .path(PRODUCT_RESOURCE_NAME)
            .request()
            .get();
        assertThat(response.getStatus(), is(200));
        List<ProductPojo> custs = response.readEntity(new GenericType<List<ProductPojo>>(){});
        assertThat(custs, is(not(empty())));
        //TODO - depending on what is in your Db when you run this, you may need to change the next line
        assertThat(custs, hasSize(1));
    }

    /**
     * Test 36 user get all product.
     *
     * @throws JsonMappingException the json mapping exception
     * @throws JsonProcessingException the json processing exception
     */
    @Test
    public void test36_user_get_all_product() throws JsonMappingException, JsonProcessingException{
        Response response = webTarget
            .register(userAuth)
            .path(PRODUCT_RESOURCE_NAME )
            .request()
            .get();
        assertThat(response.getStatus(), is(200));
        List<ProductPojo> custs = response.readEntity(new GenericType<List<ProductPojo>>(){});
        assertThat(custs, is(not(empty())));
        //TODO - depending on what is in your Db when you run this, you may need to change the next line
        assertThat(custs, hasSize(1));
    }
    
    /**
     * Test 37 admin update product.
     *
     * @throws JsonMappingException the json mapping exception
     * @throws JsonProcessingException the json processing exception
     */
    @Test
    public void test37_admin_update_product() throws JsonMappingException, JsonProcessingException{
        ProductPojo prods = new ProductPojo();
        prods.setDescription("updateProduct");
        
        Response response = webTarget
            .register(adminAuth)
            .path(PRODUCT_RESOURCE_NAME  + RESOURCE_PATH_ID_PATH)
            .resolveTemplate(RESOURCE_PATH_ID_ELEMENT, 1)
            .request()
            .put(Entity.entity(prods, MediaType.APPLICATION_JSON_TYPE));
            ProductPojo updateProd = response.readEntity(new GenericType<ProductPojo>() {});
        assertThat(response.getStatus(), is(200));
        assertThat(updateProd.getDescription(), is("updateProduct"));
        
    }

    /**
     * Test 38 user update product.
     *
     * @throws JsonMappingException the json mapping exception
     * @throws JsonProcessingException the json processing exception
     */
    @Test
    public void test38_user_update_product() throws JsonMappingException, JsonProcessingException{
        ProductPojo prods = new ProductPojo();
        prods.setDescription("updateProduct");
        
        Response response = webTarget
            .register(userAuth)
            .path(PRODUCT_RESOURCE_NAME + RESOURCE_PATH_ID_PATH)
            .resolveTemplate(RESOURCE_PATH_ID_ELEMENT, 1)
            .request()
            .put(Entity.entity(prods, MediaType.APPLICATION_JSON_TYPE));
            ProductPojo updateProd = response.readEntity(new GenericType<ProductPojo>() {});
        assertThat(response.getStatus(), is(200));
        assertThat(updateProd.getDescription(), is("updateProduct"));
    }
    
    /**
     * Test 39 delete product.
     *
     * @throws JsonMappingException the json mapping exception
     * @throws JsonProcessingException the json processing exception
     */
    @Test
    public void test39_delete_product() throws JsonMappingException, JsonProcessingException{
        Response response = webTarget
            .register(adminAuth)
            .path(PRODUCT_RESOURCE_NAME + RESOURCE_PATH_ID_PATH)
            .resolveTemplate(RESOURCE_PATH_ID_ELEMENT, 26)
            .request()
            .delete();
        assertThat(response.getStatus(), is(200));
    }

}
