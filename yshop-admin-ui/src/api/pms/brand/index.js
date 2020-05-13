import request from '@/utils/request'

export function brandPageList(page, limit, queryParams) {
  return request({
    url: '/pms/brands/page/' + page + "/limit/" + limit,
    method: 'get',
    params: queryParams
  });
}

export function brandAdd(data) {
  return request({
    url: '/pms/brands',
    method: 'post',
    data: data
  });
}

export function brandDetail(id) {
  return request({
    url: '/pms/brands/' + id,
    method: 'get'
  });
}

export function brandUpdate(id, data) {
  return request({
    url: '/pms/brands/' + id,
    method: 'put',
    data: data
  });
}

export function brandDelete(ids) {
  return request({
    url: '/pms/brands',
    method: 'delete',
    params: {ids:ids}
  })
}

export function brandList() {
  return request({
    url: '/pms/brands',
    method: 'get'
  });
}

export function brandStatusUpdate(id,status) {
  return request({
    url: '/pms/brands/id/{id}/status/{status}',
    method: 'put'
  });
}

