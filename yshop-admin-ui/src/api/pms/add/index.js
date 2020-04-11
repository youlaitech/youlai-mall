import request from '@/utils/request'



export function goodsList(obj) {
  return request({
    url:'/goods/add',
    method:'post',
    data:obj
  })
}
export function putObj(id,obj) {
  return request({
    url:'/goods/'+id,
    method:'put',
    data:obj
  })
}
export function getGoods(id) {
  return request({
    url:'/goods/updateInfo/'+id,
    method:'get'
  })
}

