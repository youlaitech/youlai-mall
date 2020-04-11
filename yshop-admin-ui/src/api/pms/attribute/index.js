import request from '@/utils/request'

export function page(pageNum, pageSize, queryParams) {
  return request({
    url: '/goods/attributes/pageNum/' + pageNum + "/pageSize/" + pageSize,
    method: 'get',
    params: queryParams
  });
}

export function postObj(obj) {
  return request({
    url: '/goods/attributes',
    method: 'post',
    data: obj
  });
}

export function getObj(id) {
  return request({
    url: '/goods/attributes/' + id,
    method: 'get'
  })
}

export function putObj(id, obj) {
  return request({
    url: '/goods/attributes/' + id,
    method: 'put',
    data: obj
  })
}

export function delObj(ids) {
  return request({
    url: '/goods/attributes/' + ids,
    method: 'delete'
  })
}


export function list(queryParams) {
  return request({
    url: '/goods/attributes/list',
    method: 'get',
    params: queryParams
  });
}


export  function cascader() {
  return request({
    url: '/goods/attributes/cascader',
    method: 'get',
  });
}
