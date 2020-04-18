import request from '@/utils/request'

export function adPageList(page, limit, queryParams) {
  return request({
    url: '/sms/ads/page/' + page + "/limit/" + limit,
    method: 'get',
    params: queryParams
  });
}

export function adAdd(data) {
  return request({
    url: '/sms/ads',
    method: 'post',
    data: data
  });
}

export function adDetail(id) {
  return request({
    url: '/sms/ads/' + id,
    method: 'get'
  });
}

export function adUpdate(id, data) {
  return request({
    url: '/sms/ads/' + id,
    method: 'put',
    data: data
  });
}

export function adDelete(ids) {
  return request({
    url: '/sms/ads',
    method: 'delete',
    params: {ids:ids}
  })
}

export function adList() {
  return request({
    url: '/sms/ads',
    method: 'get'
  });
}

export function adStatusUpdate(id,status) {
  return request({
    url: '/sms/ads/id/{id}/status/{status}',
    method: 'put'
  });
}

