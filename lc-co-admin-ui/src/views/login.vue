<template>
  <div class="login">
    <div class="container" id="container-box">
      <div class="presentation-container">
      </div>
      <div class="login-container">
        <div class="login-container-header">
          <h1 class="login-container-title">
            <span class="login-title-text">admin管理系统</span>
          </h1>
        </div>
        <el-form :model="loginForm"
                 status-icon
                 :rules="loginRules"
                 ref="loginForm"
                 class="login-form"
                 @keyup.enter.native="handleLogin()"
        >
          <el-form-item prop="username">
            <el-input type="test"
                      v-model="loginForm.username"
                      autocomplete="auto"
                      placeholder="用户名"
            >
              <template #prefix>
                <svg-icon icon-class="user" class-name="auth-form-username-icon"/>
              </template>
            </el-input>
          </el-form-item>
          <el-form-item prop="password">
            <el-input type="password"
                      v-model.trim="loginForm.password"
                      autocomplete="auto"
                      placeholder="密码"
                      show-password
                      clearable
            >
              <template #prefix>
                <svg-icon icon-class="password" class-name="auth-form-password-icon"/>
              </template>
            </el-input>
          </el-form-item>
          <el-form-item prop="code">
            <el-input type="test"
                      v-model="loginForm.code"
                      autocomplete="auto"
                      placeholder="验证码"
                      style="padding-right: 40px"
            >
              <template #prefix>
                <svg-icon icon-class="validCode" class-name="auth-form-code-icon"/>
              </template>
            </el-input>
            <div class="login-captcha" style="height: 36px;">
              <img
                :src="codeUrl"
                alt="验证码"
                class="login-captcha-img"
                @click="refreshCaptcha"
              />
            </div>
          </el-form-item>
          <div class="login-item-box">
            <el-checkbox
              v-model="loginForm.rememberMe"
            >
              记住我
            </el-checkbox>
            <a
              class="login-forget-password"
              @click="forgetPassword"
              style="font-size: 16px;"
            >
              忘记密码?
            </a>
          </div>
        </el-form>
        <div class="login-container-bottom">
          <el-button type="primary"
                     @click="handleLogin"
                     style="width: 100%"
                     :loading="loading"
          >
            登录
          </el-button>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { getCodeImg } from '@/api/auth'
import Cookies from 'js-cookie'
import { encrypt, decrypt } from '@/utils/jsencrypt'

export default {
  name: 'Login',
  data() {
    return {
      codeUrl: undefined,
      loginForm: {
        username: 'admin',
        password: '123456',
        rememberMe: false,
        code: '',
        uuid: ''
      },
      loginRules: {
        username: [
          { required: true, trigger: 'blur', message: '请输入您的账号' }
        ],
        password: [
          { required: true, trigger: 'blur', message: '请输入您的密码' }
        ],
        code: [{ required: true, trigger: 'blur', message: '请输入验证码' }]
      },
      loading: false,
      // 验证码开关
      captchaEnabled: true,
      // 注册开关
      register: false,
      redirect: undefined
    }
  },
  watch: {
    $route: {
      handler: function(route) {
        this.redirect = route.query && route.query.redirect
      },
      immediate: true
    }
  },
  created() {
    this.getCode()
    this.getCookie()
  },
  methods: {
    getCode() {
      getCodeImg().then(res => {
        this.captchaEnabled = res.captchaEnabled === undefined ? true : res.captchaEnabled
        if (this.captchaEnabled) {
          this.codeUrl = 'data:image/gif;base64,' + res.src
          this.loginForm.uuid = res.uuid
        }
      })
    },
    getCookie() {
      const username = Cookies.get('username')
      const password = Cookies.get('password')
      const rememberMe = Cookies.get('rememberMe')
      this.loginForm = {
        username: username === undefined ? this.loginForm.username : username,
        password: password === undefined ? this.loginForm.password : decrypt(password),
        rememberMe: rememberMe === undefined ? false : Boolean(rememberMe)
      }
    },
    handleLogin() {
      this.$refs.loginForm.validate(valid => {
        if (valid) {
          this.loading = true
          if (this.loginForm.rememberMe) {
            Cookies.set('username', this.loginForm.username, { expires: 30 })
            Cookies.set('password', encrypt(this.loginForm.password), { expires: 30 })
            Cookies.set('rememberMe', this.loginForm.rememberMe, { expires: 30 })
          } else {
            Cookies.remove('username')
            Cookies.remove('password')
            Cookies.remove('rememberMe')
          }
          this.$store.dispatch('Login', this.loginForm).then(() => {
            this.$router.push({ path: this.redirect || '/' }).catch(() => {
            })
          }).catch(() => {
            this.loading = false
            if (this.captchaEnabled) {
              this.getCode()
            }
          })
        }
      })
    },
    refreshCaptcha() {
      this.getCode()
    },
    forgetPassword() {
      this.$modal.msgError("忘记密码，请联系超级管理员!");
    }
  }
}
</script>

<style rel="stylesheet/scss" lang="scss" scoped>
.login {
  height: 100%;
  overflow: hidden;
  background: linear-gradient(200deg, #8ab6c9, #fbc2a6);
}

.container {
  background: #fff;
  border-radius: 10px 0 10px 0;
  box-shadow: 0 14px 28px rgba(0, 0, 0, .25), 0 10px 10px rgba(0, 0, 0, .22);
  width: 768px;
  position: relative;
  overflow: hidden;
  max-width: 100%;
  max-height: 620px;
  margin: 140px auto;
  display: flex;
}

.presentation-container {
  display: inline-block;
  width: 50%;
  background: url(../assets/images/login-background.jpg) no-repeat;
  background-size: cover;
}

.login-container {
  margin: 80px auto;
  width: 300px;
}

.login-container-header {
  text-align: center;
  margin-bottom: 20px;
}

::v-deep .el-form-item__content {
  display: flex;
  justify-content: space-around;
}

.login-forget-password {
  float: right;
  color: #409eff;
  text-decoration: none;
  cursor: pointer;
}

.login-item-box {
  margin-bottom: 10px;
}


::v-deep .el-input__prefix {
  margin-left: 6px;
}

.login-icon {
  margin-right: 10px;
  color: #1890ff;
}

.login-title-text {
  color: #707070;
}

</style>
