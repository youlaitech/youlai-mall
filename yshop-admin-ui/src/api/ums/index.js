import request from '@/utils/request'

export function getRouters() {
  return request({
    url: '/routers',
    method: 'get'
  })
}

export function login(data) {
  return request({
    url: '/auth/oauth/token',
    method: 'post',
    params: {
      username: data.username,
      password: data.password,
      grant_type: 'password'
    },
    headers: {
      'showLoading': false  // 不加载Loading
    }
  })
}

export function getInfo(token) {
  return request({
    url: '/auth/user/current',
    method: 'get',
    params: {token},
    headers: {
      'showLoading': false  // 不加载Loading
    }
  })
}

export function logout() {
  return request({
    url: '/auth/user/logout',
    method: 'delete',
    headers: {
      'showLoading': false  // 不加载Loading
    }
  })
}

