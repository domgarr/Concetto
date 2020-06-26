package com.example.concetto.services;

import com.example.concetto.models.InterInterval;

public interface InterIntervalService {
    InterInterval save(InterInterval interInterval);
    InterInterval find(Long id);
}
