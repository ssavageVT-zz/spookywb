package com.spooky.domain;


import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

import com.spooky.domain.enumeration.MonsterType;

/**
 * A SpookyMonster.
 */
@Entity
@Table(name = "spooky_monster")
public class SpookyMonster implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "monster_name")
    private String monsterName;

    @Enumerated(EnumType.STRING)
    @Column(name = "monster_type")
    private MonsterType monsterType;

    @Column(name = "current_status")
    private String currentStatus;

    @Column(name = "is_in_bathroom")
    private Boolean isInBathroom;

    @Column(name = "is_late")
    private Boolean isLate;

    @ManyToOne
    private Whiteboard whiteboard;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMonsterName() {
        return monsterName;
    }

    public SpookyMonster monsterName(String monsterName) {
        this.monsterName = monsterName;
        return this;
    }

    public void setMonsterName(String monsterName) {
        this.monsterName = monsterName;
    }

    public MonsterType getMonsterType() {
        return monsterType;
    }

    public SpookyMonster monsterType(MonsterType monsterType) {
        this.monsterType = monsterType;
        return this;
    }

    public void setMonsterType(MonsterType monsterType) {
        this.monsterType = monsterType;
    }

    public String getCurrentStatus() {
        return currentStatus;
    }

    public SpookyMonster currentStatus(String currentStatus) {
        this.currentStatus = currentStatus;
        return this;
    }

    public void setCurrentStatus(String currentStatus) {
        this.currentStatus = currentStatus;
    }

    public Boolean isIsInBathroom() {
        return isInBathroom;
    }

    public SpookyMonster isInBathroom(Boolean isInBathroom) {
        this.isInBathroom = isInBathroom;
        return this;
    }

    public void setIsInBathroom(Boolean isInBathroom) {
        this.isInBathroom = isInBathroom;
    }

    public Boolean isIsLate() {
        return isLate;
    }

    public SpookyMonster isLate(Boolean isLate) {
        this.isLate = isLate;
        return this;
    }

    public void setIsLate(Boolean isLate) {
        this.isLate = isLate;
    }

    public Whiteboard getWhiteboard() {
        return whiteboard;
    }

    public SpookyMonster whiteboard(Whiteboard whiteboard) {
        this.whiteboard = whiteboard;
        return this;
    }

    public void setWhiteboard(Whiteboard whiteboard) {
        this.whiteboard = whiteboard;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        SpookyMonster spookyMonster = (SpookyMonster) o;
        if(spookyMonster.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, spookyMonster.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "SpookyMonster{" +
            "id=" + id +
            ", monsterName='" + monsterName + "'" +
            ", monsterType='" + monsterType + "'" +
            ", currentStatus='" + currentStatus + "'" +
            ", isInBathroom='" + isInBathroom + "'" +
            ", isLate='" + isLate + "'" +
            '}';
    }
}
