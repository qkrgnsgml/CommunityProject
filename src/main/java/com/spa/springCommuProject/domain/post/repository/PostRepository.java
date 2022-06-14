package com.spa.springCommuProject.domain.post.repository;


import com.spa.springCommuProject.domain.post.entity.FreePost;
import com.spa.springCommuProject.domain.post.entity.PhotoPost;
import com.spa.springCommuProject.domain.post.entity.Post;
import com.spa.springCommuProject.domain.post.entity.VideoPost;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class PostRepository {

    private final EntityManager em;

    public void save(Post post) {
        em.persist(post);
    }

    public Post findOnePost(Long id) {
        return em.find(Post.class, id);
    }

    public List<Post> findAvailableAll(){
        return em.createQuery("select p from Post p", Post.class).getResultList().stream().filter(x->x.getAvailable()==true).collect(Collectors.toList());
    }

    public List<PhotoPost> findAvailableAllPhotoPost(){
        return em.createQuery("select p from PhotoPost p", PhotoPost.class).getResultList().stream().filter(x->x.getAvailable()==true).collect(Collectors.toList());
    }

    public List<PhotoPost> findAvailableAllPhotoPostViewDesc(){
        return em.createQuery("select p from PhotoPost p where p.available = :available order by p.view desc", PhotoPost.class)
                .setParameter("available", true)
                .setFirstResult(0)
                .setMaxResults(3)
                .getResultList();
    }
    public List<PhotoPost> findAvailablePagingPhotoPosts(int page){
        return em.createQuery("select p from PhotoPost p where p.available = :available order by p.createdDate desc", PhotoPost.class)
                .setParameter("available",true)
                .setFirstResult(page*10)
                .setMaxResults(page*10+10)
                .getResultList();
    }

    public List<VideoPost> findAvailableAllVideoPost(){
        return em.createQuery("select v from VideoPost v", VideoPost.class).getResultList().stream().filter(x->x.getAvailable()==true).collect(Collectors.toList());
    }

    public List<VideoPost> findAvailablePagingVideoPosts(int page){
        return em.createQuery("select v from VideoPost v where v.available = :available order by v.createdDate desc", VideoPost.class)
                .setParameter("available",true)
                .setFirstResult(page*10)
                .setMaxResults(page*10+10)
                .getResultList();
    }

    public List<VideoPost> mainPageVideoPosts(){
        return em.createQuery("select v from VideoPost v where v.available = :available order by v.view desc", VideoPost.class)
                .setParameter("available",true)
                .setFirstResult(0)
                .setMaxResults(5)
                .getResultList();
    }

    public List<FreePost> findAvailableAllFreePosts(){
        return em.createQuery("select p from FreePost p", FreePost.class).getResultList().stream().filter(x->x.getAvailable()==true).collect(Collectors.toList());
    }

    public List<FreePost> findAvailablePagingFreePosts(int page){
        return em.createQuery("select p from FreePost p where p.available = :available order by p.createdDate desc", FreePost.class)
                .setParameter("available",true)
                .setFirstResult(page*10)
                .setMaxResults(page*10+10)
                .getResultList();
    }

    public List<FreePost> mainPageFreePosts(){
        return em.createQuery("select p from FreePost p where p.available = :available order by p.view desc", FreePost.class)
                .setParameter("available",true)
                .setFirstResult(0)
                .setMaxResults(5)
                .getResultList();
    }

    public List<Post> findAll(){
        return em.createQuery("select p from Post p", Post.class).getResultList();
    }

//    public List<User> findAll(){
//        return em.createQuery("select u from User u", User.class).getResultList();
//    }
//
//    public List<User> finByNickName(String nickName){
//        return em.createQuery("select u from User u where u.nickName = :nickName",User.class)
//                .setParameter("nickName",nickName)
//                .getResultList();
//    }
//
//    public Optional<User> findByLoginId(String loginId){
//        return findAll().stream().filter(m->m.getLoginId().equals(loginId)).findFirst();
//    }
}
