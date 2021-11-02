package com.leb.app.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.leb.app.IntegrationTest;
import com.leb.app.domain.Request;
import com.leb.app.domain.UserInfo;
import com.leb.app.domain.enumeration.Status;
import com.leb.app.repository.RequestRepository;
import com.leb.app.service.criteria.RequestCriteria;
import com.leb.app.service.dto.RequestDTO;
import com.leb.app.service.mapper.RequestMapper;
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

    private static final String DEFAULT_INIT_DATE = "AAAAAAAAAA";
    private static final String UPDATED_INIT_DATE = "BBBBBBBBBB";

    private static final String DEFAULT_EXPIRATION_DATE = "AAAAAAAAAA";
    private static final String UPDATED_EXPIRATION_DATE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_SPECIAL_CHARACTERISTICS = "AAAAAAAAAA";
    private static final String UPDATED_SPECIAL_CHARACTERISTICS = "BBBBBBBBBB";

    private static final Double DEFAULT_WEIGHT = 1D;
    private static final Double UPDATED_WEIGHT = 2D;
    private static final Double SMALLER_WEIGHT = 1D - 1D;

    private static final Double DEFAULT_HIGHT = 1D;
    private static final Double UPDATED_HIGHT = 2D;
    private static final Double SMALLER_HIGHT = 1D - 1D;

    private static final Double DEFAULT_WIDTH = 1D;
    private static final Double UPDATED_WIDTH = 2D;
    private static final Double SMALLER_WIDTH = 1D - 1D;

    private static final Status DEFAULT_STATUS = Status.WAITING;
    private static final Status UPDATED_STATUS = Status.OPENED;

    private static final String DEFAULT_ESTIMATED_DATE = "AAAAAAAAAA";
    private static final String UPDATED_ESTIMATED_DATE = "BBBBBBBBBB";

    private static final String DEFAULT_DELIVERY_TIME = "AAAAAAAAAA";
    private static final String UPDATED_DELIVERY_TIME = "BBBBBBBBBB";

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
            .weight(DEFAULT_WEIGHT)
            .hight(DEFAULT_HIGHT)
            .width(DEFAULT_WIDTH)
            .status(DEFAULT_STATUS)
            .estimatedDate(DEFAULT_ESTIMATED_DATE)
            .deliveryTime(DEFAULT_DELIVERY_TIME)
            .shippingCosts(DEFAULT_SHIPPING_COSTS)
            .rating(DEFAULT_RATING);
        // Add required entity
        UserInfo userInfo;
        if (TestUtil.findAll(em, UserInfo.class).isEmpty()) {
            userInfo = UserInfoResourceIT.createEntity(em);
            em.persist(userInfo);
            em.flush();
        } else {
            userInfo = TestUtil.findAll(em, UserInfo.class).get(0);
        }
        request.setOwnerRequest(userInfo);
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
            .weight(UPDATED_WEIGHT)
            .hight(UPDATED_HIGHT)
            .width(UPDATED_WIDTH)
            .status(UPDATED_STATUS)
            .estimatedDate(UPDATED_ESTIMATED_DATE)
            .deliveryTime(UPDATED_DELIVERY_TIME)
            .shippingCosts(UPDATED_SHIPPING_COSTS)
            .rating(UPDATED_RATING);
        // Add required entity
        UserInfo userInfo;
        if (TestUtil.findAll(em, UserInfo.class).isEmpty()) {
            userInfo = UserInfoResourceIT.createUpdatedEntity(em);
            em.persist(userInfo);
            em.flush();
        } else {
            userInfo = TestUtil.findAll(em, UserInfo.class).get(0);
        }
        request.setOwnerRequest(userInfo);
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
        assertThat(testRequest.getWeight()).isEqualTo(DEFAULT_WEIGHT);
        assertThat(testRequest.getHight()).isEqualTo(DEFAULT_HIGHT);
        assertThat(testRequest.getWidth()).isEqualTo(DEFAULT_WIDTH);
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
            .andExpect(jsonPath("$.[*].initDate").value(hasItem(DEFAULT_INIT_DATE)))
            .andExpect(jsonPath("$.[*].expirationDate").value(hasItem(DEFAULT_EXPIRATION_DATE)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].specialCharacteristics").value(hasItem(DEFAULT_SPECIAL_CHARACTERISTICS)))
            .andExpect(jsonPath("$.[*].weight").value(hasItem(DEFAULT_WEIGHT.doubleValue())))
            .andExpect(jsonPath("$.[*].hight").value(hasItem(DEFAULT_HIGHT.doubleValue())))
            .andExpect(jsonPath("$.[*].width").value(hasItem(DEFAULT_WIDTH.doubleValue())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].estimatedDate").value(hasItem(DEFAULT_ESTIMATED_DATE)))
            .andExpect(jsonPath("$.[*].deliveryTime").value(hasItem(DEFAULT_DELIVERY_TIME)))
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
            .andExpect(jsonPath("$.initDate").value(DEFAULT_INIT_DATE))
            .andExpect(jsonPath("$.expirationDate").value(DEFAULT_EXPIRATION_DATE))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.specialCharacteristics").value(DEFAULT_SPECIAL_CHARACTERISTICS))
            .andExpect(jsonPath("$.weight").value(DEFAULT_WEIGHT.doubleValue()))
            .andExpect(jsonPath("$.hight").value(DEFAULT_HIGHT.doubleValue()))
            .andExpect(jsonPath("$.width").value(DEFAULT_WIDTH.doubleValue()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.estimatedDate").value(DEFAULT_ESTIMATED_DATE))
            .andExpect(jsonPath("$.deliveryTime").value(DEFAULT_DELIVERY_TIME))
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
    void getAllRequestsByInitDateContainsSomething() throws Exception {
        // Initialize the database
        requestRepository.saveAndFlush(request);

        // Get all the requestList where initDate contains DEFAULT_INIT_DATE
        defaultRequestShouldBeFound("initDate.contains=" + DEFAULT_INIT_DATE);

        // Get all the requestList where initDate contains UPDATED_INIT_DATE
        defaultRequestShouldNotBeFound("initDate.contains=" + UPDATED_INIT_DATE);
    }

    @Test
    @Transactional
    void getAllRequestsByInitDateNotContainsSomething() throws Exception {
        // Initialize the database
        requestRepository.saveAndFlush(request);

        // Get all the requestList where initDate does not contain DEFAULT_INIT_DATE
        defaultRequestShouldNotBeFound("initDate.doesNotContain=" + DEFAULT_INIT_DATE);

        // Get all the requestList where initDate does not contain UPDATED_INIT_DATE
        defaultRequestShouldBeFound("initDate.doesNotContain=" + UPDATED_INIT_DATE);
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
    void getAllRequestsByExpirationDateContainsSomething() throws Exception {
        // Initialize the database
        requestRepository.saveAndFlush(request);

        // Get all the requestList where expirationDate contains DEFAULT_EXPIRATION_DATE
        defaultRequestShouldBeFound("expirationDate.contains=" + DEFAULT_EXPIRATION_DATE);

        // Get all the requestList where expirationDate contains UPDATED_EXPIRATION_DATE
        defaultRequestShouldNotBeFound("expirationDate.contains=" + UPDATED_EXPIRATION_DATE);
    }

    @Test
    @Transactional
    void getAllRequestsByExpirationDateNotContainsSomething() throws Exception {
        // Initialize the database
        requestRepository.saveAndFlush(request);

        // Get all the requestList where expirationDate does not contain DEFAULT_EXPIRATION_DATE
        defaultRequestShouldNotBeFound("expirationDate.doesNotContain=" + DEFAULT_EXPIRATION_DATE);

        // Get all the requestList where expirationDate does not contain UPDATED_EXPIRATION_DATE
        defaultRequestShouldBeFound("expirationDate.doesNotContain=" + UPDATED_EXPIRATION_DATE);
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
    void getAllRequestsByWeightIsEqualToSomething() throws Exception {
        // Initialize the database
        requestRepository.saveAndFlush(request);

        // Get all the requestList where weight equals to DEFAULT_WEIGHT
        defaultRequestShouldBeFound("weight.equals=" + DEFAULT_WEIGHT);

        // Get all the requestList where weight equals to UPDATED_WEIGHT
        defaultRequestShouldNotBeFound("weight.equals=" + UPDATED_WEIGHT);
    }

    @Test
    @Transactional
    void getAllRequestsByWeightIsNotEqualToSomething() throws Exception {
        // Initialize the database
        requestRepository.saveAndFlush(request);

        // Get all the requestList where weight not equals to DEFAULT_WEIGHT
        defaultRequestShouldNotBeFound("weight.notEquals=" + DEFAULT_WEIGHT);

        // Get all the requestList where weight not equals to UPDATED_WEIGHT
        defaultRequestShouldBeFound("weight.notEquals=" + UPDATED_WEIGHT);
    }

    @Test
    @Transactional
    void getAllRequestsByWeightIsInShouldWork() throws Exception {
        // Initialize the database
        requestRepository.saveAndFlush(request);

        // Get all the requestList where weight in DEFAULT_WEIGHT or UPDATED_WEIGHT
        defaultRequestShouldBeFound("weight.in=" + DEFAULT_WEIGHT + "," + UPDATED_WEIGHT);

        // Get all the requestList where weight equals to UPDATED_WEIGHT
        defaultRequestShouldNotBeFound("weight.in=" + UPDATED_WEIGHT);
    }

    @Test
    @Transactional
    void getAllRequestsByWeightIsNullOrNotNull() throws Exception {
        // Initialize the database
        requestRepository.saveAndFlush(request);

        // Get all the requestList where weight is not null
        defaultRequestShouldBeFound("weight.specified=true");

        // Get all the requestList where weight is null
        defaultRequestShouldNotBeFound("weight.specified=false");
    }

    @Test
    @Transactional
    void getAllRequestsByWeightIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        requestRepository.saveAndFlush(request);

        // Get all the requestList where weight is greater than or equal to DEFAULT_WEIGHT
        defaultRequestShouldBeFound("weight.greaterThanOrEqual=" + DEFAULT_WEIGHT);

        // Get all the requestList where weight is greater than or equal to UPDATED_WEIGHT
        defaultRequestShouldNotBeFound("weight.greaterThanOrEqual=" + UPDATED_WEIGHT);
    }

    @Test
    @Transactional
    void getAllRequestsByWeightIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        requestRepository.saveAndFlush(request);

        // Get all the requestList where weight is less than or equal to DEFAULT_WEIGHT
        defaultRequestShouldBeFound("weight.lessThanOrEqual=" + DEFAULT_WEIGHT);

        // Get all the requestList where weight is less than or equal to SMALLER_WEIGHT
        defaultRequestShouldNotBeFound("weight.lessThanOrEqual=" + SMALLER_WEIGHT);
    }

    @Test
    @Transactional
    void getAllRequestsByWeightIsLessThanSomething() throws Exception {
        // Initialize the database
        requestRepository.saveAndFlush(request);

        // Get all the requestList where weight is less than DEFAULT_WEIGHT
        defaultRequestShouldNotBeFound("weight.lessThan=" + DEFAULT_WEIGHT);

        // Get all the requestList where weight is less than UPDATED_WEIGHT
        defaultRequestShouldBeFound("weight.lessThan=" + UPDATED_WEIGHT);
    }

    @Test
    @Transactional
    void getAllRequestsByWeightIsGreaterThanSomething() throws Exception {
        // Initialize the database
        requestRepository.saveAndFlush(request);

        // Get all the requestList where weight is greater than DEFAULT_WEIGHT
        defaultRequestShouldNotBeFound("weight.greaterThan=" + DEFAULT_WEIGHT);

        // Get all the requestList where weight is greater than SMALLER_WEIGHT
        defaultRequestShouldBeFound("weight.greaterThan=" + SMALLER_WEIGHT);
    }

    @Test
    @Transactional
    void getAllRequestsByHightIsEqualToSomething() throws Exception {
        // Initialize the database
        requestRepository.saveAndFlush(request);

        // Get all the requestList where hight equals to DEFAULT_HIGHT
        defaultRequestShouldBeFound("hight.equals=" + DEFAULT_HIGHT);

        // Get all the requestList where hight equals to UPDATED_HIGHT
        defaultRequestShouldNotBeFound("hight.equals=" + UPDATED_HIGHT);
    }

    @Test
    @Transactional
    void getAllRequestsByHightIsNotEqualToSomething() throws Exception {
        // Initialize the database
        requestRepository.saveAndFlush(request);

        // Get all the requestList where hight not equals to DEFAULT_HIGHT
        defaultRequestShouldNotBeFound("hight.notEquals=" + DEFAULT_HIGHT);

        // Get all the requestList where hight not equals to UPDATED_HIGHT
        defaultRequestShouldBeFound("hight.notEquals=" + UPDATED_HIGHT);
    }

    @Test
    @Transactional
    void getAllRequestsByHightIsInShouldWork() throws Exception {
        // Initialize the database
        requestRepository.saveAndFlush(request);

        // Get all the requestList where hight in DEFAULT_HIGHT or UPDATED_HIGHT
        defaultRequestShouldBeFound("hight.in=" + DEFAULT_HIGHT + "," + UPDATED_HIGHT);

        // Get all the requestList where hight equals to UPDATED_HIGHT
        defaultRequestShouldNotBeFound("hight.in=" + UPDATED_HIGHT);
    }

    @Test
    @Transactional
    void getAllRequestsByHightIsNullOrNotNull() throws Exception {
        // Initialize the database
        requestRepository.saveAndFlush(request);

        // Get all the requestList where hight is not null
        defaultRequestShouldBeFound("hight.specified=true");

        // Get all the requestList where hight is null
        defaultRequestShouldNotBeFound("hight.specified=false");
    }

    @Test
    @Transactional
    void getAllRequestsByHightIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        requestRepository.saveAndFlush(request);

        // Get all the requestList where hight is greater than or equal to DEFAULT_HIGHT
        defaultRequestShouldBeFound("hight.greaterThanOrEqual=" + DEFAULT_HIGHT);

        // Get all the requestList where hight is greater than or equal to UPDATED_HIGHT
        defaultRequestShouldNotBeFound("hight.greaterThanOrEqual=" + UPDATED_HIGHT);
    }

    @Test
    @Transactional
    void getAllRequestsByHightIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        requestRepository.saveAndFlush(request);

        // Get all the requestList where hight is less than or equal to DEFAULT_HIGHT
        defaultRequestShouldBeFound("hight.lessThanOrEqual=" + DEFAULT_HIGHT);

        // Get all the requestList where hight is less than or equal to SMALLER_HIGHT
        defaultRequestShouldNotBeFound("hight.lessThanOrEqual=" + SMALLER_HIGHT);
    }

    @Test
    @Transactional
    void getAllRequestsByHightIsLessThanSomething() throws Exception {
        // Initialize the database
        requestRepository.saveAndFlush(request);

        // Get all the requestList where hight is less than DEFAULT_HIGHT
        defaultRequestShouldNotBeFound("hight.lessThan=" + DEFAULT_HIGHT);

        // Get all the requestList where hight is less than UPDATED_HIGHT
        defaultRequestShouldBeFound("hight.lessThan=" + UPDATED_HIGHT);
    }

    @Test
    @Transactional
    void getAllRequestsByHightIsGreaterThanSomething() throws Exception {
        // Initialize the database
        requestRepository.saveAndFlush(request);

        // Get all the requestList where hight is greater than DEFAULT_HIGHT
        defaultRequestShouldNotBeFound("hight.greaterThan=" + DEFAULT_HIGHT);

        // Get all the requestList where hight is greater than SMALLER_HIGHT
        defaultRequestShouldBeFound("hight.greaterThan=" + SMALLER_HIGHT);
    }

    @Test
    @Transactional
    void getAllRequestsByWidthIsEqualToSomething() throws Exception {
        // Initialize the database
        requestRepository.saveAndFlush(request);

        // Get all the requestList where width equals to DEFAULT_WIDTH
        defaultRequestShouldBeFound("width.equals=" + DEFAULT_WIDTH);

        // Get all the requestList where width equals to UPDATED_WIDTH
        defaultRequestShouldNotBeFound("width.equals=" + UPDATED_WIDTH);
    }

    @Test
    @Transactional
    void getAllRequestsByWidthIsNotEqualToSomething() throws Exception {
        // Initialize the database
        requestRepository.saveAndFlush(request);

        // Get all the requestList where width not equals to DEFAULT_WIDTH
        defaultRequestShouldNotBeFound("width.notEquals=" + DEFAULT_WIDTH);

        // Get all the requestList where width not equals to UPDATED_WIDTH
        defaultRequestShouldBeFound("width.notEquals=" + UPDATED_WIDTH);
    }

    @Test
    @Transactional
    void getAllRequestsByWidthIsInShouldWork() throws Exception {
        // Initialize the database
        requestRepository.saveAndFlush(request);

        // Get all the requestList where width in DEFAULT_WIDTH or UPDATED_WIDTH
        defaultRequestShouldBeFound("width.in=" + DEFAULT_WIDTH + "," + UPDATED_WIDTH);

        // Get all the requestList where width equals to UPDATED_WIDTH
        defaultRequestShouldNotBeFound("width.in=" + UPDATED_WIDTH);
    }

    @Test
    @Transactional
    void getAllRequestsByWidthIsNullOrNotNull() throws Exception {
        // Initialize the database
        requestRepository.saveAndFlush(request);

        // Get all the requestList where width is not null
        defaultRequestShouldBeFound("width.specified=true");

        // Get all the requestList where width is null
        defaultRequestShouldNotBeFound("width.specified=false");
    }

    @Test
    @Transactional
    void getAllRequestsByWidthIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        requestRepository.saveAndFlush(request);

        // Get all the requestList where width is greater than or equal to DEFAULT_WIDTH
        defaultRequestShouldBeFound("width.greaterThanOrEqual=" + DEFAULT_WIDTH);

        // Get all the requestList where width is greater than or equal to UPDATED_WIDTH
        defaultRequestShouldNotBeFound("width.greaterThanOrEqual=" + UPDATED_WIDTH);
    }

    @Test
    @Transactional
    void getAllRequestsByWidthIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        requestRepository.saveAndFlush(request);

        // Get all the requestList where width is less than or equal to DEFAULT_WIDTH
        defaultRequestShouldBeFound("width.lessThanOrEqual=" + DEFAULT_WIDTH);

        // Get all the requestList where width is less than or equal to SMALLER_WIDTH
        defaultRequestShouldNotBeFound("width.lessThanOrEqual=" + SMALLER_WIDTH);
    }

    @Test
    @Transactional
    void getAllRequestsByWidthIsLessThanSomething() throws Exception {
        // Initialize the database
        requestRepository.saveAndFlush(request);

        // Get all the requestList where width is less than DEFAULT_WIDTH
        defaultRequestShouldNotBeFound("width.lessThan=" + DEFAULT_WIDTH);

        // Get all the requestList where width is less than UPDATED_WIDTH
        defaultRequestShouldBeFound("width.lessThan=" + UPDATED_WIDTH);
    }

    @Test
    @Transactional
    void getAllRequestsByWidthIsGreaterThanSomething() throws Exception {
        // Initialize the database
        requestRepository.saveAndFlush(request);

        // Get all the requestList where width is greater than DEFAULT_WIDTH
        defaultRequestShouldNotBeFound("width.greaterThan=" + DEFAULT_WIDTH);

        // Get all the requestList where width is greater than SMALLER_WIDTH
        defaultRequestShouldBeFound("width.greaterThan=" + SMALLER_WIDTH);
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
    void getAllRequestsByEstimatedDateContainsSomething() throws Exception {
        // Initialize the database
        requestRepository.saveAndFlush(request);

        // Get all the requestList where estimatedDate contains DEFAULT_ESTIMATED_DATE
        defaultRequestShouldBeFound("estimatedDate.contains=" + DEFAULT_ESTIMATED_DATE);

        // Get all the requestList where estimatedDate contains UPDATED_ESTIMATED_DATE
        defaultRequestShouldNotBeFound("estimatedDate.contains=" + UPDATED_ESTIMATED_DATE);
    }

    @Test
    @Transactional
    void getAllRequestsByEstimatedDateNotContainsSomething() throws Exception {
        // Initialize the database
        requestRepository.saveAndFlush(request);

        // Get all the requestList where estimatedDate does not contain DEFAULT_ESTIMATED_DATE
        defaultRequestShouldNotBeFound("estimatedDate.doesNotContain=" + DEFAULT_ESTIMATED_DATE);

        // Get all the requestList where estimatedDate does not contain UPDATED_ESTIMATED_DATE
        defaultRequestShouldBeFound("estimatedDate.doesNotContain=" + UPDATED_ESTIMATED_DATE);
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
    void getAllRequestsByDeliveryTimeContainsSomething() throws Exception {
        // Initialize the database
        requestRepository.saveAndFlush(request);

        // Get all the requestList where deliveryTime contains DEFAULT_DELIVERY_TIME
        defaultRequestShouldBeFound("deliveryTime.contains=" + DEFAULT_DELIVERY_TIME);

        // Get all the requestList where deliveryTime contains UPDATED_DELIVERY_TIME
        defaultRequestShouldNotBeFound("deliveryTime.contains=" + UPDATED_DELIVERY_TIME);
    }

    @Test
    @Transactional
    void getAllRequestsByDeliveryTimeNotContainsSomething() throws Exception {
        // Initialize the database
        requestRepository.saveAndFlush(request);

        // Get all the requestList where deliveryTime does not contain DEFAULT_DELIVERY_TIME
        defaultRequestShouldNotBeFound("deliveryTime.doesNotContain=" + DEFAULT_DELIVERY_TIME);

        // Get all the requestList where deliveryTime does not contain UPDATED_DELIVERY_TIME
        defaultRequestShouldBeFound("deliveryTime.doesNotContain=" + UPDATED_DELIVERY_TIME);
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
    void getAllRequestsByOwnerRequestIsEqualToSomething() throws Exception {
        // Initialize the database
        requestRepository.saveAndFlush(request);
        UserInfo ownerRequest;
        if (TestUtil.findAll(em, UserInfo.class).isEmpty()) {
            ownerRequest = UserInfoResourceIT.createEntity(em);
            em.persist(ownerRequest);
            em.flush();
        } else {
            ownerRequest = TestUtil.findAll(em, UserInfo.class).get(0);
        }
        em.persist(ownerRequest);
        em.flush();
        request.setOwnerRequest(ownerRequest);
        requestRepository.saveAndFlush(request);
        Long ownerRequestId = ownerRequest.getId();

        // Get all the requestList where ownerRequest equals to ownerRequestId
        defaultRequestShouldBeFound("ownerRequestId.equals=" + ownerRequestId);

        // Get all the requestList where ownerRequest equals to (ownerRequestId + 1)
        defaultRequestShouldNotBeFound("ownerRequestId.equals=" + (ownerRequestId + 1));
    }

    @Test
    @Transactional
    void getAllRequestsByTranporterIsEqualToSomething() throws Exception {
        // Initialize the database
        requestRepository.saveAndFlush(request);
        UserInfo tranporter;
        if (TestUtil.findAll(em, UserInfo.class).isEmpty()) {
            tranporter = UserInfoResourceIT.createEntity(em);
            em.persist(tranporter);
            em.flush();
        } else {
            tranporter = TestUtil.findAll(em, UserInfo.class).get(0);
        }
        em.persist(tranporter);
        em.flush();
        request.setTranporter(tranporter);
        requestRepository.saveAndFlush(request);
        Long tranporterId = tranporter.getId();

        // Get all the requestList where tranporter equals to tranporterId
        defaultRequestShouldBeFound("tranporterId.equals=" + tranporterId);

        // Get all the requestList where tranporter equals to (tranporterId + 1)
        defaultRequestShouldNotBeFound("tranporterId.equals=" + (tranporterId + 1));
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
            .andExpect(jsonPath("$.[*].initDate").value(hasItem(DEFAULT_INIT_DATE)))
            .andExpect(jsonPath("$.[*].expirationDate").value(hasItem(DEFAULT_EXPIRATION_DATE)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].specialCharacteristics").value(hasItem(DEFAULT_SPECIAL_CHARACTERISTICS)))
            .andExpect(jsonPath("$.[*].weight").value(hasItem(DEFAULT_WEIGHT.doubleValue())))
            .andExpect(jsonPath("$.[*].hight").value(hasItem(DEFAULT_HIGHT.doubleValue())))
            .andExpect(jsonPath("$.[*].width").value(hasItem(DEFAULT_WIDTH.doubleValue())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].estimatedDate").value(hasItem(DEFAULT_ESTIMATED_DATE)))
            .andExpect(jsonPath("$.[*].deliveryTime").value(hasItem(DEFAULT_DELIVERY_TIME)))
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
            .weight(UPDATED_WEIGHT)
            .hight(UPDATED_HIGHT)
            .width(UPDATED_WIDTH)
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
        assertThat(testRequest.getWeight()).isEqualTo(UPDATED_WEIGHT);
        assertThat(testRequest.getHight()).isEqualTo(UPDATED_HIGHT);
        assertThat(testRequest.getWidth()).isEqualTo(UPDATED_WIDTH);
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
            .productValue(UPDATED_PRODUCT_VALUE)
            .productName(UPDATED_PRODUCT_NAME)
            .destination(UPDATED_DESTINATION)
            .destinationContact(UPDATED_DESTINATION_CONTACT)
            .initDate(UPDATED_INIT_DATE)
            .description(UPDATED_DESCRIPTION)
            .specialCharacteristics(UPDATED_SPECIAL_CHARACTERISTICS)
            .weight(UPDATED_WEIGHT)
            .width(UPDATED_WIDTH)
            .status(UPDATED_STATUS)
            .estimatedDate(UPDATED_ESTIMATED_DATE)
            .deliveryTime(UPDATED_DELIVERY_TIME)
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
        assertThat(testRequest.getProductValue()).isEqualTo(UPDATED_PRODUCT_VALUE);
        assertThat(testRequest.getProductName()).isEqualTo(UPDATED_PRODUCT_NAME);
        assertThat(testRequest.getSource()).isEqualTo(DEFAULT_SOURCE);
        assertThat(testRequest.getDestination()).isEqualTo(UPDATED_DESTINATION);
        assertThat(testRequest.getDestinationContact()).isEqualTo(UPDATED_DESTINATION_CONTACT);
        assertThat(testRequest.getInitDate()).isEqualTo(UPDATED_INIT_DATE);
        assertThat(testRequest.getExpirationDate()).isEqualTo(DEFAULT_EXPIRATION_DATE);
        assertThat(testRequest.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testRequest.getSpecialCharacteristics()).isEqualTo(UPDATED_SPECIAL_CHARACTERISTICS);
        assertThat(testRequest.getWeight()).isEqualTo(UPDATED_WEIGHT);
        assertThat(testRequest.getHight()).isEqualTo(DEFAULT_HIGHT);
        assertThat(testRequest.getWidth()).isEqualTo(UPDATED_WIDTH);
        assertThat(testRequest.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testRequest.getEstimatedDate()).isEqualTo(UPDATED_ESTIMATED_DATE);
        assertThat(testRequest.getDeliveryTime()).isEqualTo(UPDATED_DELIVERY_TIME);
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
            .weight(UPDATED_WEIGHT)
            .hight(UPDATED_HIGHT)
            .width(UPDATED_WIDTH)
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
        assertThat(testRequest.getWeight()).isEqualTo(UPDATED_WEIGHT);
        assertThat(testRequest.getHight()).isEqualTo(UPDATED_HIGHT);
        assertThat(testRequest.getWidth()).isEqualTo(UPDATED_WIDTH);
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
