package com.domgarr.concetto.services;

import com.domgarr.concetto.models.InterInterval;

public interface InterIntervalService {
    InterInterval save(InterInterval interInterval);
    InterInterval find(Long id);
}
