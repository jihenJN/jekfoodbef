package com.jekfood.delivery.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.jekfood.delivery.IntegrationTest;
import com.jekfood.delivery.domain.Plates;
import com.jekfood.delivery.domain.Restaurant;
import com.jekfood.delivery.repository.PlatesRepository;
import com.jekfood.delivery.service.PlatesService;
import com.jekfood.delivery.service.dto.PlatesDTO;
import com.jekfood.delivery.service.mapper.PlatesMapper;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.Base64Utils;

/**
 * Integration tests for the {@link PlatesResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class PlatesResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Double DEFAULT_PRICE = 1D;
    private static final Double UPDATED_PRICE = 2D;

    private static final byte[] DEFAULT_PHOTO = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_PHOTO = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_PHOTO_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_PHOTO_CONTENT_TYPE = "image/png";

    private static final String DEFAULT_ORIGIN = "AAAAAAAAAA";
    private static final String UPDATED_ORIGIN = "BBBBBBBBBB";

    private static final Integer DEFAULT_STARS = 5;
    private static final Integer UPDATED_STARS = 4;

    private static final Boolean DEFAULT_FAVORITE = false;
    private static final Boolean UPDATED_FAVORITE = true;

    private static final Integer DEFAULT_COOK_TIME = 1;
    private static final Integer UPDATED_COOK_TIME = 2;

    private static final String DEFAULT_PHOTOS = "AAAAAAAAAA";
    private static final String UPDATED_PHOTOS = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/plates";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private PlatesRepository platesRepository;

    @Mock
    private PlatesRepository platesRepositoryMock;

    @Autowired
    private PlatesMapper platesMapper;

    @Mock
    private PlatesService platesServiceMock;

    @Autowired
    private MockMvc restPlatesMockMvc;

    private Plates plates;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Plates createEntity() {
        Plates plates = new Plates()
            .name(DEFAULT_NAME)
            .price(DEFAULT_PRICE)
            .photo(DEFAULT_PHOTO)
            .photoContentType(DEFAULT_PHOTO_CONTENT_TYPE)
            .origin(DEFAULT_ORIGIN)
            .stars(DEFAULT_STARS)
            .favorite(DEFAULT_FAVORITE)
            .cookTime(DEFAULT_COOK_TIME)
            .photos(DEFAULT_PHOTOS);
        // Add required entity
        Restaurant restaurant;
        restaurant = RestaurantResourceIT.createEntity();
        restaurant.setId("fixed-id-for-tests");
        plates.setRestaurant(restaurant);
        return plates;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Plates createUpdatedEntity() {
        Plates plates = new Plates()
            .name(UPDATED_NAME)
            .price(UPDATED_PRICE)
            .photo(UPDATED_PHOTO)
            .photoContentType(UPDATED_PHOTO_CONTENT_TYPE)
            .origin(UPDATED_ORIGIN)
            .stars(UPDATED_STARS)
            .favorite(UPDATED_FAVORITE)
            .cookTime(UPDATED_COOK_TIME)
            .photos(UPDATED_PHOTOS);
        // Add required entity
        Restaurant restaurant;
        restaurant = RestaurantResourceIT.createUpdatedEntity();
        restaurant.setId("fixed-id-for-tests");
        plates.setRestaurant(restaurant);
        return plates;
    }

    @BeforeEach
    public void initTest() {
        platesRepository.deleteAll();
        plates = createEntity();
    }

    @Test
    void createPlates() throws Exception {
        int databaseSizeBeforeCreate = platesRepository.findAll().size();
        // Create the Plates
        PlatesDTO platesDTO = platesMapper.toDto(plates);
        restPlatesMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(platesDTO)))
            .andExpect(status().isCreated());

        // Validate the Plates in the database
        List<Plates> platesList = platesRepository.findAll();
        assertThat(platesList).hasSize(databaseSizeBeforeCreate + 1);
        Plates testPlates = platesList.get(platesList.size() - 1);
        assertThat(testPlates.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testPlates.getPrice()).isEqualTo(DEFAULT_PRICE);
        assertThat(testPlates.getPhoto()).isEqualTo(DEFAULT_PHOTO);
        assertThat(testPlates.getPhotoContentType()).isEqualTo(DEFAULT_PHOTO_CONTENT_TYPE);
        assertThat(testPlates.getOrigin()).isEqualTo(DEFAULT_ORIGIN);
        assertThat(testPlates.getStars()).isEqualTo(DEFAULT_STARS);
        assertThat(testPlates.getFavorite()).isEqualTo(DEFAULT_FAVORITE);
        assertThat(testPlates.getCookTime()).isEqualTo(DEFAULT_COOK_TIME);
        assertThat(testPlates.getPhotos()).isEqualTo(DEFAULT_PHOTOS);
    }

    @Test
    void createPlatesWithExistingId() throws Exception {
        // Create the Plates with an existing ID
        plates.setId("existing_id");
        PlatesDTO platesDTO = platesMapper.toDto(plates);

        int databaseSizeBeforeCreate = platesRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPlatesMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(platesDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Plates in the database
        List<Plates> platesList = platesRepository.findAll();
        assertThat(platesList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = platesRepository.findAll().size();
        // set the field null
        plates.setName(null);

        // Create the Plates, which fails.
        PlatesDTO platesDTO = platesMapper.toDto(plates);

        restPlatesMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(platesDTO)))
            .andExpect(status().isBadRequest());

        List<Plates> platesList = platesRepository.findAll();
        assertThat(platesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void checkPriceIsRequired() throws Exception {
        int databaseSizeBeforeTest = platesRepository.findAll().size();
        // set the field null
        plates.setPrice(null);

        // Create the Plates, which fails.
        PlatesDTO platesDTO = platesMapper.toDto(plates);

        restPlatesMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(platesDTO)))
            .andExpect(status().isBadRequest());

        List<Plates> platesList = platesRepository.findAll();
        assertThat(platesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void getAllPlates() throws Exception {
        // Initialize the database
        platesRepository.save(plates);

        // Get all the platesList
        restPlatesMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(plates.getId())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].price").value(hasItem(DEFAULT_PRICE.doubleValue())))
            .andExpect(jsonPath("$.[*].photoContentType").value(hasItem(DEFAULT_PHOTO_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].photo").value(hasItem(Base64Utils.encodeToString(DEFAULT_PHOTO))))
            .andExpect(jsonPath("$.[*].origin").value(hasItem(DEFAULT_ORIGIN)))
            .andExpect(jsonPath("$.[*].stars").value(hasItem(DEFAULT_STARS)))
            .andExpect(jsonPath("$.[*].favorite").value(hasItem(DEFAULT_FAVORITE.booleanValue())))
            .andExpect(jsonPath("$.[*].cookTime").value(hasItem(DEFAULT_COOK_TIME)))
            .andExpect(jsonPath("$.[*].photos").value(hasItem(DEFAULT_PHOTOS)));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllPlatesWithEagerRelationshipsIsEnabled() throws Exception {
        when(platesServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restPlatesMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(platesServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllPlatesWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(platesServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restPlatesMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(platesRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    void getPlates() throws Exception {
        // Initialize the database
        platesRepository.save(plates);

        // Get the plates
        restPlatesMockMvc
            .perform(get(ENTITY_API_URL_ID, plates.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(plates.getId()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.price").value(DEFAULT_PRICE.doubleValue()))
            .andExpect(jsonPath("$.photoContentType").value(DEFAULT_PHOTO_CONTENT_TYPE))
            .andExpect(jsonPath("$.photo").value(Base64Utils.encodeToString(DEFAULT_PHOTO)))
            .andExpect(jsonPath("$.origin").value(DEFAULT_ORIGIN))
            .andExpect(jsonPath("$.stars").value(DEFAULT_STARS))
            .andExpect(jsonPath("$.favorite").value(DEFAULT_FAVORITE.booleanValue()))
            .andExpect(jsonPath("$.cookTime").value(DEFAULT_COOK_TIME))
            .andExpect(jsonPath("$.photos").value(DEFAULT_PHOTOS));
    }

    @Test
    void getNonExistingPlates() throws Exception {
        // Get the plates
        restPlatesMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    void putExistingPlates() throws Exception {
        // Initialize the database
        platesRepository.save(plates);

        int databaseSizeBeforeUpdate = platesRepository.findAll().size();

        // Update the plates
        Plates updatedPlates = platesRepository.findById(plates.getId()).get();
        updatedPlates
            .name(UPDATED_NAME)
            .price(UPDATED_PRICE)
            .photo(UPDATED_PHOTO)
            .photoContentType(UPDATED_PHOTO_CONTENT_TYPE)
            .origin(UPDATED_ORIGIN)
            .stars(UPDATED_STARS)
            .favorite(UPDATED_FAVORITE)
            .cookTime(UPDATED_COOK_TIME)
            .photos(UPDATED_PHOTOS);
        PlatesDTO platesDTO = platesMapper.toDto(updatedPlates);

        restPlatesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, platesDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(platesDTO))
            )
            .andExpect(status().isOk());

        // Validate the Plates in the database
        List<Plates> platesList = platesRepository.findAll();
        assertThat(platesList).hasSize(databaseSizeBeforeUpdate);
        Plates testPlates = platesList.get(platesList.size() - 1);
        assertThat(testPlates.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testPlates.getPrice()).isEqualTo(UPDATED_PRICE);
        assertThat(testPlates.getPhoto()).isEqualTo(UPDATED_PHOTO);
        assertThat(testPlates.getPhotoContentType()).isEqualTo(UPDATED_PHOTO_CONTENT_TYPE);
        assertThat(testPlates.getOrigin()).isEqualTo(UPDATED_ORIGIN);
        assertThat(testPlates.getStars()).isEqualTo(UPDATED_STARS);
        assertThat(testPlates.getFavorite()).isEqualTo(UPDATED_FAVORITE);
        assertThat(testPlates.getCookTime()).isEqualTo(UPDATED_COOK_TIME);
        assertThat(testPlates.getPhotos()).isEqualTo(UPDATED_PHOTOS);
    }

    @Test
    void putNonExistingPlates() throws Exception {
        int databaseSizeBeforeUpdate = platesRepository.findAll().size();
        plates.setId(UUID.randomUUID().toString());

        // Create the Plates
        PlatesDTO platesDTO = platesMapper.toDto(plates);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPlatesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, platesDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(platesDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Plates in the database
        List<Plates> platesList = platesRepository.findAll();
        assertThat(platesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchPlates() throws Exception {
        int databaseSizeBeforeUpdate = platesRepository.findAll().size();
        plates.setId(UUID.randomUUID().toString());

        // Create the Plates
        PlatesDTO platesDTO = platesMapper.toDto(plates);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPlatesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(platesDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Plates in the database
        List<Plates> platesList = platesRepository.findAll();
        assertThat(platesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamPlates() throws Exception {
        int databaseSizeBeforeUpdate = platesRepository.findAll().size();
        plates.setId(UUID.randomUUID().toString());

        // Create the Plates
        PlatesDTO platesDTO = platesMapper.toDto(plates);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPlatesMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(platesDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Plates in the database
        List<Plates> platesList = platesRepository.findAll();
        assertThat(platesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdatePlatesWithPatch() throws Exception {
        // Initialize the database
        platesRepository.save(plates);

        int databaseSizeBeforeUpdate = platesRepository.findAll().size();

        // Update the plates using partial update
        Plates partialUpdatedPlates = new Plates();
        partialUpdatedPlates.setId(plates.getId());

        partialUpdatedPlates
            .name(UPDATED_NAME)
            .photo(UPDATED_PHOTO)
            .photoContentType(UPDATED_PHOTO_CONTENT_TYPE)
            .favorite(UPDATED_FAVORITE);

        restPlatesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPlates.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPlates))
            )
            .andExpect(status().isOk());

        // Validate the Plates in the database
        List<Plates> platesList = platesRepository.findAll();
        assertThat(platesList).hasSize(databaseSizeBeforeUpdate);
        Plates testPlates = platesList.get(platesList.size() - 1);
        assertThat(testPlates.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testPlates.getPrice()).isEqualTo(DEFAULT_PRICE);
        assertThat(testPlates.getPhoto()).isEqualTo(UPDATED_PHOTO);
        assertThat(testPlates.getPhotoContentType()).isEqualTo(UPDATED_PHOTO_CONTENT_TYPE);
        assertThat(testPlates.getOrigin()).isEqualTo(DEFAULT_ORIGIN);
        assertThat(testPlates.getStars()).isEqualTo(DEFAULT_STARS);
        assertThat(testPlates.getFavorite()).isEqualTo(UPDATED_FAVORITE);
        assertThat(testPlates.getCookTime()).isEqualTo(DEFAULT_COOK_TIME);
        assertThat(testPlates.getPhotos()).isEqualTo(DEFAULT_PHOTOS);
    }

    @Test
    void fullUpdatePlatesWithPatch() throws Exception {
        // Initialize the database
        platesRepository.save(plates);

        int databaseSizeBeforeUpdate = platesRepository.findAll().size();

        // Update the plates using partial update
        Plates partialUpdatedPlates = new Plates();
        partialUpdatedPlates.setId(plates.getId());

        partialUpdatedPlates
            .name(UPDATED_NAME)
            .price(UPDATED_PRICE)
            .photo(UPDATED_PHOTO)
            .photoContentType(UPDATED_PHOTO_CONTENT_TYPE)
            .origin(UPDATED_ORIGIN)
            .stars(UPDATED_STARS)
            .favorite(UPDATED_FAVORITE)
            .cookTime(UPDATED_COOK_TIME)
            .photos(UPDATED_PHOTOS);

        restPlatesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPlates.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPlates))
            )
            .andExpect(status().isOk());

        // Validate the Plates in the database
        List<Plates> platesList = platesRepository.findAll();
        assertThat(platesList).hasSize(databaseSizeBeforeUpdate);
        Plates testPlates = platesList.get(platesList.size() - 1);
        assertThat(testPlates.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testPlates.getPrice()).isEqualTo(UPDATED_PRICE);
        assertThat(testPlates.getPhoto()).isEqualTo(UPDATED_PHOTO);
        assertThat(testPlates.getPhotoContentType()).isEqualTo(UPDATED_PHOTO_CONTENT_TYPE);
        assertThat(testPlates.getOrigin()).isEqualTo(UPDATED_ORIGIN);
        assertThat(testPlates.getStars()).isEqualTo(UPDATED_STARS);
        assertThat(testPlates.getFavorite()).isEqualTo(UPDATED_FAVORITE);
        assertThat(testPlates.getCookTime()).isEqualTo(UPDATED_COOK_TIME);
        assertThat(testPlates.getPhotos()).isEqualTo(UPDATED_PHOTOS);
    }

    @Test
    void patchNonExistingPlates() throws Exception {
        int databaseSizeBeforeUpdate = platesRepository.findAll().size();
        plates.setId(UUID.randomUUID().toString());

        // Create the Plates
        PlatesDTO platesDTO = platesMapper.toDto(plates);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPlatesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, platesDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(platesDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Plates in the database
        List<Plates> platesList = platesRepository.findAll();
        assertThat(platesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchPlates() throws Exception {
        int databaseSizeBeforeUpdate = platesRepository.findAll().size();
        plates.setId(UUID.randomUUID().toString());

        // Create the Plates
        PlatesDTO platesDTO = platesMapper.toDto(plates);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPlatesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(platesDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Plates in the database
        List<Plates> platesList = platesRepository.findAll();
        assertThat(platesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamPlates() throws Exception {
        int databaseSizeBeforeUpdate = platesRepository.findAll().size();
        plates.setId(UUID.randomUUID().toString());

        // Create the Plates
        PlatesDTO platesDTO = platesMapper.toDto(plates);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPlatesMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(platesDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Plates in the database
        List<Plates> platesList = platesRepository.findAll();
        assertThat(platesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void deletePlates() throws Exception {
        // Initialize the database
        platesRepository.save(plates);

        int databaseSizeBeforeDelete = platesRepository.findAll().size();

        // Delete the plates
        restPlatesMockMvc
            .perform(delete(ENTITY_API_URL_ID, plates.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Plates> platesList = platesRepository.findAll();
        assertThat(platesList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
