import request from '@/utils/request'

export function spuPageList(page, limit, queryParams) {
  return request({
    url: '/pms/spu/page/' + page + "/limit/" + limit,
    method: 'get',
    params: queryParams
  });
}

export function spuAdd(data) {
  return request({
    url: '/pms/spu',
    method: 'post',
    data: data
  });
}

export function spuDetail(id) {
  return request({
    url: '/pms/spu/' + id,
    method: 'get'
  })
}

export function spuUpdate(id, data) {
  return request({
    url: '/pms/spu/' + id,
    method: 'put',
    data: data
  })
}

export function spuDelete(ids) {
  return request({
    url: '/pms/spu',
    method: 'delete',
    params: {ids:ids}
  })
}
