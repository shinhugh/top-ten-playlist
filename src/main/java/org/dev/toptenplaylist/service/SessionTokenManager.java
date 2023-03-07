package org.dev.toptenplaylist.service;

import org.dev.toptenplaylist.exception.IllegalArgumentException;
import org.dev.toptenplaylist.exception.NoSuchElementException;
import org.dev.toptenplaylist.model.Session;
import org.dev.toptenplaylist.repository.SessionRepository;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;

@Service
public class SessionTokenManager implements SessionTokenService {
    private final SessionRepository sessionRepository;

    public SessionTokenManager(SessionRepository sessionRepository) {
        this.sessionRepository = sessionRepository;
    }

    public String handleSessionToken(HttpServletResponse response, String requestSessionToken) {
        Session session = null;
        try {
            session = sessionRepository.readByToken(requestSessionToken);
        }
        catch (IllegalArgumentException ex) {
            return null;
        }
        catch (NoSuchElementException ex) {
            clearSessionCookie(response);
            return null;
        }
        if (System.currentTimeMillis() < session.getExpiration()) {
            return session.getUserAccountId();
        }
        sessionRepository.deleteByToken(requestSessionToken);
        clearSessionCookie(response);
        return null;
    }

    private void clearSessionCookie(HttpServletResponse response) {
        response.addHeader("Set-Cookie", "session=; Path=/; Max-Age=0; SameSite=strict");
    }
}
