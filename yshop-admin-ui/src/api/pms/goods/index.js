import request from '@/utils/request'

export function goodsPageList(page, limit, queryParams) {
  return request({
    url: '/pms/goods/page/' + page + "/limit/" + limit,
    method: 'get',
    params: queryParams
  });
}

export function goodsAdd(data) {
  return request({
    url: '/pms/goods',
    method: 'post',
    data: data
  });
}

export function goodsDetail(id) {
  return request({
    url: '/pms/goods/' + id,
    method: 'get'
  })
}

export function goodsUpdate(id, data) {
  return request({
    url: '/pms/goods/' + id,
    method: 'put',
    data: data
  })
}

export function goodsDelete(ids) {
  return request({
    url: '/pms/goods/attributes',
    method: 'delete',
    params: ids
  })
}
