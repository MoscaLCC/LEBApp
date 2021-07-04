package com.leb.app.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.leb.app.IntegrationTest;
import com.leb.app.domain.DeliveryMan;
import com.leb.app.domain.Point;
import com.leb.app.domain.UserInfo;
import com.leb.app.domain.Zone;
import com.leb.app.repository.PointRepository;
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

    private static final Integer DEFAULT_NUMBER_OF_DELIVERIES = 1;
    private static final Integer UPDATED_NUMBER_OF_DELIVERIES = 2;
    private static final Integer SMALLER_NUMBER_OF_DELIVERIES = 1 - 1;

    private static final Double DEFAULT_RECEIVED_VALUE = 1D;
    private static final Double UPDATED_RECEIVED_VALUE = 2D;
    private static final Double SMALLER_RECEIVED_VALUE = 1D - 1D;

    private static final Double DEFAULT_VALUE_TO_RECEIVE = 1D;
    private static final Double UPDATED_VALUE_TO_RECEIVE = 2D;
    private static final Double SMALLER_VALUE_TO_RECEIVE = 1D - 1D;

    private static final Double DEFAULT_RANKING = 1D;
    private static final Double UPDATED_RANKING = 2D;
    private static final Double SMALLER_RANKING = 1D - 1D;

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
            .numberOfDeliveries(DEFAULT_NUMBER_OF_DELIVERIES)
            .receivedValue(DEFAULT_RECEIVED_VALUE)
            .valueToReceive(DEFAULT_VALUE_TO_RECEIVE)
            .ranking(DEFAULT_RANKING);
        // Add required entity
        UserInfo userInfo;
        if (TestUtil.findAll(em, UserInfo.class).isEmpty()) {
            userInfo = UserInfoResourceIT.createEntity(em);
            em.persist(userInfo);
            em.flush();
        } else {
            userInfo = TestUtil.findAll(em, UserInfo.class).get(0);
        }
        point.setUserInfo(userInfo);
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
            .numberOfDeliveries(UPDATED_NUMBER_OF_DELIVERIES)
            .receivedValue(UPDATED_RECEIVED_VALUE)
            .valueToReceive(UPDATED_VALUE_TO_RECEIVE)
            .ranking(UPDATED_RANKING);
        // Add required entity
        UserInfo userInfo;
        if (TestUtil.findAll(em, UserInfo.class).isEmpty()) {
            userInfo = UserInfoResourceIT.createUpdatedEntity(em);
            em.persist(userInfo);
            em.flush();
        } else {
            userInfo = TestUtil.findAll(em, UserInfo.class).get(0);
        }
        point.setUserInfo(userInfo);
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
        assertThat(testPoint.getNumberOfDeliveries()).isEqualTo(DEFAULT_NUMBER_OF_DELIVERIES);
        assertThat(testPoint.getReceivedValue()).isEqualTo(DEFAULT_RECEIVED_VALUE);
        assertThat(testPoint.getValueToReceive()).isEqualTo(DEFAULT_VALUE_TO_RECEIVE);
        assertThat(testPoint.getRanking()).isEqualTo(DEFAULT_RANKING);
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
            .andExpect(jsonPath("$.[*].numberOfDeliveries").value(hasItem(DEFAULT_NUMBER_OF_DELIVERIES)))
            .andExpect(jsonPath("$.[*].receivedValue").value(hasItem(DEFAULT_RECEIVED_VALUE.doubleValue())))
            .andExpect(jsonPath("$.[*].valueToReceive").value(hasItem(DEFAULT_VALUE_TO_RECEIVE.doubleValue())))
            .andExpect(jsonPath("$.[*].ranking").value(hasItem(DEFAULT_RANKING.doubleValue())));
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
            .andExpect(jsonPath("$.numberOfDeliveries").value(DEFAULT_NUMBER_OF_DELIVERIES))
            .andExpect(jsonPath("$.receivedValue").value(DEFAULT_RECEIVED_VALUE.doubleValue()))
            .andExpect(jsonPath("$.valueToReceive").value(DEFAULT_VALUE_TO_RECEIVE.doubleValue()))
            .andExpect(jsonPath("$.ranking").value(DEFAULT_RANKING.doubleValue()));
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
    void getAllPointsByReceivedValueIsEqualToSomething() throws Exception {
        // Initialize the database
        pointRepository.saveAndFlush(point);

        // Get all the pointList where receivedValue equals to DEFAULT_RECEIVED_VALUE
        defaultPointShouldBeFound("receivedValue.equals=" + DEFAULT_RECEIVED_VALUE);

        // Get all the pointList where receivedValue equals to UPDATED_RECEIVED_VALUE
        defaultPointShouldNotBeFound("receivedValue.equals=" + UPDATED_RECEIVED_VALUE);
    }

    @Test
    @Transactional
    void getAllPointsByReceivedValueIsNotEqualToSomething() throws Exception {
        // Initialize the database
        pointRepository.saveAndFlush(point);

        // Get all the pointList where receivedValue not equals to DEFAULT_RECEIVED_VALUE
        defaultPointShouldNotBeFound("receivedValue.notEquals=" + DEFAULT_RECEIVED_VALUE);

        // Get all the pointList where receivedValue not equals to UPDATED_RECEIVED_VALUE
        defaultPointShouldBeFound("receivedValue.notEquals=" + UPDATED_RECEIVED_VALUE);
    }

    @Test
    @Transactional
    void getAllPointsByReceivedValueIsInShouldWork() throws Exception {
        // Initialize the database
        pointRepository.saveAndFlush(point);

        // Get all the pointList where receivedValue in DEFAULT_RECEIVED_VALUE or UPDATED_RECEIVED_VALUE
        defaultPointShouldBeFound("receivedValue.in=" + DEFAULT_RECEIVED_VALUE + "," + UPDATED_RECEIVED_VALUE);

        // Get all the pointList where receivedValue equals to UPDATED_RECEIVED_VALUE
        defaultPointShouldNotBeFound("receivedValue.in=" + UPDATED_RECEIVED_VALUE);
    }

    @Test
    @Transactional
    void getAllPointsByReceivedValueIsNullOrNotNull() throws Exception {
        // Initialize the database
        pointRepository.saveAndFlush(point);

        // Get all the pointList where receivedValue is not null
        defaultPointShouldBeFound("receivedValue.specified=true");

        // Get all the pointList where receivedValue is null
        defaultPointShouldNotBeFound("receivedValue.specified=false");
    }

    @Test
    @Transactional
    void getAllPointsByReceivedValueIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        pointRepository.saveAndFlush(point);

        // Get all the pointList where receivedValue is greater than or equal to DEFAULT_RECEIVED_VALUE
        defaultPointShouldBeFound("receivedValue.greaterThanOrEqual=" + DEFAULT_RECEIVED_VALUE);

        // Get all the pointList where receivedValue is greater than or equal to UPDATED_RECEIVED_VALUE
        defaultPointShouldNotBeFound("receivedValue.greaterThanOrEqual=" + UPDATED_RECEIVED_VALUE);
    }

    @Test
    @Transactional
    void getAllPointsByReceivedValueIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        pointRepository.saveAndFlush(point);

        // Get all the pointList where receivedValue is less than or equal to DEFAULT_RECEIVED_VALUE
        defaultPointShouldBeFound("receivedValue.lessThanOrEqual=" + DEFAULT_RECEIVED_VALUE);

        // Get all the pointList where receivedValue is less than or equal to SMALLER_RECEIVED_VALUE
        defaultPointShouldNotBeFound("receivedValue.lessThanOrEqual=" + SMALLER_RECEIVED_VALUE);
    }

    @Test
    @Transactional
    void getAllPointsByReceivedValueIsLessThanSomething() throws Exception {
        // Initialize the database
        pointRepository.saveAndFlush(point);

        // Get all the pointList where receivedValue is less than DEFAULT_RECEIVED_VALUE
        defaultPointShouldNotBeFound("receivedValue.lessThan=" + DEFAULT_RECEIVED_VALUE);

        // Get all the pointList where receivedValue is less than UPDATED_RECEIVED_VALUE
        defaultPointShouldBeFound("receivedValue.lessThan=" + UPDATED_RECEIVED_VALUE);
    }

    @Test
    @Transactional
    void getAllPointsByReceivedValueIsGreaterThanSomething() throws Exception {
        // Initialize the database
        pointRepository.saveAndFlush(point);

        // Get all the pointList where receivedValue is greater than DEFAULT_RECEIVED_VALUE
        defaultPointShouldNotBeFound("receivedValue.greaterThan=" + DEFAULT_RECEIVED_VALUE);

        // Get all the pointList where receivedValue is greater than SMALLER_RECEIVED_VALUE
        defaultPointShouldBeFound("receivedValue.greaterThan=" + SMALLER_RECEIVED_VALUE);
    }

    @Test
    @Transactional
    void getAllPointsByValueToReceiveIsEqualToSomething() throws Exception {
        // Initialize the database
        pointRepository.saveAndFlush(point);

        // Get all the pointList where valueToReceive equals to DEFAULT_VALUE_TO_RECEIVE
        defaultPointShouldBeFound("valueToReceive.equals=" + DEFAULT_VALUE_TO_RECEIVE);

        // Get all the pointList where valueToReceive equals to UPDATED_VALUE_TO_RECEIVE
        defaultPointShouldNotBeFound("valueToReceive.equals=" + UPDATED_VALUE_TO_RECEIVE);
    }

    @Test
    @Transactional
    void getAllPointsByValueToReceiveIsNotEqualToSomething() throws Exception {
        // Initialize the database
        pointRepository.saveAndFlush(point);

        // Get all the pointList where valueToReceive not equals to DEFAULT_VALUE_TO_RECEIVE
        defaultPointShouldNotBeFound("valueToReceive.notEquals=" + DEFAULT_VALUE_TO_RECEIVE);

        // Get all the pointList where valueToReceive not equals to UPDATED_VALUE_TO_RECEIVE
        defaultPointShouldBeFound("valueToReceive.notEquals=" + UPDATED_VALUE_TO_RECEIVE);
    }

    @Test
    @Transactional
    void getAllPointsByValueToReceiveIsInShouldWork() throws Exception {
        // Initialize the database
        pointRepository.saveAndFlush(point);

        // Get all the pointList where valueToReceive in DEFAULT_VALUE_TO_RECEIVE or UPDATED_VALUE_TO_RECEIVE
        defaultPointShouldBeFound("valueToReceive.in=" + DEFAULT_VALUE_TO_RECEIVE + "," + UPDATED_VALUE_TO_RECEIVE);

        // Get all the pointList where valueToReceive equals to UPDATED_VALUE_TO_RECEIVE
        defaultPointShouldNotBeFound("valueToReceive.in=" + UPDATED_VALUE_TO_RECEIVE);
    }

    @Test
    @Transactional
    void getAllPointsByValueToReceiveIsNullOrNotNull() throws Exception {
        // Initialize the database
        pointRepository.saveAndFlush(point);

        // Get all the pointList where valueToReceive is not null
        defaultPointShouldBeFound("valueToReceive.specified=true");

        // Get all the pointList where valueToReceive is null
        defaultPointShouldNotBeFound("valueToReceive.specified=false");
    }

    @Test
    @Transactional
    void getAllPointsByValueToReceiveIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        pointRepository.saveAndFlush(point);

        // Get all the pointList where valueToReceive is greater than or equal to DEFAULT_VALUE_TO_RECEIVE
        defaultPointShouldBeFound("valueToReceive.greaterThanOrEqual=" + DEFAULT_VALUE_TO_RECEIVE);

        // Get all the pointList where valueToReceive is greater than or equal to UPDATED_VALUE_TO_RECEIVE
        defaultPointShouldNotBeFound("valueToReceive.greaterThanOrEqual=" + UPDATED_VALUE_TO_RECEIVE);
    }

    @Test
    @Transactional
    void getAllPointsByValueToReceiveIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        pointRepository.saveAndFlush(point);

        // Get all the pointList where valueToReceive is less than or equal to DEFAULT_VALUE_TO_RECEIVE
        defaultPointShouldBeFound("valueToReceive.lessThanOrEqual=" + DEFAULT_VALUE_TO_RECEIVE);

        // Get all the pointList where valueToReceive is less than or equal to SMALLER_VALUE_TO_RECEIVE
        defaultPointShouldNotBeFound("valueToReceive.lessThanOrEqual=" + SMALLER_VALUE_TO_RECEIVE);
    }

    @Test
    @Transactional
    void getAllPointsByValueToReceiveIsLessThanSomething() throws Exception {
        // Initialize the database
        pointRepository.saveAndFlush(point);

        // Get all the pointList where valueToReceive is less than DEFAULT_VALUE_TO_RECEIVE
        defaultPointShouldNotBeFound("valueToReceive.lessThan=" + DEFAULT_VALUE_TO_RECEIVE);

        // Get all the pointList where valueToReceive is less than UPDATED_VALUE_TO_RECEIVE
        defaultPointShouldBeFound("valueToReceive.lessThan=" + UPDATED_VALUE_TO_RECEIVE);
    }

    @Test
    @Transactional
    void getAllPointsByValueToReceiveIsGreaterThanSomething() throws Exception {
        // Initialize the database
        pointRepository.saveAndFlush(point);

        // Get all the pointList where valueToReceive is greater than DEFAULT_VALUE_TO_RECEIVE
        defaultPointShouldNotBeFound("valueToReceive.greaterThan=" + DEFAULT_VALUE_TO_RECEIVE);

        // Get all the pointList where valueToReceive is greater than SMALLER_VALUE_TO_RECEIVE
        defaultPointShouldBeFound("valueToReceive.greaterThan=" + SMALLER_VALUE_TO_RECEIVE);
    }

    @Test
    @Transactional
    void getAllPointsByRankingIsEqualToSomething() throws Exception {
        // Initialize the database
        pointRepository.saveAndFlush(point);

        // Get all the pointList where ranking equals to DEFAULT_RANKING
        defaultPointShouldBeFound("ranking.equals=" + DEFAULT_RANKING);

        // Get all the pointList where ranking equals to UPDATED_RANKING
        defaultPointShouldNotBeFound("ranking.equals=" + UPDATED_RANKING);
    }

    @Test
    @Transactional
    void getAllPointsByRankingIsNotEqualToSomething() throws Exception {
        // Initialize the database
        pointRepository.saveAndFlush(point);

        // Get all the pointList where ranking not equals to DEFAULT_RANKING
        defaultPointShouldNotBeFound("ranking.notEquals=" + DEFAULT_RANKING);

        // Get all the pointList where ranking not equals to UPDATED_RANKING
        defaultPointShouldBeFound("ranking.notEquals=" + UPDATED_RANKING);
    }

    @Test
    @Transactional
    void getAllPointsByRankingIsInShouldWork() throws Exception {
        // Initialize the database
        pointRepository.saveAndFlush(point);

        // Get all the pointList where ranking in DEFAULT_RANKING or UPDATED_RANKING
        defaultPointShouldBeFound("ranking.in=" + DEFAULT_RANKING + "," + UPDATED_RANKING);

        // Get all the pointList where ranking equals to UPDATED_RANKING
        defaultPointShouldNotBeFound("ranking.in=" + UPDATED_RANKING);
    }

    @Test
    @Transactional
    void getAllPointsByRankingIsNullOrNotNull() throws Exception {
        // Initialize the database
        pointRepository.saveAndFlush(point);

        // Get all the pointList where ranking is not null
        defaultPointShouldBeFound("ranking.specified=true");

        // Get all the pointList where ranking is null
        defaultPointShouldNotBeFound("ranking.specified=false");
    }

    @Test
    @Transactional
    void getAllPointsByRankingIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        pointRepository.saveAndFlush(point);

        // Get all the pointList where ranking is greater than or equal to DEFAULT_RANKING
        defaultPointShouldBeFound("ranking.greaterThanOrEqual=" + DEFAULT_RANKING);

        // Get all the pointList where ranking is greater than or equal to UPDATED_RANKING
        defaultPointShouldNotBeFound("ranking.greaterThanOrEqual=" + UPDATED_RANKING);
    }

    @Test
    @Transactional
    void getAllPointsByRankingIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        pointRepository.saveAndFlush(point);

        // Get all the pointList where ranking is less than or equal to DEFAULT_RANKING
        defaultPointShouldBeFound("ranking.lessThanOrEqual=" + DEFAULT_RANKING);

        // Get all the pointList where ranking is less than or equal to SMALLER_RANKING
        defaultPointShouldNotBeFound("ranking.lessThanOrEqual=" + SMALLER_RANKING);
    }

    @Test
    @Transactional
    void getAllPointsByRankingIsLessThanSomething() throws Exception {
        // Initialize the database
        pointRepository.saveAndFlush(point);

        // Get all the pointList where ranking is less than DEFAULT_RANKING
        defaultPointShouldNotBeFound("ranking.lessThan=" + DEFAULT_RANKING);

        // Get all the pointList where ranking is less than UPDATED_RANKING
        defaultPointShouldBeFound("ranking.lessThan=" + UPDATED_RANKING);
    }

    @Test
    @Transactional
    void getAllPointsByRankingIsGreaterThanSomething() throws Exception {
        // Initialize the database
        pointRepository.saveAndFlush(point);

        // Get all the pointList where ranking is greater than DEFAULT_RANKING
        defaultPointShouldNotBeFound("ranking.greaterThan=" + DEFAULT_RANKING);

        // Get all the pointList where ranking is greater than SMALLER_RANKING
        defaultPointShouldBeFound("ranking.greaterThan=" + SMALLER_RANKING);
    }

    @Test
    @Transactional
    void getAllPointsByUserInfoIsEqualToSomething() throws Exception {
        // Get already existing entity
        UserInfo userInfo = point.getUserInfo();
        pointRepository.saveAndFlush(point);
        Long userInfoId = userInfo.getId();

        // Get all the pointList where userInfo equals to userInfoId
        defaultPointShouldBeFound("userInfoId.equals=" + userInfoId);

        // Get all the pointList where userInfo equals to (userInfoId + 1)
        defaultPointShouldNotBeFound("userInfoId.equals=" + (userInfoId + 1));
    }

    @Test
    @Transactional
    void getAllPointsByDeliveryManIsEqualToSomething() throws Exception {
        // Initialize the database
        pointRepository.saveAndFlush(point);
        DeliveryMan deliveryMan = DeliveryManResourceIT.createEntity(em);
        em.persist(deliveryMan);
        em.flush();
        point.addDeliveryMan(deliveryMan);
        pointRepository.saveAndFlush(point);
        Long deliveryManId = deliveryMan.getId();

        // Get all the pointList where deliveryMan equals to deliveryManId
        defaultPointShouldBeFound("deliveryManId.equals=" + deliveryManId);

        // Get all the pointList where deliveryMan equals to (deliveryManId + 1)
        defaultPointShouldNotBeFound("deliveryManId.equals=" + (deliveryManId + 1));
    }

    @Test
    @Transactional
    void getAllPointsByZoneIsEqualToSomething() throws Exception {
        // Initialize the database
        pointRepository.saveAndFlush(point);
        Zone zone = ZoneResourceIT.createEntity(em);
        em.persist(zone);
        em.flush();
        point.setZone(zone);
        pointRepository.saveAndFlush(point);
        Long zoneId = zone.getId();

        // Get all the pointList where zone equals to zoneId
        defaultPointShouldBeFound("zoneId.equals=" + zoneId);

        // Get all the pointList where zone equals to (zoneId + 1)
        defaultPointShouldNotBeFound("zoneId.equals=" + (zoneId + 1));
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
            .andExpect(jsonPath("$.[*].numberOfDeliveries").value(hasItem(DEFAULT_NUMBER_OF_DELIVERIES)))
            .andExpect(jsonPath("$.[*].receivedValue").value(hasItem(DEFAULT_RECEIVED_VALUE.doubleValue())))
            .andExpect(jsonPath("$.[*].valueToReceive").value(hasItem(DEFAULT_VALUE_TO_RECEIVE.doubleValue())))
            .andExpect(jsonPath("$.[*].ranking").value(hasItem(DEFAULT_RANKING.doubleValue())));

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
            .numberOfDeliveries(UPDATED_NUMBER_OF_DELIVERIES)
            .receivedValue(UPDATED_RECEIVED_VALUE)
            .valueToReceive(UPDATED_VALUE_TO_RECEIVE)
            .ranking(UPDATED_RANKING);
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
        assertThat(testPoint.getNumberOfDeliveries()).isEqualTo(UPDATED_NUMBER_OF_DELIVERIES);
        assertThat(testPoint.getReceivedValue()).isEqualTo(UPDATED_RECEIVED_VALUE);
        assertThat(testPoint.getValueToReceive()).isEqualTo(UPDATED_VALUE_TO_RECEIVE);
        assertThat(testPoint.getRanking()).isEqualTo(UPDATED_RANKING);
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

        partialUpdatedPoint.openingTime(UPDATED_OPENING_TIME).receivedValue(UPDATED_RECEIVED_VALUE);

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
        assertThat(testPoint.getNumberOfDeliveries()).isEqualTo(DEFAULT_NUMBER_OF_DELIVERIES);
        assertThat(testPoint.getReceivedValue()).isEqualTo(UPDATED_RECEIVED_VALUE);
        assertThat(testPoint.getValueToReceive()).isEqualTo(DEFAULT_VALUE_TO_RECEIVE);
        assertThat(testPoint.getRanking()).isEqualTo(DEFAULT_RANKING);
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
            .numberOfDeliveries(UPDATED_NUMBER_OF_DELIVERIES)
            .receivedValue(UPDATED_RECEIVED_VALUE)
            .valueToReceive(UPDATED_VALUE_TO_RECEIVE)
            .ranking(UPDATED_RANKING);

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
        assertThat(testPoint.getNumberOfDeliveries()).isEqualTo(UPDATED_NUMBER_OF_DELIVERIES);
        assertThat(testPoint.getReceivedValue()).isEqualTo(UPDATED_RECEIVED_VALUE);
        assertThat(testPoint.getValueToReceive()).isEqualTo(UPDATED_VALUE_TO_RECEIVE);
        assertThat(testPoint.getRanking()).isEqualTo(UPDATED_RANKING);
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
