import request from '@/utils/request'

export function userPageList(page, limit, queryParams) {
  return request({
    url: '/ums/users/page/' + page + "/limit/" + limit,
    method: 'get',
    params: queryParams
  });
}

export function userAdd(data) {
  return request({
    url: '/ums/users',
    method: 'post',
    data: data
  });
}

export function userDetail(id) {
  return request({
    url: '/ums/users/' + id,
    method: 'get'
  });
}

export function userUpdate(id, data) {
  return request({
    url: '/ums/users/' + id,
    method: 'put',
    data: data
  });
}

export function userDelete(ids) {
  return request({
    url: '/ums/users',
    method: 'delete',
    params: {ids:ids}
  })
}

export function userList() {
  return request({
    url: '/ums/users',
    method: 'get'
  });
}

export function userStatusUpdate(id,status) {
  return request({
    url: '/ums/users/id/{id}/status/{status}',
    method: 'put'
  });
}

