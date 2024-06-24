<template>
  <el-form ref="form" :model="form" label-width="120px">
    <!-- <el-form-item label="Company Name">
      <el-input v-model="companyName" :disabled="true" />
    </el-form-item> -->
    <el-form-item label="Share Price">
      <el-input v-model="form.sharePrice" type="number" step="0.01"/>
    </el-form-item>
    <el-form-item label="Share Amount">
      <el-input v-model.number="form.shareAmount" />
    </el-form-item>
    <el-form-item label="Share Type">
      <el-select v-model="form.shareType" placeholder="Select share type">
        <el-option label="A" value="A"></el-option>
        <el-option label="B" value="B"></el-option>
        <el-option label="C" value="C"></el-option>
        <el-option label="D" value="D"></el-option>
      </el-select>
    </el-form-item>
    <el-form-item>
      <el-button type="primary" @click="onSubmit">Submit</el-button>
    </el-form-item>
  </el-form>
</template>

<script>
import { mapGetters } from 'vuex';
import api from '@/api';
import { ElMessage } from 'element-plus';

export default {
  name: 'AddCompanyListing',
  data() {
    return {
      form: {
        sharePrice: null,
        shareAmount: null,
        shareType: '',
      },
    };
  },
  computed: {
    ...mapGetters(['getCompanyId']),
  },
  methods: {
    onSubmit() {
      console.log('Form data:', this.form);
      api.addCompanyListings(
        this.getCompanyId,
        this.form.shareType,
        this.form.shareAmount,
        this.form.sharePrice // It seems like you have a mistake here, should it be this.form.shareType?
      ).then((res) => {
        console.log(res)
        if (res.data.status === "success") {
          console.log("added");
          ElMessage({
            message: "Successfully Added",
            type: "success",
          });
          this.$emit("renew-listings");
        } else if (res.data.status === "duplicate") { 
          console.log("Fail to add, ");
          ElMessage.error("Share exist, please considering edit listing");
        } else {
          console.log("Fail to add");
          ElMessage.error("High Concurrency, Failed to add listing");
        }
      });
    },
  },
};
</script>

<style>
.el-form {
  max-width: 400px;
  margin: auto;
  margin-top: 4em;
}
</style>
