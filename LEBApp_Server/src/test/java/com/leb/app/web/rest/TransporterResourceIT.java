package com.leb.app.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.leb.app.IntegrationTest;
import com.leb.app.domain.RidePath;
import com.leb.app.domain.Transporter;
import com.leb.app.domain.UserInfo;
import com.leb.app.domain.Zone;
import com.leb.app.repository.TransporterRepository;
import com.leb.app.service.TransporterService;
import com.leb.app.service.criteria.TransporterCriteria;
import com.leb.app.service.dto.TransporterDTO;
import com.leb.app.service.mapper.TransporterMapper;
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
            .favouriteTransport(DEFAULT_FAVOURITE_TRANSPORT)
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
        transporter.setUserInfo(userInfo);
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
            .favouriteTransport(UPDATED_FAVOURITE_TRANSPORT)
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
        transporter.setUserInfo(userInfo);
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
    void getAllTransportersByUserInfoIsEqualToSomething() throws Exception {
        // Get already existing entity
        UserInfo userInfo = transporter.getUserInfo();
        transporterRepository.saveAndFlush(transporter);
        Long userInfoId = userInfo.getId();

        // Get all the transporterList where userInfo equals to userInfoId
        defaultTransporterShouldBeFound("userInfoId.equals=" + userInfoId);

        // Get all the transporterList where userInfo equals to (userInfoId + 1)
        defaultTransporterShouldNotBeFound("userInfoId.equals=" + (userInfoId + 1));
    }

    @Test
    @Transactional
    void getAllTransportersByRidePathIsEqualToSomething() throws Exception {
        // Initialize the database
        transporterRepository.saveAndFlush(transporter);
        RidePath ridePath = RidePathResourceIT.createEntity(em);
        em.persist(ridePath);
        em.flush();
        transporter.addRidePath(ridePath);
        transporterRepository.saveAndFlush(transporter);
        Long ridePathId = ridePath.getId();

        // Get all the transporterList where ridePath equals to ridePathId
        defaultTransporterShouldBeFound("ridePathId.equals=" + ridePathId);

        // Get all the transporterList where ridePath equals to (ridePathId + 1)
        defaultTransporterShouldNotBeFound("ridePathId.equals=" + (ridePathId + 1));
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

        partialUpdatedTransporter.favouriteTransport(UPDATED_FAVOURITE_TRANSPORT).numberOfKm(UPDATED_NUMBER_OF_KM);

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
        assertThat(testTransporter.getFavouriteTransport()).isEqualTo(UPDATED_FAVOURITE_TRANSPORT);
        assertThat(testTransporter.getNumberOfDeliveries()).isEqualTo(DEFAULT_NUMBER_OF_DELIVERIES);
        assertThat(testTransporter.getNumberOfKm()).isEqualTo(UPDATED_NUMBER_OF_KM);
        assertThat(testTransporter.getReceivedValue()).isEqualTo(DEFAULT_RECEIVED_VALUE);
        assertThat(testTransporter.getValueToReceive()).isEqualTo(DEFAULT_VALUE_TO_RECEIVE);
        assertThat(testTransporter.getRanking()).isEqualTo(DEFAULT_RANKING);
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
