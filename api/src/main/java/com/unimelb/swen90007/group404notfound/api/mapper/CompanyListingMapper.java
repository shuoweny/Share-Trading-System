package com.unimelb.swen90007.group404notfound.api.mapper;

import com.unimelb.swen90007.group404notfound.api.domain.CompanyListing;
import com.unimelb.swen90007.group404notfound.api.dto.ListingDetailDTO;
import com.unimelb.swen90007.group404notfound.api.dto.PortfolioDetailDTO;
import com.unimelb.swen90007.group404notfound.api.mapper.dbconnection.PostgresConnection;
import com.unimelb.swen90007.group404notfound.api.util.UnitOfWork;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CompanyListingMapper {
    public CompanyListing findListingById(Long id) {
        String sql = "SELECT * FROM public.companylisting WHERE id = ?";
        CompanyListing listing = null;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        connection = PostgresConnection.getConnection();
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            listing = assignListingValue(resultSet);
            resultSet.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            PostgresConnection.closeConnection(connection, preparedStatement);
        }
        return listing;

    }
    public CompanyListing findListingByShareId(Long sid) {
        String sql = "SELECT * FROM public.companylisting WHERE shareid = ?";
        CompanyListing listing = null;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        connection = PostgresConnection.getConnection();
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, sid);
            ResultSet resultSet = preparedStatement.executeQuery();
            listing = assignListingValue(resultSet);
            resultSet.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            PostgresConnection.closeConnection(connection, preparedStatement);
        }
        return listing;

    }
    public boolean setCompanyListing(CompanyListing listing, Connection connection){
        String sql = "UPDATE public.companylisting SET shareid=?, numshare=?, version=version+1 WHERE id=? AND version=?";
        PreparedStatement preparedStatement = null;
        int result = 0;
        try {
            preparedStatement = connection.prepareStatement(sql);
            if(UnitOfWork.getStoreResult().get("shareid") == null){
                preparedStatement.setLong(1, listing.getShareId());
            }else{
                preparedStatement.setLong(1, UnitOfWork.getStoreResult().get("shareid"));
            }
            preparedStatement.setInt(2, listing.getNumShare());
            preparedStatement.setLong(3, listing.getListingId());
            preparedStatement.setInt(4, listing.getVersion());
            result = preparedStatement.executeUpdate();
            if(result==0){
                throw new RuntimeException("Can't update companylisting: "+ listing.getListingId());
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return result != 0;
    }

    public boolean addCompanyListing(CompanyListing listing, Connection connection){
        String sql = "INSERT INTO companyListing (shareid, numshare) VALUES (?,?)";
        PreparedStatement preparedStatement = null;
        boolean back;

        try {
            preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setLong(1, listing.getShareId());
            preparedStatement.setInt(2, listing.getNumShare());
            back = preparedStatement.executeUpdate()==1;
            ResultSet rs = preparedStatement.getGeneratedKeys();
            if(rs.next()){
                Long listingid = rs.getLong(1);
                UnitOfWork.storeResult.put("listingid", listingid);
                rs.close();

            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return true;
    }

    private CompanyListing assignListingValue(ResultSet resultSet) {
        CompanyListing listing = null;
        try {
            if (resultSet.next()) {
                listing = new CompanyListing(
                        resultSet.getLong("id"),
                        resultSet.getLong("shareid"),
                        resultSet.getInt("numshare"),
                        resultSet.getInt("version"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return listing;
    }

    public List<ListingDetailDTO> findAllCompanyListingDetail(){
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        List<ListingDetailDTO> listingDetails = new ArrayList<>();
        String sql =
                "SELECT l.*, s.price, c.companyname, s.sharetype, c.category FROM public.companylisting l" +
                        " JOIN public.share s ON l.shareid = s.id " +
                        "JOIN public.company c ON s.companyid = c.id";
        try {
            connection = PostgresConnection.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            ResultSet rs = preparedStatement.executeQuery();

            while(rs.next()) {
                ListingDetailDTO listingDetail = new ListingDetailDTO();
                listingDetail.setId(rs.getLong("id"));
                listingDetail.setShareId(rs.getLong("shareid"));
                listingDetail.setNumShare(rs.getInt("numshare"));
                listingDetail.setPrice(rs.getBigDecimal("price"));
                listingDetail.setCompanyName(rs.getString("companyname"));
                listingDetail.setShareType(rs.getString("sharetype").charAt(0));
                listingDetail.setCategory(rs.getString("category"));
                listingDetails.add(listingDetail);
            }
            rs.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {

            PostgresConnection.closeConnection(connection, preparedStatement);
        }
        return listingDetails;
    }

    public List<ListingDetailDTO> findCompanyListingDetailById(Long companyId){
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        List<ListingDetailDTO> listingDetails = new ArrayList<>();
        String sql =
                "SELECT l.*, s.price, c.companyname, s.sharetype, c.category FROM public.companylisting l" +
                        " JOIN public.share s ON l.shareid = s.id " +
                        "JOIN public.company c ON s.companyid = c.id WHERE c.id = ?";
        try {
            connection = PostgresConnection.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, companyId);
            ResultSet rs = preparedStatement.executeQuery();

            while(rs.next()) {
                ListingDetailDTO listingDetail = new ListingDetailDTO();
                listingDetail.setId(rs.getLong("id"));
                listingDetail.setShareId(rs.getLong("shareid"));
                listingDetail.setNumShare(rs.getInt("numshare"));
                listingDetail.setPrice(rs.getBigDecimal("price"));
                listingDetail.setCompanyName(rs.getString("companyname"));
                listingDetail.setShareType(rs.getString("sharetype").charAt(0));
                listingDetail.setCategory(rs.getString("category"));
                listingDetails.add(listingDetail);
            }
            rs.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {

            PostgresConnection.closeConnection(connection, preparedStatement);
        }
        return listingDetails;
    }

    public BigDecimal getProfit(PortfolioDetailDTO detail){
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        BigDecimal profit = null;
        String sql =
                "SELECT s.price FROM public.companylisting l" +
                        " JOIN public.share s ON l.shareid = s.id " +
                        "JOIN public.company c ON s.companyid = c.id WHERE c.companyname = ? " +
                        "AND s.sharetype = ?";
        try {
            connection = PostgresConnection.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, detail.getCompanyName());
            preparedStatement.setString(2, detail.getShareType().toString());
            ResultSet rs = preparedStatement.executeQuery();
            if(rs.next()){
                BigDecimal price = rs.getBigDecimal("price");
                profit = (price.subtract(detail.getPrice())).multiply(BigDecimal.valueOf(detail.getNumShare()));
            }
            rs.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {

            PostgresConnection.closeConnection(connection, preparedStatement);
        }
        return profit;
    }

    public List<CompanyListing> findAllCompanyListingByCompanyId(Long companyId){
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        List<CompanyListing> listingDetails = new ArrayList<>();
        String sql =
                "SELECT l.* FROM public.companylisting l" +
                        " JOIN public.share s ON l.shareid = s.id " +
                        "WHERE s.companyid = ?";
        try {
            connection = PostgresConnection.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, companyId);
            ResultSet rs = preparedStatement.executeQuery();

//            while(rs.next()) {
//                CompanyListing listingDetail = assignListingValue(rs);
//                listingDetails.add(listingDetail);
//            }
            while (rs.next()){
                CompanyListing companyListing = new CompanyListing(
                        rs.getLong("id"),
                        rs.getLong("shareid"),
                        rs.getInt("numshare"),
                        rs.getInt("version"));
                listingDetails.add(companyListing);

            }
            rs.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {

            PostgresConnection.closeConnection(connection, preparedStatement);
        }
        return listingDetails;
    }

    public List<CompanyListing> findAllCompanyListingDetailByCompanyName(String companyName){
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        List<CompanyListing> listingDetails = new ArrayList<>();
        String sql =
                "SELECT l.*, s.price, c.companyname, s.sharetype, c.category FROM public.companylisting l" +
                        " JOIN public.share s ON l.shareid = s.id " +
                        "JOIN public.company c ON s.companyid = c.id" +
                        "WHERE c.companyname = ?";
        try {
            connection = PostgresConnection.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, companyName);
            ResultSet rs = preparedStatement.executeQuery();

            while(rs.next()) {
                CompanyListing companyListing = new CompanyListing(
                        rs.getLong("id"),
                        rs.getLong("shareid"),
                        rs.getInt("numshare"),
                        rs.getInt("version"));
                listingDetails.add(companyListing);

            }
            rs.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {

            PostgresConnection.closeConnection(connection, preparedStatement);
        }
        return listingDetails;
    }

    public List<CompanyListing> findAllCompanyListingDetailByType(String shareType){
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        List<CompanyListing> listingDetails = new ArrayList<>();
        String sql =
                "SELECT l.*, s.price, c.companyname, s.sharetype, c.category FROM public.companylisting l" +
                        " JOIN public.share s ON l.shareid = s.id " +
                        "JOIN public.company c ON s.companyid = c.id" +
                        "WHERE s.sharetype = ?";
        try {
            connection = PostgresConnection.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, shareType);
            ResultSet rs = preparedStatement.executeQuery();

            while(rs.next()) {
                CompanyListing companyListing = new CompanyListing(
                        rs.getLong("id"),
                        rs.getLong("shareid"),
                        rs.getInt("numshare"),
                        rs.getInt("version"));
                listingDetails.add(companyListing);
            }
            rs.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {

            PostgresConnection.closeConnection(connection, preparedStatement);
        }
        return listingDetails;
    }

    public boolean deleteCompanyListingById(CompanyListing companyListing, Connection connection){
        String sql = "DELETE FROM public.companylisting WHERE id = ? AND version=?";
        PreparedStatement preparedStatement = null;
        int result = 0;
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, companyListing.getListingId());
            preparedStatement.setInt(2, companyListing.getVersion());
            result = preparedStatement.executeUpdate();
            if(result==0){
                throw new RuntimeException("Cannot delete the company listing: "+ companyListing.getListingId());
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return result!=0;
    }

    public Long getShareIdByListingId(Long listingId){
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        Long shareId = null;
        String sql =
                "SELECT shareid FROM public.companylisting WHERE id = ?";
        try {
            connection = PostgresConnection.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, listingId);
            ResultSet rs = preparedStatement.executeQuery();

            if (rs.next()) {
                shareId = rs.getLong("shareid");
            }
            rs.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {

            PostgresConnection.closeConnection(connection, preparedStatement);
        }
        return shareId;
    }
}
