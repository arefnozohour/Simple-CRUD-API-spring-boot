package com.semtrio.TestTask.repository;

import com.semtrio.TestTask.domain.Photo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PhotoRepository extends JpaRepository<Photo,Integer> {
    List<Photo> findAllByAlbumId(Integer albumId);
}
