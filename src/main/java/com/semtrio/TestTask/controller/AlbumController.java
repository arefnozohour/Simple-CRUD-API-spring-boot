package com.semtrio.TestTask.controller;

import com.semtrio.TestTask.data.request.ReqAlbumData;
import com.semtrio.TestTask.data.response.ResAlbumData;
import com.semtrio.TestTask.data.response.ResPhotoData;
import com.semtrio.TestTask.domain.Photo;
import com.semtrio.TestTask.service.interfaces.AlbumService;
import com.semtrio.TestTask.service.interfaces.PhotoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/albums")
public class AlbumController {

    @Autowired
    AlbumService albumService;

    @PostMapping
    public ResponseEntity<ResAlbumData> addAlbum(@RequestBody ReqAlbumData albumData)
    {
        return ResponseEntity.ok(albumService.makeResponse(albumService.add(albumData)));
    }
    @PutMapping("/{albumId}")
    public ResponseEntity<ResAlbumData> updateAlbum(@PathVariable Integer albumId, @Valid @RequestBody ReqAlbumData albumData)
    {
        return ResponseEntity.ok(albumService.makeResponse(albumService.update(albumId,albumData)));
    }
    @PatchMapping("/{albumId}")
    public ResponseEntity<ResAlbumData> patchAlbum(@PathVariable Integer albumId, @RequestBody ReqAlbumData albumData)
    {
        return ResponseEntity.ok(albumService.makeResponse(albumService.patch(albumId,albumData)));
    }
    @GetMapping("/{albumId}")
    public ResponseEntity<ResAlbumData> getAlbum(@PathVariable Integer albumId)
    {
        return ResponseEntity.ok(albumService.makeResponse(albumService.getById(albumId)));
    }
    @DeleteMapping("/{albumId}")
    public ResponseEntity deleteAlbum(@PathVariable Integer albumId)
    {
        albumService.delete(albumId);
        return ResponseEntity.ok().build();
    }
    @GetMapping()
    public ResponseEntity<List<ResAlbumData>> getAllAlbum(@RequestParam(required = false)Integer userId)
    {
        return ResponseEntity.ok(albumService.makeResponse(albumService.getAll(userId)));
    }
    @Autowired
    private PhotoService photoService;


    @GetMapping("{albumId}/photos")
    public ResponseEntity<List<ResPhotoData>> getAlbumPhotos(@PathVariable Integer albumId)
    {
        return ResponseEntity.ok(photoService.makeResponse(photoService.getAll(albumId)));
    }
}
