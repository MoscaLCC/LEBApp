package com.leb.app.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.leb.app.IntegrationTest;
import com.leb.app.domain.Producer;
import com.leb.app.domain.Request;
import com.leb.app.repository.ProducerRepository;
import com.leb.app.service.criteria.ProducerCriteria;
import com.leb.app.service.dto.ProducerDTO;
import com.leb.app.service.mapper.ProducerMapper;
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
 * Integration tests for the {@link ProducerResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ProducerResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_MAIL = "AAAAAAAAAA";
    private static final String UPDATED_MAIL = "BBBBBBBBBB";

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

    private static final String DEFAULT_ADRESS = "AAAAAAAAAA";
    private static final String UPDATED_ADRESS = "BBBBBBBBBB";

    private static final String DEFAULT_PHOTO = "AAAAAAAAAA";
    private static final String UPDATED_PHOTO = "BBBBBBBBBB";

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
            .name(DEFAULT_NAME)
            .mail(DEFAULT_MAIL)
            .phoneNumber(DEFAULT_PHONE_NUMBER)
            .nib(DEFAULT_NIB)
            .nif(DEFAULT_NIF)
            .birthday(DEFAULT_BIRTHDAY)
            .adress(DEFAULT_ADRESS)
            .photo(DEFAULT_PHOTO)
            .linkSocial(DEFAULT_LINK_SOCIAL)
            .numberRequests(DEFAULT_NUMBER_REQUESTS)
            .payedValue(DEFAULT_PAYED_VALUE)
            .valueToPay(DEFAULT_VALUE_TO_PAY)
            .ranking(DEFAULT_RANKING);
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
            .name(UPDATED_NAME)
            .mail(UPDATED_MAIL)
            .phoneNumber(UPDATED_PHONE_NUMBER)
            .nib(UPDATED_NIB)
            .nif(UPDATED_NIF)
            .birthday(UPDATED_BIRTHDAY)
            .adress(UPDATED_ADRESS)
            .photo(UPDATED_PHOTO)
            .linkSocial(UPDATED_LINK_SOCIAL)
            .numberRequests(UPDATED_NUMBER_REQUESTS)
            .payedValue(UPDATED_PAYED_VALUE)
            .valueToPay(UPDATED_VALUE_TO_PAY)
            .ranking(UPDATED_RANKING);
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
        assertThat(testProducer.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testProducer.getMail()).isEqualTo(DEFAULT_MAIL);
        assertThat(testProducer.getPhoneNumber()).isEqualTo(DEFAULT_PHONE_NUMBER);
        assertThat(testProducer.getNib()).isEqualTo(DEFAULT_NIB);
        assertThat(testProducer.getNif()).isEqualTo(DEFAULT_NIF);
        assertThat(testProducer.getBirthday()).isEqualTo(DEFAULT_BIRTHDAY);
        assertThat(testProducer.getAdress()).isEqualTo(DEFAULT_ADRESS);
        assertThat(testProducer.getPhoto()).isEqualTo(DEFAULT_PHOTO);
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
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].mail").value(hasItem(DEFAULT_MAIL)))
            .andExpect(jsonPath("$.[*].phoneNumber").value(hasItem(DEFAULT_PHONE_NUMBER)))
            .andExpect(jsonPath("$.[*].nib").value(hasItem(DEFAULT_NIB)))
            .andExpect(jsonPath("$.[*].nif").value(hasItem(DEFAULT_NIF)))
            .andExpect(jsonPath("$.[*].birthday").value(hasItem(DEFAULT_BIRTHDAY.toString())))
            .andExpect(jsonPath("$.[*].adress").value(hasItem(DEFAULT_ADRESS)))
            .andExpect(jsonPath("$.[*].photo").value(hasItem(DEFAULT_PHOTO)))
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
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.mail").value(DEFAULT_MAIL))
            .andExpect(jsonPath("$.phoneNumber").value(DEFAULT_PHONE_NUMBER))
            .andExpect(jsonPath("$.nib").value(DEFAULT_NIB))
            .andExpect(jsonPath("$.nif").value(DEFAULT_NIF))
            .andExpect(jsonPath("$.birthday").value(DEFAULT_BIRTHDAY.toString()))
            .andExpect(jsonPath("$.adress").value(DEFAULT_ADRESS))
            .andExpect(jsonPath("$.photo").value(DEFAULT_PHOTO))
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
    void getAllProducersByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        producerRepository.saveAndFlush(producer);

        // Get all the producerList where name equals to DEFAULT_NAME
        defaultProducerShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the producerList where name equals to UPDATED_NAME
        defaultProducerShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllProducersByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        producerRepository.saveAndFlush(producer);

        // Get all the producerList where name not equals to DEFAULT_NAME
        defaultProducerShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the producerList where name not equals to UPDATED_NAME
        defaultProducerShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllProducersByNameIsInShouldWork() throws Exception {
        // Initialize the database
        producerRepository.saveAndFlush(producer);

        // Get all the producerList where name in DEFAULT_NAME or UPDATED_NAME
        defaultProducerShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the producerList where name equals to UPDATED_NAME
        defaultProducerShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllProducersByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        producerRepository.saveAndFlush(producer);

        // Get all the producerList where name is not null
        defaultProducerShouldBeFound("name.specified=true");

        // Get all the producerList where name is null
        defaultProducerShouldNotBeFound("name.specified=false");
    }

    @Test
    @Transactional
    void getAllProducersByNameContainsSomething() throws Exception {
        // Initialize the database
        producerRepository.saveAndFlush(producer);

        // Get all the producerList where name contains DEFAULT_NAME
        defaultProducerShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the producerList where name contains UPDATED_NAME
        defaultProducerShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllProducersByNameNotContainsSomething() throws Exception {
        // Initialize the database
        producerRepository.saveAndFlush(producer);

        // Get all the producerList where name does not contain DEFAULT_NAME
        defaultProducerShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the producerList where name does not contain UPDATED_NAME
        defaultProducerShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllProducersByMailIsEqualToSomething() throws Exception {
        // Initialize the database
        producerRepository.saveAndFlush(producer);

        // Get all the producerList where mail equals to DEFAULT_MAIL
        defaultProducerShouldBeFound("mail.equals=" + DEFAULT_MAIL);

        // Get all the producerList where mail equals to UPDATED_MAIL
        defaultProducerShouldNotBeFound("mail.equals=" + UPDATED_MAIL);
    }

    @Test
    @Transactional
    void getAllProducersByMailIsNotEqualToSomething() throws Exception {
        // Initialize the database
        producerRepository.saveAndFlush(producer);

        // Get all the producerList where mail not equals to DEFAULT_MAIL
        defaultProducerShouldNotBeFound("mail.notEquals=" + DEFAULT_MAIL);

        // Get all the producerList where mail not equals to UPDATED_MAIL
        defaultProducerShouldBeFound("mail.notEquals=" + UPDATED_MAIL);
    }

    @Test
    @Transactional
    void getAllProducersByMailIsInShouldWork() throws Exception {
        // Initialize the database
        producerRepository.saveAndFlush(producer);

        // Get all the producerList where mail in DEFAULT_MAIL or UPDATED_MAIL
        defaultProducerShouldBeFound("mail.in=" + DEFAULT_MAIL + "," + UPDATED_MAIL);

        // Get all the producerList where mail equals to UPDATED_MAIL
        defaultProducerShouldNotBeFound("mail.in=" + UPDATED_MAIL);
    }

    @Test
    @Transactional
    void getAllProducersByMailIsNullOrNotNull() throws Exception {
        // Initialize the database
        producerRepository.saveAndFlush(producer);

        // Get all the producerList where mail is not null
        defaultProducerShouldBeFound("mail.specified=true");

        // Get all the producerList where mail is null
        defaultProducerShouldNotBeFound("mail.specified=false");
    }

    @Test
    @Transactional
    void getAllProducersByMailContainsSomething() throws Exception {
        // Initialize the database
        producerRepository.saveAndFlush(producer);

        // Get all the producerList where mail contains DEFAULT_MAIL
        defaultProducerShouldBeFound("mail.contains=" + DEFAULT_MAIL);

        // Get all the producerList where mail contains UPDATED_MAIL
        defaultProducerShouldNotBeFound("mail.contains=" + UPDATED_MAIL);
    }

    @Test
    @Transactional
    void getAllProducersByMailNotContainsSomething() throws Exception {
        // Initialize the database
        producerRepository.saveAndFlush(producer);

        // Get all the producerList where mail does not contain DEFAULT_MAIL
        defaultProducerShouldNotBeFound("mail.doesNotContain=" + DEFAULT_MAIL);

        // Get all the producerList where mail does not contain UPDATED_MAIL
        defaultProducerShouldBeFound("mail.doesNotContain=" + UPDATED_MAIL);
    }

    @Test
    @Transactional
    void getAllProducersByPhoneNumberIsEqualToSomething() throws Exception {
        // Initialize the database
        producerRepository.saveAndFlush(producer);

        // Get all the producerList where phoneNumber equals to DEFAULT_PHONE_NUMBER
        defaultProducerShouldBeFound("phoneNumber.equals=" + DEFAULT_PHONE_NUMBER);

        // Get all the producerList where phoneNumber equals to UPDATED_PHONE_NUMBER
        defaultProducerShouldNotBeFound("phoneNumber.equals=" + UPDATED_PHONE_NUMBER);
    }

    @Test
    @Transactional
    void getAllProducersByPhoneNumberIsNotEqualToSomething() throws Exception {
        // Initialize the database
        producerRepository.saveAndFlush(producer);

        // Get all the producerList where phoneNumber not equals to DEFAULT_PHONE_NUMBER
        defaultProducerShouldNotBeFound("phoneNumber.notEquals=" + DEFAULT_PHONE_NUMBER);

        // Get all the producerList where phoneNumber not equals to UPDATED_PHONE_NUMBER
        defaultProducerShouldBeFound("phoneNumber.notEquals=" + UPDATED_PHONE_NUMBER);
    }

    @Test
    @Transactional
    void getAllProducersByPhoneNumberIsInShouldWork() throws Exception {
        // Initialize the database
        producerRepository.saveAndFlush(producer);

        // Get all the producerList where phoneNumber in DEFAULT_PHONE_NUMBER or UPDATED_PHONE_NUMBER
        defaultProducerShouldBeFound("phoneNumber.in=" + DEFAULT_PHONE_NUMBER + "," + UPDATED_PHONE_NUMBER);

        // Get all the producerList where phoneNumber equals to UPDATED_PHONE_NUMBER
        defaultProducerShouldNotBeFound("phoneNumber.in=" + UPDATED_PHONE_NUMBER);
    }

    @Test
    @Transactional
    void getAllProducersByPhoneNumberIsNullOrNotNull() throws Exception {
        // Initialize the database
        producerRepository.saveAndFlush(producer);

        // Get all the producerList where phoneNumber is not null
        defaultProducerShouldBeFound("phoneNumber.specified=true");

        // Get all the producerList where phoneNumber is null
        defaultProducerShouldNotBeFound("phoneNumber.specified=false");
    }

    @Test
    @Transactional
    void getAllProducersByPhoneNumberContainsSomething() throws Exception {
        // Initialize the database
        producerRepository.saveAndFlush(producer);

        // Get all the producerList where phoneNumber contains DEFAULT_PHONE_NUMBER
        defaultProducerShouldBeFound("phoneNumber.contains=" + DEFAULT_PHONE_NUMBER);

        // Get all the producerList where phoneNumber contains UPDATED_PHONE_NUMBER
        defaultProducerShouldNotBeFound("phoneNumber.contains=" + UPDATED_PHONE_NUMBER);
    }

    @Test
    @Transactional
    void getAllProducersByPhoneNumberNotContainsSomething() throws Exception {
        // Initialize the database
        producerRepository.saveAndFlush(producer);

        // Get all the producerList where phoneNumber does not contain DEFAULT_PHONE_NUMBER
        defaultProducerShouldNotBeFound("phoneNumber.doesNotContain=" + DEFAULT_PHONE_NUMBER);

        // Get all the producerList where phoneNumber does not contain UPDATED_PHONE_NUMBER
        defaultProducerShouldBeFound("phoneNumber.doesNotContain=" + UPDATED_PHONE_NUMBER);
    }

    @Test
    @Transactional
    void getAllProducersByNibIsEqualToSomething() throws Exception {
        // Initialize the database
        producerRepository.saveAndFlush(producer);

        // Get all the producerList where nib equals to DEFAULT_NIB
        defaultProducerShouldBeFound("nib.equals=" + DEFAULT_NIB);

        // Get all the producerList where nib equals to UPDATED_NIB
        defaultProducerShouldNotBeFound("nib.equals=" + UPDATED_NIB);
    }

    @Test
    @Transactional
    void getAllProducersByNibIsNotEqualToSomething() throws Exception {
        // Initialize the database
        producerRepository.saveAndFlush(producer);

        // Get all the producerList where nib not equals to DEFAULT_NIB
        defaultProducerShouldNotBeFound("nib.notEquals=" + DEFAULT_NIB);

        // Get all the producerList where nib not equals to UPDATED_NIB
        defaultProducerShouldBeFound("nib.notEquals=" + UPDATED_NIB);
    }

    @Test
    @Transactional
    void getAllProducersByNibIsInShouldWork() throws Exception {
        // Initialize the database
        producerRepository.saveAndFlush(producer);

        // Get all the producerList where nib in DEFAULT_NIB or UPDATED_NIB
        defaultProducerShouldBeFound("nib.in=" + DEFAULT_NIB + "," + UPDATED_NIB);

        // Get all the producerList where nib equals to UPDATED_NIB
        defaultProducerShouldNotBeFound("nib.in=" + UPDATED_NIB);
    }

    @Test
    @Transactional
    void getAllProducersByNibIsNullOrNotNull() throws Exception {
        // Initialize the database
        producerRepository.saveAndFlush(producer);

        // Get all the producerList where nib is not null
        defaultProducerShouldBeFound("nib.specified=true");

        // Get all the producerList where nib is null
        defaultProducerShouldNotBeFound("nib.specified=false");
    }

    @Test
    @Transactional
    void getAllProducersByNibContainsSomething() throws Exception {
        // Initialize the database
        producerRepository.saveAndFlush(producer);

        // Get all the producerList where nib contains DEFAULT_NIB
        defaultProducerShouldBeFound("nib.contains=" + DEFAULT_NIB);

        // Get all the producerList where nib contains UPDATED_NIB
        defaultProducerShouldNotBeFound("nib.contains=" + UPDATED_NIB);
    }

    @Test
    @Transactional
    void getAllProducersByNibNotContainsSomething() throws Exception {
        // Initialize the database
        producerRepository.saveAndFlush(producer);

        // Get all the producerList where nib does not contain DEFAULT_NIB
        defaultProducerShouldNotBeFound("nib.doesNotContain=" + DEFAULT_NIB);

        // Get all the producerList where nib does not contain UPDATED_NIB
        defaultProducerShouldBeFound("nib.doesNotContain=" + UPDATED_NIB);
    }

    @Test
    @Transactional
    void getAllProducersByNifIsEqualToSomething() throws Exception {
        // Initialize the database
        producerRepository.saveAndFlush(producer);

        // Get all the producerList where nif equals to DEFAULT_NIF
        defaultProducerShouldBeFound("nif.equals=" + DEFAULT_NIF);

        // Get all the producerList where nif equals to UPDATED_NIF
        defaultProducerShouldNotBeFound("nif.equals=" + UPDATED_NIF);
    }

    @Test
    @Transactional
    void getAllProducersByNifIsNotEqualToSomething() throws Exception {
        // Initialize the database
        producerRepository.saveAndFlush(producer);

        // Get all the producerList where nif not equals to DEFAULT_NIF
        defaultProducerShouldNotBeFound("nif.notEquals=" + DEFAULT_NIF);

        // Get all the producerList where nif not equals to UPDATED_NIF
        defaultProducerShouldBeFound("nif.notEquals=" + UPDATED_NIF);
    }

    @Test
    @Transactional
    void getAllProducersByNifIsInShouldWork() throws Exception {
        // Initialize the database
        producerRepository.saveAndFlush(producer);

        // Get all the producerList where nif in DEFAULT_NIF or UPDATED_NIF
        defaultProducerShouldBeFound("nif.in=" + DEFAULT_NIF + "," + UPDATED_NIF);

        // Get all the producerList where nif equals to UPDATED_NIF
        defaultProducerShouldNotBeFound("nif.in=" + UPDATED_NIF);
    }

    @Test
    @Transactional
    void getAllProducersByNifIsNullOrNotNull() throws Exception {
        // Initialize the database
        producerRepository.saveAndFlush(producer);

        // Get all the producerList where nif is not null
        defaultProducerShouldBeFound("nif.specified=true");

        // Get all the producerList where nif is null
        defaultProducerShouldNotBeFound("nif.specified=false");
    }

    @Test
    @Transactional
    void getAllProducersByNifIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        producerRepository.saveAndFlush(producer);

        // Get all the producerList where nif is greater than or equal to DEFAULT_NIF
        defaultProducerShouldBeFound("nif.greaterThanOrEqual=" + DEFAULT_NIF);

        // Get all the producerList where nif is greater than or equal to UPDATED_NIF
        defaultProducerShouldNotBeFound("nif.greaterThanOrEqual=" + UPDATED_NIF);
    }

    @Test
    @Transactional
    void getAllProducersByNifIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        producerRepository.saveAndFlush(producer);

        // Get all the producerList where nif is less than or equal to DEFAULT_NIF
        defaultProducerShouldBeFound("nif.lessThanOrEqual=" + DEFAULT_NIF);

        // Get all the producerList where nif is less than or equal to SMALLER_NIF
        defaultProducerShouldNotBeFound("nif.lessThanOrEqual=" + SMALLER_NIF);
    }

    @Test
    @Transactional
    void getAllProducersByNifIsLessThanSomething() throws Exception {
        // Initialize the database
        producerRepository.saveAndFlush(producer);

        // Get all the producerList where nif is less than DEFAULT_NIF
        defaultProducerShouldNotBeFound("nif.lessThan=" + DEFAULT_NIF);

        // Get all the producerList where nif is less than UPDATED_NIF
        defaultProducerShouldBeFound("nif.lessThan=" + UPDATED_NIF);
    }

    @Test
    @Transactional
    void getAllProducersByNifIsGreaterThanSomething() throws Exception {
        // Initialize the database
        producerRepository.saveAndFlush(producer);

        // Get all the producerList where nif is greater than DEFAULT_NIF
        defaultProducerShouldNotBeFound("nif.greaterThan=" + DEFAULT_NIF);

        // Get all the producerList where nif is greater than SMALLER_NIF
        defaultProducerShouldBeFound("nif.greaterThan=" + SMALLER_NIF);
    }

    @Test
    @Transactional
    void getAllProducersByBirthdayIsEqualToSomething() throws Exception {
        // Initialize the database
        producerRepository.saveAndFlush(producer);

        // Get all the producerList where birthday equals to DEFAULT_BIRTHDAY
        defaultProducerShouldBeFound("birthday.equals=" + DEFAULT_BIRTHDAY);

        // Get all the producerList where birthday equals to UPDATED_BIRTHDAY
        defaultProducerShouldNotBeFound("birthday.equals=" + UPDATED_BIRTHDAY);
    }

    @Test
    @Transactional
    void getAllProducersByBirthdayIsNotEqualToSomething() throws Exception {
        // Initialize the database
        producerRepository.saveAndFlush(producer);

        // Get all the producerList where birthday not equals to DEFAULT_BIRTHDAY
        defaultProducerShouldNotBeFound("birthday.notEquals=" + DEFAULT_BIRTHDAY);

        // Get all the producerList where birthday not equals to UPDATED_BIRTHDAY
        defaultProducerShouldBeFound("birthday.notEquals=" + UPDATED_BIRTHDAY);
    }

    @Test
    @Transactional
    void getAllProducersByBirthdayIsInShouldWork() throws Exception {
        // Initialize the database
        producerRepository.saveAndFlush(producer);

        // Get all the producerList where birthday in DEFAULT_BIRTHDAY or UPDATED_BIRTHDAY
        defaultProducerShouldBeFound("birthday.in=" + DEFAULT_BIRTHDAY + "," + UPDATED_BIRTHDAY);

        // Get all the producerList where birthday equals to UPDATED_BIRTHDAY
        defaultProducerShouldNotBeFound("birthday.in=" + UPDATED_BIRTHDAY);
    }

    @Test
    @Transactional
    void getAllProducersByBirthdayIsNullOrNotNull() throws Exception {
        // Initialize the database
        producerRepository.saveAndFlush(producer);

        // Get all the producerList where birthday is not null
        defaultProducerShouldBeFound("birthday.specified=true");

        // Get all the producerList where birthday is null
        defaultProducerShouldNotBeFound("birthday.specified=false");
    }

    @Test
    @Transactional
    void getAllProducersByBirthdayIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        producerRepository.saveAndFlush(producer);

        // Get all the producerList where birthday is greater than or equal to DEFAULT_BIRTHDAY
        defaultProducerShouldBeFound("birthday.greaterThanOrEqual=" + DEFAULT_BIRTHDAY);

        // Get all the producerList where birthday is greater than or equal to UPDATED_BIRTHDAY
        defaultProducerShouldNotBeFound("birthday.greaterThanOrEqual=" + UPDATED_BIRTHDAY);
    }

    @Test
    @Transactional
    void getAllProducersByBirthdayIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        producerRepository.saveAndFlush(producer);

        // Get all the producerList where birthday is less than or equal to DEFAULT_BIRTHDAY
        defaultProducerShouldBeFound("birthday.lessThanOrEqual=" + DEFAULT_BIRTHDAY);

        // Get all the producerList where birthday is less than or equal to SMALLER_BIRTHDAY
        defaultProducerShouldNotBeFound("birthday.lessThanOrEqual=" + SMALLER_BIRTHDAY);
    }

    @Test
    @Transactional
    void getAllProducersByBirthdayIsLessThanSomething() throws Exception {
        // Initialize the database
        producerRepository.saveAndFlush(producer);

        // Get all the producerList where birthday is less than DEFAULT_BIRTHDAY
        defaultProducerShouldNotBeFound("birthday.lessThan=" + DEFAULT_BIRTHDAY);

        // Get all the producerList where birthday is less than UPDATED_BIRTHDAY
        defaultProducerShouldBeFound("birthday.lessThan=" + UPDATED_BIRTHDAY);
    }

    @Test
    @Transactional
    void getAllProducersByBirthdayIsGreaterThanSomething() throws Exception {
        // Initialize the database
        producerRepository.saveAndFlush(producer);

        // Get all the producerList where birthday is greater than DEFAULT_BIRTHDAY
        defaultProducerShouldNotBeFound("birthday.greaterThan=" + DEFAULT_BIRTHDAY);

        // Get all the producerList where birthday is greater than SMALLER_BIRTHDAY
        defaultProducerShouldBeFound("birthday.greaterThan=" + SMALLER_BIRTHDAY);
    }

    @Test
    @Transactional
    void getAllProducersByAdressIsEqualToSomething() throws Exception {
        // Initialize the database
        producerRepository.saveAndFlush(producer);

        // Get all the producerList where adress equals to DEFAULT_ADRESS
        defaultProducerShouldBeFound("adress.equals=" + DEFAULT_ADRESS);

        // Get all the producerList where adress equals to UPDATED_ADRESS
        defaultProducerShouldNotBeFound("adress.equals=" + UPDATED_ADRESS);
    }

    @Test
    @Transactional
    void getAllProducersByAdressIsNotEqualToSomething() throws Exception {
        // Initialize the database
        producerRepository.saveAndFlush(producer);

        // Get all the producerList where adress not equals to DEFAULT_ADRESS
        defaultProducerShouldNotBeFound("adress.notEquals=" + DEFAULT_ADRESS);

        // Get all the producerList where adress not equals to UPDATED_ADRESS
        defaultProducerShouldBeFound("adress.notEquals=" + UPDATED_ADRESS);
    }

    @Test
    @Transactional
    void getAllProducersByAdressIsInShouldWork() throws Exception {
        // Initialize the database
        producerRepository.saveAndFlush(producer);

        // Get all the producerList where adress in DEFAULT_ADRESS or UPDATED_ADRESS
        defaultProducerShouldBeFound("adress.in=" + DEFAULT_ADRESS + "," + UPDATED_ADRESS);

        // Get all the producerList where adress equals to UPDATED_ADRESS
        defaultProducerShouldNotBeFound("adress.in=" + UPDATED_ADRESS);
    }

    @Test
    @Transactional
    void getAllProducersByAdressIsNullOrNotNull() throws Exception {
        // Initialize the database
        producerRepository.saveAndFlush(producer);

        // Get all the producerList where adress is not null
        defaultProducerShouldBeFound("adress.specified=true");

        // Get all the producerList where adress is null
        defaultProducerShouldNotBeFound("adress.specified=false");
    }

    @Test
    @Transactional
    void getAllProducersByAdressContainsSomething() throws Exception {
        // Initialize the database
        producerRepository.saveAndFlush(producer);

        // Get all the producerList where adress contains DEFAULT_ADRESS
        defaultProducerShouldBeFound("adress.contains=" + DEFAULT_ADRESS);

        // Get all the producerList where adress contains UPDATED_ADRESS
        defaultProducerShouldNotBeFound("adress.contains=" + UPDATED_ADRESS);
    }

    @Test
    @Transactional
    void getAllProducersByAdressNotContainsSomething() throws Exception {
        // Initialize the database
        producerRepository.saveAndFlush(producer);

        // Get all the producerList where adress does not contain DEFAULT_ADRESS
        defaultProducerShouldNotBeFound("adress.doesNotContain=" + DEFAULT_ADRESS);

        // Get all the producerList where adress does not contain UPDATED_ADRESS
        defaultProducerShouldBeFound("adress.doesNotContain=" + UPDATED_ADRESS);
    }

    @Test
    @Transactional
    void getAllProducersByPhotoIsEqualToSomething() throws Exception {
        // Initialize the database
        producerRepository.saveAndFlush(producer);

        // Get all the producerList where photo equals to DEFAULT_PHOTO
        defaultProducerShouldBeFound("photo.equals=" + DEFAULT_PHOTO);

        // Get all the producerList where photo equals to UPDATED_PHOTO
        defaultProducerShouldNotBeFound("photo.equals=" + UPDATED_PHOTO);
    }

    @Test
    @Transactional
    void getAllProducersByPhotoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        producerRepository.saveAndFlush(producer);

        // Get all the producerList where photo not equals to DEFAULT_PHOTO
        defaultProducerShouldNotBeFound("photo.notEquals=" + DEFAULT_PHOTO);

        // Get all the producerList where photo not equals to UPDATED_PHOTO
        defaultProducerShouldBeFound("photo.notEquals=" + UPDATED_PHOTO);
    }

    @Test
    @Transactional
    void getAllProducersByPhotoIsInShouldWork() throws Exception {
        // Initialize the database
        producerRepository.saveAndFlush(producer);

        // Get all the producerList where photo in DEFAULT_PHOTO or UPDATED_PHOTO
        defaultProducerShouldBeFound("photo.in=" + DEFAULT_PHOTO + "," + UPDATED_PHOTO);

        // Get all the producerList where photo equals to UPDATED_PHOTO
        defaultProducerShouldNotBeFound("photo.in=" + UPDATED_PHOTO);
    }

    @Test
    @Transactional
    void getAllProducersByPhotoIsNullOrNotNull() throws Exception {
        // Initialize the database
        producerRepository.saveAndFlush(producer);

        // Get all the producerList where photo is not null
        defaultProducerShouldBeFound("photo.specified=true");

        // Get all the producerList where photo is null
        defaultProducerShouldNotBeFound("photo.specified=false");
    }

    @Test
    @Transactional
    void getAllProducersByPhotoContainsSomething() throws Exception {
        // Initialize the database
        producerRepository.saveAndFlush(producer);

        // Get all the producerList where photo contains DEFAULT_PHOTO
        defaultProducerShouldBeFound("photo.contains=" + DEFAULT_PHOTO);

        // Get all the producerList where photo contains UPDATED_PHOTO
        defaultProducerShouldNotBeFound("photo.contains=" + UPDATED_PHOTO);
    }

    @Test
    @Transactional
    void getAllProducersByPhotoNotContainsSomething() throws Exception {
        // Initialize the database
        producerRepository.saveAndFlush(producer);

        // Get all the producerList where photo does not contain DEFAULT_PHOTO
        defaultProducerShouldNotBeFound("photo.doesNotContain=" + DEFAULT_PHOTO);

        // Get all the producerList where photo does not contain UPDATED_PHOTO
        defaultProducerShouldBeFound("photo.doesNotContain=" + UPDATED_PHOTO);
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
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].mail").value(hasItem(DEFAULT_MAIL)))
            .andExpect(jsonPath("$.[*].phoneNumber").value(hasItem(DEFAULT_PHONE_NUMBER)))
            .andExpect(jsonPath("$.[*].nib").value(hasItem(DEFAULT_NIB)))
            .andExpect(jsonPath("$.[*].nif").value(hasItem(DEFAULT_NIF)))
            .andExpect(jsonPath("$.[*].birthday").value(hasItem(DEFAULT_BIRTHDAY.toString())))
            .andExpect(jsonPath("$.[*].adress").value(hasItem(DEFAULT_ADRESS)))
            .andExpect(jsonPath("$.[*].photo").value(hasItem(DEFAULT_PHOTO)))
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
            .name(UPDATED_NAME)
            .mail(UPDATED_MAIL)
            .phoneNumber(UPDATED_PHONE_NUMBER)
            .nib(UPDATED_NIB)
            .nif(UPDATED_NIF)
            .birthday(UPDATED_BIRTHDAY)
            .adress(UPDATED_ADRESS)
            .photo(UPDATED_PHOTO)
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
        assertThat(testProducer.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testProducer.getMail()).isEqualTo(UPDATED_MAIL);
        assertThat(testProducer.getPhoneNumber()).isEqualTo(UPDATED_PHONE_NUMBER);
        assertThat(testProducer.getNib()).isEqualTo(UPDATED_NIB);
        assertThat(testProducer.getNif()).isEqualTo(UPDATED_NIF);
        assertThat(testProducer.getBirthday()).isEqualTo(UPDATED_BIRTHDAY);
        assertThat(testProducer.getAdress()).isEqualTo(UPDATED_ADRESS);
        assertThat(testProducer.getPhoto()).isEqualTo(UPDATED_PHOTO);
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

        partialUpdatedProducer
            .mail(UPDATED_MAIL)
            .phoneNumber(UPDATED_PHONE_NUMBER)
            .birthday(UPDATED_BIRTHDAY)
            .photo(UPDATED_PHOTO)
            .linkSocial(UPDATED_LINK_SOCIAL)
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
        assertThat(testProducer.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testProducer.getMail()).isEqualTo(UPDATED_MAIL);
        assertThat(testProducer.getPhoneNumber()).isEqualTo(UPDATED_PHONE_NUMBER);
        assertThat(testProducer.getNib()).isEqualTo(DEFAULT_NIB);
        assertThat(testProducer.getNif()).isEqualTo(DEFAULT_NIF);
        assertThat(testProducer.getBirthday()).isEqualTo(UPDATED_BIRTHDAY);
        assertThat(testProducer.getAdress()).isEqualTo(DEFAULT_ADRESS);
        assertThat(testProducer.getPhoto()).isEqualTo(UPDATED_PHOTO);
        assertThat(testProducer.getLinkSocial()).isEqualTo(UPDATED_LINK_SOCIAL);
        assertThat(testProducer.getNumberRequests()).isEqualTo(DEFAULT_NUMBER_REQUESTS);
        assertThat(testProducer.getPayedValue()).isEqualTo(DEFAULT_PAYED_VALUE);
        assertThat(testProducer.getValueToPay()).isEqualTo(UPDATED_VALUE_TO_PAY);
        assertThat(testProducer.getRanking()).isEqualTo(UPDATED_RANKING);
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
            .name(UPDATED_NAME)
            .mail(UPDATED_MAIL)
            .phoneNumber(UPDATED_PHONE_NUMBER)
            .nib(UPDATED_NIB)
            .nif(UPDATED_NIF)
            .birthday(UPDATED_BIRTHDAY)
            .adress(UPDATED_ADRESS)
            .photo(UPDATED_PHOTO)
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
        assertThat(testProducer.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testProducer.getMail()).isEqualTo(UPDATED_MAIL);
        assertThat(testProducer.getPhoneNumber()).isEqualTo(UPDATED_PHONE_NUMBER);
        assertThat(testProducer.getNib()).isEqualTo(UPDATED_NIB);
        assertThat(testProducer.getNif()).isEqualTo(UPDATED_NIF);
        assertThat(testProducer.getBirthday()).isEqualTo(UPDATED_BIRTHDAY);
        assertThat(testProducer.getAdress()).isEqualTo(UPDATED_ADRESS);
        assertThat(testProducer.getPhoto()).isEqualTo(UPDATED_PHOTO);
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
