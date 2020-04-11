<template>
  <div class="app-container">
    <!-- 搜索表单::begin -->
    <el-form :inline="true" ref="queryForm" :model="queryParams">
      <el-form-item>
        <el-button type="primary" @click="handleAdd">新增</el-button>
        <el-button type="success" @click="handleUpdate" :disabled="single">修改</el-button>
        <el-button type="danger" @click="handleDelete" :disabled="multiple">删除</el-button>
      </el-form-item>
      <el-form-item label="" prop="nickName">
        <el-input v-model="queryParams.brandName" placeholder="品牌名称"></el-input>
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
        prop="brandName"
        label="品牌名称"
        min-width="11%">
      </el-table-column>
      <el-table-column
        prop="firstLetter"
        label="品牌首字母"
        min-width="11%">
      </el-table-column>
      <el-table-column
        prop="sort"
        label="排序"
        min-width="11%">
      </el-table-column>
      <el-table-column label="品牌制造商" width="100" align="center">
        <template slot-scope="scope">
          <el-switch
            @change="handleFactoryStatusChange(scope.row.brandId, scope.row)"
            :active-value="1"
            :inactive-value="0"
            v-model="scope.row.factoryStatus">
          </el-switch>
        </template>
      </el-table-column>
      <el-table-column label="是否显示" width="100" align="center">
        <template slot-scope="scope">
          <el-switch
            @change="handleShowStatusChange(scope.row.brandId, scope.row)"
            :active-value="1"
            :inactive-value="0"
            v-model="scope.row.showStatus">
          </el-switch>
        </template>
      </el-table-column>
      <el-table-column label="相关" width="220" align="center" >
        <template slot-scope="scope">
          <span>商品：</span>
          <el-button size="mini" type="text"  @click="" >{{ scope.row.productCount }}</el-button>
          <span>评价：</span>
          <el-button size="mini" type="text"  @click="" >{{ scope.row.productCommentCount }}</el-button>
        </template>
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
        <el-form-item label="品牌名称" prop="brandName">
          <el-input v-model="form.brandName" auto-complete="off" :disabled="brandNameDisabled" ></el-input>
        </el-form-item>
        <el-form-item label="品牌首字母" prop="firstLetter">
          <el-input v-model="form.firstLetter" auto-complete="off"></el-input>
        </el-form-item>
        <el-form-item label="品牌logo" prop="logoUrl" label-width="120px">
        <el-upload
          ref="upload"
          :file-list="logoList"
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
        <el-form-item label="专区大图" prop="bigPicUrl" label-width="120px">
          <el-upload
            ref="upload"
            :file-list="bigPicList"
            :action="uploadAction"
            :show-file-list="true"
            :before-upload="handleBeforeUpload"
            :on-preview="handlePreview"
            :on-success="handlePicUploadSuccess"
            :on-remove="handleRemove"
            list-type="picture-card"
            :auto-upload="true"
            :limit="1"
            :headers="fileHeaders">
            <i class="el-icon-plus"></i>
          </el-upload>
        </el-form-item>
        <el-form-item label="品牌故事" prop="brandStory">
          <el-input v-model="form.brandStory" auto-complete="off"></el-input>
        </el-form-item>
        <el-form-item label="排序" prop="sort">
          <el-input v-model="form.sort" auto-complete="off"></el-input>
        </el-form-item>
        <el-form-item label="是否显示" prop="showStatus">
          <el-radio-group v-model="form.showStatus">
            <el-radio :label="1">是</el-radio>
            <el-radio :label="0">否</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="品牌制造商" prop="factoryStatus">
          <el-radio-group v-model="form.factoryStatus">
            <el-radio :label="1">是</el-radio>
            <el-radio :label="0">否</el-radio>
          </el-radio-group>
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
  import {page, getObj, postObj, putObj, delObj} from '@/api/pms/brand'
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
          brandName: undefined
        },
        tableData: [],
        total: 0,
        title: "",
        open: false,
        form: {
          brandId: undefined,
          name: undefined,
          firstLetter: undefined,
          sort: 0,
          factoryStatus: 0,
          showStatus: 0,
          productCount: 0,
          productCommentCount: 0,
          logoUrl: undefined,
          bigPicUrl: undefined,
          brandStory: undefined,
        },
        rules: {
          brandName: [{
            required: true, message: '请输入品牌名称', trigger: 'blur'
          }],
          sort: [{
            type: 'number', message: '排序必须为数字',trigger: 'blur',
            // 在rules 中验证这个功能的时候，对填写的数值进行判断
            transform (value) {
              return _.toNumber(value)
            }
          }],
        },
        uploadAction: this.uploadAction,
        fileHeaders: {authorization: getToken()},
        dialogImageVisible: false,
        dialogImageUrl: undefined,
        logoList: [],// 回显图片
        bigPicList: [],// 回显图片
        brandNameDisabled: true
      }
    },
    methods: {
      handleQuery() {
        page(this.pageNum,this.pageSize,this.queryParams).then(response => {
          this.tableData = response.data.records;
          this.total = response.data.total
        });
      },
      handleUpdate(row) {
        this.reset();
        const brandId = row.brandId || this.ids;
        getObj(brandId).then(response => {
          this.form = response.data;
          this.open = true;
          this.title = "修改品牌";

          const logoUrl = response.data.logoUrl;
          const bigPicUrl = response.data.bigPicUrl;
          if (logoUrl||bigPicUrl) {
            this.logoList.push({url: logoUrl});
            this.bigPicList.push({url: bigPicUrl})
          }
        });
      },
      resetQuery() {
        this.pageNum = 1;
        this.pageSize = 10;
        this.queryParams = {
          name: undefined
        };
        this.resetForm("queryForm");
        this.handleQuery()
      },
      rowClick(row) {
        // this.$refs.multipleTable.toggleRowSelection(row);
      },
      handleSelectionChange(selection) {
        this.ids = selection.map(item => item.brandId);
        this.single = selection.length != 1
        this.multiple = !selection.length
      },
      handleAdd() {
        this.reset();
        this.title = "新增品牌";
        this.open = true;
        this.brandNameDisabled = false;
        this.form.showStatus = 0;
        this.form.factoryStatus = 0;
      },
      handleSubmit() {
        this.$refs["form"].validate((valid) => {
          if (valid) {
            const id = this.form.brandId;
            if (id != undefined) {
              putObj(id, this.form).then(() => {
                this.$message.success("修改成功")
                this.open = false;
                this.handleQuery()
              })
            } else {
              postObj(this.form).then(() => {
                this.$message.success("新增成功")
                this.open = false;
                this.handleQuery()
              })
            }
          }
        })
      },
      handleDelete(row) {
        const ids = row.brandId || this.ids;
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
      cancel() {
        this.open = false;
        this.reset();
      },
      reset() {
        this.resetForm("form")
        this.form = {
          id: undefined,
          name: undefined,
          firstLetter: undefined,
          sort: undefined,
          factoryStatus: undefined,
          showStatus: undefined
        };
        this.logoList = [];
        this.bigPicList = [];
        this.brandNameDisabled = true
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
          this.form.logoUrl = response.data.filePath
          // 多图则每次上传追加
          // this.form.avatarUrl =this.form.avatarUrl+ ","+response.data.filePath
          this.dialogImageUrl = response.data.filePath
        }
      },
      handlePicUploadSuccess(response, file, fileList) {
        if (response.code == 0) {
          this.$message.success('图片上传成功')
          this.form.bigPicUrl = response.data.filePath
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
      },
      // 更改显示状态
      handleShowStatusChange(id, row) {
        putObj(id, row).then(() => {
          this.$message.success("修改成功")
        });
      },
      handleFactoryStatusChange(id, row) {
        putObj(id, row).then(() => {
          this.$message.success("修改成功")
        });
      }
    },
    created() {
      this.handleQuery()
    }
  }
</script>

<style scoped>

</style>
