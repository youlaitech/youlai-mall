import request from '@/utils/request'

export function page(pageNum, pageSize, queryParams) {
  return request({
    url: '/orders/returnReason/pageNum/' + pageNum + "/pageSize/" + pageSize,
    method: 'get',
    params: queryParams
  });
}

export function postObj(obj) {
  return request({
    url: '/orders/returnReason',
    method: 'post',
    data: obj
  });
}

export function getObj(id) {
  return request({
    url: '/orders/returnReason/' + id,
    method: 'get'
  })
}

export function putObj(id, obj) {
  return request({
    url: '/orders/returnReason/' + id,
    method: 'put',
    data: obj
  })
}

export function delObj(ids) {
  return request({
    url: '/orders/returnReason/' + ids,
    method: 'delete'
  })
}


export function updateStatus(id, status) {
  return request({
    url: '/orders/returnReason/id/' + id + '/status/' + status,
    method: 'put'
  })
}



