<template>
  <div class="container">
    <el-table :data="companyListings" style="width: 100%">
      <el-table-column prop="listingid" label="Listing ID"></el-table-column>
      <el-table-column prop="companyname" label="Company Name"></el-table-column>
      <el-table-column prop="category" label="Category" v-if="displayType !== 'Delete'"></el-table-column>
      <el-table-column prop="shareType" label="Share Type"></el-table-column>
      <el-table-column prop="numShare" label="Amount"></el-table-column>
      <el-table-column prop="price" label="Price($)"></el-table-column>
      <el-table-column v-if="displayType == 'Buy'" label="Purchase">
        <template #default="scope">
          <el-button type="primary" @click="buyShare(scope.row.listingid)">Buy</el-button>
        </template>
      </el-table-column>
      <el-table-column v-if="displayType == 'Sell'" label="Sell">
        <template #default="scope">
          <el-button type="primary" @click="sellShare(scope.row.listingid)">Sell</el-button>
        </template>
      </el-table-column>
      <el-table-column v-if="displayType == 'Edit'" label="Edit">
        <template #default="scope">
          <!-- <el-popconfirm title="Are you sure to delete this?" @confirm="deleteListing(scope.row.listId)"> -->
            <!-- <template #reference> -->
              <el-button type="primary" @click="editListing(scope.row)">Edit</el-button>
            <!-- </template> -->
          <!-- </el-popconfirm> -->
        </template>
      </el-table-column>
    </el-table>
  </div>
</template>

<script>
export default {
  props: {
    companyListings: {
      type: Array,
      default: () => [],
    },
    displayType: {
      type: String,
      default: null,
    },
  },
  methods: {
    buyShare(listingid) {
      this.$emit('buy-share', listingid);
    },
    sellShare(listingid) {
      this.$emit('sell-share', listingid);
    },
    editListing(listing) {
      this.$emit('edit-listing', listing);
    }
  },
};
</script>

<style scoped lang="scss">
.container {
  padding: 20px;
}
</style>
