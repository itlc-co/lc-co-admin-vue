<template>
  <div class="app-container">
    <el-row :gutter="12">
      <el-col :span="6">
        <el-card style="height: calc(100vh - 125px)">
          <div slot="header">
            <span>缓存模块列表</span>
            <el-button
              style="float: right; padding: 3px 0"
              type="text"
              icon="el-icon-refresh-right"
              @click="refreshCacheModules()"
            ></el-button>
          </div>
          <el-table
            v-loading="loading"
            :data="cacheModules"
            :height="tableHeight"
            highlight-current-row
            @row-click="getCacheNames"
            style="width: 100%"
          >
            <el-table-column
              label="序号"
              width="38"
              type="index"
            ></el-table-column>

            <el-table-column
              label="缓存模块名称"
              align="center"
              prop="module"
              :show-overflow-tooltip="true"
              :formatter="moduleFormatter"
            ></el-table-column>

            <el-table-column
              label="备注"
              align="center"
              prop="remark"
              :show-overflow-tooltip="true"
            />
            <el-table-column
              label="操作"
              width="35"
              align="center"
              class-name="small-padding fixed-width"
            >
              <template slot-scope="scope">
                <el-button
                  size="mini"
                  type="text"
                  icon="el-icon-delete"
                  @click="handleClearCacheModule(scope.row)"
                ></el-button>
              </template>
            </el-table-column>
          </el-table>
        </el-card>
      </el-col>

      <el-col :span="6">
        <el-card style="height: calc(100vh - 125px)">
          <div slot="header">
            <span>缓存名称列表</span>
            <el-button
              style="float: right; padding: 3px 0"
              type="text"
              icon="el-icon-refresh-right"
              @click="refreshCacheNames()"
            ></el-button>
          </div>
          <el-table
            v-loading="subLoading"
            :data="cacheNames"
            :height="tableHeight"
            highlight-current-row
            @row-click="getCacheKeys"
            style="width: 100%"
          >
            <el-table-column
              label="序号"
              width="38"
              type="index"
            ></el-table-column>

            <el-table-column
              label="缓存名称"
              align="center"
              prop="name"
              :show-overflow-tooltip="true"
              :formatter="nameFormatter"
            ></el-table-column>

            <el-table-column
              label="备注"
              align="center"
              prop="remark"
              :show-overflow-tooltip="true"
            />
            <el-table-column
              label="操作"
              width="35"
              align="center"
              class-name="small-padding fixed-width"
            >
              <template slot-scope="scope">
                <el-button
                  size="mini"
                  type="text"
                  icon="el-icon-delete"
                  @click="handleClearCacheName(scope.row)"
                ></el-button>
              </template>
            </el-table-column>
          </el-table>
        </el-card>
      </el-col>

      <el-col :span="6">
        <el-card style="height: calc(100vh - 125px)">
          <div slot="header">
            <span>缓存键名列表</span>
            <el-button
              style="float: right; padding: 3px 0"
              type="text"
              icon="el-icon-refresh-right"
              @click="refreshCacheKeys()"
            ></el-button>
          </div>
          <el-table
            v-loading="gsonLoading"
            :data="cacheKeys"
            :height="tableHeight"
            highlight-current-row
            @row-click="handleCacheValue"
            style="width: 100%"
          >
            <el-table-column
              label="序号"
              width="38"
              type="index"
            ></el-table-column>
            <el-table-column
              label="缓存键名"
              align="center"
              prop="key"
              :show-overflow-tooltip="true"
              :formatter="keyFormatter"
            >
            </el-table-column>
            <el-table-column
              label="操作"
              width="35"
              align="center"
              class-name="small-padding fixed-width"
            >
              <template slot-scope="scope">
                <el-button
                  size="mini"
                  type="text"
                  icon="el-icon-delete"
                  @click="handleClearCacheKey(scope.row)"
                ></el-button>
              </template>
            </el-table-column>
          </el-table>
        </el-card>
      </el-col>

      <el-col :span="6">
        <el-card :bordered="false" style="height: calc(100vh - 125px)">
          <div slot="header">
            <span>缓存内容</span>
            <el-button
              style="float: right; padding: 3px 0"
              type="text"
              icon="el-icon-refresh-right"
              @click="handleClearCacheAll()"
            >清理全部
            </el-button
            >
          </div>
          <el-form :model="cacheForm">
            <el-row :gutter="32">
              <el-col :offset="1" :span="22">
                <el-form-item label="缓存模块:" prop="cacheModule">
                  <el-input v-model="cacheForm.module" :readOnly="true"/>
                </el-form-item>
              </el-col>
              <el-col :offset="1" :span="22">
                <el-form-item label="缓存名称:" prop="cacheName">
                  <el-input v-model="cacheForm.name" :readOnly="true"/>
                </el-form-item>
              </el-col>
              <el-col :offset="1" :span="22">
                <el-form-item label="缓存键名:" prop="cacheKey">
                  <el-input v-model="cacheForm.key" :readOnly="true"/>
                </el-form-item>
              </el-col>
              <el-col :offset="1" :span="22">
                <el-form-item label="缓存内容:" prop="cacheValue">
                  <el-input
                    v-model="cacheForm.value"
                    type="textarea"
                    :rows="8"
                    :readOnly="true"
                  />
                </el-form-item>
              </el-col>
            </el-row>
          </el-form>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script>
import {
  listCacheNames,
  listCacheKeys,
  getCacheValue,
  clearCacheKey,
  clearCacheAll,
  listCacheModules,
  clearCacheModule,
  clearCacheName
} from '@/api/monitor/cache'

export default {
  name: 'CacheList',
  data() {
    return {
      cacheModules: [],
      cacheNames: [],
      cacheKeys: [],
      cacheForm: {},
      loading: true,
      subLoading: false,
      gsonLoading: false,
      nowCacheModule: '',
      nowCacheName: '',
      nowCacheKey: '',
      tableHeight: window.innerHeight - 200
    }
  },
  created() {
    this.getCacheModules()
  },
  methods: {
    /** 查询缓存名称列表 */
    getCacheModules() {
      this.loading = true
      listCacheModules().then(response => {
        this.cacheModules = response.data
        this.loading = false
      })
    },
    /** 刷新缓存名称列表 */
    refreshCacheModules() {
      this.getCacheModules()
      this.$modal.msgSuccess('刷新缓存模块列表成功')
    },
    /** 清理指定模块缓存 */
    handleClearCacheModule(row) {
      clearCacheModule(row).then(() => {
        this.$modal.msgSuccess('清理缓存模块[' + this.nowCacheModule + ']成功')
        this.getCacheModules()
      })
    },
    /** 清理指定名称缓存 */
    handleClearCacheName(row) {
      row.module = this.nowCacheModule
      clearCacheName(row).then(() => {
        this.$modal.msgSuccess('清理缓存模块[' + this.nowCacheModule + '] 缓存名称[' + this.nowCacheName + ']成功')
        this.getCacheNames()
      })
    },
    /** 查询缓存键名列表 */
    getCacheNames(row) {
      const cacheModule = row !== undefined ? row.module : this.nowCacheModule
      if (cacheModule === '') {
        return
      }
      this.subLoading = true
      this.resetNameKeyValue()
      listCacheNames(cacheModule).then(response => {
        this.cacheNames = response.data
        this.subLoading = false
        this.nowCacheModule = cacheModule
      })
    },
    getCacheKeys(row) {
      const cacheName = row !== undefined ? row.name : this.nowCacheName
      if (cacheName === '') {
        return
      }
      this.gsonLoading = true
      this.resetKeyValue()
      listCacheKeys(cacheName, this.nowCacheModule).then(response => {
        this.cacheKeys = response.data
        this.gsonLoading = false
        this.nowCacheName = cacheName
      })
    },
    // 刷新缓存名称列表
    refreshCacheNames() {
      this.getCacheNames()
      this.$modal.msgSuccess('刷新缓存名称列表成功')
    },
    /** 刷新缓存键名列表 */
    refreshCacheKeys() {
      this.getCacheKeys()
      this.$modal.msgSuccess('刷新缓存键名列表成功')
    },
    /** 清理指定键名缓存 */
    handleClearCacheKey(cache) {
      clearCacheKey(cache).then(() => {
        this.$modal.msgSuccess('清理缓存模块[' + this.nowCacheModule + '] 缓存名称[' + this.nowCacheName + '] 缓存键[' + cache.key + ']成功')
        this.cacheForm = {}
      })
    },
    /** 列表前缀去除 */
    moduleFormatter(row) {
      return row.module.replace('::', '')
    },
    nameFormatter(row) {
      return row.name.replace('::', '')
    },
    /** 键名前缀去除 */
    keyFormatter(cache) {
      const cacheKey = cache.key
      return cacheKey.replace(this.nowCacheModule, '').replace(this.nowCacheName, '')
    },
    /** 查询缓存内容详细 */
    handleCacheValue(cache) {
      this.resetValue()
      getCacheValue(cache).then(response => {
        this.cacheForm = response.data
        this.nowCacheKey = cache.key
      })
    },
    /** 清理全部缓存 */
    handleClearCacheAll() {
      clearCacheAll().then(() => {
        this.$modal.msgSuccess('清理全部缓存成功')
      })
    },
    resetNameKeyValue() {
      this.cacheNames = []
      this.cacheKeys = []
      this.cacheForm = {}
    },
    resetKeyValue() {
      this.cacheKeys = []
      this.cacheForm = {}
    },
    resetValue() {
      this.cacheForm = {}
    }
  }
}
</script>
