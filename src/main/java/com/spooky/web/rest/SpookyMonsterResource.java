package com.spooky.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.spooky.domain.SpookyMonster;

import com.spooky.repository.SpookyMonsterRepository;
import com.spooky.web.rest.util.HeaderUtil;
import com.spooky.web.rest.util.PaginationUtil;
import com.spooky.service.dto.SpookyMonsterDTO;
import com.spooky.service.mapper.SpookyMonsterMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * REST controller for managing SpookyMonster.
 */
@RestController
@RequestMapping("/api")
public class SpookyMonsterResource {

    private final Logger log = LoggerFactory.getLogger(SpookyMonsterResource.class);
        
    @Inject
    private SpookyMonsterRepository spookyMonsterRepository;

    @Inject
    private SpookyMonsterMapper spookyMonsterMapper;

    /**
     * POST  /spooky-monsters : Create a new spookyMonster.
     *
     * @param spookyMonsterDTO the spookyMonsterDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new spookyMonsterDTO, or with status 400 (Bad Request) if the spookyMonster has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/spooky-monsters",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<SpookyMonsterDTO> createSpookyMonster(@RequestBody SpookyMonsterDTO spookyMonsterDTO) throws URISyntaxException {
        log.debug("REST request to save SpookyMonster : {}", spookyMonsterDTO);
        if (spookyMonsterDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("spookyMonster", "idexists", "A new spookyMonster cannot already have an ID")).body(null);
        }
        SpookyMonster spookyMonster = spookyMonsterMapper.spookyMonsterDTOToSpookyMonster(spookyMonsterDTO);
        spookyMonster = spookyMonsterRepository.save(spookyMonster);
        SpookyMonsterDTO result = spookyMonsterMapper.spookyMonsterToSpookyMonsterDTO(spookyMonster);
        return ResponseEntity.created(new URI("/api/spooky-monsters/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("spookyMonster", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /spooky-monsters : Updates an existing spookyMonster.
     *
     * @param spookyMonsterDTO the spookyMonsterDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated spookyMonsterDTO,
     * or with status 400 (Bad Request) if the spookyMonsterDTO is not valid,
     * or with status 500 (Internal Server Error) if the spookyMonsterDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/spooky-monsters",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<SpookyMonsterDTO> updateSpookyMonster(@RequestBody SpookyMonsterDTO spookyMonsterDTO) throws URISyntaxException {
        log.debug("REST request to update SpookyMonster : {}", spookyMonsterDTO);
        if (spookyMonsterDTO.getId() == null) {
            return createSpookyMonster(spookyMonsterDTO);
        }
        SpookyMonster spookyMonster = spookyMonsterMapper.spookyMonsterDTOToSpookyMonster(spookyMonsterDTO);
        spookyMonster = spookyMonsterRepository.save(spookyMonster);
        SpookyMonsterDTO result = spookyMonsterMapper.spookyMonsterToSpookyMonsterDTO(spookyMonster);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("spookyMonster", spookyMonsterDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /spooky-monsters : get all the spookyMonsters.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of spookyMonsters in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(value = "/spooky-monsters",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<SpookyMonsterDTO>> getAllSpookyMonsters(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of SpookyMonsters");
        Page<SpookyMonster> page = spookyMonsterRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/spooky-monsters");
        return new ResponseEntity<>(spookyMonsterMapper.spookyMonstersToSpookyMonsterDTOs(page.getContent()), headers, HttpStatus.OK);
    }

    /**
     * GET  /spooky-monsters/:id : get the "id" spookyMonster.
     *
     * @param id the id of the spookyMonsterDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the spookyMonsterDTO, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/spooky-monsters/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<SpookyMonsterDTO> getSpookyMonster(@PathVariable Long id) {
        log.debug("REST request to get SpookyMonster : {}", id);
        SpookyMonster spookyMonster = spookyMonsterRepository.findOne(id);
        SpookyMonsterDTO spookyMonsterDTO = spookyMonsterMapper.spookyMonsterToSpookyMonsterDTO(spookyMonster);
        return Optional.ofNullable(spookyMonsterDTO)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /spooky-monsters/:id : delete the "id" spookyMonster.
     *
     * @param id the id of the spookyMonsterDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/spooky-monsters/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteSpookyMonster(@PathVariable Long id) {
        log.debug("REST request to delete SpookyMonster : {}", id);
        spookyMonsterRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("spookyMonster", id.toString())).build();
    }

}
