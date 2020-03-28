import request from '@/utils/request'

export function page(pageNum, pageSize, queryParams) {
  return request({
    url: '/goods/pageNum/' + pageNum + "/pageSize/" + pageSize,
    method: 'get',
    params: queryParams
  });
}

export function updatePublishStatus(params) {
  return request({
    url:'/goods/update/publishStatus',
    method:'put',
    params:params
  })
}

export function updateNewStatus(params) {
  return request({
    url:'/goods/update/newStatus',
    method:'put',
    params:params
  })
}

export function updateRecommendStatus(params) {
  return request({
    url:'/goods/update/recommendStatus',
    method:'put',
    params:params
  })
}
export function updateDeleteStatus(params) {
  return request({
    url:'/goods/update/deleteStatus',
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
export function updateSkuStockList(goodsId,data) {
  return request({
    url:'/goods/sku/update/'+goodsId,
    method:'put',
    data:data
  })
}
