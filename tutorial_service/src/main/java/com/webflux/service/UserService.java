package com.webflux.service;

import com.webflux.domain.User;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Created by Jimmy. 2018/4/18  16:45
 */
public interface UserService {

    public Flux<User> queryAllUser();

    public Mono<User> queryUserById(Long id);

    public Mono<Void> saveUser(Mono<User> userMono);

    public Mono<User> modifyUser(Long id,Mono<User> userMono);

    public Mono<String> deleteUser(Long id);
}
