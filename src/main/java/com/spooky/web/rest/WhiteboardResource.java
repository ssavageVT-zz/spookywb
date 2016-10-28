package com.spooky.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.spooky.domain.Whiteboard;

import com.spooky.repository.WhiteboardRepository;
import com.spooky.web.rest.util.HeaderUtil;
import com.spooky.service.dto.WhiteboardDTO;
import com.spooky.service.mapper.WhiteboardMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
 * REST controller for managing Whiteboard.
 */
@RestController
@RequestMapping("/api")
public class WhiteboardResource {

    private final Logger log = LoggerFactory.getLogger(WhiteboardResource.class);
        
    @Inject
    private WhiteboardRepository whiteboardRepository;

    @Inject
    private WhiteboardMapper whiteboardMapper;

    /**
     * POST  /whiteboards : Create a new whiteboard.
     *
     * @param whiteboardDTO the whiteboardDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new whiteboardDTO, or with status 400 (Bad Request) if the whiteboard has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/whiteboards",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<WhiteboardDTO> createWhiteboard(@RequestBody WhiteboardDTO whiteboardDTO) throws URISyntaxException {
        log.debug("REST request to save Whiteboard : {}", whiteboardDTO);
        if (whiteboardDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("whiteboard", "idexists", "A new whiteboard cannot already have an ID")).body(null);
        }
        Whiteboard whiteboard = whiteboardMapper.whiteboardDTOToWhiteboard(whiteboardDTO);
        whiteboard = whiteboardRepository.save(whiteboard);
        WhiteboardDTO result = whiteboardMapper.whiteboardToWhiteboardDTO(whiteboard);
        return ResponseEntity.created(new URI("/api/whiteboards/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("whiteboard", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /whiteboards : Updates an existing whiteboard.
     *
     * @param whiteboardDTO the whiteboardDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated whiteboardDTO,
     * or with status 400 (Bad Request) if the whiteboardDTO is not valid,
     * or with status 500 (Internal Server Error) if the whiteboardDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/whiteboards",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<WhiteboardDTO> updateWhiteboard(@RequestBody WhiteboardDTO whiteboardDTO) throws URISyntaxException {
        log.debug("REST request to update Whiteboard : {}", whiteboardDTO);
        if (whiteboardDTO.getId() == null) {
            return createWhiteboard(whiteboardDTO);
        }
        Whiteboard whiteboard = whiteboardMapper.whiteboardDTOToWhiteboard(whiteboardDTO);
        whiteboard = whiteboardRepository.save(whiteboard);
        WhiteboardDTO result = whiteboardMapper.whiteboardToWhiteboardDTO(whiteboard);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("whiteboard", whiteboardDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /whiteboards : get all the whiteboards.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of whiteboards in body
     */
    @RequestMapping(value = "/whiteboards",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<WhiteboardDTO> getAllWhiteboards() {
        log.debug("REST request to get all Whiteboards");
        List<Whiteboard> whiteboards = whiteboardRepository.findAll();
        return whiteboardMapper.whiteboardsToWhiteboardDTOs(whiteboards);
    }

    /**
     * GET  /whiteboards/:id : get the "id" whiteboard.
     *
     * @param id the id of the whiteboardDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the whiteboardDTO, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/whiteboards/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<WhiteboardDTO> getWhiteboard(@PathVariable Long id) {
        log.debug("REST request to get Whiteboard : {}", id);
        Whiteboard whiteboard = whiteboardRepository.findOne(id);
        WhiteboardDTO whiteboardDTO = whiteboardMapper.whiteboardToWhiteboardDTO(whiteboard);
        return Optional.ofNullable(whiteboardDTO)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /whiteboards/:id : delete the "id" whiteboard.
     *
     * @param id the id of the whiteboardDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/whiteboards/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteWhiteboard(@PathVariable Long id) {
        log.debug("REST request to delete Whiteboard : {}", id);
        whiteboardRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("whiteboard", id.toString())).build();
    }

}
