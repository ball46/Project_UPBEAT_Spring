package com.example.demo.Configuration;

import com.sun.security.auth.UserPrincipal;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.DefaultHandshakeHandler;

import java.security.Principal;
import java.util.Map;
import java.util.UUID;
import java.util.logging.Logger;

public class SetPrincipal extends DefaultHandshakeHandler {
        private  final Logger logger = Logger.getLogger(SetPrincipal.class.getName());

        protected Principal UserPrincipal(ServerHttpRequest serverHttpRequest, WebSocketHandler webSocketHandler, Map<String, Object> attributes){
            final String UID = UUID.randomUUID().toString();
            logger.info("User connected: " + UID);
            return new UserPrincipal(UID);
        }
}
