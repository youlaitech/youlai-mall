<template>
  <el-card class="form-container" shadow="never">
    <el-form id="dataForm"
             label-width="150px"
             :model="form"
             :rules="rules"
             ref="form">
      <el-form-item label="秒杀订单超过：" prop="flashOrderOvertime">
        <el-input v-model="form.flashOrderOvertime">
          <template slot="append">分</template>
        </el-input>
        <span class="note">未付款，订单自动关闭</span>
      </el-form-item>
      <el-form-item label="正常订单超过：" prop="normalOrderOvertime">
        <el-input v-model="form.normalOrderOvertime" >
          <template slot="append">分</template>
        </el-input>
        <span class="note">未付款，订单自动关闭</span>
      </el-form-item>
      <el-form-item label="发货超过：" prop="confirmOvertime">
        <el-input v-model="form.confirmOvertime">
          <template slot="append">天</template>
        </el-input>
        <span class="note">未收货，订单自动完成</span>
      </el-form-item>
      <el-form-item label="订单完成超过：" prop="finishOvertime">
        <el-input v-model="form.finishOvertime" >
          <template slot="append">天</template>
        </el-input>
        <span class="note">自动结束交易，不能申请售后</span>
      </el-form-item>
      <el-form-item label="秒杀订单超过：" prop="commentOvertime">
        <el-input v-model="form.commentOvertime">
          <template slot="append">分</template>
        </el-input>
        <span class="note">自动五星好评</span>
      </el-form-item>
      <el-form-item style="text-align: left">
        <el-button type="primary" @click="handleSubmit">确 定</el-button>
      </el-form-item>
    </el-form>

  </el-card>
</template>

<script>
  import {getObj, postObj, putObj} from '@/api/oms/setting'

  const checkTime = (rule, value, callback) => {
    if (!value) {
      return callback(new Error('时间不能为空'));
    }
    let intValue = parseInt(value);
    if (!Number.isInteger(intValue)) {
      return callback(new Error('请输入数字值'));
    }
    callback();
  };

  export default {
    data() {
      return {
        form: {
          id: undefined,
          flashOrderOvertime: 0,
          normalOrderOvertime: 0,
          confirmOvertime: 0,
          finishOvertime: 0,
          commentOvertime: 0
        },
        rules:{
          flashOrderOvertime:{validator: checkTime, trigger: 'blur' },
          normalOrderOvertime:{validator: checkTime, trigger: 'blur' },
          confirmOvertime: {validator: checkTime, trigger: 'blur' },
          finishOvertime: {validator: checkTime, trigger: 'blur' },
          commentOvertime:{validator: checkTime, trigger: 'blur' }
        }
      }
    },
    methods: {
      handleSubmit() {
        this.$refs["form"].validate((valid) => {
          if (valid) {
            this.$confirm('确认提交修改?', "提示", {
              confirmButtonText: "确定",
              cancelButtonText: "取消",
              type: "warning"
            }).then(()=> {
              const id = this.form.id
              if (id != undefined) {
                putObj(id, this.form).then(response=>{
                  this.$message.success(response.msg)
                })
              } else {
                postObj(this.form).then(response=>{
                  this.$message.success(response.msg)
                })
              }
            }).catch(() =>
              this.$message.info("已取消")
            )


          }
        })
      }
    },
    created() {
      getObj().then(response => {
        if(response.data){
          this.form = response.data
        }
      })
    }
  }
</script>

<style lang="less" scoped>

  .form-container {
    width: 800px;
    margin: 30px auto;

    #dataForm {
      .el-input {
        width: 50%;
      }
      .note {
        margin-left: 15px;
      }
    }
  }

</style>
