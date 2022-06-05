package com.spa.springCommuProject.web.user;


import com.spa.springCommuProject.domain.user.BigThreePower;
import com.spa.springCommuProject.domain.user.User;
import com.spa.springCommuProject.domain.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final UserService userService;

    @GetMapping("/user")
    public String joinForm(UserJoinDTO userJoinDTO){
        log.info("joinForm");
        return "/user/joinForm";
    }

    @PostMapping("/user")
    public String join(@Valid UserJoinDTO userJoinDTO, BindingResult
            bindingResult, HttpServletRequest request){

        if (bindingResult.hasErrors()) {
            bindingResult.reject("joinFail", "잘못된 정보를 입력했습니다.");
            log.info("bindingError");
            return "/user/joinForm";
        } //추가

        User user = new User(userJoinDTO.getNickName(),userJoinDTO.getLoginId(), userJoinDTO.getPassword());
        log.info("join user={}",user);
        userService.join(user);

        //세션 생성
        HttpSession session = request.getSession();
        //세션에 가입 회원 정보 보관
        session.setAttribute("loginUser", user);

        //userService.login(userJoinDTO.getLoginId(), userJoinDTO.getPassword());
        //실패
        return "redirect:/";
    }

    @GetMapping("/login")
    public String loginForm(UserLoginDTO userLoginDTO){
        log.info("loginForm");
        return "/user/loginForm";
    }

    @PostMapping("/login")
    public String login(@Valid UserLoginDTO userLoginDTO, BindingResult
            bindingResult, HttpServletRequest request){

        if (bindingResult.hasErrors()) {
            bindingResult.reject("loginFail", "잘못된 정보를 입력했습니다.");
            log.info("bindingError");
            return "/user/loginForm";
        } //폼에 잘못된 정보 들어왔을때

        User loginUser = userService.login(userLoginDTO.getLoginId(), userLoginDTO.getPassword());

        if (loginUser == null) {
            bindingResult.reject("loginFail", "아이디 또는 비밀번호가 맞지 않습니다.");
            return "/user/loginForm";
        }
        if(loginUser.getAvailable()==false){
            bindingResult.reject("loginFail", "탈퇴된 회원입니다.");
            return "/user/loginForm";
        }
        log.info("login username={}",loginUser.getLoginId());
        log.info("login userpassword={}",loginUser.getPassword());

        //세션이 있으면 있는 세션 반환, 없으면 신규 세션 생성
        HttpSession session = request.getSession();

        //세션에 로그인 회원 정보 보관
        session.setAttribute("loginUser", loginUser);
        return "redirect:/";
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request) {
        //세션을 삭제한다.
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        log.info("logout");
        return "redirect:/";
    }

    @GetMapping("/user/{userId}")
    public String myView(@PathVariable Long userId, Model model) {
        log.info("myView");

        User findUser = userService.findOne(userId);
        UserUpdateDTO userUpdateDTO = new UserUpdateDTO(findUser.getNickName(), findUser.getLoginId(), findUser.getPassword());

        model.addAttribute("userUpdateDTO", userUpdateDTO);
        model.addAttribute("userId", userId);
        return "user/myView";
    }

    @GetMapping("/user/{userId}/edit")
    public String editForm(@PathVariable Long userId, Model model) {
        log.info("usereditForm");

        User findUser = userService.findOne(userId);
        UserUpdateDTO userUpdateDTO = new UserUpdateDTO(findUser.getNickName(), findUser.getLoginId(), findUser.getPassword());

        model.addAttribute("userUpdateDTO", userUpdateDTO);
        model.addAttribute("userId", userId);
        return "user/updateForm";
    }

    @PostMapping("/user/{userId}/edit")
    public String edit(@PathVariable Long userId,
                       @Valid UserUpdateDTO userUpdateDTO, BindingResult bindingResult) {
        log.info("useredit");

        if (bindingResult.hasErrors()) {
            bindingResult.reject("updateFail", "잘못된 정보를 입력했습니다.");
            log.info("bindingError");
            return "/user/updateForm";
        } //추가

        userService.updateUser(userId, userUpdateDTO.getNickName(), userUpdateDTO.getPassword());

        return "redirect:/user/" + userId;
    }

    @GetMapping("/user/{userId}/delete")
    public String deleteForm(@PathVariable Long userId, Model model) {
        log.info("userDeleteForm");

        model.addAttribute("userId",userId);

        return "user/deleteForm";
    }

    @PostMapping("/user/{userId}/delete")
    public String delete(@PathVariable Long userId) {
        log.info("userDelete");

        userService.deleteUser(userId);
        return "redirect:/logout";
    }

    @GetMapping("/user/{userId}/big")
    public String BigthreeForm(@PathVariable Long userId, Model model, BigThreeDTO bigThreeDTO) {
        log.info("BigthreeForm");

        model.addAttribute("userId",userId);

        return "user/threeForm";
    }

    @PostMapping("/user/{userId}/big")
    public String Bigthree(@PathVariable Long userId, BigThreeDTO bigThreeDTO) {
        log.info("Bigthree");

        BigThreePower bigThreePower = new BigThreePower(bigThreeDTO.getSquat(), bigThreeDTO.getBench(), bigThreeDTO.getDead());
        userService.updateBigThree(userId, bigThreePower);

        return "redirect:/videoposts";
    }



}
