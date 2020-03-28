import request from '@/utils/request'

export function page(pageNum, pageSize, queryParams) {
  return request({
    url: '/system/users/pageNum/' + pageNum + "/pageSize/" + pageSize,
    method: 'get',
    params: queryParams
  });
}

export function postObj(obj) {
  return request({
    url: '/system/users',
    method: 'post',
    data: obj
  });
}

export function getObj(id) {
  return request({
    url: '/system/users/' + id,
    method: 'get'
  })
}

export function putObj(id, obj) {
  return request({
    url: '/system/users/' + id,
    method: 'put',
    data: obj
  })
}

export function delObj(ids) {
  return request({
    url: '/system/users/' + ids,
    method: 'delete'
  })
}

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

