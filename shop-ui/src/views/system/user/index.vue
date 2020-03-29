<template>
  <div class="app-container">
    <!-- 搜索表单::begin -->
    <el-form :inline="true" ref="queryForm" :model="queryParams">
      <el-form-item>
        <el-button type="primary" @click="handleAdd">新增</el-button>
        <el-button type="success" @click="handleUpdate" :disabled="single">修改</el-button>
        <el-button type="danger" @click="handleDelete" :disabled="multiple">删除</el-button>
      </el-form-item>
      <el-form-item label="用户昵称" prop="nickName">
        <el-input v-model="queryParams.nickName" placeholder="用户昵称"></el-input>
      </el-form-item>
      <el-form-item label="性别" prop="sex">
        <el-select v-model="queryParams.sex" style="width: 300px;">
          <el-option label="男" value="0"></el-option>
          <el-option label="女" value="1"></el-option>
        </el-select>
      </el-form-item>

      <el-form-item>
        <el-button type="primary" icon="el-icon-search" @click="handleQuery">查询</el-button>
        <el-button icon="el-icon-refresh" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>
    <!-- 搜索表单::end -->

    <!-- 数据表格::start -->
    <el-table
      id="dataTable"
      ref="multipleTable"
      :data="tableData"
      @selection-change="handleSelectionChange"
      @row-click="rowClick"
      border>
      <el-table-column
        type="selection"
        min-width="5%">
      </el-table-column>
      <el-table-column
        prop="userName"
        label="用户名"
        min-width="11%">
      </el-table-column>
      <el-table-column
        prop="nickName"
        label="用户昵称"
        min-width="11%">
      </el-table-column>
      <el-table-column
        label="照片"
        min-width="11%">
        <template slot-scope="scope">
          <el-popover
            placement="right"
            title=""
            trigger="hover">
            <img :src="scope.row.avatarUrl"/>
            <img slot="reference" :src="scope.row.avatarUrl" :alt="scope.row.avatarUrl"
                 style="max-height: 60px;max-width: 60px">
          </el-popover>
        </template>
      </el-table-column>
      <el-table-column
        prop="sex"
        label="性别"
        min-width="11%"
        :formatter="formatSex">
      </el-table-column>
      <el-table-column
        prop="tel"
        label="联系电话"
        min-width="11%">
      </el-table-column>
      <el-table-column
        prop="email"
        label="邮箱"
        min-width="11%">
      </el-table-column>
      <el-table-column
        prop="createTime"
        label="创建时间"
        min-width="11%">
      </el-table-column>
      <el-table-column
        prop="updateTime"
        label="更新时间"
        min-width="11%">
      </el-table-column>
    </el-table>
    <!-- 数据表格::end-->

    <!-- 分页工具条::start -->
    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="pageNum"
      :limit.sync="pageSize"
      @pagination="handleQuery"/>
    <!-- 分页工具条::end -->

    <!-- 表单弹窗::start -->
    <el-dialog
      :title="title"
      :visible.sync="open"
      @close="cancel"
      center
      width="40%">
      <el-form id="dataForm" label-width="120px" :model="form" :rules="rules" ref="form">
        <el-form-item label="用户名" prop="userName">
          <el-input v-model="form.userName" auto-complete="off" :disabled="userNameDisabled"></el-input>
        </el-form-item>
        <el-form-item label="用户昵称" prop="nickName">
          <el-input v-model="form.nickName" auto-complete="off"></el-input>
        </el-form-item>
        <el-form-item label="性别" prop="sex">
          <el-select v-model="form.sex" style="width: 300px;">
            <el-option label="男" :value="0"></el-option>
            <el-option label="女" :value="1"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="联系电话" prop="tel">
          <el-input v-model="form.tel" auto-complete="off"></el-input>
        </el-form-item>
        <el-form-item label="邮箱" prop="email">
          <el-input v-model="form.email" auto-complete="off"></el-input>
        </el-form-item>
        <el-form-item label="照片" prop="avatarUrl" label-width="120px">
          <el-upload
            ref="upload"
            :file-list="avatarList"
            :action="uploadAction"
            :show-file-list="true"
            :before-upload="handleBeforeUpload"
            :on-preview="handlePreview"
            :on-success="handleUploadSuccess"
            :on-remove="handleRemove"
            list-type="picture-card"
            :auto-upload="true"
            :limit="1"
            :headers="fileHeaders">
            <i class="el-icon-plus"></i>
          </el-upload>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="cancel">取 消</el-button>
        <el-button type="primary" @click="handleSubmit">确 定</el-button>
      </div>
    </el-dialog>
    <!-- 表单弹窗::end -->
    <el-dialog :visible.sync="dialogImageVisible">
      <img width="60%" height="60%" :src="dialogImageUrl" alt="">
    </el-dialog>
  </div>
</template>

<script>
  import {page, getObj, postObj, putObj, delObj} from '@/api/system/user'
  import {getToken} from '@/utils/auth'
  import {deleteFile} from '@/api/file'

  export default {
    name: "index",
    data() {
      return {
        // 选中数组
        ids: [],
        // 限制一行选中按钮开启的控制【修改按钮】默认true不开启按钮
        single: true,
        // 限制一行或多行选中按钮就开启的控制 【删除按钮】 默认true不开启按钮
        multiple: true,
        pageNum: 1,
        pageSize: 10,
        queryParams: {
          userName: undefined,
          sex: undefined
        },
        tableData: [],
        total: 0,
        title: "",
        open: false,
        form: {
          userId: undefined,
          userName: undefined,
          nickName: undefined,
          tel: undefined,
          email: undefined,
          remark: undefined,
          avatarUrl: undefined
        },
        rules: {
          nickName: [{
            required: true, message: '请输入用户名称', trigger: 'blur'
          }],
          tel: [{
            required: true, message: '请填写联系电话', trigger: 'blur'
          }]
        },
        uploadAction: this.uploadAction,
        fileHeaders: {authorization: getToken()},
        dialogImageVisible: false,
        dialogImageUrl: undefined,
        avatarList: [],// 回显图片
        userNameDisabled: true
      }
    },
    methods: {
      handleQuery() {
        page(this.pageNum,this.pageSize,this.queryParams).then(response => {
          this.tableData = response.data.records
          this.total = response.data.total
        })
      },
      resetQuery() {
        this.pageNum = 1
        this.pageSize = 10
        this.queryParams = {
          nickName: undefined,
          sex: undefined
        }
        this.resetForm("queryForm")
        this.handleQuery()
      },
      formatSex: function (row) {
        return row.sex == 1 ? "女" : "男";
      },
      handleAdd() {
        this.reset()
        this.title = "新增用户"
        this.open = true
        this.userNameDisabled = false
      },
      handleUpdate(row) {
        this.reset();
        const userId = row.userId || this.ids
        getObj(userId).then(response => {
          this.form = response.data
          this.open = true
          this.title = "修改用户"

          const avatarUrl = response.data.avatarUrl
          if (avatarUrl) {
            this.avatarList.push({url: avatarUrl})
          }
        })
      },
      handleSubmit() {
        this.$refs["form"].validate((valid) => {
          if (valid) {
            const id = this.form.userId
            if (id != undefined) {
              putObj(id, this.form).then(() => {
                this.$message.success("修改成功")
                this.open = false
                this.handleQuery()
              })
            } else {
              postObj(this.form).then(() => {
                this.$message.success("新增成功")
                this.open = false
                this.handleQuery()
              })
            }
          }
        })
      },
      handleDelete(row) {
        const ids = row.id || this.ids
        this.$confirm('是否确认删除选中的数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function () {
          return delObj(ids)
        }).then(() => {
          this.$message.success("删除成功")
          this.handleQuery()
        }).catch(() =>
          this.$message.info("已取消删除")
        )
      },
      rowClick(row) {
        this.$refs.multipleTable.toggleRowSelection(row);
      },
      handleSelectionChange(selection) {
        this.ids = selection.map(item => item.userId)
        this.single = selection.length != 1
        this.multiple = !selection.length
      },
      cancel() {
        this.open = false;
        this.reset();
      },
      reset() {
        this.resetForm("form")
        this.form = {
          userId: undefined,
          userName: undefined,
          sex: undefined,
          avatarUrl: undefined,
          tel: undefined,
          email: undefined,
        }
        this.avatarList = []
        this.userNameDisabled = true
      },


      // 上传文件相关
      handleBeforeUpload(file) {
        const isJPG = file.type === 'image/jpeg'
        const isGIF = file.type === 'image/gif'
        const isPNG = file.type === 'image/png'
        const isBMP = file.type === 'image/bmp'
        const isLt2M = file.size / 1024 / 1024 < 2

        if (!isJPG && !isGIF && !isPNG && !isBMP) {
          this.$message.error('上传图片必须是JPG/GIF/PNG/BMP 格式!')
        }
        if (!isLt2M) {
          this.$message.error('上传头像图片大小不能超过 2MB!')
        }
        return (isJPG || isBMP || isGIF || isPNG) && isLt2M
      },
      handleUploadSuccess(response, file, fileList) {
        if (response.code == 0) {
          this.$message.success('图片上传成功')
          this.form.avatarUrl = response.data.filePath
          // 多图则每次上传追加
          // this.form.avatarUrl =this.form.avatarUrl+ ","+response.data.filePath
          this.dialogImageUrl = response.data.filePath
        }
      },
      // 预览放大图片
      handlePreview(file) {
        this.dialogImageVisible = true
        this.dialogImageUrl = file.url
      },
      handleRemove(file, fileList) { //删除图片
        deleteFile(file.response.data.filePath).then(() => {
          this.$message.success("删除图片成功")
        })
      }

    },
    created() {
      this.handleQuery()
    }
  }
</script>

<style scoped>

</style>
