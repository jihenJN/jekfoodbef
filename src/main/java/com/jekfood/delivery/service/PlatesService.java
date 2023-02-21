package com.jekfood.delivery.service;

import com.jekfood.delivery.domain.Plates;
import com.jekfood.delivery.repository.PlatesRepository;
import com.jekfood.delivery.service.dto.PlatesDTO;
import com.jekfood.delivery.service.mapper.PlatesMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * Service Implementation for managing {@link Plates}.
 */
@Service
public class PlatesService {

    private final Logger log = LoggerFactory.getLogger(PlatesService.class);

    private final PlatesRepository platesRepository;

    private final PlatesMapper platesMapper;

    public PlatesService(PlatesRepository platesRepository, PlatesMapper platesMapper) {
        this.platesRepository = platesRepository;
        this.platesMapper = platesMapper;
    }

    /**
     * Save a plates.
     *
     * @param platesDTO the entity to save.
     * @return the persisted entity.
     */
    public PlatesDTO save(PlatesDTO platesDTO) {
        log.debug("Request to save Plates : {}", platesDTO);
        Plates plates = platesMapper.toEntity(platesDTO);
        plates = platesRepository.save(plates);
        return platesMapper.toDto(plates);
    }

    /**
     * Update a plates.
     *
     * @param platesDTO the entity to save.
     * @return the persisted entity.
     */
    public PlatesDTO update(PlatesDTO platesDTO) {
        log.debug("Request to update Plates : {}", platesDTO);
        Plates plates = platesMapper.toEntity(platesDTO);
        plates = platesRepository.save(plates);
        return platesMapper.toDto(plates);
    }

    /**
     * Partially update a plates.
     *
     * @param platesDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<PlatesDTO> partialUpdate(PlatesDTO platesDTO) {
        log.debug("Request to partially update Plates : {}", platesDTO);

        return platesRepository
            .findById(platesDTO.getId())
            .map(existingPlates -> {
                platesMapper.partialUpdate(existingPlates, platesDTO);

                return existingPlates;
            })
            .map(platesRepository::save)
            .map(platesMapper::toDto);
    }

    /**
     * Get all the plates.
     *
     * @return the list of entities.
     */
    public List<PlatesDTO> findAll() {
        log.debug("Request to get all Plates");
        return platesRepository.findAll().stream().map(platesMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get all the plates by id restaurant.
     *
     * @return the list of entities.
     */
    public List<PlatesDTO> findAllByIdrestaurant(String idrestaurant) {
        log.debug("Request to get all Plates by id restaurant");
        return platesRepository
            .findAllByIdrestaurant(idrestaurant)
            .stream()
            .map(platesMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get all the plates with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<PlatesDTO> findAllWithEagerRelationships(Pageable pageable) {
        return platesRepository.findAllWithEagerRelationships(pageable).map(platesMapper::toDto);
    }

    /**
     * Get one plates by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    public Optional<PlatesDTO> findOne(String id) {
        log.debug("Request to get Plates : {}", id);
        return platesRepository.findOneWithEagerRelationships(id).map(platesMapper::toDto);
    }

    /**
     * Delete the plates by id.
     *
     * @param id the id of the entity.
     */
    public void delete(String id) {
        log.debug("Request to delete Plates : {}", id);
        platesRepository.deleteById(id);
    }

    private Object toDto(PlatesDTO platesdto1, Object object1) {
        return null;
    }
}
