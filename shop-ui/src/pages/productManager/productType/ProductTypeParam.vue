<template>
  <div>
    <!--    <Query-->
    <!--      size="mini"-->
    <!--      labelWidth="100px"-->
    <!--      :itemWidth="7"-->
    <!--      :searchData="searchData"-->
    <!--      :searchForm="searchForm"-->
    <!--      :searchHandle="searchHandle"-->
    <!--      :list="list"-->
    <!--    >-->
    <!--    </Query>-->
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
  name: "ProductTypeParam",
  data() {
    return {
      searchHandle: [],
      tableHandles: [],
      tableData: [],
      total: 0,
      sortName: "",
      sortType: "",
      columns: [],
      selection: [],
      searchData: "",
      list: ""
    };
  },
  created() {
    //一加载页面就运行
    //this.getTableData();
    this.getColumns();
    this.getAttributeParam();
  },
  watch: {},
  computed: {
    isEdit() {
      return eval(this.$route.query.isEdit);
    }
  },
  activated() {},
  methods: {
    /*
    getTableData: function(page) {
      this.searchData.pageNum = page ? page : 1;
      let data=
        {
          id:this.$router.push.query.id,
          type:this.$router.push.query.type
        };
      product.getProductAttributeParam(data).then(res => {
        this.tableData = res.data;
        this.total = res.data.total;
      });
    },*/
    getAttributeParam: function() {
      let data = {
        // 获取上一个页面传进来的值
        id: this.$route.query.id,
        type: this.$route.query.type
      };
      product.getProductAttributeParam(data).then(res => {
        this.tableData = res.data;
        //this.total = res.data.total;
      });
    },
    getColumns: function() {
      this.$root.$children[0]
        .getColumns("/productType/productTypeParam")
        .then(res => {
          this.columns = this.$columns(res, true);
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
