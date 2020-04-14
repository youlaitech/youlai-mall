import request from '@/utils/request'

export function fileUpload(data) {
  return request({
    url: '/fms/files',
    method: 'post',
    data:data
  })
}

export function fileDelete(url) {
  return request({
    url: '/fms/files',
    method: 'delete',
    params: {url: url},
    headers: {
      'showLoading': false  // 不加载Loading
    }
  })
}
