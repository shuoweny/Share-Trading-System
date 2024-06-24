package com.unimelb.swen90007.group404notfound.api.mapper;

import com.unimelb.swen90007.group404notfound.api.domain.CompanyListing;
import com.unimelb.swen90007.group404notfound.api.domain.InvestListing;
import com.unimelb.swen90007.group404notfound.api.domain.Listing;
import com.unimelb.swen90007.group404notfound.api.dto.ListingDetailDTO;
import com.unimelb.swen90007.group404notfound.api.mapper.dbconnection.PostgresConnection;
import com.unimelb.swen90007.group404notfound.api.util.UnitOfWork;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class InvestListingMapper {

    public Listing findListingById(Long id) {
        String sql = "SELECT * FROM public.investlisting WHERE id = ?";
        InvestListing listing = null;
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

    public boolean addInvestListing(InvestListing listing, Connection connection){
        String sql = "INSERT INTO public.investlisting (shareid, numshare) VALUES (?,?)";
        PreparedStatement preparedStatement = null;
        boolean back;
        try {
            preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setLong(1, listing.getShareId());
            preparedStatement.setInt(2, listing.getNumShare());
            back = preparedStatement.executeUpdate()==1;
            if(back ){
                ResultSet rs = preparedStatement.getGeneratedKeys();
                if(rs.next()){
                    Long listingid = rs.getLong(1);
                    HashMap<String, Long> storeResult = new HashMap<>();
                    storeResult.put("listingid", listingid);
                    UnitOfWork.setStoreResult(storeResult);
                    listing.setListingId(listingid);
                }
                rs.close();

            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return back;
    }

    public boolean setInvestListing(InvestListing listing, Connection connection){
        String sql = "UPDATE public.investlisting SET shareid=?, numshare=?, version=version+1 WHERE id=? AND version=?";
        PreparedStatement preparedStatement = null;
        int result = 0;
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, listing.getShareId());
            preparedStatement.setInt(2, listing.getNumShare());
            preparedStatement.setLong(3, listing.getListingId());
            preparedStatement.setInt(4, listing.getVersion());
            result = preparedStatement.executeUpdate();
            if(result==0){
                throw new RuntimeException("Cannot update the invest listing: "+ listing.getListingId());
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return result!=0;
    }
    private InvestListing assignListingValue(ResultSet resultSet) {
        InvestListing listing = null;
        try {
            if (resultSet.next()) {
                listing = new InvestListing(resultSet.getLong("id"),
                                resultSet.getLong("shareid"),
                                resultSet.getInt("numshare"),
                                resultSet.getInt("version"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return listing;
    }

//    public ListingDetailDTO findListingDTOById(Long listingID) {
//        Connection connection = null;
//        PreparedStatement preparedStatement = null;
//        ListingDetailDTO listingDetail = null;
//        String sql =
//                "SELECT l.* FROM public.investlisting l" +
//                        " JOIN public.share s ON l.shareid = s.id " +
//                        "JOIN public.company c ON s.companyid = c.id WHERE l.id=?";
//        try {
//            connection = PostgresConnection.getConnection();
//            preparedStatement = connection.prepareStatement(sql);
//            preparedStatement.setLong(1, listingID);
//            ResultSet rs = preparedStatement.executeQuery();
//
//            if(rs.next()) {
//                listingDetail = new ListingDetailDTO();
//                listingDetail.setId(rs.getLong("id"));
//                listingDetail.setShareId(rs.getLong("shareid"));
//                listingDetail.setNumShare(rs.getInt("numshare"));
//            }
//            rs.close();
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        } finally {
//
//            PostgresConnection.closeConnection(connection, preparedStatement);
//        }
//        return listingDetail;
//    }


    public List<InvestListing> findAllInvestListingByUserId(Long userId){
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        List<InvestListing> listingDetails = new ArrayList<>();
        String sql =
                "SELECT l.* ,p.userid FROM public.investlisting l" +
                        " JOIN public.portfolio p ON l.id = p.listingid " +
                        "WHERE p.userid = ?";
        try {
            connection = PostgresConnection.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, userId);
            ResultSet rs = preparedStatement.executeQuery();

            while(rs.next()) {
                InvestListing listingDetail = assignListingValue(rs);

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

    public boolean deleteInvestListing(InvestListing listing , Connection connection){
        String sql = "DELETE FROM public.investlisting WHERE id = ? AND version=?";
        PreparedStatement preparedStatement = null;
        int result = 0;
        Long listingId = listing.getListingId();
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, listingId);
            preparedStatement.setInt(2, listing.getVersion());
            result = preparedStatement.executeUpdate();
            if(result==0){
                throw new RuntimeException("Cannot delete the invest listing: "+ listing.getListingId());
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return result!=0;
    }
}
