package com.semtrio.TestTask.service.interfaces;

import com.semtrio.TestTask.data.request.ReqAlbumData;
import com.semtrio.TestTask.data.response.ResAlbumData;
import com.semtrio.TestTask.domain.Album;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface AlbumService {
    @Transactional
    Album add(ReqAlbumData albumData);

    @Transactional
    Album update(Integer albumId, ReqAlbumData albumData);

    @Transactional
    Album patch(Integer albumId, ReqAlbumData albumData);

    @Transactional(readOnly = true)
    List<Album> getAll(Integer userId);

    @Transactional(readOnly = true)
    Album getById(Integer albumId);

    @Transactional
    void delete(Integer albumId);

    @Transactional
    void deleteAll();

    ResAlbumData makeResponse(Album album);

    List<ResAlbumData> makeResponse(List<Album> albums);
}
