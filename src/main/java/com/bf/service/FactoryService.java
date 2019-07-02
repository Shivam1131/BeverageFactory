package com.bf.service;

import com.bf.dto.RequestBean;

import java.util.Map;

/**
 * @author Sadashiv Kadam
 */
public interface FactoryService {
    Map<String, Float> computePrice(RequestBean requestBean);
}
