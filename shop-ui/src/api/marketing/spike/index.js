import request from '@/utils/request'

export function page(pageNum, pageSize, queryParams) {
  return request({
    url: '/marketing/spike/pageNum/' + pageNum + "/pageSize/" + pageSize,
    method: 'get',
    params: queryParams
  });
}

export function postObj(obj) {
  return request({
    url: '/marketing/spike',
    method: 'post',
    data: obj
  });
}

export function getObj(id) {
  return request({
    url: '/marketing/spike/' + id,
    method: 'get'
  })
}

export function putObj(id, obj) {
  return request({
    url: '/marketing/spike/' + id,
    method: 'put',
    data: obj
  })
}

export function delObj(ids) {
  return request({
    url: '/marketing/spike/' + ids,
    method: 'delete'
  })
}


export function updateStatus(id, status) {
  return request({
    url: '/marketing/spike/id/' + id+'/status/'+status,
    method: 'put'
  })
}



