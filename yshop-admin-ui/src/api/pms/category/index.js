import request from '@/utils/request'

export function categoryPageList(page, limit, queryParams) {
  return request({
    url: '/pms/categories/page/' + page + "/limit/" + limit,
    method: 'get',
    params: queryParams
  });
}

export function categoryAdd(data) {
  return request({
    url: '/pms/categories',
    method: 'post',
    data: data
  });
}

export function categoryDetail(id) {
  return request({
    url: '/pms/categories/' + id,
    method: 'get'
  });
}

export function categoryUpdate(id, data) {
  return request({
    url: '/pms/categories/' + id,
    method: 'put',
    data: data
  });
}

export function categoryDelete(ids) {
  return request({
    url: '/pms/categories',
    method: 'delete',
    params: ids
  })
}

export function categoryList() {
  return request({
    url: '/pms/categories',
    method: 'get'
  });
}
