package com.spooky.service.dto;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import com.spooky.domain.enumeration.MonsterType;

/**
 * A DTO for the SpookyMonster entity.
 */
public class SpookyMonsterDTO implements Serializable {

    private Long id;

    private String monsterName;

    private MonsterType monsterType;

    private String currentStatus;

    private Boolean isInBathroom;

    private Boolean isLate;


    private Long whiteboardId;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public String getMonsterName() {
        return monsterName;
    }

    public void setMonsterName(String monsterName) {
        this.monsterName = monsterName;
    }
    public MonsterType getMonsterType() {
        return monsterType;
    }

    public void setMonsterType(MonsterType monsterType) {
        this.monsterType = monsterType;
    }
    public String getCurrentStatus() {
        return currentStatus;
    }

    public void setCurrentStatus(String currentStatus) {
        this.currentStatus = currentStatus;
    }
    public Boolean getIsInBathroom() {
        return isInBathroom;
    }

    public void setIsInBathroom(Boolean isInBathroom) {
        this.isInBathroom = isInBathroom;
    }
    public Boolean getIsLate() {
        return isLate;
    }

    public void setIsLate(Boolean isLate) {
        this.isLate = isLate;
    }

    public Long getWhiteboardId() {
        return whiteboardId;
    }

    public void setWhiteboardId(Long whiteboardId) {
        this.whiteboardId = whiteboardId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        SpookyMonsterDTO spookyMonsterDTO = (SpookyMonsterDTO) o;

        if ( ! Objects.equals(id, spookyMonsterDTO.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "SpookyMonsterDTO{" +
            "id=" + id +
            ", monsterName='" + monsterName + "'" +
            ", monsterType='" + monsterType + "'" +
            ", currentStatus='" + currentStatus + "'" +
            ", isInBathroom='" + isInBathroom + "'" +
            ", isLate='" + isLate + "'" +
            '}';
    }
}
