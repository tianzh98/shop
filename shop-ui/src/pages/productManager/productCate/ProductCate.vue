<template>
  <div>
    <Query
      size="mini"
      labelWidth="80px"
      :itemWidth="7"
      :searchData="searchData"
      :searchForm="searchForm"
      :searchHandle="searchHandle"
      :list="list"
    />
    <el-main class="table-main" v-if="columns.length > 0">
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
        :tableHandles="tableHandles"
        :rowClassName="rowClassName"
        @on-select-all="onSelectAll"
        @on-select="select"
        @on-selection-change="selectChange"
        @on-change="pageChange"
        @on-page-size-change="pageSizeChange"
      ></r-table>
    </el-main>
  </div>
</template>
<script>
import * as product from "@/http/implement/product";

export default {
  name: "user",
  data() {
    return {
      searchForm: [],
      searchHandle: [
        {
          label: "查询",
          page: "productList",
          type: "primary",
          handle: () => {
            this.getTableData();
          }
        }
      ],
      tableData: [],
      total: 0,
      tableHandles: [
        {
          label: "添加",
          type: "primary",
          size: "small",
          page: "productCate",
          btn: "Add",
          handle: () => {
            this.$router.push({
              path: "/product/productCateDetail",
              query: { state: "add" }
            });
          }
        }
      ],
      list: {
        userStatusCodeArrayList: [],
        orgNameList: []
      },
      columns: [],
      selection: [],
      dialogVisible: false,

      // searchData 表格查询条件参数
      searchData: {
        pageNum: this.$route.query.pageNum
          ? parseInt(this.$route.query.pageNum)
          : 1,
        pageSize: 200
      }
    };
  },
  created() {
    this.getColumns();
    this.getTableData();
    this.getENData();
  },
  activated() {
    this.getTableData();
  },
  methods: {
    getColumns: function() {
      this.$root.$children[0]
        .getColumns("/product/getProductCateList")
        .then(res => {
          this.columns = this.$columns(res, true);
        });
    },
    getENData: function() {
      // let that = this;
      // this.$root.$children[0].getDataDic("ENUserStatus").then(res => {
      //   that.list.userStatusCodeArrayList = res;
      // });
    },
    getTableData: function(page) {
      this.searchData.pageNum = page ? page : 1;
      product.getProductCateList(this.searchData).then(res => {
        this.tableData = res.data.records;
        this.total = res.data.total;
      });
    },
    rowClassName: function({ row }) {
      if (row.level === 1) {
        return "success-row";
      }
    },
    // Triggered when the number of pages changes
    pageChange(page) {
      this.getTableData(page);
    },
    // Triggered when the number of display bars per page of the table changes
    pageSizeChange(pageSize) {
      if (this.searchData.pageSize !== pageSize) {
        this.searchData.pageSize = pageSize;
        this.searchData.pageNum = 1;
        this.getTableData();
      }
    },
    select: function(selection) {
      this.selection = selection;
    },
    selectChange: function(selection) {
      this.selection = selection;
    },
    onSelectAll: function(selection) {
      this.selection = selection;
    }
  }
};
</script>
<style lang="less" scoped>
.ces-search {
  overflow: hidden;
}

a {
  margin-left: 10px;
}

.el-select-dropdown__item {
  font-size: 12px;
}

.el-input--small {
  font-size: 12px;
}
</style>
