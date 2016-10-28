package com.spooky.repository;

import com.spooky.domain.Whiteboard;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Whiteboard entity.
 */
@SuppressWarnings("unused")
public interface WhiteboardRepository extends JpaRepository<Whiteboard,Long> {

}
