package com.spa.springCommuProject.domain.post;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class FileService {

    private final FileRepository fileRepository;

    @Value("${image.dir}")
    private String imageDir;

    @Value("${video.dir}")
    private String videoDir;

    public String getFullImagePath(String filename){
        return imageDir + filename;
    }

    public String getFullVideoPath(String filename){
        return videoDir + filename;
    }

    @Transactional
    public Long saveImage(Image image){
        fileRepository.save(image);
        return image.getId();
    }

    public List<Image> storeFiles(List<MultipartFile> multipartFiles) throws IOException{
        List<Image> storeFileResult = new ArrayList<>();
        for (MultipartFile multipartFile : multipartFiles) {
            if(!multipartFile.isEmpty()){
                storeFileResult.add(storeImage(multipartFile));
            }
        }
        return storeFileResult;
    }

    private Image storeImage(MultipartFile multipartFile) throws IOException{
        if(multipartFile.isEmpty()){
            return null;
        }

        String originalFilename = multipartFile.getOriginalFilename();
        String storeFileName = createStoreFileName(originalFilename);
        multipartFile.transferTo(new File(getFullImagePath(storeFileName)));
        return new Image(originalFilename, storeFileName);
    }

    private String createStoreFileName(String originalFilename) {
        String ext = extractExt(originalFilename);
        String uuid = UUID.randomUUID().toString();
        return uuid + "." + ext;
    }

    private String extractExt(String originalFilename) {
        int pos = originalFilename.lastIndexOf(".");
        return originalFilename.substring(pos + 1);
    }

    public List<Image> findFilesbyPostId(Long postId){
        return fileRepository.findImagesbyPostId(postId);
    }


}
