package com.semtrio.TestTask.service.interfaces;

import com.semtrio.TestTask.data.request.ReqPhotoData;
import com.semtrio.TestTask.data.response.ResPhotoData;
import com.semtrio.TestTask.domain.Photo;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface PhotoService {
    @Transactional
    Photo add(ReqPhotoData photoData);
    @Transactional
    Photo update(Integer photoId, ReqPhotoData photoData);
    @Transactional
    Photo patch(Integer photoId, ReqPhotoData photoData);
    @Transactional(readOnly = true)
    List<Photo> getAll(Integer albumId);
    @Transactional(readOnly = true)
    Photo getById(Integer photoId);
    @Transactional
    void delete(Integer photoId);
    @Transactional
    void deleteAll();

    ResPhotoData makeResponse(Photo photo);

    List<ResPhotoData> makeResponse(List<Photo> photos);
}
