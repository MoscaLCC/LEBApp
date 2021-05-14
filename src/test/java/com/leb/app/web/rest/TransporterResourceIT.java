package com.leb.app.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.leb.app.IntegrationTest;
import com.leb.app.domain.Route;
import com.leb.app.domain.Transporter;
import com.leb.app.domain.Zone;
import com.leb.app.repository.TransporterRepository;
import com.leb.app.service.TransporterService;
import com.leb.app.service.criteria.TransporterCriteria;
import com.leb.app.service.dto.TransporterDTO;
import com.leb.app.service.mapper.TransporterMapper;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link TransporterResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class TransporterResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_PHONE_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_PHONE_NUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_NIB = "AAAAAAAAAA";
    private static final String UPDATED_NIB = "BBBBBBBBBB";

    private static final Integer DEFAULT_NIF = 1;
    private static final Integer UPDATED_NIF = 2;
    private static final Integer SMALLER_NIF = 1 - 1;

    private static final LocalDate DEFAULT_BIRTHDAY = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_BIRTHDAY = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_BIRTHDAY = LocalDate.ofEpochDay(-1L);

    private static final String DEFAULT_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_ADDRESS = "BBBBBBBBBB";

    private static final String DEFAULT_PHOTO = "AAAAAAAAAA";
    private static final String UPDATED_PHOTO = "BBBBBBBBBB";

    private static final String DEFAULT_FAVOURITE_TRANSPORT = "AAAAAAAAAA";
    private static final String UPDATED_FAVOURITE_TRANSPORT = "BBBBBBBBBB";

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

    private static final String ENTITY_API_URL = "/api/transporters";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private TransporterRepository transporterRepository;

    @Mock
    private TransporterRepository transporterRepositoryMock;

    @Autowired
    private TransporterMapper transporterMapper;

    @Mock
    private TransporterService transporterServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTransporterMockMvc;

    private Transporter transporter;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Transporter createEntity(EntityManager em) {
        Transporter transporter = new Transporter()
            .name(DEFAULT_NAME)
            .email(DEFAULT_EMAIL)
            .phoneNumber(DEFAULT_PHONE_NUMBER)
            .nib(DEFAULT_NIB)
            .nif(DEFAULT_NIF)
            .birthday(DEFAULT_BIRTHDAY)
            .address(DEFAULT_ADDRESS)
            .photo(DEFAULT_PHOTO)
            .favouriteTransport(DEFAULT_FAVOURITE_TRANSPORT)
            .numberOfDeliveries(DEFAULT_NUMBER_OF_DELIVERIES)
            .numberOfKm(DEFAULT_NUMBER_OF_KM)
            .receivedValue(DEFAULT_RECEIVED_VALUE)
            .valueToReceive(DEFAULT_VALUE_TO_RECEIVE)
            .ranking(DEFAULT_RANKING);
        return transporter;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Transporter createUpdatedEntity(EntityManager em) {
        Transporter transporter = new Transporter()
            .name(UPDATED_NAME)
            .email(UPDATED_EMAIL)
            .phoneNumber(UPDATED_PHONE_NUMBER)
            .nib(UPDATED_NIB)
            .nif(UPDATED_NIF)
            .birthday(UPDATED_BIRTHDAY)
            .address(UPDATED_ADDRESS)
            .photo(UPDATED_PHOTO)
            .favouriteTransport(UPDATED_FAVOURITE_TRANSPORT)
            .numberOfDeliveries(UPDATED_NUMBER_OF_DELIVERIES)
            .numberOfKm(UPDATED_NUMBER_OF_KM)
            .receivedValue(UPDATED_RECEIVED_VALUE)
            .valueToReceive(UPDATED_VALUE_TO_RECEIVE)
            .ranking(UPDATED_RANKING);
        return transporter;
    }

    @BeforeEach
    public void initTest() {
        transporter = createEntity(em);
    }

    @Test
    @Transactional
    void createTransporter() throws Exception {
        int databaseSizeBeforeCreate = transporterRepository.findAll().size();
        // Create the Transporter
        TransporterDTO transporterDTO = transporterMapper.toDto(transporter);
        restTransporterMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(transporterDTO))
            )
            .andExpect(status().isCreated());

        // Validate the Transporter in the database
        List<Transporter> transporterList = transporterRepository.findAll();
        assertThat(transporterList).hasSize(databaseSizeBeforeCreate + 1);
        Transporter testTransporter = transporterList.get(transporterList.size() - 1);
        assertThat(testTransporter.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testTransporter.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testTransporter.getPhoneNumber()).isEqualTo(DEFAULT_PHONE_NUMBER);
        assertThat(testTransporter.getNib()).isEqualTo(DEFAULT_NIB);
        assertThat(testTransporter.getNif()).isEqualTo(DEFAULT_NIF);
        assertThat(testTransporter.getBirthday()).isEqualTo(DEFAULT_BIRTHDAY);
        assertThat(testTransporter.getAddress()).isEqualTo(DEFAULT_ADDRESS);
        assertThat(testTransporter.getPhoto()).isEqualTo(DEFAULT_PHOTO);
        assertThat(testTransporter.getFavouriteTransport()).isEqualTo(DEFAULT_FAVOURITE_TRANSPORT);
        assertThat(testTransporter.getNumberOfDeliveries()).isEqualTo(DEFAULT_NUMBER_OF_DELIVERIES);
        assertThat(testTransporter.getNumberOfKm()).isEqualTo(DEFAULT_NUMBER_OF_KM);
        assertThat(testTransporter.getReceivedValue()).isEqualTo(DEFAULT_RECEIVED_VALUE);
        assertThat(testTransporter.getValueToReceive()).isEqualTo(DEFAULT_VALUE_TO_RECEIVE);
        assertThat(testTransporter.getRanking()).isEqualTo(DEFAULT_RANKING);
    }

    @Test
    @Transactional
    void createTransporterWithExistingId() throws Exception {
        // Create the Transporter with an existing ID
        transporter.setId(1L);
        TransporterDTO transporterDTO = transporterMapper.toDto(transporter);

        int databaseSizeBeforeCreate = transporterRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restTransporterMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(transporterDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Transporter in the database
        List<Transporter> transporterList = transporterRepository.findAll();
        assertThat(transporterList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllTransporters() throws Exception {
        // Initialize the database
        transporterRepository.saveAndFlush(transporter);

        // Get all the transporterList
        restTransporterMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(transporter.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].phoneNumber").value(hasItem(DEFAULT_PHONE_NUMBER)))
            .andExpect(jsonPath("$.[*].nib").value(hasItem(DEFAULT_NIB)))
            .andExpect(jsonPath("$.[*].nif").value(hasItem(DEFAULT_NIF)))
            .andExpect(jsonPath("$.[*].birthday").value(hasItem(DEFAULT_BIRTHDAY.toString())))
            .andExpect(jsonPath("$.[*].address").value(hasItem(DEFAULT_ADDRESS)))
            .andExpect(jsonPath("$.[*].photo").value(hasItem(DEFAULT_PHOTO)))
            .andExpect(jsonPath("$.[*].favouriteTransport").value(hasItem(DEFAULT_FAVOURITE_TRANSPORT)))
            .andExpect(jsonPath("$.[*].numberOfDeliveries").value(hasItem(DEFAULT_NUMBER_OF_DELIVERIES)))
            .andExpect(jsonPath("$.[*].numberOfKm").value(hasItem(DEFAULT_NUMBER_OF_KM.doubleValue())))
            .andExpect(jsonPath("$.[*].receivedValue").value(hasItem(DEFAULT_RECEIVED_VALUE.doubleValue())))
            .andExpect(jsonPath("$.[*].valueToReceive").value(hasItem(DEFAULT_VALUE_TO_RECEIVE.doubleValue())))
            .andExpect(jsonPath("$.[*].ranking").value(hasItem(DEFAULT_RANKING.doubleValue())));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllTransportersWithEagerRelationshipsIsEnabled() throws Exception {
        when(transporterServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restTransporterMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(transporterServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllTransportersWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(transporterServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restTransporterMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(transporterServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    void getTransporter() throws Exception {
        // Initialize the database
        transporterRepository.saveAndFlush(transporter);

        // Get the transporter
        restTransporterMockMvc
            .perform(get(ENTITY_API_URL_ID, transporter.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(transporter.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL))
            .andExpect(jsonPath("$.phoneNumber").value(DEFAULT_PHONE_NUMBER))
            .andExpect(jsonPath("$.nib").value(DEFAULT_NIB))
            .andExpect(jsonPath("$.nif").value(DEFAULT_NIF))
            .andExpect(jsonPath("$.birthday").value(DEFAULT_BIRTHDAY.toString()))
            .andExpect(jsonPath("$.address").value(DEFAULT_ADDRESS))
            .andExpect(jsonPath("$.photo").value(DEFAULT_PHOTO))
            .andExpect(jsonPath("$.favouriteTransport").value(DEFAULT_FAVOURITE_TRANSPORT))
            .andExpect(jsonPath("$.numberOfDeliveries").value(DEFAULT_NUMBER_OF_DELIVERIES))
            .andExpect(jsonPath("$.numberOfKm").value(DEFAULT_NUMBER_OF_KM.doubleValue()))
            .andExpect(jsonPath("$.receivedValue").value(DEFAULT_RECEIVED_VALUE.doubleValue()))
            .andExpect(jsonPath("$.valueToReceive").value(DEFAULT_VALUE_TO_RECEIVE.doubleValue()))
            .andExpect(jsonPath("$.ranking").value(DEFAULT_RANKING.doubleValue()));
    }

    @Test
    @Transactional
    void getTransportersByIdFiltering() throws Exception {
        // Initialize the database
        transporterRepository.saveAndFlush(transporter);

        Long id = transporter.getId();

        defaultTransporterShouldBeFound("id.equals=" + id);
        defaultTransporterShouldNotBeFound("id.notEquals=" + id);

        defaultTransporterShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultTransporterShouldNotBeFound("id.greaterThan=" + id);

        defaultTransporterShouldBeFound("id.lessThanOrEqual=" + id);
        defaultTransporterShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllTransportersByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        transporterRepository.saveAndFlush(transporter);

        // Get all the transporterList where name equals to DEFAULT_NAME
        defaultTransporterShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the transporterList where name equals to UPDATED_NAME
        defaultTransporterShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllTransportersByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        transporterRepository.saveAndFlush(transporter);

        // Get all the transporterList where name not equals to DEFAULT_NAME
        defaultTransporterShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the transporterList where name not equals to UPDATED_NAME
        defaultTransporterShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllTransportersByNameIsInShouldWork() throws Exception {
        // Initialize the database
        transporterRepository.saveAndFlush(transporter);

        // Get all the transporterList where name in DEFAULT_NAME or UPDATED_NAME
        defaultTransporterShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the transporterList where name equals to UPDATED_NAME
        defaultTransporterShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllTransportersByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        transporterRepository.saveAndFlush(transporter);

        // Get all the transporterList where name is not null
        defaultTransporterShouldBeFound("name.specified=true");

        // Get all the transporterList where name is null
        defaultTransporterShouldNotBeFound("name.specified=false");
    }

    @Test
    @Transactional
    void getAllTransportersByNameContainsSomething() throws Exception {
        // Initialize the database
        transporterRepository.saveAndFlush(transporter);

        // Get all the transporterList where name contains DEFAULT_NAME
        defaultTransporterShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the transporterList where name contains UPDATED_NAME
        defaultTransporterShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllTransportersByNameNotContainsSomething() throws Exception {
        // Initialize the database
        transporterRepository.saveAndFlush(transporter);

        // Get all the transporterList where name does not contain DEFAULT_NAME
        defaultTransporterShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the transporterList where name does not contain UPDATED_NAME
        defaultTransporterShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllTransportersByEmailIsEqualToSomething() throws Exception {
        // Initialize the database
        transporterRepository.saveAndFlush(transporter);

        // Get all the transporterList where email equals to DEFAULT_EMAIL
        defaultTransporterShouldBeFound("email.equals=" + DEFAULT_EMAIL);

        // Get all the transporterList where email equals to UPDATED_EMAIL
        defaultTransporterShouldNotBeFound("email.equals=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    void getAllTransportersByEmailIsNotEqualToSomething() throws Exception {
        // Initialize the database
        transporterRepository.saveAndFlush(transporter);

        // Get all the transporterList where email not equals to DEFAULT_EMAIL
        defaultTransporterShouldNotBeFound("email.notEquals=" + DEFAULT_EMAIL);

        // Get all the transporterList where email not equals to UPDATED_EMAIL
        defaultTransporterShouldBeFound("email.notEquals=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    void getAllTransportersByEmailIsInShouldWork() throws Exception {
        // Initialize the database
        transporterRepository.saveAndFlush(transporter);

        // Get all the transporterList where email in DEFAULT_EMAIL or UPDATED_EMAIL
        defaultTransporterShouldBeFound("email.in=" + DEFAULT_EMAIL + "," + UPDATED_EMAIL);

        // Get all the transporterList where email equals to UPDATED_EMAIL
        defaultTransporterShouldNotBeFound("email.in=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    void getAllTransportersByEmailIsNullOrNotNull() throws Exception {
        // Initialize the database
        transporterRepository.saveAndFlush(transporter);

        // Get all the transporterList where email is not null
        defaultTransporterShouldBeFound("email.specified=true");

        // Get all the transporterList where email is null
        defaultTransporterShouldNotBeFound("email.specified=false");
    }

    @Test
    @Transactional
    void getAllTransportersByEmailContainsSomething() throws Exception {
        // Initialize the database
        transporterRepository.saveAndFlush(transporter);

        // Get all the transporterList where email contains DEFAULT_EMAIL
        defaultTransporterShouldBeFound("email.contains=" + DEFAULT_EMAIL);

        // Get all the transporterList where email contains UPDATED_EMAIL
        defaultTransporterShouldNotBeFound("email.contains=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    void getAllTransportersByEmailNotContainsSomething() throws Exception {
        // Initialize the database
        transporterRepository.saveAndFlush(transporter);

        // Get all the transporterList where email does not contain DEFAULT_EMAIL
        defaultTransporterShouldNotBeFound("email.doesNotContain=" + DEFAULT_EMAIL);

        // Get all the transporterList where email does not contain UPDATED_EMAIL
        defaultTransporterShouldBeFound("email.doesNotContain=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    void getAllTransportersByPhoneNumberIsEqualToSomething() throws Exception {
        // Initialize the database
        transporterRepository.saveAndFlush(transporter);

        // Get all the transporterList where phoneNumber equals to DEFAULT_PHONE_NUMBER
        defaultTransporterShouldBeFound("phoneNumber.equals=" + DEFAULT_PHONE_NUMBER);

        // Get all the transporterList where phoneNumber equals to UPDATED_PHONE_NUMBER
        defaultTransporterShouldNotBeFound("phoneNumber.equals=" + UPDATED_PHONE_NUMBER);
    }

    @Test
    @Transactional
    void getAllTransportersByPhoneNumberIsNotEqualToSomething() throws Exception {
        // Initialize the database
        transporterRepository.saveAndFlush(transporter);

        // Get all the transporterList where phoneNumber not equals to DEFAULT_PHONE_NUMBER
        defaultTransporterShouldNotBeFound("phoneNumber.notEquals=" + DEFAULT_PHONE_NUMBER);

        // Get all the transporterList where phoneNumber not equals to UPDATED_PHONE_NUMBER
        defaultTransporterShouldBeFound("phoneNumber.notEquals=" + UPDATED_PHONE_NUMBER);
    }

    @Test
    @Transactional
    void getAllTransportersByPhoneNumberIsInShouldWork() throws Exception {
        // Initialize the database
        transporterRepository.saveAndFlush(transporter);

        // Get all the transporterList where phoneNumber in DEFAULT_PHONE_NUMBER or UPDATED_PHONE_NUMBER
        defaultTransporterShouldBeFound("phoneNumber.in=" + DEFAULT_PHONE_NUMBER + "," + UPDATED_PHONE_NUMBER);

        // Get all the transporterList where phoneNumber equals to UPDATED_PHONE_NUMBER
        defaultTransporterShouldNotBeFound("phoneNumber.in=" + UPDATED_PHONE_NUMBER);
    }

    @Test
    @Transactional
    void getAllTransportersByPhoneNumberIsNullOrNotNull() throws Exception {
        // Initialize the database
        transporterRepository.saveAndFlush(transporter);

        // Get all the transporterList where phoneNumber is not null
        defaultTransporterShouldBeFound("phoneNumber.specified=true");

        // Get all the transporterList where phoneNumber is null
        defaultTransporterShouldNotBeFound("phoneNumber.specified=false");
    }

    @Test
    @Transactional
    void getAllTransportersByPhoneNumberContainsSomething() throws Exception {
        // Initialize the database
        transporterRepository.saveAndFlush(transporter);

        // Get all the transporterList where phoneNumber contains DEFAULT_PHONE_NUMBER
        defaultTransporterShouldBeFound("phoneNumber.contains=" + DEFAULT_PHONE_NUMBER);

        // Get all the transporterList where phoneNumber contains UPDATED_PHONE_NUMBER
        defaultTransporterShouldNotBeFound("phoneNumber.contains=" + UPDATED_PHONE_NUMBER);
    }

    @Test
    @Transactional
    void getAllTransportersByPhoneNumberNotContainsSomething() throws Exception {
        // Initialize the database
        transporterRepository.saveAndFlush(transporter);

        // Get all the transporterList where phoneNumber does not contain DEFAULT_PHONE_NUMBER
        defaultTransporterShouldNotBeFound("phoneNumber.doesNotContain=" + DEFAULT_PHONE_NUMBER);

        // Get all the transporterList where phoneNumber does not contain UPDATED_PHONE_NUMBER
        defaultTransporterShouldBeFound("phoneNumber.doesNotContain=" + UPDATED_PHONE_NUMBER);
    }

    @Test
    @Transactional
    void getAllTransportersByNibIsEqualToSomething() throws Exception {
        // Initialize the database
        transporterRepository.saveAndFlush(transporter);

        // Get all the transporterList where nib equals to DEFAULT_NIB
        defaultTransporterShouldBeFound("nib.equals=" + DEFAULT_NIB);

        // Get all the transporterList where nib equals to UPDATED_NIB
        defaultTransporterShouldNotBeFound("nib.equals=" + UPDATED_NIB);
    }

    @Test
    @Transactional
    void getAllTransportersByNibIsNotEqualToSomething() throws Exception {
        // Initialize the database
        transporterRepository.saveAndFlush(transporter);

        // Get all the transporterList where nib not equals to DEFAULT_NIB
        defaultTransporterShouldNotBeFound("nib.notEquals=" + DEFAULT_NIB);

        // Get all the transporterList where nib not equals to UPDATED_NIB
        defaultTransporterShouldBeFound("nib.notEquals=" + UPDATED_NIB);
    }

    @Test
    @Transactional
    void getAllTransportersByNibIsInShouldWork() throws Exception {
        // Initialize the database
        transporterRepository.saveAndFlush(transporter);

        // Get all the transporterList where nib in DEFAULT_NIB or UPDATED_NIB
        defaultTransporterShouldBeFound("nib.in=" + DEFAULT_NIB + "," + UPDATED_NIB);

        // Get all the transporterList where nib equals to UPDATED_NIB
        defaultTransporterShouldNotBeFound("nib.in=" + UPDATED_NIB);
    }

    @Test
    @Transactional
    void getAllTransportersByNibIsNullOrNotNull() throws Exception {
        // Initialize the database
        transporterRepository.saveAndFlush(transporter);

        // Get all the transporterList where nib is not null
        defaultTransporterShouldBeFound("nib.specified=true");

        // Get all the transporterList where nib is null
        defaultTransporterShouldNotBeFound("nib.specified=false");
    }

    @Test
    @Transactional
    void getAllTransportersByNibContainsSomething() throws Exception {
        // Initialize the database
        transporterRepository.saveAndFlush(transporter);

        // Get all the transporterList where nib contains DEFAULT_NIB
        defaultTransporterShouldBeFound("nib.contains=" + DEFAULT_NIB);

        // Get all the transporterList where nib contains UPDATED_NIB
        defaultTransporterShouldNotBeFound("nib.contains=" + UPDATED_NIB);
    }

    @Test
    @Transactional
    void getAllTransportersByNibNotContainsSomething() throws Exception {
        // Initialize the database
        transporterRepository.saveAndFlush(transporter);

        // Get all the transporterList where nib does not contain DEFAULT_NIB
        defaultTransporterShouldNotBeFound("nib.doesNotContain=" + DEFAULT_NIB);

        // Get all the transporterList where nib does not contain UPDATED_NIB
        defaultTransporterShouldBeFound("nib.doesNotContain=" + UPDATED_NIB);
    }

    @Test
    @Transactional
    void getAllTransportersByNifIsEqualToSomething() throws Exception {
        // Initialize the database
        transporterRepository.saveAndFlush(transporter);

        // Get all the transporterList where nif equals to DEFAULT_NIF
        defaultTransporterShouldBeFound("nif.equals=" + DEFAULT_NIF);

        // Get all the transporterList where nif equals to UPDATED_NIF
        defaultTransporterShouldNotBeFound("nif.equals=" + UPDATED_NIF);
    }

    @Test
    @Transactional
    void getAllTransportersByNifIsNotEqualToSomething() throws Exception {
        // Initialize the database
        transporterRepository.saveAndFlush(transporter);

        // Get all the transporterList where nif not equals to DEFAULT_NIF
        defaultTransporterShouldNotBeFound("nif.notEquals=" + DEFAULT_NIF);

        // Get all the transporterList where nif not equals to UPDATED_NIF
        defaultTransporterShouldBeFound("nif.notEquals=" + UPDATED_NIF);
    }

    @Test
    @Transactional
    void getAllTransportersByNifIsInShouldWork() throws Exception {
        // Initialize the database
        transporterRepository.saveAndFlush(transporter);

        // Get all the transporterList where nif in DEFAULT_NIF or UPDATED_NIF
        defaultTransporterShouldBeFound("nif.in=" + DEFAULT_NIF + "," + UPDATED_NIF);

        // Get all the transporterList where nif equals to UPDATED_NIF
        defaultTransporterShouldNotBeFound("nif.in=" + UPDATED_NIF);
    }

    @Test
    @Transactional
    void getAllTransportersByNifIsNullOrNotNull() throws Exception {
        // Initialize the database
        transporterRepository.saveAndFlush(transporter);

        // Get all the transporterList where nif is not null
        defaultTransporterShouldBeFound("nif.specified=true");

        // Get all the transporterList where nif is null
        defaultTransporterShouldNotBeFound("nif.specified=false");
    }

    @Test
    @Transactional
    void getAllTransportersByNifIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        transporterRepository.saveAndFlush(transporter);

        // Get all the transporterList where nif is greater than or equal to DEFAULT_NIF
        defaultTransporterShouldBeFound("nif.greaterThanOrEqual=" + DEFAULT_NIF);

        // Get all the transporterList where nif is greater than or equal to UPDATED_NIF
        defaultTransporterShouldNotBeFound("nif.greaterThanOrEqual=" + UPDATED_NIF);
    }

    @Test
    @Transactional
    void getAllTransportersByNifIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        transporterRepository.saveAndFlush(transporter);

        // Get all the transporterList where nif is less than or equal to DEFAULT_NIF
        defaultTransporterShouldBeFound("nif.lessThanOrEqual=" + DEFAULT_NIF);

        // Get all the transporterList where nif is less than or equal to SMALLER_NIF
        defaultTransporterShouldNotBeFound("nif.lessThanOrEqual=" + SMALLER_NIF);
    }

    @Test
    @Transactional
    void getAllTransportersByNifIsLessThanSomething() throws Exception {
        // Initialize the database
        transporterRepository.saveAndFlush(transporter);

        // Get all the transporterList where nif is less than DEFAULT_NIF
        defaultTransporterShouldNotBeFound("nif.lessThan=" + DEFAULT_NIF);

        // Get all the transporterList where nif is less than UPDATED_NIF
        defaultTransporterShouldBeFound("nif.lessThan=" + UPDATED_NIF);
    }

    @Test
    @Transactional
    void getAllTransportersByNifIsGreaterThanSomething() throws Exception {
        // Initialize the database
        transporterRepository.saveAndFlush(transporter);

        // Get all the transporterList where nif is greater than DEFAULT_NIF
        defaultTransporterShouldNotBeFound("nif.greaterThan=" + DEFAULT_NIF);

        // Get all the transporterList where nif is greater than SMALLER_NIF
        defaultTransporterShouldBeFound("nif.greaterThan=" + SMALLER_NIF);
    }

    @Test
    @Transactional
    void getAllTransportersByBirthdayIsEqualToSomething() throws Exception {
        // Initialize the database
        transporterRepository.saveAndFlush(transporter);

        // Get all the transporterList where birthday equals to DEFAULT_BIRTHDAY
        defaultTransporterShouldBeFound("birthday.equals=" + DEFAULT_BIRTHDAY);

        // Get all the transporterList where birthday equals to UPDATED_BIRTHDAY
        defaultTransporterShouldNotBeFound("birthday.equals=" + UPDATED_BIRTHDAY);
    }

    @Test
    @Transactional
    void getAllTransportersByBirthdayIsNotEqualToSomething() throws Exception {
        // Initialize the database
        transporterRepository.saveAndFlush(transporter);

        // Get all the transporterList where birthday not equals to DEFAULT_BIRTHDAY
        defaultTransporterShouldNotBeFound("birthday.notEquals=" + DEFAULT_BIRTHDAY);

        // Get all the transporterList where birthday not equals to UPDATED_BIRTHDAY
        defaultTransporterShouldBeFound("birthday.notEquals=" + UPDATED_BIRTHDAY);
    }

    @Test
    @Transactional
    void getAllTransportersByBirthdayIsInShouldWork() throws Exception {
        // Initialize the database
        transporterRepository.saveAndFlush(transporter);

        // Get all the transporterList where birthday in DEFAULT_BIRTHDAY or UPDATED_BIRTHDAY
        defaultTransporterShouldBeFound("birthday.in=" + DEFAULT_BIRTHDAY + "," + UPDATED_BIRTHDAY);

        // Get all the transporterList where birthday equals to UPDATED_BIRTHDAY
        defaultTransporterShouldNotBeFound("birthday.in=" + UPDATED_BIRTHDAY);
    }

    @Test
    @Transactional
    void getAllTransportersByBirthdayIsNullOrNotNull() throws Exception {
        // Initialize the database
        transporterRepository.saveAndFlush(transporter);

        // Get all the transporterList where birthday is not null
        defaultTransporterShouldBeFound("birthday.specified=true");

        // Get all the transporterList where birthday is null
        defaultTransporterShouldNotBeFound("birthday.specified=false");
    }

    @Test
    @Transactional
    void getAllTransportersByBirthdayIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        transporterRepository.saveAndFlush(transporter);

        // Get all the transporterList where birthday is greater than or equal to DEFAULT_BIRTHDAY
        defaultTransporterShouldBeFound("birthday.greaterThanOrEqual=" + DEFAULT_BIRTHDAY);

        // Get all the transporterList where birthday is greater than or equal to UPDATED_BIRTHDAY
        defaultTransporterShouldNotBeFound("birthday.greaterThanOrEqual=" + UPDATED_BIRTHDAY);
    }

    @Test
    @Transactional
    void getAllTransportersByBirthdayIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        transporterRepository.saveAndFlush(transporter);

        // Get all the transporterList where birthday is less than or equal to DEFAULT_BIRTHDAY
        defaultTransporterShouldBeFound("birthday.lessThanOrEqual=" + DEFAULT_BIRTHDAY);

        // Get all the transporterList where birthday is less than or equal to SMALLER_BIRTHDAY
        defaultTransporterShouldNotBeFound("birthday.lessThanOrEqual=" + SMALLER_BIRTHDAY);
    }

    @Test
    @Transactional
    void getAllTransportersByBirthdayIsLessThanSomething() throws Exception {
        // Initialize the database
        transporterRepository.saveAndFlush(transporter);

        // Get all the transporterList where birthday is less than DEFAULT_BIRTHDAY
        defaultTransporterShouldNotBeFound("birthday.lessThan=" + DEFAULT_BIRTHDAY);

        // Get all the transporterList where birthday is less than UPDATED_BIRTHDAY
        defaultTransporterShouldBeFound("birthday.lessThan=" + UPDATED_BIRTHDAY);
    }

    @Test
    @Transactional
    void getAllTransportersByBirthdayIsGreaterThanSomething() throws Exception {
        // Initialize the database
        transporterRepository.saveAndFlush(transporter);

        // Get all the transporterList where birthday is greater than DEFAULT_BIRTHDAY
        defaultTransporterShouldNotBeFound("birthday.greaterThan=" + DEFAULT_BIRTHDAY);

        // Get all the transporterList where birthday is greater than SMALLER_BIRTHDAY
        defaultTransporterShouldBeFound("birthday.greaterThan=" + SMALLER_BIRTHDAY);
    }

    @Test
    @Transactional
    void getAllTransportersByAddressIsEqualToSomething() throws Exception {
        // Initialize the database
        transporterRepository.saveAndFlush(transporter);

        // Get all the transporterList where address equals to DEFAULT_ADDRESS
        defaultTransporterShouldBeFound("address.equals=" + DEFAULT_ADDRESS);

        // Get all the transporterList where address equals to UPDATED_ADDRESS
        defaultTransporterShouldNotBeFound("address.equals=" + UPDATED_ADDRESS);
    }

    @Test
    @Transactional
    void getAllTransportersByAddressIsNotEqualToSomething() throws Exception {
        // Initialize the database
        transporterRepository.saveAndFlush(transporter);

        // Get all the transporterList where address not equals to DEFAULT_ADDRESS
        defaultTransporterShouldNotBeFound("address.notEquals=" + DEFAULT_ADDRESS);

        // Get all the transporterList where address not equals to UPDATED_ADDRESS
        defaultTransporterShouldBeFound("address.notEquals=" + UPDATED_ADDRESS);
    }

    @Test
    @Transactional
    void getAllTransportersByAddressIsInShouldWork() throws Exception {
        // Initialize the database
        transporterRepository.saveAndFlush(transporter);

        // Get all the transporterList where address in DEFAULT_ADDRESS or UPDATED_ADDRESS
        defaultTransporterShouldBeFound("address.in=" + DEFAULT_ADDRESS + "," + UPDATED_ADDRESS);

        // Get all the transporterList where address equals to UPDATED_ADDRESS
        defaultTransporterShouldNotBeFound("address.in=" + UPDATED_ADDRESS);
    }

    @Test
    @Transactional
    void getAllTransportersByAddressIsNullOrNotNull() throws Exception {
        // Initialize the database
        transporterRepository.saveAndFlush(transporter);

        // Get all the transporterList where address is not null
        defaultTransporterShouldBeFound("address.specified=true");

        // Get all the transporterList where address is null
        defaultTransporterShouldNotBeFound("address.specified=false");
    }

    @Test
    @Transactional
    void getAllTransportersByAddressContainsSomething() throws Exception {
        // Initialize the database
        transporterRepository.saveAndFlush(transporter);

        // Get all the transporterList where address contains DEFAULT_ADDRESS
        defaultTransporterShouldBeFound("address.contains=" + DEFAULT_ADDRESS);

        // Get all the transporterList where address contains UPDATED_ADDRESS
        defaultTransporterShouldNotBeFound("address.contains=" + UPDATED_ADDRESS);
    }

    @Test
    @Transactional
    void getAllTransportersByAddressNotContainsSomething() throws Exception {
        // Initialize the database
        transporterRepository.saveAndFlush(transporter);

        // Get all the transporterList where address does not contain DEFAULT_ADDRESS
        defaultTransporterShouldNotBeFound("address.doesNotContain=" + DEFAULT_ADDRESS);

        // Get all the transporterList where address does not contain UPDATED_ADDRESS
        defaultTransporterShouldBeFound("address.doesNotContain=" + UPDATED_ADDRESS);
    }

    @Test
    @Transactional
    void getAllTransportersByPhotoIsEqualToSomething() throws Exception {
        // Initialize the database
        transporterRepository.saveAndFlush(transporter);

        // Get all the transporterList where photo equals to DEFAULT_PHOTO
        defaultTransporterShouldBeFound("photo.equals=" + DEFAULT_PHOTO);

        // Get all the transporterList where photo equals to UPDATED_PHOTO
        defaultTransporterShouldNotBeFound("photo.equals=" + UPDATED_PHOTO);
    }

    @Test
    @Transactional
    void getAllTransportersByPhotoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        transporterRepository.saveAndFlush(transporter);

        // Get all the transporterList where photo not equals to DEFAULT_PHOTO
        defaultTransporterShouldNotBeFound("photo.notEquals=" + DEFAULT_PHOTO);

        // Get all the transporterList where photo not equals to UPDATED_PHOTO
        defaultTransporterShouldBeFound("photo.notEquals=" + UPDATED_PHOTO);
    }

    @Test
    @Transactional
    void getAllTransportersByPhotoIsInShouldWork() throws Exception {
        // Initialize the database
        transporterRepository.saveAndFlush(transporter);

        // Get all the transporterList where photo in DEFAULT_PHOTO or UPDATED_PHOTO
        defaultTransporterShouldBeFound("photo.in=" + DEFAULT_PHOTO + "," + UPDATED_PHOTO);

        // Get all the transporterList where photo equals to UPDATED_PHOTO
        defaultTransporterShouldNotBeFound("photo.in=" + UPDATED_PHOTO);
    }

    @Test
    @Transactional
    void getAllTransportersByPhotoIsNullOrNotNull() throws Exception {
        // Initialize the database
        transporterRepository.saveAndFlush(transporter);

        // Get all the transporterList where photo is not null
        defaultTransporterShouldBeFound("photo.specified=true");

        // Get all the transporterList where photo is null
        defaultTransporterShouldNotBeFound("photo.specified=false");
    }

    @Test
    @Transactional
    void getAllTransportersByPhotoContainsSomething() throws Exception {
        // Initialize the database
        transporterRepository.saveAndFlush(transporter);

        // Get all the transporterList where photo contains DEFAULT_PHOTO
        defaultTransporterShouldBeFound("photo.contains=" + DEFAULT_PHOTO);

        // Get all the transporterList where photo contains UPDATED_PHOTO
        defaultTransporterShouldNotBeFound("photo.contains=" + UPDATED_PHOTO);
    }

    @Test
    @Transactional
    void getAllTransportersByPhotoNotContainsSomething() throws Exception {
        // Initialize the database
        transporterRepository.saveAndFlush(transporter);

        // Get all the transporterList where photo does not contain DEFAULT_PHOTO
        defaultTransporterShouldNotBeFound("photo.doesNotContain=" + DEFAULT_PHOTO);

        // Get all the transporterList where photo does not contain UPDATED_PHOTO
        defaultTransporterShouldBeFound("photo.doesNotContain=" + UPDATED_PHOTO);
    }

    @Test
    @Transactional
    void getAllTransportersByFavouriteTransportIsEqualToSomething() throws Exception {
        // Initialize the database
        transporterRepository.saveAndFlush(transporter);

        // Get all the transporterList where favouriteTransport equals to DEFAULT_FAVOURITE_TRANSPORT
        defaultTransporterShouldBeFound("favouriteTransport.equals=" + DEFAULT_FAVOURITE_TRANSPORT);

        // Get all the transporterList where favouriteTransport equals to UPDATED_FAVOURITE_TRANSPORT
        defaultTransporterShouldNotBeFound("favouriteTransport.equals=" + UPDATED_FAVOURITE_TRANSPORT);
    }

    @Test
    @Transactional
    void getAllTransportersByFavouriteTransportIsNotEqualToSomething() throws Exception {
        // Initialize the database
        transporterRepository.saveAndFlush(transporter);

        // Get all the transporterList where favouriteTransport not equals to DEFAULT_FAVOURITE_TRANSPORT
        defaultTransporterShouldNotBeFound("favouriteTransport.notEquals=" + DEFAULT_FAVOURITE_TRANSPORT);

        // Get all the transporterList where favouriteTransport not equals to UPDATED_FAVOURITE_TRANSPORT
        defaultTransporterShouldBeFound("favouriteTransport.notEquals=" + UPDATED_FAVOURITE_TRANSPORT);
    }

    @Test
    @Transactional
    void getAllTransportersByFavouriteTransportIsInShouldWork() throws Exception {
        // Initialize the database
        transporterRepository.saveAndFlush(transporter);

        // Get all the transporterList where favouriteTransport in DEFAULT_FAVOURITE_TRANSPORT or UPDATED_FAVOURITE_TRANSPORT
        defaultTransporterShouldBeFound("favouriteTransport.in=" + DEFAULT_FAVOURITE_TRANSPORT + "," + UPDATED_FAVOURITE_TRANSPORT);

        // Get all the transporterList where favouriteTransport equals to UPDATED_FAVOURITE_TRANSPORT
        defaultTransporterShouldNotBeFound("favouriteTransport.in=" + UPDATED_FAVOURITE_TRANSPORT);
    }

    @Test
    @Transactional
    void getAllTransportersByFavouriteTransportIsNullOrNotNull() throws Exception {
        // Initialize the database
        transporterRepository.saveAndFlush(transporter);

        // Get all the transporterList where favouriteTransport is not null
        defaultTransporterShouldBeFound("favouriteTransport.specified=true");

        // Get all the transporterList where favouriteTransport is null
        defaultTransporterShouldNotBeFound("favouriteTransport.specified=false");
    }

    @Test
    @Transactional
    void getAllTransportersByFavouriteTransportContainsSomething() throws Exception {
        // Initialize the database
        transporterRepository.saveAndFlush(transporter);

        // Get all the transporterList where favouriteTransport contains DEFAULT_FAVOURITE_TRANSPORT
        defaultTransporterShouldBeFound("favouriteTransport.contains=" + DEFAULT_FAVOURITE_TRANSPORT);

        // Get all the transporterList where favouriteTransport contains UPDATED_FAVOURITE_TRANSPORT
        defaultTransporterShouldNotBeFound("favouriteTransport.contains=" + UPDATED_FAVOURITE_TRANSPORT);
    }

    @Test
    @Transactional
    void getAllTransportersByFavouriteTransportNotContainsSomething() throws Exception {
        // Initialize the database
        transporterRepository.saveAndFlush(transporter);

        // Get all the transporterList where favouriteTransport does not contain DEFAULT_FAVOURITE_TRANSPORT
        defaultTransporterShouldNotBeFound("favouriteTransport.doesNotContain=" + DEFAULT_FAVOURITE_TRANSPORT);

        // Get all the transporterList where favouriteTransport does not contain UPDATED_FAVOURITE_TRANSPORT
        defaultTransporterShouldBeFound("favouriteTransport.doesNotContain=" + UPDATED_FAVOURITE_TRANSPORT);
    }

    @Test
    @Transactional
    void getAllTransportersByNumberOfDeliveriesIsEqualToSomething() throws Exception {
        // Initialize the database
        transporterRepository.saveAndFlush(transporter);

        // Get all the transporterList where numberOfDeliveries equals to DEFAULT_NUMBER_OF_DELIVERIES
        defaultTransporterShouldBeFound("numberOfDeliveries.equals=" + DEFAULT_NUMBER_OF_DELIVERIES);

        // Get all the transporterList where numberOfDeliveries equals to UPDATED_NUMBER_OF_DELIVERIES
        defaultTransporterShouldNotBeFound("numberOfDeliveries.equals=" + UPDATED_NUMBER_OF_DELIVERIES);
    }

    @Test
    @Transactional
    void getAllTransportersByNumberOfDeliveriesIsNotEqualToSomething() throws Exception {
        // Initialize the database
        transporterRepository.saveAndFlush(transporter);

        // Get all the transporterList where numberOfDeliveries not equals to DEFAULT_NUMBER_OF_DELIVERIES
        defaultTransporterShouldNotBeFound("numberOfDeliveries.notEquals=" + DEFAULT_NUMBER_OF_DELIVERIES);

        // Get all the transporterList where numberOfDeliveries not equals to UPDATED_NUMBER_OF_DELIVERIES
        defaultTransporterShouldBeFound("numberOfDeliveries.notEquals=" + UPDATED_NUMBER_OF_DELIVERIES);
    }

    @Test
    @Transactional
    void getAllTransportersByNumberOfDeliveriesIsInShouldWork() throws Exception {
        // Initialize the database
        transporterRepository.saveAndFlush(transporter);

        // Get all the transporterList where numberOfDeliveries in DEFAULT_NUMBER_OF_DELIVERIES or UPDATED_NUMBER_OF_DELIVERIES
        defaultTransporterShouldBeFound("numberOfDeliveries.in=" + DEFAULT_NUMBER_OF_DELIVERIES + "," + UPDATED_NUMBER_OF_DELIVERIES);

        // Get all the transporterList where numberOfDeliveries equals to UPDATED_NUMBER_OF_DELIVERIES
        defaultTransporterShouldNotBeFound("numberOfDeliveries.in=" + UPDATED_NUMBER_OF_DELIVERIES);
    }

    @Test
    @Transactional
    void getAllTransportersByNumberOfDeliveriesIsNullOrNotNull() throws Exception {
        // Initialize the database
        transporterRepository.saveAndFlush(transporter);

        // Get all the transporterList where numberOfDeliveries is not null
        defaultTransporterShouldBeFound("numberOfDeliveries.specified=true");

        // Get all the transporterList where numberOfDeliveries is null
        defaultTransporterShouldNotBeFound("numberOfDeliveries.specified=false");
    }

    @Test
    @Transactional
    void getAllTransportersByNumberOfDeliveriesIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        transporterRepository.saveAndFlush(transporter);

        // Get all the transporterList where numberOfDeliveries is greater than or equal to DEFAULT_NUMBER_OF_DELIVERIES
        defaultTransporterShouldBeFound("numberOfDeliveries.greaterThanOrEqual=" + DEFAULT_NUMBER_OF_DELIVERIES);

        // Get all the transporterList where numberOfDeliveries is greater than or equal to UPDATED_NUMBER_OF_DELIVERIES
        defaultTransporterShouldNotBeFound("numberOfDeliveries.greaterThanOrEqual=" + UPDATED_NUMBER_OF_DELIVERIES);
    }

    @Test
    @Transactional
    void getAllTransportersByNumberOfDeliveriesIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        transporterRepository.saveAndFlush(transporter);

        // Get all the transporterList where numberOfDeliveries is less than or equal to DEFAULT_NUMBER_OF_DELIVERIES
        defaultTransporterShouldBeFound("numberOfDeliveries.lessThanOrEqual=" + DEFAULT_NUMBER_OF_DELIVERIES);

        // Get all the transporterList where numberOfDeliveries is less than or equal to SMALLER_NUMBER_OF_DELIVERIES
        defaultTransporterShouldNotBeFound("numberOfDeliveries.lessThanOrEqual=" + SMALLER_NUMBER_OF_DELIVERIES);
    }

    @Test
    @Transactional
    void getAllTransportersByNumberOfDeliveriesIsLessThanSomething() throws Exception {
        // Initialize the database
        transporterRepository.saveAndFlush(transporter);

        // Get all the transporterList where numberOfDeliveries is less than DEFAULT_NUMBER_OF_DELIVERIES
        defaultTransporterShouldNotBeFound("numberOfDeliveries.lessThan=" + DEFAULT_NUMBER_OF_DELIVERIES);

        // Get all the transporterList where numberOfDeliveries is less than UPDATED_NUMBER_OF_DELIVERIES
        defaultTransporterShouldBeFound("numberOfDeliveries.lessThan=" + UPDATED_NUMBER_OF_DELIVERIES);
    }

    @Test
    @Transactional
    void getAllTransportersByNumberOfDeliveriesIsGreaterThanSomething() throws Exception {
        // Initialize the database
        transporterRepository.saveAndFlush(transporter);

        // Get all the transporterList where numberOfDeliveries is greater than DEFAULT_NUMBER_OF_DELIVERIES
        defaultTransporterShouldNotBeFound("numberOfDeliveries.greaterThan=" + DEFAULT_NUMBER_OF_DELIVERIES);

        // Get all the transporterList where numberOfDeliveries is greater than SMALLER_NUMBER_OF_DELIVERIES
        defaultTransporterShouldBeFound("numberOfDeliveries.greaterThan=" + SMALLER_NUMBER_OF_DELIVERIES);
    }

    @Test
    @Transactional
    void getAllTransportersByNumberOfKmIsEqualToSomething() throws Exception {
        // Initialize the database
        transporterRepository.saveAndFlush(transporter);

        // Get all the transporterList where numberOfKm equals to DEFAULT_NUMBER_OF_KM
        defaultTransporterShouldBeFound("numberOfKm.equals=" + DEFAULT_NUMBER_OF_KM);

        // Get all the transporterList where numberOfKm equals to UPDATED_NUMBER_OF_KM
        defaultTransporterShouldNotBeFound("numberOfKm.equals=" + UPDATED_NUMBER_OF_KM);
    }

    @Test
    @Transactional
    void getAllTransportersByNumberOfKmIsNotEqualToSomething() throws Exception {
        // Initialize the database
        transporterRepository.saveAndFlush(transporter);

        // Get all the transporterList where numberOfKm not equals to DEFAULT_NUMBER_OF_KM
        defaultTransporterShouldNotBeFound("numberOfKm.notEquals=" + DEFAULT_NUMBER_OF_KM);

        // Get all the transporterList where numberOfKm not equals to UPDATED_NUMBER_OF_KM
        defaultTransporterShouldBeFound("numberOfKm.notEquals=" + UPDATED_NUMBER_OF_KM);
    }

    @Test
    @Transactional
    void getAllTransportersByNumberOfKmIsInShouldWork() throws Exception {
        // Initialize the database
        transporterRepository.saveAndFlush(transporter);

        // Get all the transporterList where numberOfKm in DEFAULT_NUMBER_OF_KM or UPDATED_NUMBER_OF_KM
        defaultTransporterShouldBeFound("numberOfKm.in=" + DEFAULT_NUMBER_OF_KM + "," + UPDATED_NUMBER_OF_KM);

        // Get all the transporterList where numberOfKm equals to UPDATED_NUMBER_OF_KM
        defaultTransporterShouldNotBeFound("numberOfKm.in=" + UPDATED_NUMBER_OF_KM);
    }

    @Test
    @Transactional
    void getAllTransportersByNumberOfKmIsNullOrNotNull() throws Exception {
        // Initialize the database
        transporterRepository.saveAndFlush(transporter);

        // Get all the transporterList where numberOfKm is not null
        defaultTransporterShouldBeFound("numberOfKm.specified=true");

        // Get all the transporterList where numberOfKm is null
        defaultTransporterShouldNotBeFound("numberOfKm.specified=false");
    }

    @Test
    @Transactional
    void getAllTransportersByNumberOfKmIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        transporterRepository.saveAndFlush(transporter);

        // Get all the transporterList where numberOfKm is greater than or equal to DEFAULT_NUMBER_OF_KM
        defaultTransporterShouldBeFound("numberOfKm.greaterThanOrEqual=" + DEFAULT_NUMBER_OF_KM);

        // Get all the transporterList where numberOfKm is greater than or equal to UPDATED_NUMBER_OF_KM
        defaultTransporterShouldNotBeFound("numberOfKm.greaterThanOrEqual=" + UPDATED_NUMBER_OF_KM);
    }

    @Test
    @Transactional
    void getAllTransportersByNumberOfKmIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        transporterRepository.saveAndFlush(transporter);

        // Get all the transporterList where numberOfKm is less than or equal to DEFAULT_NUMBER_OF_KM
        defaultTransporterShouldBeFound("numberOfKm.lessThanOrEqual=" + DEFAULT_NUMBER_OF_KM);

        // Get all the transporterList where numberOfKm is less than or equal to SMALLER_NUMBER_OF_KM
        defaultTransporterShouldNotBeFound("numberOfKm.lessThanOrEqual=" + SMALLER_NUMBER_OF_KM);
    }

    @Test
    @Transactional
    void getAllTransportersByNumberOfKmIsLessThanSomething() throws Exception {
        // Initialize the database
        transporterRepository.saveAndFlush(transporter);

        // Get all the transporterList where numberOfKm is less than DEFAULT_NUMBER_OF_KM
        defaultTransporterShouldNotBeFound("numberOfKm.lessThan=" + DEFAULT_NUMBER_OF_KM);

        // Get all the transporterList where numberOfKm is less than UPDATED_NUMBER_OF_KM
        defaultTransporterShouldBeFound("numberOfKm.lessThan=" + UPDATED_NUMBER_OF_KM);
    }

    @Test
    @Transactional
    void getAllTransportersByNumberOfKmIsGreaterThanSomething() throws Exception {
        // Initialize the database
        transporterRepository.saveAndFlush(transporter);

        // Get all the transporterList where numberOfKm is greater than DEFAULT_NUMBER_OF_KM
        defaultTransporterShouldNotBeFound("numberOfKm.greaterThan=" + DEFAULT_NUMBER_OF_KM);

        // Get all the transporterList where numberOfKm is greater than SMALLER_NUMBER_OF_KM
        defaultTransporterShouldBeFound("numberOfKm.greaterThan=" + SMALLER_NUMBER_OF_KM);
    }

    @Test
    @Transactional
    void getAllTransportersByReceivedValueIsEqualToSomething() throws Exception {
        // Initialize the database
        transporterRepository.saveAndFlush(transporter);

        // Get all the transporterList where receivedValue equals to DEFAULT_RECEIVED_VALUE
        defaultTransporterShouldBeFound("receivedValue.equals=" + DEFAULT_RECEIVED_VALUE);

        // Get all the transporterList where receivedValue equals to UPDATED_RECEIVED_VALUE
        defaultTransporterShouldNotBeFound("receivedValue.equals=" + UPDATED_RECEIVED_VALUE);
    }

    @Test
    @Transactional
    void getAllTransportersByReceivedValueIsNotEqualToSomething() throws Exception {
        // Initialize the database
        transporterRepository.saveAndFlush(transporter);

        // Get all the transporterList where receivedValue not equals to DEFAULT_RECEIVED_VALUE
        defaultTransporterShouldNotBeFound("receivedValue.notEquals=" + DEFAULT_RECEIVED_VALUE);

        // Get all the transporterList where receivedValue not equals to UPDATED_RECEIVED_VALUE
        defaultTransporterShouldBeFound("receivedValue.notEquals=" + UPDATED_RECEIVED_VALUE);
    }

    @Test
    @Transactional
    void getAllTransportersByReceivedValueIsInShouldWork() throws Exception {
        // Initialize the database
        transporterRepository.saveAndFlush(transporter);

        // Get all the transporterList where receivedValue in DEFAULT_RECEIVED_VALUE or UPDATED_RECEIVED_VALUE
        defaultTransporterShouldBeFound("receivedValue.in=" + DEFAULT_RECEIVED_VALUE + "," + UPDATED_RECEIVED_VALUE);

        // Get all the transporterList where receivedValue equals to UPDATED_RECEIVED_VALUE
        defaultTransporterShouldNotBeFound("receivedValue.in=" + UPDATED_RECEIVED_VALUE);
    }

    @Test
    @Transactional
    void getAllTransportersByReceivedValueIsNullOrNotNull() throws Exception {
        // Initialize the database
        transporterRepository.saveAndFlush(transporter);

        // Get all the transporterList where receivedValue is not null
        defaultTransporterShouldBeFound("receivedValue.specified=true");

        // Get all the transporterList where receivedValue is null
        defaultTransporterShouldNotBeFound("receivedValue.specified=false");
    }

    @Test
    @Transactional
    void getAllTransportersByReceivedValueIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        transporterRepository.saveAndFlush(transporter);

        // Get all the transporterList where receivedValue is greater than or equal to DEFAULT_RECEIVED_VALUE
        defaultTransporterShouldBeFound("receivedValue.greaterThanOrEqual=" + DEFAULT_RECEIVED_VALUE);

        // Get all the transporterList where receivedValue is greater than or equal to UPDATED_RECEIVED_VALUE
        defaultTransporterShouldNotBeFound("receivedValue.greaterThanOrEqual=" + UPDATED_RECEIVED_VALUE);
    }

    @Test
    @Transactional
    void getAllTransportersByReceivedValueIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        transporterRepository.saveAndFlush(transporter);

        // Get all the transporterList where receivedValue is less than or equal to DEFAULT_RECEIVED_VALUE
        defaultTransporterShouldBeFound("receivedValue.lessThanOrEqual=" + DEFAULT_RECEIVED_VALUE);

        // Get all the transporterList where receivedValue is less than or equal to SMALLER_RECEIVED_VALUE
        defaultTransporterShouldNotBeFound("receivedValue.lessThanOrEqual=" + SMALLER_RECEIVED_VALUE);
    }

    @Test
    @Transactional
    void getAllTransportersByReceivedValueIsLessThanSomething() throws Exception {
        // Initialize the database
        transporterRepository.saveAndFlush(transporter);

        // Get all the transporterList where receivedValue is less than DEFAULT_RECEIVED_VALUE
        defaultTransporterShouldNotBeFound("receivedValue.lessThan=" + DEFAULT_RECEIVED_VALUE);

        // Get all the transporterList where receivedValue is less than UPDATED_RECEIVED_VALUE
        defaultTransporterShouldBeFound("receivedValue.lessThan=" + UPDATED_RECEIVED_VALUE);
    }

    @Test
    @Transactional
    void getAllTransportersByReceivedValueIsGreaterThanSomething() throws Exception {
        // Initialize the database
        transporterRepository.saveAndFlush(transporter);

        // Get all the transporterList where receivedValue is greater than DEFAULT_RECEIVED_VALUE
        defaultTransporterShouldNotBeFound("receivedValue.greaterThan=" + DEFAULT_RECEIVED_VALUE);

        // Get all the transporterList where receivedValue is greater than SMALLER_RECEIVED_VALUE
        defaultTransporterShouldBeFound("receivedValue.greaterThan=" + SMALLER_RECEIVED_VALUE);
    }

    @Test
    @Transactional
    void getAllTransportersByValueToReceiveIsEqualToSomething() throws Exception {
        // Initialize the database
        transporterRepository.saveAndFlush(transporter);

        // Get all the transporterList where valueToReceive equals to DEFAULT_VALUE_TO_RECEIVE
        defaultTransporterShouldBeFound("valueToReceive.equals=" + DEFAULT_VALUE_TO_RECEIVE);

        // Get all the transporterList where valueToReceive equals to UPDATED_VALUE_TO_RECEIVE
        defaultTransporterShouldNotBeFound("valueToReceive.equals=" + UPDATED_VALUE_TO_RECEIVE);
    }

    @Test
    @Transactional
    void getAllTransportersByValueToReceiveIsNotEqualToSomething() throws Exception {
        // Initialize the database
        transporterRepository.saveAndFlush(transporter);

        // Get all the transporterList where valueToReceive not equals to DEFAULT_VALUE_TO_RECEIVE
        defaultTransporterShouldNotBeFound("valueToReceive.notEquals=" + DEFAULT_VALUE_TO_RECEIVE);

        // Get all the transporterList where valueToReceive not equals to UPDATED_VALUE_TO_RECEIVE
        defaultTransporterShouldBeFound("valueToReceive.notEquals=" + UPDATED_VALUE_TO_RECEIVE);
    }

    @Test
    @Transactional
    void getAllTransportersByValueToReceiveIsInShouldWork() throws Exception {
        // Initialize the database
        transporterRepository.saveAndFlush(transporter);

        // Get all the transporterList where valueToReceive in DEFAULT_VALUE_TO_RECEIVE or UPDATED_VALUE_TO_RECEIVE
        defaultTransporterShouldBeFound("valueToReceive.in=" + DEFAULT_VALUE_TO_RECEIVE + "," + UPDATED_VALUE_TO_RECEIVE);

        // Get all the transporterList where valueToReceive equals to UPDATED_VALUE_TO_RECEIVE
        defaultTransporterShouldNotBeFound("valueToReceive.in=" + UPDATED_VALUE_TO_RECEIVE);
    }

    @Test
    @Transactional
    void getAllTransportersByValueToReceiveIsNullOrNotNull() throws Exception {
        // Initialize the database
        transporterRepository.saveAndFlush(transporter);

        // Get all the transporterList where valueToReceive is not null
        defaultTransporterShouldBeFound("valueToReceive.specified=true");

        // Get all the transporterList where valueToReceive is null
        defaultTransporterShouldNotBeFound("valueToReceive.specified=false");
    }

    @Test
    @Transactional
    void getAllTransportersByValueToReceiveIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        transporterRepository.saveAndFlush(transporter);

        // Get all the transporterList where valueToReceive is greater than or equal to DEFAULT_VALUE_TO_RECEIVE
        defaultTransporterShouldBeFound("valueToReceive.greaterThanOrEqual=" + DEFAULT_VALUE_TO_RECEIVE);

        // Get all the transporterList where valueToReceive is greater than or equal to UPDATED_VALUE_TO_RECEIVE
        defaultTransporterShouldNotBeFound("valueToReceive.greaterThanOrEqual=" + UPDATED_VALUE_TO_RECEIVE);
    }

    @Test
    @Transactional
    void getAllTransportersByValueToReceiveIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        transporterRepository.saveAndFlush(transporter);

        // Get all the transporterList where valueToReceive is less than or equal to DEFAULT_VALUE_TO_RECEIVE
        defaultTransporterShouldBeFound("valueToReceive.lessThanOrEqual=" + DEFAULT_VALUE_TO_RECEIVE);

        // Get all the transporterList where valueToReceive is less than or equal to SMALLER_VALUE_TO_RECEIVE
        defaultTransporterShouldNotBeFound("valueToReceive.lessThanOrEqual=" + SMALLER_VALUE_TO_RECEIVE);
    }

    @Test
    @Transactional
    void getAllTransportersByValueToReceiveIsLessThanSomething() throws Exception {
        // Initialize the database
        transporterRepository.saveAndFlush(transporter);

        // Get all the transporterList where valueToReceive is less than DEFAULT_VALUE_TO_RECEIVE
        defaultTransporterShouldNotBeFound("valueToReceive.lessThan=" + DEFAULT_VALUE_TO_RECEIVE);

        // Get all the transporterList where valueToReceive is less than UPDATED_VALUE_TO_RECEIVE
        defaultTransporterShouldBeFound("valueToReceive.lessThan=" + UPDATED_VALUE_TO_RECEIVE);
    }

    @Test
    @Transactional
    void getAllTransportersByValueToReceiveIsGreaterThanSomething() throws Exception {
        // Initialize the database
        transporterRepository.saveAndFlush(transporter);

        // Get all the transporterList where valueToReceive is greater than DEFAULT_VALUE_TO_RECEIVE
        defaultTransporterShouldNotBeFound("valueToReceive.greaterThan=" + DEFAULT_VALUE_TO_RECEIVE);

        // Get all the transporterList where valueToReceive is greater than SMALLER_VALUE_TO_RECEIVE
        defaultTransporterShouldBeFound("valueToReceive.greaterThan=" + SMALLER_VALUE_TO_RECEIVE);
    }

    @Test
    @Transactional
    void getAllTransportersByRankingIsEqualToSomething() throws Exception {
        // Initialize the database
        transporterRepository.saveAndFlush(transporter);

        // Get all the transporterList where ranking equals to DEFAULT_RANKING
        defaultTransporterShouldBeFound("ranking.equals=" + DEFAULT_RANKING);

        // Get all the transporterList where ranking equals to UPDATED_RANKING
        defaultTransporterShouldNotBeFound("ranking.equals=" + UPDATED_RANKING);
    }

    @Test
    @Transactional
    void getAllTransportersByRankingIsNotEqualToSomething() throws Exception {
        // Initialize the database
        transporterRepository.saveAndFlush(transporter);

        // Get all the transporterList where ranking not equals to DEFAULT_RANKING
        defaultTransporterShouldNotBeFound("ranking.notEquals=" + DEFAULT_RANKING);

        // Get all the transporterList where ranking not equals to UPDATED_RANKING
        defaultTransporterShouldBeFound("ranking.notEquals=" + UPDATED_RANKING);
    }

    @Test
    @Transactional
    void getAllTransportersByRankingIsInShouldWork() throws Exception {
        // Initialize the database
        transporterRepository.saveAndFlush(transporter);

        // Get all the transporterList where ranking in DEFAULT_RANKING or UPDATED_RANKING
        defaultTransporterShouldBeFound("ranking.in=" + DEFAULT_RANKING + "," + UPDATED_RANKING);

        // Get all the transporterList where ranking equals to UPDATED_RANKING
        defaultTransporterShouldNotBeFound("ranking.in=" + UPDATED_RANKING);
    }

    @Test
    @Transactional
    void getAllTransportersByRankingIsNullOrNotNull() throws Exception {
        // Initialize the database
        transporterRepository.saveAndFlush(transporter);

        // Get all the transporterList where ranking is not null
        defaultTransporterShouldBeFound("ranking.specified=true");

        // Get all the transporterList where ranking is null
        defaultTransporterShouldNotBeFound("ranking.specified=false");
    }

    @Test
    @Transactional
    void getAllTransportersByRankingIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        transporterRepository.saveAndFlush(transporter);

        // Get all the transporterList where ranking is greater than or equal to DEFAULT_RANKING
        defaultTransporterShouldBeFound("ranking.greaterThanOrEqual=" + DEFAULT_RANKING);

        // Get all the transporterList where ranking is greater than or equal to UPDATED_RANKING
        defaultTransporterShouldNotBeFound("ranking.greaterThanOrEqual=" + UPDATED_RANKING);
    }

    @Test
    @Transactional
    void getAllTransportersByRankingIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        transporterRepository.saveAndFlush(transporter);

        // Get all the transporterList where ranking is less than or equal to DEFAULT_RANKING
        defaultTransporterShouldBeFound("ranking.lessThanOrEqual=" + DEFAULT_RANKING);

        // Get all the transporterList where ranking is less than or equal to SMALLER_RANKING
        defaultTransporterShouldNotBeFound("ranking.lessThanOrEqual=" + SMALLER_RANKING);
    }

    @Test
    @Transactional
    void getAllTransportersByRankingIsLessThanSomething() throws Exception {
        // Initialize the database
        transporterRepository.saveAndFlush(transporter);

        // Get all the transporterList where ranking is less than DEFAULT_RANKING
        defaultTransporterShouldNotBeFound("ranking.lessThan=" + DEFAULT_RANKING);

        // Get all the transporterList where ranking is less than UPDATED_RANKING
        defaultTransporterShouldBeFound("ranking.lessThan=" + UPDATED_RANKING);
    }

    @Test
    @Transactional
    void getAllTransportersByRankingIsGreaterThanSomething() throws Exception {
        // Initialize the database
        transporterRepository.saveAndFlush(transporter);

        // Get all the transporterList where ranking is greater than DEFAULT_RANKING
        defaultTransporterShouldNotBeFound("ranking.greaterThan=" + DEFAULT_RANKING);

        // Get all the transporterList where ranking is greater than SMALLER_RANKING
        defaultTransporterShouldBeFound("ranking.greaterThan=" + SMALLER_RANKING);
    }

    @Test
    @Transactional
    void getAllTransportersByRoutesIsEqualToSomething() throws Exception {
        // Initialize the database
        transporterRepository.saveAndFlush(transporter);
        Route routes = RouteResourceIT.createEntity(em);
        em.persist(routes);
        em.flush();
        transporter.addRoutes(routes);
        transporterRepository.saveAndFlush(transporter);
        Long routesId = routes.getId();

        // Get all the transporterList where routes equals to routesId
        defaultTransporterShouldBeFound("routesId.equals=" + routesId);

        // Get all the transporterList where routes equals to (routesId + 1)
        defaultTransporterShouldNotBeFound("routesId.equals=" + (routesId + 1));
    }

    @Test
    @Transactional
    void getAllTransportersByZonesIsEqualToSomething() throws Exception {
        // Initialize the database
        transporterRepository.saveAndFlush(transporter);
        Zone zones = ZoneResourceIT.createEntity(em);
        em.persist(zones);
        em.flush();
        transporter.addZones(zones);
        transporterRepository.saveAndFlush(transporter);
        Long zonesId = zones.getId();

        // Get all the transporterList where zones equals to zonesId
        defaultTransporterShouldBeFound("zonesId.equals=" + zonesId);

        // Get all the transporterList where zones equals to (zonesId + 1)
        defaultTransporterShouldNotBeFound("zonesId.equals=" + (zonesId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultTransporterShouldBeFound(String filter) throws Exception {
        restTransporterMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(transporter.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].phoneNumber").value(hasItem(DEFAULT_PHONE_NUMBER)))
            .andExpect(jsonPath("$.[*].nib").value(hasItem(DEFAULT_NIB)))
            .andExpect(jsonPath("$.[*].nif").value(hasItem(DEFAULT_NIF)))
            .andExpect(jsonPath("$.[*].birthday").value(hasItem(DEFAULT_BIRTHDAY.toString())))
            .andExpect(jsonPath("$.[*].address").value(hasItem(DEFAULT_ADDRESS)))
            .andExpect(jsonPath("$.[*].photo").value(hasItem(DEFAULT_PHOTO)))
            .andExpect(jsonPath("$.[*].favouriteTransport").value(hasItem(DEFAULT_FAVOURITE_TRANSPORT)))
            .andExpect(jsonPath("$.[*].numberOfDeliveries").value(hasItem(DEFAULT_NUMBER_OF_DELIVERIES)))
            .andExpect(jsonPath("$.[*].numberOfKm").value(hasItem(DEFAULT_NUMBER_OF_KM.doubleValue())))
            .andExpect(jsonPath("$.[*].receivedValue").value(hasItem(DEFAULT_RECEIVED_VALUE.doubleValue())))
            .andExpect(jsonPath("$.[*].valueToReceive").value(hasItem(DEFAULT_VALUE_TO_RECEIVE.doubleValue())))
            .andExpect(jsonPath("$.[*].ranking").value(hasItem(DEFAULT_RANKING.doubleValue())));

        // Check, that the count call also returns 1
        restTransporterMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultTransporterShouldNotBeFound(String filter) throws Exception {
        restTransporterMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restTransporterMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingTransporter() throws Exception {
        // Get the transporter
        restTransporterMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewTransporter() throws Exception {
        // Initialize the database
        transporterRepository.saveAndFlush(transporter);

        int databaseSizeBeforeUpdate = transporterRepository.findAll().size();

        // Update the transporter
        Transporter updatedTransporter = transporterRepository.findById(transporter.getId()).get();
        // Disconnect from session so that the updates on updatedTransporter are not directly saved in db
        em.detach(updatedTransporter);
        updatedTransporter
            .name(UPDATED_NAME)
            .email(UPDATED_EMAIL)
            .phoneNumber(UPDATED_PHONE_NUMBER)
            .nib(UPDATED_NIB)
            .nif(UPDATED_NIF)
            .birthday(UPDATED_BIRTHDAY)
            .address(UPDATED_ADDRESS)
            .photo(UPDATED_PHOTO)
            .favouriteTransport(UPDATED_FAVOURITE_TRANSPORT)
            .numberOfDeliveries(UPDATED_NUMBER_OF_DELIVERIES)
            .numberOfKm(UPDATED_NUMBER_OF_KM)
            .receivedValue(UPDATED_RECEIVED_VALUE)
            .valueToReceive(UPDATED_VALUE_TO_RECEIVE)
            .ranking(UPDATED_RANKING);
        TransporterDTO transporterDTO = transporterMapper.toDto(updatedTransporter);

        restTransporterMockMvc
            .perform(
                put(ENTITY_API_URL_ID, transporterDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(transporterDTO))
            )
            .andExpect(status().isOk());

        // Validate the Transporter in the database
        List<Transporter> transporterList = transporterRepository.findAll();
        assertThat(transporterList).hasSize(databaseSizeBeforeUpdate);
        Transporter testTransporter = transporterList.get(transporterList.size() - 1);
        assertThat(testTransporter.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testTransporter.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testTransporter.getPhoneNumber()).isEqualTo(UPDATED_PHONE_NUMBER);
        assertThat(testTransporter.getNib()).isEqualTo(UPDATED_NIB);
        assertThat(testTransporter.getNif()).isEqualTo(UPDATED_NIF);
        assertThat(testTransporter.getBirthday()).isEqualTo(UPDATED_BIRTHDAY);
        assertThat(testTransporter.getAddress()).isEqualTo(UPDATED_ADDRESS);
        assertThat(testTransporter.getPhoto()).isEqualTo(UPDATED_PHOTO);
        assertThat(testTransporter.getFavouriteTransport()).isEqualTo(UPDATED_FAVOURITE_TRANSPORT);
        assertThat(testTransporter.getNumberOfDeliveries()).isEqualTo(UPDATED_NUMBER_OF_DELIVERIES);
        assertThat(testTransporter.getNumberOfKm()).isEqualTo(UPDATED_NUMBER_OF_KM);
        assertThat(testTransporter.getReceivedValue()).isEqualTo(UPDATED_RECEIVED_VALUE);
        assertThat(testTransporter.getValueToReceive()).isEqualTo(UPDATED_VALUE_TO_RECEIVE);
        assertThat(testTransporter.getRanking()).isEqualTo(UPDATED_RANKING);
    }

    @Test
    @Transactional
    void putNonExistingTransporter() throws Exception {
        int databaseSizeBeforeUpdate = transporterRepository.findAll().size();
        transporter.setId(count.incrementAndGet());

        // Create the Transporter
        TransporterDTO transporterDTO = transporterMapper.toDto(transporter);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTransporterMockMvc
            .perform(
                put(ENTITY_API_URL_ID, transporterDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(transporterDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Transporter in the database
        List<Transporter> transporterList = transporterRepository.findAll();
        assertThat(transporterList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchTransporter() throws Exception {
        int databaseSizeBeforeUpdate = transporterRepository.findAll().size();
        transporter.setId(count.incrementAndGet());

        // Create the Transporter
        TransporterDTO transporterDTO = transporterMapper.toDto(transporter);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTransporterMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(transporterDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Transporter in the database
        List<Transporter> transporterList = transporterRepository.findAll();
        assertThat(transporterList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamTransporter() throws Exception {
        int databaseSizeBeforeUpdate = transporterRepository.findAll().size();
        transporter.setId(count.incrementAndGet());

        // Create the Transporter
        TransporterDTO transporterDTO = transporterMapper.toDto(transporter);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTransporterMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(transporterDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Transporter in the database
        List<Transporter> transporterList = transporterRepository.findAll();
        assertThat(transporterList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateTransporterWithPatch() throws Exception {
        // Initialize the database
        transporterRepository.saveAndFlush(transporter);

        int databaseSizeBeforeUpdate = transporterRepository.findAll().size();

        // Update the transporter using partial update
        Transporter partialUpdatedTransporter = new Transporter();
        partialUpdatedTransporter.setId(transporter.getId());

        partialUpdatedTransporter
            .name(UPDATED_NAME)
            .phoneNumber(UPDATED_PHONE_NUMBER)
            .photo(UPDATED_PHOTO)
            .valueToReceive(UPDATED_VALUE_TO_RECEIVE)
            .ranking(UPDATED_RANKING);

        restTransporterMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTransporter.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTransporter))
            )
            .andExpect(status().isOk());

        // Validate the Transporter in the database
        List<Transporter> transporterList = transporterRepository.findAll();
        assertThat(transporterList).hasSize(databaseSizeBeforeUpdate);
        Transporter testTransporter = transporterList.get(transporterList.size() - 1);
        assertThat(testTransporter.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testTransporter.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testTransporter.getPhoneNumber()).isEqualTo(UPDATED_PHONE_NUMBER);
        assertThat(testTransporter.getNib()).isEqualTo(DEFAULT_NIB);
        assertThat(testTransporter.getNif()).isEqualTo(DEFAULT_NIF);
        assertThat(testTransporter.getBirthday()).isEqualTo(DEFAULT_BIRTHDAY);
        assertThat(testTransporter.getAddress()).isEqualTo(DEFAULT_ADDRESS);
        assertThat(testTransporter.getPhoto()).isEqualTo(UPDATED_PHOTO);
        assertThat(testTransporter.getFavouriteTransport()).isEqualTo(DEFAULT_FAVOURITE_TRANSPORT);
        assertThat(testTransporter.getNumberOfDeliveries()).isEqualTo(DEFAULT_NUMBER_OF_DELIVERIES);
        assertThat(testTransporter.getNumberOfKm()).isEqualTo(DEFAULT_NUMBER_OF_KM);
        assertThat(testTransporter.getReceivedValue()).isEqualTo(DEFAULT_RECEIVED_VALUE);
        assertThat(testTransporter.getValueToReceive()).isEqualTo(UPDATED_VALUE_TO_RECEIVE);
        assertThat(testTransporter.getRanking()).isEqualTo(UPDATED_RANKING);
    }

    @Test
    @Transactional
    void fullUpdateTransporterWithPatch() throws Exception {
        // Initialize the database
        transporterRepository.saveAndFlush(transporter);

        int databaseSizeBeforeUpdate = transporterRepository.findAll().size();

        // Update the transporter using partial update
        Transporter partialUpdatedTransporter = new Transporter();
        partialUpdatedTransporter.setId(transporter.getId());

        partialUpdatedTransporter
            .name(UPDATED_NAME)
            .email(UPDATED_EMAIL)
            .phoneNumber(UPDATED_PHONE_NUMBER)
            .nib(UPDATED_NIB)
            .nif(UPDATED_NIF)
            .birthday(UPDATED_BIRTHDAY)
            .address(UPDATED_ADDRESS)
            .photo(UPDATED_PHOTO)
            .favouriteTransport(UPDATED_FAVOURITE_TRANSPORT)
            .numberOfDeliveries(UPDATED_NUMBER_OF_DELIVERIES)
            .numberOfKm(UPDATED_NUMBER_OF_KM)
            .receivedValue(UPDATED_RECEIVED_VALUE)
            .valueToReceive(UPDATED_VALUE_TO_RECEIVE)
            .ranking(UPDATED_RANKING);

        restTransporterMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTransporter.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTransporter))
            )
            .andExpect(status().isOk());

        // Validate the Transporter in the database
        List<Transporter> transporterList = transporterRepository.findAll();
        assertThat(transporterList).hasSize(databaseSizeBeforeUpdate);
        Transporter testTransporter = transporterList.get(transporterList.size() - 1);
        assertThat(testTransporter.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testTransporter.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testTransporter.getPhoneNumber()).isEqualTo(UPDATED_PHONE_NUMBER);
        assertThat(testTransporter.getNib()).isEqualTo(UPDATED_NIB);
        assertThat(testTransporter.getNif()).isEqualTo(UPDATED_NIF);
        assertThat(testTransporter.getBirthday()).isEqualTo(UPDATED_BIRTHDAY);
        assertThat(testTransporter.getAddress()).isEqualTo(UPDATED_ADDRESS);
        assertThat(testTransporter.getPhoto()).isEqualTo(UPDATED_PHOTO);
        assertThat(testTransporter.getFavouriteTransport()).isEqualTo(UPDATED_FAVOURITE_TRANSPORT);
        assertThat(testTransporter.getNumberOfDeliveries()).isEqualTo(UPDATED_NUMBER_OF_DELIVERIES);
        assertThat(testTransporter.getNumberOfKm()).isEqualTo(UPDATED_NUMBER_OF_KM);
        assertThat(testTransporter.getReceivedValue()).isEqualTo(UPDATED_RECEIVED_VALUE);
        assertThat(testTransporter.getValueToReceive()).isEqualTo(UPDATED_VALUE_TO_RECEIVE);
        assertThat(testTransporter.getRanking()).isEqualTo(UPDATED_RANKING);
    }

    @Test
    @Transactional
    void patchNonExistingTransporter() throws Exception {
        int databaseSizeBeforeUpdate = transporterRepository.findAll().size();
        transporter.setId(count.incrementAndGet());

        // Create the Transporter
        TransporterDTO transporterDTO = transporterMapper.toDto(transporter);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTransporterMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, transporterDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(transporterDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Transporter in the database
        List<Transporter> transporterList = transporterRepository.findAll();
        assertThat(transporterList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchTransporter() throws Exception {
        int databaseSizeBeforeUpdate = transporterRepository.findAll().size();
        transporter.setId(count.incrementAndGet());

        // Create the Transporter
        TransporterDTO transporterDTO = transporterMapper.toDto(transporter);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTransporterMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(transporterDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Transporter in the database
        List<Transporter> transporterList = transporterRepository.findAll();
        assertThat(transporterList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamTransporter() throws Exception {
        int databaseSizeBeforeUpdate = transporterRepository.findAll().size();
        transporter.setId(count.incrementAndGet());

        // Create the Transporter
        TransporterDTO transporterDTO = transporterMapper.toDto(transporter);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTransporterMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(transporterDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Transporter in the database
        List<Transporter> transporterList = transporterRepository.findAll();
        assertThat(transporterList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteTransporter() throws Exception {
        // Initialize the database
        transporterRepository.saveAndFlush(transporter);

        int databaseSizeBeforeDelete = transporterRepository.findAll().size();

        // Delete the transporter
        restTransporterMockMvc
            .perform(delete(ENTITY_API_URL_ID, transporter.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Transporter> transporterList = transporterRepository.findAll();
        assertThat(transporterList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
