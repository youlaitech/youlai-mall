import request from '@/utils/request'

export function addressPageList(page, limit, queryParams) {
  return request({
    url: '/ums/addresses/page/' + page + "/limit/" + limit,
    method: 'get',
    params: queryParams
  });
}

export function addressAdd(data) {
  return request({
    url: '/ums/addresses',
    method: 'post',
    data: data
  });
}

export function addressDetail(id) {
  return request({
    url: '/ums/addresses/' + id,
    method: 'get'
  });
}

export function addressUpdate(id, data) {
  return request({
    url: '/ums/addresses/' + id,
    method: 'put',
    data: data
  });
}

export function addressDelete(ids) {
  return request({
    url: '/ums/addresses',
    method: 'delete',
    params: {ids:ids}
  })
}

export function addressList() {
  return request({
    url: '/ums/addresses',
    method: 'get'
  });
}

export function addressStatusUpdate(id,status) {
  return request({
    url: '/ums/addresses/id/{id}/status/{status}',
    method: 'put'
  });
}

