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
      ></r-table>
    </el-main>
  </div>
</template>

<script>
import * as product from "@/http/implement/product";

export default {
  name: "orderList",
  data() {
    return {
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
      tableData: [],
      total: 0,
      sortName: "",
      sortType: "",
      columns:[],
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
  watch: {},
  activated() {},
  methods: {
    getColumns: function() {
      this.$root.$children[0].getColumns("/order/orderList").then(res => {
        this.columns = this.$columns(res, true);
      });
    },
    getTableData: function(page) {
      this.searchData.pageNum = page ? page : 1;
      product.getOrderList(this.searchData).then(res => {
        this.tableData = res.data.records;
        this.total = res.data.total;
      });
      product.getPayType().then(res => {
        res.data.forEach(x => (x.value = parseInt(x.value)));
        this.list.payTypeList = res.data;
      });
      product.getStatus().then(res => {
        res.data.forEach(x => (x.value = parseInt(x.value)));
        this.list.statusList = res.data;
      });
    },
    select: function(selection) {
      this.selection = selection;
    },
    selectChange: function(selection) {
      this.selection = selection;
    },
    onSelectAll: function(selection) {
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
    setCellStyle({ row, column }) {
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
