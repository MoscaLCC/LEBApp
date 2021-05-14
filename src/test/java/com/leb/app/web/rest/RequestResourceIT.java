package com.leb.app.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.leb.app.IntegrationTest;
import com.leb.app.domain.Dimensions;
import com.leb.app.domain.Producer;
import com.leb.app.domain.Request;
import com.leb.app.domain.Route;
import com.leb.app.domain.enumeration.Status;
import com.leb.app.repository.RequestRepository;
import com.leb.app.service.criteria.RequestCriteria;
import com.leb.app.service.dto.RequestDTO;
import com.leb.app.service.mapper.RequestMapper;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
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
 * Integration tests for the {@link RequestResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class RequestResourceIT {

    private static final Double DEFAULT_PRODUCT_VALUE = 1D;
    private static final Double UPDATED_PRODUCT_VALUE = 2D;
    private static final Double SMALLER_PRODUCT_VALUE = 1D - 1D;

    private static final String DEFAULT_PRODUCT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_PRODUCT_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_SOURCE = "AAAAAAAAAA";
    private static final String UPDATED_SOURCE = "BBBBBBBBBB";

    private static final String DEFAULT_DESTINATION = "AAAAAAAAAA";
    private static final String UPDATED_DESTINATION = "BBBBBBBBBB";

    private static final String DEFAULT_DESTINATION_CONTACT = "AAAAAAAAAA";
    private static final String UPDATED_DESTINATION_CONTACT = "BBBBBBBBBB";

    private static final Instant DEFAULT_INIT_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_INIT_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_EXPIRATION_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_EXPIRATION_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_SPECIAL_CHARACTERISTICS = "AAAAAAAAAA";
    private static final String UPDATED_SPECIAL_CHARACTERISTICS = "BBBBBBBBBB";

    private static final Double DEFAULT_PRODUCT_WEIGHT = 1D;
    private static final Double UPDATED_PRODUCT_WEIGHT = 2D;
    private static final Double SMALLER_PRODUCT_WEIGHT = 1D - 1D;

    private static final Status DEFAULT_STATUS = Status.WAITING;
    private static final Status UPDATED_STATUS = Status.OPENED;

    private static final Instant DEFAULT_ESTIMATED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_ESTIMATED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_DELIVERY_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DELIVERY_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Double DEFAULT_SHIPPING_COSTS = 1D;
    private static final Double UPDATED_SHIPPING_COSTS = 2D;
    private static final Double SMALLER_SHIPPING_COSTS = 1D - 1D;

    private static final Double DEFAULT_RATING = 1D;
    private static final Double UPDATED_RATING = 2D;
    private static final Double SMALLER_RATING = 1D - 1D;

    private static final String ENTITY_API_URL = "/api/requests";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private RequestRepository requestRepository;

    @Autowired
    private RequestMapper requestMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restRequestMockMvc;

    private Request request;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Request createEntity(EntityManager em) {
        Request request = new Request()
            .productValue(DEFAULT_PRODUCT_VALUE)
            .productName(DEFAULT_PRODUCT_NAME)
            .source(DEFAULT_SOURCE)
            .destination(DEFAULT_DESTINATION)
            .destinationContact(DEFAULT_DESTINATION_CONTACT)
            .initDate(DEFAULT_INIT_DATE)
            .expirationDate(DEFAULT_EXPIRATION_DATE)
            .description(DEFAULT_DESCRIPTION)
            .specialCharacteristics(DEFAULT_SPECIAL_CHARACTERISTICS)
            .productWeight(DEFAULT_PRODUCT_WEIGHT)
            .status(DEFAULT_STATUS)
            .estimatedDate(DEFAULT_ESTIMATED_DATE)
            .deliveryTime(DEFAULT_DELIVERY_TIME)
            .shippingCosts(DEFAULT_SHIPPING_COSTS)
            .rating(DEFAULT_RATING);
        // Add required entity
        Route route;
        if (TestUtil.findAll(em, Route.class).isEmpty()) {
            route = RouteResourceIT.createEntity(em);
            em.persist(route);
            em.flush();
        } else {
            route = TestUtil.findAll(em, Route.class).get(0);
        }
        request.setRoute(route);
        // Add required entity
        Producer producer;
        if (TestUtil.findAll(em, Producer.class).isEmpty()) {
            producer = ProducerResourceIT.createEntity(em);
            em.persist(producer);
            em.flush();
        } else {
            producer = TestUtil.findAll(em, Producer.class).get(0);
        }
        request.setProducer(producer);
        return request;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Request createUpdatedEntity(EntityManager em) {
        Request request = new Request()
            .productValue(UPDATED_PRODUCT_VALUE)
            .productName(UPDATED_PRODUCT_NAME)
            .source(UPDATED_SOURCE)
            .destination(UPDATED_DESTINATION)
            .destinationContact(UPDATED_DESTINATION_CONTACT)
            .initDate(UPDATED_INIT_DATE)
            .expirationDate(UPDATED_EXPIRATION_DATE)
            .description(UPDATED_DESCRIPTION)
            .specialCharacteristics(UPDATED_SPECIAL_CHARACTERISTICS)
            .productWeight(UPDATED_PRODUCT_WEIGHT)
            .status(UPDATED_STATUS)
            .estimatedDate(UPDATED_ESTIMATED_DATE)
            .deliveryTime(UPDATED_DELIVERY_TIME)
            .shippingCosts(UPDATED_SHIPPING_COSTS)
            .rating(UPDATED_RATING);
        // Add required entity
        Route route;
        if (TestUtil.findAll(em, Route.class).isEmpty()) {
            route = RouteResourceIT.createUpdatedEntity(em);
            em.persist(route);
            em.flush();
        } else {
            route = TestUtil.findAll(em, Route.class).get(0);
        }
        request.setRoute(route);
        // Add required entity
        Producer producer;
        if (TestUtil.findAll(em, Producer.class).isEmpty()) {
            producer = ProducerResourceIT.createUpdatedEntity(em);
            em.persist(producer);
            em.flush();
        } else {
            producer = TestUtil.findAll(em, Producer.class).get(0);
        }
        request.setProducer(producer);
        return request;
    }

    @BeforeEach
    public void initTest() {
        request = createEntity(em);
    }

    @Test
    @Transactional
    void createRequest() throws Exception {
        int databaseSizeBeforeCreate = requestRepository.findAll().size();
        // Create the Request
        RequestDTO requestDTO = requestMapper.toDto(request);
        restRequestMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(requestDTO)))
            .andExpect(status().isCreated());

        // Validate the Request in the database
        List<Request> requestList = requestRepository.findAll();
        assertThat(requestList).hasSize(databaseSizeBeforeCreate + 1);
        Request testRequest = requestList.get(requestList.size() - 1);
        assertThat(testRequest.getProductValue()).isEqualTo(DEFAULT_PRODUCT_VALUE);
        assertThat(testRequest.getProductName()).isEqualTo(DEFAULT_PRODUCT_NAME);
        assertThat(testRequest.getSource()).isEqualTo(DEFAULT_SOURCE);
        assertThat(testRequest.getDestination()).isEqualTo(DEFAULT_DESTINATION);
        assertThat(testRequest.getDestinationContact()).isEqualTo(DEFAULT_DESTINATION_CONTACT);
        assertThat(testRequest.getInitDate()).isEqualTo(DEFAULT_INIT_DATE);
        assertThat(testRequest.getExpirationDate()).isEqualTo(DEFAULT_EXPIRATION_DATE);
        assertThat(testRequest.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testRequest.getSpecialCharacteristics()).isEqualTo(DEFAULT_SPECIAL_CHARACTERISTICS);
        assertThat(testRequest.getProductWeight()).isEqualTo(DEFAULT_PRODUCT_WEIGHT);
        assertThat(testRequest.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testRequest.getEstimatedDate()).isEqualTo(DEFAULT_ESTIMATED_DATE);
        assertThat(testRequest.getDeliveryTime()).isEqualTo(DEFAULT_DELIVERY_TIME);
        assertThat(testRequest.getShippingCosts()).isEqualTo(DEFAULT_SHIPPING_COSTS);
        assertThat(testRequest.getRating()).isEqualTo(DEFAULT_RATING);
    }

    @Test
    @Transactional
    void createRequestWithExistingId() throws Exception {
        // Create the Request with an existing ID
        request.setId(1L);
        RequestDTO requestDTO = requestMapper.toDto(request);

        int databaseSizeBeforeCreate = requestRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restRequestMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(requestDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Request in the database
        List<Request> requestList = requestRepository.findAll();
        assertThat(requestList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllRequests() throws Exception {
        // Initialize the database
        requestRepository.saveAndFlush(request);

        // Get all the requestList
        restRequestMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(request.getId().intValue())))
            .andExpect(jsonPath("$.[*].productValue").value(hasItem(DEFAULT_PRODUCT_VALUE.doubleValue())))
            .andExpect(jsonPath("$.[*].productName").value(hasItem(DEFAULT_PRODUCT_NAME)))
            .andExpect(jsonPath("$.[*].source").value(hasItem(DEFAULT_SOURCE)))
            .andExpect(jsonPath("$.[*].destination").value(hasItem(DEFAULT_DESTINATION)))
            .andExpect(jsonPath("$.[*].destinationContact").value(hasItem(DEFAULT_DESTINATION_CONTACT)))
            .andExpect(jsonPath("$.[*].initDate").value(hasItem(DEFAULT_INIT_DATE.toString())))
            .andExpect(jsonPath("$.[*].expirationDate").value(hasItem(DEFAULT_EXPIRATION_DATE.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].specialCharacteristics").value(hasItem(DEFAULT_SPECIAL_CHARACTERISTICS)))
            .andExpect(jsonPath("$.[*].productWeight").value(hasItem(DEFAULT_PRODUCT_WEIGHT.doubleValue())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].estimatedDate").value(hasItem(DEFAULT_ESTIMATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].deliveryTime").value(hasItem(DEFAULT_DELIVERY_TIME.toString())))
            .andExpect(jsonPath("$.[*].shippingCosts").value(hasItem(DEFAULT_SHIPPING_COSTS.doubleValue())))
            .andExpect(jsonPath("$.[*].rating").value(hasItem(DEFAULT_RATING.doubleValue())));
    }

    @Test
    @Transactional
    void getRequest() throws Exception {
        // Initialize the database
        requestRepository.saveAndFlush(request);

        // Get the request
        restRequestMockMvc
            .perform(get(ENTITY_API_URL_ID, request.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(request.getId().intValue()))
            .andExpect(jsonPath("$.productValue").value(DEFAULT_PRODUCT_VALUE.doubleValue()))
            .andExpect(jsonPath("$.productName").value(DEFAULT_PRODUCT_NAME))
            .andExpect(jsonPath("$.source").value(DEFAULT_SOURCE))
            .andExpect(jsonPath("$.destination").value(DEFAULT_DESTINATION))
            .andExpect(jsonPath("$.destinationContact").value(DEFAULT_DESTINATION_CONTACT))
            .andExpect(jsonPath("$.initDate").value(DEFAULT_INIT_DATE.toString()))
            .andExpect(jsonPath("$.expirationDate").value(DEFAULT_EXPIRATION_DATE.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.specialCharacteristics").value(DEFAULT_SPECIAL_CHARACTERISTICS))
            .andExpect(jsonPath("$.productWeight").value(DEFAULT_PRODUCT_WEIGHT.doubleValue()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.estimatedDate").value(DEFAULT_ESTIMATED_DATE.toString()))
            .andExpect(jsonPath("$.deliveryTime").value(DEFAULT_DELIVERY_TIME.toString()))
            .andExpect(jsonPath("$.shippingCosts").value(DEFAULT_SHIPPING_COSTS.doubleValue()))
            .andExpect(jsonPath("$.rating").value(DEFAULT_RATING.doubleValue()));
    }

    @Test
    @Transactional
    void getRequestsByIdFiltering() throws Exception {
        // Initialize the database
        requestRepository.saveAndFlush(request);

        Long id = request.getId();

        defaultRequestShouldBeFound("id.equals=" + id);
        defaultRequestShouldNotBeFound("id.notEquals=" + id);

        defaultRequestShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultRequestShouldNotBeFound("id.greaterThan=" + id);

        defaultRequestShouldBeFound("id.lessThanOrEqual=" + id);
        defaultRequestShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllRequestsByProductValueIsEqualToSomething() throws Exception {
        // Initialize the database
        requestRepository.saveAndFlush(request);

        // Get all the requestList where productValue equals to DEFAULT_PRODUCT_VALUE
        defaultRequestShouldBeFound("productValue.equals=" + DEFAULT_PRODUCT_VALUE);

        // Get all the requestList where productValue equals to UPDATED_PRODUCT_VALUE
        defaultRequestShouldNotBeFound("productValue.equals=" + UPDATED_PRODUCT_VALUE);
    }

    @Test
    @Transactional
    void getAllRequestsByProductValueIsNotEqualToSomething() throws Exception {
        // Initialize the database
        requestRepository.saveAndFlush(request);

        // Get all the requestList where productValue not equals to DEFAULT_PRODUCT_VALUE
        defaultRequestShouldNotBeFound("productValue.notEquals=" + DEFAULT_PRODUCT_VALUE);

        // Get all the requestList where productValue not equals to UPDATED_PRODUCT_VALUE
        defaultRequestShouldBeFound("productValue.notEquals=" + UPDATED_PRODUCT_VALUE);
    }

    @Test
    @Transactional
    void getAllRequestsByProductValueIsInShouldWork() throws Exception {
        // Initialize the database
        requestRepository.saveAndFlush(request);

        // Get all the requestList where productValue in DEFAULT_PRODUCT_VALUE or UPDATED_PRODUCT_VALUE
        defaultRequestShouldBeFound("productValue.in=" + DEFAULT_PRODUCT_VALUE + "," + UPDATED_PRODUCT_VALUE);

        // Get all the requestList where productValue equals to UPDATED_PRODUCT_VALUE
        defaultRequestShouldNotBeFound("productValue.in=" + UPDATED_PRODUCT_VALUE);
    }

    @Test
    @Transactional
    void getAllRequestsByProductValueIsNullOrNotNull() throws Exception {
        // Initialize the database
        requestRepository.saveAndFlush(request);

        // Get all the requestList where productValue is not null
        defaultRequestShouldBeFound("productValue.specified=true");

        // Get all the requestList where productValue is null
        defaultRequestShouldNotBeFound("productValue.specified=false");
    }

    @Test
    @Transactional
    void getAllRequestsByProductValueIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        requestRepository.saveAndFlush(request);

        // Get all the requestList where productValue is greater than or equal to DEFAULT_PRODUCT_VALUE
        defaultRequestShouldBeFound("productValue.greaterThanOrEqual=" + DEFAULT_PRODUCT_VALUE);

        // Get all the requestList where productValue is greater than or equal to UPDATED_PRODUCT_VALUE
        defaultRequestShouldNotBeFound("productValue.greaterThanOrEqual=" + UPDATED_PRODUCT_VALUE);
    }

    @Test
    @Transactional
    void getAllRequestsByProductValueIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        requestRepository.saveAndFlush(request);

        // Get all the requestList where productValue is less than or equal to DEFAULT_PRODUCT_VALUE
        defaultRequestShouldBeFound("productValue.lessThanOrEqual=" + DEFAULT_PRODUCT_VALUE);

        // Get all the requestList where productValue is less than or equal to SMALLER_PRODUCT_VALUE
        defaultRequestShouldNotBeFound("productValue.lessThanOrEqual=" + SMALLER_PRODUCT_VALUE);
    }

    @Test
    @Transactional
    void getAllRequestsByProductValueIsLessThanSomething() throws Exception {
        // Initialize the database
        requestRepository.saveAndFlush(request);

        // Get all the requestList where productValue is less than DEFAULT_PRODUCT_VALUE
        defaultRequestShouldNotBeFound("productValue.lessThan=" + DEFAULT_PRODUCT_VALUE);

        // Get all the requestList where productValue is less than UPDATED_PRODUCT_VALUE
        defaultRequestShouldBeFound("productValue.lessThan=" + UPDATED_PRODUCT_VALUE);
    }

    @Test
    @Transactional
    void getAllRequestsByProductValueIsGreaterThanSomething() throws Exception {
        // Initialize the database
        requestRepository.saveAndFlush(request);

        // Get all the requestList where productValue is greater than DEFAULT_PRODUCT_VALUE
        defaultRequestShouldNotBeFound("productValue.greaterThan=" + DEFAULT_PRODUCT_VALUE);

        // Get all the requestList where productValue is greater than SMALLER_PRODUCT_VALUE
        defaultRequestShouldBeFound("productValue.greaterThan=" + SMALLER_PRODUCT_VALUE);
    }

    @Test
    @Transactional
    void getAllRequestsByProductNameIsEqualToSomething() throws Exception {
        // Initialize the database
        requestRepository.saveAndFlush(request);

        // Get all the requestList where productName equals to DEFAULT_PRODUCT_NAME
        defaultRequestShouldBeFound("productName.equals=" + DEFAULT_PRODUCT_NAME);

        // Get all the requestList where productName equals to UPDATED_PRODUCT_NAME
        defaultRequestShouldNotBeFound("productName.equals=" + UPDATED_PRODUCT_NAME);
    }

    @Test
    @Transactional
    void getAllRequestsByProductNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        requestRepository.saveAndFlush(request);

        // Get all the requestList where productName not equals to DEFAULT_PRODUCT_NAME
        defaultRequestShouldNotBeFound("productName.notEquals=" + DEFAULT_PRODUCT_NAME);

        // Get all the requestList where productName not equals to UPDATED_PRODUCT_NAME
        defaultRequestShouldBeFound("productName.notEquals=" + UPDATED_PRODUCT_NAME);
    }

    @Test
    @Transactional
    void getAllRequestsByProductNameIsInShouldWork() throws Exception {
        // Initialize the database
        requestRepository.saveAndFlush(request);

        // Get all the requestList where productName in DEFAULT_PRODUCT_NAME or UPDATED_PRODUCT_NAME
        defaultRequestShouldBeFound("productName.in=" + DEFAULT_PRODUCT_NAME + "," + UPDATED_PRODUCT_NAME);

        // Get all the requestList where productName equals to UPDATED_PRODUCT_NAME
        defaultRequestShouldNotBeFound("productName.in=" + UPDATED_PRODUCT_NAME);
    }

    @Test
    @Transactional
    void getAllRequestsByProductNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        requestRepository.saveAndFlush(request);

        // Get all the requestList where productName is not null
        defaultRequestShouldBeFound("productName.specified=true");

        // Get all the requestList where productName is null
        defaultRequestShouldNotBeFound("productName.specified=false");
    }

    @Test
    @Transactional
    void getAllRequestsByProductNameContainsSomething() throws Exception {
        // Initialize the database
        requestRepository.saveAndFlush(request);

        // Get all the requestList where productName contains DEFAULT_PRODUCT_NAME
        defaultRequestShouldBeFound("productName.contains=" + DEFAULT_PRODUCT_NAME);

        // Get all the requestList where productName contains UPDATED_PRODUCT_NAME
        defaultRequestShouldNotBeFound("productName.contains=" + UPDATED_PRODUCT_NAME);
    }

    @Test
    @Transactional
    void getAllRequestsByProductNameNotContainsSomething() throws Exception {
        // Initialize the database
        requestRepository.saveAndFlush(request);

        // Get all the requestList where productName does not contain DEFAULT_PRODUCT_NAME
        defaultRequestShouldNotBeFound("productName.doesNotContain=" + DEFAULT_PRODUCT_NAME);

        // Get all the requestList where productName does not contain UPDATED_PRODUCT_NAME
        defaultRequestShouldBeFound("productName.doesNotContain=" + UPDATED_PRODUCT_NAME);
    }

    @Test
    @Transactional
    void getAllRequestsBySourceIsEqualToSomething() throws Exception {
        // Initialize the database
        requestRepository.saveAndFlush(request);

        // Get all the requestList where source equals to DEFAULT_SOURCE
        defaultRequestShouldBeFound("source.equals=" + DEFAULT_SOURCE);

        // Get all the requestList where source equals to UPDATED_SOURCE
        defaultRequestShouldNotBeFound("source.equals=" + UPDATED_SOURCE);
    }

    @Test
    @Transactional
    void getAllRequestsBySourceIsNotEqualToSomething() throws Exception {
        // Initialize the database
        requestRepository.saveAndFlush(request);

        // Get all the requestList where source not equals to DEFAULT_SOURCE
        defaultRequestShouldNotBeFound("source.notEquals=" + DEFAULT_SOURCE);

        // Get all the requestList where source not equals to UPDATED_SOURCE
        defaultRequestShouldBeFound("source.notEquals=" + UPDATED_SOURCE);
    }

    @Test
    @Transactional
    void getAllRequestsBySourceIsInShouldWork() throws Exception {
        // Initialize the database
        requestRepository.saveAndFlush(request);

        // Get all the requestList where source in DEFAULT_SOURCE or UPDATED_SOURCE
        defaultRequestShouldBeFound("source.in=" + DEFAULT_SOURCE + "," + UPDATED_SOURCE);

        // Get all the requestList where source equals to UPDATED_SOURCE
        defaultRequestShouldNotBeFound("source.in=" + UPDATED_SOURCE);
    }

    @Test
    @Transactional
    void getAllRequestsBySourceIsNullOrNotNull() throws Exception {
        // Initialize the database
        requestRepository.saveAndFlush(request);

        // Get all the requestList where source is not null
        defaultRequestShouldBeFound("source.specified=true");

        // Get all the requestList where source is null
        defaultRequestShouldNotBeFound("source.specified=false");
    }

    @Test
    @Transactional
    void getAllRequestsBySourceContainsSomething() throws Exception {
        // Initialize the database
        requestRepository.saveAndFlush(request);

        // Get all the requestList where source contains DEFAULT_SOURCE
        defaultRequestShouldBeFound("source.contains=" + DEFAULT_SOURCE);

        // Get all the requestList where source contains UPDATED_SOURCE
        defaultRequestShouldNotBeFound("source.contains=" + UPDATED_SOURCE);
    }

    @Test
    @Transactional
    void getAllRequestsBySourceNotContainsSomething() throws Exception {
        // Initialize the database
        requestRepository.saveAndFlush(request);

        // Get all the requestList where source does not contain DEFAULT_SOURCE
        defaultRequestShouldNotBeFound("source.doesNotContain=" + DEFAULT_SOURCE);

        // Get all the requestList where source does not contain UPDATED_SOURCE
        defaultRequestShouldBeFound("source.doesNotContain=" + UPDATED_SOURCE);
    }

    @Test
    @Transactional
    void getAllRequestsByDestinationIsEqualToSomething() throws Exception {
        // Initialize the database
        requestRepository.saveAndFlush(request);

        // Get all the requestList where destination equals to DEFAULT_DESTINATION
        defaultRequestShouldBeFound("destination.equals=" + DEFAULT_DESTINATION);

        // Get all the requestList where destination equals to UPDATED_DESTINATION
        defaultRequestShouldNotBeFound("destination.equals=" + UPDATED_DESTINATION);
    }

    @Test
    @Transactional
    void getAllRequestsByDestinationIsNotEqualToSomething() throws Exception {
        // Initialize the database
        requestRepository.saveAndFlush(request);

        // Get all the requestList where destination not equals to DEFAULT_DESTINATION
        defaultRequestShouldNotBeFound("destination.notEquals=" + DEFAULT_DESTINATION);

        // Get all the requestList where destination not equals to UPDATED_DESTINATION
        defaultRequestShouldBeFound("destination.notEquals=" + UPDATED_DESTINATION);
    }

    @Test
    @Transactional
    void getAllRequestsByDestinationIsInShouldWork() throws Exception {
        // Initialize the database
        requestRepository.saveAndFlush(request);

        // Get all the requestList where destination in DEFAULT_DESTINATION or UPDATED_DESTINATION
        defaultRequestShouldBeFound("destination.in=" + DEFAULT_DESTINATION + "," + UPDATED_DESTINATION);

        // Get all the requestList where destination equals to UPDATED_DESTINATION
        defaultRequestShouldNotBeFound("destination.in=" + UPDATED_DESTINATION);
    }

    @Test
    @Transactional
    void getAllRequestsByDestinationIsNullOrNotNull() throws Exception {
        // Initialize the database
        requestRepository.saveAndFlush(request);

        // Get all the requestList where destination is not null
        defaultRequestShouldBeFound("destination.specified=true");

        // Get all the requestList where destination is null
        defaultRequestShouldNotBeFound("destination.specified=false");
    }

    @Test
    @Transactional
    void getAllRequestsByDestinationContainsSomething() throws Exception {
        // Initialize the database
        requestRepository.saveAndFlush(request);

        // Get all the requestList where destination contains DEFAULT_DESTINATION
        defaultRequestShouldBeFound("destination.contains=" + DEFAULT_DESTINATION);

        // Get all the requestList where destination contains UPDATED_DESTINATION
        defaultRequestShouldNotBeFound("destination.contains=" + UPDATED_DESTINATION);
    }

    @Test
    @Transactional
    void getAllRequestsByDestinationNotContainsSomething() throws Exception {
        // Initialize the database
        requestRepository.saveAndFlush(request);

        // Get all the requestList where destination does not contain DEFAULT_DESTINATION
        defaultRequestShouldNotBeFound("destination.doesNotContain=" + DEFAULT_DESTINATION);

        // Get all the requestList where destination does not contain UPDATED_DESTINATION
        defaultRequestShouldBeFound("destination.doesNotContain=" + UPDATED_DESTINATION);
    }

    @Test
    @Transactional
    void getAllRequestsByDestinationContactIsEqualToSomething() throws Exception {
        // Initialize the database
        requestRepository.saveAndFlush(request);

        // Get all the requestList where destinationContact equals to DEFAULT_DESTINATION_CONTACT
        defaultRequestShouldBeFound("destinationContact.equals=" + DEFAULT_DESTINATION_CONTACT);

        // Get all the requestList where destinationContact equals to UPDATED_DESTINATION_CONTACT
        defaultRequestShouldNotBeFound("destinationContact.equals=" + UPDATED_DESTINATION_CONTACT);
    }

    @Test
    @Transactional
    void getAllRequestsByDestinationContactIsNotEqualToSomething() throws Exception {
        // Initialize the database
        requestRepository.saveAndFlush(request);

        // Get all the requestList where destinationContact not equals to DEFAULT_DESTINATION_CONTACT
        defaultRequestShouldNotBeFound("destinationContact.notEquals=" + DEFAULT_DESTINATION_CONTACT);

        // Get all the requestList where destinationContact not equals to UPDATED_DESTINATION_CONTACT
        defaultRequestShouldBeFound("destinationContact.notEquals=" + UPDATED_DESTINATION_CONTACT);
    }

    @Test
    @Transactional
    void getAllRequestsByDestinationContactIsInShouldWork() throws Exception {
        // Initialize the database
        requestRepository.saveAndFlush(request);

        // Get all the requestList where destinationContact in DEFAULT_DESTINATION_CONTACT or UPDATED_DESTINATION_CONTACT
        defaultRequestShouldBeFound("destinationContact.in=" + DEFAULT_DESTINATION_CONTACT + "," + UPDATED_DESTINATION_CONTACT);

        // Get all the requestList where destinationContact equals to UPDATED_DESTINATION_CONTACT
        defaultRequestShouldNotBeFound("destinationContact.in=" + UPDATED_DESTINATION_CONTACT);
    }

    @Test
    @Transactional
    void getAllRequestsByDestinationContactIsNullOrNotNull() throws Exception {
        // Initialize the database
        requestRepository.saveAndFlush(request);

        // Get all the requestList where destinationContact is not null
        defaultRequestShouldBeFound("destinationContact.specified=true");

        // Get all the requestList where destinationContact is null
        defaultRequestShouldNotBeFound("destinationContact.specified=false");
    }

    @Test
    @Transactional
    void getAllRequestsByDestinationContactContainsSomething() throws Exception {
        // Initialize the database
        requestRepository.saveAndFlush(request);

        // Get all the requestList where destinationContact contains DEFAULT_DESTINATION_CONTACT
        defaultRequestShouldBeFound("destinationContact.contains=" + DEFAULT_DESTINATION_CONTACT);

        // Get all the requestList where destinationContact contains UPDATED_DESTINATION_CONTACT
        defaultRequestShouldNotBeFound("destinationContact.contains=" + UPDATED_DESTINATION_CONTACT);
    }

    @Test
    @Transactional
    void getAllRequestsByDestinationContactNotContainsSomething() throws Exception {
        // Initialize the database
        requestRepository.saveAndFlush(request);

        // Get all the requestList where destinationContact does not contain DEFAULT_DESTINATION_CONTACT
        defaultRequestShouldNotBeFound("destinationContact.doesNotContain=" + DEFAULT_DESTINATION_CONTACT);

        // Get all the requestList where destinationContact does not contain UPDATED_DESTINATION_CONTACT
        defaultRequestShouldBeFound("destinationContact.doesNotContain=" + UPDATED_DESTINATION_CONTACT);
    }

    @Test
    @Transactional
    void getAllRequestsByInitDateIsEqualToSomething() throws Exception {
        // Initialize the database
        requestRepository.saveAndFlush(request);

        // Get all the requestList where initDate equals to DEFAULT_INIT_DATE
        defaultRequestShouldBeFound("initDate.equals=" + DEFAULT_INIT_DATE);

        // Get all the requestList where initDate equals to UPDATED_INIT_DATE
        defaultRequestShouldNotBeFound("initDate.equals=" + UPDATED_INIT_DATE);
    }

    @Test
    @Transactional
    void getAllRequestsByInitDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        requestRepository.saveAndFlush(request);

        // Get all the requestList where initDate not equals to DEFAULT_INIT_DATE
        defaultRequestShouldNotBeFound("initDate.notEquals=" + DEFAULT_INIT_DATE);

        // Get all the requestList where initDate not equals to UPDATED_INIT_DATE
        defaultRequestShouldBeFound("initDate.notEquals=" + UPDATED_INIT_DATE);
    }

    @Test
    @Transactional
    void getAllRequestsByInitDateIsInShouldWork() throws Exception {
        // Initialize the database
        requestRepository.saveAndFlush(request);

        // Get all the requestList where initDate in DEFAULT_INIT_DATE or UPDATED_INIT_DATE
        defaultRequestShouldBeFound("initDate.in=" + DEFAULT_INIT_DATE + "," + UPDATED_INIT_DATE);

        // Get all the requestList where initDate equals to UPDATED_INIT_DATE
        defaultRequestShouldNotBeFound("initDate.in=" + UPDATED_INIT_DATE);
    }

    @Test
    @Transactional
    void getAllRequestsByInitDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        requestRepository.saveAndFlush(request);

        // Get all the requestList where initDate is not null
        defaultRequestShouldBeFound("initDate.specified=true");

        // Get all the requestList where initDate is null
        defaultRequestShouldNotBeFound("initDate.specified=false");
    }

    @Test
    @Transactional
    void getAllRequestsByExpirationDateIsEqualToSomething() throws Exception {
        // Initialize the database
        requestRepository.saveAndFlush(request);

        // Get all the requestList where expirationDate equals to DEFAULT_EXPIRATION_DATE
        defaultRequestShouldBeFound("expirationDate.equals=" + DEFAULT_EXPIRATION_DATE);

        // Get all the requestList where expirationDate equals to UPDATED_EXPIRATION_DATE
        defaultRequestShouldNotBeFound("expirationDate.equals=" + UPDATED_EXPIRATION_DATE);
    }

    @Test
    @Transactional
    void getAllRequestsByExpirationDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        requestRepository.saveAndFlush(request);

        // Get all the requestList where expirationDate not equals to DEFAULT_EXPIRATION_DATE
        defaultRequestShouldNotBeFound("expirationDate.notEquals=" + DEFAULT_EXPIRATION_DATE);

        // Get all the requestList where expirationDate not equals to UPDATED_EXPIRATION_DATE
        defaultRequestShouldBeFound("expirationDate.notEquals=" + UPDATED_EXPIRATION_DATE);
    }

    @Test
    @Transactional
    void getAllRequestsByExpirationDateIsInShouldWork() throws Exception {
        // Initialize the database
        requestRepository.saveAndFlush(request);

        // Get all the requestList where expirationDate in DEFAULT_EXPIRATION_DATE or UPDATED_EXPIRATION_DATE
        defaultRequestShouldBeFound("expirationDate.in=" + DEFAULT_EXPIRATION_DATE + "," + UPDATED_EXPIRATION_DATE);

        // Get all the requestList where expirationDate equals to UPDATED_EXPIRATION_DATE
        defaultRequestShouldNotBeFound("expirationDate.in=" + UPDATED_EXPIRATION_DATE);
    }

    @Test
    @Transactional
    void getAllRequestsByExpirationDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        requestRepository.saveAndFlush(request);

        // Get all the requestList where expirationDate is not null
        defaultRequestShouldBeFound("expirationDate.specified=true");

        // Get all the requestList where expirationDate is null
        defaultRequestShouldNotBeFound("expirationDate.specified=false");
    }

    @Test
    @Transactional
    void getAllRequestsByDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        requestRepository.saveAndFlush(request);

        // Get all the requestList where description equals to DEFAULT_DESCRIPTION
        defaultRequestShouldBeFound("description.equals=" + DEFAULT_DESCRIPTION);

        // Get all the requestList where description equals to UPDATED_DESCRIPTION
        defaultRequestShouldNotBeFound("description.equals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllRequestsByDescriptionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        requestRepository.saveAndFlush(request);

        // Get all the requestList where description not equals to DEFAULT_DESCRIPTION
        defaultRequestShouldNotBeFound("description.notEquals=" + DEFAULT_DESCRIPTION);

        // Get all the requestList where description not equals to UPDATED_DESCRIPTION
        defaultRequestShouldBeFound("description.notEquals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllRequestsByDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        requestRepository.saveAndFlush(request);

        // Get all the requestList where description in DEFAULT_DESCRIPTION or UPDATED_DESCRIPTION
        defaultRequestShouldBeFound("description.in=" + DEFAULT_DESCRIPTION + "," + UPDATED_DESCRIPTION);

        // Get all the requestList where description equals to UPDATED_DESCRIPTION
        defaultRequestShouldNotBeFound("description.in=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllRequestsByDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        requestRepository.saveAndFlush(request);

        // Get all the requestList where description is not null
        defaultRequestShouldBeFound("description.specified=true");

        // Get all the requestList where description is null
        defaultRequestShouldNotBeFound("description.specified=false");
    }

    @Test
    @Transactional
    void getAllRequestsByDescriptionContainsSomething() throws Exception {
        // Initialize the database
        requestRepository.saveAndFlush(request);

        // Get all the requestList where description contains DEFAULT_DESCRIPTION
        defaultRequestShouldBeFound("description.contains=" + DEFAULT_DESCRIPTION);

        // Get all the requestList where description contains UPDATED_DESCRIPTION
        defaultRequestShouldNotBeFound("description.contains=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllRequestsByDescriptionNotContainsSomething() throws Exception {
        // Initialize the database
        requestRepository.saveAndFlush(request);

        // Get all the requestList where description does not contain DEFAULT_DESCRIPTION
        defaultRequestShouldNotBeFound("description.doesNotContain=" + DEFAULT_DESCRIPTION);

        // Get all the requestList where description does not contain UPDATED_DESCRIPTION
        defaultRequestShouldBeFound("description.doesNotContain=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllRequestsBySpecialCharacteristicsIsEqualToSomething() throws Exception {
        // Initialize the database
        requestRepository.saveAndFlush(request);

        // Get all the requestList where specialCharacteristics equals to DEFAULT_SPECIAL_CHARACTERISTICS
        defaultRequestShouldBeFound("specialCharacteristics.equals=" + DEFAULT_SPECIAL_CHARACTERISTICS);

        // Get all the requestList where specialCharacteristics equals to UPDATED_SPECIAL_CHARACTERISTICS
        defaultRequestShouldNotBeFound("specialCharacteristics.equals=" + UPDATED_SPECIAL_CHARACTERISTICS);
    }

    @Test
    @Transactional
    void getAllRequestsBySpecialCharacteristicsIsNotEqualToSomething() throws Exception {
        // Initialize the database
        requestRepository.saveAndFlush(request);

        // Get all the requestList where specialCharacteristics not equals to DEFAULT_SPECIAL_CHARACTERISTICS
        defaultRequestShouldNotBeFound("specialCharacteristics.notEquals=" + DEFAULT_SPECIAL_CHARACTERISTICS);

        // Get all the requestList where specialCharacteristics not equals to UPDATED_SPECIAL_CHARACTERISTICS
        defaultRequestShouldBeFound("specialCharacteristics.notEquals=" + UPDATED_SPECIAL_CHARACTERISTICS);
    }

    @Test
    @Transactional
    void getAllRequestsBySpecialCharacteristicsIsInShouldWork() throws Exception {
        // Initialize the database
        requestRepository.saveAndFlush(request);

        // Get all the requestList where specialCharacteristics in DEFAULT_SPECIAL_CHARACTERISTICS or UPDATED_SPECIAL_CHARACTERISTICS
        defaultRequestShouldBeFound("specialCharacteristics.in=" + DEFAULT_SPECIAL_CHARACTERISTICS + "," + UPDATED_SPECIAL_CHARACTERISTICS);

        // Get all the requestList where specialCharacteristics equals to UPDATED_SPECIAL_CHARACTERISTICS
        defaultRequestShouldNotBeFound("specialCharacteristics.in=" + UPDATED_SPECIAL_CHARACTERISTICS);
    }

    @Test
    @Transactional
    void getAllRequestsBySpecialCharacteristicsIsNullOrNotNull() throws Exception {
        // Initialize the database
        requestRepository.saveAndFlush(request);

        // Get all the requestList where specialCharacteristics is not null
        defaultRequestShouldBeFound("specialCharacteristics.specified=true");

        // Get all the requestList where specialCharacteristics is null
        defaultRequestShouldNotBeFound("specialCharacteristics.specified=false");
    }

    @Test
    @Transactional
    void getAllRequestsBySpecialCharacteristicsContainsSomething() throws Exception {
        // Initialize the database
        requestRepository.saveAndFlush(request);

        // Get all the requestList where specialCharacteristics contains DEFAULT_SPECIAL_CHARACTERISTICS
        defaultRequestShouldBeFound("specialCharacteristics.contains=" + DEFAULT_SPECIAL_CHARACTERISTICS);

        // Get all the requestList where specialCharacteristics contains UPDATED_SPECIAL_CHARACTERISTICS
        defaultRequestShouldNotBeFound("specialCharacteristics.contains=" + UPDATED_SPECIAL_CHARACTERISTICS);
    }

    @Test
    @Transactional
    void getAllRequestsBySpecialCharacteristicsNotContainsSomething() throws Exception {
        // Initialize the database
        requestRepository.saveAndFlush(request);

        // Get all the requestList where specialCharacteristics does not contain DEFAULT_SPECIAL_CHARACTERISTICS
        defaultRequestShouldNotBeFound("specialCharacteristics.doesNotContain=" + DEFAULT_SPECIAL_CHARACTERISTICS);

        // Get all the requestList where specialCharacteristics does not contain UPDATED_SPECIAL_CHARACTERISTICS
        defaultRequestShouldBeFound("specialCharacteristics.doesNotContain=" + UPDATED_SPECIAL_CHARACTERISTICS);
    }

    @Test
    @Transactional
    void getAllRequestsByProductWeightIsEqualToSomething() throws Exception {
        // Initialize the database
        requestRepository.saveAndFlush(request);

        // Get all the requestList where productWeight equals to DEFAULT_PRODUCT_WEIGHT
        defaultRequestShouldBeFound("productWeight.equals=" + DEFAULT_PRODUCT_WEIGHT);

        // Get all the requestList where productWeight equals to UPDATED_PRODUCT_WEIGHT
        defaultRequestShouldNotBeFound("productWeight.equals=" + UPDATED_PRODUCT_WEIGHT);
    }

    @Test
    @Transactional
    void getAllRequestsByProductWeightIsNotEqualToSomething() throws Exception {
        // Initialize the database
        requestRepository.saveAndFlush(request);

        // Get all the requestList where productWeight not equals to DEFAULT_PRODUCT_WEIGHT
        defaultRequestShouldNotBeFound("productWeight.notEquals=" + DEFAULT_PRODUCT_WEIGHT);

        // Get all the requestList where productWeight not equals to UPDATED_PRODUCT_WEIGHT
        defaultRequestShouldBeFound("productWeight.notEquals=" + UPDATED_PRODUCT_WEIGHT);
    }

    @Test
    @Transactional
    void getAllRequestsByProductWeightIsInShouldWork() throws Exception {
        // Initialize the database
        requestRepository.saveAndFlush(request);

        // Get all the requestList where productWeight in DEFAULT_PRODUCT_WEIGHT or UPDATED_PRODUCT_WEIGHT
        defaultRequestShouldBeFound("productWeight.in=" + DEFAULT_PRODUCT_WEIGHT + "," + UPDATED_PRODUCT_WEIGHT);

        // Get all the requestList where productWeight equals to UPDATED_PRODUCT_WEIGHT
        defaultRequestShouldNotBeFound("productWeight.in=" + UPDATED_PRODUCT_WEIGHT);
    }

    @Test
    @Transactional
    void getAllRequestsByProductWeightIsNullOrNotNull() throws Exception {
        // Initialize the database
        requestRepository.saveAndFlush(request);

        // Get all the requestList where productWeight is not null
        defaultRequestShouldBeFound("productWeight.specified=true");

        // Get all the requestList where productWeight is null
        defaultRequestShouldNotBeFound("productWeight.specified=false");
    }

    @Test
    @Transactional
    void getAllRequestsByProductWeightIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        requestRepository.saveAndFlush(request);

        // Get all the requestList where productWeight is greater than or equal to DEFAULT_PRODUCT_WEIGHT
        defaultRequestShouldBeFound("productWeight.greaterThanOrEqual=" + DEFAULT_PRODUCT_WEIGHT);

        // Get all the requestList where productWeight is greater than or equal to UPDATED_PRODUCT_WEIGHT
        defaultRequestShouldNotBeFound("productWeight.greaterThanOrEqual=" + UPDATED_PRODUCT_WEIGHT);
    }

    @Test
    @Transactional
    void getAllRequestsByProductWeightIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        requestRepository.saveAndFlush(request);

        // Get all the requestList where productWeight is less than or equal to DEFAULT_PRODUCT_WEIGHT
        defaultRequestShouldBeFound("productWeight.lessThanOrEqual=" + DEFAULT_PRODUCT_WEIGHT);

        // Get all the requestList where productWeight is less than or equal to SMALLER_PRODUCT_WEIGHT
        defaultRequestShouldNotBeFound("productWeight.lessThanOrEqual=" + SMALLER_PRODUCT_WEIGHT);
    }

    @Test
    @Transactional
    void getAllRequestsByProductWeightIsLessThanSomething() throws Exception {
        // Initialize the database
        requestRepository.saveAndFlush(request);

        // Get all the requestList where productWeight is less than DEFAULT_PRODUCT_WEIGHT
        defaultRequestShouldNotBeFound("productWeight.lessThan=" + DEFAULT_PRODUCT_WEIGHT);

        // Get all the requestList where productWeight is less than UPDATED_PRODUCT_WEIGHT
        defaultRequestShouldBeFound("productWeight.lessThan=" + UPDATED_PRODUCT_WEIGHT);
    }

    @Test
    @Transactional
    void getAllRequestsByProductWeightIsGreaterThanSomething() throws Exception {
        // Initialize the database
        requestRepository.saveAndFlush(request);

        // Get all the requestList where productWeight is greater than DEFAULT_PRODUCT_WEIGHT
        defaultRequestShouldNotBeFound("productWeight.greaterThan=" + DEFAULT_PRODUCT_WEIGHT);

        // Get all the requestList where productWeight is greater than SMALLER_PRODUCT_WEIGHT
        defaultRequestShouldBeFound("productWeight.greaterThan=" + SMALLER_PRODUCT_WEIGHT);
    }

    @Test
    @Transactional
    void getAllRequestsByStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        requestRepository.saveAndFlush(request);

        // Get all the requestList where status equals to DEFAULT_STATUS
        defaultRequestShouldBeFound("status.equals=" + DEFAULT_STATUS);

        // Get all the requestList where status equals to UPDATED_STATUS
        defaultRequestShouldNotBeFound("status.equals=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllRequestsByStatusIsNotEqualToSomething() throws Exception {
        // Initialize the database
        requestRepository.saveAndFlush(request);

        // Get all the requestList where status not equals to DEFAULT_STATUS
        defaultRequestShouldNotBeFound("status.notEquals=" + DEFAULT_STATUS);

        // Get all the requestList where status not equals to UPDATED_STATUS
        defaultRequestShouldBeFound("status.notEquals=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllRequestsByStatusIsInShouldWork() throws Exception {
        // Initialize the database
        requestRepository.saveAndFlush(request);

        // Get all the requestList where status in DEFAULT_STATUS or UPDATED_STATUS
        defaultRequestShouldBeFound("status.in=" + DEFAULT_STATUS + "," + UPDATED_STATUS);

        // Get all the requestList where status equals to UPDATED_STATUS
        defaultRequestShouldNotBeFound("status.in=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllRequestsByStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        requestRepository.saveAndFlush(request);

        // Get all the requestList where status is not null
        defaultRequestShouldBeFound("status.specified=true");

        // Get all the requestList where status is null
        defaultRequestShouldNotBeFound("status.specified=false");
    }

    @Test
    @Transactional
    void getAllRequestsByEstimatedDateIsEqualToSomething() throws Exception {
        // Initialize the database
        requestRepository.saveAndFlush(request);

        // Get all the requestList where estimatedDate equals to DEFAULT_ESTIMATED_DATE
        defaultRequestShouldBeFound("estimatedDate.equals=" + DEFAULT_ESTIMATED_DATE);

        // Get all the requestList where estimatedDate equals to UPDATED_ESTIMATED_DATE
        defaultRequestShouldNotBeFound("estimatedDate.equals=" + UPDATED_ESTIMATED_DATE);
    }

    @Test
    @Transactional
    void getAllRequestsByEstimatedDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        requestRepository.saveAndFlush(request);

        // Get all the requestList where estimatedDate not equals to DEFAULT_ESTIMATED_DATE
        defaultRequestShouldNotBeFound("estimatedDate.notEquals=" + DEFAULT_ESTIMATED_DATE);

        // Get all the requestList where estimatedDate not equals to UPDATED_ESTIMATED_DATE
        defaultRequestShouldBeFound("estimatedDate.notEquals=" + UPDATED_ESTIMATED_DATE);
    }

    @Test
    @Transactional
    void getAllRequestsByEstimatedDateIsInShouldWork() throws Exception {
        // Initialize the database
        requestRepository.saveAndFlush(request);

        // Get all the requestList where estimatedDate in DEFAULT_ESTIMATED_DATE or UPDATED_ESTIMATED_DATE
        defaultRequestShouldBeFound("estimatedDate.in=" + DEFAULT_ESTIMATED_DATE + "," + UPDATED_ESTIMATED_DATE);

        // Get all the requestList where estimatedDate equals to UPDATED_ESTIMATED_DATE
        defaultRequestShouldNotBeFound("estimatedDate.in=" + UPDATED_ESTIMATED_DATE);
    }

    @Test
    @Transactional
    void getAllRequestsByEstimatedDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        requestRepository.saveAndFlush(request);

        // Get all the requestList where estimatedDate is not null
        defaultRequestShouldBeFound("estimatedDate.specified=true");

        // Get all the requestList where estimatedDate is null
        defaultRequestShouldNotBeFound("estimatedDate.specified=false");
    }

    @Test
    @Transactional
    void getAllRequestsByDeliveryTimeIsEqualToSomething() throws Exception {
        // Initialize the database
        requestRepository.saveAndFlush(request);

        // Get all the requestList where deliveryTime equals to DEFAULT_DELIVERY_TIME
        defaultRequestShouldBeFound("deliveryTime.equals=" + DEFAULT_DELIVERY_TIME);

        // Get all the requestList where deliveryTime equals to UPDATED_DELIVERY_TIME
        defaultRequestShouldNotBeFound("deliveryTime.equals=" + UPDATED_DELIVERY_TIME);
    }

    @Test
    @Transactional
    void getAllRequestsByDeliveryTimeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        requestRepository.saveAndFlush(request);

        // Get all the requestList where deliveryTime not equals to DEFAULT_DELIVERY_TIME
        defaultRequestShouldNotBeFound("deliveryTime.notEquals=" + DEFAULT_DELIVERY_TIME);

        // Get all the requestList where deliveryTime not equals to UPDATED_DELIVERY_TIME
        defaultRequestShouldBeFound("deliveryTime.notEquals=" + UPDATED_DELIVERY_TIME);
    }

    @Test
    @Transactional
    void getAllRequestsByDeliveryTimeIsInShouldWork() throws Exception {
        // Initialize the database
        requestRepository.saveAndFlush(request);

        // Get all the requestList where deliveryTime in DEFAULT_DELIVERY_TIME or UPDATED_DELIVERY_TIME
        defaultRequestShouldBeFound("deliveryTime.in=" + DEFAULT_DELIVERY_TIME + "," + UPDATED_DELIVERY_TIME);

        // Get all the requestList where deliveryTime equals to UPDATED_DELIVERY_TIME
        defaultRequestShouldNotBeFound("deliveryTime.in=" + UPDATED_DELIVERY_TIME);
    }

    @Test
    @Transactional
    void getAllRequestsByDeliveryTimeIsNullOrNotNull() throws Exception {
        // Initialize the database
        requestRepository.saveAndFlush(request);

        // Get all the requestList where deliveryTime is not null
        defaultRequestShouldBeFound("deliveryTime.specified=true");

        // Get all the requestList where deliveryTime is null
        defaultRequestShouldNotBeFound("deliveryTime.specified=false");
    }

    @Test
    @Transactional
    void getAllRequestsByShippingCostsIsEqualToSomething() throws Exception {
        // Initialize the database
        requestRepository.saveAndFlush(request);

        // Get all the requestList where shippingCosts equals to DEFAULT_SHIPPING_COSTS
        defaultRequestShouldBeFound("shippingCosts.equals=" + DEFAULT_SHIPPING_COSTS);

        // Get all the requestList where shippingCosts equals to UPDATED_SHIPPING_COSTS
        defaultRequestShouldNotBeFound("shippingCosts.equals=" + UPDATED_SHIPPING_COSTS);
    }

    @Test
    @Transactional
    void getAllRequestsByShippingCostsIsNotEqualToSomething() throws Exception {
        // Initialize the database
        requestRepository.saveAndFlush(request);

        // Get all the requestList where shippingCosts not equals to DEFAULT_SHIPPING_COSTS
        defaultRequestShouldNotBeFound("shippingCosts.notEquals=" + DEFAULT_SHIPPING_COSTS);

        // Get all the requestList where shippingCosts not equals to UPDATED_SHIPPING_COSTS
        defaultRequestShouldBeFound("shippingCosts.notEquals=" + UPDATED_SHIPPING_COSTS);
    }

    @Test
    @Transactional
    void getAllRequestsByShippingCostsIsInShouldWork() throws Exception {
        // Initialize the database
        requestRepository.saveAndFlush(request);

        // Get all the requestList where shippingCosts in DEFAULT_SHIPPING_COSTS or UPDATED_SHIPPING_COSTS
        defaultRequestShouldBeFound("shippingCosts.in=" + DEFAULT_SHIPPING_COSTS + "," + UPDATED_SHIPPING_COSTS);

        // Get all the requestList where shippingCosts equals to UPDATED_SHIPPING_COSTS
        defaultRequestShouldNotBeFound("shippingCosts.in=" + UPDATED_SHIPPING_COSTS);
    }

    @Test
    @Transactional
    void getAllRequestsByShippingCostsIsNullOrNotNull() throws Exception {
        // Initialize the database
        requestRepository.saveAndFlush(request);

        // Get all the requestList where shippingCosts is not null
        defaultRequestShouldBeFound("shippingCosts.specified=true");

        // Get all the requestList where shippingCosts is null
        defaultRequestShouldNotBeFound("shippingCosts.specified=false");
    }

    @Test
    @Transactional
    void getAllRequestsByShippingCostsIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        requestRepository.saveAndFlush(request);

        // Get all the requestList where shippingCosts is greater than or equal to DEFAULT_SHIPPING_COSTS
        defaultRequestShouldBeFound("shippingCosts.greaterThanOrEqual=" + DEFAULT_SHIPPING_COSTS);

        // Get all the requestList where shippingCosts is greater than or equal to UPDATED_SHIPPING_COSTS
        defaultRequestShouldNotBeFound("shippingCosts.greaterThanOrEqual=" + UPDATED_SHIPPING_COSTS);
    }

    @Test
    @Transactional
    void getAllRequestsByShippingCostsIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        requestRepository.saveAndFlush(request);

        // Get all the requestList where shippingCosts is less than or equal to DEFAULT_SHIPPING_COSTS
        defaultRequestShouldBeFound("shippingCosts.lessThanOrEqual=" + DEFAULT_SHIPPING_COSTS);

        // Get all the requestList where shippingCosts is less than or equal to SMALLER_SHIPPING_COSTS
        defaultRequestShouldNotBeFound("shippingCosts.lessThanOrEqual=" + SMALLER_SHIPPING_COSTS);
    }

    @Test
    @Transactional
    void getAllRequestsByShippingCostsIsLessThanSomething() throws Exception {
        // Initialize the database
        requestRepository.saveAndFlush(request);

        // Get all the requestList where shippingCosts is less than DEFAULT_SHIPPING_COSTS
        defaultRequestShouldNotBeFound("shippingCosts.lessThan=" + DEFAULT_SHIPPING_COSTS);

        // Get all the requestList where shippingCosts is less than UPDATED_SHIPPING_COSTS
        defaultRequestShouldBeFound("shippingCosts.lessThan=" + UPDATED_SHIPPING_COSTS);
    }

    @Test
    @Transactional
    void getAllRequestsByShippingCostsIsGreaterThanSomething() throws Exception {
        // Initialize the database
        requestRepository.saveAndFlush(request);

        // Get all the requestList where shippingCosts is greater than DEFAULT_SHIPPING_COSTS
        defaultRequestShouldNotBeFound("shippingCosts.greaterThan=" + DEFAULT_SHIPPING_COSTS);

        // Get all the requestList where shippingCosts is greater than SMALLER_SHIPPING_COSTS
        defaultRequestShouldBeFound("shippingCosts.greaterThan=" + SMALLER_SHIPPING_COSTS);
    }

    @Test
    @Transactional
    void getAllRequestsByRatingIsEqualToSomething() throws Exception {
        // Initialize the database
        requestRepository.saveAndFlush(request);

        // Get all the requestList where rating equals to DEFAULT_RATING
        defaultRequestShouldBeFound("rating.equals=" + DEFAULT_RATING);

        // Get all the requestList where rating equals to UPDATED_RATING
        defaultRequestShouldNotBeFound("rating.equals=" + UPDATED_RATING);
    }

    @Test
    @Transactional
    void getAllRequestsByRatingIsNotEqualToSomething() throws Exception {
        // Initialize the database
        requestRepository.saveAndFlush(request);

        // Get all the requestList where rating not equals to DEFAULT_RATING
        defaultRequestShouldNotBeFound("rating.notEquals=" + DEFAULT_RATING);

        // Get all the requestList where rating not equals to UPDATED_RATING
        defaultRequestShouldBeFound("rating.notEquals=" + UPDATED_RATING);
    }

    @Test
    @Transactional
    void getAllRequestsByRatingIsInShouldWork() throws Exception {
        // Initialize the database
        requestRepository.saveAndFlush(request);

        // Get all the requestList where rating in DEFAULT_RATING or UPDATED_RATING
        defaultRequestShouldBeFound("rating.in=" + DEFAULT_RATING + "," + UPDATED_RATING);

        // Get all the requestList where rating equals to UPDATED_RATING
        defaultRequestShouldNotBeFound("rating.in=" + UPDATED_RATING);
    }

    @Test
    @Transactional
    void getAllRequestsByRatingIsNullOrNotNull() throws Exception {
        // Initialize the database
        requestRepository.saveAndFlush(request);

        // Get all the requestList where rating is not null
        defaultRequestShouldBeFound("rating.specified=true");

        // Get all the requestList where rating is null
        defaultRequestShouldNotBeFound("rating.specified=false");
    }

    @Test
    @Transactional
    void getAllRequestsByRatingIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        requestRepository.saveAndFlush(request);

        // Get all the requestList where rating is greater than or equal to DEFAULT_RATING
        defaultRequestShouldBeFound("rating.greaterThanOrEqual=" + DEFAULT_RATING);

        // Get all the requestList where rating is greater than or equal to UPDATED_RATING
        defaultRequestShouldNotBeFound("rating.greaterThanOrEqual=" + UPDATED_RATING);
    }

    @Test
    @Transactional
    void getAllRequestsByRatingIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        requestRepository.saveAndFlush(request);

        // Get all the requestList where rating is less than or equal to DEFAULT_RATING
        defaultRequestShouldBeFound("rating.lessThanOrEqual=" + DEFAULT_RATING);

        // Get all the requestList where rating is less than or equal to SMALLER_RATING
        defaultRequestShouldNotBeFound("rating.lessThanOrEqual=" + SMALLER_RATING);
    }

    @Test
    @Transactional
    void getAllRequestsByRatingIsLessThanSomething() throws Exception {
        // Initialize the database
        requestRepository.saveAndFlush(request);

        // Get all the requestList where rating is less than DEFAULT_RATING
        defaultRequestShouldNotBeFound("rating.lessThan=" + DEFAULT_RATING);

        // Get all the requestList where rating is less than UPDATED_RATING
        defaultRequestShouldBeFound("rating.lessThan=" + UPDATED_RATING);
    }

    @Test
    @Transactional
    void getAllRequestsByRatingIsGreaterThanSomething() throws Exception {
        // Initialize the database
        requestRepository.saveAndFlush(request);

        // Get all the requestList where rating is greater than DEFAULT_RATING
        defaultRequestShouldNotBeFound("rating.greaterThan=" + DEFAULT_RATING);

        // Get all the requestList where rating is greater than SMALLER_RATING
        defaultRequestShouldBeFound("rating.greaterThan=" + SMALLER_RATING);
    }

    @Test
    @Transactional
    void getAllRequestsByDimensionsIsEqualToSomething() throws Exception {
        // Initialize the database
        requestRepository.saveAndFlush(request);
        Dimensions dimensions = DimensionsResourceIT.createEntity(em);
        em.persist(dimensions);
        em.flush();
        request.setDimensions(dimensions);
        requestRepository.saveAndFlush(request);
        Long dimensionsId = dimensions.getId();

        // Get all the requestList where dimensions equals to dimensionsId
        defaultRequestShouldBeFound("dimensionsId.equals=" + dimensionsId);

        // Get all the requestList where dimensions equals to (dimensionsId + 1)
        defaultRequestShouldNotBeFound("dimensionsId.equals=" + (dimensionsId + 1));
    }

    @Test
    @Transactional
    void getAllRequestsByRouteIsEqualToSomething() throws Exception {
        // Initialize the database
        requestRepository.saveAndFlush(request);
        Route route = RouteResourceIT.createEntity(em);
        em.persist(route);
        em.flush();
        request.setRoute(route);
        requestRepository.saveAndFlush(request);
        Long routeId = route.getId();

        // Get all the requestList where route equals to routeId
        defaultRequestShouldBeFound("routeId.equals=" + routeId);

        // Get all the requestList where route equals to (routeId + 1)
        defaultRequestShouldNotBeFound("routeId.equals=" + (routeId + 1));
    }

    @Test
    @Transactional
    void getAllRequestsByProducerIsEqualToSomething() throws Exception {
        // Initialize the database
        requestRepository.saveAndFlush(request);
        Producer producer = ProducerResourceIT.createEntity(em);
        em.persist(producer);
        em.flush();
        request.setProducer(producer);
        requestRepository.saveAndFlush(request);
        Long producerId = producer.getId();

        // Get all the requestList where producer equals to producerId
        defaultRequestShouldBeFound("producerId.equals=" + producerId);

        // Get all the requestList where producer equals to (producerId + 1)
        defaultRequestShouldNotBeFound("producerId.equals=" + (producerId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultRequestShouldBeFound(String filter) throws Exception {
        restRequestMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(request.getId().intValue())))
            .andExpect(jsonPath("$.[*].productValue").value(hasItem(DEFAULT_PRODUCT_VALUE.doubleValue())))
            .andExpect(jsonPath("$.[*].productName").value(hasItem(DEFAULT_PRODUCT_NAME)))
            .andExpect(jsonPath("$.[*].source").value(hasItem(DEFAULT_SOURCE)))
            .andExpect(jsonPath("$.[*].destination").value(hasItem(DEFAULT_DESTINATION)))
            .andExpect(jsonPath("$.[*].destinationContact").value(hasItem(DEFAULT_DESTINATION_CONTACT)))
            .andExpect(jsonPath("$.[*].initDate").value(hasItem(DEFAULT_INIT_DATE.toString())))
            .andExpect(jsonPath("$.[*].expirationDate").value(hasItem(DEFAULT_EXPIRATION_DATE.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].specialCharacteristics").value(hasItem(DEFAULT_SPECIAL_CHARACTERISTICS)))
            .andExpect(jsonPath("$.[*].productWeight").value(hasItem(DEFAULT_PRODUCT_WEIGHT.doubleValue())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].estimatedDate").value(hasItem(DEFAULT_ESTIMATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].deliveryTime").value(hasItem(DEFAULT_DELIVERY_TIME.toString())))
            .andExpect(jsonPath("$.[*].shippingCosts").value(hasItem(DEFAULT_SHIPPING_COSTS.doubleValue())))
            .andExpect(jsonPath("$.[*].rating").value(hasItem(DEFAULT_RATING.doubleValue())));

        // Check, that the count call also returns 1
        restRequestMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultRequestShouldNotBeFound(String filter) throws Exception {
        restRequestMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restRequestMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingRequest() throws Exception {
        // Get the request
        restRequestMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewRequest() throws Exception {
        // Initialize the database
        requestRepository.saveAndFlush(request);

        int databaseSizeBeforeUpdate = requestRepository.findAll().size();

        // Update the request
        Request updatedRequest = requestRepository.findById(request.getId()).get();
        // Disconnect from session so that the updates on updatedRequest are not directly saved in db
        em.detach(updatedRequest);
        updatedRequest
            .productValue(UPDATED_PRODUCT_VALUE)
            .productName(UPDATED_PRODUCT_NAME)
            .source(UPDATED_SOURCE)
            .destination(UPDATED_DESTINATION)
            .destinationContact(UPDATED_DESTINATION_CONTACT)
            .initDate(UPDATED_INIT_DATE)
            .expirationDate(UPDATED_EXPIRATION_DATE)
            .description(UPDATED_DESCRIPTION)
            .specialCharacteristics(UPDATED_SPECIAL_CHARACTERISTICS)
            .productWeight(UPDATED_PRODUCT_WEIGHT)
            .status(UPDATED_STATUS)
            .estimatedDate(UPDATED_ESTIMATED_DATE)
            .deliveryTime(UPDATED_DELIVERY_TIME)
            .shippingCosts(UPDATED_SHIPPING_COSTS)
            .rating(UPDATED_RATING);
        RequestDTO requestDTO = requestMapper.toDto(updatedRequest);

        restRequestMockMvc
            .perform(
                put(ENTITY_API_URL_ID, requestDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(requestDTO))
            )
            .andExpect(status().isOk());

        // Validate the Request in the database
        List<Request> requestList = requestRepository.findAll();
        assertThat(requestList).hasSize(databaseSizeBeforeUpdate);
        Request testRequest = requestList.get(requestList.size() - 1);
        assertThat(testRequest.getProductValue()).isEqualTo(UPDATED_PRODUCT_VALUE);
        assertThat(testRequest.getProductName()).isEqualTo(UPDATED_PRODUCT_NAME);
        assertThat(testRequest.getSource()).isEqualTo(UPDATED_SOURCE);
        assertThat(testRequest.getDestination()).isEqualTo(UPDATED_DESTINATION);
        assertThat(testRequest.getDestinationContact()).isEqualTo(UPDATED_DESTINATION_CONTACT);
        assertThat(testRequest.getInitDate()).isEqualTo(UPDATED_INIT_DATE);
        assertThat(testRequest.getExpirationDate()).isEqualTo(UPDATED_EXPIRATION_DATE);
        assertThat(testRequest.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testRequest.getSpecialCharacteristics()).isEqualTo(UPDATED_SPECIAL_CHARACTERISTICS);
        assertThat(testRequest.getProductWeight()).isEqualTo(UPDATED_PRODUCT_WEIGHT);
        assertThat(testRequest.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testRequest.getEstimatedDate()).isEqualTo(UPDATED_ESTIMATED_DATE);
        assertThat(testRequest.getDeliveryTime()).isEqualTo(UPDATED_DELIVERY_TIME);
        assertThat(testRequest.getShippingCosts()).isEqualTo(UPDATED_SHIPPING_COSTS);
        assertThat(testRequest.getRating()).isEqualTo(UPDATED_RATING);
    }

    @Test
    @Transactional
    void putNonExistingRequest() throws Exception {
        int databaseSizeBeforeUpdate = requestRepository.findAll().size();
        request.setId(count.incrementAndGet());

        // Create the Request
        RequestDTO requestDTO = requestMapper.toDto(request);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRequestMockMvc
            .perform(
                put(ENTITY_API_URL_ID, requestDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(requestDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Request in the database
        List<Request> requestList = requestRepository.findAll();
        assertThat(requestList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchRequest() throws Exception {
        int databaseSizeBeforeUpdate = requestRepository.findAll().size();
        request.setId(count.incrementAndGet());

        // Create the Request
        RequestDTO requestDTO = requestMapper.toDto(request);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRequestMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(requestDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Request in the database
        List<Request> requestList = requestRepository.findAll();
        assertThat(requestList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamRequest() throws Exception {
        int databaseSizeBeforeUpdate = requestRepository.findAll().size();
        request.setId(count.incrementAndGet());

        // Create the Request
        RequestDTO requestDTO = requestMapper.toDto(request);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRequestMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(requestDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Request in the database
        List<Request> requestList = requestRepository.findAll();
        assertThat(requestList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateRequestWithPatch() throws Exception {
        // Initialize the database
        requestRepository.saveAndFlush(request);

        int databaseSizeBeforeUpdate = requestRepository.findAll().size();

        // Update the request using partial update
        Request partialUpdatedRequest = new Request();
        partialUpdatedRequest.setId(request.getId());

        partialUpdatedRequest
            .destination(UPDATED_DESTINATION)
            .initDate(UPDATED_INIT_DATE)
            .estimatedDate(UPDATED_ESTIMATED_DATE)
            .shippingCosts(UPDATED_SHIPPING_COSTS);

        restRequestMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRequest.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedRequest))
            )
            .andExpect(status().isOk());

        // Validate the Request in the database
        List<Request> requestList = requestRepository.findAll();
        assertThat(requestList).hasSize(databaseSizeBeforeUpdate);
        Request testRequest = requestList.get(requestList.size() - 1);
        assertThat(testRequest.getProductValue()).isEqualTo(DEFAULT_PRODUCT_VALUE);
        assertThat(testRequest.getProductName()).isEqualTo(DEFAULT_PRODUCT_NAME);
        assertThat(testRequest.getSource()).isEqualTo(DEFAULT_SOURCE);
        assertThat(testRequest.getDestination()).isEqualTo(UPDATED_DESTINATION);
        assertThat(testRequest.getDestinationContact()).isEqualTo(DEFAULT_DESTINATION_CONTACT);
        assertThat(testRequest.getInitDate()).isEqualTo(UPDATED_INIT_DATE);
        assertThat(testRequest.getExpirationDate()).isEqualTo(DEFAULT_EXPIRATION_DATE);
        assertThat(testRequest.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testRequest.getSpecialCharacteristics()).isEqualTo(DEFAULT_SPECIAL_CHARACTERISTICS);
        assertThat(testRequest.getProductWeight()).isEqualTo(DEFAULT_PRODUCT_WEIGHT);
        assertThat(testRequest.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testRequest.getEstimatedDate()).isEqualTo(UPDATED_ESTIMATED_DATE);
        assertThat(testRequest.getDeliveryTime()).isEqualTo(DEFAULT_DELIVERY_TIME);
        assertThat(testRequest.getShippingCosts()).isEqualTo(UPDATED_SHIPPING_COSTS);
        assertThat(testRequest.getRating()).isEqualTo(DEFAULT_RATING);
    }

    @Test
    @Transactional
    void fullUpdateRequestWithPatch() throws Exception {
        // Initialize the database
        requestRepository.saveAndFlush(request);

        int databaseSizeBeforeUpdate = requestRepository.findAll().size();

        // Update the request using partial update
        Request partialUpdatedRequest = new Request();
        partialUpdatedRequest.setId(request.getId());

        partialUpdatedRequest
            .productValue(UPDATED_PRODUCT_VALUE)
            .productName(UPDATED_PRODUCT_NAME)
            .source(UPDATED_SOURCE)
            .destination(UPDATED_DESTINATION)
            .destinationContact(UPDATED_DESTINATION_CONTACT)
            .initDate(UPDATED_INIT_DATE)
            .expirationDate(UPDATED_EXPIRATION_DATE)
            .description(UPDATED_DESCRIPTION)
            .specialCharacteristics(UPDATED_SPECIAL_CHARACTERISTICS)
            .productWeight(UPDATED_PRODUCT_WEIGHT)
            .status(UPDATED_STATUS)
            .estimatedDate(UPDATED_ESTIMATED_DATE)
            .deliveryTime(UPDATED_DELIVERY_TIME)
            .shippingCosts(UPDATED_SHIPPING_COSTS)
            .rating(UPDATED_RATING);

        restRequestMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRequest.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedRequest))
            )
            .andExpect(status().isOk());

        // Validate the Request in the database
        List<Request> requestList = requestRepository.findAll();
        assertThat(requestList).hasSize(databaseSizeBeforeUpdate);
        Request testRequest = requestList.get(requestList.size() - 1);
        assertThat(testRequest.getProductValue()).isEqualTo(UPDATED_PRODUCT_VALUE);
        assertThat(testRequest.getProductName()).isEqualTo(UPDATED_PRODUCT_NAME);
        assertThat(testRequest.getSource()).isEqualTo(UPDATED_SOURCE);
        assertThat(testRequest.getDestination()).isEqualTo(UPDATED_DESTINATION);
        assertThat(testRequest.getDestinationContact()).isEqualTo(UPDATED_DESTINATION_CONTACT);
        assertThat(testRequest.getInitDate()).isEqualTo(UPDATED_INIT_DATE);
        assertThat(testRequest.getExpirationDate()).isEqualTo(UPDATED_EXPIRATION_DATE);
        assertThat(testRequest.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testRequest.getSpecialCharacteristics()).isEqualTo(UPDATED_SPECIAL_CHARACTERISTICS);
        assertThat(testRequest.getProductWeight()).isEqualTo(UPDATED_PRODUCT_WEIGHT);
        assertThat(testRequest.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testRequest.getEstimatedDate()).isEqualTo(UPDATED_ESTIMATED_DATE);
        assertThat(testRequest.getDeliveryTime()).isEqualTo(UPDATED_DELIVERY_TIME);
        assertThat(testRequest.getShippingCosts()).isEqualTo(UPDATED_SHIPPING_COSTS);
        assertThat(testRequest.getRating()).isEqualTo(UPDATED_RATING);
    }

    @Test
    @Transactional
    void patchNonExistingRequest() throws Exception {
        int databaseSizeBeforeUpdate = requestRepository.findAll().size();
        request.setId(count.incrementAndGet());

        // Create the Request
        RequestDTO requestDTO = requestMapper.toDto(request);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRequestMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, requestDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(requestDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Request in the database
        List<Request> requestList = requestRepository.findAll();
        assertThat(requestList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchRequest() throws Exception {
        int databaseSizeBeforeUpdate = requestRepository.findAll().size();
        request.setId(count.incrementAndGet());

        // Create the Request
        RequestDTO requestDTO = requestMapper.toDto(request);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRequestMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(requestDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Request in the database
        List<Request> requestList = requestRepository.findAll();
        assertThat(requestList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamRequest() throws Exception {
        int databaseSizeBeforeUpdate = requestRepository.findAll().size();
        request.setId(count.incrementAndGet());

        // Create the Request
        RequestDTO requestDTO = requestMapper.toDto(request);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRequestMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(requestDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Request in the database
        List<Request> requestList = requestRepository.findAll();
        assertThat(requestList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteRequest() throws Exception {
        // Initialize the database
        requestRepository.saveAndFlush(request);

        int databaseSizeBeforeDelete = requestRepository.findAll().size();

        // Delete the request
        restRequestMockMvc
            .perform(delete(ENTITY_API_URL_ID, request.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Request> requestList = requestRepository.findAll();
        assertThat(requestList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
