<template>
  <div class="app-container">
    <!-- 搜索表单::begin -->
    <el-form :inline="true" ref="queryForm" :model="queryParams">
      <el-form-item>
        <el-button type="primary" @click="handleAdd">新增</el-button>
        <el-button type="success" @click="handleUpdate" :disabled="single">修改</el-button>
        <el-button type="danger" @click="handleDelete" :disabled="multiple">删除</el-button>
      </el-form-item>
      <el-form-item label="分类名称" prop="categoryName">
        <el-input v-model="queryParams.categoryName" placeholder="分类名称"></el-input>
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
      row-key="categoryId"
      :data="tableData"
      :tree-props="{children: 'children', hasChildren: 'hasChildren'}">
      <el-table-column
        prop="categoryName"
        label="分类名称"
        min-width="25%">
      </el-table-column>
      <el-table-column
        prop="goodsCount"
        label="数量"
        min-width="11%">
      </el-table-column>
      <el-table-column
        prop="goodsUnit"
        label="单位"
        min-width="11%">
      </el-table-column>
      <el-table-column
        prop="isNav"
        label="导航栏"
        min-width="11%">
        <template slot-scope="scope">
          <el-switch
            v-model="scope.row.isNav"
            :active-value="1"
            :inactive-value="0"
            @change="handleIsNavChange(scope.row)">
          </el-switch>
        </template>
      </el-table-column>
      <el-table-column
        prop="isShow"
        label="是否显示"
        min-width="11%">
        <template slot-scope="scope">
          <el-switch
            v-model="scope.row.isShow"
            :active-value="1"
            :inactive-value="0"
            @change="handleIsShowChange(scope.row)">
          </el-switch>
        </template>
      </el-table-column>
      <el-table-column
        prop="sort"
        label="排序"
        min-width="11%">
      </el-table-column>
      <el-table-column label="操作" align="center" min-width="20%" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
          >修改
          </el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-plus"
            @click="handleAdd(scope.row)"
          >新增
          </el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
          >删除
          </el-button>
        </template>
      </el-table-column>
    </el-table>
    <!-- 数据表格::end-->

    <!-- 表单弹窗::start -->
    <el-dialog
      :title="dialog.title"
      :visible.sync="dialog.visible"
      @close="cancel"
      top="5vh"
      width="50%">
      <el-form id="dataForm" label-width="150px" :model="form" :rules="rules" ref="form">
        <el-form-item label="上级商品分类" prop="parentId">
          <treeselect v-model="form.parentId" :options="categoryOptions" placeholder="选择上级商品分类"/>
        </el-form-item>
        <el-form-item label="分类名称" prop="categoryName">
          <el-input v-model="form.categoryName" placeholder="请输入分类名称"/>
        </el-form-item>
        <el-form-item label="数量单位" prop="goodsUnit">
          <el-input v-model="form.goodsUnit"/>
        </el-form-item>
        <el-form-item label="排序" prop="sort">
          <el-input v-model="form.sort"/>
        </el-form-item>
        <el-form-item label="是否显示在导航栏" prop="isNav">
          <el-radio-group v-model="form.isNav">
            <el-radio :label="1">是</el-radio>
            <el-radio :label="0">否</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="是否显示" prop="isShow">
          <el-radio-group v-model="form.isShow">
            <el-radio :label="1">是</el-radio>
            <el-radio :label="0">否</el-radio>
          </el-radio-group>
        </el-form-item>

        <el-form-item label="分类图标" prop="icon">
          <el-upload
            ref="upload"
            :file-list="fileList"
            :action="uploadAction"
            :before-upload="handleBeforeUpload"
            :on-success="handleUploadSuccess"
            :on-remove="handleRemove"
            list-type="picture"
            :auto-upload="true"
            :limit="1"
            :headers="fileHeaders">
            <el-button size="small" type="primary">点击上传</el-button>
            <div slot="tip" class="el-upload__tip">只能上传jpg/png文件，且不超过2M</div>
          </el-upload>
        </el-form-item>
        <el-form-item label="关键词" prop="keywords">
          <el-input v-model="form.keywords"/>
        </el-form-item>
        <el-form-item label="分类描述" prop="description">
          <el-input v-model="form.description"/>
        </el-form-item>

        <el-form-item
          v-for="(goodsAttribute, index) in goodsAttributes"
          :label="index | filterAttributeLabelFilter"
          :prop="'goodsAttributeIds['+index+']'"
          :rules="{required: true, message: '请选择筛选属性', trigger: 'blur'}"
        >
          <el-cascader
            :options="attributeOptions"
            :show-all-levels="false"
            v-model="goodsAttribute.value"
          >
          </el-cascader>
          <el-button @click.prevent="removeGoodsAttribute(goodsAttribute)">删除</el-button>
        </el-form-item>
        <el-form-item>
          <el-button @click="addGoodsAttribute">新增筛选属性</el-button>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="cancel">取 消</el-button>
        <el-button type="primary" @click="handleSubmit">确 定</el-button>
      </div>
    </el-dialog>
    <!-- 表单弹窗::end -->
  </div>
</template>

<script>
  import {getObj, postObj, putObj, delObj, list, treeSelect,updateShowStatus,updateNavStatus} from '@/api/pms/category'
  import Treeselect from "@riophae/vue-treeselect"
  import "@riophae/vue-treeselect/dist/vue-treeselect.css"
  import {getToken} from '@/utils/auth'
  import {deleteFile} from '@/api/file'
  import {cascader} from '@/api/pms/attribute'


  export default {
    components: {Treeselect},
    name: "index",
    data() {
      return {
        // 选中数组
        ids: [],
        // 非单个禁用
        single: true,
        // 非多个禁用
        multiple: true,
        queryParams: {
          categoryName: undefined
        },
        tableData: [],
        categoryOptions: [],
        dialog: {
          title: '',
          visible: false
        },
        form: {
          categoryId: undefined,
          categoryName: undefined,
          parentId: 0,
          goodsUnit: undefined,
          isNav: 0,
          isShow: 1,
          sort: 0,
          icon: undefined,
          keywords: undefined,
          description: undefined,
          attribute: undefined,
          goodsAttributeIds: []
        },
        rules: {
          categoryName: [{
            required: true, message: '请输入分类名称', trigger: 'blur'
          }]
        },

        uploadAction: this.uploadAction,
        fileHeaders: {authorization: getToken()},
        fileList: [],
        attributeOptions: [],
        goodsAttributes: [{value: ''}]
      }
    },
    methods: {
      handleQuery() {
        list(this.queryParams).then(response => {
          this.tableData = response.data
        })
      },
      resetQuery() {
        this.queryParams = {
          categoryName: undefined
        }
        this.resetForm("queryForm")
        this.handleQuery()
      },
      // 树形下拉选择器
      loadTreeSelect() {
        treeSelect().then(response => {
          this.categoryOptions = response.data
        });
      },
      // 级联选择器
      loadCascader() {
        cascader().then(response => {
          this.attributeOptions = response.data
        })
      },
      handleAdd(row) {
        this.reset()
        this.dialog = {
          title: "新增商品分类",
          visible: true
        }
        this.loadTreeSelect()
        this.loadCascader()
        if (row != undefined) {
          this.form.parentId = row.categoryId
        }
      },
      handleUpdate(row) {
        this.reset()
        this.loadTreeSelect()
        this.loadCascader()
        const categoryId = row.categoryId || this.ids
        getObj(categoryId).then(response => {
          const data = response.data
          this.form = data
          this.dialog = {
            title: "修改商品分类",
            visible: true
          }
          const icon = data.icon
          if (icon) {
            this.fileList.push({url: icon})
          }
          // 回显
          if (data.goodsAttributeIds != null && data.goodsAttributeIds.length > 0) {
            this.goodsAttributes = []
            data.goodsAttributeIds.forEach(id => {
              this.goodsAttributes.push({value: [null, id.toString()]})
            })
          }
        })
      },
      handleSubmit() {
        // 将级联下拉选中值goodsAttributes赋值给表单goodsAttributeIds
        // goodsAttributes [{value:[null,1]},{null,2}]  （...key省略）
        this.form.goodsAttributeIds = []
        this.goodsAttributes.forEach(item => {
          this.form.goodsAttributeIds.push(item.value[1])
        })

        this.$refs["form"].validate((valid) => {
          if (valid) {
            const id = this.form.categoryId
            if (id != undefined) {
              putObj(id, this.form).then(() => {
                this.$message.success("修改成功")
                this.dialog.visible = false
                this.handleQuery()
              })
            } else {
              postObj(this.form).then(() => {
                this.$message.success("新增成功")
                this.dialog.visible = false
                this.handleQuery()
              })
            }
          }
        })
      },
      handleDelete(row) {
        const ids = row.categoryId || this.ids
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
        this.ids = selection.map(item => item.categoryId)
        this.single = selection.length != 1
        this.multiple = !selection.length
      },
      cancel() {
        this.dialog.visible = false
        this.reset();
      },
      reset() {
        this.resetForm("form")
        this.form = {
          categoryId: undefined,
          categoryName: undefined,
          parentId: 0,
          goodsUnit: undefined,
          isNav: 0,
          isShow: 1,
          sort: 0,
          icon: undefined,
          keywords: undefined,
          description: undefined,
          goodsAttributeIds: []
        }
        this.fileList = []
      },
      // 显示隐藏
      handleIsShowChange(row) {
        let text = row.isShow === 0 ? '显示' : '隐藏'
        this.$confirm('确认要' + text + row.categoryName + '商品分类?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function () {
          return updateShowStatus(row.categoryId, row.isShow)
        }).then(() => {
          this.$message.success("操作成功")
          this.handleQuery()
        }).catch(function () {
          row.status = row.status === 0 ? 1 : 0;
        });
      },
      // 导航栏
      handleIsNavChange(row) {
        let text = row.isNav === 0 ? '显示' : '不显示'
        this.$confirm('确认要' + text + row.categoryName + '在导航栏?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function () {
          return updateNavStatus(row.categoryId,row.isNav)
        }).then(() => {
          this.$message.success("操作成功")
          this.handleQuery()
        }).catch(function () {
          row.status = row.status === 0 ? 1 : 0;
        });
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
          this.form.icon = response.data.filePath
        }
      },
      handleRemove(file, fileList) { //删除图片
        deleteFile(file.response.data.filePath).then(() => {
          this.$message.success("删除图片成功")
        })
      },
      removeGoodsAttribute(goodsAttribute) {
        if (this.goodsAttributes.length === 1) {
          this.$message.warning("至少留一个")
          return
        }
        const index = this.goodsAttributes.indexOf(goodsAttribute)
        if (index !== -1) {
          this.goodsAttributes.splice(index, 1)
        }
      },
      addGoodsAttribute() {
        if (this.goodsAttributes.length === 3) {
          this.$message.warning("最多添加三个")
          return
        }
        this.goodsAttributes.push({
          value: ''
        })
      }
    },
    filters: {
      filterAttributeLabelFilter(index) {
        return index === 0 ? '筛选属性' : ''
      }
    },
    created() {
      this.handleQuery()
    }
  }
</script>

<style scoped>

</style>
