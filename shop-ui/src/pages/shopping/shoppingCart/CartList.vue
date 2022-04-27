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
  name: "CartList",
  data() {
    return {
      searchForm: [
        {
          type: "Select",
          label: "商品品牌",
          prop: "productBrand",
          multiple: false,
          change: value => {
            this.searchData.productBrand = value;
          },
          placeholder: "请选择"
        }
        /*  {
          type: "Input",
          label: "商品名称",
          prop: "name",
          placeholder: "请输入",
          clearable: true
        },
        {
          type: "Input",
          label: "商品货号",
          prop: "productSn",
          clearable: true,
          placeholder: "请输入"
        }*/
      ],
      searchHandle: [
        {
          label: "查询",
          page: "CartList",
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
          page: "AddCart",
          btn: "Add",
          handle: () => {
            this.$router.push({
              path: "/shopping/addCart",
              query: { isEdit: false }
            });
          }
        },
        {
          label: "编辑",
          type: "primary",
          size: "small",
          page: "AddCart",
          btn: "Edit",
          handle: () => {
            if (this.selection.length !== 1) {
              this.$message.error("请选择一条记录!");
            } else {
              let that = this;
              this.$router.push({
                path: "/shopping/addCart",
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
          page: "CartList",
          btn: "Delete",
          handle: () => {
            if (this.selection.length <= 0) {
              this.$message.error("请选择一条或多条记录!");
            } else {
              this.$confirm(
                "此操作将永久删除已选中数据(如果是一级分类则其子分类也会被删除), 是否继续?",
                "提示",
                {
                  confirmButtonText: "确定",
                  cancelButtonText: "取消",
                  type: "warning"
                }
              ).then(() => {
                product
                  .deleteCartItemById(
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
      list: {
        productBrandList: []
      },
      columns: [],
      selection: [],
      dialogVisible: false,

      // searchData 表格查询条件参数
      searchData: {
        pageNum: this.$route.query.pageNum
          ? parseInt(this.$route.query.pageNum)
          : 1,
        pageSize: 200,
        productBrand: ""
      }
    };
  },
  created() {
    this.getColumns();
    this.getTableData();
  },
  activated() {
    this.getTableData();
  },
  methods: {
    getColumns: function() {
      this.$root.$children[0].getColumns("/shopping/cart").then(res => {
        this.columns = this.$columns(res, true);
      });
    },
    getProductBrand: function() {
      product.getBrands().then(res => {
        res.data.forEach(x => (x.value = parseInt(x.value)));
        this.list.productBrandList = res.data;
      });
    },
    getTableData: function(page) {
      this.searchData.pageNum = page ? page : 1;
      product.getCartItemList(this.searchData).then(res => {
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
