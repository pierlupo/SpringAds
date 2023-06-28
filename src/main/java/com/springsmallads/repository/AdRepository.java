package com.springsmallads.repository;

import com.springsmallads.entity.Ad;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdRepository extends CrudRepository<Ad, Integer> {

    public List<Ad> searchAdsByTitleLikeOrDescriptionLike(String title, String description);
}
