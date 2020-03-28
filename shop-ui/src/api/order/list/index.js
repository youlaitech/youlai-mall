import request from '@/utils/request'

export function fetchList(pageNum, pageSize, queryParams) {
  return request({
    url: '/orders/pageNum/' + pageNum + "/pageSize/" + pageSize,
    method: 'get',
    params: queryParams
  });
}

export function closeOrder(params) {
  return request({
    url:'/orders/close',
    method:'put',
    params:params
  })
}

export function deleteOrder(params) {
  return request({
    url:'/orders',
    method:'delete',
    params:params
  })
}
export function deliveryOrder(data) {
  return request({
    url:'/orders/update/delivery',
    method:'post',
    data:data
  });
}

export function getOrderDetail(id) {
  return request({
    url:'/orders/'+id,
    method:'get'
  });
}

export function putReceiverInfo(obj) {
  return request({
    url:'/orders/receiverInfo',
    method:'put',
    data:obj
  });
}

export function putMoneyInfo(obj) {
  return request({
    url:'/orders/moneyInfo',
    method:'put',
    data:obj
  });
}

export function putOrderNote(params) {
  return request({
    url:'/orders/note',
    method:'put',
    params:params
  })
}
