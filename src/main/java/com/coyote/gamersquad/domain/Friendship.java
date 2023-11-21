package com.coyote.gamersquad.domain;

import com.coyote.gamersquad.GeneratedByJHipster;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * Friendship represents the friendship relation between two AppUsers.
 */
@Entity
@Table(name = "friendship")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
@GeneratedByJHipster
public class Friendship implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "is_accepted", nullable = false)
    private Boolean isAccepted;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = { "internalUser" }, allowSetters = true)
    private AppUser appUserOwner;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = { "internalUser" }, allowSetters = true)
    private AppUser appUserReceiver;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Friendship id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getIsAccepted() {
        return this.isAccepted;
    }

    public Friendship isAccepted(Boolean isAccepted) {
        this.setIsAccepted(isAccepted);
        return this;
    }

    public void setIsAccepted(Boolean isAccepted) {
        this.isAccepted = isAccepted;
    }

    public AppUser getAppUserOwner() {
        return this.appUserOwner;
    }

    public void setAppUserOwner(AppUser appUser) {
        this.appUserOwner = appUser;
    }

    public Friendship appUserOwner(AppUser appUser) {
        this.setAppUserOwner(appUser);
        return this;
    }

    public AppUser getAppUserReceiver() {
        return this.appUserReceiver;
    }

    public void setAppUserReceiver(AppUser appUser) {
        this.appUserReceiver = appUser;
    }

    public Friendship appUserReceiver(AppUser appUser) {
        this.setAppUserReceiver(appUser);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Friendship)) {
            return false;
        }
        return id != null && id.equals(((Friendship) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Friendship{" +
            "id=" + getId() +
            ", isAccepted='" + getIsAccepted() + "'" +
            "}";
    }
}
