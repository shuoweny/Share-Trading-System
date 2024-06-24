package com.unimelb.swen90007.group404notfound.api.service.impl;

import com.unimelb.swen90007.group404notfound.api.domain.Share;
import com.unimelb.swen90007.group404notfound.api.mapper.ShareMapper;
import com.unimelb.swen90007.group404notfound.api.service.IShareService;
import com.unimelb.swen90007.group404notfound.api.util.UnitOfWork;

import java.math.BigDecimal;

public class ShareServiceImpl implements IShareService {
    private ShareMapper shareMapper = new ShareMapper();

    @Override
    public Long getCompanyIdbyId(Long sid) {

        return shareMapper.findShareById(sid).getCompanyId();
    }

    public Share getShareById(Long shareId) {
        return shareMapper.findShareById(shareId);
    }

    @Override
    public void addShare(Long companyId, Character shareType, BigDecimal price) {
        Share share = new Share(null, companyId, shareType, price, null);
        UnitOfWork.getCurrent().registerNew(share);
    }

    @Override
    public boolean updatePrice(Long sid, BigDecimal price) {
        Share share = shareMapper.findShareById(sid);
        share.setPrice(price);
        UnitOfWork.getCurrent().registerDirty(share);
        return true;
    }

    @Override
    public int addorDup(Long companyId, Character shareType) {
        return shareMapper.checkExist(companyId, shareType);
    }
}
