package com.coyote.gamersquad.domain;

import com.coyote.gamersquad.GeneratedByJHipster;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * FriendshipChat represents a message of the chat between two AppUsers friends.
 */
@Entity
@Table(name = "friendship_chat")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
@GeneratedByJHipster
public class FriendshipChat implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Size(max = 512)
    @Column(name = "message", length = 512, nullable = false)
    private String message;

    @NotNull
    @Column(name = "send_at", nullable = false)
    private Instant sendAt;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = { "appUserOwner", "appUserReceiver" }, allowSetters = true)
    private Friendship friendship;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = { "internalUser" }, allowSetters = true)
    private AppUser sender;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public FriendshipChat id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMessage() {
        return this.message;
    }

    public FriendshipChat message(String message) {
        this.setMessage(message);
        return this;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Instant getSendAt() {
        return this.sendAt;
    }

    public FriendshipChat sendAt(Instant sendAt) {
        this.setSendAt(sendAt);
        return this;
    }

    public void setSendAt(Instant sendAt) {
        this.sendAt = sendAt;
    }

    public Friendship getFriendship() {
        return this.friendship;
    }

    public void setFriendship(Friendship friendship) {
        this.friendship = friendship;
    }

    public FriendshipChat friendship(Friendship friendship) {
        this.setFriendship(friendship);
        return this;
    }

    public AppUser getSender() {
        return this.sender;
    }

    public void setSender(AppUser appUser) {
        this.sender = appUser;
    }

    public FriendshipChat sender(AppUser appUser) {
        this.setSender(appUser);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof FriendshipChat)) {
            return false;
        }
        return id != null && id.equals(((FriendshipChat) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "FriendshipChat{" +
            "id=" + getId() +
            ", message='" + getMessage() + "'" +
            ", sendAt='" + getSendAt() + "'" +
            "}";
    }
}
