package com.semtrio.TestTask.controller;

import com.semtrio.TestTask.data.request.ReqPhotoData;
import com.semtrio.TestTask.data.response.ResPhotoData;
import com.semtrio.TestTask.service.interfaces.PhotoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/photos")
public class PhotoController {

    @Autowired
    PhotoService photoService;

    @PostMapping
    public ResponseEntity<ResPhotoData> addPhoto(@RequestBody ReqPhotoData photoData)
    {
        return ResponseEntity.ok(photoService.makeResponse(photoService.add(photoData)));
    }
    @PutMapping("/{photoId}")
    public ResponseEntity<ResPhotoData> updatePhoto(@PathVariable Integer photoId, @Valid @RequestBody ReqPhotoData photoData)
    {
        return ResponseEntity.ok(photoService.makeResponse(photoService.update(photoId,photoData)));
    }
    @PatchMapping("/{photoId}")
    public ResponseEntity<ResPhotoData> patchPhoto(@PathVariable Integer photoId, @RequestBody ReqPhotoData photoData)
    {
        return ResponseEntity.ok(photoService.makeResponse(photoService.patch(photoId,photoData)));
    }
    @GetMapping("/{photoId}")
    public ResponseEntity<ResPhotoData> getPhoto(@PathVariable Integer photoId)
    {
        return ResponseEntity.ok(photoService.makeResponse(photoService.getById(photoId)));
    }
    @DeleteMapping("/{photoId}")
    public ResponseEntity deletePhoto(@PathVariable Integer photoId)
    {
        photoService.delete(photoId);
        return ResponseEntity.ok().build();
    }
    @GetMapping()
    public ResponseEntity<List<ResPhotoData>> getAllPhoto(@RequestParam(required = false)Integer albumId)
    {
        return ResponseEntity.ok(photoService.makeResponse(photoService.getAll(albumId)));
    }
}
