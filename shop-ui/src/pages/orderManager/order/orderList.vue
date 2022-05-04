<template>
  <div>
    <Query
      size="mini"
      labelWidth="100px"
      :itemWidth="7"
      :searchData="searchData"
      :searchForm="searchForm"
      :searchHandle="searchHandle"
      :list="list"
    >
    </Query>
    <el-main class="table-main">
      <r-table
        ref="myTable"
        :tableData="tableData"
        :columns="columns"
        :uniqueID="'id'"
        :current="searchData.pageNum"
        :pageSize="searchData.pageSize"
        :total="total"
        :multiSelect="true"
        :isHandle="true"
        :cell-style="setCellStyle"
        :tableHandles="tableHandles"
        @on-select-all="onSelectAll"
        @on-select="select"
        @on-selection-change="selectChange"
        @on-change="pageChange"
        @on-page-size-change="pageSizeChange"
        @on-sort-change="sortChange"
        @on-row-dblclick="goToDetail"
      ></r-table>
    </el-main>
    <pay-dialog :pay-dialog-visible="payDialogVisible" :order-id="orderId" @updatePayDialogVisible="updatePayDialogVisible"></pay-dialog>

  </div>
</template>

<script>
import * as order from "@/http/implement/order";
import PayDialog from "@/pages/orderManager/components/PayDialog";
import {confirmOrder} from "@/http/implement/order";

export default {
  name: "orderList",
  components: { PayDialog},

  data() {
    return {
      payDialogVisible: false,
      orderId: null,

      searchForm: [
        {
          type: "Input",
          label: "订单编号",
          prop: "orderSn",
          multiple: false,
          clearable: true,
          placeholder: "请输入订单编号"
        },
        {
          type: "Input",
          label: "用户账户",
          prop: "memberUsername",
          clearable: true,
          placeholder: "请输入用户名"
        },
        {
          type: "Select",
          label: "支付方式",
          prop: "payType",
          multiple: false,
          change: value => {
            this.searchData.payType = value;
          }
        },
        {
          type: "Select",
          label: "订单状态",
          prop: "status",
          multiple: false,
          change: value => {
            this.searchData.status = value;
          }
        }
      ],
      searchHandle: [
        {
          label: "查询",
          page: "brand",
          type: "primary",
          handle: () => {
            this.getTableData();
          }
        },
        {
          label: "重置",
          page: "brand",
          type: "primary",
          handle: () => {
            this.searchData.orderSn = "";
            this.searchData.memberUsername = "";
            this.searchData.payType = "";
            this.searchData.status = "";
          }
        }
      ],
      tableHandles: [
        {
          label: "查看详情",
          type: "primary",
          size: "small",
          page: "orderDetail",
          btn: "Detail",
          handle: () => {
            let that = this;
            if (that.selection.length !== 1) {
              this.$message.error("请选择一条记录!");
            } else {
              this.$router.push({
                path: "/order/orderDetail",
                query: {
                  isEdit: false,
                  id: that.selection[0].id
                }
              });
            }
          }
        },
        {
          label: "关闭订单",
          type: "primary",
          size: "small",
          page: "orderList",
          btn: "Add",
          handle: () => {
            if (this.selection.length <= 0) {
              this.$message.error("请选择一条或多条记录!");
            } else {
              // 校验选中的数据的上架/下架状态是否一致
              let orderStatus = this.selection[0].status;
              if (orderStatus !== 0) {
                this.$message.error("请选择未付款状态的订单");
                return;
              }
              for (let i = 0; i < this.selection.length; i++) {
                if (orderStatus !== this.selection[i].status) {
                  this.$message.error("请选择相同未付款状态的记录!");
                  return;
                }
              }
              this.$confirm("是否确认关闭订单" + "?", "提示", {
                confirmButtonText: "确定",
                cancelButtonText: "取消",
                type: "warning"
              }).then(() => {
                /* let orderStatusList = [];
                 for (let i = 0; i < this.selection.length; i++) {
                   orderStatusList.push({
                     id: this.selection[i].id,
                     //orderStatus: this.selection[i].status
                   });
                 }*/
                order.closeOrder(this.selection.map(s => {
                  return s.id;
                })).then(res => {
                  this.$message.success(res.info);
                  this.getTableData();
                });
              });
            }
          }
        },
        {
          perm: true,
          label: "订单发货",
          type: "primary",
          size: "small",
          page: "orderList",
          btn: "Delivery",
          handle: () => {
            if (this.selection.length <= 0) {
              this.$message.error("请选择一条或多条记录!");
            } else {
              // 校验选中的数据的订单状态是否一致
              let orderStatus = this.selection[0].status;
              if (orderStatus !== 1) {
                this.$message.error("请选择未发货状态的订单");
                return;
              }
              for (let i = 0; i < this.selection.length; i++) {
                if (orderStatus !== this.selection[i].status) {
                  this.$message.error("请选择相同未发货状态的记录!");
                  return;
                }
              }
              this.$confirm("是否确认发货订单" + "?", "提示", {
                confirmButtonText: "确定",
                cancelButtonText: "取消",
                type: "warning"
              }).then(() => {
                order.deliveryOrder(this.selection.map(s => {
                  return s.id;
                })).then(res => {
                  this.$message.success(res.info);
                  this.getTableData();
                });
              });
            }
          }
        },
        {
          perm: true,
          label: "支付订单",
          type: "primary",
          size: "small",
          page: "orderList",
          btn: "Pay",
          handle: () => {
            if (this.selection.length !== 1) {
              this.$message.error("请选择一条记录!");
            } else {
              this.orderId = this.selection[0].id;
              this.payDialogVisible = true;
            }
          }
        },
        {
          label: "确认收货",
          type: "primary",
          size: "small",
          page: "orderList",
          btn: "Delivery",
          handle: () => {
            if (this.selection.length <= 0) {
              this.$message.error("请选择一条或多条记录!");
            } else {
              // 校验选中的数据的订单状态是否一致
              let orderStatus = this.selection[0].status;
              if (orderStatus !== 2) {
                this.$message.error("请选择已发货状态的订单");
                return;
              }
              for (let i = 0; i < this.selection.length; i++) {
                if (orderStatus !== this.selection[i].status) {
                  this.$message.error("请选择相同已发货状态的记录!");
                  return;
                }
              }
              this.$confirm("是否确认收货?", "提示", {
                confirmButtonText: "确定",
                cancelButtonText: "取消",
                type: "warning"
              }).then(() => {
                order.confirmOrder(this.selection.map(s => {
                  return s.id;
                })).then(res => {
                  this.$message.success(res.info);
                  this.getTableData();
                });
              });
            }
          }
        },
        {
          label: "删除",
          type: "danger",
          size: "small",
          page: "orderList",
          btn: "Add",
          handle: () => {
            if (this.selection.length <= 0) {
              this.$message.error("请选择一条或多条记录!");
            } else {
              // 校验选中的数据的上架/下架状态是否一致
              let orderStatus = this.selection[0].status;
              if (orderStatus !== 0) {
                this.$message.error("请选择未付款状态的订单");
                return;
              }
              for (let i = 0; i < this.selection.length; i++) {
                if (orderStatus !== this.selection[i].status) {
                  this.$message.error("请选择相同未付款状态的记录!");
                  return;
                }
              }
              this.$confirm("是否确认删除订单" + "?", "提示", {
                confirmButtonText: "确定",
                cancelButtonText: "取消",
                type: "warning"
              }).then(() => {
               order.deleteOrder(this.selection.map(s => {
                  return s.id;
                })).then(res => {
                  this.$message.success(res.info);
                  this.getTableData();
                });
              });
            }
          }
        },
      ],
      tableData: [],
      total: 0,
      sortName: "",
      sortType: "",
      columns: [],
      /* columns: [
        { key: "id", label: "编号", prop: "id" },
        { key: "orderSn", label: "订单编号", prop: "orderSn" },
        { key: "createTime", label: "提交时间", prop: "createTime" },
        { key: "memberUsername", label: "用户帐号", prop: "memberUsername" },
        { key: "totalAmount", label: "订单总金额", prop: "totalAmount" },
        { key: "payTypeName", label: "支付方式", prop: "payTypeName" },
        { key: "statusName", label: "订单状态", prop: "statusName" }
      ],*/
      selection: [],
      searchData: {
        pageNum: this.$route.query.pageNum
          ? parseInt(this.$route.query.pageNum)
          : 1,
        pageSize: 200,
        orderSn: "",
        memberUsername: "",
        payType: "",
        status: ""
      },
      list: {
        payTypeList: [],
        statusList: []
      }
    };
  },
  created() {
    this.getColumns();
    //一加载页面就运行
    this.getTableData();
  },
  activated() {
    this.getTableData();
  },
  methods: {
    getColumns: function () {
      this.$root.$children[0].getColumns("/order/orderList").then(res => {
        this.columns = this.$columns(res, true);
      });
    },
    getTableData: function (page) {
      this.searchData.pageNum = page ? page : 1;
      order.getOrderList(this.searchData).then(res => {
        this.tableData = res.data.records;
        this.total = res.data.total;
      });
      order.getPayType().then(res => {
        res.data.forEach(x => (x.value = parseInt(x.value)));
        this.list.payTypeList = res.data;
      });
      order.getStatus().then(res => {
        res.data.forEach(x => (x.value = parseInt(x.value)));
        this.list.statusList = res.data;
      });
    },
    goToDetail(row) {
      this.$router.push({
        path: "/order/orderDetail",
        query: {
          isEdit: false,
          id: row.id
        }
      });
    },
    updatePayDialogVisible: function(payDialogVisible){
      this.payDialogVisible = payDialogVisible;
    },
    select: function (selection) {
      this.selection = selection;
    },
    selectChange: function (selection) {
      this.selection = selection;
    },
    onSelectAll: function (selection) {
      this.selection = selection;
    },
    pageChange(page) {
      this.getTableData(page);
    },
    pageSizeChange(pageSize) {
      if (this.searchData.pageSize !== pageSize) {
        this.searchData.pageSize = pageSize;
        this.searchData.pageNum = 1;
        this.getTableData();
      }
    },
    // 排序时生效
    sortChange(sortName, sortType) {
      this.sortName = sortName;
      this.sortType = sortType;
      this.getTableData();
    },
    setCellStyle({row, column}) {
      if (row && column.property === "name") {
        return {
          color: "#00BFFF"
        };
      }
      return {};
    }
  }
};
</script>

<style scoped></style>
