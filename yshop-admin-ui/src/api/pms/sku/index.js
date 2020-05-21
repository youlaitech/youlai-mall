import request from '@/utils/request'

export function skuPageList(page, limit, queryParams) {
  return request({
    url: '/pms/sku/page/' + page + "/limit/" + limit,
    method: 'get',
    params: queryParams
  });
}

export function skuAdd(data) {
  return request({
    url: '/pms/sku',
    method: 'post',
    data: data
  });
}

export function skuDetail(id) {
  return request({
    url: '/pms/sku/' + id,
    method: 'get'
  })
}

export function skuUpdate(id, data) {
  return request({
    url: '/pms/sku/' + id,
    method: 'put',
    data: data
  })
}

export function skuDelete(ids) {
  return request({
    url: '/pms/sku',
    method: 'delete',
    params: ids
  })
}
