<template>
  <div class="search-component">
    <h2>Search For Share</h2>
    <span>Company Name</span>
    <el-input placeholder="Company Name" v-model="filters.companyName"></el-input>
    <span>Industry / Category</span>
    <el-input placeholder="Industry" v-model="filters.industry"></el-input>
    <span>Share Type</span>
    <el-select v-model="filters.shareType" placeholder="Select Type" size="large">
      <el-option
        :label="'A'"
        :value="'A'"
      />
      <el-option
        :label="'B'"
        :value="'B'"
      />
      <el-option
        :label="'C'"
        :value="'C'"
      />
      <el-option
      :label="'D'"
      :value="'D'"
    />
    </el-select>
    <span>Price Range</span>
    <p>Minimum</p>
    <el-input placeholder="Minimum Price" v-model="filters.priceRange.minimum"></el-input>
    <p>Maximum</p>
    <el-input placeholder="Maximum Price" v-model="filters.priceRange.maximum"></el-input>
    <el-button type="primary" @click="searchShares">Search</el-button>

    <!-- Search results table with Buy button -->
    <h2>Search Result</h2>
    <company-listings class="result" :companyListings="searchResults" :displayType="'Buy'" @buy-share="buyShare"></company-listings>

  </div>
</template>

<script>
import { ref } from 'vue';
import CompanyListings from "./CompanyListings.vue"
import searchService from '@/utils/searchAlgo';

export default {
  components: {
    CompanyListings,
  },
  setup(props, context) {
    const filters = ref({
      companyName: '',
      industry: '',
      shareType: '',
      priceRange: {
        minimum: null,
        maximum: null
      },
    });
    const searchResults = ref([]);
    const purchaseAmount = ref('');

    const searchShares = () => {
      var that = this

      console.log('search cirteria ')
      console.log(filters.value)
      // Implement the search logic later
      searchResults.value = searchService.filterListings(filters.value)
      filters.value = {
                      companyName: '',
                      industry: '',
                      shareType: '',
                      priceRange: {
                        minimum: null,
                        maximum: null
                      },
                    }
    };
    const buyShare = (listId) => {
      context.emit('buy-share', listId);
    };
    return {
      filters,
      searchResults,
      purchaseAmount,
      searchShares,
      buyShare,
    };
  },
};
</script>

<style lang="scss">
.search-component {
  display: flex;
  flex-direction: column;
  align-items: center;
  .el-button {
    margin-right: 10px;
  }
  .el-input {
    margin-bottom: 20px;
    width: 50%;
  }
  .el-table {
    width: 100%;
  }
  .el-select{
    width: 50%;
  }
  .result{
    width: 100%;
  }
}
</style>
