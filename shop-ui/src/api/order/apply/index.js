import request from '@/utils/request'

export function page(pageNum, pageSize, queryParams) {
  return request({
    url: '/orders/returnApply/pageNum/' + pageNum + "/pageSize/" + pageSize,
    method: 'get',
    params: queryParams
  });
}

export function postObj(obj) {
  return request({
    url: '/orders/returnApply',
    method: 'post',
    data: obj
  });
}

export function getObj(id) {
  return request({
    url: '/orders/returnApply/' + id,
    method: 'get'
  })
}

export function putObj(id, obj) {
  return request({
    url: '/orders/returnApply/' + id,
    method: 'put',
    data: obj
  })
}

export function delObj(ids) {
  return request({
    url: '/orders/returnApply/' + ids,
    method: 'delete'
  })
}


export function updateStatus(id, obj) {
  return request({
    url: '/orders/returnApply/' + id,
    method: 'put',
    data: obj
  })
}



