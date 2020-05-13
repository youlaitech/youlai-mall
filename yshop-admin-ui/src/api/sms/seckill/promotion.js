import request from '@/utils/request'

export function promotionPageList(page, limit, queryParams) {
  return request({
    url: '/sms/seckill/promotions/page/' + page + "/limit/" + limit,
    method: 'get',
    params: queryParams
  });
}

export function promotionAdd(data) {
  return request({
    url: '/sms/seckill/promotions',
    method: 'post',
    data: data
  });
}

export function promotionDetail(id) {
  return request({
    url: '/sms/seckill/promotions/' + id,
    method: 'get'
  });
}

export function promotionUpdate(id, data) {
  return request({
    url: '/sms/seckill/promotions/' + id,
    method: 'put',
    data: data
  });
}

export function promotionDelete(ids) {
  return request({
    url: '/sms/seckill/promotions',
    method: 'delete',
    params: {ids:ids}
  })
}

export function promotionList() {
  return request({
    url: '/sms/seckill/promotions',
    method: 'get'
  });
}

export function promotionStatusUpdate(id,status) {
  return request({
    url: '/sms/seckill/promotions/id/'+id+'/status/'+status,
    method: 'put'
  });
}

