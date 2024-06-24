package com.unimelb.swen90007.group404notfound.api.domain;

import com.unimelb.swen90007.group404notfound.api.mapper.AdminMapper;
import com.unimelb.swen90007.group404notfound.api.mapper.CompanyUserMapper;
import com.unimelb.swen90007.group404notfound.api.mapper.CustomerMapper;

public abstract class User {

    public User(Long userId, String username, String password, String lastname, String firstname, Integer version) {
        this.userAuthentication = new UserAuthentication(username, password);
        this.lastname = lastname;
        this.firstname = firstname;
        this.userId = userId;
        this.version = version;
    }
    public User(Long userId, String username, String password, String lastname, String firstname) {
        this.userAuthentication = new UserAuthentication(username, password);
        this.lastname = lastname;
        this.firstname = firstname;
        this.userId = userId;
    }

    private Long userId;
    private UserAuthentication userAuthentication;
    private String lastname;
    private String firstname;

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    private Integer version;




    public String getUsername() {
        return userAuthentication.getUsername();
    }

    public String getPassword() {
        return userAuthentication.getPassword();
    }

    public UserAuthentication getUserAuthentication() {
        return userAuthentication;
    }

    public void setUserAuthentication(UserAuthentication userAuthentication) {
        this.userAuthentication = userAuthentication;
    }

    public String getLastname() {
        if (this.lastname == null) {
            load();
        }
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getFirstname() {
        if (this.firstname == null) {
            load();
        }
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public Long getUserId() {
        if (this.userId == null) {
            load();
        }
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", username='" + getUsername() + '\'' +
                ", password='" + getPassword() + '\'' +
                ", lastname='" + lastname + '\'' +
                ", firstname='" + firstname + '\'' +
                '}';
    }
    // lazy load: ghost implementation
    void load(){
        User userRecord = null;
        if(this instanceof Customer) {
            CustomerMapper customerMapper = new CustomerMapper();
            if (this.userId == null && this.userAuthentication.getUsername() != null) {
                customerMapper.findCustomerByUsername(this.userAuthentication.getUsername());
            }
            userRecord = customerMapper.findCustomerById(this.userId);
        } else if (this instanceof Admin) {
            AdminMapper adminMapper = new AdminMapper();
            if (this.userId == null && this.userAuthentication.getUsername() != null) {
                adminMapper.findAdminByUsername(this.userAuthentication.getUsername());
            }
            userRecord = adminMapper.findAdminById(this.userId);
        } else {
            CompanyUserMapper companyUserMapper = new CompanyUserMapper();
            if (this.userId == null && this.userAuthentication.getUsername() != null) {
                companyUserMapper.findCompanyUserByUsername(this.userAuthentication.getUsername());
            }
            userRecord = companyUserMapper.findCompanyUserById(this.userId);
        }
        if (this.firstname == null) {
            this.firstname = userRecord.getFirstname();
        }
        if (this.lastname == null) {
            this.lastname = userRecord.getLastname();
        }
        if(this.version == null){
            this.version = userRecord.getVersion();
        }
        if (this.userAuthentication.getUsername() == null) {
            this.userAuthentication.setUsername(userRecord.getUsername());
        }
        if (this.userAuthentication.getPassword() == null) {
            this.userAuthentication.setPassword(userRecord.getPassword());
        }
    }


}
