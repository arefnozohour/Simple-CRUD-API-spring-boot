package com.semtrio.TestTask.service.implementions;

import com.semtrio.TestTask.data.request.ReqAlbumData;
import com.semtrio.TestTask.data.response.ResAlbumData;
import com.semtrio.TestTask.domain.Album;
import com.semtrio.TestTask.exception.ExceptionData;
import com.semtrio.TestTask.exception.ServiceException;
import com.semtrio.TestTask.repository.AlbumRepository;
import com.semtrio.TestTask.service.interfaces.AlbumService;
import com.semtrio.TestTask.service.interfaces.PhotoService;
import com.semtrio.TestTask.service.interfaces.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AlbumServiceImpl implements AlbumService {

    @Autowired
    private AlbumRepository albumRepository;
    @Autowired
    private UserService userService;
    ModelMapper modelMapperSkipNull;
    ModelMapper modelMapper;

    @Autowired
    public AlbumServiceImpl() {
        this.modelMapperSkipNull = new ModelMapper();
        modelMapperSkipNull.getConfiguration().setSkipNullEnabled(true);
        modelMapperSkipNull.createTypeMap(ReqAlbumData.class, Album.class);
        this.modelMapper=new ModelMapper();
        modelMapper.createTypeMap(ReqAlbumData.class, Album.class).addMappings(mapper -> mapper.skip(Album::setId));;
        modelMapper.createTypeMap(Album.class, ResAlbumData.class).addMappings(
                mapper -> mapper.map(src -> src.getUser().getId(), ResAlbumData::setUserId)
        );
    }
    @Override
    public Album add(ReqAlbumData albumData)
    {
        Album album=modelMapper.map(albumData,Album.class);
        album.setUser(userService.getById(albumData.getUserId()));
        return albumRepository.save(album);
    }
    @Override
    public Album update(Integer albumId, ReqAlbumData albumData)
    {
        Album album=this.getById(albumId);
        modelMapper.getTypeMap(ReqAlbumData.class, Album.class).setProvider(p -> album);
        Album updatedAlbum=this.modelMapper.map(albumData,Album.class);
        return albumRepository.save(updatedAlbum);
    }
    @Override
    public Album patch(Integer albumId, ReqAlbumData albumData)
    {
        Album album=this.getById(albumId);
        modelMapperSkipNull.getTypeMap(ReqAlbumData.class, Album.class).setProvider(p -> album);
        Album updatedAlbum=this.modelMapperSkipNull.map(albumData,Album.class);
        return albumRepository.save(updatedAlbum);
    }
    @Override
    public List<Album> getAll(Integer userId)
    {
        if (userId==null)
            return albumRepository.findAll();
        return albumRepository.findAllByUserId(userId);
    }

    @Override
    public Album getById(Integer albumId)
    {
        Optional<Album> album=albumRepository.findById(albumId);
        if (album.isPresent())
            return album.get();
        throw new ServiceException(ExceptionData.album_NOTFOUND);
    }

    @Override
    public void delete(Integer albumId)
    {
        try {
            albumRepository.deleteById(albumId);
        }
        catch ( EmptyResultDataAccessException e)
        {
            throw new ServiceException(ExceptionData.album_NOTFOUND);
        }
    }
    @Override
    public void deleteAll() {
        albumRepository.deleteAll();
    }

    @Override
    public ResAlbumData makeResponse(Album album)
    {
        return modelMapper.map(album,ResAlbumData.class);
    }

    @Override
    public List<ResAlbumData> makeResponse(List<Album> albums)
    {
        return albums.stream()
                .map(element -> modelMapper.map(element, ResAlbumData.class))
                .collect(Collectors.toList());
    }
}
