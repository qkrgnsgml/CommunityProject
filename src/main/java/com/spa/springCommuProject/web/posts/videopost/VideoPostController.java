package com.spa.springCommuProject.web.posts.videopost;

import com.spa.springCommuProject.domain.post.entity.Post;
import com.spa.springCommuProject.domain.post.entity.Video;
import com.spa.springCommuProject.domain.post.entity.VideoPost;
import com.spa.springCommuProject.domain.post.service.FileService;
import com.spa.springCommuProject.domain.post.service.PostService;
import com.spa.springCommuProject.domain.user.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;

@Controller
@Slf4j
@RequiredArgsConstructor
public class VideoPostController {

    private final FileService fileService;
    private final PostService postService;

    @GetMapping("/videoposts")
    public String videoPostList(@SessionAttribute(name = "loginUser", required = false) User loginUser,
                               Model model) {
        List<VideoPost> videoPosts = postService.findAvailableVideoPosts();
        model.addAttribute("link", loginUser==null ? "login" : "videopost");
        model.addAttribute("posts", videoPosts);
        return "posts/videopost/videoPostList";
    }

    @GetMapping("/videoposts/{pageNum}")
    public String videoPostListPage(@SessionAttribute(name = "loginUser", required = false) User loginUser,
                                    @PathVariable int pageNum, Model model) {
        int size = postService.findAvailableVideoPosts().size()/10 + 1;
        List<VideoPost> videoPosts = postService.findAvailablePagingVideoPosts(pageNum);
        model.addAttribute("link", loginUser==null ? "login" : "videopost");
        model.addAttribute("size", size);
        model.addAttribute("posts", videoPosts);
        return "posts/videopost/videoPostList";
    }

    @GetMapping("/videopost")
    public String createVideoPostForm(VideoPostDTO videoPostDTO) {
        log.info("createVideoPostForm");
        return "posts/videopost/videoPostForm";
    }

    @PostMapping("/videopost")
    public String createVideoPost(@SessionAttribute(name = "loginUser", required = false) User loginUser,
                                  VideoPostDTO videoPostDTO) throws IOException {
        log.info("createVideoPost");
        List<Video> storeVideos = fileService.storeVideos(videoPostDTO.getImageFiles());

        //데이터베이스에 저장
        VideoPost post = new VideoPost(loginUser, videoPostDTO.getTitle(),
                            videoPostDTO.getContent());
        postService.savePost(post);

        for (Video storeVideo : storeVideos) {
            Video video = new Video(post, storeVideo.getUploadFileName(), storeVideo.getStoreFileName());
            fileService.saveVideo(video);
        }

        Long id = post.getId();

        return "redirect:/videopost/" + id;
    }

    @GetMapping("/videopost/{postId}")
    public String videoPostView(@SessionAttribute(name = "loginUser", required = false) User loginUser,
                               @PathVariable Long postId, Model model) {
        log.info("videopostView");

        postService.viewIncrease(postId); //추가
        Post post = postService.findOnePost(postId);

        VideoPostDTO videoPostDTO = new VideoPostDTO(post.getTitle(), post.getContent(), post.getCreatedDate(), post.getUser());
        List<Video> videos = fileService.findVideosByPostId(postId);

        model.addAttribute("loginUserId", loginUser==null ? null : loginUser.getId());
        model.addAttribute("loginUserLoginId", loginUser==null ? null : loginUser.getLoginId());
        model.addAttribute("videoPostDTO", videoPostDTO);
        model.addAttribute("videos", videos);
        model.addAttribute("postId", post.getId());
        return "posts/videopost/videoPostView";
    }

    @ResponseBody
    @GetMapping("/videos/{filename}")
    public Resource downloadVideo(@PathVariable String filename) throws
            MalformedURLException {
        return new UrlResource("file:" + fileService.getFullVideoPath(filename));
    }

    @GetMapping("/videopost/{postId}/edit")
    public String editVideoPostForm(@PathVariable Long postId, Model model) {
        log.info("videoPosteditForm");

        Post post = postService.findOnePost(postId);
        VideoPostDTO videoPostDTO = new VideoPostDTO(post.getTitle(), post.getContent());

        model.addAttribute("postId",postId);
        model.addAttribute("videoPostDTO", videoPostDTO);
        return "posts/videopost/videoPostUpdateForm";
    }

    @PostMapping("/videopost/{postId}/edit")
    public String editVideoPost(@PathVariable Long postId,
                                @Valid VideoPostDTO videoPostDTO, BindingResult bindingResult) {
        log.info("videoPostedit");

        if (bindingResult.hasErrors()) {
            bindingResult.reject("updateFail", "잘못된 정보를 입력했습니다.");
            log.info("bindingError");
            return "/posts/videopost/videoPostUpdateForm";
        } //추가

        postService.updatePost(postId, videoPostDTO.getTitle(), videoPostDTO.getContent());

        return "redirect:/videopost/" + postId;
    }

    @GetMapping("/videopost/{postId}/delete")
    public String deleteVideoPostForm(@PathVariable Long postId, Model model) {
        log.info("VideoPostDeleteForm");

        model.addAttribute("postId",postId);

        return "posts/videopost/videoPostdeleteForm";
    }

    @PostMapping("/videopost/{postId}/delete")
    public String deleteVideoPost(@PathVariable Long postId) {
        log.info("VideoPostDelete");

        postService.deletePost(postId);

        return "redirect:/videoposts/0";
    }


}
