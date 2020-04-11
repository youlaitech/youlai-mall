<template>
  <div class="dashboard-container">
    <el-card>
      <span>
        <a href="https://github.com/TyCoding/cloud-template/" target="_blank">
          <img src="https://img.shields.io/badge/SpringCloudTemplate-分布式项目脚手架-green.svg" alt="Build Status">
        </a>
        <img src="https://img.shields.io/badge/Spring%20Boot-2.1.5.RELEASE-yellowgreen.svg" alt="Downloads">
        <img src="https://img.shields.io/badge/Spring%20Cloud-Greenwich.RELEASE-blue.svg" alt="Coverage Status">
      </span>
      <el-collapse v-model="activeNames">
        <el-collapse-item title="完整的微服务架构" name="1">
          <div>基于 Netflix Zuul</div>
          <div>基于 Spring Security OAuth2</div>
          <div>基于 Spring Boot 2.1.12.RELEASE</div>
          <div>基于 Spring Cloud Greenwich.SR5</div>
        </el-collapse-item>
        <el-collapse-item title="极简的项目设计" name="2">
          <div>一套极简的Spring Cloud微服务项目模板</div>
          <div>没有复杂的代码封装，开箱即用，方便扩展</div>
        </el-collapse-item>
        <el-collapse-item title="前后端分离式开发" name="3">
          <div>基于 vue-admin-template</div>
          <div>使用Vue框架，快速入门前后端分离式开发模式</div>
        </el-collapse-item>
        <el-collapse-item title="完整的开发文档" name="4">
          <div>提供该项目开发过程的完整文档</div>
          <div>手把手教你入门Spring Cloud + Vue分布式微服务项目开发</div>
        </el-collapse-item>
      </el-collapse>
    </el-card>
  </div>
</template>

<script>

  export default {
    name: 'Dashboard',
    data() {
      return {
        activeNames: ['1', '2', '3', '4', '5']
      }
    }
  }
</script>

<style lang="scss">
  .dashboard-container {
    text-align: center;
    padding: 20px;
  }

  .el-collapse-item__header {
    display: block !important;
  }
</style>
