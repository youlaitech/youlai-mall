import request from '@/utils/request'

export function page(pageNum, pageSize, queryParams) {
  return request({
    url: '/goods/categories/pageNum/' + pageNum + "/pageSize/" + pageSize,
    method: 'get',
    params: queryParams
  });
}

export function postObj(obj) {
  return request({
    url: '/goods/categories',
    method: 'post',
    data: obj
  });
}

export function getObj(id) {
  return request({
    url: '/goods/categories/' + id,
    method: 'get'
  })
}

export function putObj(id, obj) {
  return request({
    url: '/goods/categories/' + id,
    method: 'put',
    data: obj
  })
}

export function delObj(ids) {
  return request({
    url: '/goods/categories/' + ids,
    method: 'delete'
  })
}


export function list(queryParams) {
  return request({
    url: '/goods/categories/list',
    method: 'get',
    params: queryParams
  });
}

export function treeSelect(queryParams) {
  return request({
    url: '/goods/categories/treeSelect',
    method: 'get',
    param: queryParams
  })
}

export function updateNavStatus(categoryId, isNav) {
  return request({
    url: '/goods/categories/id/' + categoryId + '/isNav/' + isNav,
    method: 'put'
  })
}

export function updateShowStatus(categoryId, isShow) {
  return request({
    url: '/goods/categories/id/' + categoryId + '/isShow/' + isShow,
    method: 'put'
  })
}


