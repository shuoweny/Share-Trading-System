<template>
  <div id="company-dashboard">
    <h1>Company Dashboard</h1>
    <el-menu mode="horizontal">
      <el-menu-item index="1" @click="switchMenu(1)">Create</el-menu-item>
      <el-menu-item index="2" @click="switchMenu(2)">Manage Current Listing</el-menu-item>
    </el-menu>

  
    <add-company-listing v-if="menuIndex === 1" @renew-listings="renewListings"></add-company-listing>
    <company-listings v-if="menuIndex === 2" :companyListings="companyListings" :displayType="'Edit'" @edit-listing="editListing"></company-listings>

    <!-- Update dialog -->
    <el-dialog v-model="isUpdateVisiable" title="Edit Listing">
      <el-form :model="updateForm">
        <h4> Edit Listing {{ updateForm.listingId }}</h4>
        <el-form-item label="Set Amount" :label-width="formLabelWidth">
          <el-input v-model="updateForm.amount" autocomplete="off" />
        </el-form-item>
        <el-form-item label="Set Price" :label-width="formLabelWidth">
          <el-input v-model="updateForm.price" autocomplete="off" />
        </el-form-item>
      </el-form>
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="cancelUpdate">Cancel</el-button>
          <el-button type="primary" @click="confirmUpdate">
            Confirm
          </el-button>
        </span>
      </template>
    </el-dialog>

  </div>
</template>

<script>
import api from '@/api';
import AddCompanyListing from '../../components/AddCompanyListing.vue';
import CompanyListings from '../../components/CompanyListings.vue';
import store from '@/store';
import { ElMessage } from 'element-plus';

export default {
  components: {
    CompanyListings,
    AddCompanyListing,
  },

  data() {
    return {
      menuIndex: 1,

      companyListings: [],

      isUpdateVisiable: false,

      updateForm: {
        listingId: null,
        amount: null,
        price : null,
      },

      formLabelWidth: "10em"

    }
  },

  methods: {
    switchMenu(index){
      this.menuIndex = index;
      this.fetchCompanyListings();
    },

    editListing(listing) {
      console.log('Editing Listing :')
      console.log(listing)
      this.isUpdateVisiable = true
      this.updateForm.listingId = listing.listingid
      this.updateForm.amount = listing.numShare
      this.updateForm.price = listing.price

    },

    cancelUpdate() {
      this.isUpdateVisiable = false,
      this.updateForm.listingId = null
      this.updateForm.amount = null
      this.updateForm.price = null
    },

    confirmUpdate() {
      console.log("Submit Update Form")
      console.log(this.updateForm)

      var that = this
      api.updateCompanyListings(this.updateForm).then((res) => {
        console.log(res)
        if (res.data.status === "success") {
          ElMessage({
            message: res.data.message,
            type: "success"
          })
          that.fetchCompanyListings()
        } else {
          ElMessage.error(res.data.message)
        }

      })

      this.isUpdateVisiable = false;

    },

    renewListings() {
      this.fetchCompanyListings()
    },

    fetchCompanyListings() {
      var that = this
      const companyId = this.$store.getters.getCompanyId
      console.log("Company Id : ", companyId)
      api.getCompanyListings("Company", companyId, null, null).then((res) => {
        if (res.data.status === "success") {
          console.log(res.data.companyListings);
          that.companyListings = res.data.companyListings;
        } else {
          ElMessage.error("High Concurrency, Resourse Not Found Error")
        }
      })
    },
      
  },

  beforeMount() { 
    this.fetchCompanyListings();
  }
};
</script>

<style lang="scss">


</style>
