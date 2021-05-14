package com.leb.app.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.leb.app.IntegrationTest;
import com.leb.app.domain.DeliveryMan;
import com.leb.app.domain.Point;
import com.leb.app.repository.DeliveryManRepository;
import com.leb.app.service.criteria.DeliveryManCriteria;
import com.leb.app.service.dto.DeliveryManDTO;
import com.leb.app.service.mapper.DeliveryManMapper;
import java.time.LocalDate;
import java.time.ZoneId;
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

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_PHONE_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_PHONE_NUMBER = "BBBBBBBBBB";

    private static final Integer DEFAULT_NIF = 1;
    private static final Integer UPDATED_NIF = 2;
    private static final Integer SMALLER_NIF = 1 - 1;

    private static final String DEFAULT_NIB = "AAAAAAAAAA";
    private static final String UPDATED_NIB = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_BIRTHDAY = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_BIRTHDAY = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_BIRTHDAY = LocalDate.ofEpochDay(-1L);

    private static final String DEFAULT_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_ADDRESS = "BBBBBBBBBB";

    private static final String DEFAULT_PHOTO = "AAAAAAAAAA";
    private static final String UPDATED_PHOTO = "BBBBBBBBBB";

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
            .name(DEFAULT_NAME)
            .email(DEFAULT_EMAIL)
            .phoneNumber(DEFAULT_PHONE_NUMBER)
            .nif(DEFAULT_NIF)
            .nib(DEFAULT_NIB)
            .birthday(DEFAULT_BIRTHDAY)
            .address(DEFAULT_ADDRESS)
            .photo(DEFAULT_PHOTO)
            .openingTime(DEFAULT_OPENING_TIME)
            .numberOfDeliveries(DEFAULT_NUMBER_OF_DELIVERIES)
            .numberOfKm(DEFAULT_NUMBER_OF_KM)
            .receivedValue(DEFAULT_RECEIVED_VALUE)
            .valueToReceive(DEFAULT_VALUE_TO_RECEIVE)
            .ranking(DEFAULT_RANKING);
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
            .name(UPDATED_NAME)
            .email(UPDATED_EMAIL)
            .phoneNumber(UPDATED_PHONE_NUMBER)
            .nif(UPDATED_NIF)
            .nib(UPDATED_NIB)
            .birthday(UPDATED_BIRTHDAY)
            .address(UPDATED_ADDRESS)
            .photo(UPDATED_PHOTO)
            .openingTime(UPDATED_OPENING_TIME)
            .numberOfDeliveries(UPDATED_NUMBER_OF_DELIVERIES)
            .numberOfKm(UPDATED_NUMBER_OF_KM)
            .receivedValue(UPDATED_RECEIVED_VALUE)
            .valueToReceive(UPDATED_VALUE_TO_RECEIVE)
            .ranking(UPDATED_RANKING);
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
        assertThat(testDeliveryMan.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testDeliveryMan.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testDeliveryMan.getPhoneNumber()).isEqualTo(DEFAULT_PHONE_NUMBER);
        assertThat(testDeliveryMan.getNif()).isEqualTo(DEFAULT_NIF);
        assertThat(testDeliveryMan.getNib()).isEqualTo(DEFAULT_NIB);
        assertThat(testDeliveryMan.getBirthday()).isEqualTo(DEFAULT_BIRTHDAY);
        assertThat(testDeliveryMan.getAddress()).isEqualTo(DEFAULT_ADDRESS);
        assertThat(testDeliveryMan.getPhoto()).isEqualTo(DEFAULT_PHOTO);
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
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].phoneNumber").value(hasItem(DEFAULT_PHONE_NUMBER)))
            .andExpect(jsonPath("$.[*].nif").value(hasItem(DEFAULT_NIF)))
            .andExpect(jsonPath("$.[*].nib").value(hasItem(DEFAULT_NIB)))
            .andExpect(jsonPath("$.[*].birthday").value(hasItem(DEFAULT_BIRTHDAY.toString())))
            .andExpect(jsonPath("$.[*].address").value(hasItem(DEFAULT_ADDRESS)))
            .andExpect(jsonPath("$.[*].photo").value(hasItem(DEFAULT_PHOTO)))
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
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL))
            .andExpect(jsonPath("$.phoneNumber").value(DEFAULT_PHONE_NUMBER))
            .andExpect(jsonPath("$.nif").value(DEFAULT_NIF))
            .andExpect(jsonPath("$.nib").value(DEFAULT_NIB))
            .andExpect(jsonPath("$.birthday").value(DEFAULT_BIRTHDAY.toString()))
            .andExpect(jsonPath("$.address").value(DEFAULT_ADDRESS))
            .andExpect(jsonPath("$.photo").value(DEFAULT_PHOTO))
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
    void getAllDeliveryMenByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        deliveryManRepository.saveAndFlush(deliveryMan);

        // Get all the deliveryManList where name equals to DEFAULT_NAME
        defaultDeliveryManShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the deliveryManList where name equals to UPDATED_NAME
        defaultDeliveryManShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllDeliveryMenByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        deliveryManRepository.saveAndFlush(deliveryMan);

        // Get all the deliveryManList where name not equals to DEFAULT_NAME
        defaultDeliveryManShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the deliveryManList where name not equals to UPDATED_NAME
        defaultDeliveryManShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllDeliveryMenByNameIsInShouldWork() throws Exception {
        // Initialize the database
        deliveryManRepository.saveAndFlush(deliveryMan);

        // Get all the deliveryManList where name in DEFAULT_NAME or UPDATED_NAME
        defaultDeliveryManShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the deliveryManList where name equals to UPDATED_NAME
        defaultDeliveryManShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllDeliveryMenByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        deliveryManRepository.saveAndFlush(deliveryMan);

        // Get all the deliveryManList where name is not null
        defaultDeliveryManShouldBeFound("name.specified=true");

        // Get all the deliveryManList where name is null
        defaultDeliveryManShouldNotBeFound("name.specified=false");
    }

    @Test
    @Transactional
    void getAllDeliveryMenByNameContainsSomething() throws Exception {
        // Initialize the database
        deliveryManRepository.saveAndFlush(deliveryMan);

        // Get all the deliveryManList where name contains DEFAULT_NAME
        defaultDeliveryManShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the deliveryManList where name contains UPDATED_NAME
        defaultDeliveryManShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllDeliveryMenByNameNotContainsSomething() throws Exception {
        // Initialize the database
        deliveryManRepository.saveAndFlush(deliveryMan);

        // Get all the deliveryManList where name does not contain DEFAULT_NAME
        defaultDeliveryManShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the deliveryManList where name does not contain UPDATED_NAME
        defaultDeliveryManShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllDeliveryMenByEmailIsEqualToSomething() throws Exception {
        // Initialize the database
        deliveryManRepository.saveAndFlush(deliveryMan);

        // Get all the deliveryManList where email equals to DEFAULT_EMAIL
        defaultDeliveryManShouldBeFound("email.equals=" + DEFAULT_EMAIL);

        // Get all the deliveryManList where email equals to UPDATED_EMAIL
        defaultDeliveryManShouldNotBeFound("email.equals=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    void getAllDeliveryMenByEmailIsNotEqualToSomething() throws Exception {
        // Initialize the database
        deliveryManRepository.saveAndFlush(deliveryMan);

        // Get all the deliveryManList where email not equals to DEFAULT_EMAIL
        defaultDeliveryManShouldNotBeFound("email.notEquals=" + DEFAULT_EMAIL);

        // Get all the deliveryManList where email not equals to UPDATED_EMAIL
        defaultDeliveryManShouldBeFound("email.notEquals=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    void getAllDeliveryMenByEmailIsInShouldWork() throws Exception {
        // Initialize the database
        deliveryManRepository.saveAndFlush(deliveryMan);

        // Get all the deliveryManList where email in DEFAULT_EMAIL or UPDATED_EMAIL
        defaultDeliveryManShouldBeFound("email.in=" + DEFAULT_EMAIL + "," + UPDATED_EMAIL);

        // Get all the deliveryManList where email equals to UPDATED_EMAIL
        defaultDeliveryManShouldNotBeFound("email.in=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    void getAllDeliveryMenByEmailIsNullOrNotNull() throws Exception {
        // Initialize the database
        deliveryManRepository.saveAndFlush(deliveryMan);

        // Get all the deliveryManList where email is not null
        defaultDeliveryManShouldBeFound("email.specified=true");

        // Get all the deliveryManList where email is null
        defaultDeliveryManShouldNotBeFound("email.specified=false");
    }

    @Test
    @Transactional
    void getAllDeliveryMenByEmailContainsSomething() throws Exception {
        // Initialize the database
        deliveryManRepository.saveAndFlush(deliveryMan);

        // Get all the deliveryManList where email contains DEFAULT_EMAIL
        defaultDeliveryManShouldBeFound("email.contains=" + DEFAULT_EMAIL);

        // Get all the deliveryManList where email contains UPDATED_EMAIL
        defaultDeliveryManShouldNotBeFound("email.contains=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    void getAllDeliveryMenByEmailNotContainsSomething() throws Exception {
        // Initialize the database
        deliveryManRepository.saveAndFlush(deliveryMan);

        // Get all the deliveryManList where email does not contain DEFAULT_EMAIL
        defaultDeliveryManShouldNotBeFound("email.doesNotContain=" + DEFAULT_EMAIL);

        // Get all the deliveryManList where email does not contain UPDATED_EMAIL
        defaultDeliveryManShouldBeFound("email.doesNotContain=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    void getAllDeliveryMenByPhoneNumberIsEqualToSomething() throws Exception {
        // Initialize the database
        deliveryManRepository.saveAndFlush(deliveryMan);

        // Get all the deliveryManList where phoneNumber equals to DEFAULT_PHONE_NUMBER
        defaultDeliveryManShouldBeFound("phoneNumber.equals=" + DEFAULT_PHONE_NUMBER);

        // Get all the deliveryManList where phoneNumber equals to UPDATED_PHONE_NUMBER
        defaultDeliveryManShouldNotBeFound("phoneNumber.equals=" + UPDATED_PHONE_NUMBER);
    }

    @Test
    @Transactional
    void getAllDeliveryMenByPhoneNumberIsNotEqualToSomething() throws Exception {
        // Initialize the database
        deliveryManRepository.saveAndFlush(deliveryMan);

        // Get all the deliveryManList where phoneNumber not equals to DEFAULT_PHONE_NUMBER
        defaultDeliveryManShouldNotBeFound("phoneNumber.notEquals=" + DEFAULT_PHONE_NUMBER);

        // Get all the deliveryManList where phoneNumber not equals to UPDATED_PHONE_NUMBER
        defaultDeliveryManShouldBeFound("phoneNumber.notEquals=" + UPDATED_PHONE_NUMBER);
    }

    @Test
    @Transactional
    void getAllDeliveryMenByPhoneNumberIsInShouldWork() throws Exception {
        // Initialize the database
        deliveryManRepository.saveAndFlush(deliveryMan);

        // Get all the deliveryManList where phoneNumber in DEFAULT_PHONE_NUMBER or UPDATED_PHONE_NUMBER
        defaultDeliveryManShouldBeFound("phoneNumber.in=" + DEFAULT_PHONE_NUMBER + "," + UPDATED_PHONE_NUMBER);

        // Get all the deliveryManList where phoneNumber equals to UPDATED_PHONE_NUMBER
        defaultDeliveryManShouldNotBeFound("phoneNumber.in=" + UPDATED_PHONE_NUMBER);
    }

    @Test
    @Transactional
    void getAllDeliveryMenByPhoneNumberIsNullOrNotNull() throws Exception {
        // Initialize the database
        deliveryManRepository.saveAndFlush(deliveryMan);

        // Get all the deliveryManList where phoneNumber is not null
        defaultDeliveryManShouldBeFound("phoneNumber.specified=true");

        // Get all the deliveryManList where phoneNumber is null
        defaultDeliveryManShouldNotBeFound("phoneNumber.specified=false");
    }

    @Test
    @Transactional
    void getAllDeliveryMenByPhoneNumberContainsSomething() throws Exception {
        // Initialize the database
        deliveryManRepository.saveAndFlush(deliveryMan);

        // Get all the deliveryManList where phoneNumber contains DEFAULT_PHONE_NUMBER
        defaultDeliveryManShouldBeFound("phoneNumber.contains=" + DEFAULT_PHONE_NUMBER);

        // Get all the deliveryManList where phoneNumber contains UPDATED_PHONE_NUMBER
        defaultDeliveryManShouldNotBeFound("phoneNumber.contains=" + UPDATED_PHONE_NUMBER);
    }

    @Test
    @Transactional
    void getAllDeliveryMenByPhoneNumberNotContainsSomething() throws Exception {
        // Initialize the database
        deliveryManRepository.saveAndFlush(deliveryMan);

        // Get all the deliveryManList where phoneNumber does not contain DEFAULT_PHONE_NUMBER
        defaultDeliveryManShouldNotBeFound("phoneNumber.doesNotContain=" + DEFAULT_PHONE_NUMBER);

        // Get all the deliveryManList where phoneNumber does not contain UPDATED_PHONE_NUMBER
        defaultDeliveryManShouldBeFound("phoneNumber.doesNotContain=" + UPDATED_PHONE_NUMBER);
    }

    @Test
    @Transactional
    void getAllDeliveryMenByNifIsEqualToSomething() throws Exception {
        // Initialize the database
        deliveryManRepository.saveAndFlush(deliveryMan);

        // Get all the deliveryManList where nif equals to DEFAULT_NIF
        defaultDeliveryManShouldBeFound("nif.equals=" + DEFAULT_NIF);

        // Get all the deliveryManList where nif equals to UPDATED_NIF
        defaultDeliveryManShouldNotBeFound("nif.equals=" + UPDATED_NIF);
    }

    @Test
    @Transactional
    void getAllDeliveryMenByNifIsNotEqualToSomething() throws Exception {
        // Initialize the database
        deliveryManRepository.saveAndFlush(deliveryMan);

        // Get all the deliveryManList where nif not equals to DEFAULT_NIF
        defaultDeliveryManShouldNotBeFound("nif.notEquals=" + DEFAULT_NIF);

        // Get all the deliveryManList where nif not equals to UPDATED_NIF
        defaultDeliveryManShouldBeFound("nif.notEquals=" + UPDATED_NIF);
    }

    @Test
    @Transactional
    void getAllDeliveryMenByNifIsInShouldWork() throws Exception {
        // Initialize the database
        deliveryManRepository.saveAndFlush(deliveryMan);

        // Get all the deliveryManList where nif in DEFAULT_NIF or UPDATED_NIF
        defaultDeliveryManShouldBeFound("nif.in=" + DEFAULT_NIF + "," + UPDATED_NIF);

        // Get all the deliveryManList where nif equals to UPDATED_NIF
        defaultDeliveryManShouldNotBeFound("nif.in=" + UPDATED_NIF);
    }

    @Test
    @Transactional
    void getAllDeliveryMenByNifIsNullOrNotNull() throws Exception {
        // Initialize the database
        deliveryManRepository.saveAndFlush(deliveryMan);

        // Get all the deliveryManList where nif is not null
        defaultDeliveryManShouldBeFound("nif.specified=true");

        // Get all the deliveryManList where nif is null
        defaultDeliveryManShouldNotBeFound("nif.specified=false");
    }

    @Test
    @Transactional
    void getAllDeliveryMenByNifIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        deliveryManRepository.saveAndFlush(deliveryMan);

        // Get all the deliveryManList where nif is greater than or equal to DEFAULT_NIF
        defaultDeliveryManShouldBeFound("nif.greaterThanOrEqual=" + DEFAULT_NIF);

        // Get all the deliveryManList where nif is greater than or equal to UPDATED_NIF
        defaultDeliveryManShouldNotBeFound("nif.greaterThanOrEqual=" + UPDATED_NIF);
    }

    @Test
    @Transactional
    void getAllDeliveryMenByNifIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        deliveryManRepository.saveAndFlush(deliveryMan);

        // Get all the deliveryManList where nif is less than or equal to DEFAULT_NIF
        defaultDeliveryManShouldBeFound("nif.lessThanOrEqual=" + DEFAULT_NIF);

        // Get all the deliveryManList where nif is less than or equal to SMALLER_NIF
        defaultDeliveryManShouldNotBeFound("nif.lessThanOrEqual=" + SMALLER_NIF);
    }

    @Test
    @Transactional
    void getAllDeliveryMenByNifIsLessThanSomething() throws Exception {
        // Initialize the database
        deliveryManRepository.saveAndFlush(deliveryMan);

        // Get all the deliveryManList where nif is less than DEFAULT_NIF
        defaultDeliveryManShouldNotBeFound("nif.lessThan=" + DEFAULT_NIF);

        // Get all the deliveryManList where nif is less than UPDATED_NIF
        defaultDeliveryManShouldBeFound("nif.lessThan=" + UPDATED_NIF);
    }

    @Test
    @Transactional
    void getAllDeliveryMenByNifIsGreaterThanSomething() throws Exception {
        // Initialize the database
        deliveryManRepository.saveAndFlush(deliveryMan);

        // Get all the deliveryManList where nif is greater than DEFAULT_NIF
        defaultDeliveryManShouldNotBeFound("nif.greaterThan=" + DEFAULT_NIF);

        // Get all the deliveryManList where nif is greater than SMALLER_NIF
        defaultDeliveryManShouldBeFound("nif.greaterThan=" + SMALLER_NIF);
    }

    @Test
    @Transactional
    void getAllDeliveryMenByNibIsEqualToSomething() throws Exception {
        // Initialize the database
        deliveryManRepository.saveAndFlush(deliveryMan);

        // Get all the deliveryManList where nib equals to DEFAULT_NIB
        defaultDeliveryManShouldBeFound("nib.equals=" + DEFAULT_NIB);

        // Get all the deliveryManList where nib equals to UPDATED_NIB
        defaultDeliveryManShouldNotBeFound("nib.equals=" + UPDATED_NIB);
    }

    @Test
    @Transactional
    void getAllDeliveryMenByNibIsNotEqualToSomething() throws Exception {
        // Initialize the database
        deliveryManRepository.saveAndFlush(deliveryMan);

        // Get all the deliveryManList where nib not equals to DEFAULT_NIB
        defaultDeliveryManShouldNotBeFound("nib.notEquals=" + DEFAULT_NIB);

        // Get all the deliveryManList where nib not equals to UPDATED_NIB
        defaultDeliveryManShouldBeFound("nib.notEquals=" + UPDATED_NIB);
    }

    @Test
    @Transactional
    void getAllDeliveryMenByNibIsInShouldWork() throws Exception {
        // Initialize the database
        deliveryManRepository.saveAndFlush(deliveryMan);

        // Get all the deliveryManList where nib in DEFAULT_NIB or UPDATED_NIB
        defaultDeliveryManShouldBeFound("nib.in=" + DEFAULT_NIB + "," + UPDATED_NIB);

        // Get all the deliveryManList where nib equals to UPDATED_NIB
        defaultDeliveryManShouldNotBeFound("nib.in=" + UPDATED_NIB);
    }

    @Test
    @Transactional
    void getAllDeliveryMenByNibIsNullOrNotNull() throws Exception {
        // Initialize the database
        deliveryManRepository.saveAndFlush(deliveryMan);

        // Get all the deliveryManList where nib is not null
        defaultDeliveryManShouldBeFound("nib.specified=true");

        // Get all the deliveryManList where nib is null
        defaultDeliveryManShouldNotBeFound("nib.specified=false");
    }

    @Test
    @Transactional
    void getAllDeliveryMenByNibContainsSomething() throws Exception {
        // Initialize the database
        deliveryManRepository.saveAndFlush(deliveryMan);

        // Get all the deliveryManList where nib contains DEFAULT_NIB
        defaultDeliveryManShouldBeFound("nib.contains=" + DEFAULT_NIB);

        // Get all the deliveryManList where nib contains UPDATED_NIB
        defaultDeliveryManShouldNotBeFound("nib.contains=" + UPDATED_NIB);
    }

    @Test
    @Transactional
    void getAllDeliveryMenByNibNotContainsSomething() throws Exception {
        // Initialize the database
        deliveryManRepository.saveAndFlush(deliveryMan);

        // Get all the deliveryManList where nib does not contain DEFAULT_NIB
        defaultDeliveryManShouldNotBeFound("nib.doesNotContain=" + DEFAULT_NIB);

        // Get all the deliveryManList where nib does not contain UPDATED_NIB
        defaultDeliveryManShouldBeFound("nib.doesNotContain=" + UPDATED_NIB);
    }

    @Test
    @Transactional
    void getAllDeliveryMenByBirthdayIsEqualToSomething() throws Exception {
        // Initialize the database
        deliveryManRepository.saveAndFlush(deliveryMan);

        // Get all the deliveryManList where birthday equals to DEFAULT_BIRTHDAY
        defaultDeliveryManShouldBeFound("birthday.equals=" + DEFAULT_BIRTHDAY);

        // Get all the deliveryManList where birthday equals to UPDATED_BIRTHDAY
        defaultDeliveryManShouldNotBeFound("birthday.equals=" + UPDATED_BIRTHDAY);
    }

    @Test
    @Transactional
    void getAllDeliveryMenByBirthdayIsNotEqualToSomething() throws Exception {
        // Initialize the database
        deliveryManRepository.saveAndFlush(deliveryMan);

        // Get all the deliveryManList where birthday not equals to DEFAULT_BIRTHDAY
        defaultDeliveryManShouldNotBeFound("birthday.notEquals=" + DEFAULT_BIRTHDAY);

        // Get all the deliveryManList where birthday not equals to UPDATED_BIRTHDAY
        defaultDeliveryManShouldBeFound("birthday.notEquals=" + UPDATED_BIRTHDAY);
    }

    @Test
    @Transactional
    void getAllDeliveryMenByBirthdayIsInShouldWork() throws Exception {
        // Initialize the database
        deliveryManRepository.saveAndFlush(deliveryMan);

        // Get all the deliveryManList where birthday in DEFAULT_BIRTHDAY or UPDATED_BIRTHDAY
        defaultDeliveryManShouldBeFound("birthday.in=" + DEFAULT_BIRTHDAY + "," + UPDATED_BIRTHDAY);

        // Get all the deliveryManList where birthday equals to UPDATED_BIRTHDAY
        defaultDeliveryManShouldNotBeFound("birthday.in=" + UPDATED_BIRTHDAY);
    }

    @Test
    @Transactional
    void getAllDeliveryMenByBirthdayIsNullOrNotNull() throws Exception {
        // Initialize the database
        deliveryManRepository.saveAndFlush(deliveryMan);

        // Get all the deliveryManList where birthday is not null
        defaultDeliveryManShouldBeFound("birthday.specified=true");

        // Get all the deliveryManList where birthday is null
        defaultDeliveryManShouldNotBeFound("birthday.specified=false");
    }

    @Test
    @Transactional
    void getAllDeliveryMenByBirthdayIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        deliveryManRepository.saveAndFlush(deliveryMan);

        // Get all the deliveryManList where birthday is greater than or equal to DEFAULT_BIRTHDAY
        defaultDeliveryManShouldBeFound("birthday.greaterThanOrEqual=" + DEFAULT_BIRTHDAY);

        // Get all the deliveryManList where birthday is greater than or equal to UPDATED_BIRTHDAY
        defaultDeliveryManShouldNotBeFound("birthday.greaterThanOrEqual=" + UPDATED_BIRTHDAY);
    }

    @Test
    @Transactional
    void getAllDeliveryMenByBirthdayIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        deliveryManRepository.saveAndFlush(deliveryMan);

        // Get all the deliveryManList where birthday is less than or equal to DEFAULT_BIRTHDAY
        defaultDeliveryManShouldBeFound("birthday.lessThanOrEqual=" + DEFAULT_BIRTHDAY);

        // Get all the deliveryManList where birthday is less than or equal to SMALLER_BIRTHDAY
        defaultDeliveryManShouldNotBeFound("birthday.lessThanOrEqual=" + SMALLER_BIRTHDAY);
    }

    @Test
    @Transactional
    void getAllDeliveryMenByBirthdayIsLessThanSomething() throws Exception {
        // Initialize the database
        deliveryManRepository.saveAndFlush(deliveryMan);

        // Get all the deliveryManList where birthday is less than DEFAULT_BIRTHDAY
        defaultDeliveryManShouldNotBeFound("birthday.lessThan=" + DEFAULT_BIRTHDAY);

        // Get all the deliveryManList where birthday is less than UPDATED_BIRTHDAY
        defaultDeliveryManShouldBeFound("birthday.lessThan=" + UPDATED_BIRTHDAY);
    }

    @Test
    @Transactional
    void getAllDeliveryMenByBirthdayIsGreaterThanSomething() throws Exception {
        // Initialize the database
        deliveryManRepository.saveAndFlush(deliveryMan);

        // Get all the deliveryManList where birthday is greater than DEFAULT_BIRTHDAY
        defaultDeliveryManShouldNotBeFound("birthday.greaterThan=" + DEFAULT_BIRTHDAY);

        // Get all the deliveryManList where birthday is greater than SMALLER_BIRTHDAY
        defaultDeliveryManShouldBeFound("birthday.greaterThan=" + SMALLER_BIRTHDAY);
    }

    @Test
    @Transactional
    void getAllDeliveryMenByAddressIsEqualToSomething() throws Exception {
        // Initialize the database
        deliveryManRepository.saveAndFlush(deliveryMan);

        // Get all the deliveryManList where address equals to DEFAULT_ADDRESS
        defaultDeliveryManShouldBeFound("address.equals=" + DEFAULT_ADDRESS);

        // Get all the deliveryManList where address equals to UPDATED_ADDRESS
        defaultDeliveryManShouldNotBeFound("address.equals=" + UPDATED_ADDRESS);
    }

    @Test
    @Transactional
    void getAllDeliveryMenByAddressIsNotEqualToSomething() throws Exception {
        // Initialize the database
        deliveryManRepository.saveAndFlush(deliveryMan);

        // Get all the deliveryManList where address not equals to DEFAULT_ADDRESS
        defaultDeliveryManShouldNotBeFound("address.notEquals=" + DEFAULT_ADDRESS);

        // Get all the deliveryManList where address not equals to UPDATED_ADDRESS
        defaultDeliveryManShouldBeFound("address.notEquals=" + UPDATED_ADDRESS);
    }

    @Test
    @Transactional
    void getAllDeliveryMenByAddressIsInShouldWork() throws Exception {
        // Initialize the database
        deliveryManRepository.saveAndFlush(deliveryMan);

        // Get all the deliveryManList where address in DEFAULT_ADDRESS or UPDATED_ADDRESS
        defaultDeliveryManShouldBeFound("address.in=" + DEFAULT_ADDRESS + "," + UPDATED_ADDRESS);

        // Get all the deliveryManList where address equals to UPDATED_ADDRESS
        defaultDeliveryManShouldNotBeFound("address.in=" + UPDATED_ADDRESS);
    }

    @Test
    @Transactional
    void getAllDeliveryMenByAddressIsNullOrNotNull() throws Exception {
        // Initialize the database
        deliveryManRepository.saveAndFlush(deliveryMan);

        // Get all the deliveryManList where address is not null
        defaultDeliveryManShouldBeFound("address.specified=true");

        // Get all the deliveryManList where address is null
        defaultDeliveryManShouldNotBeFound("address.specified=false");
    }

    @Test
    @Transactional
    void getAllDeliveryMenByAddressContainsSomething() throws Exception {
        // Initialize the database
        deliveryManRepository.saveAndFlush(deliveryMan);

        // Get all the deliveryManList where address contains DEFAULT_ADDRESS
        defaultDeliveryManShouldBeFound("address.contains=" + DEFAULT_ADDRESS);

        // Get all the deliveryManList where address contains UPDATED_ADDRESS
        defaultDeliveryManShouldNotBeFound("address.contains=" + UPDATED_ADDRESS);
    }

    @Test
    @Transactional
    void getAllDeliveryMenByAddressNotContainsSomething() throws Exception {
        // Initialize the database
        deliveryManRepository.saveAndFlush(deliveryMan);

        // Get all the deliveryManList where address does not contain DEFAULT_ADDRESS
        defaultDeliveryManShouldNotBeFound("address.doesNotContain=" + DEFAULT_ADDRESS);

        // Get all the deliveryManList where address does not contain UPDATED_ADDRESS
        defaultDeliveryManShouldBeFound("address.doesNotContain=" + UPDATED_ADDRESS);
    }

    @Test
    @Transactional
    void getAllDeliveryMenByPhotoIsEqualToSomething() throws Exception {
        // Initialize the database
        deliveryManRepository.saveAndFlush(deliveryMan);

        // Get all the deliveryManList where photo equals to DEFAULT_PHOTO
        defaultDeliveryManShouldBeFound("photo.equals=" + DEFAULT_PHOTO);

        // Get all the deliveryManList where photo equals to UPDATED_PHOTO
        defaultDeliveryManShouldNotBeFound("photo.equals=" + UPDATED_PHOTO);
    }

    @Test
    @Transactional
    void getAllDeliveryMenByPhotoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        deliveryManRepository.saveAndFlush(deliveryMan);

        // Get all the deliveryManList where photo not equals to DEFAULT_PHOTO
        defaultDeliveryManShouldNotBeFound("photo.notEquals=" + DEFAULT_PHOTO);

        // Get all the deliveryManList where photo not equals to UPDATED_PHOTO
        defaultDeliveryManShouldBeFound("photo.notEquals=" + UPDATED_PHOTO);
    }

    @Test
    @Transactional
    void getAllDeliveryMenByPhotoIsInShouldWork() throws Exception {
        // Initialize the database
        deliveryManRepository.saveAndFlush(deliveryMan);

        // Get all the deliveryManList where photo in DEFAULT_PHOTO or UPDATED_PHOTO
        defaultDeliveryManShouldBeFound("photo.in=" + DEFAULT_PHOTO + "," + UPDATED_PHOTO);

        // Get all the deliveryManList where photo equals to UPDATED_PHOTO
        defaultDeliveryManShouldNotBeFound("photo.in=" + UPDATED_PHOTO);
    }

    @Test
    @Transactional
    void getAllDeliveryMenByPhotoIsNullOrNotNull() throws Exception {
        // Initialize the database
        deliveryManRepository.saveAndFlush(deliveryMan);

        // Get all the deliveryManList where photo is not null
        defaultDeliveryManShouldBeFound("photo.specified=true");

        // Get all the deliveryManList where photo is null
        defaultDeliveryManShouldNotBeFound("photo.specified=false");
    }

    @Test
    @Transactional
    void getAllDeliveryMenByPhotoContainsSomething() throws Exception {
        // Initialize the database
        deliveryManRepository.saveAndFlush(deliveryMan);

        // Get all the deliveryManList where photo contains DEFAULT_PHOTO
        defaultDeliveryManShouldBeFound("photo.contains=" + DEFAULT_PHOTO);

        // Get all the deliveryManList where photo contains UPDATED_PHOTO
        defaultDeliveryManShouldNotBeFound("photo.contains=" + UPDATED_PHOTO);
    }

    @Test
    @Transactional
    void getAllDeliveryMenByPhotoNotContainsSomething() throws Exception {
        // Initialize the database
        deliveryManRepository.saveAndFlush(deliveryMan);

        // Get all the deliveryManList where photo does not contain DEFAULT_PHOTO
        defaultDeliveryManShouldNotBeFound("photo.doesNotContain=" + DEFAULT_PHOTO);

        // Get all the deliveryManList where photo does not contain UPDATED_PHOTO
        defaultDeliveryManShouldBeFound("photo.doesNotContain=" + UPDATED_PHOTO);
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
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].phoneNumber").value(hasItem(DEFAULT_PHONE_NUMBER)))
            .andExpect(jsonPath("$.[*].nif").value(hasItem(DEFAULT_NIF)))
            .andExpect(jsonPath("$.[*].nib").value(hasItem(DEFAULT_NIB)))
            .andExpect(jsonPath("$.[*].birthday").value(hasItem(DEFAULT_BIRTHDAY.toString())))
            .andExpect(jsonPath("$.[*].address").value(hasItem(DEFAULT_ADDRESS)))
            .andExpect(jsonPath("$.[*].photo").value(hasItem(DEFAULT_PHOTO)))
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
            .name(UPDATED_NAME)
            .email(UPDATED_EMAIL)
            .phoneNumber(UPDATED_PHONE_NUMBER)
            .nif(UPDATED_NIF)
            .nib(UPDATED_NIB)
            .birthday(UPDATED_BIRTHDAY)
            .address(UPDATED_ADDRESS)
            .photo(UPDATED_PHOTO)
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
        assertThat(testDeliveryMan.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testDeliveryMan.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testDeliveryMan.getPhoneNumber()).isEqualTo(UPDATED_PHONE_NUMBER);
        assertThat(testDeliveryMan.getNif()).isEqualTo(UPDATED_NIF);
        assertThat(testDeliveryMan.getNib()).isEqualTo(UPDATED_NIB);
        assertThat(testDeliveryMan.getBirthday()).isEqualTo(UPDATED_BIRTHDAY);
        assertThat(testDeliveryMan.getAddress()).isEqualTo(UPDATED_ADDRESS);
        assertThat(testDeliveryMan.getPhoto()).isEqualTo(UPDATED_PHOTO);
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

        partialUpdatedDeliveryMan
            .phoneNumber(UPDATED_PHONE_NUMBER)
            .nif(UPDATED_NIF)
            .address(UPDATED_ADDRESS)
            .openingTime(UPDATED_OPENING_TIME)
            .numberOfKm(UPDATED_NUMBER_OF_KM)
            .receivedValue(UPDATED_RECEIVED_VALUE);

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
        assertThat(testDeliveryMan.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testDeliveryMan.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testDeliveryMan.getPhoneNumber()).isEqualTo(UPDATED_PHONE_NUMBER);
        assertThat(testDeliveryMan.getNif()).isEqualTo(UPDATED_NIF);
        assertThat(testDeliveryMan.getNib()).isEqualTo(DEFAULT_NIB);
        assertThat(testDeliveryMan.getBirthday()).isEqualTo(DEFAULT_BIRTHDAY);
        assertThat(testDeliveryMan.getAddress()).isEqualTo(UPDATED_ADDRESS);
        assertThat(testDeliveryMan.getPhoto()).isEqualTo(DEFAULT_PHOTO);
        assertThat(testDeliveryMan.getOpeningTime()).isEqualTo(UPDATED_OPENING_TIME);
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
            .name(UPDATED_NAME)
            .email(UPDATED_EMAIL)
            .phoneNumber(UPDATED_PHONE_NUMBER)
            .nif(UPDATED_NIF)
            .nib(UPDATED_NIB)
            .birthday(UPDATED_BIRTHDAY)
            .address(UPDATED_ADDRESS)
            .photo(UPDATED_PHOTO)
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
        assertThat(testDeliveryMan.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testDeliveryMan.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testDeliveryMan.getPhoneNumber()).isEqualTo(UPDATED_PHONE_NUMBER);
        assertThat(testDeliveryMan.getNif()).isEqualTo(UPDATED_NIF);
        assertThat(testDeliveryMan.getNib()).isEqualTo(UPDATED_NIB);
        assertThat(testDeliveryMan.getBirthday()).isEqualTo(UPDATED_BIRTHDAY);
        assertThat(testDeliveryMan.getAddress()).isEqualTo(UPDATED_ADDRESS);
        assertThat(testDeliveryMan.getPhoto()).isEqualTo(UPDATED_PHOTO);
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
