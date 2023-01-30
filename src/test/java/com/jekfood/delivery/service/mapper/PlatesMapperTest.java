package com.jekfood.delivery.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PlatesMapperTest {

    private PlatesMapper platesMapper;

    @BeforeEach
    public void setUp() {
        platesMapper = new PlatesMapperImpl();
    }
}
