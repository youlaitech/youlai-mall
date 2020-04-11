import request from '@/utils/request'

export function page(pageNum, pageSize, queryParams) {
  return request({
    url: '/goods/pageNum/' + pageNum + "/pageSize/" + pageSize,
    method: 'get',
    params: queryParams
  });
}

export function putPublishStatus(params) {
  return request({
    url:'/goods/publishStatus',
    method:'put',
    params:params
  })
}

export function putNewStatus(params) {
  return request({
    url:'/goods/newStatus',
    method:'put',
    params:params
  })
}

export function putRecommendStatus(params) {
  return request({
    url:'/goods/recommendStatus',
    method:'put',
    params:params
  })
}
export function putDeleteStatus(params) {
  return request({
    url:'/goods/deleteStatus',
    method:'put',
    params:params
  })
}
export function delObj(ids) {
  return request({
    url: '/goods/' + ids,
    method: 'delete'
  })
}

export function fetchSkuStockList(goodsId,params) {
  return request({
    url:'/goods/sku/'+goodsId,
    method:'get',
    params:params
  })
}
export function putSkuStockList(goodsId,data) {
  return request({
    url:'/goods/sku/'+goodsId,
    method:'put',
    data:data
  })
}
