package com.spooky.service.dto;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;


/**
 * A DTO for the Whiteboard entity.
 */
public class WhiteboardDTO implements Serializable {

    private Long id;

    private String currentStatus;

    private Boolean isInBathroom;

    private Boolean isLate;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        WhiteboardDTO whiteboardDTO = (WhiteboardDTO) o;

        if ( ! Objects.equals(id, whiteboardDTO.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "WhiteboardDTO{" +
            "id=" + id +
            ", currentStatus='" + currentStatus + "'" +
            ", isInBathroom='" + isInBathroom + "'" +
            ", isLate='" + isLate + "'" +
            '}';
    }
}
