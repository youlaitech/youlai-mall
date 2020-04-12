import request from '@/utils/request'
export function fileDelete(filePath) {
  return request({
    url: '/files',
    method: 'delete',
    data: filePath,
    headers: {
      'showLoading': false  // 不加载Loading
    }
  })
}
