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
        @input_number="changeQuantity"
      ></r-table>
    </el-main>

    <el-drawer size="60%" title="填写订单" :visible.sync="createOrderDialogVisible">
      <div style="height: 90%;overflow: auto">
        <el-divider content-position="left">地址信息</el-divider>
        <el-row style="text-align: left">
          <span style="margin: 15px">{{receiveAddressLabel}}</span>
          <el-select @change="receiveAddressIdChange(receiveAddressId)" v-model="receiveAddressId" placeholder="请选择">
            <el-option
              v-for="item in receiveAddressDropDown"
              :key="item.value"
              :label="item.label"
              :value="item.value">
            </el-option>
          </el-select>
        </el-row>
        <el-divider content-position="left">费用信息</el-divider>
        <el-row style="text-align: left">
          <span style="margin: 15px">运费: {{freight}}元</span>
        </el-row>
        <el-divider content-position="left">商品信息</el-divider>
        <el-table
          show-summary
          :summary-method="getSummaries"
          :data="cartItemList"
          style="width: 100%">
          <el-table-column
            prop="mainPicture"
            label="商品图片"
            width="200px">
            <template slot-scope="scope">
              <img :src="scope.row.mainPicture" style="height: 100px"/>
            </template>
          </el-table-column>
          <el-table-column
            prop="productName"
            label="商品名"
            width="180">
          </el-table-column>
          <el-table-column
            prop="productAttrShow"
            label="规格">
          </el-table-column>
          <el-table-column
            prop="quantity"
            label="数量">
          </el-table-column>
          <el-table-column
            prop="price"
            label="单价">
          </el-table-column>
          <el-table-column
            prop="totalPrice"
            label="总价">
          </el-table-column>
        </el-table>
      </div>
      <div>
        <el-row>
          <el-button>应付金额:{{needPayAmount}}元</el-button>
          <el-button type="primary" @click="submitOrder">提交订单</el-button>
        </el-row>
      </div>
    </el-drawer>

    <pay-dialog :pay-dialog-visible="payDialogVisible" :order-id="orderId" @updatePayDialogVisible="updatePayDialogVisible"></pay-dialog>

  </div>
</template>
<script>
  import * as product from "@/http/implement/product";
  import {submitOrder} from "@/http/implement/order";
  import {resolvePicFileRes} from "@/utils/fileUtil";
  import PayDialog from "@/pages/orderManager/components/PayDialog";

  export default {
    name: "CartList",
    components: { PayDialog},

    data() {
      return {
        // 创建订单相关参数
        // 对话框是否显示
        createOrderDialogVisible: false,
        payDialogVisible: false,
        // 订单内的商品
        cartItemList: [],
        // 地址下拉列表
        receiveAddressDropDown: [],
        // 选中的地址
        receiveAddressId: null,
        receiveAddressLabel: null,
        orderId: null,
        // 运费
        freight: 10,
        priceSum: null,
        needPayAmount: null,
        //
        searchForm: [
          {
            type: "Input",
            label: "商品品牌",
            prop: "productBrand",
            multiple: false,
            placeholder: "请输入"
          },
          {
            type: "Input",
            label: "商品名称",
            prop: "productName",
            multiple: false,
            placeholder: "请输入"
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
            label: "去结算",
            type: "primary",
            size: "small",
            page: "AddCart",
            btn: "Edit",
            handle: () => {
              if (this.selection.length <= 0) {
                this.$message.error("请至少选择一件商品以创建订单");
              } else {
                // 获取地址下拉框信息
                product.getReceiveAddressDropDown().then(res => {
                  this.receiveAddressDropDown = res.data;
                  if (this.receiveAddressDropDown && this.receiveAddressDropDown.length > 0) {
                    // 选中的地址
                    this.receiveAddressId = this.receiveAddressDropDown[0].value;
                    this.receiveAddressLabel = this.receiveAddressDropDown[0].label;
                  }
                });
                // let that = this;
                let cartItemListMid = JSON.parse(JSON.stringify(this.selection));
                for(let i=0;i<cartItemListMid.length;i++){
                  cartItemListMid[i].totalPrice = cartItemListMid[i].quantity * cartItemListMid[i].price;
                }
                this.cartItemList = cartItemListMid;
                this.createOrderDialogVisible = true;
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
                this.$confirm("此操作将永久删除购物信息, 是否继续?", "提示", {
                  confirmButtonText: "确定",
                  cancelButtonText: "取消",
                  type: "warning"
                }).then(() => {
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
        // orderColumns 的商品数量不支持编辑
        orderColumns: [],
        selection: [],
        dialogVisible: false,

        // searchData 表格查询条件参数
        searchData: {
          pageNum: this.$route.query.pageNum
            ? parseInt(this.$route.query.pageNum)
            : 1,
          pageSize: 200,
          productBrand: "",
          productName: ""
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
      getColumns: function () {
        this.$root.$children[0].getColumns("/shopping/cart").then(res => {
          this.columns = this.$columns(res, true);
          // 深拷贝数组
          this.orderColumns = JSON.parse(JSON.stringify(this.columns));
          for (let i = 0; i < this.orderColumns.length; i++) {
            if (this.orderColumns[i].type === 'input_number') {
              this.orderColumns[i].type = null;
            }
          }
        });
      },
      submitOrder: function () {
        this.$confirm("是否提交订单?", "提示", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "info"
        }).then(() => {
          let orderSubmitReq = {
            cartItemList: this.cartItemList,
            receiveAddressId: this.receiveAddressId,
            freight: this.freight,
            priceSum: this.priceSum,
          };
          submitOrder(orderSubmitReq).then(res => {
            this.$message.success(res.info);
            // 关闭创建订单对话框
            this.createOrderDialogVisible = false;
            // 刷新一遍购物车
            this.getTableData();
            // 返回的订单id赋值给orderId
            this.orderId = res.data;
            // 打开付款页面
            this.payDialogVisible = true;
          });
        });
      },
      updatePayDialogVisible: function(payDialogVisible){
        this.payDialogVisible = payDialogVisible;
      },
      receiveAddressIdChange: function(newVal){
        for (let i =0;i< this.receiveAddressDropDown.length;i++){
          let dropDown = this.receiveAddressDropDown[i];
          if (dropDown.value === newVal) {
            this.receiveAddressLabel = dropDown.label;
            break;
          }
        }
      },
      getSummaries(param) {
        const {columns, data} = param;
        const sums = [];
        columns.forEach((column, index) => {
          if (index === 0) {
            sums[index] = '合计';
            return;
          }
          const values = data.map(item => Number(item[column.property]));
          if (!values.every(value => isNaN(value))) {
            sums[index] = values.reduce((prev, curr) => {
              const value = Number(curr);
              if (!isNaN(value)) {
                return prev + curr;
              } else {
                return prev;
              }
            }, 0);
            if (column.property === 'totalPrice') {
              this.priceSum = sums[index];
              this.needPayAmount =  this.priceSum + this.freight;
              sums[index] += ' 元';
            } else if(column.property === 'price') {
              sums[index] = '';
            }
          } else {
            sums[index] = '';
          }
        });

        return sums;
      },
      changeQuantity: function (cartItem) {
        // 更新购物车商品的数量信息
        let cartItemReq = {
          id: cartItem.id,
          quantity: cartItem.quantity
        };
        product.updateCartItemById(cartItemReq);
      },
      getProductAttrShowFromSpData: function (spData) {
        let productAttrShow = "";
        let spDataList = JSON.parse(spData);
        for (let i = 0; i < spDataList.length; i++) {
          let key = spDataList[i].key;
          let value = spDataList[i].value;
          if (i === 0) {
            productAttrShow += key + ":" + value;
          } else {
            productAttrShow += "," + (key + ":" + value);
          }
        }
        return productAttrShow;
      },
      getTableData: function (page) {
        this.tableData = [];
        this.searchData.pageNum = page ? page : 1;
        product.getCartItemList(this.searchData).then(res => {
          let records = res.data.records;
          if (records && records.length > 0) {
            for (let i = 0; i < records.length; i++) {
              records[i].productAttrShow = this.getProductAttrShowFromSpData(
                records[i].productAttr
              );
              records[i].mainPicture = resolvePicFileRes(records[i].mainPictureFile).url;
            }
          }
          this.tableData = records;
          this.total = res.data.total;
        });
      },
      rowClassName: function ({row}) {
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
      select: function (selection) {
        this.selection = selection;
      },
      selectChange: function (selection) {
        this.selection = selection;
      },
      onSelectAll: function (selection) {
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
