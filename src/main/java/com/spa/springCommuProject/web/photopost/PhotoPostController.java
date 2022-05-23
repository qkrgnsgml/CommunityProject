package com.spa.springCommuProject.web.photopost;

import com.spa.springCommuProject.domain.post.FileService;
import com.spa.springCommuProject.domain.post.Image;
import com.spa.springCommuProject.domain.post.PhotoPost;
import com.spa.springCommuProject.domain.post.PostService;
import com.spa.springCommuProject.domain.user.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

import java.io.IOException;
import java.util.List;

@Controller
@Slf4j
@RequiredArgsConstructor
public class PhotoPostController {

    private final FileService fileService;
    private final PostService postService;

    @GetMapping("/photoposts")
    public String photoPostList(@SessionAttribute(name = "loginUser", required = false) User loginUser,
                               Model model) {
        List<PhotoPost> photoPosts = postService.findAvailablePhotoPosts();
        model.addAttribute("link", loginUser==null ? "login" : "photopost");
        model.addAttribute("posts", photoPosts);
        return "posts/photoPostList";
    }

    @GetMapping("/photopost")
    public String createPhotoPostForm(PhotoPostDTO photoPostDTO) {
        log.info("createPhotoPostForm");
        return "posts/filePostForm";
    }

    @PostMapping("/photopost")
    public String createPhotoPost(@SessionAttribute(name = "loginUser", required = false) User loginUser,
                                  PhotoPostDTO photoPostDTO) throws IOException {
        log.info("createPhotoPost");
        List<Image> storeImageFiles = fileService.storeFiles(photoPostDTO.getImageFiles());

        //데이터베이스에 저장
        PhotoPost post = new PhotoPost(loginUser, photoPostDTO.getTitle(),
                            photoPostDTO.getContent());
        postService.savePost(post);

        for (Image storeImageFile : storeImageFiles) {
            Image image = new Image(post, storeImageFile.getUploadFileName(), storeImageFile.getStoreFileName());
            fileService.saveImage(image);
        }

        return "posts/filePostForm";
    }




}
