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
  name: "Brand",
  data() {
    return {
      searchForm: [
        {
          type: "Select",
          label: "商品品牌",
          prop: "id",
          multiple: false,
          change: value => {
            this.searchData.id = value;
          },
          placeholder: "请选择"
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
            this.searchData.id = "";
          }
        }
      ],
      tableData: [],
      total: 0,
      sortName: "",
      sortType: "",
      /* columns: [
        { key: "id", label: "品牌编号", prop: "id" },
        { key: "name", label: "品牌名字", prop: "name" },
        { key: "firstLetter", label: "品牌首字母", prop: "firstLetter" },
        { key: "sort", label: "排序", prop: "sort" },
        { key: "brandStory", label: "品牌故事", prop: "brandStory" }
      ],*/
      columns: [],
      selection: [],
      searchData: {
        pageNum: this.$route.query.pageNum
          ? parseInt(this.$route.query.pageNum)
          : 1,
        pageSize: 200,
        //品牌id下拉
        id: ""
      },
      list: {
        idList: []
      }
    };
  },
  created() {
    //一加载页面就运行
    this.getTableData();
    this.getColumns();
  },
  watch: {},
  activated() {},
  methods: {
    getTableData: function(page) {
      this.searchData.pageNum = page ? page : 1;
      product.getBrandDetail(this.searchData).then(res => {
        this.tableData = res.data.records;
        this.total = res.data.total;
      });
      product.getBrands().then(res => {
        res.data.forEach(x => (x.value = parseInt(x.value)));
        this.list.idList = res.data;
      });
    },
    getColumns: function() {
      this.$root.$children[0].getColumns("/product/brandList").then(res => {
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
