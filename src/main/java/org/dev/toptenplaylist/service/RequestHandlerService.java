package org.dev.toptenplaylist.service;

import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

public interface RequestHandlerService {
    Object handle(HttpServletResponse response, String requestSessionToken, RequestHandler requestHandler);

    public interface RequestHandler {
        Object handle(UUID activeUserAccountId);
    }
}
