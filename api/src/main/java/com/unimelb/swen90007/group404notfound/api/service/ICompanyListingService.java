package com.unimelb.swen90007.group404notfound.api.service;

import com.unimelb.swen90007.group404notfound.api.domain.CompanyListing;
import com.unimelb.swen90007.group404notfound.api.domain.InvestListing;
import com.unimelb.swen90007.group404notfound.api.domain.Share;
import com.unimelb.swen90007.group404notfound.api.dto.ListingDetailDTO;
import com.unimelb.swen90007.group404notfound.api.dto.PortfolioDetailDTO;

import java.math.BigDecimal;
import java.util.List;

public interface ICompanyListingService {
    Long sell(Long listingid, Integer amount);
    List<ListingDetailDTO> findAllCompanyListing();
    BigDecimal totalPrice(Long lid, int amount);
    void addCompanyListing(Long shareId, Integer numShare);
    boolean deleteCompanyListing(Long listingId);
    Long getShareIdByListingId(Long listingId);
    boolean calculateProfitForListing(PortfolioDetailDTO detailDTO);
    List<ListingDetailDTO> findCompanyListingByCompanyId(Long companyId);

    BigDecimal getTotalPrice(Long sid, Long cid,Integer amount);
    boolean addNumShareToCompanyListing(Long sid, Long cid, Integer amount);

    boolean UpdateAmount(Long lid, Integer amount);

    boolean updateSid(Long lid, Long sid);

    boolean updateListing(Long listingid, Long sid, Integer amount);

    CompanyListing checkExistListing(Long shareId);
}
