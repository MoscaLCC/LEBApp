package com.leb.app.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.leb.app.IntegrationTest;
import com.leb.app.domain.Point;
import com.leb.app.domain.UserInfo;
import com.leb.app.repository.PointRepository;
import com.leb.app.service.criteria.PointCriteria;
import com.leb.app.service.dto.PointDTO;
import com.leb.app.service.mapper.PointMapper;
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
 * Integration tests for the {@link PointResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class PointResourceIT {

    private static final String DEFAULT_OPENING_TIME = "AAAAAAAAAA";
    private static final String UPDATED_OPENING_TIME = "BBBBBBBBBB";

    private static final String DEFAULT_CLOSING_TIME = "AAAAAAAAAA";
    private static final String UPDATED_CLOSING_TIME = "BBBBBBBBBB";

    private static final String DEFAULT_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_ADDRESS = "BBBBBBBBBB";

    private static final Integer DEFAULT_NUMBER_OF_DELIVERIES = 1;
    private static final Integer UPDATED_NUMBER_OF_DELIVERIES = 2;
    private static final Integer SMALLER_NUMBER_OF_DELIVERIES = 1 - 1;

    private static final String ENTITY_API_URL = "/api/points";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private PointRepository pointRepository;

    @Autowired
    private PointMapper pointMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPointMockMvc;

    private Point point;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Point createEntity(EntityManager em) {
        Point point = new Point()
            .openingTime(DEFAULT_OPENING_TIME)
            .closingTime(DEFAULT_CLOSING_TIME)
            .address(DEFAULT_ADDRESS)
            .numberOfDeliveries(DEFAULT_NUMBER_OF_DELIVERIES);
        // Add required entity
        UserInfo userInfo;
        if (TestUtil.findAll(em, UserInfo.class).isEmpty()) {
            userInfo = UserInfoResourceIT.createEntity(em);
            em.persist(userInfo);
            em.flush();
        } else {
            userInfo = TestUtil.findAll(em, UserInfo.class).get(0);
        }
        point.setOwnerPoint(userInfo);
        return point;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Point createUpdatedEntity(EntityManager em) {
        Point point = new Point()
            .openingTime(UPDATED_OPENING_TIME)
            .closingTime(UPDATED_CLOSING_TIME)
            .address(UPDATED_ADDRESS)
            .numberOfDeliveries(UPDATED_NUMBER_OF_DELIVERIES);
        // Add required entity
        UserInfo userInfo;
        if (TestUtil.findAll(em, UserInfo.class).isEmpty()) {
            userInfo = UserInfoResourceIT.createUpdatedEntity(em);
            em.persist(userInfo);
            em.flush();
        } else {
            userInfo = TestUtil.findAll(em, UserInfo.class).get(0);
        }
        point.setOwnerPoint(userInfo);
        return point;
    }

    @BeforeEach
    public void initTest() {
        point = createEntity(em);
    }

    @Test
    @Transactional
    void createPoint() throws Exception {
        int databaseSizeBeforeCreate = pointRepository.findAll().size();
        // Create the Point
        PointDTO pointDTO = pointMapper.toDto(point);
        restPointMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(pointDTO)))
            .andExpect(status().isCreated());

        // Validate the Point in the database
        List<Point> pointList = pointRepository.findAll();
        assertThat(pointList).hasSize(databaseSizeBeforeCreate + 1);
        Point testPoint = pointList.get(pointList.size() - 1);
        assertThat(testPoint.getOpeningTime()).isEqualTo(DEFAULT_OPENING_TIME);
        assertThat(testPoint.getClosingTime()).isEqualTo(DEFAULT_CLOSING_TIME);
        assertThat(testPoint.getAddress()).isEqualTo(DEFAULT_ADDRESS);
        assertThat(testPoint.getNumberOfDeliveries()).isEqualTo(DEFAULT_NUMBER_OF_DELIVERIES);
    }

    @Test
    @Transactional
    void createPointWithExistingId() throws Exception {
        // Create the Point with an existing ID
        point.setId(1L);
        PointDTO pointDTO = pointMapper.toDto(point);

        int databaseSizeBeforeCreate = pointRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPointMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(pointDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Point in the database
        List<Point> pointList = pointRepository.findAll();
        assertThat(pointList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllPoints() throws Exception {
        // Initialize the database
        pointRepository.saveAndFlush(point);

        // Get all the pointList
        restPointMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(point.getId().intValue())))
            .andExpect(jsonPath("$.[*].openingTime").value(hasItem(DEFAULT_OPENING_TIME)))
            .andExpect(jsonPath("$.[*].closingTime").value(hasItem(DEFAULT_CLOSING_TIME)))
            .andExpect(jsonPath("$.[*].address").value(hasItem(DEFAULT_ADDRESS)))
            .andExpect(jsonPath("$.[*].numberOfDeliveries").value(hasItem(DEFAULT_NUMBER_OF_DELIVERIES)));
    }

    @Test
    @Transactional
    void getPoint() throws Exception {
        // Initialize the database
        pointRepository.saveAndFlush(point);

        // Get the point
        restPointMockMvc
            .perform(get(ENTITY_API_URL_ID, point.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(point.getId().intValue()))
            .andExpect(jsonPath("$.openingTime").value(DEFAULT_OPENING_TIME))
            .andExpect(jsonPath("$.closingTime").value(DEFAULT_CLOSING_TIME))
            .andExpect(jsonPath("$.address").value(DEFAULT_ADDRESS))
            .andExpect(jsonPath("$.numberOfDeliveries").value(DEFAULT_NUMBER_OF_DELIVERIES));
    }

    @Test
    @Transactional
    void getPointsByIdFiltering() throws Exception {
        // Initialize the database
        pointRepository.saveAndFlush(point);

        Long id = point.getId();

        defaultPointShouldBeFound("id.equals=" + id);
        defaultPointShouldNotBeFound("id.notEquals=" + id);

        defaultPointShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultPointShouldNotBeFound("id.greaterThan=" + id);

        defaultPointShouldBeFound("id.lessThanOrEqual=" + id);
        defaultPointShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllPointsByOpeningTimeIsEqualToSomething() throws Exception {
        // Initialize the database
        pointRepository.saveAndFlush(point);

        // Get all the pointList where openingTime equals to DEFAULT_OPENING_TIME
        defaultPointShouldBeFound("openingTime.equals=" + DEFAULT_OPENING_TIME);

        // Get all the pointList where openingTime equals to UPDATED_OPENING_TIME
        defaultPointShouldNotBeFound("openingTime.equals=" + UPDATED_OPENING_TIME);
    }

    @Test
    @Transactional
    void getAllPointsByOpeningTimeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        pointRepository.saveAndFlush(point);

        // Get all the pointList where openingTime not equals to DEFAULT_OPENING_TIME
        defaultPointShouldNotBeFound("openingTime.notEquals=" + DEFAULT_OPENING_TIME);

        // Get all the pointList where openingTime not equals to UPDATED_OPENING_TIME
        defaultPointShouldBeFound("openingTime.notEquals=" + UPDATED_OPENING_TIME);
    }

    @Test
    @Transactional
    void getAllPointsByOpeningTimeIsInShouldWork() throws Exception {
        // Initialize the database
        pointRepository.saveAndFlush(point);

        // Get all the pointList where openingTime in DEFAULT_OPENING_TIME or UPDATED_OPENING_TIME
        defaultPointShouldBeFound("openingTime.in=" + DEFAULT_OPENING_TIME + "," + UPDATED_OPENING_TIME);

        // Get all the pointList where openingTime equals to UPDATED_OPENING_TIME
        defaultPointShouldNotBeFound("openingTime.in=" + UPDATED_OPENING_TIME);
    }

    @Test
    @Transactional
    void getAllPointsByOpeningTimeIsNullOrNotNull() throws Exception {
        // Initialize the database
        pointRepository.saveAndFlush(point);

        // Get all the pointList where openingTime is not null
        defaultPointShouldBeFound("openingTime.specified=true");

        // Get all the pointList where openingTime is null
        defaultPointShouldNotBeFound("openingTime.specified=false");
    }

    @Test
    @Transactional
    void getAllPointsByOpeningTimeContainsSomething() throws Exception {
        // Initialize the database
        pointRepository.saveAndFlush(point);

        // Get all the pointList where openingTime contains DEFAULT_OPENING_TIME
        defaultPointShouldBeFound("openingTime.contains=" + DEFAULT_OPENING_TIME);

        // Get all the pointList where openingTime contains UPDATED_OPENING_TIME
        defaultPointShouldNotBeFound("openingTime.contains=" + UPDATED_OPENING_TIME);
    }

    @Test
    @Transactional
    void getAllPointsByOpeningTimeNotContainsSomething() throws Exception {
        // Initialize the database
        pointRepository.saveAndFlush(point);

        // Get all the pointList where openingTime does not contain DEFAULT_OPENING_TIME
        defaultPointShouldNotBeFound("openingTime.doesNotContain=" + DEFAULT_OPENING_TIME);

        // Get all the pointList where openingTime does not contain UPDATED_OPENING_TIME
        defaultPointShouldBeFound("openingTime.doesNotContain=" + UPDATED_OPENING_TIME);
    }

    @Test
    @Transactional
    void getAllPointsByClosingTimeIsEqualToSomething() throws Exception {
        // Initialize the database
        pointRepository.saveAndFlush(point);

        // Get all the pointList where closingTime equals to DEFAULT_CLOSING_TIME
        defaultPointShouldBeFound("closingTime.equals=" + DEFAULT_CLOSING_TIME);

        // Get all the pointList where closingTime equals to UPDATED_CLOSING_TIME
        defaultPointShouldNotBeFound("closingTime.equals=" + UPDATED_CLOSING_TIME);
    }

    @Test
    @Transactional
    void getAllPointsByClosingTimeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        pointRepository.saveAndFlush(point);

        // Get all the pointList where closingTime not equals to DEFAULT_CLOSING_TIME
        defaultPointShouldNotBeFound("closingTime.notEquals=" + DEFAULT_CLOSING_TIME);

        // Get all the pointList where closingTime not equals to UPDATED_CLOSING_TIME
        defaultPointShouldBeFound("closingTime.notEquals=" + UPDATED_CLOSING_TIME);
    }

    @Test
    @Transactional
    void getAllPointsByClosingTimeIsInShouldWork() throws Exception {
        // Initialize the database
        pointRepository.saveAndFlush(point);

        // Get all the pointList where closingTime in DEFAULT_CLOSING_TIME or UPDATED_CLOSING_TIME
        defaultPointShouldBeFound("closingTime.in=" + DEFAULT_CLOSING_TIME + "," + UPDATED_CLOSING_TIME);

        // Get all the pointList where closingTime equals to UPDATED_CLOSING_TIME
        defaultPointShouldNotBeFound("closingTime.in=" + UPDATED_CLOSING_TIME);
    }

    @Test
    @Transactional
    void getAllPointsByClosingTimeIsNullOrNotNull() throws Exception {
        // Initialize the database
        pointRepository.saveAndFlush(point);

        // Get all the pointList where closingTime is not null
        defaultPointShouldBeFound("closingTime.specified=true");

        // Get all the pointList where closingTime is null
        defaultPointShouldNotBeFound("closingTime.specified=false");
    }

    @Test
    @Transactional
    void getAllPointsByClosingTimeContainsSomething() throws Exception {
        // Initialize the database
        pointRepository.saveAndFlush(point);

        // Get all the pointList where closingTime contains DEFAULT_CLOSING_TIME
        defaultPointShouldBeFound("closingTime.contains=" + DEFAULT_CLOSING_TIME);

        // Get all the pointList where closingTime contains UPDATED_CLOSING_TIME
        defaultPointShouldNotBeFound("closingTime.contains=" + UPDATED_CLOSING_TIME);
    }

    @Test
    @Transactional
    void getAllPointsByClosingTimeNotContainsSomething() throws Exception {
        // Initialize the database
        pointRepository.saveAndFlush(point);

        // Get all the pointList where closingTime does not contain DEFAULT_CLOSING_TIME
        defaultPointShouldNotBeFound("closingTime.doesNotContain=" + DEFAULT_CLOSING_TIME);

        // Get all the pointList where closingTime does not contain UPDATED_CLOSING_TIME
        defaultPointShouldBeFound("closingTime.doesNotContain=" + UPDATED_CLOSING_TIME);
    }

    @Test
    @Transactional
    void getAllPointsByAddressIsEqualToSomething() throws Exception {
        // Initialize the database
        pointRepository.saveAndFlush(point);

        // Get all the pointList where address equals to DEFAULT_ADDRESS
        defaultPointShouldBeFound("address.equals=" + DEFAULT_ADDRESS);

        // Get all the pointList where address equals to UPDATED_ADDRESS
        defaultPointShouldNotBeFound("address.equals=" + UPDATED_ADDRESS);
    }

    @Test
    @Transactional
    void getAllPointsByAddressIsNotEqualToSomething() throws Exception {
        // Initialize the database
        pointRepository.saveAndFlush(point);

        // Get all the pointList where address not equals to DEFAULT_ADDRESS
        defaultPointShouldNotBeFound("address.notEquals=" + DEFAULT_ADDRESS);

        // Get all the pointList where address not equals to UPDATED_ADDRESS
        defaultPointShouldBeFound("address.notEquals=" + UPDATED_ADDRESS);
    }

    @Test
    @Transactional
    void getAllPointsByAddressIsInShouldWork() throws Exception {
        // Initialize the database
        pointRepository.saveAndFlush(point);

        // Get all the pointList where address in DEFAULT_ADDRESS or UPDATED_ADDRESS
        defaultPointShouldBeFound("address.in=" + DEFAULT_ADDRESS + "," + UPDATED_ADDRESS);

        // Get all the pointList where address equals to UPDATED_ADDRESS
        defaultPointShouldNotBeFound("address.in=" + UPDATED_ADDRESS);
    }

    @Test
    @Transactional
    void getAllPointsByAddressIsNullOrNotNull() throws Exception {
        // Initialize the database
        pointRepository.saveAndFlush(point);

        // Get all the pointList where address is not null
        defaultPointShouldBeFound("address.specified=true");

        // Get all the pointList where address is null
        defaultPointShouldNotBeFound("address.specified=false");
    }

    @Test
    @Transactional
    void getAllPointsByAddressContainsSomething() throws Exception {
        // Initialize the database
        pointRepository.saveAndFlush(point);

        // Get all the pointList where address contains DEFAULT_ADDRESS
        defaultPointShouldBeFound("address.contains=" + DEFAULT_ADDRESS);

        // Get all the pointList where address contains UPDATED_ADDRESS
        defaultPointShouldNotBeFound("address.contains=" + UPDATED_ADDRESS);
    }

    @Test
    @Transactional
    void getAllPointsByAddressNotContainsSomething() throws Exception {
        // Initialize the database
        pointRepository.saveAndFlush(point);

        // Get all the pointList where address does not contain DEFAULT_ADDRESS
        defaultPointShouldNotBeFound("address.doesNotContain=" + DEFAULT_ADDRESS);

        // Get all the pointList where address does not contain UPDATED_ADDRESS
        defaultPointShouldBeFound("address.doesNotContain=" + UPDATED_ADDRESS);
    }

    @Test
    @Transactional
    void getAllPointsByNumberOfDeliveriesIsEqualToSomething() throws Exception {
        // Initialize the database
        pointRepository.saveAndFlush(point);

        // Get all the pointList where numberOfDeliveries equals to DEFAULT_NUMBER_OF_DELIVERIES
        defaultPointShouldBeFound("numberOfDeliveries.equals=" + DEFAULT_NUMBER_OF_DELIVERIES);

        // Get all the pointList where numberOfDeliveries equals to UPDATED_NUMBER_OF_DELIVERIES
        defaultPointShouldNotBeFound("numberOfDeliveries.equals=" + UPDATED_NUMBER_OF_DELIVERIES);
    }

    @Test
    @Transactional
    void getAllPointsByNumberOfDeliveriesIsNotEqualToSomething() throws Exception {
        // Initialize the database
        pointRepository.saveAndFlush(point);

        // Get all the pointList where numberOfDeliveries not equals to DEFAULT_NUMBER_OF_DELIVERIES
        defaultPointShouldNotBeFound("numberOfDeliveries.notEquals=" + DEFAULT_NUMBER_OF_DELIVERIES);

        // Get all the pointList where numberOfDeliveries not equals to UPDATED_NUMBER_OF_DELIVERIES
        defaultPointShouldBeFound("numberOfDeliveries.notEquals=" + UPDATED_NUMBER_OF_DELIVERIES);
    }

    @Test
    @Transactional
    void getAllPointsByNumberOfDeliveriesIsInShouldWork() throws Exception {
        // Initialize the database
        pointRepository.saveAndFlush(point);

        // Get all the pointList where numberOfDeliveries in DEFAULT_NUMBER_OF_DELIVERIES or UPDATED_NUMBER_OF_DELIVERIES
        defaultPointShouldBeFound("numberOfDeliveries.in=" + DEFAULT_NUMBER_OF_DELIVERIES + "," + UPDATED_NUMBER_OF_DELIVERIES);

        // Get all the pointList where numberOfDeliveries equals to UPDATED_NUMBER_OF_DELIVERIES
        defaultPointShouldNotBeFound("numberOfDeliveries.in=" + UPDATED_NUMBER_OF_DELIVERIES);
    }

    @Test
    @Transactional
    void getAllPointsByNumberOfDeliveriesIsNullOrNotNull() throws Exception {
        // Initialize the database
        pointRepository.saveAndFlush(point);

        // Get all the pointList where numberOfDeliveries is not null
        defaultPointShouldBeFound("numberOfDeliveries.specified=true");

        // Get all the pointList where numberOfDeliveries is null
        defaultPointShouldNotBeFound("numberOfDeliveries.specified=false");
    }

    @Test
    @Transactional
    void getAllPointsByNumberOfDeliveriesIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        pointRepository.saveAndFlush(point);

        // Get all the pointList where numberOfDeliveries is greater than or equal to DEFAULT_NUMBER_OF_DELIVERIES
        defaultPointShouldBeFound("numberOfDeliveries.greaterThanOrEqual=" + DEFAULT_NUMBER_OF_DELIVERIES);

        // Get all the pointList where numberOfDeliveries is greater than or equal to UPDATED_NUMBER_OF_DELIVERIES
        defaultPointShouldNotBeFound("numberOfDeliveries.greaterThanOrEqual=" + UPDATED_NUMBER_OF_DELIVERIES);
    }

    @Test
    @Transactional
    void getAllPointsByNumberOfDeliveriesIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        pointRepository.saveAndFlush(point);

        // Get all the pointList where numberOfDeliveries is less than or equal to DEFAULT_NUMBER_OF_DELIVERIES
        defaultPointShouldBeFound("numberOfDeliveries.lessThanOrEqual=" + DEFAULT_NUMBER_OF_DELIVERIES);

        // Get all the pointList where numberOfDeliveries is less than or equal to SMALLER_NUMBER_OF_DELIVERIES
        defaultPointShouldNotBeFound("numberOfDeliveries.lessThanOrEqual=" + SMALLER_NUMBER_OF_DELIVERIES);
    }

    @Test
    @Transactional
    void getAllPointsByNumberOfDeliveriesIsLessThanSomething() throws Exception {
        // Initialize the database
        pointRepository.saveAndFlush(point);

        // Get all the pointList where numberOfDeliveries is less than DEFAULT_NUMBER_OF_DELIVERIES
        defaultPointShouldNotBeFound("numberOfDeliveries.lessThan=" + DEFAULT_NUMBER_OF_DELIVERIES);

        // Get all the pointList where numberOfDeliveries is less than UPDATED_NUMBER_OF_DELIVERIES
        defaultPointShouldBeFound("numberOfDeliveries.lessThan=" + UPDATED_NUMBER_OF_DELIVERIES);
    }

    @Test
    @Transactional
    void getAllPointsByNumberOfDeliveriesIsGreaterThanSomething() throws Exception {
        // Initialize the database
        pointRepository.saveAndFlush(point);

        // Get all the pointList where numberOfDeliveries is greater than DEFAULT_NUMBER_OF_DELIVERIES
        defaultPointShouldNotBeFound("numberOfDeliveries.greaterThan=" + DEFAULT_NUMBER_OF_DELIVERIES);

        // Get all the pointList where numberOfDeliveries is greater than SMALLER_NUMBER_OF_DELIVERIES
        defaultPointShouldBeFound("numberOfDeliveries.greaterThan=" + SMALLER_NUMBER_OF_DELIVERIES);
    }

    @Test
    @Transactional
    void getAllPointsByOwnerPointIsEqualToSomething() throws Exception {
        // Initialize the database
        pointRepository.saveAndFlush(point);
        UserInfo ownerPoint;
        if (TestUtil.findAll(em, UserInfo.class).isEmpty()) {
            ownerPoint = UserInfoResourceIT.createEntity(em);
            em.persist(ownerPoint);
            em.flush();
        } else {
            ownerPoint = TestUtil.findAll(em, UserInfo.class).get(0);
        }
        em.persist(ownerPoint);
        em.flush();
        point.setOwnerPoint(ownerPoint);
        pointRepository.saveAndFlush(point);
        Long ownerPointId = ownerPoint.getId();

        // Get all the pointList where ownerPoint equals to ownerPointId
        defaultPointShouldBeFound("ownerPointId.equals=" + ownerPointId);

        // Get all the pointList where ownerPoint equals to (ownerPointId + 1)
        defaultPointShouldNotBeFound("ownerPointId.equals=" + (ownerPointId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultPointShouldBeFound(String filter) throws Exception {
        restPointMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(point.getId().intValue())))
            .andExpect(jsonPath("$.[*].openingTime").value(hasItem(DEFAULT_OPENING_TIME)))
            .andExpect(jsonPath("$.[*].closingTime").value(hasItem(DEFAULT_CLOSING_TIME)))
            .andExpect(jsonPath("$.[*].address").value(hasItem(DEFAULT_ADDRESS)))
            .andExpect(jsonPath("$.[*].numberOfDeliveries").value(hasItem(DEFAULT_NUMBER_OF_DELIVERIES)));

        // Check, that the count call also returns 1
        restPointMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultPointShouldNotBeFound(String filter) throws Exception {
        restPointMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restPointMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingPoint() throws Exception {
        // Get the point
        restPointMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewPoint() throws Exception {
        // Initialize the database
        pointRepository.saveAndFlush(point);

        int databaseSizeBeforeUpdate = pointRepository.findAll().size();

        // Update the point
        Point updatedPoint = pointRepository.findById(point.getId()).get();
        // Disconnect from session so that the updates on updatedPoint are not directly saved in db
        em.detach(updatedPoint);
        updatedPoint
            .openingTime(UPDATED_OPENING_TIME)
            .closingTime(UPDATED_CLOSING_TIME)
            .address(UPDATED_ADDRESS)
            .numberOfDeliveries(UPDATED_NUMBER_OF_DELIVERIES);
        PointDTO pointDTO = pointMapper.toDto(updatedPoint);

        restPointMockMvc
            .perform(
                put(ENTITY_API_URL_ID, pointDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(pointDTO))
            )
            .andExpect(status().isOk());

        // Validate the Point in the database
        List<Point> pointList = pointRepository.findAll();
        assertThat(pointList).hasSize(databaseSizeBeforeUpdate);
        Point testPoint = pointList.get(pointList.size() - 1);
        assertThat(testPoint.getOpeningTime()).isEqualTo(UPDATED_OPENING_TIME);
        assertThat(testPoint.getClosingTime()).isEqualTo(UPDATED_CLOSING_TIME);
        assertThat(testPoint.getAddress()).isEqualTo(UPDATED_ADDRESS);
        assertThat(testPoint.getNumberOfDeliveries()).isEqualTo(UPDATED_NUMBER_OF_DELIVERIES);
    }

    @Test
    @Transactional
    void putNonExistingPoint() throws Exception {
        int databaseSizeBeforeUpdate = pointRepository.findAll().size();
        point.setId(count.incrementAndGet());

        // Create the Point
        PointDTO pointDTO = pointMapper.toDto(point);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPointMockMvc
            .perform(
                put(ENTITY_API_URL_ID, pointDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(pointDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Point in the database
        List<Point> pointList = pointRepository.findAll();
        assertThat(pointList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchPoint() throws Exception {
        int databaseSizeBeforeUpdate = pointRepository.findAll().size();
        point.setId(count.incrementAndGet());

        // Create the Point
        PointDTO pointDTO = pointMapper.toDto(point);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPointMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(pointDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Point in the database
        List<Point> pointList = pointRepository.findAll();
        assertThat(pointList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamPoint() throws Exception {
        int databaseSizeBeforeUpdate = pointRepository.findAll().size();
        point.setId(count.incrementAndGet());

        // Create the Point
        PointDTO pointDTO = pointMapper.toDto(point);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPointMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(pointDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Point in the database
        List<Point> pointList = pointRepository.findAll();
        assertThat(pointList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdatePointWithPatch() throws Exception {
        // Initialize the database
        pointRepository.saveAndFlush(point);

        int databaseSizeBeforeUpdate = pointRepository.findAll().size();

        // Update the point using partial update
        Point partialUpdatedPoint = new Point();
        partialUpdatedPoint.setId(point.getId());

        partialUpdatedPoint.closingTime(UPDATED_CLOSING_TIME).address(UPDATED_ADDRESS);

        restPointMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPoint.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPoint))
            )
            .andExpect(status().isOk());

        // Validate the Point in the database
        List<Point> pointList = pointRepository.findAll();
        assertThat(pointList).hasSize(databaseSizeBeforeUpdate);
        Point testPoint = pointList.get(pointList.size() - 1);
        assertThat(testPoint.getOpeningTime()).isEqualTo(DEFAULT_OPENING_TIME);
        assertThat(testPoint.getClosingTime()).isEqualTo(UPDATED_CLOSING_TIME);
        assertThat(testPoint.getAddress()).isEqualTo(UPDATED_ADDRESS);
        assertThat(testPoint.getNumberOfDeliveries()).isEqualTo(DEFAULT_NUMBER_OF_DELIVERIES);
    }

    @Test
    @Transactional
    void fullUpdatePointWithPatch() throws Exception {
        // Initialize the database
        pointRepository.saveAndFlush(point);

        int databaseSizeBeforeUpdate = pointRepository.findAll().size();

        // Update the point using partial update
        Point partialUpdatedPoint = new Point();
        partialUpdatedPoint.setId(point.getId());

        partialUpdatedPoint
            .openingTime(UPDATED_OPENING_TIME)
            .closingTime(UPDATED_CLOSING_TIME)
            .address(UPDATED_ADDRESS)
            .numberOfDeliveries(UPDATED_NUMBER_OF_DELIVERIES);

        restPointMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPoint.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPoint))
            )
            .andExpect(status().isOk());

        // Validate the Point in the database
        List<Point> pointList = pointRepository.findAll();
        assertThat(pointList).hasSize(databaseSizeBeforeUpdate);
        Point testPoint = pointList.get(pointList.size() - 1);
        assertThat(testPoint.getOpeningTime()).isEqualTo(UPDATED_OPENING_TIME);
        assertThat(testPoint.getClosingTime()).isEqualTo(UPDATED_CLOSING_TIME);
        assertThat(testPoint.getAddress()).isEqualTo(UPDATED_ADDRESS);
        assertThat(testPoint.getNumberOfDeliveries()).isEqualTo(UPDATED_NUMBER_OF_DELIVERIES);
    }

    @Test
    @Transactional
    void patchNonExistingPoint() throws Exception {
        int databaseSizeBeforeUpdate = pointRepository.findAll().size();
        point.setId(count.incrementAndGet());

        // Create the Point
        PointDTO pointDTO = pointMapper.toDto(point);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPointMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, pointDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(pointDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Point in the database
        List<Point> pointList = pointRepository.findAll();
        assertThat(pointList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchPoint() throws Exception {
        int databaseSizeBeforeUpdate = pointRepository.findAll().size();
        point.setId(count.incrementAndGet());

        // Create the Point
        PointDTO pointDTO = pointMapper.toDto(point);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPointMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(pointDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Point in the database
        List<Point> pointList = pointRepository.findAll();
        assertThat(pointList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamPoint() throws Exception {
        int databaseSizeBeforeUpdate = pointRepository.findAll().size();
        point.setId(count.incrementAndGet());

        // Create the Point
        PointDTO pointDTO = pointMapper.toDto(point);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPointMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(pointDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Point in the database
        List<Point> pointList = pointRepository.findAll();
        assertThat(pointList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deletePoint() throws Exception {
        // Initialize the database
        pointRepository.saveAndFlush(point);

        int databaseSizeBeforeDelete = pointRepository.findAll().size();

        // Delete the point
        restPointMockMvc
            .perform(delete(ENTITY_API_URL_ID, point.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Point> pointList = pointRepository.findAll();
        assertThat(pointList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
