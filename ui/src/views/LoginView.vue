<template>

  <div class="content-container">
    <el-container>
      <el-header class="title">
        <h1>404NotFound Trading System</h1>
        <h2>Login</h2>
      </el-header>
      <el-main>
        <el-form ref="form" :model="form" label-width="120px">
          <el-form-item label="User Type">
            <el-select v-model="form.userType" placeholder="Select user type">
              <el-option label="Administrator" value="Admin"/>
              <el-option label="Company User" value="Company"/>
              <el-option label="Customer" value="User"/>
            </el-select>
          </el-form-item>
          <el-form-item label="Username">
            <el-input v-model.trim="form.username" aria-placeholder="user name"></el-input>
          </el-form-item>
          <el-form-item label="Password">
            <el-input type="password" v-model.trim="form.password" aria-placeholder="password"></el-input>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="handleSubmit">Login</el-button>
          </el-form-item>
        </el-form>
      </el-main>
    </el-container>
  </div>

</template>

<script>
import router from '../router'
import api from '../api';
import { ElMessage } from 'element-plus';

export default {
  data() {
    return {
      form: {
        userType: '',
        username: '',
        password: '',
      },
    };
  },
  methods: {
    handleSubmit() {
      var that = this;
      if (this.form.userType && this.form.username && this.form.password) {
        console.log('Form submitted', this.form);
        api.login(this.form.username, this.form.password, this.form.userType).then((res) => {
          console.log(res.data)
          var resData = res.data
          if (resData.status === "success") {
            console.log(resData.message)
            var userInfo = {
              userType: resData.roletype,
              username: that.form.username,
              userId: resData.userId,
            };
            
            that.$store.dispatch('updateUserInfo', userInfo);
            console.log("user-info stored")
            that.$store.dispatch('updateAuthenticationStatus', true);
            console.log("authentication stored")
            that.$store.dispatch('updateAuthenticationToken', resData.token);
            console.log("token stored")


            ElMessage({
              message: resData.message + " user type : " + resData.roletype,
              type: 'success',
            })
            
            setTimeout(() => {
              if (userInfo.userType === 'Company') {
                that.$store.dispatch('updateCompanyId', resData.companyid)
                router.push({ path: '/company-dashboard/' + userInfo.userId + '/' + userInfo.username })
              } else if (userInfo.userType === 'Admin') {
                router.push({ path: '/admin-dashboard/' +  userInfo.userId + '/' + userInfo.username})
              } else {
                that.$store.dispatch("updateBalance", res.data.balance)
                router.push({ path: '/customer-dashboard/' + userInfo.userId + '/' + userInfo.username })
              }
            }, 3000); 


          } else {
            ElMessage.error("High Concurrency, " + resData.message)
          }

        })
        
      } else {
        console.error('All fields are required');
        ElMessage.error('High Concurrency, All fields are required')
      }
    },
  },
};
</script>

<style lang="scss" scoped>
.title{
  margin-bottom: 10%;
}
</style>
