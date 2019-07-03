package com.bf.service;

import com.bf.dto.RequestBean;
import com.bf.dto.ServiceResponseBean;

/**
 * @author Sadashiv Kadam
 */
public interface FactoryService {
    ServiceResponseBean computePrice(RequestBean requestBean);
}
