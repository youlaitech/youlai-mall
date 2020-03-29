import request from '@/utils/request'

export function page(pageNum, pageSize, queryParams) {
  return request({
    url: '/marketing/seckillGoods/pageNum/' + pageNum + "/pageSize/" + pageSize,
    method: 'get',
    params: queryParams
  });
}


export function list(seckillId, seckillSessionId) {
  return request({
    url: '/marketing/seckillGoods/seckillId/' + seckillId + "/seckillSessionId/" + seckillSessionId,
    method: 'get',
  });
}


export function postObj(obj) {
  return request({
    url: '/marketing/seckillGoods',
    method: 'post',
    data: obj
  });
}

export function getObj(id) {
  return request({
    url: '/marketing/seckillGoods/' + id,
    method: 'get'
  })
}

export function putObj(id, obj) {
  return request({
    url: '/marketing/seckillGoods/' + id,
    method: 'put',
    data: obj
  })
}

export function delObj(ids) {
  return request({
    url: '/marketing/seckillGoods/' + ids,
    method: 'delete'
  })
}




