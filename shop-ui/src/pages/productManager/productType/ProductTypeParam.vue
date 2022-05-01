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
      tableHandles: [
        {
          label: "添加",
          type: "primary",
          size: "small",
          page: "AddProductTypeParam",
          btn: "Add",
          handle: () => {
            let data = {
              type: this.$route.query.type
            };
            this.$router.push({
              path: "/product/addProductTypeParam",
              query: {
                isEdit: false,
                type: data.type
              }
            });
          }
        },
        {
          label: "编辑",
          type: "primary",
          size: "small",
          page: "AddProductTypeParam",
          btn: "Add",
          handle: () => {
            let data = {
              type: this.$route.query.type
            };
            if (this.selection.length !== 1) {
              this.$message.error("请选择一条记录!");
            } else {
              let that = this;
              this.$router.push({
                path: "/product/addProductTypeParam",
                query: {
                  isEdit: true,
                  id: that.selection[0].id,
                  type: data.type
                }
              });
            }
          }
        },
        {
          label: "删除",
          type: "danger",
          size: "small",
          page: "AddProductTypeParam",
          btn: "Add",
          handle: () => {
            if (this.selection.length <= 0) {
              this.$message.error("请选择一条!"); //或多条记录
            } else {
              this.$confirm("此操作将永久删除已选中数据, 是否继续?", "提示", {
                confirmButtonText: "确定",
                cancelButtonText: "取消",
                type: "warning"
              }).then(() => {
                let data = {
                  id: this.selection[0].id
                };
                product.deleteProductAttributeParamById(data).then(res => {
                  this.$message.success(res.info);
                  this.getAttributeParam();
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
  //页面一激活就加载这个方法
  activated() {
    this.getAttributeParam();
  },
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
