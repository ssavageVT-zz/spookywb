package com.spooky.repository;

import com.spooky.domain.SpookyMonster;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the SpookyMonster entity.
 */
@SuppressWarnings("unused")
public interface SpookyMonsterRepository extends JpaRepository<SpookyMonster,Long> {

}
