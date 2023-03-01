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
    public void create(HttpServletResponse response, @CookieValue(value = "session", required = false) String sessionToken, @RequestBody(required = false) User user) {
        requestHandlerService.handle(response, sessionToken, new CreateHandler(userService, user));
    }

    @GetMapping("/id/{id}")
    public User readById(HttpServletResponse response, @CookieValue(value = "session", required = false) String sessionToken, @PathVariable String id) {
        return (User) requestHandlerService.handle(response, sessionToken, new ReadByIdHandler(userService, id));
    }

    @GetMapping("/login-name/{loginName}")
    public User readByLoginName(HttpServletResponse response, @CookieValue(value = "session", required = false) String sessionToken, @PathVariable String loginName) {
        return (User) requestHandlerService.handle(response, sessionToken, new ReadByLoginNameHandler(userService, loginName));
    }

    @GetMapping("/public-name/{publicName}")
    public User readByPublicName(HttpServletResponse response, @CookieValue(value = "session", required = false) String sessionToken, @PathVariable String publicName) {
        return (User) requestHandlerService.handle(response, sessionToken, new ReadByPublicNameHandler(userService, publicName));
    }

    @PutMapping("/id/{id}")
    public void updateById(HttpServletResponse response, @CookieValue(value = "session", required = false) String sessionToken, @PathVariable String id, @RequestBody(required = false) User user) {
        requestHandlerService.handle(response, sessionToken, new UpdateByIdHandler(userService, id, user));
    }

    @PutMapping("/login-name/{loginName}")
    public void updateByLoginName(HttpServletResponse response, @CookieValue(value = "session", required = false) String sessionToken, @PathVariable String loginName, @RequestBody(required = false) User user) {
        requestHandlerService.handle(response, sessionToken, new UpdateByLoginNameHandler(userService, loginName, user));
    }

    @DeleteMapping("/id/{id}")
    public void deleteById(HttpServletResponse response, @CookieValue(value = "session", required = false) String sessionToken, @PathVariable String id) {
        requestHandlerService.handle(response, sessionToken, new DeleteByIdHandler(userService, id));
    }

    @DeleteMapping("/login-name/{loginName}")
    public void deleteByLoginName(HttpServletResponse response, @CookieValue(value = "session", required = false) String sessionToken, @PathVariable String loginName) {
        requestHandlerService.handle(response, sessionToken, new DeleteByLoginNameHandler(userService, loginName));
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

    private static class ReadByIdHandler implements RequestHandlerService.RequestHandler {
        private final UserService userService;
        private final UUID id;

        public ReadByIdHandler(UserService userService, String id) {
            this.userService =  userService;
            UUID convertedId = null;
            try {
                convertedId = UUID.fromString(id);
            }
            catch (IllegalArgumentException ignored) { }
            this.id = convertedId;
        }

        @Override
        public Object handle(UUID activeUserAccountId) {
            return userService.readById(activeUserAccountId, id);
        }
    }

    private static class ReadByLoginNameHandler implements RequestHandlerService.RequestHandler {
        private final UserService userService;
        private final String loginName;

        public ReadByLoginNameHandler(UserService userService, String loginName) {
            this.userService =  userService;
            this.loginName = loginName;
        }

        @Override
        public Object handle(UUID activeUserAccountId) {
            return userService.readByLoginName(activeUserAccountId, loginName);
        }
    }

    private static class ReadByPublicNameHandler implements RequestHandlerService.RequestHandler {
        private final UserService userService;
        private final String publicName;

        public ReadByPublicNameHandler(UserService userService, String publicName) {
            this.userService =  userService;
            this.publicName = publicName;
        }

        @Override
        public Object handle(UUID activeUserAccountId) {
            return userService.readByPublicName(activeUserAccountId, publicName);
        }
    }

    private static class UpdateByIdHandler implements RequestHandlerService.RequestHandler {
        private final UserService userService;
        private final UUID id;
        private final User user;

        public UpdateByIdHandler(UserService userService, String id, User user) {
            this.userService =  userService;
            UUID convertedId = null;
            try {
                convertedId = UUID.fromString(id);
            }
            catch (IllegalArgumentException ignored) { }
            this.id = convertedId;
            this.user = user;
        }

        @Override
        public Object handle(UUID activeUserAccountId) {
            userService.updateById(activeUserAccountId, id, user);
            return null;
        }
    }

    private static class UpdateByLoginNameHandler implements RequestHandlerService.RequestHandler {
        private final UserService userService;
        private final String loginName;
        private final User user;

        public UpdateByLoginNameHandler(UserService userService, String loginName, User user) {
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

    private static class DeleteByIdHandler implements RequestHandlerService.RequestHandler {
        private final UserService userService;
        private final UUID id;

        public DeleteByIdHandler(UserService userService, String id) {
            this.userService =  userService;
            UUID convertedId = null;
            try {
                convertedId = UUID.fromString(id);
            }
            catch (IllegalArgumentException ignored) { }
            this.id = convertedId;
        }

        @Override
        public Object handle(UUID activeUserAccountId) {
            userService.deleteById(activeUserAccountId, id);
            return null;
        }
    }

    private static class DeleteByLoginNameHandler implements RequestHandlerService.RequestHandler {
        private final UserService userService;
        private final String loginName;

        public DeleteByLoginNameHandler(UserService userService, String loginName) {
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
