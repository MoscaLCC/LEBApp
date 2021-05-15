package com.leb.app.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.leb.app.IntegrationTest;
import com.leb.app.domain.Producer;
import com.leb.app.domain.Request;
import com.leb.app.domain.UserInfo;
import com.leb.app.repository.ProducerRepository;
import com.leb.app.service.criteria.ProducerCriteria;
import com.leb.app.service.dto.ProducerDTO;
import com.leb.app.service.mapper.ProducerMapper;
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
 * Integration tests for the {@link ProducerResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ProducerResourceIT {

    private static final String DEFAULT_LINK_SOCIAL = "AAAAAAAAAA";
    private static final String UPDATED_LINK_SOCIAL = "BBBBBBBBBB";

    private static final Integer DEFAULT_NUMBER_REQUESTS = 1;
    private static final Integer UPDATED_NUMBER_REQUESTS = 2;
    private static final Integer SMALLER_NUMBER_REQUESTS = 1 - 1;

    private static final Double DEFAULT_PAYED_VALUE = 1D;
    private static final Double UPDATED_PAYED_VALUE = 2D;
    private static final Double SMALLER_PAYED_VALUE = 1D - 1D;

    private static final Double DEFAULT_VALUE_TO_PAY = 1D;
    private static final Double UPDATED_VALUE_TO_PAY = 2D;
    private static final Double SMALLER_VALUE_TO_PAY = 1D - 1D;

    private static final Double DEFAULT_RANKING = 1D;
    private static final Double UPDATED_RANKING = 2D;
    private static final Double SMALLER_RANKING = 1D - 1D;

    private static final String ENTITY_API_URL = "/api/producers";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ProducerRepository producerRepository;

    @Autowired
    private ProducerMapper producerMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restProducerMockMvc;

    private Producer producer;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Producer createEntity(EntityManager em) {
        Producer producer = new Producer()
            .linkSocial(DEFAULT_LINK_SOCIAL)
            .numberRequests(DEFAULT_NUMBER_REQUESTS)
            .payedValue(DEFAULT_PAYED_VALUE)
            .valueToPay(DEFAULT_VALUE_TO_PAY)
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
        producer.setUserInfo(userInfo);
        return producer;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Producer createUpdatedEntity(EntityManager em) {
        Producer producer = new Producer()
            .linkSocial(UPDATED_LINK_SOCIAL)
            .numberRequests(UPDATED_NUMBER_REQUESTS)
            .payedValue(UPDATED_PAYED_VALUE)
            .valueToPay(UPDATED_VALUE_TO_PAY)
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
        producer.setUserInfo(userInfo);
        return producer;
    }

    @BeforeEach
    public void initTest() {
        producer = createEntity(em);
    }

    @Test
    @Transactional
    void createProducer() throws Exception {
        int databaseSizeBeforeCreate = producerRepository.findAll().size();
        // Create the Producer
        ProducerDTO producerDTO = producerMapper.toDto(producer);
        restProducerMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(producerDTO)))
            .andExpect(status().isCreated());

        // Validate the Producer in the database
        List<Producer> producerList = producerRepository.findAll();
        assertThat(producerList).hasSize(databaseSizeBeforeCreate + 1);
        Producer testProducer = producerList.get(producerList.size() - 1);
        assertThat(testProducer.getLinkSocial()).isEqualTo(DEFAULT_LINK_SOCIAL);
        assertThat(testProducer.getNumberRequests()).isEqualTo(DEFAULT_NUMBER_REQUESTS);
        assertThat(testProducer.getPayedValue()).isEqualTo(DEFAULT_PAYED_VALUE);
        assertThat(testProducer.getValueToPay()).isEqualTo(DEFAULT_VALUE_TO_PAY);
        assertThat(testProducer.getRanking()).isEqualTo(DEFAULT_RANKING);
    }

    @Test
    @Transactional
    void createProducerWithExistingId() throws Exception {
        // Create the Producer with an existing ID
        producer.setId(1L);
        ProducerDTO producerDTO = producerMapper.toDto(producer);

        int databaseSizeBeforeCreate = producerRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restProducerMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(producerDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Producer in the database
        List<Producer> producerList = producerRepository.findAll();
        assertThat(producerList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllProducers() throws Exception {
        // Initialize the database
        producerRepository.saveAndFlush(producer);

        // Get all the producerList
        restProducerMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(producer.getId().intValue())))
            .andExpect(jsonPath("$.[*].linkSocial").value(hasItem(DEFAULT_LINK_SOCIAL)))
            .andExpect(jsonPath("$.[*].numberRequests").value(hasItem(DEFAULT_NUMBER_REQUESTS)))
            .andExpect(jsonPath("$.[*].payedValue").value(hasItem(DEFAULT_PAYED_VALUE.doubleValue())))
            .andExpect(jsonPath("$.[*].valueToPay").value(hasItem(DEFAULT_VALUE_TO_PAY.doubleValue())))
            .andExpect(jsonPath("$.[*].ranking").value(hasItem(DEFAULT_RANKING.doubleValue())));
    }

    @Test
    @Transactional
    void getProducer() throws Exception {
        // Initialize the database
        producerRepository.saveAndFlush(producer);

        // Get the producer
        restProducerMockMvc
            .perform(get(ENTITY_API_URL_ID, producer.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(producer.getId().intValue()))
            .andExpect(jsonPath("$.linkSocial").value(DEFAULT_LINK_SOCIAL))
            .andExpect(jsonPath("$.numberRequests").value(DEFAULT_NUMBER_REQUESTS))
            .andExpect(jsonPath("$.payedValue").value(DEFAULT_PAYED_VALUE.doubleValue()))
            .andExpect(jsonPath("$.valueToPay").value(DEFAULT_VALUE_TO_PAY.doubleValue()))
            .andExpect(jsonPath("$.ranking").value(DEFAULT_RANKING.doubleValue()));
    }

    @Test
    @Transactional
    void getProducersByIdFiltering() throws Exception {
        // Initialize the database
        producerRepository.saveAndFlush(producer);

        Long id = producer.getId();

        defaultProducerShouldBeFound("id.equals=" + id);
        defaultProducerShouldNotBeFound("id.notEquals=" + id);

        defaultProducerShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultProducerShouldNotBeFound("id.greaterThan=" + id);

        defaultProducerShouldBeFound("id.lessThanOrEqual=" + id);
        defaultProducerShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllProducersByLinkSocialIsEqualToSomething() throws Exception {
        // Initialize the database
        producerRepository.saveAndFlush(producer);

        // Get all the producerList where linkSocial equals to DEFAULT_LINK_SOCIAL
        defaultProducerShouldBeFound("linkSocial.equals=" + DEFAULT_LINK_SOCIAL);

        // Get all the producerList where linkSocial equals to UPDATED_LINK_SOCIAL
        defaultProducerShouldNotBeFound("linkSocial.equals=" + UPDATED_LINK_SOCIAL);
    }

    @Test
    @Transactional
    void getAllProducersByLinkSocialIsNotEqualToSomething() throws Exception {
        // Initialize the database
        producerRepository.saveAndFlush(producer);

        // Get all the producerList where linkSocial not equals to DEFAULT_LINK_SOCIAL
        defaultProducerShouldNotBeFound("linkSocial.notEquals=" + DEFAULT_LINK_SOCIAL);

        // Get all the producerList where linkSocial not equals to UPDATED_LINK_SOCIAL
        defaultProducerShouldBeFound("linkSocial.notEquals=" + UPDATED_LINK_SOCIAL);
    }

    @Test
    @Transactional
    void getAllProducersByLinkSocialIsInShouldWork() throws Exception {
        // Initialize the database
        producerRepository.saveAndFlush(producer);

        // Get all the producerList where linkSocial in DEFAULT_LINK_SOCIAL or UPDATED_LINK_SOCIAL
        defaultProducerShouldBeFound("linkSocial.in=" + DEFAULT_LINK_SOCIAL + "," + UPDATED_LINK_SOCIAL);

        // Get all the producerList where linkSocial equals to UPDATED_LINK_SOCIAL
        defaultProducerShouldNotBeFound("linkSocial.in=" + UPDATED_LINK_SOCIAL);
    }

    @Test
    @Transactional
    void getAllProducersByLinkSocialIsNullOrNotNull() throws Exception {
        // Initialize the database
        producerRepository.saveAndFlush(producer);

        // Get all the producerList where linkSocial is not null
        defaultProducerShouldBeFound("linkSocial.specified=true");

        // Get all the producerList where linkSocial is null
        defaultProducerShouldNotBeFound("linkSocial.specified=false");
    }

    @Test
    @Transactional
    void getAllProducersByLinkSocialContainsSomething() throws Exception {
        // Initialize the database
        producerRepository.saveAndFlush(producer);

        // Get all the producerList where linkSocial contains DEFAULT_LINK_SOCIAL
        defaultProducerShouldBeFound("linkSocial.contains=" + DEFAULT_LINK_SOCIAL);

        // Get all the producerList where linkSocial contains UPDATED_LINK_SOCIAL
        defaultProducerShouldNotBeFound("linkSocial.contains=" + UPDATED_LINK_SOCIAL);
    }

    @Test
    @Transactional
    void getAllProducersByLinkSocialNotContainsSomething() throws Exception {
        // Initialize the database
        producerRepository.saveAndFlush(producer);

        // Get all the producerList where linkSocial does not contain DEFAULT_LINK_SOCIAL
        defaultProducerShouldNotBeFound("linkSocial.doesNotContain=" + DEFAULT_LINK_SOCIAL);

        // Get all the producerList where linkSocial does not contain UPDATED_LINK_SOCIAL
        defaultProducerShouldBeFound("linkSocial.doesNotContain=" + UPDATED_LINK_SOCIAL);
    }

    @Test
    @Transactional
    void getAllProducersByNumberRequestsIsEqualToSomething() throws Exception {
        // Initialize the database
        producerRepository.saveAndFlush(producer);

        // Get all the producerList where numberRequests equals to DEFAULT_NUMBER_REQUESTS
        defaultProducerShouldBeFound("numberRequests.equals=" + DEFAULT_NUMBER_REQUESTS);

        // Get all the producerList where numberRequests equals to UPDATED_NUMBER_REQUESTS
        defaultProducerShouldNotBeFound("numberRequests.equals=" + UPDATED_NUMBER_REQUESTS);
    }

    @Test
    @Transactional
    void getAllProducersByNumberRequestsIsNotEqualToSomething() throws Exception {
        // Initialize the database
        producerRepository.saveAndFlush(producer);

        // Get all the producerList where numberRequests not equals to DEFAULT_NUMBER_REQUESTS
        defaultProducerShouldNotBeFound("numberRequests.notEquals=" + DEFAULT_NUMBER_REQUESTS);

        // Get all the producerList where numberRequests not equals to UPDATED_NUMBER_REQUESTS
        defaultProducerShouldBeFound("numberRequests.notEquals=" + UPDATED_NUMBER_REQUESTS);
    }

    @Test
    @Transactional
    void getAllProducersByNumberRequestsIsInShouldWork() throws Exception {
        // Initialize the database
        producerRepository.saveAndFlush(producer);

        // Get all the producerList where numberRequests in DEFAULT_NUMBER_REQUESTS or UPDATED_NUMBER_REQUESTS
        defaultProducerShouldBeFound("numberRequests.in=" + DEFAULT_NUMBER_REQUESTS + "," + UPDATED_NUMBER_REQUESTS);

        // Get all the producerList where numberRequests equals to UPDATED_NUMBER_REQUESTS
        defaultProducerShouldNotBeFound("numberRequests.in=" + UPDATED_NUMBER_REQUESTS);
    }

    @Test
    @Transactional
    void getAllProducersByNumberRequestsIsNullOrNotNull() throws Exception {
        // Initialize the database
        producerRepository.saveAndFlush(producer);

        // Get all the producerList where numberRequests is not null
        defaultProducerShouldBeFound("numberRequests.specified=true");

        // Get all the producerList where numberRequests is null
        defaultProducerShouldNotBeFound("numberRequests.specified=false");
    }

    @Test
    @Transactional
    void getAllProducersByNumberRequestsIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        producerRepository.saveAndFlush(producer);

        // Get all the producerList where numberRequests is greater than or equal to DEFAULT_NUMBER_REQUESTS
        defaultProducerShouldBeFound("numberRequests.greaterThanOrEqual=" + DEFAULT_NUMBER_REQUESTS);

        // Get all the producerList where numberRequests is greater than or equal to UPDATED_NUMBER_REQUESTS
        defaultProducerShouldNotBeFound("numberRequests.greaterThanOrEqual=" + UPDATED_NUMBER_REQUESTS);
    }

    @Test
    @Transactional
    void getAllProducersByNumberRequestsIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        producerRepository.saveAndFlush(producer);

        // Get all the producerList where numberRequests is less than or equal to DEFAULT_NUMBER_REQUESTS
        defaultProducerShouldBeFound("numberRequests.lessThanOrEqual=" + DEFAULT_NUMBER_REQUESTS);

        // Get all the producerList where numberRequests is less than or equal to SMALLER_NUMBER_REQUESTS
        defaultProducerShouldNotBeFound("numberRequests.lessThanOrEqual=" + SMALLER_NUMBER_REQUESTS);
    }

    @Test
    @Transactional
    void getAllProducersByNumberRequestsIsLessThanSomething() throws Exception {
        // Initialize the database
        producerRepository.saveAndFlush(producer);

        // Get all the producerList where numberRequests is less than DEFAULT_NUMBER_REQUESTS
        defaultProducerShouldNotBeFound("numberRequests.lessThan=" + DEFAULT_NUMBER_REQUESTS);

        // Get all the producerList where numberRequests is less than UPDATED_NUMBER_REQUESTS
        defaultProducerShouldBeFound("numberRequests.lessThan=" + UPDATED_NUMBER_REQUESTS);
    }

    @Test
    @Transactional
    void getAllProducersByNumberRequestsIsGreaterThanSomething() throws Exception {
        // Initialize the database
        producerRepository.saveAndFlush(producer);

        // Get all the producerList where numberRequests is greater than DEFAULT_NUMBER_REQUESTS
        defaultProducerShouldNotBeFound("numberRequests.greaterThan=" + DEFAULT_NUMBER_REQUESTS);

        // Get all the producerList where numberRequests is greater than SMALLER_NUMBER_REQUESTS
        defaultProducerShouldBeFound("numberRequests.greaterThan=" + SMALLER_NUMBER_REQUESTS);
    }

    @Test
    @Transactional
    void getAllProducersByPayedValueIsEqualToSomething() throws Exception {
        // Initialize the database
        producerRepository.saveAndFlush(producer);

        // Get all the producerList where payedValue equals to DEFAULT_PAYED_VALUE
        defaultProducerShouldBeFound("payedValue.equals=" + DEFAULT_PAYED_VALUE);

        // Get all the producerList where payedValue equals to UPDATED_PAYED_VALUE
        defaultProducerShouldNotBeFound("payedValue.equals=" + UPDATED_PAYED_VALUE);
    }

    @Test
    @Transactional
    void getAllProducersByPayedValueIsNotEqualToSomething() throws Exception {
        // Initialize the database
        producerRepository.saveAndFlush(producer);

        // Get all the producerList where payedValue not equals to DEFAULT_PAYED_VALUE
        defaultProducerShouldNotBeFound("payedValue.notEquals=" + DEFAULT_PAYED_VALUE);

        // Get all the producerList where payedValue not equals to UPDATED_PAYED_VALUE
        defaultProducerShouldBeFound("payedValue.notEquals=" + UPDATED_PAYED_VALUE);
    }

    @Test
    @Transactional
    void getAllProducersByPayedValueIsInShouldWork() throws Exception {
        // Initialize the database
        producerRepository.saveAndFlush(producer);

        // Get all the producerList where payedValue in DEFAULT_PAYED_VALUE or UPDATED_PAYED_VALUE
        defaultProducerShouldBeFound("payedValue.in=" + DEFAULT_PAYED_VALUE + "," + UPDATED_PAYED_VALUE);

        // Get all the producerList where payedValue equals to UPDATED_PAYED_VALUE
        defaultProducerShouldNotBeFound("payedValue.in=" + UPDATED_PAYED_VALUE);
    }

    @Test
    @Transactional
    void getAllProducersByPayedValueIsNullOrNotNull() throws Exception {
        // Initialize the database
        producerRepository.saveAndFlush(producer);

        // Get all the producerList where payedValue is not null
        defaultProducerShouldBeFound("payedValue.specified=true");

        // Get all the producerList where payedValue is null
        defaultProducerShouldNotBeFound("payedValue.specified=false");
    }

    @Test
    @Transactional
    void getAllProducersByPayedValueIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        producerRepository.saveAndFlush(producer);

        // Get all the producerList where payedValue is greater than or equal to DEFAULT_PAYED_VALUE
        defaultProducerShouldBeFound("payedValue.greaterThanOrEqual=" + DEFAULT_PAYED_VALUE);

        // Get all the producerList where payedValue is greater than or equal to UPDATED_PAYED_VALUE
        defaultProducerShouldNotBeFound("payedValue.greaterThanOrEqual=" + UPDATED_PAYED_VALUE);
    }

    @Test
    @Transactional
    void getAllProducersByPayedValueIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        producerRepository.saveAndFlush(producer);

        // Get all the producerList where payedValue is less than or equal to DEFAULT_PAYED_VALUE
        defaultProducerShouldBeFound("payedValue.lessThanOrEqual=" + DEFAULT_PAYED_VALUE);

        // Get all the producerList where payedValue is less than or equal to SMALLER_PAYED_VALUE
        defaultProducerShouldNotBeFound("payedValue.lessThanOrEqual=" + SMALLER_PAYED_VALUE);
    }

    @Test
    @Transactional
    void getAllProducersByPayedValueIsLessThanSomething() throws Exception {
        // Initialize the database
        producerRepository.saveAndFlush(producer);

        // Get all the producerList where payedValue is less than DEFAULT_PAYED_VALUE
        defaultProducerShouldNotBeFound("payedValue.lessThan=" + DEFAULT_PAYED_VALUE);

        // Get all the producerList where payedValue is less than UPDATED_PAYED_VALUE
        defaultProducerShouldBeFound("payedValue.lessThan=" + UPDATED_PAYED_VALUE);
    }

    @Test
    @Transactional
    void getAllProducersByPayedValueIsGreaterThanSomething() throws Exception {
        // Initialize the database
        producerRepository.saveAndFlush(producer);

        // Get all the producerList where payedValue is greater than DEFAULT_PAYED_VALUE
        defaultProducerShouldNotBeFound("payedValue.greaterThan=" + DEFAULT_PAYED_VALUE);

        // Get all the producerList where payedValue is greater than SMALLER_PAYED_VALUE
        defaultProducerShouldBeFound("payedValue.greaterThan=" + SMALLER_PAYED_VALUE);
    }

    @Test
    @Transactional
    void getAllProducersByValueToPayIsEqualToSomething() throws Exception {
        // Initialize the database
        producerRepository.saveAndFlush(producer);

        // Get all the producerList where valueToPay equals to DEFAULT_VALUE_TO_PAY
        defaultProducerShouldBeFound("valueToPay.equals=" + DEFAULT_VALUE_TO_PAY);

        // Get all the producerList where valueToPay equals to UPDATED_VALUE_TO_PAY
        defaultProducerShouldNotBeFound("valueToPay.equals=" + UPDATED_VALUE_TO_PAY);
    }

    @Test
    @Transactional
    void getAllProducersByValueToPayIsNotEqualToSomething() throws Exception {
        // Initialize the database
        producerRepository.saveAndFlush(producer);

        // Get all the producerList where valueToPay not equals to DEFAULT_VALUE_TO_PAY
        defaultProducerShouldNotBeFound("valueToPay.notEquals=" + DEFAULT_VALUE_TO_PAY);

        // Get all the producerList where valueToPay not equals to UPDATED_VALUE_TO_PAY
        defaultProducerShouldBeFound("valueToPay.notEquals=" + UPDATED_VALUE_TO_PAY);
    }

    @Test
    @Transactional
    void getAllProducersByValueToPayIsInShouldWork() throws Exception {
        // Initialize the database
        producerRepository.saveAndFlush(producer);

        // Get all the producerList where valueToPay in DEFAULT_VALUE_TO_PAY or UPDATED_VALUE_TO_PAY
        defaultProducerShouldBeFound("valueToPay.in=" + DEFAULT_VALUE_TO_PAY + "," + UPDATED_VALUE_TO_PAY);

        // Get all the producerList where valueToPay equals to UPDATED_VALUE_TO_PAY
        defaultProducerShouldNotBeFound("valueToPay.in=" + UPDATED_VALUE_TO_PAY);
    }

    @Test
    @Transactional
    void getAllProducersByValueToPayIsNullOrNotNull() throws Exception {
        // Initialize the database
        producerRepository.saveAndFlush(producer);

        // Get all the producerList where valueToPay is not null
        defaultProducerShouldBeFound("valueToPay.specified=true");

        // Get all the producerList where valueToPay is null
        defaultProducerShouldNotBeFound("valueToPay.specified=false");
    }

    @Test
    @Transactional
    void getAllProducersByValueToPayIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        producerRepository.saveAndFlush(producer);

        // Get all the producerList where valueToPay is greater than or equal to DEFAULT_VALUE_TO_PAY
        defaultProducerShouldBeFound("valueToPay.greaterThanOrEqual=" + DEFAULT_VALUE_TO_PAY);

        // Get all the producerList where valueToPay is greater than or equal to UPDATED_VALUE_TO_PAY
        defaultProducerShouldNotBeFound("valueToPay.greaterThanOrEqual=" + UPDATED_VALUE_TO_PAY);
    }

    @Test
    @Transactional
    void getAllProducersByValueToPayIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        producerRepository.saveAndFlush(producer);

        // Get all the producerList where valueToPay is less than or equal to DEFAULT_VALUE_TO_PAY
        defaultProducerShouldBeFound("valueToPay.lessThanOrEqual=" + DEFAULT_VALUE_TO_PAY);

        // Get all the producerList where valueToPay is less than or equal to SMALLER_VALUE_TO_PAY
        defaultProducerShouldNotBeFound("valueToPay.lessThanOrEqual=" + SMALLER_VALUE_TO_PAY);
    }

    @Test
    @Transactional
    void getAllProducersByValueToPayIsLessThanSomething() throws Exception {
        // Initialize the database
        producerRepository.saveAndFlush(producer);

        // Get all the producerList where valueToPay is less than DEFAULT_VALUE_TO_PAY
        defaultProducerShouldNotBeFound("valueToPay.lessThan=" + DEFAULT_VALUE_TO_PAY);

        // Get all the producerList where valueToPay is less than UPDATED_VALUE_TO_PAY
        defaultProducerShouldBeFound("valueToPay.lessThan=" + UPDATED_VALUE_TO_PAY);
    }

    @Test
    @Transactional
    void getAllProducersByValueToPayIsGreaterThanSomething() throws Exception {
        // Initialize the database
        producerRepository.saveAndFlush(producer);

        // Get all the producerList where valueToPay is greater than DEFAULT_VALUE_TO_PAY
        defaultProducerShouldNotBeFound("valueToPay.greaterThan=" + DEFAULT_VALUE_TO_PAY);

        // Get all the producerList where valueToPay is greater than SMALLER_VALUE_TO_PAY
        defaultProducerShouldBeFound("valueToPay.greaterThan=" + SMALLER_VALUE_TO_PAY);
    }

    @Test
    @Transactional
    void getAllProducersByRankingIsEqualToSomething() throws Exception {
        // Initialize the database
        producerRepository.saveAndFlush(producer);

        // Get all the producerList where ranking equals to DEFAULT_RANKING
        defaultProducerShouldBeFound("ranking.equals=" + DEFAULT_RANKING);

        // Get all the producerList where ranking equals to UPDATED_RANKING
        defaultProducerShouldNotBeFound("ranking.equals=" + UPDATED_RANKING);
    }

    @Test
    @Transactional
    void getAllProducersByRankingIsNotEqualToSomething() throws Exception {
        // Initialize the database
        producerRepository.saveAndFlush(producer);

        // Get all the producerList where ranking not equals to DEFAULT_RANKING
        defaultProducerShouldNotBeFound("ranking.notEquals=" + DEFAULT_RANKING);

        // Get all the producerList where ranking not equals to UPDATED_RANKING
        defaultProducerShouldBeFound("ranking.notEquals=" + UPDATED_RANKING);
    }

    @Test
    @Transactional
    void getAllProducersByRankingIsInShouldWork() throws Exception {
        // Initialize the database
        producerRepository.saveAndFlush(producer);

        // Get all the producerList where ranking in DEFAULT_RANKING or UPDATED_RANKING
        defaultProducerShouldBeFound("ranking.in=" + DEFAULT_RANKING + "," + UPDATED_RANKING);

        // Get all the producerList where ranking equals to UPDATED_RANKING
        defaultProducerShouldNotBeFound("ranking.in=" + UPDATED_RANKING);
    }

    @Test
    @Transactional
    void getAllProducersByRankingIsNullOrNotNull() throws Exception {
        // Initialize the database
        producerRepository.saveAndFlush(producer);

        // Get all the producerList where ranking is not null
        defaultProducerShouldBeFound("ranking.specified=true");

        // Get all the producerList where ranking is null
        defaultProducerShouldNotBeFound("ranking.specified=false");
    }

    @Test
    @Transactional
    void getAllProducersByRankingIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        producerRepository.saveAndFlush(producer);

        // Get all the producerList where ranking is greater than or equal to DEFAULT_RANKING
        defaultProducerShouldBeFound("ranking.greaterThanOrEqual=" + DEFAULT_RANKING);

        // Get all the producerList where ranking is greater than or equal to UPDATED_RANKING
        defaultProducerShouldNotBeFound("ranking.greaterThanOrEqual=" + UPDATED_RANKING);
    }

    @Test
    @Transactional
    void getAllProducersByRankingIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        producerRepository.saveAndFlush(producer);

        // Get all the producerList where ranking is less than or equal to DEFAULT_RANKING
        defaultProducerShouldBeFound("ranking.lessThanOrEqual=" + DEFAULT_RANKING);

        // Get all the producerList where ranking is less than or equal to SMALLER_RANKING
        defaultProducerShouldNotBeFound("ranking.lessThanOrEqual=" + SMALLER_RANKING);
    }

    @Test
    @Transactional
    void getAllProducersByRankingIsLessThanSomething() throws Exception {
        // Initialize the database
        producerRepository.saveAndFlush(producer);

        // Get all the producerList where ranking is less than DEFAULT_RANKING
        defaultProducerShouldNotBeFound("ranking.lessThan=" + DEFAULT_RANKING);

        // Get all the producerList where ranking is less than UPDATED_RANKING
        defaultProducerShouldBeFound("ranking.lessThan=" + UPDATED_RANKING);
    }

    @Test
    @Transactional
    void getAllProducersByRankingIsGreaterThanSomething() throws Exception {
        // Initialize the database
        producerRepository.saveAndFlush(producer);

        // Get all the producerList where ranking is greater than DEFAULT_RANKING
        defaultProducerShouldNotBeFound("ranking.greaterThan=" + DEFAULT_RANKING);

        // Get all the producerList where ranking is greater than SMALLER_RANKING
        defaultProducerShouldBeFound("ranking.greaterThan=" + SMALLER_RANKING);
    }

    @Test
    @Transactional
    void getAllProducersByUserInfoIsEqualToSomething() throws Exception {
        // Get already existing entity
        UserInfo userInfo = producer.getUserInfo();
        producerRepository.saveAndFlush(producer);
        Long userInfoId = userInfo.getId();

        // Get all the producerList where userInfo equals to userInfoId
        defaultProducerShouldBeFound("userInfoId.equals=" + userInfoId);

        // Get all the producerList where userInfo equals to (userInfoId + 1)
        defaultProducerShouldNotBeFound("userInfoId.equals=" + (userInfoId + 1));
    }

    @Test
    @Transactional
    void getAllProducersByRequestIsEqualToSomething() throws Exception {
        // Initialize the database
        producerRepository.saveAndFlush(producer);
        Request request = RequestResourceIT.createEntity(em);
        em.persist(request);
        em.flush();
        producer.addRequest(request);
        producerRepository.saveAndFlush(producer);
        Long requestId = request.getId();

        // Get all the producerList where request equals to requestId
        defaultProducerShouldBeFound("requestId.equals=" + requestId);

        // Get all the producerList where request equals to (requestId + 1)
        defaultProducerShouldNotBeFound("requestId.equals=" + (requestId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultProducerShouldBeFound(String filter) throws Exception {
        restProducerMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(producer.getId().intValue())))
            .andExpect(jsonPath("$.[*].linkSocial").value(hasItem(DEFAULT_LINK_SOCIAL)))
            .andExpect(jsonPath("$.[*].numberRequests").value(hasItem(DEFAULT_NUMBER_REQUESTS)))
            .andExpect(jsonPath("$.[*].payedValue").value(hasItem(DEFAULT_PAYED_VALUE.doubleValue())))
            .andExpect(jsonPath("$.[*].valueToPay").value(hasItem(DEFAULT_VALUE_TO_PAY.doubleValue())))
            .andExpect(jsonPath("$.[*].ranking").value(hasItem(DEFAULT_RANKING.doubleValue())));

        // Check, that the count call also returns 1
        restProducerMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultProducerShouldNotBeFound(String filter) throws Exception {
        restProducerMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restProducerMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingProducer() throws Exception {
        // Get the producer
        restProducerMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewProducer() throws Exception {
        // Initialize the database
        producerRepository.saveAndFlush(producer);

        int databaseSizeBeforeUpdate = producerRepository.findAll().size();

        // Update the producer
        Producer updatedProducer = producerRepository.findById(producer.getId()).get();
        // Disconnect from session so that the updates on updatedProducer are not directly saved in db
        em.detach(updatedProducer);
        updatedProducer
            .linkSocial(UPDATED_LINK_SOCIAL)
            .numberRequests(UPDATED_NUMBER_REQUESTS)
            .payedValue(UPDATED_PAYED_VALUE)
            .valueToPay(UPDATED_VALUE_TO_PAY)
            .ranking(UPDATED_RANKING);
        ProducerDTO producerDTO = producerMapper.toDto(updatedProducer);

        restProducerMockMvc
            .perform(
                put(ENTITY_API_URL_ID, producerDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(producerDTO))
            )
            .andExpect(status().isOk());

        // Validate the Producer in the database
        List<Producer> producerList = producerRepository.findAll();
        assertThat(producerList).hasSize(databaseSizeBeforeUpdate);
        Producer testProducer = producerList.get(producerList.size() - 1);
        assertThat(testProducer.getLinkSocial()).isEqualTo(UPDATED_LINK_SOCIAL);
        assertThat(testProducer.getNumberRequests()).isEqualTo(UPDATED_NUMBER_REQUESTS);
        assertThat(testProducer.getPayedValue()).isEqualTo(UPDATED_PAYED_VALUE);
        assertThat(testProducer.getValueToPay()).isEqualTo(UPDATED_VALUE_TO_PAY);
        assertThat(testProducer.getRanking()).isEqualTo(UPDATED_RANKING);
    }

    @Test
    @Transactional
    void putNonExistingProducer() throws Exception {
        int databaseSizeBeforeUpdate = producerRepository.findAll().size();
        producer.setId(count.incrementAndGet());

        // Create the Producer
        ProducerDTO producerDTO = producerMapper.toDto(producer);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProducerMockMvc
            .perform(
                put(ENTITY_API_URL_ID, producerDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(producerDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Producer in the database
        List<Producer> producerList = producerRepository.findAll();
        assertThat(producerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchProducer() throws Exception {
        int databaseSizeBeforeUpdate = producerRepository.findAll().size();
        producer.setId(count.incrementAndGet());

        // Create the Producer
        ProducerDTO producerDTO = producerMapper.toDto(producer);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProducerMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(producerDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Producer in the database
        List<Producer> producerList = producerRepository.findAll();
        assertThat(producerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamProducer() throws Exception {
        int databaseSizeBeforeUpdate = producerRepository.findAll().size();
        producer.setId(count.incrementAndGet());

        // Create the Producer
        ProducerDTO producerDTO = producerMapper.toDto(producer);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProducerMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(producerDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Producer in the database
        List<Producer> producerList = producerRepository.findAll();
        assertThat(producerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateProducerWithPatch() throws Exception {
        // Initialize the database
        producerRepository.saveAndFlush(producer);

        int databaseSizeBeforeUpdate = producerRepository.findAll().size();

        // Update the producer using partial update
        Producer partialUpdatedProducer = new Producer();
        partialUpdatedProducer.setId(producer.getId());

        partialUpdatedProducer.numberRequests(UPDATED_NUMBER_REQUESTS).payedValue(UPDATED_PAYED_VALUE);

        restProducerMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedProducer.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedProducer))
            )
            .andExpect(status().isOk());

        // Validate the Producer in the database
        List<Producer> producerList = producerRepository.findAll();
        assertThat(producerList).hasSize(databaseSizeBeforeUpdate);
        Producer testProducer = producerList.get(producerList.size() - 1);
        assertThat(testProducer.getLinkSocial()).isEqualTo(DEFAULT_LINK_SOCIAL);
        assertThat(testProducer.getNumberRequests()).isEqualTo(UPDATED_NUMBER_REQUESTS);
        assertThat(testProducer.getPayedValue()).isEqualTo(UPDATED_PAYED_VALUE);
        assertThat(testProducer.getValueToPay()).isEqualTo(DEFAULT_VALUE_TO_PAY);
        assertThat(testProducer.getRanking()).isEqualTo(DEFAULT_RANKING);
    }

    @Test
    @Transactional
    void fullUpdateProducerWithPatch() throws Exception {
        // Initialize the database
        producerRepository.saveAndFlush(producer);

        int databaseSizeBeforeUpdate = producerRepository.findAll().size();

        // Update the producer using partial update
        Producer partialUpdatedProducer = new Producer();
        partialUpdatedProducer.setId(producer.getId());

        partialUpdatedProducer
            .linkSocial(UPDATED_LINK_SOCIAL)
            .numberRequests(UPDATED_NUMBER_REQUESTS)
            .payedValue(UPDATED_PAYED_VALUE)
            .valueToPay(UPDATED_VALUE_TO_PAY)
            .ranking(UPDATED_RANKING);

        restProducerMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedProducer.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedProducer))
            )
            .andExpect(status().isOk());

        // Validate the Producer in the database
        List<Producer> producerList = producerRepository.findAll();
        assertThat(producerList).hasSize(databaseSizeBeforeUpdate);
        Producer testProducer = producerList.get(producerList.size() - 1);
        assertThat(testProducer.getLinkSocial()).isEqualTo(UPDATED_LINK_SOCIAL);
        assertThat(testProducer.getNumberRequests()).isEqualTo(UPDATED_NUMBER_REQUESTS);
        assertThat(testProducer.getPayedValue()).isEqualTo(UPDATED_PAYED_VALUE);
        assertThat(testProducer.getValueToPay()).isEqualTo(UPDATED_VALUE_TO_PAY);
        assertThat(testProducer.getRanking()).isEqualTo(UPDATED_RANKING);
    }

    @Test
    @Transactional
    void patchNonExistingProducer() throws Exception {
        int databaseSizeBeforeUpdate = producerRepository.findAll().size();
        producer.setId(count.incrementAndGet());

        // Create the Producer
        ProducerDTO producerDTO = producerMapper.toDto(producer);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProducerMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, producerDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(producerDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Producer in the database
        List<Producer> producerList = producerRepository.findAll();
        assertThat(producerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchProducer() throws Exception {
        int databaseSizeBeforeUpdate = producerRepository.findAll().size();
        producer.setId(count.incrementAndGet());

        // Create the Producer
        ProducerDTO producerDTO = producerMapper.toDto(producer);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProducerMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(producerDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Producer in the database
        List<Producer> producerList = producerRepository.findAll();
        assertThat(producerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamProducer() throws Exception {
        int databaseSizeBeforeUpdate = producerRepository.findAll().size();
        producer.setId(count.incrementAndGet());

        // Create the Producer
        ProducerDTO producerDTO = producerMapper.toDto(producer);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProducerMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(producerDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Producer in the database
        List<Producer> producerList = producerRepository.findAll();
        assertThat(producerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteProducer() throws Exception {
        // Initialize the database
        producerRepository.saveAndFlush(producer);

        int databaseSizeBeforeDelete = producerRepository.findAll().size();

        // Delete the producer
        restProducerMockMvc
            .perform(delete(ENTITY_API_URL_ID, producer.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Producer> producerList = producerRepository.findAll();
        assertThat(producerList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
