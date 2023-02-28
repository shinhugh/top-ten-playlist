package org.dev.toptenplaylist.controller;

import org.dev.toptenplaylist.model.User;
import org.dev.toptenplaylist.service.RequestHandlerService;
import org.dev.toptenplaylist.service.UserService;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

@RestController
@RequestMapping("/api/user")
public class UserController {
    private final RequestHandlerService requestHandlerService;
    private final UserService userService;

    public UserController(RequestHandlerService requestHandlerService, UserService userService) {
        this.requestHandlerService = requestHandlerService;
        this.userService = userService;
    }

    @PostMapping
    public void create(HttpServletResponse response, @CookieValue(value = "session", required = false) String sessionToken, @RequestBody User user) {
        requestHandlerService.handle(response, sessionToken, new CreateHandler(userService, user));
    }

    @GetMapping("/{loginName}")
    public User read(HttpServletResponse response, @CookieValue(value = "session", required = false) String sessionToken, @PathVariable String loginName) {
        return (User) requestHandlerService.handle(response, sessionToken, new ReadHandler(userService, loginName));
    }

    @PutMapping("/{loginName}")
    public void update(HttpServletResponse response, @CookieValue(value = "session", required = false) String sessionToken, @PathVariable String loginName, @RequestBody User user) {
        requestHandlerService.handle(response, sessionToken, new UpdateHandler(userService, loginName, user));
    }

    @DeleteMapping("/{loginName}")
    public void delete(HttpServletResponse response, @CookieValue(value = "session", required = false) String sessionToken, @PathVariable String loginName) {
        requestHandlerService.handle(response, sessionToken, new DeleteHandler(userService, loginName));
    }

    private static class CreateHandler implements RequestHandlerService.RequestHandler {
        private final UserService userService;
        private final User user;

        public CreateHandler(UserService userService, User user) {
            this.userService =  userService;
            this.user = user;
        }

        @Override
        public Object handle(UUID activeUserAccountId) {
            userService.create(user);
            return null;
        }
    }

    private static class ReadHandler implements RequestHandlerService.RequestHandler {
        private final UserService userService;
        private final String loginName;

        public ReadHandler(UserService userService, String loginName) {
            this.userService =  userService;
            this.loginName = loginName;
        }

        @Override
        public Object handle(UUID activeUserAccountId) {
            return userService.readByLoginName(activeUserAccountId, loginName);
        }
    }

    private static class UpdateHandler implements RequestHandlerService.RequestHandler {
        private final UserService userService;
        private final String loginName;
        private final User user;

        public UpdateHandler(UserService userService, String loginName, User user) {
            this.userService =  userService;
            this.loginName = loginName;
            this.user = user;
        }

        @Override
        public Object handle(UUID activeUserAccountId) {
            userService.updateByLoginName(activeUserAccountId, loginName, user);
            return null;
        }
    }

    private static class DeleteHandler implements RequestHandlerService.RequestHandler {
        private final UserService userService;
        private final String loginName;

        public DeleteHandler(UserService userService, String loginName) {
            this.userService =  userService;
            this.loginName = loginName;
        }

        @Override
        public Object handle(UUID activeUserAccountId) {
            userService.deleteByLoginName(activeUserAccountId, loginName);
            return null;
        }
    }
}
