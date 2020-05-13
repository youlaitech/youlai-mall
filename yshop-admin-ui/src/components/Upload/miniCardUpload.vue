<template> 
  <div>
    <el-upload
      class="mini-card-uploader"
      :headers="headers"
      :action="uploadAction"
      :multiple="false"
      :show-file-list="false"
      :file-list="fileList"
      :before-upload="beforeUpload"
      :on-success="handleUploadSuccess"
      :on-preview="handlePreview"
      :auto-upload="true"
    >
      <img v-if="fileList[0].url" :src="fileList[0].url" class="mini-card">
      <i v-else class="el-icon-plus mini-card-uploader-icon"></i>
      <i v-if="fileList[0].url" class="el-icon-close mini-card-remove-icon" @click.stop="handleRemove(fileList[0].url)"></i>
    </el-upload>

  </div>
</template>
<script>
  import {getToken} from '@/utils/auth'
  import {fileDelete} from '@/api/fms'

  export default {
    name: 'singleUpload',
    props: {
      value: String
    },
    computed: {
      imageUrl() {
        return this.value;
      },
      showFileList: {
        get: function () {
          return this.value !== null && this.value !== '' && this.value !== undefined;
        },
        set: function (newValue) {
        }
      },
      fileList() {
        return [
          {
            url: this.imageUrl
          }
        ]
      }
    },
    data() {
      return {
        headers: {authorization: getToken()},
        uploadAction: process.env.VUE_APP_BASE_API + '/fms/files',
        dialogVisible: false,
      }
    },
    methods: {
      emitInput(val) {
        this.$emit('input', val)
      },
      handleRemove(url) {
        if (url) {
          fileDelete(url)
        }
        this.emitInput('');
      },
      handlePreview(file) {
        this.dialogVisible = true;
      },
      beforeUpload(file) {

      },
      handleUploadSuccess(response, file) {
        this.showFileList = true;
        this.fileList.pop();
        let url = response.data.url
        this.fileList.push({url: url})
        this.emitInput(this.fileList[0].url);
      }
    }
  }
</script>
<style>
  .mini-card-uploader .el-upload {
    border: 1px dashed #d9d9d9;
    border-radius: 6px;
    cursor: pointer;
    position: relative;
    overflow: hidden;
  }

  .mini-card-uploader .el-upload:hover {
    border-color: #409EFF;
  }

  .mini-card-uploader-icon {
    font-size: 28px;
    color: #8c939d;
    width: 60px;
    height: 50px;
    line-height: 50px;
    text-align: center;
  }

  .mini-card {
    width: 60px;
    height: 50px;
    display: block;
  }

  .mini-card-remove-icon {
    position: absolute;
    right: 0;
    top: 0;
    color: #409eff;
    }
</style>


