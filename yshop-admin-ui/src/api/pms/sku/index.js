import request from '@/utils/request'

export function pageList(queryParams) {
  return request({
    url: '/pms/sku',
    method: 'get',
    params: queryParams
  });
}

export function save(data) {
  return request({
    url: '/pms/sku',
    method: 'post',
    data: data
  });
}

export function detail(id) {
  return request({
    url: '/pms/sku/' + id,
    method: 'get'
  })
}

export function update(id, data) {
  return request({
    url: '/pms/sku/' + id,
    method: 'put',
    data: data
  })
}

export function del(ids) {
  return request({
    url: '/pms/sku',
    method: 'delete',
    params: ids
  })
}
