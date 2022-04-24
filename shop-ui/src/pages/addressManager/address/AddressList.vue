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
  name: "addressList",
  data() {
    return {
      searchForm: [
        {
          type: "Input",
          label: "姓名",
          prop: "name",
          multiple: false,
          clearable: true,
          placeholder: "请输入订单编号"
        }
      ],
      searchHandle: [
        {
          label: "查询",
          page: "addressList",
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
            this.searchData.name = "";
          }
        }
      ],
      tableHandles: [
        {
          label: "添加",
          type: "primary",
          size: "small",
          page: "AddAddress",
          btn: "Add",
          handle: () => {
            this.$router.push({
              path: "/address/addAddress",
              query: { isEdit: false }
            });
          }
        },
        {
          label: "编辑",
          type: "primary",
          size: "small",
          page: "AddBrand",
          btn: "Edit",
          handle: () => {
            if (this.selection.length !== 1) {
              this.$message.error("请选择一条记录!");
            } else {
              let that = this;
              this.$router.push({
                path: "/address/addAddress",
                query: {
                  isEdit: true,
                  id: that.selection[0].id
                }
              });
            }
          }
        },
        {
          label: "删除",
          type: "danger",
          size: "small",
          page: "AddBrand",
          btn: "Delete",
          handle: () => {
            if (this.selection.length <= 0) {
              this.$message.error("请选择一条或多条记录!");
            } else {
              this.$confirm("此操作将永久删除已选中数据, 是否继续?", "提示", {
                confirmButtonText: "确定",
                cancelButtonText: "取消",
                type: "warning"
              }).then(() => {
                product.deleteAddressById(
                  //传递一个列表
                  this.selection.map(s => {
                    return s.id;
                  })
                )
                  .then(res => {
                  this.$message.success(res.info);
                  this.getTableData();
                });
              });
            }
          }
        }
      ],
      tableData: [],
      total: 0,
      sortName: "",
      sortType: "",
      columns: [],
      selection: [],
      searchData: {
        pageNum: this.$route.query.pageNum
          ? parseInt(this.$route.query.pageNum)
          : 1,
        pageSize: 200,
        name: ""
      },
      list: ""
    };
  },
  created() {
    this.getColumns();
    //一加载页面就运行
    //this.getTableData();
  },
  watch: {},
  activated() {
    this.getTableData();
    },
  methods: {
    getColumns: function() {
      this.$root.$children[0].getColumns("/address/addressList").then(res => {
        this.columns = this.$columns(res, true);
      });
    },
    getTableData: function(page) {
      this.searchData.pageNum = page ? page : 1;
      product.getAddressList(this.searchData).then(res => {
        this.tableData = res.data.records;
        this.total = res.data.total;
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
