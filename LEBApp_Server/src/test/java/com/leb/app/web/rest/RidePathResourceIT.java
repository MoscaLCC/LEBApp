package com.leb.app.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.leb.app.IntegrationTest;
import com.leb.app.domain.RidePath;
import com.leb.app.repository.RidePathRepository;
import com.leb.app.service.criteria.RidePathCriteria;
import com.leb.app.service.dto.RidePathDTO;
import com.leb.app.service.mapper.RidePathMapper;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link RidePathResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class RidePathResourceIT {

    private static final String DEFAULT_SOURCE = "AAAAAAAAAA";
    private static final String UPDATED_SOURCE = "BBBBBBBBBB";

    private static final String DEFAULT_DESTINATION = "AAAAAAAAAA";
    private static final String UPDATED_DESTINATION = "BBBBBBBBBB";

    private static final String DEFAULT_DISTANCE = "AAAAAAAAAA";
    private static final String UPDATED_DISTANCE = "BBBBBBBBBB";

    private static final String DEFAULT_ESTIMATED_TIME = "AAAAAAAAAA";
    private static final String UPDATED_ESTIMATED_TIME = "BBBBBBBBBB";

    private static final Double DEFAULT_RADIUS = 1D;
    private static final Double UPDATED_RADIUS = 2D;
    private static final Double SMALLER_RADIUS = 1D - 1D;

    private static final String ENTITY_API_URL = "/api/ride-paths";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private RidePathRepository ridePathRepository;

    @Autowired
    private RidePathMapper ridePathMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restRidePathMockMvc;

    private RidePath ridePath;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RidePath createEntity(EntityManager em) {
        RidePath ridePath = new RidePath()
            .source(DEFAULT_SOURCE)
            .destination(DEFAULT_DESTINATION)
            .distance(DEFAULT_DISTANCE)
            .estimatedTime(DEFAULT_ESTIMATED_TIME)
            .radius(DEFAULT_RADIUS);
        return ridePath;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RidePath createUpdatedEntity(EntityManager em) {
        RidePath ridePath = new RidePath()
            .source(UPDATED_SOURCE)
            .destination(UPDATED_DESTINATION)
            .distance(UPDATED_DISTANCE)
            .estimatedTime(UPDATED_ESTIMATED_TIME)
            .radius(UPDATED_RADIUS);
        return ridePath;
    }

    @BeforeEach
    public void initTest() {
        ridePath = createEntity(em);
    }

    @Test
    @Transactional
    void createRidePath() throws Exception {
        int databaseSizeBeforeCreate = ridePathRepository.findAll().size();
        // Create the RidePath
        RidePathDTO ridePathDTO = ridePathMapper.toDto(ridePath);
        restRidePathMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(ridePathDTO)))
            .andExpect(status().isCreated());

        // Validate the RidePath in the database
        List<RidePath> ridePathList = ridePathRepository.findAll();
        assertThat(ridePathList).hasSize(databaseSizeBeforeCreate + 1);
        RidePath testRidePath = ridePathList.get(ridePathList.size() - 1);
        assertThat(testRidePath.getSource()).isEqualTo(DEFAULT_SOURCE);
        assertThat(testRidePath.getDestination()).isEqualTo(DEFAULT_DESTINATION);
        assertThat(testRidePath.getDistance()).isEqualTo(DEFAULT_DISTANCE);
        assertThat(testRidePath.getEstimatedTime()).isEqualTo(DEFAULT_ESTIMATED_TIME);
        assertThat(testRidePath.getRadius()).isEqualTo(DEFAULT_RADIUS);
    }

    @Test
    @Transactional
    void createRidePathWithExistingId() throws Exception {
        // Create the RidePath with an existing ID
        ridePath.setId(1L);
        RidePathDTO ridePathDTO = ridePathMapper.toDto(ridePath);

        int databaseSizeBeforeCreate = ridePathRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restRidePathMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(ridePathDTO)))
            .andExpect(status().isBadRequest());

        // Validate the RidePath in the database
        List<RidePath> ridePathList = ridePathRepository.findAll();
        assertThat(ridePathList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllRidePaths() throws Exception {
        // Initialize the database
        ridePathRepository.saveAndFlush(ridePath);

        // Get all the ridePathList
        restRidePathMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(ridePath.getId().intValue())))
            .andExpect(jsonPath("$.[*].source").value(hasItem(DEFAULT_SOURCE)))
            .andExpect(jsonPath("$.[*].destination").value(hasItem(DEFAULT_DESTINATION)))
            .andExpect(jsonPath("$.[*].distance").value(hasItem(DEFAULT_DISTANCE)))
            .andExpect(jsonPath("$.[*].estimatedTime").value(hasItem(DEFAULT_ESTIMATED_TIME)))
            .andExpect(jsonPath("$.[*].radius").value(hasItem(DEFAULT_RADIUS.doubleValue())));
    }

    @Test
    @Transactional
    void getRidePath() throws Exception {
        // Initialize the database
        ridePathRepository.saveAndFlush(ridePath);

        // Get the ridePath
        restRidePathMockMvc
            .perform(get(ENTITY_API_URL_ID, ridePath.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(ridePath.getId().intValue()))
            .andExpect(jsonPath("$.source").value(DEFAULT_SOURCE))
            .andExpect(jsonPath("$.destination").value(DEFAULT_DESTINATION))
            .andExpect(jsonPath("$.distance").value(DEFAULT_DISTANCE))
            .andExpect(jsonPath("$.estimatedTime").value(DEFAULT_ESTIMATED_TIME))
            .andExpect(jsonPath("$.radius").value(DEFAULT_RADIUS.doubleValue()));
    }

    @Test
    @Transactional
    void getRidePathsByIdFiltering() throws Exception {
        // Initialize the database
        ridePathRepository.saveAndFlush(ridePath);

        Long id = ridePath.getId();

        defaultRidePathShouldBeFound("id.equals=" + id);
        defaultRidePathShouldNotBeFound("id.notEquals=" + id);

        defaultRidePathShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultRidePathShouldNotBeFound("id.greaterThan=" + id);

        defaultRidePathShouldBeFound("id.lessThanOrEqual=" + id);
        defaultRidePathShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllRidePathsBySourceIsEqualToSomething() throws Exception {
        // Initialize the database
        ridePathRepository.saveAndFlush(ridePath);

        // Get all the ridePathList where source equals to DEFAULT_SOURCE
        defaultRidePathShouldBeFound("source.equals=" + DEFAULT_SOURCE);

        // Get all the ridePathList where source equals to UPDATED_SOURCE
        defaultRidePathShouldNotBeFound("source.equals=" + UPDATED_SOURCE);
    }

    @Test
    @Transactional
    void getAllRidePathsBySourceIsNotEqualToSomething() throws Exception {
        // Initialize the database
        ridePathRepository.saveAndFlush(ridePath);

        // Get all the ridePathList where source not equals to DEFAULT_SOURCE
        defaultRidePathShouldNotBeFound("source.notEquals=" + DEFAULT_SOURCE);

        // Get all the ridePathList where source not equals to UPDATED_SOURCE
        defaultRidePathShouldBeFound("source.notEquals=" + UPDATED_SOURCE);
    }

    @Test
    @Transactional
    void getAllRidePathsBySourceIsInShouldWork() throws Exception {
        // Initialize the database
        ridePathRepository.saveAndFlush(ridePath);

        // Get all the ridePathList where source in DEFAULT_SOURCE or UPDATED_SOURCE
        defaultRidePathShouldBeFound("source.in=" + DEFAULT_SOURCE + "," + UPDATED_SOURCE);

        // Get all the ridePathList where source equals to UPDATED_SOURCE
        defaultRidePathShouldNotBeFound("source.in=" + UPDATED_SOURCE);
    }

    @Test
    @Transactional
    void getAllRidePathsBySourceIsNullOrNotNull() throws Exception {
        // Initialize the database
        ridePathRepository.saveAndFlush(ridePath);

        // Get all the ridePathList where source is not null
        defaultRidePathShouldBeFound("source.specified=true");

        // Get all the ridePathList where source is null
        defaultRidePathShouldNotBeFound("source.specified=false");
    }

    @Test
    @Transactional
    void getAllRidePathsBySourceContainsSomething() throws Exception {
        // Initialize the database
        ridePathRepository.saveAndFlush(ridePath);

        // Get all the ridePathList where source contains DEFAULT_SOURCE
        defaultRidePathShouldBeFound("source.contains=" + DEFAULT_SOURCE);

        // Get all the ridePathList where source contains UPDATED_SOURCE
        defaultRidePathShouldNotBeFound("source.contains=" + UPDATED_SOURCE);
    }

    @Test
    @Transactional
    void getAllRidePathsBySourceNotContainsSomething() throws Exception {
        // Initialize the database
        ridePathRepository.saveAndFlush(ridePath);

        // Get all the ridePathList where source does not contain DEFAULT_SOURCE
        defaultRidePathShouldNotBeFound("source.doesNotContain=" + DEFAULT_SOURCE);

        // Get all the ridePathList where source does not contain UPDATED_SOURCE
        defaultRidePathShouldBeFound("source.doesNotContain=" + UPDATED_SOURCE);
    }

    @Test
    @Transactional
    void getAllRidePathsByDestinationIsEqualToSomething() throws Exception {
        // Initialize the database
        ridePathRepository.saveAndFlush(ridePath);

        // Get all the ridePathList where destination equals to DEFAULT_DESTINATION
        defaultRidePathShouldBeFound("destination.equals=" + DEFAULT_DESTINATION);

        // Get all the ridePathList where destination equals to UPDATED_DESTINATION
        defaultRidePathShouldNotBeFound("destination.equals=" + UPDATED_DESTINATION);
    }

    @Test
    @Transactional
    void getAllRidePathsByDestinationIsNotEqualToSomething() throws Exception {
        // Initialize the database
        ridePathRepository.saveAndFlush(ridePath);

        // Get all the ridePathList where destination not equals to DEFAULT_DESTINATION
        defaultRidePathShouldNotBeFound("destination.notEquals=" + DEFAULT_DESTINATION);

        // Get all the ridePathList where destination not equals to UPDATED_DESTINATION
        defaultRidePathShouldBeFound("destination.notEquals=" + UPDATED_DESTINATION);
    }

    @Test
    @Transactional
    void getAllRidePathsByDestinationIsInShouldWork() throws Exception {
        // Initialize the database
        ridePathRepository.saveAndFlush(ridePath);

        // Get all the ridePathList where destination in DEFAULT_DESTINATION or UPDATED_DESTINATION
        defaultRidePathShouldBeFound("destination.in=" + DEFAULT_DESTINATION + "," + UPDATED_DESTINATION);

        // Get all the ridePathList where destination equals to UPDATED_DESTINATION
        defaultRidePathShouldNotBeFound("destination.in=" + UPDATED_DESTINATION);
    }

    @Test
    @Transactional
    void getAllRidePathsByDestinationIsNullOrNotNull() throws Exception {
        // Initialize the database
        ridePathRepository.saveAndFlush(ridePath);

        // Get all the ridePathList where destination is not null
        defaultRidePathShouldBeFound("destination.specified=true");

        // Get all the ridePathList where destination is null
        defaultRidePathShouldNotBeFound("destination.specified=false");
    }

    @Test
    @Transactional
    void getAllRidePathsByDestinationContainsSomething() throws Exception {
        // Initialize the database
        ridePathRepository.saveAndFlush(ridePath);

        // Get all the ridePathList where destination contains DEFAULT_DESTINATION
        defaultRidePathShouldBeFound("destination.contains=" + DEFAULT_DESTINATION);

        // Get all the ridePathList where destination contains UPDATED_DESTINATION
        defaultRidePathShouldNotBeFound("destination.contains=" + UPDATED_DESTINATION);
    }

    @Test
    @Transactional
    void getAllRidePathsByDestinationNotContainsSomething() throws Exception {
        // Initialize the database
        ridePathRepository.saveAndFlush(ridePath);

        // Get all the ridePathList where destination does not contain DEFAULT_DESTINATION
        defaultRidePathShouldNotBeFound("destination.doesNotContain=" + DEFAULT_DESTINATION);

        // Get all the ridePathList where destination does not contain UPDATED_DESTINATION
        defaultRidePathShouldBeFound("destination.doesNotContain=" + UPDATED_DESTINATION);
    }

    @Test
    @Transactional
    void getAllRidePathsByDistanceIsEqualToSomething() throws Exception {
        // Initialize the database
        ridePathRepository.saveAndFlush(ridePath);

        // Get all the ridePathList where distance equals to DEFAULT_DISTANCE
        defaultRidePathShouldBeFound("distance.equals=" + DEFAULT_DISTANCE);

        // Get all the ridePathList where distance equals to UPDATED_DISTANCE
        defaultRidePathShouldNotBeFound("distance.equals=" + UPDATED_DISTANCE);
    }

    @Test
    @Transactional
    void getAllRidePathsByDistanceIsNotEqualToSomething() throws Exception {
        // Initialize the database
        ridePathRepository.saveAndFlush(ridePath);

        // Get all the ridePathList where distance not equals to DEFAULT_DISTANCE
        defaultRidePathShouldNotBeFound("distance.notEquals=" + DEFAULT_DISTANCE);

        // Get all the ridePathList where distance not equals to UPDATED_DISTANCE
        defaultRidePathShouldBeFound("distance.notEquals=" + UPDATED_DISTANCE);
    }

    @Test
    @Transactional
    void getAllRidePathsByDistanceIsInShouldWork() throws Exception {
        // Initialize the database
        ridePathRepository.saveAndFlush(ridePath);

        // Get all the ridePathList where distance in DEFAULT_DISTANCE or UPDATED_DISTANCE
        defaultRidePathShouldBeFound("distance.in=" + DEFAULT_DISTANCE + "," + UPDATED_DISTANCE);

        // Get all the ridePathList where distance equals to UPDATED_DISTANCE
        defaultRidePathShouldNotBeFound("distance.in=" + UPDATED_DISTANCE);
    }

    @Test
    @Transactional
    void getAllRidePathsByDistanceIsNullOrNotNull() throws Exception {
        // Initialize the database
        ridePathRepository.saveAndFlush(ridePath);

        // Get all the ridePathList where distance is not null
        defaultRidePathShouldBeFound("distance.specified=true");

        // Get all the ridePathList where distance is null
        defaultRidePathShouldNotBeFound("distance.specified=false");
    }

    @Test
    @Transactional
    void getAllRidePathsByDistanceContainsSomething() throws Exception {
        // Initialize the database
        ridePathRepository.saveAndFlush(ridePath);

        // Get all the ridePathList where distance contains DEFAULT_DISTANCE
        defaultRidePathShouldBeFound("distance.contains=" + DEFAULT_DISTANCE);

        // Get all the ridePathList where distance contains UPDATED_DISTANCE
        defaultRidePathShouldNotBeFound("distance.contains=" + UPDATED_DISTANCE);
    }

    @Test
    @Transactional
    void getAllRidePathsByDistanceNotContainsSomething() throws Exception {
        // Initialize the database
        ridePathRepository.saveAndFlush(ridePath);

        // Get all the ridePathList where distance does not contain DEFAULT_DISTANCE
        defaultRidePathShouldNotBeFound("distance.doesNotContain=" + DEFAULT_DISTANCE);

        // Get all the ridePathList where distance does not contain UPDATED_DISTANCE
        defaultRidePathShouldBeFound("distance.doesNotContain=" + UPDATED_DISTANCE);
    }

    @Test
    @Transactional
    void getAllRidePathsByEstimatedTimeIsEqualToSomething() throws Exception {
        // Initialize the database
        ridePathRepository.saveAndFlush(ridePath);

        // Get all the ridePathList where estimatedTime equals to DEFAULT_ESTIMATED_TIME
        defaultRidePathShouldBeFound("estimatedTime.equals=" + DEFAULT_ESTIMATED_TIME);

        // Get all the ridePathList where estimatedTime equals to UPDATED_ESTIMATED_TIME
        defaultRidePathShouldNotBeFound("estimatedTime.equals=" + UPDATED_ESTIMATED_TIME);
    }

    @Test
    @Transactional
    void getAllRidePathsByEstimatedTimeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        ridePathRepository.saveAndFlush(ridePath);

        // Get all the ridePathList where estimatedTime not equals to DEFAULT_ESTIMATED_TIME
        defaultRidePathShouldNotBeFound("estimatedTime.notEquals=" + DEFAULT_ESTIMATED_TIME);

        // Get all the ridePathList where estimatedTime not equals to UPDATED_ESTIMATED_TIME
        defaultRidePathShouldBeFound("estimatedTime.notEquals=" + UPDATED_ESTIMATED_TIME);
    }

    @Test
    @Transactional
    void getAllRidePathsByEstimatedTimeIsInShouldWork() throws Exception {
        // Initialize the database
        ridePathRepository.saveAndFlush(ridePath);

        // Get all the ridePathList where estimatedTime in DEFAULT_ESTIMATED_TIME or UPDATED_ESTIMATED_TIME
        defaultRidePathShouldBeFound("estimatedTime.in=" + DEFAULT_ESTIMATED_TIME + "," + UPDATED_ESTIMATED_TIME);

        // Get all the ridePathList where estimatedTime equals to UPDATED_ESTIMATED_TIME
        defaultRidePathShouldNotBeFound("estimatedTime.in=" + UPDATED_ESTIMATED_TIME);
    }

    @Test
    @Transactional
    void getAllRidePathsByEstimatedTimeIsNullOrNotNull() throws Exception {
        // Initialize the database
        ridePathRepository.saveAndFlush(ridePath);

        // Get all the ridePathList where estimatedTime is not null
        defaultRidePathShouldBeFound("estimatedTime.specified=true");

        // Get all the ridePathList where estimatedTime is null
        defaultRidePathShouldNotBeFound("estimatedTime.specified=false");
    }

    @Test
    @Transactional
    void getAllRidePathsByEstimatedTimeContainsSomething() throws Exception {
        // Initialize the database
        ridePathRepository.saveAndFlush(ridePath);

        // Get all the ridePathList where estimatedTime contains DEFAULT_ESTIMATED_TIME
        defaultRidePathShouldBeFound("estimatedTime.contains=" + DEFAULT_ESTIMATED_TIME);

        // Get all the ridePathList where estimatedTime contains UPDATED_ESTIMATED_TIME
        defaultRidePathShouldNotBeFound("estimatedTime.contains=" + UPDATED_ESTIMATED_TIME);
    }

    @Test
    @Transactional
    void getAllRidePathsByEstimatedTimeNotContainsSomething() throws Exception {
        // Initialize the database
        ridePathRepository.saveAndFlush(ridePath);

        // Get all the ridePathList where estimatedTime does not contain DEFAULT_ESTIMATED_TIME
        defaultRidePathShouldNotBeFound("estimatedTime.doesNotContain=" + DEFAULT_ESTIMATED_TIME);

        // Get all the ridePathList where estimatedTime does not contain UPDATED_ESTIMATED_TIME
        defaultRidePathShouldBeFound("estimatedTime.doesNotContain=" + UPDATED_ESTIMATED_TIME);
    }

    @Test
    @Transactional
    void getAllRidePathsByRadiusIsEqualToSomething() throws Exception {
        // Initialize the database
        ridePathRepository.saveAndFlush(ridePath);

        // Get all the ridePathList where radius equals to DEFAULT_RADIUS
        defaultRidePathShouldBeFound("radius.equals=" + DEFAULT_RADIUS);

        // Get all the ridePathList where radius equals to UPDATED_RADIUS
        defaultRidePathShouldNotBeFound("radius.equals=" + UPDATED_RADIUS);
    }

    @Test
    @Transactional
    void getAllRidePathsByRadiusIsNotEqualToSomething() throws Exception {
        // Initialize the database
        ridePathRepository.saveAndFlush(ridePath);

        // Get all the ridePathList where radius not equals to DEFAULT_RADIUS
        defaultRidePathShouldNotBeFound("radius.notEquals=" + DEFAULT_RADIUS);

        // Get all the ridePathList where radius not equals to UPDATED_RADIUS
        defaultRidePathShouldBeFound("radius.notEquals=" + UPDATED_RADIUS);
    }

    @Test
    @Transactional
    void getAllRidePathsByRadiusIsInShouldWork() throws Exception {
        // Initialize the database
        ridePathRepository.saveAndFlush(ridePath);

        // Get all the ridePathList where radius in DEFAULT_RADIUS or UPDATED_RADIUS
        defaultRidePathShouldBeFound("radius.in=" + DEFAULT_RADIUS + "," + UPDATED_RADIUS);

        // Get all the ridePathList where radius equals to UPDATED_RADIUS
        defaultRidePathShouldNotBeFound("radius.in=" + UPDATED_RADIUS);
    }

    @Test
    @Transactional
    void getAllRidePathsByRadiusIsNullOrNotNull() throws Exception {
        // Initialize the database
        ridePathRepository.saveAndFlush(ridePath);

        // Get all the ridePathList where radius is not null
        defaultRidePathShouldBeFound("radius.specified=true");

        // Get all the ridePathList where radius is null
        defaultRidePathShouldNotBeFound("radius.specified=false");
    }

    @Test
    @Transactional
    void getAllRidePathsByRadiusIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        ridePathRepository.saveAndFlush(ridePath);

        // Get all the ridePathList where radius is greater than or equal to DEFAULT_RADIUS
        defaultRidePathShouldBeFound("radius.greaterThanOrEqual=" + DEFAULT_RADIUS);

        // Get all the ridePathList where radius is greater than or equal to UPDATED_RADIUS
        defaultRidePathShouldNotBeFound("radius.greaterThanOrEqual=" + UPDATED_RADIUS);
    }

    @Test
    @Transactional
    void getAllRidePathsByRadiusIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        ridePathRepository.saveAndFlush(ridePath);

        // Get all the ridePathList where radius is less than or equal to DEFAULT_RADIUS
        defaultRidePathShouldBeFound("radius.lessThanOrEqual=" + DEFAULT_RADIUS);

        // Get all the ridePathList where radius is less than or equal to SMALLER_RADIUS
        defaultRidePathShouldNotBeFound("radius.lessThanOrEqual=" + SMALLER_RADIUS);
    }

    @Test
    @Transactional
    void getAllRidePathsByRadiusIsLessThanSomething() throws Exception {
        // Initialize the database
        ridePathRepository.saveAndFlush(ridePath);

        // Get all the ridePathList where radius is less than DEFAULT_RADIUS
        defaultRidePathShouldNotBeFound("radius.lessThan=" + DEFAULT_RADIUS);

        // Get all the ridePathList where radius is less than UPDATED_RADIUS
        defaultRidePathShouldBeFound("radius.lessThan=" + UPDATED_RADIUS);
    }

    @Test
    @Transactional
    void getAllRidePathsByRadiusIsGreaterThanSomething() throws Exception {
        // Initialize the database
        ridePathRepository.saveAndFlush(ridePath);

        // Get all the ridePathList where radius is greater than DEFAULT_RADIUS
        defaultRidePathShouldNotBeFound("radius.greaterThan=" + DEFAULT_RADIUS);

        // Get all the ridePathList where radius is greater than SMALLER_RADIUS
        defaultRidePathShouldBeFound("radius.greaterThan=" + SMALLER_RADIUS);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultRidePathShouldBeFound(String filter) throws Exception {
        restRidePathMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(ridePath.getId().intValue())))
            .andExpect(jsonPath("$.[*].source").value(hasItem(DEFAULT_SOURCE)))
            .andExpect(jsonPath("$.[*].destination").value(hasItem(DEFAULT_DESTINATION)))
            .andExpect(jsonPath("$.[*].distance").value(hasItem(DEFAULT_DISTANCE)))
            .andExpect(jsonPath("$.[*].estimatedTime").value(hasItem(DEFAULT_ESTIMATED_TIME)))
            .andExpect(jsonPath("$.[*].radius").value(hasItem(DEFAULT_RADIUS.doubleValue())));

        // Check, that the count call also returns 1
        restRidePathMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultRidePathShouldNotBeFound(String filter) throws Exception {
        restRidePathMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restRidePathMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingRidePath() throws Exception {
        // Get the ridePath
        restRidePathMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewRidePath() throws Exception {
        // Initialize the database
        ridePathRepository.saveAndFlush(ridePath);

        int databaseSizeBeforeUpdate = ridePathRepository.findAll().size();

        // Update the ridePath
        RidePath updatedRidePath = ridePathRepository.findById(ridePath.getId()).get();
        // Disconnect from session so that the updates on updatedRidePath are not directly saved in db
        em.detach(updatedRidePath);
        updatedRidePath
            .source(UPDATED_SOURCE)
            .destination(UPDATED_DESTINATION)
            .distance(UPDATED_DISTANCE)
            .estimatedTime(UPDATED_ESTIMATED_TIME)
            .radius(UPDATED_RADIUS);
        RidePathDTO ridePathDTO = ridePathMapper.toDto(updatedRidePath);

        restRidePathMockMvc
            .perform(
                put(ENTITY_API_URL_ID, ridePathDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(ridePathDTO))
            )
            .andExpect(status().isOk());

        // Validate the RidePath in the database
        List<RidePath> ridePathList = ridePathRepository.findAll();
        assertThat(ridePathList).hasSize(databaseSizeBeforeUpdate);
        RidePath testRidePath = ridePathList.get(ridePathList.size() - 1);
        assertThat(testRidePath.getSource()).isEqualTo(UPDATED_SOURCE);
        assertThat(testRidePath.getDestination()).isEqualTo(UPDATED_DESTINATION);
        assertThat(testRidePath.getDistance()).isEqualTo(UPDATED_DISTANCE);
        assertThat(testRidePath.getEstimatedTime()).isEqualTo(UPDATED_ESTIMATED_TIME);
        assertThat(testRidePath.getRadius()).isEqualTo(UPDATED_RADIUS);
    }

    @Test
    @Transactional
    void putNonExistingRidePath() throws Exception {
        int databaseSizeBeforeUpdate = ridePathRepository.findAll().size();
        ridePath.setId(count.incrementAndGet());

        // Create the RidePath
        RidePathDTO ridePathDTO = ridePathMapper.toDto(ridePath);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRidePathMockMvc
            .perform(
                put(ENTITY_API_URL_ID, ridePathDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(ridePathDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the RidePath in the database
        List<RidePath> ridePathList = ridePathRepository.findAll();
        assertThat(ridePathList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchRidePath() throws Exception {
        int databaseSizeBeforeUpdate = ridePathRepository.findAll().size();
        ridePath.setId(count.incrementAndGet());

        // Create the RidePath
        RidePathDTO ridePathDTO = ridePathMapper.toDto(ridePath);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRidePathMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(ridePathDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the RidePath in the database
        List<RidePath> ridePathList = ridePathRepository.findAll();
        assertThat(ridePathList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamRidePath() throws Exception {
        int databaseSizeBeforeUpdate = ridePathRepository.findAll().size();
        ridePath.setId(count.incrementAndGet());

        // Create the RidePath
        RidePathDTO ridePathDTO = ridePathMapper.toDto(ridePath);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRidePathMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(ridePathDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the RidePath in the database
        List<RidePath> ridePathList = ridePathRepository.findAll();
        assertThat(ridePathList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateRidePathWithPatch() throws Exception {
        // Initialize the database
        ridePathRepository.saveAndFlush(ridePath);

        int databaseSizeBeforeUpdate = ridePathRepository.findAll().size();

        // Update the ridePath using partial update
        RidePath partialUpdatedRidePath = new RidePath();
        partialUpdatedRidePath.setId(ridePath.getId());

        partialUpdatedRidePath
            .source(UPDATED_SOURCE)
            .distance(UPDATED_DISTANCE)
            .estimatedTime(UPDATED_ESTIMATED_TIME)
            .radius(UPDATED_RADIUS);

        restRidePathMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRidePath.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedRidePath))
            )
            .andExpect(status().isOk());

        // Validate the RidePath in the database
        List<RidePath> ridePathList = ridePathRepository.findAll();
        assertThat(ridePathList).hasSize(databaseSizeBeforeUpdate);
        RidePath testRidePath = ridePathList.get(ridePathList.size() - 1);
        assertThat(testRidePath.getSource()).isEqualTo(UPDATED_SOURCE);
        assertThat(testRidePath.getDestination()).isEqualTo(DEFAULT_DESTINATION);
        assertThat(testRidePath.getDistance()).isEqualTo(UPDATED_DISTANCE);
        assertThat(testRidePath.getEstimatedTime()).isEqualTo(UPDATED_ESTIMATED_TIME);
        assertThat(testRidePath.getRadius()).isEqualTo(UPDATED_RADIUS);
    }

    @Test
    @Transactional
    void fullUpdateRidePathWithPatch() throws Exception {
        // Initialize the database
        ridePathRepository.saveAndFlush(ridePath);

        int databaseSizeBeforeUpdate = ridePathRepository.findAll().size();

        // Update the ridePath using partial update
        RidePath partialUpdatedRidePath = new RidePath();
        partialUpdatedRidePath.setId(ridePath.getId());

        partialUpdatedRidePath
            .source(UPDATED_SOURCE)
            .destination(UPDATED_DESTINATION)
            .distance(UPDATED_DISTANCE)
            .estimatedTime(UPDATED_ESTIMATED_TIME)
            .radius(UPDATED_RADIUS);

        restRidePathMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRidePath.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedRidePath))
            )
            .andExpect(status().isOk());

        // Validate the RidePath in the database
        List<RidePath> ridePathList = ridePathRepository.findAll();
        assertThat(ridePathList).hasSize(databaseSizeBeforeUpdate);
        RidePath testRidePath = ridePathList.get(ridePathList.size() - 1);
        assertThat(testRidePath.getSource()).isEqualTo(UPDATED_SOURCE);
        assertThat(testRidePath.getDestination()).isEqualTo(UPDATED_DESTINATION);
        assertThat(testRidePath.getDistance()).isEqualTo(UPDATED_DISTANCE);
        assertThat(testRidePath.getEstimatedTime()).isEqualTo(UPDATED_ESTIMATED_TIME);
        assertThat(testRidePath.getRadius()).isEqualTo(UPDATED_RADIUS);
    }

    @Test
    @Transactional
    void patchNonExistingRidePath() throws Exception {
        int databaseSizeBeforeUpdate = ridePathRepository.findAll().size();
        ridePath.setId(count.incrementAndGet());

        // Create the RidePath
        RidePathDTO ridePathDTO = ridePathMapper.toDto(ridePath);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRidePathMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, ridePathDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(ridePathDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the RidePath in the database
        List<RidePath> ridePathList = ridePathRepository.findAll();
        assertThat(ridePathList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchRidePath() throws Exception {
        int databaseSizeBeforeUpdate = ridePathRepository.findAll().size();
        ridePath.setId(count.incrementAndGet());

        // Create the RidePath
        RidePathDTO ridePathDTO = ridePathMapper.toDto(ridePath);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRidePathMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(ridePathDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the RidePath in the database
        List<RidePath> ridePathList = ridePathRepository.findAll();
        assertThat(ridePathList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamRidePath() throws Exception {
        int databaseSizeBeforeUpdate = ridePathRepository.findAll().size();
        ridePath.setId(count.incrementAndGet());

        // Create the RidePath
        RidePathDTO ridePathDTO = ridePathMapper.toDto(ridePath);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRidePathMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(ridePathDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the RidePath in the database
        List<RidePath> ridePathList = ridePathRepository.findAll();
        assertThat(ridePathList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteRidePath() throws Exception {
        // Initialize the database
        ridePathRepository.saveAndFlush(ridePath);

        int databaseSizeBeforeDelete = ridePathRepository.findAll().size();

        // Delete the ridePath
        restRidePathMockMvc
            .perform(delete(ENTITY_API_URL_ID, ridePath.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<RidePath> ridePathList = ridePathRepository.findAll();
        assertThat(ridePathList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
