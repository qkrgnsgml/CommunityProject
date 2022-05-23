package com.spa.springCommuProject.domain.post;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
@RequiredArgsConstructor
public class FileRepository {
    private final EntityManager em;

    public void save(Image image) {
        em.persist(image);
    }
}
