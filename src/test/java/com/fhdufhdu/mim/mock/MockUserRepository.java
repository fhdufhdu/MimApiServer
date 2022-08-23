package com.fhdufhdu.mim.mock;

import com.fhdufhdu.mim.entity.Role;
import com.fhdufhdu.mim.entity.User;
import com.fhdufhdu.mim.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class MockUserRepository extends MockJapRepository<User, String> implements UserRepository {
    private PasswordEncoder encoder;
    public MockUserRepository() {
        encoder = new BCryptPasswordEncoder();
        List<User> users = new ArrayList<>();
        IntStream.range(0, 10).forEach(idx -> users.add(User.builder()
                        .id("user" + idx)
                        .banEndDate(null)
                        .pw(encoder.encode("user" + idx))
                        .nickname("nickname" + idx)
                        .role(Role.USER)
                .build()));
        setData(users);
    }

    @Override
    public boolean existsByNickName(String nickName) {
        return false;
    }
}
