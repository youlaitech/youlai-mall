import request from '@/utils/request'

export function postObj(obj) {

  return request({
    url:'/goods/add',
    method:'post',
    data:obj
  })
}
export function updateGoods(id,obj) {
  return request({
    url:'/goods/update/'+id,
    method:'post',
    data:obj
  })
}
export function getGoods(id) {
  return request({
    url:'/goods/updateInfo/'+id,
    method:'get'
  })
}

