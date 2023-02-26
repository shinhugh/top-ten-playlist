package org.dev.toptenplaylist.repository;

import org.springframework.session.MapSession;
import org.springframework.session.Session;
import org.springframework.session.SessionRepository;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class InMemorySessionRepository implements SessionRepository<Session> {

    Map<String, Session> sessions = new HashMap<>();

    @Override
    public Session createSession() {
        return new MapSession();
    }

    @Override
    public void save(Session session) {
        sessions.put(session.getId(), session);
    }

    @Override
    public Session findById(String s) {
        return sessions.get(s);
    }

    @Override
    public void deleteById(String s) {
        sessions.remove(s);
    }
}
