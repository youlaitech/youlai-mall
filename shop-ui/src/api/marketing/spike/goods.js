import request from '@/utils/request'

export function page(pageNum, pageSize, queryParams) {
  return request({
    url: '/marketing/spikePeriodGoods/pageNum/' + pageNum + "/pageSize/" + pageSize,
    method: 'get',
    params: queryParams,
    headers: {
      'showLoading': true,
      'loadingTarget':'#goodsTable'
    }
  })
}


export function list(spikeId, spikePeriodId) {
  return request({
    url: '/marketing/spikePeriodGoods/spikeId/' + spikeId + "/spikePeriodId/" + spikePeriodId,
    method: 'get',
    headers: {
      'showLoading': true,
      'loadingTarget':'#spikePeriodGoodsForm'
    }
  })
}


export function postObj(data) {
  return request({
    url: '/marketing/spikePeriodGoods',
    method: 'post',
    data: data,
    headers: {
      'showLoading': true,
      'loadingTarget':'#spikePeriodGoods'
    }
  })
}

export function getObj(id) {
  return request({
    url: '/marketing/spikePeriodGoods/' + id,
    method: 'get'
  })
}

export function putObj(id, data) {
  return request({
    url: '/marketing/spikePeriodGoods/' + id,
    method: 'put',
    data: data,
    headers: {
      'showLoading': true,
      'loadingTarget':'#spikePeriodGoods'
    }
  })
}

export function delObj(ids) {
  return request({
    url: '/marketing/spikePeriodGoods/' + ids,
    method: 'delete'
  })
}




