import request from '@/utils/request'

export function page(pageNum, pageSize, queryParams) {
  return request({
    url: '/goods/brands/pageNum/' + pageNum + "/pageSize/" + pageSize,
    method: 'get',
    params: queryParams
  });
}
export function postObj(obj) {
  return request({
    url: '/goods/brands',
    method: 'post',
    data: obj
  });
}
export function getObj(id) {
  return request({
    url: '/goods/brands/' + id,
    method: 'get'
  });
}
export function putObj(id, obj) {
  return request({
    url: '/goods/brands/' + id,
    method: 'put',
    data: obj
  });
}
export function delObj(ids) {
  return request({
    url: '/goods/brands/' + ids,
    method: 'delete'
  })
}
export function list() {
  return request({
    url: '/goods/brands/list',
    method: 'get'
  });
}
