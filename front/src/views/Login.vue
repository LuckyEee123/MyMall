<template>
  <div style="height: 100vh; overflow: hidden">
    <div
      style="height: 50px; line-height: 50px; border-bottom: 2px solid var(--colorRed); padding-left: 50px; color: var(--colorRed)">
      <b style="font-size: 24px;">MyMall</b>
      <i style="margin-left: 20px">-- 电商系统</i>
    </div>

    <div
      style="width: 50%; margin: 100px auto; border-radius: 10px; box-shadow: 0 0 10px -2px cornflowerblue; display: flex;">
      <div style="flex: 1; padding: 100px 50px">
        <img src="@/assets/imgs/loginBackground.png" alt="" style="width: 100%;">
      </div>

      <div style="flex: 1; padding: 50px">
        <div class="form-toggle">
          <b :class="{ 'active': activeToggle === 'account' }" @click="toggle('account')">账号登录</b>
          <b :class="{ 'active': activeToggle === 'phone' }" style="margin-left: 10px"
            @click="toggle('phone')">手机号登录</b>
        </div>

        <el-form ref="form" :rules="rules" :model="loginForm" v-if="activeToggle === 'account'">
          <el-form-item prop="account">
            <el-input size="medium" v-model="loginForm.account" prefix-icon="el-icon-user" placeholder="请输入账号/手机号/邮箱">
            </el-input>
          </el-form-item>
          <el-form-item prop="password">
            <el-input size="medium" show-password v-model="loginForm.password" prefix-icon="el-icon-lock"
              placeholder="请输入密码"></el-input>
          </el-form-item>
          <div style="margin: 10px 0; text-align: right">
            忘记密码
          </div>
          <div>
            <el-button type="primary" size="medium" style="width: 100%" @click="login">登录</el-button>
          </div>
          <div style="margin: 10px 0; text-align: right;">
            <a href="/#/register" style="color: var(--colorRed)">立即注册</a>
          </div>
        </el-form>

        <el-form ref="form" :model="loginForm" v-else>
          <el-form-item prop="account">
            <el-input size="medium" v-model="loginForm.account" prefix-icon="el-icon-user" placeholder="请输入手机号">
            </el-input>
          </el-form-item>
          <div style="display: flex; margin-bottom: 18px">
            <el-input size="medium" style="flex: 2" v-model="loginForm.password" prefix-icon="el-icon-circle-check"
              placeholder="验证码">
            </el-input>
            <el-button :disabled="second > 0" size="medium" style="flex: 1; margin-left: 5px" @click="sendSMS">点击发送
              <span v-if="second > 0">({{ second }})</span>
            </el-button>
          </div>
          <div>
            <el-button type="primary" size="medium" style="width: 100%">登录</el-button>
          </div>
          <div style="margin: 10px 0; text-align: right;">
            <a href="/#/register" style="color: var(--colorRed)">立即注册</a>
          </div>
        </el-form>
      </div>
    </div>
  </div>
</template>
  
<script>
import request from '@/utils/request'

export default {


  name: "Login",
  data() {
    return {
      loginForm: {},
      activeToggle: 'phone',  // 小坑：变量名不能跟方法名一样
      second: 0,
      rules: {
        account: [
          { required: true, message: '请输入用户名/手机号/邮箱', trigger: 'blur' },
          { min: 3, max: 18, message: '长度在3-18个字符之间', trigger: 'blur' }
        ],
        password: [
          { required: true, message: '请输入密码', trigger: 'blur' },
          { min: 3, max: 18, message: '长度在3-18个字符之间', trigger: 'blur' }
        ],
      }
    }
  },
  methods: {
    toggle(active) {
      this.activeToggle = active
    },
    sendSMS() {
      this.second = 30
      let interval = setInterval(() => {  // 注意：这个方法会一直干到死
        if (this.second > 0) {
          this.second--
        } else {
          clearInterval(interval)   // 干到 second = 0 为止
        }
      }, 1000)
    },
    login() {
        this.$refs['form'].validate(async (valid) => {
        if (valid) {
          let res = await request.post('/user/login', this.loginForm)
          if (res) {
           // console.log(res.data)
           // 存数据
           this.$store.commit('setLoginUser', res.data)
           this.$router.push('/') // 跳转到主页
           // 取数据
           //console.log(this.$store.state.loginUser)
          } else {
            return false;
          }
        }
      });
    }
  }
}
</script>
  
<style scoped>
.form-toggle {
  margin: 20px 0;
  text-align: center
}

.form-toggle b {
  font-size: 20px;
  cursor: pointer;
}

.active {
  color: var(--colorRed);
}
</style>