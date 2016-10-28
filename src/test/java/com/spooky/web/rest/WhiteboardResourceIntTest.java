package com.spooky.web.rest;

import com.spooky.SavickApp;

import com.spooky.domain.Whiteboard;
import com.spooky.repository.WhiteboardRepository;
import com.spooky.service.dto.WhiteboardDTO;
import com.spooky.service.mapper.WhiteboardMapper;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.hamcrest.Matchers.hasItem;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the WhiteboardResource REST controller.
 *
 * @see WhiteboardResource
 */
@RunWith(SpringRunner.class)

@SpringBootTest(classes = SavickApp.class)

public class WhiteboardResourceIntTest {
    private static final String DEFAULT_CURRENT_STATUS = "AAAAA";
    private static final String UPDATED_CURRENT_STATUS = "BBBBB";

    private static final Boolean DEFAULT_IS_IN_BATHROOM = false;
    private static final Boolean UPDATED_IS_IN_BATHROOM = true;

    private static final Boolean DEFAULT_IS_LATE = false;
    private static final Boolean UPDATED_IS_LATE = true;

    @Inject
    private WhiteboardRepository whiteboardRepository;

    @Inject
    private WhiteboardMapper whiteboardMapper;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restWhiteboardMockMvc;

    private Whiteboard whiteboard;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        WhiteboardResource whiteboardResource = new WhiteboardResource();
        ReflectionTestUtils.setField(whiteboardResource, "whiteboardRepository", whiteboardRepository);
        ReflectionTestUtils.setField(whiteboardResource, "whiteboardMapper", whiteboardMapper);
        this.restWhiteboardMockMvc = MockMvcBuilders.standaloneSetup(whiteboardResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Whiteboard createEntity(EntityManager em) {
        Whiteboard whiteboard = new Whiteboard()
                .currentStatus(DEFAULT_CURRENT_STATUS)
                .isInBathroom(DEFAULT_IS_IN_BATHROOM)
                .isLate(DEFAULT_IS_LATE);
        return whiteboard;
    }

    @Before
    public void initTest() {
        whiteboard = createEntity(em);
    }

    @Test
    @Transactional
    public void createWhiteboard() throws Exception {
        int databaseSizeBeforeCreate = whiteboardRepository.findAll().size();

        // Create the Whiteboard
        WhiteboardDTO whiteboardDTO = whiteboardMapper.whiteboardToWhiteboardDTO(whiteboard);

        restWhiteboardMockMvc.perform(post("/api/whiteboards")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(whiteboardDTO)))
                .andExpect(status().isCreated());

        // Validate the Whiteboard in the database
        List<Whiteboard> whiteboards = whiteboardRepository.findAll();
        assertThat(whiteboards).hasSize(databaseSizeBeforeCreate + 1);
        Whiteboard testWhiteboard = whiteboards.get(whiteboards.size() - 1);
        assertThat(testWhiteboard.getCurrentStatus()).isEqualTo(DEFAULT_CURRENT_STATUS);
        assertThat(testWhiteboard.isIsInBathroom()).isEqualTo(DEFAULT_IS_IN_BATHROOM);
        assertThat(testWhiteboard.isIsLate()).isEqualTo(DEFAULT_IS_LATE);
    }

    @Test
    @Transactional
    public void getAllWhiteboards() throws Exception {
        // Initialize the database
        whiteboardRepository.saveAndFlush(whiteboard);

        // Get all the whiteboards
        restWhiteboardMockMvc.perform(get("/api/whiteboards?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.[*].id").value(hasItem(whiteboard.getId().intValue())))
                .andExpect(jsonPath("$.[*].currentStatus").value(hasItem(DEFAULT_CURRENT_STATUS.toString())))
                .andExpect(jsonPath("$.[*].isInBathroom").value(hasItem(DEFAULT_IS_IN_BATHROOM.booleanValue())))
                .andExpect(jsonPath("$.[*].isLate").value(hasItem(DEFAULT_IS_LATE.booleanValue())));
    }

    @Test
    @Transactional
    public void getWhiteboard() throws Exception {
        // Initialize the database
        whiteboardRepository.saveAndFlush(whiteboard);

        // Get the whiteboard
        restWhiteboardMockMvc.perform(get("/api/whiteboards/{id}", whiteboard.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(whiteboard.getId().intValue()))
            .andExpect(jsonPath("$.currentStatus").value(DEFAULT_CURRENT_STATUS.toString()))
            .andExpect(jsonPath("$.isInBathroom").value(DEFAULT_IS_IN_BATHROOM.booleanValue()))
            .andExpect(jsonPath("$.isLate").value(DEFAULT_IS_LATE.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingWhiteboard() throws Exception {
        // Get the whiteboard
        restWhiteboardMockMvc.perform(get("/api/whiteboards/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateWhiteboard() throws Exception {
        // Initialize the database
        whiteboardRepository.saveAndFlush(whiteboard);
        int databaseSizeBeforeUpdate = whiteboardRepository.findAll().size();

        // Update the whiteboard
        Whiteboard updatedWhiteboard = whiteboardRepository.findOne(whiteboard.getId());
        updatedWhiteboard
                .currentStatus(UPDATED_CURRENT_STATUS)
                .isInBathroom(UPDATED_IS_IN_BATHROOM)
                .isLate(UPDATED_IS_LATE);
        WhiteboardDTO whiteboardDTO = whiteboardMapper.whiteboardToWhiteboardDTO(updatedWhiteboard);

        restWhiteboardMockMvc.perform(put("/api/whiteboards")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(whiteboardDTO)))
                .andExpect(status().isOk());

        // Validate the Whiteboard in the database
        List<Whiteboard> whiteboards = whiteboardRepository.findAll();
        assertThat(whiteboards).hasSize(databaseSizeBeforeUpdate);
        Whiteboard testWhiteboard = whiteboards.get(whiteboards.size() - 1);
        assertThat(testWhiteboard.getCurrentStatus()).isEqualTo(UPDATED_CURRENT_STATUS);
        assertThat(testWhiteboard.isIsInBathroom()).isEqualTo(UPDATED_IS_IN_BATHROOM);
        assertThat(testWhiteboard.isIsLate()).isEqualTo(UPDATED_IS_LATE);
    }

    @Test
    @Transactional
    public void deleteWhiteboard() throws Exception {
        // Initialize the database
        whiteboardRepository.saveAndFlush(whiteboard);
        int databaseSizeBeforeDelete = whiteboardRepository.findAll().size();

        // Get the whiteboard
        restWhiteboardMockMvc.perform(delete("/api/whiteboards/{id}", whiteboard.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Whiteboard> whiteboards = whiteboardRepository.findAll();
        assertThat(whiteboards).hasSize(databaseSizeBeforeDelete - 1);
    }
}
