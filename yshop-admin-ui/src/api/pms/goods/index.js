import request from '@/utils/request'

export function goodsPageList(pageNum, pageSize, queryParams) {
  return request({
    url: '/pms/goods/pageNum/' + pageNum + "/pageSize/" + pageSize,
    method: 'get',
    params: queryParams
  });
}

export function publishGoods(data) {
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

export function editGoods(id, data) {
  return request({
    url: '/pms/goods/' + id,
    method: 'put',
    data: data
  })
}

export function deleteGoods(ids) {
  return request({
    url: '/pms/goods/attributes',
    method: 'delete',
    params: ids
  })
}
