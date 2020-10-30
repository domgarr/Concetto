package com.domgarr.concetto.services;


import com.domgarr.concetto.repositories.InterIntervalRepository;
import com.domgarr.concetto.models.InterInterval;
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

    @Override
    public InterInterval find(Long id) {
        return this.interIntervalRepository.findById(id).get();
    }
}
