package com.spa.springCommuProject.domain.post;


public interface PostRepository {

    void save(Post post);

    public Post findOnePost(Long id);

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
