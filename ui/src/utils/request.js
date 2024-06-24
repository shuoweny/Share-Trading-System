import axios from "axios";
import { ElMessage } from 'element-plus';
// import ElementPlus from 'element-plus';
import router from '@/router';
import path from '@/api/path.js';
import store from "@/store";


const service = axios.create({
  timeout:600000,
  headers:{
    'Content-Type' : 'application/json',
  },
  withCredentials : true,
  // crossDomain: true,
  baseURL: path.baseUrlLoc 
})

service.interceptors.request.use(
  (config) => {
    if (store.getters.getAuthenticationStatus) {
      var token = store.getters.getAuthenticationToken
      console.log("Authenticated Request")
      console.log("GET Token")
      console.log(token)
      config.headers['Authorization'] = token;
    } else {
      console.log("Not Login Yet")
    }

    return config;
  },
  error => {
    return Promise.reject(error)
  }

);

service.interceptors.response.use(
  response => {
    // check the status

    // preprocess the data

    return response;
  },
  error => {
    console.error('Error:', error);

    ElMessage.error("Concurrency, " + error)

    if (error.response.status === 401) {
      console.log("401 Error")
      router.push('/')
      return Promise.reject("Please Login Again, Close this page");
    }

    return Promise.reject(error);
  },
);

export default service;