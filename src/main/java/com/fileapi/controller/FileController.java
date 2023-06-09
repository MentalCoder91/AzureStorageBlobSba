package com.fileapi.controller;

import com.fileapi.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Paths;

@RestController
@RequestMapping("/api/v1/file")
public class FileController {

    private final FileService fileService;

    @Autowired
    public FileController(FileService fileService) {
        this.fileService = fileService;
    }

    @PostMapping(value = "/uploadFile", produces = {MediaType.IMAGE_PNG_VALUE, MediaType.IMAGE_JPEG_VALUE})
    public ResponseEntity<?> uploadFile(@RequestParam("file") MultipartFile file) {
        try {
            if (fileService.uploadFile(file, "data")) {
                final ByteArrayResource resource = new ByteArrayResource(Files.readAllBytes(Paths.get(fileService
                        .getFileStorageLocation() + "/" + file.getOriginalFilename())));
                return ResponseEntity.status(HttpStatus.OK).contentLength(resource.contentLength()).body(resource);
            }
            return ResponseEntity.ok("Error while processing file");
        } catch (Exception e) {
            return ResponseEntity.ok("Error while processing file");
        }
    }
    
    
    @PostMapping(value = "/deleteFile", produces = {MediaType.IMAGE_PNG_VALUE, MediaType.IMAGE_JPEG_VALUE})
    public ResponseEntity<?> deleteFile(@RequestParam("file") String fileName ) {
        try {
            if (fileService.deleteFile(fileName, "data")){
                
                return ResponseEntity.status(HttpStatus.OK).body("The file :"+fileName+" has been deleted");
            }
            return ResponseEntity.ok("Error while processing file");
        } catch (Exception e) {
            return ResponseEntity.ok("Error while processing file");
        }
    }
    
    
    
    
    
    
}
