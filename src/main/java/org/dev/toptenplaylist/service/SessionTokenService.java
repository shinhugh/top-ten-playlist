package org.dev.toptenplaylist.service;

import javax.servlet.http.HttpServletResponse;

public interface SessionTokenService {
    String handleSessionToken(HttpServletResponse response, String requestSessionToken);
}
