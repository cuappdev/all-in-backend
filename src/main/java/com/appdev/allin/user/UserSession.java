package com.appdev.allin.user;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.util.UUID;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseToken;

@Entity
@Table(name = "sessionIds")
public class UserSession {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(columnDefinition = "uuid")
    private UUID id;

    @Column (nullable = false)
    private String firebaseUid;

    @Column(nullable = false, columnDefinition = "deviceToken")
    private String deviceToken;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user", nullable = false)
    private User user;

    @Column(nullable = false)
    private UUID sessionId;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getFirebaseUid() {
        return firebaseUid;
    }

    public void setFirebaseUid(String firebaseUid) {
        this.firebaseUid = firebaseUid;
    }

    public String getDeviceToken() {
        return deviceToken;
    }

    public void setDeviceToken(String deviceToken) {
        this.deviceToken = deviceToken;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public UUID getSessionId() {
        return sessionId;
    }

    public void setSessionId(UUID sessionId) {
        this.sessionId = sessionId;
    }

    public UserSession() {
        this.sessionId = UUID.randomUUID();
    }


    public static UserSession createSession(String firebaseIdToken, String deviceToken, EntityManager entityManager) {
        try {
            FirebaseToken decodedToken = FirebaseAuth.getInstance().verifyIdToken(firebaseIdToken);
            String firebaseUid = decodedToken.getUid();

            User user = entityManager.createQuery("SELECT user from User user where user.firebaseUid = :firebaseUid", User.class)
                    .setParameter("firebaseUid", firebaseUid)
                    .getResultStream()
                    .findFirst()
                    .orElseGet(() -> {
                User newUser = new User();
                newUser.setFirebaseUid(firebaseUid);
                newUser.setUsername(decodedToken.getName());
                newUser.setEmail(decodedToken.getEmail());
                entityManager.persist(newUser);
                return newUser;
            });
            UserSession session = new UserSession();
            session.setFirebaseUid(firebaseUid);
            session.setUser(user);
            session.setDeviceToken(deviceToken);

            entityManager.persist(session);

            return session;
        } catch (Exception e) {
            throw new RuntimeException("Authentication failed: " + e.getMessage(), e);
        }
    }


}
