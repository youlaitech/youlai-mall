import request from '@/utils/request'

export function page(pageNum, pageSize, queryParams) {
  return request({
    url: '/order/setting/pageNum/' + pageNum + "/pageSize/" + pageSize,
    method: 'get',
    params: queryParams
  });
}

export function getObj() {
  return request({
    url: '/order/setting' ,
    method: 'get'
  })
}

export function postObj(obj) {
  return request({
    url: '/order/setting',
    method: 'post',
    data: obj
  });
}


export function putObj(id, obj) {
  return request({
    url: '/order/setting/' + id,
    method: 'put',
    data: obj
  })
}

export function delObj(ids) {
  return request({
    url: '/order/setting/' + ids,
    method: 'delete'
  })
}


export function list(queryParams) {
  return request({
    url: '/order/setting/list',
    method: 'get',
    params: queryParams
  });
}
