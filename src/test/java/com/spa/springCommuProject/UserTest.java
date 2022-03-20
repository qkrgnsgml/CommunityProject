package com.spa.springCommuProject;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserTest {

    @PersistenceContext
    private EntityManager em;

    @Test
    @Transactional
    @Rollback(value = false)
    public void DB확인() throws Exception{
        User user = new User();
        user.setName("phh");
        //user.setId(1L);

        em.persist(user);
    }

}