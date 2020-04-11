import request from '@/utils/request'


export function list(queryParams) {
  return request({
    url: '/orders/companyAddress/list',
    method: 'get',
    params: queryParams
  });
}


export function getObj(id) {
  return request({
    url: '/orders/companyAddress/' + id,
    method: 'get'
  })
}




