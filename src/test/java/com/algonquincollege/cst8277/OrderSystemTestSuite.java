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
import static com.algonquincollege.cst8277.utils.MyConstants.CUSTOMER_RESOURCE_NAME;
import static com.algonquincollege.cst8277.utils.MyConstants.ORDER_RESOURCE_NAME;
import static com.algonquincollege.cst8277.utils.MyConstants.CUSTOMER_ADDRESS_SUBRESOURCE_NAME;
import static com.algonquincollege.cst8277.utils.MyConstants.DEFAULT_ADMIN_USER;
import static com.algonquincollege.cst8277.utils.MyConstants.DEFAULT_ADMIN_USER_PASSWORD;
import static com.algonquincollege.cst8277.utils.MyConstants.DEFAULT_USER_PASSWORD;
import static com.algonquincollege.cst8277.utils.MyConstants.DEFAULT_USER_PREFIX;
import static com.algonquincollege.cst8277.utils.MyConstants.RESOURCE_PATH_ID_PATH;
import static com.algonquincollege.cst8277.utils.MyConstants.RESOURCE_PATH_ID_ELEMENT;

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
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

@TestMethodOrder(MethodOrderer.Alphanumeric.class)
public class OrderSystemTestSuite {
    private static final Class<?> _thisClaz = MethodHandles.lookup().lookupClass();
    private static final Logger logger = LoggerFactory.getLogger(_thisClaz);

    static final String APPLICATION_CONTEXT_ROOT = "rest-orderSystem";
    static final String HTTP_SCHEMA = "http";
    static final String HOST = "localhost";
    
    //TODO - if you changed your Payara's default port (to say for example 9090)
    //       your may need to alter this constant
//    static final int PORT = 9090;
    static final int PORT = 8080;

    // test fixture(s)
    static URI uri;
    static HttpAuthenticationFeature adminAuth;
    static HttpAuthenticationFeature userAuth;

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

    protected WebTarget webTarget;
    @BeforeEach
    public void setUp() {
        Client client = ClientBuilder.newClient(
            new ClientConfig().register(MyObjectMapperProvider.class).register(new LoggingFeature()));
        webTarget = client.target(uri);
    }

//    @Test
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
    // ClientBuilder APIs

    public void test02_customer_by_id_with_adminrole()throws JsonMappingException, JsonProcessingException {
        Response response = webTarget
            .register(adminAuth)
            .path(CUSTOMER_RESOURCE_NAME +"/1")
            .request()
            .get();
        assertThat(response.getStatus(), is(200));
        CustomerPojo custs = response.readEntity(new GenericType<CustomerPojo>(){});
        assertEquals(1,custs.getId());
        assertEquals("John",custs.getFirstName());//
        
    }
    
    public void test03_create_customer_admin()  throws JsonMappingException, JsonProcessingException{
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

    public void test04_update_customer_with_admin()throws JsonMappingException, JsonProcessingException {
        CustomerPojo custs = new CustomerPojo();
        custs.setFirstName("lee2");
        custs.setEmail("uu2@gamil.com");
        custs.setLastName("noon2");
        custs.setPhoneNumber("8877777872");
        Response response = webTarget
            .register(adminAuth)
            .path(CUSTOMER_RESOURCE_NAME )
            .request()
            .post(Entity.json(custs));
        
        CustomerPojo custs2 = response.readEntity(new GenericType<CustomerPojo>(){});
        assertEquals("lee2",custs2.getFirstName());
        
        custs2.setEmail("uu3@gamil.com");
        
        Response response2 = webTarget
            .register(adminAuth)
            .path(CUSTOMER_RESOURCE_NAME )
            .request()
            .put(Entity.json(custs2));
        assertEquals(200,response2.getStatus());
        CustomerPojo custs3 = response.readEntity(new GenericType<CustomerPojo>(){});
        assertEquals("uu3@gamil.com",custs3.getEmail());
    }
    
    public void test05_delete_customer_with_admin() throws JsonMappingException, JsonProcessingException{
        CustomerPojo custs = new CustomerPojo();
        custs.setFirstName("lee21");
        custs.setEmail("uu21@gamil.com");
        custs.setLastName("noon21");
        custs.setPhoneNumber("88777778721");
        Response response = webTarget
            .register(adminAuth)
            .path(CUSTOMER_RESOURCE_NAME )
            .request()
            .post(Entity.json(custs));
        assertEquals(200,response.getStatus());
        CustomerPojo custs3 = response.readEntity(new GenericType<CustomerPojo>(){});
        
        int id = custs.getId();
        Response response3 = webTarget
            .register(adminAuth)
            .path(CUSTOMER_RESOURCE_NAME + "/"+id )
            .request()
            .delete();
        
        CustomerPojo custs4 = response3.readEntity(new GenericType<CustomerPojo>(){});
        assertEquals(200, response.getStatus());
        assertEquals("lee21", custs4.getFirstName());
    }
    
    public void test06_create_order_admin()  throws JsonMappingException, JsonProcessingException{
        OrderPojo or = new OrderPojo();
        or.setDescription("one");
        Response response = webTarget
            .register(adminAuth)
            .path(ORDER_RESOURCE_NAME  )
            .request()
            .post(Entity.json(or));
        assertThat(response.getStatus(), is(200));
        OrderPojo or2 = response.readEntity(new GenericType<OrderPojo>(){});
        assertEquals("one",or2.getDescription());
       
    }
    public void test07_order_by_id_with_adminrole()throws JsonMappingException, JsonProcessingException {
        Response response = webTarget
            .register(adminAuth)
            .path(ORDER_RESOURCE_NAME +"/1")
            .request()
            .get();
        assertThat(response.getStatus(), is(200));
        OrderPojo or2 = response.readEntity(new GenericType<OrderPojo>(){});
        assertEquals(1,or2.getId());
        assertEquals("one",or2.getDescription());//
        
    }

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
    public void test09_delete_customer_with_admin() throws JsonMappingException, JsonProcessingException{
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
    public void test11_address_by_id_with_adminrole()throws JsonMappingException, JsonProcessingException {
        Response response = webTarget
            .register(adminAuth)
            .path(CUSTOMER_ADDRESS_SUBRESOURCE_NAME  +"/1")
            .request()
            .get();
        assertThat(response.getStatus(), is(200));
        BillingAddressPojo or2 = response.readEntity(new GenericType<BillingAddressPojo>(){});
        assertEquals(1,or2.getId());
        assertEquals("ottawa",or2.getCity());//
        
    }
    
    public void test12_update_address_with_admin()throws JsonMappingException, JsonProcessingException {
        BillingAddressPojo bs = new BillingAddressPojo();
     
        bs.setCity("ottawas");
        bs.setCountry("canadas");
        bs.setState("ONs");
        bs.setPostal("v8v n8us");
        bs.setStreet("boolyns");
        
        Response response = webTarget
            .register(adminAuth)
            .path(CUSTOMER_ADDRESS_SUBRESOURCE_NAME  )
            .request()
            .post(Entity.json(bs));
        
        BillingAddressPojo bs2 =  response.readEntity(new GenericType<BillingAddressPojo>(){});
        assertEquals("ottawas",bs2.getCity());
        bs2.setCity("salt city");
        Response response2 = webTarget
            .register(adminAuth)
            .path(CUSTOMER_ADDRESS_SUBRESOURCE_NAME  )
            .request()
            .put(Entity.json(bs2));
        assertEquals(200,response2.getStatus());
        BillingAddressPojo  bs3 = response.readEntity(new GenericType< BillingAddressPojo >(){});
        assertEquals("salt city",bs3.getCity());
    }

    public void test13_delete_address_with_admin() throws JsonMappingException, JsonProcessingException{

        BillingAddressPojo bs = new BillingAddressPojo();
        bs.setCity("ottawase");
        bs.setCountry("canadase");
        bs.setState("ONse");
        bs.setPostal("v8v n8use");
        bs.setStreet("boolynse");
        
        Response response = webTarget
            .register(adminAuth)
            .path(CUSTOMER_ADDRESS_SUBRESOURCE_NAME)
            .request()
            .post(Entity.json(bs));
        assertEquals(200,response.getStatus());
        BillingAddressPojo bs2 = response.readEntity(new GenericType<BillingAddressPojo>(){});
        
        int id = bs2.getId();
        Response response3 = webTarget
            .register(adminAuth)
            .path(CUSTOMER_ADDRESS_SUBRESOURCE_NAME + "/"+id )
            .request()
            .delete();
        
        BillingAddressPojo bs3= response3.readEntity(new GenericType<BillingAddressPojo>(){});
        assertEquals(200, response.getStatus());
        assertEquals("ottawase", bs3.getCity());
    }
    
    
    
   

    public void test14_something() {
    }
    
    public void test15_something() {
    }

    public void test16_something() {
    }
    
    public void test17_something() {
    }

    public void test18_something() {
    }
    
    public void test19_something() {
    }
    
    public void test20_something() {
    }

    public void test21_something() {
    }

    public void test22_something() {
    }
    
    public void test23_something() {
    }

    public void test24_something() {
    }
    
    public void test25_something() {
    }

    public void test26_something() {
    }
    
    public void test27_something() {
    }

    public void test28_something() {
    }
    
    public void test29_something() {
    }
    
    public void test30_something() {
    }

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

    @Test
    public void test32_create_product_with_userRole() throws JsonMappingException, JsonProcessingException {
        ProductPojo pp = new ProductPojo();
        pp.setDescription("badProduct");
        
        Response response = webTarget
            .register(userAuth)
            .path(PRODUCT_RESOURCE_NAME)
            .request()
            .post(Entity.entity(pp, MediaType.APPLICATION_JSON_TYPE));
        assertThat(response.getStatus(), is(401));
    }
    
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

    @Test
    public void test34_user_get_product_by_ID() throws JsonMappingException, JsonProcessingException{
        Response response = webTarget
            .register(userAuth)
            .path(PRODUCT_RESOURCE_NAME + RESOURCE_PATH_ID_PATH)
            .resolveTemplate(RESOURCE_PATH_ID_ELEMENT, 3)
            .request()
            .get();
        assertThat(response.getStatus(), is(401));
    }
    
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