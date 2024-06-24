package com.unimelb.swen90007.group404notfound.api.service.impl;

import com.unimelb.swen90007.group404notfound.api.domain.CompanyListing;
import com.unimelb.swen90007.group404notfound.api.domain.InvestListing;
import com.unimelb.swen90007.group404notfound.api.domain.Listing;
import com.unimelb.swen90007.group404notfound.api.domain.Share;
import com.unimelb.swen90007.group404notfound.api.dto.ListingDetailDTO;
import com.unimelb.swen90007.group404notfound.api.dto.PortfolioDetailDTO;
import com.unimelb.swen90007.group404notfound.api.mapper.CompanyListingMapper;
import com.unimelb.swen90007.group404notfound.api.mapper.ShareMapper;
import com.unimelb.swen90007.group404notfound.api.service.ICompanyListingService;
import com.unimelb.swen90007.group404notfound.api.util.UnitOfWork;

import java.math.BigDecimal;
import java.util.List;

public class CompanyListingServiceImpl implements ICompanyListingService {
    private CompanyListingMapper companyListingMapper = new CompanyListingMapper();
    private ShareMapper shareMapper = new ShareMapper();

    @Override
    public void addCompanyListing(Long shareId, Integer numShare) {

        CompanyListing companyListing = new CompanyListing(null, shareId, numShare, null);
        UnitOfWork.getCurrent().registerNew(companyListing);
    }

    @Override
    public boolean deleteCompanyListing(Long listingId) {
        UnitOfWork.newCurrent();
        CompanyListing companyListing = new CompanyListing(listingId, null, null, null);
        UnitOfWork.getCurrent().registerDeleted(companyListing);
        boolean result = UnitOfWork.getCurrent().commit();
        return result;
    }

    @Override
    public Long getShareIdByListingId(Long listingId) {
        return companyListingMapper.getShareIdByListingId(listingId);
    }

    @Override
    public Long sell(Long listingid, Integer amount) {
        Listing listing = companyListingMapper.findListingById(listingid);
        Long sid = listing.getShareId();
        if(listing.getNumShare()!=null && amount!= null &&  listing.getNumShare()>=amount ){
            listing.setNumShare(listing.getNumShare() - amount);
            UnitOfWork.getCurrent().registerDirty(listing);
            return sid;
        }else{
            return  -1L;
        }
    }

    @Override
    public List<ListingDetailDTO> findAllCompanyListing() {
        return companyListingMapper.findAllCompanyListingDetail();
    }



    @Override
    public BigDecimal totalPrice(Long lid, int amount) {
        Listing listing = companyListingMapper.findListingById(lid);
        if(listing==null){
            return null;
        }
        Long sid = listing.getShareId();


        Share share = shareMapper.findShareById(sid);
        BigDecimal price = share.getPrice();
        return  price.multiply(new BigDecimal(amount));
    }

    @Override
    public boolean calculateProfitForListing(PortfolioDetailDTO detailDTO) {
        BigDecimal profit = companyListingMapper.getProfit(detailDTO);
        detailDTO.setProfit(profit);
        return true;
    }

    @Override
    public BigDecimal getTotalPrice(Long sid, Long cid, Integer amount) {
        Share Customershare = shareMapper.findShareById(sid);
        Character type = Customershare.getShareType();
        Share companyShare = null;
        List<CompanyListing> companyListings = companyListingMapper.findAllCompanyListingByCompanyId(cid);
        boolean findShare = false;
        for(CompanyListing companyListing: companyListings){
            Share currentShare = shareMapper.findShareById(companyListing.getShareId());
            if(currentShare.getShareType() == type){
                findShare = true;
                companyShare = currentShare;
                break;
            }
        }
        if (!findShare){
            return BigDecimal.valueOf(-1);
        }
        return companyShare.getPrice().multiply(BigDecimal.valueOf(amount));


    }

    @Override
    public boolean addNumShareToCompanyListing(Long sid, Long cid, Integer amount) {
        Share Customershare = shareMapper.findShareById(sid);
        Character type = Customershare.getShareType();
        CompanyListing companyListing = null;
        boolean findShare = false;
        List<CompanyListing> companyListings = companyListingMapper.findAllCompanyListingByCompanyId(cid);
        for (CompanyListing currentListing : companyListings) {
            Share currentShare = shareMapper.findShareById(currentListing.getShareId());
            if (currentShare.getShareType() == type) {
                findShare = true;
                companyListing = currentListing;
                break;
            }
        }
        if (!findShare) {
            return false;
        }
        companyListing.setNumShare(companyListing.getNumShare() + amount);
        UnitOfWork.getCurrent().registerDirty(companyListing);
        return true;
    }

    @Override
    public boolean UpdateAmount(Long lid, Integer amount) {
        CompanyListing companyListing = companyListingMapper.findListingById(lid);
        companyListing.setNumShare(amount);
        UnitOfWork.getCurrent().registerDirty(companyListing);
        return true;
    }

    @Override
    public boolean updateSid(Long lid, Long sid) {
        CompanyListing companyListing = companyListingMapper.findListingById(lid);
        companyListing.setShareId(sid);
        UnitOfWork.getCurrent().registerDirty(companyListing);
        return true;
    }

    @Override
    public boolean updateListing(Long listingid, Long sid, Integer amount) {
        CompanyListing companyListing = companyListingMapper.findListingById(listingid);
        companyListing.setShareId(sid);
        companyListing.setNumShare(amount);
        UnitOfWork.getCurrent().registerDirty(companyListing);
        return true;
    }
    @Override
    public CompanyListing checkExistListing(Long shareId) {
        return companyListingMapper.findListingByShareId(shareId);
    }

    public List<ListingDetailDTO> findCompanyListingByCompanyId(Long companyId) {
        return companyListingMapper.findCompanyListingDetailById(companyId);

    }


}
