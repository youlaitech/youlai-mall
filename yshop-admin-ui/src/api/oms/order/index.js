import request from '@/utils/request'

export function orderPageList(page, limit, queryParams) {
  return request({
    url: '/oms/orders/page/' + page + "/limit/" + limit,
    method: 'get',
    params: queryParams
  });
}

export function orderAdd(data) {
  return request({
    url: '/oms/orders',
    method: 'post',
    data: data
  });
}

export function orderDetail(id) {
  return request({
    url: '/oms/orders/' + id,
    method: 'get'
  })
}

export function orderUpdate(id, data) {
  return request({
    url: '/oms/orders/' + id,
    method: 'put',
    data: data
  })
}

export function orderDelete(ids) {
  return request({
    url: '/oms/orders',
    method: 'delete',
    params: ids
  })
}

export function orderDeliver(id, data) {
  return request({
    url: '/oms/orders/' + id+'/deliver',
    method: 'put',
    data: data
  })
}

