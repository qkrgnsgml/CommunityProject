package com.spa.springCommuProject.domain.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UserRepository {


    private final EntityManager em;

    public void save(User user){
        em.persist(user);
    }

    public User findOne(Long id){
        return em.find(User.class, id);
    }

    public List<User> findAll(){
        return em.createQuery("select u from User u", User.class).getResultList();
    }

    public List<User> findByNickName(String nickName){
        return em.createQuery("select u from User u where u.nickName = :nickName",User.class)
                .setParameter("nickName",nickName)
                .getResultList();
    }

    public Optional<User> findByLoginId(String loginId){
        return findAll().stream().filter(m->m.getLoginId().equals(loginId)).findFirst();
    }

    public List<User> findUsersSumDesc(){
        return em.createQuery("select u from User u where u.available = :available order by u.bigThreePower.sum desc", User.class)
                .setParameter("available", true)
                .setFirstResult(0)
                .setMaxResults(10)
                .getResultList();
    }
}
