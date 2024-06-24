package com.unimelb.swen90007.group404notfound.api.mapper;

import com.unimelb.swen90007.group404notfound.api.domain.Company;
import com.unimelb.swen90007.group404notfound.api.domain.Share;
import com.unimelb.swen90007.group404notfound.api.mapper.dbconnection.PostgresConnection;
import com.unimelb.swen90007.group404notfound.api.util.UnitOfWork;
import org.postgresql.util.PSQLException;

import java.sql.*;
import java.util.ConcurrentModificationException;

public class ShareMapper {
    public Share findShareById(Long id){
        String sql = "SELECT * FROM public.share WHERE id = ?";
        Share share = null;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try{
            connection = PostgresConnection.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, id);
            ResultSet rs = preparedStatement.executeQuery();
            share = assignShareValue(rs);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            PostgresConnection.closeConnection(connection,preparedStatement);
        }
        return share;
    }

    private Share assignShareValue(ResultSet rs){
        Share share = null;
        try {
            if(rs.next()){
                share = new Share(rs.getLong("id"),
                                    rs.getLong("companyid"),
                                    rs.getString("sharetype").charAt(0),
                                    rs.getBigDecimal("price"),
                                    rs.getInt("version"));
            }
            rs.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return share;
    }


    public boolean addShare(Share share, Connection connection) {
        boolean result = false;
        String sql = "INSERT INTO public.share (companyid, sharetype, price) VALUES (?,?,?);";
        String sql2 = "SELECT currval('share_id_seq') FROM public.share;";
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setLong(1, share.getCompanyId());
            preparedStatement.setString(2, share.getShareType().toString());
            preparedStatement.setBigDecimal(3, share.getPrice());
            PreparedStatement preparedStatement1 = connection.prepareStatement(sql2);
            result = preparedStatement.executeUpdate()==1;//0

            ResultSet rs1 = preparedStatement1.executeQuery();
            ResultSet rs = preparedStatement.getGeneratedKeys();
            if (rs1.next()) {
                Long shareId = rs1.getLong(1);
                UnitOfWork.storeResult.put("shareid", shareId);
            }
            rs.close();

        } catch (SQLException e){
            try {
                connection.rollback();
                System.out.println("insert concurrently");
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }

        }
        return true;

    }

    public boolean setShare(Share share, Connection connection){
        String sql = "UPDATE public.share SET companyid=?, sharetype=?, price=?, version=version+1 WHERE id=? AND version=?";
        PreparedStatement preparedStatement = null;
        int result = 0;
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, share.getCompanyId());
            preparedStatement.setString(2, String.valueOf(share.getShareType()));
            preparedStatement.setBigDecimal(3, share.getPrice());
            preparedStatement.setLong(4, share.getId());
            preparedStatement.setInt(5, share.getVersion());
            result = preparedStatement.executeUpdate();
            if(result==0){
                throw new RuntimeException("Cannot set this share: " + share.getVersion());
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return result!=0;
    }

    public int checkExist(Long companyId, Character shareType){
        String sql = "SELECT * FROM public.share WHERE companyid=? AND sharetype=?";
        Share share = null;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try{
            connection = PostgresConnection.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, companyId);
            preparedStatement.setString(2, String.valueOf(shareType));
            ResultSet rs = preparedStatement.executeQuery();
            if(rs.next()){
                return 0;
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            PostgresConnection.closeConnection(connection,preparedStatement);
        }
        return 1;
    }

}
