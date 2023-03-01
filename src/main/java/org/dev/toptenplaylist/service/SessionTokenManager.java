package org.dev.toptenplaylist.service;

import org.dev.toptenplaylist.exception.IllegalArgumentException;
import org.dev.toptenplaylist.exception.NoSuchElementException;
import org.dev.toptenplaylist.model.Session;
import org.dev.toptenplaylist.repository.SessionRepository;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

@Service
public class SessionTokenManager implements SessionTokenService {
    private final SessionRepository sessionRepository;

    public SessionTokenManager(SessionRepository sessionRepository) {
        this.sessionRepository = sessionRepository;
    }

    public UUID handleSessionToken(HttpServletResponse response, String requestSessionToken) {
        UUID userAccountId = null;
        if (requestSessionToken != null) {
            Session session = null;
            try {
                session = sessionRepository.readByToken(requestSessionToken);
            }
            catch (NoSuchElementException ex) {
                clearSessionCookie(response);
            }
            catch (IllegalArgumentException ex) {
                throw new RuntimeException();
            }
            if (session != null) {
                if (System.currentTimeMillis() < session.getExpiration()) {
                    userAccountId = session.getUserAccountId();
                }
                else {
                    try {
                        sessionRepository.deleteByToken(requestSessionToken);
                    }
                    catch (IllegalArgumentException | NoSuchElementException ex) {
                        throw new RuntimeException();
                    }
                    clearSessionCookie(response);
                }
            }
        }
        return userAccountId;
    }

    private void clearSessionCookie(HttpServletResponse response) {
        Cookie cookie = new Cookie("session", null);
        cookie.setPath("/");
        cookie.setMaxAge(0);
        response.addCookie(cookie);
    }
}
