package com.semtrio.TestTask.service.implementions;

import com.semtrio.TestTask.data.request.ReqPhotoData;
import com.semtrio.TestTask.data.response.ResPhotoData;
import com.semtrio.TestTask.domain.Photo;
import com.semtrio.TestTask.exception.ExceptionData;
import com.semtrio.TestTask.exception.ServiceException;
import com.semtrio.TestTask.repository.PhotoRepository;
import com.semtrio.TestTask.service.interfaces.AlbumService;
import com.semtrio.TestTask.service.interfaces.PhotoService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PhotoServiceImpl implements PhotoService {
    @Autowired
    private PhotoRepository photoRepository;
    @Autowired
    private AlbumService albumService;
    ModelMapper modelMapperSkipNull;
    ModelMapper modelMapper;

    @Autowired
    public PhotoServiceImpl() {
        this.modelMapperSkipNull = new ModelMapper();
        modelMapperSkipNull.getConfiguration().setSkipNullEnabled(true);
        modelMapperSkipNull.createTypeMap(ReqPhotoData.class, Photo.class);
        this.modelMapper=new ModelMapper();
        modelMapper.createTypeMap(ReqPhotoData.class, Photo.class).addMappings(mapper -> mapper.skip(Photo::setId));;
        modelMapper.createTypeMap(Photo.class, ResPhotoData.class).addMappings(
                mapper -> mapper.map(src -> src.getAlbum().getId(), ResPhotoData::setAlbumId)
        );
    }
    @Override
    public Photo add(ReqPhotoData photoData)
    {
        Photo photo=modelMapper.map(photoData,Photo.class);
        photo.setId(null);
        photo.setAlbum(albumService.getById(photoData.getAlbumId()));
        return photoRepository.save(photo);
    }
    @Override
    public Photo update(Integer photoId, ReqPhotoData photoData)
    {
        Photo photo=this.getById(photoId);
        modelMapper.getTypeMap(ReqPhotoData.class, Photo.class).setProvider(p -> photo);
        Photo updatedPhoto=this.modelMapper.map(photoData,Photo.class);
        return photoRepository.save(updatedPhoto);
    }
    @Override
    public Photo patch(Integer photoId, ReqPhotoData photoData)
    {
        Photo photo=this.getById(photoId);
        modelMapperSkipNull.getTypeMap(ReqPhotoData.class, Photo.class).setProvider(p -> photo);
        Photo updatedPhoto=this.modelMapperSkipNull.map(photoData,Photo.class);
        return photoRepository.save(updatedPhoto);
    }
    @Override
    public List<Photo> getAll(Integer albumId)
    {
        if (albumId==null)
            return photoRepository.findAll();
        return photoRepository.findAllByAlbumId(albumId);
    }

    @Override
    public Photo getById(Integer photoId)
    {
        Optional<Photo> photo=photoRepository.findById(photoId);
        if (photo.isPresent())
            return photo.get();
        throw new ServiceException(ExceptionData.photo_NOTFOUND);
    }

    @Override
    public void delete(Integer photoId)
    {
        try {
            photoRepository.deleteById(photoId);
        }
        catch ( EmptyResultDataAccessException e)
        {
            throw new ServiceException(ExceptionData.photo_NOTFOUND);
        }
    }

    @Override
    public void deleteAll() {
        photoRepository.deleteAll();
    }

    @Override
    public ResPhotoData makeResponse(Photo photo)
    {
        return modelMapper.map(photo,ResPhotoData.class);
    }

    @Override
    public List<ResPhotoData> makeResponse(List<Photo> photos)
    {
        return photos.stream()
                .map(element -> modelMapper.map(element, ResPhotoData.class))
                .collect(Collectors.toList());
    }
}
