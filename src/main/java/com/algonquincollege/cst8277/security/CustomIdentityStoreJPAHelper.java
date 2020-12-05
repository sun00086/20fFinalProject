/*****************************************************************c******************o*******v******id********
 * File: CustomIdentityStoreJPAHelper.java
 * Course materials (20F) CST 8277
 *
 * @author (original) Mike Norman
 * 
 * (Modified) @author Feng Sun,Wang Peng,Yang Shuting
 */
package com.algonquincollege.cst8277.security;

import static com.algonquincollege.cst8277.models.SecurityUser.SECURITY_USER_BY_NAME_QUERY;
import static com.algonquincollege.cst8277.utils.MyConstants.PARAM1;
import static com.algonquincollege.cst8277.utils.MyConstants.PU_NAME;
import static java.util.Collections.emptySet;

import java.util.Set;
import java.util.stream.Collectors;

import javax.ejb.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import com.algonquincollege.cst8277.models.SecurityRole;
import com.algonquincollege.cst8277.models.SecurityUser;


/**
 * The Class CustomIdentityStoreJPAHelper.
 */
/*
 * Stateless Session bean should also be a Singleton
 */
@Singleton
public class CustomIdentityStoreJPAHelper {

    /** The Constant CUSTOMER_PU. */
    public static final String CUSTOMER_PU = "20f-groupProject-PU";

    /** The em. */
    @PersistenceContext(name = CUSTOMER_PU)
    
    protected EntityManager em;

    /**
     * Find user by name.
     *
     * @param username the username
     * @return the security user
     */
    public SecurityUser findUserByName(String username) {
        SecurityUser user = null;
        try {
            //TODO
            TypedQuery<SecurityUser> query = em.createNamedQuery(SECURITY_USER_BY_NAME_QUERY, SecurityUser.class);
            query.setParameter(PARAM1, username);
            user = query.getSingleResult();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return user;
    }

    /**
     * Find role names for user.
     *
     * @param username the username
     * @return the sets the
     */
    public Set<String> findRoleNamesForUser(String username) {
        Set<String> roleNames = emptySet();
        SecurityUser securityUser = findUserByName(username);
        if (securityUser != null) {
            roleNames = securityUser.getRoles().stream().map(s -> s.getRoleName()).collect(Collectors.toSet());
        }
       
        return roleNames;
    }

    /**
     * Save security user.
     *
     * @param user the user
     */
    @Transactional
    public void saveSecurityUser(SecurityUser user) {
        //TODO
        SecurityUser addUser = new SecurityUser();
        addUser.setPwHash(user.getPwHash());
        addUser.setRoles(user.getRoles());
        addUser.setUsername(user.getUsername());
        addUser.setCustomer(user.getCustomer());
        em.persist(user);
    }

    /**
     * Save security role.
     *
     * @param role the role
     */
    @Transactional
    public void saveSecurityRole(SecurityRole role) {
        //TODO
        SecurityRole addRole = new SecurityRole();
        addRole.setRoleName(role.getRoleName());
        addRole.setUsers(role.getUsers());
        em.persist(role);
    }
}