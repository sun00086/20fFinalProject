/*****************************************************************c******************o*******v******id********
 * File: CustomerService.java
 * Course materials (20F) CST 8277
 *
 * @author (original) Mike Norman
 * 
 * (Modified) @author Feng Sun,Wang Peng,Yang Shuting
 *
 */
package com.algonquincollege.cst8277.ejb;

import static com.algonquincollege.cst8277.models.SecurityRole.ROLE_BY_NAME_QUERY;
import static com.algonquincollege.cst8277.utils.MyConstants.DEFAULT_KEY_SIZE;
import static com.algonquincollege.cst8277.utils.MyConstants.DEFAULT_PROPERTY_ALGORITHM;
import static com.algonquincollege.cst8277.utils.MyConstants.DEFAULT_PROPERTY_ITERATIONS;
import static com.algonquincollege.cst8277.utils.MyConstants.DEFAULT_SALT_SIZE;
import static com.algonquincollege.cst8277.utils.MyConstants.DEFAULT_USER_PASSWORD;
import static com.algonquincollege.cst8277.utils.MyConstants.DEFAULT_USER_PREFIX;
import static com.algonquincollege.cst8277.utils.MyConstants.PARAM1;
import static com.algonquincollege.cst8277.utils.MyConstants.PROPERTY_ALGORITHM;
import static com.algonquincollege.cst8277.utils.MyConstants.PROPERTY_ITERATIONS;
import static com.algonquincollege.cst8277.utils.MyConstants.PROPERTY_KEYSIZE;
import static com.algonquincollege.cst8277.utils.MyConstants.PROPERTY_SALTSIZE;
import static com.algonquincollege.cst8277.utils.MyConstants.USER_ROLE;

import static com.algonquincollege.cst8277.models.CustomerPojo.ALL_CUSTOMERS_QUERY_NAME;
import static com.algonquincollege.cst8277.models.CustomerPojo.FIND_CUSTOMERS_QUERY_ID; //changed
//import static com.algonquincollege.cst8277.models.SecurityUser.SECURITY_USER_BY_NAME_QUERY; //changed


import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.Singleton;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
//import javax.transaction.Transactional;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.security.enterprise.identitystore.Pbkdf2PasswordHash;
import javax.transaction.Transactional;

import com.algonquincollege.cst8277.models.AddressPojo;
import com.algonquincollege.cst8277.models.CustomerPojo;
import com.algonquincollege.cst8277.models.OrderLinePojo;
import com.algonquincollege.cst8277.models.OrderPojo;
import com.algonquincollege.cst8277.models.OrderPojo_;
import com.algonquincollege.cst8277.models.ProductPojo;
import com.algonquincollege.cst8277.models.ProductPojo_;
import com.algonquincollege.cst8277.models.SecurityRole;
import com.algonquincollege.cst8277.models.SecurityUser;
import com.algonquincollege.cst8277.models.ShippingAddressPojo;
import com.algonquincollege.cst8277.models.StorePojo;
import com.algonquincollege.cst8277.models.StorePojo_;

// TODO: Auto-generated Javadoc
/**
 * Stateless Singleton Session Bean - CustomerService.
 */
@Singleton
public class CustomerService implements Serializable {
    
    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;
    
    /** The Constant CUSTOMER_PU. */
    public static final String CUSTOMER_PU = "20f-groupProject-PU";

    /** The em. */
    @PersistenceContext(name = CUSTOMER_PU)
    protected EntityManager em;

    /** The pb andj password hash. */
    @Inject
    protected Pbkdf2PasswordHash pbAndjPasswordHash;
    
    //TODO
/**
     * Gets the all customers.
     *
     * @return the all customers
     */
    //--------------------------------------------------------------------------------------------------------------
    public List<CustomerPojo> getAllCustomers() {
        //changed
        return em.createNamedQuery(ALL_CUSTOMERS_QUERY_NAME, CustomerPojo.class).getResultList();
    }

    /**
     * Gets the customer by id.
     *
     * @param custPK the cust PK
     * @return the customer by id
     */
    public CustomerPojo getCustomerById(int custPK) {    //changed
        CustomerPojo cust = null;
        try {
            cust = em.createNamedQuery(FIND_CUSTOMERS_QUERY_ID, CustomerPojo.class)
            .setParameter(PARAM1,custPK)
            .getSingleResult();
            return cust;
        }
        catch (Exception e) {
            return null;
        }
       
    }
    
    /**
     * Persist customer.
     *
     * @param newCustomer the new customer
     * @return the customer pojo
     */
    @Transactional
    public CustomerPojo persistCustomer(CustomerPojo newCustomer) {
        em.persist(newCustomer);
        em.flush();
        return newCustomer;
    }
    
    /**
     * update customer.
     *
     * @param id the id
     * @param updatedcustomer the updatedcustomer
     */
    @Transactional
    public void updateCustomerById(int id, CustomerPojo updatedcustomer) {
        CustomerPojo cust = em.find(CustomerPojo.class, id);
        if(cust!=null) {
            cust.setFirstName(updatedcustomer.getFirstName());
            cust.setLastName(updatedcustomer.getLastName());
            cust.setEmail(updatedcustomer.getEmail());
            cust.setPhoneNumber(updatedcustomer.getPhoneNumber());
            cust.setBillingAddress(updatedcustomer.getBillingAddress());
            cust.setShippingAddress(updatedcustomer.getShippingAddress());
//            cust.setOrders(cust.getOrders());
            em.merge(cust);
        }
    }
    
    /**
     * Delete customer by id.
     *
     * @param id the id
     */
    @Transactional
    public void deleteCustomerById(int id) {
        CustomerPojo cust = em.find(CustomerPojo.class, id);
        SecurityUser user = em.createQuery("select c from SecurityUser c where c.customer.id=:param1", SecurityUser.class).setParameter(PARAM1, id).getSingleResult();
        if(user !=null) {
            user.setCustomer(null);
        }
        if (cust != null) {
            em.merge(user);
            em.remove(cust);
        }
    }
    
  /**
   * Builds the user for new customer.
   *
   * @param newCustomerWithIdTimestamps the new customer with id timestamps
   */
  //--------------------------------------------------------------------------------------------------------------
    @Transactional
    public void buildUserForNewCustomer(CustomerPojo newCustomerWithIdTimestamps) {
        SecurityUser userForNewCustomer = new SecurityUser();
        userForNewCustomer.setUsername(DEFAULT_USER_PREFIX + "" + newCustomerWithIdTimestamps.getId());
        Map<String, String> pbAndjProperties = new HashMap<>();
        pbAndjProperties.put(PROPERTY_ALGORITHM, DEFAULT_PROPERTY_ALGORITHM);
        pbAndjProperties.put(PROPERTY_ITERATIONS, DEFAULT_PROPERTY_ITERATIONS);
        pbAndjProperties.put(PROPERTY_SALTSIZE, DEFAULT_SALT_SIZE);
        pbAndjProperties.put(PROPERTY_KEYSIZE, DEFAULT_KEY_SIZE);
        pbAndjPasswordHash.initialize(pbAndjProperties);
        String pwHash = pbAndjPasswordHash.generate(DEFAULT_USER_PASSWORD.toCharArray());
        userForNewCustomer.setPwHash(pwHash);
        userForNewCustomer.setCustomer(newCustomerWithIdTimestamps);
        SecurityRole userRole = em.createNamedQuery(ROLE_BY_NAME_QUERY,
            SecurityRole.class).setParameter(PARAM1, USER_ROLE).getSingleResult();
        userForNewCustomer.getRoles().add(userRole);
        userRole.getUsers().add(userForNewCustomer);
        em.persist(userForNewCustomer);
    }

    /**
     * Sets the address for.
     *
     * @param custId the cust id
     * @param newAddress the new address
     * @return the customer pojo
     */
    @Transactional
    public CustomerPojo setAddressFor(int custId, AddressPojo newAddress) {
        CustomerPojo updatedCustomer = em.find(CustomerPojo.class, custId);
        if (newAddress instanceof ShippingAddressPojo) {
            updatedCustomer.setShippingAddress(newAddress);
        }
        else {
            updatedCustomer.setBillingAddress(newAddress);
        }
        em.merge(updatedCustomer);
        return updatedCustomer;
    }

    
    
    /**
     * Gets the all products.
     *
     * @return the all products
     */
    //---------------------------------------------------------------------------------------------------------------------------
    public List<ProductPojo> getAllProducts() {
        //example of using JPA Criteria query instead of JPQL
        try {
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<ProductPojo> q = cb.createQuery(ProductPojo.class);
            Root<ProductPojo> c = q.from(ProductPojo.class);
            q.select(c);
            TypedQuery<ProductPojo> q2 = em.createQuery(q);
            List<ProductPojo> products = q2.getResultList();
            return products;
        }
        catch (Exception e) {
            return null;
        }
    }

    /**
     * Gets the product by id.
     *
     * @param prodId the prod id
     * @return the product by id
     */
    public ProductPojo getProductById(int prodId) {
        ProductPojo product = null;
        try {
//            CriteriaBuilder cb = em.getCriteriaBuilder();
//            CriteriaQuery<ProductPojo> q1 = cb.createQuery(ProductPojo.class);
//            Root<ProductPojo> root = q1.from(ProductPojo.class);
//            q1.where(cb.equal((root.get(ProductPojo_.id)),prodId));
//
//            TypedQuery<ProductPojo> tq = em.createQuery(q1);
//            ProductPojo product = tq.getSingleResult();
            product = em.createQuery("select p from Product p where p.id = :param1", ProductPojo.class)
                .setParameter(PARAM1, prodId)
                .getSingleResult();

            return product;
        }
        catch (Exception e) {
            return null;
        }
    }
    
    /**
     * Delete product.
     *
     * @param custId the cust id
     * @return true, if successful
     */
    @Transactional
    public boolean deleteProduct(int custId) {
        ProductPojo cust = em.find(ProductPojo.class, custId);
        boolean delete = false;
        try {
            if(cust != null) {
                em.remove(cust);
                delete= true;
            }
            return delete;

        }catch(Exception e){

            delete= false;
            return delete;
        }
    }
    
    /**
     * Update product by id.
     *
     * @param id the id
     * @param updatedcustomer the updatedcustomer
     */
    @Transactional
    public void updateProductById(int id, ProductPojo updatedcustomer) {
        ProductPojo cust = em.find(ProductPojo.class, id);
        if(cust!=null) {
            cust.setDescription(updatedcustomer.getDescription());
            cust.setSerialNo(updatedcustomer.getSerialNo());
            em.merge(cust);
        }
    }
    
    /**
     * Persist product.
     *
     * @param newCustomer the new customer
     * @return the product pojo
     */
    @Transactional
    public ProductPojo persistProduct(ProductPojo newCustomer) {
        em.persist(newCustomer);
        em.flush();
        return newCustomer;
    }
    
  //---------------------------------------------------------------------------------------------------------------------------
    
    /**
   * Gets the all stores.
   *
   * @return the all stores
   */
  public List<StorePojo> getAllStores() {
        try {
//            CriteriaBuilder cb = em.getCriteriaBuilder();
//            CriteriaQuery<StorePojo> q = cb.createQuery(StorePojo.class);
//            Root<StorePojo> c = q.from(StorePojo.class);
//            q.select(c);
//            TypedQuery<StorePojo> q2 = em.createQuery(q);
//            List<StorePojo> stores = q2.getResultList();
            List<StorePojo> stores = em.createQuery("select s from Stores s", StorePojo.class)
                .getResultList();
            return stores;
        }
        catch (Exception e) {
            return null;
        }
    }

    /**
     * Gets the store by id.
     *
     * @param id the id
     * @return the store by id
     */
    public StorePojo getStoreById(int id) {
        StorePojo store = null;
        try {
//            CriteriaBuilder cb = em.getCriteriaBuilder();
//            CriteriaQuery<StorePojo> q1 = cb.createQuery(StorePojo.class);
//            Root<StorePojo> root = q1.from(StorePojo.class);
//            q1.where(cb.equal((root.get(StorePojo_.id)),id));
//
//            TypedQuery<StorePojo> tq = em.createQuery(q1);
//            StorePojo product = tq.getSingleResult();
            store = em.createQuery("select s from Stores s where s.id = :param1", StorePojo.class)
                .setParameter(PARAM1, id)
                .getSingleResult();
//            System.out.println(product);
            return store;
        }
        catch (Exception e) {
            return null;
        }
    }
    
    /**
     * Delete store.
     *
     * @param custId the cust id
     * @return true, if successful
     */
    @Transactional
    public boolean deleteStore(int custId) {
        StorePojo cust = em.find(StorePojo.class, custId);
        boolean delete = false;
        try {
            if(cust != null) {
                em.remove(cust);
                delete= true;
            }
            return delete;

        }catch(Exception e){

            delete= false;
            return delete;
        }
    }
    
    /**
     * Update store by id.
     *
     * @param id the id
     * @param updatedcustomer the updatedcustomer
     */
    @Transactional
    public void updateStoreById(int id, StorePojo updatedcustomer) {
        StorePojo cust = em.find(StorePojo.class, id);
        if (cust != null) {
            cust.setStoreName(updatedcustomer.getStoreName());
            em.merge(cust);
        }
    }
    
    /**
     * Persist store.
     *
     * @param newCustomer the new customer
     * @return the store pojo
     */
    @Transactional
    public StorePojo persistStore(StorePojo newCustomer) {
        em.persist(newCustomer);
        em.flush();
        return newCustomer;
    }
    
    //---------------------------------------------------------------------------------------------------------
    /**
     * Gets the all orders.
     *
     * @return the all orders
     */
    /*
    
    public OrderPojo getAllOrders ... getOrderbyId ... build Orders with OrderLines ...
     
    */
    public List<OrderPojo> getAllOrders() {
        try {
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<OrderPojo> q = cb.createQuery(OrderPojo.class);
            Root<OrderPojo> c = q.from(OrderPojo.class);
            q.select(c);
            TypedQuery<OrderPojo> q2 = em.createQuery(q);
            List<OrderPojo> orders = q2.getResultList();
            return orders;
        }
        catch (Exception e) {
            return null;
        }
    }
    
    /**
     * Gets the order by id.
     *
     * @param orderId the order id
     * @return the order by id
     */
    public OrderPojo getOrderById(int orderId) {
        try {
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<OrderPojo> q1 = cb.createQuery(OrderPojo.class);
            Root<OrderPojo> root = q1.from(OrderPojo.class);
            q1.where(cb.equal((root.get(OrderPojo_.id)), orderId));
            
            TypedQuery<OrderPojo> tq = em.createQuery(q1);
            OrderPojo order = tq.getSingleResult();
            return order;
        }
        catch (Exception e) {
            return null;
        }
    }
    
    /**
     * Persist order.
     *
     * @param newCustomer the new customer
     * @return the order pojo
     */
    @Transactional
    public OrderPojo persistOrder(OrderPojo newCustomer) {
        em.persist(newCustomer);
        em.flush();
        return newCustomer;
    }
    
    /**
     * Delete order by id.
     *
     * @param custId the cust id
     * @return true, if successful
     */
    @Transactional
    public boolean deleteOrderById(int custId) {
        OrderPojo cust = em.find(OrderPojo.class, custId);
        boolean delete = false;
        try {
            if(cust != null) {
                em.remove(cust);
                delete= true;
            }
            return delete;

        }catch(Exception e){

            delete= false;
            return delete;
        }
    }
    
    /**
     * Update order by id.
     *
     * @param id the id
     * @param updatedcustomer the updatedcustomer
     */
    @Transactional
    public void updateOrderById(int id, OrderPojo updatedcustomer) {
        OrderPojo cust = em.find(OrderPojo.class, id);
        if(cust!=null) {
            cust.setDescription(updatedcustomer.getDescription());
            em.merge(cust);
        }
    }
    
    
    
    /**
     * Persist customer order.
     *
     * @param id the id
     * @param newOrder the new order
     * @return the customer pojo
     */
    //--------------------------------------------------------------------------------------------------------
    @Transactional
    public CustomerPojo persistCustomerOrder(int id, OrderPojo newOrder) {
        CustomerPojo cust = em.find(CustomerPojo.class, id);
        if(cust!=null) {
            cust.addOrders(newOrder);
            em.persist(newOrder);
            em.flush();
            em.merge(cust);
        }
        return cust;
    }
    
    /**
     * Gets the customer all orders.
     *
     * @param id the id
     * @return the customer all orders
     */
    @Transactional
    public List<OrderPojo> getCustomerAllOrders(int id) {
        CustomerPojo cust = em.find(CustomerPojo.class, id);

        return cust.getOrders();
    }
    
    /**
     * Gets the customer order by id.
     *
     * @param id the id
     * @param orderid the orderid
     * @return the customer order by id
     */
    @Transactional
    public OrderPojo getCustomerOrderById (int id, int orderid) {
        CustomerPojo cust = em.find(CustomerPojo.class, id);
        OrderPojo order = em.find(OrderPojo.class, orderid);
        CustomerPojo owner=order.getOwningCustomer();
        if (owner!=null && owner.equals(cust))
            return order;
        return null;
    }
    
    /**
     * Update customer order by id.
     *
     * @param id the id
     * @param orderid the orderid
     * @param neworder the neworder
     * @return the order pojo
     */
    @Transactional
    public OrderPojo updateCustomerOrderById(int id, int orderid, OrderPojo neworder) {
        OrderPojo order = getCustomerOrderById (id,orderid);
        if (order!=null) {
            order.setDescription(neworder.getDescription());
            em.merge(order);
        }
        return order;
    }
    
    /**
     * Delete customer order by id.
     *
     * @param id the id
     * @param orderid the orderid
     * @return the order pojo
     */
    @Transactional
    public OrderPojo deleteCustomerOrderById(int id, int orderid) {
        OrderPojo order = getCustomerOrderById (id,orderid);
        if(order!=null) {
            em.remove(order);
            return order;
        }
        return null;
    }
    //---------------------------------------------------------------------------------------------------------
    
    /**
     * Gets the customer order line by id.
     *
     * @param id the id
     * @param orderid the orderid
     * @return the customer order line by id
     */
    @Transactional
    public List<OrderLinePojo> getCustomerOrderLineById(int id, int orderid) {
        OrderPojo order = getCustomerOrderById (id,orderid);
        List<OrderLinePojo> line = null;
        if(order!=null) {
            line = order.getOrderlines();
        }
        return line;
    }
    
    /**
     * Persist order line.
     *
     * @param id the id
     * @param orderid the orderid
     * @param line the line
     * @return the order pojo
     */
    @Transactional
    public OrderPojo persistOrderLine(int id, int orderid, OrderLinePojo line) {
        
        OrderPojo order = getCustomerOrderById (id,orderid);
//        ProductPojo p = getProductById(line.getProduct().getId());
     
//        line.setProduct(p);
        order.addOrderline(line);
        em.persist(line);
        em.flush();
//        CustomerPojo cust=order.getOwningCustomer();
        em.merge(order);
//        em.merge(cust);
        return order;
    }
    
    
}