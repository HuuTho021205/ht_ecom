package com.ecommerce.ht_ecom.service.impl;

import com.ecommerce.ht_ecom.exceptions.APIException;
import com.ecommerce.ht_ecom.service.FileService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;
@Service
public class FileServiceImpl implements FileService {
    @Override
    public String uploadImage(String path, MultipartFile fileImage) throws IOException {
        // lấy tên file gốc vd my-iphone.png
        String originalFileName = fileImage.getOriginalFilename();
        if (originalFileName == null || !originalFileName.contains(".")){
            throw new APIException("Invalid file format");
        }
        // tạo id duy nhất vd jjalda-djadjoa-9292;
        String randomId = UUID.randomUUID().toString();
        // cắt lấy phần mở rộng của file gốc rồi nối với id -> jjalda-djadjoa-9292.png -> tránh trùng file ảnh
        String fileName = randomId.concat(originalFileName.substring(originalFileName.lastIndexOf(".")));
        // tạo đường dẫn đến vị trị lưu file. separator là ký tu phân tách thư mục "/"
        // dùng separator để không bị lỗi trên các hệ điều hành khác vd windown "/" nhưng linux "\"
        String filePath = path + File.separator + fileName;

        File folder = new File(path);
        if (!folder.exists()){
            folder.mkdirs();
        }
        // copy file ảnh vào ổ cứng
        Files.copy(fileImage.getInputStream(), Paths.get(filePath));

        return fileName;
    }
}
