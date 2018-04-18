package com.webflux.service.impl;

import com.webflux.domain.User;
import com.webflux.service.UserService;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Jimmy. 2018/4/18  16:45
 */
@Service
public class UserServiceImpl implements UserService {
    private Map<Long, User> users = new HashMap<Long, User>();

    @PostConstruct
    public void init() throws Exception {
        users.put(1L,new User(1L, "Jack"));
        users.put(2L,new User(2L, "Lucy"));
    }

    @Override
    public Flux<User> queryAllUser() {
        return Flux.fromIterable(this.users.values());
    }

    @Override
    public Mono<User> queryUserById(Long id) {
        return Mono.just(users.get(id));
    }

    @Override
    public Mono<Void> saveUser(Mono<User> userMono) {
        Mono<User> monoUser = userMono.doOnNext(user -> {
                users.put(user.getId(), user);
            System.out.println("########### POST:" + user);
        });
        return monoUser.then();
    }

    @Override
    public Mono<User> modifyUser(Long id, Mono<User> userMono) {
        Mono<User> monoUser = userMono.doOnNext(user -> {
            // reset user.Id
            user.setId(id);
            // do post
            users.put(id, user);
            System.out.println("########### PUT:" + user);
        });
        return monoUser;
    }


    @Override
    public Mono<String> deleteUser(Long id) {
        users.remove(id);
        return Mono.just("Delete Succesfully!");
    }
}
