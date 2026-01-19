package spring.boot.nextbuy.controllers;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import spring.boot.nextbuy.configurations.FileStorageProperties;
import spring.boot.nextbuy.entities.Product;
import spring.boot.nextbuy.entities.dto.ProductRequest;
import spring.boot.nextbuy.services.ProductService;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;

@RestController
@RequestMapping("api/files")
public class FileController {

    private final Path fileLocation;
    private ProductService productService;

    public FileController(FileStorageProperties fileStorageProperties, ProductService productService) {
        this.fileLocation = Paths.get(fileStorageProperties.getUploadDir()).toAbsolutePath().normalize();
        this.productService = productService;
    }

    @PostMapping("/upload")
    public ResponseEntity<?> uploadFile(@RequestParam("file")MultipartFile file) {
        String fileName = StringUtils.cleanPath(LocalDateTime.now().toString() + "_" + file.getOriginalFilename());

        try {
            Path targetLocation = fileLocation.resolve(fileName);
            file.transferTo(targetLocation);

            String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath().path("api/files/download/").path(fileName).toUriString();

            return ResponseEntity.ok(fileDownloadUri);
        } catch (IOException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/download/{fileName:.+}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileName, HttpServletRequest request) throws IOException {
        Path filePath = fileLocation.resolve(fileName).normalize();
        try {
            Resource resource = new UrlResource(filePath.toUri());
            String contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());

            if(contentType == null) {
                contentType = "application/octet-stream";
            }
            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(contentType))
                    .body(resource);
        } catch (MalformedURLException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
