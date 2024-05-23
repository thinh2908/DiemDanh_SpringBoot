package com.diemdanh.controller;

import com.diemdanh.factory.FilesStorageService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/files")
public class FileController {
    @Autowired
    FilesStorageService filesStorageService;

    @PostMapping("/images/{id}/upload")
    public ResponseEntity<?> uploadFile(@RequestPart(required = true) MultipartFile file, @PathVariable Long id)
            throws JsonMappingException, JsonProcessingException {


/*        DateFormat dateFormatter = new SimpleDateFormat("yyyyMMddHHmmssSSS_");
        String currentDateTime = dateFormatter.format(new Date());
        filesStorageService.saveAs(file, "uploads/" + currentDateTime + file.getOriginalFilename() );*/
        boolean existed = filesStorageService.delete("uploads/" + "User_" + id + ".jpg");
        filesStorageService.saveAs(file, "uploads/" + "User_" + id + ".jpg" );
        ObjectMapper mapper = new ObjectMapper();
        JsonNode json = mapper.readTree("{\"Success\": \"True\"}");
        return ResponseEntity.ok(json);
    }

    @GetMapping("/images/{filename:.+}")
    public ResponseEntity<Resource> loadImage(@PathVariable String filename) throws Exception {
        Resource file = filesStorageService.load("uploads/"+ filename);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
                .body(file);
    }
}
