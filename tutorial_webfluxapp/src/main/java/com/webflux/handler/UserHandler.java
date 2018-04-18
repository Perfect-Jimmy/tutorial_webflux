package com.webflux.handler;

import com.webflux.domain.User;
import com.webflux.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.springframework.web.reactive.function.BodyInserters.fromObject;

/**
 * Created by Jimmy. 2018/4/18  17:16
 */
@Component
public class UserHandler {
    @Autowired
    private UserService userService;


    /**
     * 查询所有user
     * @param request
     * @return
     */
    public Mono<ServerResponse> queryAllUser(ServerRequest request){
        Flux<User> users = userService.queryAllUser();
        // build response
        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(users, User.class);
    }

    /**
     * 根据id查询user
     * @param request
     * @return
     */
    public Mono<ServerResponse> queryUserById(ServerRequest request) {
        // parse path-variable
        Long id = Long.valueOf(request.pathVariable("id"));

        // build notFound response
        Mono<ServerResponse> notFound = ServerResponse.notFound().build();

        // get customer from repository
        Mono<User> userMono = userService.queryUserById(id);

        // build response
        return userMono
                .flatMap(user -> ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(fromObject(user)))
                .switchIfEmpty(notFound);
    }


    /**
     * 保存user
     * @param request
     * @return
     */
    public Mono<ServerResponse> saveUser(ServerRequest request) {
        Mono<User> userMono = request.bodyToMono(User.class);
        return ServerResponse.ok().build(userService.saveUser(userMono));
    }

    /**
     * 更新user
     * @param request
     * @return
     */
    public Mono<ServerResponse> modifyUser(ServerRequest request) {
        // parse id from path-variable
        Long id = Long.valueOf(request.pathVariable("id"));

        // get customer data from request object
        Mono<User> userMono = request.bodyToMono(User.class);

        // get customer from repository
        Mono<User> responseMono = userService.modifyUser(id,userMono);

        // build response
        return responseMono
                .flatMap(user -> ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(fromObject(user)));
    }


    /**
     * 删除user
     * @param request
     * @return
     */
    public Mono<ServerResponse> deleteUser(ServerRequest request) {
        // parse id from path-variable
        Long id = Long.valueOf(request.pathVariable("id"));

        // get customer from repository
        Mono<String> responseMono = userService.deleteUser(id);

        // build response
        return responseMono
                .flatMap(user -> ServerResponse.ok().contentType(MediaType.TEXT_PLAIN).body(fromObject(user)));
    }
}
