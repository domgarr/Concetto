package com.example.concetto.services;


import com.example.concetto.models.InterInterval;
import com.example.concetto.repositories.InterIntervalRepository;
import org.springframework.stereotype.Service;

@Service
public class InterIntervalServiceImpl implements InterIntervalService {
    private final InterIntervalRepository interIntervalRepository;

    public InterIntervalServiceImpl(InterIntervalRepository interIntervalRepository) {
        this.interIntervalRepository = interIntervalRepository;
    }

    @Override
    public InterInterval save(InterInterval interInterval) {
        return interIntervalRepository.save(interInterval);
    }
}
