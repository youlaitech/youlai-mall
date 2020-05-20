import request from '@/utils/request'

export function spuPageList(page, limit, queryParams) {
  return request({
<<<<<<< HEAD
    url: '/pms/spus/page/' + page + "/limit/" + limit,
=======
    url: '/pms/spu/page/' + page + "/limit/" + limit,
>>>>>>> 3d26641c4a5a111308766367225743ff672555c3
    method: 'get',
    params: queryParams
  });
}

export function spuAdd(data) {
  return request({
<<<<<<< HEAD
    url: '/pms/spus',
=======
    url: '/pms/spu',
>>>>>>> 3d26641c4a5a111308766367225743ff672555c3
    method: 'post',
    data: data
  });
}

export function spuDetail(id) {
  return request({
<<<<<<< HEAD
    url: '/pms/spus/' + id,
=======
    url: '/pms/spu/' + id,
>>>>>>> 3d26641c4a5a111308766367225743ff672555c3
    method: 'get'
  })
}

export function spuUpdate(id, data) {
  return request({
<<<<<<< HEAD
    url: '/pms/spus/' + id,
=======
    url: '/pms/spu/' + id,
>>>>>>> 3d26641c4a5a111308766367225743ff672555c3
    method: 'put',
    data: data
  })
}

export function spuDelete(ids) {
  return request({
<<<<<<< HEAD
    url: '/pms/spus',
=======
    url: '/pms/spu',
>>>>>>> 3d26641c4a5a111308766367225743ff672555c3
    method: 'delete',
    params: {ids:ids}
  })
}
