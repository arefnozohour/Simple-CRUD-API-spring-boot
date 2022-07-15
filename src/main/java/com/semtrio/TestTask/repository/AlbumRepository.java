package com.semtrio.TestTask.repository;

import com.semtrio.TestTask.domain.Album;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AlbumRepository extends JpaRepository<Album,Integer> {
    List<Album> findAllByUserId(Integer userId);
}
