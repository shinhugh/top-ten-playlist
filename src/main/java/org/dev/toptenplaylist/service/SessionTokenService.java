package org.dev.toptenplaylist.service;

import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

public interface SessionTokenService {
    UUID handleSessionToken(HttpServletResponse response, String requestSessionToken);
}
