import request from '@/utils/request'

export function skuPageList(page, limit, queryParams) {
  return request({
    url: '/sms/seckill/skus/page/' + page + "/limit/" + limit,
    method: 'get',
    params: queryParams
  });
}

export function skuAdd(data) {
  return request({
    url: '/sms/seckill/skus',
    method: 'post',
    data: data
  });
}

export function skuDetail(id) {
  return request({
    url: '/sms/seckill/skus/' + id,
    method: 'get'
  });
}

export function skuUpdate(id, data) {
  return request({
    url: '/sms/seckill/skus/' + id,
    method: 'put',
    data: data
  });
}

export function skuDelete(ids) {
  return request({
    url: '/sms/seckill/skus',
    method: 'delete',
    params: {ids:ids}
  })
}

export function skuList(params) {
  return request({
    url: '/sms/seckill/skus',
    method: 'get',
    params:params
  });
}

export function skuStatusUpdate(id,status) {
  return request({
    url: '/sms/seckill/skus/id/'+id+'/status/'+status,
    method: 'put'
  });
}

