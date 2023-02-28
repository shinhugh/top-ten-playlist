package org.dev.toptenplaylist.repository;

import org.dev.toptenplaylist.exception.IllegalArgumentException;
import org.dev.toptenplaylist.exception.NoSuchElementException;
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
        Node<Session> sessionNode = tokenToSessionNodeMap.get(token);
        if (sessionNode == null) {
            throw new NoSuchElementException();
        }
        return sessionNode.getContent();
    }

    @Override
    public void set(Session session) {
        Session newSession = new Session(session);
        String token = newSession.getToken();
        UUID userAccountId = newSession.getUserAccountId();
        Date expiration = newSession.getExpiration();
        if (token == null || userAccountId == null || expiration == null) {
            throw new IllegalArgumentException();
        }
        if (tokenToSessionNodeMap.containsKey(token)) {
            deleteSessionNode(tokenToSessionNodeMap.get(token));
        }
        Node<Session> sessionNode = new Node<>(newSession);
        Node<Session> oneOlderNode = newestSessionNode;
        Node<Session> oneNewerNode = null;
        while (oneOlderNode != null && oneOlderNode.getContent().getExpiration().compareTo(newSession.getExpiration()) > 0) {
            oneNewerNode = oneOlderNode;
            oneOlderNode = oneOlderNode.getPreviousNode();
        }
        sessionNode.setPreviousNode(oneOlderNode);
        sessionNode.setNextNode(oneNewerNode);
        if (oneOlderNode != null) {
            oneOlderNode.setNextNode(sessionNode);
        }
        else {
            oldestSessionNode = sessionNode;
        }
        if (oneNewerNode != null) {
            oneNewerNode.setPreviousNode(sessionNode);
        }
        else {
            newestSessionNode = sessionNode;
        }
        tokenToSessionNodeMap.put(token, sessionNode);
        Set<String> tokens =  userAccountIdToTokensMap.get(userAccountId);
        if (tokens == null) {
            tokens = new HashSet<>();
            userAccountIdToTokensMap.put(userAccountId, tokens);
        }
        tokens.add(token);
    }

    @Override
    public void deleteByToken(String token) {
        Node<Session> sessionNode = tokenToSessionNodeMap.get(token);
        if (sessionNode == null) {
            throw new NoSuchElementException();
        }
        deleteSessionNode(sessionNode);
    }

    @Override
    public void deleteByUserAccountId(UUID userAccountId) {
        Set<String> tokens = new HashSet<>(userAccountIdToTokensMap.get(userAccountId));
        for (String token : tokens) {
            deleteSessionNode(tokenToSessionNodeMap.get(token));
        }
    }

    @Override
    public void deleteByLessThanOrEqualToExpiration(Date expiration) {
        Node<Session> currentNode = oldestSessionNode;
        while (currentNode != null && currentNode.getContent().getExpiration().compareTo(expiration) <= 0) {
            Node<Session> deleteNode = currentNode;
            currentNode = currentNode.getNextNode();
            deleteSessionNode(deleteNode);
        }
    }

    private void deleteSessionNode(Node<Session> sessionNode) {
        Session session = sessionNode.getContent();
        String token = session.getToken();
        UUID userAccountId = session.getUserAccountId();
        if (!tokenToSessionNodeMap.containsKey(token)) {
            throw new NoSuchElementException();
        }
        Node<Session> previousNode = sessionNode.getPreviousNode();
        Node<Session> nextNode = sessionNode.getNextNode();
        if (previousNode != null) {
            previousNode.setNextNode(nextNode);
        }
        if (nextNode != null) {
            nextNode.setPreviousNode(previousNode);
        }
        if (oldestSessionNode == sessionNode) {
            oldestSessionNode = nextNode;
        }
        if (newestSessionNode == sessionNode) {
            newestSessionNode = previousNode;
        }
        Set<String> tokens = userAccountIdToTokensMap.get(userAccountId);
        tokens.remove(token);
        if (tokens.isEmpty()) {
            userAccountIdToTokensMap.remove(userAccountId);
        }
        tokenToSessionNodeMap.remove(token);
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
