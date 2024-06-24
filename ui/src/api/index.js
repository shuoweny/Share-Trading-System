import { ElMessage } from 'element-plus'
import axios from '../utils/request'
import axiosNoAuth from '../utils/requestNoAuth'
import path from './path'


const api = {

  login(username, pwd, type) {
    var userinfo = {
      'username':username,  
      'password': pwd,
      'roletype': type
    }
    return axios.post(path.login, userinfo, {headers:{'Content-Type' : 'application/json'}, withCredentials:true})
  },


  getCompanyListings(roleType, companyid, companyName, shareType) {
    var param = {}
    var reqPath = ""
    if (roleType === "Admin") {
      param = {
        'roletype' : "Admin"
      }
      reqPath = path.viewCompanyListings
    } else if (roleType === "Company") {
      param = {
        // 'roletype': 'Company',
        'companyid' : companyid,
      }
      reqPath = path.companyViewListings
    } else {
      param = {
        'roletype': "Customer",
        'companyname': companyName,
        'shareType': shareType,
      }
      reqPath = path.viewCompanyListings
    }
    return axios.get(reqPath, { params: param, withCredentials:true });
  },


  getAllCustomers() {
    return axios.get(path.viewAllCustomer, { withCredentials: true })
  },

  getPortfolioData(userId) {
    var param = {
      "userId" : userId
    }
    return axios.get(path.getPortfolioDetails, { params: param , withCredentials:true})
  },

  addShare(companyId, shareType, price) {
    console.log("Adding Share")
    var body = {
      "companyId": companyId,
      "shareType": shareType,
      "price": price
    }
    return axios.post(path.addShare, body, { withCredentials: true })
  },

  async addCompanyListings(companyId, shareType, amount, price) {
    var resp = await this.addShare(companyId, shareType, price)
    var body = null
    if (resp.data.status === "success") {
      console.log("share id is : ", resp.data.shareId)
      body = {
        "shareId": resp.data.shareId,
        "numShare": amount 
      }
      return axios.post(path.addCompanyListing, body, { withCredentials: true })
    } else if (resp.data.status === "duplicate") {
      ElMessage.error("Duplicate Share Type")
      return {data : {status : "duplicate"}}
    } else {
      ElMessage.error("Failed to create share")
      return {data : {status : "fail"}}
    }
    
  },

  updateCompanyListings(updateForm) {
    var body = {
      "listingid" : updateForm.listingId,
      "price": updateForm.price,
      "amount" : updateForm.amount
    }
    return axios.post(path.updateCompanyListing, body, { withCredentials: true })
  },

  sellShare(sellForm) {
    console.log(sellForm.listId)
    console.log(sellForm.amount)
    var body = {
      "listingid": sellForm.listId,
      "amount" : sellForm.amount
    }
    return axios.post(path.sellShare, body, { withCredentials: true })
  },

  purchaseShare(userId, purchaseform) {
    var body = {
      "userid" : userId,
      "listingid": purchaseform.listId,
      "amount" : purchaseform.amount
    }
    return axios.post(path.purchaseShare, body, { withCredentials: true })
  },
  


  
}
export default api
