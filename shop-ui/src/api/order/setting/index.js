import request from '@/utils/request'

export function page(pageNum, pageSize, queryParams) {
  return request({
    url: '/orders/setting/pageNum/' + pageNum + "/pageSize/" + pageSize,
    method: 'get',
    params: queryParams
  });
}

export function getObj() {
  return request({
    url: '/orders/setting' ,
    method: 'get'
  })
}

export function postObj(obj) {
  return request({
    url: '/orders/setting',
    method: 'post',
    data: obj
  });
}


export function putObj(id, obj) {
  return request({
    url: '/orders/setting/' + id,
    method: 'put',
    data: obj
  })
}

export function delObj(ids) {
  return request({
    url: '/orders/setting/' + ids,
    method: 'delete'
  })
}


export function list(queryParams) {
  return request({
    url: '/orders/setting/list',
    method: 'get',
    params: queryParams
  });
}
