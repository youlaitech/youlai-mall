import request from '@/utils/request'

export function skuPageList(page, limit, queryParams) {
  return request({
<<<<<<< HEAD
    url: '/pms/skus/page/' + page + "/limit/" + limit,
=======
    url: '/pms/sku/page/' + page + "/limit/" + limit,
>>>>>>> 3d26641c4a5a111308766367225743ff672555c3
    method: 'get',
    params: queryParams
  });
}

export function skuAdd(data) {
  return request({
<<<<<<< HEAD
    url: '/pms/skus',
=======
    url: '/pms/sku',
>>>>>>> 3d26641c4a5a111308766367225743ff672555c3
    method: 'post',
    data: data
  });
}

export function skuDetail(id) {
  return request({
<<<<<<< HEAD
    url: '/pms/skus/' + id,
=======
    url: '/pms/sku/' + id,
>>>>>>> 3d26641c4a5a111308766367225743ff672555c3
    method: 'get'
  })
}

export function skuUpdate(id, data) {
  return request({
<<<<<<< HEAD
    url: '/pms/skus/' + id,
=======
    url: '/pms/sku/' + id,
>>>>>>> 3d26641c4a5a111308766367225743ff672555c3
    method: 'put',
    data: data
  })
}

export function skuDelete(ids) {
  return request({
<<<<<<< HEAD
    url: '/pms/skus',
=======
    url: '/pms/sku',
>>>>>>> 3d26641c4a5a111308766367225743ff672555c3
    method: 'delete',
    params: ids
  })
}
