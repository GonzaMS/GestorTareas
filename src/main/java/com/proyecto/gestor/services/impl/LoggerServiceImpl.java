package com.proyecto.gestor.services.impl;

import com.proyecto.gestor.services.LoggerService;
import org.slf4j.LoggerFactory;

import org.slf4j.Logger;

public class LoggerServiceImpl implements LoggerService {
    private static final Logger logger = LoggerFactory.getLogger(LoggerServiceImpl.class);
    @Override
    public void showError() {
        logger.error("Error");

    }
}
