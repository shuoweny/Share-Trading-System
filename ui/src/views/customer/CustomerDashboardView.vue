<template>
  <div id="customer-dashboard">
    <h1>Customer Dashboard</h1>
    <h3>Hi {{this.$route.params.username}}</h3>
    <h4>Current Balance is {{this.balance}}</h4>
    <el-menu mode="horizontal">
      <el-menu-item index="1" @click="switchMenu(1)">Market</el-menu-item>
      <el-menu-item index="2" @click="switchMenu(2)">Search</el-menu-item>
      <el-menu-item index="3" @click="switchMenu(3)">Portfolio</el-menu-item>
    </el-menu>

    <h2  v-if="menuIndex === 1">Market Place</h2>
    
    <company-listings v-if="menuIndex === 1" :companyListings="marketData" :displayType="'Buy'" @buy-share="openPurchaseDialog"></company-listings>
    <search-component v-if="menuIndex === 2" @buy-share="openPurchaseDialog"></search-component>
    <portfolio-component v-if="menuIndex === 3" :userId="this.userInfo.userId" @sell-share="openSellDialog" :isUpdate="isProfileUpdate"></portfolio-component>


    <!-- Purchase dialog -->
    <el-dialog v-model="isPurchaseDialogVisible" title="Purchase Share">
      <el-form :model="purchaseForm">
        <h4> Purchansing the listing {{ purchaseForm.listId }}</h4>
        <el-form-item label="Amount" :label-width="formLabelWidth">
          <el-input v-model="purchaseForm.amount" autocomplete="off" />
        </el-form-item>
      </el-form>
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="cancelPurchase">Cancel</el-button>
          <el-button type="primary" @click="confirmPurchase(purchaseForm.listId)">
            Confirm
          </el-button>
        </span>
      </template>
    </el-dialog>

    <!-- Sell dialog -->
    <el-dialog v-model="isSellDialogVisible" title="Sell Share">
      <el-form :model="sellForm">
        <h4> Sell the share {{ sellForm.listId }}</h4>
        <el-form-item label="Amount" :label-width="formLabelWidth">
          <el-input v-model="sellForm.amount" autocomplete="off" />
        </el-form-item>
      </el-form>
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="cancelPurchase">Cancel</el-button>
          <el-button type="primary" @click="confirmSell(sellForm.listId)">
            Confirm
          </el-button>
        </span>
      </template>
    </el-dialog>

  </div>
</template>

<script>
import SearchComponent from '../../components/SearchComponent.vue';
import PortfolioComponent from '../../components/PortfolioComponent.vue';
import CompanyListings from '../../components/CompanyListings.vue';
import api from '@/api';
import store from '@/store';
import { ElMessage } from 'element-plus';

export default {
  components: {
    SearchComponent,
    PortfolioComponent,
    CompanyListings,
  },
  data() {
    return {
      menuIndex: 1,
      marketData: [],
      isPurchaseDialogVisible: false,
      isSellDialogVisible: false,
      purchaseForm: {
        listId: '',
        amount: '',
      },
      sellForm: {
        listId: '',
        amount: '',
      },
      formLabelWidth: '180px',

      userInfo: null,

      balance: 0,

      isProfileUpdate: false,
    };
  },

  methods: {
    switchMenu(index){
      this.menuIndex = index;
      this.fetchMarketData();

    },

    getUserInfo() {
      this.userInfo = this.$store.getters.getUserInfo
    },

    buyShare(listingid) {
      console.log("Buy share " + listingid)
      console.log("Amount : " + this.purchaseForm.amount)
      console.log("start execute purchase logic")
      var that = this
      api.purchaseShare(this.userInfo.userId, this.purchaseForm).then((res) => {
        console.log(res)
        if (res.data.status === "success") {
          that.$store.dispatch("updateBalance", res.data.balance)
          that.balance = res.data.balance
          ElMessage({
            message: res.data.message,
            type: "success"
          })
          that.fetchMarketData()
        } else {
          ElMessage.error("High Concurrency, " + res.data.message)
        }
      })
    },

    sellShare(listingid) {
      console.log("Sell share ")
      console.log(this.sellForm.listId)
      console.log("Amount : " + this.sellForm.amount)
      console.log("start execute sell logic")
      var that = this
      api.sellShare(this.sellForm).then((res) => {
        console.log(res)
        if (res.data.status === "success") {
          that.$store.dispatch("updateBalance", res.data.balance)
          that.balance = res.data.balance
          ElMessage({
            message: res.data.message,
            type: "success"
          })
          that.fetchMarketData()
        } else {
          ElMessage.error("High Concurrency, " + res.data.message)
        }
      })
    },

    openPurchaseDialog(listId){
      this.isPurchaseDialogVisible = true;
      this.purchaseForm.listId = listId
    },

    confirmPurchase(listId){
      this.buyShare(listId)
      this.isPurchaseDialogVisible = false;

      this.purchaseForm = {
        listId: '',
        amount: '',
      }

      this.isProfileUpdate = !this.isProfileUpdate
    },


    cancelPurchase() {
      this.isPurchaseDialogVisible = false;

      this.purchaseForm = {
        listId: '',
        amount: '',
      }
    },

    openSellDialog(listId){
      this.isSellDialogVisible = true;
      this.sellForm.listId = listId
    },

    confirmSell(listId){
      this.sellShare(listId)
      this.isSellDialogVisible = false;
      this.sellForm = {
        listId: '',
        amount: '',
      }
      this.isProfileUpdate = !this.isProfileUpdate
    },

    cancelSell(){
      this.isSellDialogVisible = false;
      this.sellForm = {
        listId: '',
        amount: '',
      }
    },

    fetchMarketData() {
      var that = this;
      
      api.getCompanyListings("User", null, null, null).then((res) => {
        console.log(res)
        if (res.data.status === "success") {
          console.log(res.data)
          that.marketData = res.data.companyListings;
          that.$store.dispatch("updateAllCompanyListings", res.data.companyListings)
        } else {
          ElMessage.error("High Concurrency, Resourse Not Found Error")
          that.$router.go(0)
        }
      })

      }
  },

  beforeMount() {
    this.getUserInfo();
    this.fetchMarketData();
    this.balance = this.$store.getters.getBalance
  },

  beforeCreate() {
    this.get
  }
  
};
</script>

<style lang="scss">
#customer-dashboard {
  max-width: 1200px;
  margin: 0 auto;
  padding: 20px;
  .el-menu {
    margin-bottom: 20px;
  }
}
</style>
