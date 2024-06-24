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
  withCredentials : false,
  crossDomain: false,
  baseURL: path.baseUrlLoc 
})

service.interceptors.request.use(
  // (config) => {
  //   const token = 


  // }

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

    if (error.response && error.response.status === 401) {
      router.push('/')
    }

    return Promise.reject(error);
  },
);

export default service;