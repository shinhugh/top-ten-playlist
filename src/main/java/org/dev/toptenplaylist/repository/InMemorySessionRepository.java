package org.dev.toptenplaylist.repository;

import org.dev.toptenplaylist.model.Session;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class InMemorySessionRepository implements SessionRepository {
    private Node<Session> newestSessionNode;
    private Node<Session> oldestSessionNode;
    private final Map<String, Node<Session>> tokenToSessionNodeMap;
    private final Map<UUID, Set<String>> userAccountIdToTokensMap;

    public InMemorySessionRepository() {
        tokenToSessionNodeMap = new HashMap<>();
        userAccountIdToTokensMap = new HashMap<>();
    }

    @Override
    public Session readByToken(String token) {
        // TODO
        return null;
    }

    @Override
    public String set(Session session) {
        // TODO
        return null;
    }

    @Override
    public void deleteByToken(String token) {
        // TODO
    }

    @Override
    public void deleteByUserAccountId(UUID userAccountId) {
        // TODO
    }

    @Override
    public void deleteByLessThanOrEqualToExpiration(Date expiration) {
        // TODO
    }

    private static class Node<T> {
        private Node<T> previousNode;
        private Node<T> nextNode;
        private final T content;

        public Node(T content) {
            this.content = content;
        }

        public Node<T> getPreviousNode() {
            return previousNode;
        }

        public void setPreviousNode(Node<T> previousNode) {
            this.previousNode = previousNode;
        }

        public Node<T> getNextNode() {
            return nextNode;
        }

        public void setNextNode(Node<T> nextNode) {
            this.nextNode = nextNode;
        }

        public T getContent() {
            return content;
        }
    }
}
