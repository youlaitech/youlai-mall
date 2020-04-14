<template>
  <div class="app-container">
    <el-form :model="form" ref="goodsAttrForm" label-width="120px" style="width: 720px" size="small">
      <el-form-item label="属性类型：">
        <el-select v-model="form.goodsAttributeCategoryId"
                   placeholder="请选择属性类型"
                   @change="handleGoodsAttrTypeChange">
          <el-option
            v-for="item in goodsAttributeTypeOptions"
            :key="item.value"
            :label="item.label"
            :value="item.value">
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="商品规格：">
        <el-card shadow="never" class="cardBg">
          <div v-for="(goodsAttr,idx) in selectGoodsAttr">
            {{goodsAttr.name}}：
            <el-checkbox-group v-if="goodsAttr.handAddStatus===0" v-model="selectGoodsAttr[idx].values">
              <el-checkbox v-for="item in getInputListArr(goodsAttr.inputList)" :label="item" :key="item"
                           class="littleMarginLeft"></el-checkbox>
            </el-checkbox-group>
            <div v-else>
              <el-checkbox-group v-model="selectGoodsAttr[idx].values">
                <div v-for="(item,index) in selectGoodsAttr[idx].options" style="display: inline-block"
                     class="littleMarginLeft">
                  <el-checkbox :label="item" :key="item"></el-checkbox>
                  <el-button type="text" class="littleMarginLeft" @click="handleRemoveGoodsAttrValue(idx,index)">删除
                  </el-button>
                </div>
              </el-checkbox-group>
              <el-input v-model="addGoodsAttrValue" style="width: 160px;margin-left: 10px" clearable></el-input>
              <el-button class="littleMarginLeft" @click="handleAddGoodsAttrValue(idx)">增加</el-button>
            </div>
          </div>
        </el-card>
        <el-table style="width: 100%;margin-top: 20px"
                  :data="form.skuStockList"
                  border>
          <el-table-column
            v-for="(item,index) in selectGoodsAttr"
            :label="item.name"
            :key="item.id"
            align="center">
            <template slot-scope="scope">
              {{getGoodsSkuSp(scope.row,index)}}
            </template>
          </el-table-column>
          <el-table-column
            label="销售价格"
            width="80"
            align="center">
            <template slot-scope="scope">
              <el-input v-model="scope.row.price"></el-input>
            </template>
          </el-table-column>
          <el-table-column
            label="商品库存"
            width="80"
            align="center">
            <template slot-scope="scope">
              <el-input v-model="scope.row.stock"></el-input>
            </template>
          </el-table-column>
          <el-table-column
            label="库存预警值"
            width="80"
            align="center">
            <template slot-scope="scope">
              <el-input v-model="scope.row.lowStock"></el-input>
            </template>
          </el-table-column>
          <el-table-column
            label="SKU编号"
            align="center">
            <template slot-scope="scope">
              <el-input v-model="scope.row.skuCode"></el-input>
            </template>
          </el-table-column>
          <el-table-column
            label="操作"
            width="80"
            align="center">
            <template slot-scope="scope">
              <el-button
                type="text"
                @click="handleRemoveGoodsSku(scope.$index, scope.row)">删除
              </el-button>
            </template>
          </el-table-column>
        </el-table>
        <el-button
          type="primary"
          style="margin-top: 20px"
          @click="handleRefreshGoodsSkuList">刷新列表
        </el-button>
        <el-button
          type="primary"
          style="margin-top: 20px"
          @click="handleSyncGoodsSkuPrice">同步价格
        </el-button>
        <el-button
        type="primary"
        style="margin-top: 20px"
        @click="handleSyncGoodsSkuStock">同步库存
      </el-button>
      </el-form-item>
      <el-form-item label="商品参数：">
        <el-card shadow="never" class="cardBg">
          <div v-for="(item,index) in selectGoodsParam" :class="{littleMarginTop:index!==0}">
            <div class="paramInputLabel">{{item.name}}:</div>
            <el-select v-if="item.inputType===1" class="paramInput" v-model="selectGoodsParam[index].value">
              <el-option
                v-for="item in getParamInputList(item.inputList)"
                :key="item"
                :label="item"
                :value="item">
              </el-option>
            </el-select>
            <el-input v-else class="paramInput" v-model="selectGoodsParam[index].value"></el-input>
          </div>
        </el-card>
      </el-form-item>

      <el-form-item label="商品相册：" label-width="120px">
        <el-upload
          ref="upload"
          :file-list="goodsPicList"
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
      <el-form-item label="规格参数：">
        <el-tabs v-model="activeHtmlName" type="card">
          <el-tab-pane label="电脑端详情" name="pc">
            <!--<tinymce :width="595" :height="300" v-model="form.detailHtml" id="pc"></tinymce>-->
          </el-tab-pane>
          <el-tab-pane label="移动端详情" name="mobile">
            <tinymce :width="595" :height="300" v-model="form.detailMobileHtml" id="mobile"></tinymce>
          </el-tab-pane>
        </el-tabs>
      </el-form-item>
      <el-form-item style="text-align: center">
        <el-button size="medium" @click="handlePrev">上一步，填写商品促销</el-button>
        <el-button type="primary" size="medium" @click="handleFinishCommit" >完成提交商品</el-button>
      </el-form-item>
    </el-form>
    <el-dialog :visible.sync="dialogImageVisible">
      <img width="60%" height="60%" :src="dialogImageUrl" alt="">
    </el-dialog>
  </div>
</template>

<script>
  import {list as fetchGoodsAttrTypeList} from '@/api/pms/attribute/type'
  import {page as fetchGoodsAttrList} from '@/api/pms/attribute'
  import {getToken} from '@/utils/auth'
  import {deleteFile} from '@/api/fms'
  //import Tinymce from '@/components/Tinymce'

  export default {
    name: "GoodsAttrDetail",
    components: {},
    props: {
      goodsParamData: Object,
      isEdit: {
        type: Boolean,
        default: false
      }
    },
    data() {
      return {
        form: this.goodsParamData,
        uploadAction: this.uploadAction,
        fileHeaders: {authorization: getToken()},
        dialogImageVisible: false,
        dialogImageUrl: undefined,
        goodsPicList: [],// 回显图片
        //编辑模式时是否初始化成功
        hasEditCreated:false,
        //商品属性分类下拉选项
        goodsAttributeTypeOptions: [],
        //选中的商品属性
        selectGoodsAttr: [],
        //选中的商品参数
        selectGoodsParam: [],
        //可手动添加的商品属性
        addGoodsAttrValue: '',
        //商品富文本详情激活类型
        activeHtmlName: 'pc'
      }
    },
    computed: {

      //商品的编号
      goodsId(){
        return this.form.id;
      }
    },
    created() {
      this.getGoodsAttrTypeList();
    },
    watch: {
      goodsParamData: function (newValue) {
        this.form = newValue;
      },
      goodsId:function (newValue) {
        if(!this.isEdit)return;
        if(this.hasEditCreated)return;
        if(newValue===undefined||newValue==null||newValue===0)return;
        this.handleEditCreated();

        // 图片回显
        const picUrl = this.form.pic;
        if (picUrl) {
          this.goodsPicList.push({url: picUrl});
        }
      }

    },
    methods: {
      handleEditCreated() {
        //根据商品属性分类id获取属性和参数
        if(this.form.goodsAttributeCategoryId!=null){
          this.handleGoodsAttrTypeChange(this.form.goodsAttributeCategoryId);
        }
        this.hasEditCreated=true;
      },

      getGoodsAttrTypeList() {
        fetchGoodsAttrTypeList().then(response => {
          this.goodsAttributeTypeOptions = [];
          let list = response.data;
          for (let i = 0; i < list.length; i++) {
            this.goodsAttributeTypeOptions.push({label: list[i].attributeTypeName, value: list[i].attributeTypeId});
          }
        });
      },
      getGoodsAttrList(type, cid) {
        let param = {type: type,attributeTypeId: cid};
        fetchGoodsAttrList(1,100,param).then(response => {

          let list = response.data.records;
          if (type === 0) {
            this.selectGoodsAttr = [];
            for (let i = 0; i < list.length; i++) {
              let options = [];
              let values = [];
              if (this.isEdit) {
                if (list[i].handAddStatus === 1) {
                  //编辑状态下获取手动添加编辑属性
                  options = this.getEditAttrOptions(list[i].id);
                }
                //编辑状态下获取选中属性
                values = this.getEditAttrValues(i);
              }
              this.selectGoodsAttr.push({
                id: list[i].attributeId,
                name: list[i].attributeName,
                handAddStatus: list[i].isHandAdd, // 是否支持手动新增
                inputList: list[i].inputList, // 可选值列表
                values: values,
                options: options
              });
            }
          } else {
            this.selectGoodsParam = [];

            for (let i = 0; i < list.length; i++) {
              let value=null;
              if(this.isEdit) {
                //编辑模式下获取参数属性
                value= this.getEditParamValue(list[i].attributeId);
              }
              this.selectGoodsParam.push({
                id: list[i].attributeId,
                name: list[i].attributeName,
                value: value,
                inputType: list[i].inputType, // 属性录入方式：0->手工录入；1->从列表中选取
                inputList: list[i].inputList  // 可选值列表
              });
            }
          }
        });
      },

      //获取属性的值
      getEditParamValue(id){
        for(let i=0;i<this.form.goodsAttributeValueList.length;i++){
          if(id===this.form.goodsAttributeValueList[i].goodsAttributeId){
            return this.form.goodsAttributeValueList[i].value;
          }
        }
      },
      //获取设置的可手动添加属性值
      getEditAttrOptions(id) {
        let options = [];
        for (let i = 0; i < this.form.goodsAttributeValueList.length; i++) {
          let attrValue = this.form.goodsAttributeValueList[i];
          if (attrValue.goodsAttributeId === id) {
            let strArr = attrValue.value.split(',');
            for (let j = 0; j < strArr.length; j++) {
              options.push(strArr[j]);
            }
            break;
          }
        }
        return options;
      },
      //获取选中的属性值
      getEditAttrValues(index) {
        let values = new Set();
        if (index === 0) {
          for (let i = 0; i < this.form.skuStockList.length; i++) {
            let sku = this.form.skuStockList[i];
            let spData = JSON.parse(sku.spData);
            if (spData!= null && spData.length>=1) {
              values.add(spData[0].value);
            }
          }
        } else if (index === 1) {
          for (let i = 0; i < this.form.skuStockList.length; i++) {
            let sku = this.form.skuStockList[i];
            let spData = JSON.parse(sku.spData);
            if (spData!= null && spData.length>=2) {
              values.add(spData[1].value);
            }
          }
        } else {
          for (let i = 0; i < this.form.skuStockList.length; i++) {
            let sku = this.form.skuStockList[i];
            let spData = JSON.parse(sku.spData);
            if (spData!= null && spData.length>=3) {
              values.add(spData[2].value);
            }
          }
        }
        return Array.from(values);
      },
      handleGoodsAttrTypeChange(value) {
        this.getGoodsAttrList(0, value);
        this.getGoodsAttrList(1, value);
      },
      getInputListArr(inputList) {
        return inputList.split(/，|,/);
      },
      handleAddGoodsAttrValue(idx) {
        let options = this.selectGoodsAttr[idx].options;
        if (this.addGoodsAttrValue == null || this.addGoodsAttrValue == '') {
          this.$message({
            message: '属性值不能为空',
            type: 'warning',
            duration: 1000
          });
          return
        }
        if (options.indexOf(this.addGoodsAttrValue) !== -1) {
          this.$message({
            message: '属性值不能重复',
            type: 'warning',
            duration: 1000
          });
          return;
        }
        this.selectGoodsAttr[idx].options.push(this.addGoodsAttrValue);
        this.addGoodsAttrValue = null;
      },
      handleRemoveGoodsAttrValue(idx, index) {
        this.selectGoodsAttr[idx].options.splice(index, 1);
      },

      getGoodsSkuSp(row, index) {
        let spData = JSON.parse(row.spData);
        if(spData!=null&&index<spData.length){
          return spData[index].value;
        }else{
          return null;
        }
      },
      handleRefreshGoodsSkuList() {
        this.$confirm('刷新列表将导致sku信息重新生成，是否要刷新', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.refreshGoodsSkuList();
        });
      },
      handleSyncGoodsSkuPrice(){
        this.$confirm('将同步第一个sku的价格到所有sku,是否继续', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          if(this.form.skuStockList!==null&&this.form.skuStockList.length>0){
            let tempSkuList = [];
            tempSkuList = tempSkuList.concat(tempSkuList,this.form.skuStockList);
            let price=this.form.skuStockList[0].price;
            for(let i=0;i<tempSkuList.length;i++){
              tempSkuList[i].price=price;
            }
            this.form.skuStockList=[];
            this.form.skuStockList=this.form.skuStockList.concat(this.form.skuStockList,tempSkuList);
          }
        });
      },
      handleSyncGoodsSkuStock(){
        this.$confirm('将同步第一个sku的库存到所有sku,是否继续', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          if(this.form.skuStockList!==null&&this.form.skuStockList.length>0){
            let tempSkuList = [];
            tempSkuList = tempSkuList.concat(tempSkuList,this.form.skuStockList);
            let stock=this.form.skuStockList[0].stock;
            let lowStock=this.form.skuStockList[0].lowStock;
            for(let i=0;i<tempSkuList.length;i++){
              tempSkuList[i].stock=stock;
              tempSkuList[i].lowStock=lowStock;
            }
            this.form.skuStockList=[];
            this.form.skuStockList=this.form.skuStockList.concat(this.form.skuStockList,tempSkuList);
          }
        });
      },
      refreshGoodsSkuList() {
        this.form.skuStockList = [];
        let skuList = this.form.skuStockList;
        //只有一个属性时
        if (this.selectGoodsAttr.length === 1) {
          let attr = this.selectGoodsAttr[0];
          for (let i = 0; i < attr.values.length; i++) {
            skuList.push({
              spData: JSON.stringify([{key:attr.name,value:attr.values[i]}])
            });
          }
        } else if (this.selectGoodsAttr.length === 2) {
          let attr0 = this.selectGoodsAttr[0];
          let attr1 = this.selectGoodsAttr[1];
          for (let i = 0; i < attr0.values.length; i++) {
            if (attr1.values.length === 0) {
              skuList.push({
                spData: JSON.stringify([{key:attr0.name,value:attr0.values[i]}])
              });
              continue;
            }
            for (let j = 0; j < attr1.values.length; j++) {
              let spData = [];
              spData.push({key:attr0.name,value:attr0.values[i]});
              spData.push({key:attr1.name,value:attr1.values[j]});
              skuList.push({
                spData: JSON.stringify(spData)
              });
            }
          }
        } else {
          let attr0 = this.selectGoodsAttr[0];
          let attr1 = this.selectGoodsAttr[1];
          let attr2 = this.selectGoodsAttr[2];
          for (let i = 0; i < attr0.values.length; i++) {
            if (attr1.values.length === 0) {
              skuList.push({
                spData: JSON.stringify([{key:attr0.name,value:attr0.values[i]}])
              });
              continue;
            }
            for (let j = 0; j < attr1.values.length; j++) {
              if (attr2.values.length === 0) {
                let spData = [];
                spData.push({key:attr0.name,value:attr0.values[i]});
                spData.push({key:attr1.name,value:attr1.values[j]});
                skuList.push({
                  spData: JSON.stringify(spData)
                });
                continue;
              }
              for (let k = 0; k < attr2.values.length; k++) {
                let spData = [];
                spData.push({key:attr0.name,value:attr0.values[i]});
                spData.push({key:attr1.name,value:attr1.values[j]});
                spData.push({key:attr2.name,value:attr2.values[k]});
                skuList.push({
                  spData: JSON.stringify(spData)
                });
              }
            }
          }
        }
      },

      //合并商品属性
      mergeGoodsAttrValue() {
        this.form.goodsAttributeValueList = [];
        for (let i = 0; i < this.selectGoodsAttr.length; i++) {
          let attr = this.selectGoodsAttr[i];
          if (attr.handAddStatus === 1 && attr.options != null && attr.options.length > 0) {
            this.form.goodsAttributeValueList.push({
              goodsAttributeId: attr.id,
              value: this.getOptionStr(attr.options)
            });
          }
        }
        for (let i = 0; i < this.selectGoodsParam.length; i++) {
          let param = this.selectGoodsParam[i];
          this.form.goodsAttributeValueList.push({
            goodsAttributeId: param.id,
            value: param.value
          });
        }
      },
      getOptionStr(arr) {
        let str = '';
        for (let i = 0; i < arr.length; i++) {
          str += arr[i];
          if (i != arr.length - 1) {
            str += ',';
          }
        }
        return str;
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
          this.form.pic = response.data.filePath
          // 多图则每次上传追加
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

      handleRemoveGoodsSku(index, row) {
        let list = this.form.skuStockList;
        if (list.length === 1) {
          list.pop();
        } else {
          list.splice(index, 1);
        }
      },
      getParamInputList(inputList) {
        return inputList.split(/，|,/);
      },
      handlePrev() {
        this.$emit('prevStep')
      },
      handleFinishCommit() {
        this.mergeGoodsAttrValue();
        this.$emit('finishCommit',this.isEdit);
      }
    }
  }
</script>

<style scoped>
  .littleMarginLeft {
    margin-left: 10px;
  }

  .littleMarginTop {
    margin-top: 10px;
  }

  .paramInput {
    width: 250px;
  }

  .paramInputLabel {
    display: inline-block;
    width: 100px;
    text-align: right;
    padding-right: 10px
  }

  .cardBg {
    background: #F8F9FC;
  }
</style>
