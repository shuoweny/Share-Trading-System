<!-- src/views/AdminDashboard.vue -->
<template>
  <div>
    <h1>Administrator Dashboard</h1>
    <el-menu mode="horizontal">
      <el-menu-item index="1" @click="switchMenu(1)">All Customers</el-menu-item>
      <el-menu-item index="2" @click="switchMenu(2)">All Company Listings</el-menu-item>
      <el-menu-item index="3" v-if="menuIndex === 3">Customer Portfolio</el-menu-item>
    </el-menu>
    <h2  v-if="menuIndex === 1">All Customers</h2>
    <h2  v-if="menuIndex === 2">All Company Listings</h2>
    <h2  v-if="menuIndex === 3">Cutomer Portfolio Details</h2>

    <AllCustomers v-if="menuIndex === 1" :customers="customers" @view-portfolio="viewPortfolio"></AllCustomers>
    <CompanyListings v-if="menuIndex === 2" :companyListings="companyListings"></CompanyListings>
    <portfolio-component v-if="menuIndex === 3" :userId="this.currentViewingCid" :displayType="'Admin'"></portfolio-component>
  </div>
</template>

<script>
import AllCustomers  from '../../components/AdminAllCustomers.vue';
import CompanyListings from '../../components/CompanyListings.vue';
import api from '../../api'
import PortfolioComponent from '../../components/PortfolioComponent.vue';
import { ElMessage } from 'element-plus';

export default {
  components: {
    AllCustomers,
    CompanyListings,
    PortfolioComponent,
  },
  data() {
    return {
      menuIndex : 1,
      customers: [],
      companyListings: [],

      currentViewingCid : null,
    };
  },
  methods: {
    switchMenu(index) {
      this.menuIndex = index;
      this.fetchCustomers();
      this.fetchAllCompanyListings();
    },
    viewPortfolio(customerId) {
      console.log('View portfolio of customer with ID:', customerId);
      this.currentViewingCid = customerId;
      this.switchMenu(3);
      // Here you can add the logic to view the portfolio of the selected customer
    },
    fetchCustomers() {
      var that = this
      api.getAllCustomers().then((res) => {
        if (res.data.status === "success") {
          that.customers = res.data.customers
        } else {
          ElMessage.error("High Concurrency, Resourse Not Found Error")
        }
      })
    },
    fetchAllCompanyListings() {
      var that = this
      api.getCompanyListings("Admin", null, null, null).then((res) => {
        if (res.data.status === "success") {
          console.log(res.data)
          that.companyListings = res.data.companyListings;
        } else {
          ElMessage.error("High Concurrency, Resourse Not Found Error")
          that.$router.go(0)
        }
      })
    },
  },

  beforeMount() {
    this.fetchCustomers();
    this.fetchAllCompanyListings();
  },
};
</script>
