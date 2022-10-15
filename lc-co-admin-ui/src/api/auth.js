import request from '@/utils/request'

// 登录方法
export function auth(username, password, code, uuid) {
  const data = {
    username,
    password
  }
  const params = {
    uuid,
    code
  }
  return request({
    url: '/auth/login',
    headers: {
      isToken: false
    },
    method: 'post',
    data: data,
    params
  })
}

// 注册方法
export function register(data) {
  return request({
    url: '/auth/register',
    headers: {
      isToken: false
    },
    method: 'post',
    data: data
  })
}

// 获取用户详细信息
export function getInfo() {
  return request({
    url: '/auth/getInfo',
    method: 'get'
  })
}

// 退出方法
export function logout() {
  return request({
    url: '/auth/logout',
    method: 'post'
  })
}

// 获取验证码
export function getCodeImg() {
  return request({
    url: '/auth/captcha',
    headers: {
      isToken: false
    },
    method: 'get',
    timeout: 20000
  })
}

// 获取路由
export const getRouters = () => {
  return request({
    url: '/auth/getRouters',
    method: 'get'
  })
}
