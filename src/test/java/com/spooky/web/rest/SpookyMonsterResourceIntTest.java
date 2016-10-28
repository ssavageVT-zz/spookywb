package com.spooky.web.rest;

import com.spooky.SpookywhiteboardApp;

import com.spooky.domain.SpookyMonster;
import com.spooky.repository.SpookyMonsterRepository;
import com.spooky.service.dto.SpookyMonsterDTO;
import com.spooky.service.mapper.SpookyMonsterMapper;

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

import com.spooky.domain.enumeration.MonsterType;
/**
 * Test class for the SpookyMonsterResource REST controller.
 *
 * @see SpookyMonsterResource
 */
@RunWith(SpringRunner.class)

@SpringBootTest(classes = SpookywhiteboardApp.class)

public class SpookyMonsterResourceIntTest {
    private static final String DEFAULT_MONSTER_NAME = "AAAAA";
    private static final String UPDATED_MONSTER_NAME = "BBBBB";

    private static final MonsterType DEFAULT_MONSTER_TYPE = MonsterType.GHOST;
    private static final MonsterType UPDATED_MONSTER_TYPE = MonsterType.GOBLIN;
    private static final String DEFAULT_CURRENT_STATUS = "AAAAA";
    private static final String UPDATED_CURRENT_STATUS = "BBBBB";

    private static final Boolean DEFAULT_IS_IN_BATHROOM = false;
    private static final Boolean UPDATED_IS_IN_BATHROOM = true;

    private static final Boolean DEFAULT_IS_LATE = false;
    private static final Boolean UPDATED_IS_LATE = true;

    @Inject
    private SpookyMonsterRepository spookyMonsterRepository;

    @Inject
    private SpookyMonsterMapper spookyMonsterMapper;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restSpookyMonsterMockMvc;

    private SpookyMonster spookyMonster;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        SpookyMonsterResource spookyMonsterResource = new SpookyMonsterResource();
        ReflectionTestUtils.setField(spookyMonsterResource, "spookyMonsterRepository", spookyMonsterRepository);
        ReflectionTestUtils.setField(spookyMonsterResource, "spookyMonsterMapper", spookyMonsterMapper);
        this.restSpookyMonsterMockMvc = MockMvcBuilders.standaloneSetup(spookyMonsterResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SpookyMonster createEntity(EntityManager em) {
        SpookyMonster spookyMonster = new SpookyMonster()
                .monsterName(DEFAULT_MONSTER_NAME)
                .monsterType(DEFAULT_MONSTER_TYPE)
                .currentStatus(DEFAULT_CURRENT_STATUS)
                .isInBathroom(DEFAULT_IS_IN_BATHROOM)
                .isLate(DEFAULT_IS_LATE);
        return spookyMonster;
    }

    @Before
    public void initTest() {
        spookyMonster = createEntity(em);
    }

    @Test
    @Transactional
    public void createSpookyMonster() throws Exception {
        int databaseSizeBeforeCreate = spookyMonsterRepository.findAll().size();

        // Create the SpookyMonster
        SpookyMonsterDTO spookyMonsterDTO = spookyMonsterMapper.spookyMonsterToSpookyMonsterDTO(spookyMonster);

        restSpookyMonsterMockMvc.perform(post("/api/spooky-monsters")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(spookyMonsterDTO)))
                .andExpect(status().isCreated());

        // Validate the SpookyMonster in the database
        List<SpookyMonster> spookyMonsters = spookyMonsterRepository.findAll();
        assertThat(spookyMonsters).hasSize(databaseSizeBeforeCreate + 1);
        SpookyMonster testSpookyMonster = spookyMonsters.get(spookyMonsters.size() - 1);
        assertThat(testSpookyMonster.getMonsterName()).isEqualTo(DEFAULT_MONSTER_NAME);
        assertThat(testSpookyMonster.getMonsterType()).isEqualTo(DEFAULT_MONSTER_TYPE);
        assertThat(testSpookyMonster.getCurrentStatus()).isEqualTo(DEFAULT_CURRENT_STATUS);
        assertThat(testSpookyMonster.isIsInBathroom()).isEqualTo(DEFAULT_IS_IN_BATHROOM);
        assertThat(testSpookyMonster.isIsLate()).isEqualTo(DEFAULT_IS_LATE);
    }

    @Test
    @Transactional
    public void getAllSpookyMonsters() throws Exception {
        // Initialize the database
        spookyMonsterRepository.saveAndFlush(spookyMonster);

        // Get all the spookyMonsters
        restSpookyMonsterMockMvc.perform(get("/api/spooky-monsters?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.[*].id").value(hasItem(spookyMonster.getId().intValue())))
                .andExpect(jsonPath("$.[*].monsterName").value(hasItem(DEFAULT_MONSTER_NAME.toString())))
                .andExpect(jsonPath("$.[*].monsterType").value(hasItem(DEFAULT_MONSTER_TYPE.toString())))
                .andExpect(jsonPath("$.[*].currentStatus").value(hasItem(DEFAULT_CURRENT_STATUS.toString())))
                .andExpect(jsonPath("$.[*].isInBathroom").value(hasItem(DEFAULT_IS_IN_BATHROOM.booleanValue())))
                .andExpect(jsonPath("$.[*].isLate").value(hasItem(DEFAULT_IS_LATE.booleanValue())));
    }

    @Test
    @Transactional
    public void getSpookyMonster() throws Exception {
        // Initialize the database
        spookyMonsterRepository.saveAndFlush(spookyMonster);

        // Get the spookyMonster
        restSpookyMonsterMockMvc.perform(get("/api/spooky-monsters/{id}", spookyMonster.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(spookyMonster.getId().intValue()))
            .andExpect(jsonPath("$.monsterName").value(DEFAULT_MONSTER_NAME.toString()))
            .andExpect(jsonPath("$.monsterType").value(DEFAULT_MONSTER_TYPE.toString()))
            .andExpect(jsonPath("$.currentStatus").value(DEFAULT_CURRENT_STATUS.toString()))
            .andExpect(jsonPath("$.isInBathroom").value(DEFAULT_IS_IN_BATHROOM.booleanValue()))
            .andExpect(jsonPath("$.isLate").value(DEFAULT_IS_LATE.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingSpookyMonster() throws Exception {
        // Get the spookyMonster
        restSpookyMonsterMockMvc.perform(get("/api/spooky-monsters/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSpookyMonster() throws Exception {
        // Initialize the database
        spookyMonsterRepository.saveAndFlush(spookyMonster);
        int databaseSizeBeforeUpdate = spookyMonsterRepository.findAll().size();

        // Update the spookyMonster
        SpookyMonster updatedSpookyMonster = spookyMonsterRepository.findOne(spookyMonster.getId());
        updatedSpookyMonster
                .monsterName(UPDATED_MONSTER_NAME)
                .monsterType(UPDATED_MONSTER_TYPE)
                .currentStatus(UPDATED_CURRENT_STATUS)
                .isInBathroom(UPDATED_IS_IN_BATHROOM)
                .isLate(UPDATED_IS_LATE);
        SpookyMonsterDTO spookyMonsterDTO = spookyMonsterMapper.spookyMonsterToSpookyMonsterDTO(updatedSpookyMonster);

        restSpookyMonsterMockMvc.perform(put("/api/spooky-monsters")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(spookyMonsterDTO)))
                .andExpect(status().isOk());

        // Validate the SpookyMonster in the database
        List<SpookyMonster> spookyMonsters = spookyMonsterRepository.findAll();
        assertThat(spookyMonsters).hasSize(databaseSizeBeforeUpdate);
        SpookyMonster testSpookyMonster = spookyMonsters.get(spookyMonsters.size() - 1);
        assertThat(testSpookyMonster.getMonsterName()).isEqualTo(UPDATED_MONSTER_NAME);
        assertThat(testSpookyMonster.getMonsterType()).isEqualTo(UPDATED_MONSTER_TYPE);
        assertThat(testSpookyMonster.getCurrentStatus()).isEqualTo(UPDATED_CURRENT_STATUS);
        assertThat(testSpookyMonster.isIsInBathroom()).isEqualTo(UPDATED_IS_IN_BATHROOM);
        assertThat(testSpookyMonster.isIsLate()).isEqualTo(UPDATED_IS_LATE);
    }

    @Test
    @Transactional
    public void deleteSpookyMonster() throws Exception {
        // Initialize the database
        spookyMonsterRepository.saveAndFlush(spookyMonster);
        int databaseSizeBeforeDelete = spookyMonsterRepository.findAll().size();

        // Get the spookyMonster
        restSpookyMonsterMockMvc.perform(delete("/api/spooky-monsters/{id}", spookyMonster.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<SpookyMonster> spookyMonsters = spookyMonsterRepository.findAll();
        assertThat(spookyMonsters).hasSize(databaseSizeBeforeDelete - 1);
    }
}
