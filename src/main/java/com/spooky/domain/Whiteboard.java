package com.spooky.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Whiteboard.
 */
@Entity
@Table(name = "whiteboard")
public class Whiteboard implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "whiteboard_name")
    private String whiteboardName;

    @OneToMany(mappedBy = "whiteboard")
    @JsonIgnore
    private Set<SpookyMonster> spookyMonsters = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getWhiteboardName() {
        return whiteboardName;
    }

    public Whiteboard whiteboardName(String whiteboardName) {
        this.whiteboardName = whiteboardName;
        return this;
    }

    public void setWhiteboardName(String whiteboardName) {
        this.whiteboardName = whiteboardName;
    }

    public Set<SpookyMonster> getSpookyMonsters() {
        return spookyMonsters;
    }

    public Whiteboard spookyMonsters(Set<SpookyMonster> spookyMonsters) {
        this.spookyMonsters = spookyMonsters;
        return this;
    }

    public Whiteboard addSpookyMonster(SpookyMonster spookyMonster) {
        spookyMonsters.add(spookyMonster);
        spookyMonster.setWhiteboard(this);
        return this;
    }

    public Whiteboard removeSpookyMonster(SpookyMonster spookyMonster) {
        spookyMonsters.remove(spookyMonster);
        spookyMonster.setWhiteboard(null);
        return this;
    }

    public void setSpookyMonsters(Set<SpookyMonster> spookyMonsters) {
        this.spookyMonsters = spookyMonsters;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Whiteboard whiteboard = (Whiteboard) o;
        if(whiteboard.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, whiteboard.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Whiteboard{" +
            "id=" + id +
            ", whiteboardName='" + whiteboardName + "'" +
            '}';
    }
}
