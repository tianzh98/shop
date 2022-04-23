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
/*import {getProductCategories, getProductCategory} from "@/http/implement/product";*/

export default {
  name: "user",
  data() {
    return {
      searchForm: [
        {
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
        },
        {
          type: "Cascader",
          label: "商品分类",
          prop: "productCategoryId",
          multiple: false,
          change: value => {
            // 这里需要取value[1]，也就是二级分类。
            this.searchData.productCategoryId = value[value.length - 1];
          },
          placeholder: "请选择"
        },
        {
          type: "Select",
          label: "商品品牌",
          prop: "brandId",
          multiple: false,
          change: value => {
            this.searchData.brandId = value;
          },
          placeholder: "请选择"
        }
      ],
      searchHandle: [
        {
          label: "重置",
          page: "productList",
          type: "primary",
          handle: () => {
            this.searchData.productCategoryId = "";
            this.searchData.name = "";
            this.searchData.productSn = "";
            this.searchData.brandId = "";
          }
        },
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
          page: "productList",
          btn: "Add",
          handle: () => {
            this.$router.push({
              path: "/product/productDetail",
              query: { isEdit: false }
            });
          }
        },
        {
          label: "编辑",
          type: "primary",
          size: "small",
          page: "productList",
          btn: "Edit",
          handle: () => {
            let that = this;
            if (that.selection.length !== 1) {
              this.$message.error("请选择一条记录!");
            } else {
              this.$router.push({
                path: "/product/productDetail",
                query: {
                  isEdit: true,
                  id: that.selection[0].id
                }
              });
            }
          }
        },
        {
          label: "上架/下架",
          type: "primary",
          size: "small",
          page: "productList",
          btn: "Sku",
          handle: () => {
            if (this.selection.length <= 0) {
              this.$message.error("请选择一条或多条记录!");
            } else {
              // 校验选中的数据的上架/下架状态是否一致
              let publishStatus = this.selection[0].publishStatus;
              let publishStatusLabel = publishStatus === 1 ? "下架" : "上架";
              for (let i = 0; i < this.selection.length; i++) {
                if (publishStatus !== this.selection[i].publishStatus) {
                  this.$message.error("请选择相同上架/下架状态的记录!");
                  return;
                }
              }
              this.$confirm("是否确认" + publishStatusLabel + "?", "提示", {
                confirmButtonText: "确定",
                cancelButtonText: "取消",
                type: "warning"
              })
                .then(() => {
                  let productList = [];
                  for (let i = 0; i < this.selection.length; i++) {
                    productList.push({
                      id: this.selection[i].id,
                      publishStatus:
                        this.selection[i].publishStatus === 1 ? 0 : 1
                    });
                  }
                  product.updateProduct(productList).then(res => {
                    this.$message.success(res.info);
                    this.getTableData();
                  });
                })
                .catch(() => {});
            }
          }
        },
        // {
        //   label: "设置库存信息",
        //   type: "primary",
        //   size: "small",
        //   page: "productList",
        //   btn: "Sku",
        //   handle: () => {
        //     this.$router.push({
        //       path: "/product/productDTO",
        //       query: { state: "add" }
        //     });
        //   }
        // },
        {
          label: "删除",
          type: "primary",
          size: "small",
          page: "productList",
          btn: "Delete",
          handle: () => {
            if (this.selection.length <= 0) {
              this.$message.error("请选择一条或多条记录!");
            } else {
              this.$confirm("此操作将永久删除已选中数据, 是否继续?", "提示", {
                confirmButtonText: "确定",
                cancelButtonText: "取消",
                type: "warning"
              })
                .then(() => {
                  product
                    .deleteProductByIds(
                      this.selection.map(s => {
                        return s.id;
                      })
                    )
                    .then(res => {
                      this.$message.success(res.info);
                      this.getTableData();
                    });
                })
                .catch(() => {
                  // this.$message({
                  //   type: 'info',
                  //   message: '已取消删除'
                  // });
                });
            }
          }
        }
      ],
      list: {
        userStatusCodeArrayList: [],
        orgNameList: [],
        brandIdList: [],
        productCategoryIdList: []
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
        productCategoryId: "",
        name: "",
        productSn: "",
        brandId: ""
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
        .getColumns("/product/getProductList")
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
      product.getProductList(this.searchData).then(res => {
        this.tableData = res.data.records;
        this.total = res.data.total;
      });
      product.getBrands().then(res => {
        res.data.forEach(x => (x.value = parseInt(x.value)));
        this.list.brandIdList = res.data;
      });
      product.getProductCategory().then(res => {
        // res.data.forEach(x => x.value = parseInt(x.value));
        // res.data.children.forEach(x => x.value = parseInt(x.value));
        this.list.productCategoryIdList = res.data;
      });
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
