package com.leb.app.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.leb.app.IntegrationTest;
import com.leb.app.domain.Point;
import com.leb.app.domain.Request;
import com.leb.app.domain.UserInfo;
import com.leb.app.repository.UserInfoRepository;
import com.leb.app.service.criteria.UserInfoCriteria;
import com.leb.app.service.dto.UserInfoDTO;
import com.leb.app.service.mapper.UserInfoMapper;
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
 * Integration tests for the {@link UserInfoResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class UserInfoResourceIT {

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

    private static final Integer DEFAULT_NUMBER_OF_DELIVERIES = 1;
    private static final Integer UPDATED_NUMBER_OF_DELIVERIES = 2;
    private static final Integer SMALLER_NUMBER_OF_DELIVERIES = 1 - 1;

    private static final Double DEFAULT_NUMBER_OF_KM = 1D;
    private static final Double UPDATED_NUMBER_OF_KM = 2D;
    private static final Double SMALLER_NUMBER_OF_KM = 1D - 1D;

    private static final String ENTITY_API_URL = "/api/user-infos";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private UserInfoRepository userInfoRepository;

    @Autowired
    private UserInfoMapper userInfoMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restUserInfoMockMvc;

    private UserInfo userInfo;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UserInfo createEntity(EntityManager em) {
        UserInfo userInfo = new UserInfo()
            .phoneNumber(DEFAULT_PHONE_NUMBER)
            .nib(DEFAULT_NIB)
            .nif(DEFAULT_NIF)
            .birthday(DEFAULT_BIRTHDAY)
            .address(DEFAULT_ADDRESS)
            .linkSocial(DEFAULT_LINK_SOCIAL)
            .numberRequests(DEFAULT_NUMBER_REQUESTS)
            .payedValue(DEFAULT_PAYED_VALUE)
            .valueToPay(DEFAULT_VALUE_TO_PAY)
            .ranking(DEFAULT_RANKING)
            .numberOfDeliveries(DEFAULT_NUMBER_OF_DELIVERIES)
            .numberOfKm(DEFAULT_NUMBER_OF_KM);
        return userInfo;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UserInfo createUpdatedEntity(EntityManager em) {
        UserInfo userInfo = new UserInfo()
            .phoneNumber(UPDATED_PHONE_NUMBER)
            .nib(UPDATED_NIB)
            .nif(UPDATED_NIF)
            .birthday(UPDATED_BIRTHDAY)
            .address(UPDATED_ADDRESS)
            .linkSocial(UPDATED_LINK_SOCIAL)
            .numberRequests(UPDATED_NUMBER_REQUESTS)
            .payedValue(UPDATED_PAYED_VALUE)
            .valueToPay(UPDATED_VALUE_TO_PAY)
            .ranking(UPDATED_RANKING)
            .numberOfDeliveries(UPDATED_NUMBER_OF_DELIVERIES)
            .numberOfKm(UPDATED_NUMBER_OF_KM);
        return userInfo;
    }

    @BeforeEach
    public void initTest() {
        userInfo = createEntity(em);
    }

    @Test
    @Transactional
    void createUserInfo() throws Exception {
        int databaseSizeBeforeCreate = userInfoRepository.findAll().size();
        // Create the UserInfo
        UserInfoDTO userInfoDTO = userInfoMapper.toDto(userInfo);
        restUserInfoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(userInfoDTO)))
            .andExpect(status().isCreated());

        // Validate the UserInfo in the database
        List<UserInfo> userInfoList = userInfoRepository.findAll();
        assertThat(userInfoList).hasSize(databaseSizeBeforeCreate + 1);
        UserInfo testUserInfo = userInfoList.get(userInfoList.size() - 1);
        assertThat(testUserInfo.getPhoneNumber()).isEqualTo(DEFAULT_PHONE_NUMBER);
        assertThat(testUserInfo.getNib()).isEqualTo(DEFAULT_NIB);
        assertThat(testUserInfo.getNif()).isEqualTo(DEFAULT_NIF);
        assertThat(testUserInfo.getBirthday()).isEqualTo(DEFAULT_BIRTHDAY);
        assertThat(testUserInfo.getAddress()).isEqualTo(DEFAULT_ADDRESS);
        assertThat(testUserInfo.getLinkSocial()).isEqualTo(DEFAULT_LINK_SOCIAL);
        assertThat(testUserInfo.getNumberRequests()).isEqualTo(DEFAULT_NUMBER_REQUESTS);
        assertThat(testUserInfo.getPayedValue()).isEqualTo(DEFAULT_PAYED_VALUE);
        assertThat(testUserInfo.getValueToPay()).isEqualTo(DEFAULT_VALUE_TO_PAY);
        assertThat(testUserInfo.getRanking()).isEqualTo(DEFAULT_RANKING);
        assertThat(testUserInfo.getNumberOfDeliveries()).isEqualTo(DEFAULT_NUMBER_OF_DELIVERIES);
        assertThat(testUserInfo.getNumberOfKm()).isEqualTo(DEFAULT_NUMBER_OF_KM);
    }

    @Test
    @Transactional
    void createUserInfoWithExistingId() throws Exception {
        // Create the UserInfo with an existing ID
        userInfo.setId(1L);
        UserInfoDTO userInfoDTO = userInfoMapper.toDto(userInfo);

        int databaseSizeBeforeCreate = userInfoRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restUserInfoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(userInfoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the UserInfo in the database
        List<UserInfo> userInfoList = userInfoRepository.findAll();
        assertThat(userInfoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllUserInfos() throws Exception {
        // Initialize the database
        userInfoRepository.saveAndFlush(userInfo);

        // Get all the userInfoList
        restUserInfoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(userInfo.getId().intValue())))
            .andExpect(jsonPath("$.[*].phoneNumber").value(hasItem(DEFAULT_PHONE_NUMBER)))
            .andExpect(jsonPath("$.[*].nib").value(hasItem(DEFAULT_NIB)))
            .andExpect(jsonPath("$.[*].nif").value(hasItem(DEFAULT_NIF)))
            .andExpect(jsonPath("$.[*].birthday").value(hasItem(DEFAULT_BIRTHDAY.toString())))
            .andExpect(jsonPath("$.[*].address").value(hasItem(DEFAULT_ADDRESS)))
            .andExpect(jsonPath("$.[*].linkSocial").value(hasItem(DEFAULT_LINK_SOCIAL)))
            .andExpect(jsonPath("$.[*].numberRequests").value(hasItem(DEFAULT_NUMBER_REQUESTS)))
            .andExpect(jsonPath("$.[*].payedValue").value(hasItem(DEFAULT_PAYED_VALUE.doubleValue())))
            .andExpect(jsonPath("$.[*].valueToPay").value(hasItem(DEFAULT_VALUE_TO_PAY.doubleValue())))
            .andExpect(jsonPath("$.[*].ranking").value(hasItem(DEFAULT_RANKING.doubleValue())))
            .andExpect(jsonPath("$.[*].numberOfDeliveries").value(hasItem(DEFAULT_NUMBER_OF_DELIVERIES)))
            .andExpect(jsonPath("$.[*].numberOfKm").value(hasItem(DEFAULT_NUMBER_OF_KM.doubleValue())));
    }

    @Test
    @Transactional
    void getUserInfo() throws Exception {
        // Initialize the database
        userInfoRepository.saveAndFlush(userInfo);

        // Get the userInfo
        restUserInfoMockMvc
            .perform(get(ENTITY_API_URL_ID, userInfo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(userInfo.getId().intValue()))
            .andExpect(jsonPath("$.phoneNumber").value(DEFAULT_PHONE_NUMBER))
            .andExpect(jsonPath("$.nib").value(DEFAULT_NIB))
            .andExpect(jsonPath("$.nif").value(DEFAULT_NIF))
            .andExpect(jsonPath("$.birthday").value(DEFAULT_BIRTHDAY.toString()))
            .andExpect(jsonPath("$.address").value(DEFAULT_ADDRESS))
            .andExpect(jsonPath("$.linkSocial").value(DEFAULT_LINK_SOCIAL))
            .andExpect(jsonPath("$.numberRequests").value(DEFAULT_NUMBER_REQUESTS))
            .andExpect(jsonPath("$.payedValue").value(DEFAULT_PAYED_VALUE.doubleValue()))
            .andExpect(jsonPath("$.valueToPay").value(DEFAULT_VALUE_TO_PAY.doubleValue()))
            .andExpect(jsonPath("$.ranking").value(DEFAULT_RANKING.doubleValue()))
            .andExpect(jsonPath("$.numberOfDeliveries").value(DEFAULT_NUMBER_OF_DELIVERIES))
            .andExpect(jsonPath("$.numberOfKm").value(DEFAULT_NUMBER_OF_KM.doubleValue()));
    }

    @Test
    @Transactional
    void getUserInfosByIdFiltering() throws Exception {
        // Initialize the database
        userInfoRepository.saveAndFlush(userInfo);

        Long id = userInfo.getId();

        defaultUserInfoShouldBeFound("id.equals=" + id);
        defaultUserInfoShouldNotBeFound("id.notEquals=" + id);

        defaultUserInfoShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultUserInfoShouldNotBeFound("id.greaterThan=" + id);

        defaultUserInfoShouldBeFound("id.lessThanOrEqual=" + id);
        defaultUserInfoShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllUserInfosByPhoneNumberIsEqualToSomething() throws Exception {
        // Initialize the database
        userInfoRepository.saveAndFlush(userInfo);

        // Get all the userInfoList where phoneNumber equals to DEFAULT_PHONE_NUMBER
        defaultUserInfoShouldBeFound("phoneNumber.equals=" + DEFAULT_PHONE_NUMBER);

        // Get all the userInfoList where phoneNumber equals to UPDATED_PHONE_NUMBER
        defaultUserInfoShouldNotBeFound("phoneNumber.equals=" + UPDATED_PHONE_NUMBER);
    }

    @Test
    @Transactional
    void getAllUserInfosByPhoneNumberIsNotEqualToSomething() throws Exception {
        // Initialize the database
        userInfoRepository.saveAndFlush(userInfo);

        // Get all the userInfoList where phoneNumber not equals to DEFAULT_PHONE_NUMBER
        defaultUserInfoShouldNotBeFound("phoneNumber.notEquals=" + DEFAULT_PHONE_NUMBER);

        // Get all the userInfoList where phoneNumber not equals to UPDATED_PHONE_NUMBER
        defaultUserInfoShouldBeFound("phoneNumber.notEquals=" + UPDATED_PHONE_NUMBER);
    }

    @Test
    @Transactional
    void getAllUserInfosByPhoneNumberIsInShouldWork() throws Exception {
        // Initialize the database
        userInfoRepository.saveAndFlush(userInfo);

        // Get all the userInfoList where phoneNumber in DEFAULT_PHONE_NUMBER or UPDATED_PHONE_NUMBER
        defaultUserInfoShouldBeFound("phoneNumber.in=" + DEFAULT_PHONE_NUMBER + "," + UPDATED_PHONE_NUMBER);

        // Get all the userInfoList where phoneNumber equals to UPDATED_PHONE_NUMBER
        defaultUserInfoShouldNotBeFound("phoneNumber.in=" + UPDATED_PHONE_NUMBER);
    }

    @Test
    @Transactional
    void getAllUserInfosByPhoneNumberIsNullOrNotNull() throws Exception {
        // Initialize the database
        userInfoRepository.saveAndFlush(userInfo);

        // Get all the userInfoList where phoneNumber is not null
        defaultUserInfoShouldBeFound("phoneNumber.specified=true");

        // Get all the userInfoList where phoneNumber is null
        defaultUserInfoShouldNotBeFound("phoneNumber.specified=false");
    }

    @Test
    @Transactional
    void getAllUserInfosByPhoneNumberContainsSomething() throws Exception {
        // Initialize the database
        userInfoRepository.saveAndFlush(userInfo);

        // Get all the userInfoList where phoneNumber contains DEFAULT_PHONE_NUMBER
        defaultUserInfoShouldBeFound("phoneNumber.contains=" + DEFAULT_PHONE_NUMBER);

        // Get all the userInfoList where phoneNumber contains UPDATED_PHONE_NUMBER
        defaultUserInfoShouldNotBeFound("phoneNumber.contains=" + UPDATED_PHONE_NUMBER);
    }

    @Test
    @Transactional
    void getAllUserInfosByPhoneNumberNotContainsSomething() throws Exception {
        // Initialize the database
        userInfoRepository.saveAndFlush(userInfo);

        // Get all the userInfoList where phoneNumber does not contain DEFAULT_PHONE_NUMBER
        defaultUserInfoShouldNotBeFound("phoneNumber.doesNotContain=" + DEFAULT_PHONE_NUMBER);

        // Get all the userInfoList where phoneNumber does not contain UPDATED_PHONE_NUMBER
        defaultUserInfoShouldBeFound("phoneNumber.doesNotContain=" + UPDATED_PHONE_NUMBER);
    }

    @Test
    @Transactional
    void getAllUserInfosByNibIsEqualToSomething() throws Exception {
        // Initialize the database
        userInfoRepository.saveAndFlush(userInfo);

        // Get all the userInfoList where nib equals to DEFAULT_NIB
        defaultUserInfoShouldBeFound("nib.equals=" + DEFAULT_NIB);

        // Get all the userInfoList where nib equals to UPDATED_NIB
        defaultUserInfoShouldNotBeFound("nib.equals=" + UPDATED_NIB);
    }

    @Test
    @Transactional
    void getAllUserInfosByNibIsNotEqualToSomething() throws Exception {
        // Initialize the database
        userInfoRepository.saveAndFlush(userInfo);

        // Get all the userInfoList where nib not equals to DEFAULT_NIB
        defaultUserInfoShouldNotBeFound("nib.notEquals=" + DEFAULT_NIB);

        // Get all the userInfoList where nib not equals to UPDATED_NIB
        defaultUserInfoShouldBeFound("nib.notEquals=" + UPDATED_NIB);
    }

    @Test
    @Transactional
    void getAllUserInfosByNibIsInShouldWork() throws Exception {
        // Initialize the database
        userInfoRepository.saveAndFlush(userInfo);

        // Get all the userInfoList where nib in DEFAULT_NIB or UPDATED_NIB
        defaultUserInfoShouldBeFound("nib.in=" + DEFAULT_NIB + "," + UPDATED_NIB);

        // Get all the userInfoList where nib equals to UPDATED_NIB
        defaultUserInfoShouldNotBeFound("nib.in=" + UPDATED_NIB);
    }

    @Test
    @Transactional
    void getAllUserInfosByNibIsNullOrNotNull() throws Exception {
        // Initialize the database
        userInfoRepository.saveAndFlush(userInfo);

        // Get all the userInfoList where nib is not null
        defaultUserInfoShouldBeFound("nib.specified=true");

        // Get all the userInfoList where nib is null
        defaultUserInfoShouldNotBeFound("nib.specified=false");
    }

    @Test
    @Transactional
    void getAllUserInfosByNibContainsSomething() throws Exception {
        // Initialize the database
        userInfoRepository.saveAndFlush(userInfo);

        // Get all the userInfoList where nib contains DEFAULT_NIB
        defaultUserInfoShouldBeFound("nib.contains=" + DEFAULT_NIB);

        // Get all the userInfoList where nib contains UPDATED_NIB
        defaultUserInfoShouldNotBeFound("nib.contains=" + UPDATED_NIB);
    }

    @Test
    @Transactional
    void getAllUserInfosByNibNotContainsSomething() throws Exception {
        // Initialize the database
        userInfoRepository.saveAndFlush(userInfo);

        // Get all the userInfoList where nib does not contain DEFAULT_NIB
        defaultUserInfoShouldNotBeFound("nib.doesNotContain=" + DEFAULT_NIB);

        // Get all the userInfoList where nib does not contain UPDATED_NIB
        defaultUserInfoShouldBeFound("nib.doesNotContain=" + UPDATED_NIB);
    }

    @Test
    @Transactional
    void getAllUserInfosByNifIsEqualToSomething() throws Exception {
        // Initialize the database
        userInfoRepository.saveAndFlush(userInfo);

        // Get all the userInfoList where nif equals to DEFAULT_NIF
        defaultUserInfoShouldBeFound("nif.equals=" + DEFAULT_NIF);

        // Get all the userInfoList where nif equals to UPDATED_NIF
        defaultUserInfoShouldNotBeFound("nif.equals=" + UPDATED_NIF);
    }

    @Test
    @Transactional
    void getAllUserInfosByNifIsNotEqualToSomething() throws Exception {
        // Initialize the database
        userInfoRepository.saveAndFlush(userInfo);

        // Get all the userInfoList where nif not equals to DEFAULT_NIF
        defaultUserInfoShouldNotBeFound("nif.notEquals=" + DEFAULT_NIF);

        // Get all the userInfoList where nif not equals to UPDATED_NIF
        defaultUserInfoShouldBeFound("nif.notEquals=" + UPDATED_NIF);
    }

    @Test
    @Transactional
    void getAllUserInfosByNifIsInShouldWork() throws Exception {
        // Initialize the database
        userInfoRepository.saveAndFlush(userInfo);

        // Get all the userInfoList where nif in DEFAULT_NIF or UPDATED_NIF
        defaultUserInfoShouldBeFound("nif.in=" + DEFAULT_NIF + "," + UPDATED_NIF);

        // Get all the userInfoList where nif equals to UPDATED_NIF
        defaultUserInfoShouldNotBeFound("nif.in=" + UPDATED_NIF);
    }

    @Test
    @Transactional
    void getAllUserInfosByNifIsNullOrNotNull() throws Exception {
        // Initialize the database
        userInfoRepository.saveAndFlush(userInfo);

        // Get all the userInfoList where nif is not null
        defaultUserInfoShouldBeFound("nif.specified=true");

        // Get all the userInfoList where nif is null
        defaultUserInfoShouldNotBeFound("nif.specified=false");
    }

    @Test
    @Transactional
    void getAllUserInfosByNifIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        userInfoRepository.saveAndFlush(userInfo);

        // Get all the userInfoList where nif is greater than or equal to DEFAULT_NIF
        defaultUserInfoShouldBeFound("nif.greaterThanOrEqual=" + DEFAULT_NIF);

        // Get all the userInfoList where nif is greater than or equal to UPDATED_NIF
        defaultUserInfoShouldNotBeFound("nif.greaterThanOrEqual=" + UPDATED_NIF);
    }

    @Test
    @Transactional
    void getAllUserInfosByNifIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        userInfoRepository.saveAndFlush(userInfo);

        // Get all the userInfoList where nif is less than or equal to DEFAULT_NIF
        defaultUserInfoShouldBeFound("nif.lessThanOrEqual=" + DEFAULT_NIF);

        // Get all the userInfoList where nif is less than or equal to SMALLER_NIF
        defaultUserInfoShouldNotBeFound("nif.lessThanOrEqual=" + SMALLER_NIF);
    }

    @Test
    @Transactional
    void getAllUserInfosByNifIsLessThanSomething() throws Exception {
        // Initialize the database
        userInfoRepository.saveAndFlush(userInfo);

        // Get all the userInfoList where nif is less than DEFAULT_NIF
        defaultUserInfoShouldNotBeFound("nif.lessThan=" + DEFAULT_NIF);

        // Get all the userInfoList where nif is less than UPDATED_NIF
        defaultUserInfoShouldBeFound("nif.lessThan=" + UPDATED_NIF);
    }

    @Test
    @Transactional
    void getAllUserInfosByNifIsGreaterThanSomething() throws Exception {
        // Initialize the database
        userInfoRepository.saveAndFlush(userInfo);

        // Get all the userInfoList where nif is greater than DEFAULT_NIF
        defaultUserInfoShouldNotBeFound("nif.greaterThan=" + DEFAULT_NIF);

        // Get all the userInfoList where nif is greater than SMALLER_NIF
        defaultUserInfoShouldBeFound("nif.greaterThan=" + SMALLER_NIF);
    }

    @Test
    @Transactional
    void getAllUserInfosByBirthdayIsEqualToSomething() throws Exception {
        // Initialize the database
        userInfoRepository.saveAndFlush(userInfo);

        // Get all the userInfoList where birthday equals to DEFAULT_BIRTHDAY
        defaultUserInfoShouldBeFound("birthday.equals=" + DEFAULT_BIRTHDAY);

        // Get all the userInfoList where birthday equals to UPDATED_BIRTHDAY
        defaultUserInfoShouldNotBeFound("birthday.equals=" + UPDATED_BIRTHDAY);
    }

    @Test
    @Transactional
    void getAllUserInfosByBirthdayIsNotEqualToSomething() throws Exception {
        // Initialize the database
        userInfoRepository.saveAndFlush(userInfo);

        // Get all the userInfoList where birthday not equals to DEFAULT_BIRTHDAY
        defaultUserInfoShouldNotBeFound("birthday.notEquals=" + DEFAULT_BIRTHDAY);

        // Get all the userInfoList where birthday not equals to UPDATED_BIRTHDAY
        defaultUserInfoShouldBeFound("birthday.notEquals=" + UPDATED_BIRTHDAY);
    }

    @Test
    @Transactional
    void getAllUserInfosByBirthdayIsInShouldWork() throws Exception {
        // Initialize the database
        userInfoRepository.saveAndFlush(userInfo);

        // Get all the userInfoList where birthday in DEFAULT_BIRTHDAY or UPDATED_BIRTHDAY
        defaultUserInfoShouldBeFound("birthday.in=" + DEFAULT_BIRTHDAY + "," + UPDATED_BIRTHDAY);

        // Get all the userInfoList where birthday equals to UPDATED_BIRTHDAY
        defaultUserInfoShouldNotBeFound("birthday.in=" + UPDATED_BIRTHDAY);
    }

    @Test
    @Transactional
    void getAllUserInfosByBirthdayIsNullOrNotNull() throws Exception {
        // Initialize the database
        userInfoRepository.saveAndFlush(userInfo);

        // Get all the userInfoList where birthday is not null
        defaultUserInfoShouldBeFound("birthday.specified=true");

        // Get all the userInfoList where birthday is null
        defaultUserInfoShouldNotBeFound("birthday.specified=false");
    }

    @Test
    @Transactional
    void getAllUserInfosByBirthdayIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        userInfoRepository.saveAndFlush(userInfo);

        // Get all the userInfoList where birthday is greater than or equal to DEFAULT_BIRTHDAY
        defaultUserInfoShouldBeFound("birthday.greaterThanOrEqual=" + DEFAULT_BIRTHDAY);

        // Get all the userInfoList where birthday is greater than or equal to UPDATED_BIRTHDAY
        defaultUserInfoShouldNotBeFound("birthday.greaterThanOrEqual=" + UPDATED_BIRTHDAY);
    }

    @Test
    @Transactional
    void getAllUserInfosByBirthdayIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        userInfoRepository.saveAndFlush(userInfo);

        // Get all the userInfoList where birthday is less than or equal to DEFAULT_BIRTHDAY
        defaultUserInfoShouldBeFound("birthday.lessThanOrEqual=" + DEFAULT_BIRTHDAY);

        // Get all the userInfoList where birthday is less than or equal to SMALLER_BIRTHDAY
        defaultUserInfoShouldNotBeFound("birthday.lessThanOrEqual=" + SMALLER_BIRTHDAY);
    }

    @Test
    @Transactional
    void getAllUserInfosByBirthdayIsLessThanSomething() throws Exception {
        // Initialize the database
        userInfoRepository.saveAndFlush(userInfo);

        // Get all the userInfoList where birthday is less than DEFAULT_BIRTHDAY
        defaultUserInfoShouldNotBeFound("birthday.lessThan=" + DEFAULT_BIRTHDAY);

        // Get all the userInfoList where birthday is less than UPDATED_BIRTHDAY
        defaultUserInfoShouldBeFound("birthday.lessThan=" + UPDATED_BIRTHDAY);
    }

    @Test
    @Transactional
    void getAllUserInfosByBirthdayIsGreaterThanSomething() throws Exception {
        // Initialize the database
        userInfoRepository.saveAndFlush(userInfo);

        // Get all the userInfoList where birthday is greater than DEFAULT_BIRTHDAY
        defaultUserInfoShouldNotBeFound("birthday.greaterThan=" + DEFAULT_BIRTHDAY);

        // Get all the userInfoList where birthday is greater than SMALLER_BIRTHDAY
        defaultUserInfoShouldBeFound("birthday.greaterThan=" + SMALLER_BIRTHDAY);
    }

    @Test
    @Transactional
    void getAllUserInfosByAddressIsEqualToSomething() throws Exception {
        // Initialize the database
        userInfoRepository.saveAndFlush(userInfo);

        // Get all the userInfoList where address equals to DEFAULT_ADDRESS
        defaultUserInfoShouldBeFound("address.equals=" + DEFAULT_ADDRESS);

        // Get all the userInfoList where address equals to UPDATED_ADDRESS
        defaultUserInfoShouldNotBeFound("address.equals=" + UPDATED_ADDRESS);
    }

    @Test
    @Transactional
    void getAllUserInfosByAddressIsNotEqualToSomething() throws Exception {
        // Initialize the database
        userInfoRepository.saveAndFlush(userInfo);

        // Get all the userInfoList where address not equals to DEFAULT_ADDRESS
        defaultUserInfoShouldNotBeFound("address.notEquals=" + DEFAULT_ADDRESS);

        // Get all the userInfoList where address not equals to UPDATED_ADDRESS
        defaultUserInfoShouldBeFound("address.notEquals=" + UPDATED_ADDRESS);
    }

    @Test
    @Transactional
    void getAllUserInfosByAddressIsInShouldWork() throws Exception {
        // Initialize the database
        userInfoRepository.saveAndFlush(userInfo);

        // Get all the userInfoList where address in DEFAULT_ADDRESS or UPDATED_ADDRESS
        defaultUserInfoShouldBeFound("address.in=" + DEFAULT_ADDRESS + "," + UPDATED_ADDRESS);

        // Get all the userInfoList where address equals to UPDATED_ADDRESS
        defaultUserInfoShouldNotBeFound("address.in=" + UPDATED_ADDRESS);
    }

    @Test
    @Transactional
    void getAllUserInfosByAddressIsNullOrNotNull() throws Exception {
        // Initialize the database
        userInfoRepository.saveAndFlush(userInfo);

        // Get all the userInfoList where address is not null
        defaultUserInfoShouldBeFound("address.specified=true");

        // Get all the userInfoList where address is null
        defaultUserInfoShouldNotBeFound("address.specified=false");
    }

    @Test
    @Transactional
    void getAllUserInfosByAddressContainsSomething() throws Exception {
        // Initialize the database
        userInfoRepository.saveAndFlush(userInfo);

        // Get all the userInfoList where address contains DEFAULT_ADDRESS
        defaultUserInfoShouldBeFound("address.contains=" + DEFAULT_ADDRESS);

        // Get all the userInfoList where address contains UPDATED_ADDRESS
        defaultUserInfoShouldNotBeFound("address.contains=" + UPDATED_ADDRESS);
    }

    @Test
    @Transactional
    void getAllUserInfosByAddressNotContainsSomething() throws Exception {
        // Initialize the database
        userInfoRepository.saveAndFlush(userInfo);

        // Get all the userInfoList where address does not contain DEFAULT_ADDRESS
        defaultUserInfoShouldNotBeFound("address.doesNotContain=" + DEFAULT_ADDRESS);

        // Get all the userInfoList where address does not contain UPDATED_ADDRESS
        defaultUserInfoShouldBeFound("address.doesNotContain=" + UPDATED_ADDRESS);
    }

    @Test
    @Transactional
    void getAllUserInfosByLinkSocialIsEqualToSomething() throws Exception {
        // Initialize the database
        userInfoRepository.saveAndFlush(userInfo);

        // Get all the userInfoList where linkSocial equals to DEFAULT_LINK_SOCIAL
        defaultUserInfoShouldBeFound("linkSocial.equals=" + DEFAULT_LINK_SOCIAL);

        // Get all the userInfoList where linkSocial equals to UPDATED_LINK_SOCIAL
        defaultUserInfoShouldNotBeFound("linkSocial.equals=" + UPDATED_LINK_SOCIAL);
    }

    @Test
    @Transactional
    void getAllUserInfosByLinkSocialIsNotEqualToSomething() throws Exception {
        // Initialize the database
        userInfoRepository.saveAndFlush(userInfo);

        // Get all the userInfoList where linkSocial not equals to DEFAULT_LINK_SOCIAL
        defaultUserInfoShouldNotBeFound("linkSocial.notEquals=" + DEFAULT_LINK_SOCIAL);

        // Get all the userInfoList where linkSocial not equals to UPDATED_LINK_SOCIAL
        defaultUserInfoShouldBeFound("linkSocial.notEquals=" + UPDATED_LINK_SOCIAL);
    }

    @Test
    @Transactional
    void getAllUserInfosByLinkSocialIsInShouldWork() throws Exception {
        // Initialize the database
        userInfoRepository.saveAndFlush(userInfo);

        // Get all the userInfoList where linkSocial in DEFAULT_LINK_SOCIAL or UPDATED_LINK_SOCIAL
        defaultUserInfoShouldBeFound("linkSocial.in=" + DEFAULT_LINK_SOCIAL + "," + UPDATED_LINK_SOCIAL);

        // Get all the userInfoList where linkSocial equals to UPDATED_LINK_SOCIAL
        defaultUserInfoShouldNotBeFound("linkSocial.in=" + UPDATED_LINK_SOCIAL);
    }

    @Test
    @Transactional
    void getAllUserInfosByLinkSocialIsNullOrNotNull() throws Exception {
        // Initialize the database
        userInfoRepository.saveAndFlush(userInfo);

        // Get all the userInfoList where linkSocial is not null
        defaultUserInfoShouldBeFound("linkSocial.specified=true");

        // Get all the userInfoList where linkSocial is null
        defaultUserInfoShouldNotBeFound("linkSocial.specified=false");
    }

    @Test
    @Transactional
    void getAllUserInfosByLinkSocialContainsSomething() throws Exception {
        // Initialize the database
        userInfoRepository.saveAndFlush(userInfo);

        // Get all the userInfoList where linkSocial contains DEFAULT_LINK_SOCIAL
        defaultUserInfoShouldBeFound("linkSocial.contains=" + DEFAULT_LINK_SOCIAL);

        // Get all the userInfoList where linkSocial contains UPDATED_LINK_SOCIAL
        defaultUserInfoShouldNotBeFound("linkSocial.contains=" + UPDATED_LINK_SOCIAL);
    }

    @Test
    @Transactional
    void getAllUserInfosByLinkSocialNotContainsSomething() throws Exception {
        // Initialize the database
        userInfoRepository.saveAndFlush(userInfo);

        // Get all the userInfoList where linkSocial does not contain DEFAULT_LINK_SOCIAL
        defaultUserInfoShouldNotBeFound("linkSocial.doesNotContain=" + DEFAULT_LINK_SOCIAL);

        // Get all the userInfoList where linkSocial does not contain UPDATED_LINK_SOCIAL
        defaultUserInfoShouldBeFound("linkSocial.doesNotContain=" + UPDATED_LINK_SOCIAL);
    }

    @Test
    @Transactional
    void getAllUserInfosByNumberRequestsIsEqualToSomething() throws Exception {
        // Initialize the database
        userInfoRepository.saveAndFlush(userInfo);

        // Get all the userInfoList where numberRequests equals to DEFAULT_NUMBER_REQUESTS
        defaultUserInfoShouldBeFound("numberRequests.equals=" + DEFAULT_NUMBER_REQUESTS);

        // Get all the userInfoList where numberRequests equals to UPDATED_NUMBER_REQUESTS
        defaultUserInfoShouldNotBeFound("numberRequests.equals=" + UPDATED_NUMBER_REQUESTS);
    }

    @Test
    @Transactional
    void getAllUserInfosByNumberRequestsIsNotEqualToSomething() throws Exception {
        // Initialize the database
        userInfoRepository.saveAndFlush(userInfo);

        // Get all the userInfoList where numberRequests not equals to DEFAULT_NUMBER_REQUESTS
        defaultUserInfoShouldNotBeFound("numberRequests.notEquals=" + DEFAULT_NUMBER_REQUESTS);

        // Get all the userInfoList where numberRequests not equals to UPDATED_NUMBER_REQUESTS
        defaultUserInfoShouldBeFound("numberRequests.notEquals=" + UPDATED_NUMBER_REQUESTS);
    }

    @Test
    @Transactional
    void getAllUserInfosByNumberRequestsIsInShouldWork() throws Exception {
        // Initialize the database
        userInfoRepository.saveAndFlush(userInfo);

        // Get all the userInfoList where numberRequests in DEFAULT_NUMBER_REQUESTS or UPDATED_NUMBER_REQUESTS
        defaultUserInfoShouldBeFound("numberRequests.in=" + DEFAULT_NUMBER_REQUESTS + "," + UPDATED_NUMBER_REQUESTS);

        // Get all the userInfoList where numberRequests equals to UPDATED_NUMBER_REQUESTS
        defaultUserInfoShouldNotBeFound("numberRequests.in=" + UPDATED_NUMBER_REQUESTS);
    }

    @Test
    @Transactional
    void getAllUserInfosByNumberRequestsIsNullOrNotNull() throws Exception {
        // Initialize the database
        userInfoRepository.saveAndFlush(userInfo);

        // Get all the userInfoList where numberRequests is not null
        defaultUserInfoShouldBeFound("numberRequests.specified=true");

        // Get all the userInfoList where numberRequests is null
        defaultUserInfoShouldNotBeFound("numberRequests.specified=false");
    }

    @Test
    @Transactional
    void getAllUserInfosByNumberRequestsIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        userInfoRepository.saveAndFlush(userInfo);

        // Get all the userInfoList where numberRequests is greater than or equal to DEFAULT_NUMBER_REQUESTS
        defaultUserInfoShouldBeFound("numberRequests.greaterThanOrEqual=" + DEFAULT_NUMBER_REQUESTS);

        // Get all the userInfoList where numberRequests is greater than or equal to UPDATED_NUMBER_REQUESTS
        defaultUserInfoShouldNotBeFound("numberRequests.greaterThanOrEqual=" + UPDATED_NUMBER_REQUESTS);
    }

    @Test
    @Transactional
    void getAllUserInfosByNumberRequestsIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        userInfoRepository.saveAndFlush(userInfo);

        // Get all the userInfoList where numberRequests is less than or equal to DEFAULT_NUMBER_REQUESTS
        defaultUserInfoShouldBeFound("numberRequests.lessThanOrEqual=" + DEFAULT_NUMBER_REQUESTS);

        // Get all the userInfoList where numberRequests is less than or equal to SMALLER_NUMBER_REQUESTS
        defaultUserInfoShouldNotBeFound("numberRequests.lessThanOrEqual=" + SMALLER_NUMBER_REQUESTS);
    }

    @Test
    @Transactional
    void getAllUserInfosByNumberRequestsIsLessThanSomething() throws Exception {
        // Initialize the database
        userInfoRepository.saveAndFlush(userInfo);

        // Get all the userInfoList where numberRequests is less than DEFAULT_NUMBER_REQUESTS
        defaultUserInfoShouldNotBeFound("numberRequests.lessThan=" + DEFAULT_NUMBER_REQUESTS);

        // Get all the userInfoList where numberRequests is less than UPDATED_NUMBER_REQUESTS
        defaultUserInfoShouldBeFound("numberRequests.lessThan=" + UPDATED_NUMBER_REQUESTS);
    }

    @Test
    @Transactional
    void getAllUserInfosByNumberRequestsIsGreaterThanSomething() throws Exception {
        // Initialize the database
        userInfoRepository.saveAndFlush(userInfo);

        // Get all the userInfoList where numberRequests is greater than DEFAULT_NUMBER_REQUESTS
        defaultUserInfoShouldNotBeFound("numberRequests.greaterThan=" + DEFAULT_NUMBER_REQUESTS);

        // Get all the userInfoList where numberRequests is greater than SMALLER_NUMBER_REQUESTS
        defaultUserInfoShouldBeFound("numberRequests.greaterThan=" + SMALLER_NUMBER_REQUESTS);
    }

    @Test
    @Transactional
    void getAllUserInfosByPayedValueIsEqualToSomething() throws Exception {
        // Initialize the database
        userInfoRepository.saveAndFlush(userInfo);

        // Get all the userInfoList where payedValue equals to DEFAULT_PAYED_VALUE
        defaultUserInfoShouldBeFound("payedValue.equals=" + DEFAULT_PAYED_VALUE);

        // Get all the userInfoList where payedValue equals to UPDATED_PAYED_VALUE
        defaultUserInfoShouldNotBeFound("payedValue.equals=" + UPDATED_PAYED_VALUE);
    }

    @Test
    @Transactional
    void getAllUserInfosByPayedValueIsNotEqualToSomething() throws Exception {
        // Initialize the database
        userInfoRepository.saveAndFlush(userInfo);

        // Get all the userInfoList where payedValue not equals to DEFAULT_PAYED_VALUE
        defaultUserInfoShouldNotBeFound("payedValue.notEquals=" + DEFAULT_PAYED_VALUE);

        // Get all the userInfoList where payedValue not equals to UPDATED_PAYED_VALUE
        defaultUserInfoShouldBeFound("payedValue.notEquals=" + UPDATED_PAYED_VALUE);
    }

    @Test
    @Transactional
    void getAllUserInfosByPayedValueIsInShouldWork() throws Exception {
        // Initialize the database
        userInfoRepository.saveAndFlush(userInfo);

        // Get all the userInfoList where payedValue in DEFAULT_PAYED_VALUE or UPDATED_PAYED_VALUE
        defaultUserInfoShouldBeFound("payedValue.in=" + DEFAULT_PAYED_VALUE + "," + UPDATED_PAYED_VALUE);

        // Get all the userInfoList where payedValue equals to UPDATED_PAYED_VALUE
        defaultUserInfoShouldNotBeFound("payedValue.in=" + UPDATED_PAYED_VALUE);
    }

    @Test
    @Transactional
    void getAllUserInfosByPayedValueIsNullOrNotNull() throws Exception {
        // Initialize the database
        userInfoRepository.saveAndFlush(userInfo);

        // Get all the userInfoList where payedValue is not null
        defaultUserInfoShouldBeFound("payedValue.specified=true");

        // Get all the userInfoList where payedValue is null
        defaultUserInfoShouldNotBeFound("payedValue.specified=false");
    }

    @Test
    @Transactional
    void getAllUserInfosByPayedValueIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        userInfoRepository.saveAndFlush(userInfo);

        // Get all the userInfoList where payedValue is greater than or equal to DEFAULT_PAYED_VALUE
        defaultUserInfoShouldBeFound("payedValue.greaterThanOrEqual=" + DEFAULT_PAYED_VALUE);

        // Get all the userInfoList where payedValue is greater than or equal to UPDATED_PAYED_VALUE
        defaultUserInfoShouldNotBeFound("payedValue.greaterThanOrEqual=" + UPDATED_PAYED_VALUE);
    }

    @Test
    @Transactional
    void getAllUserInfosByPayedValueIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        userInfoRepository.saveAndFlush(userInfo);

        // Get all the userInfoList where payedValue is less than or equal to DEFAULT_PAYED_VALUE
        defaultUserInfoShouldBeFound("payedValue.lessThanOrEqual=" + DEFAULT_PAYED_VALUE);

        // Get all the userInfoList where payedValue is less than or equal to SMALLER_PAYED_VALUE
        defaultUserInfoShouldNotBeFound("payedValue.lessThanOrEqual=" + SMALLER_PAYED_VALUE);
    }

    @Test
    @Transactional
    void getAllUserInfosByPayedValueIsLessThanSomething() throws Exception {
        // Initialize the database
        userInfoRepository.saveAndFlush(userInfo);

        // Get all the userInfoList where payedValue is less than DEFAULT_PAYED_VALUE
        defaultUserInfoShouldNotBeFound("payedValue.lessThan=" + DEFAULT_PAYED_VALUE);

        // Get all the userInfoList where payedValue is less than UPDATED_PAYED_VALUE
        defaultUserInfoShouldBeFound("payedValue.lessThan=" + UPDATED_PAYED_VALUE);
    }

    @Test
    @Transactional
    void getAllUserInfosByPayedValueIsGreaterThanSomething() throws Exception {
        // Initialize the database
        userInfoRepository.saveAndFlush(userInfo);

        // Get all the userInfoList where payedValue is greater than DEFAULT_PAYED_VALUE
        defaultUserInfoShouldNotBeFound("payedValue.greaterThan=" + DEFAULT_PAYED_VALUE);

        // Get all the userInfoList where payedValue is greater than SMALLER_PAYED_VALUE
        defaultUserInfoShouldBeFound("payedValue.greaterThan=" + SMALLER_PAYED_VALUE);
    }

    @Test
    @Transactional
    void getAllUserInfosByValueToPayIsEqualToSomething() throws Exception {
        // Initialize the database
        userInfoRepository.saveAndFlush(userInfo);

        // Get all the userInfoList where valueToPay equals to DEFAULT_VALUE_TO_PAY
        defaultUserInfoShouldBeFound("valueToPay.equals=" + DEFAULT_VALUE_TO_PAY);

        // Get all the userInfoList where valueToPay equals to UPDATED_VALUE_TO_PAY
        defaultUserInfoShouldNotBeFound("valueToPay.equals=" + UPDATED_VALUE_TO_PAY);
    }

    @Test
    @Transactional
    void getAllUserInfosByValueToPayIsNotEqualToSomething() throws Exception {
        // Initialize the database
        userInfoRepository.saveAndFlush(userInfo);

        // Get all the userInfoList where valueToPay not equals to DEFAULT_VALUE_TO_PAY
        defaultUserInfoShouldNotBeFound("valueToPay.notEquals=" + DEFAULT_VALUE_TO_PAY);

        // Get all the userInfoList where valueToPay not equals to UPDATED_VALUE_TO_PAY
        defaultUserInfoShouldBeFound("valueToPay.notEquals=" + UPDATED_VALUE_TO_PAY);
    }

    @Test
    @Transactional
    void getAllUserInfosByValueToPayIsInShouldWork() throws Exception {
        // Initialize the database
        userInfoRepository.saveAndFlush(userInfo);

        // Get all the userInfoList where valueToPay in DEFAULT_VALUE_TO_PAY or UPDATED_VALUE_TO_PAY
        defaultUserInfoShouldBeFound("valueToPay.in=" + DEFAULT_VALUE_TO_PAY + "," + UPDATED_VALUE_TO_PAY);

        // Get all the userInfoList where valueToPay equals to UPDATED_VALUE_TO_PAY
        defaultUserInfoShouldNotBeFound("valueToPay.in=" + UPDATED_VALUE_TO_PAY);
    }

    @Test
    @Transactional
    void getAllUserInfosByValueToPayIsNullOrNotNull() throws Exception {
        // Initialize the database
        userInfoRepository.saveAndFlush(userInfo);

        // Get all the userInfoList where valueToPay is not null
        defaultUserInfoShouldBeFound("valueToPay.specified=true");

        // Get all the userInfoList where valueToPay is null
        defaultUserInfoShouldNotBeFound("valueToPay.specified=false");
    }

    @Test
    @Transactional
    void getAllUserInfosByValueToPayIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        userInfoRepository.saveAndFlush(userInfo);

        // Get all the userInfoList where valueToPay is greater than or equal to DEFAULT_VALUE_TO_PAY
        defaultUserInfoShouldBeFound("valueToPay.greaterThanOrEqual=" + DEFAULT_VALUE_TO_PAY);

        // Get all the userInfoList where valueToPay is greater than or equal to UPDATED_VALUE_TO_PAY
        defaultUserInfoShouldNotBeFound("valueToPay.greaterThanOrEqual=" + UPDATED_VALUE_TO_PAY);
    }

    @Test
    @Transactional
    void getAllUserInfosByValueToPayIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        userInfoRepository.saveAndFlush(userInfo);

        // Get all the userInfoList where valueToPay is less than or equal to DEFAULT_VALUE_TO_PAY
        defaultUserInfoShouldBeFound("valueToPay.lessThanOrEqual=" + DEFAULT_VALUE_TO_PAY);

        // Get all the userInfoList where valueToPay is less than or equal to SMALLER_VALUE_TO_PAY
        defaultUserInfoShouldNotBeFound("valueToPay.lessThanOrEqual=" + SMALLER_VALUE_TO_PAY);
    }

    @Test
    @Transactional
    void getAllUserInfosByValueToPayIsLessThanSomething() throws Exception {
        // Initialize the database
        userInfoRepository.saveAndFlush(userInfo);

        // Get all the userInfoList where valueToPay is less than DEFAULT_VALUE_TO_PAY
        defaultUserInfoShouldNotBeFound("valueToPay.lessThan=" + DEFAULT_VALUE_TO_PAY);

        // Get all the userInfoList where valueToPay is less than UPDATED_VALUE_TO_PAY
        defaultUserInfoShouldBeFound("valueToPay.lessThan=" + UPDATED_VALUE_TO_PAY);
    }

    @Test
    @Transactional
    void getAllUserInfosByValueToPayIsGreaterThanSomething() throws Exception {
        // Initialize the database
        userInfoRepository.saveAndFlush(userInfo);

        // Get all the userInfoList where valueToPay is greater than DEFAULT_VALUE_TO_PAY
        defaultUserInfoShouldNotBeFound("valueToPay.greaterThan=" + DEFAULT_VALUE_TO_PAY);

        // Get all the userInfoList where valueToPay is greater than SMALLER_VALUE_TO_PAY
        defaultUserInfoShouldBeFound("valueToPay.greaterThan=" + SMALLER_VALUE_TO_PAY);
    }

    @Test
    @Transactional
    void getAllUserInfosByRankingIsEqualToSomething() throws Exception {
        // Initialize the database
        userInfoRepository.saveAndFlush(userInfo);

        // Get all the userInfoList where ranking equals to DEFAULT_RANKING
        defaultUserInfoShouldBeFound("ranking.equals=" + DEFAULT_RANKING);

        // Get all the userInfoList where ranking equals to UPDATED_RANKING
        defaultUserInfoShouldNotBeFound("ranking.equals=" + UPDATED_RANKING);
    }

    @Test
    @Transactional
    void getAllUserInfosByRankingIsNotEqualToSomething() throws Exception {
        // Initialize the database
        userInfoRepository.saveAndFlush(userInfo);

        // Get all the userInfoList where ranking not equals to DEFAULT_RANKING
        defaultUserInfoShouldNotBeFound("ranking.notEquals=" + DEFAULT_RANKING);

        // Get all the userInfoList where ranking not equals to UPDATED_RANKING
        defaultUserInfoShouldBeFound("ranking.notEquals=" + UPDATED_RANKING);
    }

    @Test
    @Transactional
    void getAllUserInfosByRankingIsInShouldWork() throws Exception {
        // Initialize the database
        userInfoRepository.saveAndFlush(userInfo);

        // Get all the userInfoList where ranking in DEFAULT_RANKING or UPDATED_RANKING
        defaultUserInfoShouldBeFound("ranking.in=" + DEFAULT_RANKING + "," + UPDATED_RANKING);

        // Get all the userInfoList where ranking equals to UPDATED_RANKING
        defaultUserInfoShouldNotBeFound("ranking.in=" + UPDATED_RANKING);
    }

    @Test
    @Transactional
    void getAllUserInfosByRankingIsNullOrNotNull() throws Exception {
        // Initialize the database
        userInfoRepository.saveAndFlush(userInfo);

        // Get all the userInfoList where ranking is not null
        defaultUserInfoShouldBeFound("ranking.specified=true");

        // Get all the userInfoList where ranking is null
        defaultUserInfoShouldNotBeFound("ranking.specified=false");
    }

    @Test
    @Transactional
    void getAllUserInfosByRankingIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        userInfoRepository.saveAndFlush(userInfo);

        // Get all the userInfoList where ranking is greater than or equal to DEFAULT_RANKING
        defaultUserInfoShouldBeFound("ranking.greaterThanOrEqual=" + DEFAULT_RANKING);

        // Get all the userInfoList where ranking is greater than or equal to UPDATED_RANKING
        defaultUserInfoShouldNotBeFound("ranking.greaterThanOrEqual=" + UPDATED_RANKING);
    }

    @Test
    @Transactional
    void getAllUserInfosByRankingIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        userInfoRepository.saveAndFlush(userInfo);

        // Get all the userInfoList where ranking is less than or equal to DEFAULT_RANKING
        defaultUserInfoShouldBeFound("ranking.lessThanOrEqual=" + DEFAULT_RANKING);

        // Get all the userInfoList where ranking is less than or equal to SMALLER_RANKING
        defaultUserInfoShouldNotBeFound("ranking.lessThanOrEqual=" + SMALLER_RANKING);
    }

    @Test
    @Transactional
    void getAllUserInfosByRankingIsLessThanSomething() throws Exception {
        // Initialize the database
        userInfoRepository.saveAndFlush(userInfo);

        // Get all the userInfoList where ranking is less than DEFAULT_RANKING
        defaultUserInfoShouldNotBeFound("ranking.lessThan=" + DEFAULT_RANKING);

        // Get all the userInfoList where ranking is less than UPDATED_RANKING
        defaultUserInfoShouldBeFound("ranking.lessThan=" + UPDATED_RANKING);
    }

    @Test
    @Transactional
    void getAllUserInfosByRankingIsGreaterThanSomething() throws Exception {
        // Initialize the database
        userInfoRepository.saveAndFlush(userInfo);

        // Get all the userInfoList where ranking is greater than DEFAULT_RANKING
        defaultUserInfoShouldNotBeFound("ranking.greaterThan=" + DEFAULT_RANKING);

        // Get all the userInfoList where ranking is greater than SMALLER_RANKING
        defaultUserInfoShouldBeFound("ranking.greaterThan=" + SMALLER_RANKING);
    }

    @Test
    @Transactional
    void getAllUserInfosByNumberOfDeliveriesIsEqualToSomething() throws Exception {
        // Initialize the database
        userInfoRepository.saveAndFlush(userInfo);

        // Get all the userInfoList where numberOfDeliveries equals to DEFAULT_NUMBER_OF_DELIVERIES
        defaultUserInfoShouldBeFound("numberOfDeliveries.equals=" + DEFAULT_NUMBER_OF_DELIVERIES);

        // Get all the userInfoList where numberOfDeliveries equals to UPDATED_NUMBER_OF_DELIVERIES
        defaultUserInfoShouldNotBeFound("numberOfDeliveries.equals=" + UPDATED_NUMBER_OF_DELIVERIES);
    }

    @Test
    @Transactional
    void getAllUserInfosByNumberOfDeliveriesIsNotEqualToSomething() throws Exception {
        // Initialize the database
        userInfoRepository.saveAndFlush(userInfo);

        // Get all the userInfoList where numberOfDeliveries not equals to DEFAULT_NUMBER_OF_DELIVERIES
        defaultUserInfoShouldNotBeFound("numberOfDeliveries.notEquals=" + DEFAULT_NUMBER_OF_DELIVERIES);

        // Get all the userInfoList where numberOfDeliveries not equals to UPDATED_NUMBER_OF_DELIVERIES
        defaultUserInfoShouldBeFound("numberOfDeliveries.notEquals=" + UPDATED_NUMBER_OF_DELIVERIES);
    }

    @Test
    @Transactional
    void getAllUserInfosByNumberOfDeliveriesIsInShouldWork() throws Exception {
        // Initialize the database
        userInfoRepository.saveAndFlush(userInfo);

        // Get all the userInfoList where numberOfDeliveries in DEFAULT_NUMBER_OF_DELIVERIES or UPDATED_NUMBER_OF_DELIVERIES
        defaultUserInfoShouldBeFound("numberOfDeliveries.in=" + DEFAULT_NUMBER_OF_DELIVERIES + "," + UPDATED_NUMBER_OF_DELIVERIES);

        // Get all the userInfoList where numberOfDeliveries equals to UPDATED_NUMBER_OF_DELIVERIES
        defaultUserInfoShouldNotBeFound("numberOfDeliveries.in=" + UPDATED_NUMBER_OF_DELIVERIES);
    }

    @Test
    @Transactional
    void getAllUserInfosByNumberOfDeliveriesIsNullOrNotNull() throws Exception {
        // Initialize the database
        userInfoRepository.saveAndFlush(userInfo);

        // Get all the userInfoList where numberOfDeliveries is not null
        defaultUserInfoShouldBeFound("numberOfDeliveries.specified=true");

        // Get all the userInfoList where numberOfDeliveries is null
        defaultUserInfoShouldNotBeFound("numberOfDeliveries.specified=false");
    }

    @Test
    @Transactional
    void getAllUserInfosByNumberOfDeliveriesIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        userInfoRepository.saveAndFlush(userInfo);

        // Get all the userInfoList where numberOfDeliveries is greater than or equal to DEFAULT_NUMBER_OF_DELIVERIES
        defaultUserInfoShouldBeFound("numberOfDeliveries.greaterThanOrEqual=" + DEFAULT_NUMBER_OF_DELIVERIES);

        // Get all the userInfoList where numberOfDeliveries is greater than or equal to UPDATED_NUMBER_OF_DELIVERIES
        defaultUserInfoShouldNotBeFound("numberOfDeliveries.greaterThanOrEqual=" + UPDATED_NUMBER_OF_DELIVERIES);
    }

    @Test
    @Transactional
    void getAllUserInfosByNumberOfDeliveriesIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        userInfoRepository.saveAndFlush(userInfo);

        // Get all the userInfoList where numberOfDeliveries is less than or equal to DEFAULT_NUMBER_OF_DELIVERIES
        defaultUserInfoShouldBeFound("numberOfDeliveries.lessThanOrEqual=" + DEFAULT_NUMBER_OF_DELIVERIES);

        // Get all the userInfoList where numberOfDeliveries is less than or equal to SMALLER_NUMBER_OF_DELIVERIES
        defaultUserInfoShouldNotBeFound("numberOfDeliveries.lessThanOrEqual=" + SMALLER_NUMBER_OF_DELIVERIES);
    }

    @Test
    @Transactional
    void getAllUserInfosByNumberOfDeliveriesIsLessThanSomething() throws Exception {
        // Initialize the database
        userInfoRepository.saveAndFlush(userInfo);

        // Get all the userInfoList where numberOfDeliveries is less than DEFAULT_NUMBER_OF_DELIVERIES
        defaultUserInfoShouldNotBeFound("numberOfDeliveries.lessThan=" + DEFAULT_NUMBER_OF_DELIVERIES);

        // Get all the userInfoList where numberOfDeliveries is less than UPDATED_NUMBER_OF_DELIVERIES
        defaultUserInfoShouldBeFound("numberOfDeliveries.lessThan=" + UPDATED_NUMBER_OF_DELIVERIES);
    }

    @Test
    @Transactional
    void getAllUserInfosByNumberOfDeliveriesIsGreaterThanSomething() throws Exception {
        // Initialize the database
        userInfoRepository.saveAndFlush(userInfo);

        // Get all the userInfoList where numberOfDeliveries is greater than DEFAULT_NUMBER_OF_DELIVERIES
        defaultUserInfoShouldNotBeFound("numberOfDeliveries.greaterThan=" + DEFAULT_NUMBER_OF_DELIVERIES);

        // Get all the userInfoList where numberOfDeliveries is greater than SMALLER_NUMBER_OF_DELIVERIES
        defaultUserInfoShouldBeFound("numberOfDeliveries.greaterThan=" + SMALLER_NUMBER_OF_DELIVERIES);
    }

    @Test
    @Transactional
    void getAllUserInfosByNumberOfKmIsEqualToSomething() throws Exception {
        // Initialize the database
        userInfoRepository.saveAndFlush(userInfo);

        // Get all the userInfoList where numberOfKm equals to DEFAULT_NUMBER_OF_KM
        defaultUserInfoShouldBeFound("numberOfKm.equals=" + DEFAULT_NUMBER_OF_KM);

        // Get all the userInfoList where numberOfKm equals to UPDATED_NUMBER_OF_KM
        defaultUserInfoShouldNotBeFound("numberOfKm.equals=" + UPDATED_NUMBER_OF_KM);
    }

    @Test
    @Transactional
    void getAllUserInfosByNumberOfKmIsNotEqualToSomething() throws Exception {
        // Initialize the database
        userInfoRepository.saveAndFlush(userInfo);

        // Get all the userInfoList where numberOfKm not equals to DEFAULT_NUMBER_OF_KM
        defaultUserInfoShouldNotBeFound("numberOfKm.notEquals=" + DEFAULT_NUMBER_OF_KM);

        // Get all the userInfoList where numberOfKm not equals to UPDATED_NUMBER_OF_KM
        defaultUserInfoShouldBeFound("numberOfKm.notEquals=" + UPDATED_NUMBER_OF_KM);
    }

    @Test
    @Transactional
    void getAllUserInfosByNumberOfKmIsInShouldWork() throws Exception {
        // Initialize the database
        userInfoRepository.saveAndFlush(userInfo);

        // Get all the userInfoList where numberOfKm in DEFAULT_NUMBER_OF_KM or UPDATED_NUMBER_OF_KM
        defaultUserInfoShouldBeFound("numberOfKm.in=" + DEFAULT_NUMBER_OF_KM + "," + UPDATED_NUMBER_OF_KM);

        // Get all the userInfoList where numberOfKm equals to UPDATED_NUMBER_OF_KM
        defaultUserInfoShouldNotBeFound("numberOfKm.in=" + UPDATED_NUMBER_OF_KM);
    }

    @Test
    @Transactional
    void getAllUserInfosByNumberOfKmIsNullOrNotNull() throws Exception {
        // Initialize the database
        userInfoRepository.saveAndFlush(userInfo);

        // Get all the userInfoList where numberOfKm is not null
        defaultUserInfoShouldBeFound("numberOfKm.specified=true");

        // Get all the userInfoList where numberOfKm is null
        defaultUserInfoShouldNotBeFound("numberOfKm.specified=false");
    }

    @Test
    @Transactional
    void getAllUserInfosByNumberOfKmIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        userInfoRepository.saveAndFlush(userInfo);

        // Get all the userInfoList where numberOfKm is greater than or equal to DEFAULT_NUMBER_OF_KM
        defaultUserInfoShouldBeFound("numberOfKm.greaterThanOrEqual=" + DEFAULT_NUMBER_OF_KM);

        // Get all the userInfoList where numberOfKm is greater than or equal to UPDATED_NUMBER_OF_KM
        defaultUserInfoShouldNotBeFound("numberOfKm.greaterThanOrEqual=" + UPDATED_NUMBER_OF_KM);
    }

    @Test
    @Transactional
    void getAllUserInfosByNumberOfKmIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        userInfoRepository.saveAndFlush(userInfo);

        // Get all the userInfoList where numberOfKm is less than or equal to DEFAULT_NUMBER_OF_KM
        defaultUserInfoShouldBeFound("numberOfKm.lessThanOrEqual=" + DEFAULT_NUMBER_OF_KM);

        // Get all the userInfoList where numberOfKm is less than or equal to SMALLER_NUMBER_OF_KM
        defaultUserInfoShouldNotBeFound("numberOfKm.lessThanOrEqual=" + SMALLER_NUMBER_OF_KM);
    }

    @Test
    @Transactional
    void getAllUserInfosByNumberOfKmIsLessThanSomething() throws Exception {
        // Initialize the database
        userInfoRepository.saveAndFlush(userInfo);

        // Get all the userInfoList where numberOfKm is less than DEFAULT_NUMBER_OF_KM
        defaultUserInfoShouldNotBeFound("numberOfKm.lessThan=" + DEFAULT_NUMBER_OF_KM);

        // Get all the userInfoList where numberOfKm is less than UPDATED_NUMBER_OF_KM
        defaultUserInfoShouldBeFound("numberOfKm.lessThan=" + UPDATED_NUMBER_OF_KM);
    }

    @Test
    @Transactional
    void getAllUserInfosByNumberOfKmIsGreaterThanSomething() throws Exception {
        // Initialize the database
        userInfoRepository.saveAndFlush(userInfo);

        // Get all the userInfoList where numberOfKm is greater than DEFAULT_NUMBER_OF_KM
        defaultUserInfoShouldNotBeFound("numberOfKm.greaterThan=" + DEFAULT_NUMBER_OF_KM);

        // Get all the userInfoList where numberOfKm is greater than SMALLER_NUMBER_OF_KM
        defaultUserInfoShouldBeFound("numberOfKm.greaterThan=" + SMALLER_NUMBER_OF_KM);
    }

    @Test
    @Transactional
    void getAllUserInfosByRequestsIsEqualToSomething() throws Exception {
        // Initialize the database
        userInfoRepository.saveAndFlush(userInfo);
        Request requests;
        if (TestUtil.findAll(em, Request.class).isEmpty()) {
            requests = RequestResourceIT.createEntity(em);
            em.persist(requests);
            em.flush();
        } else {
            requests = TestUtil.findAll(em, Request.class).get(0);
        }
        em.persist(requests);
        em.flush();
        userInfo.addRequests(requests);
        userInfoRepository.saveAndFlush(userInfo);
        Long requestsId = requests.getId();

        // Get all the userInfoList where requests equals to requestsId
        defaultUserInfoShouldBeFound("requestsId.equals=" + requestsId);

        // Get all the userInfoList where requests equals to (requestsId + 1)
        defaultUserInfoShouldNotBeFound("requestsId.equals=" + (requestsId + 1));
    }

    @Test
    @Transactional
    void getAllUserInfosByTransportationsIsEqualToSomething() throws Exception {
        // Initialize the database
        userInfoRepository.saveAndFlush(userInfo);
        Request transportations;
        if (TestUtil.findAll(em, Request.class).isEmpty()) {
            transportations = RequestResourceIT.createEntity(em);
            em.persist(transportations);
            em.flush();
        } else {
            transportations = TestUtil.findAll(em, Request.class).get(0);
        }
        em.persist(transportations);
        em.flush();
        userInfo.addTransportations(transportations);
        userInfoRepository.saveAndFlush(userInfo);
        Long transportationsId = transportations.getId();

        // Get all the userInfoList where transportations equals to transportationsId
        defaultUserInfoShouldBeFound("transportationsId.equals=" + transportationsId);

        // Get all the userInfoList where transportations equals to (transportationsId + 1)
        defaultUserInfoShouldNotBeFound("transportationsId.equals=" + (transportationsId + 1));
    }

    @Test
    @Transactional
    void getAllUserInfosByPointIsEqualToSomething() throws Exception {
        // Initialize the database
        userInfoRepository.saveAndFlush(userInfo);
        Point point;
        if (TestUtil.findAll(em, Point.class).isEmpty()) {
            point = PointResourceIT.createEntity(em);
            em.persist(point);
            em.flush();
        } else {
            point = TestUtil.findAll(em, Point.class).get(0);
        }
        em.persist(point);
        em.flush();
        userInfo.addPoint(point);
        userInfoRepository.saveAndFlush(userInfo);
        Long pointId = point.getId();

        // Get all the userInfoList where point equals to pointId
        defaultUserInfoShouldBeFound("pointId.equals=" + pointId);

        // Get all the userInfoList where point equals to (pointId + 1)
        defaultUserInfoShouldNotBeFound("pointId.equals=" + (pointId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultUserInfoShouldBeFound(String filter) throws Exception {
        restUserInfoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(userInfo.getId().intValue())))
            .andExpect(jsonPath("$.[*].phoneNumber").value(hasItem(DEFAULT_PHONE_NUMBER)))
            .andExpect(jsonPath("$.[*].nib").value(hasItem(DEFAULT_NIB)))
            .andExpect(jsonPath("$.[*].nif").value(hasItem(DEFAULT_NIF)))
            .andExpect(jsonPath("$.[*].birthday").value(hasItem(DEFAULT_BIRTHDAY.toString())))
            .andExpect(jsonPath("$.[*].address").value(hasItem(DEFAULT_ADDRESS)))
            .andExpect(jsonPath("$.[*].linkSocial").value(hasItem(DEFAULT_LINK_SOCIAL)))
            .andExpect(jsonPath("$.[*].numberRequests").value(hasItem(DEFAULT_NUMBER_REQUESTS)))
            .andExpect(jsonPath("$.[*].payedValue").value(hasItem(DEFAULT_PAYED_VALUE.doubleValue())))
            .andExpect(jsonPath("$.[*].valueToPay").value(hasItem(DEFAULT_VALUE_TO_PAY.doubleValue())))
            .andExpect(jsonPath("$.[*].ranking").value(hasItem(DEFAULT_RANKING.doubleValue())))
            .andExpect(jsonPath("$.[*].numberOfDeliveries").value(hasItem(DEFAULT_NUMBER_OF_DELIVERIES)))
            .andExpect(jsonPath("$.[*].numberOfKm").value(hasItem(DEFAULT_NUMBER_OF_KM.doubleValue())));

        // Check, that the count call also returns 1
        restUserInfoMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultUserInfoShouldNotBeFound(String filter) throws Exception {
        restUserInfoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restUserInfoMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingUserInfo() throws Exception {
        // Get the userInfo
        restUserInfoMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewUserInfo() throws Exception {
        // Initialize the database
        userInfoRepository.saveAndFlush(userInfo);

        int databaseSizeBeforeUpdate = userInfoRepository.findAll().size();

        // Update the userInfo
        UserInfo updatedUserInfo = userInfoRepository.findById(userInfo.getId()).get();
        // Disconnect from session so that the updates on updatedUserInfo are not directly saved in db
        em.detach(updatedUserInfo);
        updatedUserInfo
            .phoneNumber(UPDATED_PHONE_NUMBER)
            .nib(UPDATED_NIB)
            .nif(UPDATED_NIF)
            .birthday(UPDATED_BIRTHDAY)
            .address(UPDATED_ADDRESS)
            .linkSocial(UPDATED_LINK_SOCIAL)
            .numberRequests(UPDATED_NUMBER_REQUESTS)
            .payedValue(UPDATED_PAYED_VALUE)
            .valueToPay(UPDATED_VALUE_TO_PAY)
            .ranking(UPDATED_RANKING)
            .numberOfDeliveries(UPDATED_NUMBER_OF_DELIVERIES)
            .numberOfKm(UPDATED_NUMBER_OF_KM);
        UserInfoDTO userInfoDTO = userInfoMapper.toDto(updatedUserInfo);

        restUserInfoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, userInfoDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(userInfoDTO))
            )
            .andExpect(status().isOk());

        // Validate the UserInfo in the database
        List<UserInfo> userInfoList = userInfoRepository.findAll();
        assertThat(userInfoList).hasSize(databaseSizeBeforeUpdate);
        UserInfo testUserInfo = userInfoList.get(userInfoList.size() - 1);
        assertThat(testUserInfo.getPhoneNumber()).isEqualTo(UPDATED_PHONE_NUMBER);
        assertThat(testUserInfo.getNib()).isEqualTo(UPDATED_NIB);
        assertThat(testUserInfo.getNif()).isEqualTo(UPDATED_NIF);
        assertThat(testUserInfo.getBirthday()).isEqualTo(UPDATED_BIRTHDAY);
        assertThat(testUserInfo.getAddress()).isEqualTo(UPDATED_ADDRESS);
        assertThat(testUserInfo.getLinkSocial()).isEqualTo(UPDATED_LINK_SOCIAL);
        assertThat(testUserInfo.getNumberRequests()).isEqualTo(UPDATED_NUMBER_REQUESTS);
        assertThat(testUserInfo.getPayedValue()).isEqualTo(UPDATED_PAYED_VALUE);
        assertThat(testUserInfo.getValueToPay()).isEqualTo(UPDATED_VALUE_TO_PAY);
        assertThat(testUserInfo.getRanking()).isEqualTo(UPDATED_RANKING);
        assertThat(testUserInfo.getNumberOfDeliveries()).isEqualTo(UPDATED_NUMBER_OF_DELIVERIES);
        assertThat(testUserInfo.getNumberOfKm()).isEqualTo(UPDATED_NUMBER_OF_KM);
    }

    @Test
    @Transactional
    void putNonExistingUserInfo() throws Exception {
        int databaseSizeBeforeUpdate = userInfoRepository.findAll().size();
        userInfo.setId(count.incrementAndGet());

        // Create the UserInfo
        UserInfoDTO userInfoDTO = userInfoMapper.toDto(userInfo);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUserInfoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, userInfoDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(userInfoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the UserInfo in the database
        List<UserInfo> userInfoList = userInfoRepository.findAll();
        assertThat(userInfoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchUserInfo() throws Exception {
        int databaseSizeBeforeUpdate = userInfoRepository.findAll().size();
        userInfo.setId(count.incrementAndGet());

        // Create the UserInfo
        UserInfoDTO userInfoDTO = userInfoMapper.toDto(userInfo);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUserInfoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(userInfoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the UserInfo in the database
        List<UserInfo> userInfoList = userInfoRepository.findAll();
        assertThat(userInfoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamUserInfo() throws Exception {
        int databaseSizeBeforeUpdate = userInfoRepository.findAll().size();
        userInfo.setId(count.incrementAndGet());

        // Create the UserInfo
        UserInfoDTO userInfoDTO = userInfoMapper.toDto(userInfo);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUserInfoMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(userInfoDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the UserInfo in the database
        List<UserInfo> userInfoList = userInfoRepository.findAll();
        assertThat(userInfoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateUserInfoWithPatch() throws Exception {
        // Initialize the database
        userInfoRepository.saveAndFlush(userInfo);

        int databaseSizeBeforeUpdate = userInfoRepository.findAll().size();

        // Update the userInfo using partial update
        UserInfo partialUpdatedUserInfo = new UserInfo();
        partialUpdatedUserInfo.setId(userInfo.getId());

        partialUpdatedUserInfo
            .nib(UPDATED_NIB)
            .nif(UPDATED_NIF)
            .birthday(UPDATED_BIRTHDAY)
            .payedValue(UPDATED_PAYED_VALUE)
            .valueToPay(UPDATED_VALUE_TO_PAY)
            .ranking(UPDATED_RANKING)
            .numberOfKm(UPDATED_NUMBER_OF_KM);

        restUserInfoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedUserInfo.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedUserInfo))
            )
            .andExpect(status().isOk());

        // Validate the UserInfo in the database
        List<UserInfo> userInfoList = userInfoRepository.findAll();
        assertThat(userInfoList).hasSize(databaseSizeBeforeUpdate);
        UserInfo testUserInfo = userInfoList.get(userInfoList.size() - 1);
        assertThat(testUserInfo.getPhoneNumber()).isEqualTo(DEFAULT_PHONE_NUMBER);
        assertThat(testUserInfo.getNib()).isEqualTo(UPDATED_NIB);
        assertThat(testUserInfo.getNif()).isEqualTo(UPDATED_NIF);
        assertThat(testUserInfo.getBirthday()).isEqualTo(UPDATED_BIRTHDAY);
        assertThat(testUserInfo.getAddress()).isEqualTo(DEFAULT_ADDRESS);
        assertThat(testUserInfo.getLinkSocial()).isEqualTo(DEFAULT_LINK_SOCIAL);
        assertThat(testUserInfo.getNumberRequests()).isEqualTo(DEFAULT_NUMBER_REQUESTS);
        assertThat(testUserInfo.getPayedValue()).isEqualTo(UPDATED_PAYED_VALUE);
        assertThat(testUserInfo.getValueToPay()).isEqualTo(UPDATED_VALUE_TO_PAY);
        assertThat(testUserInfo.getRanking()).isEqualTo(UPDATED_RANKING);
        assertThat(testUserInfo.getNumberOfDeliveries()).isEqualTo(DEFAULT_NUMBER_OF_DELIVERIES);
        assertThat(testUserInfo.getNumberOfKm()).isEqualTo(UPDATED_NUMBER_OF_KM);
    }

    @Test
    @Transactional
    void fullUpdateUserInfoWithPatch() throws Exception {
        // Initialize the database
        userInfoRepository.saveAndFlush(userInfo);

        int databaseSizeBeforeUpdate = userInfoRepository.findAll().size();

        // Update the userInfo using partial update
        UserInfo partialUpdatedUserInfo = new UserInfo();
        partialUpdatedUserInfo.setId(userInfo.getId());

        partialUpdatedUserInfo
            .phoneNumber(UPDATED_PHONE_NUMBER)
            .nib(UPDATED_NIB)
            .nif(UPDATED_NIF)
            .birthday(UPDATED_BIRTHDAY)
            .address(UPDATED_ADDRESS)
            .linkSocial(UPDATED_LINK_SOCIAL)
            .numberRequests(UPDATED_NUMBER_REQUESTS)
            .payedValue(UPDATED_PAYED_VALUE)
            .valueToPay(UPDATED_VALUE_TO_PAY)
            .ranking(UPDATED_RANKING)
            .numberOfDeliveries(UPDATED_NUMBER_OF_DELIVERIES)
            .numberOfKm(UPDATED_NUMBER_OF_KM);

        restUserInfoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedUserInfo.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedUserInfo))
            )
            .andExpect(status().isOk());

        // Validate the UserInfo in the database
        List<UserInfo> userInfoList = userInfoRepository.findAll();
        assertThat(userInfoList).hasSize(databaseSizeBeforeUpdate);
        UserInfo testUserInfo = userInfoList.get(userInfoList.size() - 1);
        assertThat(testUserInfo.getPhoneNumber()).isEqualTo(UPDATED_PHONE_NUMBER);
        assertThat(testUserInfo.getNib()).isEqualTo(UPDATED_NIB);
        assertThat(testUserInfo.getNif()).isEqualTo(UPDATED_NIF);
        assertThat(testUserInfo.getBirthday()).isEqualTo(UPDATED_BIRTHDAY);
        assertThat(testUserInfo.getAddress()).isEqualTo(UPDATED_ADDRESS);
        assertThat(testUserInfo.getLinkSocial()).isEqualTo(UPDATED_LINK_SOCIAL);
        assertThat(testUserInfo.getNumberRequests()).isEqualTo(UPDATED_NUMBER_REQUESTS);
        assertThat(testUserInfo.getPayedValue()).isEqualTo(UPDATED_PAYED_VALUE);
        assertThat(testUserInfo.getValueToPay()).isEqualTo(UPDATED_VALUE_TO_PAY);
        assertThat(testUserInfo.getRanking()).isEqualTo(UPDATED_RANKING);
        assertThat(testUserInfo.getNumberOfDeliveries()).isEqualTo(UPDATED_NUMBER_OF_DELIVERIES);
        assertThat(testUserInfo.getNumberOfKm()).isEqualTo(UPDATED_NUMBER_OF_KM);
    }

    @Test
    @Transactional
    void patchNonExistingUserInfo() throws Exception {
        int databaseSizeBeforeUpdate = userInfoRepository.findAll().size();
        userInfo.setId(count.incrementAndGet());

        // Create the UserInfo
        UserInfoDTO userInfoDTO = userInfoMapper.toDto(userInfo);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUserInfoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, userInfoDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(userInfoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the UserInfo in the database
        List<UserInfo> userInfoList = userInfoRepository.findAll();
        assertThat(userInfoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchUserInfo() throws Exception {
        int databaseSizeBeforeUpdate = userInfoRepository.findAll().size();
        userInfo.setId(count.incrementAndGet());

        // Create the UserInfo
        UserInfoDTO userInfoDTO = userInfoMapper.toDto(userInfo);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUserInfoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(userInfoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the UserInfo in the database
        List<UserInfo> userInfoList = userInfoRepository.findAll();
        assertThat(userInfoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamUserInfo() throws Exception {
        int databaseSizeBeforeUpdate = userInfoRepository.findAll().size();
        userInfo.setId(count.incrementAndGet());

        // Create the UserInfo
        UserInfoDTO userInfoDTO = userInfoMapper.toDto(userInfo);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUserInfoMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(userInfoDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the UserInfo in the database
        List<UserInfo> userInfoList = userInfoRepository.findAll();
        assertThat(userInfoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteUserInfo() throws Exception {
        // Initialize the database
        userInfoRepository.saveAndFlush(userInfo);

        int databaseSizeBeforeDelete = userInfoRepository.findAll().size();

        // Delete the userInfo
        restUserInfoMockMvc
            .perform(delete(ENTITY_API_URL_ID, userInfo.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<UserInfo> userInfoList = userInfoRepository.findAll();
        assertThat(userInfoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
