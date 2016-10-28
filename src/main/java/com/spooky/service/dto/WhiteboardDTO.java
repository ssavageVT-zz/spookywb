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

    private String whiteboardName;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public String getWhiteboardName() {
        return whiteboardName;
    }

    public void setWhiteboardName(String whiteboardName) {
        this.whiteboardName = whiteboardName;
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
            ", whiteboardName='" + whiteboardName + "'" +
            '}';
    }
}
