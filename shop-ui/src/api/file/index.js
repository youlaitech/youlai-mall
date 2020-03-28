import request from '@/utils/request'

export function uploadFile(obj) {
  return request({
    url: '/files',
    method: 'post',
    data: obj,
    headers: {
      'showLoading': false  // 不加载Loading
    }
  });
}

export function deleteFile(filePath) {
  return request({
    url: '/files',
    method: 'delete',
    data: filePath,
    headers: {
      'showLoading': false  // 不加载Loading
    }
  })
}
