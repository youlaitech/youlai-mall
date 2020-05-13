import request from '@/utils/request'

export function skuPageList(page, limit, queryParams) {
  return request({
    url: '/pms/skus/page/' + page + "/limit/" + limit,
    method: 'get',
    params: queryParams
  });
}

export function skuAdd(data) {
  return request({
    url: '/pms/skus',
    method: 'post',
    data: data
  });
}

export function skuDetail(id) {
  return request({
    url: '/pms/skus/' + id,
    method: 'get'
  })
}

export function skuUpdate(id, data) {
  return request({
    url: '/pms/skus/' + id,
    method: 'put',
    data: data
  })
}

export function skuDelete(ids) {
  return request({
    url: '/pms/skus',
    method: 'delete',
    params: ids
  })
}
