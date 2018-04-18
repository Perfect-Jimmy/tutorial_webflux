package com.webflux.router;

import com.webflux.handler.UserHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

/**
 * Created by Jimmy. 2018/4/18  17:31
 * http://ju.outofmemory.cn/entry/340728
 */
@Configuration
public class UserRouter {

    @Bean
    public RouterFunction<ServerResponse> routeCity(UserHandler userHandler) {
        return  route(GET("/user/queryAll").and(accept(MediaType.APPLICATION_JSON)), userHandler::queryAllUser)
                .andRoute(GET("/user/queryById/{id}").and(accept(MediaType.APPLICATION_JSON)), userHandler::queryUserById)
                .andRoute(POST("/user/save").and(accept(MediaType.APPLICATION_JSON)), userHandler::saveUser)
                .andRoute(PUT("/user/modify/{id}").and(accept(MediaType.APPLICATION_JSON)), userHandler::modifyUser)
                .andRoute(DELETE("/user/delete/{id}").and(accept(MediaType.APPLICATION_JSON)), userHandler::deleteUser);
    }
}
