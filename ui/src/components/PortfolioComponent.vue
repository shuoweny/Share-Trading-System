<template>
  <div class="portfolio-component">
    <!-- Portfolio table with Sell button -->
    <h2>Portfolio</h2>
    <h3>Net Profit is {{-netProfit}}</h3>
    <el-table :data="portfolioData">
      <el-table-column prop="listingid" label="ID" v-if="false"></el-table-column>
      <el-table-column prop="companyName" label="Company Name"></el-table-column>
      <el-table-column prop="shareType" label="Share Type"></el-table-column>
      <el-table-column prop="numShare" label="Amount"></el-table-column>
      <el-table-column prop="purchasePrice" label="Purchase Price"></el-table-column>
      <el-table-column prop="currentPrice" label="Current Price"></el-table-column>
      <el-table-column prop="profit" label="Profit"></el-table-column>
      <el-table-column label="Actions" v-if="displayType === 'User'">
        <template #default="scope">
          <el-button type="primary" @click="sellShare(scope.row.listingid)">Sell</el-button>
        </template>
      </el-table-column>
    </el-table>

  </div>
</template>

<script>
import CompanyListings from './CompanyListings.vue';
import api from '@/api';
import { ElMessage } from 'element-plus';

export default {
  components: {
    CompanyListings,
  },
  props: {
    userId: {
      type: Number,
      default: null,
    },
    displayType: {
      type: String,
      default: "User"
    },
    isUpdate: {
      type: Boolean,
      default: false
    }

  },
  data() {
    return {
      portfolioData: [],
      netProfit: 0,
    };
  },
  methods: {
    async getPortfolioDataAndNetProfit() {
      console.log('getting user info, id : ' + this.userId);
      var that = this
      try {
        const res = await api.getPortfolioData(this.userId);
        if (res.data.status === "success") {
          console.log(res.data.portfolioDetails);
          var processedData = this.processRawPortfolioData(res.data.portfolioDetails)
          that.portfolioData = processedData.portfolioData;
          that.netProfit = processedData.profit;
        } else {
          ElMessage.error("High Concurrency, Resource Not Found");
        }
      } catch (error) {
        console.error('Error fetching portfolio data', error);
        ElMessage.error("High Concurrency, Error fetching data");
      }
    },
    processRawPortfolioData(rawData) {
      console.log("processing portfolio data")
      var processedData = []
      var netProfit = 0
      for (var portfolio of rawData) {
        console.log(portfolio)
        processedData.push({
          listingid : portfolio.listingid,
          companyName: portfolio.companyName,
          shareType: portfolio.shareType,
          numShare: portfolio.numShare,
          purchasePrice : portfolio.price,
          currentPrice: portfolio.price + portfolio.profit / portfolio.numShare,
          profit: portfolio.profit
        })
        netProfit -= portfolio.profit
      }

      return {portfolioData: processedData, profit: netProfit}
    },

    sellShare(listId) {
      console.log("Emiting Event")
      console.log(listId)
      this.$emit('sell-share', listId);
    },
  },
  created() {
    this.getPortfolioDataAndNetProfit();
  }
};
</script>


<style lang="scss">
.portfolio-component {
  .el-button {
    margin-right: 10px;
  }
  .el-input {
    margin-bottom: 20px;
  }
  .el-table {
    width: 100%;
  }
}
</style>
