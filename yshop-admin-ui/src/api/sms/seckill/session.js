import request from '@/utils/request'

export function sessionPageList(page, limit, queryParams) {
  return request({
    url: '/sms/seckill/sessions/page/' + page + "/limit/" + limit,
    method: 'get',
    params: queryParams
  });
}

export function sessionAdd(data) {
  return request({
    url: '/sms/seckill/sessions',
    method: 'post',
    data: data
  });
}

export function sessionDetail(id) {
  return request({
    url: '/sms/seckill/sessions/' + id,
    method: 'get'
  });
}

export function sessionUpdate(id, data) {
  return request({
    url: '/sms/seckill/sessions/' + id,
    method: 'put',
    data: data
  });
}

export function sessionDelete(ids) {
  return request({
    url: '/sms/seckill/sessions',
    method: 'delete',
    params: {ids:ids}
  })
}

export function sessionList() {
  return request({
    url: '/sms/seckill/sessions',
    method: 'get'
  });
}

export function sessionStatusUpdate(id,status) {
  return request({
    url: '/sms/seckill/sessions/id/'+id+'/status/'+status,
    method: 'put'
  });
}

