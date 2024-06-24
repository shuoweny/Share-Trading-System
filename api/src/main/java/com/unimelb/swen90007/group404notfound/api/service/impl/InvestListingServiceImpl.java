package com.unimelb.swen90007.group404notfound.api.service.impl;

import com.unimelb.swen90007.group404notfound.api.domain.InvestListing;
import com.unimelb.swen90007.group404notfound.api.domain.Listing;
import com.unimelb.swen90007.group404notfound.api.dto.ListingDetailDTO;
import com.unimelb.swen90007.group404notfound.api.mapper.InvestListingMapper;
import com.unimelb.swen90007.group404notfound.api.service.IInvestListingService;
import com.unimelb.swen90007.group404notfound.api.util.UnitOfWork;


public class InvestListingServiceImpl implements IInvestListingService {
    InvestListingMapper investListingMapper = new InvestListingMapper();
    @Override
    public InvestListing addListing(Long sid, Integer amount) {
        InvestListing listing = new InvestListing(null,sid, amount, null);
        UnitOfWork.getCurrent().registerNew(listing);
        return listing;
    }

    @Override
    public int removeOrUpdate(Long lid, Integer amount) {
        InvestListing listing = (InvestListing) investListingMapper.findListingById(lid);
        Integer numShare = listing.getNumShare();
        if(numShare < amount){
            return -1;
        } else if (numShare > amount) {
            //Update numShare
            listing.setNumShare(numShare - amount);
            UnitOfWork.getCurrent().registerDirty(listing);
            return 1;
        }else{
            return 0;
        }
    }

    @Override
    public boolean updateNumShare(Long lid, Integer amount) {
        Listing curListing = investListingMapper.findListingById(lid);
        curListing.setNumShare(curListing.getNumShare()+amount);
        UnitOfWork.getCurrent().registerDirty(curListing);
        return true;

    }

    @Override
    public boolean removeInvestListing(Long lid) {
        InvestListing investListing = (InvestListing) investListingMapper.findListingById(lid);
        UnitOfWork.getCurrent().registerDeleted(investListing);
        return true;

    }

    @Override
    public InvestListing findInvestListingById(Long id) {
        return (InvestListing) investListingMapper.findListingById(id);
    }

//    @Override
//    public ListingDetailDTO findListingDTOById(Long id) {
//        return  investListingMapper.findListingDTOById(id);
//    }


}
