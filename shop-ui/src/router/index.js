import Vue from "vue";
import VueRouter from "vue-router";
import index from "@/pages/Index";
import localCache from "@/utils/cache";

Vue.use(VueRouter);
const originalPush = VueRouter.prototype.push;
//重写push方法，统一处理错误捕获
VueRouter.prototype.push = function push(location) {
  return originalPush.call(this, location).catch(err => err);
};
//配置默认页面,将默认页面的文件名赋值给defaultPage
let defaultInfo = {
  defaultPage: "Home",
  defaultTitle: "首页"
};
let home = {
  path: "/home",
  alias: "",
  name: "Home",
  meta: { title: "首页" },
  component: () => import(/* webpackChunkName: "home" */ "@/pages/Home.vue")
};

const routes = [
  {
    path: "/",
    name: "index",
    component: index,
    children: [home]
  },
  {
    path: "/Login",
    name: "Login",
    component: () => import(/* webpackChunkName: "login" */ "@/pages/Login.vue")
  },
  {
    path: "/Register",
    name: "Register",
    component: () =>
      import(/* webpackChunkName: "login" */ "@/pages/Register.vue")
  },
  {
    path: "*",
    name: "error",
    component: () => import("@/pages/404")
  },
  {
    path: "/product",
    component: index,
    redirect: "/product/productList",
    name: "product",
    meta: { title: "商品" },
    children: [
      {
        path: "productList",
        name: "productList",
        component: () => import("@/pages/productManager/product/Product"),
        meta: { title: "商品列表" }
      },
      {
        path: "productDetail",
        name: "productDetail",
        component: () => import("@/pages/productManager/product/ProductDetail"),
        meta: { title: "添加商品" }
      },
      {
        path: "productCate",
        name: "productCate",
        component: () =>
          import("@/pages/productManager/productCate/ProductCate"),
        meta: { title: "商品分类", icon: "product-cate" }
      },
      {
        path: "productCateDetail",
        name: "productCateDetail",
        component: () =>
          import("@/pages/productManager/productCate/ProductCateDetail"),
        meta: { title: "添加商品分类" },
        hidden: true
      },
      {
        path: "brandList",
        name: "brandList",
        component: () => import("@/pages/productManager/brandMannger/Brand"),
        meta: { title: "品牌管理", icon: "product-cate" }
      },
      {
        path: "addBrand",
        name: "addBrand",
        component: () => import("@/pages/productManager/brandMannger/AddBrand"),
        meta: { title: "增加品牌", icon: "product-cate" }
      },
      {
        path: "productType",
        name: "productType",
        component: () =>
          import("@/pages/productManager/productType/ProductType"),
        meta: { title: "添加商品类型" },
        hidden: true
      },
      {
        path: "productTypeParam",
        name: "productTypeParam",
        component: () =>
          import("@/pages/productManager/productType/ProductTypeParam"),
        meta: { title: "添加商品类型" },
        hidden: true
      },
      {
        path: "addProductType",
        name: "addProductType",
        component: () =>
          import("@/pages/productManager/productType/AddProductType"),
        meta: { title: "添加商品类型" },
        hidden: true
      },
      {
        path: "addProductTypeParam",
        name: "addProductTypeParam",
        component: () =>
          import("@/pages/productManager/productType/AddProductTypeParam"),
        meta: { title: "添加商品类型属性" },
        hidden: true
      }
    ]
  },
  {
    path: "/order",
    component: index,
    redirect: "/order/orderList",
    name: "order",
    meta: { title: "订单" },
    children: [
      {
        path: "orderList",
        name: "orderList",
        component: () => import("@/pages/orderManager/order/orderList"),
        meta: { title: "订单管理", icon: "order-cate" }
      }
    ]
  },
  {
    path: "/address",
    component: index,
    redirect: "/address/addressList",
    name: "address",
    meta: { title: "订单" },
    children: [
      {
        path: "addressList",
        name: "addressList",
        component: () => import("@/pages/addressManager/address/AddressList"),
        meta: { title: "地址管理", icon: "order-cate" }
      },
      {
        path: "addAddress",
        name: "addAddress",
        component: () => import("@/pages/addressManager/address/AddAddress"),
        meta: { title: "增加地址", icon: "order-cate" }
      }
    ]
  },
  {
    path: "/shopping",
    component: index,
    redirect: "/shopping/cartList",
    name: "shopping",
    meta: { title: "下单" },
    children: [
      {
        path: "cartList",
        name: "cartList",
        component: () => import("@/pages/shopping/shoppingCart/CartList"),
        meta: { title: "下单", icon: "order-cate" }
      },
      {
        path: "addCart",
        name: "addCart",
        component: () => import("@/pages/shopping/shoppingCart/AddCart"),
        meta: { title: "编辑订单", icon: "order-cate" }
      }
    ]
  }
];
// const files = require.context("@/pages", true, /router\.js$/);
// let child = files.keys().map(key => {
//   const page = require("@/pages" + key.replace(".", ""));
//   return page.default;
// });
// child = child.reduce((all, item) => {
//   for (let key in item) {
//     all.push(item[key]);
//   }
//   return all;
// }, []);

// let others = child.filter(item => {
//   if (item.name === defaultInfo.defaultPage) {
//     item.alias = "";
//     home = item;
//   }
//   return item.name !== defaultInfo.defaultPage;
// });
localCache.setSession("home", {
  url: home.path,
  title: defaultInfo.defaultTitle,
  name: home.name
});
// routes[0].children = routes[0].children.concat([home]).concat(others);

const router = new VueRouter({
  mode: "history",
  base: process.env.BASE_URL,
  routes
});
router.beforeEach((to, from, next) => {
  // 登录界面登录成功之后，会把用户信息保存在会话
  // 存在时间为会话生命周期，页面关闭即失效。
  // let token = localCache.getCookie("token");
  // let userId = localCache.getCache("userInfo")?.id;
  if (to.path === "/Login") {
    if (localCache.isLogin()) {
      next({ path: "/" });
    } else {
      next();
    }
  } else {
    next();
    // if (!localCache.isLogin()) {
    //   next({ path: "/Login" });
    // } else {
    //   next();
    // }
  }
});
export default router;
