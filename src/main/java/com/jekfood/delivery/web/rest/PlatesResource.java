package com.jekfood.delivery.web.rest;

import com.jekfood.delivery.repository.PlatesRepository;
import com.jekfood.delivery.service.PlatesService;
import com.jekfood.delivery.service.dto.PlatesDTO;
import com.jekfood.delivery.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.jekfood.delivery.domain.Plates}.
 */
@RestController
@RequestMapping("/api")
public class PlatesResource {

    private final Logger log = LoggerFactory.getLogger(PlatesResource.class);

    private static final String ENTITY_NAME = "plates";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PlatesService platesService;

    private final PlatesRepository platesRepository;

    public PlatesResource(PlatesService platesService, PlatesRepository platesRepository) {
        this.platesService = platesService;
        this.platesRepository = platesRepository;
    }

    /**
     * {@code POST  /plates} : Create a new plates.
     *
     * @param platesDTO the platesDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new platesDTO, or with status {@code 400 (Bad Request)} if the plates has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/plates")
    public ResponseEntity<PlatesDTO> createPlates(@Valid @RequestBody PlatesDTO platesDTO) throws URISyntaxException {
        log.debug("REST request to save Plates : {}", platesDTO);
        if (platesDTO.getId() != null) {
            throw new BadRequestAlertException("A new plates cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PlatesDTO result = platesService.save(platesDTO);
        return ResponseEntity
            .created(new URI("/api/plates/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId()))
            .body(result);
    }

    /**
     * {@code PUT  /plates/:id} : Updates an existing plates.
     *
     * @param id the id of the platesDTO to save.
     * @param platesDTO the platesDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated platesDTO,
     * or with status {@code 400 (Bad Request)} if the platesDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the platesDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/plates/{id}")
    public ResponseEntity<PlatesDTO> updatePlates(
        @PathVariable(value = "id", required = false) final String id,
        @Valid @RequestBody PlatesDTO platesDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Plates : {}, {}", id, platesDTO);
        if (platesDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, platesDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!platesRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        PlatesDTO result = platesService.update(platesDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, platesDTO.getId()))
            .body(result);
    }

    /**
     * {@code PATCH  /plates/:id} : Partial updates given fields of an existing plates, field will ignore if it is null
     *
     * @param id the id of the platesDTO to save.
     * @param platesDTO the platesDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated platesDTO,
     * or with status {@code 400 (Bad Request)} if the platesDTO is not valid,
     * or with status {@code 404 (Not Found)} if the platesDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the platesDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/plates/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<PlatesDTO> partialUpdatePlates(
        @PathVariable(value = "id", required = false) final String id,
        @NotNull @RequestBody PlatesDTO platesDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Plates partially : {}, {}", id, platesDTO);
        if (platesDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, platesDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!platesRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<PlatesDTO> result = platesService.partialUpdate(platesDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, platesDTO.getId())
        );
    }

    /**
     * {@code GET  /plates} : get all the plates.
     *
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of plates in body.
     */
    @GetMapping("/plates")
    public List<PlatesDTO> getAllPlates(@RequestParam(required = false, defaultValue = "false") boolean eagerload) {
        log.debug("REST request to get all Plates");
        return platesService.findAll();
    }

    /**
     * {@code GET  /plates/:id} : get the "id" plates.
     *
     * @param id the id of the platesDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the platesDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/plates/{id}")
    public ResponseEntity<PlatesDTO> getPlates(@PathVariable String id) {
        log.debug("REST request to get Plates : {}", id);
        Optional<PlatesDTO> platesDTO = platesService.findOne(id);
        return ResponseUtil.wrapOrNotFound(platesDTO);
    }

    /**
     * {@code DELETE  /plates/:id} : delete the "id" plates.
     *
     * @param id the id of the platesDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/plates/{id}")
    public ResponseEntity<Void> deletePlates(@PathVariable String id) {
        log.debug("REST request to delete Plates : {}", id);
        platesService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id)).build();
    }
}
