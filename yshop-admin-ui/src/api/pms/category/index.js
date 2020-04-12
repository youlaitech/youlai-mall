import request from '@/utils/request'


export function categoryAdd(data) {
  return request({
    url: '/pms/categories',
    method: 'post',
    data: data
  });
}

export function categoryDetail(id) {
  return request({
    url: '/pms/categories/' + id,
    method: 'get'
  });
}

export function categoryUpdate(id, data) {
  return request({
    url: '/pms/categories/' + id,
    method: 'put',
    data: data
  });
}

export function categoryDelete(ids) {
  return request({
    url: '/pms/categories',
    method: 'delete',
    params: ids
  })
}

export function categoryList(queryParams) {
  return request({
    url: '/pms/categories',
    method: 'get',
    params: queryParams
  });
}

export function categoryCascadeList() {
  return request({
    url: '/pms/categories/cascade',
    method: 'get'
  })
}

export function categoryFirstLevelList() {
  return request({
    url: '/pms/categories/firstLevel',
    method: 'get'
  })
}

export function categoryIsShowUpdate(id, is_show) {
  return request({
    url: '/pms/categories/id/' + id + '/is_show/' + is_show,
    method: 'put'
  })
}



