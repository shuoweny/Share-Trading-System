package com.unimelb.swen90007.group404notfound.api.service;

import com.unimelb.swen90007.group404notfound.api.domain.Share;

import java.math.BigDecimal;

public interface IShareService {
    public Long getCompanyIdbyId(Long sid);
    void addShare(Long companyid, Character shareType, BigDecimal price);
    boolean updatePrice(Long sid, BigDecimal price);
    int addorDup(Long companyId, Character shareType);

    Share getShareById(Long sid);
}
