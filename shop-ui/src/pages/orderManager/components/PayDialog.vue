<template>
  <el-dialog
    title="支付订单"
    :visible.sync="visible"
    width="500px"
  >
    <div v-if="orderRemainingTime > 0">
      <el-row>订单提交成功!</el-row>
      <el-row>请在 [{{orderRemainingTimeShow}}] 内完成支付!</el-row>
      <el-row>支付金额:￥{{needPayAmount}}</el-row>
      <el-form label-width="100px">
        <el-form-item label="支付方式" prop="payType">
          <el-radio-group v-model="payType">
            <el-radio label="1">支付宝</el-radio>
            <el-radio label="2">微信</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="pay">支付</el-button>
        </el-form-item>
      </el-form>
    </div>
    <div v-else>
      <el-row>此订单不能支付!</el-row>
    </div>
  </el-dialog>
</template>

<script>
  import {getOrderDetail, payOrder} from "@/http/implement/order";
  import {formatSeconds} from "@/utils/common"

  export default {
    name: "PayDialog",
    props: {
      payDialogVisible: Boolean,
      orderId: Number
    },
    created() {
      // this.getOrderPayDetail();
    },
    activated() {
    },
    computed: {
      visible: {
        get() {
          return this.payDialogVisible;
        },
        set(visible) {
          this.$emit('updatePayDialogVisible', visible);
        }
      }
    },
    data() {
      return {
        // 订单剩余支付时间，秒
        orderRemainingTime: null,
        orderRemainingTimeShow: null,
        // 支付金额
        needPayAmount: null,
        payType: '1'
      };
    },
    watch: {
      payDialogVisible(newVal) {
        if (newVal === true) {
          this.getOrderPayDetail();
        }
      }
    },
    methods: {
      getOrderPayDetail: function () {
        getOrderDetail({id: this.orderId}).then(res => {
          this.orderRemainingTime = res.data.orderRemainingTime;
          this.orderRemainingTimeShow = formatSeconds(res.data.orderRemainingTime);
          this.needPayAmount = res.data.payAmount +
            res.data.freightAmount -
            res.data.discountAmount;
        });
      },
      pay: function () {
        payOrder({id: this.orderId,payType: this.payType}).then(res => {
          this.$message.success(res.info);
          this.visible = false;
        });
      }
    }
  }
</script>

<style scoped>

</style>
