package com.spa.springCommuProject.domain.post.service;

import com.spa.springCommuProject.domain.post.entity.Image;
import com.spa.springCommuProject.domain.post.entity.Video;
import com.spa.springCommuProject.domain.post.repository.FileRepository;
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

    @Transactional
    public Long saveVideo(Video video){
        fileRepository.save(video);
        return video.getId();
    }

    public List<Image> storeImages(List<MultipartFile> multipartFiles) throws IOException{
        List<Image> storeFileResult = new ArrayList<>();
        for (MultipartFile multipartFile : multipartFiles) {
            if(!multipartFile.isEmpty()){
                storeFileResult.add(storeImage(multipartFile));
            }
        }
        return storeFileResult;
    }

    public List<Video> storeVideos(List<MultipartFile> multipartFiles) throws IOException{
        List<Video> storeFileResult = new ArrayList<>();
        for (MultipartFile multipartFile : multipartFiles) {
            if(!multipartFile.isEmpty()){
                storeFileResult.add(storeVideo(multipartFile));
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

    private Video storeVideo(MultipartFile multipartFile) throws IOException{
        if(multipartFile.isEmpty()){
            return null;
        }

        String originalFilename = multipartFile.getOriginalFilename();
        String storeFileName = createStoreFileName(originalFilename);
        multipartFile.transferTo(new File(getFullVideoPath(storeFileName)));
        return new Video(originalFilename, storeFileName);
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

    public List<Image> findImagesByPostId(Long postId){
        return fileRepository.findImagesByPostId(postId);
    }

    public Image findOneImageByPostId(Long postId){
        return fileRepository.findOneImageByPostId(postId);
    }

    public List<Video> findVideosByPostId(Long postId){
        return fileRepository.findVideosByPostId(postId);
    }


}
