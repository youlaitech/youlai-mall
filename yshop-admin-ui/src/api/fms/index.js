import request from '@/utils/request'

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
