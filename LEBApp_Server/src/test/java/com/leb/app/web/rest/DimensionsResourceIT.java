package com.leb.app.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.leb.app.IntegrationTest;
import com.leb.app.domain.Dimensions;
import com.leb.app.domain.Request;
import com.leb.app.repository.DimensionsRepository;
import com.leb.app.service.criteria.DimensionsCriteria;
import com.leb.app.service.dto.DimensionsDTO;
import com.leb.app.service.mapper.DimensionsMapper;
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
 * Integration tests for the {@link DimensionsResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class DimensionsResourceIT {

    private static final Double DEFAULT_HEIGHT = 1D;
    private static final Double UPDATED_HEIGHT = 2D;
    private static final Double SMALLER_HEIGHT = 1D - 1D;

    private static final Double DEFAULT_WIDTH = 1D;
    private static final Double UPDATED_WIDTH = 2D;
    private static final Double SMALLER_WIDTH = 1D - 1D;

    private static final Double DEFAULT_DEPTH = 1D;
    private static final Double UPDATED_DEPTH = 2D;
    private static final Double SMALLER_DEPTH = 1D - 1D;

    private static final String ENTITY_API_URL = "/api/dimensions";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private DimensionsRepository dimensionsRepository;

    @Autowired
    private DimensionsMapper dimensionsMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDimensionsMockMvc;

    private Dimensions dimensions;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Dimensions createEntity(EntityManager em) {
        Dimensions dimensions = new Dimensions().height(DEFAULT_HEIGHT).width(DEFAULT_WIDTH).depth(DEFAULT_DEPTH);
        return dimensions;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Dimensions createUpdatedEntity(EntityManager em) {
        Dimensions dimensions = new Dimensions().height(UPDATED_HEIGHT).width(UPDATED_WIDTH).depth(UPDATED_DEPTH);
        return dimensions;
    }

    @BeforeEach
    public void initTest() {
        dimensions = createEntity(em);
    }

    @Test
    @Transactional
    void createDimensions() throws Exception {
        int databaseSizeBeforeCreate = dimensionsRepository.findAll().size();
        // Create the Dimensions
        DimensionsDTO dimensionsDTO = dimensionsMapper.toDto(dimensions);
        restDimensionsMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(dimensionsDTO)))
            .andExpect(status().isCreated());

        // Validate the Dimensions in the database
        List<Dimensions> dimensionsList = dimensionsRepository.findAll();
        assertThat(dimensionsList).hasSize(databaseSizeBeforeCreate + 1);
        Dimensions testDimensions = dimensionsList.get(dimensionsList.size() - 1);
        assertThat(testDimensions.getHeight()).isEqualTo(DEFAULT_HEIGHT);
        assertThat(testDimensions.getWidth()).isEqualTo(DEFAULT_WIDTH);
        assertThat(testDimensions.getDepth()).isEqualTo(DEFAULT_DEPTH);
    }

    @Test
    @Transactional
    void createDimensionsWithExistingId() throws Exception {
        // Create the Dimensions with an existing ID
        dimensions.setId(1L);
        DimensionsDTO dimensionsDTO = dimensionsMapper.toDto(dimensions);

        int databaseSizeBeforeCreate = dimensionsRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restDimensionsMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(dimensionsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Dimensions in the database
        List<Dimensions> dimensionsList = dimensionsRepository.findAll();
        assertThat(dimensionsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllDimensions() throws Exception {
        // Initialize the database
        dimensionsRepository.saveAndFlush(dimensions);

        // Get all the dimensionsList
        restDimensionsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(dimensions.getId().intValue())))
            .andExpect(jsonPath("$.[*].height").value(hasItem(DEFAULT_HEIGHT.doubleValue())))
            .andExpect(jsonPath("$.[*].width").value(hasItem(DEFAULT_WIDTH.doubleValue())))
            .andExpect(jsonPath("$.[*].depth").value(hasItem(DEFAULT_DEPTH.doubleValue())));
    }

    @Test
    @Transactional
    void getDimensions() throws Exception {
        // Initialize the database
        dimensionsRepository.saveAndFlush(dimensions);

        // Get the dimensions
        restDimensionsMockMvc
            .perform(get(ENTITY_API_URL_ID, dimensions.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(dimensions.getId().intValue()))
            .andExpect(jsonPath("$.height").value(DEFAULT_HEIGHT.doubleValue()))
            .andExpect(jsonPath("$.width").value(DEFAULT_WIDTH.doubleValue()))
            .andExpect(jsonPath("$.depth").value(DEFAULT_DEPTH.doubleValue()));
    }

    @Test
    @Transactional
    void getDimensionsByIdFiltering() throws Exception {
        // Initialize the database
        dimensionsRepository.saveAndFlush(dimensions);

        Long id = dimensions.getId();

        defaultDimensionsShouldBeFound("id.equals=" + id);
        defaultDimensionsShouldNotBeFound("id.notEquals=" + id);

        defaultDimensionsShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultDimensionsShouldNotBeFound("id.greaterThan=" + id);

        defaultDimensionsShouldBeFound("id.lessThanOrEqual=" + id);
        defaultDimensionsShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllDimensionsByHeightIsEqualToSomething() throws Exception {
        // Initialize the database
        dimensionsRepository.saveAndFlush(dimensions);

        // Get all the dimensionsList where height equals to DEFAULT_HEIGHT
        defaultDimensionsShouldBeFound("height.equals=" + DEFAULT_HEIGHT);

        // Get all the dimensionsList where height equals to UPDATED_HEIGHT
        defaultDimensionsShouldNotBeFound("height.equals=" + UPDATED_HEIGHT);
    }

    @Test
    @Transactional
    void getAllDimensionsByHeightIsNotEqualToSomething() throws Exception {
        // Initialize the database
        dimensionsRepository.saveAndFlush(dimensions);

        // Get all the dimensionsList where height not equals to DEFAULT_HEIGHT
        defaultDimensionsShouldNotBeFound("height.notEquals=" + DEFAULT_HEIGHT);

        // Get all the dimensionsList where height not equals to UPDATED_HEIGHT
        defaultDimensionsShouldBeFound("height.notEquals=" + UPDATED_HEIGHT);
    }

    @Test
    @Transactional
    void getAllDimensionsByHeightIsInShouldWork() throws Exception {
        // Initialize the database
        dimensionsRepository.saveAndFlush(dimensions);

        // Get all the dimensionsList where height in DEFAULT_HEIGHT or UPDATED_HEIGHT
        defaultDimensionsShouldBeFound("height.in=" + DEFAULT_HEIGHT + "," + UPDATED_HEIGHT);

        // Get all the dimensionsList where height equals to UPDATED_HEIGHT
        defaultDimensionsShouldNotBeFound("height.in=" + UPDATED_HEIGHT);
    }

    @Test
    @Transactional
    void getAllDimensionsByHeightIsNullOrNotNull() throws Exception {
        // Initialize the database
        dimensionsRepository.saveAndFlush(dimensions);

        // Get all the dimensionsList where height is not null
        defaultDimensionsShouldBeFound("height.specified=true");

        // Get all the dimensionsList where height is null
        defaultDimensionsShouldNotBeFound("height.specified=false");
    }

    @Test
    @Transactional
    void getAllDimensionsByHeightIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        dimensionsRepository.saveAndFlush(dimensions);

        // Get all the dimensionsList where height is greater than or equal to DEFAULT_HEIGHT
        defaultDimensionsShouldBeFound("height.greaterThanOrEqual=" + DEFAULT_HEIGHT);

        // Get all the dimensionsList where height is greater than or equal to UPDATED_HEIGHT
        defaultDimensionsShouldNotBeFound("height.greaterThanOrEqual=" + UPDATED_HEIGHT);
    }

    @Test
    @Transactional
    void getAllDimensionsByHeightIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        dimensionsRepository.saveAndFlush(dimensions);

        // Get all the dimensionsList where height is less than or equal to DEFAULT_HEIGHT
        defaultDimensionsShouldBeFound("height.lessThanOrEqual=" + DEFAULT_HEIGHT);

        // Get all the dimensionsList where height is less than or equal to SMALLER_HEIGHT
        defaultDimensionsShouldNotBeFound("height.lessThanOrEqual=" + SMALLER_HEIGHT);
    }

    @Test
    @Transactional
    void getAllDimensionsByHeightIsLessThanSomething() throws Exception {
        // Initialize the database
        dimensionsRepository.saveAndFlush(dimensions);

        // Get all the dimensionsList where height is less than DEFAULT_HEIGHT
        defaultDimensionsShouldNotBeFound("height.lessThan=" + DEFAULT_HEIGHT);

        // Get all the dimensionsList where height is less than UPDATED_HEIGHT
        defaultDimensionsShouldBeFound("height.lessThan=" + UPDATED_HEIGHT);
    }

    @Test
    @Transactional
    void getAllDimensionsByHeightIsGreaterThanSomething() throws Exception {
        // Initialize the database
        dimensionsRepository.saveAndFlush(dimensions);

        // Get all the dimensionsList where height is greater than DEFAULT_HEIGHT
        defaultDimensionsShouldNotBeFound("height.greaterThan=" + DEFAULT_HEIGHT);

        // Get all the dimensionsList where height is greater than SMALLER_HEIGHT
        defaultDimensionsShouldBeFound("height.greaterThan=" + SMALLER_HEIGHT);
    }

    @Test
    @Transactional
    void getAllDimensionsByWidthIsEqualToSomething() throws Exception {
        // Initialize the database
        dimensionsRepository.saveAndFlush(dimensions);

        // Get all the dimensionsList where width equals to DEFAULT_WIDTH
        defaultDimensionsShouldBeFound("width.equals=" + DEFAULT_WIDTH);

        // Get all the dimensionsList where width equals to UPDATED_WIDTH
        defaultDimensionsShouldNotBeFound("width.equals=" + UPDATED_WIDTH);
    }

    @Test
    @Transactional
    void getAllDimensionsByWidthIsNotEqualToSomething() throws Exception {
        // Initialize the database
        dimensionsRepository.saveAndFlush(dimensions);

        // Get all the dimensionsList where width not equals to DEFAULT_WIDTH
        defaultDimensionsShouldNotBeFound("width.notEquals=" + DEFAULT_WIDTH);

        // Get all the dimensionsList where width not equals to UPDATED_WIDTH
        defaultDimensionsShouldBeFound("width.notEquals=" + UPDATED_WIDTH);
    }

    @Test
    @Transactional
    void getAllDimensionsByWidthIsInShouldWork() throws Exception {
        // Initialize the database
        dimensionsRepository.saveAndFlush(dimensions);

        // Get all the dimensionsList where width in DEFAULT_WIDTH or UPDATED_WIDTH
        defaultDimensionsShouldBeFound("width.in=" + DEFAULT_WIDTH + "," + UPDATED_WIDTH);

        // Get all the dimensionsList where width equals to UPDATED_WIDTH
        defaultDimensionsShouldNotBeFound("width.in=" + UPDATED_WIDTH);
    }

    @Test
    @Transactional
    void getAllDimensionsByWidthIsNullOrNotNull() throws Exception {
        // Initialize the database
        dimensionsRepository.saveAndFlush(dimensions);

        // Get all the dimensionsList where width is not null
        defaultDimensionsShouldBeFound("width.specified=true");

        // Get all the dimensionsList where width is null
        defaultDimensionsShouldNotBeFound("width.specified=false");
    }

    @Test
    @Transactional
    void getAllDimensionsByWidthIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        dimensionsRepository.saveAndFlush(dimensions);

        // Get all the dimensionsList where width is greater than or equal to DEFAULT_WIDTH
        defaultDimensionsShouldBeFound("width.greaterThanOrEqual=" + DEFAULT_WIDTH);

        // Get all the dimensionsList where width is greater than or equal to UPDATED_WIDTH
        defaultDimensionsShouldNotBeFound("width.greaterThanOrEqual=" + UPDATED_WIDTH);
    }

    @Test
    @Transactional
    void getAllDimensionsByWidthIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        dimensionsRepository.saveAndFlush(dimensions);

        // Get all the dimensionsList where width is less than or equal to DEFAULT_WIDTH
        defaultDimensionsShouldBeFound("width.lessThanOrEqual=" + DEFAULT_WIDTH);

        // Get all the dimensionsList where width is less than or equal to SMALLER_WIDTH
        defaultDimensionsShouldNotBeFound("width.lessThanOrEqual=" + SMALLER_WIDTH);
    }

    @Test
    @Transactional
    void getAllDimensionsByWidthIsLessThanSomething() throws Exception {
        // Initialize the database
        dimensionsRepository.saveAndFlush(dimensions);

        // Get all the dimensionsList where width is less than DEFAULT_WIDTH
        defaultDimensionsShouldNotBeFound("width.lessThan=" + DEFAULT_WIDTH);

        // Get all the dimensionsList where width is less than UPDATED_WIDTH
        defaultDimensionsShouldBeFound("width.lessThan=" + UPDATED_WIDTH);
    }

    @Test
    @Transactional
    void getAllDimensionsByWidthIsGreaterThanSomething() throws Exception {
        // Initialize the database
        dimensionsRepository.saveAndFlush(dimensions);

        // Get all the dimensionsList where width is greater than DEFAULT_WIDTH
        defaultDimensionsShouldNotBeFound("width.greaterThan=" + DEFAULT_WIDTH);

        // Get all the dimensionsList where width is greater than SMALLER_WIDTH
        defaultDimensionsShouldBeFound("width.greaterThan=" + SMALLER_WIDTH);
    }

    @Test
    @Transactional
    void getAllDimensionsByDepthIsEqualToSomething() throws Exception {
        // Initialize the database
        dimensionsRepository.saveAndFlush(dimensions);

        // Get all the dimensionsList where depth equals to DEFAULT_DEPTH
        defaultDimensionsShouldBeFound("depth.equals=" + DEFAULT_DEPTH);

        // Get all the dimensionsList where depth equals to UPDATED_DEPTH
        defaultDimensionsShouldNotBeFound("depth.equals=" + UPDATED_DEPTH);
    }

    @Test
    @Transactional
    void getAllDimensionsByDepthIsNotEqualToSomething() throws Exception {
        // Initialize the database
        dimensionsRepository.saveAndFlush(dimensions);

        // Get all the dimensionsList where depth not equals to DEFAULT_DEPTH
        defaultDimensionsShouldNotBeFound("depth.notEquals=" + DEFAULT_DEPTH);

        // Get all the dimensionsList where depth not equals to UPDATED_DEPTH
        defaultDimensionsShouldBeFound("depth.notEquals=" + UPDATED_DEPTH);
    }

    @Test
    @Transactional
    void getAllDimensionsByDepthIsInShouldWork() throws Exception {
        // Initialize the database
        dimensionsRepository.saveAndFlush(dimensions);

        // Get all the dimensionsList where depth in DEFAULT_DEPTH or UPDATED_DEPTH
        defaultDimensionsShouldBeFound("depth.in=" + DEFAULT_DEPTH + "," + UPDATED_DEPTH);

        // Get all the dimensionsList where depth equals to UPDATED_DEPTH
        defaultDimensionsShouldNotBeFound("depth.in=" + UPDATED_DEPTH);
    }

    @Test
    @Transactional
    void getAllDimensionsByDepthIsNullOrNotNull() throws Exception {
        // Initialize the database
        dimensionsRepository.saveAndFlush(dimensions);

        // Get all the dimensionsList where depth is not null
        defaultDimensionsShouldBeFound("depth.specified=true");

        // Get all the dimensionsList where depth is null
        defaultDimensionsShouldNotBeFound("depth.specified=false");
    }

    @Test
    @Transactional
    void getAllDimensionsByDepthIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        dimensionsRepository.saveAndFlush(dimensions);

        // Get all the dimensionsList where depth is greater than or equal to DEFAULT_DEPTH
        defaultDimensionsShouldBeFound("depth.greaterThanOrEqual=" + DEFAULT_DEPTH);

        // Get all the dimensionsList where depth is greater than or equal to UPDATED_DEPTH
        defaultDimensionsShouldNotBeFound("depth.greaterThanOrEqual=" + UPDATED_DEPTH);
    }

    @Test
    @Transactional
    void getAllDimensionsByDepthIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        dimensionsRepository.saveAndFlush(dimensions);

        // Get all the dimensionsList where depth is less than or equal to DEFAULT_DEPTH
        defaultDimensionsShouldBeFound("depth.lessThanOrEqual=" + DEFAULT_DEPTH);

        // Get all the dimensionsList where depth is less than or equal to SMALLER_DEPTH
        defaultDimensionsShouldNotBeFound("depth.lessThanOrEqual=" + SMALLER_DEPTH);
    }

    @Test
    @Transactional
    void getAllDimensionsByDepthIsLessThanSomething() throws Exception {
        // Initialize the database
        dimensionsRepository.saveAndFlush(dimensions);

        // Get all the dimensionsList where depth is less than DEFAULT_DEPTH
        defaultDimensionsShouldNotBeFound("depth.lessThan=" + DEFAULT_DEPTH);

        // Get all the dimensionsList where depth is less than UPDATED_DEPTH
        defaultDimensionsShouldBeFound("depth.lessThan=" + UPDATED_DEPTH);
    }

    @Test
    @Transactional
    void getAllDimensionsByDepthIsGreaterThanSomething() throws Exception {
        // Initialize the database
        dimensionsRepository.saveAndFlush(dimensions);

        // Get all the dimensionsList where depth is greater than DEFAULT_DEPTH
        defaultDimensionsShouldNotBeFound("depth.greaterThan=" + DEFAULT_DEPTH);

        // Get all the dimensionsList where depth is greater than SMALLER_DEPTH
        defaultDimensionsShouldBeFound("depth.greaterThan=" + SMALLER_DEPTH);
    }

    @Test
    @Transactional
    void getAllDimensionsByRequestIsEqualToSomething() throws Exception {
        // Initialize the database
        dimensionsRepository.saveAndFlush(dimensions);
        Request request = RequestResourceIT.createEntity(em);
        em.persist(request);
        em.flush();
        dimensions.setRequest(request);
        request.setDimensions(dimensions);
        dimensionsRepository.saveAndFlush(dimensions);
        Long requestId = request.getId();

        // Get all the dimensionsList where request equals to requestId
        defaultDimensionsShouldBeFound("requestId.equals=" + requestId);

        // Get all the dimensionsList where request equals to (requestId + 1)
        defaultDimensionsShouldNotBeFound("requestId.equals=" + (requestId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultDimensionsShouldBeFound(String filter) throws Exception {
        restDimensionsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(dimensions.getId().intValue())))
            .andExpect(jsonPath("$.[*].height").value(hasItem(DEFAULT_HEIGHT.doubleValue())))
            .andExpect(jsonPath("$.[*].width").value(hasItem(DEFAULT_WIDTH.doubleValue())))
            .andExpect(jsonPath("$.[*].depth").value(hasItem(DEFAULT_DEPTH.doubleValue())));

        // Check, that the count call also returns 1
        restDimensionsMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultDimensionsShouldNotBeFound(String filter) throws Exception {
        restDimensionsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restDimensionsMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingDimensions() throws Exception {
        // Get the dimensions
        restDimensionsMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewDimensions() throws Exception {
        // Initialize the database
        dimensionsRepository.saveAndFlush(dimensions);

        int databaseSizeBeforeUpdate = dimensionsRepository.findAll().size();

        // Update the dimensions
        Dimensions updatedDimensions = dimensionsRepository.findById(dimensions.getId()).get();
        // Disconnect from session so that the updates on updatedDimensions are not directly saved in db
        em.detach(updatedDimensions);
        updatedDimensions.height(UPDATED_HEIGHT).width(UPDATED_WIDTH).depth(UPDATED_DEPTH);
        DimensionsDTO dimensionsDTO = dimensionsMapper.toDto(updatedDimensions);

        restDimensionsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, dimensionsDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(dimensionsDTO))
            )
            .andExpect(status().isOk());

        // Validate the Dimensions in the database
        List<Dimensions> dimensionsList = dimensionsRepository.findAll();
        assertThat(dimensionsList).hasSize(databaseSizeBeforeUpdate);
        Dimensions testDimensions = dimensionsList.get(dimensionsList.size() - 1);
        assertThat(testDimensions.getHeight()).isEqualTo(UPDATED_HEIGHT);
        assertThat(testDimensions.getWidth()).isEqualTo(UPDATED_WIDTH);
        assertThat(testDimensions.getDepth()).isEqualTo(UPDATED_DEPTH);
    }

    @Test
    @Transactional
    void putNonExistingDimensions() throws Exception {
        int databaseSizeBeforeUpdate = dimensionsRepository.findAll().size();
        dimensions.setId(count.incrementAndGet());

        // Create the Dimensions
        DimensionsDTO dimensionsDTO = dimensionsMapper.toDto(dimensions);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDimensionsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, dimensionsDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(dimensionsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Dimensions in the database
        List<Dimensions> dimensionsList = dimensionsRepository.findAll();
        assertThat(dimensionsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchDimensions() throws Exception {
        int databaseSizeBeforeUpdate = dimensionsRepository.findAll().size();
        dimensions.setId(count.incrementAndGet());

        // Create the Dimensions
        DimensionsDTO dimensionsDTO = dimensionsMapper.toDto(dimensions);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDimensionsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(dimensionsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Dimensions in the database
        List<Dimensions> dimensionsList = dimensionsRepository.findAll();
        assertThat(dimensionsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamDimensions() throws Exception {
        int databaseSizeBeforeUpdate = dimensionsRepository.findAll().size();
        dimensions.setId(count.incrementAndGet());

        // Create the Dimensions
        DimensionsDTO dimensionsDTO = dimensionsMapper.toDto(dimensions);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDimensionsMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(dimensionsDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Dimensions in the database
        List<Dimensions> dimensionsList = dimensionsRepository.findAll();
        assertThat(dimensionsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateDimensionsWithPatch() throws Exception {
        // Initialize the database
        dimensionsRepository.saveAndFlush(dimensions);

        int databaseSizeBeforeUpdate = dimensionsRepository.findAll().size();

        // Update the dimensions using partial update
        Dimensions partialUpdatedDimensions = new Dimensions();
        partialUpdatedDimensions.setId(dimensions.getId());

        partialUpdatedDimensions.height(UPDATED_HEIGHT).depth(UPDATED_DEPTH);

        restDimensionsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDimensions.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDimensions))
            )
            .andExpect(status().isOk());

        // Validate the Dimensions in the database
        List<Dimensions> dimensionsList = dimensionsRepository.findAll();
        assertThat(dimensionsList).hasSize(databaseSizeBeforeUpdate);
        Dimensions testDimensions = dimensionsList.get(dimensionsList.size() - 1);
        assertThat(testDimensions.getHeight()).isEqualTo(UPDATED_HEIGHT);
        assertThat(testDimensions.getWidth()).isEqualTo(DEFAULT_WIDTH);
        assertThat(testDimensions.getDepth()).isEqualTo(UPDATED_DEPTH);
    }

    @Test
    @Transactional
    void fullUpdateDimensionsWithPatch() throws Exception {
        // Initialize the database
        dimensionsRepository.saveAndFlush(dimensions);

        int databaseSizeBeforeUpdate = dimensionsRepository.findAll().size();

        // Update the dimensions using partial update
        Dimensions partialUpdatedDimensions = new Dimensions();
        partialUpdatedDimensions.setId(dimensions.getId());

        partialUpdatedDimensions.height(UPDATED_HEIGHT).width(UPDATED_WIDTH).depth(UPDATED_DEPTH);

        restDimensionsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDimensions.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDimensions))
            )
            .andExpect(status().isOk());

        // Validate the Dimensions in the database
        List<Dimensions> dimensionsList = dimensionsRepository.findAll();
        assertThat(dimensionsList).hasSize(databaseSizeBeforeUpdate);
        Dimensions testDimensions = dimensionsList.get(dimensionsList.size() - 1);
        assertThat(testDimensions.getHeight()).isEqualTo(UPDATED_HEIGHT);
        assertThat(testDimensions.getWidth()).isEqualTo(UPDATED_WIDTH);
        assertThat(testDimensions.getDepth()).isEqualTo(UPDATED_DEPTH);
    }

    @Test
    @Transactional
    void patchNonExistingDimensions() throws Exception {
        int databaseSizeBeforeUpdate = dimensionsRepository.findAll().size();
        dimensions.setId(count.incrementAndGet());

        // Create the Dimensions
        DimensionsDTO dimensionsDTO = dimensionsMapper.toDto(dimensions);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDimensionsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, dimensionsDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(dimensionsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Dimensions in the database
        List<Dimensions> dimensionsList = dimensionsRepository.findAll();
        assertThat(dimensionsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchDimensions() throws Exception {
        int databaseSizeBeforeUpdate = dimensionsRepository.findAll().size();
        dimensions.setId(count.incrementAndGet());

        // Create the Dimensions
        DimensionsDTO dimensionsDTO = dimensionsMapper.toDto(dimensions);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDimensionsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(dimensionsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Dimensions in the database
        List<Dimensions> dimensionsList = dimensionsRepository.findAll();
        assertThat(dimensionsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamDimensions() throws Exception {
        int databaseSizeBeforeUpdate = dimensionsRepository.findAll().size();
        dimensions.setId(count.incrementAndGet());

        // Create the Dimensions
        DimensionsDTO dimensionsDTO = dimensionsMapper.toDto(dimensions);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDimensionsMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(dimensionsDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Dimensions in the database
        List<Dimensions> dimensionsList = dimensionsRepository.findAll();
        assertThat(dimensionsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteDimensions() throws Exception {
        // Initialize the database
        dimensionsRepository.saveAndFlush(dimensions);

        int databaseSizeBeforeDelete = dimensionsRepository.findAll().size();

        // Delete the dimensions
        restDimensionsMockMvc
            .perform(delete(ENTITY_API_URL_ID, dimensions.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Dimensions> dimensionsList = dimensionsRepository.findAll();
        assertThat(dimensionsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
