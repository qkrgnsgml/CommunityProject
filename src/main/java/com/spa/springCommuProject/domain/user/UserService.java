package com.spa.springCommuProject.domain.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService {


    private final UserRepository userRepository;

    @Transactional
    public Long join(User user){
        validateDuplicateUser(user); //중복 닉네임, 아이디 검증
        userRepository.save(user);
        return user.getId();
    }

    private void validateDuplicateUser(User user) {
        List<User> findUserByNickName = userRepository.findByNickName(user.getNickName());
        if(!findUserByNickName.isEmpty()){
            throw new IllegalStateException("이미 존재하는 닉네임입니다.");
        }
        if(userRepository.findByLoginId(user.getLoginId()).isPresent()){
            throw new IllegalStateException("이미 존재하는 아이디입니다.");
        }
    }

    public List<User> findMembers() {
        return userRepository.findAll();
    }

    public User findOne(Long userId) {
        return userRepository.findOne(userId);
    }

    public User login(String loginId, String password){
        /**
         * return null 이면 로그인 실패
         */
        return userRepository.findByLoginId(loginId).filter(m->m.getPassword().equals(password))
                .orElse(null);
    }

    public List<User> findUsersSumDesc(){
        return userRepository.findUsersSumDesc();
    }

    @Transactional
    public void updateUser(Long userId, String nickName, String password){
        User findUser = userRepository.findOne(userId);
        findUser.update(nickName, password);
    }
    @Transactional
    public void deleteUser(Long userId){
        User findUser = userRepository.findOne(userId);
        findUser.delete();
    }

    @Transactional
    public void updateBigThree(Long userId, BigThreePower bigThreePower){
        User findUser = userRepository.findOne(userId);
        findUser.updateBig(bigThreePower);
    }
}
