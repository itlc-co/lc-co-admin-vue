import request from '@/utils/request'

// 查询缓存详细
export function getCache() {
  return request({
    url: '/monitor/redis',
    method: 'get'
  })
}

// 查询缓存模块名称列表
export function listCacheModules() {
  return request({
    url: '/monitor/redis/getModules',
    method: 'get'
  })
}

// 查询缓存名称列表
export function listCacheNames(cacheModule) {
  return request({
    url: '/monitor/redis/getNames/' + cacheModule,
    method: 'get'
  })
}

// 查询缓存键名列表
export function listCacheKeys(cacheName,cacheModule) {
  const params = {
    cacheName,
    cacheModule
  }
  return request({
    url: '/monitor/redis/getKeys/',
    method: 'get',
    params
  })
}

// 查询缓存内容
export function getCacheValue(cache) {
  const params = {
    cacheModule:cache.module,
    cacheName:cache.name,
    cacheKey:cache.key
  }
  return request({
    url: '/monitor/redis/getValue/',
    method: 'get',
    params
  })
}

// 清理指定模块名称缓存
export function clearCacheModule(cache) {
  const data = {
    cacheModule:cache.module
  }
  return request({
    url: '/monitor/redis/clearCache',
    method: 'delete',
    data
  })
}

// 清理指定名称缓存
export function clearCacheName(cache) {
  const data = {
    cacheModule:cache.module,
    cacheName:cache.name
  }
  return request({
    url: '/monitor/redis/clearCache',
    method: 'delete',
    data
  })
}

// 清理指定键名缓存
export function clearCacheKey(cache) {
  const data = {
    cacheModule:cache.module,
    cacheName:cache.name,
    cacheKey:cache.key
  }
  return request({
    url: '/monitor/redis/clearCache',
    method: 'delete',
    data
  })
}

// 清理全部缓存
export function clearCacheAll() {
  return request({
    url: '/monitor/redis/clearCache',
    method: 'delete',
    data:{}
  })
}
