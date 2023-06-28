package com.springsmallads.repository;

import com.springsmallads.entity.Image;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImageRepository extends CrudRepository<Image, Integer> {
}
