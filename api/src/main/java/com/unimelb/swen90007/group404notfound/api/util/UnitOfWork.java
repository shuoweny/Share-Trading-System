package com.unimelb.swen90007.group404notfound.api.util;

import com.unimelb.swen90007.group404notfound.api.domain.*;
import com.unimelb.swen90007.group404notfound.api.mapper.*;
import com.unimelb.swen90007.group404notfound.api.mapper.dbconnection.PostgresConnection;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class UnitOfWork {
    private static UnitOfWork current;
    private List<Object> newObjects = new ArrayList<Object>();
    private List<Object> dirtyObjects = new ArrayList<Object>();
    private List<Object> deletedObjects = new ArrayList<Object>();
    private static CustomerMapper customerMapper = new CustomerMapper();
    private static CompanyUserMapper companyUserMapper = new CompanyUserMapper();
    private static InvestListingMapper listingMapper = new InvestListingMapper();
    private static CompanyListingMapper companyListingMapper = new CompanyListingMapper();
    private static PortfolioMapper portfolioMapper = new PortfolioMapper();
    private static InvestListingMapper investListingMapper = new InvestListingMapper();
    public static HashMap<String, Long> storeResult;

    private static CompanyMapper companyMapper = new CompanyMapper();
    private static ShareMapper shareMapper = new ShareMapper();
    private Connection connection;

    public static void newCurrent(){
        current = new UnitOfWork();
        storeResult = new HashMap<>();
    }

    public static HashMap<String, Long> getStoreResult() {
        return storeResult;
    }

    public static void setStoreResult(HashMap<String, Long> storeResult) {
        UnitOfWork.storeResult = storeResult;
    }

    public static UnitOfWork getCurrent() {
        return current;
    }

    public static void setCurrent(UnitOfWork current) {
        UnitOfWork.current = current;
    }

    public void registerNew(Object obj){
        newObjects.add(obj);
    }

    public void registerDirty(Object obj){
        dirtyObjects.add(obj);
    }

    public void registerDeleted(Object obj){
        if (newObjects.contains(obj)){
            newObjects.remove(obj);
            return;
        }
        dirtyObjects.remove(obj);
        if (!deletedObjects.contains(obj)){
            deletedObjects.add(obj);
        }
    }

    public boolean commit(){
        boolean result = false;
        connection = PostgresConnection.getConnection();
        try {
            connection.setAutoCommit(false);
            for (Object obj : newObjects){
                if (obj instanceof Customer){
                    result = customerMapper.addCustomer((Customer) obj, connection);
                    if(!result){ throw new Exception("add customerMapper fail");}
                } else if (obj instanceof CompanyUser) {
                    result = companyUserMapper.addCompanyUser((CompanyUser) obj, connection);
                    if(!result){ throw new Exception("add companyUserMapper fail");}
                } else if(obj instanceof InvestListing){
                    result = listingMapper.addInvestListing((InvestListing) obj, connection);
                    if(!result){ throw new Exception("add listingMapper fail");}
                } else if (obj instanceof CompanyListing) {
                    result = companyListingMapper.addCompanyListing((CompanyListing) obj,connection);
                    if(!result){ throw new Exception("add companyListingMapper fail");}
                } else if(obj instanceof Portfolio){
                    result = portfolioMapper.addPortfolio((Portfolio) obj, connection);
                    if(!result){ throw new Exception("add portfolioMapper fail");}
                }else if(obj instanceof Company){
                    result = companyMapper.addCompany((Company) obj, connection);
                    if(!result){ throw new Exception("add companyMapper fail");}
                }else if(obj instanceof Share){
                    result = shareMapper.addShare((Share) obj, connection);
                    if(!result){ throw new Exception("add shareMapper fail");}
                }
            }
            newObjects.clear();
            try {
                for (Object obj : dirtyObjects) {
                    if (obj instanceof Customer) {
                        result = customerMapper.setCustomer((Customer) obj, connection);
                        if(!result){ throw new Exception("update customerMapper fail");}
                    } else if (obj instanceof CompanyUser) {
                        result = companyUserMapper.setCompanyUser((CompanyUser) obj, connection);
                        if(!result){ throw new Exception("update companyUserMapper fail");}
                    } else if (obj instanceof InvestListing) {
                        result = listingMapper.setInvestListing((InvestListing) obj, connection);
                        if(!result){ throw new Exception("update investlistingMapper fail");}
                    } else if (obj instanceof CompanyListing) {
                        result = companyListingMapper.setCompanyListing((CompanyListing) obj, connection);
                        if(!result){ throw new Exception("update companyListingMapper fail");}
                    } else if (obj instanceof Company) {
                        result = companyMapper.setCompany((Company) obj, connection);
                        if(!result){ throw new Exception("update companyMapper fail");}
                    } else if (obj instanceof Share) {
                        result = shareMapper.setShare((Share) obj, connection);
                        if(!result){ throw new Exception("update shareMapper fail");}
                    }
                }
            }catch (Exception e){
                try{
                    System.out.println("Rolling back transaction: " + e.getMessage());
                    connection.rollback();
                    connection.close();
                    return false;
                }catch (SQLException e1){
                    e1.printStackTrace();
                }
            }
            dirtyObjects.clear();
            try {
                for (Object obj : deletedObjects) {
                    if (obj instanceof InvestListing) {
                        result = investListingMapper.deleteInvestListing((InvestListing) obj, connection);
                        if(!result){ throw new Exception("delete investListingMapper fail");}
                    } else if (obj instanceof Portfolio) {
                        result = portfolioMapper.deletePortfolio((Portfolio) obj, connection);
                        if(!result){ throw new Exception("delete portfolioMapper fail");}
                    } else if (obj instanceof CompanyListing) {
                        result = companyListingMapper.deleteCompanyListingById((CompanyListing) obj, connection);
                        if(!result){ throw new Exception("delete companyListingMapper fail");}
                    }
                }
            }catch (Exception e){
                try{
                    System.out.println("Rolling back transaction: " + e.getMessage());
                    connection.rollback();
                    connection.close();
                    return false;
                }catch (SQLException e1){
                    e1.printStackTrace();
                }
            }



            deletedObjects.clear();
            connection.commit();
        } catch (SQLException e) {
            try {
                connection.rollback();
                connection.close();
                return false;
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        } catch (Exception e) {
            try {
                connection.rollback();
                connection.close();
                return false;
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }

        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return result;
    }
}
