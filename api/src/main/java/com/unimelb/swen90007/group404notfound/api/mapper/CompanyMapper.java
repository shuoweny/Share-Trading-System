package com.unimelb.swen90007.group404notfound.api.mapper;

import com.unimelb.swen90007.group404notfound.api.domain.Company;
import com.unimelb.swen90007.group404notfound.api.mapper.dbconnection.PostgresConnection;
import com.unimelb.swen90007.group404notfound.api.util.UnitOfWork;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class CompanyMapper {

    public boolean addCompany(Company company, Connection connection){
        boolean result;
        String sql = "INSERT INTO public.company(companyname, category)"+
                "VALUES (?, ?)";
        PreparedStatement preparedStatement = null;

        try {
            preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, company.getCompanyName());
            preparedStatement.setString(2, company.getCategory());
            result = preparedStatement.executeUpdate()==1;
            if(result) {
                ResultSet rs = preparedStatement.getGeneratedKeys();
                if (rs.next()) {
                    Long companyId = rs.getLong(1);
                    UnitOfWork.storeResult.put("companyid", companyId);
                }
                rs.close();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    public Company findCompanyById(Long id){
        Company company = null;
        String sql = "SELECT * FROM public.company WHERE id = ?";
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = PostgresConnection.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()){
                company = new Company(
                        resultSet.getLong("id"),
                        resultSet.getString("companyname"),
                        resultSet.getString("category"),
                        resultSet.getBigDecimal("balance"),
                        resultSet.getInt("version")
                );
            }
            resultSet.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            PostgresConnection.closeConnection(connection,preparedStatement);
        }
        return company;
    }

    public boolean setCompany(Company company, Connection connection){
        String sql = "UPDATE public.company SET companyname=?, category=?, balance=?, version = version + 1 WHERE id=? AND version=?";
        PreparedStatement preparedStatement = null;
        int result = 0;
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, company.getCompanyName());
            preparedStatement.setString(2, company.getCategory());
            preparedStatement.setBigDecimal(3, company.getBalance());
            preparedStatement.setLong(4, company.getCompanyId());
            preparedStatement.setInt(5, company.getVersion());
            result = preparedStatement.executeUpdate();
            if(result == 0){
                throw new RuntimeException("Cannot update company: " + company.getCompanyName());
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return result!=0;
    }

    public Company findCompanyByName(String username){
        Company company = null;
        String sql = "SELECT id FROM public.company WHERE companyname = ?";
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = PostgresConnection.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, username);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                company = new Company(
                        resultSet.getLong("id"),
                        null,
                        null,
                        null,
                        resultSet.getInt("version")
                        );

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            PostgresConnection.closeConnection(connection,preparedStatement);
        }
        return company;
    }

    public List<Company> findAllCompany(){
        List<Company> companies = new ArrayList<>();
        String sql = "SELECT id, companyname FROM public.company";
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = PostgresConnection.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                Company company = new Company(
                        resultSet.getLong("id"),
                        resultSet.getString("companyname"),
                        null,
                        null,
                        resultSet.getInt("version")
                );
                companies.add(company);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            PostgresConnection.closeConnection(connection,preparedStatement);
        }
        return companies;
    }
}
