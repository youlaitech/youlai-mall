import request from '@/utils/request'

export function page(pageNum, pageSize, queryParams) {
  return request({
    url: '/goods/attributes/type/pageNum/' + pageNum + "/pageSize/" + pageSize,
    method: 'get',
    params: queryParams
  });
}

export function postObj(obj) {
  return request({
    url: '/goods/attributes/type',
    method: 'post',
    data: obj
  });
}

export function getObj(id) {
  return request({
    url: '/goods/attributes/type/' + id,
    method: 'get'
  })
}

export function putObj(id, obj) {
  return request({
    url: '/goods/attributes/type/' + id,
    method: 'put',
    data: obj
  })
}

export function delObj(ids) {
  return request({
    url: '/goods/attributes/type/' + ids,
    method: 'delete'
  })
}

export function list(queryParams) {
  return request({
    url: '/goods/attributes/type/list',
    method: 'get',
    params: queryParams
  });
}

