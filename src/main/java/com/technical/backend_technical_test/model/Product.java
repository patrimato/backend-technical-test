package com.technical.backendtechnicaltest.model;

public record Product(
    String id,
    String name,
    Double price,
    Boolean availability
) {}