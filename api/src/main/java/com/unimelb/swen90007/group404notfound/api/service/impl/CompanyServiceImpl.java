package com.unimelb.swen90007.group404notfound.api.service.impl;

import com.unimelb.swen90007.group404notfound.api.domain.Company;
import com.unimelb.swen90007.group404notfound.api.domain.Share;
import com.unimelb.swen90007.group404notfound.api.mapper.CompanyMapper;
import com.unimelb.swen90007.group404notfound.api.mapper.ShareMapper;
import com.unimelb.swen90007.group404notfound.api.service.ICompanyService;
import com.unimelb.swen90007.group404notfound.api.util.UnitOfWork;

import java.math.BigDecimal;
import java.util.List;

public class CompanyServiceImpl implements ICompanyService {
    private CompanyMapper companyMapper = new CompanyMapper();
    private ShareMapper shareMapper = new ShareMapper();

    @Override
    public void addCompany(String companyName, String category) {
        Company company = new Company(null, companyName, category, null, null);
        UnitOfWork.newCurrent();
        UnitOfWork.getCurrent().registerNew(company);
    }

    @Override
    public boolean checkCompanyExist(String username) {
        if (companyMapper.findCompanyByName(username)!=null){
            return true;
        }
        return false;
    }

    @Override
    public boolean earn(Long cid, Long sid, Integer amount) {
        Company company = companyMapper.findCompanyById(cid);
        Share share = shareMapper.findShareById(sid);
        BigDecimal totalEarn = share.getPrice().multiply(new BigDecimal(amount));
        company.setBalance(company.getBalance().add(totalEarn));
        UnitOfWork.getCurrent().registerDirty(company);
        return true;
    }

    @Override
    public List<Company> findAllCompany() {
        return companyMapper.findAllCompany();
    }

    @Override
    public boolean addBalance(Long cid, BigDecimal totalPrice) {
        Company company = companyMapper.findCompanyById(cid);
        company.setBalance(company.getBalance().add(totalPrice));
        UnitOfWork.getCurrent().registerDirty(company);
        return true;
    }

    @Override
    public boolean decreaseBalance(Long cid, BigDecimal totalPrice) {
        Company company = companyMapper.findCompanyById(cid);
        company.setBalance(company.getBalance().subtract(totalPrice));
        UnitOfWork.getCurrent().registerDirty(company);
        return true;
    }

    public Company getCompanyById(Long companyId) {
        return companyMapper.findCompanyById(companyId);
    }
}
