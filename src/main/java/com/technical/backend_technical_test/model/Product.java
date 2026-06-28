package com.technical.backend_technical_test.model;

public record Product(
    String id,
    String name,
    Double price,
    Boolean availability
) {}