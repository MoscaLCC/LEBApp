package com.leb.app.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.leb.app.IntegrationTest;
import com.leb.app.domain.DeliveryMan;
import com.leb.app.domain.Point;
import com.leb.app.domain.UserInfo;
import com.leb.app.repository.DeliveryManRepository;
import com.leb.app.service.criteria.DeliveryManCriteria;
import com.leb.app.service.dto.DeliveryManDTO;
import com.leb.app.service.mapper.DeliveryManMapper;
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
 * Integration tests for the {@link DeliveryManResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class DeliveryManResourceIT {

    private static final String DEFAULT_OPENING_TIME = "AAAAAAAAAA";
    private static final String UPDATED_OPENING_TIME = "BBBBBBBBBB";

    private static final Integer DEFAULT_NUMBER_OF_DELIVERIES = 1;
    private static final Integer UPDATED_NUMBER_OF_DELIVERIES = 2;
    private static final Integer SMALLER_NUMBER_OF_DELIVERIES = 1 - 1;

    private static final Double DEFAULT_NUMBER_OF_KM = 1D;
    private static final Double UPDATED_NUMBER_OF_KM = 2D;
    private static final Double SMALLER_NUMBER_OF_KM = 1D - 1D;

    private static final Double DEFAULT_RECEIVED_VALUE = 1D;
    private static final Double UPDATED_RECEIVED_VALUE = 2D;
    private static final Double SMALLER_RECEIVED_VALUE = 1D - 1D;

    private static final Double DEFAULT_VALUE_TO_RECEIVE = 1D;
    private static final Double UPDATED_VALUE_TO_RECEIVE = 2D;
    private static final Double SMALLER_VALUE_TO_RECEIVE = 1D - 1D;

    private static final Double DEFAULT_RANKING = 1D;
    private static final Double UPDATED_RANKING = 2D;
    private static final Double SMALLER_RANKING = 1D - 1D;

    private static final String ENTITY_API_URL = "/api/delivery-men";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private DeliveryManRepository deliveryManRepository;

    @Autowired
    private DeliveryManMapper deliveryManMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDeliveryManMockMvc;

    private DeliveryMan deliveryMan;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DeliveryMan createEntity(EntityManager em) {
        DeliveryMan deliveryMan = new DeliveryMan()
            .openingTime(DEFAULT_OPENING_TIME)
            .numberOfDeliveries(DEFAULT_NUMBER_OF_DELIVERIES)
            .numberOfKm(DEFAULT_NUMBER_OF_KM)
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
        deliveryMan.setUserInfo(userInfo);
        // Add required entity
        Point point;
        if (TestUtil.findAll(em, Point.class).isEmpty()) {
            point = PointResourceIT.createEntity(em);
            em.persist(point);
            em.flush();
        } else {
            point = TestUtil.findAll(em, Point.class).get(0);
        }
        deliveryMan.setPoint(point);
        return deliveryMan;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DeliveryMan createUpdatedEntity(EntityManager em) {
        DeliveryMan deliveryMan = new DeliveryMan()
            .openingTime(UPDATED_OPENING_TIME)
            .numberOfDeliveries(UPDATED_NUMBER_OF_DELIVERIES)
            .numberOfKm(UPDATED_NUMBER_OF_KM)
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
        deliveryMan.setUserInfo(userInfo);
        // Add required entity
        Point point;
        if (TestUtil.findAll(em, Point.class).isEmpty()) {
            point = PointResourceIT.createUpdatedEntity(em);
            em.persist(point);
            em.flush();
        } else {
            point = TestUtil.findAll(em, Point.class).get(0);
        }
        deliveryMan.setPoint(point);
        return deliveryMan;
    }

    @BeforeEach
    public void initTest() {
        deliveryMan = createEntity(em);
    }

    @Test
    @Transactional
    void createDeliveryMan() throws Exception {
        int databaseSizeBeforeCreate = deliveryManRepository.findAll().size();
        // Create the DeliveryMan
        DeliveryManDTO deliveryManDTO = deliveryManMapper.toDto(deliveryMan);
        restDeliveryManMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(deliveryManDTO))
            )
            .andExpect(status().isCreated());

        // Validate the DeliveryMan in the database
        List<DeliveryMan> deliveryManList = deliveryManRepository.findAll();
        assertThat(deliveryManList).hasSize(databaseSizeBeforeCreate + 1);
        DeliveryMan testDeliveryMan = deliveryManList.get(deliveryManList.size() - 1);
        assertThat(testDeliveryMan.getOpeningTime()).isEqualTo(DEFAULT_OPENING_TIME);
        assertThat(testDeliveryMan.getNumberOfDeliveries()).isEqualTo(DEFAULT_NUMBER_OF_DELIVERIES);
        assertThat(testDeliveryMan.getNumberOfKm()).isEqualTo(DEFAULT_NUMBER_OF_KM);
        assertThat(testDeliveryMan.getReceivedValue()).isEqualTo(DEFAULT_RECEIVED_VALUE);
        assertThat(testDeliveryMan.getValueToReceive()).isEqualTo(DEFAULT_VALUE_TO_RECEIVE);
        assertThat(testDeliveryMan.getRanking()).isEqualTo(DEFAULT_RANKING);
    }

    @Test
    @Transactional
    void createDeliveryManWithExistingId() throws Exception {
        // Create the DeliveryMan with an existing ID
        deliveryMan.setId(1L);
        DeliveryManDTO deliveryManDTO = deliveryManMapper.toDto(deliveryMan);

        int databaseSizeBeforeCreate = deliveryManRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restDeliveryManMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(deliveryManDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DeliveryMan in the database
        List<DeliveryMan> deliveryManList = deliveryManRepository.findAll();
        assertThat(deliveryManList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllDeliveryMen() throws Exception {
        // Initialize the database
        deliveryManRepository.saveAndFlush(deliveryMan);

        // Get all the deliveryManList
        restDeliveryManMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(deliveryMan.getId().intValue())))
            .andExpect(jsonPath("$.[*].openingTime").value(hasItem(DEFAULT_OPENING_TIME)))
            .andExpect(jsonPath("$.[*].numberOfDeliveries").value(hasItem(DEFAULT_NUMBER_OF_DELIVERIES)))
            .andExpect(jsonPath("$.[*].numberOfKm").value(hasItem(DEFAULT_NUMBER_OF_KM.doubleValue())))
            .andExpect(jsonPath("$.[*].receivedValue").value(hasItem(DEFAULT_RECEIVED_VALUE.doubleValue())))
            .andExpect(jsonPath("$.[*].valueToReceive").value(hasItem(DEFAULT_VALUE_TO_RECEIVE.doubleValue())))
            .andExpect(jsonPath("$.[*].ranking").value(hasItem(DEFAULT_RANKING.doubleValue())));
    }

    @Test
    @Transactional
    void getDeliveryMan() throws Exception {
        // Initialize the database
        deliveryManRepository.saveAndFlush(deliveryMan);

        // Get the deliveryMan
        restDeliveryManMockMvc
            .perform(get(ENTITY_API_URL_ID, deliveryMan.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(deliveryMan.getId().intValue()))
            .andExpect(jsonPath("$.openingTime").value(DEFAULT_OPENING_TIME))
            .andExpect(jsonPath("$.numberOfDeliveries").value(DEFAULT_NUMBER_OF_DELIVERIES))
            .andExpect(jsonPath("$.numberOfKm").value(DEFAULT_NUMBER_OF_KM.doubleValue()))
            .andExpect(jsonPath("$.receivedValue").value(DEFAULT_RECEIVED_VALUE.doubleValue()))
            .andExpect(jsonPath("$.valueToReceive").value(DEFAULT_VALUE_TO_RECEIVE.doubleValue()))
            .andExpect(jsonPath("$.ranking").value(DEFAULT_RANKING.doubleValue()));
    }

    @Test
    @Transactional
    void getDeliveryMenByIdFiltering() throws Exception {
        // Initialize the database
        deliveryManRepository.saveAndFlush(deliveryMan);

        Long id = deliveryMan.getId();

        defaultDeliveryManShouldBeFound("id.equals=" + id);
        defaultDeliveryManShouldNotBeFound("id.notEquals=" + id);

        defaultDeliveryManShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultDeliveryManShouldNotBeFound("id.greaterThan=" + id);

        defaultDeliveryManShouldBeFound("id.lessThanOrEqual=" + id);
        defaultDeliveryManShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllDeliveryMenByOpeningTimeIsEqualToSomething() throws Exception {
        // Initialize the database
        deliveryManRepository.saveAndFlush(deliveryMan);

        // Get all the deliveryManList where openingTime equals to DEFAULT_OPENING_TIME
        defaultDeliveryManShouldBeFound("openingTime.equals=" + DEFAULT_OPENING_TIME);

        // Get all the deliveryManList where openingTime equals to UPDATED_OPENING_TIME
        defaultDeliveryManShouldNotBeFound("openingTime.equals=" + UPDATED_OPENING_TIME);
    }

    @Test
    @Transactional
    void getAllDeliveryMenByOpeningTimeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        deliveryManRepository.saveAndFlush(deliveryMan);

        // Get all the deliveryManList where openingTime not equals to DEFAULT_OPENING_TIME
        defaultDeliveryManShouldNotBeFound("openingTime.notEquals=" + DEFAULT_OPENING_TIME);

        // Get all the deliveryManList where openingTime not equals to UPDATED_OPENING_TIME
        defaultDeliveryManShouldBeFound("openingTime.notEquals=" + UPDATED_OPENING_TIME);
    }

    @Test
    @Transactional
    void getAllDeliveryMenByOpeningTimeIsInShouldWork() throws Exception {
        // Initialize the database
        deliveryManRepository.saveAndFlush(deliveryMan);

        // Get all the deliveryManList where openingTime in DEFAULT_OPENING_TIME or UPDATED_OPENING_TIME
        defaultDeliveryManShouldBeFound("openingTime.in=" + DEFAULT_OPENING_TIME + "," + UPDATED_OPENING_TIME);

        // Get all the deliveryManList where openingTime equals to UPDATED_OPENING_TIME
        defaultDeliveryManShouldNotBeFound("openingTime.in=" + UPDATED_OPENING_TIME);
    }

    @Test
    @Transactional
    void getAllDeliveryMenByOpeningTimeIsNullOrNotNull() throws Exception {
        // Initialize the database
        deliveryManRepository.saveAndFlush(deliveryMan);

        // Get all the deliveryManList where openingTime is not null
        defaultDeliveryManShouldBeFound("openingTime.specified=true");

        // Get all the deliveryManList where openingTime is null
        defaultDeliveryManShouldNotBeFound("openingTime.specified=false");
    }

    @Test
    @Transactional
    void getAllDeliveryMenByOpeningTimeContainsSomething() throws Exception {
        // Initialize the database
        deliveryManRepository.saveAndFlush(deliveryMan);

        // Get all the deliveryManList where openingTime contains DEFAULT_OPENING_TIME
        defaultDeliveryManShouldBeFound("openingTime.contains=" + DEFAULT_OPENING_TIME);

        // Get all the deliveryManList where openingTime contains UPDATED_OPENING_TIME
        defaultDeliveryManShouldNotBeFound("openingTime.contains=" + UPDATED_OPENING_TIME);
    }

    @Test
    @Transactional
    void getAllDeliveryMenByOpeningTimeNotContainsSomething() throws Exception {
        // Initialize the database
        deliveryManRepository.saveAndFlush(deliveryMan);

        // Get all the deliveryManList where openingTime does not contain DEFAULT_OPENING_TIME
        defaultDeliveryManShouldNotBeFound("openingTime.doesNotContain=" + DEFAULT_OPENING_TIME);

        // Get all the deliveryManList where openingTime does not contain UPDATED_OPENING_TIME
        defaultDeliveryManShouldBeFound("openingTime.doesNotContain=" + UPDATED_OPENING_TIME);
    }

    @Test
    @Transactional
    void getAllDeliveryMenByNumberOfDeliveriesIsEqualToSomething() throws Exception {
        // Initialize the database
        deliveryManRepository.saveAndFlush(deliveryMan);

        // Get all the deliveryManList where numberOfDeliveries equals to DEFAULT_NUMBER_OF_DELIVERIES
        defaultDeliveryManShouldBeFound("numberOfDeliveries.equals=" + DEFAULT_NUMBER_OF_DELIVERIES);

        // Get all the deliveryManList where numberOfDeliveries equals to UPDATED_NUMBER_OF_DELIVERIES
        defaultDeliveryManShouldNotBeFound("numberOfDeliveries.equals=" + UPDATED_NUMBER_OF_DELIVERIES);
    }

    @Test
    @Transactional
    void getAllDeliveryMenByNumberOfDeliveriesIsNotEqualToSomething() throws Exception {
        // Initialize the database
        deliveryManRepository.saveAndFlush(deliveryMan);

        // Get all the deliveryManList where numberOfDeliveries not equals to DEFAULT_NUMBER_OF_DELIVERIES
        defaultDeliveryManShouldNotBeFound("numberOfDeliveries.notEquals=" + DEFAULT_NUMBER_OF_DELIVERIES);

        // Get all the deliveryManList where numberOfDeliveries not equals to UPDATED_NUMBER_OF_DELIVERIES
        defaultDeliveryManShouldBeFound("numberOfDeliveries.notEquals=" + UPDATED_NUMBER_OF_DELIVERIES);
    }

    @Test
    @Transactional
    void getAllDeliveryMenByNumberOfDeliveriesIsInShouldWork() throws Exception {
        // Initialize the database
        deliveryManRepository.saveAndFlush(deliveryMan);

        // Get all the deliveryManList where numberOfDeliveries in DEFAULT_NUMBER_OF_DELIVERIES or UPDATED_NUMBER_OF_DELIVERIES
        defaultDeliveryManShouldBeFound("numberOfDeliveries.in=" + DEFAULT_NUMBER_OF_DELIVERIES + "," + UPDATED_NUMBER_OF_DELIVERIES);

        // Get all the deliveryManList where numberOfDeliveries equals to UPDATED_NUMBER_OF_DELIVERIES
        defaultDeliveryManShouldNotBeFound("numberOfDeliveries.in=" + UPDATED_NUMBER_OF_DELIVERIES);
    }

    @Test
    @Transactional
    void getAllDeliveryMenByNumberOfDeliveriesIsNullOrNotNull() throws Exception {
        // Initialize the database
        deliveryManRepository.saveAndFlush(deliveryMan);

        // Get all the deliveryManList where numberOfDeliveries is not null
        defaultDeliveryManShouldBeFound("numberOfDeliveries.specified=true");

        // Get all the deliveryManList where numberOfDeliveries is null
        defaultDeliveryManShouldNotBeFound("numberOfDeliveries.specified=false");
    }

    @Test
    @Transactional
    void getAllDeliveryMenByNumberOfDeliveriesIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        deliveryManRepository.saveAndFlush(deliveryMan);

        // Get all the deliveryManList where numberOfDeliveries is greater than or equal to DEFAULT_NUMBER_OF_DELIVERIES
        defaultDeliveryManShouldBeFound("numberOfDeliveries.greaterThanOrEqual=" + DEFAULT_NUMBER_OF_DELIVERIES);

        // Get all the deliveryManList where numberOfDeliveries is greater than or equal to UPDATED_NUMBER_OF_DELIVERIES
        defaultDeliveryManShouldNotBeFound("numberOfDeliveries.greaterThanOrEqual=" + UPDATED_NUMBER_OF_DELIVERIES);
    }

    @Test
    @Transactional
    void getAllDeliveryMenByNumberOfDeliveriesIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        deliveryManRepository.saveAndFlush(deliveryMan);

        // Get all the deliveryManList where numberOfDeliveries is less than or equal to DEFAULT_NUMBER_OF_DELIVERIES
        defaultDeliveryManShouldBeFound("numberOfDeliveries.lessThanOrEqual=" + DEFAULT_NUMBER_OF_DELIVERIES);

        // Get all the deliveryManList where numberOfDeliveries is less than or equal to SMALLER_NUMBER_OF_DELIVERIES
        defaultDeliveryManShouldNotBeFound("numberOfDeliveries.lessThanOrEqual=" + SMALLER_NUMBER_OF_DELIVERIES);
    }

    @Test
    @Transactional
    void getAllDeliveryMenByNumberOfDeliveriesIsLessThanSomething() throws Exception {
        // Initialize the database
        deliveryManRepository.saveAndFlush(deliveryMan);

        // Get all the deliveryManList where numberOfDeliveries is less than DEFAULT_NUMBER_OF_DELIVERIES
        defaultDeliveryManShouldNotBeFound("numberOfDeliveries.lessThan=" + DEFAULT_NUMBER_OF_DELIVERIES);

        // Get all the deliveryManList where numberOfDeliveries is less than UPDATED_NUMBER_OF_DELIVERIES
        defaultDeliveryManShouldBeFound("numberOfDeliveries.lessThan=" + UPDATED_NUMBER_OF_DELIVERIES);
    }

    @Test
    @Transactional
    void getAllDeliveryMenByNumberOfDeliveriesIsGreaterThanSomething() throws Exception {
        // Initialize the database
        deliveryManRepository.saveAndFlush(deliveryMan);

        // Get all the deliveryManList where numberOfDeliveries is greater than DEFAULT_NUMBER_OF_DELIVERIES
        defaultDeliveryManShouldNotBeFound("numberOfDeliveries.greaterThan=" + DEFAULT_NUMBER_OF_DELIVERIES);

        // Get all the deliveryManList where numberOfDeliveries is greater than SMALLER_NUMBER_OF_DELIVERIES
        defaultDeliveryManShouldBeFound("numberOfDeliveries.greaterThan=" + SMALLER_NUMBER_OF_DELIVERIES);
    }

    @Test
    @Transactional
    void getAllDeliveryMenByNumberOfKmIsEqualToSomething() throws Exception {
        // Initialize the database
        deliveryManRepository.saveAndFlush(deliveryMan);

        // Get all the deliveryManList where numberOfKm equals to DEFAULT_NUMBER_OF_KM
        defaultDeliveryManShouldBeFound("numberOfKm.equals=" + DEFAULT_NUMBER_OF_KM);

        // Get all the deliveryManList where numberOfKm equals to UPDATED_NUMBER_OF_KM
        defaultDeliveryManShouldNotBeFound("numberOfKm.equals=" + UPDATED_NUMBER_OF_KM);
    }

    @Test
    @Transactional
    void getAllDeliveryMenByNumberOfKmIsNotEqualToSomething() throws Exception {
        // Initialize the database
        deliveryManRepository.saveAndFlush(deliveryMan);

        // Get all the deliveryManList where numberOfKm not equals to DEFAULT_NUMBER_OF_KM
        defaultDeliveryManShouldNotBeFound("numberOfKm.notEquals=" + DEFAULT_NUMBER_OF_KM);

        // Get all the deliveryManList where numberOfKm not equals to UPDATED_NUMBER_OF_KM
        defaultDeliveryManShouldBeFound("numberOfKm.notEquals=" + UPDATED_NUMBER_OF_KM);
    }

    @Test
    @Transactional
    void getAllDeliveryMenByNumberOfKmIsInShouldWork() throws Exception {
        // Initialize the database
        deliveryManRepository.saveAndFlush(deliveryMan);

        // Get all the deliveryManList where numberOfKm in DEFAULT_NUMBER_OF_KM or UPDATED_NUMBER_OF_KM
        defaultDeliveryManShouldBeFound("numberOfKm.in=" + DEFAULT_NUMBER_OF_KM + "," + UPDATED_NUMBER_OF_KM);

        // Get all the deliveryManList where numberOfKm equals to UPDATED_NUMBER_OF_KM
        defaultDeliveryManShouldNotBeFound("numberOfKm.in=" + UPDATED_NUMBER_OF_KM);
    }

    @Test
    @Transactional
    void getAllDeliveryMenByNumberOfKmIsNullOrNotNull() throws Exception {
        // Initialize the database
        deliveryManRepository.saveAndFlush(deliveryMan);

        // Get all the deliveryManList where numberOfKm is not null
        defaultDeliveryManShouldBeFound("numberOfKm.specified=true");

        // Get all the deliveryManList where numberOfKm is null
        defaultDeliveryManShouldNotBeFound("numberOfKm.specified=false");
    }

    @Test
    @Transactional
    void getAllDeliveryMenByNumberOfKmIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        deliveryManRepository.saveAndFlush(deliveryMan);

        // Get all the deliveryManList where numberOfKm is greater than or equal to DEFAULT_NUMBER_OF_KM
        defaultDeliveryManShouldBeFound("numberOfKm.greaterThanOrEqual=" + DEFAULT_NUMBER_OF_KM);

        // Get all the deliveryManList where numberOfKm is greater than or equal to UPDATED_NUMBER_OF_KM
        defaultDeliveryManShouldNotBeFound("numberOfKm.greaterThanOrEqual=" + UPDATED_NUMBER_OF_KM);
    }

    @Test
    @Transactional
    void getAllDeliveryMenByNumberOfKmIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        deliveryManRepository.saveAndFlush(deliveryMan);

        // Get all the deliveryManList where numberOfKm is less than or equal to DEFAULT_NUMBER_OF_KM
        defaultDeliveryManShouldBeFound("numberOfKm.lessThanOrEqual=" + DEFAULT_NUMBER_OF_KM);

        // Get all the deliveryManList where numberOfKm is less than or equal to SMALLER_NUMBER_OF_KM
        defaultDeliveryManShouldNotBeFound("numberOfKm.lessThanOrEqual=" + SMALLER_NUMBER_OF_KM);
    }

    @Test
    @Transactional
    void getAllDeliveryMenByNumberOfKmIsLessThanSomething() throws Exception {
        // Initialize the database
        deliveryManRepository.saveAndFlush(deliveryMan);

        // Get all the deliveryManList where numberOfKm is less than DEFAULT_NUMBER_OF_KM
        defaultDeliveryManShouldNotBeFound("numberOfKm.lessThan=" + DEFAULT_NUMBER_OF_KM);

        // Get all the deliveryManList where numberOfKm is less than UPDATED_NUMBER_OF_KM
        defaultDeliveryManShouldBeFound("numberOfKm.lessThan=" + UPDATED_NUMBER_OF_KM);
    }

    @Test
    @Transactional
    void getAllDeliveryMenByNumberOfKmIsGreaterThanSomething() throws Exception {
        // Initialize the database
        deliveryManRepository.saveAndFlush(deliveryMan);

        // Get all the deliveryManList where numberOfKm is greater than DEFAULT_NUMBER_OF_KM
        defaultDeliveryManShouldNotBeFound("numberOfKm.greaterThan=" + DEFAULT_NUMBER_OF_KM);

        // Get all the deliveryManList where numberOfKm is greater than SMALLER_NUMBER_OF_KM
        defaultDeliveryManShouldBeFound("numberOfKm.greaterThan=" + SMALLER_NUMBER_OF_KM);
    }

    @Test
    @Transactional
    void getAllDeliveryMenByReceivedValueIsEqualToSomething() throws Exception {
        // Initialize the database
        deliveryManRepository.saveAndFlush(deliveryMan);

        // Get all the deliveryManList where receivedValue equals to DEFAULT_RECEIVED_VALUE
        defaultDeliveryManShouldBeFound("receivedValue.equals=" + DEFAULT_RECEIVED_VALUE);

        // Get all the deliveryManList where receivedValue equals to UPDATED_RECEIVED_VALUE
        defaultDeliveryManShouldNotBeFound("receivedValue.equals=" + UPDATED_RECEIVED_VALUE);
    }

    @Test
    @Transactional
    void getAllDeliveryMenByReceivedValueIsNotEqualToSomething() throws Exception {
        // Initialize the database
        deliveryManRepository.saveAndFlush(deliveryMan);

        // Get all the deliveryManList where receivedValue not equals to DEFAULT_RECEIVED_VALUE
        defaultDeliveryManShouldNotBeFound("receivedValue.notEquals=" + DEFAULT_RECEIVED_VALUE);

        // Get all the deliveryManList where receivedValue not equals to UPDATED_RECEIVED_VALUE
        defaultDeliveryManShouldBeFound("receivedValue.notEquals=" + UPDATED_RECEIVED_VALUE);
    }

    @Test
    @Transactional
    void getAllDeliveryMenByReceivedValueIsInShouldWork() throws Exception {
        // Initialize the database
        deliveryManRepository.saveAndFlush(deliveryMan);

        // Get all the deliveryManList where receivedValue in DEFAULT_RECEIVED_VALUE or UPDATED_RECEIVED_VALUE
        defaultDeliveryManShouldBeFound("receivedValue.in=" + DEFAULT_RECEIVED_VALUE + "," + UPDATED_RECEIVED_VALUE);

        // Get all the deliveryManList where receivedValue equals to UPDATED_RECEIVED_VALUE
        defaultDeliveryManShouldNotBeFound("receivedValue.in=" + UPDATED_RECEIVED_VALUE);
    }

    @Test
    @Transactional
    void getAllDeliveryMenByReceivedValueIsNullOrNotNull() throws Exception {
        // Initialize the database
        deliveryManRepository.saveAndFlush(deliveryMan);

        // Get all the deliveryManList where receivedValue is not null
        defaultDeliveryManShouldBeFound("receivedValue.specified=true");

        // Get all the deliveryManList where receivedValue is null
        defaultDeliveryManShouldNotBeFound("receivedValue.specified=false");
    }

    @Test
    @Transactional
    void getAllDeliveryMenByReceivedValueIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        deliveryManRepository.saveAndFlush(deliveryMan);

        // Get all the deliveryManList where receivedValue is greater than or equal to DEFAULT_RECEIVED_VALUE
        defaultDeliveryManShouldBeFound("receivedValue.greaterThanOrEqual=" + DEFAULT_RECEIVED_VALUE);

        // Get all the deliveryManList where receivedValue is greater than or equal to UPDATED_RECEIVED_VALUE
        defaultDeliveryManShouldNotBeFound("receivedValue.greaterThanOrEqual=" + UPDATED_RECEIVED_VALUE);
    }

    @Test
    @Transactional
    void getAllDeliveryMenByReceivedValueIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        deliveryManRepository.saveAndFlush(deliveryMan);

        // Get all the deliveryManList where receivedValue is less than or equal to DEFAULT_RECEIVED_VALUE
        defaultDeliveryManShouldBeFound("receivedValue.lessThanOrEqual=" + DEFAULT_RECEIVED_VALUE);

        // Get all the deliveryManList where receivedValue is less than or equal to SMALLER_RECEIVED_VALUE
        defaultDeliveryManShouldNotBeFound("receivedValue.lessThanOrEqual=" + SMALLER_RECEIVED_VALUE);
    }

    @Test
    @Transactional
    void getAllDeliveryMenByReceivedValueIsLessThanSomething() throws Exception {
        // Initialize the database
        deliveryManRepository.saveAndFlush(deliveryMan);

        // Get all the deliveryManList where receivedValue is less than DEFAULT_RECEIVED_VALUE
        defaultDeliveryManShouldNotBeFound("receivedValue.lessThan=" + DEFAULT_RECEIVED_VALUE);

        // Get all the deliveryManList where receivedValue is less than UPDATED_RECEIVED_VALUE
        defaultDeliveryManShouldBeFound("receivedValue.lessThan=" + UPDATED_RECEIVED_VALUE);
    }

    @Test
    @Transactional
    void getAllDeliveryMenByReceivedValueIsGreaterThanSomething() throws Exception {
        // Initialize the database
        deliveryManRepository.saveAndFlush(deliveryMan);

        // Get all the deliveryManList where receivedValue is greater than DEFAULT_RECEIVED_VALUE
        defaultDeliveryManShouldNotBeFound("receivedValue.greaterThan=" + DEFAULT_RECEIVED_VALUE);

        // Get all the deliveryManList where receivedValue is greater than SMALLER_RECEIVED_VALUE
        defaultDeliveryManShouldBeFound("receivedValue.greaterThan=" + SMALLER_RECEIVED_VALUE);
    }

    @Test
    @Transactional
    void getAllDeliveryMenByValueToReceiveIsEqualToSomething() throws Exception {
        // Initialize the database
        deliveryManRepository.saveAndFlush(deliveryMan);

        // Get all the deliveryManList where valueToReceive equals to DEFAULT_VALUE_TO_RECEIVE
        defaultDeliveryManShouldBeFound("valueToReceive.equals=" + DEFAULT_VALUE_TO_RECEIVE);

        // Get all the deliveryManList where valueToReceive equals to UPDATED_VALUE_TO_RECEIVE
        defaultDeliveryManShouldNotBeFound("valueToReceive.equals=" + UPDATED_VALUE_TO_RECEIVE);
    }

    @Test
    @Transactional
    void getAllDeliveryMenByValueToReceiveIsNotEqualToSomething() throws Exception {
        // Initialize the database
        deliveryManRepository.saveAndFlush(deliveryMan);

        // Get all the deliveryManList where valueToReceive not equals to DEFAULT_VALUE_TO_RECEIVE
        defaultDeliveryManShouldNotBeFound("valueToReceive.notEquals=" + DEFAULT_VALUE_TO_RECEIVE);

        // Get all the deliveryManList where valueToReceive not equals to UPDATED_VALUE_TO_RECEIVE
        defaultDeliveryManShouldBeFound("valueToReceive.notEquals=" + UPDATED_VALUE_TO_RECEIVE);
    }

    @Test
    @Transactional
    void getAllDeliveryMenByValueToReceiveIsInShouldWork() throws Exception {
        // Initialize the database
        deliveryManRepository.saveAndFlush(deliveryMan);

        // Get all the deliveryManList where valueToReceive in DEFAULT_VALUE_TO_RECEIVE or UPDATED_VALUE_TO_RECEIVE
        defaultDeliveryManShouldBeFound("valueToReceive.in=" + DEFAULT_VALUE_TO_RECEIVE + "," + UPDATED_VALUE_TO_RECEIVE);

        // Get all the deliveryManList where valueToReceive equals to UPDATED_VALUE_TO_RECEIVE
        defaultDeliveryManShouldNotBeFound("valueToReceive.in=" + UPDATED_VALUE_TO_RECEIVE);
    }

    @Test
    @Transactional
    void getAllDeliveryMenByValueToReceiveIsNullOrNotNull() throws Exception {
        // Initialize the database
        deliveryManRepository.saveAndFlush(deliveryMan);

        // Get all the deliveryManList where valueToReceive is not null
        defaultDeliveryManShouldBeFound("valueToReceive.specified=true");

        // Get all the deliveryManList where valueToReceive is null
        defaultDeliveryManShouldNotBeFound("valueToReceive.specified=false");
    }

    @Test
    @Transactional
    void getAllDeliveryMenByValueToReceiveIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        deliveryManRepository.saveAndFlush(deliveryMan);

        // Get all the deliveryManList where valueToReceive is greater than or equal to DEFAULT_VALUE_TO_RECEIVE
        defaultDeliveryManShouldBeFound("valueToReceive.greaterThanOrEqual=" + DEFAULT_VALUE_TO_RECEIVE);

        // Get all the deliveryManList where valueToReceive is greater than or equal to UPDATED_VALUE_TO_RECEIVE
        defaultDeliveryManShouldNotBeFound("valueToReceive.greaterThanOrEqual=" + UPDATED_VALUE_TO_RECEIVE);
    }

    @Test
    @Transactional
    void getAllDeliveryMenByValueToReceiveIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        deliveryManRepository.saveAndFlush(deliveryMan);

        // Get all the deliveryManList where valueToReceive is less than or equal to DEFAULT_VALUE_TO_RECEIVE
        defaultDeliveryManShouldBeFound("valueToReceive.lessThanOrEqual=" + DEFAULT_VALUE_TO_RECEIVE);

        // Get all the deliveryManList where valueToReceive is less than or equal to SMALLER_VALUE_TO_RECEIVE
        defaultDeliveryManShouldNotBeFound("valueToReceive.lessThanOrEqual=" + SMALLER_VALUE_TO_RECEIVE);
    }

    @Test
    @Transactional
    void getAllDeliveryMenByValueToReceiveIsLessThanSomething() throws Exception {
        // Initialize the database
        deliveryManRepository.saveAndFlush(deliveryMan);

        // Get all the deliveryManList where valueToReceive is less than DEFAULT_VALUE_TO_RECEIVE
        defaultDeliveryManShouldNotBeFound("valueToReceive.lessThan=" + DEFAULT_VALUE_TO_RECEIVE);

        // Get all the deliveryManList where valueToReceive is less than UPDATED_VALUE_TO_RECEIVE
        defaultDeliveryManShouldBeFound("valueToReceive.lessThan=" + UPDATED_VALUE_TO_RECEIVE);
    }

    @Test
    @Transactional
    void getAllDeliveryMenByValueToReceiveIsGreaterThanSomething() throws Exception {
        // Initialize the database
        deliveryManRepository.saveAndFlush(deliveryMan);

        // Get all the deliveryManList where valueToReceive is greater than DEFAULT_VALUE_TO_RECEIVE
        defaultDeliveryManShouldNotBeFound("valueToReceive.greaterThan=" + DEFAULT_VALUE_TO_RECEIVE);

        // Get all the deliveryManList where valueToReceive is greater than SMALLER_VALUE_TO_RECEIVE
        defaultDeliveryManShouldBeFound("valueToReceive.greaterThan=" + SMALLER_VALUE_TO_RECEIVE);
    }

    @Test
    @Transactional
    void getAllDeliveryMenByRankingIsEqualToSomething() throws Exception {
        // Initialize the database
        deliveryManRepository.saveAndFlush(deliveryMan);

        // Get all the deliveryManList where ranking equals to DEFAULT_RANKING
        defaultDeliveryManShouldBeFound("ranking.equals=" + DEFAULT_RANKING);

        // Get all the deliveryManList where ranking equals to UPDATED_RANKING
        defaultDeliveryManShouldNotBeFound("ranking.equals=" + UPDATED_RANKING);
    }

    @Test
    @Transactional
    void getAllDeliveryMenByRankingIsNotEqualToSomething() throws Exception {
        // Initialize the database
        deliveryManRepository.saveAndFlush(deliveryMan);

        // Get all the deliveryManList where ranking not equals to DEFAULT_RANKING
        defaultDeliveryManShouldNotBeFound("ranking.notEquals=" + DEFAULT_RANKING);

        // Get all the deliveryManList where ranking not equals to UPDATED_RANKING
        defaultDeliveryManShouldBeFound("ranking.notEquals=" + UPDATED_RANKING);
    }

    @Test
    @Transactional
    void getAllDeliveryMenByRankingIsInShouldWork() throws Exception {
        // Initialize the database
        deliveryManRepository.saveAndFlush(deliveryMan);

        // Get all the deliveryManList where ranking in DEFAULT_RANKING or UPDATED_RANKING
        defaultDeliveryManShouldBeFound("ranking.in=" + DEFAULT_RANKING + "," + UPDATED_RANKING);

        // Get all the deliveryManList where ranking equals to UPDATED_RANKING
        defaultDeliveryManShouldNotBeFound("ranking.in=" + UPDATED_RANKING);
    }

    @Test
    @Transactional
    void getAllDeliveryMenByRankingIsNullOrNotNull() throws Exception {
        // Initialize the database
        deliveryManRepository.saveAndFlush(deliveryMan);

        // Get all the deliveryManList where ranking is not null
        defaultDeliveryManShouldBeFound("ranking.specified=true");

        // Get all the deliveryManList where ranking is null
        defaultDeliveryManShouldNotBeFound("ranking.specified=false");
    }

    @Test
    @Transactional
    void getAllDeliveryMenByRankingIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        deliveryManRepository.saveAndFlush(deliveryMan);

        // Get all the deliveryManList where ranking is greater than or equal to DEFAULT_RANKING
        defaultDeliveryManShouldBeFound("ranking.greaterThanOrEqual=" + DEFAULT_RANKING);

        // Get all the deliveryManList where ranking is greater than or equal to UPDATED_RANKING
        defaultDeliveryManShouldNotBeFound("ranking.greaterThanOrEqual=" + UPDATED_RANKING);
    }

    @Test
    @Transactional
    void getAllDeliveryMenByRankingIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        deliveryManRepository.saveAndFlush(deliveryMan);

        // Get all the deliveryManList where ranking is less than or equal to DEFAULT_RANKING
        defaultDeliveryManShouldBeFound("ranking.lessThanOrEqual=" + DEFAULT_RANKING);

        // Get all the deliveryManList where ranking is less than or equal to SMALLER_RANKING
        defaultDeliveryManShouldNotBeFound("ranking.lessThanOrEqual=" + SMALLER_RANKING);
    }

    @Test
    @Transactional
    void getAllDeliveryMenByRankingIsLessThanSomething() throws Exception {
        // Initialize the database
        deliveryManRepository.saveAndFlush(deliveryMan);

        // Get all the deliveryManList where ranking is less than DEFAULT_RANKING
        defaultDeliveryManShouldNotBeFound("ranking.lessThan=" + DEFAULT_RANKING);

        // Get all the deliveryManList where ranking is less than UPDATED_RANKING
        defaultDeliveryManShouldBeFound("ranking.lessThan=" + UPDATED_RANKING);
    }

    @Test
    @Transactional
    void getAllDeliveryMenByRankingIsGreaterThanSomething() throws Exception {
        // Initialize the database
        deliveryManRepository.saveAndFlush(deliveryMan);

        // Get all the deliveryManList where ranking is greater than DEFAULT_RANKING
        defaultDeliveryManShouldNotBeFound("ranking.greaterThan=" + DEFAULT_RANKING);

        // Get all the deliveryManList where ranking is greater than SMALLER_RANKING
        defaultDeliveryManShouldBeFound("ranking.greaterThan=" + SMALLER_RANKING);
    }

    @Test
    @Transactional
    void getAllDeliveryMenByUserInfoIsEqualToSomething() throws Exception {
        // Get already existing entity
        UserInfo userInfo = deliveryMan.getUserInfo();
        deliveryManRepository.saveAndFlush(deliveryMan);
        Long userInfoId = userInfo.getId();

        // Get all the deliveryManList where userInfo equals to userInfoId
        defaultDeliveryManShouldBeFound("userInfoId.equals=" + userInfoId);

        // Get all the deliveryManList where userInfo equals to (userInfoId + 1)
        defaultDeliveryManShouldNotBeFound("userInfoId.equals=" + (userInfoId + 1));
    }

    @Test
    @Transactional
    void getAllDeliveryMenByPointIsEqualToSomething() throws Exception {
        // Initialize the database
        deliveryManRepository.saveAndFlush(deliveryMan);
        Point point = PointResourceIT.createEntity(em);
        em.persist(point);
        em.flush();
        deliveryMan.setPoint(point);
        deliveryManRepository.saveAndFlush(deliveryMan);
        Long pointId = point.getId();

        // Get all the deliveryManList where point equals to pointId
        defaultDeliveryManShouldBeFound("pointId.equals=" + pointId);

        // Get all the deliveryManList where point equals to (pointId + 1)
        defaultDeliveryManShouldNotBeFound("pointId.equals=" + (pointId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultDeliveryManShouldBeFound(String filter) throws Exception {
        restDeliveryManMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(deliveryMan.getId().intValue())))
            .andExpect(jsonPath("$.[*].openingTime").value(hasItem(DEFAULT_OPENING_TIME)))
            .andExpect(jsonPath("$.[*].numberOfDeliveries").value(hasItem(DEFAULT_NUMBER_OF_DELIVERIES)))
            .andExpect(jsonPath("$.[*].numberOfKm").value(hasItem(DEFAULT_NUMBER_OF_KM.doubleValue())))
            .andExpect(jsonPath("$.[*].receivedValue").value(hasItem(DEFAULT_RECEIVED_VALUE.doubleValue())))
            .andExpect(jsonPath("$.[*].valueToReceive").value(hasItem(DEFAULT_VALUE_TO_RECEIVE.doubleValue())))
            .andExpect(jsonPath("$.[*].ranking").value(hasItem(DEFAULT_RANKING.doubleValue())));

        // Check, that the count call also returns 1
        restDeliveryManMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultDeliveryManShouldNotBeFound(String filter) throws Exception {
        restDeliveryManMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restDeliveryManMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingDeliveryMan() throws Exception {
        // Get the deliveryMan
        restDeliveryManMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewDeliveryMan() throws Exception {
        // Initialize the database
        deliveryManRepository.saveAndFlush(deliveryMan);

        int databaseSizeBeforeUpdate = deliveryManRepository.findAll().size();

        // Update the deliveryMan
        DeliveryMan updatedDeliveryMan = deliveryManRepository.findById(deliveryMan.getId()).get();
        // Disconnect from session so that the updates on updatedDeliveryMan are not directly saved in db
        em.detach(updatedDeliveryMan);
        updatedDeliveryMan
            .openingTime(UPDATED_OPENING_TIME)
            .numberOfDeliveries(UPDATED_NUMBER_OF_DELIVERIES)
            .numberOfKm(UPDATED_NUMBER_OF_KM)
            .receivedValue(UPDATED_RECEIVED_VALUE)
            .valueToReceive(UPDATED_VALUE_TO_RECEIVE)
            .ranking(UPDATED_RANKING);
        DeliveryManDTO deliveryManDTO = deliveryManMapper.toDto(updatedDeliveryMan);

        restDeliveryManMockMvc
            .perform(
                put(ENTITY_API_URL_ID, deliveryManDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(deliveryManDTO))
            )
            .andExpect(status().isOk());

        // Validate the DeliveryMan in the database
        List<DeliveryMan> deliveryManList = deliveryManRepository.findAll();
        assertThat(deliveryManList).hasSize(databaseSizeBeforeUpdate);
        DeliveryMan testDeliveryMan = deliveryManList.get(deliveryManList.size() - 1);
        assertThat(testDeliveryMan.getOpeningTime()).isEqualTo(UPDATED_OPENING_TIME);
        assertThat(testDeliveryMan.getNumberOfDeliveries()).isEqualTo(UPDATED_NUMBER_OF_DELIVERIES);
        assertThat(testDeliveryMan.getNumberOfKm()).isEqualTo(UPDATED_NUMBER_OF_KM);
        assertThat(testDeliveryMan.getReceivedValue()).isEqualTo(UPDATED_RECEIVED_VALUE);
        assertThat(testDeliveryMan.getValueToReceive()).isEqualTo(UPDATED_VALUE_TO_RECEIVE);
        assertThat(testDeliveryMan.getRanking()).isEqualTo(UPDATED_RANKING);
    }

    @Test
    @Transactional
    void putNonExistingDeliveryMan() throws Exception {
        int databaseSizeBeforeUpdate = deliveryManRepository.findAll().size();
        deliveryMan.setId(count.incrementAndGet());

        // Create the DeliveryMan
        DeliveryManDTO deliveryManDTO = deliveryManMapper.toDto(deliveryMan);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDeliveryManMockMvc
            .perform(
                put(ENTITY_API_URL_ID, deliveryManDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(deliveryManDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DeliveryMan in the database
        List<DeliveryMan> deliveryManList = deliveryManRepository.findAll();
        assertThat(deliveryManList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchDeliveryMan() throws Exception {
        int databaseSizeBeforeUpdate = deliveryManRepository.findAll().size();
        deliveryMan.setId(count.incrementAndGet());

        // Create the DeliveryMan
        DeliveryManDTO deliveryManDTO = deliveryManMapper.toDto(deliveryMan);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDeliveryManMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(deliveryManDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DeliveryMan in the database
        List<DeliveryMan> deliveryManList = deliveryManRepository.findAll();
        assertThat(deliveryManList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamDeliveryMan() throws Exception {
        int databaseSizeBeforeUpdate = deliveryManRepository.findAll().size();
        deliveryMan.setId(count.incrementAndGet());

        // Create the DeliveryMan
        DeliveryManDTO deliveryManDTO = deliveryManMapper.toDto(deliveryMan);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDeliveryManMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(deliveryManDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the DeliveryMan in the database
        List<DeliveryMan> deliveryManList = deliveryManRepository.findAll();
        assertThat(deliveryManList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateDeliveryManWithPatch() throws Exception {
        // Initialize the database
        deliveryManRepository.saveAndFlush(deliveryMan);

        int databaseSizeBeforeUpdate = deliveryManRepository.findAll().size();

        // Update the deliveryMan using partial update
        DeliveryMan partialUpdatedDeliveryMan = new DeliveryMan();
        partialUpdatedDeliveryMan.setId(deliveryMan.getId());

        partialUpdatedDeliveryMan.numberOfKm(UPDATED_NUMBER_OF_KM).receivedValue(UPDATED_RECEIVED_VALUE);

        restDeliveryManMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDeliveryMan.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDeliveryMan))
            )
            .andExpect(status().isOk());

        // Validate the DeliveryMan in the database
        List<DeliveryMan> deliveryManList = deliveryManRepository.findAll();
        assertThat(deliveryManList).hasSize(databaseSizeBeforeUpdate);
        DeliveryMan testDeliveryMan = deliveryManList.get(deliveryManList.size() - 1);
        assertThat(testDeliveryMan.getOpeningTime()).isEqualTo(DEFAULT_OPENING_TIME);
        assertThat(testDeliveryMan.getNumberOfDeliveries()).isEqualTo(DEFAULT_NUMBER_OF_DELIVERIES);
        assertThat(testDeliveryMan.getNumberOfKm()).isEqualTo(UPDATED_NUMBER_OF_KM);
        assertThat(testDeliveryMan.getReceivedValue()).isEqualTo(UPDATED_RECEIVED_VALUE);
        assertThat(testDeliveryMan.getValueToReceive()).isEqualTo(DEFAULT_VALUE_TO_RECEIVE);
        assertThat(testDeliveryMan.getRanking()).isEqualTo(DEFAULT_RANKING);
    }

    @Test
    @Transactional
    void fullUpdateDeliveryManWithPatch() throws Exception {
        // Initialize the database
        deliveryManRepository.saveAndFlush(deliveryMan);

        int databaseSizeBeforeUpdate = deliveryManRepository.findAll().size();

        // Update the deliveryMan using partial update
        DeliveryMan partialUpdatedDeliveryMan = new DeliveryMan();
        partialUpdatedDeliveryMan.setId(deliveryMan.getId());

        partialUpdatedDeliveryMan
            .openingTime(UPDATED_OPENING_TIME)
            .numberOfDeliveries(UPDATED_NUMBER_OF_DELIVERIES)
            .numberOfKm(UPDATED_NUMBER_OF_KM)
            .receivedValue(UPDATED_RECEIVED_VALUE)
            .valueToReceive(UPDATED_VALUE_TO_RECEIVE)
            .ranking(UPDATED_RANKING);

        restDeliveryManMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDeliveryMan.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDeliveryMan))
            )
            .andExpect(status().isOk());

        // Validate the DeliveryMan in the database
        List<DeliveryMan> deliveryManList = deliveryManRepository.findAll();
        assertThat(deliveryManList).hasSize(databaseSizeBeforeUpdate);
        DeliveryMan testDeliveryMan = deliveryManList.get(deliveryManList.size() - 1);
        assertThat(testDeliveryMan.getOpeningTime()).isEqualTo(UPDATED_OPENING_TIME);
        assertThat(testDeliveryMan.getNumberOfDeliveries()).isEqualTo(UPDATED_NUMBER_OF_DELIVERIES);
        assertThat(testDeliveryMan.getNumberOfKm()).isEqualTo(UPDATED_NUMBER_OF_KM);
        assertThat(testDeliveryMan.getReceivedValue()).isEqualTo(UPDATED_RECEIVED_VALUE);
        assertThat(testDeliveryMan.getValueToReceive()).isEqualTo(UPDATED_VALUE_TO_RECEIVE);
        assertThat(testDeliveryMan.getRanking()).isEqualTo(UPDATED_RANKING);
    }

    @Test
    @Transactional
    void patchNonExistingDeliveryMan() throws Exception {
        int databaseSizeBeforeUpdate = deliveryManRepository.findAll().size();
        deliveryMan.setId(count.incrementAndGet());

        // Create the DeliveryMan
        DeliveryManDTO deliveryManDTO = deliveryManMapper.toDto(deliveryMan);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDeliveryManMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, deliveryManDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(deliveryManDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DeliveryMan in the database
        List<DeliveryMan> deliveryManList = deliveryManRepository.findAll();
        assertThat(deliveryManList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchDeliveryMan() throws Exception {
        int databaseSizeBeforeUpdate = deliveryManRepository.findAll().size();
        deliveryMan.setId(count.incrementAndGet());

        // Create the DeliveryMan
        DeliveryManDTO deliveryManDTO = deliveryManMapper.toDto(deliveryMan);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDeliveryManMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(deliveryManDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DeliveryMan in the database
        List<DeliveryMan> deliveryManList = deliveryManRepository.findAll();
        assertThat(deliveryManList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamDeliveryMan() throws Exception {
        int databaseSizeBeforeUpdate = deliveryManRepository.findAll().size();
        deliveryMan.setId(count.incrementAndGet());

        // Create the DeliveryMan
        DeliveryManDTO deliveryManDTO = deliveryManMapper.toDto(deliveryMan);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDeliveryManMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(deliveryManDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the DeliveryMan in the database
        List<DeliveryMan> deliveryManList = deliveryManRepository.findAll();
        assertThat(deliveryManList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteDeliveryMan() throws Exception {
        // Initialize the database
        deliveryManRepository.saveAndFlush(deliveryMan);

        int databaseSizeBeforeDelete = deliveryManRepository.findAll().size();

        // Delete the deliveryMan
        restDeliveryManMockMvc
            .perform(delete(ENTITY_API_URL_ID, deliveryMan.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<DeliveryMan> deliveryManList = deliveryManRepository.findAll();
        assertThat(deliveryManList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
